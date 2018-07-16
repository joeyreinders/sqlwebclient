package net.sqlwebclient.core.connections.queryexec;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.core.objects.result.ResultBuilder;
import net.sqlwebclient.core.objects.result.Tuple;
import net.sqlwebclient.exception.ExceptionCode;
import net.sqlwebclient.exception.ExceptionFactory;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TypeHelper;
import org.hibernate.dialect.Dialect;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.LiteralType;
import org.hibernate.type.SingleColumnType;
import org.hibernate.type.Type;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

final class HQLQueryRequestExecutor extends BaseQueryRequestExecutor {
	private TypeHelper typeHelper;

	@Override
	public Result execute() {
		final Stopwatch stopwatch = new Stopwatch().start();
		final ResultBuilder builder = prepareResultBuilder();
		final Session session = transakt().getSession();

		try {
			final Query query = session.createQuery(queryRequest().getQueryString());
			final List<?> queryResult = query.list();
			final Dialect dialect = getHibernateDialect();

			boolean first = true;
			ClassMetadata classMetadata = null;

			final ListIterator<?> iterator = queryResult.listIterator();
			while (iterator.hasNext()) {
				final Object row = iterator.next();

				if(row instanceof Object[]) {
					throw ExceptionFactory.create(ExceptionCode.ENTITY_MAPPING_NOT_FOUND);
				}

				if(first) {
					first = false;
					classMetadata = getSessionFactory().getClassMetadata(row.getClass());
					//Build colums
					buildColumnMapping(builder, classMetadata);
				}

				try {
					builder.tuple(createTupe(row, classMetadata, dialect));
				} catch (Exception e) {
					e.printStackTrace();
				}

				iterator.remove();
			}
		} catch (final JDBCException ex) {
			builder.error("SQL State: " + ex.getSQLState() + " - " + ex.getMessage());
		} finally {
			final String duration = stopwatch.stop().toString();
			builder.duration("Query took: " + duration);
		}

		return builder.build();
	}

	private List<String> getPropertyNames(final ClassMetadata classMetadata) {
		return ImmutableList.<String>builder()
				.addAll(Arrays.asList(classMetadata.getPropertyNames()))
				.add(classMetadata.getIdentifierPropertyName())
				.build();
	}

	private void buildColumnMapping(final ResultBuilder resultBuilder,
									final ClassMetadata classMetadata) {
		for(String propertyName : getPropertyNames(classMetadata)) {
			final Type type = classMetadata.getPropertyType(propertyName);
			final int sqlType = type instanceof SingleColumnType ? ((SingleColumnType) type).sqlType() : -1;
			final String entityName = classMetadata.getEntityName();

			resultBuilder.column(propertyName, sqlType, entityName);
		}
	}

	private Tuple createTupe(final Object object,
							 final ClassMetadata classMetadata,
							 final Dialect dialect) throws Exception {
		final Tuple tuple = Tuple.newInstance();

		for(String propertyName: getPropertyNames(classMetadata)) {
			final Object value;
			if(propertyName.equals(classMetadata.getIdentifierPropertyName())) {
				value = classMetadata.getIdentifier(object);
			} else {
				value = classMetadata.getPropertyValue(object, propertyName);
			}

			if(value == null) {
				tuple.put(propertyName, null);
			} else {
				final LiteralType literalType = (LiteralType) typeHelper.basic(value.getClass());
				final String sqlValue = literalType.objectToSQLString(value, dialect);

				tuple.put(propertyName, removeQuotes(sqlValue));
			}
		}

		return tuple;
	}

	@Override
	protected void init() {
		this.typeHelper = transakt().getSession().getTypeHelper();
	}
}

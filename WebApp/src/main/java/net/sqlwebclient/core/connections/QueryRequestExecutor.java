package net.sqlwebclient.core.connections;

import com.google.common.base.Stopwatch;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.core.objects.result.Column;
import net.sqlwebclient.core.objects.result.ResultBuilder;
import net.sqlwebclient.core.objects.result.Tuple;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.TypeHelper;
import org.hibernate.dialect.Dialect;
import org.hibernate.jdbc.AbstractWork;
import org.hibernate.type.LiteralType;

import java.sql.*;
import java.util.Map;

public final class QueryRequestExecutor {
    private final QueryRequest queryRequest;
    private final DatabaseConnection databaseConnection;

    private final TypeHelper typeHelper;

    private QueryRequestExecutor(final QueryRequest queryRequest,
								 final DatabaseConnection databaseConnection) {
        this.queryRequest = queryRequest;
        this.databaseConnection = databaseConnection;

        this.typeHelper = databaseConnection.getSessionFactory().getTypeHelper();
    }

	ResultBuilder execute(final Transakt transakt) {
		final Stopwatch stopwatch = new Stopwatch().start();
		final ResultBuilder builder = ResultBuilder.newInstance();
		final Session session = transakt.getSession();

		try {
			session.doWork(new AbstractWork() {
				@Override
				public void execute(final Connection connection) throws SQLException {
					executeRequest(builder, connection);
				}
			});
		} catch (final JDBCException ex) {
			builder.error("SQL State: " + ex.getSQLState() + " - " + ex.getMessage());
		} finally {
			final String duration = stopwatch.stop().toString();
			builder.duration("Query took: " + duration);
		}

		return builder;
	}

    private void executeRequest(    final ResultBuilder builder,
                                    final Connection connection) throws SQLException {
        final Statement stmt = connection.createStatement();
        try {
            final boolean b = stmt.execute(queryRequest.getQueryString());
            if(b) {
                final ResultSet resultSet = stmt.getResultSet();

                processMetaData(builder, resultSet.getMetaData());
                processResultSet(builder, resultSet);
            } else if (stmt.getUpdateCount() != -1) {
                builder.count(stmt.getUpdateCount());
            } else {
                builder.count(0);
            }
        } finally {
            stmt.close();
        }
    }

    private void processResultSet(final ResultBuilder builder,
                                  final ResultSet resultSet) throws SQLException {
        final Map<String, Column> columnMap = builder.buildColumnMap();
        final Dialect hibernateDialect = databaseConnection.getConnectionDetails().getDialect().getHibernateDialect();

        while(resultSet.next()) {
            final Tuple tuple = createTuple(resultSet, columnMap, hibernateDialect);

            builder.tuple(tuple);
        }
    }

    private Tuple createTuple(final ResultSet resultSet,
                              final Map<String, Column> columnMap,
                              final Dialect hibernateDialect) throws SQLException {
        final Tuple tuple = Tuple.newInstance();

        for(Map.Entry<String, Column> entry: columnMap.entrySet()) {
            final Object value = resultSet.getObject(entry.getKey());

            try {
                if(value == null) {
                    tuple.put(entry.getKey(), null);
                } else {
                    final LiteralType literalType = (LiteralType) typeHelper.basic(value.getClass());
                    final String sqlValue = literalType.objectToSQLString(value, hibernateDialect);

                    tuple.put(entry.getKey(), removeQuotes(sqlValue));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return tuple;
    }

    private String removeQuotes(final String sqlValue) {
        return sqlValue.replaceAll("'", "");
    }

    private void processMetaData(final ResultBuilder builder,
                                 final ResultSetMetaData resultSetMetaData) throws SQLException{
        for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){
            builder.column(
                resultSetMetaData.getColumnName(i),
                resultSetMetaData.getColumnType(i),
                resultSetMetaData.getTableName(i)
            );
        }
    }

    public static QueryRequestExecutor newInstance(final QueryRequest queryRequest,
                                                   final DatabaseConnection databaseConnection) {
        return new QueryRequestExecutor(queryRequest, databaseConnection);
    }
}

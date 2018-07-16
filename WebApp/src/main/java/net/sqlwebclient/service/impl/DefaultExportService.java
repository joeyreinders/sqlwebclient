package net.sqlwebclient.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sqlwebclient.core.objects.result.Column;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.core.objects.result.Tuple;
import net.sqlwebclient.service.ExportService;
import net.sqlwebclient.util.StringPrintWriter;
import org.hibernate.dialect.Dialect;
import org.hibernate.sql.Insert;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class DefaultExportService implements ExportService {
    @Override
    public String generateSQLInsert(final Dialect dialect,
                                    final Result result) {
        final StringPrintWriter stringPrintWriter = StringPrintWriter.newInstance();

        final Set<String> tables = getTable(result.getColumns());
        if(tables.size() != 1) {
            throw new RuntimeException();
        }
        final String table = (String) tables.toArray()[0];
        for(Tuple tuple: result.getRows()) {
            final Insert insert = new Insert(dialect)
                    .setTableName(table);

			loopTuple(insert, tuple, result.getColumns());

            stringPrintWriter.println(insert.toStatementString() + ";");
        }

        return stringPrintWriter.getString();
    }

	private void loopTuple(final Insert insert,
						   final Tuple tuple,
						   final Collection<Column> columns) {
		final Map<String, String> sorted = tuple.getSorted(columns);
		for(Map.Entry<String, String> entry: sorted.entrySet()) {
			final String value = String.valueOf(entry.getValue());
			insert.addColumn(entry.getKey(), "'" + value + "'");
		}
	}

    @Override
    public String generateCSV(final Result result) {
        final StringPrintWriter writer = StringPrintWriter.newInstance();

        //do header
        final List<String> headers = Lists.transform(result.getColumns(), new Function<Column, String>() {
            @Nullable
            @Override
            public String apply(@Nullable final Column input) {
                return input != null ? input.getColumnName() : "";
            }
        });
        writer.println(Joiner.on(',').join(headers));

        //do rows
        for(Tuple tuple: result.getRows()) {
			final Map<String, String> sorted = tuple.getSorted(result.getColumns());
            writer.println(Joiner.on(',').join(sorted.values()));
        }


        return writer.getString();
    }

    private Set<String> getTable(final Collection<Column> columns) {
        final Set<String> res = Sets.newHashSet();
        for(Column column: columns) {
            res.add(column.getTableName());
        }

        return res;
    }
}

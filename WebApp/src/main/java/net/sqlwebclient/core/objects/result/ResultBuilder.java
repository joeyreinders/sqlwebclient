package net.sqlwebclient.core.objects.result;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.sqlwebclient.util.Builder;

import java.util.List;
import java.util.Map;

public final class ResultBuilder implements Builder<Result> {
    private final List<Column> columns = Lists.newArrayList();
    private final List<Tuple> rows = Lists.newArrayList();

    private long count = -1;

    private String error;
    private String info;
    private String duration;
    private String query;
    private long uuid;
    private long connectionDetails;
	private String type;

    private ResultBuilder() {
        //hide constructor
    }

    public ResultBuilder duration(final String duration) {
        this.duration = duration;
        return this;
    }

    public ResultBuilder count(final long count) {
        this.count = count;
        return this;
    }

    public ResultBuilder error(final String error) {
        this.error = error;
        return this;
    }

    public ResultBuilder info(final String info) {
        this.info = info;
        return this;
    }

	public ResultBuilder type(final String type) {
		this.type = type;
		return this;
	}

    public ResultBuilder column(final String columnName,
                                final int jdbcType,
                                final String tableName) {
        columns.add(Column.newInstance(columnName, jdbcType, tableName));
        return this;
    }

    public ResultBuilder tuple(final Tuple tuple) {
        rows.add(tuple);
        return this;
    }

    public ResultBuilder query(final String query) {
        this.query = query;
        return this;
    }

    public ResultBuilder uuid(final long uuid) {
        this.uuid = uuid;
        return this;
    }

    public ResultBuilder connectionDetails(final long connectionDetails) {
        this.connectionDetails = connectionDetails;
        return this;
    }

    @Override
    public Result build() {
        final Message message = new Message(error, info, duration);
        final Meta meta = new Meta(query, uuid, connectionDetails, type);

        if(count == -1) {
            count(rows.size());
        }

        return new Result(
                ImmutableList.copyOf(columns),
                message,
                count,
                ImmutableList.copyOf(rows),
                meta
        );
    }

    public Map<String, Column> buildColumnMap() {
        final ImmutableMap.Builder<String, Column> res = ImmutableMap.builder();

        for(Column column: columns) {
            res.put(column.getColumnName(), column);
        }

        return res.build();
    }

    public static ResultBuilder newInstance() {
        return new ResultBuilder();
    }
}

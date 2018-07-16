package net.sqlwebclient.core.objects.result;

import java.util.List;

public final class Result {
    private final List<Column> columns;

    private final Message message;

    private final long count;

    private final List<Tuple> rows;

    private final Meta meta;

    public Result() { //only for gson
        this(null, null, 0, null, null);
    }

    Result(final List<Column> columns,
                  final Message message,
                  final long count,
                  final List<Tuple> rows,
                  final Meta meta) {
        this.columns = columns;
        this.message = message;
        this.count = count;
        this.rows = rows;
        this.meta = meta;    }

    public List<Column> getColumns() {
        return columns;
    }

    public Message getMessage() {
        return message;
    }

    public long getCount() {
        return count;
    }

    public List<Tuple> getRows() {
        return rows;
    }

    public Meta getMeta() {
        return meta;
    }
}

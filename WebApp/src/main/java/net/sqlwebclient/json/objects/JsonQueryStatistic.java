package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.objects.QueryStatistic;

public final class JsonQueryStatistic extends Json {
    private static final long serialVersionUID = 1099166124788298663L;

    private final String queryString;
    private final long executionTime;
    private final long rowCount;
    private final String queryType;
    private final String connectionName;

    public JsonQueryStatistic(final String queryString,
                              final long executionTime,
                              final long rowCount,
                              final String queryType,
                              final String connectionName) {
        this.queryString = queryString;
        this.executionTime = executionTime;
        this.rowCount = rowCount;
        this.queryType = queryType;
        this.connectionName = connectionName;
    }

    public String getQueryString() {
        return queryString;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public long getRowCount() {
        return rowCount;
    }

    public String getQueryType() {
        return queryType;
    }

    public static JsonQueryStatistic valueOf(final QueryStatistic queryStatistic) {
        return new JsonQueryStatistic(
                queryStatistic.getQueryString(),
                queryStatistic.getExecutionTime(),
                queryStatistic.getRowCount(),
                queryStatistic.getQueryType() == null ? "" : queryStatistic.getQueryType().name(),
                queryStatistic.getConnectionDetails().getName()
        );
    }
}

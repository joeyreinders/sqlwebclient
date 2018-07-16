package net.sqlwebclient.core.objects;

import net.sqlwebclient.util.Builder;

public final class QueryStatistic {
    private final String queryString;
    private final long executionTime;
    private final long rowCount;
    private final QueryType queryType;
    private final ConnectionDetails connectionDetails;

    private QueryStatistic(final QueryStatisticBuilder builder) {
        this.queryString = builder.queryString;
        this.executionTime = builder.executionTime;
        this.rowCount = builder.rowCount;
        this.queryType = builder.queryType;
        this.connectionDetails = builder.connectionDetails;
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

    public QueryType getQueryType() {
        return this.queryType;
    }

    public ConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

    public static QueryStatisticBuilder builder() {
        return new QueryStatisticBuilder();
    }

    public static enum QueryType {
        HQL,
        SQL
    }

    public static final class QueryStatisticBuilder implements Builder<QueryStatistic> {
        private static final long serialVersionUID = 3505954172573891016L;

        private String queryString;
        private long executionTime;
        private long rowCount;
        private QueryType queryType;
        private ConnectionDetails connectionDetails;

        private QueryStatisticBuilder(){}

        public QueryStatisticBuilder setQueryString(final String queryString) {
            this.queryString = queryString;
            return this;
        }

        public QueryStatisticBuilder setExecutionTime(final long executionTime) {
            this.executionTime = executionTime;
            return this;
        }

        public QueryStatisticBuilder setRowCount(final long rowCount) {
            this.rowCount = rowCount;
            return this;
        }

        public QueryStatisticBuilder setQueryType(final QueryType queryType) {
            this.queryType = queryType;
            return this;
        }

        public QueryStatisticBuilder setConnectionDetails(final ConnectionDetails connectionDetails) {
            this.connectionDetails = connectionDetails;
            return this;
        }

        @Override
        public QueryStatistic build() {
            return new QueryStatistic(this);
        }
    }
}

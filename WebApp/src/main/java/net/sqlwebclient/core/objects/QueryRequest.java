package net.sqlwebclient.core.objects;

import net.sqlwebclient.util.Builder;

import java.io.Serializable;

public final class QueryRequest implements Serializable {
    private static final long serialVersionUID = 510437727215616895L;

    private final String queryString;

    private final Integer start;

    private final Integer limit;

    private final ConnectionDetails connectionDetails;

	private final String queryType;

    private QueryRequest(final QueryRequestBuilder builder) {
		this.queryString = builder.queryString;
		this.start = builder.start;
		this.limit = builder.limit;
		this.connectionDetails = builder.connectionDetails;
		this.queryType = builder.queryType;
    }

    public String getQueryString() {
        return queryString;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLimit() {
        return limit;
    }

    public ConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

	public String getQueryType() {
		return queryType;
	}

    public static QueryRequestBuilder builder() {
        return new QueryRequestBuilder();
    }

	public static QueryRequest simpleRequest(	final ConnectionDetails connectionDetails,
												final String queryString) {
		return builder()
				.addConnectionDetails(connectionDetails)
				.addQueryString(queryString)
				.build();
	}

	public static final class QueryRequestBuilder implements Builder<QueryRequest> {
        private String queryString;

        private Integer start;

        private Integer limit;

        private ConnectionDetails connectionDetails;

		private String queryType;

        @Override
        public QueryRequest build() {
            return new QueryRequest(this);
        }

        public QueryRequestBuilder addQueryString(final String queryString) {
            this.queryString = queryString;
            return this;
        }

        public QueryRequestBuilder addStart(final Integer start) {
            this.start = start;
            return this;
        }

        public QueryRequestBuilder addLimit(final Integer limit) {
            this.limit = limit;
            return this;
        }

        public QueryRequestBuilder addConnectionDetails(final ConnectionDetails connectionDetails) {
            this.connectionDetails = connectionDetails;
            return this;
        }

		public QueryRequestBuilder addQueryType(final String queryType) {
			this.queryType = queryType;
			return this;
		}
    }
}

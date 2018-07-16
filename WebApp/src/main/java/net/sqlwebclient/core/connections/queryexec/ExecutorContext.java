package net.sqlwebclient.core.connections.queryexec;

import net.sqlwebclient.core.connections.Transakt;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.util.Builder;

import java.util.Objects;

final class ExecutorContext {
	private final QueryRequest queryRequest;
	private final Transakt transakt;

	ExecutorContext(final QueryRequest queryRequest,
					final Transakt transakt) {
		this.queryRequest = queryRequest;
		this.transakt = transakt;
	}

	public QueryRequest getQueryRequest() {
		return queryRequest;
	}

	public Transakt getTransakt() {
		return transakt;
	}

	public static ExecutorContextBuilder builder() {
		return new ExecutorContextBuilder();
	}

	public static final class ExecutorContextBuilder implements Builder<ExecutorContext> {
		private QueryRequest queryRequest;
		private Transakt transakt;

		private ExecutorContextBuilder() {
			//hide constructor
		}

		public ExecutorContextBuilder add(final QueryRequest queryRequest) {
			this.queryRequest = queryRequest;
			return this;
		}

		public ExecutorContextBuilder add(final Transakt transakt) {
			this.transakt = transakt;
			return this;
		}

		@Override
		public ExecutorContext build() {
			Objects.requireNonNull(queryRequest);
			Objects.requireNonNull(transakt);

			return new ExecutorContext(queryRequest, transakt);
		}
	}
}

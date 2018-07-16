package net.sqlwebclient.core.connections.queryexec;

import net.sqlwebclient.core.connections.Transakt;
import net.sqlwebclient.core.objects.QueryRequest;

public final class QueryExecutorFactory {
	public static QueryRequestExecutor createExecutor(final QueryRequest request,
											   final Transakt transakt) {
		final BaseQueryRequestExecutor executor;
		if("sql".equals(request.getQueryType())) {
			executor = new SQLQueryRequestExecutor();
		} else if("hql".equals(request.getQueryType())) {
			executor = new HQLQueryRequestExecutor();
		} else {
			throw new RuntimeException("executor type not found");
		}

		executor.setExecutorContext(ExecutorContext.builder()
						.add(request)
						.add(transakt)
						.build()
		);

		executor.init();

		return executor;
	}
}

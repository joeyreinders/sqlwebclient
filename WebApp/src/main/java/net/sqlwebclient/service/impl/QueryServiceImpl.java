package net.sqlwebclient.service.impl;

import com.google.inject.Inject;
import net.sqlwebclient.core.connections.DatabaseConnection;
import net.sqlwebclient.core.connections.DatabaseConnectionFactoryCache;
import net.sqlwebclient.core.connections.Transakt;
import net.sqlwebclient.core.connections.queryexec.QueryExecutorFactory;
import net.sqlwebclient.core.connections.queryexec.QueryRequestExecutor;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.service.QueryService;

import java.util.Objects;

final class QueryServiceImpl implements QueryService {
    private final DatabaseConnectionFactoryCache connectionFactory;

    @Inject
    QueryServiceImpl(final DatabaseConnectionFactoryCache connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Result run(final QueryRequest request) {
		validatRequest(request);

		final DatabaseConnection connection = connectionFactory.getConnection(request);
		final Transakt transakt = connection.createTransaction();
		final QueryRequestExecutor executor = QueryExecutorFactory.createExecutor(request, transakt);
		final Result res = executor.execute();
		transakt.close();
		return res;
    }

	private void validatRequest(final QueryRequest request) {
		Objects.requireNonNull(request.getQueryType(), "Query type is missing");
		Objects.requireNonNull(request.getQueryString(), "Query string is missing");
		Objects.requireNonNull(request.getConnectionDetails(), "ConnectionDetails are missing");
	}
}

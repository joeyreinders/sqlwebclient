package net.sqlwebclient.core.connections.queryexec;

import com.google.inject.AbstractModule;

public final class QueryExecutorModule extends AbstractModule {
	private QueryExecutorModule() {
		//hide
	}

	@Override
	protected void configure() {
		bind(QueryExecutorFactory.class);
	}

	public static QueryExecutorModule newInstance() {
		return new QueryExecutorModule();
	}
}

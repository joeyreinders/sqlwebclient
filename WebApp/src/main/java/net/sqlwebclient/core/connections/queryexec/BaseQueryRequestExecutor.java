package net.sqlwebclient.core.connections.queryexec;

import net.sqlwebclient.core.connections.Transakt;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.core.objects.result.ResultBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;

abstract class BaseQueryRequestExecutor implements QueryRequestExecutor {
	private ExecutorContext executorContext;

	@Override
	public void setExecutorContext(final ExecutorContext context) {
		this.executorContext = context;
	}

	protected abstract void init();

	protected QueryRequest queryRequest() {
		return executorContext.getQueryRequest();
	}

	protected Transakt transakt() {
		return executorContext.getTransakt();
	}

	protected ResultBuilder prepareResultBuilder() {
		return ResultBuilder.newInstance()
			.connectionDetails(executorContext.getQueryRequest().getConnectionDetails().getUuid())
			.query(executorContext.getQueryRequest().getQueryString())
			.type(executorContext.getQueryRequest().getQueryType())
		;
	}

	protected ExecutorContext getExecutorContext() {
		return this.executorContext;
	}

	protected SessionFactory getSessionFactory() {
		return executorContext.getTransakt().getSession().getSessionFactory();
	}

	protected String removeQuotes(final String sqlValue) {
		return sqlValue.replaceAll("'", "");
	}

	protected Dialect getHibernateDialect() {
		return queryRequest().getConnectionDetails().getDialect().getHibernateDialect();
	}
}

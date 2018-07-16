package net.sqlwebclient.core.connections;

import com.google.common.base.Ticker;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

final class DefaultTransakt implements Transakt {
	private static final Log logger = LogFactory.getLog(DefaultTransakt.class);

	private final DatabaseConnection databaseConnection;
	private final Session session;
	private final Transaction transaction;

	DefaultTransakt(final DatabaseConnection databaseConnection) {
		logger.debug("creating new transaction for db: " + databaseConnection.getConnectionDetails().getName());
		this.databaseConnection = databaseConnection;
		this.session = databaseConnection.getSessionFactory().openSession();
		this.transaction = session.beginTransaction();
	}

	@Override
	public void close() {
		logger.debug("closing transaction");
		try {
			this.transaction.commit();
			this.session.close();
		} catch (Exception ex) {
			logger.error("error while closing the Transakt, but continuing anyway", ex);
		}
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public Result execute(final QueryRequest request) {
		logger.info("executing query");
		return QueryRequestExecutor.newInstance(request, databaseConnection).execute(this)
				.connectionDetails(request.getConnectionDetails().getUuid())
				.query(request.getQueryString())
				.uuid(Ticker.systemTicker().read())
				.build();
	}
}

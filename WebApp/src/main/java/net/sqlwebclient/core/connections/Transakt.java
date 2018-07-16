package net.sqlwebclient.core.connections;

import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.core.objects.result.Result;
import org.hibernate.Session;

public interface Transakt {
	void close();

	Result execute(final QueryRequest request);

	Session getSession();
}

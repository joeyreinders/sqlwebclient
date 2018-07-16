package net.sqlwebclient.core.connections;

import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.QueryRequest;

public interface DatabaseConnectionFactoryCache {
    DatabaseConnection getConnection(final QueryRequest queryRequest);

    DatabaseConnection getConnection(final ConnectionDetails connectionDetails);

    DatabaseConnection getNonCachedConnection(final ConnectionDetails connectionDetails);

    void doLoadCacheOnServerStartup();

	int getNumberOfOpenConnections();
}

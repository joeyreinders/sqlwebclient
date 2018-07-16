package net.sqlwebclient.core.connections;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.util.Holder;

import java.util.Map;

final class DatabaseConnectionFactoryCacheImpl implements DatabaseConnectionFactoryCache {
    private final Map<Long, DatabaseConnection> connectionCache = Maps.newHashMap();
    private final Holder<Boolean> doneCacheOnStartup = Holder.of(false);
    private final ConnectionDetailsService connectionDetailsService;

    @Inject
    DatabaseConnectionFactoryCacheImpl(final ConnectionDetailsService connectionDetailsService) {
        this.connectionDetailsService = connectionDetailsService;
    }

    @Override
    public DatabaseConnection getConnection(final QueryRequest queryRequest) {
        return getConnection(queryRequest.getConnectionDetails());
    }

    @Override
    public DatabaseConnection getConnection(final ConnectionDetails connectionDetails) {
        return getFromOrCreateInCache(connectionDetails);
    }

    @Override
    public DatabaseConnection getNonCachedConnection(final ConnectionDetails connectionDetails) {
        return DatabaseConnectionImpl.of(connectionDetails);
    }

    private DatabaseConnection getFromOrCreateInCache(final ConnectionDetails connectionDetails) {
        final DatabaseConnection res = connectionCache.get(connectionDetails.getUuid());
        if(res != null) {
            return res;
        }

        final DatabaseConnection newConnection = getNonCachedConnection(connectionDetails);
        connectionCache.put(connectionDetails.getUuid(), newConnection);
        return newConnection;
    }

    @Override
    public void doLoadCacheOnServerStartup() {
        if(doneCacheOnStartup.get()) {
            return;
        }

        final ConnectionDetails[] allConnectionDetails = connectionDetailsService.getConnections();
        for(ConnectionDetails connectionDetails: allConnectionDetails) {
            getFromOrCreateInCache(connectionDetails);
        }

        doneCacheOnStartup.set(true);
    }

	@Override
	public int getNumberOfOpenConnections() {
		return connectionCache.size();
	}
}

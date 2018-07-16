package net.sqlwebclient.service.impl;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import net.sqlwebclient.core.connections.DatabaseConnectionFactoryCache;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.ConnectionDetailsTestResult;
import net.sqlwebclient.json.objects.JsonConnectionDetails;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.JsonConversionService;
import net.sqlwebclient.service.LocalStorageService;
import org.hibernate.JDBCException;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Objects;

final class ConnectionDetailsServiceImpl implements ConnectionDetailsService {
    public static final String PREFIX = "_CD_";

    private final JsonConversionService jsonConversionService;
    private final LocalStorageService localStorageService;
    private final DatabaseConnectionFactoryCache databaseConnectionFactoryCache;

    @Inject
    public ConnectionDetailsServiceImpl(final JsonConversionService jsonConversionService,
                                        final LocalStorageService localStorageService,
										final DatabaseConnectionFactoryCache databaseConnectionFactoryCache) {
        this.jsonConversionService = jsonConversionService;
        this.localStorageService = localStorageService;
        this.databaseConnectionFactoryCache = databaseConnectionFactoryCache;
    }

    @Override
    public ConnectionDetails getByUuid(final Long uuid) {
        if(uuid == null) {
            return null;
        }

        for(ConnectionDetails connectionDetails: getConnections()) {
            if(Objects.equals(connectionDetails.getUuid(), uuid)) {
                return connectionDetails;
            }
        }

        return null;
    }

    @Override
    public ConnectionDetails save(final ConnectionDetails connectionDetails) {
        assert connectionDetails != null;

        if(connectionDetails.getUuid() == null) {
           return create(connectionDetails);
        }

        return update(connectionDetails);
    }

    @Override
    public ConnectionDetails[] getConnections() {
        final String[] result = localStorageService.getAll(new Predicate<String>() {
            @Override
            public boolean apply(@Nullable final String input) {
                return input != null && input.startsWith(PREFIX);
            }
        });

        final ConnectionDetails[] res = new ConnectionDetails[result.length];
        for(int i = 0 ; i < res.length; i++) {
            final JsonConnectionDetails json = jsonConversionService.fromJson(result[i], JsonConnectionDetails.class);
            res[i] = ConnectionDetails.of(json);
        }

        return res;
    }

    @Override
    public void delete(final ConnectionDetails connectionDetails) {
        localStorageService.delete(PREFIX + connectionDetails.getUuid());
    }

    @Override
    public ConnectionDetails getDefault() {
        final ConnectionDetails[] all = getConnections();
        for(ConnectionDetails connectionDetails: all) {
            if(connectionDetails.isDefault()) {
                return connectionDetails;
            }
        }

        return all.length != 0 ? all[0] : null;
    }

    @Override
    public ConnectionDetailsTestResult testConnectionDetails(final ConnectionDetails connectionDetails) {
        try {
            databaseConnectionFactoryCache.getNonCachedConnection(connectionDetails).getSessionFactory().openSession();
            return ConnectionDetailsTestResult.newInstance(true, "Test ok");
        } catch (JDBCException exception) {
            final SQLException sqlException = exception.getSQLException();
            return ConnectionDetailsTestResult.newInstance(false, sqlException.getMessage());
        } catch (Exception ex) {
            return ConnectionDetailsTestResult.newInstance(false, ex.getMessage());
        }
    }

    private ConnectionDetails create(final ConnectionDetails connectionDetails) {
        final JsonConnectionDetails json = JsonConnectionDetails.of(connectionDetails);
        json.setUuid(System.currentTimeMillis()); //We doubt that this will never be unique
        return update(ConnectionDetails.of(json));
    }

    private ConnectionDetails update(final ConnectionDetails connectionDetails) {
        final JsonConnectionDetails json = JsonConnectionDetails.of(connectionDetails);
        localStorageService.save(PREFIX + json.getUuid(), jsonConversionService.toJson(json));
        return connectionDetails;
    }
}

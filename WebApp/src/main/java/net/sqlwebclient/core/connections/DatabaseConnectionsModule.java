package net.sqlwebclient.core.connections;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

public final class DatabaseConnectionsModule extends AbstractModule {
    private static final Log logger = LogFactory.getLog(DatabaseConnectionsModule.class);

    private DatabaseConnectionsModule() {
        logger.info("New Instance created");
    }

    @Override
    protected void configure() {
        bind(DatabaseConnectionFactoryCache.class).to(DatabaseConnectionFactoryCacheImpl.class).in(Singleton.class);
    }

    public static DatabaseConnectionsModule newInstance() {
        return new DatabaseConnectionsModule();
    }
}

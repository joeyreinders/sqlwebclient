package net.sqlwebclient.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.sqlwebclient.controller.configuration.ControllerModule;
import net.sqlwebclient.core.connections.DatabaseConnectionsModule;
import net.sqlwebclient.core.connections.queryexec.QueryExecutorModule;
import net.sqlwebclient.exception.config.ExceptionModule;
import net.sqlwebclient.service.config.ServiceModule;
import net.sqlwebclient.servlet.ServletsModule;

public final class GuiceConfig {
    private final Injector injector;

    private GuiceConfig() {
        injector = Guice.createInjector(
                DatabaseConnectionsModule.newInstance(),
                ServiceModule.newInstance(),
				ExceptionModule.newInstance(),
				ControllerModule.newInstance(),
				ServletsModule.newInstance(),
				QueryExecutorModule.newInstance()
        );
    }

    public Injector getInjector() {
        return injector;
    }

    public static GuiceConfig newInstance() {
        return new GuiceConfig();
    }
}

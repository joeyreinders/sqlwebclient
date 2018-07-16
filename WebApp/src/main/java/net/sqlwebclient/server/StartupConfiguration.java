package net.sqlwebclient.server;

import com.google.common.base.Stopwatch;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import net.sqlwebclient.guice.GuiceConfig;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogConfigurator;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import javax.servlet.ServletContextEvent;

public final class StartupConfiguration extends GuiceServletContextListener {
    private static final Log logger;

    static {
        //Start of all things, initialization of log configuration
        LogConfigurator.configure();

        //Logging
        logger = LogFactory.getLog(StartupConfiguration.class);
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        logger  .info("--------------------------------")
                .info("- Loading Server configuration -")
                .info("--------------------------------");

        final Stopwatch stopwatch = new Stopwatch().start();

        super.contextInitialized(sce);

        final Injector injector = (Injector) sce.getServletContext().getAttribute(Injector.class.getName());
        PostStartup.newInstance(injector).postLoad();

        //End stopwatch
        stopwatch.stop();
        logger  .info("--------------------------------")
                .info("- Server configuration loaded in " + stopwatch.toString())
                .info("--------------------------------");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
    }

    @Override
    protected Injector getInjector() {
        return GuiceConfig.newInstance().getInjector();
    }

    public static StartupConfiguration newInstance() {
        return new StartupConfiguration();
    }
}

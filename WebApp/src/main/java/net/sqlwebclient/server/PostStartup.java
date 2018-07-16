package net.sqlwebclient.server;

import com.google.common.base.Joiner;
import com.google.inject.Injector;
import net.sqlwebclient.core.connections.DatabaseConnectionFactoryCache;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.service.SettingsService;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.LogLevel;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import net.sqlwebclient.util.logging.wrappers.LogListener;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

final class PostStartup {
    private static final Log logger = LogFactory.getLog(PostStartup.class);

    private final Injector injector;

    private PostStartup(final Injector injector) {
        this.injector = injector;
    }

    public void postLoad() {
        logger.info("-- Post Startup Tasks --");

        loadSettings();
        loadConnections();
		//createSettingJavaLogListener();

		logger.info("-- Done Post Startup --");
    }

    private void loadSettings() {
        logger.info("   -> Load Settings");
        injector.getInstance(SettingsService.class).getAll();
    }

    private void loadConnections() {
        final boolean doCaching = Settings.CACHE_DB_CONNECTIONS_ON_STARTUP.valueEquals(true);
        logger.info("   -> Cache DB connections, needed? " + doCaching);
        if (!doCaching) {
            return;
        }

        injector.getInstance(DatabaseConnectionFactoryCache.class).doLoadCacheOnServerStartup();
    }

	private void createSettingJavaLogListener() {
		final LogListener sysoListener = new LogListener() {
			@Override
			public void onLog(final LogLevel logLevel,
							  final String msg,
							  final Throwable throwable) {
				if(! Settings.JAVA_CONSOLE_LOGGING.valueEquals(true)) {
					return;
				}
				final List<?> args = Arrays.asList(new Date(), logLevel.getName(), msg, throwable.getMessage());
				System.out.println(Joiner.on(" - ").join(args));
			}
		};

		LogFactory.addLogListener(sysoListener);
	}

    public static PostStartup newInstance(final Injector injector) {
        return new PostStartup(injector);
    }
}

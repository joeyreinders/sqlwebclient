package net.sqlwebclient.util.logging.wrappers;

import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.SupportedLogFramework;

public final class LogFactory {
    private static final SupportedLogFramework supportedLogFramework = LogConfigurator.SUPPORTED_LOG_FRAMEWORK;
	private static final LogListeners listeners = LogListeners.INSTANCE;

    public static Log getLog(final String className) {
        return supportedLogFramework.createLog(className);
    }

    public static Log getLog(final Class<?> cls) {
        return getLog(cls.getName());
    }

	public static void addLogListener(final LogListener logListener) {
		listeners.add(logListener);
	}

	public static void removeLogListener(final LogListener logListener) {
		listeners.remove(logListener);
	}
}

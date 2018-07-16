package net.sqlwebclient.util.logging.wrappers;

import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.LogLevel;

abstract class LogWrapper implements Log {
    private final String className;

    protected LogWrapper(final String className) {
        this.className = className;
    }

    protected String getClassName() {
        return className;
    }

    protected abstract void logImpl(final LogLevel logLevel, final String msg, final Throwable throwable);

    protected abstract boolean isLevelEnabled(final LogLevel logLevel);

    private void log(final LogLevel logLevel, final String msg, final Throwable throwable) {
		LogListeners.INSTANCE.onLog(logLevel, msg, throwable);

        if(! isLevelEnabled(logLevel)) {
            return;
        }

        logImpl(logLevel, msg, throwable);
    }

    @Override
    public Log debug(final String msg, final Throwable throwable) {
        log(LogLevel.DEBUG, msg, throwable);
        return this;
    }

    @Override
    public Log debug(final String msg) {
        log(LogLevel.DEBUG, msg, null);
        return this;
    }

    @Override
    public Log error(final String msg, final Throwable throwable) {
        log(LogLevel.ERROR, msg, throwable);
        return this;
    }

    @Override
    public Log error(final String msg) {
        log(LogLevel.ERROR, msg, null);
        return this;
    }

    @Override
    public Log info(final String msg, final Throwable throwable) {
        log(LogLevel.INFO, msg, throwable);
        return this;
    }

    @Override
    public Log info(final String msg) {
        log(LogLevel.INFO, msg, null);
        return this;
    }

    @Override
    public Log fatal(final String msg, final Throwable throwable) {
        log(LogLevel.FATAL, msg, throwable);
        return this;
    }

    @Override
    public Log fatal(final String msg) {
        log(LogLevel.FATAL, msg, null);
        return this;
    }

    @Override
    public Log warn(final String msg, final Throwable throwable) {
        log(LogLevel.WARN, msg, throwable);
        return this;
    }

    @Override
    public Log warn(final String msg) {
        log(LogLevel.WARN, msg, null);
        return this;
    }

    protected static abstract class LoggerHolder<T> {
        private T instance;

        public final T get() {
            if(instance == null) {
                load();
            }

            return instance;
        }

        protected abstract void load();

        protected void set(final T instance) {
            this.instance = instance;
        }
    }
}

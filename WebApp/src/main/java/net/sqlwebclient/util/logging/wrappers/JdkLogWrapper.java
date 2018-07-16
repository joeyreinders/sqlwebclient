package net.sqlwebclient.util.logging.wrappers;

import com.google.common.collect.ImmutableMap;
import net.sqlwebclient.util.logging.LogLevel;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

final class JdkLogWrapper extends LogWrapper {
    private final LoggerHolder<Logger> holder = new LoggerHolder<Logger>() {
        @Override
        protected void load() {
            set(Logger.getLogger(getClassName()));
        }
    };


    JdkLogWrapper(final String className) {
        super(className);
    }

    @Override
    protected void logImpl(final LogLevel logLevel, final String msg, final Throwable throwable) {
        final Level level = logLevel2jdkLevel.get(logLevel);
        if(throwable != null) {
            holder.get().log(level, msg, throwable);
        } else {
            holder.get().log(level, msg);
        }
    }

    @Override
    protected boolean isLevelEnabled(final LogLevel logLevel) {
        final Level level = logLevel2jdkLevel.get(logLevel);
        return holder.get().isLoggable(level);
    }

    private static final Map<LogLevel, Level> logLevel2jdkLevel;
    static {
        logLevel2jdkLevel = ImmutableMap.<LogLevel, Level>builder()
                .put(LogLevel.FATAL, Level.SEVERE)
                .put(LogLevel.ERROR, Level.SEVERE)
                .put(LogLevel.INFO, Level.INFO)
                .put(LogLevel.DEBUG, Level.FINE)
                .put(LogLevel.WARN, Level.WARNING)
                .build();
    }
}

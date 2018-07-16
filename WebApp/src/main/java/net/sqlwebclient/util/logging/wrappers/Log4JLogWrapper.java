package net.sqlwebclient.util.logging.wrappers;

import com.google.common.collect.ImmutableMap;
import net.sqlwebclient.util.logging.LogLevel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Map;

final class Log4JLogWrapper extends LogWrapper {
    private final LoggerHolder<Logger> holder = new LoggerHolder<Logger>() {
        @Override
        protected void load() {
            set(Logger.getLogger(getClassName()));
        }
    };

    Log4JLogWrapper(final String className) {
        super(className);
    }

    @Override
    protected void logImpl(final LogLevel logLevel, final String msg, final Throwable throwable) {
        final Level log4jLevel = getLog4JLevel(logLevel);
        if(throwable == null) {
            holder.get().log(log4jLevel, msg);
        } else {
            holder.get().log(log4jLevel, throwable);
        }
    }

    @Override
    protected boolean isLevelEnabled(final LogLevel logLevel) {
        final Level log4jLevel = getLog4JLevel(logLevel);
        return holder.get().isEnabledFor(log4jLevel);
    }

    private Level getLog4JLevel(final LogLevel logLevel) {
        return logLevel2log4jLevel.get(logLevel);
    }

    private static final Map<LogLevel, Level> logLevel2log4jLevel;
    static {
        logLevel2log4jLevel = ImmutableMap.<LogLevel, Level>builder()
                .put(LogLevel.FATAL, Level.FATAL)
                .put(LogLevel.ERROR, Level.ERROR)
                .put(LogLevel.INFO, Level.INFO)
                .put(LogLevel.DEBUG, Level.DEBUG)
                .put(LogLevel.WARN, Level.WARN)
                .build();
    }
}

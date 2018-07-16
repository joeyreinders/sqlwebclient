package net.sqlwebclient.util.logging;

public enum LogLevel {
    FATAL("FATAL", 0),
    ERROR("ERROR", 1),
    WARN("WARN", 2),
    INFO("INFO", 3),
    DEBUG("DEBUG", 4)
    ;

    final String name;
    private final int code;

    LogLevel(final String name, final int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}

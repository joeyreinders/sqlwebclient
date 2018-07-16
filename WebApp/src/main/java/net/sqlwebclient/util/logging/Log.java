package net.sqlwebclient.util.logging;

public interface Log {
    Log debug(String msg, Throwable throwable);
    Log debug(String msg);

    Log error(String msg, Throwable throwable);
    Log error(String msg);

    Log info(String msg, Throwable throwable);
    Log info(String msg);

    Log fatal(String msg, Throwable throwable);
    Log fatal(String msg);

    Log warn(String msg, Throwable throwable);
    Log warn(String msg);
}

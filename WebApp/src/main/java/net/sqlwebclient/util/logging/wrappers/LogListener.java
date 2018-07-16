package net.sqlwebclient.util.logging.wrappers;

import net.sqlwebclient.util.logging.LogLevel;

import java.util.EventListener;

public interface LogListener extends EventListener {
	void onLog(final LogLevel logLevel,
			   final String msg,
			   final Throwable throwable);
}

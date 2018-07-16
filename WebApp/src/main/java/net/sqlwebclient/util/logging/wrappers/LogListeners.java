package net.sqlwebclient.util.logging.wrappers;

import com.google.common.collect.Lists;
import net.sqlwebclient.util.logging.LogLevel;

import java.util.List;

enum LogListeners implements LogListener {
	INSTANCE;

	private final List<LogListener> listeners = Lists.newArrayList();

	void add(final LogListener logListener) {
		this.listeners.add(logListener);
	}

	void remove(final LogListener logListener) {
		this.listeners.remove(logListener);
	}

	@Override
	public void onLog(final LogLevel logLevel,
					  final String msg,
					  final Throwable throwable) {
		for(LogListener logListener: listeners) {
			try {
				logListener.onLog(logLevel, msg, throwable);
			} catch(Error error) {
				//do nothing with an error
			}
		}
	}
}

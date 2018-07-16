package net.sqlwebclient.guice;

import com.google.inject.AbstractModule;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

public abstract class GuiceModule extends AbstractModule {
	private static final Log logger = LogFactory.getLog(GuiceModule.class);

	@Override
	protected void configure() {
		final String logDescription = getLogDescription();

		logger.debug("Configuring: " + logDescription);
		doConfigure();
		logger.debug("Done configuring: " + logDescription);
	}

	protected abstract void doConfigure();

	protected abstract String getLogDescription();
}

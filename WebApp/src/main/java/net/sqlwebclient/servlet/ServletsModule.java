package net.sqlwebclient.servlet;

import com.google.inject.servlet.ServletModule;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

public final class ServletsModule extends ServletModule {
	private static final Log logger = LogFactory.getLog(ServletsModule.class);

	private ServletsModule() {
		//noop
	}

	@Override
	protected void configureServlets() {
		logger.debug("configuring servlets");

		serve("/*").with(DispatcherServlet.class);
		filter("/script/*", "/static/*", "/pages/*").through(CachingFilter.class);

	}

	public static ServletModule newInstance() {
		return new ServletsModule();
	}
}

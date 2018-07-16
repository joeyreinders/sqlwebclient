package net.sqlwebclient.servlet;

import com.google.inject.Singleton;
import net.sqlwebclient.util.ServletUtil;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
final class CachingFilter implements Filter{
	private static final Log logger = LogFactory.getLog(CachingFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		logger.debug("-- RequestFilter initialized");
	}

	@Override
	public void doFilter(final ServletRequest request,
						 final ServletResponse response,
						 final FilterChain chain) throws IOException, ServletException {
		ServletUtil.addCachingExpiration((HttpServletResponse)response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.debug("-- RequestFilter destroyed");
	}
}

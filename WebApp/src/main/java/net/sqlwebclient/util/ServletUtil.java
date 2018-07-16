package net.sqlwebclient.util;

import com.google.inject.Injector;
import net.sqlwebclient.controller.request.RequestContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public final class ServletUtil {
	private ServletUtil() {
		throw new AssertionError();
	}

	public static void addCachingExpiration(final HttpServletResponse response) {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 1);
		response.setDateHeader("Expires", calendar.getTimeInMillis());
	}

	public static void addCachingExpiration(final RequestContext context) {
		addCachingExpiration(context.getResponse());
	}

	public static Injector getInjector(final RequestContext ctx) {
		return getInjector(ctx.getRequest().getServletContext());
	}

	public static Injector getInjector(final HttpServlet servlet) {
		return getInjector(servlet.getServletContext());
	}

	public static Injector getInjector(final ServletContext servletContext) {
		return (Injector) servletContext.getAttribute(Injector.class.getName());
	}
}

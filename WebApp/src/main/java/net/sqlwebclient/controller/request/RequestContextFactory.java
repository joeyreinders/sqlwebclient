package net.sqlwebclient.controller.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class RequestContextFactory {
	public static RequestContext create(final HttpServletRequest request,
										final HttpServletResponse response,
										final String id) {
		return new DefaultRequestContext(request, response, id);
	}
}

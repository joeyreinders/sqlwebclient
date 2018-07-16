package net.sqlwebclient.controller.request;

import com.google.common.base.Throwables;
import net.sqlwebclient.util.Toolkit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

final class DefaultRequestContext implements RequestContext {
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final String body = null;
	private final String id;

	DefaultRequestContext(	final HttpServletRequest request,
						   	final HttpServletResponse response,
							final String id) {
		this.request = request;
		this.response = response;
		this.id = id;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getServletPath() {
		return request.getServletPath();
	}

	public String getParam(final String key) {
		return request.getParameter(key);
	}

	public String getBody() {
		if(body == null) {
			readBody();
		}

		return body;
	}

	public String getMethod() {
		return request.getMethod();
	}

	public String getPathInfo() {
		return request.getPathInfo();
	}

	@Override
	public ServletContext getServletContext() {
		return request.getServletContext();
	}

	@Override
	public String getId() {
		return id;
	}

	private void readBody() {
		try {
			final String res = Toolkit.toString(request.getInputStream());
			final Field fld = DefaultRequestContext.class.getDeclaredField("body");
			fld.setAccessible(true);
			fld.set(this, res);
		} catch (final Exception ex) {
			Throwables.propagate(ex);
		}
	}
}

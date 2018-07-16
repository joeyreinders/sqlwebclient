package net.sqlwebclient.controller.request;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestContext {
    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    String getServletPath();

    String getParam(final String key);

    String getBody();

    String getMethod();

    String getPathInfo();

	ServletContext getServletContext();

	String getId();
}

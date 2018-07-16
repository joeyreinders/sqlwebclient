package net.sqlwebclient.controller.manager;

public interface ControllerMatcher {
	boolean matches(final String requestPath);

	String getId(final String requestPath);
}

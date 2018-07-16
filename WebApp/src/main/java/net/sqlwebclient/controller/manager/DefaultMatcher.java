package net.sqlwebclient.controller.manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DefaultMatcher implements ControllerMatcher {
	private final Pattern regExIdPattern;
	private final Pattern regExAllPattern;

	DefaultMatcher(final String path) {
		regExIdPattern = Pattern.compile(path + "/([a-z0-9.]*)");
		regExAllPattern = Pattern.compile(path);
	}

	@Override
	public boolean matches(final String requestPath) {
		final Matcher idMatcher = regExIdPattern.matcher(requestPath);
		boolean res = false;
		if(idMatcher.find()) {
			res = true;
		}

		if(! res) {
			final Matcher allMatcher = regExAllPattern.matcher(requestPath);
			res = allMatcher.find();
		}

		return res;
	}

	@Override
	public String getId(final String requestPath) {
		final Matcher matcher = regExIdPattern.matcher(requestPath);
		if(matcher.matches()) {
			return matcher.group(1);
		}

		return null;
	}
}

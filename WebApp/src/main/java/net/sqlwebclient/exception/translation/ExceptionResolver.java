package net.sqlwebclient.exception.translation;

import net.sqlwebclient.exception.CustomException;

public interface ExceptionResolver {
	String resolve(final CustomException customException, final String language);
}

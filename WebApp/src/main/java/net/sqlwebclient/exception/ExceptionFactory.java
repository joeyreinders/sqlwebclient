package net.sqlwebclient.exception;

public final class ExceptionFactory {
	private static CustomException create(	final ExceptionCode exceptionCode,
								   			final Object[] arguments,
								   			final Throwable throwable,
								   			final boolean translatable) {
		return new CustomException(exceptionCode, arguments, throwable, translatable);
	}

	public static CustomException create(final Exception exception) {
		return create(null, null, exception, false);
	}

	public static CustomException create(final ExceptionCode exceptionCode, Object... arguments) {
		return create(exceptionCode, arguments, null, true);
	}
}

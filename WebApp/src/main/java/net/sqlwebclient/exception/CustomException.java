package net.sqlwebclient.exception;

public final class CustomException extends RuntimeException {
	private static final long serialVersionUID = -2297238570675553578L;

	private final ExceptionCode exceptionCode;
	private final Object[] arguments;
	private final Throwable throwable;
	private final boolean translatable;

	CustomException(final ExceptionCode exceptionCode,
					final Object[] arguments,
					final Throwable throwable,
					final boolean translatable) {
		this.exceptionCode = exceptionCode;
		this.arguments = arguments;
		this.throwable = throwable;
		this.translatable = translatable;
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public boolean isTranslatable() {
		return translatable;
	}
}

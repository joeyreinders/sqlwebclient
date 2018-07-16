package net.sqlwebclient.exception;

public class OldSqlWebClientException extends RuntimeException {
    private static final long serialVersionUID = 4784010526609183739L;

    public OldSqlWebClientException() {
    }

    public OldSqlWebClientException(final String message) {
        super(message);
    }

    public OldSqlWebClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OldSqlWebClientException(final Throwable cause) {
        super(cause);
    }

    public OldSqlWebClientException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

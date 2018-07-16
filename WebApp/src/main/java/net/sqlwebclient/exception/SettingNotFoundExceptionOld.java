package net.sqlwebclient.exception;

public final class SettingNotFoundExceptionOld extends OldSqlWebClientException {
    private static final long serialVersionUID = 6765012188693916376L;

    public SettingNotFoundExceptionOld(final String message) {
        super("No setting found with name: " + message);
    }
}

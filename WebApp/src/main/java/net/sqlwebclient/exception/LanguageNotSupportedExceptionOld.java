package net.sqlwebclient.exception;

public final class LanguageNotSupportedExceptionOld extends OldSqlWebClientException {
    private static final long serialVersionUID = -8562628669171769248L;

    private LanguageNotSupportedExceptionOld(final String lang) {
        super("language: '" + lang + "' not supported");
    }

    public static LanguageNotSupportedExceptionOld newInstance(final String language) {
        return new LanguageNotSupportedExceptionOld(language);
    }
}

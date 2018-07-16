package net.sqlwebclient.json.objects;

public final class JsonErrorMessage extends Json {
    private static final long serialVersionUID = -8874985789903549298L;

    private final String message;

    private JsonErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static JsonErrorMessage valueOf(final String message) {
        return new JsonErrorMessage(message);
    }
}

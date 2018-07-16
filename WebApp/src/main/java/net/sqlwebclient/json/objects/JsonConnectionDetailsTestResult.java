package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.objects.ConnectionDetailsTestResult;

public final class JsonConnectionDetailsTestResult extends Json {
    private static final long serialVersionUID = 2340956203073097612L;

    private final boolean connected;
    private final String message;

    private JsonConnectionDetailsTestResult(final boolean result,
                                            final String errorMessage) {
        this.connected = result;
        this.message = errorMessage;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getMessage() {
        return message;
    }

    public static JsonConnectionDetailsTestResult of(final ConnectionDetailsTestResult obj) {
        return new JsonConnectionDetailsTestResult(
            obj.isConnected(),
            obj.getMessage()
        );
    }
}

package net.sqlwebclient.core.objects;

public final class ConnectionDetailsTestResult {
    private final boolean connected;
    private final String message;

    private ConnectionDetailsTestResult(final boolean result,
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

    public static ConnectionDetailsTestResult newInstance(final boolean result,
                                                          final String errorMessage) {
        return new ConnectionDetailsTestResult(result, errorMessage);
    }
}

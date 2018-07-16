package net.sqlwebclient.core.objects.result;

public final class Message {
    private final String error;
    private final String info;
    private final String duration;

    public Message() {
        this(null, null, null);
    }

    Message(final String error,
                   final String info,
                   final String duration) {
        this.error = error;
        this.info = info;
        this.duration = duration;
    }

    public String getError() {
        return error;
    }

    public String getInfo() {
        return info;
    }

    public String getDuration() {
        return duration;
    }
}

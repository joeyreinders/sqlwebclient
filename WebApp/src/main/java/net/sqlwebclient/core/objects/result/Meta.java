package net.sqlwebclient.core.objects.result;

public final class Meta {
    private final String query;

    private final long uuid;

    private final long connectionDetails;

	private final String type;

    public Meta() {
        this(null, 0, 0, "");
    }

    Meta(final String query,
         final long uuid,
         final long connectionDetails,
		 final String type) {
        this.query = query;
        this.uuid = uuid;
        this.connectionDetails = connectionDetails;
		this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public long getUuid() {
        return uuid;
    }

    public long getConnectionDetails() {
        return connectionDetails;
    }

	public String getType() {
		return type;
	}
}

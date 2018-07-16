package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.dialect.Dialect;

public final class JsonDialect extends Json {
    private static final long serialVersionUID = 3505954655647232777L;

    private final Long uuid;

    private final String name;

    private JsonDialect(final Dialect d) {
        this.uuid = d.getUuid();
        this.name = d.getName();
    }

    public Long getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public static JsonDialect valueOf(final Dialect dialect) {
        return new JsonDialect(dialect);
    }
}

package net.sqlwebclient.json.objects;

import com.google.common.collect.ImmutableList;
import net.sqlwebclient.core.objects.DatabaseInfo;
import net.sqlwebclient.core.objects.DatabaseTable;

import java.util.List;

public final class JsonDatabaseInfo extends Json {
    private static final long serialVersionUID = -2481524887907134565L;

    private String schemaName;
    private String databaseProductName;
    private String databaseProductVersion;
    private List<JsonDatabaseTable> tables;

    private JsonDatabaseInfo(final String schemaName,
                            final String databaseProductName,
                            final String databaseProductVersion,
                            final List<JsonDatabaseTable> tables) {
        this.schemaName = schemaName;
        this.databaseProductName = databaseProductName;
        this.databaseProductVersion = databaseProductVersion;
        this.tables = tables;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getDatabaseProductName() {
        return databaseProductName;
    }

    public String getDatabaseProductVersion() {
        return databaseProductVersion;
    }

    public List<JsonDatabaseTable> getTables() {
        return tables;
    }

    public static JsonDatabaseInfo of(final DatabaseInfo info) {
        final ImmutableList.Builder<JsonDatabaseTable> tables = ImmutableList.builder();
        for(DatabaseTable table: info.getTables()) {
            tables.add(JsonDatabaseTable.of(table));
        }

        return new JsonDatabaseInfo(info.getSchemaName(),
                info.getDatabaseProductName(),
                info.getDatabaseProductVersion(),
                tables.build());
    }
}

package net.sqlwebclient.core.objects;

import com.google.common.collect.ImmutableList;
import net.sqlwebclient.util.Builder;

import java.util.List;

public final class DatabaseInfo {
    private final String schemaName;
    private final String databaseProductName;
    private final String databaseProductVersion;
    private final List<DatabaseTable> tables;

    private DatabaseInfo(final DatabaseInfoBuilder builder) {
        this.schemaName = builder.schemaName;
        this.databaseProductName = builder.databaseProductName;
        this.databaseProductVersion = builder.databaseProductVersion;
        this.tables = builder.tables.build();
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

    public List<DatabaseTable> getTables() {
        return tables;
    }

    public static DatabaseInfoBuilder builder() {
        return new DatabaseInfoBuilder();
    }

    public static final class DatabaseInfoBuilder implements Builder<DatabaseInfo> {
        private String schemaName;
        private String databaseProductName;
        private String databaseProductVersion;
        private ImmutableList.Builder<DatabaseTable> tables = ImmutableList.builder();

        private DatabaseInfoBuilder() { /*noop*/ }

        public DatabaseInfoBuilder schemaName(final String name) {
            this.schemaName = name;
            return this;
        }

        public DatabaseInfoBuilder productName(final String name) {
            this.databaseProductName = name;
            return this;
        }

        public DatabaseInfoBuilder productVersion(final String version) {
            this.databaseProductVersion = version;
            return this;
        }

        public DatabaseInfoBuilder add(final DatabaseTable table) {
            tables.add(table);
            return this;
        }

        @Override
        public DatabaseInfo build() {
            return new DatabaseInfo(this);
        }
    }
}

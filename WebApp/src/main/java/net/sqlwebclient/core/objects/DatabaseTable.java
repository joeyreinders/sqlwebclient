package net.sqlwebclient.core.objects;

import com.google.common.collect.ImmutableList;
import net.sqlwebclient.util.Builder;

import java.util.List;

public final class DatabaseTable {
    private final String tableName;
    private final long records;
    private final String tableType;
    private final List<DatabaseTableColumn> columns;
    private final String remarks;

    private DatabaseTable(final DatabaseTableBuilder builder) {
        this.tableName = builder.tableName;
        this.records = builder.records;
        this.columns = builder.columns;
        this.tableType = builder.tableType;
        this.remarks = builder.remarks;
    }

    public String getTableName() {
        return tableName;
    }

    public long getRecords() {
        return records;
    }

    public List<DatabaseTableColumn> getColumnList() {
        return columns;
    }

    public String getTableType() {
        return tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public static DatabaseTableBuilder builder() {
        return new DatabaseTableBuilder();
    }

    public static final class DatabaseTableBuilder implements Builder<DatabaseTable> {
        private String tableName;
        private long records;
        private List<DatabaseTableColumn> columns;
        private String tableType;
        private String remarks;

        private ImmutableList.Builder<DatabaseTableColumn> columnBuilder = ImmutableList.builder();

        private DatabaseTableBuilder() {
            //noop
        }

        public DatabaseTableBuilder name(final String name) {
            this.tableName = name;
            return this;
        }

        public DatabaseTableBuilder records(final long records) {
            this.records = records;
            return this;
        }

        public DatabaseTableBuilder add(final DatabaseTableColumn column) {
            columnBuilder.add(column);
            return this;
        }

        public DatabaseTableBuilder tableType(final String tableType) {
            this.tableType = tableType;
            return this;
        }

        public DatabaseTableBuilder remarks(final String remarks) {
            this.remarks = remarks;
            return this;
        }

        @Override
        public DatabaseTable build() {
            columns = columnBuilder.build();
            return new DatabaseTable(this);
        }
    }
}

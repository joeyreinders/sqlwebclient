package net.sqlwebclient.core.objects;

import net.sqlwebclient.util.Builder;

public final class DatabaseTableColumn {
    private final String columnName;
    private final String dataType;
    private final boolean nullable;
    private final String typeName;
    private final boolean autoIncrement;
    private final int size;
    private final Integer decimalDigits;
    private final String remarks;
    private final String primaryKeys;

    private DatabaseTableColumn(final DatabaseTableColumnBuilder builder) {
        this.columnName = builder.columnName;
        this.dataType = builder.dataType;
        this.nullable = builder.nullable;
        this.typeName = builder.typeName;
        this.autoIncrement = builder.autoIncrement;
        this.size = builder.size;
        this.decimalDigits = builder.decimalDigits;
        this.remarks = builder.remarks;
        this.primaryKeys = builder.primaryKeys;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public int getSize() {
        return size;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public String getPrimaryKeys() {
        return primaryKeys;
    }

    public static DatabaseTableColumnBuilder builder() {
        return new DatabaseTableColumnBuilder();
    }

    public static final class DatabaseTableColumnBuilder implements Builder<DatabaseTableColumn> {
        private String columnName;
        private String dataType;
        private boolean nullable;
        private String typeName;
        private boolean autoIncrement;
        private int size;
        private Integer decimalDigits;
        private String remarks;
        private String primaryKeys;

        private DatabaseTableColumnBuilder() {
            //noop
        }

        public DatabaseTableColumnBuilder columnName(final String columnName) {
            this.columnName = columnName;
            return this;
        }

        public DatabaseTableColumnBuilder dataType(final String dataType) {
            this.dataType = dataType;
            return this;
        }

        public DatabaseTableColumnBuilder nullable(final boolean nullable) {
            this.nullable = nullable;
            return this;
        }

        public DatabaseTableColumnBuilder typeName(final String typeName) {
            this.typeName = typeName;
            return this;
        }

        public DatabaseTableColumnBuilder autoIncrement(final boolean autoIncrement) {
            this.autoIncrement = autoIncrement;
            return this;
        }

        public DatabaseTableColumnBuilder size(final int size) {
            this.size = size;
            return this;
        }

        public DatabaseTableColumnBuilder decimalDigits(final Integer decimalDegits) {
            this.decimalDigits = decimalDegits;
            return this;
        }

        public DatabaseTableColumnBuilder remarks(final String remarks) {
            this.remarks = remarks;
            return this;
        }

        public DatabaseTableColumnBuilder primaryKeys(final String primaryKeys) {
            this.primaryKeys = primaryKeys;
            return this;
        }

        @Override
        public DatabaseTableColumn build() {
            return new DatabaseTableColumn(this);
        }
    }
}

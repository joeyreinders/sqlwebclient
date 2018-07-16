package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.objects.DatabaseTableColumn;

public final class JsonDatabaseTableColumn extends Json {
    private static final long serialVersionUID = 7143076522286014826L;

    private final String columnName;
    private final String dataType;
    private final boolean nullable;
    private final String typeName;
    private final boolean autoIncrement;
    private final int size;
    private final Integer decimalDigits;
    private final String remarks;
    private final String primaryKeys;

    private JsonDatabaseTableColumn(final DatabaseTableColumn tableColumn) {
        this.columnName = tableColumn.getColumnName();
        this.dataType = tableColumn.getDataType();
        this.nullable = tableColumn.isNullable();
        this.typeName = tableColumn.getTypeName();
        this.autoIncrement = tableColumn.isAutoIncrement();
        this.size = tableColumn.getSize();
        this.decimalDigits = tableColumn.getDecimalDigits();
        this.remarks = tableColumn.getRemarks();
        this.primaryKeys = tableColumn.getPrimaryKeys();
    }

    public static JsonDatabaseTableColumn of(final DatabaseTableColumn col) {
        return new JsonDatabaseTableColumn(col);
    }
}

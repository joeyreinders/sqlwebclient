package net.sqlwebclient.json.objects;

import com.google.common.collect.ImmutableList;
import net.sqlwebclient.core.objects.DatabaseTable;
import net.sqlwebclient.core.objects.DatabaseTableColumn;

import java.util.List;

public final class JsonDatabaseTable extends Json {
    private static final long serialVersionUID = 3700830562500215960L;
    private final String tableName;
    private final long records;
    private final String tableType;
    private final List<JsonDatabaseTableColumn> columns;
    private final String remarks;

    private JsonDatabaseTable(final String tableName,
                             final long records,
                             final String tableType,
                             final List<JsonDatabaseTableColumn> columns,
                             final String remarks) {
        this.tableName = tableName;
        this.records = records;
        this.tableType = tableType;
        this.columns = columns;
        this.remarks = remarks;
    }

    public String getTableName() {
        return tableName;
    }

    public long getRecords() {
        return records;
    }

    public String getTableType() {
        return tableType;
    }

    public List<JsonDatabaseTableColumn> getColumns() {
        return columns;
    }

    public static JsonDatabaseTable of(final DatabaseTable table) {
        final ImmutableList.Builder<JsonDatabaseTableColumn> colums = ImmutableList.builder();
        for(DatabaseTableColumn column: table.getColumnList()) {
            colums.add(JsonDatabaseTableColumn.of(column));
        }

        return new JsonDatabaseTable(table.getTableName(),
                table.getRecords(),
                table.getTableType(),
                colums.build(),
                table.getRemarks());
    }
}


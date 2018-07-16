package net.sqlwebclient.core.objects.result;

public final class Column {
    private final String columnName;
    private final int jdbcType;
    private final String tableName;

    public Column() {
        this(null, 0, null);
    }

    private Column(final String columnName,
                  final int jdbcType,
                  final String tableName) {
        this.columnName = columnName;
        this.jdbcType = jdbcType;
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public String getTableName() {
        return tableName;
    }

	public static Column newInstance(final String columnName,
							   		final int jdbcType,
							   		final String tableName) {
		return new Column(columnName, jdbcType, tableName);
	}
}

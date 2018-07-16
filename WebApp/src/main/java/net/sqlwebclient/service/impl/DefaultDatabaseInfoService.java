package net.sqlwebclient.service.impl;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import net.sqlwebclient.core.connections.DatabaseConnection;
import net.sqlwebclient.core.connections.DatabaseConnectionFactoryCache;
import net.sqlwebclient.core.connections.Transakt;
import net.sqlwebclient.core.objects.*;
import net.sqlwebclient.service.DatabaseInfoService;
import org.hibernate.Session;
import org.hibernate.jdbc.AbstractWork;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

final class DefaultDatabaseInfoService implements DatabaseInfoService {
    private static final String[] fetchedTableTypes = new String[]{"TABLE", "VIEW", "ALIAS", "SYNONYM"};
    private final DatabaseConnectionFactoryCache connectionFactoryCache;

    @Inject
    DefaultDatabaseInfoService(final DatabaseConnectionFactoryCache connectionFactoryCache) {
        this.connectionFactoryCache = connectionFactoryCache;
    }

    @Override
    public DatabaseInfo getDatabaseInfo(final ConnectionDetails connectionDetails) {
        final DatabaseConnection dbConnection = connectionFactoryCache.getConnection(connectionDetails);
        final DatabaseInfo.DatabaseInfoBuilder info = DatabaseInfo.builder();
        final Session[] session = new Session[1];

        try {
            session[0] = dbConnection.getSessionFactory().openSession();
            session[0].doWork(new AbstractWork() {
                @Override
                public void execute(final Connection connection) throws SQLException {
                    final DatabaseMetaData metaData = connection.getMetaData();

                    info.schemaName(connectionDetails.getDatabase())
                            .productName(metaData.getDatabaseProductName())
                            .productVersion(metaData.getDatabaseProductVersion());

                    for(DatabaseTable table: getTables(metaData, connectionDetails, dbConnection)) {
                        info.add(table);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            //noop;
        } finally {
            if (session[0] != null) {
                session[0].close();
            }
        }

        return info.build();
    }

    private List<DatabaseTable> getTables(final DatabaseMetaData metaData,
                                          final ConnectionDetails connectionDetails,
										  final DatabaseConnection databaseConnection)
		throws SQLException {
        final ImmutableList.Builder<DatabaseTable> res = ImmutableList.builder();


        final ResultSet rs = metaData.getTables(null, connectionDetails.getDatabase(), "%", fetchedTableTypes);
		final Transakt transaction = databaseConnection.createTransaction();
        while(rs.next()) {
            res.add(getTable(rs, metaData, connectionDetails, transaction));
        }

		try {
			transaction.close();
		} catch (Exception ex) {
			throw Throwables.propagate(ex);
		}

        return res.build();
    }

    private DatabaseTable getTable( final ResultSet resultSet,
                                    final DatabaseMetaData metaData,
                                    final ConnectionDetails connectionDetails,
									final Transakt transaction)
            throws SQLException {
        final DatabaseTable.DatabaseTableBuilder res = DatabaseTable.builder();

        final String tableName = resultSet.getString("TABLE_NAME");
        final String remarks = resultSet.getString("REMARKS");
		final QueryRequest request =
				QueryRequest.simpleRequest(connectionDetails, "SELECT COUNT(*) AS COUNT FROM " + tableName);
		final String countString = transaction.execute(request).getRows().get(0).get("COUNT");
		final Long count = Strings.isNullOrEmpty(countString) ? 0 : Long.valueOf(countString);

        res.name(tableName)
                .tableType(resultSet.getString("TABLE_TYPE"))
                .records(count)
                .remarks(remarks)
        ;

        //Add columns
        final List<DatabaseTableColumn> columns = getColumns(metaData, connectionDetails, tableName);
        for(DatabaseTableColumn col: columns) {
            res.add(col);
        }

        return res.build();
    }

    private List<DatabaseTableColumn> getColumns(final DatabaseMetaData metaData,
                                                 final ConnectionDetails connectionDetails,
                                                 final String tableName)
            throws SQLException {
        final ImmutableList.Builder<DatabaseTableColumn> res = ImmutableList.builder();
        final Map<String, String> pks = getPrimaryKeys(metaData, connectionDetails, tableName);

        final ResultSet rs = metaData.getColumns(null, connectionDetails.getDatabase(), tableName, "%");
        while(rs.next()) {
            final String columnName = rs.getString("COLUMN_NAME");

            final DatabaseTableColumn column = DatabaseTableColumn.builder()
                    .columnName(columnName)
                    .dataType(String.valueOf(rs.getInt("DATA_TYPE")))
                    .typeName(rs.getString("TYPE_NAME"))
                    .autoIncrement("YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT")))
                    .nullable("YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")))
                    .size(rs.getInt("COLUMN_SIZE"))
                    .decimalDigits(rs.getInt("DECIMAL_DIGITS"))
                    .remarks(rs.getString("REMARKS"))
                    .primaryKeys(pks.get(columnName))
                    .build();
            res.add(column);
        }

        return res.build();
    }

    private Map<String, String> getPrimaryKeys(final DatabaseMetaData metaData,
                                               final ConnectionDetails connectionDetails,
                                               final String tableName) throws SQLException {
        final Map<String, String> res = Maps.newHashMap();

        final ResultSet rs = metaData.getPrimaryKeys(null, connectionDetails.getDatabase(), tableName);

        while (rs.next()) {
            final String columnName = rs.getString("COLUMN_NAME");
            final String pkName = rs.getString("PK_NAME");

            if(Strings.isNullOrEmpty(pkName)) {
                continue;
            }

            final String currentValue = res.get(columnName);
            if(Strings.isNullOrEmpty(currentValue)) {
                res.put(columnName, pkName);
            } else {
                res.put(columnName, currentValue + ", " + pkName);
            }
        }

        return res;
    }
 }

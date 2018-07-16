package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.ConnectionDetails;

public interface DDLService {
    String generateDDL(final ConnectionDetails connectionDetails,
                       final String tableName);
}

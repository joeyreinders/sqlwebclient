package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.DatabaseInfo;

public interface DatabaseInfoService {
    DatabaseInfo getDatabaseInfo(final ConnectionDetails connectionDetails);
}

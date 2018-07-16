package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.ConnectionDetailsTestResult;

public interface ConnectionDetailsService {
    ConnectionDetails getByUuid(final Long uuid);

    ConnectionDetails save(final ConnectionDetails connectionDetails);

    ConnectionDetails[] getConnections();

    void delete(final ConnectionDetails connectionDetails);

    ConnectionDetails getDefault();

    ConnectionDetailsTestResult testConnectionDetails(final ConnectionDetails connectionDetails);
}

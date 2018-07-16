package net.sqlwebclient.core.connections;

import net.sqlwebclient.core.objects.ConnectionDetails;
import org.hibernate.SessionFactory;

public interface DatabaseConnection {
    SessionFactory getSessionFactory();

    ConnectionDetails getConnectionDetails();

	Transakt createTransaction();
}

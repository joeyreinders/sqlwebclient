package net.sqlwebclient.core.connections;

import demo.Department;
import demo.Employee;
import net.sqlwebclient.core.objects.ConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

final class DatabaseConnectionImpl implements DatabaseConnection {
    private final ConnectionDetails connectionDetails;
    private final SessionFactory sessionFactory;

    DatabaseConnectionImpl(final ConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
        this.sessionFactory = newSessionFactory();
    }

    private SessionFactory newSessionFactory() {
        final Properties properties = new Properties();
        properties.putAll(connectionDetails.createConfiguration());

        final Configuration config = new Configuration().setProperties(properties);

		if(connectionDetails.getName().toLowerCase().startsWith("employee")) {
			config.addAnnotatedClass(Employee.class);
			config.addAnnotatedClass(Department.class);
		}

        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties())
                .build();
        return config.buildSessionFactory(serviceRegistry);
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public ConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

	@Override
	public Transakt createTransaction() {
		return new DefaultTransakt(this);
	}

	static DatabaseConnectionImpl of(final ConnectionDetails connectionDetails) {
        return new DatabaseConnectionImpl(connectionDetails);
    }
}

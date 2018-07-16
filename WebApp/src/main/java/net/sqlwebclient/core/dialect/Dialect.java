package net.sqlwebclient.core.dialect;

import com.google.common.collect.Lists;
import net.sqlwebclient.util.ClassUtil;

import java.util.List;

public enum Dialect {
    MY_SQL_5("My SQL 5", "com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQL5Dialect", "jdbc:mysql:", 3306),
    POSTGRESQL_9("PostgreSQL 9", "org.postgresql.Driver", "org.hibernate.dialect.PostgreSQL9Dialect", "jdbc:postgresql:", 5432),
    SYBASE_ASE("Sybase ASE", "net.sourceforge.jtds.jdbc.Driver", "org.hibernate.dialect.SybaseASE157Dialect", "jdbc:jtds:sybase", 7100),
    MS_SQL_SERVER_2005("MS SQL Server 2005", "net.sourceforge.jtds.jdbc.Driver", "org.hibernate.dialect.SQLServer2005Dialect", "jdbc:jtds:sqlserver", 1433),
    MS_SQL_SERVER_2008("MS SQL Server 2008", "net.sourceforge.jtds.jdbc.Driver", "org.hibernate.dialect.SQLServer2008Dialect", "jdbc:jtds:sqlserver", 1433),
    MS_SQL_SERVER_2012("MS SQL Server 2012", "net.sourceforge.jtds.jdbc.Driver", "org.hibernate.dialect.SQLServer2012Dialect", "jdbc:jtds:sqlserver", 1433)
    ;

    private final String name;
    private final String driver;
    private final String dialect;
    private final String urlPrefix;
    private final int defaultPort;
    private final boolean isAvailable;

    private int uuid;
	private org.hibernate.dialect.Dialect hibernateDialect;

    Dialect(final String name,
            final String driver,
            final String dialect,
            final String urlPrefix,
            final int defaultPort) {
        this.name = name;
        this.driver = driver;
        this.dialect = dialect;
        this.urlPrefix = urlPrefix;
        this.defaultPort = defaultPort;
		this.isAvailable = ClassUtil.isClassPresentOnClassPath(driver);
    }

    public String getName() {
        return name;
    }

    public String getDriver() {
        return driver;
    }

    public String getDialect() {
        return dialect;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public String showDatabasesSql() {
        return "SHOW DATABASES";
    }

    public long getUuid() {
        if(this.uuid == 0) {
            this.uuid = name().hashCode();
        }
        return uuid;
    }

    public static Dialect getByUuid(final long uuid) {
        for(Dialect d: values()) {
            if(d.getUuid() == uuid) {
                return d;
            }
        }

        return null;
    }

    //todo cleanup
    public String buildUrl(final String host, final int port, final String database) {
        String url = getUrlPrefix() + "//" + host + ":" + port;
        if(! (database == null || database.isEmpty())) {
            url += "/" + database;
        }

        return url;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public static Dialect[] getAvailableDialects() {
        final List<Dialect> res = Lists.newArrayList();
        for(Dialect dialect: values()) {
            if(dialect.isAvailable()) {
                res.add(dialect);
            }
        }

        return res.toArray(new Dialect[res.size()]);
    }

    public org.hibernate.dialect.Dialect getHibernateDialect() {
        if(hibernateDialect == null) {
			hibernateDialect = (org.hibernate.dialect.Dialect) ClassUtil.createClassInstance(dialect, new Object[] {});
		}

		return hibernateDialect;
    }
}

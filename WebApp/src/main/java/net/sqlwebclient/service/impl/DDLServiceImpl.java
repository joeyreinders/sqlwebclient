package net.sqlwebclient.service.impl;

import com.google.inject.Inject;
import net.sqlwebclient.core.connections.DatabaseConnectionFactoryCache;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.service.DDLService;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.task.WriteSchemaSqlToFileCommand;
import org.hibernate.cfg.Environment;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

final class DDLServiceImpl implements DDLService {
    final DatabaseConnectionFactoryCache databaseConnectionFactoryCache;

    @Inject
    DDLServiceImpl(DatabaseConnectionFactoryCache databaseConnectionFactoryCache) {
        this.databaseConnectionFactoryCache = databaseConnectionFactoryCache;
    }

    @Override
    public String generateDDL(final ConnectionDetails connectionDetails,
                              final String tableName) {
        final File file = new File("test");
        if(! file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(file.getAbsolutePath());

        final WriteSchemaSqlToFileCommand writeSchemaSqlToFileCommand = new WriteSchemaSqlToFileCommand();
        writeSchemaSqlToFileCommand.setOutputFile(file);
        writeSchemaSqlToFileCommand.setDoDrops(false);
        writeSchemaSqlToFileCommand.setAlterDatabase(true);

        final DataSource ds = getDataSource(connectionDetails);
        final Platform platform = PlatformFactory.createNewPlatformInstance(ds);
        final Database db = platform.readModelFromDatabase("model");

        new DatabaseIO().write(db, file.getAbsolutePath());

        return null;
    }

    private DataSource getDataSource(final ConnectionDetails connectionDetails) {
        final Properties properties = new Properties();
        properties.putAll(connectionDetails.createConfiguration());

        final BasicDataSource ds = new BasicDataSource();
        ds.setPassword(properties.getProperty(Environment.PASS));
        ds.setUsername(properties.getProperty(Environment.USER));
        ds.setUrl(properties.getProperty(Environment.URL));
        ds.setDriverClassName(properties.getProperty(Environment.DRIVER));

        return ds;
    }
}

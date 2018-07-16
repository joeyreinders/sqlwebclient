package net.sqlwebclient.core.objects;

import com.google.common.collect.ImmutableMap;
import net.sqlwebclient.core.dialect.Dialect;
import net.sqlwebclient.json.objects.JsonConnectionDetails;
import net.sqlwebclient.util.Builder;
import org.hibernate.cfg.Environment;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public final class ConnectionDetails implements Serializable {
    private static final long serialVersionUID = 129314007161302676L;

    private String name;

    private String hostName;

    private Integer port;

    private String database;

    private String username;

    private String password;

    private Dialect dialect;

    private Long uuid;

    private boolean isDefault;

    public String getHostName() {
        return hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUuid() {
        return uuid;
    }

    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hostName, port, database, username);
    }

    @Override
    public boolean equals(final Object other) {
        return !(other == null || other.getClass() != this.getClass()) &&
                Objects.equals(this.getUuid(), ((ConnectionDetails) other).getUuid());
    }

    public Map<String, Object> createConfiguration() {
        final String url = getDialect().buildUrl(this.getHostName(), this.getPort(), this.getDatabase());
        return ImmutableMap.<String, Object> builder()
                .put(Environment.DRIVER, getDialect().getDriver())
                .put(Environment.USER, this.getUsername())
                .put(Environment.PASS, this.getPassword())
                .put(Environment.DIALECT, getDialect().getDialect())
                .put(Environment.URL, url)
				.put(Environment.GENERATE_STATISTICS, false)
                .build();
    }

    public Dialect getDialect() {
        return dialect;
    }

    public static ConnectionDetailsBuilder newBuilder() {
        return new ConnectionDetailsBuilder();
    }

    public static final class ConnectionDetailsBuilder implements Builder<ConnectionDetails> {
        private ConnectionDetailsBuilder() {
            //Noop
        }

        private String name;

        private String hostName;

        private Integer port;

        private String database;

        private String username;

        private String password;

        private Dialect dialect;

        private Boolean isDefault;

        public ConnectionDetailsBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        public ConnectionDetailsBuilder setHostName(final String hostName) {
            this.hostName = hostName;
            return this;
        }

        public ConnectionDetailsBuilder setPort(final Integer port) {
            this.port = port;
            return this;
        }

        public ConnectionDetailsBuilder setDatabase(final String database) {
            this.database = database;
            return this;
        }

        public ConnectionDetailsBuilder setUsername(final String username) {
            this.username = username;
            return this;
        }

        public ConnectionDetailsBuilder setPassword(final String password) {
            this.password = password;
            return this;
        }

        public ConnectionDetailsBuilder setDialect(final Dialect dialect) {
            this.dialect = dialect;
            return this;
        }

        public ConnectionDetailsBuilder setDefault(final Boolean bln) {
            this.isDefault = bln;
            return this;
        }

        @Override
        public ConnectionDetails build() {
            final ConnectionDetails returnValue = new ConnectionDetails();
            returnValue.database = this.database;
            returnValue.name = this.name;
            returnValue.hostName = this.hostName;
            returnValue.port = this.port;
            returnValue.username = this.username;
            returnValue.password = this.password;
            returnValue.dialect = this.dialect;
            returnValue.isDefault = this.isDefault != null && this.isDefault;
            return returnValue;
        }
    }

    public static ConnectionDetails of(final JsonConnectionDetails json) {
        final ConnectionDetails res = ConnectionDetails.newBuilder()
                .setDatabase(json.getDatabase())
                .setDialect(Dialect.getByUuid(json.getDialect()))
                .setHostName(json.getHostName())
                .setName(json.getName())
                .setPassword(json.getPassword())
                .setPort(json.getPort())
                .setUsername(json.getUsername())
                .setDefault(json.isDefault())
                .build();
        res.uuid = json.getUuid();
        return res;
    }

    /**
     * This is a shortcut, must be reviewed
     */
    public static ConnectionDetails newInstance(final long uuid) {
        final ConnectionDetails res = new ConnectionDetails();
        res.uuid = uuid;
        return res;
    }
}

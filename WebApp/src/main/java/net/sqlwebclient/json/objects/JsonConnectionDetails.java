package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.objects.ConnectionDetails;

public final class JsonConnectionDetails extends Json {
    private static final long serialVersionUID = 5686215218524769110L;

    private String name;

    private String hostName;

    private Integer port;

    private String database;

    private String username;

    private String password;

    private Long dialect;

    private Long uuid;

    private Boolean isDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDialect() {
        return dialect;
    }

    public void setDialect(Long dialect) {
        this.dialect = dialect;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(final Long uuid) {
        this.uuid = uuid;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(final Boolean defaultConnection) {
        this.isDefault = defaultConnection;
    }

    public static JsonConnectionDetails of(final ConnectionDetails connectionDetails) {
        final JsonConnectionDetails res = new JsonConnectionDetails();
        res.setName(connectionDetails.getName());
        res.setHostName(connectionDetails.getHostName());
        res.setPort(connectionDetails.getPort());
        res.setDatabase(connectionDetails.getDatabase());
        res.setUsername(connectionDetails.getUsername());
        res.setPassword(connectionDetails.getPassword());
        res.setDialect(connectionDetails.getDialect().getUuid());
        res.setUuid(connectionDetails.getUuid());
        res.setDefault(connectionDetails.isDefault());
        return res;
    }
}

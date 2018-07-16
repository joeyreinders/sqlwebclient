package net.sqlwebclient.service.config;

import net.sqlwebclient.json.objects.Json;

final class JsonServiceConfig extends Json {
    private static final long serialVersionUID = -5363996523782236614L;

    private String ifCls;

    private String implCls;

    private String scope;

    public String getInterfaceClass() {
        return this.ifCls;
    }

    public String getImplementationClass() {
        return this.implCls;
    }

    public String getScope() {
        return this.scope;
    }
}

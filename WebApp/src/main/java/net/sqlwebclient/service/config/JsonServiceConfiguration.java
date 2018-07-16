package net.sqlwebclient.service.config;

import net.sqlwebclient.json.objects.Json;

final class JsonServiceConfiguration extends Json {
    private static final long serialVersionUID = 6829910057601911709L;

    private JsonServiceConfig[] configs;

    public JsonServiceConfig[] getConfigs() {
        return configs;
    }
}

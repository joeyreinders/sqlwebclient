package net.sqlwebclient.service.impl;

import com.google.gson.Gson;
import net.sqlwebclient.json.mapper.JsonMapper;
import net.sqlwebclient.json.mapper.JsonMappers;
import net.sqlwebclient.service.JsonConversionService;
import net.sqlwebclient.util.gson.GsonConfiguration;

final class JsonConversionServiceImpl implements JsonConversionService {
    private final Gson gson = GsonConfiguration.INSTANCE.getGson();

    private final JsonMapper mappers = JsonMappers.newInstance(gson);

    @Override
    public <T> T fromJson(final String jsonString, final Class<T> cls) {
        return gson.fromJson(jsonString, cls);
    }

    @Override
    public String toJson(final Object jsonObject) {
        return mappers.fromObject(jsonObject);
    }

    @Override
    public Gson getGson() {
        return gson;
    }
}

package net.sqlwebclient.json.mapper;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import net.sqlwebclient.json.objects.JsonList;
import net.sqlwebclient.json.objects.JsonMap;

import java.util.Map;

public final class JsonMappers implements JsonMapper {
    private final Map<String, JsonMapper> mappers = Maps.newHashMap();
    private final JsonMapper defaultMapper = new DefaultMapper();
    private final Gson gson;

    private JsonMappers(final Gson gson) {
        this.gson = gson;

        //init
        mappers.put(JsonList.class.getName(), JsonListMapper.newInstance(gson));
        mappers.put(JsonMap.class.getName(), JsonMapMapper.newInstance(gson));
    }

    @Override
    public String fromObject(final Object object) {
        if(object == null) {
            return gson.toJson(null);
        }

        final JsonMapper mapper = mappers.get(object.getClass().getName());
        return mapper == null ? defaultMapper.fromObject(object) : mapper.fromObject(object);
    }

    private final class DefaultMapper implements JsonMapper {
        @Override
        public String fromObject(final Object object) {
            return gson.toJson(object);
        }
    }

    public static JsonMappers newInstance(final Gson gson) {
        return new JsonMappers(gson);
    }
}

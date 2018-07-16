package net.sqlwebclient.json.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.sqlwebclient.json.objects.JsonMap;

final class JsonMapMapper implements JsonMapper {
    private final Gson gson;

    private JsonMapMapper(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public String fromObject(final Object object) {
        final JsonMap<?, ?> map = (JsonMap<?, ?>) object;
        final JsonObject json = MapperUtil.ofMap(map, gson);

        return gson.toJson(json);
    }

    static JsonMapMapper newInstance(final Gson gson) {
        return new JsonMapMapper(gson);
    }
}

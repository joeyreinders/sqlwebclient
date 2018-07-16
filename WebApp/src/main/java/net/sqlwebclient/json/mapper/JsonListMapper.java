package net.sqlwebclient.json.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import net.sqlwebclient.json.objects.JsonList;

final class JsonListMapper implements JsonMapper {
    private final Gson gson;

    private JsonListMapper(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public String fromObject(final Object object) {
        final JsonList<?> jsonList = (JsonList<?>) object;

        final JsonArray jsonArray = MapperUtil.ofList(jsonList, gson);

        return gson.toJson(jsonArray);
    }

    static JsonListMapper newInstance(final Gson gson) {
        return new JsonListMapper(gson);
    }
}

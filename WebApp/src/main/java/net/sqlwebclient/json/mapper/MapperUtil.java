package net.sqlwebclient.json.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.sqlwebclient.json.objects.JsonList;
import net.sqlwebclient.json.objects.JsonMap;

import java.util.Collection;
import java.util.Map;

final class MapperUtil {
    private MapperUtil() {
        throw new AssertionError("No instance for you");
    }

    public static JsonArray ofList(final JsonList<?> list, final Gson gson) {
        return ofCollection(list.getValues(), gson);
    }

    public static JsonArray ofCollection(final Collection<?> col, final Gson gson) {
        final JsonArray res = new JsonArray();

        for(Object obj: col) {
            res.add(gson.toJsonTree(obj));
        }

        return res;
    }

    public static JsonObject ofMap(final JsonMap<?, ?> map, final Gson gson) {
        final JsonObject res = new JsonObject();

        if(map != null && map.getMap() != null) {
            for(Map.Entry<?, ?> entry: map.getMap().entrySet()) {
                res.add(String.valueOf(entry.getKey()), gson.toJsonTree(entry.getValue()));
            }
        }
        return res;
    }
}

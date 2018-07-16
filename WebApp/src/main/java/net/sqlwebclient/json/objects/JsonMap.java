package net.sqlwebclient.json.objects;

import java.util.Map;

public final class JsonMap<K, V> extends Json {
    private static final long serialVersionUID = -432213160238654239L;

    private final Map<K, V> map;

    private JsonMap(final Map<K, V> map) {
        this.map = map;
    }

    public Map<K, V> getMap() {
        return map;
    }

    public static <K, V> JsonMap<K, V> of(final Map<K, V> map) {
        return new JsonMap<K, V>(map);
    }
}

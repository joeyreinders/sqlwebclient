package net.sqlwebclient.json.objects;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class JsonList<T> extends Json {
    private static final long serialVersionUID = -8726168468522815505L;

    private final List<T> values;

    private JsonList(final List<T> values) {
        this.values = values;
    }

    public List<T> getValues() {
        return values;
    }

    public static <T> JsonList<T> valueOf(final List<T> list) {
        return new JsonList<T>(ImmutableList.copyOf(list));
    }

    public static <T> JsonList<T> valueOf(final T[] array) {
        return new JsonList<T>(ImmutableList.copyOf(array));
    }
 }

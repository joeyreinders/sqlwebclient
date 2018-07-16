package net.sqlwebclient.core.objects.result;

import com.google.common.collect.Maps;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public final class Tuple extends HashMap<String, String> {
    private static final long serialVersionUID = -1879039476236197048L;

    public Tuple(){

    }

    public static Tuple newInstance() {
        return new Tuple();
    }

    public static JsonDeserializer<Tuple> getDeserializer() {
        return new JsonDeserializer<Tuple>() {
            @Override
            public Tuple deserialize(final JsonElement jsonElement,
                                     final Type type,
                                     final JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                final Tuple tuple = newInstance();
                final Set<Map.Entry<String, JsonElement>> properties = jsonElement.getAsJsonObject().entrySet();

                for(Map.Entry<String, JsonElement> entry: properties) {
					final String value;
					if(entry.getValue() instanceof JsonNull || entry.getValue() == null) {
						value = "null";
					} else {
						value = entry.getValue().getAsString();
					}

                    tuple.put(entry.getKey(), value);
                }

                return tuple;
            }
        };
    }

	public LinkedHashMap<String, String> getSorted(final Collection<Column> columns) {
		final LinkedHashMap<String, String> res = Maps.newLinkedHashMap();
		for(Column column: columns) {
			res.put(column.getColumnName(), this.get(column.getColumnName()));
		}

		return res;
	}

	public static Tuple of(final Map<String, String> values) {
		final Tuple res = new Tuple();
		res.putAll(values);
		return res;
	}
}

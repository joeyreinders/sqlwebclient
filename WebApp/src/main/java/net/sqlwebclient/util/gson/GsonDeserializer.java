package net.sqlwebclient.util.gson;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.sqlwebclient.core.objects.result.Tuple;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;

final class GsonDeserializer {
	static Map<Type, Object> getDeserializers() {
		return ImmutableMap.<Type, Object> builder()
				.put(Tuple.class, Tuple.getDeserializer())
				.put(Serializable.class, new SerializableDeserialzer())
				.build();
	}

	private static class SerializableDeserialzer implements JsonDeserializer<Serializable> {
		@Override
		public Serializable deserialize(final JsonElement json,
										final Type typeOfT,
										final JsonDeserializationContext context) throws JsonParseException {
			return json.getAsString();
		}
	}
}

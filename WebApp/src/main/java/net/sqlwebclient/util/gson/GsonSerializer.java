package net.sqlwebclient.util.gson;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

final class GsonSerializer {
	private static final JsonElement jsonNull = new JsonPrimitive("null");

	static Map<Type, Object> getSerializers() {
		return ImmutableMap.<Type, Object> builder()
				.put(Timestamp.class, new TimestampSerializer())
				.put(Date.class, new DateSerializer())
				.build();
	}

	private static final class TimestampSerializer implements JsonSerializer<Timestamp> {
		@Override
		public JsonElement serialize(final Timestamp src,
									 final Type typeOfSrc,
									 final JsonSerializationContext context) {
			return GsonSerializer.serialize(src);
		}
	}

	private static final class DateSerializer implements JsonSerializer<Date> {
		@Override
		public JsonElement serialize(final Date src,
									 final Type typeOfSrc,
									 final JsonSerializationContext context) {
			return GsonSerializer.serialize(src);
		}
	}

	private static JsonElement serialize(final Object src) {
		return Objects.isNull(src) ? jsonNull : new JsonPrimitive(src.toString());
	}
}

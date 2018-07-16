package net.sqlwebclient.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Map;

public enum GsonConfiguration {
	INSTANCE;

	private final Gson gson;

	private GsonConfiguration() {
		this.gson = create();
	}

	public Gson getGson() {
		return gson;
	}

	private Gson create() {
		final GsonBuilder builder = new GsonBuilder();

		generalSettings(builder);
		registerSerializers(builder);
		registerDeserializers(builder);

		return builder.create();
	}

	private void generalSettings(final GsonBuilder gsonBuilder) {
		gsonBuilder.setPrettyPrinting()
				.serializeNulls()
				//.excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	private void registerSerializers(final GsonBuilder gsonBuilder) {
		for(Map.Entry<Type, Object> entry: GsonSerializer.getSerializers().entrySet()) {
			gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}
	}

	private void registerDeserializers(final GsonBuilder gsonBuilder) {
		for(Map.Entry<Type, Object> entry: GsonDeserializer.getDeserializers().entrySet()) {
			gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}
	}
}

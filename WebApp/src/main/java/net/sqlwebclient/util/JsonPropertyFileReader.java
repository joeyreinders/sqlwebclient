package net.sqlwebclient.util;

import com.google.common.io.Closeables;
import com.google.gson.Gson;
import net.sqlwebclient.util.gson.GsonConfiguration;

import java.io.IOException;
import java.io.InputStream;

public final class JsonPropertyFileReader {
    private static final Gson GSON = GsonConfiguration.INSTANCE.getGson();

    private final InputStream inputStream;

    public JsonPropertyFileReader(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public <T> T read(final Class<T> cls) {
        try {
            final String jsonStream = Toolkit.toString(inputStream);
            return GSON.fromJson(jsonStream, cls);
        } catch (IOException ioException) {
            throw new RuntimeException("Error while reading json file ", ioException);
        } finally {
            Closeables.closeQuietly(inputStream);
        }
    }

    public static JsonPropertyFileReader newInstance(final InputStream is) {
        return new JsonPropertyFileReader(is);
    }
}

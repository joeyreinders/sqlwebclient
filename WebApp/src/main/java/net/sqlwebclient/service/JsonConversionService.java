package net.sqlwebclient.service;

import com.google.gson.Gson;

public interface JsonConversionService {
    <T> T fromJson(String jsonString, Class<T> cls);

    String toJson(Object jsonObject);

    Gson getGson();
}

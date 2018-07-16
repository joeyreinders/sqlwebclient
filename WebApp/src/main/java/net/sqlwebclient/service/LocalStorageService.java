package net.sqlwebclient.service;

import com.google.common.base.Predicate;

public interface LocalStorageService {
    void save(final String key, final String value);

    void delete(final String key);

    String get(final String key);

    String get(final String key, final String defaultValue);

    String[] getAll();

    /**
     * Predicate that applies on the key
     * @param predicate the predicate
     * @return all found instances
     */
    String[] getAll(final Predicate<String> predicate);
}

package net.sqlwebclient.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import net.sqlwebclient.service.LocalStorageService;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

final class UserPreferencesLocalStorage implements LocalStorageService {
    private static final Log logger = LogFactory.getLog(UserPreferencesLocalStorage.class);
    private final Preferences preferences = Preferences.userNodeForPackage(UserPreferencesLocalStorage.class);

    @Override
    public void save(final String key, final String value) {
        preferences.put(key, value);
    }

    @Override
    public void delete(final String key) {
        preferences.remove(key);
    }

    @Override
    public String get(final String key) {
        return get(key, "null");
    }

    @Override
    public String get(final String key, final String defaultValue) {
        return preferences.get(key, defaultValue);
    }

    @Override
    public String[] getAll() {
        return getAll(new Predicate<String>() {
            @Override
            public boolean apply(@Nullable final String input) {
                return true;
            }
        });
    }

    @Override
    public String[] getAll(final Predicate<String> predicate) {
        final List<String> res = Lists.newArrayList();
        try {
            for (final String key : preferences.keys()) {
                if (predicate.apply(key)) {
                    res.add(get(key));
                }
            }
        } catch (BackingStoreException ex) {
            logger.debug("Error while fetching connection details", ex);
        }

        return res.toArray(new String[res.size()]);
    }
}

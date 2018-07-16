package net.sqlwebclient.core.objects;

import net.sqlwebclient.event.SettingListener;
import net.sqlwebclient.event.SettingListenerSupport;
import net.sqlwebclient.event.impl.SettingListenerUtil;
import net.sqlwebclient.exception.SettingNotFoundExceptionOld;

import java.util.Objects;

public enum Settings {
    LANGUAGE("en"),
    JS_CONSOLE_LOGGING("true"),
	JAVA_CONSOLE_LOGGING("false"),
    QUERY_STATISTICS("false"), //TODO re-enable when statistics part has been build,
    QUERY_CACHE_SIZE("100"),
    CACHE_DB_CONNECTIONS_ON_STARTUP("false"),
	PAGINATION_DEFAULT_VALUE("25")
    ;

    public static final String UNDEFINED = "undefined";
	private static final SettingListenerSupport support = SettingListenerUtil.createSupport();

    private final String defaultValue;
    private String value;

    private Settings(final String defaultValue) {
        this(defaultValue, UNDEFINED); //fix this todo
    }

    private Settings(final String defaultValue, final String value) {
        this.defaultValue = defaultValue;
        this.value = value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getValueOrDefault() {
        return UNDEFINED.equals(this.value) ? this.defaultValue : this.value;
    }

    public boolean valueEquals(final Object obj) {
        final String otherValue = obj == null ? UNDEFINED : String.valueOf(obj);
        return Objects.equals(getValueOrDefault(), otherValue);
    }

    //Duplicate but i want a custom exception
    public static Settings getByName(final String name) {
        try {
            return Settings.valueOf(name);
        } catch (IllegalArgumentException iae) {
            throw new SettingNotFoundExceptionOld(name);
        }
    }

	public static SettingListenerSupport getSupport() {
		return support;
	}

	public static void addSettingListener(final SettingListener settingListener) {
		getSupport().addSettingListener(settingListener);
	}

	public static void removeSettingListener(final SettingListener settingListener) {
		getSupport().removeSettingListener(settingListener);
	}
}

package net.sqlwebclient.event;

public interface SettingListenerSupport {
	void addSettingListener(final SettingListener settingListener);

	void removeSettingListener(final SettingListener settingListener);
}

package net.sqlwebclient.event;

import net.sqlwebclient.core.objects.Settings;

import java.util.EventListener;

public interface SettingListener extends EventListener {
	void settingChanged(final Settings setting);
}

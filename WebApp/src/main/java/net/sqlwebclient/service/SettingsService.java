package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.event.SettingListenerSupport;

public interface SettingsService extends SettingListenerSupport {
    void saveSettings(final Settings ... settings);

    Settings[] getAll();
}

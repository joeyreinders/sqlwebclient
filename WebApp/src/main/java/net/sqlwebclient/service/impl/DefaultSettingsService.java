package net.sqlwebclient.service.impl;

import com.google.inject.Inject;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.event.SettingListener;
import net.sqlwebclient.event.impl.SettingListenerUtil;
import net.sqlwebclient.service.LocalStorageService;
import net.sqlwebclient.service.SettingsService;
import net.sqlwebclient.util.Holder;

final class DefaultSettingsService implements SettingsService {
    private final LocalStorageService localStorageService;
    private final Holder<Boolean> alreadyLoaded = Holder.of(false);
    public static final String PREFIX = "_SETTING_";

    @Inject
    DefaultSettingsService(final LocalStorageService localStorageService) {
        this.localStorageService = localStorageService;
    }

    @Override
    public void saveSettings(final Settings... settings) {
        if (settings == null || settings.length == 0) {
            return;
        }

        for (Settings setting : settings) {
            localStorageService.save(PREFIX + setting.name(), setting.getValue());
			fire(setting); //only fire when really saved on the computer
        }
    }

    private void loadSetting(final Settings setting) {
        final String value = localStorageService.get(PREFIX + setting.name(), setting.getDefaultValue());
        setting.setValue(value);
    }

    @Override
    public Settings[] getAll() {
        if(! alreadyLoaded.get()) {
            for(Settings setting: Settings.values()) {
                loadSetting(setting);
            }

            alreadyLoaded.set(true);
        }

        return Settings.values();
    }

	@Override
	public void addSettingListener(final SettingListener settingListener) {
		Settings.getSupport().addSettingListener(settingListener);
	}

	@Override
	public void removeSettingListener(final SettingListener settingListener) {
		Settings.getSupport().removeSettingListener(settingListener);
	}

	private void fire(final Settings settings) {
		SettingListenerUtil.fire(Settings.getSupport(), settings);
	}
}

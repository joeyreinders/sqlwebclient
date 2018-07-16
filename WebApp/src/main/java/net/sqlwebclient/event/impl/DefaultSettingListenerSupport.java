package net.sqlwebclient.event.impl;

import com.google.common.collect.Lists;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.event.SettingListener;
import net.sqlwebclient.event.SettingListenerSupport;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import java.util.List;

final class DefaultSettingListenerSupport implements SettingListenerSupport {
	private final Log logger = LogFactory.getLog(DefaultSettingListenerSupport.class);
	private final List<SettingListener> listeners = Lists.newArrayList();

	DefaultSettingListenerSupport() {
		//noop
	}

	@Override
	public void addSettingListener(final SettingListener settingListener) {
		listeners.add(settingListener);
	}

	@Override
	public void removeSettingListener(final SettingListener settingListener) {
		listeners.add(settingListener);
	}

	void fire(final Settings setting) {
		for(SettingListener listener: listeners) {
			fireListener(setting, listener);
		}
	}

	private void fireListener(final Settings settings,
					  final SettingListener listener) {
		if(settings == null) {
			return;
		}

		try {
			listener.settingChanged(settings);
		} catch (final Exception ex) {
			//log exception, don't stop the chain
			logger.error("error while executing listener", ex);
		}
	}
}

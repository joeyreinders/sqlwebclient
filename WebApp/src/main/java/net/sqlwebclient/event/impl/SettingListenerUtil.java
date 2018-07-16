package net.sqlwebclient.event.impl;

import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.event.SettingListenerSupport;

public final class SettingListenerUtil {
	private SettingListenerUtil() {
		throw new AssertionError("No instance creation allowed");
	}

	public static SettingListenerSupport createSupport() {
		return new DefaultSettingListenerSupport();
	}

	public static void fire(final SettingListenerSupport support,
							final Settings settings) {
		final DefaultSettingListenerSupport res = (DefaultSettingListenerSupport) support;
		res.fire(settings);
	}
}

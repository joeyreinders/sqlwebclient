package net.sqlwebclient.core;

import java.nio.charset.Charset;
import java.util.Locale;

public final class AppConstant {
    public static final Locale[] LOCALES = {Locale.ENGLISH, Locale.FRENCH, new Locale("nl")};
	public static final int SOCKET_PORT = 15489;
	public static final Charset ENCODING = Charset.forName("UTF-8");
}

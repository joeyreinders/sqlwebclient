package net.sqlwebclient.util;

import net.sqlwebclient.core.AppConstant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class UTF8Control extends ResourceBundle.Control {
	private UTF8Control() {
		//noop
	}

	@Override
	public ResourceBundle newBundle(final String baseName,
									final Locale locale,
									final String format,
									final ClassLoader loader,
									final boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {

		final String bundleName = toBundleName(baseName, locale);
		final String resourceName = toResourceName(bundleName, "properties");

		final ResourceBundle bundle;
		final InputStream stream;

		if(reload) {
			final URL url = loader.getResource(resourceName);
			if(url != null) {
				final URLConnection urlConnection = url.openConnection();
				if(urlConnection != null) {
					urlConnection.setUseCaches(false);
					stream = urlConnection.getInputStream();
				} else {
					stream = null;
				}
			} else {
				stream = null;
			}
		} else {
			stream = loader.getResourceAsStream(resourceName);
		}

		if(stream != null) {
			try {
				bundle = new PropertyResourceBundle(
						new InputStreamReader(stream, AppConstant.ENCODING)
				);
			} finally {
				stream.close();
			}
		} else {
			bundle = null;
		}

		return bundle;
	}

	public static ResourceBundle.Control newInstance() {
		return new UTF8Control();
	}
}

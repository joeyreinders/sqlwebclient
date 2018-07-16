package net.sqlwebclient.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.sqlwebclient.core.AppConstant;
import net.sqlwebclient.exception.ExceptionCode;
import net.sqlwebclient.exception.ExceptionFactory;
import net.sqlwebclient.service.TranslationService;
import net.sqlwebclient.util.UTF8Control;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

final class DefaultTranslationService implements TranslationService {
	private static final ResourceBundle.Control utf8Control = UTF8Control.newInstance();
	private static final Map<String, Locale> locales = Maps.newHashMap();

	static {
		for(int i = 0; i<AppConstant.LOCALES.length; i++) {
			final Locale locale = AppConstant.LOCALES[i];
			locales.put(locale.getLanguage(), locale);
		}
	}

    @Override
    public Map<String, String> getTranslations(final String lang) {
        checkLanguageSupport(lang);

        final Locale locale = locales.get(lang);
        final ResourceBundle resourceBundle = getResourceBundle(locale);
        final ImmutableMap.Builder<String, String> res = ImmutableMap.builder();
        final Enumeration<String> keys = resourceBundle.getKeys();
        while(keys.hasMoreElements()) {
            final String key = keys.nextElement();
            res.put(key, resourceBundle.getString(key));
        }

        return res.build();
    }

	@Override
	public String getTranslation(final String lang,
								 final String key) {
		final Locale locale = locales.get(lang);
		return getResourceBundle(locale).containsKey(key) ?
				getResourceBundle(locale).getString(key) : key;
	}

	private ResourceBundle getResourceBundle(final Locale locale) {
		return ResourceBundle.getBundle("translations", locale, utf8Control);
	}

	private void checkLanguageSupport(final String lang) {
        for(Locale locale: AppConstant.LOCALES) {
            if(locale.getLanguage().equalsIgnoreCase(lang)) {
                return;
            }
        }

        throw ExceptionFactory.create(ExceptionCode.LANGUAGE_NOT_SUPPORTED);
    }
}

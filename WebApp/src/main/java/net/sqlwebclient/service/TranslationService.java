package net.sqlwebclient.service;

import java.util.Map;

public interface TranslationService {
    Map<String, String> getTranslations(final String lang);

	String getTranslation(final String lang,
						  final String key);
}

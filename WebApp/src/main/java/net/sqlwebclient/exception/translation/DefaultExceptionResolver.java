package net.sqlwebclient.exception.translation;

import com.google.inject.Inject;
import net.sqlwebclient.exception.CustomException;
import net.sqlwebclient.service.TranslationService;

final class DefaultExceptionResolver implements ExceptionResolver {
	private final TranslationService translationService;

	@Inject
	DefaultExceptionResolver(final TranslationService translationService) {
		this.translationService = translationService;
	}

	@Override
	public String resolve(final CustomException customException, final String language) {
		final String translatedMessage;

		if(customException.isTranslatable()) {
			translatedMessage = translationService.getTranslation(language, customException.getExceptionCode().getCode());
		} else {
			translatedMessage = customException.getMessage();
		}


		return resolve(translatedMessage, customException.getArguments());
	}

	private String resolve(	final String translatedMessage,
							final Object[] arguments) {
		return String.format(translatedMessage, arguments);
	}
}

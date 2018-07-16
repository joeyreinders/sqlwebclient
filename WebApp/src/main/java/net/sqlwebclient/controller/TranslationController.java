package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.Cacheable;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.json.objects.JsonMap;
import net.sqlwebclient.service.TranslationService;

import java.util.Map;

@RequestMapping("/api/translation")
@Cacheable
final class TranslationController extends CrudController {
    private static final long serialVersionUID = -1836283169204885767L;

    private final TranslationService service;

    @Inject
    TranslationController(final TranslationService service) {
        this.service = service;
    }

	@Override
	protected CtrlResult all(final RequestContext ctx) {
		return get(ctx);
	}

	@Override
    protected CtrlResult get(final RequestContext ctx) {
        final String lang;
        if(ctx.getId() != null) {
            lang = ctx.getId();
        } else {
            lang = Settings.LANGUAGE.getValueOrDefault();
        }

        final Map<String, String> translations = service.getTranslations(lang);

        return ok(JsonMap.of(translations));
    }
}

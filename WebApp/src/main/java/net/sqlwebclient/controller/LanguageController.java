package net.sqlwebclient.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.AppConstant;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequestMapping("/api/language")
final class LanguageController extends CrudController {
    private static final long serialVersionUID = 1805970007517767289L;

    @Override
    public CtrlResult callGet(final RequestContext ctx) {
		final List<Map<String, String>> result = Lists.newArrayList();

		for(int i = 0; i<AppConstant.LOCALES.length; i++) {
			final Locale locale = AppConstant.LOCALES[i];
			final String language = locale.getDisplayLanguage(locale);

			result.add(
					ImmutableMap.<String, String> builder()
						.put("uuid", locale.getLanguage())
						.put("name", language)
						.build()
			);
		}

      	return ok(result);
    }
}

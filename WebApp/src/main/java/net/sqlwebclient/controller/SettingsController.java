package net.sqlwebclient.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.core.objects.result.Tuple;
import net.sqlwebclient.service.SettingsService;

import java.util.Map;

@RequestMapping("/api/settings")
final class SettingsController extends CrudController {
    private static final long serialVersionUID = -370497588201293386L;

    private final SettingsService settingsService;

    @Inject
    SettingsController(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    protected CtrlResult get(final RequestContext ctx) {
        if(ctx.getId() != null) {
         	final String setting = ctx.getId();


			final String value = ctx.getParam("value");

			final Settings settings = Settings.getByName(setting);
			settings.setValue(value);
			settingsService.saveSettings(settings);
        }

        return super.get(ctx);
    }

	@Override
	protected CtrlResult put(final RequestContext ctx) {
		final String setting = ctx.getId();
		final Map<String, String> map = getJsonConversionService().fromJson(ctx.getBody(), Tuple.class);

		final Settings settings = Settings.getByName(setting);
		settings.setValue(map.get("value"));
		settingsService.saveSettings(settings);

		return ok(
				ImmutableMap.<String, String> builder()
				.put(settings.name(), settings.getValueOrDefault())
				.build()
		);
	}

	@Override
	protected CtrlResult all(final RequestContext ctx) {
		settingsService.getAll();

		final Map<String, String> result = Maps.newHashMap();

		for(Settings settings : Settings.values()) {
			result.put(settings.name(), settings.getValueOrDefault());
		}

		return ok(result);
	}

	@Override
    protected CtrlResult post(final RequestContext ctx) {
        return super.post(ctx);
    }
}

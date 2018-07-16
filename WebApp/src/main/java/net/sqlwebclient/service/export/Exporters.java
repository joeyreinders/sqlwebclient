package net.sqlwebclient.service.export;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.exception.ExceptionCode;
import net.sqlwebclient.exception.ExceptionFactory;
import net.sqlwebclient.service.ExportService;
import net.sqlwebclient.service.JsonConversionService;
import net.sqlwebclient.util.Toolkit;
import org.apache.tools.ant.filters.StringInputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Exporters {
	@Inject
    private final JsonConversionService conversionService = null;

	@Inject
	private final ExportService exportService = null;

	private static final Map<String, Exporter> exporters;

	static {
		exporters = ImmutableMap.<String, Exporter>builder()
				.put("sql", SQLExporter.newInstance())
				.put("csv", CSVExporter.newInstance())
				.put("xml", XMLExporter.newInstance())
				.build();
	}

    private Exporters() {
		//noop
    }


	public List<String> getExportTypes() {
		return ImmutableList.copyOf(exporters.keySet());
	}


	public void doExport(final RequestContext ctx) throws IOException {
		final Exporter exporter = selectExporter(ctx);
		final Result resultObject = conversionService.fromJson(ctx.getBody(), Result.class);

		final String content = exporter.generateContent(exportService, resultObject);
		final HttpServletResponse response = ctx.getResponse();
		exporter.prepareResponse(content, response);

		Toolkit.copy(new StringInputStream(content), response.getOutputStream());
	}

	private Exporter selectExporter(final RequestContext ctx) {
		final String param = ctx.getParam("export");

		for(Map.Entry<String, Exporter> entry: exporters.entrySet()) {
			if(entry.getKey().equals(param)) {
				return entry.getValue();
			}
		}

		throw ExceptionFactory.create(ExceptionCode.EXPORT_TYPE_NOT_SUPPORTED, param);
	}

	public static Exporters newInstance(final Injector injector) {
		final Exporters result = new Exporters();
		injector.injectMembers(result);
		for(Exporter exporter: exporters.values()) {
			injector.injectMembers(exporter);
		}

		return result;
	}
}

package net.sqlwebclient.controller;

import com.google.inject.Injector;
import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.service.export.Exporters;

import javax.servlet.ServletException;
import java.io.IOException;

@RequestMapping("/api/export")
final class ExportController extends Controller {
    private static final long serialVersionUID = -7702280264311206020L;

	private Exporters exporters;

    @Override
	public void doPost(final RequestContext ctx) throws ServletException, IOException {
		getExporters(ctx).doExport(ctx);
    }

	@Override
	public void doGet(final RequestContext ctx) throws ServletException, IOException {
		super.doGet(ctx);
	}

	private Exporters getExporters(final RequestContext ctx) {
		if(exporters == null) {
			final Injector injector = net.sqlwebclient.util.ServletUtil.getInjector(ctx);
			exporters = Exporters.newInstance(injector);
		}

		return exporters;
	}
}

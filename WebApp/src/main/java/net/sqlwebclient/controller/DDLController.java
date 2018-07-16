package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.DDLService;

@RequestMapping("/api/ddl")
final class DDLController extends CrudController {
    private static final long serialVersionUID = -1127541596799574855L;

    private final DDLService ddlService;
    private final ConnectionDetailsService connectionDetailsService;

    @Inject
	DDLController(final DDLService ddlService,
				  final ConnectionDetailsService connectionDetailsService) {
        this.ddlService = ddlService;
        this.connectionDetailsService = connectionDetailsService;
    }

    @Override
    protected CtrlResult callGet(final RequestContext ctx) {
        final ConnectionDetails connectionDetails = ServletUtil.getConnectionFromRequest(connectionDetailsService, ctx);
        ddlService.generateDDL(connectionDetails, "employees");

        return super.get(ctx);
    }
}

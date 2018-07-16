package net.sqlwebclient.controller;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import net.sqlwebclient.controller.core.ControllerException;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.DatabaseInfo;
import net.sqlwebclient.json.objects.JsonDatabaseInfo;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.DatabaseInfoService;
import net.sqlwebclient.util.Toolkit;

@RequestMapping("/api/dbinfo")
final class DatabaseInfoController extends CrudController {
    private static final long serialVersionUID = 4292015798143300157L;

    private final DatabaseInfoService databaseInfoService;
    private final ConnectionDetailsService connectionDetailsService;

    @Inject
    DatabaseInfoController(final DatabaseInfoService databaseInfoService,
                           final ConnectionDetailsService connectionDetailsService) {
        this.databaseInfoService = databaseInfoService;
        this.connectionDetailsService = connectionDetailsService;
    }

    @Override
    protected CtrlResult callGet(final RequestContext ctx) {
        final String uuid = ctx.getId();
        if(Strings.isNullOrEmpty(uuid) || ! Toolkit.isFullyNumeric(uuid)) {
            throw ControllerException.CONNECTION_DETAILS_NOT_PROVIDED.throwNew();
        }

        final ConnectionDetails connectionDetails = connectionDetailsService.getByUuid(Long.valueOf(uuid));
        final DatabaseInfo info = databaseInfoService.getDatabaseInfo(connectionDetails);
        return ok(JsonDatabaseInfo.of(info));
    }
}

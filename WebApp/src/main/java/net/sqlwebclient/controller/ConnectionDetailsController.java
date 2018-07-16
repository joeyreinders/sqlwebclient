package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.ControllerException;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.ConnectionDetailsTestResult;
import net.sqlwebclient.json.objects.Json;
import net.sqlwebclient.json.objects.JsonConnectionDetails;
import net.sqlwebclient.json.objects.JsonConnectionDetailsTestResult;
import net.sqlwebclient.json.objects.JsonList;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.JsonConversionService;
import net.sqlwebclient.util.HttpStatus;

@RequestMapping("/api/connection")
final class ConnectionDetailsController extends CrudController {
    private static final long serialVersionUID = -5253435431761770034L;

    private final JsonConversionService conversionService;
    private final ConnectionDetailsService connectionDetailsService;

    @Inject
    ConnectionDetailsController(final JsonConversionService conversionService,
                                final ConnectionDetailsService connectionDetailsService) {
        this.conversionService = conversionService;
        this.connectionDetailsService = connectionDetailsService;
    }

    @Override
    protected CtrlResult all(final RequestContext ctx) {
        final Json res;
        if(! "default".equals(ctx.getParam("q"))) {
            final ConnectionDetails[] all = connectionDetailsService.getConnections();
            final JsonConnectionDetails[] jsonArray = new JsonConnectionDetails[all.length];

            for(int i = 0; i < jsonArray.length; i++) {
                jsonArray[i] = JsonConnectionDetails.of(all[i]);
            }

            res = JsonList.valueOf(jsonArray);
        } else {
            res = JsonConnectionDetails.of(getDefault());
        }

        return ok(res);
    }

    protected ConnectionDetails getDefault() {
        return connectionDetailsService.getDefault();
    }

    @Override
    protected CtrlResult get(final RequestContext ctx) {
        final Json res;
        final ConnectionDetails connectionDetails =
                connectionDetailsService.getByUuid(Long.valueOf(ctx.getId()));
        if(connectionDetails == null) {
            throw ControllerException.NOT_FOUND.throwNew();
        }

        res = JsonConnectionDetails.of(connectionDetails);

        return ok(res);
    }

    @Override
    protected CtrlResult put(final RequestContext ctx) {
        return save(ctx).addStatus(HttpStatus.OK).build();
    }

    @Override
    protected CtrlResult delete(final RequestContext ctx) {
        if(ctx.getId() == null) {
            throw ControllerException.PARAM_MISSING.throwNew();
        }

        final Long uuid = Long.valueOf(ctx.getId());
        final ConnectionDetails connectionDetails = ConnectionDetails.newInstance(uuid);
        connectionDetailsService.delete(connectionDetails);

        return CtrlResult.newBuilder().addStatus(HttpStatus.OK).build();
    }

    @Override
    protected CtrlResult post(final RequestContext ctx) {
        if(ctx.getParam("testConnection") != null) {
            final ConnectionDetails connDetails = readFromRequest(ctx);
            final ConnectionDetailsTestResult res = connectionDetailsService.testConnectionDetails(connDetails);
            return ok(JsonConnectionDetailsTestResult.of(res));
        }

        return save(ctx).addStatus(HttpStatus.CREATED).build();
    }

    private ConnectionDetails readFromRequest(final RequestContext ctx) {
        final JsonConnectionDetails json = conversionService.fromJson(ctx.getBody(), JsonConnectionDetails.class);
        return ConnectionDetails.of(json);
    }

    private CtrlResult.CtrlResultBuilder save(final RequestContext ctx) {
        //Convert Json
        final ConnectionDetails connectionDetails = readFromRequest(ctx);
        final ConnectionDetails saved = connectionDetailsService.save(connectionDetails);
        //Convert to return
        final JsonConnectionDetails jsonSaved = JsonConnectionDetails.of(saved);
        return CtrlResult.newBuilder().addResponseObject(jsonSaved);
    }
}

package net.sqlwebclient.controller;

import com.google.common.collect.Maps;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.json.objects.JsonMap;
import net.sqlwebclient.util.HttpStatus;

import java.util.Map;

@RequestMapping("/api")
final class WrongAccessController extends CrudController {
    private static final long serialVersionUID = -3727654991406754636L;

    @Override
    protected CtrlResult get(final RequestContext ctx) {
        return process(ctx);
    }

    @Override
    protected CtrlResult put(final RequestContext ctx) {
        return process(ctx);
    }

    @Override
    protected CtrlResult delete(final RequestContext ctx) {
        return process(ctx);
    }

    @Override
    protected CtrlResult post(final RequestContext ctx) {
        return process(ctx);
    }

    private CtrlResult process(final RequestContext ctx) {
        final Map<String, String> res = Maps.newHashMap();
        res.put("msg", "No servlet configured for path: " + ctx.getPathInfo() + " and method: " + ctx.getMethod());
        return CtrlResult.newBuilder()
                .addStatus(HttpStatus.NOT_FOUND)
                .addResponseObject(JsonMap.of(res))
                .build();
    }
}

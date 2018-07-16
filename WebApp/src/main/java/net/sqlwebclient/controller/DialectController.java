package net.sqlwebclient.controller;

import com.google.common.collect.Lists;
import net.sqlwebclient.controller.core.Cacheable;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.dialect.Dialect;
import net.sqlwebclient.json.objects.JsonDialect;
import net.sqlwebclient.json.objects.JsonList;

import java.util.List;

@RequestMapping("/api/dialect")
@Cacheable
final class DialectController extends CrudController {
    private static final long serialVersionUID = -2605186562117447316L;

    @Override
    protected CtrlResult all(final RequestContext ctx) {
        final List<JsonDialect> dialects = Lists.newArrayList();

        for(Dialect dialect: Dialect.getAvailableDialects()) {
            dialects.add(JsonDialect.valueOf(dialect));
        }

        return ok(JsonList.valueOf(dialects));
    }

    @Override
    protected CtrlResult get(final RequestContext ctx) {
        final long uuid = Long.valueOf(ctx.getId());
        return ok(JsonDialect.valueOf(Dialect.getByUuid(uuid)));
    }
}

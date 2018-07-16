package net.sqlwebclient.controller;

import com.google.common.base.Strings;
import net.sqlwebclient.controller.core.ControllerException;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.util.Toolkit;

final class ServletUtil {
    private ServletUtil(){}

    static ConnectionDetails getConnectionFromRequest(final ConnectionDetailsService connectionDetailsService,
                                                      final RequestContext ctx) {
        final String param = ctx.getParam("c");
        if(Strings.isNullOrEmpty(param)
                || ! Toolkit.isFullyNumeric(param)
                || "undefined".equals(param)) {
            throw ControllerException.CONNECTION_DETAILS_NOT_PROVIDED.throwNew();
        }

        return connectionDetailsService.getByUuid(Long.valueOf(param));
    }
}

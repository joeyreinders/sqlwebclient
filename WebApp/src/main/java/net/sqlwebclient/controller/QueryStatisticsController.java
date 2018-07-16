package net.sqlwebclient.controller;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.QueryStatistic;
import net.sqlwebclient.json.objects.JsonList;
import net.sqlwebclient.json.objects.JsonQueryStatistic;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.StatisticsService;

import java.util.List;
import java.util.Objects;

@RequestMapping("/querystatistics")
final class QueryStatisticsController extends CrudController {
    private static final long serialVersionUID = 7743209307262367705L;
    private final StatisticsService statisticsService;
    private final ConnectionDetailsService connectionDetailsService;

    @Inject
    QueryStatisticsController(final StatisticsService statisticsService,
                              final ConnectionDetailsService connectionDetailsService) {
        this.statisticsService = statisticsService;
        this.connectionDetailsService = connectionDetailsService;
    }

    @Override
    protected CtrlResult get(final RequestContext ctx) {
        final QueryStatistic[] res;
        final String connectionId = ctx.getParam("connectionId");
        if (connectionId != null) {
            final ConnectionDetails connectionDetails = connectionDetailsService.getByUuid(Long.valueOf(connectionId));
            Objects.requireNonNull(connectionDetails);
            res = statisticsService.getQueryStatistics(connectionDetails);
        } else {
            res = statisticsService.getQueryStatistics();
        }

        final List<JsonQueryStatistic> json = Lists.newArrayList();
        for(QueryStatistic queryStatistic: res) {
            json.add(JsonQueryStatistic.valueOf(queryStatistic));
        }

        return ok(JsonList.valueOf(json));
    }
}

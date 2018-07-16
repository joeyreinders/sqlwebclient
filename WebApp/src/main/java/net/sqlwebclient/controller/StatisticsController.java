package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.Statistics;
import net.sqlwebclient.json.objects.JsonStatistics;
import net.sqlwebclient.service.StatisticsService;

@RequestMapping("/api/statistics")
final class StatisticsController extends CrudController {
    private static final long serialVersionUID = 7197337167001820491L;

    private final StatisticsService statisticsService;

    @Inject
    StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    protected CtrlResult callGet(final RequestContext ctx) {
        final Statistics stats = statisticsService.getStatistics();
        final JsonStatistics res = JsonStatistics.of(stats);

        return ok(res);
    }
}

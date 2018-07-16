package net.sqlwebclient.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.QueryStatistic;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.core.objects.Statistics;
import net.sqlwebclient.service.StatisticsService;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

final class DefaultStatisticsService implements StatisticsService {
    private static final int MB = 1024 * 1024;

    private final LinkedHashMap<Long, QueryStatistic> queryStatistics = Maps.newLinkedHashMap();

    @Override
    public Statistics getStatistics() {
        final Runtime runtime = Runtime.getRuntime();

        return Statistics.builder()
                .setMaxMemory(runtime.maxMemory() / MB)
                .setTotalMemory(runtime.totalMemory() / MB)
                .setFreeMemory(runtime.freeMemory() / MB)
                .setUsedMemory((runtime.totalMemory() - runtime.freeMemory()) / MB)
                .round()
                .build();
    }

    @Override
    public long addQueryStatistic(final QueryStatistic queryStatistic) {
        if(queryStatistic != null && Settings.QUERY_STATISTICS.valueEquals(true)) {
            final long uuid = System.currentTimeMillis();
            queryStatistics.put(uuid, queryStatistic);
            return uuid;
        }

        return -1;
    }

    @Override
    public QueryStatistic[] getQueryStatistics() {
        return queryStatistics.values().toArray(new QueryStatistic[queryStatistics.values().size()]);
    }

    @Override
    public QueryStatistic[] getQueryStatistics(final ConnectionDetails connectionDetails) {
        final Map<Long, QueryStatistic> res = Maps.filterValues(queryStatistics, new Predicate<QueryStatistic>() {
            @Override
            public boolean apply(@Nullable final QueryStatistic input) {
                return input != null && Objects.equals(input, connectionDetails);
            }
        });

        return res.values().toArray(new QueryStatistic[res.size()]);
    }

    @Override
    public QueryStatistic getByUuid(final long uuid) {
        return queryStatistics.get(uuid);
    }
}

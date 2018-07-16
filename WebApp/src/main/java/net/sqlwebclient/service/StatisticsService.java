package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.QueryStatistic;
import net.sqlwebclient.core.objects.Statistics;

public interface StatisticsService {
    Statistics getStatistics();

    long addQueryStatistic(final QueryStatistic queryStatistic);

    QueryStatistic[] getQueryStatistics();

    QueryStatistic[] getQueryStatistics(final ConnectionDetails connectionDetails);

    QueryStatistic getByUuid(final long uuid);
}

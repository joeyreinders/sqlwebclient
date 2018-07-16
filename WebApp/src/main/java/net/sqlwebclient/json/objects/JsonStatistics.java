package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.objects.Statistics;

public final class JsonStatistics extends Json {
    private static final long serialVersionUID = -8693474281407491426L;

    private final double maxMemory;
    private final double totalMemory;
    private final double freeMemory;
    private final double usedMemory;
    private final long numberOfQueries;
    private final double averageQueryTime;

    private JsonStatistics(final Statistics statistics) {
        this.maxMemory = statistics.getMaxMemory();
        this.totalMemory = statistics.getTotalMemory();
        this.freeMemory = statistics.getFreeMemory();
        this.usedMemory = statistics.getUsedMemory();
        this.numberOfQueries = statistics.getNumberOfQueries();
        this.averageQueryTime = statistics.getAverageQueryTime();
    }

    public double getMaxMemory() {
        return maxMemory;
    }

    public double getTotalMemory() {
        return totalMemory;
    }

    public double getFreeMemory() {
        return freeMemory;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public long getNumberOfQueries() {
        return numberOfQueries;
    }

    public double getAverageQueryTime() {
        return averageQueryTime;
    }

    public static JsonStatistics of(final Statistics statistics) {
        return new JsonStatistics(statistics);
    }
}

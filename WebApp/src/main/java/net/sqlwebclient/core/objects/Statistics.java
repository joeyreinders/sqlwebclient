package net.sqlwebclient.core.objects;

import net.sqlwebclient.util.Builder;
import net.sqlwebclient.util.Toolkit;

public final class Statistics {
    private final double maxMemory;
    private final double totalMemory;
    private final double freeMemory;
    private final double usedMemory;
    private final long numberOfQueries;
    private final double averageQueryTime;

    private Statistics(final StatisticsBuilder statisticsBuilder) {
        this.maxMemory = statisticsBuilder.maxMemory;
        this.totalMemory = statisticsBuilder.totalMemory;
        this.freeMemory = statisticsBuilder.freeMemory;
        this.usedMemory = statisticsBuilder.usedMemory;
        this.numberOfQueries = statisticsBuilder.numberOfQueries;
        this.averageQueryTime = statisticsBuilder.averageQueryTime;
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

    public static StatisticsBuilder builder() {
        return new StatisticsBuilder();
    }

    public static final class StatisticsBuilder implements Builder<Statistics> {
        private static final long serialVersionUID = -3763246857540124556L;

        private double maxMemory;
        private double totalMemory;
        private double freeMemory;
        private double usedMemory;
        private long numberOfQueries;
        private double averageQueryTime;

        private StatisticsBuilder() {
            //noop
        }

        public StatisticsBuilder setMaxMemory(final double maxMemory) {
            this.maxMemory = maxMemory;
            return this;
        }

        public StatisticsBuilder setTotalMemory(final double totalMemory) {
            this.totalMemory = totalMemory;
            return this;
        }

        public StatisticsBuilder setFreeMemory(final double freeMemory) {
            this.freeMemory = freeMemory;
            return this;
        }

        public StatisticsBuilder setUsedMemory(final double usedMemory) {
            this.usedMemory = usedMemory;
            return this;
        }

        public StatisticsBuilder setNumberOfQueries(final long numberOfQueries) {
            this.numberOfQueries = numberOfQueries;
            return this;
        }

        public StatisticsBuilder setAverageQueryTime(final double averageQueryTime) {
            this.averageQueryTime = averageQueryTime;
            return this;
        }

        public StatisticsBuilder round() {
            this.maxMemory = Toolkit.round(this.maxMemory);
            this.totalMemory = Toolkit.round(this.totalMemory);
            this.freeMemory = Toolkit.round(this.freeMemory);
            this.usedMemory = Toolkit.round(this.usedMemory);

            return this;
        }

        @Override
        public Statistics build() {
            return new Statistics(this);
        }
    }
}

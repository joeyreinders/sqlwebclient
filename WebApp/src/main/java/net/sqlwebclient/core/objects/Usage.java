package net.sqlwebclient.core.objects;

import net.sqlwebclient.util.Builder;
import net.sqlwebclient.util.Toolkit;

public final class Usage {
	private final double maxMemory =  0;
	private final double totalMemory =  0;
	private final double freeMemory =  0;
	private final double usedMemory =  0;

	private final int openDBConnections =  0;
	private final int totalDBConnections =  0;

	private Usage() {
		//noop
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

	public int getOpenDBConnections() {
		return openDBConnections;
	}

	public int getTotalDBConnections() {
		return totalDBConnections;
	}

	public static UsageBuilder builder() {
		return new UsageBuilder();
	}

	public static final class UsageBuilder implements Builder<Usage> {
		private double maxMemory;
		private double totalMemory;
		private double freeMemory;
		private double usedMemory;

		private int openDBConnections;
		private int totalDBConnections;

		private UsageBuilder() {
			//noop
		}

		public UsageBuilder setMaxMemory(final double maxMemory) {
			this.maxMemory = maxMemory;
			return this;
		}

		public UsageBuilder setTotalMemory(final double totalMemory) {
			this.totalMemory = totalMemory;
			return this;
		}

		public UsageBuilder setFreeMemory(final double freeMemory) {
			this.freeMemory = freeMemory;
			return this;
		}

		public UsageBuilder setUsedMemory(final double usedMemory) {
			this.usedMemory = usedMemory;
			return this;
		}

		public UsageBuilder setOpenDBConnections(final int openDBConnections) {
			this.openDBConnections = openDBConnections;
			return this;
		}

		public UsageBuilder setTotalDBConnections(final int totalDBConnections) {
			this.totalDBConnections = totalDBConnections;
			return this;
		}

		@Override
		public Usage build() {
			final Usage res = new Usage();
			Toolkit.copyProperties(res, this);
			return null;
		}
	}
}

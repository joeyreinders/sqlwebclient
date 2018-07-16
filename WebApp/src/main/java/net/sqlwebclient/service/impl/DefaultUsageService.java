package net.sqlwebclient.service.impl;

import com.google.inject.Inject;
import net.sqlwebclient.core.connections.DatabaseConnectionFactoryCache;
import net.sqlwebclient.core.objects.Usage;
import net.sqlwebclient.service.UsageService;

final class DefaultUsageService implements UsageService {
	private static final long MB = 1024 * 1024;

	private final DatabaseConnectionFactoryCache connectionCache;

	@Inject
	DefaultUsageService(final DatabaseConnectionFactoryCache connectionCache) {
		this.connectionCache = connectionCache;
	}

	@Override
	public Usage getUsage() {
		final Usage.UsageBuilder builder = Usage.builder();

		//
		final Runtime runtime = Runtime.getRuntime();

		final long totalMemory = bytesToMegabytes(runtime.totalMemory());
		final long freeMemory = bytesToMegabytes(runtime.freeMemory());
		final long usedMemory = bytesToMegabytes(totalMemory - freeMemory);
		final long maxMemory = bytesToMegabytes(runtime.maxMemory());

		final int openConnections = connectionCache.getNumberOfOpenConnections();

		return Usage.builder()
				.setFreeMemory(freeMemory)
				.setMaxMemory(maxMemory)
				.setTotalMemory(totalMemory)
				.setUsedMemory(usedMemory)
				.setOpenDBConnections(openConnections)
				.build();
	}

	private long bytesToMegabytes(long bytes) {
		return bytes / MB;
	}
}

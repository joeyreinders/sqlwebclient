package net.sqlwebclient.service.impl;

import net.sqlwebclient.service.ThreadService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

final class ThreadServiceImpl implements ThreadService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void submit(final FutureTask<?> future) {
        executorService.submit(future);
    }
}

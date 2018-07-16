package net.sqlwebclient.service;

import java.util.concurrent.FutureTask;

public interface ThreadService {
    void submit(final FutureTask<?> future);
}

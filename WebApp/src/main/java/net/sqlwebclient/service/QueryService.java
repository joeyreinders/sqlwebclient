package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.core.objects.result.Result;

public interface QueryService {
    Result run(final QueryRequest request);
}

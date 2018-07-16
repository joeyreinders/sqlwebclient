package net.sqlwebclient.service;

import net.sqlwebclient.core.objects.result.Result;
import org.hibernate.dialect.Dialect;

public interface ExportService {
    String generateSQLInsert(   final Dialect dialect,
                                final Result result);

    String generateCSV(         final Result result);
}

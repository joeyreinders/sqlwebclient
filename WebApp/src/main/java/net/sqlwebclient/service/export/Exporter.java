package net.sqlwebclient.service.export;

import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.service.ExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

interface Exporter {
    String generateContent(final ExportService exportService,
                           final Result requestBody) throws IOException;

    void prepareResponse(final String content,
                         final HttpServletResponse response) throws IOException;
/*
    boilerplate

            try {
            ctx.getResponse().setContentType("text/plain");
            ctx.getResponse().setHeader("Content-Disposition", "attachment; filename=\"export.sql\"");
            ctx.getResponse().setContentLength(sql.length());
            ctx.getResponse().setStatus(HttpStatus.OK.value());
            IOUtils.copy(new StringInputStream(sql), ctx.getResponse().getOutputStream());
        } catch (final Exception ex) {
            Throwables.propagate(ex);
        }
 */
}

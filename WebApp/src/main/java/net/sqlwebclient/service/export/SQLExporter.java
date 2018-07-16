package net.sqlwebclient.service.export;

import com.google.inject.Inject;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.ExportService;
import net.sqlwebclient.util.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

final class SQLExporter implements Exporter {
	@Inject
	private ConnectionDetailsService connectionDetailsService;

	@Override
	public String generateContent(final ExportService exportService,
								  final Result requestBody) throws IOException {
		final ConnectionDetails connectionDetails =
				connectionDetailsService.getByUuid(requestBody.getMeta().getConnectionDetails());
		return exportService.generateSQLInsert(connectionDetails.getDialect().getHibernateDialect(), requestBody);
	}

	@Override
	public void prepareResponse(final String content,
								final HttpServletResponse response) throws IOException {
		response.setContentType("text/sql");
		response.setHeader("Content-Disposition", "attachment; filename=\"export.sql\"");
		response.setContentLength(content.length());
		response.setStatus(HttpStatus.OK.value());
	}

	static Exporter newInstance() {
		return new SQLExporter();
	}
}

package net.sqlwebclient.service.export;

import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.service.ExportService;
import net.sqlwebclient.util.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

final class CSVExporter implements Exporter {
	@Override
	public String generateContent(final ExportService exportService,
								  final Result requestBody) throws IOException {

		return exportService.generateCSV(requestBody);
	}

	@Override
	public void prepareResponse(final String content,
								final HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"export.csv\"");
		response.setContentLength(content.length());
		response.setStatus(HttpStatus.OK.value());
	}

	static Exporter newInstance() {
		return new CSVExporter();
	}
}

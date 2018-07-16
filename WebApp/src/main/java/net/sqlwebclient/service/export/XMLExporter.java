package net.sqlwebclient.service.export;

import net.sqlwebclient.core.AppConstant;
import net.sqlwebclient.core.objects.result.Column;
import net.sqlwebclient.core.objects.result.Result;
import net.sqlwebclient.core.objects.result.Tuple;
import net.sqlwebclient.service.ExportService;
import net.sqlwebclient.util.HttpStatus;
import net.sqlwebclient.util.Toolkit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class XMLExporter implements Exporter {
	private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private final Transformer transformer;
	private final DocumentBuilder documentBuilder;

	private XMLExporter() {
		this.transformer = createTransformer();
		this.documentBuilder = newDocumentBuilder();

		initTransformer();
	}

	@Override
	public String generateContent(final ExportService exportService,
								  final Result requestBody) throws IOException {
		final Document doc = documentBuilder.newDocument();
		final Element results = doc.createElement("Table");
		doc.appendChild(results);

		for(Tuple tuple: requestBody.getRows()) {
			final Element row = doc.createElement("Row");
			results.appendChild(row);

			transformTuple(row, doc, tuple, requestBody.getColumns());
		}

		return transform(doc);
	}

	private void transformTuple(final Element element,
								final Document document,
								final Tuple tuple,
								final List<Column> columns) {
		final LinkedHashMap<String, String> sorted = tuple.getSorted(columns);
		for(Map.Entry<String, String> entry: sorted.entrySet()) {
			final String value = String.valueOf(entry.getValue());
			final Element node = document.createElement(entry.getKey());
			node.appendChild(document.createTextNode(value));
			element.appendChild(node);
		}
	}


	@Override
	public void prepareResponse(final String content, final HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Content-Disposition", "attachment; filename=\"export.xml\"");
		response.setContentLength(content.length());
		response.setStatus(HttpStatus.OK.value());
	}

	private String transform(final Document document) {
		final DOMSource domSource = new DOMSource(document);
		final StringWriter sw = new StringWriter();

		Toolkit.wrapExceptionMethod(new Toolkit.ExceptionMethod() {
			@Override
			public Object execute() throws Exception {
				transformer.transform(domSource, new StreamResult(sw));
				return Void.TYPE;
			}
		});

		return sw.toString();
	}

	private DocumentBuilder newDocumentBuilder() {
		return Toolkit.wrapExceptionMethod(new Toolkit.ExceptionMethod() {
			@Override
			public Object execute() throws Exception {
				return documentBuilderFactory.newDocumentBuilder();
			}
		});
	}

	private Transformer createTransformer() {
		return Toolkit.wrapExceptionMethod(new Toolkit.ExceptionMethod() {
			@Override
			public Object execute() throws Exception {
				return transformerFactory.newTransformer();
			}
		});
	}

	private void initTransformer() {
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, AppConstant.ENCODING.name());
	}

	static XMLExporter newInstance() {
		return new XMLExporter();
	}
}

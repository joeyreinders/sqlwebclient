package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.service.UploadService;
import net.sqlwebclient.upload.UploadType;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

@RequestMapping("/api/upload")
final class FileUploadController extends CrudController {
	private static final long serialVersionUID = -314857454029498288L;
	private final Log logger = LogFactory.getLog(FileUploadController.class);

	private final UploadService uploadService;

	@Inject
	FileUploadController(final UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@Override
	protected CtrlResult post(final RequestContext ctx) {
		final ServletRequestContext requestContext = new ServletRequestContext(ctx.getRequest());
		final ServletFileUpload upload = new ServletFileUpload();
		try {
			final FileItemIterator iter = upload.getItemIterator(requestContext);
			while (iter.hasNext()) {
				final FileItemStream fileItemStream = iter.next();
				if(! "file".equals(fileItemStream.getFieldName())) {
					continue;
				}

				uploadService.upload(fileItemStream.getName(), fileItemStream.openStream(), UploadType.JAR);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ok("ok");
	}
}

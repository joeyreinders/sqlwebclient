package net.sqlwebclient.controller;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import net.sqlwebclient.controller.core.Cacheable;
import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.service.StaticResourceService;
import net.sqlwebclient.util.Builder;
import net.sqlwebclient.util.HttpStatus;
import net.sqlwebclient.util.ServerEnvironment;
import net.sqlwebclient.util.Toolkit;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RequestMapping("/")
@Cacheable
final class StaticPageController extends Controller {
    private static final long serialVersionUID = -5111273349341385349L;
    private static final Log logger = LogFactory.getLog(StaticPageController.class);
    public static final String LOOKUP_RESULT = "lookupResult";

    private final StaticResourceService staticResourceService;

    @Inject
	StaticPageController(final StaticResourceService staticResourceService) {
        this.staticResourceService = staticResourceService;
    }

    @Override
    public void doGet(final RequestContext ctx) throws ServletException, IOException {
        final String path = getPath(ctx);

		final ServerEnvironment environment = ServerEnvironment.get(ctx.getServletContext());
		final URL url = environment.getResourceUrl(path);

		if(url == null) {
			ctx.getResponse().setStatus(HttpStatus.NOT_FOUND.value());
			return;
		}

		final String mimeType = getMimeType(path, ctx);
		final InputStream in = url.openStream();
		Toolkit.copy(in, ctx.getResponse().getOutputStream());
		ctx.getResponse().setContentType(mimeType);
		ctx.getResponse().setStatus(HttpStatus.OK.value());
    }

    @Override
	public void doHead(final RequestContext ctx) throws ServletException, IOException {
        lookup(ctx).respondHead(ctx);
    }

     //TODO find out why it doesn't work
    protected long getLastModified(final HttpServletRequest req) {
        return -1;
        //final RequestContext ctx = RequestContext.fromRequest(req);
        //return lookup(ctx).getLastModified();
    }

    private String getPath(final RequestContext ctx) {
        final String servletPath = ctx.getServletPath();
        final String pathInfo = ctx.getPathInfo() != null && ctx.getPathInfo().length() != 1 ? ctx.getPathInfo() : "/pages/index.html";
        return servletPath + pathInfo;
    }

    private LookupResult lookup(final RequestContext ctx) {
        final Stopwatch stopwatch = new Stopwatch().start();
        logger.debug("--Static Resource - Serving request: " + ctx.getPathInfo() + "---");
        final LookupResult res = getOrLookup(ctx);
        ctx.getRequest().setAttribute(LOOKUP_RESULT, res);
        logger.debug("-- Static Resource - Service done for " + ctx.getPathInfo() +
                " in " + stopwatch.stop().elapsedMillis() + " ms ---");
        return res;
    }

    private LookupResult getOrLookup(final RequestContext ctx) {
        final LookupResult res = (LookupResult) ctx.getRequest().getAttribute(LOOKUP_RESULT);
        return res != null ? res : lookupNoCache(ctx);
    }

    private LookupResult forbidden() {
        return Error.of(HttpStatus.FORBIDDEN);
    }

    private LookupResult lookupNoCache(final RequestContext ctx) {
        final ServerEnvironment environment = ServerEnvironment.get(ctx.getServletContext());
        final String path = getPath(ctx);

        if(staticResourceService.isForbidden(path)) {
            return forbidden();
        }

        final URL url;
        try {
			logger.debug("resource url: " + path);
            url = environment.getResourceUrl(path);
            //url = getServletContext().getResource("/WEB-INF/" + path);
            //Thread.currentThread().getContextClassLoader().getResource("./WEB-INF/" + path)
        } catch (MalformedURLException e) {
			logger.error("No url found for path (bad request): " + path);
			return Error.of(HttpStatus.BAD_REQUEST);
        }
        if (url == null) {
			logger.error("No url found for path: " + path);
            return Error.of(HttpStatus.NOT_FOUND);
        }

        final String mimeType = getMimeType(path, ctx);

        final String realPath = environment.getRealPath(path);
        //final String realPath = getServletContext().getRealPath("/WEB-INF/" + path);
        if (realPath != null) {
            // Try as an ordinary file
            final File f = new File(realPath);
            if(! f.isFile()) {
                return forbidden();
            }
            return builder()
                    .lastModified(f.lastModified())
                    .mimeType(mimeType)
                    .contentLength((int) f.length())
                    .url(url)
                    .build();
        }

        return Error.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected String getMimeType(final String path, final RequestContext ctx) {
		if(path != null && path.endsWith(".js")) {
			return "application/x-javascript";
		}
        return Toolkit.coalesce(ctx.getServletContext().getMimeType(path), "application/octet-stream");
    }

    private static interface LookupResult {
        public void respondGet(final RequestContext ctx) throws IOException;

        public void respondHead(final RequestContext ctx);

        public long getLastModified();
    }

    private static final class Error implements LookupResult {
        private final HttpStatus status;
        private final String message;

        private Error(final HttpStatus status,
                      final String message) {
            this.status = status;
            this.message = message;
        }

        public long getLastModified() {
            return -1;
        }

        public void respondGet(final RequestContext ctx) throws IOException {
            ctx.getResponse().sendError(status.value(), message);
        }

        public void respondHead(final RequestContext ctx) {
            throw new UnsupportedOperationException();
        }

        private static LookupResult of(final HttpStatus status) {
            return new Error(status, status.getReasonPhrase());
        }
    }

    private static StaticFileBuilder builder() {
        return new StaticFileBuilder();
    }

    private static final class StaticFileBuilder implements Builder<LookupResult> {
        private long lastModified;
        private String mimeType;
        private int contentLength;
        private URL url;

        private StaticFileBuilder() {/*noop*/}

        private StaticFileBuilder lastModified(final long lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        private StaticFileBuilder mimeType(final String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        private StaticFileBuilder contentLength(final int contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        private StaticFileBuilder url(final URL url) {
            this.url = url;
            return this;
        }

        @Override
        public LookupResult build() {
            return new StaticFile(this);
        }
    }

    private static final class StaticFile implements LookupResult {
        private final long lastModified;
        private final String mimeType;
        private final int contentLength;
        private final URL url;

        public StaticFile(final StaticFileBuilder builder) {
            this.lastModified = builder.lastModified;
            this.mimeType = builder.mimeType;
            this.contentLength = builder.contentLength;
            this.url = builder.url;
        }

        public long getLastModified() {
            return lastModified;
        }

        protected void setHeaders(final RequestContext ctx) {
            final HttpServletResponse resp = ctx.getResponse();
            resp.setStatus(HttpStatus.OK.value());
            resp.setDateHeader("Expires", System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
            resp.setContentType(mimeType);
            //resp.setContentLength(contentLength);
        }

        @Override
        public void respondGet(final RequestContext ctx) throws IOException {
            setHeaders(ctx);
            final HttpServletResponse resp = ctx.getResponse();
            final OutputStream os = resp.getOutputStream();

            Toolkit.copy(url.openStream(), os);
			logger.debug("*** copied static source to servlet ***");
        }

        @Override
        public void respondHead(final RequestContext ctx) {
            setHeaders(ctx);
        }
    }
}

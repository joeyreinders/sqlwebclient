package net.sqlwebclient.controller;

import com.google.common.io.Closeables;
import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.json.objects.Json;
import net.sqlwebclient.util.HttpStatus;
import net.sqlwebclient.util.JsonPropertyFileReader;
import net.sqlwebclient.util.ServerEnvironment;
import net.sqlwebclient.util.Toolkit;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RequestMapping("/script")
final class CustomJavascriptController extends Controller {
    private static final long serialVersionUID = 417048981136509587L;
	private static final Log logger = LogFactory.getLog(CustomJavascriptController.class);

    @Override
    public void doGet(final RequestContext ctx) throws ServletException, IOException {
        final ServerEnvironment environment = ServerEnvironment.get(ctx.getServletContext());
        final OutputStream out = ctx.getResponse().getOutputStream();
        final JavaScriptFile file = JavaScriptFile.getByRequest(ctx.getId());

		loopScript(out, environment, file);
        finishResponse(ctx);
		Closeables.closeQuietly(out);
    }

    private void loopScript(final OutputStream out,
                            final ServerEnvironment serverEnvironment,
                            final JavaScriptFile javaScriptFile) throws IOException {
        final Script script = loadScript(serverEnvironment, javaScriptFile);
        for(final String fileName: script.getScripts()) {
            copyResource(out, fileName, serverEnvironment, javaScriptFile);
        }
    }

    private void copyResource(final OutputStream out,
                              final String fileName,
                              final ServerEnvironment serverEnvironment,
                              final JavaScriptFile javaScriptFile) throws IOException {
        final String url = javaScriptFile.getResourceUrl(fileName);
        final InputStream in = serverEnvironment.getResourceAsStream(url);
        Toolkit.copy(in, out);
        Closeables.closeQuietly(in);
    }

    private Script loadScript(final ServerEnvironment serverEnvironment, final JavaScriptFile javaScriptFile)
        throws IOException {
        final InputStream is = javaScriptFile.getScriptConfig(serverEnvironment);
        return JsonPropertyFileReader
                .newInstance(is)
                .read(Script.class);
    }

    private void finishResponse(final RequestContext ctx) {
		final HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/x-javascript");
		response.setStatus(HttpStatus.OK.value());
    }

    private static final class Script extends Json {
        private static final long serialVersionUID = -5145298489668828970L;

        private List<String> scripts;

        public List<String> getScripts() {
            return scripts;
        }
    }

    private enum JavaScriptFile {
        CUSTOM(     "custom.js",    "/scripts/"),
        EXTERNAL(   "external.js",  "/static/"),
		;

        private final String requestedPath;
        private final String path;

        JavaScriptFile(final String requestedPath,
                       final String path) {
            this.requestedPath = requestedPath;
            this.path = path;
        }

        InputStream getScriptConfig(final ServerEnvironment environment) throws IOException {
            final String p = getResourceUrl("scripts.json");
            return environment.getResourceAsStream(p);
        }

        private String getResourceUrl(final String fileName) {
            return this.path + fileName;
        }

        public static JavaScriptFile getByRequest(final String path) {
            for(final JavaScriptFile file: values()) {
                if(file.requestedPath.equalsIgnoreCase(path)) {
					logger.debug("js resource: " + file.name());
                    return file;
                }
            }

            throw new IllegalArgumentException("could not find enum with request: " + path);
        }
    }
}

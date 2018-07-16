package net.sqlwebclient.controller;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.controller.core.ControllerException;
import net.sqlwebclient.controller.core.CtrlException;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.AppConstant;
import net.sqlwebclient.core.objects.Settings;
import net.sqlwebclient.exception.OldSqlWebClientException;
import net.sqlwebclient.json.objects.JsonErrorMessage;
import net.sqlwebclient.service.JsonConversionService;
import net.sqlwebclient.service.TranslationService;
import net.sqlwebclient.util.HttpStatus;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class CrudController extends Controller {
    private static final long serialVersionUID = 4556076283024927589L;
    private static final Log logger = LogFactory.getLog(CrudController.class);

    public static final String CONTENT_TYPE = "application/json";
    public static final String CALLBACK_PARAM = "callback";

    @Inject
    private JsonConversionService jsonConversionService;

	@Inject
	private TranslationService translationService;

    protected CtrlResult get(final RequestContext ctx) {
        return throwRequestMethodNotImplemented(ctx);
    }

    protected CtrlResult put(final RequestContext ctx) {
        return throwRequestMethodNotImplemented(ctx);
    }

    protected CtrlResult delete(final RequestContext ctx) {
        return throwRequestMethodNotImplemented(ctx);
    }

    protected CtrlResult post(final RequestContext ctx) {
        return throwRequestMethodNotImplemented(ctx);
    }

    protected CtrlResult all(final RequestContext ctx) {
        return throwRequestMethodNotImplemented(ctx);
    }

    private CtrlResult throwRequestMethodNotImplemented(final RequestContext ctx) {
        throw ControllerException.REQUEST_METHOD_NOT_IMPLEMENTED.throwNewFormatted(ctx.getMethod());
    }

    protected CtrlResult callGet(final RequestContext ctx) {
        if (ctx.getId() != null) {
            return get(ctx);
        } else {
            return all(ctx);
        }
    }

    /*
        Intercept calls
     */

    @Override
    public final void doGet(final RequestContext ctx) throws ServletException, IOException {
        process(ctx, new MethodWrapper() {
            @Override
            public CtrlResult execute(final RequestContext ctx) {
                return callGet(ctx);
            }
        });
    }

    @Override
	public final void doPut(final RequestContext ctx) throws ServletException, IOException {
        process(ctx, new MethodWrapper() {
            @Override
            public CtrlResult execute(final RequestContext ctx) {
                return put(ctx);
            }
        });
    }

    @Override
	public final void doPost(final RequestContext ctx) throws ServletException, IOException {
        process(ctx, new MethodWrapper() {
            @Override
            public CtrlResult execute(final RequestContext ctx) {
                return post(ctx);
            }
        });
    }

    @Override
	public final void doDelete(final RequestContext ctx) throws ServletException, IOException {
        process(ctx, new MethodWrapper() {
            @Override
            public CtrlResult execute(final RequestContext ctx) {
                return delete(ctx);
            }
        });
    }

    /*
            Implementation stuff
    */
    private void process(final RequestContext ctx, final MethodWrapper methodWrapper) throws ServletException, IOException {
        logger.debug("--START-- Processing request");
        final Stopwatch stopwatch = new Stopwatch().start();

        //Build context
        CtrlResult res = null;

        try {
            res = methodWrapper.execute(ctx);
        } catch (NullPointerException npe) {
            res = CtrlResult.newBuilder().addException(ControllerException.INTERNAL_ERROR.throwNew()).addStatus(HttpStatus.NOT_FOUND).build();
        } catch (CtrlException cte) {
            res = CtrlResult.newBuilder().addException(cte).build();
        } catch (OldSqlWebClientException swce) {
            res = CtrlResult.newBuilder().addException(ControllerException.INTERNAL_ERROR.throwNew(swce.getMessage())).build();
            //} catch (TimeoutException timeoutException) {
            //    res = CtrlResult.newBuilder().addException(ControllerException.TIME_OUT.throwNew()).build();
        } catch (Exception ex) {
            res = CtrlResult.newBuilder().addException(ControllerException.INTERNAL_ERROR.throwNew()).build();
        } finally {
            buildResponse(res, ctx);
        }

        stopwatch.stop();
        logger.debug("--STOP-- Request served in : " + stopwatch.elapsedMillis() + " millis");
    }

    //handle : callback=JSON_CALLBACK
    private String generateJsonString(final Object responseObject,
                                      final RequestContext ctx) throws IOException {
        final StringResult res = StringResult.fromBody(jsonConversionService.toJson(responseObject));

        if (ctx.getParam(CALLBACK_PARAM) != null) {
            res.setPrefix(ctx.getParam(CALLBACK_PARAM) + "(");
            res.setSuffix(")");
        }

        return res.toString();
    }

    private void buildResponse(final CtrlResult ctrlResult, final RequestContext ctx) throws IOException {
        final HttpStatus status = ctrlResult.getStatus() == null ? HttpStatus.OK : ctrlResult.getStatus();
        final Object responseObject = ctrlResult.exceptionOccurred() ?
                JsonErrorMessage.valueOf(getMessage(ctrlResult.getException().getMessage()))
				: ctrlResult.getResponseObject();

        final HttpServletResponse response = ctx.getResponse();

        response.setStatus(status.value());
        response.setContentType(CONTENT_TYPE);

        IOUtils.write(	generateJsonString(responseObject, ctx),
						response.getOutputStream(),
						AppConstant.ENCODING);
    }

	protected String getMessage(final String code) {
		return translationService.getTranslation(Settings.LANGUAGE.getValueOrDefault(), code);
	}

	protected JsonConversionService getJsonConversionService() {
		return jsonConversionService;
	}

	/*
			Convenience
		 */
    protected final CtrlResult ok(final Object json) {
        return CtrlResult.newBuilder()
                .addStatus(HttpStatus.OK)
                .addResponseObject(json)
                .build();
    }

    private static final class StringResult {
        private String prefix = "";
        private final String body;
        private String suffix = "";

        private StringResult(final String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return prefix + body + suffix;
        }

        public void setPrefix(final String prefix) {
            this.prefix = prefix;
        }

        public void setSuffix(final String suffix) {
            this.suffix = suffix;
        }

        private static StringResult fromBody(final String body) {
            return new StringResult(body);
        }
    }

    private static interface MethodWrapper {
        CtrlResult execute(final RequestContext ctx);
    }
}

package net.sqlwebclient.servlet;

import com.google.inject.Singleton;
import net.sqlwebclient.controller.core.Cacheable;
import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.controller.manager.ControllerManager;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.controller.request.RequestContextFactory;
import net.sqlwebclient.util.Pair;
import net.sqlwebclient.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
final class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = -1720540110657006512L;

	protected final void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		final Container container = createContainer(req, resp);
		container.getCtrl().doGet(container.getCtx());
	}

	protected final void doHead(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		final Container container = createContainer(req, resp);
		container.getCtrl().doHead(container.getCtx());
	}

	protected final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		final Container container = createContainer(req, resp);
		container.getCtrl().doPost(container.getCtx());
	}

	protected final void doPut(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		final Container container = createContainer(req, resp);
		container.getCtrl().doPut(container.getCtx());
	}

	protected final void doDelete(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		final Container container = createContainer(req, resp);
		container.getCtrl().doDelete(container.getCtx());
	}

	private Container createContainer(final HttpServletRequest request,
									  final HttpServletResponse response) throws IOException {
		final ControllerManager controllerManager =
				ServletUtil.getInjector(request.getServletContext()).getInstance(ControllerManager.class);

		final String path = request.getPathInfo() == null ? "/" : request.getPathInfo();

		final Pair<Controller, String> pair = controllerManager.getController(path);
		final RequestContext ctx = RequestContextFactory.create(request, response, pair.getRight());

		if(pair.getLeft().getClass().getAnnotation(Cacheable.class) != null) {
			ServletUtil.addCachingExpiration(response);
		}

		return new Container(pair.getLeft(), ctx);
	}

	private static final class Container {
		final Controller ctrl;
		final RequestContext ctx;

		private Container(final Controller ctrl, final RequestContext ctx) {
			this.ctrl = ctrl;
			this.ctx = ctx;
		}

		public Controller getCtrl() {
			return ctrl;
		}

		public RequestContext getCtx() {
			return ctx;
		}
	}
}

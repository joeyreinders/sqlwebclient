package net.sqlwebclient.jetty;

import com.google.inject.servlet.GuiceFilter;
import net.sqlwebclient.jetty.lifecycle.JettyLifecycleListener;
import net.sqlwebclient.jetty.lifecycle.JettyLifecycleWrapper;
import net.sqlwebclient.jetty.util.BrowserLauncher;
import net.sqlwebclient.server.StartupConfiguration;
import net.sqlwebclient.util.ServerEnvironment;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import net.sqlwebclient.util.resource.ResourceCache;
import net.sqlwebclient.util.resource.ResourceCacheFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.servlets.MultiPartFilter;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;
import java.util.Properties;


public final class JettyStarter {
	private static final Log logger = LogFactory.getLog(JettyStarter.class);
    public static final int PORT = 8089;

    public static void main(final String[] args) throws Exception {
		final ResourceCache resourceCache = ResourceCacheFactory.create();

		final Properties p = new Properties();
		p.setProperty("org.eclipse.jetty.LEVEL", "WARN");
		org.eclipse.jetty.util.log.StdErrLog.setProperties(p);

		//Default 10 Threads
		final ThreadPool threadPool = new QueuedThreadPool(10, 1);
		final Server server = new Server(threadPool);

		//Connector
		final ServerConnector serverConnector = new ServerConnector(server);
		serverConnector.setPort(PORT);
		server.setConnectors(new Connector[]{serverConnector});

        final ServletContextHandler handler =
                new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS);

        //GuiceConfig.newInstance();

        handler.setResourceBase("./WEB-INF/");

        //Adding all controller
        handler.addFilter(GzipFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
      	handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		handler.addFilter(MultiPartFilter.class, "/api/upload/*", EnumSet.allOf(DispatcherType.class));
        handler.addEventListener(StartupConfiguration.newInstance());
        //handler.addServlet(DefaultServlet.class, "/");
		//handler.addServlet(DispatcherServlet.class, "/*");

        if(! Boolean.getBoolean("dev") &&"kjfaskfjkasjf".hashCode() == 1) {
            server.addLifeCycleListener(
                    JettyLifecycleWrapper.newInstance()
                            .add(JettyLifecycleWrapper.State.STARTED, createBrowserLauncher())
            );
        }

		server.start();

		final ServletContext servletContext = handler.getServletHandler().getServletContext();
		servletContext.setAttribute(ServerEnvironment.SERVLET_CONTEXT_ATTRIBUTE, ServerEnvironment.JETTY);
		servletContext.setAttribute(ResourceCache.class.getName(), resourceCache);

        server.join();
    }

    private static JettyLifecycleListener createBrowserLauncher() {
        return new JettyLifecycleListener() {
            @Override
            public void onState(final JettyLifecycleWrapper.State state) {
                try {
                    BrowserLauncher.newInstance(PORT).launch();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
}

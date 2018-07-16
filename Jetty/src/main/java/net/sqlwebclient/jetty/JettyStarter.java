package net.sqlwebclient.jetty;

import net.sqlwebclient.jetty.custom.JettyLifecycleListener;
import net.sqlwebclient.jetty.custom.JettyLifecycleWrapper;
import net.sqlwebclient.parser.ArgumentContext;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.IOException;
import java.security.ProtectionDomain;

public final class JettyStarter {
    private final int port;
    private final String contextPath;
    private final String workPath;
    private final String secret;
    private final Server srv;
    private final JettyLifecycleWrapper jettyLifecycleWrapper = JettyLifecycleWrapper.newInstance();

    private JettyStarter(final ArgumentContext ctx) {
        port = ctx.getPort();
        contextPath = System.getProperty("jetty.contextPath", "/");
        workPath = System.getProperty("jetty.workDir", null);
        secret = System.getProperty("jetty.secret", "eb27fb2e61ed603363461b3b4e37e0a0");
        srv = new Server();
    }

    public static JettyStarter newInstance(final ArgumentContext ctx) {
        return new JettyStarter(ctx);
    }

    public JettyStarter add(final JettyLifecycleListener listener) {
        jettyLifecycleWrapper.add(listener);
        return this;
    }

    public JettyStarter add(final JettyLifecycleWrapper.State state, final JettyLifecycleListener listener) {
        jettyLifecycleWrapper.add(state, listener);
        return this;
    }

    public JettyStarter start() {
        // Start a Jetty server with some sensible(?) defaults
        try {
            srv.setStopAtShutdown(true);

            // Allow 5 seconds to complete.
            // Adjust this to fit with your own webapp needs.
            // Remove this if you wish to shut down immediately (i.e. kill <pid> or Ctrl+C).
            srv.setGracefulShutdown(5000);

            // Increase thread pool
            final QueuedThreadPool threadPool = new QueuedThreadPool();
            threadPool.setMaxThreads(100);
            srv.setThreadPool(threadPool);

            // Ensure using the non-blocking connector (NIO)
            final Connector connector = new SelectChannelConnector();
            connector.setPort(port);
            connector.setMaxIdleTime(30000);
            srv.setConnectors(new Connector[]{connector});

            // Get the war-file
            final ProtectionDomain protectionDomain = JettyStarter.class.getProtectionDomain();
            String warFile = protectionDomain.getCodeSource().getLocation().toExternalForm();
            String currentDir = new File(protectionDomain.getCodeSource().getLocation().getPath()).getParent();

            // Handle signout/signin in BigIP-cluster

            // Add the warFile (this jar)
            final WebAppContext context = new WebAppContext(warFile, contextPath);
            context.setServer(srv);
            resetTempDirectory(context, currentDir);

            // Add the handlers
            final HandlerList handlers = new HandlerList();
            handlers.addHandler(context);
            handlers.addHandler(new ShutdownHandler(srv, context, secret));
            handlers.addHandler(new BigIPNodeHandler(secret));
            srv.setHandler(handlers);

            srv.addLifeCycleListener(jettyLifecycleWrapper);

            srv.start();
            srv.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public JettyStarter stop() throws Exception {
        System.out.println(ShutdownHandler.shutdown(port, secret));
        srv.stop();
        return this;
    }

    private void status() {
        System.out.println(BigIPNodeHandler.check(port));
    }

    private void online() {
        System.out.println(BigIPNodeHandler.online(port, secret));
    }

    private void offline() {
        System.out.println(BigIPNodeHandler.offline(port, secret));
    }

    private void usage() {
        System.out.println("Usage: java -jar <file.jar> [start|stop|status|enable|disable]\n\t" +
                "start    Start the server (default)\n\t" +
                "stop     Stop the server gracefully\n\t" +
                "status   Check the current server status\n\t" +
                "online   Sign in to BigIP load balancer\n\t" +
                "offline  Sign out from BigIP load balancer\n"
        );
        System.exit(-1);
    }

    private void resetTempDirectory(WebAppContext context, String currentDir) throws IOException {
        final File workDir;
        if (workPath != null) {
            workDir = new File(workPath);
        } else {
            workDir = new File(currentDir, "work");
        }
        FileUtils.deleteDirectory(workDir);
        context.setTempDirectory(workDir);
    }
}

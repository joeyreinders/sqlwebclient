package net.sqlwebclient.util;

import com.google.common.base.Throwables;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import net.sqlwebclient.util.resource.ResourceCache;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class ServerEnvironment {
	private static final Log logger = LogFactory.getLog(ServerEnvironment.class);

    public static final String SERVLET_CONTEXT_ATTRIBUTE = "ServletEnvironmentContextVariable";
    public static final String JETTY = "JETTY";
    private static final String CACHED_SERVER_ENVIRONMENT = "cached_server_environment";

    final ServletContext servletContext;

    private ServerEnvironment(final ServletContext context){
        this.servletContext = context;
    }

    public abstract URL getResourceUrl(final String path) throws MalformedURLException;

    public abstract String getRealPath(final String path);

	public abstract InputStream getResourceAsStream(final String path);

    public static ServerEnvironment get(final ServletContext servletContext) {
        final ServerEnvironment cached = retrieveCached(servletContext);
        if(cached != null){
            return cached;
        }

        final String attribute = (String) servletContext.getAttribute(SERVLET_CONTEXT_ATTRIBUTE);
        final ServerEnvironment res;
        if(attribute == null || attribute.isEmpty()) {
            res = new NotEmbeddedServer(servletContext);
        } else if(JETTY.equals(attribute)) {
            res = new JettyServer(servletContext);
        } else {
            throw new RuntimeException("Wrong server environment");
        }

		logger.debug("ServletEnvironment: " + res.getClass().getName());
        servletContext.setAttribute(CACHED_SERVER_ENVIRONMENT, res);
        return res;
    }

    private static ServerEnvironment retrieveCached(final ServletContext servletContext) {
        final ServerEnvironment attribute = (ServerEnvironment) servletContext.getAttribute(CACHED_SERVER_ENVIRONMENT);
        return attribute != null ? attribute : null;
    }

    private static final class JettyServer extends ServerEnvironment {
        private JettyServer(final ServletContext context) {
            super(context);
        }

        @Override
        public URL getResourceUrl(final String path) throws MalformedURLException {
            return getResourceCache().getResource("/WEB-INF" + path);
        }

        @Override
        public String getRealPath(final String path) {
			return getResourceCache().getResource("/WEB-INF" + path).getPath();
		}

		@Override
		public InputStream getResourceAsStream(final String path) {
			try {
				return getResourceCache().getResource("/WEB-INF" + path).openStream();
			} catch (Exception ex) {
				throw Throwables.propagate(ex);
			}
		}

		private ResourceCache getResourceCache() {
			return (ResourceCache) servletContext.getAttribute(ResourceCache.class.getName());
		}
	}

    private static final class NotEmbeddedServer extends ServerEnvironment {
        private NotEmbeddedServer(final ServletContext context) {
            super(context);
        }

        @Override
        public URL getResourceUrl(final String path) throws MalformedURLException {
            return servletContext.getResource(path);
        }

        @Override
        public String getRealPath(final String path) {
            return servletContext.getRealPath(path);
        }

		@Override
		public InputStream getResourceAsStream(final String path) {
			return servletContext.getResourceAsStream(path);
		}
	}
}

package net.sqlwebclient.util.resource;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

final class DefaultResourceCache implements ResourceCache {
	private static final Log logger = LogFactory.getLog(ResourceCache.class);

	private final Map<String, URL> resources;

	DefaultResourceCache(final Collection<String> paths) {
		final ImmutableMap.Builder<String, URL> mapBuilder = ImmutableMap.builder();
		loadResources(paths, mapBuilder);
		resources = mapBuilder.build();
	}

	private void loadResources(final Collection<String> paths,
							   final ImmutableMap.Builder<String, URL> mapBuilder) {
		final Stopwatch stopwatch = new Stopwatch().start();
		logger.debug("Start caching resources");
		for(String path: paths) {
			loadResource(path, mapBuilder);
		}
		stopwatch.stop();
		logger.debug("done caching resources, duration: " + stopwatch.toString() + "ms");
	}

	private void loadResource(final String path, final ImmutableMap.Builder<String, URL> mapBuilder) {
		logger.debug("loading resource for path: " + path);
		final URL url = ClassLoader.getSystemResource(path);
		logger.debug("resource found for path " + path + " ? " + (url !=null));
		if(url != null) {
			mapBuilder.put("/" + path, url);
		}
	}

	@Override
	public URL getResource(final String path) {
		logger.debug("looking for resource of path: " + path);
		final URL res = resources.get(path);
		logger.debug("found resource for path: " + path + " ? " + (res != null));
		return res;
	}
}

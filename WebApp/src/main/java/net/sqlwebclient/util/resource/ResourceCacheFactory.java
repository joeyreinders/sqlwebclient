package net.sqlwebclient.util.resource;

import com.google.common.collect.ImmutableSet;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.regex.Pattern;

public final class ResourceCacheFactory {
	public static ResourceCache create() {
		final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

		final Reflections reflections = prepareReflections();
		//html
		builder.addAll(reflections.getResources(Pattern.compile(".*\\.html")))
				.addAll(reflections.getResources(Pattern.compile(".*\\.js")))
				.addAll(reflections.getResources(Pattern.compile(".*\\.css")))
				.addAll(reflections.getResources(Pattern.compile(".*\\.json")));

		return new DefaultResourceCache(builder.build());
	}

	private static Reflections prepareReflections() {
		return new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage("net.sqlwebclient"))
				.setScanners(new ResourcesScanner(),
						new TypeAnnotationsScanner(),
						new SubTypesScanner()));

	}
}

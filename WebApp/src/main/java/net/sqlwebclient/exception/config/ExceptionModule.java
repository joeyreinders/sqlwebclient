package net.sqlwebclient.exception.config;

import com.google.inject.AbstractModule;
import net.sqlwebclient.exception.translation.ExceptionResolver;
import net.sqlwebclient.util.ClassUtil;

public final class ExceptionModule extends AbstractModule {
	private ExceptionModule() {
		//No interface
	}

	@Override
	protected void configure() {
		final Class<? extends ExceptionResolver> implClass =
				ClassUtil.forName("net.sqlwebclient.exception.translation.DefaultExceptionResolver");
		bind(ExceptionResolver.class).to(implClass).asEagerSingleton();
	}

	public static ExceptionModule newInstance() {
		return new ExceptionModule();
	}
}

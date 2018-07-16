package net.sqlwebclient.util;

import java.lang.reflect.Constructor;
import java.util.Objects;

public final class ClassUtil {
	private ClassUtil() {
		throw new AssertionError();
	}

	public static boolean isClassPresentOnClassPath(final String className) {
		return forName(className) != null;
	}

	public static <T> Class<T> forName(final String className) {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			return Toolkit.wrapExceptionMethod(new Toolkit.ExceptionMethod() {
				@Override
				public Object execute() throws Exception {
					return classLoader.loadClass(className);
				}
			});
		} catch (Exception exception) {
			return null;
		}
	}

	public static Object createClassInstance(final String className, final Object[] params) {
		final Class<?> cls = forName(className);
		Objects.requireNonNull(cls);

		try {
			final Class[] types = new Class[params.length];
			for(int i = 0 ; i < params.length; i ++) {
				types[i] = params[i].getClass();
			}

			final Constructor<?> constructor = cls.getDeclaredConstructor(types);
			constructor.setAccessible(true);
			return constructor.newInstance(params);
		} catch (Exception ex) {
			return null;
		}
	}}

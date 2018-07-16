package net.sqlwebclient.controller.manager;

import com.google.common.collect.Maps;
import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.controller.core.ControllerException;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.util.Pair;

import java.util.Map;

public final class DefaultControllerManager implements ControllerManager {
	private final Injector injector;
	private final Map<ControllerMatcher, Controller> controllers = Maps.newLinkedHashMap();

	@Inject
	DefaultControllerManager(final Injector injector) {
		this.injector = injector;
	}

	@Override
	public Pair<Controller, String> getController(final String requestPath) {
		checkInitialization();

		return selectController(requestPath);
	}

	private Pair<Controller, String> selectController(final String requestPath) {
		for (Map.Entry<ControllerMatcher, Controller> entry : controllers.entrySet()) {
			if (entry.getKey().matches(requestPath)) {
				return Pair.newInstance(entry.getValue(), entry.getKey().getId(requestPath));
			}
		}

		throw ControllerException.CONTROLLER_NOT_FOUND.throwNew();
	}

	private void checkInitialization() {
		if (controllers.isEmpty()) {
			initializeMappings();
		}
	}

	private void initializeMappings() {
		final Map<Key<?>, Binding<?>> bindings = injector.getBindings();
		for (Binding<?> binding : bindings.values()) {
			if (Controller.class.isAssignableFrom(binding.getKey().getTypeLiteral().getRawType())) {
				final Object instance = binding.getProvider().get();
				register((Controller) instance);
			}
		}
	}

	private void register(final Controller controller) {
		final Class<?> cls = controller.getClass();
		final String path = cls.getAnnotation(RequestMapping.class).value();

		controllers.put(new DefaultMatcher(path), controller);
	}
}

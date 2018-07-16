package net.sqlwebclient.controller.configuration;

import com.google.inject.Singleton;
import net.sqlwebclient.controller.manager.ControllerManager;
import net.sqlwebclient.controller.manager.DefaultControllerManager;
import net.sqlwebclient.guice.GuiceModule;
import net.sqlwebclient.util.ClassUtil;
import net.sqlwebclient.util.JsonPropertyFileReader;

import java.io.InputStream;

public final class ControllerModule extends GuiceModule {
	private ControllerModule() {
		//noop
	}

	@Override
	protected void doConfigure() {
		bind(ControllerManager.class).to(DefaultControllerManager.class).in(Singleton.class);
		bindControllers();
	}

	@Override
	protected String getLogDescription() {
		return "Controllers";
	}

	private void bindControllers() {
		final Controllers ctrls = loadConfiguration();
		for(String clsName: ctrls.controllers) {
			bind(ClassUtil.forName(clsName)).in(Singleton.class);
		}
	}

	private Controllers loadConfiguration() {
		final InputStream is = getClass().getResourceAsStream("controllers.json");
		return JsonPropertyFileReader.newInstance(is).read(Controllers.class);
	}

	public static ControllerModule newInstance() {
		return new ControllerModule();
	}

	private static final class Controllers {
		private String[] controllers;
	}
}

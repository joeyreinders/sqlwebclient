package net.sqlwebclient.controller.manager;

import net.sqlwebclient.controller.core.Controller;
import net.sqlwebclient.util.Pair;

public interface ControllerManager {
	Pair<Controller, String> getController(final String requestPath);
}

package net.sqlwebclient.jetty.lifecycle;

import java.util.EventListener;

public interface JettyLifecycleListener extends EventListener {
    void onState(JettyLifecycleWrapper.State state);
}

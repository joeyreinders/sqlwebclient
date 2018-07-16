package net.sqlwebclient.jetty.custom;

import java.util.EventListener;

public interface JettyLifecycleListener extends EventListener {
    void onState(JettyLifecycleWrapper.State state);
}

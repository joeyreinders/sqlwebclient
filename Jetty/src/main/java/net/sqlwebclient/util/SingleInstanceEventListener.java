package net.sqlwebclient.util;

import java.util.EventListener;

interface SingleInstanceEventListener extends EventListener {
    void onEvent(final SingleInstanceEvent event);
}

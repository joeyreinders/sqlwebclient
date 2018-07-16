package net.sqlwebclient.jetty.util;

import java.awt.*;
import java.net.URL;

public final class BrowserLauncher {
    private final int port;

    private BrowserLauncher(final int port) {
        this.port = port;
    }

    public void launch() throws Exception {
        if(! isBrowsingSupported()) {
            System.out.print("Browsing is not supported on this platform");
            return;
        }

        final String url = "http://localhost:" + port;
        Desktop.getDesktop().browse(new URL(url).toURI());
    }

    private boolean isBrowsingSupported() {
        return Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
    }

    public static BrowserLauncher newInstance(final int port) {
        return new BrowserLauncher(port);
    }
}

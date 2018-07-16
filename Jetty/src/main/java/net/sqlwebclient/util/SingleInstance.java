package net.sqlwebclient.util;

import net.sqlwebclient.parser.ArgumentContext;

import java.lang.management.ManagementFactory;

public final class SingleInstance {


    private static long getPID() {
        final String processName = ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(processName.split("@")[0]);
    }

    public static SingleInstance newInstance(final ArgumentContext ctx) {
        return null;
    }

    public boolean alreadyRunning() {
        return false;
    }
}

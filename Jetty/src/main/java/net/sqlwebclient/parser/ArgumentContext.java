package net.sqlwebclient.parser;

import net.sourceforge.argparse4j.annotation.Arg;

public final class ArgumentContext {
    @Arg(dest = "port")
    private int port;

    public int getPort() {
        return port;
    }

    static ArgumentContext newInstance() {
        return new ArgumentContext();
    }
}

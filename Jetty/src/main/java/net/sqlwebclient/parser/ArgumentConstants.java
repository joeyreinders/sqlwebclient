package net.sqlwebclient.parser;

import java.util.HashMap;
import java.util.Map;

public final class ArgumentConstants {
    public static final String APPLICATION_NAME = "SQL Web Client";
    public static final String PORT = "port";

    private ArgumentConstants() {
        throw new Error();
    }

    public static Map<String, Object> getDefaults() {
        final Map<String, Object> res = new HashMap<String, Object>();

        res.put(PORT, 8089);

        return res;
    }
}

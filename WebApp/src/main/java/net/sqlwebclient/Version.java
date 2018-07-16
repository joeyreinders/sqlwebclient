package net.sqlwebclient;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public final class Version {
    public static final String VERSION = "0.1.0";

    private Version() {
        //No instance
    }

    /*
        External libraries
     */
    public static Map<String, String> getExternalLibraries() {
        return ImmutableMap.<String, String>builder()
                .put("Gson", "http://code.google.com/p/google-gson")
                .put("Guava", "http://code.google.com/p/guava-libraries")
                .put("Log4J", "http://logging.apache.org/log4j")
                .build();
    }
}

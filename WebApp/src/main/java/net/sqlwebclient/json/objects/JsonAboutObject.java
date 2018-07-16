package net.sqlwebclient.json.objects;

import net.sqlwebclient.core.objects.AboutObject;

import java.util.Map;

public final class JsonAboutObject extends Json {
    private static final long serialVersionUID = -3887562371545632465L;

    private final String version;
    private final Map<String, String> externalLibraries;

    private JsonAboutObject(final String version,
                           final Map<String, String> externalLibraries) {
        this.version = version;
        this.externalLibraries = externalLibraries;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getExternalLibraries() {
        return externalLibraries;
    }

    public static JsonAboutObject of(final AboutObject aboutObject) {
        return new JsonAboutObject(aboutObject.getVersion(), aboutObject.getExternalLibraries());
    }
}

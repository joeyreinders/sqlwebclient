package net.sqlwebclient.core.objects;

import java.util.Map;

public final class AboutObject {
    private final String version;
    private final Map<String, String> externalLibraries;

    private AboutObject(final String version,
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

    public static AboutObject of(final String version,
								 final Map<String, String> externalLibraries) {
        return new AboutObject(version, externalLibraries);
    }
}

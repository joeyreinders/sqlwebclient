package net.sqlwebclient.service.impl;

import net.sqlwebclient.service.StaticResourceService;
import org.apache.logging.log4j.core.helpers.Strings;

final class DefaultStaticResourceService implements StaticResourceService {
    DefaultStaticResourceService() {
        //Noop
    }

    @Override
    public boolean isForbidden(final String path) {
        final String lPath = Strings.isNotEmpty(path) ? path.toLowerCase() : "";
        return lPath.startsWith("/web-inf/") || lPath.startsWith("/meta-inf/");
    }

    @Override
    public boolean deflatable(final String mimeType) {
        return mimeType.startsWith("text/")
                || mimeType.equals("application/postscript")
                || mimeType.startsWith("application/ms")
                || mimeType.startsWith("application/vnd")
                || mimeType.endsWith("xml")
                || mimeType.endsWith("application/javascript");
    }
}

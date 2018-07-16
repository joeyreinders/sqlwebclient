package net.sqlwebclient.service;

public interface StaticResourceService {
    boolean isForbidden(final String path);


    boolean deflatable(final String mimeType);
}

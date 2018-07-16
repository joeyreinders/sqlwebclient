package net.sqlwebclient.service.impl;

import net.sqlwebclient.Version;
import net.sqlwebclient.core.objects.AboutObject;
import net.sqlwebclient.service.AboutService;

final class DefaultAboutService implements AboutService {
    @Override
    public AboutObject create() {
        return AboutObject.of(Version.VERSION, Version.getExternalLibraries());
    }
}

package net.sqlwebclient.service.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import net.sqlwebclient.util.ClassUtil;
import net.sqlwebclient.util.JsonPropertyFileReader;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;

import java.lang.annotation.Annotation;

public final class ServiceModule extends AbstractModule {
    private static final Log log = LogFactory.getLog(ServiceModule.class);

    private ServiceModule() {
        //Noop
    }

    @Override
    protected void configure() {
        log.info("-- Start loading services --");

        final JsonServiceConfiguration configuration = loadConfiguration();
        loopAndWire(configuration);

        log.info("-- Done loading services --");
    }

    private void loopAndWire(final JsonServiceConfiguration configuration) {
        for(JsonServiceConfig config: configuration.getConfigs()) {
            wire(config);
        }
    }

    private void wire(final JsonServiceConfig config) {
        final Class<Object> ifClass = loadClass(config.getInterfaceClass(), "interface");
        final Class<Object> implClass = loadClass(config.getImplementationClass(), "implementation");

        if(! ifClass.isAssignableFrom(implClass)) {
            final String msg = implClass.getName() + " does not implement or extend from " + ifClass.getName();
            log.fatal(msg);
            addError(msg);
        }

        final Class<? extends Annotation> scope = loadScopeAnnotation(config.getScope());

        bind(ifClass).to(implClass).in(scope);
    }

    private <T> Class<T> loadClass(final String className, final String type) {
        final Class<T> cls = ClassUtil.forName(className);
        if(cls == null) {
            final String msg = "No class found for " + type + " with classname: " + className;
            log.fatal(msg);
            addError(msg);
        }

        return cls;
    }

    private Class<? extends Annotation> loadScopeAnnotation(final String scope) {
        if("singleton".equalsIgnoreCase(scope)) {
            return Singleton.class;
        } else {
            addError("Scope: '" + scope + "' not yet supported");
            return null;
        }
    }

    private JsonServiceConfiguration loadConfiguration() {
        return JsonPropertyFileReader
                .newInstance(getClass().getResourceAsStream("Services.json"))
                .read(JsonServiceConfiguration.class);
    }

    public static ServiceModule newInstance() {
        return new ServiceModule();
    }
}

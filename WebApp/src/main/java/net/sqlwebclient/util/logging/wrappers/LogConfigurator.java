package net.sqlwebclient.util.logging.wrappers;

import net.sqlwebclient.util.ClassUtil;
import net.sqlwebclient.util.logging.SupportedLogFramework;
import org.apache.log4j.BasicConfigurator;

public final class LogConfigurator {
    static final SupportedLogFramework SUPPORTED_LOG_FRAMEWORK = detectPresentFramework();

    private LogConfigurator() {
        //Noop
    }

    public static void configure() {
        BasicConfigurator.configure();
    }

    private static SupportedLogFramework detectPresentFramework(){
        for(SupportedLogFramework frameWork: SupportedLogFramework.values()) {
            if(ClassUtil.isClassPresentOnClassPath(frameWork.getClassPathName())) {
                return frameWork;
            }
        }

        return SupportedLogFramework.JDK_DEFAULT;
    }
}

package net.sqlwebclient.util.logging;

import net.sqlwebclient.util.ClassUtil;

public enum SupportedLogFramework {
    LOG_4_J("org.apache.commons.logging.impl.Log4JLogger", "net.sqlwebclient.util.logging.wrappers.Log4JLogWrapper"),
    JDK_DEFAULT("org.apache.commons.logging.impl.Jdk14Logger", "net.sqlwebclient.util.logging.wrappers.JdkLogWrapper"),
    SIMPLE_LOGGING("org.apache.commons.logging.impl.SimpleLog", "");

    private final String classPath;
    private final String wrapperClass;

    SupportedLogFramework(final String classPath,
                          final String wrapperClass) {
        this.classPath = classPath;
        this.wrapperClass = wrapperClass;
    }

    public String getClassPathName() {
        return classPath;
    }

    public Log createLog(final String className) {
        final Log log = (Log) ClassUtil.createClassInstance(this.wrapperClass, new Object[] {className});
        //If no log then always return the JDK logger
        return (log == null && this != JDK_DEFAULT) ? JDK_DEFAULT.createLog(className) : log;
    }
}

package common;

import common.log.LoggerName;
import common.log.SimpleLogger;
import java.security.AccessControlException;

public abstract class Logger {
    private static Logger logger = null;

    public abstract void debug(Object obj);

    public abstract void debug(Object obj, Throwable th);

    public abstract void error(Object obj);

    public abstract void error(Object obj, Throwable th);

    public abstract void fatal(Object obj);

    public abstract void fatal(Object obj, Throwable th);

    /* access modifiers changed from: protected */
    public abstract Logger getLoggerImpl(Class cls);

    public abstract void info(Object obj);

    public abstract void info(Object obj, Throwable th);

    public abstract void warn(Object obj);

    public abstract void warn(Object obj, Throwable th);

    public static final Logger getLogger(Class cl) {
        if (logger == null) {
            initializeLogger();
        }
        return logger.getLoggerImpl(cl);
    }

    private static synchronized void initializeLogger() {
        synchronized (Logger.class) {
            if (logger == null) {
                String loggerName = LoggerName.NAME;
                try {
                    String loggerName2 = System.getProperty("logger");
                    if (loggerName2 == null) {
                        loggerName2 = LoggerName.NAME;
                    }
                    logger = (Logger) Class.forName(loggerName2).newInstance();
                } catch (IllegalAccessException e) {
                    logger = new SimpleLogger();
                    logger.warn(new StringBuffer().append("Could not instantiate logger ").append(loggerName).append(" using default").toString());
                } catch (InstantiationException e2) {
                    logger = new SimpleLogger();
                    logger.warn(new StringBuffer().append("Could not instantiate logger ").append(loggerName).append(" using default").toString());
                } catch (AccessControlException e3) {
                    logger = new SimpleLogger();
                    logger.warn(new StringBuffer().append("Could not instantiate logger ").append(loggerName).append(" using default").toString());
                } catch (ClassNotFoundException e4) {
                    logger = new SimpleLogger();
                    logger.warn(new StringBuffer().append("Could not instantiate logger ").append(loggerName).append(" using default").toString());
                }
            }
        }
        return;
    }

    protected Logger() {
    }

    public void setSuppressWarnings(boolean w) {
    }
}

package common.log;

import common.Logger;

public class SimpleLogger extends Logger {
    private boolean suppressWarnings = false;

    public void debug(Object message) {
        if (!this.suppressWarnings) {
            System.out.print("Debug: ");
            System.out.println(message);
        }
    }

    public void debug(Object message, Throwable t) {
        if (!this.suppressWarnings) {
            System.out.print("Debug: ");
            System.out.println(message);
            t.printStackTrace();
        }
    }

    public void error(Object message) {
        System.err.print("Error: ");
        System.err.println(message);
    }

    public void error(Object message, Throwable t) {
        System.err.print("Error: ");
        System.err.println(message);
        t.printStackTrace();
    }

    public void fatal(Object message) {
        System.err.print("Fatal: ");
        System.err.println(message);
    }

    public void fatal(Object message, Throwable t) {
        System.err.print("Fatal:  ");
        System.err.println(message);
        t.printStackTrace();
    }

    public void info(Object message) {
        if (!this.suppressWarnings) {
            System.out.println(message);
        }
    }

    public void info(Object message, Throwable t) {
        if (!this.suppressWarnings) {
            System.out.println(message);
            t.printStackTrace();
        }
    }

    public void warn(Object message) {
        if (!this.suppressWarnings) {
            System.err.print("Warning:  ");
            System.err.println(message);
        }
    }

    public void warn(Object message, Throwable t) {
        if (!this.suppressWarnings) {
            System.err.print("Warning:  ");
            System.err.println(message);
            t.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public Logger getLoggerImpl(Class c) {
        return this;
    }

    public void setSuppressWarnings(boolean w) {
        this.suppressWarnings = w;
    }
}

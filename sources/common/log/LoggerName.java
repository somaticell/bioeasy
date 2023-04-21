package common.log;

public class LoggerName {
    public static final String NAME;
    static Class class$common$log$SimpleLogger;

    static {
        Class cls;
        if (class$common$log$SimpleLogger == null) {
            cls = class$("common.log.SimpleLogger");
            class$common$log$SimpleLogger = cls;
        } else {
            cls = class$common$log$SimpleLogger;
        }
        NAME = cls.getName();
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

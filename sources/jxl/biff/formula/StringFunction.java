package jxl.biff.formula;

import common.Logger;
import jxl.WorkbookSettings;

class StringFunction extends StringParseItem {
    static Class class$jxl$biff$formula$StringFunction;
    private static Logger logger;
    private Function function;
    private String functionString;

    static {
        Class cls;
        if (class$jxl$biff$formula$StringFunction == null) {
            cls = class$("jxl.biff.formula.StringFunction");
            class$jxl$biff$formula$StringFunction = cls;
        } else {
            cls = class$jxl$biff$formula$StringFunction;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    StringFunction(String s) {
        this.functionString = s.substring(0, s.length() - 1);
    }

    /* access modifiers changed from: package-private */
    public Function getFunction(WorkbookSettings ws) {
        if (this.function == null) {
            this.function = Function.getFunction(this.functionString, ws);
        }
        return this.function;
    }
}

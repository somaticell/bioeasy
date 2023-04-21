package jxl.biff.formula;

import common.Logger;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class FunctionNames {
    static Class class$jxl$biff$formula$FunctionNames;
    private static Logger logger;
    private HashMap functions = new HashMap(Function.functions.length);
    private HashMap names = new HashMap(Function.functions.length);

    static {
        Class cls;
        if (class$jxl$biff$formula$FunctionNames == null) {
            cls = class$("jxl.biff.formula.FunctionNames");
            class$jxl$biff$formula$FunctionNames = cls;
        } else {
            cls = class$jxl$biff$formula$FunctionNames;
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

    public FunctionNames(Locale l) {
        ResourceBundle rb = ResourceBundle.getBundle("functions", l);
        for (Function f : Function.functions) {
            String propname = f.getPropertyName();
            String n = propname.length() != 0 ? rb.getString(propname) : null;
            if (n != null) {
                this.names.put(f, n);
                this.functions.put(n, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Function getFunction(String s) {
        return (Function) this.functions.get(s);
    }

    /* access modifiers changed from: package-private */
    public String getName(Function f) {
        return (String) this.names.get(f);
    }
}

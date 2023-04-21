package jxl.biff.formula;

import common.Logger;
import jxl.biff.DoubleHelper;

class DoubleValue extends NumberValue implements ParsedThing {
    static Class class$jxl$biff$formula$DoubleValue;
    private static Logger logger;
    private double value;

    static {
        Class cls;
        if (class$jxl$biff$formula$DoubleValue == null) {
            cls = class$("jxl.biff.formula.DoubleValue");
            class$jxl$biff$formula$DoubleValue = cls;
        } else {
            cls = class$jxl$biff$formula$DoubleValue;
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

    public DoubleValue() {
    }

    DoubleValue(double v) {
        this.value = v;
    }

    public DoubleValue(String s) {
        try {
            this.value = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            logger.warn(e, e);
            this.value = 0.0d;
        }
    }

    public int read(byte[] data, int pos) {
        this.value = DoubleHelper.getIEEEDouble(data, pos);
        return 8;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[9];
        data[0] = Token.DOUBLE.getCode();
        DoubleHelper.getIEEEBytes(this.value, data, 1);
        return data;
    }

    public double getValue() {
        return this.value;
    }
}

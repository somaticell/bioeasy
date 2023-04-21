package jxl.biff.formula;

import common.Logger;
import jxl.biff.IntegerHelper;

class IntegerValue extends NumberValue implements ParsedThing {
    static Class class$jxl$biff$formula$IntegerValue;
    private static Logger logger;
    private boolean outOfRange;
    private double value;

    static {
        Class cls;
        if (class$jxl$biff$formula$IntegerValue == null) {
            cls = class$("jxl.biff.formula.IntegerValue");
            class$jxl$biff$formula$IntegerValue = cls;
        } else {
            cls = class$jxl$biff$formula$IntegerValue;
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

    public IntegerValue() {
        this.outOfRange = false;
    }

    public IntegerValue(String s) {
        try {
            this.value = (double) Integer.parseInt(s);
        } catch (NumberFormatException e) {
            logger.warn(e, e);
            this.value = 0.0d;
        }
        this.outOfRange = this.value != ((double) ((short) ((int) this.value)));
    }

    public int read(byte[] data, int pos) {
        this.value = (double) IntegerHelper.getInt(data[pos], data[pos + 1]);
        return 2;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[3];
        data[0] = Token.INTEGER.getCode();
        IntegerHelper.getTwoBytes((int) this.value, data, 1);
        return data;
    }

    public double getValue() {
        return this.value;
    }

    /* access modifiers changed from: package-private */
    public boolean isOutOfRange() {
        return this.outOfRange;
    }
}

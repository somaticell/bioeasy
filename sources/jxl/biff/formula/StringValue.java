package jxl.biff.formula;

import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;

class StringValue extends Operand implements ParsedThing {
    static Class class$jxl$biff$formula$StringValue;
    private static final Logger logger;
    private WorkbookSettings settings;
    private String value;

    static {
        Class cls;
        if (class$jxl$biff$formula$StringValue == null) {
            cls = class$("jxl.biff.formula.StringValue");
            class$jxl$biff$formula$StringValue = cls;
        } else {
            cls = class$jxl$biff$formula$StringValue;
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

    public StringValue(WorkbookSettings ws) {
        this.settings = ws;
    }

    public StringValue(String s) {
        this.value = s;
    }

    public int read(byte[] data, int pos) {
        int length = IntegerHelper.getInt(data[pos], data[pos + 1]);
        if ((data[pos + 1] & 1) == 0) {
            this.value = StringHelper.getString(data, length, pos + 2, this.settings);
            return 2 + length;
        }
        this.value = StringHelper.getUnicodeString(data, length, pos + 2);
        return 2 + (length * 2);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[((this.value.length() * 2) + 3)];
        data[0] = Token.STRING.getCode();
        data[1] = (byte) this.value.length();
        data[2] = 1;
        StringHelper.getUnicodeBytes(this.value, data, 3);
        return data;
    }

    public void getString(StringBuffer buf) {
        buf.append("\"");
        buf.append(this.value);
        buf.append("\"");
    }
}

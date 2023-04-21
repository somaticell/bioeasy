package jxl.biff.drawing;

import common.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;

class Opt extends EscherAtom {
    static Class class$jxl$biff$drawing$Opt;
    private static Logger logger;
    private byte[] data;
    private int numProperties;
    private ArrayList properties;

    static {
        Class cls;
        if (class$jxl$biff$drawing$Opt == null) {
            cls = class$("jxl.biff.drawing.Opt");
            class$jxl$biff$drawing$Opt = cls;
        } else {
            cls = class$jxl$biff$drawing$Opt;
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

    static final class Property {
        boolean blipId;
        boolean complex;
        int id;
        String stringValue;
        int value;

        public Property(int i, boolean bl, boolean co, int v) {
            this.id = i;
            this.blipId = bl;
            this.complex = co;
            this.value = v;
        }

        public Property(int i, boolean bl, boolean co, int v, String s) {
            this.id = i;
            this.blipId = bl;
            this.complex = co;
            this.value = v;
            this.stringValue = s;
        }
    }

    public Opt(EscherRecordData erd) {
        super(erd);
        this.numProperties = getInstance();
        readProperties();
    }

    private void readProperties() {
        boolean z;
        this.properties = new ArrayList();
        int pos = 0;
        byte[] bytes = getBytes();
        for (int i = 0; i < this.numProperties; i++) {
            int val = IntegerHelper.getInt(bytes[pos], bytes[pos + 1]);
            int id = val & 16383;
            int value = IntegerHelper.getInt(bytes[pos + 2], bytes[pos + 3], bytes[pos + 4], bytes[pos + 5]);
            if ((val & 16384) != 0) {
                z = true;
            } else {
                z = false;
            }
            pos += 6;
            this.properties.add(new Property(id, z, (32768 & val) != 0, value));
        }
        Iterator i2 = this.properties.iterator();
        while (i2.hasNext()) {
            Property p = (Property) i2.next();
            if (p.complex) {
                p.stringValue = StringHelper.getUnicodeString(bytes, p.value / 2, pos);
                pos += p.value;
            }
        }
    }

    public Opt() {
        super(EscherRecordType.OPT);
        this.properties = new ArrayList();
        setVersion(3);
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        this.numProperties = this.properties.size();
        setInstance(this.numProperties);
        this.data = new byte[(this.numProperties * 6)];
        int pos = 0;
        Iterator i = this.properties.iterator();
        while (i.hasNext()) {
            Property p = (Property) i.next();
            int val = p.id & 16383;
            if (p.blipId) {
                val |= 16384;
            }
            if (p.complex) {
                val |= 32768;
            }
            IntegerHelper.getTwoBytes(val, this.data, pos);
            IntegerHelper.getFourBytes(p.value, this.data, pos + 2);
            pos += 6;
        }
        Iterator i2 = this.properties.iterator();
        while (i2.hasNext()) {
            Property p2 = (Property) i2.next();
            if (p2.complex && p2.stringValue != null) {
                byte[] newData = new byte[(this.data.length + (p2.stringValue.length() * 2))];
                System.arraycopy(this.data, 0, newData, 0, this.data.length);
                StringHelper.getUnicodeBytes(p2.stringValue, newData, this.data.length);
                this.data = newData;
            }
        }
        return setHeaderData(this.data);
    }

    /* access modifiers changed from: package-private */
    public void addProperty(int id, boolean blip, boolean complex, int val) {
        this.properties.add(new Property(id, blip, complex, val));
    }

    /* access modifiers changed from: package-private */
    public void addProperty(int id, boolean blip, boolean complex, int val, String s) {
        this.properties.add(new Property(id, blip, complex, val, s));
    }

    /* access modifiers changed from: package-private */
    public Property getProperty(int id) {
        boolean found = false;
        Property p = null;
        Iterator i = this.properties.iterator();
        while (i.hasNext() && !found) {
            p = (Property) i.next();
            if (p.id == id) {
                found = true;
            }
        }
        if (found) {
            return p;
        }
        return null;
    }
}

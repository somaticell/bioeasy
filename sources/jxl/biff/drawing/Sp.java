package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;

class Sp extends EscherAtom {
    static Class class$jxl$biff$drawing$Sp;
    private static Logger logger;
    private byte[] data;
    private int persistenceFlags;
    private int shapeId;
    private int shapeType;

    static {
        Class cls;
        if (class$jxl$biff$drawing$Sp == null) {
            cls = class$("jxl.biff.drawing.Sp");
            class$jxl$biff$drawing$Sp = cls;
        } else {
            cls = class$jxl$biff$drawing$Sp;
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

    public Sp(EscherRecordData erd) {
        super(erd);
        this.shapeType = getInstance();
        byte[] bytes = getBytes();
        this.shapeId = IntegerHelper.getInt(bytes[0], bytes[1], bytes[2], bytes[3]);
        this.persistenceFlags = IntegerHelper.getInt(bytes[4], bytes[5], bytes[6], bytes[7]);
    }

    public Sp(ShapeType st, int sid, int p) {
        super(EscherRecordType.SP);
        setVersion(2);
        this.shapeType = st.value;
        this.shapeId = sid;
        this.persistenceFlags = p;
        setInstance(this.shapeType);
    }

    /* access modifiers changed from: package-private */
    public int getShapeId() {
        return this.shapeId;
    }

    /* access modifiers changed from: package-private */
    public int getShapeType() {
        return this.shapeType;
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        this.data = new byte[8];
        IntegerHelper.getFourBytes(this.shapeId, this.data, 0);
        IntegerHelper.getFourBytes(this.persistenceFlags, this.data, 4);
        return setHeaderData(this.data);
    }
}

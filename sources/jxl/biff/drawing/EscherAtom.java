package jxl.biff.drawing;

import common.Logger;

class EscherAtom extends EscherRecord {
    static Class class$jxl$biff$drawing$EscherAtom;
    private static Logger logger;

    static {
        Class cls;
        if (class$jxl$biff$drawing$EscherAtom == null) {
            cls = class$("jxl.biff.drawing.EscherAtom");
            class$jxl$biff$drawing$EscherAtom = cls;
        } else {
            cls = class$jxl$biff$drawing$EscherAtom;
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

    public EscherAtom(EscherRecordData erd) {
        super(erd);
    }

    protected EscherAtom(EscherRecordType type) {
        super(type);
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        logger.warn(new StringBuffer().append("escher atom getData called on object of type ").append(getClass().getName()).append(" code ").append(Integer.toString(getType().getValue(), 16)).toString());
        return null;
    }
}

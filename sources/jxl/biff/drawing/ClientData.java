package jxl.biff.drawing;

import common.Logger;

class ClientData extends EscherAtom {
    static Class class$jxl$biff$drawing$ClientData;
    private static Logger logger;
    private byte[] data;

    static {
        Class cls;
        if (class$jxl$biff$drawing$ClientData == null) {
            cls = class$("jxl.biff.drawing.ClientData");
            class$jxl$biff$drawing$ClientData = cls;
        } else {
            cls = class$jxl$biff$drawing$ClientData;
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

    public ClientData(EscherRecordData erd) {
        super(erd);
    }

    public ClientData() {
        super(EscherRecordType.CLIENT_DATA);
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        this.data = new byte[0];
        return setHeaderData(this.data);
    }
}

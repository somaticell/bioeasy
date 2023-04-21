package jxl.biff.drawing;

import common.Logger;

class ClientTextBox extends EscherAtom {
    static Class class$jxl$biff$drawing$ClientTextBox;
    private static Logger logger;
    private byte[] data;

    static {
        Class cls;
        if (class$jxl$biff$drawing$ClientTextBox == null) {
            cls = class$("jxl.biff.drawing.ClientTextBox");
            class$jxl$biff$drawing$ClientTextBox = cls;
        } else {
            cls = class$jxl$biff$drawing$ClientTextBox;
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

    public ClientTextBox(EscherRecordData erd) {
        super(erd);
    }

    public ClientTextBox() {
        super(EscherRecordType.CLIENT_TEXT_BOX);
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        this.data = new byte[0];
        return setHeaderData(this.data);
    }
}

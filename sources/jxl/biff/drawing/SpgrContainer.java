package jxl.biff.drawing;

import common.Logger;

class SpgrContainer extends EscherContainer {
    static Class class$jxl$biff$drawing$SpgrContainer;
    private static final Logger logger;

    static {
        Class cls;
        if (class$jxl$biff$drawing$SpgrContainer == null) {
            cls = class$("jxl.biff.drawing.SpgrContainer");
            class$jxl$biff$drawing$SpgrContainer = cls;
        } else {
            cls = class$jxl$biff$drawing$SpgrContainer;
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

    public SpgrContainer() {
        super(EscherRecordType.SPGR_CONTAINER);
    }

    public SpgrContainer(EscherRecordData erd) {
        super(erd);
    }
}

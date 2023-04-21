package jxl.biff.drawing;

import common.Logger;
import java.util.ArrayList;
import java.util.Iterator;

class EscherContainer extends EscherRecord {
    static Class class$jxl$biff$drawing$EscherContainer;
    private static Logger logger;
    private ArrayList children;
    private boolean initialized;

    static {
        Class cls;
        if (class$jxl$biff$drawing$EscherContainer == null) {
            cls = class$("jxl.biff.drawing.EscherContainer");
            class$jxl$biff$drawing$EscherContainer = cls;
        } else {
            cls = class$jxl$biff$drawing$EscherContainer;
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

    public EscherContainer(EscherRecordData erd) {
        super(erd);
        this.initialized = false;
        this.children = new ArrayList();
    }

    protected EscherContainer(EscherRecordType type) {
        super(type);
        setContainer(true);
        this.children = new ArrayList();
    }

    public EscherRecord[] getChildren() {
        if (!this.initialized) {
            initialize();
        }
        return (EscherRecord[]) this.children.toArray(new EscherRecord[this.children.size()]);
    }

    public void add(EscherRecord child) {
        this.children.add(child);
    }

    public void remove(EscherRecord child) {
        this.children.remove(child);
    }

    private void initialize() {
        EscherRecord newRecord;
        int curpos = getPos() + 8;
        int endpos = Math.min(getPos() + getLength(), getStreamLength());
        while (curpos < endpos) {
            EscherRecordData erd = new EscherRecordData(getEscherStream(), curpos);
            EscherRecordType type = erd.getType();
            if (type == EscherRecordType.DGG) {
                newRecord = new Dgg(erd);
            } else if (type == EscherRecordType.DG) {
                newRecord = new Dg(erd);
            } else if (type == EscherRecordType.BSTORE_CONTAINER) {
                newRecord = new BStoreContainer(erd);
            } else if (type == EscherRecordType.SPGR_CONTAINER) {
                newRecord = new SpgrContainer(erd);
            } else if (type == EscherRecordType.SP_CONTAINER) {
                newRecord = new SpContainer(erd);
            } else if (type == EscherRecordType.SPGR) {
                newRecord = new Spgr(erd);
            } else if (type == EscherRecordType.SP) {
                newRecord = new Sp(erd);
            } else if (type == EscherRecordType.CLIENT_ANCHOR) {
                newRecord = new ClientAnchor(erd);
            } else if (type == EscherRecordType.CLIENT_DATA) {
                newRecord = new ClientData(erd);
            } else if (type == EscherRecordType.BSE) {
                newRecord = new BlipStoreEntry(erd);
            } else if (type == EscherRecordType.OPT) {
                newRecord = new Opt(erd);
            } else if (type == EscherRecordType.SPLIT_MENU_COLORS) {
                newRecord = new SplitMenuColors(erd);
            } else if (type == EscherRecordType.CLIENT_TEXT_BOX) {
                newRecord = new ClientTextBox(erd);
            } else {
                newRecord = new EscherAtom(erd);
            }
            this.children.add(newRecord);
            curpos += newRecord.getLength();
        }
        this.initialized = true;
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        byte[] data = new byte[0];
        Iterator i = this.children.iterator();
        while (i.hasNext()) {
            byte[] childData = ((EscherRecord) i.next()).getData();
            if (childData != null) {
                byte[] newData = new byte[(data.length + childData.length)];
                System.arraycopy(data, 0, newData, 0, data.length);
                System.arraycopy(childData, 0, newData, data.length, childData.length);
                data = newData;
            }
        }
        return setHeaderData(data);
    }
}

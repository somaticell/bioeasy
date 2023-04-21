package jxl.biff.drawing;

import common.Assert;
import common.Logger;

public class DrawingData implements EscherStream {
    static Class class$jxl$biff$drawing$DrawingData;
    private static Logger logger;
    private byte[] drawingData = null;
    private boolean initialized = false;
    private int numDrawings = 0;
    private EscherRecord[] spgrChildren;

    static {
        Class cls;
        if (class$jxl$biff$drawing$DrawingData == null) {
            cls = class$("jxl.biff.drawing.DrawingData");
            class$jxl$biff$drawing$DrawingData = cls;
        } else {
            cls = class$jxl$biff$drawing$DrawingData;
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

    private void initialize() {
        boolean z = false;
        EscherRecordData er = new EscherRecordData(this, 0);
        Assert.verify(er.isContainer());
        EscherContainer dgContainer = new EscherContainer(er);
        EscherRecord[] children = dgContainer.getChildren();
        EscherRecord[] children2 = dgContainer.getChildren();
        EscherContainer spgrContainer = null;
        for (int i = 0; i < children2.length && spgrContainer == null; i++) {
            EscherRecord child = children2[i];
            if (child.getType() == EscherRecordType.SPGR_CONTAINER) {
                spgrContainer = (EscherContainer) child;
            }
        }
        if (spgrContainer != null) {
            z = true;
        }
        Assert.verify(z);
        this.spgrChildren = spgrContainer.getChildren();
        this.initialized = true;
    }

    public void addData(byte[] data) {
        addRawData(data);
        this.numDrawings++;
    }

    public void addRawData(byte[] data) {
        if (this.drawingData == null) {
            this.drawingData = data;
            return;
        }
        byte[] newArray = new byte[(this.drawingData.length + data.length)];
        System.arraycopy(this.drawingData, 0, newArray, 0, this.drawingData.length);
        System.arraycopy(data, 0, newArray, this.drawingData.length, data.length);
        this.drawingData = newArray;
        this.initialized = false;
    }

    /* access modifiers changed from: package-private */
    public final int getNumDrawings() {
        return this.numDrawings;
    }

    /* access modifiers changed from: package-private */
    public EscherContainer getSpContainer(int drawingNum) {
        if (!this.initialized) {
            initialize();
        }
        EscherContainer spContainer = (EscherContainer) this.spgrChildren[drawingNum + 1];
        Assert.verify(spContainer != null);
        return spContainer;
    }

    public byte[] getData() {
        return this.drawingData;
    }
}

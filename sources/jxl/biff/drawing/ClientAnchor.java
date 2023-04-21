package jxl.biff.drawing;

import common.Logger;
import jxl.biff.IntegerHelper;

class ClientAnchor extends EscherAtom {
    static Class class$jxl$biff$drawing$ClientAnchor;
    private static final Logger logger;
    private byte[] data;
    private double x1;
    private double x2;
    private double y1;
    private double y2;

    static {
        Class cls;
        if (class$jxl$biff$drawing$ClientAnchor == null) {
            cls = class$("jxl.biff.drawing.ClientAnchor");
            class$jxl$biff$drawing$ClientAnchor = cls;
        } else {
            cls = class$jxl$biff$drawing$ClientAnchor;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x12) {
            throw new NoClassDefFoundError(x12.getMessage());
        }
    }

    public ClientAnchor(EscherRecordData erd) {
        super(erd);
        byte[] bytes = getBytes();
        this.x1 = ((double) IntegerHelper.getInt(bytes[2], bytes[3])) + (((double) IntegerHelper.getInt(bytes[4], bytes[5])) / 1024.0d);
        this.y1 = ((double) IntegerHelper.getInt(bytes[6], bytes[7])) + (((double) IntegerHelper.getInt(bytes[8], bytes[9])) / 256.0d);
        this.x2 = ((double) IntegerHelper.getInt(bytes[10], bytes[11])) + (((double) IntegerHelper.getInt(bytes[12], bytes[13])) / 1024.0d);
        this.y2 = ((double) IntegerHelper.getInt(bytes[14], bytes[15])) + (((double) IntegerHelper.getInt(bytes[16], bytes[17])) / 256.0d);
    }

    public ClientAnchor(double x12, double y12, double x22, double y22) {
        super(EscherRecordType.CLIENT_ANCHOR);
        this.x1 = x12;
        this.y1 = y12;
        this.x2 = x22;
        this.y2 = y22;
    }

    /* access modifiers changed from: package-private */
    public byte[] getData() {
        this.data = new byte[18];
        IntegerHelper.getTwoBytes(2, this.data, 0);
        IntegerHelper.getTwoBytes((int) this.x1, this.data, 2);
        IntegerHelper.getTwoBytes((int) ((this.x1 - ((double) ((int) this.x1))) * 1024.0d), this.data, 4);
        IntegerHelper.getTwoBytes((int) this.y1, this.data, 6);
        IntegerHelper.getTwoBytes((int) ((this.y1 - ((double) ((int) this.y1))) * 256.0d), this.data, 8);
        IntegerHelper.getTwoBytes((int) this.x2, this.data, 10);
        IntegerHelper.getTwoBytes((int) ((this.x2 - ((double) ((int) this.x2))) * 1024.0d), this.data, 12);
        IntegerHelper.getTwoBytes((int) this.y2, this.data, 14);
        IntegerHelper.getTwoBytes((int) ((this.y2 - ((double) ((int) this.y2))) * 256.0d), this.data, 16);
        return setHeaderData(this.data);
    }

    /* access modifiers changed from: package-private */
    public double getX1() {
        return this.x1;
    }

    /* access modifiers changed from: package-private */
    public double getY1() {
        return this.y1;
    }

    /* access modifiers changed from: package-private */
    public double getX2() {
        return this.x2;
    }

    /* access modifiers changed from: package-private */
    public double getY2() {
        return this.y2;
    }
}

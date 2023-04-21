package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import jxl.WorkbookSettings;
import jxl.biff.ByteData;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.read.biff.File;

public class Chart implements ByteData, EscherStream {
    static Class class$jxl$biff$drawing$Chart;
    private static final Logger logger;
    private byte[] data;
    private DrawingData drawingData;
    private int drawingNumber;
    private int endpos;
    private File file;
    private boolean initialized;
    private MsoDrawingRecord msoDrawingRecord;
    private ObjRecord objRecord;
    private int startpos;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$biff$drawing$Chart == null) {
            cls = class$("jxl.biff.drawing.Chart");
            class$jxl$biff$drawing$Chart = cls;
        } else {
            cls = class$jxl$biff$drawing$Chart;
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

    public Chart(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, int sp, int ep, File f, WorkbookSettings ws) {
        boolean z = false;
        this.msoDrawingRecord = mso;
        this.objRecord = obj;
        this.startpos = sp;
        this.endpos = ep;
        this.file = f;
        this.workbookSettings = ws;
        if (this.msoDrawingRecord != null) {
            this.drawingData = dd;
            this.drawingData.addData(this.msoDrawingRecord.getRecord().getData());
            this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        }
        this.initialized = false;
        if (!(mso == null || obj == null) || (mso == null && obj == null)) {
            z = true;
        }
        Assert.verify(z);
    }

    public byte[] getBytes() {
        if (!this.initialized) {
            initialize();
        }
        return this.data;
    }

    public byte[] getData() {
        return this.msoDrawingRecord.getRecord().getData();
    }

    private void initialize() {
        this.data = this.file.read(this.startpos, this.endpos - this.startpos);
        this.initialized = true;
    }

    public void rationalize(IndexMapping xfMapping, IndexMapping fontMapping, IndexMapping formatMapping) {
        if (!this.initialized) {
            initialize();
        }
        int pos = 0;
        while (pos < this.data.length) {
            int code = IntegerHelper.getInt(this.data[pos], this.data[pos + 1]);
            int length = IntegerHelper.getInt(this.data[pos + 2], this.data[pos + 3]);
            Type type = Type.getType(code);
            if (type == Type.FONTX) {
                IntegerHelper.getTwoBytes(fontMapping.getNewIndex(IntegerHelper.getInt(this.data[pos + 4], this.data[pos + 5])), this.data, pos + 4);
            } else if (type == Type.FBI) {
                IntegerHelper.getTwoBytes(fontMapping.getNewIndex(IntegerHelper.getInt(this.data[pos + 12], this.data[pos + 13])), this.data, pos + 12);
            } else if (type == Type.IFMT) {
                IntegerHelper.getTwoBytes(formatMapping.getNewIndex(IntegerHelper.getInt(this.data[pos + 4], this.data[pos + 5])), this.data, pos + 4);
            } else if (type == Type.ALRUNS) {
                int numRuns = IntegerHelper.getInt(this.data[pos + 4], this.data[pos + 5]);
                int fontPos = pos + 6;
                for (int i = 0; i < numRuns; i++) {
                    IntegerHelper.getTwoBytes(fontMapping.getNewIndex(IntegerHelper.getInt(this.data[fontPos + 2], this.data[fontPos + 3])), this.data, fontPos + 2);
                    fontPos += 4;
                }
            }
            pos += length + 4;
        }
    }

    /* access modifiers changed from: package-private */
    public EscherContainer getSpContainer() {
        return this.drawingData.getSpContainer(this.drawingNumber);
    }

    /* access modifiers changed from: package-private */
    public MsoDrawingRecord getMsoDrawingRecord() {
        return this.msoDrawingRecord;
    }

    /* access modifiers changed from: package-private */
    public ObjRecord getObjRecord() {
        return this.objRecord;
    }
}

package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.read.biff.Record;

public class DrawingGroup implements EscherStream {
    static Class class$jxl$biff$drawing$DrawingGroup;
    private static Logger logger;
    private BStoreContainer bstoreContainer;
    private byte[] drawingData;
    private int drawingGroupId;
    private ArrayList drawings;
    private boolean drawingsOmitted;
    private EscherContainer escherData;
    private HashMap imageFiles;
    private boolean initialized;
    private int maxObjectId;
    private int maxShapeId;
    private int numBlips;
    private int numCharts;
    private Origin origin;

    static {
        Class cls;
        if (class$jxl$biff$drawing$DrawingGroup == null) {
            cls = class$("jxl.biff.drawing.DrawingGroup");
            class$jxl$biff$drawing$DrawingGroup = cls;
        } else {
            cls = class$jxl$biff$drawing$DrawingGroup;
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

    public DrawingGroup(Origin o) {
        this.origin = o;
        this.initialized = o == Origin.WRITE;
        this.drawings = new ArrayList();
        this.imageFiles = new HashMap();
        this.drawingsOmitted = false;
        this.maxObjectId = 1;
        this.maxShapeId = 1024;
    }

    public DrawingGroup(DrawingGroup dg) {
        this.drawingData = dg.drawingData;
        this.escherData = dg.escherData;
        this.bstoreContainer = dg.bstoreContainer;
        this.initialized = dg.initialized;
        this.drawingData = dg.drawingData;
        this.escherData = dg.escherData;
        this.bstoreContainer = dg.bstoreContainer;
        this.numBlips = dg.numBlips;
        this.numCharts = dg.numCharts;
        this.drawingGroupId = dg.drawingGroupId;
        this.drawingsOmitted = dg.drawingsOmitted;
        this.origin = dg.origin;
        this.imageFiles = (HashMap) dg.imageFiles.clone();
        this.maxObjectId = dg.maxObjectId;
        this.maxShapeId = dg.maxShapeId;
        this.drawings = new ArrayList();
    }

    public void add(MsoDrawingGroupRecord mso) {
        addData(mso.getData());
    }

    public void add(Record cont) {
        addData(cont.getData());
    }

    private void addData(byte[] msodata) {
        if (this.drawingData == null) {
            this.drawingData = new byte[msodata.length];
            System.arraycopy(msodata, 0, this.drawingData, 0, msodata.length);
            return;
        }
        byte[] newdata = new byte[(this.drawingData.length + msodata.length)];
        System.arraycopy(this.drawingData, 0, newdata, 0, this.drawingData.length);
        System.arraycopy(msodata, 0, newdata, this.drawingData.length, msodata.length);
        this.drawingData = newdata;
    }

    /* access modifiers changed from: package-private */
    public final void addDrawing(DrawingGroupObject d) {
        this.drawings.add(d);
        this.maxObjectId = Math.max(this.maxObjectId, d.getObjectId());
        this.maxShapeId = Math.max(this.maxShapeId, d.getShapeId());
    }

    public void add(Chart c) {
        this.numCharts++;
    }

    public void add(DrawingGroupObject d) {
        boolean z = true;
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
            BStoreContainer bsc = getBStoreContainer();
            Dgg dgg = (Dgg) this.escherData.getChildren()[0];
            this.drawingGroupId = (dgg.getCluster(1).drawingGroupId - this.numBlips) - 1;
            this.numBlips = dgg.getDrawingsSaved();
            if (bsc != null) {
                if (this.numBlips != bsc.getNumBlips()) {
                    z = false;
                }
                Assert.verify(z);
            }
        }
        if (!(d instanceof Drawing)) {
            this.maxObjectId++;
            this.maxShapeId++;
            d.setDrawingGroup(this);
            d.setObjectId(this.maxObjectId, this.numBlips + 1, this.maxShapeId);
            if (this.drawings.size() > this.maxObjectId) {
                logger.warn(new StringBuffer().append("drawings length ").append(this.drawings.size()).append(" exceeds the max object id ").append(this.maxObjectId).toString());
                return;
            }
            return;
        }
        Drawing drawing = (Drawing) d;
        Drawing refImage = (Drawing) this.imageFiles.get(d.getImageFilePath());
        if (refImage == null) {
            this.maxObjectId++;
            this.maxShapeId++;
            this.drawings.add(drawing);
            drawing.setDrawingGroup(this);
            drawing.setObjectId(this.maxObjectId, this.numBlips + 1, this.maxShapeId);
            this.numBlips++;
            this.imageFiles.put(drawing.getImageFilePath(), drawing);
            return;
        }
        refImage.setReferenceCount(refImage.getReferenceCount() + 1);
        drawing.setDrawingGroup(this);
        drawing.setObjectId(refImage.getObjectId(), refImage.getBlipId(), refImage.getShapeId());
    }

    public void remove(DrawingGroupObject d) {
        if (getBStoreContainer() != null) {
            if (this.origin == Origin.READ) {
                this.origin = Origin.READ_WRITE;
                this.numBlips = getBStoreContainer().getNumBlips();
                this.drawingGroupId = (((Dgg) this.escherData.getChildren()[0]).getCluster(1).drawingGroupId - this.numBlips) - 1;
            }
            BlipStoreEntry bse = (BlipStoreEntry) getBStoreContainer().getChildren()[d.getBlipId() - 1];
            bse.dereference();
            if (bse.getReferenceCount() == 0) {
                getBStoreContainer().remove(bse);
                Iterator i = this.drawings.iterator();
                while (i.hasNext()) {
                    DrawingGroupObject drawing = (DrawingGroupObject) i.next();
                    if (drawing.getBlipId() > d.getBlipId()) {
                        drawing.setObjectId(drawing.getObjectId(), drawing.getBlipId() - 1, drawing.getShapeId());
                    }
                }
                this.numBlips--;
            }
        }
    }

    private void initialize() {
        boolean z;
        boolean z2 = false;
        EscherRecordData er = new EscherRecordData(this, 0);
        Assert.verify(er.isContainer());
        this.escherData = new EscherContainer(er);
        if (this.escherData.getLength() == this.drawingData.length) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        if (this.escherData.getType() == EscherRecordType.DGG_CONTAINER) {
            z2 = true;
        }
        Assert.verify(z2);
        this.initialized = true;
    }

    private BStoreContainer getBStoreContainer() {
        if (this.bstoreContainer == null) {
            if (!this.initialized) {
                initialize();
            }
            EscherRecord[] children = this.escherData.getChildren();
            if (children.length > 1 && children[1].getType() == EscherRecordType.BSTORE_CONTAINER) {
                this.bstoreContainer = (BStoreContainer) children[1];
            }
        }
        return this.bstoreContainer;
    }

    public byte[] getData() {
        return this.drawingData;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0157, code lost:
        r7 = (jxl.biff.drawing.Drawing) r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(jxl.write.biff.File r23) throws java.io.IOException {
        /*
            r22 = this;
            r0 = r22
            jxl.biff.drawing.Origin r0 = r0.origin
            r18 = r0
            jxl.biff.drawing.Origin r19 = jxl.biff.drawing.Origin.WRITE
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x00b6
            jxl.biff.drawing.DggContainer r9 = new jxl.biff.drawing.DggContainer
            r9.<init>()
            jxl.biff.drawing.Dgg r8 = new jxl.biff.drawing.Dgg
            r0 = r22
            int r0 = r0.numBlips
            r18 = r0
            r0 = r22
            int r0 = r0.numCharts
            r19 = r0
            int r18 = r18 + r19
            int r18 = r18 + 1
            r0 = r22
            int r0 = r0.numBlips
            r19 = r0
            r0 = r18
            r1 = r19
            r8.<init>(r0, r1)
            r18 = 1
            r19 = 0
            r0 = r18
            r1 = r19
            r8.addCluster(r0, r1)
            r0 = r22
            int r0 = r0.numBlips
            r18 = r0
            int r18 = r18 + 1
            r19 = 0
            r0 = r18
            r1 = r19
            r8.addCluster(r0, r1)
            r9.add(r8)
            r11 = 0
            jxl.biff.drawing.BStoreContainer r5 = new jxl.biff.drawing.BStoreContainer
            r5.<init>()
            r0 = r22
            java.util.ArrayList r0 = r0.drawings
            r18 = r0
            java.util.Iterator r12 = r18.iterator()
        L_0x0061:
            boolean r18 = r12.hasNext()
            if (r18 == 0) goto L_0x007f
            java.lang.Object r14 = r12.next()
            boolean r0 = r14 instanceof jxl.biff.drawing.Drawing
            r18 = r0
            if (r18 == 0) goto L_0x0061
            r7 = r14
            jxl.biff.drawing.Drawing r7 = (jxl.biff.drawing.Drawing) r7
            jxl.biff.drawing.BlipStoreEntry r4 = new jxl.biff.drawing.BlipStoreEntry
            r4.<init>((jxl.biff.drawing.Drawing) r7)
            r5.add(r4)
            int r11 = r11 + 1
            goto L_0x0061
        L_0x007f:
            if (r11 <= 0) goto L_0x0087
            r5.setNumBlips(r11)
            r9.add(r5)
        L_0x0087:
            jxl.biff.drawing.Opt r15 = new jxl.biff.drawing.Opt
            r15.<init>()
            r9.add(r15)
            jxl.biff.drawing.SplitMenuColors r17 = new jxl.biff.drawing.SplitMenuColors
            r17.<init>()
            r0 = r17
            r9.add(r0)
            byte[] r18 = r9.getData()
            r0 = r18
            r1 = r22
            r1.drawingData = r0
        L_0x00a3:
            jxl.biff.drawing.MsoDrawingGroupRecord r13 = new jxl.biff.drawing.MsoDrawingGroupRecord
            r0 = r22
            byte[] r0 = r0.drawingData
            r18 = r0
            r0 = r18
            r13.<init>((byte[]) r0)
            r0 = r23
            r0.write(r13)
            return
        L_0x00b6:
            r0 = r22
            jxl.biff.drawing.Origin r0 = r0.origin
            r18 = r0
            jxl.biff.drawing.Origin r19 = jxl.biff.drawing.Origin.READ_WRITE
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x00a3
            jxl.biff.drawing.DggContainer r9 = new jxl.biff.drawing.DggContainer
            r9.<init>()
            jxl.biff.drawing.Dgg r8 = new jxl.biff.drawing.Dgg
            r0 = r22
            int r0 = r0.numBlips
            r18 = r0
            r0 = r22
            int r0 = r0.numCharts
            r19 = r0
            int r18 = r18 + r19
            int r18 = r18 + 1
            r0 = r22
            int r0 = r0.numBlips
            r19 = r0
            r0 = r18
            r1 = r19
            r8.<init>(r0, r1)
            r18 = 1
            r19 = 0
            r0 = r18
            r1 = r19
            r8.addCluster(r0, r1)
            r0 = r22
            int r0 = r0.drawingGroupId
            r18 = r0
            r0 = r22
            int r0 = r0.numBlips
            r19 = r0
            int r18 = r18 + r19
            int r18 = r18 + 1
            r19 = 0
            r0 = r18
            r1 = r19
            r8.addCluster(r0, r1)
            r9.add(r8)
            jxl.biff.drawing.BStoreContainer r5 = new jxl.biff.drawing.BStoreContainer
            r5.<init>()
            r0 = r22
            int r0 = r0.numBlips
            r18 = r0
            r0 = r18
            r5.setNumBlips(r0)
            jxl.biff.drawing.BStoreContainer r16 = r22.getBStoreContainer()
            if (r16 == 0) goto L_0x0172
            jxl.biff.drawing.EscherRecord[] r6 = r16.getChildren()
            r12 = 0
        L_0x012a:
            int r0 = r6.length
            r18 = r0
            r0 = r18
            if (r12 >= r0) goto L_0x013b
            r4 = r6[r12]
            jxl.biff.drawing.BlipStoreEntry r4 = (jxl.biff.drawing.BlipStoreEntry) r4
            r5.add(r4)
            int r12 = r12 + 1
            goto L_0x012a
        L_0x013b:
            r0 = r22
            java.util.ArrayList r0 = r0.drawings
            r18 = r0
            java.util.Iterator r12 = r18.iterator()
        L_0x0145:
            boolean r18 = r12.hasNext()
            if (r18 == 0) goto L_0x016f
            java.lang.Object r10 = r12.next()
            jxl.biff.drawing.DrawingGroupObject r10 = (jxl.biff.drawing.DrawingGroupObject) r10
            boolean r0 = r10 instanceof jxl.biff.drawing.Drawing
            r18 = r0
            if (r18 == 0) goto L_0x0145
            r7 = r10
            jxl.biff.drawing.Drawing r7 = (jxl.biff.drawing.Drawing) r7
            jxl.biff.drawing.Origin r18 = r7.getOrigin()
            jxl.biff.drawing.Origin r19 = jxl.biff.drawing.Origin.READ
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x0145
            jxl.biff.drawing.BlipStoreEntry r4 = new jxl.biff.drawing.BlipStoreEntry
            r4.<init>((jxl.biff.drawing.Drawing) r7)
            r5.add(r4)
            goto L_0x0145
        L_0x016f:
            r9.add(r5)
        L_0x0172:
            jxl.biff.drawing.Opt r15 = new jxl.biff.drawing.Opt
            r15.<init>()
            r18 = 191(0xbf, float:2.68E-43)
            r19 = 0
            r20 = 0
            r21 = 524296(0x80008, float:7.34695E-40)
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r15.addProperty(r0, r1, r2, r3)
            r18 = 385(0x181, float:5.4E-43)
            r19 = 0
            r20 = 0
            r21 = 134217737(0x8000009, float:3.851864E-34)
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r15.addProperty(r0, r1, r2, r3)
            r18 = 448(0x1c0, float:6.28E-43)
            r19 = 0
            r20 = 0
            r21 = 134217792(0x8000040, float:3.8518893E-34)
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r15.addProperty(r0, r1, r2, r3)
            r9.add(r15)
            jxl.biff.drawing.SplitMenuColors r17 = new jxl.biff.drawing.SplitMenuColors
            r17.<init>()
            r0 = r17
            r9.add(r0)
            byte[] r18 = r9.getData()
            r0 = r18
            r1 = r22
            r1.drawingData = r0
            goto L_0x00a3
        */
        throw new UnsupportedOperationException("Method not decompiled: jxl.biff.drawing.DrawingGroup.write(jxl.write.biff.File):void");
    }

    /* access modifiers changed from: package-private */
    public final int getNumberOfBlips() {
        return this.numBlips;
    }

    /* access modifiers changed from: package-private */
    public byte[] getImageData(int blipId) {
        boolean z;
        boolean z2 = false;
        this.numBlips = getBStoreContainer().getNumBlips();
        if (blipId <= this.numBlips) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        if (this.origin == Origin.READ || this.origin == Origin.READ_WRITE) {
            z2 = true;
        }
        Assert.verify(z2);
        return ((BlipStoreEntry) getBStoreContainer().getChildren()[blipId - 1]).getImageData();
    }

    public void setDrawingsOmitted(MsoDrawingRecord mso, ObjRecord obj) {
        this.drawingsOmitted = true;
        if (obj != null) {
            this.maxObjectId = Math.max(this.maxObjectId, obj.getObjectId());
        }
    }

    public boolean hasDrawingsOmitted() {
        return this.drawingsOmitted;
    }

    public void updateData(DrawingGroup dg) {
        this.drawingsOmitted = dg.drawingsOmitted;
        this.maxObjectId = dg.maxObjectId;
        this.maxShapeId = dg.maxShapeId;
    }
}

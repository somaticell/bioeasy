package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import jxl.Image;

public class Drawing implements DrawingGroupObject, Image {
    static Class class$jxl$biff$drawing$Drawing;
    private static Logger logger;
    private int blipId;
    private DrawingData drawingData;
    private DrawingGroup drawingGroup;
    private int drawingNumber;
    private EscherContainer escherData;
    private double height;
    private byte[] imageData;
    private File imageFile;
    private boolean initialized = false;
    private MsoDrawingRecord msoDrawingRecord;
    private ObjRecord objRecord;
    private int objectId;
    private Origin origin;
    private EscherContainer readSpContainer;
    private int referenceCount;
    private int shapeId;
    private ShapeType type;
    private double width;
    private double x;
    private double y;

    static {
        Class cls;
        if (class$jxl$biff$drawing$Drawing == null) {
            cls = class$("jxl.biff.drawing.Drawing");
            class$jxl$biff$drawing$Drawing = cls;
        } else {
            cls = class$jxl$biff$drawing$Drawing;
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

    public Drawing(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, DrawingGroup dg) {
        boolean z = false;
        this.drawingGroup = dg;
        this.msoDrawingRecord = mso;
        this.drawingData = dd;
        this.objRecord = obj;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData.addData(this.msoDrawingRecord.getData());
        this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        this.drawingGroup.addDrawing(this);
        if (!(mso == null || obj == null)) {
            z = true;
        }
        Assert.verify(z);
        initialize();
    }

    protected Drawing(DrawingGroupObject dgo, DrawingGroup dg) {
        Drawing d = (Drawing) dgo;
        Assert.verify(d.origin == Origin.READ);
        this.msoDrawingRecord = d.msoDrawingRecord;
        this.objRecord = d.objRecord;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData = d.drawingData;
        this.drawingGroup = dg;
        this.drawingNumber = d.drawingNumber;
        this.drawingGroup.addDrawing(this);
    }

    public Drawing(double x2, double y2, double width2, double height2, File image) {
        this.imageFile = image;
        this.initialized = true;
        this.origin = Origin.WRITE;
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
        this.referenceCount = 1;
        this.type = ShapeType.PICTURE_FRAME;
    }

    public Drawing(double x2, double y2, double width2, double height2, byte[] image) {
        this.imageData = image;
        this.initialized = true;
        this.origin = Origin.WRITE;
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
        this.referenceCount = 1;
        this.type = ShapeType.PICTURE_FRAME;
    }

    private void initialize() {
        boolean z;
        this.readSpContainer = this.drawingData.getSpContainer(this.drawingNumber);
        if (this.readSpContainer != null) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        EscherRecord[] children = this.readSpContainer.getChildren();
        Sp sp = (Sp) this.readSpContainer.getChildren()[0];
        this.shapeId = sp.getShapeId();
        this.objectId = this.objRecord.getObjectId();
        this.type = ShapeType.getType(sp.getShapeType());
        if (this.type == ShapeType.UNKNOWN) {
            logger.warn("Unknown shape type");
        }
        Opt opt = (Opt) this.readSpContainer.getChildren()[1];
        if (opt.getProperty(260) != null) {
            this.blipId = opt.getProperty(260).value;
        }
        if (opt.getProperty(261) != null) {
            this.imageFile = new File(opt.getProperty(261).stringValue);
        } else if (this.type == ShapeType.PICTURE_FRAME) {
            logger.warn("no filename property for drawing");
            this.imageFile = new File(Integer.toString(this.blipId));
        }
        ClientAnchor clientAnchor = null;
        for (int i = 0; i < children.length && clientAnchor == null; i++) {
            if (children[i].getType() == EscherRecordType.CLIENT_ANCHOR) {
                clientAnchor = (ClientAnchor) children[i];
            }
        }
        if (clientAnchor == null) {
            logger.warn("client anchor not found");
        } else {
            this.x = clientAnchor.getX1();
            this.y = clientAnchor.getY1();
            this.width = clientAnchor.getX2() - this.x;
            this.height = clientAnchor.getY2() - this.y;
        }
        if (this.blipId == 0) {
            logger.warn("linked drawings are not supported");
        }
        this.initialized = true;
    }

    public File getImageFile() {
        return this.imageFile;
    }

    public String getImageFilePath() {
        if (this.imageFile == null) {
            return this.blipId != 0 ? Integer.toString(this.blipId) : "__new__image__";
        }
        return this.imageFile.getPath();
    }

    public final void setObjectId(int objid, int bip, int sid) {
        this.objectId = objid;
        this.blipId = bip;
        this.shapeId = sid;
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
        }
    }

    public final int getObjectId() {
        if (!this.initialized) {
            initialize();
        }
        return this.objectId;
    }

    public int getShapeId() {
        if (!this.initialized) {
            initialize();
        }
        return this.shapeId;
    }

    public final int getBlipId() {
        if (!this.initialized) {
            initialize();
        }
        return this.blipId;
    }

    public MsoDrawingRecord getMsoDrawingRecord() {
        return this.msoDrawingRecord;
    }

    public EscherContainer getSpContainer() {
        if (!this.initialized) {
            initialize();
        }
        if (this.origin == Origin.READ) {
            return getReadSpContainer();
        }
        SpContainer spContainer = new SpContainer();
        spContainer.add(new Sp(this.type, this.shapeId, 2560));
        Opt opt = new Opt();
        opt.addProperty(260, true, false, this.blipId);
        if (this.type == ShapeType.PICTURE_FRAME) {
            String filePath = this.imageFile != null ? this.imageFile.getPath() : "";
            opt.addProperty(261, true, true, filePath.length() * 2, filePath);
            opt.addProperty(447, false, false, 65536);
            opt.addProperty(959, false, false, 524288);
            spContainer.add(opt);
        }
        spContainer.add(new ClientAnchor(this.x, this.y, this.width + this.x, this.height + this.y));
        spContainer.add(new ClientData());
        return spContainer;
    }

    public void setDrawingGroup(DrawingGroup dg) {
        this.drawingGroup = dg;
    }

    public DrawingGroup getDrawingGroup() {
        return this.drawingGroup;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public int getReferenceCount() {
        return this.referenceCount;
    }

    public void setReferenceCount(int r) {
        this.referenceCount = r;
    }

    public double getX() {
        if (!this.initialized) {
            initialize();
        }
        return this.x;
    }

    public void setX(double x2) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.x = x2;
    }

    public double getY() {
        if (!this.initialized) {
            initialize();
        }
        return this.y;
    }

    public void setY(double y2) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.y = y2;
    }

    public double getWidth() {
        if (!this.initialized) {
            initialize();
        }
        return this.width;
    }

    public void setWidth(double w) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.width = w;
    }

    public double getHeight() {
        if (!this.initialized) {
            initialize();
        }
        return this.height;
    }

    public void setHeight(double h) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.height = h;
    }

    private EscherContainer getReadSpContainer() {
        if (!this.initialized) {
            initialize();
        }
        return this.readSpContainer;
    }

    public byte[] getImageData() {
        Assert.verify(this.origin == Origin.READ || this.origin == Origin.READ_WRITE);
        if (!this.initialized) {
            initialize();
        }
        return this.drawingGroup.getImageData(this.blipId);
    }

    public byte[] getImageBytes() throws IOException {
        boolean z = true;
        if (this.origin == Origin.READ || this.origin == Origin.READ_WRITE) {
            return getImageData();
        }
        Assert.verify(this.origin == Origin.WRITE);
        if (this.imageFile == null) {
            if (this.imageData == null) {
                z = false;
            }
            Assert.verify(z);
            return this.imageData;
        }
        byte[] data = new byte[((int) this.imageFile.length())];
        FileInputStream fis = new FileInputStream(this.imageFile);
        fis.read(data, 0, data.length);
        fis.close();
        return data;
    }

    public ShapeType getType() {
        return this.type;
    }

    public void writeAdditionalRecords(jxl.write.biff.File outputFile) throws IOException {
        if (this.origin == Origin.READ) {
            outputFile.write(this.objRecord);
        } else {
            outputFile.write(new ObjRecord(this.objectId, ObjRecord.PICTURE));
        }
    }

    public void writeTailRecords(jxl.write.biff.File outputFile) throws IOException {
    }

    public double getColumn() {
        return getX();
    }

    public double getRow() {
        return getY();
    }

    public boolean isFirst() {
        return this.msoDrawingRecord.isFirst();
    }

    public boolean isFormObject() {
        return false;
    }
}

package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import java.io.IOException;
import jxl.WorkbookSettings;
import jxl.biff.ContinueRecord;
import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.write.biff.File;

public class Button implements DrawingGroupObject {
    static Class class$jxl$biff$drawing$Button;
    private static Logger logger;
    private int blipId;
    private int column;
    private String commentText;
    private DrawingData drawingData;
    private DrawingGroup drawingGroup;
    private int drawingNumber;
    private EscherContainer escherData;
    private ContinueRecord formatting;
    private double height;
    private boolean initialized = false;
    private MsoDrawingRecord mso;
    private MsoDrawingRecord msoDrawingRecord;
    private ObjRecord objRecord;
    private int objectId;
    private Origin origin;
    private EscherContainer readSpContainer;
    private int referenceCount;
    private int row;
    private int shapeId;
    private EscherContainer spContainer;
    private ContinueRecord text;
    private TextObjectRecord txo;
    private ShapeType type;
    private double width;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$biff$drawing$Button == null) {
            cls = class$("jxl.biff.drawing.Button");
            class$jxl$biff$drawing$Button = cls;
        } else {
            cls = class$jxl$biff$drawing$Button;
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

    public Button(MsoDrawingRecord mso2, ObjRecord obj, DrawingData dd, DrawingGroup dg, WorkbookSettings ws) {
        boolean z = false;
        this.drawingGroup = dg;
        this.msoDrawingRecord = mso2;
        this.drawingData = dd;
        this.objRecord = obj;
        this.initialized = false;
        this.workbookSettings = ws;
        this.origin = Origin.READ;
        this.drawingData.addData(this.msoDrawingRecord.getData());
        this.drawingNumber = this.drawingData.getNumDrawings() - 1;
        this.drawingGroup.addDrawing(this);
        if (!(mso2 == null || obj == null)) {
            z = true;
        }
        Assert.verify(z);
        initialize();
    }

    public Button(DrawingGroupObject dgo, DrawingGroup dg, WorkbookSettings ws) {
        Button d = (Button) dgo;
        Assert.verify(d.origin == Origin.READ);
        this.msoDrawingRecord = d.msoDrawingRecord;
        this.objRecord = d.objRecord;
        this.initialized = false;
        this.origin = Origin.READ;
        this.drawingData = d.drawingData;
        this.drawingGroup = dg;
        this.drawingNumber = d.drawingNumber;
        this.drawingGroup.addDrawing(this);
        this.mso = d.mso;
        this.txo = d.txo;
        this.text = d.text;
        this.formatting = d.formatting;
        this.workbookSettings = ws;
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
        this.objectId = this.objRecord.getObjectId();
        this.shapeId = sp.getShapeId();
        this.type = ShapeType.getType(sp.getShapeType());
        if (this.type == ShapeType.UNKNOWN) {
            logger.warn("Unknown shape type");
        }
        Opt opt = (Opt) this.readSpContainer.getChildren()[1];
        ClientAnchor clientAnchor = null;
        for (int i = 0; i < children.length && clientAnchor == null; i++) {
            if (children[i].getType() == EscherRecordType.CLIENT_ANCHOR) {
                clientAnchor = (ClientAnchor) children[i];
            }
        }
        if (clientAnchor == null) {
            logger.warn("Client anchor not found");
        } else {
            this.column = ((int) clientAnchor.getX1()) - 1;
            this.row = ((int) clientAnchor.getY1()) + 1;
        }
        this.initialized = true;
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

    public final int getShapeId() {
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
        Assert.verify(false);
        return this.spContainer;
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
        return (double) this.column;
    }

    public void setX(double x) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.column = (int) x;
    }

    public double getY() {
        if (!this.initialized) {
            initialize();
        }
        return (double) this.row;
    }

    public void setY(double y) {
        if (this.origin == Origin.READ) {
            if (!this.initialized) {
                initialize();
            }
            this.origin = Origin.READ_WRITE;
        }
        this.row = (int) y;
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

    public ShapeType getType() {
        return this.type;
    }

    public void setTextObject(TextObjectRecord t) {
        this.txo = t;
    }

    public void setText(ContinueRecord t) {
        this.text = t;
    }

    public void setFormatting(ContinueRecord t) {
        this.formatting = t;
    }

    public byte[] getImageBytes() {
        Assert.verify(false);
        return null;
    }

    public String getImageFilePath() {
        Assert.verify(false);
        return null;
    }

    public void addMso(MsoDrawingRecord d) {
        this.mso = d;
        this.drawingData.addRawData(this.mso.getData());
    }

    public void writeAdditionalRecords(File outputFile) throws IOException {
        if (this.origin == Origin.READ) {
            outputFile.write(this.objRecord);
            if (this.mso != null) {
                outputFile.write(this.mso);
            }
            outputFile.write(this.txo);
            outputFile.write(this.text);
            if (this.formatting != null) {
                outputFile.write(this.formatting);
                return;
            }
            return;
        }
        Assert.verify(false);
        outputFile.write(new ObjRecord(this.objectId, ObjRecord.EXCELNOTE));
        outputFile.write(new MsoDrawingRecord(new ClientTextBox().getData()));
        outputFile.write(new TextObjectRecord(getText()));
        byte[] textData = new byte[((this.commentText.length() * 2) + 1)];
        textData[0] = 1;
        StringHelper.getUnicodeBytes(this.commentText, textData, 1);
        outputFile.write(new ContinueRecord(textData));
        byte[] frData = new byte[16];
        IntegerHelper.getTwoBytes(0, frData, 0);
        IntegerHelper.getTwoBytes(0, frData, 2);
        IntegerHelper.getTwoBytes(this.commentText.length(), frData, 8);
        IntegerHelper.getTwoBytes(0, frData, 10);
        outputFile.write(new ContinueRecord(frData));
    }

    public void writeTailRecords(File outputFile) throws IOException {
    }

    public int getRow() {
        return 0;
    }

    public int getColumn() {
        return 0;
    }

    public String getText() {
        if (this.commentText == null) {
            Assert.verify(this.text != null);
            byte[] td = this.text.getData();
            if (td[0] == 0) {
                this.commentText = StringHelper.getString(td, td.length - 1, 1, this.workbookSettings);
            } else {
                this.commentText = StringHelper.getUnicodeString(td, (td.length - 1) / 2, 1);
            }
        }
        return this.commentText;
    }

    public int hashCode() {
        return this.commentText.hashCode();
    }

    public void setButtonText(String t) {
        this.commentText = t;
        if (this.origin == Origin.READ) {
            this.origin = Origin.READ_WRITE;
        }
    }

    public boolean isFirst() {
        return this.mso.isFirst();
    }

    public boolean isFormObject() {
        return true;
    }
}

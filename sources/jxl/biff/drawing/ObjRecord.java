package jxl.biff.drawing;

import common.Assert;
import common.Logger;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.Record;

public class ObjRecord extends WritableRecordData {
    public static final ObjType BUTTON = new ObjType(7, "Button");
    public static final ObjType CHART = new ObjType(5, "Chart");
    public static final ObjType CHECKBOX = new ObjType(14, "Checkbox");
    private static final int CLIPBOARD_FORMAT_LENGTH = 6;
    public static final ObjType COMBOBOX = new ObjType(20, "Combo Box");
    private static final int COMMON_DATA_LENGTH = 22;
    public static final ObjType DIALOGUEBOX = new ObjType(15, "Dialogue Box");
    public static final ObjType EDITBOX = new ObjType(13, "Edit Box");
    private static final int END_LENGTH = 4;
    public static final ObjType EXCELNOTE = new ObjType(25, "Excel Note");
    public static final ObjType FORMCONTROL = new ObjType(20, "Form Combo Box");
    public static final ObjType GROUPBOX = new ObjType(19, "Group Box");
    public static final ObjType LABEL = new ObjType(14, "Label");
    public static final ObjType LISTBOX = new ObjType(18, "List Box");
    public static final ObjType MSOFFICEDRAWING = new ObjType(30, "MS Office Drawing");
    private static final int NOTE_STRUCTURE_LENGTH = 26;
    public static final ObjType OPTION = new ObjType(12, "Option");
    public static final ObjType PICTURE = new ObjType(8, "Picture");
    private static final int PICTURE_OPTION_LENGTH = 6;
    public static final ObjType TBD = new ObjType(2, "TBD");
    public static final ObjType TBD2 = new ObjType(1, "TBD2");
    public static final ObjType TEXT = new ObjType(6, "Text");
    public static final ObjType UNKNOWN = new ObjType(255, "Unknown");
    static Class class$jxl$biff$drawing$ObjRecord;
    private static final Logger logger;
    private int objectId;
    private boolean read;
    private ObjType type;

    static {
        Class cls;
        if (class$jxl$biff$drawing$ObjRecord == null) {
            cls = class$("jxl.biff.drawing.ObjRecord");
            class$jxl$biff$drawing$ObjRecord = cls;
        } else {
            cls = class$jxl$biff$drawing$ObjRecord;
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

    private static final class ObjType {
        private static ObjType[] types = new ObjType[0];
        public String desc;
        public int value;

        ObjType(int v, String d) {
            this.value = v;
            this.desc = d;
            ObjType[] oldtypes = types;
            types = new ObjType[(types.length + 1)];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            types[oldtypes.length] = this;
        }

        public String toString() {
            return this.desc;
        }

        public static ObjType getType(int val) {
            ObjType retval = ObjRecord.UNKNOWN;
            for (int i = 0; i < types.length && retval == ObjRecord.UNKNOWN; i++) {
                if (types[i].value == val) {
                    retval = types[i];
                }
            }
            return retval;
        }
    }

    public ObjRecord(Record t) {
        super(t);
        byte[] data = t.getData();
        int objtype = IntegerHelper.getInt(data[4], data[5]);
        this.read = true;
        this.type = ObjType.getType(objtype);
        if (this.type == UNKNOWN) {
        }
        this.objectId = IntegerHelper.getInt(data[6], data[7]);
    }

    ObjRecord(int objId, ObjType t) {
        super(Type.OBJ);
        this.objectId = objId;
        this.type = t;
    }

    public byte[] getData() {
        if (this.read) {
            return getRecord().getData();
        }
        if (this.type == PICTURE || this.type == CHART) {
            return getPictureData();
        }
        if (this.type == EXCELNOTE) {
            return getNoteData();
        }
        Assert.verify(false);
        return null;
    }

    private byte[] getPictureData() {
        byte[] data = new byte[38];
        IntegerHelper.getTwoBytes(21, data, 0);
        IntegerHelper.getTwoBytes(18, data, 2);
        IntegerHelper.getTwoBytes(this.type.value, data, 4);
        IntegerHelper.getTwoBytes(this.objectId, data, 6);
        IntegerHelper.getTwoBytes(24593, data, 8);
        int pos = 0 + 22;
        IntegerHelper.getTwoBytes(7, data, pos);
        IntegerHelper.getTwoBytes(2, data, 24);
        IntegerHelper.getTwoBytes(65535, data, 26);
        int pos2 = pos + 6;
        IntegerHelper.getTwoBytes(8, data, pos2);
        IntegerHelper.getTwoBytes(2, data, 30);
        IntegerHelper.getTwoBytes(1, data, 32);
        int pos3 = pos2 + 6;
        IntegerHelper.getTwoBytes(0, data, pos3);
        IntegerHelper.getTwoBytes(0, data, 36);
        int pos4 = pos3 + 4;
        return data;
    }

    private byte[] getNoteData() {
        byte[] data = new byte[52];
        IntegerHelper.getTwoBytes(21, data, 0);
        IntegerHelper.getTwoBytes(18, data, 2);
        IntegerHelper.getTwoBytes(this.type.value, data, 4);
        IntegerHelper.getTwoBytes(this.objectId, data, 6);
        IntegerHelper.getTwoBytes(16401, data, 8);
        int pos = 0 + 22;
        IntegerHelper.getTwoBytes(13, data, pos);
        IntegerHelper.getTwoBytes(22, data, 24);
        int pos2 = pos + 26;
        IntegerHelper.getTwoBytes(0, data, pos2);
        IntegerHelper.getTwoBytes(0, data, 50);
        int pos3 = pos2 + 4;
        return data;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    public ObjType getType() {
        return this.type;
    }

    /* access modifiers changed from: package-private */
    public int getObjectId() {
        return this.objectId;
    }
}

package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.StringHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.read.biff.NameRecord;

class NameRecord extends WritableRecordData {
    private static final int areaReference = 59;
    private static final int cellReference = 58;
    private static final int subExpression = 41;
    private static final int union = 16;
    private byte[] data;
    private int index;
    private String name;
    private NameRange[] ranges;
    private int sheetRef = 0;

    static int access$000(NameRecord x0) {
        return x0.sheetRef;
    }

    class NameRange {
        private int columnFirst;
        private int columnLast;
        private int externalSheet;
        private int rowFirst;
        private int rowLast;
        private final NameRecord this$0;

        static int access$100(NameRange x0) {
            return x0.externalSheet;
        }

        NameRange(NameRecord this$02, NameRecord.NameRange nr) {
            this.this$0 = this$02;
            this.columnFirst = nr.getFirstColumn();
            this.rowFirst = nr.getFirstRow();
            this.columnLast = nr.getLastColumn();
            this.rowLast = nr.getLastRow();
            this.externalSheet = nr.getExternalSheet();
        }

        NameRange(NameRecord this$02, int theSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol) {
            this.this$0 = this$02;
            this.columnFirst = theStartCol;
            this.rowFirst = theStartRow;
            this.columnLast = theEndCol;
            this.rowLast = theEndRow;
            this.externalSheet = theSheet;
        }

        /* access modifiers changed from: package-private */
        public int getFirstColumn() {
            return this.columnFirst;
        }

        /* access modifiers changed from: package-private */
        public int getFirstRow() {
            return this.rowFirst;
        }

        /* access modifiers changed from: package-private */
        public int getLastColumn() {
            return this.columnLast;
        }

        /* access modifiers changed from: package-private */
        public int getLastRow() {
            return this.rowLast;
        }

        /* access modifiers changed from: package-private */
        public int getExternalSheet() {
            return this.externalSheet;
        }

        /* access modifiers changed from: package-private */
        public byte[] getData() {
            byte[] d = new byte[10];
            IntegerHelper.getTwoBytes(NameRecord.access$000(this.this$0), d, 0);
            IntegerHelper.getTwoBytes(this.rowFirst, d, 2);
            IntegerHelper.getTwoBytes(this.rowLast, d, 4);
            IntegerHelper.getTwoBytes(this.columnFirst & 255, d, 6);
            IntegerHelper.getTwoBytes(this.columnLast & 255, d, 8);
            return d;
        }
    }

    public NameRecord(jxl.read.biff.NameRecord sr, int ind) {
        super(Type.NAME);
        this.data = sr.getData();
        this.name = sr.getName();
        this.sheetRef = sr.getSheetRef();
        this.index = ind;
        NameRecord.NameRange[] r = sr.getRanges();
        this.ranges = new NameRange[r.length];
        for (int i = 0; i < this.ranges.length; i++) {
            this.ranges[i] = new NameRange(this, r[i]);
        }
    }

    NameRecord(String theName, int theIndex, int theSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol) {
        super(Type.NAME);
        this.name = theName;
        this.index = theIndex;
        this.sheetRef = 0;
        this.ranges = new NameRange[1];
        this.ranges[0] = new NameRange(this, theSheet, theStartRow, theEndRow, theStartCol, theEndCol);
    }

    public byte[] getData() {
        if (this.data != null) {
            return this.data;
        }
        this.data = new byte[(this.name.length() + 15 + 11)];
        IntegerHelper.getTwoBytes(0, this.data, 0);
        this.data[2] = 0;
        this.data[3] = (byte) this.name.length();
        IntegerHelper.getTwoBytes(11, this.data, 4);
        IntegerHelper.getTwoBytes(NameRange.access$100(this.ranges[0]), this.data, 6);
        IntegerHelper.getTwoBytes(NameRange.access$100(this.ranges[0]), this.data, 8);
        StringHelper.getBytes(this.name, this.data, 15);
        int pos = this.name.length() + 15;
        this.data[pos] = 59;
        byte[] rd = this.ranges[0].getData();
        System.arraycopy(rd, 0, this.data, pos + 1, rd.length);
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }

    public int getSheetRef() {
        return this.sheetRef;
    }

    public void setSheetRef(int i) {
        this.sheetRef = i;
        IntegerHelper.getTwoBytes(this.sheetRef, this.data, 8);
    }

    public NameRange[] getRanges() {
        return this.ranges;
    }
}

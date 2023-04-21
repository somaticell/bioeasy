package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;

public class NameRecord extends RecordData {
    private static final int areaReference = 59;
    public static Biff7 biff7 = new Biff7((AnonymousClass1) null);
    private static final int builtIn = 32;
    private static final String[] builtInNames = {"Consolidate_Area", "Auto_Open", "Auto_Close", "Extract", "Database", "Criteria", "Print_Area", "Print_Titles", "Recorder", "Data_Form", "Auto_Activate", "Auto_Deactivate", "Sheet_Title", "_FilterDatabase"};
    private static final int cellReference = 58;
    static Class class$jxl$read$biff$NameRecord = null;
    private static final int commandMacro = 12;
    private static Logger logger = null;
    private static final int subExpression = 41;
    private static final int union = 16;
    private int index;
    private boolean isbiff8;
    private String name;
    private ArrayList ranges;
    private int sheetRef = 0;

    /* renamed from: jxl.read.biff.NameRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$NameRecord == null) {
            cls = class$("jxl.read.biff.NameRecord");
            class$jxl$read$biff$NameRecord = cls;
        } else {
            cls = class$jxl$read$biff$NameRecord;
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

    private static class Biff7 {
        private Biff7() {
        }

        Biff7(AnonymousClass1 x0) {
            this();
        }
    }

    public class NameRange {
        private int columnFirst;
        private int columnLast;
        private int externalSheet;
        private int rowFirst;
        private int rowLast;
        private final NameRecord this$0;

        NameRange(NameRecord this$02, int s1, int c1, int r1, int c2, int r2) {
            this.this$0 = this$02;
            this.columnFirst = c1;
            this.rowFirst = r1;
            this.columnLast = c2;
            this.rowLast = r2;
            this.externalSheet = s1;
        }

        public int getFirstColumn() {
            return this.columnFirst;
        }

        public int getFirstRow() {
            return this.rowFirst;
        }

        public int getLastColumn() {
            return this.columnLast;
        }

        public int getLastRow() {
            return this.rowLast;
        }

        public int getExternalSheet() {
            return this.externalSheet;
        }
    }

    NameRecord(Record t, WorkbookSettings ws, int ind) {
        super(t);
        int pos;
        String stringBuffer;
        this.index = ind;
        this.isbiff8 = true;
        try {
            this.ranges = new ArrayList();
            byte[] data = getRecord().getData();
            int option = IntegerHelper.getInt(data[0], data[1]);
            byte length = data[3];
            this.sheetRef = IntegerHelper.getInt(data[8], data[9]);
            if ((option & 32) != 0) {
                if (data[15] < 13) {
                    stringBuffer = builtInNames[data[15]];
                } else {
                    stringBuffer = new StringBuffer().append("Builtin_").append(Integer.toString(data[15], 16)).toString();
                }
                this.name = stringBuffer;
                return;
            }
            this.name = StringHelper.getString(data, length, 15, ws);
            if ((option & 12) == 0) {
                int pos2 = length + 15;
                if (data[pos2] == 58) {
                    int sheet = IntegerHelper.getInt(data[pos2 + 1], data[pos2 + 2]);
                    int row = IntegerHelper.getInt(data[pos2 + 3], data[pos2 + 4]);
                    int columnMask = IntegerHelper.getInt(data[pos2 + 5], data[pos2 + 6]);
                    int column = columnMask & 255;
                    Assert.verify((786432 & columnMask) == 0);
                    this.ranges.add(new NameRange(this, sheet, column, row, column, row));
                } else if (data[pos2] == 59) {
                    while (pos2 < data.length) {
                        int sheet1 = IntegerHelper.getInt(data[pos2 + 1], data[pos2 + 2]);
                        int r1 = IntegerHelper.getInt(data[pos2 + 3], data[pos2 + 4]);
                        int r2 = IntegerHelper.getInt(data[pos2 + 5], data[pos2 + 6]);
                        int columnMask2 = IntegerHelper.getInt(data[pos2 + 7], data[pos2 + 8]);
                        int c1 = columnMask2 & 255;
                        Assert.verify((786432 & columnMask2) == 0);
                        int columnMask3 = IntegerHelper.getInt(data[pos2 + 9], data[pos2 + 10]);
                        int c2 = columnMask3 & 255;
                        Assert.verify((786432 & columnMask3) == 0);
                        this.ranges.add(new NameRange(this, sheet1, c1, r1, c2, r2));
                        pos2 += 11;
                    }
                } else if (data[pos2] == 41) {
                    if (!(pos2 >= data.length || data[pos2] == 58 || data[pos2] == 59)) {
                        if (data[pos2] == 41) {
                            pos2 += 3;
                        } else if (data[pos2] == 16) {
                            pos2++;
                        }
                    }
                    while (pos < data.length) {
                        int sheet12 = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
                        int r12 = IntegerHelper.getInt(data[pos + 3], data[pos + 4]);
                        int r22 = IntegerHelper.getInt(data[pos + 5], data[pos + 6]);
                        int columnMask4 = IntegerHelper.getInt(data[pos + 7], data[pos + 8]);
                        int c12 = columnMask4 & 255;
                        Assert.verify((786432 & columnMask4) == 0);
                        int columnMask5 = IntegerHelper.getInt(data[pos + 9], data[pos + 10]);
                        int c22 = columnMask5 & 255;
                        Assert.verify((786432 & columnMask5) == 0);
                        this.ranges.add(new NameRange(this, sheet12, c12, r12, c22, r22));
                        pos += 11;
                        if (!(pos >= data.length || data[pos] == 58 || data[pos] == 59)) {
                            if (data[pos] == 41) {
                                pos += 3;
                            } else if (data[pos] == 16) {
                                pos++;
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            logger.warn("Cannot read name");
            this.name = "ERROR";
        }
    }

    NameRecord(Record t, WorkbookSettings ws, int ind, Biff7 dummy) {
        super(t);
        int pos;
        this.index = ind;
        this.isbiff8 = false;
        try {
            this.ranges = new ArrayList();
            byte[] data = getRecord().getData();
            byte length = data[3];
            this.sheetRef = IntegerHelper.getInt(data[8], data[9]);
            this.name = StringHelper.getString(data, length, 14, ws);
            int pos2 = length + 14;
            if (pos2 < data.length) {
                if (data[pos2] == 58) {
                    int sheet = IntegerHelper.getInt(data[pos2 + 11], data[pos2 + 12]);
                    int row = IntegerHelper.getInt(data[pos2 + 15], data[pos2 + 16]);
                    byte column = data[pos2 + 17];
                    this.ranges.add(new NameRange(this, sheet, column, row, column, row));
                } else if (data[pos2] == 59) {
                    while (pos2 < data.length) {
                        int sheet1 = IntegerHelper.getInt(data[pos2 + 11], data[pos2 + 12]);
                        int sheet2 = IntegerHelper.getInt(data[pos2 + 13], data[pos2 + 14]);
                        this.ranges.add(new NameRange(this, sheet1, data[pos2 + 19], IntegerHelper.getInt(data[pos2 + 15], data[pos2 + 16]), data[pos2 + 20], IntegerHelper.getInt(data[pos2 + 17], data[pos2 + 18])));
                        pos2 += 21;
                    }
                } else if (data[pos2] == 41) {
                    if (!(pos2 >= data.length || data[pos2] == 58 || data[pos2] == 59)) {
                        if (data[pos2] == 41) {
                            pos2 += 3;
                        } else if (data[pos2] == 16) {
                            pos2++;
                        }
                    }
                    while (pos < data.length) {
                        int sheet12 = IntegerHelper.getInt(data[pos + 11], data[pos + 12]);
                        int sheet22 = IntegerHelper.getInt(data[pos + 13], data[pos + 14]);
                        this.ranges.add(new NameRange(this, sheet12, data[pos + 19], IntegerHelper.getInt(data[pos + 15], data[pos + 16]), data[pos + 20], IntegerHelper.getInt(data[pos + 17], data[pos + 18])));
                        pos += 21;
                        if (!(pos >= data.length || data[pos] == 58 || data[pos] == 59)) {
                            if (data[pos] == 41) {
                                pos += 3;
                            } else if (data[pos] == 16) {
                                pos++;
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            logger.warn("Cannot read name.");
            this.name = "ERROR";
        }
    }

    public String getName() {
        return this.name;
    }

    public NameRange[] getRanges() {
        return (NameRange[]) this.ranges.toArray(new NameRange[this.ranges.size()]);
    }

    /* access modifiers changed from: package-private */
    public int getIndex() {
        return this.index;
    }

    public int getSheetRef() {
        return this.sheetRef;
    }

    public void setSheetRef(int i) {
        this.sheetRef = i;
    }

    public byte[] getData() {
        return getRecord().getData();
    }

    public boolean isBiff8() {
        return this.isbiff8;
    }
}

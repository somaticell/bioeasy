package jxl.biff;

import common.Logger;
import jxl.Cell;
import jxl.WorkbookSettings;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.biff.formula.FormulaParser;

public class DVParser {
    public static final DVType ANY = new DVType(0);
    public static final Condition BETWEEN = new Condition(0);
    public static final DVType DATE = new DVType(4);
    public static final DVType DECIMAL = new DVType(2);
    private static int EMPTY_CELLS_ALLOWED_MASK = 256;
    public static final Condition EQUAL = new Condition(2);
    public static final DVType FORMULA = new DVType(7);
    public static final Condition GREATER_EQUAL = new Condition(6);
    public static final Condition GREATER_THAN = new Condition(4);
    public static final ErrorStyle INFO = new ErrorStyle(2);
    public static final DVType INTEGER = new DVType(1);
    public static final Condition LESS_EQUAL = new Condition(7);
    public static final Condition LESS_THAN = new Condition(5);
    public static final DVType LIST = new DVType(3);
    public static final Condition NOT_BETWEEN = new Condition(1);
    public static final Condition NOT_EQUAL = new Condition(3);
    private static int SHOW_ERROR_MASK = 524288;
    private static int SHOW_PROMPT_MASK = 262144;
    public static final ErrorStyle STOP = new ErrorStyle(0);
    private static int STRING_LIST_GIVEN_MASK = 128;
    private static int SUPPRESS_ARROW_MASK = 512;
    public static final DVType TEXT_LENGTH = new DVType(6);
    public static final DVType TIME = new DVType(5);
    public static final ErrorStyle WARNING = new ErrorStyle(1);
    static Class class$jxl$biff$DVParser;
    private static Logger logger;
    private int column1;
    private int column2;
    private Condition condition;
    private boolean emptyCellsAllowed;
    private ErrorStyle errorStyle;
    private String errorText;
    private String errorTitle;
    private FormulaParser formula1;
    private FormulaParser formula2;
    private String promptText;
    private String promptTitle;
    private int row1;
    private int row2;
    private boolean showError;
    private boolean showPrompt;
    private boolean stringListGiven;
    private boolean suppressArrow;
    private DVType type;

    static {
        Class cls;
        if (class$jxl$biff$DVParser == null) {
            cls = class$("jxl.biff.DVParser");
            class$jxl$biff$DVParser = cls;
        } else {
            cls = class$jxl$biff$DVParser;
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

    public static class DVType {
        private static DVType[] types = new DVType[0];
        private int value;

        DVType(int v) {
            this.value = v;
            DVType[] oldtypes = types;
            types = new DVType[(oldtypes.length + 1)];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            types[oldtypes.length] = this;
        }

        static DVType getType(int v) {
            DVType found = null;
            for (int i = 0; i < types.length && found == null; i++) {
                if (types[i].value == v) {
                    found = types[i];
                }
            }
            return found;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class ErrorStyle {
        private static ErrorStyle[] types = new ErrorStyle[0];
        private int value;

        ErrorStyle(int v) {
            this.value = v;
            ErrorStyle[] oldtypes = types;
            types = new ErrorStyle[(oldtypes.length + 1)];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            types[oldtypes.length] = this;
        }

        static ErrorStyle getErrorStyle(int v) {
            ErrorStyle found = null;
            for (int i = 0; i < types.length && found == null; i++) {
                if (types[i].value == v) {
                    found = types[i];
                }
            }
            return found;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class Condition {
        private static Condition[] types = new Condition[0];
        private int value;

        Condition(int v) {
            this.value = v;
            Condition[] oldtypes = types;
            types = new Condition[(oldtypes.length + 1)];
            System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
            types[oldtypes.length] = this;
        }

        static Condition getCondition(int v) {
            Condition found = null;
            for (int i = 0; i < types.length && found == null; i++) {
                if (types[i].value == v) {
                    found = types[i];
                }
            }
            return found;
        }

        public int getValue() {
            return this.value;
        }
    }

    public DVParser(byte[] data, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) throws FormulaException {
        int options = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
        this.type = DVType.getType(options & 15);
        this.errorStyle = ErrorStyle.getErrorStyle((options & 112) >> 4);
        this.condition = Condition.getCondition((15728640 & options) >> 20);
        this.stringListGiven = (STRING_LIST_GIVEN_MASK & options) != 0;
        this.emptyCellsAllowed = (EMPTY_CELLS_ALLOWED_MASK & options) != 0;
        this.suppressArrow = (SUPPRESS_ARROW_MASK & options) != 0;
        this.showPrompt = (SHOW_PROMPT_MASK & options) != 0;
        this.showError = (SHOW_ERROR_MASK & options) != 0;
        int length = IntegerHelper.getInt(data[4], data[5]);
        this.promptTitle = StringHelper.getUnicodeString(data, length, 6);
        int pos = 4 + (length * 2) + 2;
        int length2 = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.errorTitle = StringHelper.getUnicodeString(data, length2, pos + 2);
        int pos2 = pos + (length2 * 2) + 2;
        int length3 = IntegerHelper.getInt(data[pos2], data[pos2 + 1]);
        this.promptText = StringHelper.getUnicodeString(data, length3, pos2 + 2);
        int pos3 = pos2 + (length3 * 2) + 2;
        int length4 = IntegerHelper.getInt(data[pos3], data[pos3 + 1]);
        this.errorText = StringHelper.getUnicodeString(data, length4, pos3 + 2);
        int pos4 = pos3 + (length4 * 2) + 2;
        int formulaLength = IntegerHelper.getInt(data[pos4], data[pos4 + 1]);
        int pos5 = pos4 + 4;
        if (formulaLength != 0) {
            byte[] tokens = new byte[formulaLength];
            System.arraycopy(data, pos5, tokens, 0, formulaLength);
            this.formula1 = new FormulaParser(tokens, (Cell) null, es, nt, ws);
            this.formula1.parse();
            pos5 += formulaLength;
        }
        int formulaLength2 = IntegerHelper.getInt(data[pos5], data[pos5 + 1]);
        int pos6 = pos5 + 4;
        if (formulaLength2 != 0) {
            byte[] tokens2 = new byte[formulaLength2];
            System.arraycopy(data, pos6, tokens2, 0, formulaLength2);
            this.formula2 = new FormulaParser(tokens2, (Cell) null, es, nt, ws);
            this.formula2.parse();
            pos6 += formulaLength2;
        }
        int pos7 = pos6 + 2;
        this.row1 = IntegerHelper.getInt(data[pos7], data[pos7 + 1]);
        int pos8 = pos7 + 2;
        this.row2 = IntegerHelper.getInt(data[pos8], data[pos8 + 1]);
        int pos9 = pos8 + 2;
        this.column1 = IntegerHelper.getInt(data[pos9], data[pos9 + 1]);
        int pos10 = pos9 + 2;
        this.column2 = IntegerHelper.getInt(data[pos10], data[pos10 + 1]);
        int pos11 = pos10 + 2;
    }

    public byte[] getData() {
        byte[] f1Bytes = this.formula1 != null ? this.formula1.getBytes() : new byte[0];
        byte[] f2Bytes = this.formula2 != null ? this.formula2.getBytes() : new byte[0];
        byte[] data = new byte[((this.promptTitle.length() * 2) + 4 + 2 + (this.errorTitle.length() * 2) + 2 + (this.promptText.length() * 2) + 2 + (this.errorText.length() * 2) + 2 + f1Bytes.length + 2 + f2Bytes.length + 2 + 4 + 10)];
        int options = 0 | this.type.getValue() | (this.errorStyle.getValue() << 4) | (this.condition.getValue() << 20);
        if (this.stringListGiven) {
            options |= STRING_LIST_GIVEN_MASK;
        }
        if (this.emptyCellsAllowed) {
            options |= EMPTY_CELLS_ALLOWED_MASK;
        }
        if (this.suppressArrow) {
            options |= SUPPRESS_ARROW_MASK;
        }
        if (this.showPrompt) {
            options |= SHOW_PROMPT_MASK;
        }
        if (this.showError) {
            options |= SHOW_ERROR_MASK;
        }
        IntegerHelper.getFourBytes(options, data, 0);
        int pos = 0 + 4;
        IntegerHelper.getTwoBytes(this.promptTitle.length(), data, pos);
        StringHelper.getUnicodeBytes(this.promptTitle, data, pos + 2);
        int pos2 = (this.promptTitle.length() * 2) + 6;
        IntegerHelper.getTwoBytes(this.errorTitle.length(), data, pos2);
        int pos3 = pos2 + 2;
        StringHelper.getUnicodeBytes(this.errorTitle, data, pos3);
        int pos4 = pos3 + (this.errorTitle.length() * 2);
        IntegerHelper.getTwoBytes(this.promptText.length(), data, pos4);
        int pos5 = pos4 + 2;
        StringHelper.getUnicodeBytes(this.promptText, data, pos5);
        int pos6 = pos5 + (this.promptText.length() * 2);
        IntegerHelper.getTwoBytes(this.errorText.length(), data, pos6);
        int pos7 = pos6 + 2;
        StringHelper.getUnicodeBytes(this.errorText, data, pos7);
        int pos8 = pos7 + (this.errorText.length() * 2);
        IntegerHelper.getTwoBytes(f1Bytes.length, data, pos8);
        int pos9 = pos8 + 4;
        System.arraycopy(f1Bytes, 0, data, pos9, f1Bytes.length);
        int pos10 = pos9 + f1Bytes.length;
        IntegerHelper.getTwoBytes(f2Bytes.length, data, pos10);
        int pos11 = pos10 + 2;
        System.arraycopy(f2Bytes, 0, data, pos11, f2Bytes.length);
        int pos12 = pos11 + f2Bytes.length + 2;
        IntegerHelper.getTwoBytes(1, data, pos12);
        int pos13 = pos12 + 2;
        IntegerHelper.getTwoBytes(this.row1, data, pos13);
        int pos14 = pos13 + 2;
        IntegerHelper.getTwoBytes(this.row2, data, pos14);
        int pos15 = pos14 + 2;
        IntegerHelper.getTwoBytes(this.column1, data, pos15);
        int pos16 = pos15 + 2;
        IntegerHelper.getTwoBytes(this.column2, data, pos16);
        int pos17 = pos16 + 2;
        return data;
    }

    public void insertRow(int row) {
        if (this.formula1 != null) {
            this.formula1.rowInserted(0, row, true);
        }
        if (this.formula2 != null) {
            this.formula2.rowInserted(0, row, true);
        }
        if (this.row1 >= row) {
            this.row1++;
        }
        if (this.row2 >= row) {
            this.row2++;
        }
    }

    public void insertColumn(int col) {
        if (this.formula1 != null) {
            this.formula1.columnInserted(0, col, true);
        }
        if (this.formula2 != null) {
            this.formula2.columnInserted(0, col, true);
        }
        if (this.column1 >= col) {
            this.column1++;
        }
        if (this.column2 >= col) {
            this.column2++;
        }
    }

    public void removeRow(int row) {
        if (this.formula1 != null) {
            this.formula1.rowRemoved(0, row, true);
        }
        if (this.formula2 != null) {
            this.formula2.rowRemoved(0, row, true);
        }
        if (this.row1 > row) {
            this.row1--;
        }
        if (this.row2 >= row) {
            this.row2--;
        }
    }

    public void removeColumn(int col) {
        if (this.formula1 != null) {
            this.formula1.columnRemoved(0, col, true);
        }
        if (this.formula2 != null) {
            this.formula2.columnRemoved(0, col, true);
        }
        if (this.column1 > col) {
            this.column1--;
        }
        if (this.column2 >= col) {
            this.column2--;
        }
    }

    public int getFirstColumn() {
        return this.column1;
    }

    public int getLastColumn() {
        return this.column2;
    }

    public int getFirstRow() {
        return this.row1;
    }

    public int getLastRow() {
        return this.row2;
    }
}

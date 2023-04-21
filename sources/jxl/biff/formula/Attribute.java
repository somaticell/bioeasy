package jxl.biff.formula;

import common.Logger;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;

class Attribute extends Operator implements ParsedThing {
    static Class class$jxl$biff$formula$Attribute = null;
    private static final int gotoMask = 8;
    private static final int ifMask = 2;
    private static Logger logger = null;
    private static final int sumMask = 16;
    private VariableArgFunction ifConditions;
    private int options;
    private WorkbookSettings settings;
    private int word;

    static {
        Class cls;
        if (class$jxl$biff$formula$Attribute == null) {
            cls = class$("jxl.biff.formula.Attribute");
            class$jxl$biff$formula$Attribute = cls;
        } else {
            cls = class$jxl$biff$formula$Attribute;
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

    public Attribute(WorkbookSettings ws) {
        this.settings = ws;
    }

    public Attribute(StringFunction sf, WorkbookSettings ws) {
        this.settings = ws;
        if (sf.getFunction(this.settings) == Function.SUM) {
            this.options |= 16;
        } else if (sf.getFunction(this.settings) == Function.IF) {
            this.options |= 2;
        }
    }

    /* access modifiers changed from: package-private */
    public void setIfConditions(VariableArgFunction vaf) {
        this.ifConditions = vaf;
        this.options |= 2;
    }

    public int read(byte[] data, int pos) {
        this.options = data[pos];
        this.word = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
        return 3;
    }

    public boolean isFunction() {
        return (this.options & 18) != 0;
    }

    public boolean isSum() {
        return (this.options & 16) != 0;
    }

    public boolean isIf() {
        return (this.options & 2) != 0;
    }

    public boolean isGoto() {
        return (this.options & 8) != 0;
    }

    public void getOperands(Stack s) {
        if ((this.options & 16) != 0) {
            add((ParseItem) s.pop());
        } else if ((this.options & 2) != 0) {
            add((ParseItem) s.pop());
        }
    }

    public void getString(StringBuffer buf) {
        if ((this.options & 16) != 0) {
            ParseItem[] operands = getOperands();
            buf.append(Function.SUM.getName(this.settings));
            buf.append('(');
            operands[0].getString(buf);
            buf.append(')');
        } else if ((this.options & 2) != 0) {
            buf.append(Function.IF.getName(this.settings));
            buf.append('(');
            ParseItem[] operands2 = this.ifConditions.getOperands();
            for (int i = 0; i < operands2.length - 1; i++) {
                operands2[i].getString(buf);
                buf.append(',');
            }
            operands2[operands2.length - 1].getString(buf);
            buf.append(')');
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = new byte[0];
        if (isSum()) {
            ParseItem[] operands = getOperands();
            for (int i = operands.length - 1; i >= 0; i--) {
                byte[] opdata = operands[i].getBytes();
                byte[] newdata = new byte[(data.length + opdata.length)];
                System.arraycopy(data, 0, newdata, 0, data.length);
                System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
                data = newdata;
            }
            byte[] newdata2 = new byte[(data.length + 4)];
            System.arraycopy(data, 0, newdata2, 0, data.length);
            newdata2[data.length] = Token.ATTRIBUTE.getCode();
            newdata2[data.length + 1] = 16;
            data = newdata2;
        } else if (isIf()) {
            return getIf();
        }
        return data;
    }

    private byte[] getIf() {
        ParseItem[] operands = this.ifConditions.getOperands();
        int numArgs = operands.length;
        byte[] data = operands[0].getBytes();
        int pos = data.length;
        byte[] newdata = new byte[(data.length + 4)];
        System.arraycopy(data, 0, newdata, 0, data.length);
        byte[] data2 = newdata;
        data2[pos] = Token.ATTRIBUTE.getCode();
        data2[pos + 1] = 2;
        int falseOffsetPos = pos + 2;
        byte[] truedata = operands[1].getBytes();
        byte[] newdata2 = new byte[(data2.length + truedata.length)];
        System.arraycopy(data2, 0, newdata2, 0, data2.length);
        System.arraycopy(truedata, 0, newdata2, data2.length, truedata.length);
        byte[] data3 = newdata2;
        int pos2 = data3.length;
        byte[] newdata3 = new byte[(data3.length + 4)];
        System.arraycopy(data3, 0, newdata3, 0, data3.length);
        byte[] data4 = newdata3;
        data4[pos2] = Token.ATTRIBUTE.getCode();
        data4[pos2 + 1] = 8;
        int gotoEndPos = pos2 + 2;
        if (numArgs > 2) {
            IntegerHelper.getTwoBytes((data4.length - falseOffsetPos) - 2, data4, falseOffsetPos);
            byte[] falsedata = operands[numArgs - 1].getBytes();
            byte[] newdata4 = new byte[(data4.length + falsedata.length)];
            System.arraycopy(data4, 0, newdata4, 0, data4.length);
            System.arraycopy(falsedata, 0, newdata4, data4.length, falsedata.length);
            byte[] data5 = newdata4;
            int pos3 = data5.length;
            byte[] newdata5 = new byte[(data5.length + 4)];
            System.arraycopy(data5, 0, newdata5, 0, data5.length);
            data4 = newdata5;
            data4[pos3] = Token.ATTRIBUTE.getCode();
            data4[pos3 + 1] = 8;
            data4[pos3 + 2] = 3;
        }
        int pos4 = data4.length;
        byte[] newdata6 = new byte[(data4.length + 4)];
        System.arraycopy(data4, 0, newdata6, 0, data4.length);
        byte[] data6 = newdata6;
        data6[pos4] = Token.FUNCTIONVARARG.getCode();
        data6[pos4 + 1] = (byte) numArgs;
        data6[pos4 + 2] = 1;
        data6[pos4 + 3] = 0;
        int endPos = data6.length - 1;
        if (numArgs < 3) {
            IntegerHelper.getTwoBytes((endPos - falseOffsetPos) - 5, data6, falseOffsetPos);
        }
        IntegerHelper.getTwoBytes((endPos - gotoEndPos) - 2, data6, gotoEndPos);
        return data6;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 3;
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        ParseItem[] operands;
        if (isIf()) {
            operands = this.ifConditions.getOperands();
        } else {
            operands = getOperands();
        }
        for (ParseItem adjustRelativeCellReferences : operands) {
            adjustRelativeCellReferences.adjustRelativeCellReferences(colAdjust, rowAdjust);
        }
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands;
        if (isIf()) {
            operands = this.ifConditions.getOperands();
        } else {
            operands = getOperands();
        }
        for (ParseItem columnInserted : operands) {
            columnInserted.columnInserted(sheetIndex, col, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands;
        if (isIf()) {
            operands = this.ifConditions.getOperands();
        } else {
            operands = getOperands();
        }
        for (ParseItem columnRemoved : operands) {
            columnRemoved.columnRemoved(sheetIndex, col, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands;
        if (isIf()) {
            operands = this.ifConditions.getOperands();
        } else {
            operands = getOperands();
        }
        for (ParseItem rowInserted : operands) {
            rowInserted.rowInserted(sheetIndex, row, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands;
        if (isIf()) {
            operands = this.ifConditions.getOperands();
        } else {
            operands = getOperands();
        }
        for (ParseItem rowRemoved : operands) {
            rowRemoved.rowRemoved(sheetIndex, row, currentSheet);
        }
    }
}

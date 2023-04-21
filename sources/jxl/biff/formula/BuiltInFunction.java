package jxl.biff.formula;

import common.Assert;
import common.Logger;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;

class BuiltInFunction extends Operator implements ParsedThing {
    static Class class$jxl$biff$formula$BuiltInFunction;
    private static Logger logger;
    private Function function;
    private WorkbookSettings settings;

    static {
        Class cls;
        if (class$jxl$biff$formula$BuiltInFunction == null) {
            cls = class$("jxl.biff.formula.BuiltInFunction");
            class$jxl$biff$formula$BuiltInFunction = cls;
        } else {
            cls = class$jxl$biff$formula$BuiltInFunction;
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

    public BuiltInFunction(WorkbookSettings ws) {
        this.settings = ws;
    }

    public BuiltInFunction(Function f, WorkbookSettings ws) {
        this.function = f;
        this.settings = ws;
    }

    public int read(byte[] data, int pos) {
        int index = IntegerHelper.getInt(data[pos], data[pos + 1]);
        this.function = Function.getFunction(index);
        Function function2 = this.function;
        Function function3 = this.function;
        Assert.verify(function2 != Function.UNKNOWN, new StringBuffer().append("function code ").append(index).toString());
        return 2;
    }

    public void getOperands(Stack s) {
        ParseItem[] items = new ParseItem[this.function.getNumArgs()];
        for (int i = this.function.getNumArgs() - 1; i >= 0; i--) {
            items[i] = (ParseItem) s.pop();
        }
        for (int i2 = 0; i2 < this.function.getNumArgs(); i2++) {
            add(items[i2]);
        }
    }

    public void getString(StringBuffer buf) {
        buf.append(this.function.getName(this.settings));
        buf.append('(');
        int numArgs = this.function.getNumArgs();
        if (numArgs > 0) {
            ParseItem[] operands = getOperands();
            operands[0].getString(buf);
            for (int i = 1; i < numArgs; i++) {
                buf.append(',');
                operands[i].getString(buf);
            }
        }
        buf.append(')');
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        ParseItem[] operands = getOperands();
        for (ParseItem adjustRelativeCellReferences : operands) {
            adjustRelativeCellReferences.adjustRelativeCellReferences(colAdjust, rowAdjust);
        }
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        for (ParseItem columnInserted : operands) {
            columnInserted.columnInserted(sheetIndex, col, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        for (ParseItem columnRemoved : operands) {
            columnRemoved.columnRemoved(sheetIndex, col, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        for (ParseItem rowInserted : operands) {
            rowInserted.rowInserted(sheetIndex, row, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        for (ParseItem rowRemoved : operands) {
            rowRemoved.rowRemoved(sheetIndex, row, currentSheet);
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        ParseItem[] operands = getOperands();
        byte[] data = new byte[0];
        for (ParseItem bytes : operands) {
            byte[] opdata = bytes.getBytes();
            byte[] newdata = new byte[(data.length + opdata.length)];
            System.arraycopy(data, 0, newdata, 0, data.length);
            System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
            data = newdata;
        }
        byte[] newdata2 = new byte[(data.length + 3)];
        System.arraycopy(data, 0, newdata2, 0, data.length);
        newdata2[data.length] = !useAlternateCode() ? Token.FUNCTION.getCode() : Token.FUNCTION.getCode2();
        IntegerHelper.getTwoBytes(this.function.getCode(), newdata2, data.length + 1);
        return newdata2;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 3;
    }
}

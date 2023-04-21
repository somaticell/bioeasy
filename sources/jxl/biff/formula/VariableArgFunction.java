package jxl.biff.formula;

import common.Logger;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;

class VariableArgFunction extends Operator implements ParsedThing {
    static Class class$jxl$biff$formula$VariableArgFunction;
    private static Logger logger;
    private int arguments;
    private Function function;
    private boolean readFromSheet = true;
    private WorkbookSettings settings;

    static {
        Class cls;
        if (class$jxl$biff$formula$VariableArgFunction == null) {
            cls = class$("jxl.biff.formula.VariableArgFunction");
            class$jxl$biff$formula$VariableArgFunction = cls;
        } else {
            cls = class$jxl$biff$formula$VariableArgFunction;
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

    public VariableArgFunction(WorkbookSettings ws) {
        this.settings = ws;
    }

    public VariableArgFunction(Function f, int a, WorkbookSettings ws) {
        this.function = f;
        this.arguments = a;
        this.settings = ws;
    }

    public int read(byte[] data, int pos) throws FormulaException {
        this.arguments = data[pos];
        int index = IntegerHelper.getInt(data[pos + 1], data[pos + 2]);
        this.function = Function.getFunction(index);
        Function function2 = this.function;
        Function function3 = this.function;
        if (function2 != Function.UNKNOWN) {
            return 3;
        }
        throw new FormulaException(FormulaException.unrecognizedFunction, index);
    }

    public void getOperands(Stack s) {
        ParseItem[] items = new ParseItem[this.arguments];
        for (int i = this.arguments - 1; i >= 0; i--) {
            items[i] = (ParseItem) s.pop();
        }
        for (int i2 = 0; i2 < this.arguments; i2++) {
            add(items[i2]);
        }
    }

    public void getString(StringBuffer buf) {
        buf.append(this.function.getName(this.settings));
        buf.append('(');
        if (this.arguments > 0) {
            ParseItem[] operands = getOperands();
            if (this.readFromSheet) {
                operands[0].getString(buf);
                for (int i = 1; i < this.arguments; i++) {
                    buf.append(',');
                    operands[i].getString(buf);
                }
            } else {
                operands[this.arguments - 1].getString(buf);
                for (int i2 = this.arguments - 2; i2 >= 0; i2--) {
                    buf.append(',');
                    operands[i2].getString(buf);
                }
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
    public Function getFunction() {
        return this.function;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        handleSpecialCases();
        ParseItem[] operands = getOperands();
        byte[] data = new byte[0];
        for (ParseItem bytes : operands) {
            byte[] opdata = bytes.getBytes();
            byte[] newdata = new byte[(data.length + opdata.length)];
            System.arraycopy(data, 0, newdata, 0, data.length);
            System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
            data = newdata;
        }
        byte[] newdata2 = new byte[(data.length + 4)];
        System.arraycopy(data, 0, newdata2, 0, data.length);
        newdata2[data.length] = !useAlternateCode() ? Token.FUNCTIONVARARG.getCode() : Token.FUNCTIONVARARG.getCode2();
        newdata2[data.length + 1] = (byte) this.arguments;
        IntegerHelper.getTwoBytes(this.function.getCode(), newdata2, data.length + 2);
        return newdata2;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 3;
    }

    private void handleSpecialCases() {
        if (this.function == Function.SUMPRODUCT) {
            ParseItem[] operands = getOperands();
            for (int i = operands.length - 1; i >= 0; i--) {
                if (operands[i] instanceof Area) {
                    operands[i].setAlternateCode();
                }
            }
        }
    }
}

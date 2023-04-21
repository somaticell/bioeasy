package jxl.biff.formula;

import java.util.Stack;

abstract class BinaryOperator extends Operator implements ParsedThing {
    /* access modifiers changed from: package-private */
    public abstract String getSymbol();

    /* access modifiers changed from: package-private */
    public abstract Token getToken();

    public int read(byte[] data, int pos) {
        return 0;
    }

    public void getOperands(Stack s) {
        add((ParseItem) s.pop());
        add((ParseItem) s.pop());
    }

    public void getString(StringBuffer buf) {
        ParseItem[] operands = getOperands();
        operands[1].getString(buf);
        buf.append(getSymbol());
        operands[0].getString(buf);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        ParseItem[] operands = getOperands();
        operands[1].adjustRelativeCellReferences(colAdjust, rowAdjust);
        operands[0].adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        operands[1].columnInserted(sheetIndex, col, currentSheet);
        operands[0].columnInserted(sheetIndex, col, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        operands[1].columnRemoved(sheetIndex, col, currentSheet);
        operands[0].columnRemoved(sheetIndex, col, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        operands[1].rowInserted(sheetIndex, row, currentSheet);
        operands[0].rowInserted(sheetIndex, row, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        ParseItem[] operands = getOperands();
        operands[1].rowRemoved(sheetIndex, row, currentSheet);
        operands[0].rowRemoved(sheetIndex, row, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        ParseItem[] operands = getOperands();
        byte[] data = new byte[0];
        for (int i = operands.length - 1; i >= 0; i--) {
            byte[] opdata = operands[i].getBytes();
            byte[] newdata = new byte[(data.length + opdata.length)];
            System.arraycopy(data, 0, newdata, 0, data.length);
            System.arraycopy(opdata, 0, newdata, data.length, opdata.length);
            data = newdata;
        }
        byte[] newdata2 = new byte[(data.length + 1)];
        System.arraycopy(data, 0, newdata2, 0, data.length);
        newdata2[data.length] = getToken().getCode();
        return newdata2;
    }
}

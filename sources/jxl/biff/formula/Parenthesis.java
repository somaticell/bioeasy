package jxl.biff.formula;

import java.util.Stack;

class Parenthesis extends Operator implements ParsedThing {
    public int read(byte[] data, int pos) {
        return 0;
    }

    public void getOperands(Stack s) {
        add((ParseItem) s.pop());
    }

    public void getString(StringBuffer buf) {
        ParseItem[] operands = getOperands();
        buf.append('(');
        operands[0].getString(buf);
        buf.append(')');
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        getOperands()[0].adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        getOperands()[0].columnInserted(sheetIndex, col, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        getOperands()[0].columnRemoved(sheetIndex, col, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        getOperands()[0].rowInserted(sheetIndex, row, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        getOperands()[0].rowRemoved(sheetIndex, row, currentSheet);
    }

    /* access modifiers changed from: package-private */
    public Token getToken() {
        return Token.PARENTHESIS;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        byte[] data = getOperands()[0].getBytes();
        byte[] newdata = new byte[(data.length + 1)];
        System.arraycopy(data, 0, newdata, 0, data.length);
        newdata[data.length] = getToken().getCode();
        return newdata;
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        return 4;
    }
}

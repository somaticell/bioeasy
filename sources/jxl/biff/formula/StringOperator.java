package jxl.biff.formula;

import common.Assert;
import java.util.Stack;

abstract class StringOperator extends Operator {
    /* access modifiers changed from: package-private */
    public abstract Operator getBinaryOperator();

    /* access modifiers changed from: package-private */
    public abstract Operator getUnaryOperator();

    protected StringOperator() {
    }

    public void getOperands(Stack s) {
        Assert.verify(false);
    }

    /* access modifiers changed from: package-private */
    public int getPrecedence() {
        Assert.verify(false);
        return 0;
    }

    /* access modifiers changed from: package-private */
    public byte[] getBytes() {
        Assert.verify(false);
        return null;
    }

    /* access modifiers changed from: package-private */
    public void getString(StringBuffer buf) {
        Assert.verify(false);
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        Assert.verify(false);
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        Assert.verify(false);
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        Assert.verify(false);
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        Assert.verify(false);
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        Assert.verify(false);
    }
}

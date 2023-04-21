package jxl.biff.formula;

abstract class Operand extends ParseItem {
    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
    }
}

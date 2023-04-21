package jxl;

import jxl.format.CellFormat;

public final class CellView {
    private boolean depUsed = false;
    private int dimension = 1;
    private CellFormat format;
    private boolean hidden = false;
    private int size = 1;

    public void setHidden(boolean h) {
        this.hidden = h;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setDimension(int d) {
        this.dimension = d;
        this.depUsed = true;
    }

    public void setSize(int d) {
        this.size = d;
        this.depUsed = false;
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getSize() {
        return this.size;
    }

    public void setFormat(CellFormat cf) {
        this.format = cf;
    }

    public CellFormat getFormat() {
        return this.format;
    }

    public boolean depUsed() {
        return this.depUsed;
    }
}

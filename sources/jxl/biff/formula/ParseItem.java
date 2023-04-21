package jxl.biff.formula;

abstract class ParseItem {
    private boolean alternateCode = false;
    private ParseItem parent;
    private boolean volatileFunction = false;

    /* access modifiers changed from: package-private */
    public abstract void adjustRelativeCellReferences(int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract void columnInserted(int i, int i2, boolean z);

    /* access modifiers changed from: package-private */
    public abstract void columnRemoved(int i, int i2, boolean z);

    /* access modifiers changed from: package-private */
    public abstract byte[] getBytes();

    /* access modifiers changed from: package-private */
    public abstract void getString(StringBuffer stringBuffer);

    /* access modifiers changed from: package-private */
    public abstract void rowInserted(int i, int i2, boolean z);

    /* access modifiers changed from: package-private */
    public abstract void rowRemoved(int i, int i2, boolean z);

    /* access modifiers changed from: protected */
    public void setParent(ParseItem p) {
        this.parent = p;
    }

    /* access modifiers changed from: protected */
    public void setVolatile() {
        this.volatileFunction = true;
        if (this.parent != null && !this.parent.isVolatile()) {
            this.parent.setVolatile();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isVolatile() {
        return this.volatileFunction;
    }

    /* access modifiers changed from: protected */
    public void setAlternateCode() {
        this.alternateCode = true;
    }

    /* access modifiers changed from: protected */
    public final boolean useAlternateCode() {
        return this.alternateCode;
    }
}

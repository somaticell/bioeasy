package jxl.biff.drawing;

class BStoreContainer extends EscherContainer {
    private int numBlips;

    public BStoreContainer(EscherRecordData erd) {
        super(erd);
        this.numBlips = getInstance();
    }

    public BStoreContainer() {
        super(EscherRecordType.BSTORE_CONTAINER);
    }

    /* access modifiers changed from: package-private */
    public void setNumBlips(int count) {
        this.numBlips = count;
        setInstance(this.numBlips);
    }

    public int getNumBlips() {
        return this.numBlips;
    }

    public void getDrawing(int i) {
        BlipStoreEntry blipStoreEntry = (BlipStoreEntry) getChildren()[i];
    }
}

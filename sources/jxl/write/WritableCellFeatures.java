package jxl.write;

import jxl.CellFeatures;

public class WritableCellFeatures extends CellFeatures {
    public WritableCellFeatures() {
    }

    public WritableCellFeatures(CellFeatures cf) {
        super(cf);
    }

    public void setComment(String s) {
        super.setComment(s);
    }

    public void setComment(String s, double width, double height) {
        super.setComment(s, width, height);
    }

    public void removeComment() {
        super.removeComment();
    }
}

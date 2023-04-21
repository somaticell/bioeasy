package jxl.write;

import java.io.File;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;

public class WritableImage extends Drawing {
    public WritableImage(double x, double y, double width, double height, File image) {
        super(x, y, width, height, image);
    }

    public WritableImage(double x, double y, double width, double height, byte[] imageData) {
        super(x, y, width, height, imageData);
    }

    public WritableImage(DrawingGroupObject d, DrawingGroup dg) {
        super(d, dg);
    }

    public double getColumn() {
        return super.getX();
    }

    public void setColumn(double c) {
        super.setX(c);
    }

    public double getRow() {
        return super.getY();
    }

    public void setRow(double c) {
        super.setY(c);
    }

    public double getWidth() {
        return super.getWidth();
    }

    public void setWidth(double c) {
        super.setWidth(c);
    }

    public double getHeight() {
        return super.getHeight();
    }

    public void setHeight(double c) {
        super.setHeight(c);
    }

    public File getImageFile() {
        return super.getImageFile();
    }

    public byte[] getImageData() {
        return super.getImageData();
    }
}

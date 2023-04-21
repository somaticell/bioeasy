package jxl;

public interface Range {
    Cell getBottomRight();

    int getFirstSheetIndex();

    int getLastSheetIndex();

    Cell getTopLeft();
}

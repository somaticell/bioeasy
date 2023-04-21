package jxl;

import jxl.format.CellFormat;

public interface Sheet {
    Cell findCell(String str);

    LabelCell findLabelCell(String str);

    Cell getCell(int i, int i2);

    Cell[] getColumn(int i);

    CellFormat getColumnFormat(int i);

    CellView getColumnView(int i);

    int getColumnWidth(int i);

    int getColumns();

    Image getDrawing(int i);

    Hyperlink[] getHyperlinks();

    Range[] getMergedCells();

    String getName();

    int getNumberOfImages();

    Cell[] getRow(int i);

    int getRowHeight(int i);

    CellView getRowView(int i);

    int getRows();

    SheetSettings getSettings();

    boolean isHidden();

    boolean isProtected();
}

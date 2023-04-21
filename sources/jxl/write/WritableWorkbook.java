package jxl.write;

import java.io.File;
import java.io.IOException;
import jxl.Range;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;

public abstract class WritableWorkbook {
    public static final WritableFont ARIAL_10_PT = new WritableFont(WritableFont.ARIAL);
    public static final WritableCellFormat HIDDEN_STYLE = new WritableCellFormat((DisplayFormat) new DateFormat(";;;"));
    public static final WritableFont HYPERLINK_FONT = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE, Colour.BLUE);
    public static final WritableCellFormat HYPERLINK_STYLE = new WritableCellFormat(HYPERLINK_FONT);
    public static final WritableCellFormat NORMAL_STYLE = new WritableCellFormat(ARIAL_10_PT, NumberFormats.DEFAULT);

    public abstract void addNameArea(String str, WritableSheet writableSheet, int i, int i2, int i3, int i4);

    public abstract void close() throws IOException, WriteException;

    public abstract void copySheet(int i, String str, int i2);

    public abstract void copySheet(String str, String str2, int i);

    public abstract WritableSheet createSheet(String str, int i);

    public abstract Range[] findByName(String str);

    public abstract WritableCell findCellByName(String str);

    public abstract int getNumberOfSheets();

    public abstract String[] getRangeNames();

    public abstract WritableSheet getSheet(int i) throws IndexOutOfBoundsException;

    public abstract WritableSheet getSheet(String str);

    public abstract String[] getSheetNames();

    public abstract WritableSheet[] getSheets();

    public abstract WritableSheet moveSheet(int i, int i2);

    public abstract void removeSheet(int i);

    public abstract void setColourRGB(Colour colour, int i, int i2, int i3);

    public abstract void setOutputFile(File file) throws IOException;

    public abstract void setProtected(boolean z);

    public abstract void write() throws IOException;

    protected WritableWorkbook() {
    }

    public void copy(Workbook w) {
    }
}

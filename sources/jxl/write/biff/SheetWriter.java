package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import jxl.Cell;
import jxl.Range;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.XFRecord;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.SheetDrawingWriter;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Blank;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableHyperlink;
import jxl.write.WriteException;

final class SheetWriter {
    static Class class$jxl$write$biff$SheetWriter;
    private static Logger logger;
    private ButtonPropertySetRecord buttonPropertySet;
    private boolean chartOnly;
    private TreeSet columnFormats;
    private DataValidation dataValidation;
    private SheetDrawingWriter drawingWriter;
    private FooterRecord footer;
    private HeaderRecord header;
    private ArrayList hyperlinks;
    private MergedCells mergedCells;
    private int numCols;
    private int numRows;
    private File outputFile;
    private PLSRecord plsRecord;
    private ArrayList rowBreaks;
    private RowRecord[] rows;
    private SheetSettings settings;
    private WritableSheetImpl sheet;
    private WorkbookSettings workbookSettings;
    private WorkspaceInformationRecord workspaceOptions = new WorkspaceInformationRecord();

    static {
        Class cls;
        if (class$jxl$write$biff$SheetWriter == null) {
            cls = class$("jxl.write.biff.SheetWriter");
            class$jxl$write$biff$SheetWriter = cls;
        } else {
            cls = class$jxl$write$biff$SheetWriter;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public SheetWriter(File of, WritableSheetImpl wsi, WorkbookSettings ws) {
        this.outputFile = of;
        this.sheet = wsi;
        this.workbookSettings = ws;
        this.chartOnly = false;
        this.drawingWriter = new SheetDrawingWriter(ws);
    }

    public void write() throws IOException {
        Assert.verify(this.rows != null);
        if (this.chartOnly) {
            this.drawingWriter.write(this.outputFile);
            return;
        }
        int pos = this.outputFile.getPos();
        this.outputFile.write(new BOFRecord(BOFRecord.sheet));
        int numBlocks = this.numRows / 32;
        if (this.numRows - (numBlocks * 32) != 0) {
            numBlocks++;
        }
        int indexPos = this.outputFile.getPos();
        IndexRecord indexRecord = new IndexRecord(0, this.numRows, numBlocks);
        this.outputFile.write(indexRecord);
        this.outputFile.write(new CalcModeRecord(CalcModeRecord.automatic));
        this.outputFile.write(new CalcCountRecord(100));
        this.outputFile.write(new RefModeRecord());
        this.outputFile.write(new IterationRecord(false));
        this.outputFile.write(new DeltaRecord(0.001d));
        this.outputFile.write(new SaveRecalcRecord(true));
        this.outputFile.write(new PrintHeadersRecord(this.settings.getPrintHeaders()));
        this.outputFile.write(new PrintGridLinesRecord(this.settings.getPrintGridLines()));
        this.outputFile.write(new GridSetRecord(true));
        this.outputFile.write(new GuttersRecord());
        int defaultRowHeight = this.settings.getDefaultRowHeight();
        int defaultRowHeight2 = this.settings.getDefaultRowHeight();
        SheetSettings sheetSettings = this.settings;
        this.outputFile.write(new DefaultRowHeightRecord(defaultRowHeight, defaultRowHeight2 != 255));
        this.workspaceOptions.setFitToPages(this.settings.getFitToPages());
        this.outputFile.write(this.workspaceOptions);
        if (this.rowBreaks.size() > 0) {
            int[] rb = new int[this.rowBreaks.size()];
            for (int i = 0; i < rb.length; i++) {
                rb[i] = ((Integer) this.rowBreaks.get(i)).intValue();
            }
            this.outputFile.write(new HorizontalPageBreaksRecord(rb));
        }
        this.outputFile.write(new HeaderRecord(this.settings.getHeader().toString()));
        this.outputFile.write(new FooterRecord(this.settings.getFooter().toString()));
        this.outputFile.write(new HorizontalCentreRecord(this.settings.isHorizontalCentre()));
        this.outputFile.write(new VerticalCentreRecord(this.settings.isVerticalCentre()));
        if (this.settings.getLeftMargin() != this.settings.getDefaultWidthMargin()) {
            this.outputFile.write(new LeftMarginRecord(this.settings.getLeftMargin()));
        }
        if (this.settings.getRightMargin() != this.settings.getDefaultWidthMargin()) {
            this.outputFile.write(new RightMarginRecord(this.settings.getRightMargin()));
        }
        if (this.settings.getTopMargin() != this.settings.getDefaultHeightMargin()) {
            this.outputFile.write(new TopMarginRecord(this.settings.getTopMargin()));
        }
        if (this.settings.getBottomMargin() != this.settings.getDefaultHeightMargin()) {
            this.outputFile.write(new BottomMarginRecord(this.settings.getBottomMargin()));
        }
        if (this.plsRecord != null) {
            this.outputFile.write(this.plsRecord);
        }
        this.outputFile.write(new SetupRecord(this.settings));
        if (this.settings.isProtected()) {
            this.outputFile.write(new ProtectRecord(this.settings.isProtected()));
            this.outputFile.write(new ScenarioProtectRecord(this.settings.isProtected()));
            this.outputFile.write(new ObjectProtectRecord(this.settings.isProtected()));
            if (this.settings.getPassword() != null) {
                this.outputFile.write(new PasswordRecord(this.settings.getPassword()));
            } else if (this.settings.getPasswordHash() != 0) {
                this.outputFile.write(new PasswordRecord(this.settings.getPasswordHash()));
            }
        }
        indexRecord.setDataStartPosition(this.outputFile.getPos());
        this.outputFile.write(new DefaultColumnWidth(this.settings.getDefaultColumnWidth()));
        XFRecord normalStyle = this.sheet.getWorkbook().getStyles().getNormalStyle();
        WritableCellFormat defaultDateFormat = this.sheet.getWorkbook().getStyles().getDefaultDateFormat();
        Iterator colit = this.columnFormats.iterator();
        while (colit.hasNext()) {
            ColumnInfoRecord cir = (ColumnInfoRecord) colit.next();
            if (cir.getColumn() < 256) {
                this.outputFile.write(cir);
            }
            XFRecord xfr = cir.getCellFormat();
            if (xfr != normalStyle && cir.getColumn() < 256) {
                Cell[] cells = getColumn(cir.getColumn());
                for (int i2 = 0; i2 < cells.length; i2++) {
                    if (cells[i2] != null && (cells[i2].getCellFormat() == normalStyle || cells[i2].getCellFormat() == defaultDateFormat)) {
                        ((WritableCell) cells[i2]).setCellFormat(xfr);
                    }
                }
            }
        }
        this.outputFile.write(new DimensionRecord(this.numRows, this.numCols));
        for (int block = 0; block < numBlocks; block++) {
            DBCellRecord dbcell = new DBCellRecord(this.outputFile.getPos());
            int blockRows = Math.min(32, this.numRows - (block * 32));
            boolean firstRow = true;
            for (int i3 = block * 32; i3 < (block * 32) + blockRows; i3++) {
                if (this.rows[i3] != null) {
                    this.rows[i3].write(this.outputFile);
                    if (firstRow) {
                        dbcell.setCellOffset(this.outputFile.getPos());
                        firstRow = false;
                    }
                }
            }
            for (int i4 = block * 32; i4 < (block * 32) + blockRows; i4++) {
                if (this.rows[i4] != null) {
                    dbcell.addCellRowPosition(this.outputFile.getPos());
                    this.rows[i4].writeCells(this.outputFile);
                }
            }
            indexRecord.addBlockPosition(this.outputFile.getPos());
            dbcell.setPosition(this.outputFile.getPos());
            this.outputFile.write(dbcell);
        }
        if (this.dataValidation != null) {
            this.dataValidation.write(this.outputFile);
        }
        if (!this.workbookSettings.getDrawingsDisabled()) {
            this.drawingWriter.write(this.outputFile);
        }
        this.outputFile.write(new Window2Record(this.settings));
        if (this.settings.getHorizontalFreeze() == 0 && this.settings.getVerticalFreeze() == 0) {
            this.outputFile.write(new SelectionRecord(SelectionRecord.upperLeft, 0, 0));
        } else {
            this.outputFile.write(new PaneRecord(this.settings.getHorizontalFreeze(), this.settings.getVerticalFreeze()));
            this.outputFile.write(new SelectionRecord(SelectionRecord.upperLeft, 0, 0));
            if (this.settings.getHorizontalFreeze() != 0) {
                this.outputFile.write(new SelectionRecord(SelectionRecord.upperRight, this.settings.getHorizontalFreeze(), 0));
            }
            if (this.settings.getVerticalFreeze() != 0) {
                this.outputFile.write(new SelectionRecord(SelectionRecord.lowerLeft, 0, this.settings.getVerticalFreeze()));
            }
            if (!(this.settings.getHorizontalFreeze() == 0 || this.settings.getVerticalFreeze() == 0)) {
                this.outputFile.write(new SelectionRecord(SelectionRecord.lowerRight, this.settings.getHorizontalFreeze(), this.settings.getVerticalFreeze()));
            }
            this.outputFile.write(new Weird1Record());
        }
        if (this.settings.getZoomFactor() != 100) {
            this.outputFile.write(new SCLRecord(this.settings.getZoomFactor()));
        }
        this.mergedCells.write(this.outputFile);
        Iterator hi = this.hyperlinks.iterator();
        while (hi.hasNext()) {
            this.outputFile.write((WritableHyperlink) hi.next());
        }
        if (this.buttonPropertySet != null) {
            this.outputFile.write(this.buttonPropertySet);
        }
        this.outputFile.write(new EOFRecord());
        this.outputFile.setData(indexRecord.getData(), indexPos + 4);
    }

    /* access modifiers changed from: package-private */
    public final HeaderRecord getHeader() {
        return this.header;
    }

    /* access modifiers changed from: package-private */
    public final FooterRecord getFooter() {
        return this.footer;
    }

    /* access modifiers changed from: package-private */
    public void setWriteData(RowRecord[] rws, ArrayList rb, ArrayList hl, MergedCells mc, TreeSet cf) {
        this.rows = rws;
        this.rowBreaks = rb;
        this.hyperlinks = hl;
        this.mergedCells = mc;
        this.columnFormats = cf;
    }

    /* access modifiers changed from: package-private */
    public void setDimensions(int rws, int cls) {
        this.numRows = rws;
        this.numCols = cls;
    }

    /* access modifiers changed from: package-private */
    public void setSettings(SheetSettings sr) {
        this.settings = sr;
    }

    /* access modifiers changed from: package-private */
    public WorkspaceInformationRecord getWorkspaceOptions() {
        return this.workspaceOptions;
    }

    /* access modifiers changed from: package-private */
    public void setWorkspaceOptions(WorkspaceInformationRecord wo) {
        if (wo != null) {
            this.workspaceOptions = wo;
        }
    }

    /* access modifiers changed from: package-private */
    public void setCharts(Chart[] ch) {
        this.drawingWriter.setCharts(ch);
    }

    /* access modifiers changed from: package-private */
    public void setDrawings(ArrayList dr, boolean mod) {
        this.drawingWriter.setDrawings(dr, mod);
    }

    /* access modifiers changed from: package-private */
    public Chart[] getCharts() {
        return this.drawingWriter.getCharts();
    }

    /* access modifiers changed from: package-private */
    public void checkMergedBorders() {
        Range[] mcells = this.mergedCells.getMergedCells();
        ArrayList borderFormats = new ArrayList();
        for (int mci = 0; mci < mcells.length; mci++) {
            Range range = mcells[mci];
            Cell topLeft = range.getTopLeft();
            XFRecord tlformat = (XFRecord) topLeft.getCellFormat();
            if (tlformat != null && tlformat.hasBorders() && !tlformat.isRead()) {
                try {
                    CellXFRecord cf1 = new CellXFRecord(tlformat);
                    Cell bottomRight = range.getBottomRight();
                    cf1.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                    cf1.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                    cf1.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                    if (topLeft.getRow() == bottomRight.getRow()) {
                        cf1.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                    }
                    if (topLeft.getColumn() == bottomRight.getColumn()) {
                        cf1.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                    }
                    int index = borderFormats.indexOf(cf1);
                    if (index != -1) {
                        cf1 = (CellXFRecord) borderFormats.get(index);
                    } else {
                        borderFormats.add(cf1);
                    }
                    ((WritableCell) topLeft).setCellFormat(cf1);
                    if (bottomRight.getRow() > topLeft.getRow()) {
                        if (bottomRight.getColumn() != topLeft.getColumn()) {
                            CellXFRecord cf2 = new CellXFRecord(tlformat);
                            cf2.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                            cf2.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                            cf2.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                            int index2 = borderFormats.indexOf(cf2);
                            if (index2 != -1) {
                                cf2 = (CellXFRecord) borderFormats.get(index2);
                            } else {
                                borderFormats.add(cf2);
                            }
                            this.sheet.addCell(new Blank(topLeft.getColumn(), bottomRight.getRow(), (CellFormat) cf2));
                        }
                        for (int i = topLeft.getRow() + 1; i < bottomRight.getRow(); i++) {
                            CellXFRecord cf3 = new CellXFRecord(tlformat);
                            cf3.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                            cf3.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                            if (topLeft.getColumn() == bottomRight.getColumn()) {
                                cf3.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                            }
                            int index3 = borderFormats.indexOf(cf3);
                            if (index3 != -1) {
                                cf3 = (CellXFRecord) borderFormats.get(index3);
                            } else {
                                borderFormats.add(cf3);
                            }
                            this.sheet.addCell(new Blank(topLeft.getColumn(), i, (CellFormat) cf3));
                        }
                    }
                    if (bottomRight.getColumn() > topLeft.getColumn()) {
                        if (bottomRight.getRow() != topLeft.getRow()) {
                            CellXFRecord cf6 = new CellXFRecord(tlformat);
                            cf6.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                            cf6.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                            cf6.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                            int index4 = borderFormats.indexOf(cf6);
                            if (index4 != -1) {
                                cf6 = (CellXFRecord) borderFormats.get(index4);
                            } else {
                                borderFormats.add(cf6);
                            }
                            this.sheet.addCell(new Blank(bottomRight.getColumn(), topLeft.getRow(), (CellFormat) cf6));
                        }
                        for (int i2 = topLeft.getRow() + 1; i2 < bottomRight.getRow(); i2++) {
                            CellXFRecord cf7 = new CellXFRecord(tlformat);
                            cf7.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                            cf7.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                            int index5 = borderFormats.indexOf(cf7);
                            if (index5 != -1) {
                                cf7 = (CellXFRecord) borderFormats.get(index5);
                            } else {
                                borderFormats.add(cf7);
                            }
                            this.sheet.addCell(new Blank(bottomRight.getColumn(), i2, (CellFormat) cf7));
                        }
                        for (int i3 = topLeft.getColumn() + 1; i3 < bottomRight.getColumn(); i3++) {
                            CellXFRecord cf8 = new CellXFRecord(tlformat);
                            cf8.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                            cf8.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                            if (topLeft.getRow() == bottomRight.getRow()) {
                                cf8.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                            }
                            int index6 = borderFormats.indexOf(cf8);
                            if (index6 != -1) {
                                cf8 = (CellXFRecord) borderFormats.get(index6);
                            } else {
                                borderFormats.add(cf8);
                            }
                            this.sheet.addCell(new Blank(i3, topLeft.getRow(), (CellFormat) cf8));
                        }
                    }
                    if (bottomRight.getColumn() > topLeft.getColumn() || bottomRight.getRow() > topLeft.getRow()) {
                        CellXFRecord cf4 = new CellXFRecord(tlformat);
                        cf4.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                        cf4.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
                        cf4.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                        if (bottomRight.getRow() == topLeft.getRow()) {
                            cf4.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                        }
                        if (bottomRight.getColumn() == topLeft.getColumn()) {
                            cf4.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
                        }
                        int index7 = borderFormats.indexOf(cf4);
                        if (index7 != -1) {
                            cf4 = (CellXFRecord) borderFormats.get(index7);
                        } else {
                            borderFormats.add(cf4);
                        }
                        this.sheet.addCell(new Blank(bottomRight.getColumn(), bottomRight.getRow(), (CellFormat) cf4));
                        for (int i4 = topLeft.getColumn() + 1; i4 < bottomRight.getColumn(); i4++) {
                            CellXFRecord cf5 = new CellXFRecord(tlformat);
                            cf5.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
                            cf5.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
                            if (topLeft.getRow() == bottomRight.getRow()) {
                                cf5.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
                            }
                            int index8 = borderFormats.indexOf(cf5);
                            if (index8 != -1) {
                                cf5 = (CellXFRecord) borderFormats.get(index8);
                            } else {
                                borderFormats.add(cf5);
                            }
                            this.sheet.addCell(new Blank(i4, bottomRight.getRow(), (CellFormat) cf5));
                        }
                    }
                } catch (WriteException e) {
                    logger.warn(e.toString());
                }
            }
        }
    }

    private Cell[] getColumn(int col) {
        boolean found = false;
        int row = this.numRows - 1;
        while (row >= 0 && !found) {
            if (this.rows[row] == null || this.rows[row].getCell(col) == null) {
                row--;
            } else {
                found = true;
            }
        }
        Cell[] cells = new Cell[(row + 1)];
        for (int i = 0; i <= row; i++) {
            cells[i] = this.rows[i] != null ? this.rows[i].getCell(col) : null;
        }
        return cells;
    }

    /* access modifiers changed from: package-private */
    public void setChartOnly() {
        this.chartOnly = true;
    }

    /* access modifiers changed from: package-private */
    public void setPLS(PLSRecord pls) {
        this.plsRecord = pls;
    }

    /* access modifiers changed from: package-private */
    public void setButtonPropertySet(ButtonPropertySetRecord bps) {
        this.buttonPropertySet = bps;
    }

    /* access modifiers changed from: package-private */
    public void setDataValidation(DataValidation dv) {
        this.dataValidation = dv;
    }
}

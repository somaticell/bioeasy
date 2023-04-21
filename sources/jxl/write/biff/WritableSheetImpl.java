package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.DateCell;
import jxl.HeaderFooter;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.EmptyCell;
import jxl.biff.FormattingRecords;
import jxl.biff.FormulaData;
import jxl.biff.IndexMapping;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.SheetRangeImpl;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.XFRecord;
import jxl.biff.drawing.Button;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.Comment;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.read.biff.ColumnInfoRecord;
import jxl.read.biff.DataValidation;
import jxl.read.biff.RowRecord;
import jxl.read.biff.SheetImpl;
import jxl.write.Blank;
import jxl.write.Boolean;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

class WritableSheetImpl implements WritableSheet {
    static Class class$jxl$write$biff$WritableSheetImpl = null;
    private static final char[] illegalSheetNameCharacters = {'*', ':', '?', '\\'};
    private static final String[] imageTypes = {"png"};
    private static Logger logger = null;
    private static final int maxSheetNameLength = 31;
    private static final int numRowsPerSheet = 65536;
    private static final int rowGrowSize = 10;
    private ButtonPropertySetRecord buttonPropertySet;
    private boolean chartOnly = false;
    private TreeSet columnFormats;
    private DataValidation dataValidation;
    private ArrayList drawings;
    private boolean drawingsModified;
    private FormattingRecords formatRecords;
    private ArrayList hyperlinks;
    private ArrayList images;
    private MergedCells mergedCells;
    private String name;
    private int numColumns = 0;
    private int numRows = 0;
    private File outputFile;
    private PLSRecord plsRecord;
    private ArrayList rowBreaks;
    private RowRecord[] rows = new RowRecord[0];
    private SheetSettings settings;
    private SharedStrings sharedStrings;
    private SheetWriter sheetWriter;
    private WritableWorkbookImpl workbook;
    private WorkbookSettings workbookSettings;

    /* renamed from: jxl.write.biff.WritableSheetImpl$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$write$biff$WritableSheetImpl == null) {
            cls = class$("jxl.write.biff.WritableSheetImpl");
            class$jxl$write$biff$WritableSheetImpl = cls;
        } else {
            cls = class$jxl$write$biff$WritableSheetImpl;
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

    private static class ColumnInfoComparator implements Comparator {
        private ColumnInfoComparator() {
        }

        ColumnInfoComparator(AnonymousClass1 x0) {
            this();
        }

        public boolean equals(Object o) {
            return o == this;
        }

        public int compare(Object o1, Object o2) {
            if (o1 == o2) {
                return 0;
            }
            Assert.verify(o1 instanceof ColumnInfoRecord);
            Assert.verify(o2 instanceof ColumnInfoRecord);
            return ((ColumnInfoRecord) o1).getColumn() - ((ColumnInfoRecord) o2).getColumn();
        }
    }

    public WritableSheetImpl(String n, File of, FormattingRecords fr, SharedStrings ss, WorkbookSettings ws, WritableWorkbookImpl ww) {
        this.name = validateName(n);
        this.outputFile = of;
        this.workbook = ww;
        this.formatRecords = fr;
        this.sharedStrings = ss;
        this.workbookSettings = ws;
        this.drawingsModified = false;
        this.columnFormats = new TreeSet(new ColumnInfoComparator((AnonymousClass1) null));
        this.hyperlinks = new ArrayList();
        this.mergedCells = new MergedCells(this);
        this.rowBreaks = new ArrayList();
        this.drawings = new ArrayList();
        this.images = new ArrayList();
        this.settings = new SheetSettings();
        this.sheetWriter = new SheetWriter(this.outputFile, this, this.workbookSettings);
    }

    public Cell getCell(int column, int row) {
        return getWritableCell(column, row);
    }

    public WritableCell getWritableCell(int column, int row) {
        WritableCell c = null;
        if (row < this.rows.length && this.rows[row] != null) {
            c = this.rows[row].getCell(column);
        }
        if (c == null) {
            return new EmptyCell(column, row);
        }
        return c;
    }

    public int getRows() {
        return this.numRows;
    }

    public int getColumns() {
        return this.numColumns;
    }

    public Cell findCell(String contents) {
        Cell cell = null;
        boolean found = false;
        for (int i = 0; i < getRows() && !found; i++) {
            Cell[] row = getRow(i);
            for (int j = 0; j < row.length && !found; j++) {
                if (row[j].getContents().equals(contents)) {
                    cell = row[j];
                    found = true;
                }
            }
        }
        return cell;
    }

    public LabelCell findLabelCell(String contents) {
        LabelCell cell = null;
        boolean found = false;
        for (int i = 0; i < getRows() && !found; i++) {
            Cell[] row = getRow(i);
            for (int j = 0; j < row.length && !found; j++) {
                if ((row[j].getType() == CellType.LABEL || row[j].getType() == CellType.STRING_FORMULA) && row[j].getContents().equals(contents)) {
                    cell = (LabelCell) row[j];
                    found = true;
                }
            }
        }
        return cell;
    }

    public Cell[] getRow(int row) {
        boolean found = false;
        int col = this.numColumns - 1;
        while (col >= 0 && !found) {
            if (getCell(col, row).getType() != CellType.EMPTY) {
                found = true;
            } else {
                col--;
            }
        }
        Cell[] cells = new Cell[(col + 1)];
        for (int i = 0; i <= col; i++) {
            cells[i] = getCell(i, row);
        }
        return cells;
    }

    public Cell[] getColumn(int col) {
        boolean found = false;
        int row = this.numRows - 1;
        while (row >= 0 && !found) {
            if (getCell(col, row).getType() != CellType.EMPTY) {
                found = true;
            } else {
                row--;
            }
        }
        Cell[] cells = new Cell[(row + 1)];
        for (int i = 0; i <= row; i++) {
            cells[i] = getCell(col, i);
        }
        return cells;
    }

    public String getName() {
        return this.name;
    }

    public void insertRow(int row) {
        if (row >= 0 && row < this.numRows) {
            RowRecord[] oldRows = this.rows;
            if (this.numRows == this.rows.length) {
                this.rows = new RowRecord[(oldRows.length + 10)];
            } else {
                this.rows = new RowRecord[oldRows.length];
            }
            System.arraycopy(oldRows, 0, this.rows, 0, row);
            System.arraycopy(oldRows, row, this.rows, row + 1, this.numRows - row);
            for (int i = row + 1; i <= this.numRows; i++) {
                if (this.rows[i] != null) {
                    this.rows[i].incrementRow();
                }
            }
            Iterator i2 = this.hyperlinks.iterator();
            while (i2.hasNext()) {
                ((HyperlinkRecord) i2.next()).insertRow(row);
            }
            if (this.dataValidation != null) {
                this.dataValidation.insertRow(row);
            }
            this.mergedCells.insertRow(row);
            ArrayList newRowBreaks = new ArrayList();
            Iterator ri = this.rowBreaks.iterator();
            while (ri.hasNext()) {
                int val = ((Integer) ri.next()).intValue();
                if (val >= row) {
                    val++;
                }
                newRowBreaks.add(new Integer(val));
            }
            this.rowBreaks = newRowBreaks;
            if (this.workbookSettings.getFormulaAdjust()) {
                this.workbook.rowInserted(this, row);
            }
            this.numRows++;
        }
    }

    public void insertColumn(int col) {
        if (col >= 0 && col < this.numColumns) {
            for (int i = 0; i < this.numRows; i++) {
                if (this.rows[i] != null) {
                    this.rows[i].insertColumn(col);
                }
            }
            Iterator i2 = this.hyperlinks.iterator();
            while (i2.hasNext()) {
                ((HyperlinkRecord) i2.next()).insertColumn(col);
            }
            Iterator i3 = this.columnFormats.iterator();
            while (i3.hasNext()) {
                ColumnInfoRecord cir = (ColumnInfoRecord) i3.next();
                if (cir.getColumn() >= col) {
                    cir.incrementColumn();
                }
            }
            if (this.dataValidation != null) {
                this.dataValidation.insertColumn(col);
            }
            this.mergedCells.insertColumn(col);
            if (this.workbookSettings.getFormulaAdjust()) {
                this.workbook.columnInserted(this, col);
            }
            this.numColumns++;
        }
    }

    public void removeColumn(int col) {
        if (col >= 0 && col < this.numColumns) {
            for (int i = 0; i < this.numRows; i++) {
                if (this.rows[i] != null) {
                    this.rows[i].removeColumn(col);
                }
            }
            Iterator i2 = this.hyperlinks.iterator();
            while (i2.hasNext()) {
                HyperlinkRecord hr = (HyperlinkRecord) i2.next();
                if (hr.getColumn() == col && hr.getLastColumn() == col) {
                    this.hyperlinks.remove(this.hyperlinks.indexOf(hr));
                } else {
                    hr.removeColumn(col);
                }
            }
            if (this.dataValidation != null) {
                this.dataValidation.removeColumn(col);
            }
            this.mergedCells.removeColumn(col);
            Iterator i3 = this.columnFormats.iterator();
            ColumnInfoRecord removeColumn = null;
            while (i3.hasNext()) {
                ColumnInfoRecord cir = (ColumnInfoRecord) i3.next();
                if (cir.getColumn() == col) {
                    removeColumn = cir;
                } else if (cir.getColumn() > col) {
                    cir.decrementColumn();
                }
            }
            if (removeColumn != null) {
                this.columnFormats.remove(removeColumn);
            }
            if (this.workbookSettings.getFormulaAdjust()) {
                this.workbook.columnRemoved(this, col);
            }
            this.numColumns--;
        }
    }

    public void removeRow(int row) {
        if (row >= 0 && row < this.numRows) {
            RowRecord[] oldRows = this.rows;
            this.rows = new RowRecord[oldRows.length];
            System.arraycopy(oldRows, 0, this.rows, 0, row);
            System.arraycopy(oldRows, row + 1, this.rows, row, this.numRows - (row + 1));
            for (int i = row; i < this.numRows; i++) {
                if (this.rows[i] != null) {
                    this.rows[i].decrementRow();
                }
            }
            Iterator i2 = this.hyperlinks.iterator();
            while (i2.hasNext()) {
                HyperlinkRecord hr = (HyperlinkRecord) i2.next();
                if (hr.getRow() == row && hr.getLastRow() == row) {
                    i2.remove();
                } else {
                    hr.removeRow(row);
                }
            }
            if (this.dataValidation != null) {
                this.dataValidation.removeRow(row);
            }
            this.mergedCells.removeRow(row);
            ArrayList newRowBreaks = new ArrayList();
            Iterator ri = this.rowBreaks.iterator();
            while (ri.hasNext()) {
                int val = ((Integer) ri.next()).intValue();
                if (val != row) {
                    if (val > row) {
                        val--;
                    }
                    newRowBreaks.add(new Integer(val));
                }
            }
            this.rowBreaks = newRowBreaks;
            if (this.workbookSettings.getFormulaAdjust()) {
                this.workbook.rowRemoved(this, row);
            }
            this.numRows--;
        }
    }

    public void addCell(WritableCell cell) throws WriteException, RowsExceededException {
        if (cell.getType() != CellType.EMPTY || cell == null || cell.getCellFormat() != null) {
            CellValue cv = (CellValue) cell;
            if (cv.isReferenced()) {
                throw new JxlWriteException(JxlWriteException.cellReferenced);
            }
            int row = cell.getRow();
            RowRecord rowrec = getRowRecord(row);
            rowrec.addCell(cv);
            this.numRows = Math.max(row + 1, this.numRows);
            this.numColumns = Math.max(this.numColumns, rowrec.getMaxColumn());
            cv.setCellDetails(this.formatRecords, this.sharedStrings, this);
        }
    }

    private RowRecord getRowRecord(int row) throws RowsExceededException {
        if (row >= 65536) {
            throw new RowsExceededException();
        }
        if (row >= this.rows.length) {
            RowRecord[] oldRows = this.rows;
            this.rows = new RowRecord[Math.max(oldRows.length + 10, row + 1)];
            System.arraycopy(oldRows, 0, this.rows, 0, oldRows.length);
        }
        RowRecord rowrec = this.rows[row];
        if (rowrec != null) {
            return rowrec;
        }
        RowRecord rowrec2 = new RowRecord(row);
        this.rows[row] = rowrec2;
        return rowrec2;
    }

    /* access modifiers changed from: package-private */
    public RowRecord getRowInfo(int r) {
        if (r < 0 || r > this.rows.length) {
            return null;
        }
        return this.rows[r];
    }

    /* access modifiers changed from: package-private */
    public ColumnInfoRecord getColumnInfo(int c) {
        Iterator i = this.columnFormats.iterator();
        ColumnInfoRecord cir = null;
        boolean stop = false;
        while (i.hasNext() && !stop) {
            cir = (ColumnInfoRecord) i.next();
            if (cir.getColumn() >= c) {
                stop = true;
            }
        }
        if (!stop) {
            return null;
        }
        if (cir.getColumn() != c) {
            cir = null;
        }
        return cir;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setHidden(boolean h) {
        this.settings.setHidden(h);
    }

    public void setProtected(boolean prot) {
        this.settings.setProtected(prot);
    }

    public void setSelected() {
        this.settings.setSelected();
    }

    public boolean isHidden() {
        return this.settings.isHidden();
    }

    public void setColumnView(int col, int width) {
        CellView cv = new CellView();
        cv.setSize(width * 256);
        setColumnView(col, cv);
    }

    public void setColumnView(int col, int width, CellFormat format) {
        CellView cv = new CellView();
        cv.setSize(width * 256);
        cv.setFormat(format);
        setColumnView(col, cv);
    }

    public void setColumnView(int col, CellView view) {
        XFRecord xfr = (XFRecord) view.getFormat();
        if (xfr == null) {
            xfr = getWorkbook().getStyles().getNormalStyle();
        }
        try {
            if (!xfr.isInitialized()) {
                this.formatRecords.addStyle(xfr);
            }
            ColumnInfoRecord cir = new ColumnInfoRecord(col, view.depUsed() ? view.getDimension() * 256 : view.getSize(), xfr);
            if (view.isHidden()) {
                cir.setHidden(true);
            }
            if (!this.columnFormats.contains(cir)) {
                this.columnFormats.add(cir);
                return;
            }
            boolean remove = this.columnFormats.remove(cir);
            this.columnFormats.add(cir);
        } catch (NumFormatRecordsException e) {
            logger.warn("Maximum number of format records exceeded.  Using default format.");
            ColumnInfoRecord cir2 = new ColumnInfoRecord(col, view.getDimension() * 256, (XFRecord) WritableWorkbook.NORMAL_STYLE);
            if (!this.columnFormats.contains(cir2)) {
                this.columnFormats.add(cir2);
            }
        }
    }

    public void setRowView(int row, int height) throws RowsExceededException {
        setRowView(row, height, false);
    }

    public void setRowView(int row, boolean collapsed) throws RowsExceededException {
        getRowRecord(row).setCollapsed(collapsed);
    }

    public void setRowView(int row, int height, boolean collapsed) throws RowsExceededException {
        RowRecord rowrec = getRowRecord(row);
        rowrec.setRowHeight(height);
        rowrec.setCollapsed(collapsed);
    }

    public void write() throws IOException {
        boolean dmod = this.drawingsModified;
        if (this.workbook.getDrawingGroup() != null) {
            dmod |= this.workbook.getDrawingGroup().hasDrawingsOmitted();
        }
        this.sheetWriter.setWriteData(this.rows, this.rowBreaks, this.hyperlinks, this.mergedCells, this.columnFormats);
        this.sheetWriter.setDimensions(getRows(), getColumns());
        this.sheetWriter.setSettings(this.settings);
        this.sheetWriter.setPLS(this.plsRecord);
        this.sheetWriter.setDrawings(this.drawings, dmod);
        this.sheetWriter.setButtonPropertySet(this.buttonPropertySet);
        this.sheetWriter.setDataValidation(this.dataValidation);
        this.sheetWriter.write();
    }

    private void copyCells(Sheet s) {
        int cells = s.getRows();
        for (int i = 0; i < cells; i++) {
            Cell[] row = s.getRow(i);
            for (Cell cell : row) {
                CellType ct = cell.getType();
                try {
                    if (ct == CellType.LABEL) {
                        addCell(new Label((LabelCell) cell));
                    } else if (ct == CellType.NUMBER) {
                        addCell(new Number((NumberCell) cell));
                    } else if (ct == CellType.DATE) {
                        addCell(new DateTime((DateCell) cell));
                    } else if (ct == CellType.BOOLEAN) {
                        addCell(new Boolean((BooleanCell) cell));
                    } else if (ct == CellType.NUMBER_FORMULA) {
                        addCell(new ReadNumberFormulaRecord((FormulaData) cell));
                    } else if (ct == CellType.STRING_FORMULA) {
                        addCell(new ReadStringFormulaRecord((FormulaData) cell));
                    } else if (ct == CellType.BOOLEAN_FORMULA) {
                        addCell(new ReadBooleanFormulaRecord((FormulaData) cell));
                    } else if (ct == CellType.DATE_FORMULA) {
                        addCell(new ReadDateFormulaRecord((FormulaData) cell));
                    } else if (ct == CellType.FORMULA_ERROR) {
                        addCell(new ReadErrorFormulaRecord((FormulaData) cell));
                    } else if (ct == CellType.EMPTY && cell.getCellFormat() != null) {
                        addCell(new Blank(cell));
                    }
                } catch (WriteException e) {
                    Assert.verify(false);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void copy(Sheet s) {
        this.settings = new SheetSettings(s.getSettings());
        copyCells(s);
        SheetImpl si = (SheetImpl) s;
        ColumnInfoRecord[] readCirs = si.getColumnInfos();
        for (ColumnInfoRecord rcir : readCirs) {
            for (int j = rcir.getStartColumn(); j <= rcir.getEndColumn(); j++) {
                ColumnInfoRecord cir = new ColumnInfoRecord(rcir, j, this.formatRecords);
                cir.setHidden(rcir.getHidden());
                this.columnFormats.add(cir);
            }
        }
        Hyperlink[] hls = s.getHyperlinks();
        for (Hyperlink writableHyperlink : hls) {
            this.hyperlinks.add(new WritableHyperlink(writableHyperlink, this));
        }
        Range[] merged = s.getMergedCells();
        for (Range range : merged) {
            this.mergedCells.add(new SheetRangeImpl((SheetRangeImpl) range, this));
        }
        try {
            RowRecord[] rowprops = si.getRowProperties();
            for (int i = 0; i < rowprops.length; i++) {
                getRowRecord(rowprops[i].getRowNumber()).setRowDetails(rowprops[i].getRowHeight(), rowprops[i].matchesDefaultFontHeight(), rowprops[i].isCollapsed(), rowprops[i].hasDefaultFormat() ? this.formatRecords.getXFRecord(rowprops[i].getXFIndex()) : null);
            }
        } catch (RowsExceededException e) {
            Assert.verify(false);
        }
        int[] rowbreaks = si.getRowPageBreaks();
        if (rowbreaks != null) {
            for (int num : rowbreaks) {
                this.rowBreaks.add(new Integer(num));
            }
        }
        DataValidation rdv = si.getDataValidation();
        if (rdv != null) {
            this.dataValidation = new DataValidation(rdv, this.workbook, this.workbookSettings);
        }
        this.sheetWriter.setCharts(si.getCharts());
        DrawingGroupObject[] dr = si.getDrawings();
        for (int i2 = 0; i2 < dr.length; i2++) {
            if (dr[i2] instanceof Drawing) {
                WritableImage wi = new WritableImage(dr[i2], this.workbook.getDrawingGroup());
                this.drawings.add(wi);
                this.images.add(wi);
            } else if (dr[i2] instanceof Comment) {
                Comment c = new Comment(dr[i2], this.workbook.getDrawingGroup(), this.workbookSettings);
                this.drawings.add(c);
                CellValue cv = (CellValue) getWritableCell(c.getColumn(), c.getRow());
                Assert.verify(cv.getCellFeatures() != null);
                cv.getWritableCellFeatures().setCommentDrawing(c);
            } else if (dr[i2] instanceof Button) {
                this.drawings.add(new Button(dr[i2], this.workbook.getDrawingGroup(), this.workbookSettings));
            }
        }
        this.sheetWriter.setWorkspaceOptions(si.getWorkspaceOptions());
        if (si.getSheetBof().isChart()) {
            this.chartOnly = true;
            this.sheetWriter.setChartOnly();
        }
        if (si.getPLS() != null) {
            if (si.getWorkbookBof().isBiff7()) {
                logger.warn("Cannot copy Biff7 print settings record - ignoring");
            } else {
                this.plsRecord = new PLSRecord(si.getPLS());
            }
        }
        if (si.getButtonPropertySet() != null) {
            this.buttonPropertySet = new ButtonPropertySetRecord(si.getButtonPropertySet());
        }
    }

    /* access modifiers changed from: package-private */
    public void copy(WritableSheet s) {
        this.settings = new SheetSettings(s.getSettings());
        copyCells(s);
        this.columnFormats = ((WritableSheetImpl) s).columnFormats;
        Range[] merged = s.getMergedCells();
        for (Range range : merged) {
            this.mergedCells.add(new SheetRangeImpl((SheetRangeImpl) range, this));
        }
        try {
            RowRecord[] copyRows = ((WritableSheetImpl) s).rows;
            for (int i = 0; i < copyRows.length; i++) {
                RowRecord row = copyRows[i];
                if (row != null && (!row.isDefaultHeight() || row.isCollapsed())) {
                    getRowRecord(i).setRowDetails(row.getRowHeight(), row.matchesDefaultFontHeight(), row.isCollapsed(), row.getStyle());
                }
            }
        } catch (RowsExceededException e) {
            Assert.verify(false);
        }
        WritableSheetImpl si = (WritableSheetImpl) s;
        this.rowBreaks = new ArrayList(si.rowBreaks);
        DataValidation rdv = si.dataValidation;
        if (rdv != null) {
            this.dataValidation = new DataValidation(rdv, this.workbook, this.workbookSettings);
        }
        this.sheetWriter.setCharts(si.getCharts());
        DrawingGroupObject[] dr = si.getDrawings();
        for (int i2 = 0; i2 < dr.length; i2++) {
            if (dr[i2] instanceof Drawing) {
                WritableImage wi = new WritableImage(dr[i2], this.workbook.getDrawingGroup());
                this.drawings.add(wi);
                this.images.add(wi);
            } else if (dr[i2] instanceof Comment) {
                this.drawings.add(new Comment(dr[i2], this.workbook.getDrawingGroup(), this.workbookSettings));
            }
        }
        this.sheetWriter.setWorkspaceOptions(si.getWorkspaceOptions());
        if (si.plsRecord != null) {
            this.plsRecord = new PLSRecord(si.plsRecord);
        }
        if (si.buttonPropertySet != null) {
            this.buttonPropertySet = new ButtonPropertySetRecord(si.buttonPropertySet);
        }
    }

    /* access modifiers changed from: package-private */
    public final HeaderRecord getHeader() {
        return this.sheetWriter.getHeader();
    }

    /* access modifiers changed from: package-private */
    public final FooterRecord getFooter() {
        return this.sheetWriter.getFooter();
    }

    public boolean isProtected() {
        return this.settings.isProtected();
    }

    public Hyperlink[] getHyperlinks() {
        Hyperlink[] hl = new Hyperlink[this.hyperlinks.size()];
        for (int i = 0; i < this.hyperlinks.size(); i++) {
            hl[i] = (Hyperlink) this.hyperlinks.get(i);
        }
        return hl;
    }

    public Range[] getMergedCells() {
        return this.mergedCells.getMergedCells();
    }

    public WritableHyperlink[] getWritableHyperlinks() {
        WritableHyperlink[] hl = new WritableHyperlink[this.hyperlinks.size()];
        for (int i = 0; i < this.hyperlinks.size(); i++) {
            hl[i] = (WritableHyperlink) this.hyperlinks.get(i);
        }
        return hl;
    }

    public void removeHyperlink(WritableHyperlink h) {
        removeHyperlink(h, false);
    }

    public void removeHyperlink(WritableHyperlink h, boolean preserveLabel) {
        this.hyperlinks.remove(this.hyperlinks.indexOf(h));
        if (!preserveLabel) {
            Assert.verify(this.rows.length > h.getRow() && this.rows[h.getRow()] != null);
            this.rows[h.getRow()].removeCell(h.getColumn());
        }
    }

    public void addHyperlink(WritableHyperlink h) throws WriteException, RowsExceededException {
        Cell c = getCell(h.getColumn(), h.getRow());
        String contents = null;
        if (h.isFile() || h.isUNC()) {
            String cnts = h.getContents();
            if (cnts == null) {
                contents = h.getFile().getPath();
            } else {
                contents = cnts;
            }
        } else if (h.isURL()) {
            String cnts2 = h.getContents();
            if (cnts2 == null) {
                contents = h.getURL().toString();
            } else {
                contents = cnts2;
            }
        } else if (h.isLocation()) {
            contents = h.getContents();
        }
        if (c.getType() == CellType.LABEL) {
            Label l = (Label) c;
            l.setString(contents);
            l.setCellFormat(WritableWorkbook.HYPERLINK_STYLE);
        } else {
            addCell(new Label(h.getColumn(), h.getRow(), contents, WritableWorkbook.HYPERLINK_STYLE));
        }
        for (int i = h.getRow(); i <= h.getLastRow(); i++) {
            for (int j = h.getColumn(); j <= h.getLastColumn(); j++) {
                if (!(i == h.getRow() || j == h.getColumn() || this.rows[i] == null)) {
                    this.rows[i].removeCell(j);
                }
            }
        }
        h.initialize(this);
        this.hyperlinks.add(h);
    }

    public Range mergeCells(int col1, int row1, int col2, int row2) throws WriteException, RowsExceededException {
        if (col2 < col1 || row2 < row1) {
            logger.warn("Cannot merge cells - top left and bottom right incorrectly specified");
        }
        if (col2 >= this.numColumns || row2 >= this.numRows) {
            addCell(new Blank(col2, row2));
        }
        SheetRangeImpl range = new SheetRangeImpl(this, col1, row1, col2, row2);
        this.mergedCells.add(range);
        return range;
    }

    public void unmergeCells(Range r) {
        this.mergedCells.unmergeCells(r);
    }

    public void setHeader(String l, String c, String r) {
        HeaderFooter header = new HeaderFooter();
        header.getLeft().append(l);
        header.getCentre().append(c);
        header.getRight().append(r);
        this.settings.setHeader(header);
    }

    public void setFooter(String l, String c, String r) {
        HeaderFooter footer = new HeaderFooter();
        footer.getLeft().append(l);
        footer.getCentre().append(c);
        footer.getRight().append(r);
        this.settings.setFooter(footer);
    }

    public void setPageSetup(PageOrientation p) {
        this.settings.setOrientation(p);
    }

    public void setPageSetup(PageOrientation p, double hm, double fm) {
        this.settings.setOrientation(p);
        this.settings.setHeaderMargin(hm);
        this.settings.setFooterMargin(fm);
    }

    public void setPageSetup(PageOrientation p, PaperSize ps, double hm, double fm) {
        this.settings.setPaperSize(ps);
        this.settings.setOrientation(p);
        this.settings.setHeaderMargin(hm);
        this.settings.setFooterMargin(fm);
    }

    public SheetSettings getSettings() {
        return this.settings;
    }

    /* access modifiers changed from: package-private */
    public WorkbookSettings getWorkbookSettings() {
        return this.workbookSettings;
    }

    public void addRowPageBreak(int row) {
        Iterator i = this.rowBreaks.iterator();
        boolean found = false;
        while (i.hasNext() && !found) {
            if (((Integer) i.next()).intValue() == row) {
                found = true;
            }
        }
        if (!found) {
            this.rowBreaks.add(new Integer(row));
        }
    }

    private Chart[] getCharts() {
        return this.sheetWriter.getCharts();
    }

    private DrawingGroupObject[] getDrawings() {
        return (DrawingGroupObject[]) this.drawings.toArray(new DrawingGroupObject[this.drawings.size()]);
    }

    /* access modifiers changed from: package-private */
    public void checkMergedBorders() {
        this.sheetWriter.setWriteData(this.rows, this.rowBreaks, this.hyperlinks, this.mergedCells, this.columnFormats);
        this.sheetWriter.setDimensions(getRows(), getColumns());
        this.sheetWriter.checkMergedBorders();
    }

    private WorkspaceInformationRecord getWorkspaceOptions() {
        return this.sheetWriter.getWorkspaceOptions();
    }

    /* access modifiers changed from: package-private */
    public void rationalize(IndexMapping xfMapping, IndexMapping fontMapping, IndexMapping formatMapping) {
        Iterator i = this.columnFormats.iterator();
        while (i.hasNext()) {
            ((ColumnInfoRecord) i.next()).rationalize(xfMapping);
        }
        for (int i2 = 0; i2 < this.rows.length; i2++) {
            if (this.rows[i2] != null) {
                this.rows[i2].rationalize(xfMapping);
            }
        }
        Chart[] charts = getCharts();
        for (Chart rationalize : charts) {
            rationalize.rationalize(xfMapping, fontMapping, formatMapping);
        }
    }

    /* access modifiers changed from: package-private */
    public WritableWorkbookImpl getWorkbook() {
        return this.workbook;
    }

    public CellFormat getColumnFormat(int col) {
        return getColumnView(col).getFormat();
    }

    public int getColumnWidth(int col) {
        return getColumnView(col).getDimension();
    }

    public int getRowHeight(int row) {
        return getRowView(row).getDimension();
    }

    /* access modifiers changed from: package-private */
    public boolean isChartOnly() {
        return this.chartOnly;
    }

    public CellView getRowView(int row) {
        CellView cv = new CellView();
        try {
            RowRecord rr = getRowRecord(row);
            if (rr == null || rr.isDefaultHeight()) {
                cv.setDimension(this.settings.getDefaultRowHeight());
                cv.setSize(this.settings.getDefaultRowHeight());
                return cv;
            }
            if (rr.isCollapsed()) {
                cv.setHidden(true);
            } else {
                cv.setDimension(rr.getRowHeight());
                cv.setSize(rr.getRowHeight());
            }
            return cv;
        } catch (RowsExceededException e) {
            cv.setDimension(this.settings.getDefaultRowHeight());
            cv.setSize(this.settings.getDefaultRowHeight());
        }
    }

    public CellView getColumnView(int col) {
        ColumnInfoRecord cir = getColumnInfo(col);
        CellView cv = new CellView();
        if (cir != null) {
            cv.setDimension(cir.getWidth() / 256);
            cv.setSize(cir.getWidth());
            cv.setHidden(cir.getHidden());
            cv.setFormat(cir.getCellFormat());
        } else {
            cv.setDimension(this.settings.getDefaultColumnWidth() / 256);
            cv.setSize(this.settings.getDefaultColumnWidth());
        }
        return cv;
    }

    public void addImage(WritableImage image) {
        boolean supported = false;
        File imageFile = image.getImageFile();
        String fileType = "?";
        if (imageFile != null) {
            String fileName = imageFile.getName();
            int fileTypeIndex = fileName.lastIndexOf(46);
            fileType = fileTypeIndex != -1 ? fileName.substring(fileTypeIndex + 1) : "";
            for (int i = 0; i < imageTypes.length && !supported; i++) {
                if (fileType.equalsIgnoreCase(imageTypes[i])) {
                    supported = true;
                }
            }
        } else {
            supported = true;
        }
        if (supported) {
            this.workbook.addDrawing(image);
            this.drawings.add(image);
            this.images.add(image);
            return;
        }
        StringBuffer message = new StringBuffer("Image type ");
        message.append(fileType);
        message.append(" not supported.  Supported types are ");
        message.append(imageTypes[0]);
        for (int i2 = 1; i2 < imageTypes.length; i2++) {
            message.append(", ");
            message.append(imageTypes[i2]);
        }
        logger.warn(message.toString());
    }

    public int getNumberOfImages() {
        return this.images.size();
    }

    public WritableImage getImage(int i) {
        return (WritableImage) this.images.get(i);
    }

    public Image getDrawing(int i) {
        return (Image) this.images.get(i);
    }

    public void removeImage(WritableImage wi) {
        boolean remove = this.drawings.remove(wi);
        this.images.remove(wi);
        this.drawingsModified = true;
        this.workbook.removeDrawing(wi);
    }

    private String validateName(String n) {
        if (n.length() > 31) {
            logger.warn(new StringBuffer().append("Sheet name ").append(n).append(" too long - truncating").toString());
            n = n.substring(0, 31);
        }
        if (n.charAt(0) == '\'') {
            logger.warn("Sheet naming cannot start with ' - removing");
            n = n.substring(1);
        }
        for (int i = 0; i < illegalSheetNameCharacters.length; i++) {
            String newname = n.replace(illegalSheetNameCharacters[i], '@');
            if (n != newname) {
                logger.warn(new StringBuffer().append(illegalSheetNameCharacters[i]).append(" is not a valid character within a sheet name - replacing").toString());
            }
            n = newname;
        }
        return n;
    }

    /* access modifiers changed from: package-private */
    public void addDrawing(DrawingGroupObject o) {
        this.drawings.add(o);
        Assert.verify(!(o instanceof Drawing));
    }

    /* access modifiers changed from: package-private */
    public void removeDrawing(DrawingGroupObject o) {
        boolean z = true;
        int origSize = this.drawings.size();
        this.drawings.remove(o);
        int newSize = this.drawings.size();
        this.drawingsModified = true;
        if (newSize != origSize - 1) {
            z = false;
        }
        Assert.verify(z);
    }
}

package jxl.read.biff;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.EmptyCell;
import jxl.biff.FormattingRecords;
import jxl.biff.Type;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.format.CellFormat;

public class SheetImpl implements Sheet {
    private ButtonPropertySetRecord buttonPropertySet;
    private Cell[][] cells;
    private ArrayList charts;
    private ColumnInfoRecord[] columnInfos;
    private ArrayList columnInfosArray = new ArrayList();
    private boolean columnInfosInitialized = false;
    private DataValidation dataValidation;
    private ArrayList drawings;
    private File excelFile;
    private FormattingRecords formattingRecords;
    private boolean hidden;
    private ArrayList hyperlinks = new ArrayList();
    private ArrayList images;
    private Range[] mergedCells;
    private String name;
    private boolean nineteenFour;
    private int numCols;
    private int numRows;
    private PLSRecord plsRecord;
    private int[] rowBreaks;
    private ArrayList rowProperties = new ArrayList(10);
    private RowRecord[] rowRecords;
    private boolean rowRecordsInitialized = false;
    private SheetSettings settings;
    private ArrayList sharedFormulas = new ArrayList();
    private SSTRecord sharedStrings;
    private BOFRecord sheetBof;
    private int startPosition;
    private WorkbookParser workbook;
    private BOFRecord workbookBof;
    private WorkbookSettings workbookSettings;
    private WorkspaceInformationRecord workspaceOptions;

    SheetImpl(File f, SSTRecord sst, FormattingRecords fr, BOFRecord sb, BOFRecord wb, boolean nf, WorkbookParser wp) throws BiffException {
        this.excelFile = f;
        this.sharedStrings = sst;
        this.formattingRecords = fr;
        this.sheetBof = sb;
        this.workbookBof = wb;
        this.nineteenFour = nf;
        this.workbook = wp;
        this.workbookSettings = this.workbook.getSettings();
        this.startPosition = f.getPos();
        if (this.sheetBof.isChart()) {
            this.startPosition -= this.sheetBof.getLength() + 4;
        }
        int bofs = 1;
        while (bofs >= 1) {
            Record r = f.next();
            bofs = r.getCode() == Type.EOF.value ? bofs - 1 : bofs;
            if (r.getCode() == Type.BOF.value) {
                bofs++;
            }
        }
    }

    public Cell getCell(int column, int row) {
        if (this.cells == null) {
            readSheet();
        }
        Cell c = this.cells[row][column];
        if (c != null) {
            return c;
        }
        Cell c2 = new EmptyCell(column, row);
        this.cells[row][column] = c2;
        return c2;
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

    public int getRows() {
        if (this.cells == null) {
            readSheet();
        }
        return this.numRows;
    }

    public int getColumns() {
        if (this.cells == null) {
            readSheet();
        }
        return this.numCols;
    }

    public Cell[] getRow(int row) {
        if (this.cells == null) {
            readSheet();
        }
        boolean found = false;
        int col = this.numCols - 1;
        while (col >= 0 && !found) {
            if (this.cells[row][col] != null) {
                found = true;
            } else {
                col--;
            }
        }
        Cell[] c = new Cell[(col + 1)];
        for (int i = 0; i <= col; i++) {
            c[i] = getCell(i, row);
        }
        return c;
    }

    public Cell[] getColumn(int col) {
        if (this.cells == null) {
            readSheet();
        }
        boolean found = false;
        int row = this.numRows - 1;
        while (row >= 0 && !found) {
            if (this.cells[row][col] != null) {
                found = true;
            } else {
                row--;
            }
        }
        Cell[] c = new Cell[(row + 1)];
        for (int i = 0; i <= row; i++) {
            c[i] = getCell(col, i);
        }
        return c;
    }

    public String getName() {
        return this.name;
    }

    /* access modifiers changed from: package-private */
    public final void setName(String s) {
        this.name = s;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public ColumnInfoRecord getColumnInfo(int col) {
        if (!this.columnInfosInitialized) {
            Iterator i = this.columnInfosArray.iterator();
            while (i.hasNext()) {
                ColumnInfoRecord cir = (ColumnInfoRecord) i.next();
                int startcol = Math.max(0, cir.getStartColumn());
                int endcol = Math.min(this.columnInfos.length - 1, cir.getEndColumn());
                for (int c = startcol; c <= endcol; c++) {
                    this.columnInfos[c] = cir;
                }
                if (endcol < startcol) {
                    this.columnInfos[startcol] = cir;
                }
            }
            this.columnInfosInitialized = true;
        }
        if (col < this.columnInfos.length) {
            return this.columnInfos[col];
        }
        return null;
    }

    public ColumnInfoRecord[] getColumnInfos() {
        ColumnInfoRecord[] infos = new ColumnInfoRecord[this.columnInfosArray.size()];
        for (int i = 0; i < this.columnInfosArray.size(); i++) {
            infos[i] = (ColumnInfoRecord) this.columnInfosArray.get(i);
        }
        return infos;
    }

    /* access modifiers changed from: package-private */
    public final void setHidden(boolean h) {
        this.hidden = h;
    }

    /* access modifiers changed from: package-private */
    public final void clear() {
        this.cells = null;
        this.mergedCells = null;
        this.columnInfosArray.clear();
        this.sharedFormulas.clear();
        this.hyperlinks.clear();
        this.columnInfosInitialized = false;
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
    }

    /* access modifiers changed from: package-private */
    public final void readSheet() {
        if (!this.sheetBof.isWorksheet()) {
            this.numRows = 0;
            this.numCols = 0;
            this.cells = (Cell[][]) Array.newInstance(Cell.class, new int[]{0, 0});
        }
        SheetReader reader = new SheetReader(this.excelFile, this.sharedStrings, this.formattingRecords, this.sheetBof, this.workbookBof, this.nineteenFour, this.workbook, this.startPosition, this);
        reader.read();
        this.numRows = reader.getNumRows();
        this.numCols = reader.getNumCols();
        this.cells = reader.getCells();
        this.rowProperties = reader.getRowProperties();
        this.columnInfosArray = reader.getColumnInfosArray();
        this.hyperlinks = reader.getHyperlinks();
        this.charts = reader.getCharts();
        this.drawings = reader.getDrawings();
        this.dataValidation = reader.getDataValidation();
        this.mergedCells = reader.getMergedCells();
        this.settings = reader.getSettings();
        this.settings.setHidden(this.hidden);
        this.rowBreaks = reader.getRowBreaks();
        this.workspaceOptions = reader.getWorkspaceOptions();
        this.plsRecord = reader.getPLS();
        this.buttonPropertySet = reader.getButtonPropertySet();
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
        if (this.columnInfosArray.size() > 0) {
            this.columnInfos = new ColumnInfoRecord[(((ColumnInfoRecord) this.columnInfosArray.get(this.columnInfosArray.size() - 1)).getEndColumn() + 1)];
        } else {
            this.columnInfos = new ColumnInfoRecord[0];
        }
    }

    public Hyperlink[] getHyperlinks() {
        Hyperlink[] hl = new Hyperlink[this.hyperlinks.size()];
        for (int i = 0; i < this.hyperlinks.size(); i++) {
            hl[i] = (Hyperlink) this.hyperlinks.get(i);
        }
        return hl;
    }

    public Range[] getMergedCells() {
        if (this.mergedCells == null) {
            return new Range[0];
        }
        return this.mergedCells;
    }

    public RowRecord[] getRowProperties() {
        RowRecord[] rp = new RowRecord[this.rowProperties.size()];
        for (int i = 0; i < rp.length; i++) {
            rp[i] = (RowRecord) this.rowProperties.get(i);
        }
        return rp;
    }

    public DataValidation getDataValidation() {
        return this.dataValidation;
    }

    /* access modifiers changed from: package-private */
    public RowRecord getRowInfo(int r) {
        if (!this.rowRecordsInitialized) {
            this.rowRecords = new RowRecord[getRows()];
            Iterator i = this.rowProperties.iterator();
            while (i.hasNext()) {
                RowRecord rr = (RowRecord) i.next();
                int rownum = rr.getRowNumber();
                if (rownum < this.rowRecords.length) {
                    this.rowRecords[rownum] = rr;
                }
            }
            this.rowRecordsInitialized = true;
        }
        return this.rowRecords[r];
    }

    public final int[] getRowPageBreaks() {
        return this.rowBreaks;
    }

    public final Chart[] getCharts() {
        Chart[] ch = new Chart[this.charts.size()];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = (Chart) this.charts.get(i);
        }
        return ch;
    }

    public final DrawingGroupObject[] getDrawings() {
        return (DrawingGroupObject[]) this.drawings.toArray(new DrawingGroupObject[this.drawings.size()]);
    }

    public boolean isProtected() {
        return this.settings.isProtected();
    }

    public WorkspaceInformationRecord getWorkspaceOptions() {
        return this.workspaceOptions;
    }

    public SheetSettings getSettings() {
        return this.settings;
    }

    /* access modifiers changed from: package-private */
    public WorkbookParser getWorkbook() {
        return this.workbook;
    }

    public CellFormat getColumnFormat(int col) {
        return getColumnView(col).getFormat();
    }

    public int getColumnWidth(int col) {
        return getColumnView(col).getSize() / 256;
    }

    public CellView getColumnView(int col) {
        ColumnInfoRecord cir = getColumnInfo(col);
        CellView cv = new CellView();
        if (cir != null) {
            cv.setDimension(cir.getWidth() / 256);
            cv.setSize(cir.getWidth());
            cv.setHidden(cir.getHidden());
            cv.setFormat(this.formattingRecords.getXFRecord(cir.getXFIndex()));
        } else {
            cv.setDimension(this.settings.getDefaultColumnWidth() / 256);
            cv.setSize(this.settings.getDefaultColumnWidth());
        }
        return cv;
    }

    public int getRowHeight(int row) {
        return getRowView(row).getDimension();
    }

    public CellView getRowView(int row) {
        RowRecord rr = getRowInfo(row);
        CellView cv = new CellView();
        if (rr != null) {
            cv.setDimension(rr.getRowHeight());
            cv.setSize(rr.getRowHeight());
            cv.setHidden(rr.isCollapsed());
        } else {
            cv.setDimension(this.settings.getDefaultRowHeight());
            cv.setSize(this.settings.getDefaultRowHeight());
        }
        return cv;
    }

    public BOFRecord getSheetBof() {
        return this.sheetBof;
    }

    public BOFRecord getWorkbookBof() {
        return this.workbookBof;
    }

    public PLSRecord getPLS() {
        return this.plsRecord;
    }

    public ButtonPropertySetRecord getButtonPropertySet() {
        return this.buttonPropertySet;
    }

    public int getNumberOfImages() {
        if (this.images == null) {
            initializeImages();
        }
        return this.images.size();
    }

    public Image getDrawing(int i) {
        if (this.images == null) {
            initializeImages();
        }
        return (Image) this.images.get(i);
    }

    private void initializeImages() {
        if (this.images == null) {
            this.images = new ArrayList();
            DrawingGroupObject[] dgos = getDrawings();
            for (int i = 0; i < dgos.length; i++) {
                if (dgos[i] instanceof Drawing) {
                    this.images.add(dgos[i]);
                }
            }
        }
    }
}

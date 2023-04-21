package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.HeaderFooter;
import jxl.Range;
import jxl.SheetSettings;
import jxl.WorkbookSettings;
import jxl.biff.ContinueRecord;
import jxl.biff.FormattingRecords;
import jxl.biff.Type;
import jxl.biff.WorkspaceInformationRecord;
import jxl.biff.drawing.Button;
import jxl.biff.drawing.Chart;
import jxl.biff.drawing.Comment;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingData;
import jxl.biff.drawing.MsoDrawingRecord;
import jxl.biff.drawing.NoteRecord;
import jxl.biff.drawing.ObjRecord;
import jxl.biff.drawing.TextObjectRecord;
import jxl.biff.formula.FormulaException;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;

final class SheetReader {
    static Class class$jxl$read$biff$SheetReader;
    private static Logger logger;
    private ButtonPropertySetRecord buttonPropertySet;
    private Cell[][] cells;
    private ArrayList charts = new ArrayList();
    private ArrayList columnInfosArray = new ArrayList();
    private DataValidation dataValidation;
    private DrawingData drawingData;
    private ArrayList drawings = new ArrayList();
    private File excelFile;
    private FormattingRecords formattingRecords;
    private ArrayList hyperlinks = new ArrayList();
    private Range[] mergedCells;
    private boolean nineteenFour;
    private int numCols;
    private int numRows;
    private PLSRecord plsRecord;
    private int[] rowBreaks;
    private ArrayList rowProperties = new ArrayList(10);
    private SheetSettings settings;
    private ArrayList sharedFormulas = new ArrayList();
    private SSTRecord sharedStrings;
    private SheetImpl sheet;
    private BOFRecord sheetBof;
    private int startPosition;
    private WorkbookParser workbook;
    private BOFRecord workbookBof;
    private WorkbookSettings workbookSettings;
    private WorkspaceInformationRecord workspaceOptions;

    static {
        Class cls;
        if (class$jxl$read$biff$SheetReader == null) {
            cls = class$("jxl.read.biff.SheetReader");
            class$jxl$read$biff$SheetReader = cls;
        } else {
            cls = class$jxl$read$biff$SheetReader;
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

    SheetReader(File f, SSTRecord sst, FormattingRecords fr, BOFRecord sb, BOFRecord wb, boolean nf, WorkbookParser wp, int sp, SheetImpl sh) {
        this.excelFile = f;
        this.sharedStrings = sst;
        this.formattingRecords = fr;
        this.sheetBof = sb;
        this.workbookBof = wb;
        this.nineteenFour = nf;
        this.workbook = wp;
        this.startPosition = sp;
        this.sheet = sh;
        this.settings = new SheetSettings();
        this.workbookSettings = this.workbook.getSettings();
    }

    private void addCell(Cell cell) {
        if (cell.getRow() >= this.numRows || cell.getColumn() >= this.numCols) {
            logger.warn(new StringBuffer().append("Cell ").append(CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow())).append(" exceeds defined cell boundaries in Dimension record ").append("(").append(this.numCols).append("x").append(this.numRows).append(")").toString());
            return;
        }
        if (this.cells[cell.getRow()][cell.getColumn()] != null) {
            StringBuffer sb = new StringBuffer();
            CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow(), sb);
            logger.warn(new StringBuffer().append("Cell ").append(sb.toString()).append(" already contains data").toString());
        }
        this.cells[cell.getRow()][cell.getColumn()] = cell;
    }

    /* access modifiers changed from: package-private */
    public final void read() {
        Cell cell;
        BaseSharedFormulaRecord sharedFormula;
        HorizontalPageBreaksRecord dr;
        FooterRecord fr;
        HeaderRecord hr;
        LabelRecord lr;
        BaseSharedFormulaRecord sharedFormula2;
        DimensionRecord dr2;
        boolean sharedFormulaAdded = false;
        boolean cont = true;
        this.excelFile.setPos(this.startPosition);
        MsoDrawingRecord msoRecord = null;
        ObjRecord objRecord = null;
        boolean firstMsoRecord = true;
        Window2Record window2Record = null;
        HashMap comments = new HashMap();
        BaseSharedFormulaRecord sharedFormula3 = null;
        while (cont) {
            Record r = this.excelFile.next();
            if (r.getType() == Type.UNKNOWN && r.getCode() == 0) {
                logger.warn("Biff code zero found");
                if (r.getLength() == 10) {
                    logger.warn("Biff code zero found - trying a dimension record.");
                    r.setType(Type.DIMENSION);
                } else {
                    logger.warn("Biff code zero found - Ignoring.");
                }
            }
            if (r.getType() == Type.DIMENSION) {
                if (this.workbookBof.isBiff8()) {
                    dr2 = new DimensionRecord(r);
                } else {
                    dr2 = new DimensionRecord(r, DimensionRecord.biff7);
                }
                this.numRows = dr2.getNumberOfRows();
                this.numCols = dr2.getNumberOfColumns();
                this.cells = (Cell[][]) Array.newInstance(Cell.class, new int[]{this.numRows, this.numCols});
            } else if (r.getType() == Type.LABELSST) {
                addCell(new LabelSSTRecord(r, this.sharedStrings, this.formattingRecords, this.sheet));
            } else if (r.getType() == Type.RK || r.getType() == Type.RK2) {
                RKRecord rkr = new RKRecord(r, this.formattingRecords, this.sheet);
                if (this.formattingRecords.isDate(rkr.getXFIndex())) {
                    addCell(new DateRecord(rkr, rkr.getXFIndex(), this.formattingRecords, this.nineteenFour, this.sheet));
                } else {
                    addCell(rkr);
                }
            } else if (r.getType() == Type.HLINK) {
                this.hyperlinks.add(new HyperlinkRecord(r, this.sheet, this.workbookSettings));
            } else if (r.getType() == Type.MERGEDCELLS) {
                MergedCellsRecord mergedCellsRecord = new MergedCellsRecord(r, this.sheet);
                if (this.mergedCells == null) {
                    this.mergedCells = mergedCellsRecord.getRanges();
                } else {
                    Range[] newMergedCells = new Range[(this.mergedCells.length + mergedCellsRecord.getRanges().length)];
                    System.arraycopy(this.mergedCells, 0, newMergedCells, 0, this.mergedCells.length);
                    System.arraycopy(mergedCellsRecord.getRanges(), 0, newMergedCells, this.mergedCells.length, mergedCellsRecord.getRanges().length);
                    this.mergedCells = newMergedCells;
                }
            } else if (r.getType() == Type.MULRK) {
                MulRKRecord mulRKRecord = new MulRKRecord(r);
                int num = mulRKRecord.getNumberOfColumns();
                for (int i = 0; i < num; i++) {
                    int ixf = mulRKRecord.getXFIndex(i);
                    NumberValue nv = new NumberValue(mulRKRecord.getRow(), mulRKRecord.getFirstColumn() + i, RKHelper.getDouble(mulRKRecord.getRKNumber(i)), ixf, this.formattingRecords, this.sheet);
                    if (this.formattingRecords.isDate(ixf)) {
                        addCell(new DateRecord(nv, ixf, this.formattingRecords, this.nineteenFour, this.sheet));
                    } else {
                        nv.setNumberFormat(this.formattingRecords.getNumberFormat(ixf));
                        addCell(nv);
                    }
                }
            } else if (r.getType() == Type.NUMBER) {
                NumberRecord nr = new NumberRecord(r, this.formattingRecords, this.sheet);
                if (this.formattingRecords.isDate(nr.getXFIndex())) {
                    addCell(new DateRecord(nr, nr.getXFIndex(), this.formattingRecords, this.nineteenFour, this.sheet));
                } else {
                    addCell(nr);
                }
            } else if (r.getType() == Type.BOOLERR) {
                BooleanRecord booleanRecord = new BooleanRecord(r, this.formattingRecords, this.sheet);
                if (booleanRecord.isError()) {
                    addCell(new ErrorRecord(booleanRecord.getRecord(), this.formattingRecords, this.sheet));
                } else {
                    addCell(booleanRecord);
                }
            } else if (r.getType() == Type.PRINTGRIDLINES) {
                this.settings.setPrintGridLines(new PrintGridLinesRecord(r).getPrintGridLines());
            } else if (r.getType() == Type.PRINTHEADERS) {
                this.settings.setPrintHeaders(new PrintHeadersRecord(r).getPrintHeaders());
            } else if (r.getType() == Type.WINDOW2) {
                window2Record = new Window2Record(r);
                this.settings.setShowGridLines(window2Record.getShowGridLines());
                this.settings.setDisplayZeroValues(window2Record.getDisplayZeroValues());
                this.settings.setSelected(true);
            } else if (r.getType() == Type.PANE) {
                PaneRecord paneRecord = new PaneRecord(r);
                if (window2Record != null && window2Record.getFrozen()) {
                    this.settings.setVerticalFreeze(paneRecord.getRowsVisible());
                    this.settings.setHorizontalFreeze(paneRecord.getColumnsVisible());
                }
            } else if (r.getType() != Type.CONTINUE) {
                if (r.getType() == Type.NOTE) {
                    if (!this.workbookSettings.getDrawingsDisabled()) {
                        NoteRecord nr2 = new NoteRecord(r);
                        Comment comment = (Comment) comments.remove(new Integer(nr2.getObjectId()));
                        if (comment == null) {
                            logger.warn(new StringBuffer().append(" cannot find comment for note id ").append(nr2.getObjectId()).append("...ignoring").toString());
                        } else {
                            comment.setNote(nr2);
                            this.drawings.add(comment);
                            addCellComment(comment.getColumn(), comment.getRow(), comment.getText(), comment.getWidth(), comment.getHeight());
                        }
                    }
                } else if (r.getType() != Type.ARRAY) {
                    if (r.getType() == Type.PROTECT) {
                        this.settings.setProtected(new ProtectRecord(r).isProtected());
                    } else if (r.getType() == Type.SHAREDFORMULA) {
                        if (sharedFormula3 == null) {
                            logger.warn("Shared template formula is null - trying most recent formula template");
                            SharedFormulaRecord lastSharedFormula = (SharedFormulaRecord) this.sharedFormulas.get(this.sharedFormulas.size() - 1);
                            if (lastSharedFormula != null) {
                                sharedFormula2 = lastSharedFormula.getTemplateFormula();
                                this.sharedFormulas.add(new SharedFormulaRecord(r, sharedFormula2, this.workbook, this.workbook, this.sheet));
                                sharedFormula3 = null;
                            }
                        }
                        sharedFormula2 = sharedFormula3;
                        this.sharedFormulas.add(new SharedFormulaRecord(r, sharedFormula2, this.workbook, this.workbook, this.sheet));
                        sharedFormula3 = null;
                    } else if (r.getType() == Type.FORMULA || r.getType() == Type.FORMULA2) {
                        FormulaRecord fr2 = new FormulaRecord(r, this.excelFile, this.formattingRecords, this.workbook, this.workbook, this.sheet, this.workbookSettings);
                        if (fr2.isShared()) {
                            BaseSharedFormulaRecord prevSharedFormula = sharedFormula3;
                            sharedFormula = (BaseSharedFormulaRecord) fr2.getFormula();
                            sharedFormulaAdded = addToSharedFormulas(sharedFormula);
                            if (sharedFormulaAdded) {
                                sharedFormula = prevSharedFormula;
                            }
                            if (!sharedFormulaAdded && prevSharedFormula != null) {
                                addCell(revertSharedFormula(prevSharedFormula));
                            }
                        } else {
                            Cell cell2 = fr2.getFormula();
                            try {
                                if (fr2.getFormula().getType() == CellType.NUMBER_FORMULA) {
                                    NumberFormulaRecord nfr = (NumberFormulaRecord) fr2.getFormula();
                                    if (this.formattingRecords.isDate(nfr.getXFIndex())) {
                                        cell = new DateFormulaRecord(nfr, this.formattingRecords, this.workbook, this.workbook, this.nineteenFour, this.sheet);
                                        addCell(cell);
                                        sharedFormula = sharedFormula3;
                                    }
                                }
                                cell = cell2;
                                try {
                                    addCell(cell);
                                    sharedFormula = sharedFormula3;
                                } catch (FormulaException e) {
                                    e = e;
                                    logger.warn(new StringBuffer().append(CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow())).append(" ").append(e.getMessage()).toString());
                                    sharedFormula = sharedFormula3;
                                    sharedFormula3 = sharedFormula;
                                }
                            } catch (FormulaException e2) {
                                e = e2;
                                cell = cell2;
                                logger.warn(new StringBuffer().append(CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow())).append(" ").append(e.getMessage()).toString());
                                sharedFormula = sharedFormula3;
                                sharedFormula3 = sharedFormula;
                            }
                        }
                        sharedFormula3 = sharedFormula;
                    } else if (r.getType() == Type.LABEL) {
                        if (this.workbookBof.isBiff8()) {
                            lr = new LabelRecord(r, this.formattingRecords, this.sheet, this.workbookSettings);
                        } else {
                            lr = new LabelRecord(r, this.formattingRecords, this.sheet, this.workbookSettings, LabelRecord.biff7);
                        }
                        addCell(lr);
                    } else if (r.getType() == Type.RSTRING) {
                        Assert.verify(!this.workbookBof.isBiff8());
                        addCell(new RStringRecord(r, this.formattingRecords, this.sheet, this.workbookSettings, RStringRecord.biff7));
                    } else if (r.getType() != Type.NAME) {
                        if (r.getType() == Type.PASSWORD) {
                            this.settings.setPasswordHash(new PasswordRecord(r).getPasswordHash());
                        } else if (r.getType() == Type.ROW) {
                            RowRecord rowRecord = new RowRecord(r);
                            if (!rowRecord.isDefaultHeight() || !rowRecord.matchesDefaultFontHeight() || rowRecord.isCollapsed() || rowRecord.hasDefaultFormat()) {
                                this.rowProperties.add(rowRecord);
                            }
                        } else if (r.getType() == Type.BLANK) {
                            if (!this.workbookSettings.getIgnoreBlanks()) {
                                addCell(new BlankCell(r, this.formattingRecords, this.sheet));
                            }
                        } else if (r.getType() == Type.MULBLANK) {
                            if (!this.workbookSettings.getIgnoreBlanks()) {
                                MulBlankRecord mulBlankRecord = new MulBlankRecord(r);
                                int num2 = mulBlankRecord.getNumberOfColumns();
                                for (int i2 = 0; i2 < num2; i2++) {
                                    addCell(new MulBlankCell(mulBlankRecord.getRow(), mulBlankRecord.getFirstColumn() + i2, mulBlankRecord.getXFIndex(i2), this.formattingRecords, this.sheet));
                                }
                            }
                        } else if (r.getType() == Type.SCL) {
                            this.settings.setZoomFactor(new SCLRecord(r).getZoomFactor());
                        } else if (r.getType() == Type.COLINFO) {
                            this.columnInfosArray.add(new ColumnInfoRecord(r));
                        } else if (r.getType() == Type.HEADER) {
                            if (this.workbookBof.isBiff8()) {
                                hr = new HeaderRecord(r, this.workbookSettings);
                            } else {
                                hr = new HeaderRecord(r, this.workbookSettings, HeaderRecord.biff7);
                            }
                            this.settings.setHeader(new HeaderFooter(hr.getHeader()));
                        } else if (r.getType() == Type.FOOTER) {
                            if (this.workbookBof.isBiff8()) {
                                fr = new FooterRecord(r, this.workbookSettings);
                            } else {
                                fr = new FooterRecord(r, this.workbookSettings, FooterRecord.biff7);
                            }
                            this.settings.setFooter(new HeaderFooter(fr.getFooter()));
                        } else if (r.getType() == Type.SETUP) {
                            SetupRecord setupRecord = new SetupRecord(r);
                            if (setupRecord.isPortrait()) {
                                this.settings.setOrientation(PageOrientation.PORTRAIT);
                            } else {
                                this.settings.setOrientation(PageOrientation.LANDSCAPE);
                            }
                            this.settings.setPaperSize(PaperSize.getPaperSize(setupRecord.getPaperSize()));
                            this.settings.setHeaderMargin(setupRecord.getHeaderMargin());
                            this.settings.setFooterMargin(setupRecord.getFooterMargin());
                            this.settings.setScaleFactor(setupRecord.getScaleFactor());
                            this.settings.setPageStart(setupRecord.getPageStart());
                            this.settings.setFitWidth(setupRecord.getFitWidth());
                            this.settings.setFitHeight(setupRecord.getFitHeight());
                            this.settings.setHorizontalPrintResolution(setupRecord.getHorizontalPrintResolution());
                            this.settings.setVerticalPrintResolution(setupRecord.getVerticalPrintResolution());
                            this.settings.setCopies(setupRecord.getCopies());
                            if (this.workspaceOptions != null) {
                                this.settings.setFitToPages(this.workspaceOptions.getFitToPages());
                            }
                        } else if (r.getType() == Type.WSBOOL) {
                            this.workspaceOptions = new WorkspaceInformationRecord(r);
                        } else if (r.getType() == Type.DEFCOLWIDTH) {
                            this.settings.setDefaultColumnWidth(new DefaultColumnWidthRecord(r).getWidth());
                        } else if (r.getType() == Type.DEFAULTROWHEIGHT) {
                            DefaultRowHeightRecord defaultRowHeightRecord = new DefaultRowHeightRecord(r);
                            if (defaultRowHeightRecord.getHeight() != 0) {
                                this.settings.setDefaultRowHeight(defaultRowHeightRecord.getHeight());
                            }
                        } else if (r.getType() == Type.LEFTMARGIN) {
                            this.settings.setLeftMargin(new LeftMarginRecord(r).getMargin());
                        } else if (r.getType() == Type.RIGHTMARGIN) {
                            this.settings.setRightMargin(new RightMarginRecord(r).getMargin());
                        } else if (r.getType() == Type.TOPMARGIN) {
                            this.settings.setTopMargin(new TopMarginRecord(r).getMargin());
                        } else if (r.getType() == Type.BOTTOMMARGIN) {
                            this.settings.setBottomMargin(new BottomMarginRecord(r).getMargin());
                        } else if (r.getType() == Type.HORIZONTALPAGEBREAKS) {
                            if (this.workbookBof.isBiff8()) {
                                dr = new HorizontalPageBreaksRecord(r);
                            } else {
                                dr = new HorizontalPageBreaksRecord(r, HorizontalPageBreaksRecord.biff7);
                            }
                            this.rowBreaks = dr.getRowBreaks();
                        } else if (r.getType() == Type.PLS) {
                            this.plsRecord = new PLSRecord(r);
                        } else if (r.getType() != Type.DVAL) {
                            if (r.getType() == Type.HCENTER) {
                                this.settings.setHorizontalCentre(new CentreRecord(r).isCentre());
                            } else if (r.getType() == Type.VCENTER) {
                                this.settings.setVerticalCentre(new CentreRecord(r).isCentre());
                            } else if (r.getType() != Type.DV) {
                                if (r.getType() == Type.OBJ) {
                                    objRecord = new ObjRecord(r);
                                    if (!this.workbookSettings.getDrawingsDisabled()) {
                                        handleObjectRecord(objRecord, msoRecord, comments);
                                    }
                                    if (objRecord.getType() != ObjRecord.CHART) {
                                        objRecord = null;
                                        msoRecord = null;
                                    }
                                } else if (r.getType() == Type.MSODRAWING) {
                                    if (!this.workbookSettings.getDrawingsDisabled()) {
                                        if (msoRecord != null) {
                                            this.drawingData.addRawData(msoRecord.getData());
                                        }
                                        msoRecord = new MsoDrawingRecord(r);
                                        if (firstMsoRecord) {
                                            msoRecord.setFirst();
                                            firstMsoRecord = false;
                                        }
                                    }
                                } else if (r.getType() == Type.BUTTONPROPERTYSET) {
                                    this.buttonPropertySet = new ButtonPropertySetRecord(r);
                                } else if (r.getType() == Type.BOF) {
                                    BOFRecord bOFRecord = new BOFRecord(r);
                                    Assert.verify(!bOFRecord.isWorksheet());
                                    int startpos = (this.excelFile.getPos() - r.getLength()) - 4;
                                    Record r2 = this.excelFile.next();
                                    while (r2.getCode() != Type.EOF.value) {
                                        r2 = this.excelFile.next();
                                    }
                                    if (bOFRecord.isChart()) {
                                        if (!this.workbook.getWorkbookBof().isBiff8()) {
                                            logger.warn("only biff8 charts are supported");
                                        } else {
                                            if (this.drawingData == null) {
                                                this.drawingData = new DrawingData();
                                            }
                                            if (!this.workbookSettings.getDrawingsDisabled()) {
                                                Chart chart = new Chart(msoRecord, objRecord, this.drawingData, startpos, this.excelFile.getPos(), this.excelFile, this.workbookSettings);
                                                this.charts.add(chart);
                                                if (this.workbook.getDrawingGroup() != null) {
                                                    this.workbook.getDrawingGroup().add(chart);
                                                }
                                            }
                                        }
                                        msoRecord = null;
                                        objRecord = null;
                                    }
                                    if (this.sheetBof.isChart()) {
                                        cont = false;
                                    }
                                } else if (r.getType() == Type.EOF) {
                                    cont = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        this.excelFile.restorePos();
        Iterator i3 = this.sharedFormulas.iterator();
        while (i3.hasNext()) {
            Cell[] sfnr = ((SharedFormulaRecord) i3.next()).getFormulas(this.formattingRecords, this.nineteenFour);
            for (int sf = 0; sf < sfnr.length; sf++) {
                addCell(sfnr[sf]);
            }
        }
        if (!sharedFormulaAdded && sharedFormula3 != null) {
            addCell(revertSharedFormula(sharedFormula3));
        }
        if (!(msoRecord == null || this.workbook.getDrawingGroup() == null)) {
            this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
        }
        if (!comments.isEmpty()) {
            logger.warn("Not all comments have a corresponding Note record");
        }
    }

    private boolean addToSharedFormulas(BaseSharedFormulaRecord fr) {
        Iterator i = this.sharedFormulas.iterator();
        boolean added = false;
        while (i.hasNext() && !added) {
            added = ((SharedFormulaRecord) i.next()).add(fr);
        }
        return added;
    }

    private Cell revertSharedFormula(BaseSharedFormulaRecord f) {
        int pos = this.excelFile.getPos();
        this.excelFile.setPos(f.getFilePos());
        FormulaRecord fr = new FormulaRecord(f.getRecord(), this.excelFile, this.formattingRecords, this.workbook, this.workbook, FormulaRecord.ignoreSharedFormula, this.sheet, this.workbookSettings);
        try {
            Cell cell = fr.getFormula();
            if (fr.getFormula().getType() == CellType.NUMBER_FORMULA) {
                NumberFormulaRecord nfr = (NumberFormulaRecord) fr.getFormula();
                if (this.formattingRecords.isDate(fr.getXFIndex())) {
                    cell = new DateFormulaRecord(nfr, this.formattingRecords, this.workbook, this.workbook, this.nineteenFour, this.sheet);
                }
            }
            this.excelFile.setPos(pos);
            return cell;
        } catch (FormulaException e) {
            logger.warn(new StringBuffer().append(CellReferenceHelper.getCellReference(fr.getColumn(), fr.getRow())).append(" ").append(e.getMessage()).toString());
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final int getNumRows() {
        return this.numRows;
    }

    /* access modifiers changed from: package-private */
    public final int getNumCols() {
        return this.numCols;
    }

    /* access modifiers changed from: package-private */
    public final Cell[][] getCells() {
        return this.cells;
    }

    /* access modifiers changed from: package-private */
    public final ArrayList getRowProperties() {
        return this.rowProperties;
    }

    /* access modifiers changed from: package-private */
    public final ArrayList getColumnInfosArray() {
        return this.columnInfosArray;
    }

    /* access modifiers changed from: package-private */
    public final ArrayList getHyperlinks() {
        return this.hyperlinks;
    }

    /* access modifiers changed from: package-private */
    public final ArrayList getCharts() {
        return this.charts;
    }

    /* access modifiers changed from: package-private */
    public final ArrayList getDrawings() {
        return this.drawings;
    }

    /* access modifiers changed from: package-private */
    public final DataValidation getDataValidation() {
        return this.dataValidation;
    }

    /* access modifiers changed from: package-private */
    public final Range[] getMergedCells() {
        return this.mergedCells;
    }

    /* access modifiers changed from: package-private */
    public final SheetSettings getSettings() {
        return this.settings;
    }

    /* access modifiers changed from: package-private */
    public final int[] getRowBreaks() {
        return this.rowBreaks;
    }

    /* access modifiers changed from: package-private */
    public final WorkspaceInformationRecord getWorkspaceOptions() {
        return this.workspaceOptions;
    }

    /* access modifiers changed from: package-private */
    public final PLSRecord getPLS() {
        return this.plsRecord;
    }

    /* access modifiers changed from: package-private */
    public final ButtonPropertySetRecord getButtonPropertySet() {
        return this.buttonPropertySet;
    }

    private void addCellComment(int col, int row, String text, double width, double height) {
        Cell c = this.cells[row][col];
        if (c == null) {
            logger.warn(new StringBuffer().append("Cell at ").append(CellReferenceHelper.getCellReference(col, row)).append(" not present - adding a blank").toString());
            MulBlankCell mbc = new MulBlankCell(row, col, 0, this.formattingRecords, this.sheet);
            CellFeatures cf = new CellFeatures();
            cf.setReadComment(text, width, height);
            mbc.setCellFeatures(cf);
            addCell(mbc);
        } else if (c instanceof CellFeaturesAccessor) {
            CellFeaturesAccessor cv = (CellFeaturesAccessor) c;
            CellFeatures cf2 = cv.getCellFeatures();
            if (cf2 == null) {
                cf2 = new CellFeatures();
                cv.setCellFeatures(cf2);
            }
            cf2.setReadComment(text, width, height);
        } else {
            logger.warn(new StringBuffer().append("Not able to add comment to cell type ").append(c.getClass().getName()).append(" at ").append(CellReferenceHelper.getCellReference(col, row)).toString());
        }
    }

    private void handleObjectRecord(ObjRecord objRecord, MsoDrawingRecord msoRecord, HashMap comments) {
        if (msoRecord == null) {
            logger.warn("Object record is not associated with a drawing  record - ignoring");
        } else if (objRecord.getType() == ObjRecord.PICTURE) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            this.drawings.add(new Drawing(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup()));
        } else if (objRecord.getType() == ObjRecord.EXCELNOTE) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            Comment comment = new Comment(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
            Record r2 = this.excelFile.next();
            if (r2.getType() == Type.MSODRAWING) {
                comment.addMso(new MsoDrawingRecord(r2));
                r2 = this.excelFile.next();
            }
            Assert.verify(r2.getType() == Type.TXO);
            comment.setTextObject(new TextObjectRecord(r2));
            Record r22 = this.excelFile.next();
            Assert.verify(r22.getType() == Type.CONTINUE);
            comment.setText(new ContinueRecord(r22));
            Record r23 = this.excelFile.next();
            if (r23.getType() == Type.CONTINUE) {
                comment.setFormatting(new ContinueRecord(r23));
            }
            comments.put(new Integer(comment.getObjectId()), comment);
        } else if (objRecord.getType() == ObjRecord.BUTTON) {
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            Button button = new Button(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
            Record r24 = this.excelFile.next();
            if (r24.getType() == Type.MSODRAWING) {
                button.addMso(new MsoDrawingRecord(r24));
                r24 = this.excelFile.next();
            }
            Assert.verify(r24.getType() == Type.TXO);
            button.setTextObject(new TextObjectRecord(r24));
            Record r25 = this.excelFile.next();
            Assert.verify(r25.getType() == Type.CONTINUE);
            button.setText(new ContinueRecord(r25));
            Record r26 = this.excelFile.next();
            if (r26.getType() == Type.CONTINUE) {
                button.setFormatting(new ContinueRecord(r26));
            }
            this.drawings.add(button);
        } else if (objRecord.getType() != ObjRecord.CHART) {
            logger.warn(new StringBuffer().append(objRecord.getType()).append(" on sheet \"").append(this.sheet.getName()).append("\" not supported - omitting").toString());
            if (this.drawingData == null) {
                this.drawingData = new DrawingData();
            }
            this.drawingData.addData(msoRecord.getData());
            if (this.workbook.getDrawingGroup() != null) {
                this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
            }
        }
    }
}

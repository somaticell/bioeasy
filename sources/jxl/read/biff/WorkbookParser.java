package jxl.read.biff;

import common.Assert;
import common.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.FontRecord;
import jxl.biff.Fonts;
import jxl.biff.FormatRecord;
import jxl.biff.FormattingRecords;
import jxl.biff.NumFormatRecordsException;
import jxl.biff.PaletteRecord;
import jxl.biff.RangeImpl;
import jxl.biff.Type;
import jxl.biff.WorkbookMethods;
import jxl.biff.XFRecord;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.MsoDrawingGroupRecord;
import jxl.biff.drawing.Origin;
import jxl.biff.formula.ExternalSheet;
import jxl.read.biff.NameRecord;

public class WorkbookParser extends Workbook implements ExternalSheet, WorkbookMethods {
    static Class class$jxl$read$biff$WorkbookParser;
    private static Logger logger;
    private int bofs;
    private ArrayList boundsheets = new ArrayList(10);
    private ButtonPropertySetRecord buttonPropertySet;
    private boolean containsMacros = false;
    private CountryRecord countryRecord;
    private DrawingGroup drawingGroup;
    private File excelFile;
    private ExternalSheetRecord externSheet;
    private Fonts fonts = new Fonts();
    private FormattingRecords formattingRecords = new FormattingRecords(this.fonts);
    private SheetImpl lastSheet;
    private int lastSheetIndex = -1;
    private MsoDrawingGroupRecord msoDrawingGroup;
    private ArrayList nameTable;
    private HashMap namedRecords = new HashMap();
    private boolean nineteenFour;
    private WorkbookSettings settings;
    private SSTRecord sharedStrings;
    private ArrayList sheets = new ArrayList(10);
    private ArrayList supbooks = new ArrayList(10);
    private boolean wbProtected = false;
    private BOFRecord workbookBof;

    static {
        Class cls;
        if (class$jxl$read$biff$WorkbookParser == null) {
            cls = class$("jxl.read.biff.WorkbookParser");
            class$jxl$read$biff$WorkbookParser = cls;
        } else {
            cls = class$jxl$read$biff$WorkbookParser;
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

    public WorkbookParser(File f, WorkbookSettings s) {
        this.excelFile = f;
        this.settings = s;
    }

    public Sheet[] getSheets() {
        return (Sheet[]) this.sheets.toArray(new Sheet[getNumberOfSheets()]);
    }

    public Sheet getReadSheet(int index) {
        return getSheet(index);
    }

    public Sheet getSheet(int index) {
        if (this.lastSheet != null && this.lastSheetIndex == index) {
            return this.lastSheet;
        }
        if (this.lastSheet != null) {
            this.lastSheet.clear();
            if (!this.settings.getGCDisabled()) {
                System.gc();
            }
        }
        this.lastSheet = (SheetImpl) this.sheets.get(index);
        this.lastSheetIndex = index;
        this.lastSheet.readSheet();
        return this.lastSheet;
    }

    public Sheet getSheet(String name) {
        int pos = 0;
        boolean found = false;
        Iterator i = this.boundsheets.iterator();
        while (i.hasNext() && !found) {
            if (((BoundsheetRecord) i.next()).getName().equals(name)) {
                found = true;
            } else {
                pos++;
            }
        }
        if (found) {
            return getSheet(pos);
        }
        return null;
    }

    public String[] getSheetNames() {
        String[] names = new String[this.boundsheets.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = ((BoundsheetRecord) this.boundsheets.get(i)).getName();
        }
        return names;
    }

    public int getExternalSheetIndex(int index) {
        if (this.workbookBof.isBiff7()) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        return this.externSheet.getFirstTabIndex(index);
    }

    public int getLastExternalSheetIndex(int index) {
        if (this.workbookBof.isBiff7()) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        return this.externSheet.getLastTabIndex(index);
    }

    public String getExternalSheetName(int index) {
        if (this.workbookBof.isBiff7()) {
            return ((BoundsheetRecord) this.boundsheets.get(index)).getName();
        }
        SupbookRecord sr = (SupbookRecord) this.supbooks.get(this.externSheet.getSupbookIndex(index));
        int firstTab = this.externSheet.getFirstTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            return ((BoundsheetRecord) this.boundsheets.get(firstTab)).getName();
        }
        if (sr.getType() != SupbookRecord.EXTERNAL) {
            return "[UNKNOWN]";
        }
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        sb.append(sr.getFileName());
        sb.append(']');
        sb.append(sr.getSheetName(firstTab));
        return sb.toString();
    }

    public String getLastExternalSheetName(int index) {
        if (this.workbookBof.isBiff7()) {
            return ((BoundsheetRecord) this.boundsheets.get(index)).getName();
        }
        SupbookRecord sr = (SupbookRecord) this.supbooks.get(this.externSheet.getSupbookIndex(index));
        int lastTab = this.externSheet.getLastTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            return ((BoundsheetRecord) this.boundsheets.get(lastTab)).getName();
        }
        if (sr.getType() != SupbookRecord.EXTERNAL) {
            return "[UNKNOWN]";
        }
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        sb.append(sr.getFileName());
        sb.append(']');
        sb.append(sr.getSheetName(lastTab));
        return sb.toString();
    }

    public int getNumberOfSheets() {
        return this.sheets.size();
    }

    public void close() {
        if (this.lastSheet != null) {
            this.lastSheet.clear();
        }
        this.excelFile.clear();
        if (!this.settings.getGCDisabled()) {
            System.gc();
        }
    }

    /* access modifiers changed from: package-private */
    public final void addSheet(Sheet s) {
        this.sheets.add(s);
    }

    /* access modifiers changed from: protected */
    public void parse() throws BiffException, PasswordException {
        BOFRecord bof;
        BoundsheetRecord br;
        XFRecord xfr;
        FormatRecord fr;
        FontRecord fr2;
        NameRecord nr;
        Record r = null;
        BOFRecord bof2 = new BOFRecord(this.excelFile.next());
        this.workbookBof = bof2;
        this.bofs++;
        if (!bof2.isBiff8() && !bof2.isBiff7()) {
            throw new BiffException(BiffException.unrecognizedBiffVersion);
        } else if (!bof2.isWorkbookGlobals()) {
            throw new BiffException(BiffException.expectedGlobals);
        } else {
            ArrayList continueRecords = new ArrayList();
            this.nameTable = new ArrayList();
            while (this.bofs == 1) {
                r = this.excelFile.next();
                if (r.getType() == Type.SST) {
                    continueRecords.clear();
                    Record nextrec = this.excelFile.peek();
                    while (nextrec.getType() == Type.CONTINUE) {
                        continueRecords.add(this.excelFile.next());
                        nextrec = this.excelFile.peek();
                    }
                    this.sharedStrings = new SSTRecord(r, (Record[]) continueRecords.toArray(new Record[continueRecords.size()]), this.settings);
                } else if (r.getType() == Type.FILEPASS) {
                    throw new PasswordException();
                } else if (r.getType() == Type.NAME) {
                    if (bof2.isBiff8()) {
                        nr = new NameRecord(r, this.settings, this.namedRecords.size());
                    } else {
                        nr = new NameRecord(r, this.settings, this.namedRecords.size(), NameRecord.biff7);
                    }
                    this.namedRecords.put(nr.getName(), nr);
                    this.nameTable.add(nr);
                } else if (r.getType() == Type.FONT) {
                    if (bof2.isBiff8()) {
                        fr2 = new FontRecord(r, this.settings);
                    } else {
                        fr2 = new FontRecord(r, this.settings, FontRecord.biff7);
                    }
                    this.fonts.addFont(fr2);
                } else if (r.getType() == Type.PALETTE) {
                    this.formattingRecords.setPalette(new PaletteRecord(r));
                } else if (r.getType() == Type.NINETEENFOUR) {
                    this.nineteenFour = new NineteenFourRecord(r).is1904();
                } else if (r.getType() == Type.FORMAT) {
                    if (bof2.isBiff8()) {
                        fr = new FormatRecord(r, this.settings, FormatRecord.biff8);
                    } else {
                        fr = new FormatRecord(r, this.settings, FormatRecord.biff7);
                    }
                    try {
                        this.formattingRecords.addFormat(fr);
                    } catch (NumFormatRecordsException e) {
                        e.printStackTrace();
                        Assert.verify(false, e.getMessage());
                    }
                } else if (r.getType() == Type.XF) {
                    if (bof2.isBiff8()) {
                        xfr = new XFRecord(r, this.settings, XFRecord.biff8);
                    } else {
                        xfr = new XFRecord(r, this.settings, XFRecord.biff7);
                    }
                    try {
                        this.formattingRecords.addStyle(xfr);
                    } catch (NumFormatRecordsException e2) {
                        Assert.verify(false, e2.getMessage());
                    }
                } else if (r.getType() == Type.BOUNDSHEET) {
                    if (bof2.isBiff8()) {
                        br = new BoundsheetRecord(r);
                    } else {
                        br = new BoundsheetRecord(r, BoundsheetRecord.biff7);
                    }
                    if (br.isSheet()) {
                        this.boundsheets.add(br);
                    } else if (br.isChart() && !this.settings.getDrawingsDisabled()) {
                        this.boundsheets.add(br);
                    }
                } else if (r.getType() == Type.EXTERNSHEET) {
                    if (bof2.isBiff8()) {
                        this.externSheet = new ExternalSheetRecord(r, this.settings);
                    } else {
                        this.externSheet = new ExternalSheetRecord(r, this.settings, ExternalSheetRecord.biff7);
                    }
                } else if (r.getType() == Type.CODEPAGE) {
                    this.settings.setCharacterSet(new CodepageRecord(r).getCharacterSet());
                } else if (r.getType() == Type.SUPBOOK) {
                    Record nextrec2 = this.excelFile.peek();
                    while (nextrec2.getType() == Type.CONTINUE) {
                        r.addContinueRecord(this.excelFile.next());
                        nextrec2 = this.excelFile.peek();
                    }
                    this.supbooks.add(new SupbookRecord(r, this.settings));
                } else if (r.getType() == Type.PROTECT) {
                    this.wbProtected = new ProtectRecord(r).isProtected();
                } else if (r.getType() == Type.OBJPROJ) {
                    this.containsMacros = true;
                } else if (r.getType() == Type.COUNTRY) {
                    this.countryRecord = new CountryRecord(r);
                } else if (r.getType() == Type.MSODRAWINGGROUP) {
                    if (!this.settings.getDrawingsDisabled()) {
                        this.msoDrawingGroup = new MsoDrawingGroupRecord(r);
                        if (this.drawingGroup == null) {
                            this.drawingGroup = new DrawingGroup(Origin.READ);
                        }
                        this.drawingGroup.add(this.msoDrawingGroup);
                        Record nextrec3 = this.excelFile.peek();
                        while (nextrec3.getType() == Type.CONTINUE) {
                            this.drawingGroup.add(this.excelFile.next());
                            nextrec3 = this.excelFile.peek();
                        }
                    }
                } else if (r.getType() == Type.BUTTONPROPERTYSET) {
                    this.buttonPropertySet = new ButtonPropertySetRecord(r);
                } else if (r.getType() == Type.EOF) {
                    this.bofs--;
                }
            }
            BOFRecord bof3 = null;
            if (this.excelFile.hasNext()) {
                r = this.excelFile.next();
                if (r.getType() == Type.BOF) {
                    bof3 = new BOFRecord(r);
                }
            }
            while (bof != null && getNumberOfSheets() < this.boundsheets.size()) {
                if (bof.isBiff8() || bof.isBiff7()) {
                    if (bof.isWorksheet()) {
                        SheetImpl s = new SheetImpl(this.excelFile, this.sharedStrings, this.formattingRecords, bof, this.workbookBof, this.nineteenFour, this);
                        BoundsheetRecord br2 = (BoundsheetRecord) this.boundsheets.get(getNumberOfSheets());
                        s.setName(br2.getName());
                        s.setHidden(br2.isHidden());
                        addSheet(s);
                    } else if (bof.isChart()) {
                        SheetImpl s2 = new SheetImpl(this.excelFile, this.sharedStrings, this.formattingRecords, bof, this.workbookBof, this.nineteenFour, this);
                        BoundsheetRecord br3 = (BoundsheetRecord) this.boundsheets.get(getNumberOfSheets());
                        s2.setName(br3.getName());
                        s2.setHidden(br3.isHidden());
                        addSheet(s2);
                    } else {
                        logger.warn("BOF is unrecognized");
                        while (this.excelFile.hasNext() && r.getType() != Type.EOF) {
                            r = this.excelFile.next();
                        }
                    }
                    bof = null;
                    if (this.excelFile.hasNext()) {
                        r = this.excelFile.next();
                        if (r.getType() == Type.BOF) {
                            bof = new BOFRecord(r);
                        }
                    }
                } else {
                    throw new BiffException(BiffException.unrecognizedBiffVersion);
                }
            }
        }
    }

    public FormattingRecords getFormattingRecords() {
        return this.formattingRecords;
    }

    public ExternalSheetRecord getExternalSheetRecord() {
        return this.externSheet;
    }

    public MsoDrawingGroupRecord getMsoDrawingGroupRecord() {
        return this.msoDrawingGroup;
    }

    public SupbookRecord[] getSupbookRecords() {
        return (SupbookRecord[]) this.supbooks.toArray(new SupbookRecord[this.supbooks.size()]);
    }

    public NameRecord[] getNameRecords() {
        return (NameRecord[]) this.nameTable.toArray(new NameRecord[this.nameTable.size()]);
    }

    public Fonts getFonts() {
        return this.fonts;
    }

    public Cell findCellByName(String name) {
        NameRecord nr = (NameRecord) this.namedRecords.get(name);
        if (nr == null) {
            return null;
        }
        NameRecord.NameRange[] ranges = nr.getRanges();
        return getSheet(ranges[0].getExternalSheet()).getCell(ranges[0].getFirstColumn(), ranges[0].getFirstRow());
    }

    public Range[] findByName(String name) {
        NameRecord nr = (NameRecord) this.namedRecords.get(name);
        if (nr == null) {
            return null;
        }
        NameRecord.NameRange[] ranges = nr.getRanges();
        Range[] cellRanges = new Range[ranges.length];
        for (int i = 0; i < ranges.length; i++) {
            cellRanges[i] = new RangeImpl(this, getExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getFirstColumn(), ranges[i].getFirstRow(), getLastExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getLastColumn(), ranges[i].getLastRow());
        }
        return cellRanges;
    }

    public String[] getRangeNames() {
        Object[] keys = this.namedRecords.keySet().toArray();
        String[] names = new String[keys.length];
        System.arraycopy(keys, 0, names, 0, keys.length);
        return names;
    }

    public BOFRecord getWorkbookBof() {
        return this.workbookBof;
    }

    public boolean isProtected() {
        return this.wbProtected;
    }

    public WorkbookSettings getSettings() {
        return this.settings;
    }

    public int getExternalSheetIndex(String sheetName) {
        return 0;
    }

    public int getLastExternalSheetIndex(String sheetName) {
        return 0;
    }

    public String getName(int index) {
        Assert.verify(index >= 0 && index < this.nameTable.size());
        return ((NameRecord) this.nameTable.get(index)).getName();
    }

    public int getNameIndex(String name) {
        NameRecord nr = (NameRecord) this.namedRecords.get(name);
        if (nr != null) {
            return nr.getIndex();
        }
        return 0;
    }

    public DrawingGroup getDrawingGroup() {
        return this.drawingGroup;
    }

    public CompoundFile getCompoundFile() {
        return this.excelFile.getCompoundFile();
    }

    public boolean containsMacros() {
        return this.containsMacros;
    }

    public ButtonPropertySetRecord getButtonPropertySet() {
        return this.buttonPropertySet;
    }

    public CountryRecord getCountryRecord() {
        return this.countryRecord;
    }
}

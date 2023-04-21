package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.biff.Fonts;
import jxl.biff.FormattingRecords;
import jxl.biff.IndexMapping;
import jxl.biff.IntegerHelper;
import jxl.biff.RangeImpl;
import jxl.biff.WorkbookMethods;
import jxl.biff.drawing.Drawing;
import jxl.biff.drawing.DrawingGroup;
import jxl.biff.drawing.DrawingGroupObject;
import jxl.biff.drawing.Origin;
import jxl.biff.formula.ExternalSheet;
import jxl.format.Colour;
import jxl.format.RGB;
import jxl.read.biff.BOFRecord;
import jxl.read.biff.CompoundFile;
import jxl.read.biff.NameRecord;
import jxl.read.biff.SupbookRecord;
import jxl.read.biff.WorkbookParser;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.NameRecord;

public class WritableWorkbookImpl extends WritableWorkbook implements ExternalSheet, WorkbookMethods {
    static Class class$jxl$write$biff$WritableWorkbookImpl;
    private static Logger logger;
    private ButtonPropertySetRecord buttonPropertySet;
    private boolean closeStream;
    private boolean containsMacros;
    private CountryRecord countryRecord;
    private DrawingGroup drawingGroup;
    private ExternalSheetRecord externSheet;
    private Fonts fonts;
    private FormattingRecords formatRecords;
    private HashMap nameRecords = new HashMap();
    private ArrayList names;
    private File outputFile;
    private ArrayList rcirCells;
    private WorkbookSettings settings;
    private SharedStrings sharedStrings = new SharedStrings();
    private ArrayList sheets = new ArrayList();
    private Styles styles;
    private ArrayList supbooks;
    private boolean wbProtected;

    static {
        Class cls;
        if (class$jxl$write$biff$WritableWorkbookImpl == null) {
            cls = class$("jxl.write.biff.WritableWorkbookImpl");
            class$jxl$write$biff$WritableWorkbookImpl = cls;
        } else {
            cls = class$jxl$write$biff$WritableWorkbookImpl;
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

    public WritableWorkbookImpl(OutputStream os, boolean cs, WorkbookSettings ws) throws IOException {
        this.outputFile = new File(os, ws, (CompoundFile) null);
        this.closeStream = cs;
        this.wbProtected = false;
        this.containsMacros = false;
        this.settings = ws;
        this.rcirCells = new ArrayList();
        this.styles = new Styles();
        WritableWorkbook.ARIAL_10_PT.uninitialize();
        WritableWorkbook.HYPERLINK_FONT.uninitialize();
        WritableWorkbook.NORMAL_STYLE.uninitialize();
        WritableWorkbook.HYPERLINK_STYLE.uninitialize();
        WritableWorkbook.HIDDEN_STYLE.uninitialize();
        DateRecord.defaultDateFormat.uninitialize();
        this.fonts = new WritableFonts(this);
        this.formatRecords = new WritableFormattingRecords(this.fonts, this.styles);
    }

    public WritableWorkbookImpl(OutputStream os, Workbook w, boolean cs, WorkbookSettings ws) throws IOException {
        WorkbookParser wp = (WorkbookParser) w;
        WritableWorkbook.ARIAL_10_PT.uninitialize();
        WritableWorkbook.HYPERLINK_FONT.uninitialize();
        WritableWorkbook.NORMAL_STYLE.uninitialize();
        WritableWorkbook.HYPERLINK_STYLE.uninitialize();
        WritableWorkbook.HIDDEN_STYLE.uninitialize();
        DateRecord.defaultDateFormat.uninitialize();
        this.closeStream = cs;
        this.fonts = wp.getFonts();
        this.formatRecords = wp.getFormattingRecords();
        this.wbProtected = false;
        this.settings = ws;
        this.rcirCells = new ArrayList();
        this.styles = new Styles();
        this.outputFile = new File(os, ws, wp.getCompoundFile());
        this.containsMacros = false;
        if (!ws.getPropertySetsDisabled()) {
            this.containsMacros = wp.containsMacros();
        }
        if (wp.getCountryRecord() != null) {
            this.countryRecord = new CountryRecord(wp.getCountryRecord());
        }
        if (wp.getExternalSheetRecord() != null) {
            this.externSheet = new ExternalSheetRecord(wp.getExternalSheetRecord());
            SupbookRecord[] readsr = wp.getSupbookRecords();
            this.supbooks = new ArrayList(readsr.length);
            for (SupbookRecord supbookRecord : readsr) {
                this.supbooks.add(new SupbookRecord(supbookRecord, this.settings));
            }
        }
        if (wp.getDrawingGroup() != null) {
            this.drawingGroup = new DrawingGroup(wp.getDrawingGroup());
        }
        if (this.containsMacros && wp.getButtonPropertySet() != null) {
            this.buttonPropertySet = new ButtonPropertySetRecord(wp.getButtonPropertySet());
        }
        if (!this.settings.getNamesDisabled()) {
            NameRecord[] na = wp.getNameRecords();
            this.names = new ArrayList(na.length);
            for (int i = 0; i < na.length; i++) {
                if (na[i].isBiff8()) {
                    NameRecord n = new NameRecord(na[i], i);
                    this.names.add(n);
                    this.nameRecords.put(n.getName(), n);
                } else {
                    logger.warn("Cannot copy Biff7 name records - ignoring");
                }
            }
        }
        copyWorkbook(w);
        if (this.drawingGroup != null) {
            this.drawingGroup.updateData(wp.getDrawingGroup());
        }
    }

    public WritableSheet[] getSheets() {
        WritableSheet[] sheetArray = new WritableSheet[getNumberOfSheets()];
        for (int i = 0; i < getNumberOfSheets(); i++) {
            sheetArray[i] = getSheet(i);
        }
        return sheetArray;
    }

    public String[] getSheetNames() {
        String[] sheetNames = new String[getNumberOfSheets()];
        for (int i = 0; i < sheetNames.length; i++) {
            sheetNames[i] = getSheet(i).getName();
        }
        return sheetNames;
    }

    public Sheet getReadSheet(int index) {
        return getSheet(index);
    }

    public WritableSheet getSheet(int index) {
        return (WritableSheet) this.sheets.get(index);
    }

    public WritableSheet getSheet(String name) {
        boolean found = false;
        Iterator i = this.sheets.iterator();
        WritableSheet s = null;
        while (i.hasNext() && !found) {
            s = (WritableSheet) i.next();
            if (s.getName().equals(name)) {
                found = true;
            }
        }
        if (found) {
            return s;
        }
        return null;
    }

    public int getNumberOfSheets() {
        return this.sheets.size();
    }

    public void close() throws IOException, JxlWriteException {
        this.outputFile.close(this.closeStream);
    }

    public void setOutputFile(File fileName) throws IOException {
        this.outputFile.setOutputFile(new FileOutputStream(fileName));
    }

    private WritableSheet createSheet(String name, int index, boolean handleRefs) {
        WritableSheet w = new WritableSheetImpl(name, this.outputFile, this.formatRecords, this.sharedStrings, this.settings, this);
        int pos = index;
        if (index <= 0) {
            pos = 0;
            this.sheets.add(0, w);
        } else if (index > this.sheets.size()) {
            pos = this.sheets.size();
            this.sheets.add(w);
        } else {
            this.sheets.add(index, w);
        }
        if (handleRefs && this.externSheet != null) {
            this.externSheet.sheetInserted(pos);
        }
        if (this.supbooks != null && this.supbooks.size() > 0) {
            SupbookRecord supbook = (SupbookRecord) this.supbooks.get(0);
            if (supbook.getType() == SupbookRecord.INTERNAL) {
                supbook.adjustInternal(this.sheets.size());
            }
        }
        return w;
    }

    public WritableSheet createSheet(String name, int index) {
        return createSheet(name, index, true);
    }

    public void removeSheet(int index) {
        int pos = index;
        if (index <= 0) {
            pos = 0;
            this.sheets.remove(0);
        } else if (index >= this.sheets.size()) {
            pos = this.sheets.size() - 1;
            this.sheets.remove(this.sheets.size() - 1);
        } else {
            this.sheets.remove(index);
        }
        if (this.externSheet != null) {
            this.externSheet.sheetRemoved(pos);
        }
        if (this.supbooks != null && this.supbooks.size() > 0) {
            SupbookRecord supbook = (SupbookRecord) this.supbooks.get(0);
            if (supbook.getType() == SupbookRecord.INTERNAL) {
                supbook.adjustInternal(this.sheets.size());
            }
        }
        if (this.names != null && this.names.size() > 0) {
            for (int i = 0; i < this.names.size(); i++) {
                NameRecord n = (NameRecord) this.names.get(i);
                int oldRef = n.getSheetRef();
                if (oldRef == pos + 1) {
                    n.setSheetRef(0);
                } else if (oldRef > pos + 1) {
                    if (oldRef < 1) {
                        oldRef = 1;
                    }
                    n.setSheetRef(oldRef - 1);
                }
            }
        }
    }

    public WritableSheet moveSheet(int fromIndex, int toIndex) {
        int fromIndex2 = Math.min(Math.max(fromIndex, 0), this.sheets.size() - 1);
        int toIndex2 = Math.min(Math.max(toIndex, 0), this.sheets.size() - 1);
        WritableSheet sheet = (WritableSheet) this.sheets.remove(fromIndex2);
        this.sheets.add(toIndex2, sheet);
        return sheet;
    }

    public void write() throws IOException {
        for (int i = 0; i < getNumberOfSheets(); i++) {
            ((WritableSheetImpl) getSheet(i)).checkMergedBorders();
        }
        if (!this.settings.getRationalizationDisabled()) {
            rationalize();
        }
        this.outputFile.write(new BOFRecord(BOFRecord.workbookGlobals));
        this.outputFile.write(new InterfaceHeaderRecord());
        this.outputFile.write(new MMSRecord(0, 0));
        this.outputFile.write(new InterfaceEndRecord());
        this.outputFile.write(new WriteAccessRecord());
        this.outputFile.write(new CodepageRecord());
        this.outputFile.write(new DSFRecord());
        this.outputFile.write(new TabIdRecord(getNumberOfSheets()));
        if (this.containsMacros) {
            this.outputFile.write(new ObjProjRecord());
        }
        if (this.buttonPropertySet != null) {
            this.outputFile.write(this.buttonPropertySet);
        }
        this.outputFile.write(new FunctionGroupCountRecord());
        this.outputFile.write(new WindowProtectRecord(false));
        this.outputFile.write(new ProtectRecord(this.wbProtected));
        this.outputFile.write(new PasswordRecord((String) null));
        this.outputFile.write(new Prot4RevRecord(false));
        this.outputFile.write(new Prot4RevPassRecord());
        this.outputFile.write(new Window1Record());
        this.outputFile.write(new BackupRecord(false));
        this.outputFile.write(new HideobjRecord(false));
        this.outputFile.write(new NineteenFourRecord(false));
        this.outputFile.write(new PrecisionRecord(false));
        this.outputFile.write(new RefreshAllRecord(false));
        this.outputFile.write(new BookboolRecord(true));
        this.fonts.write(this.outputFile);
        this.formatRecords.write(this.outputFile);
        if (this.formatRecords.getPalette() != null) {
            this.outputFile.write(this.formatRecords.getPalette());
        }
        this.outputFile.write(new UsesElfsRecord());
        int[] boundsheetPos = new int[getNumberOfSheets()];
        for (int i2 = 0; i2 < getNumberOfSheets(); i2++) {
            boundsheetPos[i2] = this.outputFile.getPos();
            Sheet sheet = getSheet(i2);
            BoundsheetRecord br = new BoundsheetRecord(sheet.getName());
            if (sheet.getSettings().isHidden()) {
                br.setHidden();
            }
            if (((WritableSheetImpl) this.sheets.get(i2)).isChartOnly()) {
                br.setChartOnly();
            }
            this.outputFile.write(br);
        }
        if (this.countryRecord == null) {
            CountryCode lang = CountryCode.getCountryCode(this.settings.getExcelDisplayLanguage());
            if (lang == CountryCode.UNKNOWN) {
                logger.warn(new StringBuffer().append("Unknown country code ").append(this.settings.getExcelDisplayLanguage()).append(" using ").append(CountryCode.USA.getCode()).toString());
                lang = CountryCode.USA;
            }
            CountryCode region = CountryCode.getCountryCode(this.settings.getExcelRegionalSettings());
            this.countryRecord = new CountryRecord(lang, region);
            if (region == CountryCode.UNKNOWN) {
                logger.warn(new StringBuffer().append("Unknown country code ").append(this.settings.getExcelDisplayLanguage()).append(" using ").append(CountryCode.UK.getCode()).toString());
                CountryCode countryCode = CountryCode.UK;
            }
        }
        this.outputFile.write(this.countryRecord);
        if (this.externSheet != null) {
            for (int i3 = 0; i3 < this.supbooks.size(); i3++) {
                this.outputFile.write((SupbookRecord) this.supbooks.get(i3));
            }
            this.outputFile.write(this.externSheet);
        }
        if (this.names != null) {
            for (int i4 = 0; i4 < this.names.size(); i4++) {
                this.outputFile.write((NameRecord) this.names.get(i4));
            }
        }
        if (this.drawingGroup != null) {
            this.drawingGroup.write(this.outputFile);
        }
        this.sharedStrings.write(this.outputFile);
        this.outputFile.write(new EOFRecord());
        boolean sheetSelected = false;
        for (int i5 = 0; i5 < getNumberOfSheets() && !sheetSelected; i5++) {
            if (((WritableSheetImpl) getSheet(i5)).getSettings().isSelected()) {
                sheetSelected = true;
            }
        }
        if (!sheetSelected) {
            ((WritableSheetImpl) getSheet(0)).getSettings().setSelected(true);
        }
        for (int i6 = 0; i6 < getNumberOfSheets(); i6++) {
            this.outputFile.setData(IntegerHelper.getFourBytes(this.outputFile.getPos()), boundsheetPos[i6] + 4);
            ((WritableSheetImpl) getSheet(i6)).write();
        }
    }

    private void copyWorkbook(Workbook w) {
        int numSheets = w.getNumberOfSheets();
        this.wbProtected = w.isProtected();
        for (int i = 0; i < numSheets; i++) {
            Sheet s = w.getSheet(i);
            ((WritableSheetImpl) createSheet(s.getName(), i, false)).copy(s);
        }
    }

    public void copySheet(int s, String name, int index) {
        ((WritableSheetImpl) createSheet(name, index)).copy(getSheet(s));
    }

    public void copySheet(String s, String name, int index) {
        ((WritableSheetImpl) createSheet(name, index)).copy(getSheet(s));
    }

    public void setProtected(boolean prot) {
        this.wbProtected = prot;
    }

    private void rationalize() {
        IndexMapping fontMapping = this.formatRecords.rationalizeFonts();
        IndexMapping formatMapping = this.formatRecords.rationalizeDisplayFormats();
        IndexMapping xfMapping = this.formatRecords.rationalize(fontMapping, formatMapping);
        for (int i = 0; i < this.sheets.size(); i++) {
            ((WritableSheetImpl) this.sheets.get(i)).rationalize(xfMapping, fontMapping, formatMapping);
        }
    }

    public String getExternalSheetName(int index) {
        SupbookRecord sr = (SupbookRecord) this.supbooks.get(this.externSheet.getSupbookIndex(index));
        int firstTab = this.externSheet.getFirstTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            return getSheet(firstTab).getName();
        }
        if (sr.getType() == SupbookRecord.EXTERNAL) {
            return new StringBuffer().append(sr.getFileName()).append(sr.getSheetName(firstTab)).toString();
        }
        return "[UNKNOWN]";
    }

    public String getLastExternalSheetName(int index) {
        SupbookRecord sr = (SupbookRecord) this.supbooks.get(this.externSheet.getSupbookIndex(index));
        int lastTab = this.externSheet.getLastTabIndex(index);
        if (sr.getType() == SupbookRecord.INTERNAL) {
            return getSheet(lastTab).getName();
        }
        if (sr.getType() == SupbookRecord.EXTERNAL) {
            Assert.verify(false);
        }
        return "[UNKNOWN]";
    }

    public BOFRecord getWorkbookBof() {
        return null;
    }

    public int getExternalSheetIndex(int index) {
        if (this.externSheet == null) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        return this.externSheet.getFirstTabIndex(index);
    }

    public int getLastExternalSheetIndex(int index) {
        if (this.externSheet == null) {
            return index;
        }
        Assert.verify(this.externSheet != null);
        return this.externSheet.getLastTabIndex(index);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0080, code lost:
        r18 = r13.getNumberOfSheets();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getExternalSheetIndex(java.lang.String r23) {
        /*
            r22 = this;
            r0 = r22
            jxl.write.biff.ExternalSheetRecord r0 = r0.externSheet
            r18 = r0
            if (r18 != 0) goto L_0x0036
            jxl.write.biff.ExternalSheetRecord r18 = new jxl.write.biff.ExternalSheetRecord
            r18.<init>()
            r0 = r18
            r1 = r22
            r1.externSheet = r0
            java.util.ArrayList r18 = new java.util.ArrayList
            r18.<init>()
            r0 = r18
            r1 = r22
            r1.supbooks = r0
            r0 = r22
            java.util.ArrayList r0 = r0.supbooks
            r18 = r0
            jxl.write.biff.SupbookRecord r19 = new jxl.write.biff.SupbookRecord
            int r20 = r22.getNumberOfSheets()
            r0 = r22
            jxl.WorkbookSettings r0 = r0.settings
            r21 = r0
            r19.<init>((int) r20, (jxl.WorkbookSettings) r21)
            r18.add(r19)
        L_0x0036:
            r5 = 0
            r0 = r22
            java.util.ArrayList r0 = r0.sheets
            r18 = r0
            java.util.Iterator r6 = r18.iterator()
            r12 = 0
            r10 = 0
        L_0x0043:
            boolean r18 = r6.hasNext()
            if (r18 == 0) goto L_0x0064
            if (r5 != 0) goto L_0x0064
            java.lang.Object r10 = r6.next()
            jxl.write.biff.WritableSheetImpl r10 = (jxl.write.biff.WritableSheetImpl) r10
            java.lang.String r18 = r10.getName()
            r0 = r18
            r1 = r23
            boolean r18 = r0.equals(r1)
            if (r18 == 0) goto L_0x0061
            r5 = 1
            goto L_0x0043
        L_0x0061:
            int r12 = r12 + 1
            goto L_0x0043
        L_0x0064:
            if (r5 == 0) goto L_0x00a7
            r0 = r22
            java.util.ArrayList r0 = r0.supbooks
            r18 = r0
            r19 = 0
            java.lang.Object r13 = r18.get(r19)
            jxl.write.biff.SupbookRecord r13 = (jxl.write.biff.SupbookRecord) r13
            jxl.write.biff.SupbookRecord$SupbookType r18 = r13.getType()
            jxl.write.biff.SupbookRecord$SupbookType r19 = jxl.write.biff.SupbookRecord.INTERNAL
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x00a4
            int r18 = r13.getNumberOfSheets()
            int r19 = r22.getNumberOfSheets()
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x00a4
            r18 = 1
        L_0x0090:
            common.Assert.verify(r18)
            r0 = r22
            jxl.write.biff.ExternalSheetRecord r0 = r0.externSheet
            r18 = r0
            r19 = 0
            r0 = r18
            r1 = r19
            int r18 = r0.getIndex(r1, r12)
        L_0x00a3:
            return r18
        L_0x00a4:
            r18 = 0
            goto L_0x0090
        L_0x00a7:
            r18 = 93
            r0 = r23
            r1 = r18
            int r2 = r0.lastIndexOf(r1)
            r18 = 91
            r0 = r23
            r1 = r18
            int r8 = r0.lastIndexOf(r1)
            r18 = -1
            r0 = r18
            if (r2 == r0) goto L_0x00c7
            r18 = -1
            r0 = r18
            if (r8 != r0) goto L_0x00ca
        L_0x00c7:
            r18 = -1
            goto L_0x00a3
        L_0x00ca:
            int r18 = r2 + 1
            r0 = r23
            r1 = r18
            java.lang.String r17 = r0.substring(r1)
            int r18 = r8 + 1
            r0 = r23
            r1 = r18
            java.lang.String r16 = r0.substring(r1, r2)
            r18 = 0
            r0 = r23
            r1 = r18
            java.lang.String r9 = r0.substring(r1, r8)
            java.lang.StringBuffer r18 = new java.lang.StringBuffer
            r18.<init>()
            r0 = r18
            java.lang.StringBuffer r18 = r0.append(r9)
            r0 = r18
            r1 = r16
            java.lang.StringBuffer r18 = r0.append(r1)
            java.lang.String r4 = r18.toString()
            r14 = 0
            r3 = 0
            r15 = -1
            r7 = 0
        L_0x0103:
            r0 = r22
            java.util.ArrayList r0 = r0.supbooks
            r18 = r0
            int r18 = r18.size()
            r0 = r18
            if (r7 >= r0) goto L_0x013e
            if (r14 != 0) goto L_0x013e
            r0 = r22
            java.util.ArrayList r0 = r0.supbooks
            r18 = r0
            r0 = r18
            java.lang.Object r3 = r0.get(r7)
            jxl.write.biff.SupbookRecord r3 = (jxl.write.biff.SupbookRecord) r3
            jxl.write.biff.SupbookRecord$SupbookType r18 = r3.getType()
            jxl.write.biff.SupbookRecord$SupbookType r19 = jxl.write.biff.SupbookRecord.EXTERNAL
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x013b
            java.lang.String r18 = r3.getFileName()
            r0 = r18
            boolean r18 = r0.equals(r4)
            if (r18 == 0) goto L_0x013b
            r14 = 1
            r15 = r7
        L_0x013b:
            int r7 = r7 + 1
            goto L_0x0103
        L_0x013e:
            if (r14 != 0) goto L_0x0162
            jxl.write.biff.SupbookRecord r3 = new jxl.write.biff.SupbookRecord
            r0 = r22
            jxl.WorkbookSettings r0 = r0.settings
            r18 = r0
            r0 = r18
            r3.<init>((java.lang.String) r4, (jxl.WorkbookSettings) r0)
            r0 = r22
            java.util.ArrayList r0 = r0.supbooks
            r18 = r0
            int r15 = r18.size()
            r0 = r22
            java.util.ArrayList r0 = r0.supbooks
            r18 = r0
            r0 = r18
            r0.add(r3)
        L_0x0162:
            r0 = r17
            int r11 = r3.getSheetIndex(r0)
            r0 = r22
            jxl.write.biff.ExternalSheetRecord r0 = r0.externSheet
            r18 = r0
            r0 = r18
            int r18 = r0.getIndex(r15, r11)
            goto L_0x00a3
        */
        throw new UnsupportedOperationException("Method not decompiled: jxl.write.biff.WritableWorkbookImpl.getExternalSheetIndex(java.lang.String):int");
    }

    public int getLastExternalSheetIndex(String sheetName) {
        boolean z;
        if (this.externSheet == null) {
            this.externSheet = new ExternalSheetRecord();
            this.supbooks = new ArrayList();
            this.supbooks.add(new SupbookRecord(getNumberOfSheets(), this.settings));
        }
        boolean found = false;
        Iterator i = this.sheets.iterator();
        int sheetpos = 0;
        while (i.hasNext() && !found) {
            if (((WritableSheetImpl) i.next()).getName().equals(sheetName)) {
                found = true;
            } else {
                sheetpos++;
            }
        }
        if (!found) {
            return -1;
        }
        SupbookRecord supbook = (SupbookRecord) this.supbooks.get(0);
        if (supbook.getType() == SupbookRecord.INTERNAL && supbook.getNumberOfSheets() == getNumberOfSheets()) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        return this.externSheet.getIndex(0, sheetpos);
    }

    public void setColourRGB(Colour c, int r, int g, int b) {
        this.formatRecords.setColourRGB(c, r, g, b);
    }

    public RGB getColourRGB(Colour c) {
        return this.formatRecords.getColourRGB(c);
    }

    public String getName(int index) {
        Assert.verify(index >= 0 && index < this.names.size());
        return ((NameRecord) this.names.get(index)).getName();
    }

    public int getNameIndex(String name) {
        NameRecord nr = (NameRecord) this.nameRecords.get(name);
        if (nr != null) {
            return nr.getIndex();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void addRCIRCell(CellValue cv) {
        this.rcirCells.add(cv);
    }

    /* access modifiers changed from: package-private */
    public void columnInserted(WritableSheetImpl s, int col) {
        int externalSheetIndex = getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            ((CellValue) i.next()).columnInserted(s, externalSheetIndex, col);
        }
    }

    /* access modifiers changed from: package-private */
    public void columnRemoved(WritableSheetImpl s, int col) {
        int externalSheetIndex = getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            ((CellValue) i.next()).columnRemoved(s, externalSheetIndex, col);
        }
    }

    /* access modifiers changed from: package-private */
    public void rowInserted(WritableSheetImpl s, int row) {
        int externalSheetIndex = getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            ((CellValue) i.next()).rowInserted(s, externalSheetIndex, row);
        }
    }

    /* access modifiers changed from: package-private */
    public void rowRemoved(WritableSheetImpl s, int row) {
        int externalSheetIndex = getExternalSheetIndex(s.getName());
        Iterator i = this.rcirCells.iterator();
        while (i.hasNext()) {
            ((CellValue) i.next()).rowRemoved(s, externalSheetIndex, row);
        }
    }

    public WritableCell findCellByName(String name) {
        NameRecord nr = (NameRecord) this.nameRecords.get(name);
        if (nr == null) {
            return null;
        }
        NameRecord.NameRange[] ranges = nr.getRanges();
        return getSheet(getExternalSheetIndex(ranges[0].getExternalSheet())).getWritableCell(ranges[0].getFirstColumn(), ranges[0].getFirstRow());
    }

    public Range[] findByName(String name) {
        NameRecord nr = (NameRecord) this.nameRecords.get(name);
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

    /* access modifiers changed from: package-private */
    public void addDrawing(DrawingGroupObject d) {
        if (this.drawingGroup == null) {
            this.drawingGroup = new DrawingGroup(Origin.WRITE);
        }
        this.drawingGroup.add(d);
    }

    /* access modifiers changed from: package-private */
    public void removeDrawing(Drawing d) {
        Assert.verify(this.drawingGroup != null);
        this.drawingGroup.remove(d);
    }

    /* access modifiers changed from: package-private */
    public DrawingGroup getDrawingGroup() {
        return this.drawingGroup;
    }

    public String[] getRangeNames() {
        String[] n = new String[this.names.size()];
        for (int i = 0; i < this.names.size(); i++) {
            n[i] = ((NameRecord) this.names.get(i)).getName();
        }
        return n;
    }

    /* access modifiers changed from: package-private */
    public Styles getStyles() {
        return this.styles;
    }

    public void addNameArea(String name, WritableSheet sheet, int firstCol, int firstRow, int lastCol, int lastRow) {
        if (this.names == null) {
            this.names = new ArrayList();
        }
        int externalSheetIndex = getExternalSheetIndex(sheet.getName());
        NameRecord nr = new NameRecord(name, this.names.size(), externalSheetIndex, firstRow, lastRow, firstCol, lastCol);
        this.names.add(nr);
        this.nameRecords.put(name, nr);
    }

    /* access modifiers changed from: package-private */
    public WorkbookSettings getSettings() {
        return this.settings;
    }
}

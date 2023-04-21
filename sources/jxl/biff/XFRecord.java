package jxl.biff;

import android.support.v4.media.TransportMediator;
import common.Assert;
import common.Logger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Format;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.read.biff.Record;

public class XFRecord extends WritableRecordData implements CellFormat {
    public static final BiffType biff7 = new BiffType((AnonymousClass1) null);
    public static final BiffType biff8 = new BiffType((AnonymousClass1) null);
    protected static final XFType cell = new XFType((AnonymousClass1) null);
    static Class class$jxl$biff$XFRecord;
    private static final int[] dateFormats = {14, 15, 16, 17, 18, 19, 20, 21, 22, 45, 46, 47};
    private static final DateFormat[] javaDateFormats = {new SimpleDateFormat("dd/MM/yyyy"), new SimpleDateFormat("d-MMM-yy"), new SimpleDateFormat("d-MMM"), new SimpleDateFormat("MMM-yy"), new SimpleDateFormat("h:mm a"), new SimpleDateFormat("h:mm:ss a"), new SimpleDateFormat("H:mm"), new SimpleDateFormat("H:mm:ss"), new SimpleDateFormat("M/d/yy H:mm"), new SimpleDateFormat("mm:ss"), new SimpleDateFormat("H:mm:ss"), new SimpleDateFormat("mm:ss.S")};
    private static NumberFormat[] javaNumberFormats = {new DecimalFormat("0"), new DecimalFormat("0.00"), new DecimalFormat("#,##0"), new DecimalFormat("#,##0.00"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("0%"), new DecimalFormat("0.00%"), new DecimalFormat("0.00E00"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("##0.0E0")};
    private static Logger logger;
    private static int[] numberFormats = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 37, 38, 39, 40, 41, 42, 43, 44, 48};
    protected static final XFType style = new XFType((AnonymousClass1) null);
    private Alignment align;
    private Colour backgroundColour;
    private BiffType biffType;
    private BorderLineStyle bottomBorder;
    private Colour bottomBorderColour;
    private boolean copied;
    private boolean date;
    private DateFormat dateFormat;
    private Format excelFormat;
    private FontRecord font;
    private int fontIndex;
    private DisplayFormat format;
    public int formatIndex;
    private boolean formatInfoInitialized;
    private FormattingRecords formattingRecords;
    private boolean hidden;
    private int indentation;
    private boolean initialized;
    private BorderLineStyle leftBorder;
    private Colour leftBorderColour;
    private boolean locked;
    private boolean number;
    private NumberFormat numberFormat;
    private int options;
    private Orientation orientation;
    private int parentFormat;
    private Pattern pattern;
    private boolean read;
    private BorderLineStyle rightBorder;
    private Colour rightBorderColour;
    private boolean shrinkToFit;
    private BorderLineStyle topBorder;
    private Colour topBorderColour;
    private byte usedAttributes;
    private VerticalAlignment valign;
    private boolean wrap;
    private XFType xfFormatType;
    private int xfIndex;

    /* renamed from: jxl.biff.XFRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$biff$XFRecord == null) {
            cls = class$("jxl.biff.XFRecord");
            class$jxl$biff$XFRecord = cls;
        } else {
            cls = class$jxl$biff$XFRecord;
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

    private static class BiffType {
        private BiffType() {
        }

        BiffType(AnonymousClass1 x0) {
            this();
        }
    }

    private static class XFType {
        private XFType() {
        }

        XFType(AnonymousClass1 x0) {
            this();
        }
    }

    public XFRecord(Record t, WorkbookSettings ws, BiffType bt) {
        super(t);
        boolean z;
        boolean z2;
        this.biffType = bt;
        byte[] data = getRecord().getData();
        this.fontIndex = IntegerHelper.getInt(data[0], data[1]);
        this.formatIndex = IntegerHelper.getInt(data[2], data[3]);
        this.date = false;
        this.number = false;
        for (int i = 0; i < dateFormats.length && !this.date; i++) {
            if (this.formatIndex == dateFormats[i]) {
                this.date = true;
                this.dateFormat = javaDateFormats[i];
            }
        }
        for (int i2 = 0; i2 < numberFormats.length && !this.number; i2++) {
            if (this.formatIndex == numberFormats[i2]) {
                this.number = true;
                DecimalFormat df = (DecimalFormat) javaNumberFormats[i2].clone();
                df.setDecimalFormatSymbols(new DecimalFormatSymbols(ws.getLocale()));
                this.numberFormat = df;
            }
        }
        int cellAttributes = IntegerHelper.getInt(data[4], data[5]);
        this.parentFormat = (65520 & cellAttributes) >> 4;
        this.xfFormatType = (cellAttributes & 4) == 0 ? cell : style;
        if ((cellAttributes & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.locked = z;
        if ((cellAttributes & 2) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.hidden = z2;
        if (this.xfFormatType == cell && (this.parentFormat & 4095) == 4095) {
            this.parentFormat = 0;
            logger.warn("Invalid parent format found - ignoring");
        }
        this.initialized = false;
        this.read = true;
        this.formatInfoInitialized = false;
        this.copied = false;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public XFRecord(FontRecord fnt, DisplayFormat form) {
        super(Type.XF);
        boolean z;
        boolean z2 = true;
        this.initialized = false;
        this.locked = true;
        this.hidden = false;
        this.align = Alignment.GENERAL;
        this.valign = VerticalAlignment.BOTTOM;
        this.orientation = Orientation.HORIZONTAL;
        this.wrap = false;
        this.leftBorder = BorderLineStyle.NONE;
        this.rightBorder = BorderLineStyle.NONE;
        this.topBorder = BorderLineStyle.NONE;
        this.bottomBorder = BorderLineStyle.NONE;
        this.leftBorderColour = Colour.AUTOMATIC;
        this.rightBorderColour = Colour.AUTOMATIC;
        this.topBorderColour = Colour.AUTOMATIC;
        this.bottomBorderColour = Colour.AUTOMATIC;
        this.pattern = Pattern.NONE;
        this.backgroundColour = Colour.DEFAULT_BACKGROUND;
        this.indentation = 0;
        this.shrinkToFit = false;
        this.parentFormat = 0;
        this.xfFormatType = null;
        this.font = fnt;
        this.format = form;
        this.biffType = biff8;
        this.read = false;
        this.copied = false;
        this.formatInfoInitialized = true;
        if (this.font != null) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        Assert.verify(this.format == null ? false : z2);
    }

    protected XFRecord(XFRecord fmt) {
        super(Type.XF);
        this.initialized = false;
        this.locked = fmt.locked;
        this.hidden = fmt.hidden;
        this.align = fmt.align;
        this.valign = fmt.valign;
        this.orientation = fmt.orientation;
        this.wrap = fmt.wrap;
        this.leftBorder = fmt.leftBorder;
        this.rightBorder = fmt.rightBorder;
        this.topBorder = fmt.topBorder;
        this.bottomBorder = fmt.bottomBorder;
        this.leftBorderColour = fmt.leftBorderColour;
        this.rightBorderColour = fmt.rightBorderColour;
        this.topBorderColour = fmt.topBorderColour;
        this.bottomBorderColour = fmt.bottomBorderColour;
        this.pattern = fmt.pattern;
        this.xfFormatType = fmt.xfFormatType;
        this.indentation = fmt.indentation;
        this.shrinkToFit = fmt.shrinkToFit;
        this.parentFormat = fmt.parentFormat;
        this.backgroundColour = fmt.backgroundColour;
        this.font = fmt.font;
        this.format = fmt.format;
        this.fontIndex = fmt.fontIndex;
        this.formatIndex = fmt.formatIndex;
        this.formatInfoInitialized = fmt.formatInfoInitialized;
        this.biffType = biff8;
        this.read = false;
        this.copied = true;
    }

    protected XFRecord(CellFormat cellFormat) {
        super(Type.XF);
        boolean z;
        if (cellFormat != null) {
            z = true;
        } else {
            z = false;
        }
        Assert.verify(z);
        Assert.verify(cellFormat instanceof XFRecord);
        XFRecord fmt = (XFRecord) cellFormat;
        if (!fmt.formatInfoInitialized) {
            fmt.initializeFormatInformation();
        }
        this.locked = fmt.locked;
        this.hidden = fmt.hidden;
        this.align = fmt.align;
        this.valign = fmt.valign;
        this.orientation = fmt.orientation;
        this.wrap = fmt.wrap;
        this.leftBorder = fmt.leftBorder;
        this.rightBorder = fmt.rightBorder;
        this.topBorder = fmt.topBorder;
        this.bottomBorder = fmt.bottomBorder;
        this.leftBorderColour = fmt.leftBorderColour;
        this.rightBorderColour = fmt.rightBorderColour;
        this.topBorderColour = fmt.topBorderColour;
        this.bottomBorderColour = fmt.bottomBorderColour;
        this.pattern = fmt.pattern;
        this.xfFormatType = fmt.xfFormatType;
        this.parentFormat = fmt.parentFormat;
        this.indentation = fmt.indentation;
        this.shrinkToFit = fmt.shrinkToFit;
        this.backgroundColour = fmt.backgroundColour;
        this.font = new FontRecord(fmt.getFont());
        if (fmt.getFormat() == null) {
            if (fmt.format.isBuiltIn()) {
                this.format = fmt.format;
            } else {
                this.format = new FormatRecord((FormatRecord) fmt.format);
            }
        } else if (fmt.getFormat() instanceof BuiltInFormat) {
            this.excelFormat = (BuiltInFormat) fmt.excelFormat;
            this.format = (BuiltInFormat) fmt.excelFormat;
        } else {
            Assert.verify(fmt.formatInfoInitialized);
            Assert.verify(fmt.excelFormat instanceof FormatRecord);
            FormatRecord fr = new FormatRecord((FormatRecord) fmt.excelFormat);
            this.excelFormat = fr;
            this.format = fr;
        }
        this.biffType = biff8;
        this.formatInfoInitialized = true;
        this.read = false;
        this.copied = false;
        this.initialized = false;
    }

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }

    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public int getFormatRecord() {
        return this.formatIndex;
    }

    public boolean isDate() {
        return this.date;
    }

    public boolean isNumber() {
        return this.number;
    }

    public byte[] getData() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        byte[] data = new byte[20];
        IntegerHelper.getTwoBytes(this.fontIndex, data, 0);
        IntegerHelper.getTwoBytes(this.formatIndex, data, 2);
        int cellAttributes = 0;
        if (getLocked()) {
            cellAttributes = 0 | 1;
        }
        if (getHidden()) {
            cellAttributes |= 2;
        }
        if (this.xfFormatType == style) {
            cellAttributes |= 4;
            this.parentFormat = 65535;
        }
        IntegerHelper.getTwoBytes(cellAttributes | (this.parentFormat << 4), data, 4);
        int alignMask = this.align.getValue();
        if (this.wrap) {
            alignMask |= 8;
        }
        IntegerHelper.getTwoBytes(alignMask | (this.valign.getValue() << 4) | (this.orientation.getValue() << 8), data, 6);
        data[9] = 16;
        int borderMask = this.leftBorder.getValue() | (this.rightBorder.getValue() << 4) | (this.topBorder.getValue() << 8) | (this.bottomBorder.getValue() << 12);
        IntegerHelper.getTwoBytes(borderMask, data, 10);
        if (borderMask != 0) {
            int lc = (byte) this.leftBorderColour.getValue();
            byte rc = (byte) this.rightBorderColour.getValue();
            int tc = (byte) this.topBorderColour.getValue();
            byte bc = (byte) this.bottomBorderColour.getValue();
            IntegerHelper.getTwoBytes((lc & TransportMediator.KEYCODE_MEDIA_PAUSE) | ((rc & Byte.MAX_VALUE) << 7), data, 12);
            IntegerHelper.getTwoBytes((tc & TransportMediator.KEYCODE_MEDIA_PAUSE) | ((bc & Byte.MAX_VALUE) << 7), data, 14);
        }
        IntegerHelper.getTwoBytes(this.pattern.getValue() << 10, data, 16);
        IntegerHelper.getTwoBytes(this.backgroundColour.getValue() | 8192, data, 18);
        this.options |= this.indentation & 15;
        if (this.shrinkToFit) {
            this.options |= 16;
        } else {
            this.options &= 239;
        }
        data[8] = (byte) this.options;
        if (this.biffType == biff8) {
            data[9] = this.usedAttributes;
        }
        return data;
    }

    /* access modifiers changed from: protected */
    public final boolean getLocked() {
        return this.locked;
    }

    /* access modifiers changed from: protected */
    public final boolean getHidden() {
        return this.hidden;
    }

    /* access modifiers changed from: protected */
    public final void setXFLocked(boolean l) {
        this.locked = l;
    }

    /* access modifiers changed from: protected */
    public final void setXFCellOptions(int opt) {
        this.options |= opt;
    }

    /* access modifiers changed from: protected */
    public void setXFAlignment(Alignment a) {
        Assert.verify(!this.initialized);
        this.align = a;
    }

    /* access modifiers changed from: protected */
    public void setXFIndentation(int i) {
        Assert.verify(!this.initialized);
        this.indentation = i;
    }

    /* access modifiers changed from: protected */
    public void setXFShrinkToFit(boolean s) {
        Assert.verify(!this.initialized);
        this.shrinkToFit = s;
    }

    public Alignment getAlignment() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.align;
    }

    public int getIndentation() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.indentation;
    }

    public boolean isShrinkToFit() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.shrinkToFit;
    }

    public boolean isLocked() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.locked;
    }

    public VerticalAlignment getVerticalAlignment() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.valign;
    }

    public Orientation getOrientation() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.orientation;
    }

    /* access modifiers changed from: protected */
    public void setXFBackground(Colour c, Pattern p) {
        Assert.verify(!this.initialized);
        this.backgroundColour = c;
        this.pattern = p;
    }

    public Colour getBackgroundColour() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.backgroundColour;
    }

    public Pattern getPattern() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.pattern;
    }

    /* access modifiers changed from: protected */
    public void setXFVerticalAlignment(VerticalAlignment va) {
        Assert.verify(!this.initialized);
        this.valign = va;
    }

    /* access modifiers changed from: protected */
    public void setXFOrientation(Orientation o) {
        Assert.verify(!this.initialized);
        this.orientation = o;
    }

    /* access modifiers changed from: protected */
    public void setXFWrap(boolean w) {
        Assert.verify(!this.initialized);
        this.wrap = w;
    }

    public boolean getWrap() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.wrap;
    }

    /* access modifiers changed from: protected */
    public void setXFBorder(Border b, BorderLineStyle ls, Colour c) {
        Assert.verify(!this.initialized);
        if (c == Colour.BLACK) {
            c = Colour.PALETTE_BLACK;
        }
        if (b == Border.LEFT) {
            this.leftBorder = ls;
            this.leftBorderColour = c;
        } else if (b == Border.RIGHT) {
            this.rightBorder = ls;
            this.rightBorderColour = c;
        } else if (b == Border.TOP) {
            this.topBorder = ls;
            this.topBorderColour = c;
        } else if (b == Border.BOTTOM) {
            this.bottomBorder = ls;
            this.bottomBorderColour = c;
        }
    }

    public BorderLineStyle getBorder(Border border) {
        return getBorderLine(border);
    }

    public BorderLineStyle getBorderLine(Border border) {
        if (border == Border.NONE || border == Border.ALL) {
            return BorderLineStyle.NONE;
        }
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        if (border == Border.LEFT) {
            return this.leftBorder;
        }
        if (border == Border.RIGHT) {
            return this.rightBorder;
        }
        if (border == Border.TOP) {
            return this.topBorder;
        }
        if (border == Border.BOTTOM) {
            return this.bottomBorder;
        }
        return BorderLineStyle.NONE;
    }

    public Colour getBorderColour(Border border) {
        if (border == Border.NONE || border == Border.ALL) {
            return Colour.PALETTE_BLACK;
        }
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        if (border == Border.LEFT) {
            return this.leftBorderColour;
        }
        if (border == Border.RIGHT) {
            return this.rightBorderColour;
        }
        if (border == Border.TOP) {
            return this.topBorderColour;
        }
        if (border == Border.BOTTOM) {
            return this.bottomBorderColour;
        }
        return Colour.BLACK;
    }

    public final boolean hasBorders() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        if (this.leftBorder == BorderLineStyle.NONE && this.rightBorder == BorderLineStyle.NONE && this.topBorder == BorderLineStyle.NONE && this.bottomBorder == BorderLineStyle.NONE) {
            return false;
        }
        return true;
    }

    public final void initialize(int pos, FormattingRecords fr, Fonts fonts) throws NumFormatRecordsException {
        this.xfIndex = pos;
        this.formattingRecords = fr;
        if (this.read || this.copied) {
            this.initialized = true;
            return;
        }
        if (!this.font.isInitialized()) {
            fonts.addFont(this.font);
        }
        if (!this.format.isInitialized()) {
            fr.addFormat(this.format);
        }
        this.fontIndex = this.font.getFontIndex();
        this.formatIndex = this.format.getFormatIndex();
        this.initialized = true;
    }

    public final void uninitialize() {
        if (this.initialized) {
            logger.warn("A default format has been initialized");
        }
        this.initialized = false;
    }

    /* access modifiers changed from: package-private */
    public final void setXFIndex(int xfi) {
        this.xfIndex = xfi;
    }

    public final int getXFIndex() {
        return this.xfIndex;
    }

    public final boolean isInitialized() {
        return this.initialized;
    }

    public final boolean isRead() {
        return this.read;
    }

    public Format getFormat() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.excelFormat;
    }

    public Font getFont() {
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        return this.font;
    }

    private void initializeFormatInformation() {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (this.formatIndex >= BuiltInFormat.builtIns.length || BuiltInFormat.builtIns[this.formatIndex] == null) {
            this.excelFormat = this.formattingRecords.getFormatRecord(this.formatIndex);
        } else {
            this.excelFormat = BuiltInFormat.builtIns[this.formatIndex];
        }
        this.font = this.formattingRecords.getFonts().getFont(this.fontIndex);
        byte[] data = getRecord().getData();
        int cellAttributes = IntegerHelper.getInt(data[4], data[5]);
        this.parentFormat = (65520 & cellAttributes) >> 4;
        this.xfFormatType = (cellAttributes & 4) == 0 ? cell : style;
        if ((cellAttributes & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.locked = z;
        if ((cellAttributes & 2) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.hidden = z2;
        if (this.xfFormatType == cell && (this.parentFormat & 4095) == 4095) {
            this.parentFormat = 0;
            logger.warn("Invalid parent format found - ignoring");
        }
        int alignMask = IntegerHelper.getInt(data[6], data[7]);
        if ((alignMask & 8) != 0) {
            this.wrap = true;
        }
        this.align = Alignment.getAlignment(alignMask & 7);
        this.valign = VerticalAlignment.getAlignment((alignMask >> 4) & 7);
        this.orientation = Orientation.getOrientation((alignMask >> 8) & 255);
        int attr = IntegerHelper.getInt(data[8], data[9]);
        this.indentation = attr & 15;
        if ((attr & 16) != 0) {
            z3 = true;
        }
        this.shrinkToFit = z3;
        if (this.biffType == biff8) {
            this.usedAttributes = data[9];
        }
        int borderMask = IntegerHelper.getInt(data[10], data[11]);
        this.leftBorder = BorderLineStyle.getStyle(borderMask & 7);
        this.rightBorder = BorderLineStyle.getStyle((borderMask >> 4) & 7);
        this.topBorder = BorderLineStyle.getStyle((borderMask >> 8) & 7);
        this.bottomBorder = BorderLineStyle.getStyle((borderMask >> 12) & 7);
        int borderColourMask = IntegerHelper.getInt(data[12], data[13]);
        this.leftBorderColour = Colour.getInternalColour(borderColourMask & TransportMediator.KEYCODE_MEDIA_PAUSE);
        this.rightBorderColour = Colour.getInternalColour((borderColourMask & 16256) >> 7);
        int borderColourMask2 = IntegerHelper.getInt(data[14], data[15]);
        this.topBorderColour = Colour.getInternalColour(borderColourMask2 & TransportMediator.KEYCODE_MEDIA_PAUSE);
        this.bottomBorderColour = Colour.getInternalColour((borderColourMask2 & 16256) >> 7);
        if (this.biffType == biff8) {
            this.pattern = Pattern.getPattern((IntegerHelper.getInt(data[16], data[17]) & 64512) >> 10);
            this.backgroundColour = Colour.getInternalColour(IntegerHelper.getInt(data[18], data[19]) & 63);
            if (this.backgroundColour == Colour.UNKNOWN || this.backgroundColour == Colour.DEFAULT_BACKGROUND1) {
                this.backgroundColour = Colour.DEFAULT_BACKGROUND;
            }
        } else {
            this.pattern = Pattern.NONE;
            this.backgroundColour = Colour.DEFAULT_BACKGROUND;
        }
        this.formatInfoInitialized = true;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3 = 1;
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        int i4 = 37 * ((this.hidden ? 1 : 0) + 629);
        if (this.locked) {
            i = 1;
        } else {
            i = 0;
        }
        int i5 = 37 * (i4 + i);
        if (this.wrap) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        int i6 = 37 * (i5 + i2);
        if (!this.shrinkToFit) {
            i3 = 0;
        }
        int hashValue = i6 + i3;
        if (this.xfFormatType == cell) {
            hashValue = (37 * hashValue) + 1;
        } else if (this.xfFormatType == style) {
            hashValue = (37 * hashValue) + 2;
        }
        return (37 * ((37 * ((37 * ((37 * ((37 * ((37 * ((37 * ((37 * ((37 * ((37 * ((37 * ((((((37 * ((37 * ((37 * hashValue) + (this.align.getValue() + 1))) + (this.valign.getValue() + 1))) + this.orientation.getValue()) ^ this.leftBorder.getDescription().hashCode()) ^ this.rightBorder.getDescription().hashCode()) ^ this.topBorder.getDescription().hashCode()) ^ this.bottomBorder.getDescription().hashCode())) + this.leftBorderColour.getValue())) + this.rightBorderColour.getValue())) + this.topBorderColour.getValue())) + this.bottomBorderColour.getValue())) + this.backgroundColour.getValue())) + this.pattern.getValue() + 1)) + this.usedAttributes)) + this.parentFormat)) + this.fontIndex)) + this.formatIndex)) + this.indentation;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof XFRecord)) {
            return false;
        }
        XFRecord xfr = (XFRecord) o;
        if (!this.formatInfoInitialized) {
            initializeFormatInformation();
        }
        if (!xfr.formatInfoInitialized) {
            xfr.initializeFormatInformation();
        }
        if (this.xfFormatType != xfr.xfFormatType || this.parentFormat != xfr.parentFormat || this.locked != xfr.locked || this.hidden != xfr.hidden || this.usedAttributes != xfr.usedAttributes) {
            return false;
        }
        if (this.align != xfr.align || this.valign != xfr.valign || this.orientation != xfr.orientation || this.wrap != xfr.wrap || this.shrinkToFit != xfr.shrinkToFit || this.indentation != xfr.indentation) {
            return false;
        }
        if (this.leftBorder != xfr.leftBorder || this.rightBorder != xfr.rightBorder || this.topBorder != xfr.topBorder || this.bottomBorder != xfr.bottomBorder) {
            return false;
        }
        if (this.leftBorderColour != xfr.leftBorderColour || this.rightBorderColour != xfr.rightBorderColour || this.topBorderColour != xfr.topBorderColour || this.bottomBorderColour != xfr.bottomBorderColour) {
            return false;
        }
        if (this.backgroundColour != xfr.backgroundColour || this.pattern != xfr.pattern) {
            return false;
        }
        if (!this.initialized || !xfr.initialized) {
            if (!this.font.equals(xfr.font) || !this.format.equals(xfr.format)) {
                return false;
            }
            return true;
        } else if (this.fontIndex == xfr.fontIndex && this.formatIndex == xfr.formatIndex) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void setFormatIndex(int newindex) {
        this.formatIndex = newindex;
    }

    /* access modifiers changed from: package-private */
    public int getFontIndex() {
        return this.fontIndex;
    }

    /* access modifiers changed from: package-private */
    public void setFontIndex(int newindex) {
        this.fontIndex = newindex;
    }

    /* access modifiers changed from: protected */
    public void setXFDetails(XFType t, int pf) {
        this.xfFormatType = t;
        this.parentFormat = pf;
    }

    /* access modifiers changed from: package-private */
    public void rationalize(IndexMapping xfMapping) {
        this.xfIndex = xfMapping.getNewIndex(this.xfIndex);
        if (this.xfFormatType == cell) {
            this.parentFormat = xfMapping.getNewIndex(this.parentFormat);
        }
    }

    public void setFont(FontRecord f) {
        this.font = f;
    }
}

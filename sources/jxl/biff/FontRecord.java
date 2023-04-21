package jxl.biff;

import common.Assert;
import common.Logger;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.read.biff.Record;

public class FontRecord extends WritableRecordData implements Font {
    private static final int EXCEL_UNITS_PER_POINT = 20;
    public static final Biff7 biff7 = new Biff7((AnonymousClass1) null);
    static Class class$jxl$biff$FontRecord;
    private static Logger logger;
    private int boldWeight;
    private byte characterSet;
    private int colourIndex;
    private byte fontFamily;
    private int fontIndex;
    private boolean initialized;
    private boolean italic;
    private String name;
    private int pointHeight;
    private int scriptStyle;
    private boolean struckout;
    private int underlineStyle;

    /* renamed from: jxl.biff.FontRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$biff$FontRecord == null) {
            cls = class$("jxl.biff.FontRecord");
            class$jxl$biff$FontRecord = cls;
        } else {
            cls = class$jxl$biff$FontRecord;
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

    private static class Biff7 {
        private Biff7() {
        }

        Biff7(AnonymousClass1 x0) {
            this();
        }
    }

    protected FontRecord(String fn, int ps, int bold, boolean it, int us, int ci, int ss) {
        super(Type.FONT);
        this.boldWeight = bold;
        this.underlineStyle = us;
        this.name = fn;
        this.pointHeight = ps;
        this.italic = it;
        this.scriptStyle = ss;
        this.colourIndex = ci;
        this.initialized = false;
        this.struckout = false;
    }

    public FontRecord(Record t, WorkbookSettings ws) {
        super(t);
        byte[] data = getRecord().getData();
        this.pointHeight = IntegerHelper.getInt(data[0], data[1]) / 20;
        this.colourIndex = IntegerHelper.getInt(data[4], data[5]);
        this.boldWeight = IntegerHelper.getInt(data[6], data[7]);
        this.scriptStyle = IntegerHelper.getInt(data[8], data[9]);
        this.underlineStyle = data[10];
        this.fontFamily = data[11];
        this.characterSet = data[12];
        this.initialized = false;
        if ((data[2] & 2) != 0) {
            this.italic = true;
        }
        if ((data[2] & 8) != 0) {
            this.struckout = true;
        }
        byte numChars = data[14];
        if (data[15] == 0) {
            this.name = StringHelper.getString(data, numChars, 16, ws);
        } else if (data[15] == 1) {
            this.name = StringHelper.getUnicodeString(data, numChars, 16);
        } else {
            this.name = StringHelper.getString(data, numChars, 15, ws);
        }
    }

    public FontRecord(Record t, WorkbookSettings ws, Biff7 dummy) {
        super(t);
        byte[] data = getRecord().getData();
        this.pointHeight = IntegerHelper.getInt(data[0], data[1]) / 20;
        this.colourIndex = IntegerHelper.getInt(data[4], data[5]);
        this.boldWeight = IntegerHelper.getInt(data[6], data[7]);
        this.scriptStyle = IntegerHelper.getInt(data[8], data[9]);
        this.underlineStyle = data[10];
        this.fontFamily = data[11];
        this.initialized = false;
        if ((data[2] & 2) != 0) {
            this.italic = true;
        }
        if ((data[2] & 8) != 0) {
            this.struckout = true;
        }
        this.name = StringHelper.getString(data, data[14], 15, ws);
    }

    protected FontRecord(Font f) {
        super(Type.FONT);
        Assert.verify(f != null);
        this.pointHeight = f.getPointSize();
        this.colourIndex = f.getColour().getValue();
        this.boldWeight = f.getBoldWeight();
        this.scriptStyle = f.getScriptStyle().getValue();
        this.underlineStyle = f.getUnderlineStyle().getValue();
        this.italic = f.isItalic();
        this.name = f.getName();
        this.struckout = false;
        this.initialized = false;
    }

    public byte[] getData() {
        byte[] data = new byte[((this.name.length() * 2) + 16)];
        IntegerHelper.getTwoBytes(this.pointHeight * 20, data, 0);
        if (this.italic) {
            data[2] = (byte) (data[2] | 2);
        }
        if (this.struckout) {
            data[2] = (byte) (data[2] | 8);
        }
        IntegerHelper.getTwoBytes(this.colourIndex, data, 4);
        IntegerHelper.getTwoBytes(this.boldWeight, data, 6);
        IntegerHelper.getTwoBytes(this.scriptStyle, data, 8);
        data[10] = (byte) this.underlineStyle;
        data[11] = this.fontFamily;
        data[12] = this.characterSet;
        data[13] = 0;
        data[14] = (byte) this.name.length();
        data[15] = 1;
        StringHelper.getUnicodeBytes(this.name, data, 16);
        return data;
    }

    public final boolean isInitialized() {
        return this.initialized;
    }

    public final void initialize(int pos) {
        this.fontIndex = pos;
        this.initialized = true;
    }

    public final void uninitialize() {
        this.initialized = false;
    }

    public final int getFontIndex() {
        return this.fontIndex;
    }

    /* access modifiers changed from: protected */
    public void setFontPointSize(int ps) {
        Assert.verify(!this.initialized);
        this.pointHeight = ps;
    }

    public int getPointSize() {
        return this.pointHeight;
    }

    /* access modifiers changed from: protected */
    public void setFontBoldStyle(int bs) {
        Assert.verify(!this.initialized);
        this.boldWeight = bs;
    }

    public int getBoldWeight() {
        return this.boldWeight;
    }

    /* access modifiers changed from: protected */
    public void setFontItalic(boolean i) {
        Assert.verify(!this.initialized);
        this.italic = i;
    }

    public boolean isItalic() {
        return this.italic;
    }

    /* access modifiers changed from: protected */
    public void setFontUnderlineStyle(int us) {
        Assert.verify(!this.initialized);
        this.underlineStyle = us;
    }

    public UnderlineStyle getUnderlineStyle() {
        return UnderlineStyle.getStyle(this.underlineStyle);
    }

    /* access modifiers changed from: protected */
    public void setFontColour(int c) {
        Assert.verify(!this.initialized);
        this.colourIndex = c;
    }

    public Colour getColour() {
        return Colour.getInternalColour(this.colourIndex);
    }

    /* access modifiers changed from: protected */
    public void setFontScriptStyle(int ss) {
        Assert.verify(!this.initialized);
        this.scriptStyle = ss;
    }

    public ScriptStyle getScriptStyle() {
        return ScriptStyle.getStyle(this.scriptStyle);
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FontRecord)) {
            return false;
        }
        FontRecord font = (FontRecord) o;
        if (this.pointHeight == font.pointHeight && this.colourIndex == font.colourIndex && this.boldWeight == font.boldWeight && this.scriptStyle == font.scriptStyle && this.underlineStyle == font.underlineStyle && this.italic == font.italic && this.struckout == font.struckout && this.fontFamily == font.fontFamily && this.characterSet == font.characterSet && this.name.equals(font.name)) {
            return true;
        }
        return false;
    }

    public boolean isStruckout() {
        return this.struckout;
    }

    /* access modifiers changed from: protected */
    public void setFontStruckout(boolean os) {
        this.struckout = os;
    }
}

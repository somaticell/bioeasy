package jxl.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.format.Colour;
import jxl.format.RGB;
import jxl.write.biff.File;

public class FormattingRecords {
    static Class class$jxl$biff$FormattingRecords = null;
    private static final int customFormatStartIndex = 164;
    private static Logger logger = null;
    private static final int maxFormatRecordsIndex = 441;
    private static final int minXFRecords = 21;
    private Fonts fonts;
    private HashMap formats = new HashMap(10);
    private ArrayList formatsList = new ArrayList(10);
    private int nextCustomIndexNumber;
    private PaletteRecord palette;
    private ArrayList xfRecords = new ArrayList(10);

    static {
        Class cls;
        if (class$jxl$biff$FormattingRecords == null) {
            cls = class$("jxl.biff.FormattingRecords");
            class$jxl$biff$FormattingRecords = cls;
        } else {
            cls = class$jxl$biff$FormattingRecords;
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

    public FormattingRecords(Fonts f) {
        this.fonts = f;
        this.nextCustomIndexNumber = customFormatStartIndex;
    }

    public final void addStyle(XFRecord xf) throws NumFormatRecordsException {
        if (!xf.isInitialized()) {
            xf.initialize(this.xfRecords.size(), this, this.fonts);
            this.xfRecords.add(xf);
        } else if (xf.getXFIndex() >= this.xfRecords.size()) {
            this.xfRecords.add(xf);
        }
    }

    public final void addFormat(DisplayFormat fr) throws NumFormatRecordsException {
        if (fr.isInitialized() && fr.getFormatIndex() >= maxFormatRecordsIndex) {
            logger.warn("Format index exceeds Excel maximum - assigning custom number");
            fr.initialize(this.nextCustomIndexNumber);
            this.nextCustomIndexNumber++;
        }
        if (!fr.isInitialized()) {
            fr.initialize(this.nextCustomIndexNumber);
            this.nextCustomIndexNumber++;
        }
        if (this.nextCustomIndexNumber > maxFormatRecordsIndex) {
            this.nextCustomIndexNumber = maxFormatRecordsIndex;
            throw new NumFormatRecordsException();
        }
        if (fr.getFormatIndex() >= this.nextCustomIndexNumber) {
            this.nextCustomIndexNumber = fr.getFormatIndex() + 1;
        }
        if (!fr.isBuiltIn()) {
            this.formatsList.add(fr);
            this.formats.put(new Integer(fr.getFormatIndex()), fr);
        }
    }

    public final boolean isDate(int pos) {
        XFRecord xfr = (XFRecord) this.xfRecords.get(pos);
        if (xfr.isDate()) {
            return true;
        }
        FormatRecord fr = (FormatRecord) this.formats.get(new Integer(xfr.getFormatRecord()));
        if (fr == null) {
            return false;
        }
        return fr.isDate();
    }

    public final DateFormat getDateFormat(int pos) {
        XFRecord xfr = (XFRecord) this.xfRecords.get(pos);
        if (xfr.isDate()) {
            return xfr.getDateFormat();
        }
        FormatRecord fr = (FormatRecord) this.formats.get(new Integer(xfr.getFormatRecord()));
        if (fr == null || !fr.isDate()) {
            return null;
        }
        return fr.getDateFormat();
    }

    public final NumberFormat getNumberFormat(int pos) {
        XFRecord xfr = (XFRecord) this.xfRecords.get(pos);
        if (xfr.isNumber()) {
            return xfr.getNumberFormat();
        }
        FormatRecord fr = (FormatRecord) this.formats.get(new Integer(xfr.getFormatRecord()));
        if (fr == null || !fr.isNumber()) {
            return null;
        }
        return fr.getNumberFormat();
    }

    /* access modifiers changed from: package-private */
    public FormatRecord getFormatRecord(int index) {
        return (FormatRecord) this.formats.get(new Integer(index));
    }

    public void write(File outputFile) throws IOException {
        Iterator i = this.formatsList.iterator();
        while (i.hasNext()) {
            outputFile.write((FormatRecord) i.next());
        }
        Iterator i2 = this.xfRecords.iterator();
        while (i2.hasNext()) {
            outputFile.write((XFRecord) i2.next());
        }
        outputFile.write(new BuiltInStyle(16, 3));
        outputFile.write(new BuiltInStyle(17, 6));
        outputFile.write(new BuiltInStyle(18, 4));
        outputFile.write(new BuiltInStyle(19, 7));
        outputFile.write(new BuiltInStyle(0, 0));
        outputFile.write(new BuiltInStyle(20, 5));
    }

    /* access modifiers changed from: protected */
    public final Fonts getFonts() {
        return this.fonts;
    }

    public final XFRecord getXFRecord(int index) {
        return (XFRecord) this.xfRecords.get(index);
    }

    /* access modifiers changed from: protected */
    public final int getNumberOfFormatRecords() {
        return this.formatsList.size();
    }

    public IndexMapping rationalizeFonts() {
        return this.fonts.rationalize();
    }

    public IndexMapping rationalize(IndexMapping fontMapping, IndexMapping formatMapping) {
        Iterator it = this.xfRecords.iterator();
        while (it.hasNext()) {
            XFRecord xfr = (XFRecord) it.next();
            if (xfr.getFormatRecord() >= customFormatStartIndex) {
                xfr.setFormatIndex(formatMapping.getNewIndex(xfr.getFormatRecord()));
            }
            xfr.setFontIndex(fontMapping.getNewIndex(xfr.getFontIndex()));
        }
        ArrayList newrecords = new ArrayList(21);
        IndexMapping mapping = new IndexMapping(this.xfRecords.size());
        int numremoved = 0;
        for (int i = 0; i < 21; i++) {
            newrecords.add(this.xfRecords.get(i));
            mapping.setMapping(i, i);
        }
        for (int i2 = 21; i2 < this.xfRecords.size(); i2++) {
            XFRecord xf = (XFRecord) this.xfRecords.get(i2);
            boolean duplicate = false;
            Iterator it2 = newrecords.iterator();
            while (it2.hasNext() && !duplicate) {
                XFRecord xf2 = (XFRecord) it2.next();
                if (xf2.equals(xf)) {
                    duplicate = true;
                    mapping.setMapping(i2, mapping.getNewIndex(xf2.getXFIndex()));
                    numremoved++;
                }
            }
            if (!duplicate) {
                newrecords.add(xf);
                mapping.setMapping(i2, i2 - numremoved);
            }
        }
        Iterator i3 = this.xfRecords.iterator();
        while (i3.hasNext()) {
            ((XFRecord) i3.next()).rationalize(mapping);
        }
        this.xfRecords = newrecords;
        return mapping;
    }

    public IndexMapping rationalizeDisplayFormats() {
        ArrayList newformats = new ArrayList();
        int numremoved = 0;
        IndexMapping mapping = new IndexMapping(this.nextCustomIndexNumber);
        Iterator i = this.formatsList.iterator();
        while (i.hasNext()) {
            DisplayFormat df = (DisplayFormat) i.next();
            Assert.verify(!df.isBuiltIn());
            Iterator i2 = newformats.iterator();
            boolean duplicate = false;
            while (i2.hasNext() && !duplicate) {
                DisplayFormat df2 = (DisplayFormat) i2.next();
                if (df2.equals(df)) {
                    duplicate = true;
                    mapping.setMapping(df.getFormatIndex(), mapping.getNewIndex(df2.getFormatIndex()));
                    numremoved++;
                }
            }
            if (!duplicate) {
                newformats.add(df);
                if (df.getFormatIndex() - numremoved > maxFormatRecordsIndex) {
                    logger.warn("Too many number formats - using default format.");
                }
                mapping.setMapping(df.getFormatIndex(), df.getFormatIndex() - numremoved);
            }
        }
        this.formatsList = newformats;
        Iterator i3 = this.formatsList.iterator();
        while (i3.hasNext()) {
            DisplayFormat df3 = (DisplayFormat) i3.next();
            df3.initialize(mapping.getNewIndex(df3.getFormatIndex()));
        }
        return mapping;
    }

    public PaletteRecord getPalette() {
        return this.palette;
    }

    public void setPalette(PaletteRecord pr) {
        this.palette = pr;
    }

    public void setColourRGB(Colour c, int r, int g, int b) {
        if (this.palette == null) {
            this.palette = new PaletteRecord();
        }
        this.palette.setColourRGB(c, r, g, b);
    }

    public RGB getColourRGB(Colour c) {
        if (this.palette == null) {
            return c.getDefaultRGB();
        }
        return this.palette.getColourRGB(c);
    }
}

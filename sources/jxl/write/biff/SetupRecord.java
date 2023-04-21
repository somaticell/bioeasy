package jxl.write.biff;

import common.Logger;
import jxl.SheetSettings;
import jxl.biff.DoubleHelper;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;

class SetupRecord extends WritableRecordData {
    static Class class$jxl$write$biff$SetupRecord;
    private int copies;
    private byte[] data;
    private int fitHeight;
    private int fitWidth;
    private double footerMargin;
    private double headerMargin;
    private int horizontalPrintResolution;
    Logger logger;
    private PageOrientation orientation;
    private int pageStart;
    private int paperSize;
    private int scaleFactor;
    private int verticalPrintResolution;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public SetupRecord() {
        super(Type.SETUP);
        Class cls;
        if (class$jxl$write$biff$SetupRecord == null) {
            cls = class$("jxl.write.biff.SetupRecord");
            class$jxl$write$biff$SetupRecord = cls;
        } else {
            cls = class$jxl$write$biff$SetupRecord;
        }
        this.logger = Logger.getLogger(cls);
        this.orientation = PageOrientation.PORTRAIT;
        this.headerMargin = 0.5d;
        this.footerMargin = 0.5d;
        this.paperSize = PaperSize.A4.getValue();
        this.horizontalPrintResolution = 300;
        this.verticalPrintResolution = 300;
        this.copies = 1;
    }

    public SetupRecord(SheetSettings s) {
        super(Type.SETUP);
        Class cls;
        if (class$jxl$write$biff$SetupRecord == null) {
            cls = class$("jxl.write.biff.SetupRecord");
            class$jxl$write$biff$SetupRecord = cls;
        } else {
            cls = class$jxl$write$biff$SetupRecord;
        }
        this.logger = Logger.getLogger(cls);
        this.orientation = s.getOrientation();
        this.headerMargin = s.getHeaderMargin();
        this.footerMargin = s.getFooterMargin();
        this.paperSize = s.getPaperSize().getValue();
        this.horizontalPrintResolution = s.getHorizontalPrintResolution();
        this.verticalPrintResolution = s.getVerticalPrintResolution();
        this.fitWidth = s.getFitWidth();
        this.fitHeight = s.getFitHeight();
        this.pageStart = s.getPageStart();
        this.scaleFactor = s.getScaleFactor();
        this.copies = s.getCopies();
    }

    public SetupRecord(jxl.read.biff.SetupRecord sr) {
        super(Type.SETUP);
        Class cls;
        if (class$jxl$write$biff$SetupRecord == null) {
            cls = class$("jxl.write.biff.SetupRecord");
            class$jxl$write$biff$SetupRecord = cls;
        } else {
            cls = class$jxl$write$biff$SetupRecord;
        }
        this.logger = Logger.getLogger(cls);
        this.orientation = sr.isPortrait() ? PageOrientation.PORTRAIT : PageOrientation.LANDSCAPE;
        this.paperSize = sr.getPaperSize();
        this.headerMargin = sr.getHeaderMargin();
        this.footerMargin = sr.getFooterMargin();
        this.scaleFactor = sr.getScaleFactor();
        this.pageStart = sr.getPageStart();
        this.fitWidth = sr.getFitWidth();
        this.fitHeight = sr.getFitHeight();
        this.horizontalPrintResolution = sr.getHorizontalPrintResolution();
        this.verticalPrintResolution = sr.getVerticalPrintResolution();
        this.copies = sr.getCopies();
    }

    public void setOrientation(PageOrientation o) {
        this.orientation = o;
    }

    public void setMargins(double hm, double fm) {
        this.headerMargin = hm;
        this.footerMargin = fm;
    }

    public void setPaperSize(PaperSize ps) {
        this.paperSize = ps.getValue();
    }

    public byte[] getData() {
        this.data = new byte[34];
        IntegerHelper.getTwoBytes(this.paperSize, this.data, 0);
        IntegerHelper.getTwoBytes(this.scaleFactor, this.data, 2);
        IntegerHelper.getTwoBytes(this.pageStart, this.data, 4);
        IntegerHelper.getTwoBytes(this.fitWidth, this.data, 6);
        IntegerHelper.getTwoBytes(this.fitHeight, this.data, 8);
        if (this.orientation == PageOrientation.PORTRAIT) {
            IntegerHelper.getTwoBytes(2, this.data, 10);
        }
        IntegerHelper.getTwoBytes(this.horizontalPrintResolution, this.data, 12);
        IntegerHelper.getTwoBytes(this.verticalPrintResolution, this.data, 14);
        DoubleHelper.getIEEEBytes(this.headerMargin, this.data, 16);
        DoubleHelper.getIEEEBytes(this.footerMargin, this.data, 24);
        IntegerHelper.getTwoBytes(this.copies, this.data, 32);
        return this.data;
    }
}

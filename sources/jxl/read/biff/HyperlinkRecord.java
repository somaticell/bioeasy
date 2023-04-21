package jxl.read.biff;

import common.Logger;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import jxl.CellReferenceHelper;
import jxl.Hyperlink;
import jxl.Range;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.SheetRangeImpl;
import jxl.biff.StringHelper;

public class HyperlinkRecord extends RecordData implements Hyperlink {
    static Class class$jxl$read$biff$HyperlinkRecord;
    private static final LinkType fileLink = new LinkType((AnonymousClass1) null);
    private static Logger logger;
    private static final LinkType unknown = new LinkType((AnonymousClass1) null);
    private static final LinkType urlLink = new LinkType((AnonymousClass1) null);
    private static final LinkType workbookLink = new LinkType((AnonymousClass1) null);
    private File file;
    private int firstColumn;
    private int firstRow;
    private int lastColumn;
    private int lastRow;
    private LinkType linkType = unknown;
    private String location;
    private SheetRangeImpl range;
    private URL url;

    /* renamed from: jxl.read.biff.HyperlinkRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$read$biff$HyperlinkRecord == null) {
            cls = class$("jxl.read.biff.HyperlinkRecord");
            class$jxl$read$biff$HyperlinkRecord = cls;
        } else {
            cls = class$jxl$read$biff$HyperlinkRecord;
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

    private static class LinkType {
        private LinkType() {
        }

        LinkType(AnonymousClass1 x0) {
            this();
        }
    }

    HyperlinkRecord(Record t, Sheet s, WorkbookSettings ws) {
        super(t);
        byte[] data = getRecord().getData();
        this.firstRow = IntegerHelper.getInt(data[0], data[1]);
        this.lastRow = IntegerHelper.getInt(data[2], data[3]);
        this.firstColumn = IntegerHelper.getInt(data[4], data[5]);
        this.lastColumn = IntegerHelper.getInt(data[6], data[7]);
        this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
        int options = IntegerHelper.getInt(data[28], data[29], data[30], data[31]);
        int startpos = 32 + ((options & 20) != 0 ? (IntegerHelper.getInt(data[32], data[33], data[34], data[35]) * 2) + 4 : 0);
        int startpos2 = startpos + ((options & 128) != 0 ? (IntegerHelper.getInt(data[startpos], data[startpos + 1], data[startpos + 2], data[startpos + 3]) * 2) + 4 : 0);
        if ((options & 3) == 3) {
            this.linkType = urlLink;
            if (data[startpos2] == 3) {
                this.linkType = fileLink;
            }
        } else if ((options & 1) != 0) {
            this.linkType = fileLink;
            if (data[startpos2] == -32) {
                this.linkType = urlLink;
            }
        } else if ((options & 8) != 0) {
            this.linkType = workbookLink;
        }
        if (this.linkType == urlLink) {
            String urlString = null;
            int startpos3 = startpos2 + 16;
            try {
                urlString = StringHelper.getUnicodeString(data, (IntegerHelper.getInt(data[startpos3], data[startpos3 + 1], data[startpos3 + 2], data[startpos3 + 3]) / 2) - 1, startpos3 + 4);
                this.url = new URL(urlString);
            } catch (MalformedURLException e) {
                logger.warn(new StringBuffer().append("URL ").append(urlString).append(" is malformed.  Trying a file").toString());
                try {
                    this.linkType = fileLink;
                    this.file = new File(urlString);
                } catch (Exception e2) {
                    logger.warn("Cannot set to file.  Setting a default URL");
                    try {
                        this.linkType = urlLink;
                        this.url = new URL("http://www.andykhan.com/jexcelapi/index.html");
                    } catch (MalformedURLException e3) {
                    }
                }
            } catch (Throwable e4) {
                StringBuffer sb1 = new StringBuffer();
                StringBuffer sb2 = new StringBuffer();
                CellReferenceHelper.getCellReference(this.firstColumn, this.firstRow, sb1);
                CellReferenceHelper.getCellReference(this.lastColumn, this.lastRow, sb2);
                sb1.insert(0, "Exception when parsing URL ");
                sb1.append('\"').append(sb2.toString()).append("\".  Using default.");
                logger.warn(sb1, e4);
                try {
                    this.url = new URL("http://www.andykhan.com/jexcelapi/index.html");
                } catch (MalformedURLException e5) {
                }
            }
        } else if (this.linkType == fileLink) {
            int startpos4 = startpos2 + 16;
            try {
                int upLevelCount = IntegerHelper.getInt(data[startpos4], data[startpos4 + 1]);
                String fileName = StringHelper.getString(data, IntegerHelper.getInt(data[startpos4 + 2], data[startpos4 + 3], data[startpos4 + 4], data[startpos4 + 5]) - 1, startpos4 + 6, ws);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < upLevelCount; i++) {
                    sb.append("..\\");
                }
                sb.append(fileName);
                this.file = new File(sb.toString());
            } catch (Throwable e6) {
                e6.printStackTrace();
                logger.warn(new StringBuffer().append("Exception when parsing file ").append(e6.getClass().getName()).append(".").toString());
                this.file = new File(".");
            }
        } else if (this.linkType == workbookLink) {
            this.location = StringHelper.getUnicodeString(data, IntegerHelper.getInt(data[32], data[33], data[34], data[35]) - 1, 36);
        } else {
            logger.warn("Cannot determine link type");
        }
    }

    public boolean isFile() {
        return this.linkType == fileLink;
    }

    public boolean isURL() {
        return this.linkType == urlLink;
    }

    public boolean isLocation() {
        return this.linkType == workbookLink;
    }

    public int getRow() {
        return this.firstRow;
    }

    public int getColumn() {
        return this.firstColumn;
    }

    public int getLastRow() {
        return this.lastRow;
    }

    public int getLastColumn() {
        return this.lastColumn;
    }

    public URL getURL() {
        return this.url;
    }

    public File getFile() {
        return this.file;
    }

    public Record getRecord() {
        return super.getRecord();
    }

    public Range getRange() {
        return this.range;
    }

    public String getLocation() {
        return this.location;
    }
}

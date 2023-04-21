package jxl.read.biff;

import common.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import jxl.WorkbookSettings;
import jxl.biff.BaseCompoundFile;
import jxl.biff.IntegerHelper;
import jxl.biff.Type;

public class File {
    static Class class$jxl$read$biff$File;
    private static Logger logger;
    private int arrayGrowSize;
    private CompoundFile compoundFile;
    private byte[] data;
    private int filePos;
    private int initialFileSize;
    private int oldPos;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$read$biff$File == null) {
            cls = class$("jxl.read.biff.File");
            class$jxl$read$biff$File = cls;
        } else {
            cls = class$jxl$read$biff$File;
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

    public File(InputStream is, WorkbookSettings ws) throws IOException, BiffException {
        this.workbookSettings = ws;
        this.initialFileSize = this.workbookSettings.getInitialFileSize();
        this.arrayGrowSize = this.workbookSettings.getArrayGrowSize();
        byte[] d = new byte[this.initialFileSize];
        int bytesRead = is.read(d);
        int pos = bytesRead;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        while (bytesRead != -1) {
            if (pos >= d.length) {
                byte[] newArray = new byte[(d.length + this.arrayGrowSize)];
                System.arraycopy(d, 0, newArray, 0, d.length);
                d = newArray;
            }
            bytesRead = is.read(d, pos, d.length - pos);
            pos += bytesRead;
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedIOException();
            }
        }
        if (pos + 1 == 0) {
            throw new BiffException(BiffException.excelFileNotFound);
        }
        CompoundFile cf = new CompoundFile(d, ws);
        try {
            this.data = cf.getStream("workbook");
        } catch (BiffException e) {
            this.data = cf.getStream("book");
        }
        if (!this.workbookSettings.getPropertySetsDisabled() && cf.getPropertySetNames().length > BaseCompoundFile.STANDARD_PROPERTY_SETS.length) {
            this.compoundFile = cf;
        }
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
    }

    public File(byte[] d) {
        this.data = d;
    }

    /* access modifiers changed from: package-private */
    public Record next() {
        return new Record(this.data, this.filePos, this);
    }

    /* access modifiers changed from: package-private */
    public Record peek() {
        int tempPos = this.filePos;
        Record r = new Record(this.data, this.filePos, this);
        this.filePos = tempPos;
        return r;
    }

    public void skip(int bytes) {
        this.filePos += bytes;
    }

    public byte[] read(int pos, int length) {
        byte[] ret = new byte[length];
        try {
            System.arraycopy(this.data, pos, ret, 0, length);
            return ret;
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error(new StringBuffer().append("Array index out of bounds at position ").append(pos).append(" record length ").append(length).toString());
            throw e;
        }
    }

    public int getPos() {
        return this.filePos;
    }

    public void setPos(int p) {
        this.oldPos = this.filePos;
        this.filePos = p;
    }

    public void restorePos() {
        this.filePos = this.oldPos;
    }

    private void moveToFirstBof() {
        boolean bofFound = false;
        while (!bofFound) {
            if (IntegerHelper.getInt(this.data[this.filePos], this.data[this.filePos + 1]) == Type.BOF.value) {
                bofFound = true;
            } else {
                skip(128);
            }
        }
    }

    public void close() {
    }

    public void clear() {
        this.data = null;
    }

    public boolean hasNext() {
        return this.filePos < this.data.length + -4;
    }

    /* access modifiers changed from: package-private */
    public CompoundFile getCompoundFile() {
        return this.compoundFile;
    }
}

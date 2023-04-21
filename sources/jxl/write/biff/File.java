package jxl.write.biff;

import common.Logger;
import java.io.IOException;
import java.io.OutputStream;
import jxl.WorkbookSettings;
import jxl.biff.ByteData;
import jxl.read.biff.CompoundFile;

public final class File {
    static Class class$jxl$write$biff$File;
    private static Logger logger;
    private int arrayGrowSize;
    private byte[] data = new byte[this.initialFileSize];
    private int initialFileSize;
    private OutputStream outputStream;
    private int pos = 0;
    CompoundFile readCompoundFile;
    private WorkbookSettings workbookSettings;

    static {
        Class cls;
        if (class$jxl$write$biff$File == null) {
            cls = class$("jxl.write.biff.File");
            class$jxl$write$biff$File = cls;
        } else {
            cls = class$jxl$write$biff$File;
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

    File(OutputStream os, WorkbookSettings ws, CompoundFile rcf) {
        this.initialFileSize = ws.getInitialFileSize();
        this.arrayGrowSize = ws.getArrayGrowSize();
        this.outputStream = os;
        this.workbookSettings = ws;
        this.readCompoundFile = rcf;
    }

    /* access modifiers changed from: package-private */
    public void close(boolean cs) throws IOException, JxlWriteException {
        new CompoundFile(this.data, this.pos, this.outputStream, this.readCompoundFile).write();
        this.outputStream.flush();
        if (cs) {
            this.outputStream.close();
        }
        this.data = null;
        if (!this.workbookSettings.getGCDisabled()) {
            System.gc();
        }
    }

    public void write(ByteData record) throws IOException {
        try {
            byte[] bytes = record.getBytes();
            while (this.pos + bytes.length > this.data.length) {
                byte[] newdata = new byte[(this.data.length + this.arrayGrowSize)];
                System.arraycopy(this.data, 0, newdata, 0, this.pos);
                this.data = newdata;
            }
            System.arraycopy(bytes, 0, this.data, this.pos, bytes.length);
            this.pos += bytes.length;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    /* access modifiers changed from: package-private */
    public int getPos() {
        return this.pos;
    }

    /* access modifiers changed from: package-private */
    public void setData(byte[] newdata, int pos2) {
        System.arraycopy(newdata, 0, this.data, pos2, newdata.length);
    }

    public void setOutputFile(OutputStream os) {
        if (this.data != null) {
            logger.warn("Rewriting a workbook with non-empty data");
        }
        this.outputStream = os;
        this.data = new byte[this.initialFileSize];
        this.pos = 0;
    }
}

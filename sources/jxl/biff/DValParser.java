package jxl.biff;

import common.Logger;

public class DValParser {
    private static int PROMPT_BOX_AT_CELL_MASK = 2;
    private static int PROMPT_BOX_VISIBLE_MASK = 1;
    private static int VALIDITY_DATA_CACHED_MASK = 4;
    static Class class$jxl$biff$DValParser;
    private static Logger logger;
    private int numDVRecords;
    private boolean promptBoxAtCell;
    private boolean promptBoxVisible;
    private boolean validityDataCached;

    static {
        Class cls;
        if (class$jxl$biff$DValParser == null) {
            cls = class$("jxl.biff.DValParser");
            class$jxl$biff$DValParser = cls;
        } else {
            cls = class$jxl$biff$DValParser;
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

    public DValParser(byte[] data) {
        boolean z;
        boolean z2 = true;
        int options = IntegerHelper.getInt(data[0], data[1]);
        this.promptBoxVisible = (PROMPT_BOX_VISIBLE_MASK & options) != 0;
        if ((PROMPT_BOX_AT_CELL_MASK & options) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.promptBoxAtCell = z;
        this.validityDataCached = (VALIDITY_DATA_CACHED_MASK & options) == 0 ? false : z2;
        this.numDVRecords = IntegerHelper.getInt(data[14], data[15], data[16], data[17]);
    }

    public byte[] getData() {
        byte[] data = new byte[18];
        int options = 0;
        if (this.promptBoxVisible) {
            options = 0 | PROMPT_BOX_VISIBLE_MASK;
        }
        if (this.promptBoxAtCell) {
            options |= PROMPT_BOX_AT_CELL_MASK;
        }
        if (this.validityDataCached) {
            int options2 = options | VALIDITY_DATA_CACHED_MASK;
        }
        IntegerHelper.getFourBytes(-1, data, 10);
        IntegerHelper.getFourBytes(this.numDVRecords, data, 14);
        return data;
    }

    public void dvRemoved() {
        this.numDVRecords--;
    }

    public int getNumberOfDVRecords() {
        return this.numDVRecords;
    }
}

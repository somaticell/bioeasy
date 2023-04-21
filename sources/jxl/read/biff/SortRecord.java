package jxl.read.biff;

import jxl.biff.RecordData;
import jxl.biff.StringHelper;
import jxl.biff.Type;

public class SortRecord extends RecordData {
    private String col1Name;
    private int col1Size;
    private String col2Name;
    private int col2Size;
    private String col3Name;
    private int col3Size;
    private byte optionFlags;
    private boolean sortCaseSensitive = false;
    private boolean sortColumns = false;
    private boolean sortKey1Desc = false;
    private boolean sortKey2Desc = false;
    private boolean sortKey3Desc = false;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SortRecord(Record r) {
        super(Type.SORT);
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int curPos;
        boolean z5 = true;
        byte[] data = r.getData();
        this.optionFlags = data[0];
        if ((this.optionFlags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.sortColumns = z;
        if ((this.optionFlags & 2) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.sortKey1Desc = z2;
        if ((this.optionFlags & 4) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.sortKey2Desc = z3;
        if ((this.optionFlags & 8) != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.sortKey3Desc = z4;
        this.sortCaseSensitive = (this.optionFlags & 16) == 0 ? false : z5;
        this.col1Size = data[2];
        this.col2Size = data[3];
        this.col3Size = data[4];
        int curPos2 = 5 + 1;
        if (data[5] == 0) {
            this.col1Name = new String(data, curPos2, this.col1Size);
            curPos = this.col1Size + 6;
        } else {
            this.col1Name = StringHelper.getUnicodeString(data, this.col1Size, curPos2);
            curPos = (this.col1Size * 2) + 6;
        }
        if (this.col2Size > 0) {
            int curPos3 = curPos + 1;
            if (data[curPos] == 0) {
                this.col2Name = new String(data, curPos3, this.col2Size);
                curPos = curPos3 + this.col2Size;
            } else {
                this.col2Name = StringHelper.getUnicodeString(data, this.col2Size, curPos3);
                curPos = curPos3 + (this.col2Size * 2);
            }
        } else {
            this.col2Name = "";
        }
        if (this.col3Size > 0) {
            int curPos4 = curPos + 1;
            if (data[curPos] == 0) {
                this.col3Name = new String(data, curPos4, this.col3Size);
                int i = curPos4 + this.col3Size;
                return;
            }
            this.col3Name = StringHelper.getUnicodeString(data, this.col3Size, curPos4);
            int i2 = curPos4 + (this.col3Size * 2);
            return;
        }
        this.col3Name = "";
    }

    public String getSortCol1Name() {
        return this.col1Name;
    }

    public String getSortCol2Name() {
        return this.col2Name;
    }

    public String getSortCol3Name() {
        return this.col3Name;
    }

    public boolean getSortColumns() {
        return this.sortColumns;
    }

    public boolean getSortKey1Desc() {
        return this.sortKey1Desc;
    }

    public boolean getSortKey2Desc() {
        return this.sortKey2Desc;
    }

    public boolean getSortKey3Desc() {
        return this.sortKey3Desc;
    }

    public boolean getSortCaseSensitive() {
        return this.sortCaseSensitive;
    }
}

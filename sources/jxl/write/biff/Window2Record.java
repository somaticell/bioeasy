package jxl.write.biff;

import jxl.SheetSettings;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class Window2Record extends WritableRecordData {
    private byte[] data;

    public Window2Record(SheetSettings settings) {
        super(Type.WINDOW2);
        int selected;
        if (settings.isSelected()) {
            selected = 6;
        } else {
            selected = 0;
        }
        int options = 0 | 0;
        int options2 = (settings.getShowGridLines() ? options | 2 : options) | 4 | 0;
        int options3 = (settings.getDisplayZeroValues() ? options2 | 16 : options2) | 32 | 128;
        if (!(settings.getHorizontalFreeze() == 0 && settings.getVerticalFreeze() == 0)) {
            options3 |= 8;
            selected |= 1;
        }
        this.data = new byte[]{(byte) options3, (byte) selected, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public byte[] getData() {
        return this.data;
    }
}

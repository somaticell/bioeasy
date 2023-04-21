package jxl.write.biff;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class Window1Record extends WritableRecordData {
    private byte[] data = {104, 1, 14, 1, 92, 58, -66, BLEServiceApi.POWER_CMD, 56, 0, 0, 0, 0, 0, 1, 0, 88, 2};

    public Window1Record() {
        super(Type.WINDOW1);
    }

    public byte[] getData() {
        return this.data;
    }
}

package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class PasswordRecord extends WritableRecordData {
    private byte[] data;
    private String password;

    public PasswordRecord(String pw) {
        super(Type.PASSWORD);
        this.password = pw;
        if (pw == null) {
            this.data = new byte[2];
            IntegerHelper.getTwoBytes(0, this.data, 0);
            return;
        }
        byte[] passwordBytes = pw.getBytes();
        int passwordHash = 0;
        for (int a = 0; a < passwordBytes.length; a++) {
            passwordHash ^= rotLeft15Bit(passwordBytes[a], a + 1);
        }
        this.data = new byte[2];
        IntegerHelper.getTwoBytes((passwordHash ^ passwordBytes.length) ^ 52811, this.data, 0);
    }

    public PasswordRecord(int ph) {
        super(Type.PASSWORD);
        this.data = new byte[2];
        IntegerHelper.getTwoBytes(ph, this.data, 0);
    }

    public byte[] getData() {
        return this.data;
    }

    private int rotLeft15Bit(int val, int rotate) {
        int val2 = val & 32767;
        while (rotate > 0) {
            if ((val2 & 16384) != 0) {
                val2 = ((val2 << 1) & 32767) + 1;
            } else {
                val2 = (val2 << 1) & 32767;
            }
            rotate--;
        }
        return val2;
    }
}

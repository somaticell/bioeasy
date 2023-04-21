package jxl.read.biff;

import jxl.WorkbookSettings;
import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;
import jxl.biff.StringHelper;

public class FooterRecord extends RecordData {
    public static Biff7 biff7 = new Biff7((AnonymousClass1) null);
    private String footer;

    /* renamed from: jxl.read.biff.FooterRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    private static class Biff7 {
        private Biff7() {
        }

        Biff7(AnonymousClass1 x0) {
            this();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FooterRecord(Record t, WorkbookSettings ws) {
        super(t);
        boolean unicode = true;
        byte[] data = getRecord().getData();
        if (data.length != 0) {
            int chars = IntegerHelper.getInt(data[0], data[1]);
            if (data[2] != 1 ? false : unicode) {
                this.footer = StringHelper.getUnicodeString(data, chars, 3);
            } else {
                this.footer = StringHelper.getString(data, chars, 3, ws);
            }
        }
    }

    FooterRecord(Record t, WorkbookSettings ws, Biff7 dummy) {
        super(t);
        byte[] data = getRecord().getData();
        if (data.length != 0) {
            this.footer = StringHelper.getString(data, data[0], 1, ws);
        }
    }

    /* access modifiers changed from: package-private */
    public String getFooter() {
        return this.footer;
    }
}

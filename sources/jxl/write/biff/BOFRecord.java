package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class BOFRecord extends WritableRecordData {
    public static final SheetBOF sheet = new SheetBOF((AnonymousClass1) null);
    public static final WorkbookGlobalsBOF workbookGlobals = new WorkbookGlobalsBOF((AnonymousClass1) null);
    private byte[] data = {0, 6, 16, 0, -14, 21, -52, 7, 0, 0, 0, 0, 6, 0, 0, 0};

    /* renamed from: jxl.write.biff.BOFRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    private static class WorkbookGlobalsBOF {
        private WorkbookGlobalsBOF() {
        }

        WorkbookGlobalsBOF(AnonymousClass1 x0) {
            this();
        }
    }

    private static class SheetBOF {
        private SheetBOF() {
        }

        SheetBOF(AnonymousClass1 x0) {
            this();
        }
    }

    public BOFRecord(WorkbookGlobalsBOF dummy) {
        super(Type.BOF);
    }

    public BOFRecord(SheetBOF dummy) {
        super(Type.BOF);
    }

    public byte[] getData() {
        return this.data;
    }
}

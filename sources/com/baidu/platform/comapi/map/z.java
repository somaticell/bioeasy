package com.baidu.platform.comapi.map;

import android.os.Message;

class z {
    private static final String a = z.class.getSimpleName();
    private y b;

    z() {
    }

    /* access modifiers changed from: package-private */
    public void a(Message message) {
        if (message.what == 65289) {
            switch (message.arg1) {
                case -1:
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 12:
                case 101:
                case 102:
                    if (this.b != null) {
                        this.b.a(message.arg1, message.arg2);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(y yVar) {
        this.b = yVar;
    }

    /* access modifiers changed from: package-private */
    public void b(y yVar) {
        this.b = null;
    }
}

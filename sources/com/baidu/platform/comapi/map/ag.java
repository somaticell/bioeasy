package com.baidu.platform.comapi.map;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

class ag extends Handler {
    final /* synthetic */ af a;

    ag(af afVar) {
        this.a = afVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.a.h != null && this.a.h.g != null && ((Long) message.obj).longValue() == this.a.h.h) {
            if (message.what == 4000) {
                for (l next : this.a.h.f) {
                    Bitmap bitmap = null;
                    if (message.arg2 == 1) {
                        int[] iArr = new int[(af.a * af.b)];
                        int[] iArr2 = new int[(af.a * af.b)];
                        if (this.a.h.g != null) {
                            int[] a2 = this.a.h.g.a(iArr, af.a, af.b);
                            for (int i = 0; i < af.b; i++) {
                                for (int i2 = 0; i2 < af.a; i2++) {
                                    int i3 = a2[(af.a * i) + i2];
                                    iArr2[(((af.b - i) - 1) * af.a) + i2] = (i3 & -16711936) | ((i3 << 16) & 16711680) | ((i3 >> 16) & 255);
                                }
                            }
                            bitmap = Bitmap.createBitmap(iArr2, af.a, af.b, Bitmap.Config.ARGB_8888);
                        } else {
                            return;
                        }
                    }
                    next.a(bitmap);
                }
            } else if (message.what == 39) {
                if (this.a.h != null) {
                    if (message.arg1 == 100) {
                        this.a.h.B();
                    } else if (message.arg1 == 200) {
                        this.a.h.L();
                    } else if (message.arg1 == 1) {
                        if (this.a.g != null) {
                            this.a.g.a();
                        }
                    } else if (message.arg1 == 0) {
                        if (this.a.g != null) {
                            this.a.g.a();
                        }
                    } else if (message.arg1 == 2) {
                        for (l c : this.a.h.f) {
                            c.c();
                        }
                    }
                    if (!this.a.h.i && af.b > 0 && af.a > 0 && this.a.h.b(0, 0) != null) {
                        this.a.h.i = true;
                        for (l b : this.a.h.f) {
                            b.b();
                        }
                    }
                    for (l a3 : this.a.h.f) {
                        a3.a();
                    }
                    if (this.a.h.q()) {
                        for (l next2 : this.a.h.f) {
                            if (this.a.h.E().a >= 18.0f) {
                                next2.a(true);
                            } else {
                                next2.a(false);
                            }
                        }
                    }
                }
            } else if (message.what == 41) {
                if (this.a.h == null) {
                    return;
                }
                if (this.a.h.l || this.a.h.m) {
                    for (l next3 : this.a.h.f) {
                        next3.b(this.a.h.E());
                        if (this.a.h.q()) {
                            if (this.a.h.E().a >= 18.0f) {
                                next3.a(true);
                            } else {
                                next3.a(false);
                            }
                        }
                    }
                }
            } else if (message.what == 999) {
                for (l e : this.a.h.f) {
                    e.e();
                }
            } else if (message.what == 50) {
                for (l next4 : this.a.h.f) {
                    if (message.arg1 == 0) {
                        next4.a(false);
                    } else if (message.arg1 == 1) {
                        if (this.a.h.E().a >= 18.0f) {
                            next4.a(true);
                        } else {
                            next4.a(false);
                        }
                    }
                }
            }
        }
    }
}

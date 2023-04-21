package com.baidu.platform.comapi.map;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

class k extends Handler {
    final /* synthetic */ j a;

    k(j jVar) {
        this.a = jVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.a.g == null || ((Long) message.obj).longValue() != this.a.g.h) {
            return;
        }
        if (message.what == 4000) {
            for (l next : this.a.g.f) {
                Bitmap bitmap = null;
                if (message.arg2 == 1) {
                    int[] iArr = new int[(this.a.d * this.a.e)];
                    int[] iArr2 = new int[(this.a.d * this.a.e)];
                    if (this.a.g.g != null) {
                        int[] a2 = this.a.g.g.a(iArr, this.a.d, this.a.e);
                        for (int i = 0; i < this.a.e; i++) {
                            for (int i2 = 0; i2 < this.a.d; i2++) {
                                int i3 = a2[(this.a.d * i) + i2];
                                iArr2[(((this.a.e - i) - 1) * this.a.d) + i2] = (i3 & -16711936) | ((i3 << 16) & 16711680) | ((i3 >> 16) & 255);
                            }
                        }
                        bitmap = Bitmap.createBitmap(iArr2, this.a.d, this.a.e, Bitmap.Config.ARGB_8888);
                    } else {
                        return;
                    }
                }
                next.a(bitmap);
            }
        } else if (message.what == 39) {
            if (this.a.g != null) {
                if (message.arg1 == 100) {
                    this.a.g.B();
                } else if (message.arg1 == 200) {
                    this.a.g.L();
                } else if (message.arg1 == 1) {
                    this.a.requestRender();
                } else if (message.arg1 == 0) {
                    this.a.requestRender();
                    if (!this.a.g.b() && this.a.getRenderMode() != 0) {
                        this.a.setRenderMode(0);
                    }
                } else if (message.arg1 == 2) {
                    for (l c : this.a.g.f) {
                        c.c();
                    }
                }
                if (!this.a.g.i && this.a.e > 0 && this.a.d > 0 && this.a.g.b(0, 0) != null) {
                    this.a.g.i = true;
                    for (l b : this.a.g.f) {
                        b.b();
                    }
                }
                for (l a3 : this.a.g.f) {
                    a3.a();
                }
                if (this.a.g.q()) {
                    for (l next2 : this.a.g.f) {
                        if (this.a.g.E().a >= 18.0f) {
                            next2.a(true);
                        } else {
                            next2.a(false);
                        }
                    }
                }
            }
        } else if (message.what == 41) {
            if (this.a.g == null) {
                return;
            }
            if (this.a.g.l || this.a.g.m) {
                for (l next3 : this.a.g.f) {
                    next3.b(this.a.g.E());
                    if (this.a.g.q()) {
                        if (this.a.g.E().a >= 18.0f) {
                            next3.a(true);
                        } else {
                            next3.a(false);
                        }
                    }
                }
            }
        } else if (message.what == 999) {
            for (l e : this.a.g.f) {
                e.e();
            }
        } else if (message.what == 50) {
            for (l next4 : this.a.g.f) {
                if (message.arg1 == 0) {
                    next4.a(false);
                } else if (message.arg1 == 1) {
                    if (this.a.g.E().a >= 18.0f) {
                        next4.a(true);
                    } else {
                        next4.a(false);
                    }
                }
            }
        }
    }
}

package com.mob.commons.eventrecoder;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.cons.a;
import com.mob.commons.LockAction;
import com.mob.commons.d;
import com.mob.tools.MobLog;
import com.mob.tools.utils.FileLocker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public final class EventRecorder {
    /* access modifiers changed from: private */
    public static Context a;
    /* access modifiers changed from: private */
    public static File b;
    /* access modifiers changed from: private */
    public static FileOutputStream c;

    public static final synchronized void prepare(Context context) {
        synchronized (EventRecorder.class) {
            a = context.getApplicationContext();
            a((LockAction) new LockAction() {
                public boolean run(FileLocker fileLocker) {
                    try {
                        File unused = EventRecorder.b = new File(EventRecorder.a.getFilesDir(), ".mrecord");
                        if (!EventRecorder.b.exists()) {
                            EventRecorder.b.createNewFile();
                        }
                        FileOutputStream unused2 = EventRecorder.c = new FileOutputStream(EventRecorder.b, true);
                        return false;
                    } catch (Throwable th) {
                        MobLog.getInstance().w(th);
                        return false;
                    }
                }
            });
        }
    }

    public static final synchronized void addBegin(String str, String str2) {
        synchronized (EventRecorder.class) {
            a(str + " " + str2 + " 0\n");
        }
    }

    private static final void a(LockAction lockAction) {
        d.a(new File(a.getFilesDir(), "comm/locks/.mrlock"), lockAction);
    }

    public static final synchronized void addEnd(String str, String str2) {
        synchronized (EventRecorder.class) {
            a(str + " " + str2 + " 1\n");
        }
    }

    private static final void a(final String str) {
        a((LockAction) new LockAction() {
            public boolean run(FileLocker fileLocker) {
                try {
                    EventRecorder.c.write(str.getBytes("utf-8"));
                    EventRecorder.c.flush();
                    return false;
                } catch (Throwable th) {
                    MobLog.getInstance().w(th);
                    return false;
                }
            }
        });
    }

    public static final synchronized String checkRecord(final String str) {
        String str2;
        synchronized (EventRecorder.class) {
            final LinkedList linkedList = new LinkedList();
            a((LockAction) new LockAction() {
                public boolean run(FileLocker fileLocker) {
                    int indexOf;
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(EventRecorder.b), "utf-8"));
                        for (String readLine = bufferedReader.readLine(); !TextUtils.isEmpty(readLine); readLine = bufferedReader.readLine()) {
                            String[] split = readLine.split(" ");
                            if (str.equals(split[0])) {
                                if ("0".equals(split[2])) {
                                    linkedList.add(split[1]);
                                } else if (a.e.equals(split[2]) && (indexOf = linkedList.indexOf(split[1])) != -1) {
                                    linkedList.remove(indexOf);
                                }
                            }
                        }
                        bufferedReader.close();
                    } catch (Throwable th) {
                        MobLog.getInstance().d(th);
                    }
                    return false;
                }
            });
            if (linkedList.size() > 0) {
                str2 = (String) linkedList.get(0);
            } else {
                str2 = null;
            }
        }
        return str2;
    }

    public static final synchronized void clear() {
        synchronized (EventRecorder.class) {
            a((LockAction) new LockAction() {
                public boolean run(FileLocker fileLocker) {
                    try {
                        EventRecorder.c.close();
                        EventRecorder.b.delete();
                        File unused = EventRecorder.b = new File(EventRecorder.a.getFilesDir(), ".mrecord");
                        EventRecorder.b.createNewFile();
                        FileOutputStream unused2 = EventRecorder.c = new FileOutputStream(EventRecorder.b, true);
                        return false;
                    } catch (Throwable th) {
                        MobLog.getInstance().w(th);
                        return false;
                    }
                }
            });
        }
    }
}

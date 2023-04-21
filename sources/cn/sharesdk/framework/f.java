package cn.sharesdk.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.TextUtils;
import cn.sharesdk.framework.utils.d;
import com.facebook.common.util.UriUtil;
import com.mob.commons.eventrecoder.EventRecorder;
import com.mob.tools.SSDKHandlerThread;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: ShareSDKCoreThread */
public class f extends SSDKHandlerThread {
    private a a = a.IDLE;
    private Context b;
    private HashMap<String, HashMap<String, String>> c;
    private ArrayList<Platform> d;
    private HashMap<String, Integer> e;
    private HashMap<Integer, String> f;
    private HashMap<Integer, CustomPlatform> g;
    private HashMap<Integer, HashMap<String, Object>> h;
    private HashMap<Integer, Service> i;
    private String j;
    private boolean k;
    private String l;
    private boolean m;
    private boolean n;

    /* compiled from: ShareSDKCoreThread */
    private enum a {
        IDLE,
        INITIALIZING,
        READY
    }

    public f(Context context, String str) {
        this.j = str;
        this.b = context.getApplicationContext();
        this.c = new HashMap<>();
        this.d = new ArrayList<>();
        this.e = new HashMap<>();
        this.f = new HashMap<>();
        this.g = new HashMap<>();
        this.h = new HashMap<>();
        this.i = new HashMap<>();
    }

    public void a(boolean z) {
        this.k = z;
    }

    public void startThread() {
        this.a = a.INITIALIZING;
        d.a(this.b, 60072, this.j);
        EventRecorder.prepare(this.b);
        f();
        super.startThread();
    }

    private void f() {
        HashMap remove;
        synchronized (this.c) {
            this.c.clear();
            g();
            if (this.c.containsKey("ShareSDK") && (remove = this.c.remove("ShareSDK")) != null) {
                if (this.j == null) {
                    this.j = (String) remove.get("AppKey");
                }
                this.l = remove.containsKey("UseVersion") ? (String) remove.get("UseVersion") : "latest";
            }
        }
    }

    private void g() {
        XmlPullParser newPullParser;
        InputStream open;
        try {
            XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            newPullParser = newInstance.newPullParser();
            open = this.b.getAssets().open("ShareSDK.xml");
        } catch (Throwable th) {
            d.a().d(th);
            return;
        }
        newPullParser.setInput(open, "utf-8");
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                HashMap hashMap = new HashMap();
                int attributeCount = newPullParser.getAttributeCount();
                for (int i2 = 0; i2 < attributeCount; i2++) {
                    hashMap.put(newPullParser.getAttributeName(i2), newPullParser.getAttributeValue(i2).trim());
                }
                this.c.put(name, hashMap);
            }
        }
        open.close();
    }

    /* access modifiers changed from: protected */
    public void onStart(Message msg) {
        synchronized (this.i) {
            synchronized (this.d) {
                try {
                    String checkRecord = EventRecorder.checkRecord("ShareSDK");
                    if (!TextUtils.isEmpty(checkRecord)) {
                        cn.sharesdk.framework.b.a.a(this.b, this.j).a((HashMap<String, Object>) null);
                        d.a().w("EventRecorder checkRecord result ==" + checkRecord, new Object[0]);
                        e();
                    }
                    EventRecorder.clear();
                } catch (Throwable th) {
                    this.a = a.READY;
                    this.d.notify();
                    this.i.notify();
                    d.a().w(th);
                }
                i();
                j();
                this.a = a.READY;
                this.d.notify();
                this.i.notify();
                h();
            }
        }
    }

    private void h() {
        new Thread() {
            public void run() {
                try {
                    HashMap hashMap = new HashMap();
                    if (!f.this.d() && f.this.a((HashMap<String, Object>) hashMap)) {
                        f.this.b((HashMap<String, Object>) hashMap);
                    }
                } catch (Throwable th) {
                    d.a().w(th);
                }
            }
        }.start();
    }

    private void i() {
        this.d.clear();
        ArrayList<Platform> a2 = new e(this.b, this.j).a();
        if (a2 != null) {
            this.d.addAll(a2);
        }
        synchronized (this.e) {
            synchronized (this.f) {
                Iterator<Platform> it = this.d.iterator();
                while (it.hasNext()) {
                    Platform next = it.next();
                    this.f.put(Integer.valueOf(next.getPlatformId()), next.getName());
                    this.e.put(next.getName(), Integer.valueOf(next.getPlatformId()));
                }
            }
        }
    }

    private void j() {
        e eVar = new e(this.b, this.j);
        eVar.a(this);
        eVar.a(this.handler, this.k, 72);
    }

    /* access modifiers changed from: protected */
    public void onStop(Message msg) {
        synchronized (this.i) {
            for (Map.Entry<Integer, Service> value : this.i.entrySet()) {
                ((Service) value.getValue()).onUnbind();
            }
            this.i.clear();
        }
        synchronized (this.g) {
            this.g.clear();
        }
        try {
            new e(this.b, this.j).b();
        } catch (Throwable th) {
            d.a().w(th);
            this.handler.getLooper().quit();
            this.a = a.IDLE;
        }
    }

    /* access modifiers changed from: protected */
    public void onMessage(Message msg) {
        switch (msg.what) {
            case 1:
                this.a = a.IDLE;
                this.handler.getLooper().quit();
                return;
            default:
                return;
        }
    }

    public void a(Class<? extends Service> cls) {
        synchronized (this.i) {
            if (!this.i.containsKey(Integer.valueOf(cls.hashCode()))) {
                try {
                    Service service = (Service) cls.newInstance();
                    this.i.put(Integer.valueOf(cls.hashCode()), service);
                    service.a(this.b);
                    service.a(this.j);
                    service.onBind();
                } catch (Throwable th) {
                    d.a().w(th);
                }
                return;
            }
            return;
        }
    }

    public void b(Class<? extends Service> cls) {
        synchronized (this.i) {
            int hashCode = cls.hashCode();
            if (this.i.containsKey(Integer.valueOf(hashCode))) {
                this.i.get(Integer.valueOf(hashCode)).onUnbind();
                this.i.remove(Integer.valueOf(hashCode));
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T extends cn.sharesdk.framework.Service> T c(java.lang.Class<T> r5) {
        /*
            r4 = this;
            r1 = 0
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.Service> r2 = r4.i
            monitor-enter(r2)
            cn.sharesdk.framework.f$a r0 = r4.a     // Catch:{ all -> 0x002e }
            cn.sharesdk.framework.f$a r3 = cn.sharesdk.framework.f.a.IDLE     // Catch:{ all -> 0x002e }
            if (r0 != r3) goto L_0x000d
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            r0 = r1
        L_0x000c:
            return r0
        L_0x000d:
            cn.sharesdk.framework.f$a r0 = r4.a     // Catch:{ all -> 0x002e }
            cn.sharesdk.framework.f$a r3 = cn.sharesdk.framework.f.a.INITIALIZING     // Catch:{ all -> 0x002e }
            if (r0 != r3) goto L_0x0018
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.Service> r0 = r4.i     // Catch:{ Throwable -> 0x0031 }
            r0.wait()     // Catch:{ Throwable -> 0x0031 }
        L_0x0018:
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.Service> r0 = r4.i     // Catch:{ Throwable -> 0x003a }
            int r3 = r5.hashCode()     // Catch:{ Throwable -> 0x003a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x003a }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ Throwable -> 0x003a }
            java.lang.Object r0 = r5.cast(r0)     // Catch:{ Throwable -> 0x003a }
            cn.sharesdk.framework.Service r0 = (cn.sharesdk.framework.Service) r0     // Catch:{ Throwable -> 0x003a }
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            goto L_0x000c
        L_0x002e:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            throw r0
        L_0x0031:
            r0 = move-exception
            com.mob.tools.log.NLog r3 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x002e }
            r3.w(r0)     // Catch:{ all -> 0x002e }
            goto L_0x0018
        L_0x003a:
            r0 = move-exception
            com.mob.tools.log.NLog r3 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x002e }
            r3.w(r0)     // Catch:{ all -> 0x002e }
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            r0 = r1
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.framework.f.c(java.lang.Class):cn.sharesdk.framework.Service");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void d(java.lang.Class<? extends cn.sharesdk.framework.CustomPlatform> r8) {
        /*
            r7 = this;
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.CustomPlatform> r1 = r7.g
            monitor-enter(r1)
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.CustomPlatform> r0 = r7.g     // Catch:{ all -> 0x0070 }
            int r2 = r8.hashCode()     // Catch:{ all -> 0x0070 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0070 }
            boolean r0 = r0.containsKey(r2)     // Catch:{ all -> 0x0070 }
            if (r0 == 0) goto L_0x0015
            monitor-exit(r1)     // Catch:{ all -> 0x0070 }
        L_0x0014:
            return
        L_0x0015:
            r0 = 1
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch:{ Throwable -> 0x0079 }
            r2 = 0
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r0[r2] = r3     // Catch:{ Throwable -> 0x0079 }
            java.lang.reflect.Constructor r0 = r8.getConstructor(r0)     // Catch:{ Throwable -> 0x0079 }
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0079 }
            r3 = 0
            android.content.Context r4 = r7.b     // Catch:{ Throwable -> 0x0079 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x0079 }
            java.lang.Object r0 = r0.newInstance(r2)     // Catch:{ Throwable -> 0x0079 }
            cn.sharesdk.framework.CustomPlatform r0 = (cn.sharesdk.framework.CustomPlatform) r0     // Catch:{ Throwable -> 0x0079 }
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.CustomPlatform> r2 = r7.g     // Catch:{ Throwable -> 0x0079 }
            int r3 = r8.hashCode()     // Catch:{ Throwable -> 0x0079 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0079 }
            r2.put(r3, r0)     // Catch:{ Throwable -> 0x0079 }
            java.util.HashMap<java.lang.String, java.lang.Integer> r2 = r7.e     // Catch:{ Throwable -> 0x0079 }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x0079 }
            java.util.HashMap<java.lang.Integer, java.lang.String> r3 = r7.f     // Catch:{ all -> 0x0076 }
            monitor-enter(r3)     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x006c
            boolean r4 = r0.b()     // Catch:{ all -> 0x0073 }
            if (r4 == 0) goto L_0x006c
            java.util.HashMap<java.lang.Integer, java.lang.String> r4 = r7.f     // Catch:{ all -> 0x0073 }
            int r5 = r0.getPlatformId()     // Catch:{ all -> 0x0073 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0073 }
            java.lang.String r6 = r0.getName()     // Catch:{ all -> 0x0073 }
            r4.put(r5, r6)     // Catch:{ all -> 0x0073 }
            java.util.HashMap<java.lang.String, java.lang.Integer> r4 = r7.e     // Catch:{ all -> 0x0073 }
            java.lang.String r5 = r0.getName()     // Catch:{ all -> 0x0073 }
            int r0 = r0.getPlatformId()     // Catch:{ all -> 0x0073 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x0073 }
            r4.put(r5, r0)     // Catch:{ all -> 0x0073 }
        L_0x006c:
            monitor-exit(r3)     // Catch:{ all -> 0x0073 }
            monitor-exit(r2)     // Catch:{ all -> 0x0076 }
        L_0x006e:
            monitor-exit(r1)     // Catch:{ all -> 0x0070 }
            goto L_0x0014
        L_0x0070:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0070 }
            throw r0
        L_0x0073:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0073 }
            throw r0     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0076 }
            throw r0     // Catch:{ Throwable -> 0x0079 }
        L_0x0079:
            r0 = move-exception
            com.mob.tools.log.NLog r2 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x0070 }
            r2.w(r0)     // Catch:{ all -> 0x0070 }
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.framework.f.d(java.lang.Class):void");
    }

    public void e(Class<? extends CustomPlatform> cls) {
        int hashCode = cls.hashCode();
        synchronized (this.g) {
            this.g.remove(Integer.valueOf(hashCode));
        }
    }

    public Platform a(String str) {
        Platform[] a2;
        if (str == null || (a2 = a()) == null) {
            return null;
        }
        for (Platform platform : a2) {
            if (str.equals(platform.getName())) {
                return platform;
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        r6 = new java.util.ArrayList();
        r3 = r10.d.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        if (r3.hasNext() == false) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        r0 = r3.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0035, code lost:
        if (r0 == null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        if (r0.b() == false) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003d, code lost:
        r0.a();
        r6.add(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0050, code lost:
        new cn.sharesdk.framework.e(r10.b, r10.j).a((java.util.ArrayList<cn.sharesdk.framework.Platform>) r6);
        r3 = r10.g.entrySet().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006a, code lost:
        if (r3.hasNext() == false) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006c, code lost:
        r0 = (cn.sharesdk.framework.Platform) r3.next().getValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0078, code lost:
        if (r0 == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007e, code lost:
        if (r0.b() == false) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0080, code lost:
        r6.add(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0088, code lost:
        if (r6.size() > 0) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x008c, code lost:
        r3 = new cn.sharesdk.framework.Platform[r6.size()];
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0094, code lost:
        if (r1 >= r3.length) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0096, code lost:
        r3[r1] = (cn.sharesdk.framework.Platform) r6.get(r1);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a2, code lost:
        cn.sharesdk.framework.utils.d.a().i("sort list use time: %s", java.lang.Long.valueOf(java.lang.System.currentTimeMillis() - r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return r3;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cn.sharesdk.framework.Platform[] a() {
        /*
            r10 = this;
            r1 = 0
            r2 = 0
            long r4 = java.lang.System.currentTimeMillis()
            java.util.ArrayList<cn.sharesdk.framework.Platform> r3 = r10.d
            monitor-enter(r3)
            cn.sharesdk.framework.f$a r0 = r10.a     // Catch:{ all -> 0x004d }
            cn.sharesdk.framework.f$a r6 = cn.sharesdk.framework.f.a.IDLE     // Catch:{ all -> 0x004d }
            if (r0 != r6) goto L_0x0012
            monitor-exit(r3)     // Catch:{ all -> 0x004d }
            r0 = r1
        L_0x0011:
            return r0
        L_0x0012:
            cn.sharesdk.framework.f$a r0 = r10.a     // Catch:{ all -> 0x004d }
            cn.sharesdk.framework.f$a r6 = cn.sharesdk.framework.f.a.INITIALIZING     // Catch:{ all -> 0x004d }
            if (r0 != r6) goto L_0x001d
            java.util.ArrayList<cn.sharesdk.framework.Platform> r0 = r10.d     // Catch:{ Throwable -> 0x0044 }
            r0.wait()     // Catch:{ Throwable -> 0x0044 }
        L_0x001d:
            monitor-exit(r3)     // Catch:{ all -> 0x004d }
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.ArrayList<cn.sharesdk.framework.Platform> r0 = r10.d
            java.util.Iterator r3 = r0.iterator()
        L_0x0029:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0050
            java.lang.Object r0 = r3.next()
            cn.sharesdk.framework.Platform r0 = (cn.sharesdk.framework.Platform) r0
            if (r0 == 0) goto L_0x0029
            boolean r7 = r0.b()
            if (r7 == 0) goto L_0x0029
            r0.a()
            r6.add(r0)
            goto L_0x0029
        L_0x0044:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = cn.sharesdk.framework.utils.d.a()     // Catch:{ all -> 0x004d }
            r6.w(r0)     // Catch:{ all -> 0x004d }
            goto L_0x001d
        L_0x004d:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004d }
            throw r0
        L_0x0050:
            cn.sharesdk.framework.e r0 = new cn.sharesdk.framework.e
            android.content.Context r3 = r10.b
            java.lang.String r7 = r10.j
            r0.<init>(r3, r7)
            r0.a((java.util.ArrayList<cn.sharesdk.framework.Platform>) r6)
            java.util.HashMap<java.lang.Integer, cn.sharesdk.framework.CustomPlatform> r0 = r10.g
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r3 = r0.iterator()
        L_0x0066:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0084
            java.lang.Object r0 = r3.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r0 = r0.getValue()
            cn.sharesdk.framework.Platform r0 = (cn.sharesdk.framework.Platform) r0
            if (r0 == 0) goto L_0x0066
            boolean r7 = r0.b()
            if (r7 == 0) goto L_0x0066
            r6.add(r0)
            goto L_0x0066
        L_0x0084:
            int r0 = r6.size()
            if (r0 > 0) goto L_0x008c
            r0 = r1
            goto L_0x0011
        L_0x008c:
            int r0 = r6.size()
            cn.sharesdk.framework.Platform[] r3 = new cn.sharesdk.framework.Platform[r0]
            r1 = r2
        L_0x0093:
            int r0 = r3.length
            if (r1 >= r0) goto L_0x00a2
            java.lang.Object r0 = r6.get(r1)
            cn.sharesdk.framework.Platform r0 = (cn.sharesdk.framework.Platform) r0
            r3[r1] = r0
            int r0 = r1 + 1
            r1 = r0
            goto L_0x0093
        L_0x00a2:
            com.mob.tools.log.NLog r0 = cn.sharesdk.framework.utils.d.a()
            java.lang.String r1 = "sort list use time: %s"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            long r8 = java.lang.System.currentTimeMillis()
            long r4 = r8 - r4
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r6[r2] = r4
            r0.i(r1, r6)
            r0 = r3
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.framework.f.a():cn.sharesdk.framework.Platform[]");
    }

    public String b() {
        try {
            return new e(this.b, this.j).c();
        } catch (Throwable th) {
            d.a().w(th);
            return "2.8.3";
        }
    }

    public void a(int i2) {
        NetworkHelper.connectionTimeout = i2;
    }

    public void b(int i2) {
        NetworkHelper.readTimout = i2;
    }

    public void b(boolean z) {
        this.m = z;
    }

    public boolean c() {
        return this.m;
    }

    public void a(int i2, Platform platform) {
        new e(this.b, this.j).a(i2, platform);
    }

    public void a(String str, int i2) {
        new e(this.b, this.j).a(str, i2);
    }

    public void a(String str, HashMap<String, Object> hashMap) {
        HashMap hashMap2;
        synchronized (this.c) {
            HashMap hashMap3 = this.c.get(str);
            if (hashMap3 == null) {
                hashMap2 = new HashMap();
            } else {
                hashMap2 = hashMap3;
            }
            synchronized (hashMap2) {
                for (Map.Entry next : hashMap.entrySet()) {
                    String str2 = (String) next.getKey();
                    Object value = next.getValue();
                    if (value != null) {
                        hashMap2.put(str2, String.valueOf(value));
                    }
                }
            }
            this.c.put(str, hashMap2);
        }
        synchronized (this.d) {
            if (this.a == a.INITIALIZING) {
                try {
                    this.d.wait();
                } catch (Throwable th) {
                    d.a().w(th);
                }
            }
            Iterator<Platform> it = this.d.iterator();
            while (true) {
                if (it.hasNext()) {
                    Platform next2 = it.next();
                    if (next2 != null && next2.getName().equals(str)) {
                        next2.a();
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    public String c(int i2) {
        String str;
        synchronized (this.f) {
            str = this.f.get(Integer.valueOf(i2));
        }
        return str;
    }

    public int b(String str) {
        int i2;
        synchronized (this.e) {
            if (this.e.containsKey(str)) {
                i2 = this.e.get(str).intValue();
            } else {
                i2 = 0;
            }
        }
        return i2;
    }

    public void a(String str, String str2) {
        synchronized (this.c) {
            this.c.put(str2, this.c.get(str));
        }
    }

    public void a(int i2, int i3) {
        synchronized (this.h) {
            new e(this.b, this.j).a(i2, i3, this.h);
        }
    }

    public String b(String str, String str2) {
        String str3;
        synchronized (this.c) {
            HashMap hashMap = this.c.get(str);
            if (hashMap == null) {
                str3 = null;
            } else {
                str3 = (String) hashMap.get(str2);
            }
        }
        return str3;
    }

    public String a(int i2, String str) {
        String a2;
        synchronized (this.h) {
            a2 = new e(this.b, this.j).a(i2, str, this.h);
        }
        return a2;
    }

    public boolean d() {
        boolean z;
        synchronized (this.h) {
            if (this.h == null || this.h.size() <= 0) {
                z = this.n;
            } else {
                z = true;
            }
        }
        return z;
    }

    public boolean a(HashMap<String, Object> hashMap) {
        if (a.READY != this.a) {
            d.a().d("Statistics module unopened", new Object[0]);
            return false;
        }
        final cn.sharesdk.framework.b.a a2 = cn.sharesdk.framework.b.a.a(this.b, this.j);
        boolean a3 = a(a2, a2.d(), hashMap);
        if (a3) {
            this.n = true;
            new Thread() {
                public void run() {
                    try {
                        HashMap<String, Object> e = a2.e();
                        HashMap hashMap = new HashMap();
                        if (f.this.a(a2, e, hashMap)) {
                            f.this.b((HashMap<String, Object>) hashMap);
                        }
                        a2.a(e);
                    } catch (Throwable th) {
                        d.a().w(th);
                    }
                }
            }.start();
            return a3;
        }
        try {
            HashMap<String, Object> e2 = a2.e();
            boolean a4 = a(a2, e2, hashMap);
            if (a4) {
                a2.a(e2);
            }
            this.n = true;
            return a4;
        } catch (Throwable th) {
            d.a().w(th);
            this.n = false;
            return a3;
        }
    }

    /* access modifiers changed from: private */
    public boolean a(cn.sharesdk.framework.b.a aVar, HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2) {
        try {
            if (hashMap.containsKey("error")) {
                d.a().i("ShareSDK parse sns config ==>>", new Hashon().fromHashMap(hashMap));
                return false;
            } else if (!hashMap.containsKey(UriUtil.LOCAL_RESOURCE_SCHEME)) {
                d.a().d("ShareSDK platform config result ==>>", "SNS configuration is empty");
                return false;
            } else {
                String str = (String) hashMap.get(UriUtil.LOCAL_RESOURCE_SCHEME);
                if (str == null) {
                    return false;
                }
                hashMap2.putAll(aVar.b(str));
                return true;
            }
        } catch (Throwable th) {
            d.a().w(th);
            return false;
        }
    }

    public boolean b(HashMap<String, Object> hashMap) {
        boolean a2;
        synchronized (this.h) {
            this.h.clear();
            a2 = new e(this.b, this.j).a(hashMap, this.h);
        }
        return a2;
    }

    public String a(String str, boolean z, int i2, String str2) {
        return a.READY != this.a ? str : new e(this.b, this.j).a(str, z, i2, str2);
    }

    public String c(String str) {
        if (a.READY != this.a) {
            return null;
        }
        return new e(this.b, this.j).a(str);
    }

    public String a(Bitmap bitmap) {
        if (a.READY != this.a) {
            return null;
        }
        return new e(this.b, this.j).a(bitmap);
    }

    public void e() {
        try {
            ResHelper.clearCache(this.b);
        } catch (Throwable th) {
            d.a().w(th);
        }
    }
}

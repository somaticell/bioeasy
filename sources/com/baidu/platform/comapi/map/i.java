package com.baidu.platform.comapi.map;

import android.content.Context;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.NetworkUtil;
import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.platform.comapi.NativeLoader;
import com.baidu.platform.comapi.commonutils.SysUpdateUtil;
import com.baidu.platform.comapi.util.SysUpdateObservable;
import com.baidu.platform.comjni.engine.AppEngine;
import com.baidu.vi.VMsg;

public class i {
    private static int a;
    private static Context b = BMapManager.getContext();

    static {
        if (!VersionInfo.getApiVersion().equals(VersionInfo.getApiVersion())) {
            throw new BaiduMapSDKException("the version of map is not match with base");
        }
        NativeLoader.getInstance().loadLibrary(VersionInfo.getKitName());
        AppEngine.InitClass();
        a(BMapManager.getContext());
        SysUpdateObservable.getInstance().addObserver(new SysUpdateUtil());
        SysUpdateObservable.getInstance().init();
    }

    public static void a() {
        if (a == 0) {
            if (b == null) {
                throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
            }
            VMsg.init();
            AppEngine.InitEngine(b);
            AppEngine.StartSocketProc();
            NetworkUtil.updateNetworkProxy(b);
        }
        a++;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0169 A[Catch:{ Exception -> 0x01de }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x01ce A[LOOP:0: B:29:0x01cb->B:31:0x01ce, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01e6 A[LOOP:1: B:37:0x01e6->B:39:0x01e9, LOOP_START, PHI: r1 
      PHI: (r1v1 int) = (r1v0 int), (r1v2 int) binds: [B:36:0x01e4, B:39:0x01e9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r14) {
        /*
            r8 = 3
            r6 = 2
            r7 = 6
            r0 = 1
            r1 = 0
            if (r14 != 0) goto L_0x0008
        L_0x0007:
            return
        L_0x0008:
            java.lang.String r2 = com.baidu.mapapi.common.SysOSUtil.getModuleFileName()
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x01d8 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x01d8 }
            boolean r2 = r3.exists()     // Catch:{ Exception -> 0x01d8 }
            if (r2 != 0) goto L_0x001a
            r3.mkdirs()     // Catch:{ Exception -> 0x01d8 }
        L_0x001a:
            r14.getAssets()
            r2 = 19
            java.lang.String[] r3 = new java.lang.String[r2]
            java.lang.String r2 = "cfg/a/mode_1/map.sdkrs"
            r3[r1] = r2
            java.lang.String r2 = "cfg/a/mode_1/map1.sdkrs"
            r3[r0] = r2
            java.lang.String r2 = "cfg/a/mode_1/reduct.sdkrs"
            r3[r6] = r2
            java.lang.String r2 = "cfg/a/mode_1/traffic.sdkrs"
            r3[r8] = r2
            r2 = 4
            java.lang.String r4 = "cfg/a/mode_1/bus.sty"
            r3[r2] = r4
            r2 = 5
            java.lang.String r4 = "cfg/a/mode_1/car.sty"
            r3[r2] = r4
            java.lang.String r2 = "cfg/a/mode_1/cycle.sty"
            r3[r7] = r2
            r2 = 7
            java.lang.String r4 = "cfg/a/mode_1/map.sty"
            r3[r2] = r4
            r2 = 8
            java.lang.String r4 = "cfg/a/mode_1/reduct.sty"
            r3[r2] = r4
            r2 = 9
            java.lang.String r4 = "cfg/a/mode_1/traffic.sty"
            r3[r2] = r4
            r2 = 10
            java.lang.String r4 = "cfg/idrres/ResPackIndoorMap.sdkrs"
            r3[r2] = r4
            r2 = 11
            java.lang.String r4 = "cfg/idrres/DVIndoor.cfg"
            r3[r2] = r4
            r2 = 12
            java.lang.String r4 = "cfg/idrres/baseindoormap.sty"
            r3[r2] = r4
            r2 = 13
            java.lang.String r4 = "cfg/a/DVDirectory.cfg"
            r3[r2] = r4
            r2 = 14
            java.lang.String r4 = "cfg/a/DVHotcity.cfg"
            r3[r2] = r4
            r2 = 15
            java.lang.String r4 = "cfg/a/DVHotMap.cfg"
            r3[r2] = r4
            r2 = 16
            java.lang.String r4 = "cfg/a/DVSDirectory.cfg"
            r3[r2] = r4
            r2 = 17
            java.lang.String r4 = "cfg/a/DVVersion.cfg"
            r3[r2] = r4
            r2 = 18
            java.lang.String r4 = "cfg/a/CustomIndex"
            r3[r2] = r4
            java.lang.String[] r4 = new java.lang.String[r0]
            java.lang.String r2 = "cfg/a/CustomIndex"
            r4[r1] = r2
            r2 = 19
            java.lang.String[] r5 = new java.lang.String[r2]
            java.lang.String r2 = "cfg/a/mode_1/map.rs"
            r5[r1] = r2
            java.lang.String r2 = "cfg/a/mode_1/map1.rs"
            r5[r0] = r2
            java.lang.String r2 = "cfg/a/mode_1/reduct.rs"
            r5[r6] = r2
            java.lang.String r2 = "cfg/a/mode_1/traffic.rs"
            r5[r8] = r2
            r2 = 4
            java.lang.String r6 = "cfg/a/mode_1/bus.sty"
            r5[r2] = r6
            r2 = 5
            java.lang.String r6 = "cfg/a/mode_1/car.sty"
            r5[r2] = r6
            java.lang.String r2 = "cfg/a/mode_1/cycle.sty"
            r5[r7] = r2
            r2 = 7
            java.lang.String r6 = "cfg/a/mode_1/map.sty"
            r5[r2] = r6
            r2 = 8
            java.lang.String r6 = "cfg/a/mode_1/reduct.sty"
            r5[r2] = r6
            r2 = 9
            java.lang.String r6 = "cfg/a/mode_1/traffic.sty"
            r5[r2] = r6
            r2 = 10
            java.lang.String r6 = "cfg/idrres/ResPackIndoorMap.rs"
            r5[r2] = r6
            r2 = 11
            java.lang.String r6 = "cfg/idrres/DVIndoor.cfg"
            r5[r2] = r6
            r2 = 12
            java.lang.String r6 = "cfg/idrres/baseindoormap.sty"
            r5[r2] = r6
            r2 = 13
            java.lang.String r6 = "cfg/a/DVDirectory.cfg"
            r5[r2] = r6
            r2 = 14
            java.lang.String r6 = "cfg/a/DVHotcity.cfg"
            r5[r2] = r6
            r2 = 15
            java.lang.String r6 = "cfg/a/DVHotMap.cfg"
            r5[r2] = r6
            r2 = 16
            java.lang.String r6 = "cfg/a/DVSDirectory.cfg"
            r5[r2] = r6
            r2 = 17
            java.lang.String r6 = "cfg/a/DVVersion.cfg"
            r5[r2] = r6
            r2 = 18
            java.lang.String r6 = "cfg/a/CustomIndex"
            r5[r2] = r6
            java.lang.String[] r6 = new java.lang.String[r0]
            java.lang.String r2 = "cfg/a/CustomIndex"
            r6[r1] = r2
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
            r2.<init>()     // Catch:{ Exception -> 0x01de }
            java.lang.String r8 = com.baidu.mapapi.common.SysOSUtil.getModuleFileName()     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r2 = r2.append(r8)     // Catch:{ Exception -> 0x01de }
            java.lang.String r8 = "/ver.dat"
            java.lang.StringBuilder r2 = r2.append(r8)     // Catch:{ Exception -> 0x01de }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01de }
            r7.<init>(r2)     // Catch:{ Exception -> 0x01de }
            r2 = 6
            byte[] r8 = new byte[r2]     // Catch:{ Exception -> 0x01de }
            r8 = {4, 0, 3, 0, 0, 0} // fill-array     // Catch:{ Exception -> 0x01de }
            boolean r2 = r7.exists()     // Catch:{ Exception -> 0x01de }
            if (r2 == 0) goto L_0x01f3
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x01de }
            r2.<init>(r7)     // Catch:{ Exception -> 0x01de }
            int r9 = r2.available()     // Catch:{ Exception -> 0x01de }
            byte[] r9 = new byte[r9]     // Catch:{ Exception -> 0x01de }
            r2.read(r9)     // Catch:{ Exception -> 0x01de }
            r2.close()     // Catch:{ Exception -> 0x01de }
            boolean r2 = java.util.Arrays.equals(r9, r8)     // Catch:{ Exception -> 0x01de }
            if (r2 == 0) goto L_0x01f3
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
            r9.<init>()     // Catch:{ Exception -> 0x01de }
            java.lang.String r10 = com.baidu.mapapi.common.SysOSUtil.getModuleFileName()     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x01de }
            java.lang.String r10 = "/cfg/a/mode_1/map.sty"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x01de }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x01de }
            r2.<init>(r9)     // Catch:{ Exception -> 0x01de }
            boolean r9 = r2.exists()     // Catch:{ Exception -> 0x01de }
            if (r9 == 0) goto L_0x01f3
            long r10 = r2.length()     // Catch:{ Exception -> 0x01de }
            r12 = 0
            int r2 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r2 <= 0) goto L_0x01f3
            r2 = r1
        L_0x0167:
            if (r2 == 0) goto L_0x01ca
            boolean r0 = r7.exists()     // Catch:{ Exception -> 0x01de }
            if (r0 == 0) goto L_0x0172
            r7.delete()     // Catch:{ Exception -> 0x01de }
        L_0x0172:
            r7.createNewFile()     // Catch:{ Exception -> 0x01de }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x01de }
            r0.<init>(r7)     // Catch:{ Exception -> 0x01de }
            r0.write(r8)     // Catch:{ Exception -> 0x01de }
            r0.close()     // Catch:{ Exception -> 0x01de }
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
            r7.<init>()     // Catch:{ Exception -> 0x01de }
            java.lang.String r8 = com.baidu.mapapi.common.SysOSUtil.getModuleFileName()     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x01de }
            java.lang.String r8 = "/cfg/a/mode_1"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x01de }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x01de }
            r0.<init>(r7)     // Catch:{ Exception -> 0x01de }
            boolean r7 = r0.exists()     // Catch:{ Exception -> 0x01de }
            if (r7 != 0) goto L_0x01a5
            r0.mkdirs()     // Catch:{ Exception -> 0x01de }
        L_0x01a5:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
            r7.<init>()     // Catch:{ Exception -> 0x01de }
            java.lang.String r8 = com.baidu.mapapi.common.SysOSUtil.getModuleFileName()     // Catch:{ Exception -> 0x01de }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x01de }
            java.lang.String r8 = "/cfg/idrres"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x01de }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x01de }
            r0.<init>(r7)     // Catch:{ Exception -> 0x01de }
            boolean r7 = r0.exists()     // Catch:{ Exception -> 0x01de }
            if (r7 != 0) goto L_0x01ca
            r0.mkdirs()     // Catch:{ Exception -> 0x01de }
        L_0x01ca:
            r0 = r1
        L_0x01cb:
            int r7 = r6.length
            if (r0 >= r7) goto L_0x01e4
            r7 = r4[r0]
            r8 = r6[r0]
            com.baidu.platform.comapi.commonutils.a.a(r7, r8, r14)
            int r0 = r0 + 1
            goto L_0x01cb
        L_0x01d8:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0007
        L_0x01de:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0007
        L_0x01e4:
            if (r2 == 0) goto L_0x0007
        L_0x01e6:
            int r0 = r5.length
            if (r1 >= r0) goto L_0x0007
            r0 = r3[r1]
            r2 = r5[r1]
            com.baidu.platform.comapi.commonutils.a.a(r0, r2, r14)
            int r1 = r1 + 1
            goto L_0x01e6
        L_0x01f3:
            r2 = r0
            goto L_0x0167
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.i.a(android.content.Context):void");
    }

    public static void a(boolean z) {
        e.k(z);
    }

    public static void b() {
        a--;
        if (a == 0) {
            AppEngine.UnInitEngine();
            VMsg.destroy();
        }
    }
}

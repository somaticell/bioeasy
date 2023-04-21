package com.loc;

import android.content.BroadcastReceiver;

final class cp extends BroadcastReceiver {
    final /* synthetic */ cl a;

    private cp(cl clVar) {
        this.a = clVar;
    }

    /* synthetic */ cp(cl clVar, byte b) {
        this(clVar);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onReceive(android.content.Context r7, android.content.Intent r8) {
        /*
            r6 = this;
            if (r7 == 0) goto L_0x00a5
            if (r8 == 0) goto L_0x00a5
            com.loc.cl r0 = r6.a     // Catch:{ Exception -> 0x00a9 }
            android.net.wifi.WifiManager r0 = r0.g     // Catch:{ Exception -> 0x00a9 }
            if (r0 == 0) goto L_0x00a5
            com.loc.cl r0 = r6.a     // Catch:{ Exception -> 0x00a9 }
            java.util.Timer r0 = r0.D     // Catch:{ Exception -> 0x00a9 }
            if (r0 == 0) goto L_0x00a5
            com.loc.cl r0 = r6.a     // Catch:{ Exception -> 0x00a9 }
            java.util.List r0 = r0.C     // Catch:{ Exception -> 0x00a9 }
            if (r0 == 0) goto L_0x00a5
            java.lang.String r0 = r8.getAction()     // Catch:{ Exception -> 0x00a9 }
            if (r0 == 0) goto L_0x00a5
            java.lang.String r0 = "android.net.wifi.SCAN_RESULTS"
            java.lang.String r1 = r8.getAction()     // Catch:{ Exception -> 0x00a9 }
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x00a9 }
            if (r0 == 0) goto L_0x00a5
            com.loc.cl r0 = r6.a     // Catch:{ Exception -> 0x00a9 }
            android.net.wifi.WifiManager r0 = r0.g     // Catch:{ Exception -> 0x00a9 }
            java.util.List r2 = r0.getScanResults()     // Catch:{ Exception -> 0x00a9 }
            monitor-enter(r6)     // Catch:{ Exception -> 0x00a9 }
            com.loc.cl r0 = r6.a     // Catch:{ all -> 0x00a6 }
            java.util.List r0 = r0.C     // Catch:{ all -> 0x00a6 }
            r0.clear()     // Catch:{ all -> 0x00a6 }
            com.loc.cl r0 = r6.a     // Catch:{ all -> 0x00a6 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00a6 }
            long unused = r0.v = r4     // Catch:{ all -> 0x00a6 }
            if (r2 == 0) goto L_0x006e
            int r0 = r2.size()     // Catch:{ all -> 0x00a6 }
            if (r0 <= 0) goto L_0x006e
            r0 = 0
            r1 = r0
        L_0x0055:
            int r0 = r2.size()     // Catch:{ all -> 0x00a6 }
            if (r1 >= r0) goto L_0x006e
            java.lang.Object r0 = r2.get(r1)     // Catch:{ all -> 0x00a6 }
            android.net.wifi.ScanResult r0 = (android.net.wifi.ScanResult) r0     // Catch:{ all -> 0x00a6 }
            com.loc.cl r3 = r6.a     // Catch:{ all -> 0x00a6 }
            java.util.List r3 = r3.C     // Catch:{ all -> 0x00a6 }
            r3.add(r0)     // Catch:{ all -> 0x00a6 }
            int r0 = r1 + 1
            r1 = r0
            goto L_0x0055
        L_0x006e:
            monitor-exit(r6)     // Catch:{ all -> 0x00a6 }
            com.loc.cq r0 = new com.loc.cq     // Catch:{ Exception -> 0x00a9 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x00a9 }
            monitor-enter(r6)     // Catch:{ Exception -> 0x00a9 }
            com.loc.cl r1 = r6.a     // Catch:{ all -> 0x00ab }
            java.util.Timer r1 = r1.D     // Catch:{ all -> 0x00ab }
            if (r1 == 0) goto L_0x008c
            com.loc.cl r1 = r6.a     // Catch:{ all -> 0x00ab }
            java.util.Timer r1 = r1.D     // Catch:{ all -> 0x00ab }
            r1.cancel()     // Catch:{ all -> 0x00ab }
            com.loc.cl r1 = r6.a     // Catch:{ all -> 0x00ab }
            r2 = 0
            java.util.Timer unused = r1.D = r2     // Catch:{ all -> 0x00ab }
        L_0x008c:
            com.loc.cl r1 = r6.a     // Catch:{ all -> 0x00ab }
            java.util.Timer r2 = new java.util.Timer     // Catch:{ all -> 0x00ab }
            r2.<init>()     // Catch:{ all -> 0x00ab }
            java.util.Timer unused = r1.D = r2     // Catch:{ all -> 0x00ab }
            com.loc.cl r1 = r6.a     // Catch:{ all -> 0x00ab }
            java.util.Timer r1 = r1.D     // Catch:{ all -> 0x00ab }
            int r2 = com.loc.cl.G     // Catch:{ all -> 0x00ab }
            long r2 = (long) r2     // Catch:{ all -> 0x00ab }
            r1.schedule(r0, r2)     // Catch:{ all -> 0x00ab }
            monitor-exit(r6)     // Catch:{ all -> 0x00ab }
        L_0x00a5:
            return
        L_0x00a6:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ Exception -> 0x00a9 }
            throw r0     // Catch:{ Exception -> 0x00a9 }
        L_0x00a9:
            r0 = move-exception
            goto L_0x00a5
        L_0x00ab:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ Exception -> 0x00a9 }
            throw r0     // Catch:{ Exception -> 0x00a9 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cp.onReceive(android.content.Context, android.content.Intent):void");
    }
}

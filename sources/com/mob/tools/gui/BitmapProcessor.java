package com.mob.tools.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.RawNetworkCallback;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;

public class BitmapProcessor {
    private static final int CAPACITY = 3;
    private static final int MAX_CACHE_SIZE = 50;
    private static final int MAX_CACHE_TIME = 60000;
    private static final int MAX_REQ_TIME = 20000;
    private static final int MAX_SIZE = 100;
    private static final int OVERFLOW_SIZE = 120;
    private static final int SCAN_INTERVAL = 20000;
    /* access modifiers changed from: private */
    public static File cacheDir;
    /* access modifiers changed from: private */
    public static CachePool<String, SoftReference<Bitmap>> cachePool = new CachePool<>(50);
    private static ManagerThread manager;
    /* access modifiers changed from: private */
    public static ArrayList<ImageReq> netReqTPS = new ArrayList<>();
    /* access modifiers changed from: private */
    public static ArrayList<ImageReq> reqList = new ArrayList<>();
    /* access modifiers changed from: private */
    public static NetworkHelper.NetworkTimeOut timeout;
    /* access modifiers changed from: private */
    public static boolean work;
    /* access modifiers changed from: private */
    public static WorkerThread[] workerList = new WorkerThread[3];

    public interface BitmapCallback {
        void onImageGot(String str, Bitmap bitmap);
    }

    static {
        NetworkHelper.NetworkTimeOut timeout2 = new NetworkHelper.NetworkTimeOut();
        timeout2.connectionTimeout = 5000;
        timeout2.readTimout = 20000 - timeout2.connectionTimeout;
    }

    public static synchronized void prepare(Context context) {
        synchronized (BitmapProcessor.class) {
            cacheDir = new File(ResHelper.getImageCachePath(context));
        }
    }

    public static synchronized void start() {
        synchronized (BitmapProcessor.class) {
            if (!work) {
                work = true;
                manager = new ManagerThread();
            }
        }
    }

    public static synchronized void stop() {
        synchronized (BitmapProcessor.class) {
            if (work) {
                work = false;
                synchronized (reqList) {
                    reqList.clear();
                    cachePool.clear();
                }
                manager.quit();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r1 = new com.mob.tools.gui.BitmapProcessor.ImageReq();
        com.mob.tools.gui.BitmapProcessor.ImageReq.access$002(r1, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0050, code lost:
        if (r8 == null) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0052, code lost:
        com.mob.tools.gui.BitmapProcessor.ImageReq.access$100(r1).add(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0059, code lost:
        r5 = reqList;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x005b, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        reqList.add(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0069, code lost:
        if (reqList.size() <= OVERFLOW_SIZE) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0073, code lost:
        if (reqList.size() <= 100) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0075, code lost:
        reqList.remove(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        start();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void process(java.lang.String r7, com.mob.tools.gui.BitmapProcessor.BitmapCallback r8) {
        /*
            java.lang.Class<com.mob.tools.gui.BitmapProcessor> r4 = com.mob.tools.gui.BitmapProcessor.class
            monitor-enter(r4)
            if (r7 != 0) goto L_0x0007
        L_0x0005:
            monitor-exit(r4)
            return
        L_0x0007:
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r5 = reqList     // Catch:{ all -> 0x0041 }
            monitor-enter(r5)     // Catch:{ all -> 0x0041 }
            r0 = 0
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r3 = reqList     // Catch:{ all -> 0x003e }
            int r2 = r3.size()     // Catch:{ all -> 0x003e }
        L_0x0011:
            if (r0 >= r2) goto L_0x0047
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r3 = reqList     // Catch:{ all -> 0x003e }
            java.lang.Object r1 = r3.get(r0)     // Catch:{ all -> 0x003e }
            com.mob.tools.gui.BitmapProcessor$ImageReq r1 = (com.mob.tools.gui.BitmapProcessor.ImageReq) r1     // Catch:{ all -> 0x003e }
            java.lang.String r3 = r1.url     // Catch:{ all -> 0x003e }
            boolean r3 = r3.equals(r7)     // Catch:{ all -> 0x003e }
            if (r3 == 0) goto L_0x0044
            if (r8 == 0) goto L_0x0039
            java.util.ArrayList r3 = r1.callbacks     // Catch:{ all -> 0x003e }
            int r3 = r3.indexOf(r8)     // Catch:{ all -> 0x003e }
            r6 = -1
            if (r3 != r6) goto L_0x0039
            java.util.ArrayList r3 = r1.callbacks     // Catch:{ all -> 0x003e }
            r3.add(r8)     // Catch:{ all -> 0x003e }
        L_0x0039:
            start()     // Catch:{ all -> 0x003e }
            monitor-exit(r5)     // Catch:{ all -> 0x003e }
            goto L_0x0005
        L_0x003e:
            r3 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x003e }
            throw r3     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r3 = move-exception
            monitor-exit(r4)
            throw r3
        L_0x0044:
            int r0 = r0 + 1
            goto L_0x0011
        L_0x0047:
            monitor-exit(r5)     // Catch:{ all -> 0x003e }
            com.mob.tools.gui.BitmapProcessor$ImageReq r1 = new com.mob.tools.gui.BitmapProcessor$ImageReq     // Catch:{ all -> 0x0041 }
            r1.<init>()     // Catch:{ all -> 0x0041 }
            java.lang.String unused = r1.url = r7     // Catch:{ all -> 0x0041 }
            if (r8 == 0) goto L_0x0059
            java.util.ArrayList r3 = r1.callbacks     // Catch:{ all -> 0x0041 }
            r3.add(r8)     // Catch:{ all -> 0x0041 }
        L_0x0059:
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r5 = reqList     // Catch:{ all -> 0x0041 }
            monitor-enter(r5)     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r3 = reqList     // Catch:{ all -> 0x007c }
            r3.add(r1)     // Catch:{ all -> 0x007c }
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r3 = reqList     // Catch:{ all -> 0x007c }
            int r3 = r3.size()     // Catch:{ all -> 0x007c }
            r6 = 120(0x78, float:1.68E-43)
            if (r3 <= r6) goto L_0x007f
        L_0x006b:
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r3 = reqList     // Catch:{ all -> 0x007c }
            int r3 = r3.size()     // Catch:{ all -> 0x007c }
            r6 = 100
            if (r3 <= r6) goto L_0x007f
            java.util.ArrayList<com.mob.tools.gui.BitmapProcessor$ImageReq> r3 = reqList     // Catch:{ all -> 0x007c }
            r6 = 0
            r3.remove(r6)     // Catch:{ all -> 0x007c }
            goto L_0x006b
        L_0x007c:
            r3 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x007c }
            throw r3     // Catch:{ all -> 0x0041 }
        L_0x007f:
            monitor-exit(r5)     // Catch:{ all -> 0x007c }
            start()     // Catch:{ all -> 0x0041 }
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.gui.BitmapProcessor.process(java.lang.String, com.mob.tools.gui.BitmapProcessor$BitmapCallback):void");
    }

    public static Bitmap getBitmapFromCache(String url) {
        if (cachePool == null || url == null || cachePool.get(url) == null) {
            return null;
        }
        return (Bitmap) cachePool.get(url).get();
    }

    private static class ManagerThread implements Handler.Callback {
        private Handler handler;

        public ManagerThread() {
            MobHandlerThread thread = new MobHandlerThread() {
                public void run() {
                    ManagerThread.this.beforeRun();
                    super.run();
                }
            };
            thread.start();
            this.handler = new Handler(thread.getLooper(), this);
            this.handler.sendEmptyMessageDelayed(1, 20000);
        }

        /* access modifiers changed from: private */
        public void beforeRun() {
            int i = 0;
            while (i < BitmapProcessor.workerList.length) {
                if (BitmapProcessor.workerList[i] == null) {
                    BitmapProcessor.workerList[i] = new WorkerThread();
                    BitmapProcessor.workerList[i].setName("worker " + i);
                    boolean unused = BitmapProcessor.workerList[i].localType = i == 0;
                    BitmapProcessor.workerList[i].start();
                }
                i++;
            }
        }

        public boolean handleMessage(Message msg) {
            if (BitmapProcessor.cachePool != null) {
                BitmapProcessor.cachePool.trimBeforeTime(System.currentTimeMillis() - 60000);
            }
            MobLog.getInstance().d(">>>> BitmapProcessor.cachePool: " + (BitmapProcessor.cachePool == null ? 0 : BitmapProcessor.cachePool.size()), new Object[0]);
            MobLog.getInstance().d(">>>> BitmapProcessor.reqList: " + (BitmapProcessor.reqList == null ? 0 : BitmapProcessor.reqList.size()), new Object[0]);
            if (BitmapProcessor.work) {
                this.handler.sendEmptyMessageDelayed(1, 20000);
            }
            return false;
        }

        public void quit() {
            this.handler.removeMessages(1);
            this.handler.getLooper().quit();
            for (int i = 0; i < BitmapProcessor.workerList.length; i++) {
                if (BitmapProcessor.workerList[i] != null) {
                    BitmapProcessor.workerList[i].interrupt();
                    BitmapProcessor.workerList[i] = null;
                }
            }
        }
    }

    private static class WorkerThread extends Thread {
        /* access modifiers changed from: private */
        public ImageReq curReq;
        /* access modifiers changed from: private */
        public boolean localType;

        private WorkerThread() {
        }

        public void run() {
            while (BitmapProcessor.work) {
                try {
                    if (this.localType) {
                        doLocalTask();
                    } else {
                        doNetworkTask();
                    }
                } catch (Throwable t) {
                    MobLog.getInstance().w(t);
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v21, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.mob.tools.gui.BitmapProcessor$ImageReq} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void doLocalTask() throws java.lang.Throwable {
            /*
                r9 = this;
                r4 = 0
                java.util.ArrayList r6 = com.mob.tools.gui.BitmapProcessor.reqList
                monitor-enter(r6)
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x003e }
                int r5 = r5.size()     // Catch:{ all -> 0x003e }
                if (r5 <= 0) goto L_0x001d
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x003e }
                r7 = 0
                java.lang.Object r5 = r5.remove(r7)     // Catch:{ all -> 0x003e }
                r0 = r5
                com.mob.tools.gui.BitmapProcessor$ImageReq r0 = (com.mob.tools.gui.BitmapProcessor.ImageReq) r0     // Catch:{ all -> 0x003e }
                r4 = r0
            L_0x001d:
                monitor-exit(r6)     // Catch:{ all -> 0x003e }
                if (r4 == 0) goto L_0x00ab
                com.mob.tools.gui.CachePool r5 = com.mob.tools.gui.BitmapProcessor.cachePool
                java.lang.String r6 = r4.url
                java.lang.Object r3 = r5.get(r6)
                java.lang.ref.SoftReference r3 = (java.lang.ref.SoftReference) r3
                if (r3 != 0) goto L_0x0041
                r2 = 0
            L_0x0031:
                if (r2 == 0) goto L_0x0049
                r9.curReq = r4
                com.mob.tools.gui.BitmapProcessor$ImageReq r5 = r9.curReq
                com.mob.tools.gui.BitmapProcessor.WorkerThread unused = r5.worker = r9
                r4.throwComplete(r2)
            L_0x003d:
                return
            L_0x003e:
                r5 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x003e }
                throw r5
            L_0x0041:
                java.lang.Object r5 = r3.get()
                android.graphics.Bitmap r5 = (android.graphics.Bitmap) r5
                r2 = r5
                goto L_0x0031
            L_0x0049:
                java.io.File r5 = com.mob.tools.gui.BitmapProcessor.cacheDir
                if (r5 == 0) goto L_0x006a
                java.io.File r5 = new java.io.File
                java.io.File r6 = com.mob.tools.gui.BitmapProcessor.cacheDir
                java.lang.String r7 = r4.url
                java.lang.String r7 = com.mob.tools.utils.Data.MD5((java.lang.String) r7)
                r5.<init>(r6, r7)
                boolean r5 = r5.exists()
                if (r5 == 0) goto L_0x006a
                r9.doTask(r4)
                goto L_0x003d
            L_0x006a:
                java.util.ArrayList r6 = com.mob.tools.gui.BitmapProcessor.reqList
                monitor-enter(r6)
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.netReqTPS     // Catch:{ all -> 0x0096 }
                int r5 = r5.size()     // Catch:{ all -> 0x0096 }
                r7 = 100
                if (r5 <= r7) goto L_0x00a2
                java.util.ArrayList r7 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x0096 }
                monitor-enter(r7)     // Catch:{ all -> 0x0096 }
            L_0x0080:
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x0093 }
                int r5 = r5.size()     // Catch:{ all -> 0x0093 }
                if (r5 <= 0) goto L_0x0099
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x0093 }
                r8 = 0
                r5.remove(r8)     // Catch:{ all -> 0x0093 }
                goto L_0x0080
            L_0x0093:
                r5 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x0093 }
                throw r5     // Catch:{ all -> 0x0096 }
            L_0x0096:
                r5 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0096 }
                throw r5
            L_0x0099:
                monitor-exit(r7)     // Catch:{ all -> 0x0093 }
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.netReqTPS     // Catch:{ all -> 0x0096 }
                r7 = 0
                r5.remove(r7)     // Catch:{ all -> 0x0096 }
            L_0x00a2:
                monitor-exit(r6)     // Catch:{ all -> 0x0096 }
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.netReqTPS
                r5.add(r4)
                goto L_0x003d
            L_0x00ab:
                r6 = 30
                java.lang.Thread.sleep(r6)     // Catch:{ Throwable -> 0x00b1 }
                goto L_0x003d
            L_0x00b1:
                r5 = move-exception
                goto L_0x003d
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.gui.BitmapProcessor.WorkerThread.doLocalTask():void");
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.mob.tools.gui.BitmapProcessor$ImageReq} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.mob.tools.gui.BitmapProcessor$ImageReq} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void doNetworkTask() throws java.lang.Throwable {
            /*
                r8 = this;
                r4 = 0
                java.util.ArrayList r6 = com.mob.tools.gui.BitmapProcessor.netReqTPS
                monitor-enter(r6)
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.netReqTPS     // Catch:{ all -> 0x005d }
                int r5 = r5.size()     // Catch:{ all -> 0x005d }
                if (r5 <= 0) goto L_0x001d
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.netReqTPS     // Catch:{ all -> 0x005d }
                r7 = 0
                java.lang.Object r5 = r5.remove(r7)     // Catch:{ all -> 0x005d }
                r0 = r5
                com.mob.tools.gui.BitmapProcessor$ImageReq r0 = (com.mob.tools.gui.BitmapProcessor.ImageReq) r0     // Catch:{ all -> 0x005d }
                r4 = r0
            L_0x001d:
                monitor-exit(r6)     // Catch:{ all -> 0x005d }
                if (r4 != 0) goto L_0x003d
                java.util.ArrayList r6 = com.mob.tools.gui.BitmapProcessor.reqList
                monitor-enter(r6)
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x0060 }
                int r5 = r5.size()     // Catch:{ all -> 0x0060 }
                if (r5 <= 0) goto L_0x003c
                java.util.ArrayList r5 = com.mob.tools.gui.BitmapProcessor.reqList     // Catch:{ all -> 0x0060 }
                r7 = 0
                java.lang.Object r5 = r5.remove(r7)     // Catch:{ all -> 0x0060 }
                r0 = r5
                com.mob.tools.gui.BitmapProcessor$ImageReq r0 = (com.mob.tools.gui.BitmapProcessor.ImageReq) r0     // Catch:{ all -> 0x0060 }
                r4 = r0
            L_0x003c:
                monitor-exit(r6)     // Catch:{ all -> 0x0060 }
            L_0x003d:
                if (r4 == 0) goto L_0x006f
                com.mob.tools.gui.CachePool r5 = com.mob.tools.gui.BitmapProcessor.cachePool
                java.lang.String r6 = r4.url
                java.lang.Object r3 = r5.get(r6)
                java.lang.ref.SoftReference r3 = (java.lang.ref.SoftReference) r3
                if (r3 != 0) goto L_0x0063
                r2 = 0
            L_0x0050:
                if (r2 == 0) goto L_0x006b
                r8.curReq = r4
                com.mob.tools.gui.BitmapProcessor$ImageReq r5 = r8.curReq
                com.mob.tools.gui.BitmapProcessor.WorkerThread unused = r5.worker = r8
                r4.throwComplete(r2)
            L_0x005c:
                return
            L_0x005d:
                r5 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x005d }
                throw r5
            L_0x0060:
                r5 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0060 }
                throw r5
            L_0x0063:
                java.lang.Object r5 = r3.get()
                android.graphics.Bitmap r5 = (android.graphics.Bitmap) r5
                r2 = r5
                goto L_0x0050
            L_0x006b:
                r8.doTask(r4)
                goto L_0x005c
            L_0x006f:
                r6 = 30
                java.lang.Thread.sleep(r6)     // Catch:{ Throwable -> 0x0075 }
                goto L_0x005c
            L_0x0075:
                r5 = move-exception
                goto L_0x005c
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.gui.BitmapProcessor.WorkerThread.doNetworkTask():void");
        }

        private void doTask(final ImageReq req) throws Throwable {
            try {
                this.curReq = req;
                WorkerThread unused = this.curReq.worker = this;
                final String md5 = Data.MD5(req.url);
                if (BitmapProcessor.cacheDir == null || !new File(BitmapProcessor.cacheDir, md5).exists()) {
                    new NetworkHelper().rawGet(req.url, (RawNetworkCallback) new RawNetworkCallback() {
                        public void onResponse(InputStream is) throws Throwable {
                            Bitmap bitmap;
                            PatchInputStream pis = new PatchInputStream(is);
                            if (BitmapProcessor.cacheDir != null) {
                                File file = new File(BitmapProcessor.cacheDir, md5);
                                WorkerThread.this.saveFile(pis, file);
                                bitmap = BitmapHelper.getBitmap(file, 1);
                            } else {
                                bitmap = BitmapHelper.getBitmap((InputStream) pis, 1);
                            }
                            if (bitmap == null || bitmap.isRecycled()) {
                                req.throwError();
                            } else {
                                BitmapProcessor.cachePool.put(req.url, new SoftReference(bitmap));
                                req.throwComplete(bitmap);
                            }
                            ImageReq unused = WorkerThread.this.curReq = null;
                        }
                    }, BitmapProcessor.timeout);
                    return;
                }
                Bitmap bm = BitmapHelper.getBitmap(new File(BitmapProcessor.cacheDir, md5).getAbsolutePath());
                if (bm != null) {
                    BitmapProcessor.cachePool.put(req.url, new SoftReference(bm));
                    req.throwComplete(bm);
                } else {
                    req.throwError();
                }
                this.curReq = null;
            } catch (Throwable th) {
                req.throwError();
                this.curReq = null;
            }
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x004b A[Catch:{ all -> 0x0057 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void saveFile(java.io.InputStream r8, java.io.File r9) {
            /*
                r7 = this;
                r0 = 0
                boolean r5 = r9.exists()     // Catch:{ Throwable -> 0x0044 }
                if (r5 == 0) goto L_0x000a
                r9.delete()     // Catch:{ Throwable -> 0x0044 }
            L_0x000a:
                java.io.File r5 = r9.getParentFile()     // Catch:{ Throwable -> 0x0044 }
                boolean r5 = r5.exists()     // Catch:{ Throwable -> 0x0044 }
                if (r5 != 0) goto L_0x001b
                java.io.File r5 = r9.getParentFile()     // Catch:{ Throwable -> 0x0044 }
                r5.mkdirs()     // Catch:{ Throwable -> 0x0044 }
            L_0x001b:
                r9.createNewFile()     // Catch:{ Throwable -> 0x0044 }
                java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0044 }
                r1.<init>(r9)     // Catch:{ Throwable -> 0x0044 }
                r5 = 256(0x100, float:3.59E-43)
                byte[] r4 = new byte[r5]     // Catch:{ Throwable -> 0x0064, all -> 0x0061 }
                int r2 = r8.read(r4)     // Catch:{ Throwable -> 0x0064, all -> 0x0061 }
            L_0x002b:
                if (r2 <= 0) goto L_0x0036
                r5 = 0
                r1.write(r4, r5, r2)     // Catch:{ Throwable -> 0x0064, all -> 0x0061 }
                int r2 = r8.read(r4)     // Catch:{ Throwable -> 0x0064, all -> 0x0061 }
                goto L_0x002b
            L_0x0036:
                r1.flush()     // Catch:{ Throwable -> 0x0064, all -> 0x0061 }
                r1.close()     // Catch:{ Throwable -> 0x0041 }
                r8.close()     // Catch:{ Throwable -> 0x0041 }
                r0 = r1
            L_0x0040:
                return
            L_0x0041:
                r5 = move-exception
                r0 = r1
                goto L_0x0040
            L_0x0044:
                r3 = move-exception
            L_0x0045:
                boolean r5 = r9.exists()     // Catch:{ all -> 0x0057 }
                if (r5 == 0) goto L_0x004e
                r9.delete()     // Catch:{ all -> 0x0057 }
            L_0x004e:
                r0.close()     // Catch:{ Throwable -> 0x0055 }
                r8.close()     // Catch:{ Throwable -> 0x0055 }
                goto L_0x0040
            L_0x0055:
                r5 = move-exception
                goto L_0x0040
            L_0x0057:
                r5 = move-exception
            L_0x0058:
                r0.close()     // Catch:{ Throwable -> 0x005f }
                r8.close()     // Catch:{ Throwable -> 0x005f }
            L_0x005e:
                throw r5
            L_0x005f:
                r6 = move-exception
                goto L_0x005e
            L_0x0061:
                r5 = move-exception
                r0 = r1
                goto L_0x0058
            L_0x0064:
                r3 = move-exception
                r0 = r1
                goto L_0x0045
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.gui.BitmapProcessor.WorkerThread.saveFile(java.io.InputStream, java.io.File):void");
        }

        public void interrupt() {
            try {
                super.interrupt();
            } catch (Throwable th) {
            }
        }
    }

    private static class PatchInputStream extends FilterInputStream {
        InputStream in;

        protected PatchInputStream(InputStream in2) {
            super(in2);
            this.in = in2;
        }

        public long skip(long n) throws IOException {
            long m = 0;
            while (m < n) {
                long mm = this.in.skip(n - m);
                if (mm == 0) {
                    break;
                }
                m += mm;
            }
            return m;
        }
    }

    public static class ImageReq {
        /* access modifiers changed from: private */
        public ArrayList<BitmapCallback> callbacks = new ArrayList<>();
        private long reqTime = System.currentTimeMillis();
        /* access modifiers changed from: private */
        public String url;
        /* access modifiers changed from: private */
        public WorkerThread worker;

        /* access modifiers changed from: private */
        public void throwComplete(Bitmap bitmap) {
            Iterator<BitmapCallback> it = this.callbacks.iterator();
            while (it.hasNext()) {
                it.next().onImageGot(this.url, bitmap);
            }
        }

        /* access modifiers changed from: private */
        public void throwError() {
            Iterator<BitmapCallback> it = this.callbacks.iterator();
            while (it.hasNext()) {
                it.next().onImageGot(this.url, (Bitmap) null);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("url=").append(this.url);
            sb.append("time=").append(this.reqTime);
            sb.append("worker=").append(this.worker.getName()).append(" (").append(this.worker.getId()).append("");
            return sb.toString();
        }
    }
}

package cn.com.bioeasy.app.download;

import android.app.Notification;
import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.event.DownloadFinishEvent;
import cn.com.bioeasy.app.net.HttpHelper;
import java.io.Serializable;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DownloadTask implements Serializable {
    public int current_percent = 0;
    @Inject
    HttpHelper httpHelper;
    public int id;
    public boolean isUnknownLength = false;
    public Notification mNotification;
    public Subscription mSubscription;
    public String mUrl;
    @Inject
    RxBus rxBus;

    public DownloadTask(int id2, String mUrl2) {
        this.id = id2;
        this.mUrl = mUrl2;
    }

    public void start() {
        this.mSubscription = ((DownloadApi) this.httpHelper.getService(DownloadApi.class)).downloadFile(this.mUrl).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Action1<ResponseBody>() {
            public void call(ResponseBody responseBody) {
                DownloadTask.this.rxBus.post(new DownloadFinishEvent(DownloadTask.this, DownloadTask.this.writeResponseBodyToDisk(responseBody, AppUtils.getUrlFileName(DownloadTask.this.mUrl))));
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                DownloadTask.this.rxBus.post(new DownloadFinishEvent(DownloadTask.this, false));
            }
        });
    }

    public void cancel() {
        if (this.mSubscription != null && !this.mSubscription.isUnsubscribed()) {
            this.mSubscription.unsubscribe();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0091 A[Catch:{ IOException -> 0x008b }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0096 A[Catch:{ IOException -> 0x008b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeResponseBodyToDisk(okhttp3.ResponseBody r19, java.lang.String r20) {
        /*
            r18 = this;
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x008b }
            r2.<init>()     // Catch:{ IOException -> 0x008b }
            java.io.File r3 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ IOException -> 0x008b }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ IOException -> 0x008b }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x008b }
            java.lang.String r3 = "/Download/"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x008b }
            java.lang.String r16 = r2.toString()     // Catch:{ IOException -> 0x008b }
            java.io.File r11 = new java.io.File     // Catch:{ IOException -> 0x008b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x008b }
            r2.<init>()     // Catch:{ IOException -> 0x008b }
            r0 = r16
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ IOException -> 0x008b }
            r0 = r20
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ IOException -> 0x008b }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x008b }
            r11.<init>(r2)     // Catch:{ IOException -> 0x008b }
            r12 = 0
            r13 = 0
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r10 = new byte[r2]     // Catch:{ IOException -> 0x009d, all -> 0x008e }
            long r4 = r19.contentLength()     // Catch:{ IOException -> 0x009d, all -> 0x008e }
            r6 = 0
            java.io.InputStream r12 = r19.byteStream()     // Catch:{ IOException -> 0x009d, all -> 0x008e }
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x009d, all -> 0x008e }
            r14.<init>(r11)     // Catch:{ IOException -> 0x009d, all -> 0x008e }
        L_0x004a:
            int r15 = r12.read(r10)     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            r2 = -1
            if (r15 != r2) goto L_0x0060
            r14.flush()     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            r2 = 1
            if (r12 == 0) goto L_0x005a
            r12.close()     // Catch:{ IOException -> 0x008b }
        L_0x005a:
            if (r14 == 0) goto L_0x005f
            r14.close()     // Catch:{ IOException -> 0x008b }
        L_0x005f:
            return r2
        L_0x0060:
            r2 = 0
            r14.write(r10, r2, r15)     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            long r2 = (long) r15     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            long r6 = r6 + r2
            r0 = r18
            cn.com.bioeasy.app.RxBus r0 = r0.rxBus     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            r17 = r0
            cn.com.bioeasy.app.event.DownloadEvent r2 = new cn.com.bioeasy.app.event.DownloadEvent     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            r0 = r18
            java.lang.String r3 = r0.mUrl     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            r8 = r18
            r2.<init>(r3, r4, r6, r8)     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            r0 = r17
            r0.post(r2)     // Catch:{ IOException -> 0x007d, all -> 0x009a }
            goto L_0x004a
        L_0x007d:
            r9 = move-exception
            r13 = r14
        L_0x007f:
            r2 = 0
            if (r12 == 0) goto L_0x0085
            r12.close()     // Catch:{ IOException -> 0x008b }
        L_0x0085:
            if (r13 == 0) goto L_0x005f
            r13.close()     // Catch:{ IOException -> 0x008b }
            goto L_0x005f
        L_0x008b:
            r9 = move-exception
            r2 = 0
            goto L_0x005f
        L_0x008e:
            r2 = move-exception
        L_0x008f:
            if (r12 == 0) goto L_0x0094
            r12.close()     // Catch:{ IOException -> 0x008b }
        L_0x0094:
            if (r13 == 0) goto L_0x0099
            r13.close()     // Catch:{ IOException -> 0x008b }
        L_0x0099:
            throw r2     // Catch:{ IOException -> 0x008b }
        L_0x009a:
            r2 = move-exception
            r13 = r14
            goto L_0x008f
        L_0x009d:
            r9 = move-exception
            goto L_0x007f
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.download.DownloadTask.writeResponseBodyToDisk(okhttp3.ResponseBody, java.lang.String):boolean");
    }
}

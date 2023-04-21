package cn.com.bioeasy.app.download;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.commonlib.R;
import cn.com.bioeasy.app.event.DownloadEvent;
import cn.com.bioeasy.app.event.DownloadFinishEvent;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DownloadService extends Service {
    private static String ACTION = "DOWNLOAD_ACTION";
    private static int ACTION_CANCEL = 1;
    public static String DOWNLOAD_URL = "DOWNLOAD_URL";
    @Inject
    RxBus bus;
    /* access modifiers changed from: private */
    public NotificationManager mNotificationManager;
    /* access modifiers changed from: private */
    public HashMap<String, DownloadTask> mTaskMap = new HashMap<>();
    @Inject
    ToastUtils toastUtils;

    public void onCreate() {
        super.onCreate();
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        this.bus.toObservable(DownloadEvent.class).onBackpressureBuffer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<DownloadEvent>() {
            public void call(DownloadEvent downloadEvent) {
                if (downloadEvent.total != -1) {
                    float percent = (((float) downloadEvent.progress) / ((float) downloadEvent.total)) * 100.0f;
                    if (downloadEvent.task.current_percent != ((int) percent)) {
                        downloadEvent.task.current_percent = (int) percent;
                        downloadEvent.task.mNotification.contentView.setProgressBar(R.id.progressbar_download, 100, (int) percent, false);
                        if (DownloadService.this.mNotificationManager != null) {
                            DownloadService.this.mNotificationManager.notify(downloadEvent.task.id, downloadEvent.task.mNotification);
                        }
                        Log.v("FileDownload", "Process: " + String.valueOf((int) percent));
                    }
                } else if (!downloadEvent.task.isUnknownLength) {
                    downloadEvent.task.isUnknownLength = true;
                    downloadEvent.task.mNotification.contentView.setViewVisibility(R.id.progressbar_download, 4);
                    downloadEvent.task.mNotification.contentView.setViewVisibility(R.id.progressbar_download_unknown, 0);
                    if (DownloadService.this.mNotificationManager != null) {
                        DownloadService.this.mNotificationManager.notify(downloadEvent.task.id, downloadEvent.task.mNotification);
                    }
                }
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        this.bus.toObservable(DownloadFinishEvent.class).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<DownloadFinishEvent>() {
            public void call(DownloadFinishEvent downloadFinishEvent) {
                if (DownloadService.this.mNotificationManager != null) {
                    DownloadService.this.mNotificationManager.cancel(downloadFinishEvent.task.id);
                }
                if (!downloadFinishEvent.isSuccess) {
                    ToastUtils toastUtils = DownloadService.this.toastUtils;
                    ToastUtils.showToast("下载失败");
                }
                DownloadService.this.mTaskMap.remove(downloadFinishEvent.task.mUrl);
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra(ACTION, 0) == ACTION_CANCEL) {
            DownloadTask task = this.mTaskMap.get(intent.getStringExtra(DOWNLOAD_URL));
            if (this.mNotificationManager != null) {
                this.mNotificationManager.cancel(task.id);
            }
            task.cancel();
        } else {
            String url = intent.getStringExtra(DOWNLOAD_URL);
            if (!TextUtils.isEmpty(url)) {
                if (this.mTaskMap.containsKey(url)) {
                    ToastUtils toastUtils2 = this.toastUtils;
                    ToastUtils.showToast("正在下载中...");
                } else {
                    DownloadTask task2 = new DownloadTask(this.mTaskMap.size(), url);
                    this.mTaskMap.put(url, task2);
                    task2.start();
                    createNotification(task2);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        this.mNotificationManager = null;
        cancelAll();
        super.onDestroy();
    }

    private void cancelAll() {
        for (Map.Entry<String, DownloadTask> entry : this.mTaskMap.entrySet()) {
            ((DownloadTask) entry.getValue()).cancel();
        }
    }

    private void createNotification(DownloadTask task) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.lib_layout_download_notification);
        remoteViews.setProgressBar(R.id.progressbar_download, 100, 0, false);
        remoteViews.setTextViewText(R.id.tv_title, "正在下载" + AppUtils.getUrlFileName(task.mUrl));
        Intent cancelIntent = new Intent(this, DownloadService.class);
        cancelIntent.putExtra(ACTION, ACTION_CANCEL);
        cancelIntent.putExtra(DOWNLOAD_URL, task.mUrl);
        remoteViews.setOnClickPendingIntent(R.id.layout_btn_cancel, PendingIntent.getService(this, task.id, cancelIntent, 134217728));
        task.mNotification = new NotificationCompat.Builder(this).setContent(remoteViews).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(false).setPriority(2).setOngoing(true).setTicker("下载中，请稍等...").build();
        this.mNotificationManager.notify(task.id, task.mNotification);
    }
}

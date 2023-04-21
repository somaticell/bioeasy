package cn.com.bioeasy.app.upload;

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
import cn.com.bioeasy.app.commonlib.R;
import cn.com.bioeasy.app.event.UploadEvent;
import cn.com.bioeasy.app.event.UploadEventInternal;
import cn.com.bioeasy.app.event.UploadFinishEvent;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UploadService extends Service {
    private static String ACTION = "UPLOAD_ACTION";
    private static int ACTION_CANCEL = 1;
    private static String UPLOAD_CANCEL_ID = "UPLOAD_CANCEL_ID";
    public static String UPLOAD_FILES = "UPLOAD_FILES";
    public static String UPLOAD_PARAMS = "UPLOAD_PARAMS";
    public static String UPLOAD_URL = "UPLOAD_URL";
    @Inject
    RxBus bus;
    /* access modifiers changed from: private */
    public NotificationManager mNotificationManager;
    /* access modifiers changed from: private */
    public HashMap<Integer, UploadTask> mTaskMap = new HashMap<>();
    @Inject
    ToastUtils toastUtils;

    public void onCreate() {
        super.onCreate();
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        this.bus.toObservable(UploadEventInternal.class).onBackpressureBuffer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<UploadEventInternal>() {
            public void call(UploadEventInternal uploadEvent) {
                uploadEvent.task.progress += uploadEvent.byteCount;
                float percent = (((float) uploadEvent.task.progress) / ((float) uploadEvent.task.total)) * 100.0f;
                if (uploadEvent.task.current_percent != ((int) percent)) {
                    uploadEvent.task.current_percent = (int) percent;
                    uploadEvent.task.mNotification.contentView.setProgressBar(R.id.progressbar_upload, 100, (int) percent, false);
                    if (UploadService.this.mNotificationManager != null) {
                        UploadService.this.mNotificationManager.notify(uploadEvent.task.id, uploadEvent.task.mNotification);
                    }
                    Log.v("FileUpload", "Process: " + String.valueOf((int) percent));
                    UploadService.this.bus.post(new UploadEvent(uploadEvent.task.total, uploadEvent.task.progress, uploadEvent.task));
                    if (((int) percent) == 100) {
                        UploadService.this.bus.post(new UploadFinishEvent(uploadEvent.task, true));
                    }
                }
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        this.bus.toObservable(UploadFinishEvent.class).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<UploadFinishEvent>() {
            public void call(UploadFinishEvent uploadFinishEvent) {
                if (UploadService.this.mNotificationManager != null) {
                    UploadService.this.mNotificationManager.cancel(uploadFinishEvent.task.id);
                }
                if (!uploadFinishEvent.isSuccess) {
                    ToastUtils toastUtils = UploadService.this.toastUtils;
                    ToastUtils.showToast("上传失败");
                }
                UploadService.this.mTaskMap.remove(Integer.valueOf(uploadFinishEvent.task.id));
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra(ACTION, 0) == ACTION_CANCEL) {
            UploadTask task = this.mTaskMap.get(Integer.valueOf(intent.getIntExtra(UPLOAD_CANCEL_ID, -1)));
            if (task != null) {
                if (this.mNotificationManager != null) {
                    this.mNotificationManager.cancel(task.id);
                }
                task.cancel();
            }
        } else {
            String url = intent.getStringExtra(UPLOAD_URL);
            List<UploadParam> fileList = intent.getParcelableArrayListExtra(UPLOAD_FILES);
            List<UploadParam> paramList = intent.getParcelableArrayListExtra(UPLOAD_PARAMS);
            if (!TextUtils.isEmpty(url) && fileList != null && fileList.size() > 0) {
                long total = 0;
                HashMap<String, File> fileMap = new HashMap<>();
                for (UploadParam param : fileList) {
                    File file = new File(param.value);
                    total += file.length();
                    fileMap.put(param.key, file);
                }
                HashMap<String, String> paramMap = new HashMap<>();
                if (paramList != null) {
                    for (UploadParam param2 : paramList) {
                        paramMap.put(param2.key, param2.value);
                    }
                }
                UploadTask task2 = new UploadTask(this.mTaskMap.size(), url, fileMap, paramMap);
                task2.total = total;
                this.mTaskMap.put(Integer.valueOf(this.mTaskMap.size()), task2);
                task2.start();
                createNotification(task2);
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
        for (Map.Entry<Integer, UploadTask> entry : this.mTaskMap.entrySet()) {
            ((UploadTask) entry.getValue()).cancel();
        }
    }

    private void createNotification(UploadTask task) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.lib_layout_upload_notification);
        remoteViews.setProgressBar(R.id.progressbar_upload, 100, 0, false);
        remoteViews.setTextViewText(R.id.tv_title, "正在上传" + task.mFileMap.size() + "个文件");
        Intent cancelIntent = new Intent(this, UploadService.class);
        cancelIntent.putExtra(ACTION, ACTION_CANCEL);
        cancelIntent.putExtra(UPLOAD_CANCEL_ID, task.id);
        remoteViews.setOnClickPendingIntent(R.id.layout_btn_cancel, PendingIntent.getService(this, task.id, cancelIntent, 134217728));
        task.mNotification = new NotificationCompat.Builder(this).setContent(remoteViews).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(false).setPriority(2).setOngoing(true).setTicker("上传中，请稍等...").build();
        this.mNotificationManager.notify(task.id, task.mNotification);
    }
}

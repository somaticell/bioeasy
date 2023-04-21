package cn.com.bioeasy.app.upload;

import android.app.Notification;
import android.util.Log;
import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.event.UploadFinishEvent;
import cn.com.bioeasy.app.file.FileTypeUtil;
import cn.com.bioeasy.app.net.HttpHelper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UploadTask {
    @Inject
    RxBus bus;
    public int current_percent = 0;
    @Inject
    HttpHelper httpHelper;
    public int id;
    public HashMap<String, File> mFileMap;
    public Notification mNotification;
    public HashMap<String, String> mParamMap;
    public Subscription mSubscription;
    public String mUrl;
    public long progress = 0;
    public long total = 0;

    public UploadTask(int id2, String mUrl2, HashMap<String, File> map, HashMap<String, String> paramMap) {
        this.id = id2;
        this.mUrl = mUrl2;
        this.mFileMap = map;
        this.mParamMap = paramMap;
    }

    public void start() {
        Map<String, RequestBody> files = new HashMap<>();
        for (Map.Entry entry : this.mFileMap.entrySet()) {
            File file = (File) entry.getValue();
            RequestBody fileBody = RequestBody.create(MediaType.parse(getContentType(file)), file);
            files.put("" + ((String) entry.getKey()) + "\"; filename=\"" + AppUtils.getUrlFileName(file.getAbsolutePath()) + "", new UploadRequestBody(fileBody, this));
        }
        for (Map.Entry entry2 : this.mParamMap.entrySet()) {
            files.put((String) entry2.getKey(), RequestBody.create(MediaType.parse("text/plain"), (String) entry2.getValue()));
        }
        this.mSubscription = ((UploadApi) this.httpHelper.getService(UploadApi.class)).uploadFile(this.mUrl, files).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            public void call(ResponseBody responseBody) {
                try {
                    Log.v("FileUpload", responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                UploadTask.this.bus.post(new UploadFinishEvent(UploadTask.this, false));
            }
        });
    }

    public void cancel() {
        if (this.mSubscription != null && !this.mSubscription.isUnsubscribed()) {
            this.mSubscription.unsubscribe();
        }
    }

    private String getContentType(File f) {
        String fileType = FileTypeUtil.getFileType(f.getAbsolutePath());
        if (fileType == null || fileType.equals("")) {
            return "application/octet-stream";
        }
        return "image/" + fileType;
    }
}

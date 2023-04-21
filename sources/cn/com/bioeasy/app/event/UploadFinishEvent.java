package cn.com.bioeasy.app.event;

import cn.com.bioeasy.app.upload.UploadTask;

public class UploadFinishEvent {
    public boolean isSuccess;
    public UploadTask task;

    public UploadFinishEvent(UploadTask task2, boolean isSuccess2) {
        this.task = task2;
        this.isSuccess = isSuccess2;
    }
}

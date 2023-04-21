package cn.com.bioeasy.app.event;

import cn.com.bioeasy.app.upload.UploadTask;

public class UploadEvent {
    public long progress;
    public UploadTask task;
    public long total;

    public UploadEvent(long total2, long progress2, UploadTask task2) {
        this.total = total2;
        this.progress = progress2;
        this.task = task2;
    }
}

package cn.com.bioeasy.app.event;

import cn.com.bioeasy.app.upload.UploadTask;

public class UploadEventInternal {
    public long byteCount;
    public UploadTask task;

    public UploadEventInternal(long byteCount2, UploadTask task2) {
        this.byteCount = byteCount2;
        this.task = task2;
    }
}

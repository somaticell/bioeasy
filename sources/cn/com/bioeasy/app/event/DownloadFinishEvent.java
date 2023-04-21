package cn.com.bioeasy.app.event;

import cn.com.bioeasy.app.download.DownloadTask;

public class DownloadFinishEvent {
    public boolean isSuccess;
    public DownloadTask task;

    public DownloadFinishEvent(DownloadTask task2, boolean isSuccess2) {
        this.task = task2;
        this.isSuccess = isSuccess2;
    }
}

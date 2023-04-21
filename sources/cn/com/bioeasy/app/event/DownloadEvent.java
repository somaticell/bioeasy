package cn.com.bioeasy.app.event;

import cn.com.bioeasy.app.download.DownloadTask;

public class DownloadEvent {
    public long progress;
    public DownloadTask task;
    public long total;
    public String url;

    public DownloadEvent(String url2, long total2, long progress2, DownloadTask task2) {
        this.url = url2;
        this.total = total2;
        this.progress = progress2;
        this.task = task2;
    }
}

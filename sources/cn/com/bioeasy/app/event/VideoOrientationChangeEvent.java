package cn.com.bioeasy.app.event;

import android.content.res.Configuration;

public class VideoOrientationChangeEvent {
    public Configuration newConfig;

    public VideoOrientationChangeEvent(Configuration newConfig2) {
        this.newConfig = newConfig2;
    }
}

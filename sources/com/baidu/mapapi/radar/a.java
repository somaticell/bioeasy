package com.baidu.mapapi.radar;

import android.os.Handler;
import android.os.Message;

class a extends Handler {
    final /* synthetic */ RadarSearchManager a;

    a(RadarSearchManager radarSearchManager) {
        this.a = radarSearchManager;
    }

    public void handleMessage(Message message) {
        if (RadarSearchManager.a != null) {
            switch (message.what) {
                case 1:
                    if (this.a.g != null) {
                        RadarUploadInfo onUploadInfoCallback = this.a.g.onUploadInfoCallback();
                        if (onUploadInfoCallback != null) {
                            RadarUploadInfo unused = this.a.h = onUploadInfoCallback;
                        }
                        boolean unused2 = this.a.a(onUploadInfoCallback);
                        break;
                    }
                    break;
            }
            super.handleMessage(message);
        }
    }
}

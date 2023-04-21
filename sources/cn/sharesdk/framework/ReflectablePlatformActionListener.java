package cn.sharesdk.framework;

import android.os.Handler;
import android.os.Message;
import com.mob.tools.utils.UIHandler;
import java.util.HashMap;

public class ReflectablePlatformActionListener implements PlatformActionListener {
    private int a;
    private Handler.Callback b;
    private int c;
    private Handler.Callback d;
    private int e;
    private Handler.Callback f;

    public void setOnCompleteCallback(int what, Handler.Callback callback) {
        this.a = what;
        this.b = callback;
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (this.b != null) {
            Message message = new Message();
            message.what = this.a;
            message.obj = new Object[]{platform, Integer.valueOf(action), res};
            UIHandler.sendMessage(message, this.b);
        }
    }

    public void setOnErrorCallback(int what, Handler.Callback callback) {
        this.c = what;
        this.d = callback;
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (this.d != null) {
            Message message = new Message();
            message.what = this.c;
            message.obj = new Object[]{platform, Integer.valueOf(action), t};
            UIHandler.sendMessage(message, this.d);
        }
    }

    public void setOnCancelCallback(int what, Handler.Callback callback) {
        this.e = what;
        this.f = callback;
    }

    public void onCancel(Platform platform, int action) {
        if (this.f != null) {
            Message message = new Message();
            message.what = this.e;
            message.obj = new Object[]{platform, Integer.valueOf(action)};
            UIHandler.sendMessage(message, this.f);
        }
    }
}

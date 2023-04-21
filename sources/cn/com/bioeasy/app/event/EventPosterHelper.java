package cn.com.bioeasy.app.event;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class EventPosterHelper {
    /* access modifiers changed from: private */
    public final Bus mBus;

    @Inject
    public EventPosterHelper(Bus bus) {
        this.mBus = bus;
    }

    public Bus getBus() {
        return this.mBus;
    }

    public void postEventSafely(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                EventPosterHelper.this.mBus.post(event);
            }
        });
    }
}

package cn.com.bioeasy.app.base;

import android.os.Bundle;
import cn.com.bioeasy.app.base.BasePresenter;
import icepick.Bundler;
import icepick.Injector;
import java.util.HashMap;
import java.util.Map;

public class BasePresenter$$Icepick<T extends BasePresenter> extends Injector.Object<T> {
    private static final Map<String, Bundler<?>> BUNDLERS = new HashMap();
    private static final Injector.Helper H = new Injector.Helper("cn.com.bioeasy.app.base.BasePresenter$$Icepick.", BUNDLERS);

    public void restore(T target, Bundle state) {
        if (state != null) {
            target.baseMessage = H.getString(state, "baseMessage");
            super.restore(target, state);
        }
    }

    public void save(T target, Bundle state) {
        super.save(target, state);
        H.putString(state, "baseMessage", target.baseMessage);
    }
}

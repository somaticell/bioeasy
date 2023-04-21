package cn.com.bioeasy.app.widgets.wheel.adapters;

import android.content.Context;
import cn.com.bioeasy.app.widgets.wheel.WheelAdapter;

public class AdapterWheel extends AbstractWheelTextAdapter {
    private WheelAdapter adapter;

    public AdapterWheel(Context context, WheelAdapter adapter2) {
        super(context);
        this.adapter = adapter2;
    }

    public WheelAdapter getAdapter() {
        return this.adapter;
    }

    public int getItemsCount() {
        return this.adapter.getItemsCount();
    }

    /* access modifiers changed from: protected */
    public CharSequence getItemText(int index) {
        return this.adapter.getItem(index);
    }
}

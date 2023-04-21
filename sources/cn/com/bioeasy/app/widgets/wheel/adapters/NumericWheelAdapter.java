package cn.com.bioeasy.app.widgets.wheel.adapters;

import android.content.Context;

public class NumericWheelAdapter extends AbstractWheelTextAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private String format;
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter(Context context) {
        this(context, 0, 9);
    }

    public NumericWheelAdapter(Context context, int minValue2, int maxValue2) {
        this(context, minValue2, maxValue2, (String) null);
    }

    public NumericWheelAdapter(Context context, int minValue2, int maxValue2, String format2) {
        super(context);
        this.minValue = minValue2;
        this.maxValue = maxValue2;
        this.format = format2;
    }

    public CharSequence getItemText(int index) {
        if (index < 0 || index >= getItemsCount()) {
            return null;
        }
        int value = this.minValue + index;
        if (this.format == null) {
            return Integer.toString(value);
        }
        return String.format(this.format, new Object[]{Integer.valueOf(value)});
    }

    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }
}

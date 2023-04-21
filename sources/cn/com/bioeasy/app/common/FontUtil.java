package cn.com.bioeasy.app.common;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;

public class FontUtil {
    public static CharSequence addColor(String colorCode, String text) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor(colorCode)), 0, text.length(), 33);
        return spanString;
    }

    public static CharSequence addColor(int color, String text) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new ForegroundColorSpan(color), 0, text.length(), 33);
        return spanString;
    }

    public static CharSequence resize(float relativeSice, String text) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new RelativeSizeSpan(relativeSice), 0, text.length(), 33);
        return spanString;
    }

    public static CharSequence scaleX(float relativeXSice, String text) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new ScaleXSpan(relativeXSice), 0, text.length(), 33);
        return spanString;
    }
}

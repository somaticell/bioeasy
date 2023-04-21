package com.jude.rollviewpager.hintview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.jude.rollviewpager.Util;

public class ColorPointHintView extends ShapeHintView {
    private int focusColor;
    private int normalColor;

    public ColorPointHintView(Context context, int focusColor2, int normalColor2) {
        super(context);
        this.focusColor = focusColor2;
        this.normalColor = normalColor2;
    }

    public Drawable makeFocusDrawable() {
        GradientDrawable dot_focus = new GradientDrawable();
        dot_focus.setColor(this.focusColor);
        dot_focus.setCornerRadius((float) Util.dip2px(getContext(), 4.0f));
        dot_focus.setSize(Util.dip2px(getContext(), 8.0f), Util.dip2px(getContext(), 8.0f));
        return dot_focus;
    }

    public Drawable makeNormalDrawable() {
        GradientDrawable dot_normal = new GradientDrawable();
        dot_normal.setColor(this.normalColor);
        dot_normal.setCornerRadius((float) Util.dip2px(getContext(), 4.0f));
        dot_normal.setSize(Util.dip2px(getContext(), 8.0f), Util.dip2px(getContext(), 8.0f));
        return dot_normal;
    }
}

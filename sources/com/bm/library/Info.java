package com.bm.library;

import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageView;

public class Info {
    RectF mBaseRect = new RectF();
    float mDegrees;
    RectF mImgRect = new RectF();
    RectF mRect = new RectF();
    float mScale;
    ImageView.ScaleType mScaleType;
    PointF mScreenCenter = new PointF();
    RectF mWidgetRect = new RectF();

    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        this.mRect.set(rect);
        this.mImgRect.set(img);
        this.mWidgetRect.set(widget);
        this.mScale = scale;
        this.mScaleType = scaleType;
        this.mDegrees = degrees;
        this.mBaseRect.set(base);
        this.mScreenCenter.set(screenCenter);
    }
}

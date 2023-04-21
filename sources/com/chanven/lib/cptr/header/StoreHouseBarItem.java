package com.chanven.lib.cptr.header;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.util.Random;

public class StoreHouseBarItem extends Animation {
    public int index;
    private PointF mCEndPoint;
    private PointF mCStartPoint;
    private float mFromAlpha = 1.0f;
    private final Paint mPaint = new Paint();
    private float mToAlpha = 0.4f;
    public PointF midPoint;
    public float translationX;

    public StoreHouseBarItem(int index2, PointF start, PointF end, int color, int lineWidth) {
        this.index = index2;
        this.midPoint = new PointF((start.x + end.x) / 2.0f, (start.y + end.y) / 2.0f);
        this.mCStartPoint = new PointF(start.x - this.midPoint.x, start.y - this.midPoint.y);
        this.mCEndPoint = new PointF(end.x - this.midPoint.x, end.y - this.midPoint.y);
        setColor(color);
        setLineWidth(lineWidth);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setLineWidth(int width) {
        this.mPaint.setStrokeWidth((float) width);
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void resetPosition(int horizontalRandomness) {
        this.translationX = (float) ((-new Random().nextInt(horizontalRandomness)) + horizontalRandomness);
    }

    /* access modifiers changed from: protected */
    public void applyTransformation(float interpolatedTime, Transformation t) {
        float alpha = this.mFromAlpha;
        setAlpha(alpha + ((this.mToAlpha - alpha) * interpolatedTime));
    }

    public void start(float fromAlpha, float toAlpha) {
        this.mFromAlpha = fromAlpha;
        this.mToAlpha = toAlpha;
        super.start();
    }

    public void setAlpha(float alpha) {
        this.mPaint.setAlpha((int) (255.0f * alpha));
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(this.mCStartPoint.x, this.mCStartPoint.y, this.mCEndPoint.x, this.mCEndPoint.y, this.mPaint);
    }
}

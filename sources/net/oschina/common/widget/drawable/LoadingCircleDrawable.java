package net.oschina.common.widget.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class LoadingCircleDrawable extends LoadingDrawable {
    private static final int ANGLE_ADD = 5;
    private static int DEFAULT_SIZE = 56;
    private static final int MAX_ANGLE_SWEEP = 255;
    private static final int MIN_ANGLE_SWEEP = 3;
    private int mAngleIncrement = -3;
    private int mMaxSize = DEFAULT_SIZE;
    private int mMinSize = DEFAULT_SIZE;
    private RectF mOval = new RectF();
    private float mStartAngle = 0.0f;
    private float mSweepAngle = 0.0f;

    public LoadingCircleDrawable() {
        this.mForegroundPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public LoadingCircleDrawable(int minSize, int maxSize) {
        this.mMinSize = minSize;
        this.mMaxSize = maxSize;
    }

    public int getIntrinsicHeight() {
        return Math.min(this.mMaxSize, Math.max((int) ((2.0f * Math.max(this.mBackgroundPaint.getStrokeWidth(), this.mForegroundPaint.getStrokeWidth())) + 10.0f), this.mMinSize));
    }

    public int getIntrinsicWidth() {
        return Math.min(this.mMaxSize, Math.max((int) ((2.0f * Math.max(this.mBackgroundPaint.getStrokeWidth(), this.mForegroundPaint.getStrokeWidth())) + 10.0f), this.mMinSize));
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (bounds.left != 0 || bounds.top != 0 || bounds.right != 0 || bounds.bottom != 0) {
            int centerX = bounds.centerX();
            int centerY = bounds.centerY();
            int areRadius = (Math.min(bounds.height(), bounds.width()) >> 1) - ((((int) Math.max(getForegroundLineSize(), getBackgroundLineSize())) >> 1) + 1);
            this.mOval.set((float) (centerX - areRadius), (float) (centerY - areRadius), (float) (centerX + areRadius), (float) (centerY + areRadius));
        }
    }

    /* access modifiers changed from: protected */
    public void onProgressChange(float progress) {
        this.mStartAngle = 0.0f;
        this.mSweepAngle = 360.0f * progress;
    }

    /* access modifiers changed from: protected */
    public void onRefresh() {
        this.mStartAngle += 5.0f;
        if (this.mStartAngle > 360.0f) {
            this.mStartAngle -= 360.0f;
        }
        if (this.mSweepAngle > 255.0f) {
            this.mAngleIncrement = -this.mAngleIncrement;
        } else if (this.mSweepAngle < 3.0f) {
            this.mSweepAngle = 3.0f;
            return;
        } else if (this.mSweepAngle == 3.0f) {
            this.mAngleIncrement = -this.mAngleIncrement;
            getNextForegroundColor();
        }
        this.mSweepAngle += (float) this.mAngleIncrement;
    }

    /* access modifiers changed from: protected */
    public void drawBackground(Canvas canvas, Paint backgroundPaint) {
        canvas.drawArc(this.mOval, 0.0f, 360.0f, false, backgroundPaint);
    }

    /* access modifiers changed from: protected */
    public void drawForeground(Canvas canvas, Paint foregroundPaint) {
        canvas.drawArc(this.mOval, this.mStartAngle, -this.mSweepAngle, false, foregroundPaint);
    }
}

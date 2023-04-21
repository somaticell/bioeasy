package com.facebook.imagepipeline.animated.impl;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableDiagnostics;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;

public class AnimatedDrawableDiagnosticsImpl implements AnimatedDrawableDiagnostics {
    private static final Class<?> TAG = AnimatedDrawableDiagnostics.class;
    private AnimatedDrawableCachingBackend mAnimatedDrawableBackend;
    private final AnimatedDrawableUtil mAnimatedDrawableUtil;
    private final TextPaint mDebugTextPaint = new TextPaint();
    private final DisplayMetrics mDisplayMetrics;
    private final RollingStat mDrawnFrames = new RollingStat();
    private final RollingStat mDroppedFramesStat = new RollingStat();
    private long mLastTimeStamp;
    private final StringBuilder sbTemp = new StringBuilder();

    public AnimatedDrawableDiagnosticsImpl(AnimatedDrawableUtil animatedDrawableUtil, DisplayMetrics displayMetrics) {
        this.mAnimatedDrawableUtil = animatedDrawableUtil;
        this.mDisplayMetrics = displayMetrics;
        this.mDebugTextPaint.setColor(-16776961);
        this.mDebugTextPaint.setTextSize((float) convertDpToPx(14));
    }

    public void setBackend(AnimatedDrawableCachingBackend animatedDrawableBackend) {
        this.mAnimatedDrawableBackend = animatedDrawableBackend;
    }

    public void onStartMethodBegin() {
        this.mLastTimeStamp = SystemClock.elapsedRealtime();
    }

    public void onStartMethodEnd() {
        long elapsedMs = SystemClock.elapsedRealtime() - this.mLastTimeStamp;
        if (elapsedMs > 3) {
            FLog.v(TAG, "onStart took %d", (Object) Long.valueOf(elapsedMs));
        }
    }

    public void onNextFrameMethodBegin() {
        this.mLastTimeStamp = SystemClock.elapsedRealtime();
    }

    public void onNextFrameMethodEnd() {
        long elapsedMs = SystemClock.elapsedRealtime() - this.mLastTimeStamp;
        if (elapsedMs > 3) {
            FLog.v(TAG, "onNextFrame took %d", (Object) Long.valueOf(elapsedMs));
        }
    }

    public void incrementDroppedFrames(int droppedFrames) {
        this.mDroppedFramesStat.incrementStats(droppedFrames);
        if (droppedFrames > 0) {
            FLog.v(TAG, "Dropped %d frames", (Object) Integer.valueOf(droppedFrames));
        }
    }

    public void incrementDrawnFrames(int drawnFrames) {
        this.mDrawnFrames.incrementStats(drawnFrames);
    }

    public void onDrawMethodBegin() {
        this.mLastTimeStamp = SystemClock.elapsedRealtime();
    }

    public void onDrawMethodEnd() {
        FLog.v(TAG, "draw took %d", (Object) Long.valueOf(SystemClock.elapsedRealtime() - this.mLastTimeStamp));
    }

    public void drawDebugOverlay(Canvas canvas, Rect destRect) {
        int droppedFrame10 = this.mDroppedFramesStat.getSum(10);
        int drawnFrames10 = this.mDrawnFrames.getSum(10);
        int totalFrames = drawnFrames10 + droppedFrame10;
        int leftMargin = convertDpToPx(10);
        int x = leftMargin;
        int y = convertDpToPx(20);
        int spacingBetweenTextPx = convertDpToPx(5);
        if (totalFrames > 0) {
            this.sbTemp.setLength(0);
            this.sbTemp.append((drawnFrames10 * 100) / totalFrames);
            this.sbTemp.append("%");
            canvas.drawText(this.sbTemp, 0, this.sbTemp.length(), (float) x, (float) y, this.mDebugTextPaint);
            x = ((int) (((float) x) + this.mDebugTextPaint.measureText(this.sbTemp, 0, this.sbTemp.length()))) + spacingBetweenTextPx;
        }
        int bytesUsed = this.mAnimatedDrawableBackend.getMemoryUsage();
        this.sbTemp.setLength(0);
        this.mAnimatedDrawableUtil.appendMemoryString(this.sbTemp, bytesUsed);
        float textWidth = this.mDebugTextPaint.measureText(this.sbTemp, 0, this.sbTemp.length());
        if (((float) x) + textWidth > ((float) destRect.width())) {
            x = leftMargin;
            y = (int) (((float) y) + this.mDebugTextPaint.getTextSize() + ((float) spacingBetweenTextPx));
        }
        canvas.drawText(this.sbTemp, 0, this.sbTemp.length(), (float) x, (float) y, this.mDebugTextPaint);
        int x2 = ((int) (((float) x) + textWidth)) + spacingBetweenTextPx;
        this.sbTemp.setLength(0);
        this.mAnimatedDrawableBackend.appendDebugOptionString(this.sbTemp);
        if (((float) x2) + this.mDebugTextPaint.measureText(this.sbTemp, 0, this.sbTemp.length()) > ((float) destRect.width())) {
            x2 = leftMargin;
            y = (int) (((float) y) + this.mDebugTextPaint.getTextSize() + ((float) spacingBetweenTextPx));
        }
        canvas.drawText(this.sbTemp, 0, this.sbTemp.length(), (float) x2, (float) y, this.mDebugTextPaint);
    }

    private int convertDpToPx(int dips) {
        return (int) TypedValue.applyDimension(1, (float) dips, this.mDisplayMetrics);
    }
}

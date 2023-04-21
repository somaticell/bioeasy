package com.facebook.imagepipeline.animated.impl;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableDiagnostics;

public class AnimatedDrawableDiagnosticsNoop implements AnimatedDrawableDiagnostics {
    private static AnimatedDrawableDiagnosticsNoop sInstance = new AnimatedDrawableDiagnosticsNoop();

    public static AnimatedDrawableDiagnosticsNoop getInstance() {
        return sInstance;
    }

    public void setBackend(AnimatedDrawableCachingBackend animatedDrawableBackend) {
    }

    public void onStartMethodBegin() {
    }

    public void onStartMethodEnd() {
    }

    public void onNextFrameMethodBegin() {
    }

    public void onNextFrameMethodEnd() {
    }

    public void incrementDroppedFrames(int droppedFrames) {
    }

    public void incrementDrawnFrames(int drawnFrames) {
    }

    public void onDrawMethodBegin() {
    }

    public void onDrawMethodEnd() {
    }

    public void drawDebugOverlay(Canvas canvas, Rect destRect) {
    }
}

package com.bm.library;

import android.view.MotionEvent;

public class RotateGestureDetector {
    private static final int MAX_DEGREES_STEP = 120;
    private float mCurrSlope;
    private OnRotateListener mListener;
    private float mPrevSlope;
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    public RotateGestureDetector(OnRotateListener l) {
        this.mListener = l;
    }

    public void onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 2:
                if (event.getPointerCount() > 1) {
                    this.mCurrSlope = caculateSlope(event);
                    double deltaSlope = Math.toDegrees(Math.atan((double) this.mCurrSlope)) - Math.toDegrees(Math.atan((double) this.mPrevSlope));
                    if (Math.abs(deltaSlope) <= 120.0d) {
                        this.mListener.onRotate((float) deltaSlope, (this.x2 + this.x1) / 2.0f, (this.y2 + this.y1) / 2.0f);
                    }
                    this.mPrevSlope = this.mCurrSlope;
                    return;
                }
                return;
            case 5:
            case 6:
                if (event.getPointerCount() == 2) {
                    this.mPrevSlope = caculateSlope(event);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private float caculateSlope(MotionEvent event) {
        this.x1 = event.getX(0);
        this.y1 = event.getY(0);
        this.x2 = event.getX(1);
        this.y2 = event.getY(1);
        return (this.y2 - this.y1) / (this.x2 - this.x1);
    }
}

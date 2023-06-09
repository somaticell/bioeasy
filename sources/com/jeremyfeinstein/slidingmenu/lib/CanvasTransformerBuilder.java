package com.jeremyfeinstein.slidingmenu.lib;

import android.graphics.Canvas;
import android.view.animation.Interpolator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class CanvasTransformerBuilder {
    private static Interpolator lin = new Interpolator() {
        public float getInterpolation(float t) {
            return t;
        }
    };
    /* access modifiers changed from: private */
    public SlidingMenu.CanvasTransformer mTrans;

    private void initTransformer() {
        if (this.mTrans == null) {
            this.mTrans = new SlidingMenu.CanvasTransformer() {
                public void transformCanvas(Canvas canvas, float percentOpen) {
                }
            };
        }
    }

    public SlidingMenu.CanvasTransformer zoom(int openedX, int closedX, int openedY, int closedY, int px, int py) {
        return zoom(openedX, closedX, openedY, closedY, px, py, lin);
    }

    public SlidingMenu.CanvasTransformer zoom(int openedX, int closedX, int openedY, int closedY, int px, int py, Interpolator interp) {
        initTransformer();
        final Interpolator interpolator = interp;
        final int i = openedX;
        final int i2 = closedX;
        final int i3 = openedY;
        final int i4 = closedY;
        final int i5 = px;
        final int i6 = py;
        this.mTrans = new SlidingMenu.CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, percentOpen);
                float f = interpolator.getInterpolation(percentOpen);
                canvas.scale((((float) (i - i2)) * f) + ((float) i2), (((float) (i3 - i4)) * f) + ((float) i4), (float) i5, (float) i6);
            }
        };
        return this.mTrans;
    }

    public SlidingMenu.CanvasTransformer rotate(int openedDeg, int closedDeg, int px, int py) {
        return rotate(openedDeg, closedDeg, px, py, lin);
    }

    public SlidingMenu.CanvasTransformer rotate(int openedDeg, int closedDeg, int px, int py, Interpolator interp) {
        initTransformer();
        final Interpolator interpolator = interp;
        final int i = openedDeg;
        final int i2 = closedDeg;
        final int i3 = px;
        final int i4 = py;
        this.mTrans = new SlidingMenu.CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, percentOpen);
                canvas.rotate((((float) (i - i2)) * interpolator.getInterpolation(percentOpen)) + ((float) i2), (float) i3, (float) i4);
            }
        };
        return this.mTrans;
    }

    public SlidingMenu.CanvasTransformer translate(int openedX, int closedX, int openedY, int closedY) {
        return translate(openedX, closedX, openedY, closedY, lin);
    }

    public SlidingMenu.CanvasTransformer translate(int openedX, int closedX, int openedY, int closedY, Interpolator interp) {
        initTransformer();
        final Interpolator interpolator = interp;
        final int i = openedX;
        final int i2 = closedX;
        final int i3 = openedY;
        final int i4 = closedY;
        this.mTrans = new SlidingMenu.CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, percentOpen);
                float f = interpolator.getInterpolation(percentOpen);
                canvas.translate((((float) (i - i2)) * f) + ((float) i2), (((float) (i3 - i4)) * f) + ((float) i4));
            }
        };
        return this.mTrans;
    }

    public SlidingMenu.CanvasTransformer concatTransformer(final SlidingMenu.CanvasTransformer t) {
        initTransformer();
        this.mTrans = new SlidingMenu.CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float percentOpen) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, percentOpen);
                t.transformCanvas(canvas, percentOpen);
            }
        };
        return this.mTrans;
    }
}

package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private float SCALE_MAX;
    /* access modifiers changed from: private */
    public float SCALE_MID;
    /* access modifiers changed from: private */
    public boolean isAutoScale;
    private boolean isCanDrag;
    private boolean isFirst;
    private boolean isInit;
    private int lastPointerCount;
    private int mCropHeight;
    private int mCropWidth;
    private GestureDetector mGestureDetector;
    private float mLastX;
    private float mLastY;
    private final float[] mMatrixValues;
    private int mOffset;
    /* access modifiers changed from: private */
    public float mScale;
    private ScaleGestureDetector mScaleGestureDetector;
    /* access modifiers changed from: private */
    public Matrix mScaleMatrix;
    private int mVOffset;

    public ZoomImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOffset = 0;
        this.mVOffset = 0;
        this.SCALE_MAX = 4.0f;
        this.SCALE_MID = 2.0f;
        this.mScale = 1.0f;
        this.isFirst = true;
        this.mMatrixValues = new float[9];
        this.mScaleGestureDetector = null;
        this.mScaleMatrix = new Matrix();
        setScaleType(ImageView.ScaleType.MATRIX);
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            public boolean onDoubleTap(MotionEvent e) {
                if (!ZoomImageView.this.isAutoScale) {
                    float x = e.getX();
                    float y = e.getY();
                    if (ZoomImageView.this.getScale() < ZoomImageView.this.SCALE_MID) {
                        ZoomImageView.this.postDelayed(new ScaleRunnable(ZoomImageView.this.SCALE_MID, x, y), 16);
                        boolean unused = ZoomImageView.this.isAutoScale = true;
                    } else {
                        ZoomImageView.this.postDelayed(new ScaleRunnable(ZoomImageView.this.mScale, x, y), 16);
                        boolean unused2 = ZoomImageView.this.isAutoScale = true;
                    }
                }
                return true;
            }
        });
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
    }

    private class ScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mScale;
        private float mTargetScale;
        private float x;
        private float y;

        ScaleRunnable(float targetScale, float x2, float y2) {
            this.mTargetScale = targetScale;
            this.x = x2;
            this.y = y2;
            if (ZoomImageView.this.getScale() < this.mTargetScale) {
                this.mScale = BIGGER;
            } else {
                this.mScale = SMALLER;
            }
        }

        public void run() {
            ZoomImageView.this.mScaleMatrix.postScale(this.mScale, this.mScale, this.x, this.y);
            ZoomImageView.this.checkBorder();
            ZoomImageView.this.setImageMatrix(ZoomImageView.this.mScaleMatrix);
            float currentScale = ZoomImageView.this.getScale();
            if ((this.mScale <= 1.0f || currentScale >= this.mTargetScale) && (this.mScale >= 1.0f || this.mTargetScale >= currentScale)) {
                float deltaScale = this.mTargetScale / currentScale;
                ZoomImageView.this.mScaleMatrix.postScale(deltaScale, deltaScale, this.x, this.y);
                ZoomImageView.this.checkBorder();
                ZoomImageView.this.setImageMatrix(ZoomImageView.this.mScaleMatrix);
                boolean unused = ZoomImageView.this.isAutoScale = false;
                return;
            }
            ZoomImageView.this.postDelayed(this, 16);
        }
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() != null && ((scale < this.SCALE_MAX && scaleFactor > 1.0f) || (scale > this.mScale && scaleFactor < 1.0f))) {
            if (scaleFactor * scale < this.mScale) {
                scaleFactor = this.mScale / scale;
            }
            if (scaleFactor * scale > this.SCALE_MAX) {
                scaleFactor = this.SCALE_MAX / scale;
            }
            this.mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorder();
            setImageMatrix(this.mScaleMatrix);
        }
        return true;
    }

    private RectF getMatrixRectF() {
        Matrix matrix = this.mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rect.set(0.0f, 0.0f, (float) d.getIntrinsicWidth(), (float) d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (!this.mGestureDetector.onTouchEvent(event)) {
            this.mScaleGestureDetector.onTouchEvent(event);
            float x = 0.0f;
            float y = 0.0f;
            int pointerCount = event.getPointerCount();
            for (int i = 0; i < pointerCount; i++) {
                x += event.getX(i);
                y += event.getY(i);
            }
            float x2 = x / ((float) pointerCount);
            float y2 = y / ((float) pointerCount);
            if (pointerCount != this.lastPointerCount) {
                this.isCanDrag = false;
                this.mLastX = x2;
                this.mLastY = y2;
            }
            this.lastPointerCount = pointerCount;
            switch (event.getAction()) {
                case 1:
                case 3:
                    this.lastPointerCount = 0;
                    break;
                case 2:
                    float dx = x2 - this.mLastX;
                    float dy = y2 - this.mLastY;
                    if (!this.isCanDrag) {
                        this.isCanDrag = isCanDrag(dx, dy);
                    }
                    if (this.isCanDrag && getDrawable() != null) {
                        RectF rectF = getMatrixRectF();
                        if (rectF.width() <= ((float) (getWidth() - (this.mOffset * 2)))) {
                            dx = 0.0f;
                        }
                        if (rectF.height() <= ((float) (getHeight() - (this.mVOffset * 2)))) {
                            dy = 0.0f;
                        }
                        this.mScaleMatrix.postTranslate(dx, dy);
                        checkBorder();
                        setImageMatrix(this.mScaleMatrix);
                    }
                    this.mLastX = x2;
                    this.mLastY = y2;
                    break;
            }
        }
        return true;
    }

    public final float getScale() {
        this.mScaleMatrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[0];
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mScaleMatrix = null;
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int l, int t, int r, int b) {
        if (this.isInit) {
            return false;
        }
        boolean frame = super.setFrame(l, t, r, b);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return false;
        }
        int boundWidth = drawable.getBounds().width();
        int boundHeight = drawable.getBounds().height();
        if (boundWidth > this.mCropWidth || boundHeight > this.mCropHeight) {
            return false;
        }
        int width = getWidth();
        int height = getHeight();
        this.mScale = ((float) width) / ((float) boundWidth);
        this.isInit = true;
        postDelayed(new ScaleRunnable(this.mScale, (float) (width / 2), (float) (height / 2)), 50);
        this.isAutoScale = false;
        return frame;
    }

    public void onGlobalLayout() {
        Drawable d;
        if (this.isFirst && (d = getDrawable()) != null) {
            this.mVOffset = (getHeight() - (getWidth() - (this.mOffset * 2))) / 2;
            int width = getWidth();
            int height = getHeight();
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            if (dw < getWidth() - (this.mOffset * 2) && dh > getHeight() - (this.mVOffset * 2)) {
                scale = ((((float) getWidth()) * 1.0f) - ((float) (this.mOffset * 2))) / ((float) dw);
            }
            if (dh < getHeight() - (this.mVOffset * 2) && dw > getWidth() - (this.mOffset * 2)) {
                scale = ((((float) getHeight()) * 1.0f) - ((float) (this.mVOffset * 2))) / ((float) dh);
            }
            if (dw < getWidth() - (this.mOffset * 2) && dh < getHeight() - (this.mVOffset * 2)) {
                scale = Math.max(((((float) getWidth()) * 1.0f) - ((float) (this.mOffset * 2))) / ((float) dw), ((((float) getHeight()) * 1.0f) - ((float) (this.mVOffset * 2))) / ((float) dh));
            }
            this.mScale = scale;
            this.SCALE_MID = this.mScale * 2.0f;
            this.SCALE_MAX = this.mScale * 4.0f;
            this.mScaleMatrix.postTranslate((float) ((width - dw) / 2), (float) ((height - dh) / 2));
            this.mScaleMatrix.postScale(scale, scale, (float) (getWidth() / 2), (float) (getHeight() / 2));
            setImageMatrix(this.mScaleMatrix);
            this.isFirst = false;
        }
    }

    public Bitmap cropBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        draw(new Canvas(bitmap));
        return Bitmap.createBitmap(bitmap, this.mOffset, this.mVOffset, getWidth() - (this.mOffset * 2), getWidth() - (this.mOffset * 2));
    }

    /* access modifiers changed from: private */
    public void checkBorder() {
        RectF rect = getMatrixRectF();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        int width = getWidth();
        int height = getHeight();
        if (((double) rect.width()) + 0.01d >= ((double) (width - (this.mOffset * 2)))) {
            if (rect.left > ((float) this.mOffset)) {
                deltaX = (-rect.left) + ((float) this.mOffset);
            }
            if (rect.right < ((float) (width - this.mOffset))) {
                deltaX = ((float) (width - this.mOffset)) - rect.right;
            }
        }
        if (((double) rect.height()) + 0.01d >= ((double) (height - (this.mVOffset * 2)))) {
            if (rect.top > ((float) this.mVOffset)) {
                deltaY = (-rect.top) + ((float) this.mVOffset);
            }
            if (rect.bottom < ((float) (height - this.mVOffset))) {
                deltaY = ((float) (height - this.mVOffset)) - rect.bottom;
            }
        }
        this.mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((double) ((dx * dx) + (dy * dy))) >= 0.0d;
    }

    public void setHOffset(int hOffset) {
        this.mOffset = hOffset;
    }

    public void setVOffset(int vOffset) {
        this.mVOffset = vOffset;
    }

    public void setCropWidth(int mCropWidth2) {
        this.mCropWidth = mCropWidth2;
    }

    public void setCropHeight(int mCropHeight2) {
        this.mCropHeight = mCropHeight2;
    }
}

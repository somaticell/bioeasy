package com.bm.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActivityChooserView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;

public class PhotoView extends ImageView {
    private static final int ANIMA_DURING = 340;
    private static final float MAX_SCALE = 2.5f;
    private static final int MIN_ROTATE = 35;
    private int MAX_ANIM_FROM_WAITE = 500;
    /* access modifiers changed from: private */
    public int MAX_FLING_OVER_SCROLL = 0;
    private int MAX_OVER_RESISTANCE = 0;
    private int MAX_OVER_SCROLL = 0;
    /* access modifiers changed from: private */
    public boolean canRotate;
    private boolean hasDrawable;
    /* access modifiers changed from: private */
    public boolean hasMultiTouch;
    /* access modifiers changed from: private */
    public boolean hasOverTranslate;
    /* access modifiers changed from: private */
    public boolean imgLargeHeight;
    /* access modifiers changed from: private */
    public boolean imgLargeWidth;
    private boolean isEnable = false;
    private boolean isInit;
    private boolean isKnowSize;
    /* access modifiers changed from: private */
    public boolean isZoonUp;
    private boolean mAdjustViewBounds;
    /* access modifiers changed from: private */
    public int mAnimaDuring;
    /* access modifiers changed from: private */
    public Matrix mAnimaMatrix = new Matrix();
    private Matrix mBaseMatrix = new Matrix();
    /* access modifiers changed from: private */
    public RectF mBaseRect = new RectF();
    /* access modifiers changed from: private */
    public View.OnClickListener mClickListener;
    /* access modifiers changed from: private */
    public Runnable mClickRunnable = new Runnable() {
        public void run() {
            if (PhotoView.this.mClickListener != null) {
                PhotoView.this.mClickListener.onClick(PhotoView.this);
            }
        }
    };
    /* access modifiers changed from: private */
    public RectF mClip;
    /* access modifiers changed from: private */
    public RectF mCommonRect = new RectF();
    /* access modifiers changed from: private */
    public Runnable mCompleteCallBack;
    /* access modifiers changed from: private */
    public float mDegrees;
    private GestureDetector mDetector;
    private Info mFromInfo;
    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            if (PhotoView.this.mLongClick != null) {
                PhotoView.this.mLongClick.onLongClick(PhotoView.this);
            }
        }

        public boolean onDown(MotionEvent e) {
            boolean unused = PhotoView.this.hasOverTranslate = false;
            boolean unused2 = PhotoView.this.hasMultiTouch = false;
            boolean unused3 = PhotoView.this.canRotate = false;
            PhotoView.this.removeCallbacks(PhotoView.this.mClickRunnable);
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (PhotoView.this.hasMultiTouch) {
                return false;
            }
            if ((!PhotoView.this.imgLargeWidth && !PhotoView.this.imgLargeHeight) || PhotoView.this.mTranslate.isRuning) {
                return false;
            }
            float vx = velocityX;
            float vy = velocityY;
            if (((float) Math.round(PhotoView.this.mImgRect.left)) >= PhotoView.this.mWidgetRect.left || ((float) Math.round(PhotoView.this.mImgRect.right)) <= PhotoView.this.mWidgetRect.right) {
                vx = 0.0f;
            }
            if (((float) Math.round(PhotoView.this.mImgRect.top)) >= PhotoView.this.mWidgetRect.top || ((float) Math.round(PhotoView.this.mImgRect.bottom)) <= PhotoView.this.mWidgetRect.bottom) {
                vy = 0.0f;
            }
            if (PhotoView.this.canRotate || PhotoView.this.mDegrees % 90.0f != 0.0f) {
                float toDegrees = (float) (((int) (PhotoView.this.mDegrees / 90.0f)) * 90);
                float remainder = PhotoView.this.mDegrees % 90.0f;
                if (remainder > 45.0f) {
                    toDegrees += 90.0f;
                } else if (remainder < -45.0f) {
                    toDegrees -= 90.0f;
                }
                PhotoView.this.mTranslate.withRotate((int) PhotoView.this.mDegrees, (int) toDegrees);
                float unused = PhotoView.this.mDegrees = toDegrees;
            }
            PhotoView.this.doTranslateReset(PhotoView.this.mImgRect);
            PhotoView.this.mTranslate.withFling(vx, vy);
            PhotoView.this.mTranslate.start();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (PhotoView.this.mTranslate.isRuning) {
                PhotoView.this.mTranslate.stop();
            }
            if (PhotoView.this.canScrollHorizontallySelf(distanceX)) {
                if (distanceX < 0.0f && PhotoView.this.mImgRect.left - distanceX > PhotoView.this.mWidgetRect.left) {
                    distanceX = PhotoView.this.mImgRect.left;
                }
                if (distanceX > 0.0f && PhotoView.this.mImgRect.right - distanceX < PhotoView.this.mWidgetRect.right) {
                    distanceX = PhotoView.this.mImgRect.right - PhotoView.this.mWidgetRect.right;
                }
                PhotoView.this.mAnimaMatrix.postTranslate(-distanceX, 0.0f);
                int unused = PhotoView.this.mTranslateX = (int) (((float) PhotoView.this.mTranslateX) - distanceX);
            } else if (PhotoView.this.imgLargeWidth || PhotoView.this.hasMultiTouch || PhotoView.this.hasOverTranslate) {
                PhotoView.this.checkRect();
                if (!PhotoView.this.hasMultiTouch) {
                    if (distanceX < 0.0f && PhotoView.this.mImgRect.left - distanceX > PhotoView.this.mCommonRect.left) {
                        distanceX = PhotoView.this.resistanceScrollByX(PhotoView.this.mImgRect.left - PhotoView.this.mCommonRect.left, distanceX);
                    }
                    if (distanceX > 0.0f && PhotoView.this.mImgRect.right - distanceX < PhotoView.this.mCommonRect.right) {
                        distanceX = PhotoView.this.resistanceScrollByX(PhotoView.this.mImgRect.right - PhotoView.this.mCommonRect.right, distanceX);
                    }
                }
                int unused2 = PhotoView.this.mTranslateX = (int) (((float) PhotoView.this.mTranslateX) - distanceX);
                PhotoView.this.mAnimaMatrix.postTranslate(-distanceX, 0.0f);
                boolean unused3 = PhotoView.this.hasOverTranslate = true;
            }
            if (PhotoView.this.canScrollVerticallySelf(distanceY)) {
                if (distanceY < 0.0f && PhotoView.this.mImgRect.top - distanceY > PhotoView.this.mWidgetRect.top) {
                    distanceY = PhotoView.this.mImgRect.top;
                }
                if (distanceY > 0.0f && PhotoView.this.mImgRect.bottom - distanceY < PhotoView.this.mWidgetRect.bottom) {
                    distanceY = PhotoView.this.mImgRect.bottom - PhotoView.this.mWidgetRect.bottom;
                }
                PhotoView.this.mAnimaMatrix.postTranslate(0.0f, -distanceY);
                int unused4 = PhotoView.this.mTranslateY = (int) (((float) PhotoView.this.mTranslateY) - distanceY);
            } else if (PhotoView.this.imgLargeHeight || PhotoView.this.hasOverTranslate || PhotoView.this.hasMultiTouch) {
                PhotoView.this.checkRect();
                if (!PhotoView.this.hasMultiTouch) {
                    if (distanceY < 0.0f && PhotoView.this.mImgRect.top - distanceY > PhotoView.this.mCommonRect.top) {
                        distanceY = PhotoView.this.resistanceScrollByY(PhotoView.this.mImgRect.top - PhotoView.this.mCommonRect.top, distanceY);
                    }
                    if (distanceY > 0.0f && PhotoView.this.mImgRect.bottom - distanceY < PhotoView.this.mCommonRect.bottom) {
                        distanceY = PhotoView.this.resistanceScrollByY(PhotoView.this.mImgRect.bottom - PhotoView.this.mCommonRect.bottom, distanceY);
                    }
                }
                PhotoView.this.mAnimaMatrix.postTranslate(0.0f, -distanceY);
                int unused5 = PhotoView.this.mTranslateY = (int) (((float) PhotoView.this.mTranslateY) - distanceY);
                boolean unused6 = PhotoView.this.hasOverTranslate = true;
            }
            PhotoView.this.executeTranslate();
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            PhotoView.this.postDelayed(PhotoView.this.mClickRunnable, 250);
            return false;
        }

        public boolean onDoubleTap(MotionEvent e) {
            float from;
            float to;
            PhotoView.this.mTranslate.stop();
            float imgcx = PhotoView.this.mImgRect.left + (PhotoView.this.mImgRect.width() / 2.0f);
            float imgcy = PhotoView.this.mImgRect.top + (PhotoView.this.mImgRect.height() / 2.0f);
            PhotoView.this.mScaleCenter.set(imgcx, imgcy);
            PhotoView.this.mRotateCenter.set(imgcx, imgcy);
            int unused = PhotoView.this.mTranslateX = 0;
            int unused2 = PhotoView.this.mTranslateY = 0;
            if (PhotoView.this.isZoonUp) {
                from = PhotoView.this.mScale;
                to = 1.0f;
            } else {
                from = PhotoView.this.mScale;
                to = PhotoView.this.mMaxScale;
                PhotoView.this.mScaleCenter.set(e.getX(), e.getY());
            }
            PhotoView.this.mTmpMatrix.reset();
            PhotoView.this.mTmpMatrix.postTranslate(-PhotoView.this.mBaseRect.left, -PhotoView.this.mBaseRect.top);
            PhotoView.this.mTmpMatrix.postTranslate(PhotoView.this.mRotateCenter.x, PhotoView.this.mRotateCenter.y);
            PhotoView.this.mTmpMatrix.postTranslate(-PhotoView.this.mHalfBaseRectWidth, -PhotoView.this.mHalfBaseRectHeight);
            PhotoView.this.mTmpMatrix.postRotate(PhotoView.this.mDegrees, PhotoView.this.mRotateCenter.x, PhotoView.this.mRotateCenter.y);
            PhotoView.this.mTmpMatrix.postScale(to, to, PhotoView.this.mScaleCenter.x, PhotoView.this.mScaleCenter.y);
            PhotoView.this.mTmpMatrix.postTranslate((float) PhotoView.this.mTranslateX, (float) PhotoView.this.mTranslateY);
            PhotoView.this.mTmpMatrix.mapRect(PhotoView.this.mTmpRect, PhotoView.this.mBaseRect);
            PhotoView.this.doTranslateReset(PhotoView.this.mTmpRect);
            boolean unused3 = PhotoView.this.isZoonUp = !PhotoView.this.isZoonUp;
            PhotoView.this.mTranslate.withScale(from, to);
            PhotoView.this.mTranslate.start();
            return false;
        }
    };
    /* access modifiers changed from: private */
    public float mHalfBaseRectHeight;
    /* access modifiers changed from: private */
    public float mHalfBaseRectWidth;
    /* access modifiers changed from: private */
    public RectF mImgRect = new RectF();
    private long mInfoTime;
    /* access modifiers changed from: private */
    public View.OnLongClickListener mLongClick;
    /* access modifiers changed from: private */
    public float mMaxScale;
    /* access modifiers changed from: private */
    public int mMinRotate;
    /* access modifiers changed from: private */
    public PointF mRotateCenter = new PointF();
    private RotateGestureDetector mRotateDetector;
    /* access modifiers changed from: private */
    public float mRotateFlag;
    private OnRotateListener mRotateListener = new OnRotateListener() {
        public void onRotate(float degrees, float focusX, float focusY) {
            float unused = PhotoView.this.mRotateFlag = PhotoView.this.mRotateFlag + degrees;
            if (PhotoView.this.canRotate) {
                float unused2 = PhotoView.this.mDegrees = PhotoView.this.mDegrees + degrees;
                PhotoView.this.mAnimaMatrix.postRotate(degrees, focusX, focusY);
            } else if (Math.abs(PhotoView.this.mRotateFlag) >= ((float) PhotoView.this.mMinRotate)) {
                boolean unused3 = PhotoView.this.canRotate = true;
                float unused4 = PhotoView.this.mRotateFlag = 0.0f;
            }
        }
    };
    /* access modifiers changed from: private */
    public float mScale = 1.0f;
    /* access modifiers changed from: private */
    public PointF mScaleCenter = new PointF();
    private ScaleGestureDetector mScaleDetector;
    private ScaleGestureDetector.OnScaleGestureListener mScaleListener = new ScaleGestureDetector.OnScaleGestureListener() {
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                return false;
            }
            float unused = PhotoView.this.mScale = PhotoView.this.mScale * scaleFactor;
            PhotoView.this.mAnimaMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            PhotoView.this.executeTranslate();
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };
    private ImageView.ScaleType mScaleType;
    private PointF mScreenCenter = new PointF();
    private Matrix mSynthesisMatrix = new Matrix();
    /* access modifiers changed from: private */
    public Matrix mTmpMatrix = new Matrix();
    /* access modifiers changed from: private */
    public RectF mTmpRect = new RectF();
    /* access modifiers changed from: private */
    public Transform mTranslate = new Transform();
    /* access modifiers changed from: private */
    public int mTranslateX;
    /* access modifiers changed from: private */
    public int mTranslateY;
    /* access modifiers changed from: private */
    public RectF mWidgetRect = new RectF();

    public interface ClipCalculate {
        float calculateTop();
    }

    public PhotoView(Context context) {
        super(context);
        init();
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.setScaleType(ImageView.ScaleType.MATRIX);
        if (this.mScaleType == null) {
            this.mScaleType = ImageView.ScaleType.CENTER_INSIDE;
        }
        this.mRotateDetector = new RotateGestureDetector(this.mRotateListener);
        this.mDetector = new GestureDetector(getContext(), this.mGestureListener);
        this.mScaleDetector = new ScaleGestureDetector(getContext(), this.mScaleListener);
        float density = getResources().getDisplayMetrics().density;
        this.MAX_OVER_SCROLL = (int) (density * 30.0f);
        this.MAX_FLING_OVER_SCROLL = (int) (density * 30.0f);
        this.MAX_OVER_RESISTANCE = (int) (140.0f * density);
        this.mMinRotate = 35;
        this.mAnimaDuring = ANIMA_DURING;
        this.mMaxScale = MAX_SCALE;
    }

    public int getDefaultAnimaDuring() {
        return ANIMA_DURING;
    }

    public void setOnClickListener(View.OnClickListener l) {
        super.setOnClickListener(l);
        this.mClickListener = l;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        ImageView.ScaleType old = this.mScaleType;
        this.mScaleType = scaleType;
        if (old != scaleType) {
            initBase();
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener l) {
        this.mLongClick = l;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mTranslate.setInterpolator(interpolator);
    }

    public int getAnimaDuring() {
        return this.mAnimaDuring;
    }

    public void setAnimaDuring(int during) {
        this.mAnimaDuring = during;
    }

    public void setMaxScale(float maxScale) {
        this.mMaxScale = maxScale;
    }

    public float getMaxScale() {
        return this.mMaxScale;
    }

    public void enable() {
        this.isEnable = true;
    }

    public void disenable() {
        this.isEnable = false;
    }

    public void setMaxAnimFromWaiteTime(int wait) {
        this.MAX_ANIM_FROM_WAITE = wait;
    }

    public void setImageResource(int resId) {
        Drawable drawable = null;
        try {
            drawable = getResources().getDrawable(resId);
        } catch (Exception e) {
        }
        setImageDrawable(drawable);
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable == null) {
            this.hasDrawable = false;
        } else if (hasSize(drawable)) {
            if (!this.hasDrawable) {
                this.hasDrawable = true;
            }
            initBase();
        }
    }

    private boolean hasSize(Drawable d) {
        if ((d.getIntrinsicHeight() <= 0 || d.getIntrinsicWidth() <= 0) && ((d.getMinimumWidth() <= 0 || d.getMinimumHeight() <= 0) && (d.getBounds().width() <= 0 || d.getBounds().height() <= 0))) {
            return false;
        }
        return true;
    }

    private static int getDrawableWidth(Drawable d) {
        int width = d.getIntrinsicWidth();
        if (width <= 0) {
            width = d.getMinimumWidth();
        }
        if (width <= 0) {
            return d.getBounds().width();
        }
        return width;
    }

    private static int getDrawableHeight(Drawable d) {
        int height = d.getIntrinsicHeight();
        if (height <= 0) {
            height = d.getMinimumHeight();
        }
        if (height <= 0) {
            return d.getBounds().height();
        }
        return height;
    }

    private void initBase() {
        float scale;
        if (this.hasDrawable && this.isKnowSize) {
            this.mBaseMatrix.reset();
            this.mAnimaMatrix.reset();
            this.isZoonUp = false;
            Drawable img = getDrawable();
            int w = getWidth();
            int h = getHeight();
            int imgw = getDrawableWidth(img);
            int imgh = getDrawableHeight(img);
            this.mBaseRect.set(0.0f, 0.0f, (float) imgw, (float) imgh);
            int tx = (w - imgw) / 2;
            int ty = (h - imgh) / 2;
            float sx = 1.0f;
            float sy = 1.0f;
            if (imgw > w) {
                sx = ((float) w) / ((float) imgw);
            }
            if (imgh > h) {
                sy = ((float) h) / ((float) imgh);
            }
            if (sx < sy) {
                scale = sx;
            } else {
                scale = sy;
            }
            this.mBaseMatrix.reset();
            this.mBaseMatrix.postTranslate((float) tx, (float) ty);
            this.mBaseMatrix.postScale(scale, scale, this.mScreenCenter.x, this.mScreenCenter.y);
            this.mBaseMatrix.mapRect(this.mBaseRect);
            this.mHalfBaseRectWidth = this.mBaseRect.width() / 2.0f;
            this.mHalfBaseRectHeight = this.mBaseRect.height() / 2.0f;
            this.mScaleCenter.set(this.mScreenCenter);
            this.mRotateCenter.set(this.mScaleCenter);
            executeTranslate();
            switch (AnonymousClass6.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                case 1:
                    initCenter();
                    break;
                case 2:
                    initCenterCrop();
                    break;
                case 3:
                    initCenterInside();
                    break;
                case 4:
                    initFitCenter();
                    break;
                case 5:
                    initFitStart();
                    break;
                case 6:
                    initFitEnd();
                    break;
                case 7:
                    initFitXY();
                    break;
            }
            this.isInit = true;
            if (this.mFromInfo != null && System.currentTimeMillis() - this.mInfoTime < ((long) this.MAX_ANIM_FROM_WAITE)) {
                animaFrom(this.mFromInfo);
            }
            this.mFromInfo = null;
        }
    }

    /* renamed from: com.bm.library.PhotoView$6  reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ImageView.ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private void initCenter() {
        if (this.hasDrawable && this.isKnowSize) {
            Drawable img = getDrawable();
            int imgw = getDrawableWidth(img);
            int imgh = getDrawableHeight(img);
            if (((float) imgw) > this.mWidgetRect.width() || ((float) imgh) > this.mWidgetRect.height()) {
                float scaleX = ((float) imgw) / this.mImgRect.width();
                float scaleY = ((float) imgh) / this.mImgRect.height();
                if (scaleX <= scaleY) {
                    scaleX = scaleY;
                }
                this.mScale = scaleX;
                this.mAnimaMatrix.postScale(this.mScale, this.mScale, this.mScreenCenter.x, this.mScreenCenter.y);
                executeTranslate();
                resetBase();
            }
        }
    }

    private void initCenterCrop() {
        if (this.mImgRect.width() < this.mWidgetRect.width() || this.mImgRect.height() < this.mWidgetRect.height()) {
            float scaleX = this.mWidgetRect.width() / this.mImgRect.width();
            float scaleY = this.mWidgetRect.height() / this.mImgRect.height();
            if (scaleX <= scaleY) {
                scaleX = scaleY;
            }
            this.mScale = scaleX;
            this.mAnimaMatrix.postScale(this.mScale, this.mScale, this.mScreenCenter.x, this.mScreenCenter.y);
            executeTranslate();
            resetBase();
        }
    }

    private void initCenterInside() {
        if (this.mImgRect.width() > this.mWidgetRect.width() || this.mImgRect.height() > this.mWidgetRect.height()) {
            float scaleX = this.mWidgetRect.width() / this.mImgRect.width();
            float scaleY = this.mWidgetRect.height() / this.mImgRect.height();
            if (scaleX >= scaleY) {
                scaleX = scaleY;
            }
            this.mScale = scaleX;
            this.mAnimaMatrix.postScale(this.mScale, this.mScale, this.mScreenCenter.x, this.mScreenCenter.y);
            executeTranslate();
            resetBase();
        }
    }

    private void initFitCenter() {
        if (this.mImgRect.width() < this.mWidgetRect.width()) {
            this.mScale = this.mWidgetRect.width() / this.mImgRect.width();
            this.mAnimaMatrix.postScale(this.mScale, this.mScale, this.mScreenCenter.x, this.mScreenCenter.y);
            executeTranslate();
            resetBase();
        }
    }

    private void initFitStart() {
        initFitCenter();
        float ty = -this.mImgRect.top;
        this.mTranslateY = (int) (((float) this.mTranslateY) + ty);
        this.mAnimaMatrix.postTranslate(0.0f, ty);
        executeTranslate();
        resetBase();
    }

    private void initFitEnd() {
        initFitCenter();
        float ty = this.mWidgetRect.bottom - this.mImgRect.bottom;
        this.mTranslateY = (int) (((float) this.mTranslateY) + ty);
        this.mAnimaMatrix.postTranslate(0.0f, ty);
        executeTranslate();
        resetBase();
    }

    private void initFitXY() {
        this.mAnimaMatrix.postScale(this.mWidgetRect.width() / this.mImgRect.width(), this.mWidgetRect.height() / this.mImgRect.height(), this.mScreenCenter.x, this.mScreenCenter.y);
        executeTranslate();
        resetBase();
    }

    private void resetBase() {
        Drawable img = getDrawable();
        this.mBaseRect.set(0.0f, 0.0f, (float) getDrawableWidth(img), (float) getDrawableHeight(img));
        this.mBaseMatrix.set(this.mSynthesisMatrix);
        this.mBaseMatrix.mapRect(this.mBaseRect);
        this.mHalfBaseRectWidth = this.mBaseRect.width() / 2.0f;
        this.mHalfBaseRectHeight = this.mBaseRect.height() / 2.0f;
        this.mScale = 1.0f;
        this.mTranslateX = 0;
        this.mTranslateY = 0;
        this.mAnimaMatrix.reset();
    }

    /* access modifiers changed from: private */
    public void executeTranslate() {
        boolean z;
        boolean z2 = true;
        this.mSynthesisMatrix.set(this.mBaseMatrix);
        this.mSynthesisMatrix.postConcat(this.mAnimaMatrix);
        setImageMatrix(this.mSynthesisMatrix);
        this.mAnimaMatrix.mapRect(this.mImgRect, this.mBaseRect);
        if (this.mImgRect.width() > this.mWidgetRect.width()) {
            z = true;
        } else {
            z = false;
        }
        this.imgLargeWidth = z;
        if (this.mImgRect.height() <= this.mWidgetRect.height()) {
            z2 = false;
        }
        this.imgLargeHeight = z2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        float scale;
        if (!this.hasDrawable) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        Drawable d = getDrawable();
        int drawableW = getDrawableWidth(d);
        int drawableH = getDrawableHeight(d);
        int pWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int pHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        ViewGroup.LayoutParams p = getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-2, -2);
        }
        if (p.width == -1) {
            width = widthMode == 0 ? drawableW : pWidth;
        } else if (widthMode == 1073741824) {
            width = pWidth;
        } else if (widthMode == Integer.MIN_VALUE) {
            width = drawableW > pWidth ? pWidth : drawableW;
        } else {
            width = drawableW;
        }
        if (p.height == -1) {
            height = heightMode == 0 ? drawableH : pHeight;
        } else if (heightMode == 1073741824) {
            height = pHeight;
        } else if (heightMode == Integer.MIN_VALUE) {
            height = drawableH > pHeight ? pHeight : drawableH;
        } else {
            height = drawableH;
        }
        if (this.mAdjustViewBounds && ((float) drawableW) / ((float) drawableH) != ((float) width) / ((float) height)) {
            float hScale = ((float) height) / ((float) drawableH);
            float wScale = ((float) width) / ((float) drawableW);
            if (hScale < wScale) {
                scale = hScale;
            } else {
                scale = wScale;
            }
            if (p.width != -1) {
                width = (int) (((float) drawableW) * scale);
            }
            if (p.height != -1) {
                height = (int) (((float) drawableH) * scale);
            }
        }
        setMeasuredDimension(width, height);
    }

    public void setAdjustViewBounds(boolean adjustViewBounds) {
        super.setAdjustViewBounds(adjustViewBounds);
        this.mAdjustViewBounds = adjustViewBounds;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidgetRect.set(0.0f, 0.0f, (float) w, (float) h);
        this.mScreenCenter.set((float) (w / 2), (float) (h / 2));
        if (!this.isKnowSize) {
            this.isKnowSize = true;
            initBase();
        }
    }

    public void draw(Canvas canvas) {
        if (this.mClip != null) {
            canvas.clipRect(this.mClip);
            this.mClip = null;
        }
        super.draw(canvas);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!this.isEnable) {
            return super.dispatchTouchEvent(event);
        }
        int Action = event.getActionMasked();
        if (event.getPointerCount() >= 2) {
            this.hasMultiTouch = true;
        }
        this.mDetector.onTouchEvent(event);
        this.mRotateDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        if (Action != 1 && Action != 3) {
            return true;
        }
        onUp();
        return true;
    }

    private void onUp() {
        if (!this.mTranslate.isRuning) {
            if (this.canRotate || this.mDegrees % 90.0f != 0.0f) {
                float toDegrees = (float) (((int) (this.mDegrees / 90.0f)) * 90);
                float remainder = this.mDegrees % 90.0f;
                if (remainder > 45.0f) {
                    toDegrees += 90.0f;
                } else if (remainder < -45.0f) {
                    toDegrees -= 90.0f;
                }
                this.mTranslate.withRotate((int) this.mDegrees, (int) toDegrees);
                this.mDegrees = toDegrees;
            }
            float scale = this.mScale;
            if (this.mScale < 1.0f) {
                scale = 1.0f;
                this.mTranslate.withScale(this.mScale, 1.0f);
            } else if (this.mScale > this.mMaxScale) {
                scale = this.mMaxScale;
                this.mTranslate.withScale(this.mScale, this.mMaxScale);
            }
            float cx = this.mImgRect.left + (this.mImgRect.width() / 2.0f);
            float cy = this.mImgRect.top + (this.mImgRect.height() / 2.0f);
            this.mScaleCenter.set(cx, cy);
            this.mRotateCenter.set(cx, cy);
            this.mTranslateX = 0;
            this.mTranslateY = 0;
            this.mTmpMatrix.reset();
            this.mTmpMatrix.postTranslate(-this.mBaseRect.left, -this.mBaseRect.top);
            this.mTmpMatrix.postTranslate(cx - this.mHalfBaseRectWidth, cy - this.mHalfBaseRectHeight);
            this.mTmpMatrix.postScale(scale, scale, cx, cy);
            this.mTmpMatrix.postRotate(this.mDegrees, cx, cy);
            this.mTmpMatrix.mapRect(this.mTmpRect, this.mBaseRect);
            doTranslateReset(this.mTmpRect);
            this.mTranslate.start();
        }
    }

    /* access modifiers changed from: private */
    public void doTranslateReset(RectF imgRect) {
        int tx = 0;
        int ty = 0;
        if (imgRect.width() <= this.mWidgetRect.width()) {
            if (!isImageCenterWidth(imgRect)) {
                tx = -((int) (((this.mWidgetRect.width() - imgRect.width()) / 2.0f) - imgRect.left));
            }
        } else if (imgRect.left > this.mWidgetRect.left) {
            tx = (int) (imgRect.left - this.mWidgetRect.left);
        } else if (imgRect.right < this.mWidgetRect.right) {
            tx = (int) (imgRect.right - this.mWidgetRect.right);
        }
        if (imgRect.height() <= this.mWidgetRect.height()) {
            if (!isImageCenterHeight(imgRect)) {
                ty = -((int) (((this.mWidgetRect.height() - imgRect.height()) / 2.0f) - imgRect.top));
            }
        } else if (imgRect.top > this.mWidgetRect.top) {
            ty = (int) (imgRect.top - this.mWidgetRect.top);
        } else if (imgRect.bottom < this.mWidgetRect.bottom) {
            ty = (int) (imgRect.bottom - this.mWidgetRect.bottom);
        }
        if (tx != 0 || ty != 0) {
            if (!this.mTranslate.mFlingScroller.isFinished()) {
                this.mTranslate.mFlingScroller.abortAnimation();
            }
            this.mTranslate.withTranslate(this.mTranslateX, this.mTranslateY, -tx, -ty);
        }
    }

    private boolean isImageCenterHeight(RectF rect) {
        return Math.abs(((float) Math.round(rect.top)) - ((this.mWidgetRect.height() - rect.height()) / 2.0f)) < 1.0f;
    }

    private boolean isImageCenterWidth(RectF rect) {
        return Math.abs(((float) Math.round(rect.left)) - ((this.mWidgetRect.width() - rect.width()) / 2.0f)) < 1.0f;
    }

    /* access modifiers changed from: private */
    public float resistanceScrollByX(float overScroll, float detalX) {
        return detalX * (Math.abs(Math.abs(overScroll) - ((float) this.MAX_OVER_RESISTANCE)) / ((float) this.MAX_OVER_RESISTANCE));
    }

    /* access modifiers changed from: private */
    public float resistanceScrollByY(float overScroll, float detalY) {
        return detalY * (Math.abs(Math.abs(overScroll) - ((float) this.MAX_OVER_RESISTANCE)) / ((float) this.MAX_OVER_RESISTANCE));
    }

    private void mapRect(RectF r1, RectF r2, RectF out) {
        float l = r1.left > r2.left ? r1.left : r2.left;
        float r = r1.right < r2.right ? r1.right : r2.right;
        if (l > r) {
            out.set(0.0f, 0.0f, 0.0f, 0.0f);
            return;
        }
        float t = r1.top > r2.top ? r1.top : r2.top;
        float b = r1.bottom < r2.bottom ? r1.bottom : r2.bottom;
        if (t > b) {
            out.set(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            out.set(l, t, r, b);
        }
    }

    /* access modifiers changed from: private */
    public void checkRect() {
        if (!this.hasOverTranslate) {
            mapRect(this.mWidgetRect, this.mImgRect, this.mCommonRect);
        }
    }

    public boolean canScrollHorizontallySelf(float direction) {
        if (this.mImgRect.width() <= this.mWidgetRect.width()) {
            return false;
        }
        if (direction < 0.0f && ((float) Math.round(this.mImgRect.left)) - direction >= this.mWidgetRect.left) {
            return false;
        }
        if (direction <= 0.0f || ((float) Math.round(this.mImgRect.right)) - direction > this.mWidgetRect.right) {
            return true;
        }
        return false;
    }

    public boolean canScrollVerticallySelf(float direction) {
        if (this.mImgRect.height() <= this.mWidgetRect.height()) {
            return false;
        }
        if (direction < 0.0f && ((float) Math.round(this.mImgRect.top)) - direction >= this.mWidgetRect.top) {
            return false;
        }
        if (direction <= 0.0f || ((float) Math.round(this.mImgRect.bottom)) - direction > this.mWidgetRect.bottom) {
            return true;
        }
        return false;
    }

    public boolean canScrollHorizontally(int direction) {
        if (this.hasMultiTouch) {
            return true;
        }
        return canScrollHorizontallySelf((float) direction);
    }

    public boolean canScrollVertically(int direction) {
        if (this.hasMultiTouch) {
            return true;
        }
        return canScrollVerticallySelf((float) direction);
    }

    private class InterpolatorProxy implements Interpolator {
        private Interpolator mTarget;

        private InterpolatorProxy() {
            this.mTarget = new DecelerateInterpolator();
        }

        public void setTargetInterpolator(Interpolator interpolator) {
            this.mTarget = interpolator;
        }

        public float getInterpolation(float input) {
            if (this.mTarget != null) {
                return this.mTarget.getInterpolation(input);
            }
            return input;
        }
    }

    private class Transform implements Runnable {
        ClipCalculate C;
        boolean isRuning;
        RectF mClipRect = new RectF();
        Scroller mClipScroller;
        OverScroller mFlingScroller;
        InterpolatorProxy mInterpolatorProxy = new InterpolatorProxy();
        int mLastFlingX;
        int mLastFlingY;
        int mLastTranslateX;
        int mLastTranslateY;
        Scroller mRotateScroller;
        Scroller mScaleScroller;
        OverScroller mTranslateScroller;

        Transform() {
            Context ctx = PhotoView.this.getContext();
            this.mTranslateScroller = new OverScroller(ctx, this.mInterpolatorProxy);
            this.mScaleScroller = new Scroller(ctx, this.mInterpolatorProxy);
            this.mFlingScroller = new OverScroller(ctx, this.mInterpolatorProxy);
            this.mClipScroller = new Scroller(ctx, this.mInterpolatorProxy);
            this.mRotateScroller = new Scroller(ctx, this.mInterpolatorProxy);
        }

        public void setInterpolator(Interpolator interpolator) {
            this.mInterpolatorProxy.setTargetInterpolator(interpolator);
        }

        /* access modifiers changed from: package-private */
        public void withTranslate(int startX, int startY, int deltaX, int deltaY) {
            this.mLastTranslateX = 0;
            this.mLastTranslateY = 0;
            this.mTranslateScroller.startScroll(0, 0, deltaX, deltaY, PhotoView.this.mAnimaDuring);
        }

        /* access modifiers changed from: package-private */
        public void withScale(float form, float to) {
            this.mScaleScroller.startScroll((int) (form * 10000.0f), 0, (int) ((to - form) * 10000.0f), 0, PhotoView.this.mAnimaDuring);
        }

        /* access modifiers changed from: package-private */
        public void withClip(float fromX, float fromY, float deltaX, float deltaY, int d, ClipCalculate c) {
            this.mClipScroller.startScroll((int) (fromX * 10000.0f), (int) (fromY * 10000.0f), (int) (deltaX * 10000.0f), (int) (10000.0f * deltaY), d);
            this.C = c;
        }

        /* access modifiers changed from: package-private */
        public void withRotate(int fromDegrees, int toDegrees) {
            int i = fromDegrees;
            this.mRotateScroller.startScroll(i, 0, toDegrees - fromDegrees, 0, PhotoView.this.mAnimaDuring);
        }

        /* access modifiers changed from: package-private */
        public void withRotate(int fromDegrees, int toDegrees, int during) {
            this.mRotateScroller.startScroll(fromDegrees, 0, toDegrees - fromDegrees, 0, during);
        }

        /* access modifiers changed from: package-private */
        public void withFling(float velocityX, float velocityY) {
            int maxX;
            int overX;
            int maxY;
            int overY;
            this.mLastFlingX = velocityX < 0.0f ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : 0;
            int distanceX = (int) (velocityX > 0.0f ? Math.abs(PhotoView.this.mImgRect.left) : PhotoView.this.mImgRect.right - PhotoView.this.mWidgetRect.right);
            if (velocityX < 0.0f) {
                distanceX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - distanceX;
            }
            int minX = velocityX < 0.0f ? distanceX : 0;
            if (velocityX < 0.0f) {
                maxX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else {
                maxX = distanceX;
            }
            if (velocityX < 0.0f) {
                overX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - minX;
            } else {
                overX = distanceX;
            }
            this.mLastFlingY = velocityY < 0.0f ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : 0;
            int distanceY = (int) (velocityY > 0.0f ? Math.abs(PhotoView.this.mImgRect.top) : PhotoView.this.mImgRect.bottom - PhotoView.this.mWidgetRect.bottom);
            if (velocityY < 0.0f) {
                distanceY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - distanceY;
            }
            int minY = velocityY < 0.0f ? distanceY : 0;
            if (velocityY < 0.0f) {
                maxY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else {
                maxY = distanceY;
            }
            if (velocityY < 0.0f) {
                overY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - minY;
            } else {
                overY = distanceY;
            }
            if (velocityX == 0.0f) {
                maxX = 0;
                minX = 0;
            }
            if (velocityY == 0.0f) {
                maxY = 0;
                minY = 0;
            }
            this.mFlingScroller.fling(this.mLastFlingX, this.mLastFlingY, (int) velocityX, (int) velocityY, minX, maxX, minY, maxY, Math.abs(overX) < PhotoView.this.MAX_FLING_OVER_SCROLL * 2 ? 0 : PhotoView.this.MAX_FLING_OVER_SCROLL, Math.abs(overY) < PhotoView.this.MAX_FLING_OVER_SCROLL * 2 ? 0 : PhotoView.this.MAX_FLING_OVER_SCROLL);
        }

        /* access modifiers changed from: package-private */
        public void start() {
            this.isRuning = true;
            postExecute();
        }

        /* access modifiers changed from: package-private */
        public void stop() {
            PhotoView.this.removeCallbacks(this);
            this.mTranslateScroller.abortAnimation();
            this.mScaleScroller.abortAnimation();
            this.mFlingScroller.abortAnimation();
            this.mRotateScroller.abortAnimation();
            this.isRuning = false;
        }

        public void run() {
            boolean endAnima = true;
            if (this.mScaleScroller.computeScrollOffset()) {
                float unused = PhotoView.this.mScale = ((float) this.mScaleScroller.getCurrX()) / 10000.0f;
                endAnima = false;
            }
            if (this.mTranslateScroller.computeScrollOffset()) {
                int tx = this.mTranslateScroller.getCurrX() - this.mLastTranslateX;
                int ty = this.mTranslateScroller.getCurrY() - this.mLastTranslateY;
                int unused2 = PhotoView.this.mTranslateX = PhotoView.this.mTranslateX + tx;
                int unused3 = PhotoView.this.mTranslateY = PhotoView.this.mTranslateY + ty;
                this.mLastTranslateX = this.mTranslateScroller.getCurrX();
                this.mLastTranslateY = this.mTranslateScroller.getCurrY();
                endAnima = false;
            }
            if (this.mFlingScroller.computeScrollOffset()) {
                int x = this.mFlingScroller.getCurrX() - this.mLastFlingX;
                int y = this.mFlingScroller.getCurrY() - this.mLastFlingY;
                this.mLastFlingX = this.mFlingScroller.getCurrX();
                this.mLastFlingY = this.mFlingScroller.getCurrY();
                int unused4 = PhotoView.this.mTranslateX = PhotoView.this.mTranslateX + x;
                int unused5 = PhotoView.this.mTranslateY = PhotoView.this.mTranslateY + y;
                endAnima = false;
            }
            if (this.mRotateScroller.computeScrollOffset()) {
                float unused6 = PhotoView.this.mDegrees = (float) this.mRotateScroller.getCurrX();
                endAnima = false;
            }
            if (this.mClipScroller.computeScrollOffset() || PhotoView.this.mClip != null) {
                float sx = ((float) this.mClipScroller.getCurrX()) / 10000.0f;
                float sy = ((float) this.mClipScroller.getCurrY()) / 10000.0f;
                PhotoView.this.mTmpMatrix.setScale(sx, sy, (PhotoView.this.mImgRect.left + PhotoView.this.mImgRect.right) / 2.0f, this.C.calculateTop());
                PhotoView.this.mTmpMatrix.mapRect(this.mClipRect, PhotoView.this.mImgRect);
                if (sx == 1.0f) {
                    this.mClipRect.left = PhotoView.this.mWidgetRect.left;
                    this.mClipRect.right = PhotoView.this.mWidgetRect.right;
                }
                if (sy == 1.0f) {
                    this.mClipRect.top = PhotoView.this.mWidgetRect.top;
                    this.mClipRect.bottom = PhotoView.this.mWidgetRect.bottom;
                }
                RectF unused7 = PhotoView.this.mClip = this.mClipRect;
            }
            if (!endAnima) {
                applyAnima();
                postExecute();
                return;
            }
            this.isRuning = false;
            boolean needFix = false;
            if (PhotoView.this.imgLargeWidth) {
                if (PhotoView.this.mImgRect.left > 0.0f) {
                    int unused8 = PhotoView.this.mTranslateX = (int) (((float) PhotoView.this.mTranslateX) - PhotoView.this.mImgRect.left);
                } else if (PhotoView.this.mImgRect.right < PhotoView.this.mWidgetRect.width()) {
                    int unused9 = PhotoView.this.mTranslateX = PhotoView.this.mTranslateX - ((int) (PhotoView.this.mWidgetRect.width() - PhotoView.this.mImgRect.right));
                }
                needFix = true;
            }
            if (PhotoView.this.imgLargeHeight) {
                if (PhotoView.this.mImgRect.top > 0.0f) {
                    int unused10 = PhotoView.this.mTranslateY = (int) (((float) PhotoView.this.mTranslateY) - PhotoView.this.mImgRect.top);
                } else if (PhotoView.this.mImgRect.bottom < PhotoView.this.mWidgetRect.height()) {
                    int unused11 = PhotoView.this.mTranslateY = PhotoView.this.mTranslateY - ((int) (PhotoView.this.mWidgetRect.height() - PhotoView.this.mImgRect.bottom));
                }
                needFix = true;
            }
            if (needFix) {
                applyAnima();
            }
            PhotoView.this.invalidate();
            if (PhotoView.this.mCompleteCallBack != null) {
                PhotoView.this.mCompleteCallBack.run();
                Runnable unused12 = PhotoView.this.mCompleteCallBack = null;
            }
        }

        private void applyAnima() {
            PhotoView.this.mAnimaMatrix.reset();
            PhotoView.this.mAnimaMatrix.postTranslate(-PhotoView.this.mBaseRect.left, -PhotoView.this.mBaseRect.top);
            PhotoView.this.mAnimaMatrix.postTranslate(PhotoView.this.mRotateCenter.x, PhotoView.this.mRotateCenter.y);
            PhotoView.this.mAnimaMatrix.postTranslate(-PhotoView.this.mHalfBaseRectWidth, -PhotoView.this.mHalfBaseRectHeight);
            PhotoView.this.mAnimaMatrix.postRotate(PhotoView.this.mDegrees, PhotoView.this.mRotateCenter.x, PhotoView.this.mRotateCenter.y);
            PhotoView.this.mAnimaMatrix.postScale(PhotoView.this.mScale, PhotoView.this.mScale, PhotoView.this.mScaleCenter.x, PhotoView.this.mScaleCenter.y);
            PhotoView.this.mAnimaMatrix.postTranslate((float) PhotoView.this.mTranslateX, (float) PhotoView.this.mTranslateY);
            PhotoView.this.executeTranslate();
        }

        private void postExecute() {
            if (this.isRuning) {
                PhotoView.this.post(this);
            }
        }
    }

    public Info getInfo() {
        RectF rect = new RectF();
        int[] p = new int[2];
        getLocation(this, p);
        rect.set(((float) p[0]) + this.mImgRect.left, ((float) p[1]) + this.mImgRect.top, ((float) p[0]) + this.mImgRect.right, ((float) p[1]) + this.mImgRect.bottom);
        return new Info(rect, this.mImgRect, this.mWidgetRect, this.mBaseRect, this.mScreenCenter, this.mScale, this.mDegrees, this.mScaleType);
    }

    public static Info getImageViewInfo(ImageView imgView) {
        int[] p = new int[2];
        getLocation(imgView, p);
        Drawable drawable = imgView.getDrawable();
        Matrix matrix = imgView.getImageMatrix();
        RectF imgRect = new RectF(0.0f, 0.0f, (float) getDrawableWidth(drawable), (float) getDrawableHeight(drawable));
        matrix.mapRect(imgRect);
        RectF rect = new RectF(((float) p[0]) + imgRect.left, ((float) p[1]) + imgRect.top, ((float) p[0]) + imgRect.right, ((float) p[1]) + imgRect.bottom);
        RectF widgetRect = new RectF(0.0f, 0.0f, (float) imgView.getWidth(), (float) imgView.getHeight());
        return new Info(rect, imgRect, widgetRect, new RectF(widgetRect), new PointF(widgetRect.width() / 2.0f, widgetRect.height() / 2.0f), 1.0f, 0.0f, imgView.getScaleType());
    }

    private static void getLocation(View target, int[] position) {
        position[0] = position[0] + target.getLeft();
        position[1] = position[1] + target.getTop();
        ViewParent viewParent = target.getParent();
        while (viewParent instanceof View) {
            View view = (View) viewParent;
            if (view.getId() != 16908290) {
                position[0] = position[0] - view.getScrollX();
                position[1] = position[1] - view.getScrollY();
                position[0] = position[0] + view.getLeft();
                position[1] = position[1] + view.getTop();
                viewParent = view.getParent();
            } else {
                return;
            }
        }
        position[0] = (int) (((float) position[0]) + 0.5f);
        position[1] = (int) (((float) position[1]) + 0.5f);
    }

    private void reset() {
        this.mAnimaMatrix.reset();
        executeTranslate();
        this.mScale = 1.0f;
        this.mTranslateX = 0;
        this.mTranslateY = 0;
    }

    public class START implements ClipCalculate {
        public START() {
        }

        public float calculateTop() {
            return PhotoView.this.mImgRect.top;
        }
    }

    public class END implements ClipCalculate {
        public END() {
        }

        public float calculateTop() {
            return PhotoView.this.mImgRect.bottom;
        }
    }

    public class OTHER implements ClipCalculate {
        public OTHER() {
        }

        public float calculateTop() {
            return (PhotoView.this.mImgRect.top + PhotoView.this.mImgRect.bottom) / 2.0f;
        }
    }

    public void animaFrom(Info info) {
        float scale;
        ClipCalculate c;
        if (this.isInit) {
            reset();
            Info mine = getInfo();
            float scaleX = info.mImgRect.width() / mine.mImgRect.width();
            float scaleY = info.mImgRect.height() / mine.mImgRect.height();
            if (scaleX < scaleY) {
                scale = scaleX;
            } else {
                scale = scaleY;
            }
            float ocx = info.mRect.left + (info.mRect.width() / 2.0f);
            float ocy = info.mRect.top + (info.mRect.height() / 2.0f);
            this.mAnimaMatrix.reset();
            this.mAnimaMatrix.postTranslate(-this.mBaseRect.left, -this.mBaseRect.top);
            this.mAnimaMatrix.postTranslate(ocx - (this.mBaseRect.width() / 2.0f), ocy - (this.mBaseRect.height() / 2.0f));
            this.mAnimaMatrix.postScale(scale, scale, ocx, ocy);
            this.mAnimaMatrix.postRotate(info.mDegrees, ocx, ocy);
            executeTranslate();
            this.mScaleCenter.set(ocx, ocy);
            this.mRotateCenter.set(ocx, ocy);
            this.mTranslate.withTranslate(0, 0, (int) (this.mScreenCenter.x - ocx), (int) (this.mScreenCenter.y - ocy));
            this.mTranslate.withScale(scale, 1.0f);
            this.mTranslate.withRotate((int) info.mDegrees, 0);
            if (info.mWidgetRect.width() < info.mImgRect.width() || info.mWidgetRect.height() < info.mImgRect.height()) {
                float clipX = info.mWidgetRect.width() / info.mImgRect.width();
                float clipY = info.mWidgetRect.height() / info.mImgRect.height();
                if (clipX > 1.0f) {
                    clipX = 1.0f;
                }
                if (clipY > 1.0f) {
                    clipY = 1.0f;
                }
                if (info.mScaleType == ImageView.ScaleType.FIT_START) {
                    c = new START();
                } else {
                    c = info.mScaleType == ImageView.ScaleType.FIT_END ? new END() : new OTHER();
                }
                this.mTranslate.withClip(clipX, clipY, 1.0f - clipX, 1.0f - clipY, this.mAnimaDuring / 3, c);
                this.mTmpMatrix.setScale(clipX, clipY, (this.mImgRect.left + this.mImgRect.right) / 2.0f, c.calculateTop());
                this.mTmpMatrix.mapRect(this.mTranslate.mClipRect, this.mImgRect);
                this.mClip = this.mTranslate.mClipRect;
            }
            this.mTranslate.start();
            return;
        }
        this.mFromInfo = info;
        this.mInfoTime = System.currentTimeMillis();
    }

    public void animaTo(Info info, Runnable completeCallBack) {
        float scale;
        if (this.isInit) {
            this.mTranslate.stop();
            this.mTranslateX = 0;
            this.mTranslateY = 0;
            float tcx = info.mRect.left + (info.mRect.width() / 2.0f);
            float tcy = info.mRect.top + (info.mRect.height() / 2.0f);
            this.mScaleCenter.set(this.mImgRect.left + (this.mImgRect.width() / 2.0f), this.mImgRect.top + (this.mImgRect.height() / 2.0f));
            this.mRotateCenter.set(this.mScaleCenter);
            this.mAnimaMatrix.postRotate(-this.mDegrees, this.mScaleCenter.x, this.mScaleCenter.y);
            this.mAnimaMatrix.mapRect(this.mImgRect, this.mBaseRect);
            float scaleX = info.mImgRect.width() / this.mBaseRect.width();
            float scaleY = info.mImgRect.height() / this.mBaseRect.height();
            if (scaleX > scaleY) {
                scale = scaleX;
            } else {
                scale = scaleY;
            }
            this.mAnimaMatrix.postRotate(this.mDegrees, this.mScaleCenter.x, this.mScaleCenter.y);
            this.mAnimaMatrix.mapRect(this.mImgRect, this.mBaseRect);
            this.mDegrees %= 360.0f;
            this.mTranslate.withTranslate(0, 0, (int) (tcx - this.mScaleCenter.x), (int) (tcy - this.mScaleCenter.y));
            this.mTranslate.withScale(this.mScale, scale);
            this.mTranslate.withRotate((int) this.mDegrees, (int) info.mDegrees, (this.mAnimaDuring * 2) / 3);
            if (info.mWidgetRect.width() < info.mRect.width() || info.mWidgetRect.height() < info.mRect.height()) {
                float clipX = info.mWidgetRect.width() / info.mRect.width();
                float clipY = info.mWidgetRect.height() / info.mRect.height();
                if (clipX > 1.0f) {
                    clipX = 1.0f;
                }
                if (clipY > 1.0f) {
                    clipY = 1.0f;
                }
                final float cx = clipX;
                final float cy = clipY;
                final ClipCalculate c = info.mScaleType == ImageView.ScaleType.FIT_START ? new START() : info.mScaleType == ImageView.ScaleType.FIT_END ? new END() : new OTHER();
                postDelayed(new Runnable() {
                    public void run() {
                        PhotoView.this.mTranslate.withClip(1.0f, 1.0f, -1.0f + cx, -1.0f + cy, PhotoView.this.mAnimaDuring / 2, c);
                    }
                }, (long) (this.mAnimaDuring / 2));
            }
            this.mCompleteCallBack = completeCallBack;
            this.mTranslate.start();
        }
    }
}

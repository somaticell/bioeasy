package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class ImagePreviewView extends ImageView {
    private static final float mMaxScale = 4.0f;
    private static final float mMinScale = 0.4f;
    /* access modifiers changed from: private */
    public boolean isAutoScale;
    private AccelerateInterpolator mAccInterpolator;
    /* access modifiers changed from: private */
    public int mBoundHeight;
    /* access modifiers changed from: private */
    public int mBoundWidth;
    /* access modifiers changed from: private */
    public DecelerateInterpolator mDecInterpolator;
    private GestureDetector mFlatDetector;
    private FloatEvaluator mFloatEvaluator;
    private ScaleGestureDetector mScaleDetector;
    /* access modifiers changed from: private */
    public OnReachBorderListener onReachBorderListener;
    private ValueAnimator.AnimatorUpdateListener onScaleAnimationUpdate;
    private ValueAnimator.AnimatorUpdateListener onTranslateXAnimationUpdate;
    private ValueAnimator.AnimatorUpdateListener onTranslateYAnimationUpdate;
    private ValueAnimator resetScaleAnimator;
    private ValueAnimator resetXAnimator;
    private ValueAnimator resetYAnimator;
    /* access modifiers changed from: private */
    public float scale;
    /* access modifiers changed from: private */
    public float translateLeft;
    /* access modifiers changed from: private */
    public float translateTop;

    public interface OnReachBorderListener {
        void onReachBorder(boolean z);
    }

    public void setOnReachBorderListener(OnReachBorderListener l) {
        this.onReachBorderListener = l;
    }

    public ImagePreviewView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ImagePreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagePreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.scale = 1.0f;
        this.translateLeft = 0.0f;
        this.translateTop = 0.0f;
        this.mBoundWidth = 0;
        this.mBoundHeight = 0;
        this.isAutoScale = false;
        this.mFloatEvaluator = new FloatEvaluator();
        this.mAccInterpolator = new AccelerateInterpolator();
        this.mDecInterpolator = new DecelerateInterpolator();
        this.mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        this.mFlatDetector = new GestureDetector(getContext(), new FlatGestureListener());
    }

    public ValueAnimator.AnimatorUpdateListener getOnScaleAnimationUpdate() {
        if (this.onScaleAnimationUpdate != null) {
            return this.onScaleAnimationUpdate;
        }
        this.onScaleAnimationUpdate = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float unused = ImagePreviewView.this.scale = ((Float) animation.getAnimatedValue()).floatValue();
                ImagePreviewView.this.invalidate();
            }
        };
        return this.onScaleAnimationUpdate;
    }

    public ValueAnimator.AnimatorUpdateListener getOnTranslateXAnimationUpdate() {
        if (this.onTranslateXAnimationUpdate != null) {
            return this.onTranslateXAnimationUpdate;
        }
        this.onTranslateXAnimationUpdate = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float unused = ImagePreviewView.this.translateLeft = ((Float) animation.getAnimatedValue()).floatValue();
                ImagePreviewView.this.invalidate();
            }
        };
        return this.onTranslateXAnimationUpdate;
    }

    public ValueAnimator.AnimatorUpdateListener getOnTranslateYAnimationUpdate() {
        if (this.onTranslateYAnimationUpdate != null) {
            return this.onTranslateYAnimationUpdate;
        }
        this.onTranslateYAnimationUpdate = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float unused = ImagePreviewView.this.translateTop = ((Float) animation.getAnimatedValue()).floatValue();
                ImagePreviewView.this.invalidate();
            }
        };
        return this.onTranslateYAnimationUpdate;
    }

    /* access modifiers changed from: private */
    public ValueAnimator getResetScaleAnimator() {
        if (this.resetScaleAnimator != null) {
            this.resetScaleAnimator.removeAllUpdateListeners();
        } else {
            this.resetScaleAnimator = ValueAnimator.ofFloat(new float[0]);
        }
        this.resetScaleAnimator.setDuration(150);
        this.resetScaleAnimator.setInterpolator(this.mAccInterpolator);
        this.resetScaleAnimator.setEvaluator(this.mFloatEvaluator);
        return this.resetScaleAnimator;
    }

    /* access modifiers changed from: private */
    public ValueAnimator getResetXAnimator() {
        if (this.resetXAnimator != null) {
            this.resetXAnimator.removeAllUpdateListeners();
        } else {
            this.resetXAnimator = ValueAnimator.ofFloat(new float[0]);
        }
        this.resetXAnimator.setDuration(150);
        this.resetXAnimator.setInterpolator(this.mAccInterpolator);
        this.resetXAnimator.setEvaluator(this.mFloatEvaluator);
        return this.resetXAnimator;
    }

    /* access modifiers changed from: private */
    public ValueAnimator getResetYAnimator() {
        if (this.resetYAnimator != null) {
            this.resetYAnimator.removeAllUpdateListeners();
        } else {
            this.resetYAnimator = ValueAnimator.ofFloat(new float[0]);
        }
        this.resetYAnimator.setDuration(150);
        this.resetYAnimator.setInterpolator(this.mAccInterpolator);
        this.resetYAnimator.setEvaluator(this.mFloatEvaluator);
        return this.resetYAnimator;
    }

    private void cancelAnimation() {
        if (this.resetScaleAnimator != null && this.resetScaleAnimator.isRunning()) {
            this.resetScaleAnimator.cancel();
        }
        if (this.resetXAnimator != null && this.resetXAnimator.isRunning()) {
            this.resetXAnimator.cancel();
        }
        if (this.resetYAnimator != null && this.resetYAnimator.isRunning()) {
            this.resetYAnimator.cancel();
        }
    }

    /* access modifiers changed from: private */
    public float getDiffX() {
        float mScaledWidth = ((float) this.mBoundWidth) * this.scale;
        if (this.translateLeft >= 0.0f) {
            return this.translateLeft;
        }
        if ((((float) getWidth()) - this.translateLeft) - mScaledWidth > 0.0f) {
            return -((((float) getWidth()) - this.translateLeft) - mScaledWidth);
        }
        return 0.0f;
    }

    /* access modifiers changed from: private */
    public float getDiffY() {
        float mScaledHeight = ((float) this.mBoundHeight) * this.scale;
        if (this.translateTop >= 0.0f) {
            return this.translateTop;
        }
        if ((((float) getHeight()) - this.translateTop) - mScaledHeight > 0.0f) {
            return -((((float) getHeight()) - this.translateTop) - mScaledHeight);
        }
        return 0.0f;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            cancelAnimation();
        }
        this.mFlatDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        if (action == 1 || action == 3) {
            if (this.isAutoScale) {
                this.isAutoScale = false;
            } else {
                if (this.scale < 1.0f) {
                    ValueAnimator animator = getResetScaleAnimator();
                    animator.setFloatValues(new float[]{this.scale, 1.0f});
                    animator.addUpdateListener(getOnScaleAnimationUpdate());
                    animator.start();
                }
                float mScaledWidth = ((float) this.mBoundWidth) * this.scale;
                float mScaledHeight = ((float) this.mBoundHeight) * this.scale;
                float mDiffX = getDiffX();
                float mDiffY = getDiffY();
                if (mScaledWidth >= ((float) getWidth()) && mDiffX != 0.0f) {
                    ValueAnimator animator2 = getResetXAnimator();
                    animator2.setFloatValues(new float[]{this.translateLeft, this.translateLeft - mDiffX});
                    animator2.addUpdateListener(getOnTranslateXAnimationUpdate());
                    animator2.start();
                }
                if (mScaledHeight >= ((float) getHeight()) && mDiffY != 0.0f) {
                    ValueAnimator animator3 = getResetYAnimator();
                    animator3.setFloatValues(new float[]{this.translateTop, this.translateTop - mDiffY});
                    animator3.addUpdateListener(getOnTranslateYAnimationUpdate());
                    animator3.start();
                }
                if (mScaledWidth < ((float) getWidth()) && mScaledHeight >= ((float) getHeight()) && mDiffX != 0.0f) {
                    ValueAnimator animator4 = getResetXAnimator();
                    animator4.setFloatValues(new float[]{this.translateLeft, 0.0f});
                    animator4.addUpdateListener(getOnTranslateXAnimationUpdate());
                    animator4.start();
                }
                if (mScaledHeight < ((float) getHeight()) && mScaledWidth >= ((float) getWidth()) && mDiffY != 0.0f) {
                    ValueAnimator animator5 = getResetYAnimator();
                    animator5.setFloatValues(new float[]{this.translateTop, (((float) getHeight()) - mScaledHeight) / 2.0f});
                    animator5.addUpdateListener(getOnTranslateYAnimationUpdate());
                    animator5.start();
                }
                if (mScaledWidth < ((float) getWidth()) && mScaledHeight < ((float) getHeight())) {
                    resetDefaultState();
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void resetDefaultState() {
        if (this.translateLeft != 0.0f) {
            ValueAnimator mTranslateXAnimator = getResetXAnimator();
            mTranslateXAnimator.setFloatValues(new float[]{this.translateLeft, 0.0f});
            mTranslateXAnimator.addUpdateListener(getOnTranslateXAnimationUpdate());
            mTranslateXAnimator.start();
        }
        ValueAnimator mTranslateYAnimator = getResetYAnimator();
        mTranslateYAnimator.setFloatValues(new float[]{this.translateTop, getDefaultTranslateTop(getHeight(), this.mBoundHeight)});
        mTranslateYAnimator.addUpdateListener(getOnTranslateYAnimationUpdate());
        mTranslateYAnimator.start();
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int l, int t, int r, int b) {
        super.setFrame(l, t, r, b);
        if (getDrawable() == null) {
            return false;
        }
        if (this.mBoundWidth != 0 && this.mBoundHeight != 0 && this.scale != 1.0f) {
            return false;
        }
        adjustBounds(getWidth(), getHeight());
        return true;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        adjustBounds(w, h);
    }

    private void adjustBounds(int width, int height) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            this.mBoundWidth = drawable.getBounds().width();
            this.mBoundHeight = drawable.getBounds().height();
            this.mBoundHeight = (int) (((float) this.mBoundHeight) / (((float) this.mBoundWidth) / ((float) width)));
            this.mBoundWidth = width;
            drawable.setBounds(0, 0, this.mBoundWidth, this.mBoundHeight);
            this.translateLeft = 0.0f;
            this.translateTop = getDefaultTranslateTop(height, this.mBoundHeight);
        }
    }

    /* access modifiers changed from: private */
    public float getDefaultTranslateTop(int height, int bh) {
        float top = ((float) (height - bh)) / 2.0f;
        if (top > 0.0f) {
            return top;
        }
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        if (mDrawable != null) {
            int mDrawableWidth = mDrawable.getIntrinsicWidth();
            int mDrawableHeight = mDrawable.getIntrinsicHeight();
            if (mDrawableWidth != 0 && mDrawableHeight != 0) {
                int saveCount = canvas.getSaveCount();
                canvas.save();
                canvas.translate(this.translateLeft, this.translateTop);
                canvas.scale(this.scale, this.scale);
                mDrawable.draw(canvas);
                canvas.restoreToCount(saveCount);
            }
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float mOldScaledWidth = ((float) ImagePreviewView.this.mBoundWidth) * ImagePreviewView.this.scale;
            float mOldScaledHeight = ((float) ImagePreviewView.this.mBoundHeight) * ImagePreviewView.this.scale;
            if ((mOldScaledWidth > ((float) ImagePreviewView.this.getWidth()) && ImagePreviewView.this.getDiffX() != 0.0f) || (mOldScaledHeight > ((float) ImagePreviewView.this.getHeight()) && ImagePreviewView.this.getDiffY() != 0.0f)) {
                return false;
            }
            float value = ImagePreviewView.this.scale + ((detector.getScaleFactor() - 1.0f) * 2.0f);
            if (value == ImagePreviewView.this.scale) {
                return true;
            }
            if (value <= ImagePreviewView.mMinScale || value > ImagePreviewView.mMaxScale) {
                return false;
            }
            float unused = ImagePreviewView.this.scale = value;
            float mScaledWidth = ((float) ImagePreviewView.this.mBoundWidth) * ImagePreviewView.this.scale;
            float mScaledHeight = ((float) ImagePreviewView.this.mBoundHeight) * ImagePreviewView.this.scale;
            float unused2 = ImagePreviewView.this.translateLeft = (((float) ImagePreviewView.this.getWidth()) / 2.0f) - ((((((float) ImagePreviewView.this.getWidth()) / 2.0f) - ImagePreviewView.this.translateLeft) * mScaledWidth) / mOldScaledWidth);
            float unused3 = ImagePreviewView.this.translateTop = (((float) ImagePreviewView.this.getHeight()) / 2.0f) - ((((((float) ImagePreviewView.this.getHeight()) / 2.0f) - ImagePreviewView.this.translateTop) * mScaledHeight) / mOldScaledHeight);
            float diffX = ImagePreviewView.this.getDiffX();
            float diffY = ImagePreviewView.this.getDiffY();
            if (diffX > 0.0f && mScaledWidth > ((float) ImagePreviewView.this.getWidth())) {
                float unused4 = ImagePreviewView.this.translateLeft = 0.0f;
            }
            if (diffX < 0.0f && mScaledWidth > ((float) ImagePreviewView.this.getWidth())) {
                float unused5 = ImagePreviewView.this.translateLeft = ((float) ImagePreviewView.this.getWidth()) - mScaledWidth;
            }
            if (diffY > 0.0f && mScaledHeight > ((float) ImagePreviewView.this.getHeight())) {
                float unused6 = ImagePreviewView.this.translateTop = 0.0f;
            }
            if (diffY < 0.0f && mScaledHeight > ((float) ImagePreviewView.this.getHeight())) {
                float unused7 = ImagePreviewView.this.translateTop = ((float) ImagePreviewView.this.getHeight()) - mScaledHeight;
            }
            ImagePreviewView.this.invalidate();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public float getExplicitTranslateLeft(float l) {
        float mScaledWidth = ((float) this.mBoundWidth) * this.scale;
        if (l > 0.0f) {
            l = 0.0f;
        }
        if ((-l) + ((float) getWidth()) > mScaledWidth) {
            return ((float) getWidth()) - mScaledWidth;
        }
        return l;
    }

    /* access modifiers changed from: private */
    public float getExplicitTranslateTop(float t) {
        float mScaledHeight = ((float) this.mBoundHeight) * this.scale;
        if (t > 0.0f) {
            t = 0.0f;
        }
        if ((-t) + ((float) getHeight()) > mScaledHeight) {
            return ((float) getHeight()) - mScaledHeight;
        }
        return t;
    }

    private class FlatGestureListener extends GestureDetector.SimpleOnGestureListener {
        private FlatGestureListener() {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mScaledWidth = ((float) ImagePreviewView.this.mBoundWidth) * ImagePreviewView.this.scale;
            if (((float) ImagePreviewView.this.mBoundHeight) * ImagePreviewView.this.scale > ((float) ImagePreviewView.this.getHeight())) {
                float unused = ImagePreviewView.this.translateTop = (float) (((double) ImagePreviewView.this.translateTop) - (((double) distanceY) * 1.5d));
                float unused2 = ImagePreviewView.this.translateTop = ImagePreviewView.this.getExplicitTranslateTop(ImagePreviewView.this.translateTop);
            }
            boolean isReachBorder = false;
            if (mScaledWidth > ((float) ImagePreviewView.this.getWidth())) {
                float unused3 = ImagePreviewView.this.translateLeft = (float) (((double) ImagePreviewView.this.translateLeft) - (((double) distanceX) * 1.5d));
                float t = ImagePreviewView.this.getExplicitTranslateLeft(ImagePreviewView.this.translateLeft);
                if (t != ImagePreviewView.this.translateLeft) {
                    isReachBorder = true;
                }
                float unused4 = ImagePreviewView.this.translateLeft = t;
            } else {
                isReachBorder = true;
            }
            if (ImagePreviewView.this.onReachBorderListener != null) {
                ImagePreviewView.this.onReachBorderListener.onReachBorder(isReachBorder);
            }
            ImagePreviewView.this.invalidate();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return ImagePreviewView.this.performClick();
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (((float) ImagePreviewView.this.mBoundWidth) * ImagePreviewView.this.scale > ((float) ImagePreviewView.this.getWidth())) {
                float sx = ImagePreviewView.this.getExplicitTranslateLeft(ImagePreviewView.this.translateLeft + (0.5f * velocityX * 0.5f * 0.5f));
                ValueAnimator mResetXAnimator = ImagePreviewView.this.getResetXAnimator();
                mResetXAnimator.setDuration(300);
                mResetXAnimator.setInterpolator(ImagePreviewView.this.mDecInterpolator);
                mResetXAnimator.setFloatValues(new float[]{ImagePreviewView.this.translateLeft, sx});
                mResetXAnimator.addUpdateListener(ImagePreviewView.this.getOnTranslateXAnimationUpdate());
                mResetXAnimator.start();
            }
            if (((float) ImagePreviewView.this.mBoundHeight) * ImagePreviewView.this.scale <= ((float) ImagePreviewView.this.getHeight())) {
                return true;
            }
            float sy = ImagePreviewView.this.getExplicitTranslateTop(ImagePreviewView.this.translateTop + (0.5f * velocityY * 0.5f * 0.5f));
            ValueAnimator mResetYAnimator = ImagePreviewView.this.getResetYAnimator();
            mResetYAnimator.setDuration(300);
            mResetYAnimator.setInterpolator(ImagePreviewView.this.mDecInterpolator);
            mResetYAnimator.setFloatValues(new float[]{ImagePreviewView.this.translateTop, sy});
            mResetYAnimator.addUpdateListener(ImagePreviewView.this.getOnTranslateYAnimationUpdate());
            mResetYAnimator.start();
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            boolean unused = ImagePreviewView.this.isAutoScale = true;
            ValueAnimator mResetScaleAnimator = ImagePreviewView.this.getResetScaleAnimator();
            if (ImagePreviewView.this.scale == 1.0f) {
                mResetScaleAnimator.setFloatValues(new float[]{1.0f, 2.0f});
                ValueAnimator mResetXAnimator = ImagePreviewView.this.getResetXAnimator();
                ValueAnimator mResetYAnimator = ImagePreviewView.this.getResetYAnimator();
                mResetXAnimator.setFloatValues(new float[]{ImagePreviewView.this.translateLeft, (((float) ImagePreviewView.this.getWidth()) - (((float) ImagePreviewView.this.mBoundWidth) * 2.0f)) / 2.0f});
                mResetYAnimator.setFloatValues(new float[]{ImagePreviewView.this.translateTop, ImagePreviewView.this.getDefaultTranslateTop(ImagePreviewView.this.getHeight(), ImagePreviewView.this.mBoundHeight * 2)});
                mResetXAnimator.addUpdateListener(ImagePreviewView.this.getOnTranslateXAnimationUpdate());
                mResetYAnimator.addUpdateListener(ImagePreviewView.this.getOnTranslateYAnimationUpdate());
                mResetXAnimator.start();
                mResetYAnimator.start();
            } else {
                mResetScaleAnimator.setFloatValues(new float[]{ImagePreviewView.this.scale, 1.0f});
                ImagePreviewView.this.resetDefaultState();
            }
            mResetScaleAnimator.addUpdateListener(ImagePreviewView.this.getOnScaleAnimationUpdate());
            mResetScaleAnimator.start();
            return true;
        }
    }
}

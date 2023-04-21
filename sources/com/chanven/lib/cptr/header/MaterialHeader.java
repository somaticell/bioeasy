package com.chanven.lib.cptr.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrUIHandler;
import com.chanven.lib.cptr.PtrUIHandlerHook;
import com.chanven.lib.cptr.indicator.PtrIndicator;

public class MaterialHeader extends View implements PtrUIHandler {
    /* access modifiers changed from: private */
    public MaterialProgressDrawable mDrawable;
    private PtrFrameLayout mPtrFrameLayout;
    /* access modifiers changed from: private */
    public float mScale = 1.0f;
    /* access modifiers changed from: private */
    public Animation mScaleAnimation = new Animation() {
        public void applyTransformation(float interpolatedTime, Transformation t) {
            float unused = MaterialHeader.this.mScale = 1.0f - interpolatedTime;
            MaterialHeader.this.mDrawable.setAlpha((int) (255.0f * MaterialHeader.this.mScale));
            MaterialHeader.this.invalidate();
        }
    };

    public MaterialHeader(Context context) {
        super(context);
        initView();
    }

    public MaterialHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MaterialHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {
        final PtrUIHandlerHook mPtrUIHandlerHook = new PtrUIHandlerHook() {
            public void run() {
                MaterialHeader.this.startAnimation(MaterialHeader.this.mScaleAnimation);
            }
        };
        this.mScaleAnimation.setDuration(200);
        this.mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mPtrUIHandlerHook.resume();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mPtrFrameLayout = layout;
        this.mPtrFrameLayout.setRefreshCompleteHook(mPtrUIHandlerHook);
    }

    private void initView() {
        this.mDrawable = new MaterialProgressDrawable(getContext(), this);
        this.mDrawable.setBackgroundColor(-1);
        this.mDrawable.setCallback(this);
    }

    public void invalidateDrawable(Drawable dr) {
        if (dr == this.mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

    public void setColorSchemeColors(int[] colors) {
        this.mDrawable.setColorSchemeColors(colors);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(this.mDrawable.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom(), 1073741824));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int size = this.mDrawable.getIntrinsicHeight();
        this.mDrawable.setBounds(0, 0, size, size);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        Rect rect = this.mDrawable.getBounds();
        canvas.translate((float) (getPaddingLeft() + ((getMeasuredWidth() - this.mDrawable.getIntrinsicWidth()) / 2)), (float) getPaddingTop());
        canvas.scale(this.mScale, this.mScale, rect.exactCenterX(), rect.exactCenterY());
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    public void onUIReset(PtrFrameLayout frame) {
        this.mScale = 1.0f;
        this.mDrawable.stop();
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    public void onUIRefreshBegin(PtrFrameLayout frame) {
        this.mDrawable.setAlpha(255);
        this.mDrawable.start();
    }

    public void onUIRefreshComplete(PtrFrameLayout frame) {
        this.mDrawable.stop();
    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float percent = Math.min(1.0f, ptrIndicator.getCurrentPercent());
        if (status == 2) {
            this.mDrawable.setAlpha((int) (255.0f * percent));
            this.mDrawable.showArrow(true);
            this.mDrawable.setStartEndTrim(0.0f, Math.min(0.8f, percent * 0.8f));
            this.mDrawable.setArrowScale(Math.min(1.0f, percent));
            this.mDrawable.setProgressRotation((-0.25f + (0.4f * percent) + (2.0f * percent)) * 0.5f);
            invalidate();
        }
    }
}

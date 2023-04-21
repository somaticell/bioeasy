package com.chanven.lib.cptr.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import cn.com.bioeasy.app.utils.ListUtils;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrUIHandler;
import com.chanven.lib.cptr.indicator.PtrIndicator;
import com.chanven.lib.cptr.utils.PtrLocalDisplay;
import java.util.ArrayList;

public class StoreHouseHeader extends View implements PtrUIHandler {
    private AniController mAniController = new AniController();
    private float mBarDarkAlpha = 0.4f;
    private int mDrawZoneHeight = 0;
    private int mDrawZoneWidth = 0;
    private int mDropHeight = -1;
    /* access modifiers changed from: private */
    public float mFromAlpha = 1.0f;
    private int mHorizontalRandomness = -1;
    private float mInternalAnimationFactor = 0.7f;
    private boolean mIsInLoading = false;
    public ArrayList<StoreHouseBarItem> mItemList = new ArrayList<>();
    private int mLineWidth = -1;
    /* access modifiers changed from: private */
    public int mLoadingAniDuration = 1000;
    /* access modifiers changed from: private */
    public int mLoadingAniItemDuration = 400;
    /* access modifiers changed from: private */
    public int mLoadingAniSegDuration = 1000;
    private int mOffsetX = 0;
    private int mOffsetY = 0;
    private float mProgress = 0.0f;
    private float mScale = 1.0f;
    private int mTextColor = -1;
    /* access modifiers changed from: private */
    public float mToAlpha = 0.4f;
    private Transformation mTransformation = new Transformation();

    public StoreHouseHeader(Context context) {
        super(context);
        initView();
    }

    public StoreHouseHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StoreHouseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        PtrLocalDisplay.init(getContext());
        this.mLineWidth = PtrLocalDisplay.dp2px(1.0f);
        this.mDropHeight = PtrLocalDisplay.dp2px(40.0f);
        this.mHorizontalRandomness = PtrLocalDisplay.SCREEN_WIDTH_PIXELS / 2;
    }

    private void setProgress(float progress) {
        this.mProgress = progress;
    }

    public int getLoadingAniDuration() {
        return this.mLoadingAniDuration;
    }

    public void setLoadingAniDuration(int duration) {
        this.mLoadingAniDuration = duration;
        this.mLoadingAniSegDuration = duration;
    }

    public StoreHouseHeader setLineWidth(int width) {
        this.mLineWidth = width;
        for (int i = 0; i < this.mItemList.size(); i++) {
            this.mItemList.get(i).setLineWidth(width);
        }
        return this;
    }

    public StoreHouseHeader setTextColor(int color) {
        this.mTextColor = color;
        for (int i = 0; i < this.mItemList.size(); i++) {
            this.mItemList.get(i).setColor(color);
        }
        return this;
    }

    public StoreHouseHeader setDropHeight(int height) {
        this.mDropHeight = height;
        return this;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(getTopOffset() + this.mDrawZoneHeight + getBottomOffset(), 1073741824));
        this.mOffsetX = (getMeasuredWidth() - this.mDrawZoneWidth) / 2;
        this.mOffsetY = getTopOffset();
        this.mDropHeight = getTopOffset();
    }

    private int getTopOffset() {
        return getPaddingTop() + PtrLocalDisplay.dp2px(10.0f);
    }

    private int getBottomOffset() {
        return getPaddingBottom() + PtrLocalDisplay.dp2px(10.0f);
    }

    public void initWithString(String str) {
        initWithString(str, 25);
    }

    public void initWithString(String str, int fontSize) {
        initWithPointList(StoreHousePath.getPath(str, ((float) fontSize) * 0.01f, 14));
    }

    public void initWithStringArray(int id) {
        String[] points = getResources().getStringArray(id);
        ArrayList<float[]> pointList = new ArrayList<>();
        for (String split : points) {
            String[] x = split.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            float[] f = new float[4];
            for (int j = 0; j < 4; j++) {
                f[j] = Float.parseFloat(x[j]);
            }
            pointList.add(f);
        }
        initWithPointList(pointList);
    }

    public float getScale() {
        return this.mScale;
    }

    public void setScale(float scale) {
        this.mScale = scale;
    }

    public void initWithPointList(ArrayList<float[]> pointList) {
        boolean shouldLayout;
        float drawWidth = 0.0f;
        float drawHeight = 0.0f;
        if (this.mItemList.size() > 0) {
            shouldLayout = true;
        } else {
            shouldLayout = false;
        }
        this.mItemList.clear();
        for (int i = 0; i < pointList.size(); i++) {
            float[] line = pointList.get(i);
            PointF startPoint = new PointF(((float) PtrLocalDisplay.dp2px(line[0])) * this.mScale, ((float) PtrLocalDisplay.dp2px(line[1])) * this.mScale);
            PointF endPoint = new PointF(((float) PtrLocalDisplay.dp2px(line[2])) * this.mScale, ((float) PtrLocalDisplay.dp2px(line[3])) * this.mScale);
            drawWidth = Math.max(Math.max(drawWidth, startPoint.x), endPoint.x);
            drawHeight = Math.max(Math.max(drawHeight, startPoint.y), endPoint.y);
            StoreHouseBarItem item = new StoreHouseBarItem(i, startPoint, endPoint, this.mTextColor, this.mLineWidth);
            item.resetPosition(this.mHorizontalRandomness);
            this.mItemList.add(item);
        }
        this.mDrawZoneWidth = (int) Math.ceil((double) drawWidth);
        this.mDrawZoneHeight = (int) Math.ceil((double) drawHeight);
        if (shouldLayout) {
            requestLayout();
        }
    }

    private void beginLoading() {
        this.mIsInLoading = true;
        this.mAniController.start();
        invalidate();
    }

    private void loadFinish() {
        this.mIsInLoading = false;
        this.mAniController.stop();
    }

    public void onDraw(Canvas canvas) {
        float realProgress;
        super.onDraw(canvas);
        float progress = this.mProgress;
        int c1 = canvas.save();
        int len = this.mItemList.size();
        for (int i = 0; i < len; i++) {
            canvas.save();
            StoreHouseBarItem storeHouseBarItem = this.mItemList.get(i);
            float offsetX = ((float) this.mOffsetX) + storeHouseBarItem.midPoint.x;
            float offsetY = ((float) this.mOffsetY) + storeHouseBarItem.midPoint.y;
            if (this.mIsInLoading) {
                storeHouseBarItem.getTransformation(getDrawingTime(), this.mTransformation);
                canvas.translate(offsetX, offsetY);
            } else if (progress == 0.0f) {
                storeHouseBarItem.resetPosition(this.mHorizontalRandomness);
            } else {
                float startPadding = ((1.0f - this.mInternalAnimationFactor) * ((float) i)) / ((float) len);
                float endPadding = (1.0f - this.mInternalAnimationFactor) - startPadding;
                if (progress == 1.0f || progress >= 1.0f - endPadding) {
                    canvas.translate(offsetX, offsetY);
                    storeHouseBarItem.setAlpha(this.mBarDarkAlpha);
                } else {
                    if (progress <= startPadding) {
                        realProgress = 0.0f;
                    } else {
                        realProgress = Math.min(1.0f, (progress - startPadding) / this.mInternalAnimationFactor);
                    }
                    float offsetX2 = offsetX + (storeHouseBarItem.translationX * (1.0f - realProgress));
                    float offsetY2 = offsetY + (((float) (-this.mDropHeight)) * (1.0f - realProgress));
                    Matrix matrix = new Matrix();
                    matrix.postRotate(360.0f * realProgress);
                    matrix.postScale(realProgress, realProgress);
                    matrix.postTranslate(offsetX2, offsetY2);
                    storeHouseBarItem.setAlpha(this.mBarDarkAlpha * realProgress);
                    canvas.concat(matrix);
                }
            }
            storeHouseBarItem.draw(canvas);
            canvas.restore();
        }
        if (this.mIsInLoading) {
            invalidate();
        }
        canvas.restoreToCount(c1);
    }

    public void onUIReset(PtrFrameLayout frame) {
        loadFinish();
        for (int i = 0; i < this.mItemList.size(); i++) {
            this.mItemList.get(i).resetPosition(this.mHorizontalRandomness);
        }
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    public void onUIRefreshBegin(PtrFrameLayout frame) {
        beginLoading();
    }

    public void onUIRefreshComplete(PtrFrameLayout frame) {
        loadFinish();
    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        setProgress(Math.min(1.0f, ptrIndicator.getCurrentPercent()));
        invalidate();
    }

    private class AniController implements Runnable {
        private int mCountPerSeg;
        private int mInterval;
        private boolean mRunning;
        private int mSegCount;
        private int mTick;

        private AniController() {
            this.mTick = 0;
            this.mCountPerSeg = 0;
            this.mSegCount = 0;
            this.mInterval = 0;
            this.mRunning = true;
        }

        /* access modifiers changed from: private */
        public void start() {
            this.mRunning = true;
            this.mTick = 0;
            this.mInterval = StoreHouseHeader.this.mLoadingAniDuration / StoreHouseHeader.this.mItemList.size();
            this.mCountPerSeg = StoreHouseHeader.this.mLoadingAniSegDuration / this.mInterval;
            this.mSegCount = (StoreHouseHeader.this.mItemList.size() / this.mCountPerSeg) + 1;
            run();
        }

        public void run() {
            int pos = this.mTick % this.mCountPerSeg;
            for (int i = 0; i < this.mSegCount; i++) {
                int index = (this.mCountPerSeg * i) + pos;
                if (index <= this.mTick) {
                    StoreHouseBarItem item = StoreHouseHeader.this.mItemList.get(index % StoreHouseHeader.this.mItemList.size());
                    item.setFillAfter(false);
                    item.setFillEnabled(true);
                    item.setFillBefore(false);
                    item.setDuration((long) StoreHouseHeader.this.mLoadingAniItemDuration);
                    item.start(StoreHouseHeader.this.mFromAlpha, StoreHouseHeader.this.mToAlpha);
                }
            }
            this.mTick++;
            if (this.mRunning) {
                StoreHouseHeader.this.postDelayed(this, (long) this.mInterval);
            }
        }

        /* access modifiers changed from: private */
        public void stop() {
            this.mRunning = false;
            StoreHouseHeader.this.removeCallbacks(this);
        }
    }
}

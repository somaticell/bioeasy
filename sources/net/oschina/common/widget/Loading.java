package net.oschina.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import net.oschina.common.R;
import net.oschina.common.widget.drawable.LoadingCircleDrawable;
import net.oschina.common.widget.drawable.LoadingDrawable;

public class Loading extends View {
    public static int STYLE_CIRCLE = 1;
    public static int STYLE_LINE = 2;
    private boolean mAutoRun;
    private LoadingDrawable mLoadingDrawable;
    private boolean mNeedRun;

    public Loading(Context context) {
        super(context);
        init((AttributeSet) null, 0, 0);
    }

    public Loading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public Loading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public Loading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();
        Resources resource = getResources();
        if (attrs == null) {
            setProgressStyle();
            return;
        }
        int baseSize = (int) (2.0f * resource.getDisplayMetrics().density);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Loading, defStyleAttr, defStyleRes);
        int bgLineSize = a.getDimensionPixelOffset(R.styleable.Loading_gBackgroundLineSize, baseSize);
        int fgLineSize = a.getDimensionPixelOffset(R.styleable.Loading_gForegroundLineSize, baseSize);
        int bgColor = 0;
        ColorStateList colorStateList = a.getColorStateList(R.styleable.Loading_gBackgroundColor);
        if (colorStateList != null) {
            bgColor = colorStateList.getDefaultColor();
        }
        int fgColorId = a.getResourceId(R.styleable.Loading_gForegroundColor, R.array.g_default_loading_fg);
        boolean autoRun = a.getBoolean(R.styleable.Loading_gAutoRun, true);
        float progress = a.getFloat(R.styleable.Loading_gProgressFloat, 0.0f);
        a.recycle();
        setProgressStyle();
        setAutoRun(autoRun);
        setProgress(progress);
        setBackgroundLineSize(bgLineSize);
        setForegroundLineSize(fgLineSize);
        setBackgroundColor(bgColor);
        if (!isInEditMode()) {
            String type = resource.getResourceTypeName(fgColorId);
            char c = 65535;
            try {
                switch (type.hashCode()) {
                    case 93090393:
                        if (type.equals("array")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 94842723:
                        if (type.equals("color")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        setForegroundColor(resource.getColor(fgColorId));
                        return;
                    case 1:
                        setForegroundColor(resource.getIntArray(fgColorId));
                        return;
                    default:
                        setForegroundColor(resource.getIntArray(R.array.g_default_loading_fg));
                        return;
                }
            } catch (Exception e) {
                setForegroundColor(resource.getIntArray(R.array.g_default_loading_fg));
            }
        }
    }

    public void start() {
        this.mLoadingDrawable.start();
        this.mNeedRun = false;
    }

    public void stop() {
        this.mLoadingDrawable.stop();
        this.mNeedRun = false;
    }

    public boolean isRunning() {
        return this.mLoadingDrawable.isRunning();
    }

    public void setBackgroundLineSize(int size) {
        this.mLoadingDrawable.setBackgroundLineSize((float) size);
        invalidate();
        requestLayout();
    }

    public void setForegroundLineSize(int size) {
        this.mLoadingDrawable.setForegroundLineSize((float) size);
        invalidate();
        requestLayout();
    }

    public float getBackgroundLineSize() {
        return this.mLoadingDrawable.getBackgroundLineSize();
    }

    public float getForegroundLineSize() {
        return this.mLoadingDrawable.getForegroundLineSize();
    }

    public void setBackgroundColor(int color) {
        this.mLoadingDrawable.setBackgroundColor(color);
        invalidate();
    }

    public void setBackgroundColorRes(int colorRes) {
        ColorStateList colorStateList = getResources().getColorStateList(colorRes);
        if (colorStateList == null) {
            setBackgroundColor(0);
        } else {
            setBackgroundColor(colorStateList.getDefaultColor());
        }
    }

    public int getBackgroundColor() {
        return this.mLoadingDrawable.getBackgroundColor();
    }

    public void setForegroundColor(int color) {
        setForegroundColor(new int[]{color});
    }

    public void setForegroundColor(int[] colors) {
        this.mLoadingDrawable.setForegroundColor(colors);
        invalidate();
    }

    public int[] getForegroundColor() {
        return this.mLoadingDrawable.getForegroundColor();
    }

    public float getProgress() {
        return this.mLoadingDrawable.getProgress();
    }

    public void setProgress(float progress) {
        this.mLoadingDrawable.setProgress(progress);
        invalidate();
    }

    public void setAutoRun(boolean autoRun) {
        this.mAutoRun = autoRun;
    }

    public boolean isAutoRun() {
        return this.mAutoRun;
    }

    public void setProgressStyle() {
        Resources resources = getResources();
        LoadingDrawable drawable = new LoadingCircleDrawable(resources.getDimensionPixelOffset(R.dimen.g_loading_minSize), resources.getDimensionPixelOffset(R.dimen.g_loading_maxSize));
        drawable.setCallback(this);
        this.mLoadingDrawable = drawable;
        invalidate();
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth;
        int measuredHeight;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int iHeight = this.mLoadingDrawable.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom();
        int iWidth = this.mLoadingDrawable.getIntrinsicWidth() + getPaddingLeft() + getPaddingRight();
        if (widthMode == 1073741824) {
            measuredWidth = widthSize;
        } else if (widthMode == Integer.MIN_VALUE) {
            measuredWidth = Math.min(widthSize, iWidth);
        } else {
            measuredWidth = iWidth;
        }
        if (heightMode == 1073741824) {
            measuredHeight = heightSize;
        } else if (heightMode == Integer.MIN_VALUE) {
            measuredHeight = Math.min(heightSize, iHeight);
        } else {
            measuredHeight = iHeight;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mLoadingDrawable.setBounds(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return who == this.mLoadingDrawable || super.verifyDrawable(who);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mLoadingDrawable.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        changeRunStateByVisibility(visibility);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        changeRunStateByVisibility(visibility);
    }

    private void changeRunStateByVisibility(int visibility) {
        if (this.mLoadingDrawable != null) {
            if (visibility == 0) {
                if (this.mNeedRun) {
                    start();
                }
            } else if (this.mLoadingDrawable.isRunning()) {
                this.mNeedRun = true;
                this.mLoadingDrawable.stop();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAutoRun && this.mLoadingDrawable.getProgress() == 0.0f) {
            if (getVisibility() == 0) {
                this.mLoadingDrawable.start();
            } else {
                this.mNeedRun = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mLoadingDrawable.stop();
    }
}

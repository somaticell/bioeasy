package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.com.bioeasy.healty.app.healthapp.R;

public class SlideDetailsLayout extends ViewGroup {
    private static final int DEFAULT_DURATION = 300;
    private static final float DEFAULT_PERCENT = 0.2f;
    private boolean isFirstShowBehindView;
    private View mBehindView;
    private int mDefaultPanel;
    private long mDuration;
    private View mFrontView;
    private float mInitMotionX;
    private float mInitMotionY;
    /* access modifiers changed from: private */
    public OnSlideDetailsListener mOnSlideDetailsListener;
    private float mPercent;
    /* access modifiers changed from: private */
    public float mSlideOffset;
    /* access modifiers changed from: private */
    public Status mStatus;
    private View mTarget;
    private float mTouchSlop;

    public interface OnSlideDetailsListener {
        void onStatusChanged(Status status);
    }

    public enum Status {
        CLOSE,
        OPEN;

        public static Status valueOf(int stats) {
            if (stats == 0) {
                return CLOSE;
            }
            if (1 == stats) {
                return OPEN;
            }
            return CLOSE;
        }
    }

    public SlideDetailsLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SlideDetailsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDetailsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mStatus = Status.CLOSE;
        this.isFirstShowBehindView = true;
        this.mPercent = DEFAULT_PERCENT;
        this.mDuration = 300;
        this.mDefaultPanel = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideDetailsLayout, defStyleAttr, 0);
        this.mPercent = a.getFloat(0, DEFAULT_PERCENT);
        this.mDuration = (long) a.getInt(1, 300);
        this.mDefaultPanel = a.getInt(2, 0);
        a.recycle();
        this.mTouchSlop = (float) ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setOnSlideDetailsListener(OnSlideDetailsListener listener) {
        this.mOnSlideDetailsListener = listener;
    }

    public void smoothOpen(boolean smooth) {
        if (this.mStatus != Status.OPEN) {
            this.mStatus = Status.OPEN;
            animatorSwitch(0.0f, (float) (-getMeasuredHeight()), true, smooth ? this.mDuration : 0);
        }
    }

    public void smoothClose(boolean smooth) {
        if (this.mStatus != Status.CLOSE) {
            this.mStatus = Status.CLOSE;
            animatorSwitch((float) (-getMeasuredHeight()), 0.0f, true, smooth ? this.mDuration : 0);
        }
    }

    public void setPercent(float percent) {
        this.mPercent = percent;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-2, -2);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new ViewGroup.MarginLayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (1 >= getChildCount()) {
            throw new RuntimeException("SlideDetailsLayout only accept childs more than 1!!");
        }
        this.mFrontView = getChildAt(0);
        this.mBehindView = getChildAt(1);
        if (this.mDefaultPanel == 1) {
            post(new Runnable() {
                public void run() {
                    SlideDetailsLayout.this.smoothOpen(false);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int pWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int pHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(pWidth, 1073741824);
        int childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(pHeight, 1073741824);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
        setMeasuredDimension(pWidth, pHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int top;
        int bottom;
        int left = l;
        int right = r;
        int offset = (int) this.mSlideOffset;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                if (child == this.mBehindView) {
                    top = b + offset;
                    bottom = (top + b) - t;
                } else {
                    top = t + offset;
                    bottom = b + offset;
                }
                child.layout(left, top, right, bottom);
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        if (this.mTarget == null || !isEnabled()) {
            return false;
        }
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                this.mInitMotionX = ev.getX();
                this.mInitMotionY = ev.getY();
                return false;
            case 1:
            case 3:
                return false;
            case 2:
                float x = ev.getX();
                float y = ev.getY();
                float xDiff = x - this.mInitMotionX;
                float yDiff = y - this.mInitMotionY;
                if (canChildScrollVertically((int) yDiff)) {
                    return false;
                }
                float xDiffabs = Math.abs(xDiff);
                float yDiffabs = Math.abs(yDiff);
                if (yDiffabs <= this.mTouchSlop || yDiffabs < xDiffabs) {
                    return false;
                }
                if (this.mStatus == Status.CLOSE && yDiff > 0.0f) {
                    return false;
                }
                if (this.mStatus != Status.OPEN || yDiff >= 0.0f) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        ensureTarget();
        if (this.mTarget == null || !isEnabled()) {
            return false;
        }
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                if (this.mTarget instanceof View) {
                    return true;
                }
                return true;
            case 1:
            case 3:
                finishTouchEvent();
                return false;
            case 2:
                float yDiff = ev.getY() - this.mInitMotionY;
                if (canChildScrollVertically((int) yDiff)) {
                    return false;
                }
                processTouchEvent(yDiff);
                return true;
            default:
                return true;
        }
    }

    private void processTouchEvent(float offset) {
        if (Math.abs(offset) >= this.mTouchSlop) {
            float oldOffset = this.mSlideOffset;
            if (this.mStatus == Status.CLOSE) {
                if (offset >= 0.0f) {
                    this.mSlideOffset = 0.0f;
                } else {
                    this.mSlideOffset = offset;
                }
                if (this.mSlideOffset == oldOffset) {
                    return;
                }
            } else if (this.mStatus == Status.OPEN) {
                float pHeight = (float) (-getMeasuredHeight());
                if (offset <= 0.0f) {
                    this.mSlideOffset = pHeight;
                } else {
                    this.mSlideOffset = pHeight + offset;
                }
                if (this.mSlideOffset == oldOffset) {
                    return;
                }
            }
            requestLayout();
        }
    }

    private void finishTouchEvent() {
        int pHeight = getMeasuredHeight();
        int percent = (int) (((float) pHeight) * this.mPercent);
        float offset = this.mSlideOffset;
        boolean changed = false;
        if (Status.CLOSE == this.mStatus) {
            if (offset <= ((float) (-percent))) {
                this.mSlideOffset = (float) (-pHeight);
                this.mStatus = Status.OPEN;
                changed = true;
            } else {
                this.mSlideOffset = 0.0f;
            }
        } else if (Status.OPEN == this.mStatus) {
            if (((float) pHeight) + offset >= ((float) percent)) {
                this.mSlideOffset = 0.0f;
                this.mStatus = Status.CLOSE;
                changed = true;
            } else {
                this.mSlideOffset = (float) (-pHeight);
            }
        }
        animatorSwitch(offset, this.mSlideOffset, changed);
    }

    private void animatorSwitch(float start, float end) {
        animatorSwitch(start, end, true, this.mDuration);
    }

    private void animatorSwitch(float start, float end, long duration) {
        animatorSwitch(start, end, true, duration);
    }

    private void animatorSwitch(float start, float end, boolean changed) {
        animatorSwitch(start, end, changed, this.mDuration);
    }

    private void animatorSwitch(float start, float end, final boolean changed, long duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{start, end});
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float unused = SlideDetailsLayout.this.mSlideOffset = ((Float) animation.getAnimatedValue()).floatValue();
                SlideDetailsLayout.this.requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (changed) {
                    if (SlideDetailsLayout.this.mStatus == Status.OPEN) {
                        SlideDetailsLayout.this.checkAndFirstOpenPanel();
                    }
                    if (SlideDetailsLayout.this.mOnSlideDetailsListener != null) {
                        SlideDetailsLayout.this.mOnSlideDetailsListener.onStatusChanged(SlideDetailsLayout.this.mStatus);
                    }
                }
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    /* access modifiers changed from: private */
    public void checkAndFirstOpenPanel() {
        if (this.isFirstShowBehindView) {
            this.isFirstShowBehindView = false;
            this.mBehindView.setVisibility(0);
        }
    }

    private void ensureTarget() {
        if (this.mStatus == Status.CLOSE) {
            this.mTarget = this.mFrontView;
        } else {
            this.mTarget = this.mBehindView;
        }
    }

    /* access modifiers changed from: protected */
    public boolean canChildScrollVertically(int direction) {
        if (this.mTarget instanceof AbsListView) {
            return canListViewSroll((AbsListView) this.mTarget);
        }
        if ((this.mTarget instanceof FrameLayout) || (this.mTarget instanceof RelativeLayout) || (this.mTarget instanceof LinearLayout)) {
            for (int i = 0; i < ((ViewGroup) this.mTarget).getChildCount(); i++) {
                View child = ((ViewGroup) this.mTarget).getChildAt(i);
                if (child instanceof AbsListView) {
                    return canListViewSroll((AbsListView) child);
                }
            }
        }
        if (Build.VERSION.SDK_INT < 14) {
            return ViewCompat.canScrollVertically(this.mTarget, -direction) || this.mTarget.getScrollY() > 0;
        }
        return ViewCompat.canScrollVertically(this.mTarget, -direction);
    }

    /* access modifiers changed from: protected */
    public boolean canListViewSroll(AbsListView absListView) {
        if (this.mStatus != Status.OPEN) {
            int count = absListView.getChildCount();
            if (count <= 0 || (absListView.getLastVisiblePosition() >= count - 1 && absListView.getChildAt(count - 1).getBottom() <= absListView.getMeasuredHeight())) {
                return false;
            }
            return true;
        } else if (absListView.getChildCount() <= 0 || (absListView.getFirstVisiblePosition() <= 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop())) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        float unused = ss.offset = this.mSlideOffset;
        int unused2 = ss.status = this.mStatus.ordinal();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mSlideOffset = ss.offset;
        this.mStatus = Status.valueOf(ss.status);
        if (this.mStatus == Status.OPEN) {
            this.mBehindView.setVisibility(0);
        }
        requestLayout();
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        /* access modifiers changed from: private */
        public float offset;
        /* access modifiers changed from: private */
        public int status;

        public SavedState(Parcel source) {
            super(source);
            this.offset = source.readFloat();
            this.status = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.offset);
            out.writeInt(this.status);
        }
    }
}

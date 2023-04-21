package com.anthony.ultimateswipetool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.anthony.ultimateswipetool.R;
import com.anthony.ultimateswipetool.activity.ViewDragHelper;
import java.util.ArrayList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    private static final int[] EDGE_FLAGS = {1, 2, 8, 15};
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    private static final int FULL_ALPHA = 255;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int OVERSCROLL_DISTANCE = 10;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    /* access modifiers changed from: private */
    public Activity mActivity;
    /* access modifiers changed from: private */
    public int mContentLeft;
    /* access modifiers changed from: private */
    public int mContentTop;
    /* access modifiers changed from: private */
    public View mContentView;
    /* access modifiers changed from: private */
    public ViewDragHelper mDragHelper;
    /* access modifiers changed from: private */
    public int mEdgeFlag;
    private boolean mEnable;
    private boolean mInLayout;
    /* access modifiers changed from: private */
    public List<SwipeListener> mListeners;
    private int mScrimColor;
    private float mScrimOpacity;
    /* access modifiers changed from: private */
    public float mScrollPercent;
    /* access modifiers changed from: private */
    public float mScrollThreshold;
    /* access modifiers changed from: private */
    public Drawable mShadowBottom;
    /* access modifiers changed from: private */
    public Drawable mShadowLeft;
    /* access modifiers changed from: private */
    public Drawable mShadowRight;
    /* access modifiers changed from: private */
    public Drawable mShadowTop;
    private Rect mTmpRect;
    /* access modifiers changed from: private */
    public int mTrackingEdge;

    public interface SwipeListener {
        void onEdgeTouch(int i);

        void onScrollOverThreshold();

        void onScrollStateChange(int i, float f);
    }

    public SwipeBackLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SwipeBackLayoutStyle);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;
        this.mEnable = true;
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mTmpRect = new Rect();
        this.mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeBackLayout, defStyle, R.style.SwipeBackLayout);
        int edgeSize = a.getDimensionPixelSize(R.styleable.SwipeBackLayout_edge_size, -1);
        if (edgeSize > 0) {
            setEdgeSize(edgeSize);
        }
        setEdgeTrackingEnabled(EDGE_FLAGS[a.getInt(R.styleable.SwipeBackLayout_edge_flag, 0)]);
        int shadowLeft = a.getResourceId(R.styleable.SwipeBackLayout_shadow_left, R.drawable.shadow_left);
        int shadowRight = a.getResourceId(R.styleable.SwipeBackLayout_shadow_right, R.drawable.shadow_right);
        int shadowBottom = a.getResourceId(R.styleable.SwipeBackLayout_shadow_bottom, R.drawable.shadow_bottom);
        int shadowTop = a.getResourceId(R.styleable.SwipeBackLayout_shadow_top, R.drawable.shadow_top);
        setShadow(shadowLeft, 1);
        setShadow(shadowRight, 2);
        setShadow(shadowBottom, 8);
        setShadow(shadowTop, 4);
        a.recycle();
        float minVel = 400.0f * getResources().getDisplayMetrics().density;
        this.mDragHelper.setMinVelocity(minVel);
        this.mDragHelper.setMaxVelocity(2.0f * minVel);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.mEnable) {
            return false;
        }
        try {
            return this.mDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mEnable) {
            return false;
        }
        this.mDragHelper.processTouchEvent(event);
        return true;
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        private boolean mIsScrollOverValid;

        private ViewDragCallback() {
        }

        public boolean tryCaptureView(View child, int pointerId) {
            boolean ret = SwipeBackLayout.this.mDragHelper.isEdgeTouched(SwipeBackLayout.this.mEdgeFlag, pointerId);
            if (ret) {
                if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(1, pointerId)) {
                    int unused = SwipeBackLayout.this.mTrackingEdge = 1;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(2, pointerId)) {
                    int unused2 = SwipeBackLayout.this.mTrackingEdge = 2;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(8, pointerId)) {
                    int unused3 = SwipeBackLayout.this.mTrackingEdge = 8;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(4, pointerId)) {
                    int unused4 = SwipeBackLayout.this.mTrackingEdge = 4;
                }
                if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty()) {
                    for (SwipeListener listener : SwipeBackLayout.this.mListeners) {
                        listener.onEdgeTouch(SwipeBackLayout.this.mTrackingEdge);
                    }
                }
                this.mIsScrollOverValid = true;
            }
            boolean directionCheck = false;
            if (SwipeBackLayout.this.mEdgeFlag == 1 || SwipeBackLayout.this.mEdgeFlag == 2) {
                directionCheck = !SwipeBackLayout.this.mDragHelper.checkTouchSlop(2, pointerId);
            } else if (SwipeBackLayout.this.mEdgeFlag == 8 || SwipeBackLayout.this.mEdgeFlag == 4) {
                directionCheck = !SwipeBackLayout.this.mDragHelper.checkTouchSlop(1, pointerId);
            } else if (SwipeBackLayout.this.mEdgeFlag == 15) {
                directionCheck = true;
            }
            return ret & directionCheck;
        }

        public int getViewHorizontalDragRange(View child) {
            return SwipeBackLayout.this.mEdgeFlag & 3;
        }

        public int getViewVerticalDragRange(View child) {
            return SwipeBackLayout.this.mEdgeFlag & 12;
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                float unused = SwipeBackLayout.this.mScrollPercent = Math.abs(((float) left) / ((float) (SwipeBackLayout.this.mContentView.getWidth() + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth())));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                float unused2 = SwipeBackLayout.this.mScrollPercent = Math.abs(((float) left) / ((float) (SwipeBackLayout.this.mContentView.getWidth() + SwipeBackLayout.this.mShadowRight.getIntrinsicWidth())));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                float unused3 = SwipeBackLayout.this.mScrollPercent = Math.abs(((float) top) / ((float) (SwipeBackLayout.this.mContentView.getHeight() + SwipeBackLayout.this.mShadowBottom.getIntrinsicHeight())));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 4) != 0) {
                float unused4 = SwipeBackLayout.this.mScrollPercent = Math.abs(((float) top) / ((float) (SwipeBackLayout.this.mContentView.getHeight() + SwipeBackLayout.this.mShadowTop.getIntrinsicHeight())));
            }
            int unused5 = SwipeBackLayout.this.mContentLeft = left;
            int unused6 = SwipeBackLayout.this.mContentTop = top;
            SwipeBackLayout.this.invalidate();
            if (SwipeBackLayout.this.mScrollPercent < SwipeBackLayout.this.mScrollThreshold && !this.mIsScrollOverValid) {
                this.mIsScrollOverValid = true;
            }
            if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty() && SwipeBackLayout.this.mDragHelper.getViewDragState() == 1 && SwipeBackLayout.this.mScrollPercent >= SwipeBackLayout.this.mScrollThreshold && this.mIsScrollOverValid) {
                this.mIsScrollOverValid = false;
                for (SwipeListener listener : SwipeBackLayout.this.mListeners) {
                    listener.onScrollOverThreshold();
                }
            }
            if (SwipeBackLayout.this.mScrollPercent >= 1.0f && !SwipeBackLayout.this.mActivity.isFinishing()) {
                SwipeBackLayout.this.mActivity.finish();
                SwipeBackLayout.this.mActivity.overridePendingTransition(0, 0);
            }
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int childWidth = releasedChild.getWidth();
            int childHeight = releasedChild.getHeight();
            int left = 0;
            int top = 0;
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                left = (xvel > 0.0f || (xvel == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth() + childWidth + 10 : 0;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                left = (xvel < 0.0f || (xvel == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? -(SwipeBackLayout.this.mShadowRight.getIntrinsicWidth() + childWidth + 10) : 0;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                top = (yvel < 0.0f || (yvel == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? -(SwipeBackLayout.this.mShadowBottom.getIntrinsicHeight() + childHeight + 10) : 0;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 4) != 0) {
                top = (yvel > 0.0f || (yvel == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? SwipeBackLayout.this.mShadowTop.getIntrinsicHeight() + childHeight + 10 : 0;
            }
            SwipeBackLayout.this.mDragHelper.settleCapturedViewAt(left, top);
            SwipeBackLayout.this.invalidate();
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                return Math.min(child.getWidth(), Math.max(left, 0));
            }
            if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                return Math.min(0, Math.max(left, -child.getWidth()));
            }
            return 0;
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            int ret = 0;
            if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                ret = Math.min(0, Math.max(top, -child.getHeight()));
            }
            if ((SwipeBackLayout.this.mTrackingEdge & 4) != 0) {
                return Math.min(child.getHeight(), Math.max(top, 0));
            }
            return ret;
        }

        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty()) {
                for (SwipeListener listener : SwipeBackLayout.this.mListeners) {
                    listener.onScrollStateChange(state, SwipeBackLayout.this.mScrollPercent);
                }
            }
        }
    }

    public void setSensitivity(Context context, float sensitivity) {
        this.mDragHelper.setSensitivity(context, sensitivity);
    }

    private void setContentView(View view) {
        this.mContentView = view;
    }

    public void setEnableGesture(boolean enable) {
        this.mEnable = enable;
    }

    public void setEdgeTrackingEnabled(int edgeFlags) {
        this.mEdgeFlag = edgeFlags;
        this.mDragHelper.setEdgeTrackingEnabled(this.mEdgeFlag);
    }

    @Deprecated
    public void setSwipeListener(SwipeListener listener) {
        addSwipeListener(listener);
    }

    public void addSwipeListener(SwipeListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(listener);
    }

    public void removeSwipeListener(SwipeListener listener) {
        if (this.mListeners != null) {
            this.mListeners.remove(listener);
        }
    }

    public void setShadow(Drawable shadow, int edgeFlag) {
        if ((edgeFlag & 1) != 0) {
            this.mShadowLeft = shadow;
        } else if ((edgeFlag & 2) != 0) {
            this.mShadowRight = shadow;
        } else if ((edgeFlag & 8) != 0) {
            this.mShadowBottom = shadow;
        } else if ((edgeFlag & 4) != 0) {
            this.mShadowTop = shadow;
        }
        invalidate();
    }

    public void setShadow(int resId, int edgeFlag) {
        setShadow(getResources().getDrawable(resId), edgeFlag);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mInLayout = true;
        if (this.mContentView != null) {
            this.mContentView.layout(this.mContentLeft, this.mContentTop, this.mContentLeft + this.mContentView.getMeasuredWidth(), this.mContentTop + this.mContentView.getMeasuredHeight());
        }
        this.mInLayout = false;
    }

    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean drawContent = child == this.mContentView;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (this.mScrimOpacity > 0.0f && drawContent && this.mDragHelper.getViewDragState() != 0) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawShadow(Canvas canvas, View child) {
        Rect childRect = this.mTmpRect;
        child.getHitRect(childRect);
        if ((this.mEdgeFlag & 1) != 0) {
            this.mShadowLeft.setBounds(childRect.left - this.mShadowLeft.getIntrinsicWidth(), childRect.top, childRect.left, childRect.bottom);
            this.mShadowLeft.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowLeft.draw(canvas);
        }
        if ((this.mEdgeFlag & 2) != 0) {
            this.mShadowRight.setBounds(childRect.right, childRect.top, childRect.right + this.mShadowRight.getIntrinsicWidth(), childRect.bottom);
            this.mShadowRight.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowRight.draw(canvas);
        }
        if ((this.mEdgeFlag & 8) != 0) {
            this.mShadowBottom.setBounds(childRect.left, childRect.bottom, childRect.right, childRect.bottom + this.mShadowBottom.getIntrinsicHeight());
            this.mShadowBottom.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowBottom.draw(canvas);
        }
        if ((this.mEdgeFlag & 4) != 0) {
            this.mShadowTop.setBounds(childRect.left, childRect.top - this.mShadowTop.getIntrinsicHeight(), childRect.right, childRect.top);
            this.mShadowTop.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowTop.draw(canvas);
        }
    }

    private void drawScrim(Canvas canvas, View child) {
        int color = (((int) (((float) ((this.mScrimColor & -16777216) >>> 24)) * this.mScrimOpacity)) << 24) | (this.mScrimColor & ViewCompat.MEASURED_SIZE_MASK);
        if ((this.mTrackingEdge & 1) != 0) {
            canvas.clipRect(0, 0, child.getLeft(), getHeight());
        } else if ((this.mTrackingEdge & 2) != 0) {
            canvas.clipRect(child.getRight(), 0, getRight(), getHeight());
        } else if ((this.mTrackingEdge & 8) != 0) {
            canvas.clipRect(child.getLeft(), child.getBottom(), getRight(), getHeight());
        } else if ((this.mTrackingEdge & 4) != 0) {
            canvas.clipRect(child.getLeft(), getHeight(), getRight(), child.getTop());
        }
        canvas.drawColor(color);
    }

    public void scrollToFinishActivity() {
        int childWidth = this.mContentView.getWidth();
        int childHeight = this.mContentView.getHeight();
        int left = 0;
        int top = 0;
        if ((this.mEdgeFlag & 1) != 0) {
            left = this.mShadowLeft.getIntrinsicWidth() + childWidth + 10;
            this.mTrackingEdge = 1;
        } else if ((this.mEdgeFlag & 2) != 0) {
            left = ((-childWidth) - this.mShadowRight.getIntrinsicWidth()) - 10;
            this.mTrackingEdge = 2;
        } else if ((this.mEdgeFlag & 8) != 0) {
            top = ((-childHeight) - this.mShadowBottom.getIntrinsicHeight()) - 10;
            this.mTrackingEdge = 8;
        } else if ((this.mEdgeFlag & 4) != 0) {
            top = ((-childHeight) + this.mShadowTop.getIntrinsicHeight()) - 10;
            this.mTrackingEdge = 4;
        }
        this.mDragHelper.smoothSlideViewTo(this.mContentView, left, top);
        invalidate();
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    public void computeScroll() {
        this.mScrimOpacity = 1.0f - this.mScrollPercent;
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setScrimColor(int color) {
        this.mScrimColor = color;
        invalidate();
    }

    public void setEdgeSize(int size) {
        this.mDragHelper.setEdgeSize(size);
    }
}

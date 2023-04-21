package cn.com.bioeasy.app.widgets.pullrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;
import cn.com.bioeasy.app.commonlib.R;

public class PullToRefreshView extends ViewGroup {
    private int AUTO_SCROLL_PULL_DOWN = 3;
    /* access modifiers changed from: private */
    public int IDLE = 0;
    private int PULL_DOWN = 1;
    private int PULL_UP = 2;
    private View contentView;
    private float deltaX = 0.0f;
    private float deltaY = 0.0f;
    private View footer;
    private View header;
    private boolean isInControl = false;
    /* access modifiers changed from: private */
    public boolean isLoading = false;
    private boolean isNeedIntercept;
    private boolean isPullDownEnable = true;
    private boolean isPullUpEnable = true;
    private int mActivePointerId = -1;
    private int mFooterActionPosition;
    private int mFooterHoldingPosition;
    private BaseIndicator mFooterIndicator;
    private String mFooterIndicatorClassName;
    private int mHeaderActionPosition;
    private int mHeaderHoldingPosition;
    private BaseIndicator mHeaderIndicator;
    private String mHeaderIndicatorClassName;
    private LayoutInflater mInflater;
    private float mLastX;
    private float mLastY;
    private OnRefreshListener mListener;
    private OverScroller mScroller;
    private long mStartLoadingTime;
    /* access modifiers changed from: private */
    public int mStatus = this.IDLE;
    private VelocityTracker mVelocityTracker;
    private float yVelocity;

    public interface OnRefreshListener {
        void onLoadMore();

        void onRefresh();
    }

    public PullToRefreshView(Context context) {
        super(context);
    }

    public PullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = LayoutInflater.from(context);
        this.mScroller = new OverScroller(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
        if (ta.hasValue(R.styleable.PullToRefresh_header_indicator)) {
            this.mHeaderIndicatorClassName = ta.getString(R.styleable.PullToRefresh_header_indicator);
        }
        if (ta.hasValue(R.styleable.PullToRefresh_footer_indicator)) {
            this.mFooterIndicatorClassName = ta.getString(R.styleable.PullToRefresh_footer_indicator);
        }
        ta.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        if (getChildCount() != 1) {
            throw new RuntimeException("The child of VIPullToRefresh should be only one!!!");
        }
        this.contentView = getChildAt(0);
        setPadding(0, 0, 0, 0);
        this.contentView.setPadding(0, 0, 0, 0);
        this.mHeaderIndicator = getIndicator(this.mHeaderIndicatorClassName);
        if (this.mHeaderIndicator == null) {
            this.mHeaderIndicator = new DefaultHeader();
        }
        this.header = this.mHeaderIndicator.createView(this.mInflater, this);
        this.mFooterIndicator = getIndicator(this.mFooterIndicatorClassName);
        if (this.mFooterIndicator == null) {
            this.mFooterIndicator = new DefaultFooter();
        }
        this.footer = this.mFooterIndicator.createView(this.mInflater, this);
        this.contentView.bringToFront();
        super.onFinishInflate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            }
        }
        this.mHeaderActionPosition = (this.header.getMeasuredHeight() / 3) + this.header.getMeasuredHeight();
        this.mFooterActionPosition = (this.footer.getMeasuredHeight() / 3) + this.footer.getMeasuredHeight();
        this.mHeaderHoldingPosition = this.header.getMeasuredHeight();
        this.mFooterHoldingPosition = this.footer.getMeasuredHeight();
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.contentView != null) {
            if (this.header != null) {
                this.header.layout(0, -this.header.getMeasuredHeight(), getWidth(), 0);
            }
            if (this.footer != null) {
                this.footer.layout(0, getHeight(), getWidth(), getHeight() + this.footer.getMeasuredHeight());
            }
            this.contentView.layout(0, 0, this.contentView.getMeasuredWidth(), this.contentView.getMeasuredHeight());
        }
    }

    private boolean isNeedIntercept() {
        if ((this.deltaY > 0.0f && isContentScrollToTop()) || getScrollY() < -10) {
            this.mStatus = this.PULL_DOWN;
            return true;
        } else if ((this.deltaY >= 0.0f || !isContentScrollToBottom()) && getScrollY() <= 10) {
            return false;
        } else {
            this.mStatus = this.PULL_UP;
            return true;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        dealMultiTouch(ev);
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    this.mVelocityTracker.clear();
                }
                this.mVelocityTracker.addMovement(ev);
                break;
            case 1:
                autoBackToPosition();
                this.isNeedIntercept = false;
                break;
            case 2:
                this.mVelocityTracker.addMovement(ev);
                this.mVelocityTracker.computeCurrentVelocity(500);
                this.yVelocity = this.mVelocityTracker.getYVelocity();
                this.isNeedIntercept = isNeedIntercept();
                if (this.isNeedIntercept && !this.isInControl) {
                    this.isInControl = true;
                    ev.setAction(3);
                    MotionEvent ev2 = MotionEvent.obtain(ev);
                    dispatchTouchEvent(ev);
                    ev2.setAction(0);
                    return dispatchTouchEvent(ev2);
                }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isNeedIntercept;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 1:
                autoBackToPosition();
                break;
            case 2:
                if ((this.isPullDownEnable || this.deltaY <= 0.0f || getScrollY() > 0) && (this.isPullUpEnable || this.deltaY >= 0.0f || getScrollY() < 0)) {
                    if (this.isNeedIntercept) {
                        if ((this.mStatus != this.PULL_DOWN || getScrollY() <= 0) && (this.mStatus != this.PULL_UP || getScrollY() >= 0)) {
                            scrollBy(0, (int) (-getMoveFloat(this.yVelocity, this.deltaY)));
                            updateIndicator();
                            break;
                        }
                    } else {
                        ev.setAction(0);
                        dispatchTouchEvent(ev);
                        this.isInControl = false;
                        break;
                    }
                }
        }
        return true;
    }

    public void dealMultiTouch(MotionEvent ev) {
        int newPointerIndex = 0;
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                float x = MotionEventCompat.getX(ev, pointerIndex);
                float y = MotionEventCompat.getY(ev, pointerIndex);
                this.mLastX = x;
                this.mLastY = y;
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                return;
            case 1:
            case 3:
                this.mActivePointerId = -1;
                return;
            case 2:
                int pointerIndex2 = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                float x2 = MotionEventCompat.getX(ev, pointerIndex2);
                float y2 = MotionEventCompat.getY(ev, pointerIndex2);
                this.deltaX = x2 - this.mLastX;
                this.deltaY = y2 - this.mLastY;
                this.mLastY = y2;
                this.mLastX = x2;
                return;
            case 5:
                int pointerIndex3 = MotionEventCompat.getActionIndex(ev);
                if (MotionEventCompat.getPointerId(ev, pointerIndex3) != this.mActivePointerId) {
                    this.mLastX = MotionEventCompat.getX(ev, pointerIndex3);
                    this.mLastY = MotionEventCompat.getY(ev, pointerIndex3);
                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex3);
                    return;
                }
                return;
            case 6:
                int pointerIndex4 = MotionEventCompat.getActionIndex(ev);
                if (MotionEventCompat.getPointerId(ev, pointerIndex4) == this.mActivePointerId) {
                    if (pointerIndex4 == 0) {
                        newPointerIndex = 1;
                    }
                    this.mLastX = MotionEventCompat.getX(ev, newPointerIndex);
                    this.mLastY = MotionEventCompat.getY(ev, newPointerIndex);
                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(0, this.mScroller.getCurrY());
            invalidate();
        }
    }

    private float getMoveFloat(float velocity, float org2) {
        return (((10000.0f - Math.abs(velocity)) / 10000.0f) * org2) / 1.5f;
    }

    private void autoBackToPosition() {
        if (this.mStatus == this.PULL_DOWN && Math.abs(getScrollY()) < this.mHeaderActionPosition) {
            autoBackToOriginalPosition();
        } else if (this.mStatus == this.PULL_DOWN && Math.abs(getScrollY()) > this.mHeaderActionPosition) {
            autoBackToLoadingPosition();
        } else if (this.mStatus == this.PULL_UP && Math.abs(getScrollY()) < this.mFooterActionPosition) {
            autoBackToOriginalPosition();
        } else if (this.mStatus == this.PULL_UP && Math.abs(getScrollY()) > this.mFooterActionPosition) {
            autoBackToLoadingPosition();
        }
    }

    private void autoBackToLoadingPosition() {
        this.mStartLoadingTime = System.currentTimeMillis();
        if (this.mStatus == this.PULL_DOWN) {
            this.mScroller.startScroll(0, getScrollY(), 0, (-getScrollY()) - this.mHeaderHoldingPosition, 400);
            invalidate();
            if (!this.isLoading) {
                this.isLoading = true;
                this.mListener.onRefresh();
            }
        }
        if (this.mStatus == this.PULL_UP) {
            this.mScroller.startScroll(0, getScrollY(), 0, this.mFooterHoldingPosition - getScrollY(), 400);
            invalidate();
            if (!this.isLoading) {
                this.isLoading = true;
                this.mListener.onLoadMore();
            }
        }
        loadingIndicator();
    }

    /* access modifiers changed from: private */
    public void autoBackToOriginalPosition() {
        this.mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 400);
        invalidate();
        postDelayed(new Runnable() {
            public void run() {
                PullToRefreshView.this.restoreIndicator();
                int unused = PullToRefreshView.this.mStatus = PullToRefreshView.this.IDLE;
            }
        }, 500);
    }

    private boolean isContentScrollToTop() {
        return !ViewCompat.canScrollVertically(this.contentView, -1);
    }

    private boolean isContentScrollToBottom() {
        return !ViewCompat.canScrollVertically(this.contentView, 1);
    }

    private void updateIndicator() {
        if (this.mStatus != this.PULL_DOWN || this.deltaY <= 0.0f) {
            if (this.mStatus != this.PULL_DOWN || this.deltaY >= 0.0f) {
                if (this.mStatus != this.PULL_UP || this.deltaY >= 0.0f) {
                    if (this.mStatus == this.PULL_UP && this.deltaY > 0.0f && Math.abs(getScrollY()) < this.mFooterActionPosition) {
                        this.mFooterIndicator.onUnaction();
                    }
                } else if (Math.abs(getScrollY()) > this.mFooterActionPosition) {
                    this.mFooterIndicator.onAction();
                }
            } else if (Math.abs(getScrollY()) < this.mHeaderActionPosition) {
                this.mHeaderIndicator.onUnaction();
            }
        } else if (Math.abs(getScrollY()) > this.mHeaderActionPosition) {
            this.mHeaderIndicator.onAction();
        }
    }

    /* access modifiers changed from: private */
    public void restoreIndicator() {
        this.mHeaderIndicator.onRestore();
        this.mFooterIndicator.onRestore();
    }

    private void loadingIndicator() {
        if (this.mStatus == this.PULL_DOWN) {
            this.mHeaderIndicator.onLoading();
        }
        if (this.mStatus == this.PULL_UP) {
            this.mFooterIndicator.onLoading();
        }
    }

    public void onFinishLoading() {
        if (System.currentTimeMillis() - this.mStartLoadingTime > 2000) {
            this.isLoading = false;
            autoBackToOriginalPosition();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                boolean unused = PullToRefreshView.this.isLoading = false;
                PullToRefreshView.this.autoBackToOriginalPosition();
            }
        }, 1000);
    }

    public void onAutoRefresh() {
        this.mStartLoadingTime = System.currentTimeMillis();
        this.mStatus = this.PULL_DOWN;
        this.mScroller.startScroll(0, getScrollY(), 0, -this.mHeaderHoldingPosition, 400);
        invalidate();
        if (!this.isLoading) {
            this.isLoading = true;
            this.mListener.onRefresh();
        }
        loadingIndicator();
    }

    private BaseIndicator getIndicator(String className) {
        if (!TextUtils.isEmpty(className)) {
            try {
                return (BaseIndicator) Class.forName(className).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setListener(OnRefreshListener mListener2) {
        this.mListener = mListener2;
    }

    public boolean isPullDownEnable() {
        return this.isPullDownEnable;
    }

    public void setPullDownEnable(boolean pullDownEnable) {
        this.isPullDownEnable = pullDownEnable;
    }

    public boolean isPullUpEnable() {
        return this.isPullUpEnable;
    }

    public void setPullUpEnable(boolean pullUpEnable) {
        this.isPullUpEnable = pullUpEnable;
    }
}

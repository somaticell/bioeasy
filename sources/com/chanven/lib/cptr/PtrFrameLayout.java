package com.chanven.lib.cptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Scroller;
import android.widget.TextView;
import com.chanven.lib.cptr.indicator.PtrIndicator;
import com.chanven.lib.cptr.loadmore.DefaultLoadMoreViewFooter;
import com.chanven.lib.cptr.loadmore.GridViewHandler;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;
import com.chanven.lib.cptr.loadmore.ListViewHandler;
import com.chanven.lib.cptr.loadmore.LoadMoreHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.loadmore.OnScrollBottomListener;
import com.chanven.lib.cptr.loadmore.RecyclerViewHandler;
import com.chanven.lib.cptr.utils.PtrCLog;

public class PtrFrameLayout extends ViewGroup {
    public static boolean DEBUG = false;
    private static final boolean DEBUG_LAYOUT = true;
    private static byte FLAG_AUTO_REFRESH_AT_ONCE = 1;
    private static byte FLAG_AUTO_REFRESH_BUT_LATER = 2;
    private static byte FLAG_ENABLE_NEXT_PTR_AT_ONCE = 4;
    private static byte FLAG_PIN_CONTENT = 8;
    private static int ID = 1;
    private static byte MASK_AUTO_REFRESH = 3;
    public static final byte PTR_STATUS_COMPLETE = 4;
    public static final byte PTR_STATUS_INIT = 1;
    public static final byte PTR_STATUS_LOADING = 3;
    public static final byte PTR_STATUS_PREPARE = 2;
    protected final String LOG_TAG;
    private boolean hasInitLoadMoreView;
    /* access modifiers changed from: private */
    public boolean isAutoLoadMoreEnable;
    /* access modifiers changed from: private */
    public boolean isLoadMoreEnable;
    private boolean isLoadingMore;
    private ILoadMoreViewFactory loadMoreViewFactory;
    private int mContainerId;
    protected View mContent;
    private View mContentView;
    private boolean mDisableWhenHorizontalMove;
    private int mDurationToClose;
    private int mDurationToCloseHeader;
    private int mFlag;
    private boolean mHasSendCancelEvent;
    private int mHeaderHeight;
    private int mHeaderId;
    private View mHeaderView;
    private boolean mKeepHeaderWhenRefresh;
    private MotionEvent mLastMoveEvent;
    private LoadMoreHandler mLoadMoreHandler;
    private ILoadMoreViewFactory.ILoadMoreView mLoadMoreView;
    private int mLoadingMinTime;
    private long mLoadingStartTime;
    OnLoadMoreListener mOnLoadMoreListener;
    private int mPagingTouchSlop;
    private boolean mPreventForHorizontal;
    private PtrHandler mPtrHandler;
    /* access modifiers changed from: private */
    public PtrIndicator mPtrIndicator;
    private PtrUIHandlerHolder mPtrUIHandlerHolder;
    private boolean mPullToRefresh;
    private PtrUIHandlerHook mRefreshCompleteHook;
    private ScrollChecker mScrollChecker;
    private byte mStatus;
    private View.OnClickListener onClickLoadMoreListener;
    private OnScrollBottomListener onScrollBottomListener;

    public PtrFrameLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public PtrFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        StringBuilder append = new StringBuilder().append("ptr-frame-");
        int i = ID + 1;
        ID = i;
        this.LOG_TAG = append.append(i).toString();
        this.mHeaderId = 0;
        this.mContainerId = 0;
        this.mDurationToClose = 200;
        this.mDurationToCloseHeader = 1000;
        this.mKeepHeaderWhenRefresh = true;
        this.mPullToRefresh = false;
        this.mPtrUIHandlerHolder = PtrUIHandlerHolder.create();
        this.mStatus = 1;
        this.mDisableWhenHorizontalMove = false;
        this.mFlag = 0;
        this.mPreventForHorizontal = false;
        this.mLoadingMinTime = 500;
        this.mLoadingStartTime = 0;
        this.mHasSendCancelEvent = false;
        this.isLoadingMore = false;
        this.isAutoLoadMoreEnable = true;
        this.isLoadMoreEnable = false;
        this.hasInitLoadMoreView = false;
        this.onScrollBottomListener = new OnScrollBottomListener() {
            public void onScorllBootom() {
                if (PtrFrameLayout.this.isAutoLoadMoreEnable && PtrFrameLayout.this.isLoadMoreEnable && !PtrFrameLayout.this.isLoadingMore()) {
                    PtrFrameLayout.this.loadMore();
                }
            }
        };
        this.onClickLoadMoreListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (PtrFrameLayout.this.isLoadMoreEnable && !PtrFrameLayout.this.isLoadingMore()) {
                    PtrFrameLayout.this.loadMore();
                }
            }
        };
        this.mPtrIndicator = new PtrIndicator();
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.PtrFrameLayout, 0, 0);
        if (arr != null) {
            this.mHeaderId = arr.getResourceId(R.styleable.PtrFrameLayout_ptr_header, this.mHeaderId);
            this.mContainerId = arr.getResourceId(R.styleable.PtrFrameLayout_ptr_content, this.mContainerId);
            this.mPtrIndicator.setResistance(arr.getFloat(R.styleable.PtrFrameLayout_ptr_resistance, this.mPtrIndicator.getResistance()));
            this.mDurationToClose = arr.getInt(R.styleable.PtrFrameLayout_ptr_duration_to_close, this.mDurationToClose);
            this.mDurationToCloseHeader = arr.getInt(R.styleable.PtrFrameLayout_ptr_duration_to_close_header, this.mDurationToCloseHeader);
            this.mPtrIndicator.setRatioOfHeaderHeightToRefresh(arr.getFloat(R.styleable.PtrFrameLayout_ptr_ratio_of_header_height_to_refresh, this.mPtrIndicator.getRatioOfHeaderToHeightRefresh()));
            this.mKeepHeaderWhenRefresh = arr.getBoolean(R.styleable.PtrFrameLayout_ptr_keep_header_when_refresh, this.mKeepHeaderWhenRefresh);
            this.mPullToRefresh = arr.getBoolean(R.styleable.PtrFrameLayout_ptr_pull_to_fresh, this.mPullToRefresh);
            arr.recycle();
        }
        this.mScrollChecker = new ScrollChecker();
        this.mPagingTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() * 2;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount > 2) {
            throw new IllegalStateException("PtrFrameLayout only can host 2 elements");
        }
        if (childCount == 2) {
            if (this.mHeaderId != 0 && this.mHeaderView == null) {
                this.mHeaderView = findViewById(this.mHeaderId);
            }
            if (this.mContainerId != 0 && this.mContent == null) {
                this.mContent = findViewById(this.mContainerId);
            }
            if (this.mContent == null || this.mHeaderView == null) {
                View child1 = getChildAt(0);
                View child2 = getChildAt(1);
                if (child1 instanceof PtrUIHandler) {
                    this.mHeaderView = child1;
                    this.mContent = child2;
                } else if (child2 instanceof PtrUIHandler) {
                    this.mHeaderView = child2;
                    this.mContent = child1;
                } else if (this.mContent == null && this.mHeaderView == null) {
                    this.mHeaderView = child1;
                    this.mContent = child2;
                } else if (this.mHeaderView == null) {
                    if (this.mContent != child1) {
                        child2 = child1;
                    }
                    this.mHeaderView = child2;
                } else {
                    if (this.mHeaderView != child1) {
                        child2 = child1;
                    }
                    this.mContent = child2;
                }
            }
        } else if (childCount == 1) {
            this.mContent = getChildAt(0);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setTextColor(-39424);
            errorView.setGravity(17);
            errorView.setTextSize(20.0f);
            errorView.setText("The content view in PtrFrameLayout is empty. Do you forget to specify its id in xml layout file?");
            this.mContent = errorView;
            addView(this.mContent);
        }
        if (this.mHeaderView != null) {
            this.mHeaderView.bringToFront();
        }
        super.onFinishInflate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (DEBUG) {
            PtrCLog.d(this.LOG_TAG, "onMeasure frame: width: %s, height: %s, padding: %s %s %s %s", Integer.valueOf(getMeasuredHeight()), Integer.valueOf(getMeasuredWidth()), Integer.valueOf(getPaddingLeft()), Integer.valueOf(getPaddingRight()), Integer.valueOf(getPaddingTop()), Integer.valueOf(getPaddingBottom()));
        }
        if (this.mHeaderView != null) {
            measureChildWithMargins(this.mHeaderView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mHeaderView.getLayoutParams();
            this.mHeaderHeight = this.mHeaderView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            this.mPtrIndicator.setHeaderHeight(this.mHeaderHeight);
        }
        if (this.mContent != null) {
            measureContentView(this.mContent, widthMeasureSpec, heightMeasureSpec);
            if (DEBUG) {
                ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) this.mContent.getLayoutParams();
                PtrCLog.d(this.LOG_TAG, "onMeasure content, width: %s, height: %s, margin: %s %s %s %s", Integer.valueOf(getMeasuredWidth()), Integer.valueOf(getMeasuredHeight()), Integer.valueOf(lp2.leftMargin), Integer.valueOf(lp2.topMargin), Integer.valueOf(lp2.rightMargin), Integer.valueOf(lp2.bottomMargin));
                PtrCLog.d(this.LOG_TAG, "onMeasure, currentPos: %s, lastPos: %s, top: %s", Integer.valueOf(this.mPtrIndicator.getCurrentPosY()), Integer.valueOf(this.mPtrIndicator.getLastPosY()), Integer.valueOf(this.mContent.getTop()));
            }
        }
    }

    private void measureContentView(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        child.measure(getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin, lp.width), getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin, lp.height));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean flag, int i, int j, int k, int l) {
        layoutChildren();
    }

    private void layoutChildren() {
        int offsetX = this.mPtrIndicator.getCurrentPosY();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (this.mHeaderView != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mHeaderView.getLayoutParams();
            int left = paddingLeft + lp.leftMargin;
            int top = ((lp.topMargin + paddingTop) + offsetX) - this.mHeaderHeight;
            int right = left + this.mHeaderView.getMeasuredWidth();
            int bottom = top + this.mHeaderView.getMeasuredHeight();
            this.mHeaderView.layout(left, top, right, bottom);
            if (DEBUG) {
                PtrCLog.d(this.LOG_TAG, "onLayout header: %s %s %s %s", Integer.valueOf(left), Integer.valueOf(top), Integer.valueOf(right), Integer.valueOf(bottom));
            }
        }
        if (this.mContent != null) {
            if (isPinContent()) {
                offsetX = 0;
            }
            ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) this.mContent.getLayoutParams();
            int left2 = paddingLeft + lp2.leftMargin;
            int top2 = lp2.topMargin + paddingTop + offsetX;
            int right2 = left2 + this.mContent.getMeasuredWidth();
            int bottom2 = top2 + this.mContent.getMeasuredHeight();
            if (DEBUG) {
                PtrCLog.d(this.LOG_TAG, "onLayout content: %s %s %s %s", Integer.valueOf(left2), Integer.valueOf(top2), Integer.valueOf(right2), Integer.valueOf(bottom2));
            }
            this.mContent.layout(left2, top2, right2, bottom2);
        }
    }

    public boolean dispatchTouchEventSupper(MotionEvent e) {
        return super.dispatchTouchEvent(e);
    }

    public boolean dispatchTouchEvent(MotionEvent e) {
        boolean moveDown;
        boolean moveUp;
        boolean canMoveDown;
        if (!isEnabled() || this.mContent == null || this.mHeaderView == null) {
            return dispatchTouchEventSupper(e);
        }
        switch (e.getAction()) {
            case 0:
                this.mHasSendCancelEvent = false;
                this.mPtrIndicator.onPressDown(e.getX(), e.getY());
                this.mScrollChecker.abortIfWorking();
                this.mPreventForHorizontal = false;
                dispatchTouchEventSupper(e);
                return true;
            case 1:
            case 3:
                this.mPtrIndicator.onRelease();
                if (!this.mPtrIndicator.hasLeftStartPosition()) {
                    return dispatchTouchEventSupper(e);
                }
                if (DEBUG) {
                    PtrCLog.d(this.LOG_TAG, "call onRelease when user release");
                }
                onRelease(false);
                if (!this.mPtrIndicator.hasMovedAfterPressedDown()) {
                    return dispatchTouchEventSupper(e);
                }
                sendCancelEvent();
                return true;
            case 2:
                this.mLastMoveEvent = e;
                this.mPtrIndicator.onMove(e.getX(), e.getY());
                float offsetX = this.mPtrIndicator.getOffsetX();
                float offsetY = this.mPtrIndicator.getOffsetY();
                if (this.mDisableWhenHorizontalMove && !this.mPreventForHorizontal && Math.abs(offsetX) > ((float) this.mPagingTouchSlop) && Math.abs(offsetX) > Math.abs(offsetY) && this.mPtrIndicator.isInStartPosition()) {
                    this.mPreventForHorizontal = true;
                }
                if (this.mPreventForHorizontal) {
                    return dispatchTouchEventSupper(e);
                }
                if (offsetY > 0.0f) {
                    moveDown = true;
                } else {
                    moveDown = false;
                }
                if (!moveDown) {
                    moveUp = true;
                } else {
                    moveUp = false;
                }
                boolean canMoveUp = this.mPtrIndicator.hasLeftStartPosition();
                if (DEBUG) {
                    if (this.mPtrHandler == null || !this.mPtrHandler.checkCanDoRefresh(this, this.mContent, this.mHeaderView)) {
                        canMoveDown = false;
                    } else {
                        canMoveDown = true;
                    }
                    PtrCLog.v(this.LOG_TAG, "ACTION_MOVE: offsetY:%s, currentPos: %s, moveUp: %s, canMoveUp: %s, moveDown: %s: canMoveDown: %s", Float.valueOf(offsetY), Integer.valueOf(this.mPtrIndicator.getCurrentPosY()), Boolean.valueOf(moveUp), Boolean.valueOf(canMoveUp), Boolean.valueOf(moveDown), Boolean.valueOf(canMoveDown));
                }
                if (moveDown && this.mPtrHandler != null && !this.mPtrHandler.checkCanDoRefresh(this, this.mContent, this.mHeaderView)) {
                    return dispatchTouchEventSupper(e);
                }
                if ((moveUp && canMoveUp) || moveDown) {
                    movePos(offsetY);
                    return true;
                }
                break;
        }
        return dispatchTouchEventSupper(e);
    }

    /* access modifiers changed from: private */
    public void movePos(float deltaY) {
        if (deltaY >= 0.0f || !this.mPtrIndicator.isInStartPosition()) {
            int to = this.mPtrIndicator.getCurrentPosY() + ((int) deltaY);
            if (this.mPtrIndicator.willOverTop(to)) {
                if (DEBUG) {
                    PtrCLog.e(this.LOG_TAG, String.format("over top", new Object[0]));
                }
                to = 0;
            }
            this.mPtrIndicator.setCurrentPos(to);
            updatePos(to - this.mPtrIndicator.getLastPosY());
        } else if (DEBUG) {
            PtrCLog.e(this.LOG_TAG, String.format("has reached the top", new Object[0]));
        }
    }

    private void updatePos(int change) {
        if (change != 0) {
            boolean isUnderTouch = this.mPtrIndicator.isUnderTouch();
            if (isUnderTouch && !this.mHasSendCancelEvent && this.mPtrIndicator.hasMovedAfterPressedDown()) {
                this.mHasSendCancelEvent = true;
                sendCancelEvent();
            }
            if ((this.mPtrIndicator.hasJustLeftStartPosition() && this.mStatus == 1) || (this.mPtrIndicator.goDownCrossFinishPosition() && this.mStatus == 4 && isEnabledNextPtrAtOnce())) {
                this.mStatus = 2;
                this.mPtrUIHandlerHolder.onUIRefreshPrepare(this);
                if (DEBUG) {
                    PtrCLog.i(this.LOG_TAG, "PtrUIHandler: onUIRefreshPrepare, mFlag %s", Integer.valueOf(this.mFlag));
                }
            }
            if (this.mPtrIndicator.hasJustBackToStartPosition()) {
                tryToNotifyReset();
                if (isUnderTouch) {
                    sendDownEvent();
                }
            }
            if (this.mStatus == 2) {
                if (isUnderTouch && !isAutoRefresh() && this.mPullToRefresh && this.mPtrIndicator.crossRefreshLineFromTopToBottom()) {
                    tryToPerformRefresh();
                }
                if (performAutoRefreshButLater() && this.mPtrIndicator.hasJustReachedHeaderHeightFromTopToBottom()) {
                    tryToPerformRefresh();
                }
            }
            if (DEBUG) {
                PtrCLog.v(this.LOG_TAG, "updatePos: change: %s, current: %s last: %s, top: %s, headerHeight: %s", Integer.valueOf(change), Integer.valueOf(this.mPtrIndicator.getCurrentPosY()), Integer.valueOf(this.mPtrIndicator.getLastPosY()), Integer.valueOf(this.mContent.getTop()), Integer.valueOf(this.mHeaderHeight));
            }
            this.mHeaderView.offsetTopAndBottom(change);
            if (!isPinContent()) {
                this.mContent.offsetTopAndBottom(change);
            }
            invalidate();
            if (this.mPtrUIHandlerHolder.hasHandler()) {
                this.mPtrUIHandlerHolder.onUIPositionChange(this, isUnderTouch, this.mStatus, this.mPtrIndicator);
            }
            onPositionChange(isUnderTouch, this.mStatus, this.mPtrIndicator);
        }
    }

    /* access modifiers changed from: protected */
    public void onPositionChange(boolean isInTouching, byte status, PtrIndicator mPtrIndicator2) {
    }

    public int getHeaderHeight() {
        return this.mHeaderHeight;
    }

    private void onRelease(boolean stayForLoading) {
        tryToPerformRefresh();
        if (this.mStatus == 3) {
            if (!this.mKeepHeaderWhenRefresh) {
                tryScrollBackToTopWhileLoading();
            } else if (this.mPtrIndicator.isOverOffsetToKeepHeaderWhileLoading() && !stayForLoading) {
                this.mScrollChecker.tryToScrollTo(this.mPtrIndicator.getOffsetToKeepHeaderWhileLoading(), this.mDurationToClose);
            }
        } else if (this.mStatus == 4) {
            notifyUIRefreshComplete(false);
        } else {
            tryScrollBackToTopAbortRefresh();
        }
    }

    public void setRefreshCompleteHook(PtrUIHandlerHook hook) {
        this.mRefreshCompleteHook = hook;
        hook.setResumeAction(new Runnable() {
            public void run() {
                if (PtrFrameLayout.DEBUG) {
                    PtrCLog.d(PtrFrameLayout.this.LOG_TAG, "mRefreshCompleteHook resume.");
                }
                PtrFrameLayout.this.notifyUIRefreshComplete(true);
            }
        });
    }

    private void tryScrollBackToTop() {
        if (!this.mPtrIndicator.isUnderTouch()) {
            this.mScrollChecker.tryToScrollTo(0, this.mDurationToCloseHeader);
        }
    }

    private void tryScrollBackToTopWhileLoading() {
        tryScrollBackToTop();
    }

    private void tryScrollBackToTopAfterComplete() {
        tryScrollBackToTop();
    }

    private void tryScrollBackToTopAbortRefresh() {
        tryScrollBackToTop();
    }

    private boolean tryToPerformRefresh() {
        if (this.mStatus == 2 && ((this.mPtrIndicator.isOverOffsetToKeepHeaderWhileLoading() && isAutoRefresh()) || this.mPtrIndicator.isOverOffsetToRefresh())) {
            this.mStatus = 3;
            performRefresh();
        }
        return false;
    }

    private void performRefresh() {
        this.mLoadingStartTime = System.currentTimeMillis();
        if (this.mPtrUIHandlerHolder.hasHandler()) {
            this.mPtrUIHandlerHolder.onUIRefreshBegin(this);
            if (DEBUG) {
                PtrCLog.i(this.LOG_TAG, "PtrUIHandler: onUIRefreshBegin");
            }
        }
        if (this.mPtrHandler != null) {
            this.mPtrHandler.onRefreshBegin(this);
        }
    }

    private boolean tryToNotifyReset() {
        if ((this.mStatus != 4 && this.mStatus != 2) || !this.mPtrIndicator.isInStartPosition()) {
            return false;
        }
        if (this.mPtrUIHandlerHolder.hasHandler()) {
            this.mPtrUIHandlerHolder.onUIReset(this);
            if (DEBUG) {
                PtrCLog.i(this.LOG_TAG, "PtrUIHandler: onUIReset");
            }
        }
        this.mStatus = 1;
        clearFlag();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPtrScrollAbort() {
        if (this.mPtrIndicator.hasLeftStartPosition() && isAutoRefresh()) {
            if (DEBUG) {
                PtrCLog.d(this.LOG_TAG, "call onRelease after scroll abort");
            }
            onRelease(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onPtrScrollFinish() {
        if (this.mPtrIndicator.hasLeftStartPosition() && isAutoRefresh()) {
            if (DEBUG) {
                PtrCLog.d(this.LOG_TAG, "call onRelease after scroll finish");
            }
            onRelease(true);
        }
    }

    public boolean isRefreshing() {
        return this.mStatus == 3;
    }

    public final void refreshComplete() {
        if (DEBUG) {
            PtrCLog.i(this.LOG_TAG, "refreshComplete");
        }
        if (this.mRefreshCompleteHook != null) {
            this.mRefreshCompleteHook.reset();
        }
        int delay = (int) (((long) this.mLoadingMinTime) - (System.currentTimeMillis() - this.mLoadingStartTime));
        if (delay <= 0) {
            if (DEBUG) {
                PtrCLog.d(this.LOG_TAG, "performRefreshComplete at once");
            }
            performRefreshComplete();
            return;
        }
        postDelayed(new Runnable() {
            public void run() {
                PtrFrameLayout.this.performRefreshComplete();
            }
        }, (long) delay);
        if (DEBUG) {
            PtrCLog.d(this.LOG_TAG, "performRefreshComplete after delay: %s", Integer.valueOf(delay));
        }
    }

    /* access modifiers changed from: private */
    public void performRefreshComplete() {
        this.mStatus = 4;
        if (!this.mScrollChecker.mIsRunning || !isAutoRefresh()) {
            notifyUIRefreshComplete(false);
        } else if (DEBUG) {
            PtrCLog.d(this.LOG_TAG, "performRefreshComplete do nothing, scrolling: %s, auto refresh: %s", Boolean.valueOf(this.mScrollChecker.mIsRunning), Integer.valueOf(this.mFlag));
        }
    }

    /* access modifiers changed from: private */
    public void notifyUIRefreshComplete(boolean ignoreHook) {
        if (!this.mPtrIndicator.hasLeftStartPosition() || ignoreHook || this.mRefreshCompleteHook == null) {
            if (this.mPtrUIHandlerHolder.hasHandler()) {
                if (DEBUG) {
                    PtrCLog.i(this.LOG_TAG, "PtrUIHandler: onUIRefreshComplete");
                }
                this.mPtrUIHandlerHolder.onUIRefreshComplete(this);
            }
            this.mPtrIndicator.onUIRefreshComplete();
            tryScrollBackToTopAfterComplete();
            tryToNotifyReset();
            return;
        }
        if (DEBUG) {
            PtrCLog.d(this.LOG_TAG, "notifyUIRefreshComplete mRefreshCompleteHook run.");
        }
        this.mRefreshCompleteHook.takeOver();
    }

    public void autoRefresh() {
        autoRefresh(true, this.mDurationToCloseHeader);
    }

    public void autoRefresh(boolean atOnce) {
        autoRefresh(atOnce, this.mDurationToCloseHeader);
    }

    private void clearFlag() {
        this.mFlag &= MASK_AUTO_REFRESH ^ -1;
    }

    public void autoRefresh(boolean atOnce, int duration) {
        if (this.mStatus == 1) {
            this.mFlag = (atOnce ? FLAG_AUTO_REFRESH_AT_ONCE : FLAG_AUTO_REFRESH_BUT_LATER) | this.mFlag;
            this.mStatus = 2;
            if (this.mPtrUIHandlerHolder.hasHandler()) {
                this.mPtrUIHandlerHolder.onUIRefreshPrepare(this);
                if (DEBUG) {
                    PtrCLog.i(this.LOG_TAG, "PtrUIHandler: onUIRefreshPrepare, mFlag %s", Integer.valueOf(this.mFlag));
                }
            }
            this.mScrollChecker.tryToScrollTo(this.mPtrIndicator.getOffsetToRefresh(), duration);
            if (atOnce) {
                this.mStatus = 3;
                performRefresh();
            }
        }
    }

    public boolean isAutoRefresh() {
        return (this.mFlag & MASK_AUTO_REFRESH) > 0;
    }

    private boolean performAutoRefreshButLater() {
        return (this.mFlag & MASK_AUTO_REFRESH) == FLAG_AUTO_REFRESH_BUT_LATER;
    }

    public void setEnabledNextPtrAtOnce(boolean enable) {
        if (enable) {
            this.mFlag |= FLAG_ENABLE_NEXT_PTR_AT_ONCE;
        } else {
            this.mFlag &= FLAG_ENABLE_NEXT_PTR_AT_ONCE ^ -1;
        }
    }

    public boolean isEnabledNextPtrAtOnce() {
        return (this.mFlag & FLAG_ENABLE_NEXT_PTR_AT_ONCE) > 0;
    }

    public void setPinContent(boolean pinContent) {
        if (pinContent) {
            this.mFlag |= FLAG_PIN_CONTENT;
        } else {
            this.mFlag &= FLAG_PIN_CONTENT ^ -1;
        }
    }

    public boolean isPinContent() {
        return (this.mFlag & FLAG_PIN_CONTENT) > 0;
    }

    public void disableWhenHorizontalMove(boolean disable) {
        this.mDisableWhenHorizontalMove = disable;
    }

    public void setLoadingMinTime(int time) {
        this.mLoadingMinTime = time;
    }

    @Deprecated
    public void setInterceptEventWhileWorking(boolean yes) {
    }

    public View getContentView() {
        return this.mContent;
    }

    public void setPtrHandler(PtrHandler ptrHandler) {
        this.mPtrHandler = ptrHandler;
    }

    public void addPtrUIHandler(PtrUIHandler ptrUIHandler) {
        PtrUIHandlerHolder.addHandler(this.mPtrUIHandlerHolder, ptrUIHandler);
    }

    public void removePtrUIHandler(PtrUIHandler ptrUIHandler) {
        this.mPtrUIHandlerHolder = PtrUIHandlerHolder.removeHandler(this.mPtrUIHandlerHolder, ptrUIHandler);
    }

    public void setPtrIndicator(PtrIndicator slider) {
        if (!(this.mPtrIndicator == null || this.mPtrIndicator == slider)) {
            slider.convertFrom(this.mPtrIndicator);
        }
        this.mPtrIndicator = slider;
    }

    public float getResistance() {
        return this.mPtrIndicator.getResistance();
    }

    public void setResistance(float resistance) {
        this.mPtrIndicator.setResistance(resistance);
    }

    public float getDurationToClose() {
        return (float) this.mDurationToClose;
    }

    public void setDurationToClose(int duration) {
        this.mDurationToClose = duration;
    }

    public long getDurationToCloseHeader() {
        return (long) this.mDurationToCloseHeader;
    }

    public void setDurationToCloseHeader(int duration) {
        this.mDurationToCloseHeader = duration;
    }

    public void setRatioOfHeaderHeightToRefresh(float ratio) {
        this.mPtrIndicator.setRatioOfHeaderHeightToRefresh(ratio);
    }

    public int getOffsetToRefresh() {
        return this.mPtrIndicator.getOffsetToRefresh();
    }

    public void setOffsetToRefresh(int offset) {
        this.mPtrIndicator.setOffsetToRefresh(offset);
    }

    public float getRatioOfHeaderToHeightRefresh() {
        return this.mPtrIndicator.getRatioOfHeaderToHeightRefresh();
    }

    public void setOffsetToKeepHeaderWhileLoading(int offset) {
        this.mPtrIndicator.setOffsetToKeepHeaderWhileLoading(offset);
    }

    public int getOffsetToKeepHeaderWhileLoading() {
        return this.mPtrIndicator.getOffsetToKeepHeaderWhileLoading();
    }

    public boolean isKeepHeaderWhenRefresh() {
        return this.mKeepHeaderWhenRefresh;
    }

    public void setKeepHeaderWhenRefresh(boolean keepOrNot) {
        this.mKeepHeaderWhenRefresh = keepOrNot;
    }

    public boolean isPullToRefresh() {
        return this.mPullToRefresh;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        this.mPullToRefresh = pullToRefresh;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public void setHeaderView(View header) {
        if (!(this.mHeaderView == null || header == null || this.mHeaderView == header)) {
            removeView(this.mHeaderView);
        }
        if (header.getLayoutParams() == null) {
            header.setLayoutParams(new LayoutParams(-1, -2));
        }
        this.mHeaderView = header;
        addView(header);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p != null && (p instanceof LayoutParams);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    private void sendCancelEvent() {
        if (DEBUG) {
            PtrCLog.d(this.LOG_TAG, "send cancel event");
        }
        if (this.mLastMoveEvent != null) {
            MotionEvent last = this.mLastMoveEvent;
            dispatchTouchEventSupper(MotionEvent.obtain(last.getDownTime(), last.getEventTime() + ((long) ViewConfiguration.getLongPressTimeout()), 3, last.getX(), last.getY(), last.getMetaState()));
        }
    }

    private void sendDownEvent() {
        if (DEBUG) {
            PtrCLog.d(this.LOG_TAG, "send down event");
        }
        MotionEvent last = this.mLastMoveEvent;
        dispatchTouchEventSupper(MotionEvent.obtain(last.getDownTime(), last.getEventTime(), 0, last.getX(), last.getY(), last.getMetaState()));
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    class ScrollChecker implements Runnable {
        /* access modifiers changed from: private */
        public boolean mIsRunning = false;
        private int mLastFlingY;
        private Scroller mScroller;
        private int mStart;
        private int mTo;

        public ScrollChecker() {
            this.mScroller = new Scroller(PtrFrameLayout.this.getContext());
        }

        public void run() {
            boolean finish;
            if (!this.mScroller.computeScrollOffset() || this.mScroller.isFinished()) {
                finish = true;
            } else {
                finish = false;
            }
            int curY = this.mScroller.getCurrY();
            int deltaY = curY - this.mLastFlingY;
            if (PtrFrameLayout.DEBUG && deltaY != 0) {
                PtrCLog.v(PtrFrameLayout.this.LOG_TAG, "scroll: %s, start: %s, to: %s, currentPos: %s, current :%s, last: %s, delta: %s", Boolean.valueOf(finish), Integer.valueOf(this.mStart), Integer.valueOf(this.mTo), Integer.valueOf(PtrFrameLayout.this.mPtrIndicator.getCurrentPosY()), Integer.valueOf(curY), Integer.valueOf(this.mLastFlingY), Integer.valueOf(deltaY));
            }
            if (!finish) {
                this.mLastFlingY = curY;
                PtrFrameLayout.this.movePos((float) deltaY);
                PtrFrameLayout.this.post(this);
                return;
            }
            finish();
        }

        private void finish() {
            if (PtrFrameLayout.DEBUG) {
                PtrCLog.v(PtrFrameLayout.this.LOG_TAG, "finish, currentPos:%s", Integer.valueOf(PtrFrameLayout.this.mPtrIndicator.getCurrentPosY()));
            }
            reset();
            PtrFrameLayout.this.onPtrScrollFinish();
        }

        private void reset() {
            this.mIsRunning = false;
            this.mLastFlingY = 0;
            PtrFrameLayout.this.removeCallbacks(this);
        }

        public void abortIfWorking() {
            if (this.mIsRunning) {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.forceFinished(true);
                }
                PtrFrameLayout.this.onPtrScrollAbort();
                reset();
            }
        }

        public void tryToScrollTo(int to, int duration) {
            if (!PtrFrameLayout.this.mPtrIndicator.isAlreadyHere(to)) {
                this.mStart = PtrFrameLayout.this.mPtrIndicator.getCurrentPosY();
                this.mTo = to;
                int distance = to - this.mStart;
                if (PtrFrameLayout.DEBUG) {
                    PtrCLog.d(PtrFrameLayout.this.LOG_TAG, "tryToScrollTo: start: %s, distance:%s, to:%s", Integer.valueOf(this.mStart), Integer.valueOf(distance), Integer.valueOf(to));
                }
                PtrFrameLayout.this.removeCallbacks(this);
                this.mLastFlingY = 0;
                if (!this.mScroller.isFinished()) {
                    this.mScroller.forceFinished(true);
                }
                this.mScroller.startScroll(0, 0, 0, distance, duration);
                PtrFrameLayout.this.post(this);
                this.mIsRunning = true;
            }
        }
    }

    public void setAutoLoadMoreEnable(boolean isAutoLoadMoreEnable2) {
        this.isAutoLoadMoreEnable = isAutoLoadMoreEnable2;
    }

    public void setFooterView(ILoadMoreViewFactory factory) {
        if (factory == null) {
            return;
        }
        if (this.loadMoreViewFactory == null || this.loadMoreViewFactory != factory) {
            this.loadMoreViewFactory = factory;
            if (this.hasInitLoadMoreView) {
                this.mLoadMoreHandler.removeFooter();
                this.mLoadMoreView = this.loadMoreViewFactory.madeLoadMoreView();
                this.hasInitLoadMoreView = this.mLoadMoreHandler.handleSetAdapter(this.mContentView, this.mLoadMoreView, this.onClickLoadMoreListener);
                if (!this.isLoadMoreEnable) {
                    this.mLoadMoreHandler.removeFooter();
                }
            }
        }
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        if (this.isLoadMoreEnable != loadMoreEnable) {
            this.isLoadMoreEnable = loadMoreEnable;
            if (!this.hasInitLoadMoreView && this.isLoadMoreEnable) {
                this.mContentView = getContentView();
                if (this.loadMoreViewFactory == null) {
                    this.loadMoreViewFactory = new DefaultLoadMoreViewFooter();
                }
                this.mLoadMoreView = this.loadMoreViewFactory.madeLoadMoreView();
                if (this.mLoadMoreHandler == null) {
                    if (this.mContentView instanceof GridView) {
                        this.mLoadMoreHandler = new GridViewHandler();
                    } else if (this.mContentView instanceof AbsListView) {
                        this.mLoadMoreHandler = new ListViewHandler();
                    } else if (this.mContentView instanceof RecyclerView) {
                        this.mLoadMoreHandler = new RecyclerViewHandler();
                    }
                }
                if (this.mLoadMoreHandler == null) {
                    throw new IllegalStateException("unSupported contentView !");
                }
                this.hasInitLoadMoreView = this.mLoadMoreHandler.handleSetAdapter(this.mContentView, this.mLoadMoreView, this.onClickLoadMoreListener);
                this.mLoadMoreHandler.setOnScrollBottomListener(this.mContentView, this.onScrollBottomListener);
            } else if (!this.hasInitLoadMoreView) {
            } else {
                if (this.isLoadMoreEnable) {
                    this.mLoadMoreHandler.addFooter();
                } else {
                    this.mLoadMoreHandler.removeFooter();
                }
            }
        }
    }

    public boolean isLoadMoreEnable() {
        return this.isLoadMoreEnable;
    }

    /* access modifiers changed from: package-private */
    public void loadMore() {
        this.isLoadingMore = true;
        this.mLoadMoreView.showLoading();
        this.mOnLoadMoreListener.loadMore();
    }

    public void loadMoreComplete(boolean hasMore) {
        this.isLoadingMore = false;
        this.isLoadMoreEnable = hasMore;
        if (hasMore) {
            this.mLoadMoreView.showNormal();
        } else {
            setNoMoreData();
        }
    }

    public void setNoMoreData() {
        this.mLoadMoreView.showNomore();
    }

    public boolean isLoadingMore() {
        return this.isLoadingMore;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mOnLoadMoreListener = loadMoreListener;
    }
}

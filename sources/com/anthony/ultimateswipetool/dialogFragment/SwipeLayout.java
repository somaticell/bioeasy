package com.anthony.ultimateswipetool.dialogFragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import com.anthony.ultimateswipetool.R;

public class SwipeLayout extends FrameLayout implements View.OnTouchListener {
    private long mAnimationTime;
    /* access modifiers changed from: private */
    public DismissCallbacks mCallbacks;
    private View mContentView;
    private Context mContext;
    private float mDownX;
    private float mDownY;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private int mSlop;
    private boolean mSwiping;
    private int mSwipingSlop;
    private boolean mTiltEnabled;
    /* access modifiers changed from: private */
    public Object mToken;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    /* access modifiers changed from: private */
    public View mView;
    private int mViewWidth;

    public interface DismissCallbacks {
        boolean canDismiss(Object obj);

        void onDismiss(View view, boolean z, Object obj);
    }

    public SwipeLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SwipeBackLayoutStyle);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mViewWidth = 1;
        this.mTiltEnabled = true;
        this.mContext = context;
        init();
    }

    private void init() {
        ViewConfiguration vc = ViewConfiguration.get(this.mContext);
        this.mSlop = vc.getScaledTouchSlop();
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        this.mAnimationTime = (long) this.mContext.getResources().getInteger(17694720);
        setOnTouchListener(this);
    }

    public void setTiltEnabled(boolean tiltEnabled) {
        this.mTiltEnabled = tiltEnabled;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        final boolean dismissRight;
        float f;
        motionEvent.offsetLocation(this.mTranslationX, 0.0f);
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mView.getWidth();
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mDownX = motionEvent.getRawX();
                this.mDownY = motionEvent.getRawY();
                if (this.mCallbacks.canDismiss(this.mToken)) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                    this.mVelocityTracker.addMovement(motionEvent);
                }
                return false;
            case 1:
                if (this.mVelocityTracker != null) {
                    float deltaX = motionEvent.getRawX() - this.mDownX;
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float velocityX = this.mVelocityTracker.getXVelocity();
                    float absVelocityX = Math.abs(velocityX);
                    float absVelocityY = Math.abs(this.mVelocityTracker.getYVelocity());
                    boolean dismiss = false;
                    if (Math.abs(deltaX) > ((float) (this.mViewWidth / 2)) && this.mSwiping) {
                        dismiss = true;
                        dismissRight = deltaX > 0.0f;
                    } else if (((float) this.mMinFlingVelocity) > absVelocityX || absVelocityX > ((float) this.mMaxFlingVelocity) || absVelocityY >= absVelocityX || absVelocityY >= absVelocityX || !this.mSwiping) {
                        dismissRight = false;
                    } else {
                        dismiss = ((velocityX > 0.0f ? 1 : (velocityX == 0.0f ? 0 : -1)) < 0) == ((deltaX > 0.0f ? 1 : (deltaX == 0.0f ? 0 : -1)) < 0);
                        dismissRight = this.mVelocityTracker.getXVelocity() > 0.0f;
                    }
                    if (dismiss) {
                        ViewPropertyAnimator translationX = this.mView.animate().translationX(dismissRight ? (float) this.mViewWidth : (float) (-this.mViewWidth));
                        if (this.mTiltEnabled) {
                            f = (float) (dismissRight ? 45 : -45);
                        } else {
                            f = 0.0f;
                        }
                        translationX.rotation(f).alpha(0.0f).setDuration(this.mAnimationTime).setListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                SwipeLayout.this.performDismiss(dismissRight);
                            }
                        });
                    } else if (this.mSwiping) {
                        this.mView.animate().translationX(0.0f).rotation(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener((Animator.AnimatorListener) null);
                    }
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mTranslationX = 0.0f;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mSwiping = false;
                    break;
                }
                break;
            case 2:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    float deltaX2 = motionEvent.getRawX() - this.mDownX;
                    float deltaY = motionEvent.getRawY() - this.mDownY;
                    if (Math.abs(deltaX2) > ((float) this.mSlop) && Math.abs(deltaY) < Math.abs(deltaX2) / 2.0f) {
                        this.mSwiping = true;
                        if (deltaX2 > 0.0f) {
                            i = this.mSlop;
                        } else {
                            i = -this.mSlop;
                        }
                        this.mSwipingSlop = i;
                        this.mView.getParent().requestDisallowInterceptTouchEvent(true);
                        MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                        cancelEvent.setAction((motionEvent.getActionIndex() << 8) | 3);
                        this.mView.onTouchEvent(cancelEvent);
                        cancelEvent.recycle();
                    }
                    if (this.mSwiping) {
                        this.mTranslationX = deltaX2;
                        this.mView.setTranslationX(deltaX2 - ((float) this.mSwipingSlop));
                        this.mView.setRotation(this.mTiltEnabled ? (45.0f * deltaX2) / ((float) this.mViewWidth) : 0.0f);
                        this.mView.setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - ((2.0f * Math.abs(deltaX2)) / ((float) this.mViewWidth)))));
                        return true;
                    }
                }
                break;
            case 3:
                if (this.mVelocityTracker != null) {
                    this.mView.animate().translationX(0.0f).rotation(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener((Animator.AnimatorListener) null);
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mTranslationX = 0.0f;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mSwiping = false;
                    break;
                }
                break;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void performDismiss(final boolean toRight) {
        final ViewGroup.LayoutParams lp = this.mView.getLayoutParams();
        final int originalHeight = this.mView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{originalHeight, 1}).setDuration(this.mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeLayout.this.mCallbacks.onDismiss(SwipeLayout.this.mView, toRight, SwipeLayout.this.mToken);
                SwipeLayout.this.mView.setAlpha(1.0f);
                SwipeLayout.this.mView.setTranslationX(0.0f);
                SwipeLayout.this.mView.setRotation(0.0f);
                lp.height = originalHeight;
                SwipeLayout.this.mView.setLayoutParams(lp);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SwipeLayout.this.mView.setLayoutParams(lp);
            }
        });
        animator.start();
    }

    public void addSwipeListener(View decorView, Object token, DismissCallbacks callbacks) {
        this.mCallbacks = callbacks;
        this.mView = decorView;
        this.mToken = token;
    }
}

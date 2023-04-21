package com.anthony.ultimateswipetool.cards;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import com.anthony.ultimateswipetool.R;
import java.util.Random;

public class SwipeCards extends ViewGroup {
    public static final int DEFAULT_ANIMATION_DURATION = 300;
    public static final boolean DEFAULT_DISABLE_HW_ACCELERATION = true;
    public static final float DEFAULT_SCALE_FACTOR = 1.0f;
    public static final int DEFAULT_STACK_ROTATION = 8;
    public static final int DEFAULT_STACK_SIZE = 3;
    public static final float DEFAULT_SWIPE_OPACITY = 1.0f;
    public static final float DEFAULT_SWIPE_ROTATION = 30.0f;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_SUPER_STATE = "superState";
    public static final int SWIPE_DIRECTION_BOTH = 0;
    public static final int SWIPE_DIRECTION_ONLY_LEFT = 1;
    public static final int SWIPE_DIRECTION_ONLY_RIGHT = 2;
    private Adapter mAdapter;
    private int mAllowedSwipeDirections;
    private int mAnimationDuration;
    private int mCurrentViewIndex;
    private DataSetObserver mDataObserver;
    private boolean mDisableHwAcceleration;
    private boolean mIsFirstLayout;
    private SwipeCardsListener mListener;
    private int mNumberOfStackedViews;
    private SwipeProgressListener mProgressListener;
    private Random mRandom;
    private float mScaleFactor;
    private SwipeCardsHelper mSwipeCardsHelper;
    private float mSwipeOpacity;
    private float mSwipeRotation;
    private View mTopView;
    private int mViewRotation;
    private int mViewSpacing;

    public interface SwipeCardsListener {
        void onCardsEmpty();

        void onViewSwipedToLeft(int i);

        void onViewSwipedToRight(int i);
    }

    public interface SwipeProgressListener {
        void onSwipeEnd(int i);

        void onSwipeProgress(int i, float f);

        void onSwipeStart(int i);
    }

    public SwipeCards(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwipeCards(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCards(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsFirstLayout = true;
        readAttributes(attrs);
        initialize();
    }

    private void readAttributes(AttributeSet attributeSet) {
        TypedArray attrs = getContext().obtainStyledAttributes(attributeSet, R.styleable.SwipeCards);
        try {
            this.mAllowedSwipeDirections = attrs.getInt(R.styleable.SwipeCards_allowed_swipe_directions, 0);
            this.mAnimationDuration = attrs.getInt(R.styleable.SwipeCards_animation_duration, 300);
            this.mNumberOfStackedViews = attrs.getInt(R.styleable.SwipeCards_stack_size, 3);
            this.mViewSpacing = attrs.getDimensionPixelSize(R.styleable.SwipeCards_stack_spacing, getResources().getDimensionPixelSize(R.dimen.default_stack_spacing));
            this.mViewRotation = attrs.getInt(R.styleable.SwipeCards_stack_rotation, 8);
            this.mSwipeRotation = attrs.getFloat(R.styleable.SwipeCards_swipe_rotation, 30.0f);
            this.mSwipeOpacity = attrs.getFloat(R.styleable.SwipeCards_swipe_opacity, 1.0f);
            this.mScaleFactor = attrs.getFloat(R.styleable.SwipeCards_scale_factor, 1.0f);
            this.mDisableHwAcceleration = attrs.getBoolean(R.styleable.SwipeCards_disable_hw_acceleration, true);
        } finally {
            attrs.recycle();
        }
    }

    private void initialize() {
        this.mRandom = new Random();
        setClipToPadding(false);
        setClipChildren(false);
        this.mSwipeCardsHelper = new SwipeCardsHelper(this);
        this.mSwipeCardsHelper.setAnimationDuration(this.mAnimationDuration);
        this.mSwipeCardsHelper.setRotation(this.mSwipeRotation);
        this.mSwipeCardsHelper.setOpacityEnd(this.mSwipeOpacity);
        this.mDataObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                SwipeCards.this.invalidate();
                SwipeCards.this.requestLayout();
            }
        };
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_CURRENT_INDEX, this.mCurrentViewIndex - getChildCount());
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mCurrentViewIndex = bundle.getInt(KEY_CURRENT_INDEX);
            state = bundle.getParcelable(KEY_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mAdapter == null || this.mAdapter.isEmpty()) {
            this.mCurrentViewIndex = 0;
            removeAllViewsInLayout();
            return;
        }
        for (int x = getChildCount(); x < this.mNumberOfStackedViews && this.mCurrentViewIndex < this.mAdapter.getCount(); x++) {
            addNextView();
        }
        reorderItems();
        this.mIsFirstLayout = false;
    }

    private void addNextView() {
        if (this.mCurrentViewIndex < this.mAdapter.getCount()) {
            View bottomView = this.mAdapter.getView(this.mCurrentViewIndex, (View) null, this);
            bottomView.setTag(R.id.new_view, true);
            if (!this.mDisableHwAcceleration) {
                bottomView.setLayerType(2, (Paint) null);
            }
            if (this.mViewRotation > 0) {
                bottomView.setRotation((float) (this.mRandom.nextInt(this.mViewRotation) - (this.mViewRotation / 2)));
            }
            int width = getWidth() - (getPaddingLeft() + getPaddingRight());
            int height = getHeight() - (getPaddingTop() + getPaddingBottom());
            ViewGroup.LayoutParams params = bottomView.getLayoutParams();
            if (params == null) {
                params = new ViewGroup.LayoutParams(-2, -2);
            }
            int measureSpecWidth = Integer.MIN_VALUE;
            int measureSpecHeight = Integer.MIN_VALUE;
            if (params.width == -1) {
                measureSpecWidth = 1073741824;
            }
            if (params.height == -1) {
                measureSpecHeight = 1073741824;
            }
            bottomView.measure(measureSpecWidth | width, measureSpecHeight | height);
            addViewInLayout(bottomView, 0, params, true);
            this.mCurrentViewIndex++;
        }
    }

    private void reorderItems() {
        for (int x = 0; x < getChildCount(); x++) {
            View childView = getChildAt(x);
            int topViewIndex = getChildCount() - 1;
            int distanceToViewAbove = (this.mViewSpacing * topViewIndex) - (this.mViewSpacing * x);
            int newPositionX = (getWidth() - childView.getMeasuredWidth()) / 2;
            int newPositionY = distanceToViewAbove + getPaddingTop();
            childView.layout(newPositionX, getPaddingTop(), childView.getMeasuredWidth() + newPositionX, getPaddingTop() + childView.getMeasuredHeight());
            if (Build.VERSION.SDK_INT >= 21) {
                childView.setTranslationZ((float) x);
            }
            boolean isNewView = ((Boolean) childView.getTag(R.id.new_view)).booleanValue();
            float scaleFactor = (float) Math.pow((double) this.mScaleFactor, (double) (getChildCount() - x));
            if (x == topViewIndex) {
                this.mSwipeCardsHelper.unregisterObservedView();
                this.mTopView = childView;
                this.mSwipeCardsHelper.registerObservedView(this.mTopView, (float) newPositionX, (float) newPositionY);
            }
            if (!this.mIsFirstLayout) {
                if (isNewView) {
                    childView.setTag(R.id.new_view, false);
                    childView.setAlpha(0.0f);
                    childView.setY((float) newPositionY);
                    childView.setScaleY(scaleFactor);
                    childView.setScaleX(scaleFactor);
                }
                childView.animate().y((float) newPositionY).scaleX(scaleFactor).scaleY(scaleFactor).alpha(1.0f).setDuration((long) this.mAnimationDuration);
            } else {
                childView.setTag(R.id.new_view, false);
                childView.setY((float) newPositionY);
                childView.setScaleY(scaleFactor);
                childView.setScaleX(scaleFactor);
            }
        }
    }

    private void removeTopView() {
        if (this.mTopView != null) {
            removeView(this.mTopView);
            this.mTopView = null;
        }
        if (getChildCount() == 0 && this.mListener != null) {
            this.mListener.onCardsEmpty();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    public void onSwipeStart() {
        if (this.mProgressListener != null) {
            this.mProgressListener.onSwipeStart(getCurrentPosition());
        }
    }

    public void onSwipeProgress(float progress) {
        if (this.mProgressListener != null) {
            this.mProgressListener.onSwipeProgress(getCurrentPosition(), progress);
        }
    }

    public void onSwipeEnd() {
        if (this.mProgressListener != null) {
            this.mProgressListener.onSwipeEnd(getCurrentPosition());
        }
    }

    public void onViewSwipedToLeft() {
        if (this.mListener != null) {
            this.mListener.onViewSwipedToLeft(getCurrentPosition());
        }
        removeTopView();
    }

    public void onViewSwipedToRight() {
        if (this.mListener != null) {
            this.mListener.onViewSwipedToRight(getCurrentPosition());
        }
        removeTopView();
    }

    public int getCurrentPosition() {
        return this.mCurrentViewIndex - getChildCount();
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = adapter;
        this.mAdapter.registerDataSetObserver(this.mDataObserver);
    }

    public int getAllowedSwipeDirections() {
        return this.mAllowedSwipeDirections;
    }

    public void setAllowedSwipeDirections(int directions) {
        this.mAllowedSwipeDirections = directions;
    }

    public void setListener(@Nullable SwipeCardsListener listener) {
        this.mListener = listener;
    }

    public void setSwipeProgressListener(@Nullable SwipeProgressListener listener) {
        this.mProgressListener = listener;
    }

    public View getTopView() {
        return this.mTopView;
    }

    public void swipeTopViewToRight() {
        if (getChildCount() != 0) {
            this.mSwipeCardsHelper.swipeViewToRight();
        }
    }

    public void swipeTopViewToLeft() {
        if (getChildCount() != 0) {
            this.mSwipeCardsHelper.swipeViewToLeft();
        }
    }

    public void resetStack() {
        this.mCurrentViewIndex = 0;
        removeAllViewsInLayout();
        requestLayout();
    }
}

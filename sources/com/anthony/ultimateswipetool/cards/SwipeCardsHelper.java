package com.anthony.ultimateswipetool.cards;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import com.anthony.ultimateswipetool.SwipeHelper;

public class SwipeCardsHelper implements View.OnTouchListener {
    private int mAnimationDuration = 300;
    private float mDownX;
    private float mDownY;
    private float mInitialX;
    private float mInitialY;
    private boolean mListenForTouchEvents;
    private View mObservedView;
    private float mOpacityEnd = 1.0f;
    private int mPointerId;
    private float mRotateDegrees = 30.0f;
    /* access modifiers changed from: private */
    public final SwipeCards mSwipeCards;

    public SwipeCardsHelper(SwipeCards swipeCards) {
        this.mSwipeCards = swipeCards;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                if (!this.mListenForTouchEvents || !this.mSwipeCards.isEnabled()) {
                    return false;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true);
                this.mSwipeCards.onSwipeStart();
                this.mPointerId = event.getPointerId(0);
                this.mDownX = event.getX(this.mPointerId);
                this.mDownY = event.getY(this.mPointerId);
                return true;
            case 1:
                v.getParent().requestDisallowInterceptTouchEvent(false);
                this.mSwipeCards.onSwipeEnd();
                checkViewPosition();
                return true;
            case 2:
                int pointerIndex = event.findPointerIndex(this.mPointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                float dx = event.getX(pointerIndex) - this.mDownX;
                float dy = event.getY(pointerIndex) - this.mDownY;
                float newX = this.mObservedView.getX() + dx;
                float newY = this.mObservedView.getY() + dy;
                this.mObservedView.setX(newX);
                this.mObservedView.setY(newY);
                float swipeProgress = Math.min(Math.max((newX - this.mInitialX) / ((float) this.mSwipeCards.getWidth()), -1.0f), 1.0f);
                this.mSwipeCards.onSwipeProgress(swipeProgress);
                if (this.mRotateDegrees > 0.0f) {
                    this.mObservedView.setRotation(this.mRotateDegrees * swipeProgress);
                }
                if (this.mOpacityEnd >= 1.0f) {
                    return true;
                }
                this.mObservedView.setAlpha(1.0f - Math.min(Math.abs(2.0f * swipeProgress), 1.0f));
                return true;
            default:
                return false;
        }
    }

    private void checkViewPosition() {
        if (!this.mSwipeCards.isEnabled()) {
            resetViewPosition();
            return;
        }
        float viewCenterHorizontal = this.mObservedView.getX() + ((float) (this.mObservedView.getWidth() / 2));
        float parentFirstThird = ((float) this.mSwipeCards.getWidth()) / 3.0f;
        float parentLastThird = parentFirstThird * 2.0f;
        if (viewCenterHorizontal < parentFirstThird && this.mSwipeCards.getAllowedSwipeDirections() != 2) {
            swipeViewToLeft(this.mAnimationDuration / 2);
        } else if (viewCenterHorizontal <= parentLastThird || this.mSwipeCards.getAllowedSwipeDirections() == 1) {
            resetViewPosition();
        } else {
            swipeViewToRight(this.mAnimationDuration / 2);
        }
    }

    private void resetViewPosition() {
        this.mObservedView.animate().x(this.mInitialX).y(this.mInitialY).rotation(0.0f).alpha(1.0f).setDuration((long) this.mAnimationDuration).setInterpolator(new OvershootInterpolator(1.4f)).setListener((Animator.AnimatorListener) null);
    }

    private void swipeViewToLeft(int duration) {
        if (this.mListenForTouchEvents) {
            this.mListenForTouchEvents = false;
            this.mObservedView.animate().cancel();
            this.mObservedView.animate().x(((float) (-this.mSwipeCards.getWidth())) + this.mObservedView.getX()).rotation(-this.mRotateDegrees).alpha(0.0f).setDuration((long) duration).setListener(new SwipeHelper.AnimationEndListener() {
                public void onAnimationEnd(Animator animation) {
                    SwipeCardsHelper.this.mSwipeCards.onViewSwipedToLeft();
                }
            });
        }
    }

    private void swipeViewToRight(int duration) {
        if (this.mListenForTouchEvents) {
            this.mListenForTouchEvents = false;
            this.mObservedView.animate().cancel();
            this.mObservedView.animate().x(((float) this.mSwipeCards.getWidth()) + this.mObservedView.getX()).rotation(this.mRotateDegrees).alpha(0.0f).setDuration((long) duration).setListener(new SwipeHelper.AnimationEndListener() {
                public void onAnimationEnd(Animator animation) {
                    SwipeCardsHelper.this.mSwipeCards.onViewSwipedToRight();
                }
            });
        }
    }

    public void registerObservedView(View view, float initialX, float initialY) {
        if (view != null) {
            this.mObservedView = view;
            this.mObservedView.setOnTouchListener(this);
            this.mInitialX = initialX;
            this.mInitialY = initialY;
            this.mListenForTouchEvents = true;
        }
    }

    public void unregisterObservedView() {
        if (this.mObservedView != null) {
            this.mObservedView.setOnTouchListener((View.OnTouchListener) null);
        }
        this.mObservedView = null;
        this.mListenForTouchEvents = false;
    }

    public void setAnimationDuration(int duration) {
        this.mAnimationDuration = duration;
    }

    public void setRotation(float rotation) {
        this.mRotateDegrees = rotation;
    }

    public void setOpacityEnd(float alpha) {
        this.mOpacityEnd = alpha;
    }

    public void swipeViewToLeft() {
        swipeViewToLeft(this.mAnimationDuration);
    }

    public void swipeViewToRight() {
        swipeViewToRight(this.mAnimationDuration);
    }
}

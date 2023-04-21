package com.bigkoo.convenientbanner.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

public class CBLoopViewPager extends ViewPager {
    private static final float sens = 5.0f;
    private boolean canLoop = true;
    private boolean isCanScroll = true;
    /* access modifiers changed from: private */
    public CBPageAdapter mAdapter;
    ViewPager.OnPageChangeListener mOuterPageChangeListener;
    private float newX = 0.0f;
    private float oldX = 0.0f;
    private OnItemClickListener onItemClickListener;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        private float mPreviousPosition = -1.0f;

        public void onPageSelected(int position) {
            int realPosition = CBLoopViewPager.this.mAdapter.toRealPosition(position);
            if (this.mPreviousPosition != ((float) realPosition)) {
                this.mPreviousPosition = (float) realPosition;
                if (CBLoopViewPager.this.mOuterPageChangeListener != null) {
                    CBLoopViewPager.this.mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = position;
            if (CBLoopViewPager.this.mOuterPageChangeListener == null) {
                return;
            }
            if (realPosition != CBLoopViewPager.this.mAdapter.getRealCount() - 1) {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            } else if (((double) positionOffset) > 0.5d) {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrolled(0, 0.0f, 0);
            } else {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrolled(realPosition, 0.0f, 0);
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (CBLoopViewPager.this.mOuterPageChangeListener != null) {
                CBLoopViewPager.this.mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public void setAdapter(PagerAdapter adapter, boolean canLoop2) {
        this.mAdapter = (CBPageAdapter) adapter;
        this.mAdapter.setCanLoop(canLoop2);
        this.mAdapter.setViewPager(this);
        super.setAdapter(this.mAdapter);
        setCurrentItem(getFristItem(), false);
    }

    public int getFristItem() {
        if (this.canLoop) {
            return this.mAdapter.getRealCount();
        }
        return 0;
    }

    public int getLastItem() {
        return this.mAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return this.isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll2) {
        this.isCanScroll = isCanScroll2;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.isCanScroll) {
            return false;
        }
        if (this.onItemClickListener != null) {
            switch (ev.getAction()) {
                case 0:
                    this.oldX = ev.getX();
                    break;
                case 1:
                    this.newX = ev.getX();
                    if (Math.abs(this.oldX - this.newX) < sens) {
                        this.onItemClickListener.onItemClick(getRealItem());
                    }
                    this.oldX = 0.0f;
                    this.newX = 0.0f;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public CBPageAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getRealItem() {
        if (this.mAdapter != null) {
            return this.mAdapter.toRealPosition(super.getCurrentItem());
        }
        return 0;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mOuterPageChangeListener = listener;
    }

    public CBLoopViewPager(Context context) {
        super(context);
        init();
    }

    public CBLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(this.onPageChangeListener);
    }

    public boolean isCanLoop() {
        return this.canLoop;
    }

    public void setCanLoop(boolean canLoop2) {
        this.canLoop = canLoop2;
        if (!canLoop2) {
            setCurrentItem(getRealItem(), false);
        }
        if (this.mAdapter != null) {
            this.mAdapter.setCanLoop(canLoop2);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }
}

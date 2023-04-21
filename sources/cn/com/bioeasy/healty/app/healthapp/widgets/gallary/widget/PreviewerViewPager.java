package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PreviewerViewPager extends ViewPager {
    private boolean isInterceptable;
    private boolean isTransition;
    /* access modifiers changed from: private */
    public int mScrollState;

    public PreviewerViewPager(Context context) {
        this(context, (AttributeSet) null);
    }

    public PreviewerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isInterceptable = false;
        this.isTransition = false;
        this.mScrollState = 0;
        addOnPageChangeListener(new PageChangeListener());
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mScrollState != 0) {
            return super.onInterceptTouchEvent(ev);
        }
        if (this.isTransition) {
            int action = ev.getAction();
            ev.setAction(0);
            super.onInterceptTouchEvent(ev);
            ev.setAction(action);
            this.isTransition = false;
        }
        boolean b = false;
        int action2 = ev.getAction();
        if (action2 == 0) {
            this.isInterceptable = false;
        }
        if (action2 != 2 || this.isInterceptable) {
            b = super.onInterceptTouchEvent(ev);
        }
        if (!this.isInterceptable || !b) {
            return false;
        }
        return true;
    }

    public void isInterceptable(boolean b) {
        if (!this.isInterceptable && b) {
            this.isTransition = true;
        }
        this.isInterceptable = b;
    }

    private class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        private PageChangeListener() {
        }

        public void onPageScrollStateChanged(int state) {
            int unused = PreviewerViewPager.this.mScrollState = state;
        }
    }
}

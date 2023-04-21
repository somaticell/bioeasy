package cn.com.bioeasy.app.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private boolean scrollable = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.scrollable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (this.scrollable) {
            return super.onInterceptTouchEvent(arg0);
        }
        return false;
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    public void setScrollable(boolean scrollable2) {
        this.scrollable = scrollable2;
    }
}

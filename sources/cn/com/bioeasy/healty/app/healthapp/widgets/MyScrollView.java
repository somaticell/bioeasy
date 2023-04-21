package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    /* access modifiers changed from: private */
    public boolean canScroll = true;
    private GestureDetector mGestureDetector = new GestureDetector(new YScrollDetector());
    View.OnTouchListener mGestureListener;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 1) {
            this.canScroll = true;
        }
        if (!super.onInterceptTouchEvent(ev) || !this.mGestureDetector.onTouchEvent(ev)) {
            return false;
        }
        return true;
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        YScrollDetector() {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (MyScrollView.this.canScroll) {
                if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                    boolean unused = MyScrollView.this.canScroll = true;
                } else {
                    boolean unused2 = MyScrollView.this.canScroll = false;
                }
            }
            return MyScrollView.this.canScroll;
        }
    }
}

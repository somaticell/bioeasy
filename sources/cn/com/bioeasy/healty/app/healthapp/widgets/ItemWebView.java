package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class ItemWebView extends WebView {
    private float oldX;
    public float oldY;
    private int t;

    public ItemWebView(Context context) {
        super(context);
    }

    public ItemWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                this.oldY = ev.getY();
                this.oldX = ev.getX();
                break;
            case 1:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case 2:
                float x = ev.getX();
                if (ev.getY() - this.oldY > 0.0f && this.t == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }
        }
        return super.onTouchEvent(ev);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t2, int oldl, int oldt) {
        this.t = t2;
        super.onScrollChanged(l, t2, oldl, oldt);
    }
}

package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

public class ItemListView extends ListView implements AbsListView.OnScrollListener {
    private int currentPosition;
    private float oldX;
    private float oldY;

    public ItemListView(Context context) {
        super(context);
        setOnScrollListener(this);
    }

    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    public ItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollListener(this);
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
                getLocationInWindow(new int[2]);
                if (ev.getY() - this.oldY > 0.0f && this.currentPosition == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }
        }
        return super.onTouchEvent(ev);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentPosition = getFirstVisiblePosition();
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}

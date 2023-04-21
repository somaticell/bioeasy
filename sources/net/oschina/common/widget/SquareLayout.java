package net.oschina.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import net.oschina.common.R;

public class SquareLayout extends FrameLayout {
    private int mBaseDirection;

    public SquareLayout(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0, 0);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = 21)
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SquareLayout, defStyleAttr, defStyleRes);
        this.mBaseDirection = array.getInt(R.styleable.SquareLayout_oscAccordTo, 3);
        array.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mBaseDirection == 1) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else if (this.mBaseDirection == 2) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        } else {
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (heightSize == 0) {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            } else if (widthSize == 0) {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            } else if (widthSize > heightSize) {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            } else {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            }
        }
    }
}

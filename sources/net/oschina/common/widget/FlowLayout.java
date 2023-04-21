package net.oschina.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import net.oschina.common.R;

public class FlowLayout extends ViewGroup {
    private float mHorizontalSpacing;
    private float mVerticalSpacing;

    public FlowLayout(Context context) {
        super(context);
        init((AttributeSet) null, 0, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();
        int vSpace = (int) (4.0f * getResources().getDisplayMetrics().density);
        int hSpace = vSpace;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, defStyleRes);
            vSpace = a.getDimensionPixelOffset(R.styleable.FlowLayout_oscVerticalSpace, vSpace);
            hSpace = a.getDimensionPixelOffset(R.styleable.FlowLayout_oscHorizontalSpace, hSpace);
            a.recycle();
        }
        setVerticalSpacing((float) vSpace);
        setHorizontalSpacing((float) hSpace);
    }

    public void setHorizontalSpacing(float pixelSize) {
        this.mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(float pixelSize) {
        this.mVerticalSpacing = pixelSize;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int selfWidth = resolveSize(0, widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;
        int contentWidth = (selfWidth - paddingRight) - paddingLeft;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            ViewGroup.LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLayoutParams.width), getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLayoutParams.height));
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);
            int childLeft2 = childLeft + childWidth;
            if (childLeft2 > contentWidth) {
                childLeft = childWidth;
                childTop = (int) (((float) childTop) + this.mVerticalSpacing + ((float) lineHeight));
                lineHeight = childHeight;
            } else {
                childLeft = (int) (((float) childLeft2) + this.mHorizontalSpacing);
            }
        }
        setMeasuredDimension(selfWidth, resolveSize(childTop + lineHeight + paddingBottom, heightMeasureSpec));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        float childCount = (float) getChildCount();
        if (childCount > 0.0f) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            if (childCount == 1.0f) {
                View childView = getChildAt(0);
                childView.layout(paddingLeft, paddingTop, paddingLeft + childView.getMeasuredWidth(), paddingTop + childView.getMeasuredHeight());
                return;
            }
            int mWidth = r - l;
            int paddingRight = getPaddingRight();
            int lineHeight = 0;
            int childLeft = paddingLeft;
            int childTop = paddingTop;
            for (int i = 0; ((float) i) < childCount; i++) {
                View childView2 = getChildAt(i);
                if (childView2.getVisibility() != 8) {
                    int childWidth = childView2.getMeasuredWidth();
                    int childHeight = childView2.getMeasuredHeight();
                    lineHeight = Math.max(childHeight, lineHeight);
                    if (childLeft + childWidth + paddingRight > mWidth) {
                        childLeft = paddingLeft;
                        childTop = (int) (((float) childTop) + this.mVerticalSpacing + ((float) lineHeight));
                        lineHeight = childHeight;
                    }
                    childView2.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    childLeft = (int) (((float) childLeft) + ((float) childWidth) + this.mHorizontalSpacing);
                }
            }
        }
    }
}

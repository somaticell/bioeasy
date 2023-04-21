package cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.healty.app.healthapp.R;

public class FlowLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private boolean debugDraw = false;
    private int horizontalSpacing = 0;
    private int orientation = 0;
    private int verticalSpacing = 0;

    public FlowLayout(Context context) {
        super(context);
        readStyleParameters(context, (AttributeSet) null);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        readStyleParameters(context, attributeSet);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        readStyleParameters(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size;
        int mode;
        int controlMaxLength;
        int controlMaxThickness;
        int childLength;
        int childThickness;
        int spacingLength;
        int spacingThickness;
        int posX;
        int posY;
        int sizeWidth = (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight()) - getPaddingLeft();
        int sizeHeight = (View.MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
        int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);
        if (this.orientation == 0) {
            size = sizeWidth;
            mode = modeWidth;
        } else {
            size = sizeHeight;
            mode = modeHeight;
        }
        int lineThicknessWithSpacing = 0;
        int lineThickness = 0;
        int lineLengthWithSpacing = 0;
        int prevLinePosition = 0;
        int controlMaxLength2 = 0;
        int controlMaxThickness2 = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                child.measure(getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), lp.width), getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), lp.height));
                int hSpacing = getHorizontalSpacing(lp);
                int vSpacing = getVerticalSpacing(lp);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (this.orientation == 0) {
                    childLength = childWidth;
                    childThickness = childHeight;
                    spacingLength = hSpacing;
                    spacingThickness = vSpacing;
                } else {
                    childLength = childHeight;
                    childThickness = childWidth;
                    spacingLength = vSpacing;
                    spacingThickness = hSpacing;
                }
                int lineLength = lineLengthWithSpacing + childLength;
                lineLengthWithSpacing = lineLength + spacingLength;
                if (lp.newLine || (mode != 0 && lineLength > size)) {
                    prevLinePosition += lineThicknessWithSpacing;
                    lineThickness = childThickness;
                    lineLength = childLength;
                    lineThicknessWithSpacing = childThickness + spacingThickness;
                    lineLengthWithSpacing = lineLength + spacingLength;
                }
                lineThicknessWithSpacing = Math.max(lineThicknessWithSpacing, childThickness + spacingThickness);
                lineThickness = Math.max(lineThickness, childThickness);
                if (this.orientation == 0) {
                    posX = (getPaddingLeft() + lineLength) - childLength;
                    posY = getPaddingTop() + prevLinePosition;
                } else {
                    posX = getPaddingLeft() + prevLinePosition;
                    posY = (getPaddingTop() + lineLength) - childHeight;
                }
                lp.setPosition(posX, posY);
                controlMaxLength2 = Math.max(controlMaxLength2, lineLength);
                controlMaxThickness2 = prevLinePosition + lineThickness;
            }
        }
        if (this.orientation == 0) {
            controlMaxLength = controlMaxLength2 + getPaddingLeft() + getPaddingRight();
            controlMaxThickness = controlMaxThickness2 + getPaddingBottom() + getPaddingTop();
        } else {
            controlMaxLength = controlMaxLength2 + getPaddingBottom() + getPaddingTop();
            controlMaxThickness = controlMaxThickness2 + getPaddingLeft() + getPaddingRight();
        }
        if (this.orientation == 0) {
            setMeasuredDimension(resolveSize(controlMaxLength, widthMeasureSpec), resolveSize(controlMaxThickness, heightMeasureSpec));
        } else {
            setMeasuredDimension(resolveSize(controlMaxThickness, widthMeasureSpec), resolveSize(controlMaxLength, heightMeasureSpec));
        }
    }

    private int getVerticalSpacing(LayoutParams lp) {
        if (lp.verticalSpacingSpecified()) {
            return lp.verticalSpacing;
        }
        return this.verticalSpacing;
    }

    private int getHorizontalSpacing(LayoutParams lp) {
        if (lp.horizontalSpacingSpecified()) {
            return lp.horizontalSpacing;
        }
        return this.horizontalSpacing;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean more = super.drawChild(canvas, child, drawingTime);
        drawDebugInfo(canvas, child);
        return more;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout);
        try {
            this.horizontalSpacing = a.getDimensionPixelSize(2, 0);
            this.verticalSpacing = a.getDimensionPixelSize(3, 0);
            this.orientation = a.getInteger(4, 0);
            this.debugDraw = a.getBoolean(5, false);
        } finally {
            a.recycle();
        }
    }

    private void drawDebugInfo(Canvas canvas, View child) {
        if (this.debugDraw) {
            Paint childPaint = createPaint(InputDeviceCompat.SOURCE_ANY);
            Paint layoutPaint = createPaint(-16711936);
            Paint newLinePaint = createPaint(SupportMenu.CATEGORY_MASK);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.horizontalSpacing > 0) {
                float x = (float) child.getRight();
                float y = ((float) child.getTop()) + (((float) child.getHeight()) / 2.0f);
                canvas.drawLine(x, y, x + ((float) lp.horizontalSpacing), y, childPaint);
                canvas.drawLine((((float) lp.horizontalSpacing) + x) - 4.0f, y - 4.0f, x + ((float) lp.horizontalSpacing), y, childPaint);
                canvas.drawLine((((float) lp.horizontalSpacing) + x) - 4.0f, y + 4.0f, x + ((float) lp.horizontalSpacing), y, childPaint);
            } else if (this.horizontalSpacing > 0) {
                float x2 = (float) child.getRight();
                float y2 = ((float) child.getTop()) + (((float) child.getHeight()) / 2.0f);
                canvas.drawLine(x2, y2, x2 + ((float) this.horizontalSpacing), y2, layoutPaint);
                canvas.drawLine((((float) this.horizontalSpacing) + x2) - 4.0f, y2 - 4.0f, x2 + ((float) this.horizontalSpacing), y2, layoutPaint);
                canvas.drawLine((((float) this.horizontalSpacing) + x2) - 4.0f, y2 + 4.0f, x2 + ((float) this.horizontalSpacing), y2, layoutPaint);
            }
            if (lp.verticalSpacing > 0) {
                float x3 = ((float) child.getLeft()) + (((float) child.getWidth()) / 2.0f);
                float y3 = (float) child.getBottom();
                canvas.drawLine(x3, y3, x3, y3 + ((float) lp.verticalSpacing), childPaint);
                canvas.drawLine(x3 - 4.0f, (((float) lp.verticalSpacing) + y3) - 4.0f, x3, y3 + ((float) lp.verticalSpacing), childPaint);
                canvas.drawLine(x3 + 4.0f, (((float) lp.verticalSpacing) + y3) - 4.0f, x3, y3 + ((float) lp.verticalSpacing), childPaint);
            } else if (this.verticalSpacing > 0) {
                float x4 = ((float) child.getLeft()) + (((float) child.getWidth()) / 2.0f);
                float y4 = (float) child.getBottom();
                canvas.drawLine(x4, y4, x4, y4 + ((float) this.verticalSpacing), layoutPaint);
                canvas.drawLine(x4 - 4.0f, (((float) this.verticalSpacing) + y4) - 4.0f, x4, y4 + ((float) this.verticalSpacing), layoutPaint);
                canvas.drawLine(x4 + 4.0f, (((float) this.verticalSpacing) + y4) - 4.0f, x4, y4 + ((float) this.verticalSpacing), layoutPaint);
            }
            if (!lp.newLine) {
                return;
            }
            if (this.orientation == 0) {
                float x5 = (float) child.getLeft();
                float y5 = ((float) child.getTop()) + (((float) child.getHeight()) / 2.0f);
                canvas.drawLine(x5, y5 - 6.0f, x5, y5 + 6.0f, newLinePaint);
                return;
            }
            float x6 = ((float) child.getLeft()) + (((float) child.getWidth()) / 2.0f);
            float y6 = (float) child.getTop();
            canvas.drawLine(x6 - 6.0f, y6, x6 + 6.0f, y6, newLinePaint);
        }
    }

    private Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(2.0f);
        return paint;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        private static int NO_SPACING = -1;
        /* access modifiers changed from: private */
        public int horizontalSpacing = NO_SPACING;
        /* access modifiers changed from: private */
        public boolean newLine = false;
        /* access modifiers changed from: private */
        public int verticalSpacing = NO_SPACING;
        /* access modifiers changed from: private */
        public int x;
        /* access modifiers changed from: private */
        public int y;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            readStyleParameters(context, attributeSet);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean horizontalSpacingSpecified() {
            return this.horizontalSpacing != NO_SPACING;
        }

        public boolean verticalSpacingSpecified() {
            return this.verticalSpacing != NO_SPACING;
        }

        public void setPosition(int x2, int y2) {
            this.x = x2;
            this.y = y2;
        }

        private void readStyleParameters(Context context, AttributeSet attributeSet) {
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout_LayoutParams);
            try {
                this.horizontalSpacing = a.getDimensionPixelSize(1, NO_SPACING);
                this.verticalSpacing = a.getDimensionPixelSize(2, NO_SPACING);
                this.newLine = a.getBoolean(0, false);
            } finally {
                a.recycle();
            }
        }
    }
}

package cn.com.bioeasy.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import cn.com.bioeasy.app.commonlib.R;

public class CirclePointView extends ImageView {
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final int DEFAULT_BORDER_WIDTH = 10;
    private final Paint mBitmapPaint;
    private int mBorderColor;
    private int mBorderWidth;

    public CirclePointView(Context context) {
        super(context);
        this.mBitmapPaint = new Paint();
        this.mBorderColor = -16777216;
        this.mBorderWidth = 10;
    }

    public CirclePointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePointView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mBitmapPaint = new Paint();
        this.mBorderColor = -16777216;
        this.mBorderWidth = 10;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePointView, defStyle, 0);
        this.mBorderWidth = a.getDimensionPixelSize(R.styleable.CirclePointView_width, 10);
        this.mBorderColor = a.getColor(R.styleable.CirclePointView_bg_color, -16777216);
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setColor(this.mBorderColor);
        canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, (float) this.mBorderWidth, this.mBitmapPaint);
    }
}

package cn.com.bioeasy.app.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import cn.com.bioeasy.app.commonlib.R;

public class CircleProgressBar extends View {
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final int DEFAULT_BORDER_WIDTH = 10;
    private static final int DEFAULT_DURATION = 2;
    private static final int DEFAULT_FILL_COLOR = Color.argb(32, 0, 0, 0);
    /* access modifiers changed from: private */
    public float mAnimatorValue;
    private Paint mBgPaint;
    private int mBorderColor;
    private int mBorderWidth;
    private Path mDst;
    private int mDuration;
    private int mFillColor;
    private float mLength;
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private ValueAnimator mValueAnimator;

    public CircleProgressBar(Context context) {
        super(context);
        this.mBorderColor = -16777216;
        this.mBorderWidth = 10;
        this.mDuration = 2;
        this.mFillColor = DEFAULT_FILL_COLOR;
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBorderColor = -16777216;
        this.mBorderWidth = 10;
        this.mDuration = 2;
        this.mFillColor = DEFAULT_FILL_COLOR;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);
        this.mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_border_width, 10);
        this.mBorderColor = a.getColor(R.styleable.CircleProgressBar_border_color, -16777216);
        this.mDuration = a.getInt(R.styleable.CircleProgressBar_duration_second, 2);
        this.mFillColor = a.getColor(R.styleable.CirclePageIndicator_fillColor, DEFAULT_FILL_COLOR);
        a.recycle();
        init();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getHeight() != 0 && getWidth() != 0) {
            this.mPath.addCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) ((Math.min(getWidth(), getHeight()) / 2) - this.mBorderWidth), Path.Direction.CW);
            this.mPathMeasure.setPath(this.mPath, true);
            this.mLength = this.mPathMeasure.getLength();
        }
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.mBorderWidth);
        this.mPaint.setColor(this.mBorderColor);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPath = new Path();
        this.mPathMeasure = new PathMeasure();
        this.mDst = new Path();
        this.mValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float unused = CircleProgressBar.this.mAnimatorValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircleProgressBar.this.invalidate();
            }
        });
        this.mValueAnimator.setDuration((long) (this.mDuration * 1000));
        this.mValueAnimator.start();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBgPaint == null) {
            this.mBgPaint = new Paint(1);
            this.mBgPaint.setStyle(Paint.Style.FILL);
            this.mBgPaint.setColor(this.mFillColor);
        }
        canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), ((float) (Math.min(getWidth(), getHeight()) / 2)) - (((float) this.mBorderWidth) / 1.8f), this.mBgPaint);
        this.mDst.reset();
        this.mDst.lineTo(0.0f, 0.0f);
        this.mPathMeasure.getSegment(0.0f, this.mLength * this.mAnimatorValue, this.mDst, true);
        canvas.drawPath(this.mDst, this.mPaint);
    }

    public void stop() {
        this.mValueAnimator.cancel();
    }
}

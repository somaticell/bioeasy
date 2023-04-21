package cn.pedant.SweetAlert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SuccessTickView extends View {
    /* access modifiers changed from: private */
    public final float CONST_LEFT_RECT_W = dip2px(15.0f);
    private final float CONST_RADIUS = dip2px(1.2f);
    private final float CONST_RECT_WEIGHT = dip2px(3.0f);
    /* access modifiers changed from: private */
    public final float CONST_RIGHT_RECT_W = dip2px(25.0f);
    /* access modifiers changed from: private */
    public final float MAX_RIGHT_RECT_W = (this.CONST_RIGHT_RECT_W + dip2px(6.7f));
    /* access modifiers changed from: private */
    public final float MIN_LEFT_RECT_W = dip2px(3.3f);
    private float mDensity = -1.0f;
    /* access modifiers changed from: private */
    public boolean mLeftRectGrowMode;
    /* access modifiers changed from: private */
    public float mLeftRectWidth;
    /* access modifiers changed from: private */
    public float mMaxLeftRectWidth;
    private Paint mPaint;
    /* access modifiers changed from: private */
    public float mRightRectWidth;

    public SuccessTickView(Context context) {
        super(context);
        init();
    }

    public SuccessTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mPaint.setColor(getResources().getColor(R.color.success_stroke_color));
        this.mLeftRectWidth = this.CONST_LEFT_RECT_W;
        this.mRightRectWidth = this.CONST_RIGHT_RECT_W;
        this.mLeftRectGrowMode = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int totalW = getWidth();
        int totalH = getHeight();
        canvas.rotate(45.0f, (float) (totalW / 2), (float) (totalH / 2));
        int totalW2 = (int) (((double) totalW) / 1.2d);
        int totalH2 = (int) (((double) totalH) / 1.4d);
        this.mMaxLeftRectWidth = (((((float) totalW2) + this.CONST_LEFT_RECT_W) / 2.0f) + this.CONST_RECT_WEIGHT) - 1.0f;
        RectF leftRect = new RectF();
        if (this.mLeftRectGrowMode) {
            leftRect.left = 0.0f;
            leftRect.right = leftRect.left + this.mLeftRectWidth;
            leftRect.top = (((float) totalH2) + this.CONST_RIGHT_RECT_W) / 2.0f;
            leftRect.bottom = leftRect.top + this.CONST_RECT_WEIGHT;
        } else {
            leftRect.right = (((((float) totalW2) + this.CONST_LEFT_RECT_W) / 2.0f) + this.CONST_RECT_WEIGHT) - 1.0f;
            leftRect.left = leftRect.right - this.mLeftRectWidth;
            leftRect.top = (((float) totalH2) + this.CONST_RIGHT_RECT_W) / 2.0f;
            leftRect.bottom = leftRect.top + this.CONST_RECT_WEIGHT;
        }
        canvas.drawRoundRect(leftRect, this.CONST_RADIUS, this.CONST_RADIUS, this.mPaint);
        RectF rightRect = new RectF();
        rightRect.bottom = (((((float) totalH2) + this.CONST_RIGHT_RECT_W) / 2.0f) + this.CONST_RECT_WEIGHT) - 1.0f;
        rightRect.left = (((float) totalW2) + this.CONST_LEFT_RECT_W) / 2.0f;
        rightRect.right = rightRect.left + this.CONST_RECT_WEIGHT;
        rightRect.top = rightRect.bottom - this.mRightRectWidth;
        canvas.drawRoundRect(rightRect, this.CONST_RADIUS, this.CONST_RADIUS, this.mPaint);
    }

    public float dip2px(float dpValue) {
        if (this.mDensity == -1.0f) {
            this.mDensity = getResources().getDisplayMetrics().density;
        }
        return (this.mDensity * dpValue) + 0.5f;
    }

    public void startTickAnim() {
        this.mLeftRectWidth = 0.0f;
        this.mRightRectWidth = 0.0f;
        invalidate();
        Animation tickAnim = new Animation() {
            /* access modifiers changed from: protected */
            public void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                if (0.54d < ((double) interpolatedTime) && 0.7d >= ((double) interpolatedTime)) {
                    boolean unused = SuccessTickView.this.mLeftRectGrowMode = true;
                    float unused2 = SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.mMaxLeftRectWidth * ((interpolatedTime - 0.54f) / 0.16f);
                    if (0.65d < ((double) interpolatedTime)) {
                        float unused3 = SuccessTickView.this.mRightRectWidth = SuccessTickView.this.MAX_RIGHT_RECT_W * ((interpolatedTime - 0.65f) / 0.19f);
                    }
                    SuccessTickView.this.invalidate();
                } else if (0.7d < ((double) interpolatedTime) && 0.84d >= ((double) interpolatedTime)) {
                    boolean unused4 = SuccessTickView.this.mLeftRectGrowMode = false;
                    float unused5 = SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.mMaxLeftRectWidth * (1.0f - ((interpolatedTime - 0.7f) / 0.14f));
                    float unused6 = SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.mLeftRectWidth < SuccessTickView.this.MIN_LEFT_RECT_W ? SuccessTickView.this.MIN_LEFT_RECT_W : SuccessTickView.this.mLeftRectWidth;
                    float unused7 = SuccessTickView.this.mRightRectWidth = SuccessTickView.this.MAX_RIGHT_RECT_W * ((interpolatedTime - 0.65f) / 0.19f);
                    SuccessTickView.this.invalidate();
                } else if (0.84d < ((double) interpolatedTime) && 1.0f >= interpolatedTime) {
                    boolean unused8 = SuccessTickView.this.mLeftRectGrowMode = false;
                    float unused9 = SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.MIN_LEFT_RECT_W + ((SuccessTickView.this.CONST_LEFT_RECT_W - SuccessTickView.this.MIN_LEFT_RECT_W) * ((interpolatedTime - 0.84f) / 0.16f));
                    float unused10 = SuccessTickView.this.mRightRectWidth = SuccessTickView.this.CONST_RIGHT_RECT_W + ((SuccessTickView.this.MAX_RIGHT_RECT_W - SuccessTickView.this.CONST_RIGHT_RECT_W) * (1.0f - ((interpolatedTime - 0.84f) / 0.16f)));
                    SuccessTickView.this.invalidate();
                }
            }
        };
        tickAnim.setDuration(750);
        tickAnim.setStartOffset(100);
        startAnimation(tickAnim);
    }
}

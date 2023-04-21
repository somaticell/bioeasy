package net.oschina.common.widget.drawable.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

public class BorderShape extends Shape {
    private RectF mBorder;
    private Path mPath;
    private DashPathEffect mPathEffect;

    public BorderShape(RectF border) {
        this(border, 0.0f, 0.0f);
    }

    public BorderShape(RectF border, float dashWidth, float dashGap) {
        this.mBorder = null;
        this.mPathEffect = null;
        this.mPath = null;
        if (border.left != 0.0f || border.top != 0.0f || border.right != 0.0f || border.bottom != 0.0f) {
            this.mBorder = border;
            if (dashWidth > 0.0f && dashGap > 0.0f) {
                this.mPathEffect = new DashPathEffect(new float[]{dashWidth, dashGap}, 0.0f);
                this.mPath = new Path();
            }
        }
    }

    public void setBorder(RectF border) {
        if (border.left == 0.0f && border.top == 0.0f && border.right == 0.0f && border.bottom == 0.0f) {
            this.mBorder = null;
        } else if (this.mBorder == null) {
            this.mBorder = new RectF(border);
        } else {
            this.mBorder.set(border);
        }
    }

    public RectF getBorder(RectF border) {
        if (!(this.mBorder == null || border == null)) {
            border.set(this.mBorder);
        }
        return border;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (this.mBorder != null) {
            float width = getWidth();
            float height = getHeight();
            if (this.mPathEffect == null) {
                if (this.mBorder.left > 0.0f) {
                    canvas.drawRect(0.0f, 0.0f, this.mBorder.left, height, paint);
                }
                if (this.mBorder.top > 0.0f) {
                    canvas.drawRect(0.0f, 0.0f, width, this.mBorder.top, paint);
                }
                if (this.mBorder.right > 0.0f) {
                    canvas.drawRect(width - this.mBorder.right, 0.0f, width, height, paint);
                }
                if (this.mBorder.bottom > 0.0f) {
                    canvas.drawRect(0.0f, height - this.mBorder.bottom, width, height, paint);
                    return;
                }
                return;
            }
            if (paint.getPathEffect() != this.mPathEffect) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setPathEffect(this.mPathEffect);
            }
            if (this.mBorder.left > 0.0f) {
                paint.setStrokeWidth(this.mBorder.left);
                initPath(this.mBorder.left / 2.0f, 0.0f, this.mBorder.left / 2.0f, height);
                canvas.drawPath(this.mPath, paint);
            }
            if (this.mBorder.top > 0.0f) {
                paint.setStrokeWidth(this.mBorder.top);
                initPath(0.0f, this.mBorder.top / 2.0f, width, this.mBorder.top / 2.0f);
                canvas.drawPath(this.mPath, paint);
            }
            if (this.mBorder.right > 0.0f) {
                paint.setStrokeWidth(this.mBorder.right);
                initPath(width - (this.mBorder.right / 2.0f), 0.0f, width - (this.mBorder.right / 2.0f), height);
                canvas.drawPath(this.mPath, paint);
            }
            if (this.mBorder.bottom > 0.0f) {
                paint.setStrokeWidth(this.mBorder.bottom);
                initPath(0.0f, height - (this.mBorder.bottom / 2.0f), width, height - (this.mBorder.bottom / 2.0f));
                canvas.drawPath(this.mPath, paint);
            }
        }
    }

    private void initPath(float startX, float startY, float endX, float endY) {
        this.mPath.reset();
        this.mPath.moveTo(startX, startY);
        this.mPath.lineTo(endX, endY);
    }
}

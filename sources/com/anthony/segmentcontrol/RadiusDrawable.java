package com.anthony.segmentcontrol;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class RadiusDrawable extends Drawable {
    private int bottom;
    private int bottomLeftRadius;
    private int bottomRightRadius;
    private int fillColor;
    private final boolean isStroke;
    private int left;
    private final Paint paint;
    private Path path;
    private int right;
    private int strokeColor;
    private int strokeWidth;
    private int top;
    private int topLeftRadius;
    private int topRightRadius;

    public RadiusDrawable(boolean isStroke2) {
        this(0, isStroke2);
    }

    public RadiusDrawable(int radius, boolean isStroke2) {
        this.strokeWidth = 0;
        this.strokeColor = -16711681;
        setRadius(radius);
        this.paint = new Paint(1);
        this.isStroke = isStroke2;
    }

    public void setStrokeWidth(int width) {
        this.strokeWidth = width;
        setBounds(this.left, this.top, this.right, this.bottom);
    }

    public void setStrokeColor(int strokeColor2) {
        this.strokeColor = strokeColor2;
    }

    public void setFillColor(int fillColor2) {
        this.fillColor = fillColor2;
    }

    public void setRadius(int radius) {
        this.bottomRightRadius = radius;
        this.bottomLeftRadius = radius;
        this.topRightRadius = radius;
        this.topLeftRadius = radius;
    }

    public void setRadius(int topLeftRadius2, int topRightRadius2, int bottomLeftRadius2, int bottomRightRadius2) {
        this.topLeftRadius = topLeftRadius2;
        this.topRightRadius = topRightRadius2;
        this.bottomLeftRadius = bottomLeftRadius2;
        this.bottomRightRadius = bottomRightRadius2;
    }

    public void setBounds(int left2, int top2, int right2, int bottom2) {
        super.setBounds(left2, top2, right2, bottom2);
        this.left = left2;
        this.top = top2;
        this.right = right2;
        this.bottom = bottom2;
        if (this.isStroke) {
            int halfStrokeWidth = this.strokeWidth / 2;
            left2 += halfStrokeWidth;
            top2 += halfStrokeWidth;
            right2 -= halfStrokeWidth;
            bottom2 -= halfStrokeWidth;
        }
        this.path = new Path();
        this.path.moveTo((float) (this.topLeftRadius + left2), (float) top2);
        this.path.lineTo((float) (right2 - this.topRightRadius), (float) top2);
        this.path.arcTo(new RectF((float) (right2 - (this.topRightRadius * 2)), (float) top2, (float) right2, (float) ((this.topRightRadius * 2) + top2)), -90.0f, 90.0f);
        this.path.lineTo((float) right2, (float) (bottom2 - this.bottomRightRadius));
        this.path.arcTo(new RectF((float) (right2 - (this.bottomRightRadius * 2)), (float) (bottom2 - (this.bottomRightRadius * 2)), (float) right2, (float) bottom2), 0.0f, 90.0f);
        this.path.lineTo((float) (this.bottomLeftRadius + left2), (float) bottom2);
        this.path.arcTo(new RectF((float) left2, (float) (bottom2 - (this.bottomLeftRadius * 2)), (float) ((this.bottomLeftRadius * 2) + left2), (float) bottom2), 90.0f, 90.0f);
        this.path.lineTo((float) left2, (float) (this.topLeftRadius + top2));
        this.path.arcTo(new RectF((float) left2, (float) top2, (float) ((this.topLeftRadius * 2) + left2), (float) ((this.topLeftRadius * 2) + top2)), 180.0f, 90.0f);
        this.path.close();
    }

    public void draw(Canvas canvas) {
        if (this.fillColor != 0) {
            this.paint.setColor(this.fillColor);
            this.paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(this.path, this.paint);
        }
        if (this.strokeWidth > 0) {
            this.paint.setColor(this.strokeColor);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeJoin(Paint.Join.MITER);
            this.paint.setStrokeWidth((float) this.strokeWidth);
            canvas.drawPath(this.path, this.paint);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -3;
    }
}

package org.achartengine.chart;

import android.graphics.RectF;

public class ClickableArea {
    private RectF rect;
    private double x;
    private double y;

    public ClickableArea(RectF rect2, double x2, double y2) {
        this.rect = rect2;
        this.x = x2;
        this.y = y2;
    }

    public RectF getRect() {
        return this.rect;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}

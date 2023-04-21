package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;

public class ScalingUtils {

    public enum ScaleType {
        FIT_XY,
        FIT_START,
        FIT_CENTER,
        FIT_END,
        CENTER,
        CENTER_INSIDE,
        CENTER_CROP,
        FOCUS_CROP
    }

    public static Matrix getTransform(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY, ScaleType scaleType) {
        float scale;
        float dx;
        float dy;
        float scale2;
        float dx2;
        float dy2;
        int parentWidth = parentBounds.width();
        int parentHeight = parentBounds.height();
        float scaleX = ((float) parentWidth) / ((float) childWidth);
        float scaleY = ((float) parentHeight) / ((float) childHeight);
        switch (scaleType) {
            case FIT_XY:
                float dx3 = (float) parentBounds.left;
                float dy3 = (float) parentBounds.top;
                transform.setScale(scaleX, scaleY);
                transform.postTranslate((float) ((int) (0.5f + dx3)), (float) ((int) (0.5f + dy3)));
                break;
            case FIT_START:
                float scale3 = Math.min(scaleX, scaleY);
                float dx4 = (float) parentBounds.left;
                float dy4 = (float) parentBounds.top;
                transform.setScale(scale3, scale3);
                transform.postTranslate((float) ((int) (0.5f + dx4)), (float) ((int) (0.5f + dy4)));
                break;
            case FIT_CENTER:
                float scale4 = Math.min(scaleX, scaleY);
                float dx5 = ((float) parentBounds.left) + ((((float) parentWidth) - (((float) childWidth) * scale4)) * 0.5f);
                float dy5 = ((float) parentBounds.top) + ((((float) parentHeight) - (((float) childHeight) * scale4)) * 0.5f);
                transform.setScale(scale4, scale4);
                transform.postTranslate((float) ((int) (0.5f + dx5)), (float) ((int) (0.5f + dy5)));
                break;
            case FIT_END:
                float scale5 = Math.min(scaleX, scaleY);
                float dx6 = ((float) parentBounds.left) + (((float) parentWidth) - (((float) childWidth) * scale5));
                float dy6 = ((float) parentBounds.top) + (((float) parentHeight) - (((float) childHeight) * scale5));
                transform.setScale(scale5, scale5);
                transform.postTranslate((float) ((int) (0.5f + dx6)), (float) ((int) (0.5f + dy6)));
                break;
            case CENTER:
                transform.setTranslate((float) ((int) (0.5f + ((float) parentBounds.left) + (((float) (parentWidth - childWidth)) * 0.5f))), (float) ((int) (0.5f + ((float) parentBounds.top) + (((float) (parentHeight - childHeight)) * 0.5f))));
                break;
            case CENTER_INSIDE:
                float scale6 = Math.min(Math.min(scaleX, scaleY), 1.0f);
                float dx7 = ((float) parentBounds.left) + ((((float) parentWidth) - (((float) childWidth) * scale6)) * 0.5f);
                float dy7 = ((float) parentBounds.top) + ((((float) parentHeight) - (((float) childHeight) * scale6)) * 0.5f);
                transform.setScale(scale6, scale6);
                transform.postTranslate((float) ((int) (0.5f + dx7)), (float) ((int) (0.5f + dy7)));
                break;
            case CENTER_CROP:
                if (scaleY > scaleX) {
                    scale2 = scaleY;
                    dx2 = ((float) parentBounds.left) + ((((float) parentWidth) - (((float) childWidth) * scale2)) * 0.5f);
                    dy2 = (float) parentBounds.top;
                } else {
                    scale2 = scaleX;
                    dx2 = (float) parentBounds.left;
                    dy2 = ((float) parentBounds.top) + ((((float) parentHeight) - (((float) childHeight) * scale2)) * 0.5f);
                }
                transform.setScale(scale2, scale2);
                transform.postTranslate((float) ((int) (0.5f + dx2)), (float) ((int) (0.5f + dy2)));
                break;
            case FOCUS_CROP:
                if (scaleY > scaleX) {
                    scale = scaleY;
                    dx = ((float) parentBounds.left) + Math.max(Math.min((((float) parentWidth) * 0.5f) - ((((float) childWidth) * scale) * focusX), 0.0f), ((float) parentWidth) - (((float) childWidth) * scale));
                    dy = (float) parentBounds.top;
                } else {
                    scale = scaleX;
                    dx = (float) parentBounds.left;
                    dy = ((float) parentBounds.top) + Math.max(Math.min((((float) parentHeight) * 0.5f) - ((((float) childHeight) * scale) * focusY), 0.0f), ((float) parentHeight) - (((float) childHeight) * scale));
                }
                transform.setScale(scale, scale);
                transform.postTranslate((float) ((int) (0.5f + dx)), (float) ((int) (0.5f + dy)));
                break;
            default:
                throw new UnsupportedOperationException("Unsupported scale type: " + scaleType);
        }
        return transform;
    }
}

package com.facebook.drawee.drawable;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class DrawableProperties {
    private static final int UNSET = -1;
    private int mAlpha = -1;
    private ColorFilter mColorFilter = null;
    private int mDither = -1;
    private int mFilterBitmap = -1;
    private boolean mIsSetColorFilter = false;

    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mIsSetColorFilter = true;
    }

    public void setDither(boolean dither) {
        this.mDither = dither ? 1 : 0;
    }

    public void setFilterBitmap(boolean filterBitmap) {
        this.mFilterBitmap = filterBitmap ? 1 : 0;
    }

    public void applyTo(Drawable drawable) {
        boolean z;
        boolean z2 = true;
        if (drawable != null) {
            if (this.mAlpha != -1) {
                drawable.setAlpha(this.mAlpha);
            }
            if (this.mIsSetColorFilter) {
                drawable.setColorFilter(this.mColorFilter);
            }
            if (this.mDither != -1) {
                if (this.mDither != 0) {
                    z = true;
                } else {
                    z = false;
                }
                drawable.setDither(z);
            }
            if (this.mFilterBitmap != -1) {
                if (this.mFilterBitmap == 0) {
                    z2 = false;
                }
                drawable.setFilterBitmap(z2);
            }
        }
    }
}

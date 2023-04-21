package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import javax.annotation.Nullable;

public class ArrayDrawable extends Drawable implements Drawable.Callback, TransformCallback, TransformAwareDrawable {
    private final DrawableParent[] mDrawableParents;
    private final DrawableProperties mDrawableProperties = new DrawableProperties();
    private boolean mIsMutated = false;
    private boolean mIsStateful = false;
    private boolean mIsStatefulCalculated = false;
    private final Drawable[] mLayers;
    private final Rect mTmpRect = new Rect();
    private TransformCallback mTransformCallback;

    public ArrayDrawable(Drawable[] layers) {
        Preconditions.checkNotNull(layers);
        this.mLayers = layers;
        for (Drawable callbacks : this.mLayers) {
            DrawableUtils.setCallbacks(callbacks, this, this);
        }
        this.mDrawableParents = new DrawableParent[this.mLayers.length];
    }

    public int getNumberOfLayers() {
        return this.mLayers.length;
    }

    @Nullable
    public Drawable getDrawable(int index) {
        boolean z;
        boolean z2 = true;
        if (index >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (index >= this.mLayers.length) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        return this.mLayers[index];
    }

    @Nullable
    public Drawable setDrawable(int index, @Nullable Drawable drawable) {
        boolean z;
        boolean z2 = true;
        if (index >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (index >= this.mLayers.length) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        Drawable oldDrawable = this.mLayers[index];
        if (drawable != oldDrawable) {
            if (drawable != null && this.mIsMutated) {
                drawable.mutate();
            }
            DrawableUtils.setCallbacks(this.mLayers[index], (Drawable.Callback) null, (TransformCallback) null);
            DrawableUtils.setCallbacks(drawable, (Drawable.Callback) null, (TransformCallback) null);
            DrawableUtils.setDrawableProperties(drawable, this.mDrawableProperties);
            if (drawable != null) {
                drawable.setBounds(getBounds());
                drawable.setLevel(getLevel());
                drawable.setState(getState());
                drawable.setVisible(isVisible(), false);
            }
            DrawableUtils.setCallbacks(drawable, this, this);
            this.mIsStatefulCalculated = false;
            this.mLayers[index] = drawable;
            invalidateSelf();
        }
        return oldDrawable;
    }

    public int getIntrinsicWidth() {
        int width = -1;
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                width = Math.max(width, drawable.getIntrinsicWidth());
            }
        }
        if (width > 0) {
            return width;
        }
        return -1;
    }

    public int getIntrinsicHeight() {
        int height = -1;
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                height = Math.max(height, drawable.getIntrinsicHeight());
            }
        }
        if (height > 0) {
            return height;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.setBounds(bounds);
            }
        }
    }

    public boolean isStateful() {
        boolean z;
        if (!this.mIsStatefulCalculated) {
            this.mIsStateful = false;
            for (Drawable drawable : this.mLayers) {
                boolean z2 = this.mIsStateful;
                if (drawable == null || !drawable.isStateful()) {
                    z = false;
                } else {
                    z = true;
                }
                this.mIsStateful = z | z2;
            }
            this.mIsStatefulCalculated = true;
        }
        return this.mIsStateful;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        boolean stateChanged = false;
        for (Drawable drawable : this.mLayers) {
            if (drawable != null && drawable.setState(state)) {
                stateChanged = true;
            }
        }
        return stateChanged;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        boolean levelChanged = false;
        for (Drawable drawable : this.mLayers) {
            if (drawable != null && drawable.setLevel(level)) {
                levelChanged = true;
            }
        }
        return levelChanged;
    }

    public void draw(Canvas canvas) {
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    public boolean getPadding(Rect padding) {
        padding.left = 0;
        padding.top = 0;
        padding.right = 0;
        padding.bottom = 0;
        Rect rect = this.mTmpRect;
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.getPadding(rect);
                padding.left = Math.max(padding.left, rect.left);
                padding.top = Math.max(padding.top, rect.top);
                padding.right = Math.max(padding.right, rect.right);
                padding.bottom = Math.max(padding.bottom, rect.bottom);
            }
        }
        return true;
    }

    public Drawable mutate() {
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.mutate();
            }
        }
        this.mIsMutated = true;
        return this;
    }

    public int getOpacity() {
        if (this.mLayers.length == 0) {
            return -2;
        }
        int opacity = -1;
        for (int i = 1; i < this.mLayers.length; i++) {
            Drawable drawable = this.mLayers[i];
            if (drawable != null) {
                opacity = Drawable.resolveOpacity(opacity, drawable.getOpacity());
            }
        }
        return opacity;
    }

    public void setAlpha(int alpha) {
        this.mDrawableProperties.setAlpha(alpha);
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.setAlpha(alpha);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawableProperties.setColorFilter(colorFilter);
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public void setDither(boolean dither) {
        this.mDrawableProperties.setDither(dither);
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.setDither(dither);
            }
        }
    }

    public void setFilterBitmap(boolean filterBitmap) {
        this.mDrawableProperties.setFilterBitmap(filterBitmap);
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.setFilterBitmap(filterBitmap);
            }
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        for (Drawable drawable : this.mLayers) {
            if (drawable != null) {
                drawable.setVisible(visible, restart);
            }
        }
        return changed;
    }

    public DrawableParent getDrawableParentForIndex(int index) {
        boolean z = true;
        Preconditions.checkArgument(index >= 0);
        if (index >= this.mDrawableParents.length) {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (this.mDrawableParents[index] == null) {
            this.mDrawableParents[index] = createDrawableParentForIndex(index);
        }
        return this.mDrawableParents[index];
    }

    private DrawableParent createDrawableParentForIndex(final int index) {
        return new DrawableParent() {
            public Drawable setDrawable(Drawable newDrawable) {
                return ArrayDrawable.this.setDrawable(index, newDrawable);
            }

            public Drawable getDrawable() {
                return ArrayDrawable.this.getDrawable(index);
            }
        };
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    public void setTransformCallback(TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    public void getTransform(Matrix transform) {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getTransform(transform);
        } else {
            transform.reset();
        }
    }

    public void getRootBounds(RectF bounds) {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getRootBounds(bounds);
        } else {
            bounds.set(getBounds());
        }
    }
}

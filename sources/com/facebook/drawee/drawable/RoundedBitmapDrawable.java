package com.facebook.drawee.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import javax.annotation.Nullable;

public class RoundedBitmapDrawable extends BitmapDrawable implements TransformAwareDrawable, Rounded {
    @VisibleForTesting
    final RectF mBitmapBounds;
    private int mBorderColor;
    private final Paint mBorderPaint;
    private final Path mBorderPath;
    @VisibleForTesting
    final float[] mBorderRadii;
    private float mBorderWidth;
    @VisibleForTesting
    final Matrix mBoundsTransform;
    private final float[] mCornerRadii;
    @VisibleForTesting
    final RectF mDrawableBounds;
    @VisibleForTesting
    final Matrix mInverseParentTransform;
    private boolean mIsCircle;
    private boolean mIsPathDirty;
    private boolean mIsShaderTransformDirty;
    private WeakReference<Bitmap> mLastBitmap;
    private float mPadding;
    private final Paint mPaint;
    @VisibleForTesting
    final Matrix mParentTransform;
    private final Path mPath;
    @VisibleForTesting
    final Matrix mPrevBoundsTransform;
    @VisibleForTesting
    final Matrix mPrevParentTransform;
    @VisibleForTesting
    final RectF mPrevRootBounds;
    private boolean mRadiiNonZero;
    @VisibleForTesting
    final RectF mRootBounds;
    @VisibleForTesting
    final Matrix mTransform;
    @Nullable
    private TransformCallback mTransformCallback;

    public RoundedBitmapDrawable(Resources res, Bitmap bitmap) {
        this(res, bitmap, (Paint) null);
    }

    public RoundedBitmapDrawable(Resources res, Bitmap bitmap, @Nullable Paint paint) {
        super(res, bitmap);
        this.mIsCircle = false;
        this.mRadiiNonZero = false;
        this.mCornerRadii = new float[8];
        this.mBorderRadii = new float[8];
        this.mRootBounds = new RectF();
        this.mPrevRootBounds = new RectF();
        this.mBitmapBounds = new RectF();
        this.mDrawableBounds = new RectF();
        this.mBoundsTransform = new Matrix();
        this.mPrevBoundsTransform = new Matrix();
        this.mParentTransform = new Matrix();
        this.mPrevParentTransform = new Matrix();
        this.mInverseParentTransform = new Matrix();
        this.mTransform = new Matrix();
        this.mBorderWidth = 0.0f;
        this.mBorderColor = 0;
        this.mPadding = 0.0f;
        this.mPath = new Path();
        this.mBorderPath = new Path();
        this.mIsPathDirty = true;
        this.mPaint = new Paint();
        this.mBorderPaint = new Paint(1);
        this.mIsShaderTransformDirty = true;
        if (paint != null) {
            this.mPaint.set(paint);
        }
        this.mPaint.setFlags(1);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    public static RoundedBitmapDrawable fromBitmapDrawable(Resources res, BitmapDrawable bitmapDrawable) {
        return new RoundedBitmapDrawable(res, bitmapDrawable.getBitmap(), bitmapDrawable.getPaint());
    }

    public void setCircle(boolean isCircle) {
        this.mIsCircle = isCircle;
        this.mIsPathDirty = true;
        invalidateSelf();
    }

    public boolean isCircle() {
        return this.mIsCircle;
    }

    public void setRadius(float radius) {
        boolean z = false;
        Preconditions.checkState(radius >= 0.0f);
        Arrays.fill(this.mCornerRadii, radius);
        if (radius != 0.0f) {
            z = true;
        }
        this.mRadiiNonZero = z;
        this.mIsPathDirty = true;
        invalidateSelf();
    }

    public void setRadii(float[] radii) {
        boolean z;
        boolean z2;
        if (radii == null) {
            Arrays.fill(this.mCornerRadii, 0.0f);
            this.mRadiiNonZero = false;
        } else {
            if (radii.length == 8) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "radii should have exactly 8 values");
            System.arraycopy(radii, 0, this.mCornerRadii, 0, 8);
            this.mRadiiNonZero = false;
            for (int i = 0; i < 8; i++) {
                boolean z3 = this.mRadiiNonZero;
                if (radii[i] > 0.0f) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                this.mRadiiNonZero = z2 | z3;
            }
        }
        this.mIsPathDirty = true;
        invalidateSelf();
    }

    public float[] getRadii() {
        return this.mCornerRadii;
    }

    public void setBorder(int color, float width) {
        if (this.mBorderColor != color || this.mBorderWidth != width) {
            this.mBorderColor = color;
            this.mBorderWidth = width;
            this.mIsPathDirty = true;
            invalidateSelf();
        }
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public void setPadding(float padding) {
        if (this.mPadding != padding) {
            this.mPadding = padding;
            this.mIsPathDirty = true;
            invalidateSelf();
        }
    }

    public float getPadding() {
        return this.mPadding;
    }

    public void setTransformCallback(@Nullable TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        super.setColorFilter(colorFilter);
    }

    public void draw(Canvas canvas) {
        if (!shouldRound()) {
            super.draw(canvas);
            return;
        }
        updateTransform();
        updatePath();
        updatePaint();
        int saveCount = canvas.save();
        canvas.concat(this.mInverseParentTransform);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mBorderWidth > 0.0f) {
            this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
            this.mBorderPaint.setColor(DrawableUtils.multiplyColorAlpha(this.mBorderColor, this.mPaint.getAlpha()));
            canvas.drawPath(this.mBorderPath, this.mBorderPaint);
        }
        canvas.restoreToCount(saveCount);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean shouldRound() {
        return this.mIsCircle || this.mRadiiNonZero || this.mBorderWidth > 0.0f;
    }

    private void updateTransform() {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getTransform(this.mParentTransform);
            this.mTransformCallback.getRootBounds(this.mRootBounds);
        } else {
            this.mParentTransform.reset();
            this.mRootBounds.set(getBounds());
        }
        this.mBitmapBounds.set(0.0f, 0.0f, (float) getBitmap().getWidth(), (float) getBitmap().getHeight());
        this.mDrawableBounds.set(getBounds());
        this.mBoundsTransform.setRectToRect(this.mBitmapBounds, this.mDrawableBounds, Matrix.ScaleToFit.FILL);
        if (!this.mParentTransform.equals(this.mPrevParentTransform) || !this.mBoundsTransform.equals(this.mPrevBoundsTransform)) {
            this.mIsShaderTransformDirty = true;
            this.mParentTransform.invert(this.mInverseParentTransform);
            this.mTransform.set(this.mParentTransform);
            this.mTransform.preConcat(this.mBoundsTransform);
            this.mPrevParentTransform.set(this.mParentTransform);
            this.mPrevBoundsTransform.set(this.mBoundsTransform);
        }
        if (!this.mRootBounds.equals(this.mPrevRootBounds)) {
            this.mIsPathDirty = true;
            this.mPrevRootBounds.set(this.mRootBounds);
        }
    }

    private void updatePath() {
        if (this.mIsPathDirty) {
            this.mBorderPath.reset();
            this.mRootBounds.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
            if (this.mIsCircle) {
                this.mBorderPath.addCircle(this.mRootBounds.centerX(), this.mRootBounds.centerY(), Math.min(this.mRootBounds.width(), this.mRootBounds.height()) / 2.0f, Path.Direction.CW);
            } else {
                for (int i = 0; i < this.mBorderRadii.length; i++) {
                    this.mBorderRadii[i] = (this.mCornerRadii[i] + this.mPadding) - (this.mBorderWidth / 2.0f);
                }
                this.mBorderPath.addRoundRect(this.mRootBounds, this.mBorderRadii, Path.Direction.CW);
            }
            this.mRootBounds.inset((-this.mBorderWidth) / 2.0f, (-this.mBorderWidth) / 2.0f);
            this.mPath.reset();
            this.mRootBounds.inset(this.mPadding, this.mPadding);
            if (this.mIsCircle) {
                this.mPath.addCircle(this.mRootBounds.centerX(), this.mRootBounds.centerY(), Math.min(this.mRootBounds.width(), this.mRootBounds.height()) / 2.0f, Path.Direction.CW);
            } else {
                this.mPath.addRoundRect(this.mRootBounds, this.mCornerRadii, Path.Direction.CW);
            }
            this.mRootBounds.inset(-this.mPadding, -this.mPadding);
            this.mPath.setFillType(Path.FillType.WINDING);
            this.mIsPathDirty = false;
        }
    }

    private void updatePaint() {
        Bitmap bitmap = getBitmap();
        if (this.mLastBitmap == null || this.mLastBitmap.get() != bitmap) {
            this.mLastBitmap = new WeakReference<>(bitmap);
            this.mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            this.mIsShaderTransformDirty = true;
        }
        if (this.mIsShaderTransformDirty) {
            this.mPaint.getShader().setLocalMatrix(this.mTransform);
            this.mIsShaderTransformDirty = false;
        }
    }
}

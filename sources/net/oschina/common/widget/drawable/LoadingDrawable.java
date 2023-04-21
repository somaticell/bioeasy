package net.oschina.common.widget.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

public abstract class LoadingDrawable extends Drawable implements Animatable, Animatable {
    private static final int LINE_SIZE = 4;
    private final Runnable mAnim = new Runnable() {
        public void run() {
            if (LoadingDrawable.this.mRun) {
                LoadingDrawable.this.onRefresh();
                LoadingDrawable.this.invalidateSelf();
                return;
            }
            LoadingDrawable.this.unscheduleSelf(this);
        }
    };
    protected Paint mBackgroundPaint = new Paint(1);
    private int[] mForegroundColor = {-872415232, -100251, -8117352};
    private int mForegroundColorIndex = 0;
    protected Paint mForegroundPaint = new Paint(1);
    protected float mProgress;
    /* access modifiers changed from: private */
    public boolean mRun;

    /* access modifiers changed from: protected */
    public abstract void drawBackground(Canvas canvas, Paint paint);

    /* access modifiers changed from: protected */
    public abstract void drawForeground(Canvas canvas, Paint paint);

    /* access modifiers changed from: protected */
    public abstract void onProgressChange(float f);

    /* access modifiers changed from: protected */
    public abstract void onRefresh();

    public LoadingDrawable() {
        Paint bPaint = this.mBackgroundPaint;
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setAntiAlias(true);
        bPaint.setDither(true);
        bPaint.setStrokeWidth(4.0f);
        bPaint.setColor(838860800);
        Paint fPaint = this.mForegroundPaint;
        fPaint.setStyle(Paint.Style.STROKE);
        fPaint.setAntiAlias(true);
        fPaint.setDither(true);
        fPaint.setStrokeWidth(4.0f);
        fPaint.setColor(this.mForegroundColor[0]);
    }

    public int getIntrinsicHeight() {
        return (int) (2.0f * Math.max(this.mBackgroundPaint.getStrokeWidth(), this.mForegroundPaint.getStrokeWidth()));
    }

    public int getIntrinsicWidth() {
        return (int) (2.0f * Math.max(this.mBackgroundPaint.getStrokeWidth(), this.mForegroundPaint.getStrokeWidth()));
    }

    public void setBackgroundLineSize(float size) {
        this.mBackgroundPaint.setStrokeWidth(size);
        onBoundsChange(getBounds());
    }

    public void setForegroundLineSize(float size) {
        this.mForegroundPaint.setStrokeWidth(size);
        onBoundsChange(getBounds());
    }

    public float getBackgroundLineSize() {
        return this.mBackgroundPaint.getStrokeWidth();
    }

    public float getForegroundLineSize() {
        return this.mForegroundPaint.getStrokeWidth();
    }

    public void setBackgroundColor(int color) {
        this.mBackgroundPaint.setColor(color);
    }

    public int getBackgroundColor() {
        return this.mBackgroundPaint.getColor();
    }

    public void setForegroundColor(int[] colors) {
        if (colors != null) {
            this.mForegroundColor = colors;
            this.mForegroundColorIndex = -1;
            getNextForegroundColor();
        }
    }

    public int[] getForegroundColor() {
        return this.mForegroundColor;
    }

    /* access modifiers changed from: package-private */
    public int getNextForegroundColor() {
        int[] colors = this.mForegroundColor;
        Paint fPaint = this.mForegroundPaint;
        if (colors.length > 1) {
            int index = this.mForegroundColorIndex + 1;
            if (index >= colors.length) {
                index = 0;
            }
            fPaint.setColor(colors[index]);
            this.mForegroundColorIndex = index;
        } else {
            fPaint.setColor(colors[0]);
        }
        return fPaint.getColor();
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float progress) {
        if (progress < 0.0f) {
            this.mProgress = 0.0f;
        } else if (this.mProgress > 1.0f) {
            this.mProgress = 1.0f;
        } else {
            this.mProgress = progress;
        }
        stop();
        onProgressChange(this.mProgress);
        invalidateSelf();
    }

    public boolean isRunning() {
        return this.mRun;
    }

    public void start() {
        if (!this.mRun) {
            this.mRun = true;
            scheduleSelf(this.mAnim, SystemClock.uptimeMillis() + 16);
        }
    }

    public void stop() {
        if (this.mRun) {
            this.mRun = false;
            unscheduleSelf(this.mAnim);
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        int count = canvas.save();
        Paint bPaint = this.mBackgroundPaint;
        if (bPaint.getColor() != 0 && bPaint.getStrokeWidth() > 0.0f) {
            drawBackground(canvas, bPaint);
        }
        Paint fPaint = this.mForegroundPaint;
        if (this.mRun) {
            if (fPaint.getColor() != 0 && fPaint.getStrokeWidth() > 0.0f) {
                drawForeground(canvas, fPaint);
            }
            scheduleSelf(this.mAnim, SystemClock.uptimeMillis() + 16);
        } else if (this.mProgress > 0.0f && fPaint.getColor() != 0 && fPaint.getStrokeWidth() > 0.0f) {
            drawForeground(canvas, fPaint);
        }
        canvas.restoreToCount(count);
    }

    public void setAlpha(int alpha) {
        this.mForegroundPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        boolean needRefresh = false;
        Paint bPaint = this.mBackgroundPaint;
        if (bPaint.getColorFilter() != cf) {
            bPaint.setColorFilter(cf);
            needRefresh = true;
        }
        Paint fPaint = this.mForegroundPaint;
        if (fPaint.getColorFilter() != cf) {
            fPaint.setColorFilter(cf);
            needRefresh = true;
        }
        if (needRefresh) {
            invalidateSelf();
        }
    }

    public int getOpacity() {
        Paint bPaint = this.mBackgroundPaint;
        Paint fPaint = this.mForegroundPaint;
        if (bPaint.getXfermode() == null && fPaint.getXfermode() == null) {
            int alpha = Color.alpha(fPaint.getColor());
            if (alpha == 0) {
                return -2;
            }
            if (alpha == 255) {
                return -1;
            }
        }
        return -3;
    }
}

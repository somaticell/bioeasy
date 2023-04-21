package com.facebook.imagepipeline.animated.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.time.MonotonicClock;
import com.facebook.drawable.base.DrawableWithCaches;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AnimatedDrawable extends Drawable implements AnimatableDrawable, DrawableWithCaches {
    private static final int NO_FRAME = -1;
    private static final int POLL_FOR_RENDERED_FRAME_MS = 5;
    /* access modifiers changed from: private */
    public static final Class<?> TAG = AnimatedDrawable.class;
    private static final long WATCH_DOG_TIMER_MIN_TIMEOUT_MS = 1000;
    private static final long WATCH_DOG_TIMER_POLL_INTERVAL_MS = 2000;
    private AnimatedDrawableCachingBackend mAnimatedDrawableBackend;
    private final AnimatedDrawableDiagnostics mAnimatedDrawableDiagnostics;
    private boolean mApplyTransformation;
    private final Rect mDstRect = new Rect();
    private final int mDurationMs;
    private final int mFrameCount;
    private boolean mHaveWatchdogScheduled;
    private final Runnable mInvalidateTask = new Runnable() {
        public void run() {
            FLog.v((Class<?>) AnimatedDrawable.TAG, "(%s) Invalidate Task", (Object) AnimatedDrawable.this.mLogId);
            boolean unused = AnimatedDrawable.this.mInvalidateTaskScheduled = false;
            AnimatedDrawable.this.doInvalidateSelf();
        }
    };
    /* access modifiers changed from: private */
    public boolean mInvalidateTaskScheduled;
    private boolean mIsRunning;
    private CloseableReference<Bitmap> mLastDrawnFrame;
    private int mLastDrawnFrameMonotonicNumber = -1;
    private int mLastDrawnFrameNumber = -1;
    private long mLastInvalidateTimeMs = -1;
    /* access modifiers changed from: private */
    public volatile String mLogId;
    private final MonotonicClock mMonotonicClock;
    private final Runnable mNextFrameTask = new Runnable() {
        public void run() {
            FLog.v((Class<?>) AnimatedDrawable.TAG, "(%s) Next Frame Task", (Object) AnimatedDrawable.this.mLogId);
            AnimatedDrawable.this.onNextFrame();
        }
    };
    private long mNextFrameTaskMs = -1;
    private final Paint mPaint = new Paint(6);
    private int mPendingRenderedFrameMonotonicNumber;
    private int mPendingRenderedFrameNumber;
    private final ScheduledExecutorService mScheduledExecutorServiceForUiThread;
    private int mScheduledFrameMonotonicNumber;
    private int mScheduledFrameNumber;
    private final Runnable mStartTask = new Runnable() {
        public void run() {
            AnimatedDrawable.this.onStart();
        }
    };
    private long mStartTimeMs;
    private float mSx = 1.0f;
    private float mSy = 1.0f;
    private final int mTotalLoops;
    private final Paint mTransparentPaint;
    private boolean mWaitingForDraw;
    private final Runnable mWatchdogTask = new Runnable() {
        public void run() {
            FLog.v((Class<?>) AnimatedDrawable.TAG, "(%s) Watchdog Task", (Object) AnimatedDrawable.this.mLogId);
            AnimatedDrawable.this.doWatchdogCheck();
        }
    };

    public AnimatedDrawable(ScheduledExecutorService scheduledExecutorServiceForUiThread, AnimatedDrawableCachingBackend animatedDrawableBackend, AnimatedDrawableDiagnostics animatedDrawableDiagnostics, MonotonicClock monotonicClock) {
        this.mScheduledExecutorServiceForUiThread = scheduledExecutorServiceForUiThread;
        this.mAnimatedDrawableBackend = animatedDrawableBackend;
        this.mAnimatedDrawableDiagnostics = animatedDrawableDiagnostics;
        this.mMonotonicClock = monotonicClock;
        this.mDurationMs = this.mAnimatedDrawableBackend.getDurationMs();
        this.mFrameCount = this.mAnimatedDrawableBackend.getFrameCount();
        this.mAnimatedDrawableDiagnostics.setBackend(this.mAnimatedDrawableBackend);
        this.mTotalLoops = this.mAnimatedDrawableBackend.getLoopCount();
        this.mTransparentPaint = new Paint();
        this.mTransparentPaint.setColor(0);
        this.mTransparentPaint.setStyle(Paint.Style.FILL);
        resetToPreviewFrame();
    }

    private void resetToPreviewFrame() {
        this.mScheduledFrameNumber = this.mAnimatedDrawableBackend.getFrameForPreview();
        this.mScheduledFrameMonotonicNumber = this.mScheduledFrameNumber;
        this.mPendingRenderedFrameNumber = -1;
        this.mPendingRenderedFrameMonotonicNumber = -1;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        if (this.mLastDrawnFrame != null) {
            this.mLastDrawnFrame.close();
            this.mLastDrawnFrame = null;
        }
    }

    public void setLogId(String logId) {
        this.mLogId = logId;
    }

    public int getIntrinsicWidth() {
        return this.mAnimatedDrawableBackend.getWidth();
    }

    public int getIntrinsicHeight() {
        return this.mAnimatedDrawableBackend.getHeight();
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
        doInvalidateSelf();
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
        doInvalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mApplyTransformation = true;
        if (this.mLastDrawnFrame != null) {
            this.mLastDrawnFrame.close();
            this.mLastDrawnFrame = null;
        }
        this.mLastDrawnFrameNumber = -1;
        this.mLastDrawnFrameMonotonicNumber = -1;
        this.mAnimatedDrawableBackend.dropCaches();
    }

    /* access modifiers changed from: private */
    public void onStart() {
        if (this.mIsRunning) {
            this.mAnimatedDrawableDiagnostics.onStartMethodBegin();
            try {
                this.mStartTimeMs = this.mMonotonicClock.now();
                this.mScheduledFrameNumber = 0;
                this.mScheduledFrameMonotonicNumber = 0;
                long nextFrameMs = this.mStartTimeMs + ((long) this.mAnimatedDrawableBackend.getDurationMsForFrame(0));
                scheduleSelf(this.mNextFrameTask, nextFrameMs);
                this.mNextFrameTaskMs = nextFrameMs;
                doInvalidateSelf();
            } finally {
                this.mAnimatedDrawableDiagnostics.onStartMethodEnd();
            }
        }
    }

    /* access modifiers changed from: private */
    public void onNextFrame() {
        this.mNextFrameTaskMs = -1;
        if (this.mIsRunning && this.mDurationMs != 0) {
            this.mAnimatedDrawableDiagnostics.onNextFrameMethodBegin();
            try {
                computeAndScheduleNextFrame(true);
            } finally {
                this.mAnimatedDrawableDiagnostics.onNextFrameMethodEnd();
            }
        }
    }

    private void computeAndScheduleNextFrame(boolean scheduleNextFrame) {
        if (this.mDurationMs != 0) {
            long nowMs = this.mMonotonicClock.now();
            int loops = (int) ((nowMs - this.mStartTimeMs) / ((long) this.mDurationMs));
            if (this.mTotalLoops <= 0 || loops < this.mTotalLoops) {
                int timestampMs = (int) ((nowMs - this.mStartTimeMs) % ((long) this.mDurationMs));
                int newCurrentFrameNumber = this.mAnimatedDrawableBackend.getFrameForTimestampMs(timestampMs);
                boolean changed = this.mScheduledFrameNumber != newCurrentFrameNumber;
                this.mScheduledFrameNumber = newCurrentFrameNumber;
                this.mScheduledFrameMonotonicNumber = (this.mFrameCount * loops) + newCurrentFrameNumber;
                if (!scheduleNextFrame) {
                    return;
                }
                if (changed) {
                    doInvalidateSelf();
                    return;
                }
                int durationMs = (this.mAnimatedDrawableBackend.getTimestampMsForFrame(this.mScheduledFrameNumber) + this.mAnimatedDrawableBackend.getDurationMsForFrame(this.mScheduledFrameNumber)) - timestampMs;
                int nextFrame = (this.mScheduledFrameNumber + 1) % this.mFrameCount;
                long nextFrameMs = nowMs + ((long) durationMs);
                if (this.mNextFrameTaskMs == -1 || this.mNextFrameTaskMs > nextFrameMs) {
                    FLog.v(TAG, "(%s) Next frame (%d) in %d ms", (Object) this.mLogId, (Object) Integer.valueOf(nextFrame), (Object) Integer.valueOf(durationMs));
                    unscheduleSelf(this.mNextFrameTask);
                    scheduleSelf(this.mNextFrameTask, nextFrameMs);
                    this.mNextFrameTaskMs = nextFrameMs;
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        CloseableReference<Bitmap> previewBitmapReference;
        this.mAnimatedDrawableDiagnostics.onDrawMethodBegin();
        try {
            this.mWaitingForDraw = false;
            if (this.mIsRunning && !this.mHaveWatchdogScheduled) {
                this.mScheduledExecutorServiceForUiThread.schedule(this.mWatchdogTask, WATCH_DOG_TIMER_POLL_INTERVAL_MS, TimeUnit.MILLISECONDS);
                this.mHaveWatchdogScheduled = true;
            }
            if (this.mApplyTransformation) {
                this.mDstRect.set(getBounds());
                if (!this.mDstRect.isEmpty()) {
                    AnimatedDrawableCachingBackend newBackend = this.mAnimatedDrawableBackend.forNewBounds(this.mDstRect);
                    if (newBackend != this.mAnimatedDrawableBackend) {
                        this.mAnimatedDrawableBackend.dropCaches();
                        this.mAnimatedDrawableBackend = newBackend;
                        this.mAnimatedDrawableDiagnostics.setBackend(newBackend);
                    }
                    this.mSx = ((float) this.mDstRect.width()) / ((float) this.mAnimatedDrawableBackend.getRenderedWidth());
                    this.mSy = ((float) this.mDstRect.height()) / ((float) this.mAnimatedDrawableBackend.getRenderedHeight());
                    this.mApplyTransformation = false;
                }
            }
            if (!this.mDstRect.isEmpty()) {
                canvas.save();
                canvas.scale(this.mSx, this.mSy);
                boolean didDrawFrame = false;
                if (this.mPendingRenderedFrameNumber != -1) {
                    boolean rendered = renderFrame(canvas, this.mPendingRenderedFrameNumber, this.mPendingRenderedFrameMonotonicNumber);
                    didDrawFrame = false | rendered;
                    if (rendered) {
                        FLog.v(TAG, "(%s) Rendered pending frame %d", (Object) this.mLogId, (Object) Integer.valueOf(this.mPendingRenderedFrameNumber));
                        this.mPendingRenderedFrameNumber = -1;
                        this.mPendingRenderedFrameMonotonicNumber = -1;
                    } else {
                        FLog.v(TAG, "(%s) Trying again later for pending %d", (Object) this.mLogId, (Object) Integer.valueOf(this.mPendingRenderedFrameNumber));
                        scheduleInvalidatePoll();
                    }
                }
                if (this.mPendingRenderedFrameNumber == -1) {
                    if (this.mIsRunning) {
                        computeAndScheduleNextFrame(false);
                    }
                    boolean rendered2 = renderFrame(canvas, this.mScheduledFrameNumber, this.mScheduledFrameMonotonicNumber);
                    didDrawFrame |= rendered2;
                    if (rendered2) {
                        FLog.v(TAG, "(%s) Rendered current frame %d", (Object) this.mLogId, (Object) Integer.valueOf(this.mScheduledFrameNumber));
                        if (this.mIsRunning) {
                            computeAndScheduleNextFrame(true);
                        }
                    } else {
                        FLog.v(TAG, "(%s) Trying again later for current %d", (Object) this.mLogId, (Object) Integer.valueOf(this.mScheduledFrameNumber));
                        this.mPendingRenderedFrameNumber = this.mScheduledFrameNumber;
                        this.mPendingRenderedFrameMonotonicNumber = this.mScheduledFrameMonotonicNumber;
                        scheduleInvalidatePoll();
                    }
                }
                if (!didDrawFrame && this.mLastDrawnFrame != null) {
                    canvas.drawBitmap(this.mLastDrawnFrame.get(), 0.0f, 0.0f, this.mPaint);
                    didDrawFrame = true;
                    FLog.v(TAG, "(%s) Rendered last known frame %d", (Object) this.mLogId, (Object) Integer.valueOf(this.mLastDrawnFrameNumber));
                }
                if (!didDrawFrame && (previewBitmapReference = this.mAnimatedDrawableBackend.getPreviewBitmap()) != null) {
                    canvas.drawBitmap(previewBitmapReference.get(), 0.0f, 0.0f, this.mPaint);
                    previewBitmapReference.close();
                    FLog.v(TAG, "(%s) Rendered preview frame", (Object) this.mLogId);
                    didDrawFrame = true;
                }
                if (!didDrawFrame) {
                    canvas.drawRect(0.0f, 0.0f, (float) this.mDstRect.width(), (float) this.mDstRect.height(), this.mTransparentPaint);
                    FLog.v(TAG, "(%s) Failed to draw a frame", (Object) this.mLogId);
                }
                canvas.restore();
                this.mAnimatedDrawableDiagnostics.drawDebugOverlay(canvas, this.mDstRect);
                this.mAnimatedDrawableDiagnostics.onDrawMethodEnd();
            }
        } finally {
            this.mAnimatedDrawableDiagnostics.onDrawMethodEnd();
        }
    }

    private void scheduleInvalidatePoll() {
        if (!this.mInvalidateTaskScheduled) {
            this.mInvalidateTaskScheduled = true;
            scheduleSelf(this.mInvalidateTask, 5);
        }
    }

    public boolean didLastDrawRender() {
        return this.mLastDrawnFrame != null;
    }

    private boolean renderFrame(Canvas canvas, int frameNumber, int frameMonotonicNumber) {
        CloseableReference<Bitmap> bitmapReference = this.mAnimatedDrawableBackend.getBitmapForFrame(frameNumber);
        if (bitmapReference == null) {
            return false;
        }
        canvas.drawBitmap(bitmapReference.get(), 0.0f, 0.0f, this.mPaint);
        if (this.mLastDrawnFrame != null) {
            this.mLastDrawnFrame.close();
        }
        if (this.mIsRunning && frameMonotonicNumber > this.mLastDrawnFrameMonotonicNumber) {
            int droppedFrames = (frameMonotonicNumber - this.mLastDrawnFrameMonotonicNumber) - 1;
            this.mAnimatedDrawableDiagnostics.incrementDrawnFrames(1);
            this.mAnimatedDrawableDiagnostics.incrementDroppedFrames(droppedFrames);
            if (droppedFrames > 0) {
                FLog.v(TAG, "(%s) Dropped %d frames", (Object) this.mLogId, (Object) Integer.valueOf(droppedFrames));
            }
        }
        this.mLastDrawnFrame = bitmapReference;
        this.mLastDrawnFrameNumber = frameNumber;
        this.mLastDrawnFrameMonotonicNumber = frameMonotonicNumber;
        FLog.v(TAG, "(%s) Drew frame %d", (Object) this.mLogId, (Object) Integer.valueOf(frameNumber));
        return true;
    }

    /* access modifiers changed from: private */
    public void doWatchdogCheck() {
        boolean hasNotDrawnWithinTimeout;
        boolean hasNotAdvancedFrameWithinTimeout;
        this.mHaveWatchdogScheduled = false;
        if (this.mIsRunning) {
            long now = this.mMonotonicClock.now();
            if (!this.mWaitingForDraw || now - this.mLastInvalidateTimeMs <= WATCH_DOG_TIMER_MIN_TIMEOUT_MS) {
                hasNotDrawnWithinTimeout = false;
            } else {
                hasNotDrawnWithinTimeout = true;
            }
            if (this.mNextFrameTaskMs == -1 || now - this.mNextFrameTaskMs <= WATCH_DOG_TIMER_MIN_TIMEOUT_MS) {
                hasNotAdvancedFrameWithinTimeout = false;
            } else {
                hasNotAdvancedFrameWithinTimeout = true;
            }
            if (hasNotDrawnWithinTimeout || hasNotAdvancedFrameWithinTimeout) {
                dropCaches();
                doInvalidateSelf();
                return;
            }
            this.mScheduledExecutorServiceForUiThread.schedule(this.mWatchdogTask, WATCH_DOG_TIMER_POLL_INTERVAL_MS, TimeUnit.MILLISECONDS);
            this.mHaveWatchdogScheduled = true;
        }
    }

    /* access modifiers changed from: private */
    public void doInvalidateSelf() {
        this.mWaitingForDraw = true;
        this.mLastInvalidateTimeMs = this.mMonotonicClock.now();
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isWaitingForDraw() {
        return this.mWaitingForDraw;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isWaitingForNextFrame() {
        return this.mNextFrameTaskMs != -1;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getScheduledFrameNumber() {
        return this.mScheduledFrameNumber;
    }

    public void start() {
        if (this.mDurationMs != 0 && this.mFrameCount > 1) {
            this.mIsRunning = true;
            scheduleSelf(this.mStartTask, this.mMonotonicClock.now());
        }
    }

    public void stop() {
        this.mIsRunning = false;
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        int frame;
        if (this.mIsRunning || (frame = this.mAnimatedDrawableBackend.getFrameForTimestampMs(level)) == this.mScheduledFrameNumber) {
            return false;
        }
        try {
            this.mScheduledFrameNumber = frame;
            this.mScheduledFrameMonotonicNumber = frame;
            doInvalidateSelf();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public ValueAnimator createValueAnimator(int maxDurationMs) {
        ValueAnimator animator = createValueAnimator();
        animator.setRepeatCount(Math.max(maxDurationMs / this.mAnimatedDrawableBackend.getDurationMs(), 1));
        return animator;
    }

    public ValueAnimator createValueAnimator() {
        int loopCount = this.mAnimatedDrawableBackend.getLoopCount();
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0, this.mDurationMs);
        animator.setDuration((long) this.mDurationMs);
        if (loopCount == 0) {
            loopCount = -1;
        }
        animator.setRepeatCount(loopCount);
        animator.setRepeatMode(1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(createAnimatorUpdateListener());
        return animator;
    }

    public ValueAnimator.AnimatorUpdateListener createAnimatorUpdateListener() {
        return new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimatedDrawable.this.setLevel(((Integer) animation.getAnimatedValue()).intValue());
            }
        };
    }

    public void dropCaches() {
        FLog.v(TAG, "(%s) Dropping caches", (Object) this.mLogId);
        if (this.mLastDrawnFrame != null) {
            this.mLastDrawnFrame.close();
            this.mLastDrawnFrame = null;
            this.mLastDrawnFrameNumber = -1;
            this.mLastDrawnFrameMonotonicNumber = -1;
        }
        this.mAnimatedDrawableBackend.dropCaches();
    }
}

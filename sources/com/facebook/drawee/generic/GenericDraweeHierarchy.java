package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;
import javax.annotation.Nullable;

public class GenericDraweeHierarchy implements SettableDraweeHierarchy {
    private final int mActualImageIndex;
    private final ForwardingDrawable mActualImageWrapper;
    private final Drawable mEmptyActualImageDrawable = new ColorDrawable(0);
    private final FadeDrawable mFadeDrawable;
    private final int mFailureImageIndex;
    private final int mPlaceholderImageIndex;
    private final int mProgressBarImageIndex;
    private final Resources mResources;
    private final int mRetryImageIndex;
    private RoundingParams mRoundingParams;
    private final RootDrawable mTopLevelDrawable;

    GenericDraweeHierarchy(GenericDraweeHierarchyBuilder builder) {
        this.mResources = builder.getResources();
        this.mRoundingParams = builder.getRoundingParams();
        this.mActualImageWrapper = new ForwardingDrawable(this.mEmptyActualImageDrawable);
        int numBackgrounds = builder.getBackgrounds() != null ? builder.getBackgrounds().size() : 0;
        int numOverlays = (builder.getOverlays() != null ? builder.getOverlays().size() : 0) + (builder.getPressedStateOverlay() != null ? 1 : 0);
        int numLayers = 0 + numBackgrounds;
        int numLayers2 = numLayers + 1;
        this.mPlaceholderImageIndex = numLayers;
        int numLayers3 = numLayers2 + 1;
        this.mActualImageIndex = numLayers2;
        int numLayers4 = numLayers3 + 1;
        this.mProgressBarImageIndex = numLayers3;
        int numLayers5 = numLayers4 + 1;
        this.mRetryImageIndex = numLayers4;
        int numLayers6 = numLayers5 + 1;
        this.mFailureImageIndex = numLayers5;
        int overlaysIndex = numLayers6;
        Drawable[] layers = new Drawable[(numLayers6 + numOverlays)];
        if (numBackgrounds > 0) {
            int index = 0;
            for (Drawable background : builder.getBackgrounds()) {
                layers[index + 0] = buildBranch(background, (ScalingUtils.ScaleType) null);
                index++;
            }
        }
        layers[this.mPlaceholderImageIndex] = buildBranch(builder.getPlaceholderImage(), builder.getPlaceholderImageScaleType());
        layers[this.mActualImageIndex] = buildActualImageBranch(this.mActualImageWrapper, builder.getActualImageScaleType(), builder.getActualImageFocusPoint(), builder.getActualImageMatrix(), builder.getActualImageColorFilter());
        layers[this.mProgressBarImageIndex] = buildBranch(builder.getProgressBarImage(), builder.getProgressBarImageScaleType());
        layers[this.mRetryImageIndex] = buildBranch(builder.getRetryImage(), builder.getRetryImageScaleType());
        layers[this.mFailureImageIndex] = buildBranch(builder.getFailureImage(), builder.getFailureImageScaleType());
        if (numOverlays > 0) {
            int index2 = 0;
            if (builder.getOverlays() != null) {
                for (Drawable overlay : builder.getOverlays()) {
                    layers[overlaysIndex + index2] = buildBranch(overlay, (ScalingUtils.ScaleType) null);
                    index2++;
                }
            }
            if (builder.getPressedStateOverlay() != null) {
                layers[overlaysIndex + index2] = buildBranch(builder.getPressedStateOverlay(), (ScalingUtils.ScaleType) null);
            }
        }
        this.mFadeDrawable = new FadeDrawable(layers);
        this.mFadeDrawable.setTransitionDuration(builder.getFadeDuration());
        this.mTopLevelDrawable = new RootDrawable(WrappingUtils.maybeWrapWithRoundedOverlayColor(this.mFadeDrawable, this.mRoundingParams));
        this.mTopLevelDrawable.mutate();
        resetFade();
    }

    @Nullable
    private Drawable buildActualImageBranch(Drawable drawable, @Nullable ScalingUtils.ScaleType scaleType, @Nullable PointF focusPoint, @Nullable Matrix matrix, @Nullable ColorFilter colorFilter) {
        drawable.setColorFilter(colorFilter);
        return WrappingUtils.maybeWrapWithMatrix(WrappingUtils.maybeWrapWithScaleType(drawable, scaleType, focusPoint), matrix);
    }

    @Nullable
    private Drawable buildBranch(@Nullable Drawable drawable, @Nullable ScalingUtils.ScaleType scaleType) {
        return WrappingUtils.maybeWrapWithScaleType(WrappingUtils.maybeApplyLeafRounding(drawable, this.mRoundingParams, this.mResources), scaleType);
    }

    private void resetActualImages() {
        this.mActualImageWrapper.setDrawable(this.mEmptyActualImageDrawable);
    }

    private void resetFade() {
        if (this.mFadeDrawable != null) {
            this.mFadeDrawable.beginBatchMode();
            this.mFadeDrawable.fadeInAllLayers();
            fadeOutBranches();
            fadeInLayer(this.mPlaceholderImageIndex);
            this.mFadeDrawable.finishTransitionImmediately();
            this.mFadeDrawable.endBatchMode();
        }
    }

    private void fadeOutBranches() {
        fadeOutLayer(this.mPlaceholderImageIndex);
        fadeOutLayer(this.mActualImageIndex);
        fadeOutLayer(this.mProgressBarImageIndex);
        fadeOutLayer(this.mRetryImageIndex);
        fadeOutLayer(this.mFailureImageIndex);
    }

    private void fadeInLayer(int index) {
        if (index >= 0) {
            this.mFadeDrawable.fadeInLayer(index);
        }
    }

    private void fadeOutLayer(int index) {
        if (index >= 0) {
            this.mFadeDrawable.fadeOutLayer(index);
        }
    }

    private void setProgress(float progress) {
        Drawable progressBarDrawable = getLayerParentDrawable(this.mProgressBarImageIndex).getDrawable();
        if (progressBarDrawable != null) {
            if (progress >= 0.999f) {
                if (progressBarDrawable instanceof Animatable) {
                    ((Animatable) progressBarDrawable).stop();
                }
                fadeOutLayer(this.mProgressBarImageIndex);
            } else {
                if (progressBarDrawable instanceof Animatable) {
                    ((Animatable) progressBarDrawable).start();
                }
                fadeInLayer(this.mProgressBarImageIndex);
            }
            progressBarDrawable.setLevel(Math.round(10000.0f * progress));
        }
    }

    public Drawable getTopLevelDrawable() {
        return this.mTopLevelDrawable;
    }

    public void reset() {
        resetActualImages();
        resetFade();
    }

    public void setImage(Drawable drawable, float progress, boolean immediate) {
        Drawable drawable2 = WrappingUtils.maybeApplyLeafRounding(drawable, this.mRoundingParams, this.mResources);
        drawable2.mutate();
        this.mActualImageWrapper.setDrawable(drawable2);
        this.mFadeDrawable.beginBatchMode();
        fadeOutBranches();
        fadeInLayer(this.mActualImageIndex);
        setProgress(progress);
        if (immediate) {
            this.mFadeDrawable.finishTransitionImmediately();
        }
        this.mFadeDrawable.endBatchMode();
    }

    public void setProgress(float progress, boolean immediate) {
        this.mFadeDrawable.beginBatchMode();
        setProgress(progress);
        if (immediate) {
            this.mFadeDrawable.finishTransitionImmediately();
        }
        this.mFadeDrawable.endBatchMode();
    }

    public void setFailure(Throwable throwable) {
        this.mFadeDrawable.beginBatchMode();
        fadeOutBranches();
        if (this.mFadeDrawable.getDrawable(this.mFailureImageIndex) != null) {
            fadeInLayer(this.mFailureImageIndex);
        } else {
            fadeInLayer(this.mPlaceholderImageIndex);
        }
        this.mFadeDrawable.endBatchMode();
    }

    public void setRetry(Throwable throwable) {
        this.mFadeDrawable.beginBatchMode();
        fadeOutBranches();
        if (this.mFadeDrawable.getDrawable(this.mRetryImageIndex) != null) {
            fadeInLayer(this.mRetryImageIndex);
        } else {
            fadeInLayer(this.mPlaceholderImageIndex);
        }
        this.mFadeDrawable.endBatchMode();
    }

    public void setControllerOverlay(@Nullable Drawable drawable) {
        this.mTopLevelDrawable.setControllerOverlay(drawable);
    }

    private DrawableParent getLayerParentDrawable(int index) {
        DrawableParent parent = this.mFadeDrawable.getDrawableParentForIndex(index);
        if (parent.getDrawable() instanceof MatrixDrawable) {
            parent = (MatrixDrawable) parent.getDrawable();
        }
        if (parent.getDrawable() instanceof ScaleTypeDrawable) {
            return (ScaleTypeDrawable) parent.getDrawable();
        }
        return parent;
    }

    private void setLayerChildDrawable(int index, @Nullable Drawable drawable) {
        if (drawable == null) {
            this.mFadeDrawable.setDrawable(index, (Drawable) null);
            return;
        }
        getLayerParentDrawable(index).setDrawable(WrappingUtils.maybeApplyLeafRounding(drawable, this.mRoundingParams, this.mResources));
    }

    private ScaleTypeDrawable getLayerScaleTypeDrawable(int index) {
        DrawableParent parent = getLayerParentDrawable(index);
        if (parent instanceof ScaleTypeDrawable) {
            return (ScaleTypeDrawable) parent;
        }
        return WrappingUtils.wrapChildWithScaleType(parent, ScalingUtils.ScaleType.FIT_XY);
    }

    public void setFadeDuration(int durationMs) {
        this.mFadeDrawable.setTransitionDuration(durationMs);
    }

    public void setActualImageFocusPoint(PointF focusPoint) {
        Preconditions.checkNotNull(focusPoint);
        getLayerScaleTypeDrawable(this.mActualImageIndex).setFocusPoint(focusPoint);
    }

    public void setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
        Preconditions.checkNotNull(scaleType);
        getLayerScaleTypeDrawable(this.mActualImageIndex).setScaleType(scaleType);
    }

    public void setActualImageColorFilter(ColorFilter colorfilter) {
        this.mActualImageWrapper.setColorFilter(colorfilter);
    }

    public void getActualImageBounds(RectF outBounds) {
        this.mActualImageWrapper.getTransformedBounds(outBounds);
    }

    public void setPlaceholderImage(@Nullable Drawable drawable) {
        setLayerChildDrawable(this.mPlaceholderImageIndex, drawable);
    }

    public void setPlaceholderImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        setLayerChildDrawable(this.mPlaceholderImageIndex, drawable);
        getLayerScaleTypeDrawable(this.mPlaceholderImageIndex).setScaleType(scaleType);
    }

    public void setPlaceholderImageFocusPoint(PointF focusPoint) {
        Preconditions.checkNotNull(focusPoint);
        getLayerScaleTypeDrawable(this.mPlaceholderImageIndex).setFocusPoint(focusPoint);
    }

    public void setPlaceholderImage(int resourceId) {
        setPlaceholderImage(this.mResources.getDrawable(resourceId));
    }

    public void setFailureImage(@Nullable Drawable drawable) {
        setLayerChildDrawable(this.mFailureImageIndex, drawable);
    }

    public void setFailureImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        setLayerChildDrawable(this.mFailureImageIndex, drawable);
        getLayerScaleTypeDrawable(this.mFailureImageIndex).setScaleType(scaleType);
    }

    public void setRetryImage(@Nullable Drawable drawable) {
        setLayerChildDrawable(this.mRetryImageIndex, drawable);
    }

    public void setRetryImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        setLayerChildDrawable(this.mRetryImageIndex, drawable);
        getLayerScaleTypeDrawable(this.mRetryImageIndex).setScaleType(scaleType);
    }

    public void setProgressBarImage(@Nullable Drawable drawable) {
        setLayerChildDrawable(this.mProgressBarImageIndex, drawable);
    }

    public void setProgressBarImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        setLayerChildDrawable(this.mProgressBarImageIndex, drawable);
        getLayerScaleTypeDrawable(this.mProgressBarImageIndex).setScaleType(scaleType);
    }

    public void setRoundingParams(RoundingParams roundingParams) {
        this.mRoundingParams = roundingParams;
        WrappingUtils.updateOverlayColorRounding(this.mTopLevelDrawable, this.mRoundingParams);
        for (int i = 0; i < this.mFadeDrawable.getNumberOfLayers(); i++) {
            WrappingUtils.updateLeafRounding(getLayerParentDrawable(i), this.mRoundingParams, this.mResources);
        }
    }

    public RoundingParams getRoundingParams() {
        return this.mRoundingParams;
    }
}

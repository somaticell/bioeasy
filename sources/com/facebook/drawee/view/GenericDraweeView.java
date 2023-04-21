package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.facebook.drawee.R;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import javax.annotation.Nullable;

public class GenericDraweeView extends DraweeView<GenericDraweeHierarchy> {
    public GenericDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context);
        setHierarchy(hierarchy);
    }

    public GenericDraweeView(Context context) {
        super(context);
        inflateHierarchy(context, (AttributeSet) null);
    }

    public GenericDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateHierarchy(context, attrs);
    }

    public GenericDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateHierarchy(context, attrs);
    }

    @TargetApi(21)
    public GenericDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateHierarchy(context, attrs);
    }

    private void inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        Resources resources = context.getResources();
        int fadeDuration = 300;
        int placeholderId = 0;
        ScalingUtils.ScaleType placeholderScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;
        int retryImageId = 0;
        ScalingUtils.ScaleType retryImageScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;
        int failureImageId = 0;
        ScalingUtils.ScaleType failureImageScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;
        int progressBarId = 0;
        ScalingUtils.ScaleType progressBarScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;
        ScalingUtils.ScaleType actualImageScaleType = GenericDraweeHierarchyBuilder.DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
        int backgroundId = 0;
        int overlayId = 0;
        int pressedStateOverlayId = 0;
        boolean roundAsCircle = false;
        int roundedCornerRadius = 0;
        boolean roundTopLeft = true;
        boolean roundTopRight = true;
        boolean roundBottomRight = true;
        boolean roundBottomLeft = true;
        int roundWithOverlayColor = 0;
        int roundingBorderWidth = 0;
        int roundingBorderColor = 0;
        int roundingBorderPadding = 0;
        int progressBarAutoRotateInterval = 0;
        if (attrs != null) {
            TypedArray gdhAttrs = context.obtainStyledAttributes(attrs, R.styleable.GenericDraweeView);
            try {
                int indexCount = gdhAttrs.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int idx = gdhAttrs.getIndex(i);
                    if (idx == R.styleable.GenericDraweeView_actualImageScaleType) {
                        actualImageScaleType = getScaleTypeFromXml(gdhAttrs, R.styleable.GenericDraweeView_actualImageScaleType, actualImageScaleType);
                    } else if (idx == R.styleable.GenericDraweeView_placeholderImage) {
                        placeholderId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_placeholderImage, placeholderId);
                    } else if (idx == R.styleable.GenericDraweeView_pressedStateOverlayImage) {
                        pressedStateOverlayId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_pressedStateOverlayImage, pressedStateOverlayId);
                    } else if (idx == R.styleable.GenericDraweeView_progressBarImage) {
                        progressBarId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_progressBarImage, progressBarId);
                    } else if (idx == R.styleable.GenericDraweeView_fadeDuration) {
                        fadeDuration = gdhAttrs.getInt(R.styleable.GenericDraweeView_fadeDuration, fadeDuration);
                    } else if (idx == R.styleable.GenericDraweeView_viewAspectRatio) {
                        setAspectRatio(gdhAttrs.getFloat(R.styleable.GenericDraweeView_viewAspectRatio, getAspectRatio()));
                    } else if (idx == R.styleable.GenericDraweeView_placeholderImageScaleType) {
                        placeholderScaleType = getScaleTypeFromXml(gdhAttrs, R.styleable.GenericDraweeView_placeholderImageScaleType, placeholderScaleType);
                    } else if (idx == R.styleable.GenericDraweeView_retryImage) {
                        retryImageId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_retryImage, retryImageId);
                    } else if (idx == R.styleable.GenericDraweeView_retryImageScaleType) {
                        retryImageScaleType = getScaleTypeFromXml(gdhAttrs, R.styleable.GenericDraweeView_retryImageScaleType, retryImageScaleType);
                    } else if (idx == R.styleable.GenericDraweeView_failureImage) {
                        failureImageId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_failureImage, failureImageId);
                    } else if (idx == R.styleable.GenericDraweeView_failureImageScaleType) {
                        failureImageScaleType = getScaleTypeFromXml(gdhAttrs, R.styleable.GenericDraweeView_failureImageScaleType, failureImageScaleType);
                    } else if (idx == R.styleable.GenericDraweeView_progressBarImageScaleType) {
                        progressBarScaleType = getScaleTypeFromXml(gdhAttrs, R.styleable.GenericDraweeView_progressBarImageScaleType, progressBarScaleType);
                    } else if (idx == R.styleable.GenericDraweeView_progressBarAutoRotateInterval) {
                        progressBarAutoRotateInterval = gdhAttrs.getInteger(R.styleable.GenericDraweeView_progressBarAutoRotateInterval, 0);
                    } else if (idx == R.styleable.GenericDraweeView_backgroundImage) {
                        backgroundId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_backgroundImage, backgroundId);
                    } else if (idx == R.styleable.GenericDraweeView_overlayImage) {
                        overlayId = gdhAttrs.getResourceId(R.styleable.GenericDraweeView_overlayImage, overlayId);
                    } else if (idx == R.styleable.GenericDraweeView_roundAsCircle) {
                        roundAsCircle = gdhAttrs.getBoolean(R.styleable.GenericDraweeView_roundAsCircle, roundAsCircle);
                    } else if (idx == R.styleable.GenericDraweeView_roundedCornerRadius) {
                        roundedCornerRadius = gdhAttrs.getDimensionPixelSize(R.styleable.GenericDraweeView_roundedCornerRadius, roundedCornerRadius);
                    } else if (idx == R.styleable.GenericDraweeView_roundTopLeft) {
                        roundTopLeft = gdhAttrs.getBoolean(R.styleable.GenericDraweeView_roundTopLeft, roundTopLeft);
                    } else if (idx == R.styleable.GenericDraweeView_roundTopRight) {
                        roundTopRight = gdhAttrs.getBoolean(R.styleable.GenericDraweeView_roundTopRight, roundTopRight);
                    } else if (idx == R.styleable.GenericDraweeView_roundBottomRight) {
                        roundBottomRight = gdhAttrs.getBoolean(R.styleable.GenericDraweeView_roundBottomRight, roundBottomRight);
                    } else if (idx == R.styleable.GenericDraweeView_roundBottomLeft) {
                        roundBottomLeft = gdhAttrs.getBoolean(R.styleable.GenericDraweeView_roundBottomLeft, roundBottomLeft);
                    } else if (idx == R.styleable.GenericDraweeView_roundWithOverlayColor) {
                        roundWithOverlayColor = gdhAttrs.getColor(R.styleable.GenericDraweeView_roundWithOverlayColor, roundWithOverlayColor);
                    } else if (idx == R.styleable.GenericDraweeView_roundingBorderWidth) {
                        roundingBorderWidth = gdhAttrs.getDimensionPixelSize(R.styleable.GenericDraweeView_roundingBorderWidth, roundingBorderWidth);
                    } else if (idx == R.styleable.GenericDraweeView_roundingBorderColor) {
                        roundingBorderColor = gdhAttrs.getColor(R.styleable.GenericDraweeView_roundingBorderColor, roundingBorderColor);
                    } else if (idx == R.styleable.GenericDraweeView_roundingBorderPadding) {
                        roundingBorderPadding = gdhAttrs.getDimensionPixelSize(R.styleable.GenericDraweeView_roundingBorderPadding, roundingBorderPadding);
                    }
                }
            } finally {
                gdhAttrs.recycle();
            }
        }
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
        builder.setFadeDuration(fadeDuration);
        if (placeholderId > 0) {
            builder.setPlaceholderImage(resources.getDrawable(placeholderId), placeholderScaleType);
        }
        if (retryImageId > 0) {
            builder.setRetryImage(resources.getDrawable(retryImageId), retryImageScaleType);
        }
        if (failureImageId > 0) {
            builder.setFailureImage(resources.getDrawable(failureImageId), failureImageScaleType);
        }
        if (progressBarId > 0) {
            Drawable progressBarDrawable = resources.getDrawable(progressBarId);
            if (progressBarAutoRotateInterval > 0) {
                progressBarDrawable = new AutoRotateDrawable(progressBarDrawable, progressBarAutoRotateInterval);
            }
            builder.setProgressBarImage(progressBarDrawable, progressBarScaleType);
        }
        if (backgroundId > 0) {
            builder.setBackground(resources.getDrawable(backgroundId));
        }
        if (overlayId > 0) {
            builder.setOverlay(resources.getDrawable(overlayId));
        }
        if (pressedStateOverlayId > 0) {
            builder.setPressedStateOverlay(getResources().getDrawable(pressedStateOverlayId));
        }
        builder.setActualImageScaleType(actualImageScaleType);
        if (roundAsCircle || roundedCornerRadius > 0) {
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setRoundAsCircle(roundAsCircle);
            if (roundedCornerRadius > 0) {
                roundingParams.setCornersRadii(roundTopLeft ? (float) roundedCornerRadius : 0.0f, roundTopRight ? (float) roundedCornerRadius : 0.0f, roundBottomRight ? (float) roundedCornerRadius : 0.0f, roundBottomLeft ? (float) roundedCornerRadius : 0.0f);
            }
            if (roundWithOverlayColor != 0) {
                roundingParams.setOverlayColor(roundWithOverlayColor);
            }
            if (roundingBorderColor != 0 && roundingBorderWidth > 0) {
                roundingParams.setBorder(roundingBorderColor, (float) roundingBorderWidth);
            }
            if (roundingBorderPadding != 0) {
                roundingParams.setPadding((float) roundingBorderPadding);
            }
            builder.setRoundingParams(roundingParams);
        }
        setHierarchy(builder.build());
    }

    private static ScalingUtils.ScaleType getScaleTypeFromXml(TypedArray attrs, int attrId, ScalingUtils.ScaleType defaultScaleType) {
        int index = attrs.getInt(attrId, -1);
        return index < 0 ? defaultScaleType : ScalingUtils.ScaleType.values()[index];
    }
}

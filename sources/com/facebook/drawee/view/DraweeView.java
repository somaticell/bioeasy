package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.facebook.common.internal.Objects;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.AspectRatioMeasure;
import javax.annotation.Nullable;

public class DraweeView<DH extends DraweeHierarchy> extends ImageView {
    private float mAspectRatio = 0.0f;
    private DraweeHolder<DH> mDraweeHolder;
    private boolean mInitialised = false;
    private final AspectRatioMeasure.Spec mMeasureSpec = new AspectRatioMeasure.Spec();

    public DraweeView(Context context) {
        super(context);
        init(context);
    }

    public DraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @TargetApi(21)
    public DraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        ColorStateList imageTintList;
        if (!this.mInitialised) {
            this.mInitialised = true;
            this.mDraweeHolder = DraweeHolder.create(null, context);
            if (Build.VERSION.SDK_INT >= 21 && (imageTintList = getImageTintList()) != null) {
                setColorFilter(imageTintList.getDefaultColor());
            }
        }
    }

    public void setHierarchy(DH hierarchy) {
        this.mDraweeHolder.setHierarchy(hierarchy);
        super.setImageDrawable(this.mDraweeHolder.getTopLevelDrawable());
    }

    public DH getHierarchy() {
        return this.mDraweeHolder.getHierarchy();
    }

    public boolean hasHierarchy() {
        return this.mDraweeHolder.hasHierarchy();
    }

    @Nullable
    public Drawable getTopLevelDrawable() {
        return this.mDraweeHolder.getTopLevelDrawable();
    }

    public void setController(@Nullable DraweeController draweeController) {
        this.mDraweeHolder.setController(draweeController);
        super.setImageDrawable(this.mDraweeHolder.getTopLevelDrawable());
    }

    @Nullable
    public DraweeController getController() {
        return this.mDraweeHolder.getController();
    }

    public boolean hasController() {
        return this.mDraweeHolder.getController() != null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mDraweeHolder.onAttach();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDraweeHolder.onDetach();
    }

    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        this.mDraweeHolder.onDetach();
    }

    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mDraweeHolder.onAttach();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDraweeHolder.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Deprecated
    public void setImageDrawable(Drawable drawable) {
        init(getContext());
        this.mDraweeHolder.setController((DraweeController) null);
        super.setImageDrawable(drawable);
    }

    @Deprecated
    public void setImageBitmap(Bitmap bm) {
        init(getContext());
        this.mDraweeHolder.setController((DraweeController) null);
        super.setImageBitmap(bm);
    }

    @Deprecated
    public void setImageResource(int resId) {
        init(getContext());
        this.mDraweeHolder.setController((DraweeController) null);
        super.setImageResource(resId);
    }

    @Deprecated
    public void setImageURI(Uri uri) {
        init(getContext());
        this.mDraweeHolder.setController((DraweeController) null);
        super.setImageURI(uri);
    }

    public void setAspectRatio(float aspectRatio) {
        if (aspectRatio != this.mAspectRatio) {
            this.mAspectRatio = aspectRatio;
            requestLayout();
        }
    }

    public float getAspectRatio() {
        return this.mAspectRatio;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mMeasureSpec.width = widthMeasureSpec;
        this.mMeasureSpec.height = heightMeasureSpec;
        AspectRatioMeasure.updateMeasureSpec(this.mMeasureSpec, this.mAspectRatio, getLayoutParams(), getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom());
        super.onMeasure(this.mMeasureSpec.width, this.mMeasureSpec.height);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("holder", (Object) this.mDraweeHolder != null ? this.mDraweeHolder.toString() : "<no holder set>").toString();
    }
}

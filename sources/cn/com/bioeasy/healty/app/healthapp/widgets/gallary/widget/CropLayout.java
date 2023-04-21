package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.util.Util;

public class CropLayout extends FrameLayout {
    private int mCropHeight = 500;
    private CropFloatView mCropView;
    private int mCropWidth = 500;
    private ZoomImageView mZoomImageView;

    public CropLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mZoomImageView = new ZoomImageView(context);
        this.mCropView = new CropFloatView(context);
        ViewGroup.LayoutParams lp = new FrameLayout.LayoutParams(-1, -1);
        addView(this.mZoomImageView, lp);
        addView(this.mCropView, lp);
    }

    public ZoomImageView getImageView() {
        return this.mZoomImageView;
    }

    public Bitmap cropBitmap() {
        return this.mZoomImageView.cropBitmap();
    }

    public void setCropWidth(int mCropWidth2) {
        this.mCropWidth = mCropWidth2;
        this.mCropView.setCropWidth(mCropWidth2);
        this.mZoomImageView.setCropWidth(mCropWidth2);
    }

    public void setCropHeight(int mCropHeight2) {
        this.mCropHeight = mCropHeight2;
        this.mCropView.setCropHeight(mCropHeight2);
        this.mZoomImageView.setCropHeight(mCropHeight2);
    }

    public void start() {
        int height = Util.getScreenHeight(getContext());
        int mHOffset = (Util.getScreenWidth(getContext()) - this.mCropWidth) / 2;
        int mVOffset = (height - this.mCropHeight) / 2;
        this.mZoomImageView.setHOffset(mHOffset);
        this.mZoomImageView.setVOffset(mVOffset);
        this.mCropView.setHOffset(mHOffset);
        this.mCropView.setVOffset(mVOffset);
    }
}

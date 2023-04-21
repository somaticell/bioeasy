package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;

public class CropFloatView extends View {
    private boolean isCrop;
    private CropDrawable mCropDrawable;
    private int mCropHeight;
    private int mCropWidth;
    private Rect mFloatRect = new Rect();
    private int mHOffset;
    private int mVOffset;

    public CropFloatView(Context context) {
        super(context);
        this.mCropDrawable = new CropDrawable(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cropDrawable();
        canvas.save();
        canvas.clipRect(this.mFloatRect, Region.Op.DIFFERENCE);
        canvas.drawColor(Color.parseColor("#a0000000"));
        canvas.restore();
        this.mCropDrawable.draw(canvas);
    }

    private void cropDrawable() {
        if (!this.isCrop) {
            this.mCropDrawable.setRegion(this.mFloatRect);
            this.isCrop = true;
        }
    }

    public void setCropWidth(int mCropWidth2) {
        this.mCropWidth = mCropWidth2;
        this.mCropDrawable.setCropWidth(mCropWidth2);
    }

    public void setCropHeight(int mCropHeight2) {
        this.mCropHeight = mCropHeight2;
        this.mCropDrawable.setCropHeight(mCropHeight2);
    }

    public void setHOffset(int mHOffset2) {
        this.mHOffset = mHOffset2;
    }

    public void setVOffset(int mVOffset2) {
        this.mVOffset = mVOffset2;
    }
}

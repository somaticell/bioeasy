package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.util.Util;

public class CropDrawable extends Drawable {
    private static final int RADIUS = 20;
    private int mBottom;
    private Paint mCirclePaint = new Paint();
    private Context mContext;
    private int mCropHeight = 800;
    private int mCropWidth = 800;
    private int mLeft;
    private Paint mLinePaint = new Paint();
    private int mRight;
    private int mTop;
    private int offset = 50;

    public CropDrawable(Context mContext2) {
        this.mContext = mContext2;
        initPaint();
    }

    private void initPaint() {
        this.mLinePaint.setARGB(200, 50, 50, 50);
        this.mLinePaint.setStrokeWidth(2.0f);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setColor(-1);
        this.mCirclePaint.setColor(-1);
        this.mCirclePaint.setStyle(Paint.Style.FILL);
        this.mCirclePaint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        int width = Util.getScreenWidth(this.mContext);
        int height = Util.getScreenHeight(this.mContext);
        this.mLeft = (width - this.mCropWidth) / 2;
        this.mTop = (height - this.mCropHeight) / 2;
        this.mRight = (this.mCropWidth + width) / 2;
        this.mBottom = (this.mCropHeight + height) / 2;
        Rect rect = new Rect(this.mLeft, this.mTop, this.mRight, this.mBottom);
        canvas.drawRect(rect, this.mLinePaint);
        canvas.drawCircle((float) rect.left, (float) (rect.top + this.mCropHeight), 20.0f, this.mCirclePaint);
        canvas.drawCircle((float) rect.right, (float) (rect.top + this.mCropHeight), 20.0f, this.mCirclePaint);
        canvas.drawCircle((float) rect.left, (float) (rect.bottom - this.mCropHeight), 20.0f, this.mCirclePaint);
        canvas.drawCircle((float) rect.right, (float) (rect.bottom - this.mCropHeight), 20.0f, this.mCirclePaint);
    }

    public void setAlpha(int alpha) {
    }

    public void offset(int x, int y) {
        getBounds().offset(x, y);
    }

    public void setBounds(Rect bounds) {
        super.setBounds(new Rect(this.mLeft, this.mTop, this.mRight, this.mBottom));
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }

    public void setRegion(Rect rect) {
        int width = Util.getScreenWidth(this.mContext);
        int height = Util.getScreenHeight(this.mContext);
        rect.set((width - this.mCropWidth) / 2, (height - this.mCropHeight) / 2, (this.mCropWidth + width) / 2, (this.mCropHeight + height) / 2);
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getTop() {
        return this.mCropHeight;
    }

    public int getBottom() {
        return this.mBottom;
    }

    public void setCropWidth(int mCropWidth2) {
        this.mCropWidth = mCropWidth2;
    }

    public void setCropHeight(int mCropHeight2) {
        this.mCropHeight = mCropHeight2;
    }
}

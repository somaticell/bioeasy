package cn.com.bioeasy.healty.app.healthapp.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import cn.com.bioeasy.healty.app.healthapp.R;

public class BatteryState extends View {
    private float height = 64.0f;
    private Context mContext;
    private Paint mPaint;
    private float powerQuantity = 0.0f;
    private TextPaint textPaint;
    private float width = 64.0f;

    public BatteryState(Context context) {
        super(context);
        this.mContext = context;
        this.mPaint = new Paint();
        this.textPaint = myTextPaint();
    }

    public TextPaint myTextPaint() {
        TextPaint textPaint2 = new TextPaint(InputDeviceCompat.SOURCE_KEYBOARD);
        int TEXT_SIZE = Math.round(32.0f);
        textPaint2.setTextAlign(Paint.Align.CENTER);
        textPaint2.setTextSize((float) TEXT_SIZE);
        textPaint2.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint2.setColor(Color.argb(255, 94, 38, 18));
        return textPaint2;
    }

    public BatteryState(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mPaint = new Paint();
        this.textPaint = myTextPaint();
    }

    public BatteryState(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mPaint = new Paint();
        this.textPaint = myTextPaint();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap batteryBitmap = readBitmap(getResources(), R.drawable.battery_empty, 64, 64);
        this.width = (float) batteryBitmap.getWidth();
        this.height = (float) batteryBitmap.getHeight();
        if (this.powerQuantity > 0.3f && this.powerQuantity <= 1.0f) {
            this.mPaint.setColor(-16711936);
        } else if (this.powerQuantity >= 0.0f && ((double) this.powerQuantity) <= 0.3d) {
            this.mPaint.setColor(SupportMenu.CATEGORY_MASK);
        }
        float right = this.width * 0.94f;
        canvas.drawRect((this.width * 0.21f) + ((right - (this.width * 0.21f)) * (1.0f - this.powerQuantity)), this.height * 0.3f, right, this.height * 0.7f, this.mPaint);
        canvas.drawBitmap(batteryBitmap, 0.0f, 0.0f, this.mPaint);
        Canvas canvas2 = canvas;
        drawText(canvas2, this.textPaint, ((int) (this.powerQuantity * 100.0f)) + "%", ((int) this.width) / 2, (((int) this.height) * 2) / 5, (int) this.width);
    }

    public void drawText(Canvas canvas, TextPaint Paint, String textString, int x, int y, int width2) {
        int start_x = Math.round((float) x);
        int start_y = Math.round((float) y);
        StaticLayout staticLayout = new StaticLayout(textString, Paint, width2, Layout.Alignment.ALIGN_NORMAL, 1.5f, 0.0f, false);
        canvas.translate((float) start_x, (float) start_y);
        staticLayout.draw(canvas);
    }

    public void refreshPower(float power) {
        this.powerQuantity = power;
        if (this.powerQuantity > 1.0f) {
            this.powerQuantity = 1.0f;
        }
        if (this.powerQuantity < 0.0f) {
            this.powerQuantity = 0.0f;
        }
        invalidate();
    }

    public Bitmap readBitmap(Resources resources, int resourcesId, int width2, int height2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        float srcWidth = (float) options.outWidth;
        float srcHeight = (float) options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > ((float) height2) || srcWidth > ((float) width2)) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / ((float) height2));
            } else {
                inSampleSize = Math.round(srcWidth / ((float) width2));
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeResource(resources, resourcesId, options);
    }
}

package cn.com.bioeasy.app.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import cn.com.bioeasy.app.commonlib.R;

public class BorderImageView extends ImageView {
    private int color;

    public BorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.color = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BorderImageView, 0, 0).getColor(R.styleable.BorderImageView_borderColor, context.getResources().getColor(R.color.alpha_gray));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rec = canvas.getClipBounds();
        rec.bottom--;
        rec.right--;
        Paint paint = new Paint();
        paint.setColor(this.color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rec, paint);
    }
}

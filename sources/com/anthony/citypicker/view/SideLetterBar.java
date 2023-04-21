package com.anthony.citypicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.anthony.citypicker.R;

public class SideLetterBar extends View {
    private static final String[] b = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;
    private Paint paint = new Paint();
    private boolean showBg = false;

    public interface OnLetterChangedListener {
        void onLetterChanged(String str);
    }

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
    }

    public void setOverlay(TextView overlay2) {
        this.overlay = overlay2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.showBg) {
            canvas.drawColor(0);
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            this.paint.setTextSize(getResources().getDimension(R.dimen.side_letter_bar_letter_size));
            this.paint.setColor(getResources().getColor(R.color.gray));
            this.paint.setAntiAlias(true);
            if (i == this.choose) {
                this.paint.setColor(getResources().getColor(R.color.gray_deep));
            }
            canvas.drawText(b[i], ((float) (width / 2)) - (this.paint.measureText(b[i]) / 2.0f), (float) ((singleHeight * i) + singleHeight), this.paint);
            this.paint.reset();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = this.choose;
        OnLetterChangedListener listener = this.onLetterChangedListener;
        int c = (int) ((y / ((float) getHeight())) * ((float) b.length));
        switch (action) {
            case 0:
                this.showBg = true;
                if (oldChoose != c && listener != null && c >= 0 && c < b.length) {
                    listener.onLetterChanged(b[c]);
                    this.choose = c;
                    invalidate();
                    if (this.overlay != null) {
                        this.overlay.setVisibility(0);
                        this.overlay.setText(b[c]);
                        break;
                    }
                }
                break;
            case 1:
                this.showBg = false;
                this.choose = -1;
                invalidate();
                if (this.overlay != null) {
                    this.overlay.setVisibility(8);
                    break;
                }
                break;
            case 2:
                if (oldChoose != c && listener != null && c >= 0 && c < b.length) {
                    listener.onLetterChanged(b[c]);
                    this.choose = c;
                    invalidate();
                    if (this.overlay != null) {
                        this.overlay.setVisibility(0);
                        this.overlay.setText(b[c]);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener2) {
        this.onLetterChangedListener = onLetterChangedListener2;
    }
}

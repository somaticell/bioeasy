package net.oschina.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import net.oschina.common.R;

public class AdjustTextView extends AppCompatTextView {
    private int mContentWidth;
    private float mDefaultTextSize;
    private int mMinTextSize;

    public AdjustTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public AdjustTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdjustTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mMinTextSize = 1;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Context context = getContext();
        this.mContentWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
        this.mDefaultTextSize = getTextSize();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AdjustTextView, defStyle, 0);
            this.mMinTextSize = a.getDimensionPixelSize(R.styleable.AdjustTextView_oscMinTextSize, this.mMinTextSize);
            a.recycle();
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        this.mContentWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        this.mContentWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void resize(CharSequence charSequence) {
        String text = charSequence.toString();
        if (!TextUtils.isEmpty(text) && this.mContentWidth > 0) {
            TextPaint paint = new TextPaint(getPaint());
            paint.setTextSize(this.mDefaultTextSize);
            float ts = this.mDefaultTextSize;
            float mw = paint.measureText(text);
            while (ts > ((float) this.mMinTextSize) && mw > ((float) this.mContentWidth)) {
                ts -= 1.0f;
                paint.setTextSize(ts);
                mw = paint.measureText(text);
            }
            setTextSize(0, ts);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mContentWidth = (w - getPaddingLeft()) - getPaddingRight();
        resize(getText());
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        resize(text);
    }
}

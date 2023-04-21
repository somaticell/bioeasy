package cn.com.bioeasy.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VerticalTextView extends LinearLayout {
    private int color;
    private Context mContext;
    private String mText;

    public VerticalTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -16776961);
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr, int color2) {
        super(context, attrs, defStyleAttr);
        this.color = -16776961;
        setOrientation(1);
        this.mContext = context;
        this.color = color2;
    }

    public void setColor(int color2) {
        this.color = color2;
    }

    public void setText(String text) {
        this.mText = text;
        addText();
    }

    private void addText() {
        removeAllViews();
        System.out.println("------>" + this.mText);
        if (this.mText != null) {
            char[] chara = this.mText.toCharArray();
            for (int i = 0; i < chara.length; i++) {
                TextView oneText = new TextView(this.mContext);
                oneText.setText(this.mText.substring(i, i + 1));
                oneText.setTextColor(this.color);
                oneText.setTextSize(2, 18.0f);
                oneText.setGravity(1);
                addView(oneText, -1, -2);
            }
        }
    }
}

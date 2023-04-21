package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import com.orhanobut.logger.Logger;

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private static final String TAG = "AmountView";
    private int amount;
    private Button btnDecrease;
    private Button btnIncrease;
    private TextView etAmount;
    private int goods_storage;
    private OnAmountChangeListener mListener;

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int i);
    }

    public AmountView(Context context) {
        this(context, (AttributeSet) null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.amount = 1;
        this.goods_storage = 0;
        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        this.etAmount = (TextView) findViewById(R.id.etAmount);
        this.btnDecrease = (Button) findViewById(R.id.btnDecrease);
        this.btnIncrease = (Button) findViewById(R.id.btnIncrease);
        this.btnDecrease.setOnClickListener(this);
        this.btnIncrease.setOnClickListener(this);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(0, -2);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(1, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        obtainStyledAttributes.recycle();
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(btnWidth, -1);
        this.btnDecrease.setLayoutParams(btnParams);
        this.btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            this.btnDecrease.setTextSize(0, (float) btnTextSize);
            this.btnIncrease.setTextSize(0, (float) btnTextSize);
        }
        this.etAmount.setLayoutParams(new LinearLayout.LayoutParams(tvWidth, -1));
        if (tvTextSize != 0) {
            this.etAmount.setTextSize((float) tvTextSize);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage2) {
        this.goods_storage = goods_storage2;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (this.amount > 1) {
                this.amount--;
                this.etAmount.setText(this.amount + "");
            }
            Logger.e("decrease::" + this.amount, new Object[0]);
        } else if (i == R.id.btnIncrease) {
            if (this.amount < this.goods_storage) {
                this.amount++;
                this.etAmount.setText(this.amount + "");
            }
            Logger.e("Increase::" + this.amount, new Object[0]);
        }
        this.etAmount.clearFocus();
        if (this.mListener != null) {
            this.mListener.onAmountChange(this, this.amount);
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        if (!s.toString().isEmpty()) {
            this.amount = Integer.valueOf(s.toString()).intValue();
            if (this.amount > this.goods_storage) {
                this.etAmount.setText(this.goods_storage + "");
            } else if (this.mListener != null) {
                this.mListener.onAmountChange(this, this.amount);
            }
        }
    }
}

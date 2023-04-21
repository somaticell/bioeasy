package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.com.bioeasy.app.widgets.wheel.OnWheelScrollListener;
import cn.com.bioeasy.app.widgets.wheel.WheelView;
import cn.com.bioeasy.app.widgets.wheel.adapters.ArrayWheelAdapter;
import cn.com.bioeasy.healty.app.healthapp.R;

public class ListChoseDialog extends Dialog implements View.OnClickListener, OnWheelScrollListener, SeekBar.OnSeekBarChangeListener {
    public static SendValue sendValues;
    SeekBar mSbar;
    TextView mTvTitle;
    RelativeLayout rlSeebarWraper;
    int value;
    WheelView wheelView;

    public interface SendValue {
        void SendData(int i);
    }

    public ListChoseDialog(String[] dataList, Context context) {
        super(context, R.style.my_dialog);
        initView(dataList, context);
    }

    private void initView(String[] dataList, Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_device, (ViewGroup) null);
        root.findViewById(R.id.btn_cancle).setOnClickListener(this);
        root.findViewById(R.id.btn_confire).setOnClickListener(this);
        this.mSbar = (SeekBar) root.findViewById(R.id.seebar);
        this.rlSeebarWraper = (RelativeLayout) root.findViewById(R.id.rl_seekbar);
        this.mSbar.setOnSeekBarChangeListener(this);
        this.rlSeebarWraper.setVisibility(8);
        this.wheelView = (WheelView) root.findViewById(R.id.wheel_view);
        this.mTvTitle = (TextView) root.findViewById(R.id.tv_device_title);
        this.wheelView.setVisibility(0);
        setContentView(root);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(80);
        dialogWindow.setWindowAnimations(R.style.dialogstyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = -20;
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9.0f;
        dialogWindow.setAttributes(lp);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.wheelView.addScrollingListener(this);
        this.wheelView.setViewAdapter(new ArrayWheelAdapter(context, dataList));
        this.value = this.wheelView.getCurrentItem();
        this.wheelView.setVisibleItems(7);
    }

    public void setListValue(int value2) {
        this.wheelView.setCurrentItem(value2);
    }

    public void ShowDialog() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }

    public void setTitles(int title) {
        if (title != 0) {
            this.mTvTitle.setText(title);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancle) {
            hide();
            return;
        }
        if (this.value >= 0 && sendValues != null) {
            sendValues.SendData(this.value);
        }
        hide();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progress >= 0) {
            this.value = progress;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public static void setData(SendValue sendValue) {
        sendValues = sendValue;
    }

    public void onScrollingStarted(WheelView wheel) {
    }

    public void onScrollingFinished(WheelView wheel) {
        this.value = wheel.getCurrentItem();
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.SearchAdapter2;

public class SampleInfoDialog extends Dialog implements View.OnClickListener {
    public static SendValue sendValues;
    private SearchAdapter2<String> autoCompleteAdapter;
    private AutoCompleteTextView mAcText;
    private TextView mTvTitle;
    private EditText sampleNoText;

    public interface SendValue {
        void SendData(String str, String str2);
    }

    public SampleInfoDialog(Context context) {
        super(context, R.style.my_dialog);
        initView(context);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_sample_info, (ViewGroup) null);
        this.sampleNoText = (EditText) root.findViewById(R.id.id_sample_no);
        this.mAcText = (AutoCompleteTextView) root.findViewById(R.id.id_sample_name);
        setContentView(root);
        root.findViewById(R.id.btn_cancle).setOnClickListener(this);
        root.findViewById(R.id.btn_confire).setOnClickListener(this);
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogstyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = 80;
        lp.alpha = 9.0f;
        dialogWindow.setAttributes(lp);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.autoCompleteAdapter = new SearchAdapter2<>(getContext(), 17367043, (T[]) new String[]{"红枸杞", "黑枸杞", "枸杞芽茶", "枸杞叶茶"}, -1);
        this.mAcText.setAdapter(this.autoCompleteAdapter);
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNoText.setText(sampleNo);
    }

    public void ShowDialog() {
        if (isShowing()) {
            hide();
        }
        show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancle) {
            hide();
            return;
        }
        String sampleNo = this.sampleNoText.getText().toString();
        String sampleName = this.mAcText.getText().toString();
        if (StringUtil.isNullOrEmpty(sampleNo) || StringUtil.isNullOrEmpty(sampleName)) {
            ToastUtils.showToast("请输入样品编号和样品名称");
            return;
        }
        if (sendValues != null) {
            sendValues.SendData(sampleNo, sampleName);
        }
        hide();
    }

    public static void setData(SendValue sendValue) {
        sendValues = sendValue;
    }
}

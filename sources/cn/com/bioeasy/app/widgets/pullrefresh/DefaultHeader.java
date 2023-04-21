package cn.com.bioeasy.app.widgets.pullrefresh;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.bioeasy.app.commonlib.R;
import com.pnikosis.materialishprogress.ProgressWheel;

public class DefaultHeader extends BaseIndicator {
    private int default_rim_color;
    private TextView mStringIndicator;
    private ProgressWheel progress_wheel;

    public View createView(LayoutInflater inflater, ViewGroup parent) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.prj_ptr_header_default, parent, true);
        View child = v.getChildAt(v.getChildCount() - 1);
        this.mStringIndicator = (TextView) child.findViewById(R.id.tv_header);
        this.progress_wheel = (ProgressWheel) v.findViewById(R.id.progress_wheel);
        this.default_rim_color = this.progress_wheel.getRimColor();
        return child;
    }

    public void onAction() {
        this.mStringIndicator.setText("放开以刷新");
    }

    public void onUnaction() {
        this.mStringIndicator.setText("下拉以刷新");
    }

    public void onRestore() {
        this.mStringIndicator.setText("下拉以刷新");
        this.progress_wheel.setRimColor(this.default_rim_color);
        this.progress_wheel.stopSpinning();
    }

    public void onLoading() {
        this.mStringIndicator.setText("加载中...");
        this.progress_wheel.setRimColor(Color.parseColor("#00000000"));
        this.progress_wheel.spin();
    }
}

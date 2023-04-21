package cn.com.bioeasy.app.widgets.pullrefresh;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.bioeasy.app.commonlib.R;
import com.pnikosis.materialishprogress.ProgressWheel;

public class DefaultFooter extends BaseIndicator {
    private int default_rim_color;
    private TextView mStringIndicator;
    private ProgressWheel progress_wheell;

    public View createView(LayoutInflater inflater, ViewGroup parent) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.prj_ptr_footer_default, parent, true);
        View child = v.getChildAt(v.getChildCount() - 1);
        this.mStringIndicator = (TextView) child.findViewById(R.id.tv_footer);
        this.progress_wheell = (ProgressWheel) v.findViewById(R.id.progress_wheell);
        this.default_rim_color = this.progress_wheell.getRimColor();
        return child;
    }

    public void onAction() {
        this.mStringIndicator.setText("放开加载更多");
    }

    public void onUnaction() {
        this.mStringIndicator.setText("上拉加载更多");
    }

    public void onRestore() {
        this.mStringIndicator.setText("上拉加载更多");
        this.progress_wheell.setRimColor(this.default_rim_color);
        this.progress_wheell.stopSpinning();
    }

    public void onLoading() {
        this.mStringIndicator.setText("加载中...");
        this.progress_wheell.setRimColor(Color.parseColor("#00000000"));
        this.progress_wheell.spin();
    }
}

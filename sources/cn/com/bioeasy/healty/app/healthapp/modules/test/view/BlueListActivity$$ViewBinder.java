package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.BlueListActivity;
import com.kyleduo.switchbutton.SwitchButton;

public class BlueListActivity$$ViewBinder<T extends BlueListActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mLv = (ListView) finder.castView((View) finder.findRequiredView(source, R.id.listview_blue, "field 'mLv'"), R.id.listview_blue, "field 'mLv'");
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.tvBlueName = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_connect_name, "field 'tvBlueName'"), R.id.tv_connect_name, "field 'tvBlueName'");
        target.ivChecked = (ImageView) finder.castView((View) finder.findRequiredView(source, R.id.iv_check, "field 'ivChecked'"), R.id.iv_check, "field 'ivChecked'");
        View view = (View) finder.findRequiredView(source, R.id.bt_next, "field 'mBtNext' and method 'onClicks'");
        target.mBtNext = (Button) finder.castView(view, R.id.bt_next, "field 'mBtNext'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.sbt_open_blue, "field 'mSwitchButton' and method 'onClicks'");
        target.mSwitchButton = (SwitchButton) finder.castView(view2, R.id.sbt_open_blue, "field 'mSwitchButton'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        View view3 = (View) finder.findRequiredView(source, R.id.btn_partanter_setting, "field 'btnSetting' and method 'onClicks'");
        target.btnSetting = (Button) finder.castView(view3, R.id.btn_partanter_setting, "field 'btnSetting'");
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClicks'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.buy_device_tip, "method 'onClicks'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClicks(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mLv = null;
        target.tvTitle = null;
        target.tvBlueName = null;
        target.ivChecked = null;
        target.mBtNext = null;
        target.mSwitchButton = null;
        target.btnSetting = null;
    }
}

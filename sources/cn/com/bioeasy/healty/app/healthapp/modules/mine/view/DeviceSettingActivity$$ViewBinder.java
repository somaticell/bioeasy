package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BatteryState;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity;

public class DeviceSettingActivity$$ViewBinder<T extends DeviceSettingActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.tv_current_bluename, "field 'tvBlueName' and method 'onClick'");
        target.tvBlueName = (TextView) finder.castView(view, R.id.tv_current_bluename, "field 'tvBlueName'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.rlLoadFile = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_load_file, "field 'rlLoadFile'"), R.id.rl_load_file, "field 'rlLoadFile'");
        target.tvPmFile = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_pwm_file, "field 'tvPmFile'"), R.id.tv_pwm_file, "field 'tvPmFile'");
        View view2 = (View) finder.findRequiredView(source, R.id.rl_check_num, "field 'checkNum' and method 'onClick'");
        target.checkNum = (RelativeLayout) finder.castView(view2, R.id.rl_check_num, "field 'checkNum'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view3 = (View) finder.findRequiredView(source, R.id.btn_send_pwm, "field 'sendBtn' and method 'onClick'");
        target.sendBtn = (Button) finder.castView(view3, R.id.btn_send_pwm, "field 'sendBtn'");
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.powerStatus = (BatteryState) finder.castView((View) finder.findRequiredView(source, R.id.bs_power, "field 'powerStatus'"), R.id.bs_power, "field 'powerStatus'");
        ((View) finder.findRequiredView(source, R.id.rl_led_change, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.bt_change, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_auto_power_failure, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_check_pwm, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.tvBlueName = null;
        target.rlLoadFile = null;
        target.tvPmFile = null;
        target.checkNum = null;
        target.sendBtn = null;
        target.powerStatus = null;
    }
}

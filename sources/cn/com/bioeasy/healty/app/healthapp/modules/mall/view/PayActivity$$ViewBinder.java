package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.PayActivity;

public class PayActivity$$ViewBinder<T extends PayActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.rl_weixin, "field 'mrlWeixin' and method 'onClick'");
        target.mrlWeixin = (RelativeLayout) finder.castView(view, R.id.rl_weixin, "field 'mrlWeixin'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.rl_zhifubao, "field 'mrlZhifubao' and method 'onClick'");
        target.mrlZhifubao = (RelativeLayout) finder.castView(view2, R.id.rl_zhifubao, "field 'mrlZhifubao'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view3 = (View) finder.findRequiredView(source, R.id.rl_submit_pay, "field 'mrlPay' and method 'onClick'");
        target.mrlPay = (RelativeLayout) finder.castView(view3, R.id.rl_submit_pay, "field 'mrlPay'");
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.cbWeixin = (CheckBox) finder.castView((View) finder.findRequiredView(source, R.id.cb_weixin, "field 'cbWeixin'"), R.id.cb_weixin, "field 'cbWeixin'");
        target.cbZhifubao = (CheckBox) finder.castView((View) finder.findRequiredView(source, R.id.cb_zhifubao, "field 'cbZhifubao'"), R.id.cb_zhifubao, "field 'cbZhifubao'");
        target.mTvMoney = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_money, "field 'mTvMoney'"), R.id.tv_money, "field 'mTvMoney'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mrlWeixin = null;
        target.mrlZhifubao = null;
        target.mrlPay = null;
        target.cbWeixin = null;
        target.cbZhifubao = null;
        target.mTvMoney = null;
    }
}

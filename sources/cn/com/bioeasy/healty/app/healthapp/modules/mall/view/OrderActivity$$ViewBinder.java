package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderActivity;
import com.kyleduo.switchbutton.SwitchButton;

public class OrderActivity$$ViewBinder<T extends OrderActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        View view = (View) finder.findRequiredView(source, R.id.sbt_fapiao, "field 'mSbFapiao' and method 'onClick'");
        target.mSbFapiao = (SwitchButton) finder.castView(view, R.id.sbt_fapiao, "field 'mSbFapiao'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.mRlTaitouWrap = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_fapiaotaitou, "field 'mRlTaitouWrap'"), R.id.rl_fapiaotaitou, "field 'mRlTaitouWrap'");
        target.rlExitDefaultAddress = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.rl_address_wrap, "field 'rlExitDefaultAddress'"), R.id.rl_address_wrap, "field 'rlExitDefaultAddress'");
        target.tvNoAddressTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_no_address_title, "field 'tvNoAddressTitle'"), R.id.tv_no_address_title, "field 'tvNoAddressTitle'");
        target.tvName = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_name, "field 'tvName'"), R.id.tv_name, "field 'tvName'");
        target.tvPhone = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_phone, "field 'tvPhone'"), R.id.tv_phone, "field 'tvPhone'");
        target.tvAddress = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_address, "field 'tvAddress'"), R.id.tv_address, "field 'tvAddress'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.lv_order_goods_list, "field 'recyclerView'"), R.id.lv_order_goods_list, "field 'recyclerView'");
        target.tvGoodTotalPrice = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_totalmoney, "field 'tvGoodTotalPrice'"), R.id.tv_totalmoney, "field 'tvGoodTotalPrice'");
        target.tvRealTotalPrice = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_total_price, "field 'tvRealTotalPrice'"), R.id.tv_total_price, "field 'tvRealTotalPrice'");
        target.invoiceTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.invoice_title, "field 'invoiceTitle'"), R.id.invoice_title, "field 'invoiceTitle'");
        target.tvRemark = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.tv_remark, "field 'tvRemark'"), R.id.tv_remark, "field 'tvRemark'");
        target.buttonLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.order_button_gallery_lyaout, "field 'buttonLayout'"), R.id.order_button_gallery_lyaout, "field 'buttonLayout'");
        ((View) finder.findRequiredView(source, R.id.rl_go_address, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_submit_order, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mSbFapiao = null;
        target.mRlTaitouWrap = null;
        target.rlExitDefaultAddress = null;
        target.tvNoAddressTitle = null;
        target.tvName = null;
        target.tvPhone = null;
        target.tvAddress = null;
        target.recyclerView = null;
        target.tvGoodTotalPrice = null;
        target.tvRealTotalPrice = null;
        target.invoiceTitle = null;
        target.tvRemark = null;
        target.buttonLayout = null;
    }
}

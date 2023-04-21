package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.ShopCartActivity;

public class ShopCartActivity$$ViewBinder<T extends ShopCartActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.mListView = (ListView) finder.castView((View) finder.findRequiredView(source, R.id.lv, "field 'mListView'"), R.id.lv, "field 'mListView'");
        View view = (View) finder.findRequiredView(source, R.id.id_tv_add_all, "field 'mtvEdit' and method 'onClick'");
        target.mtvEdit = (TextView) finder.castView(view, R.id.id_tv_add_all, "field 'mtvEdit'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.rlEmpty = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.id_rl_cart_is_empty, "field 'rlEmpty'"), R.id.id_rl_cart_is_empty, "field 'rlEmpty'");
        target.id_rl_foot = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.id_rl_foot, "field 'id_rl_foot'"), R.id.id_rl_foot, "field 'id_rl_foot'");
        target.id_tv_totalPrice = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.id_tv_totalPrice, "field 'id_tv_totalPrice'"), R.id.id_tv_totalPrice, "field 'id_tv_totalPrice'");
        target.id_cb_select_all = (CheckBox) finder.castView((View) finder.findRequiredView(source, R.id.id_cb_select_all, "field 'id_cb_select_all'"), R.id.id_cb_select_all, "field 'id_cb_select_all'");
        View view2 = (View) finder.findRequiredView(source, R.id.tv_totalCount_jiesuan, "field 'id_tv_totalCount_jiesuan' and method 'onClick'");
        target.id_tv_totalCount_jiesuan = (TextView) finder.castView(view2, R.id.tv_totalCount_jiesuan, "field 'id_tv_totalCount_jiesuan'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.tvTitle = null;
        target.mListView = null;
        target.mtvEdit = null;
        target.rlEmpty = null;
        target.id_rl_foot = null;
        target.id_tv_totalPrice = null;
        target.id_cb_select_all = null;
        target.id_tv_totalCount_jiesuan = null;
    }
}

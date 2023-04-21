package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.AddressActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class AddressActivity$$ViewBinder<T extends AddressActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.mLv = (ListView) finder.castView((View) finder.findRequiredView(source, R.id.lv_address, "field 'mLv'"), R.id.lv_address, "field 'mLv'");
        target.mRlExitAddress = (FrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.lr_exit_address, "field 'mRlExitAddress'"), R.id.lr_exit_address, "field 'mRlExitAddress'");
        target.mRlNoAddress = (RelativeLayout) finder.castView((View) finder.findRequiredView(source, R.id.lr_no_address, "field 'mRlNoAddress'"), R.id.lr_no_address, "field 'mRlNoAddress'");
        View view = (View) finder.findRequiredView(source, R.id.id_tv_edit_all, "field 'tvAddAddress' and method 'onClick'");
        target.tvAddAddress = (TextView) finder.castView(view, R.id.id_tv_edit_all, "field 'tvAddAddress'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        ((View) finder.findRequiredView(source, R.id.btn_add_address, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.btn_add_address2, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
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
        target.mLv = null;
        target.mRlExitAddress = null;
        target.mRlNoAddress = null;
        target.tvAddAddress = null;
        target.ptrClassicFrameLayout = null;
    }
}

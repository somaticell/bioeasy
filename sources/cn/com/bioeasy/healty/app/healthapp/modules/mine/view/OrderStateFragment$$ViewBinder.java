package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.OrderStateFragment;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class OrderStateFragment$$ViewBinder<T extends OrderStateFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, T target, Object source) {
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'"), R.id.test_list_view_frame, "field 'ptrClassicFrameLayout'");
        target.mOrderLv = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.order_listv, "field 'mOrderLv'"), R.id.order_listv, "field 'mOrderLv'");
    }

    public void unbind(T target) {
        target.ptrClassicFrameLayout = null;
        target.mOrderLv = null;
    }
}

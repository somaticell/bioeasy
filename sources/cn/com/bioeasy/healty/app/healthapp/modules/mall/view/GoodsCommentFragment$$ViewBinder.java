package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsCommentFragment;
import com.chanven.lib.cptr.PtrClassicFrameLayout;

public class GoodsCommentFragment$$ViewBinder<T extends GoodsCommentFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        View view = (View) finder.findRequiredView(source, R.id.tv_all_comment, "field 'tv_comment_count' and method 'onClick'");
        target.tv_comment_count = (TextView) finder.castView(view, R.id.tv_all_comment, "field 'tv_comment_count'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.ev_quality = (RatingBar) finder.castView((View) finder.findRequiredView(source, R.id.eva_quality, "field 'ev_quality'"), R.id.eva_quality, "field 'ev_quality'");
        target.tv_good_comment_rate = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_good_comment_rate, "field 'tv_good_comment_rate'"), R.id.tv_good_comment_rate, "field 'tv_good_comment_rate'");
        View view2 = (View) finder.findRequiredView(source, R.id.tv_good_comment, "field 'tv_good_comment' and method 'onClick'");
        target.tv_good_comment = (TextView) finder.castView(view2, R.id.tv_good_comment, "field 'tv_good_comment'");
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view3 = (View) finder.findRequiredView(source, R.id.tv_medial_comment, "field 'tv_medial_comment' and method 'onClick'");
        target.tv_medial_comment = (TextView) finder.castView(view3, R.id.tv_medial_comment, "field 'tv_medial_comment'");
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.tv_eva_quality = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_eva_quality, "field 'tv_eva_quality'"), R.id.tv_eva_quality, "field 'tv_eva_quality'");
        View view4 = (View) finder.findRequiredView(source, R.id.tv_bad_comment, "field 'tv_bad_comment' and method 'onClick'");
        target.tv_bad_comment = (TextView) finder.castView(view4, R.id.tv_bad_comment, "field 'tv_bad_comment'");
        view4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        target.ptrClassicFrameLayout = (PtrClassicFrameLayout) finder.castView((View) finder.findRequiredView(source, R.id.goods_eva_view_frame, "field 'ptrClassicFrameLayout'"), R.id.goods_eva_view_frame, "field 'ptrClassicFrameLayout'");
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.goods_eva_lv, "field 'recyclerView'"), R.id.goods_eva_lv, "field 'recyclerView'");
        target.emptyCommentPanel = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.ll_empty_comment, "field 'emptyCommentPanel'"), R.id.ll_empty_comment, "field 'emptyCommentPanel'");
    }

    public void unbind(T target) {
        target.tv_comment_count = null;
        target.ev_quality = null;
        target.tv_good_comment_rate = null;
        target.tv_good_comment = null;
        target.tv_medial_comment = null;
        target.tv_eva_quality = null;
        target.tv_bad_comment = null;
        target.ptrClassicFrameLayout = null;
        target.recyclerView = null;
        target.emptyCommentPanel = null;
    }
}

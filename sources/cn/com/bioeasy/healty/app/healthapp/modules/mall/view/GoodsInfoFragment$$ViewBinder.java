package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsInfoFragment;
import com.bigkoo.convenientbanner.ConvenientBanner;

public class GoodsInfoFragment$$ViewBinder<T extends GoodsInfoFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.vp_item_goods_img = (ConvenientBanner) finder.castView((View) finder.findRequiredView(source, R.id.vp_item_goods_img, "field 'vp_item_goods_img'"), R.id.vp_item_goods_img, "field 'vp_item_goods_img'");
        target.vp_recommend = (ConvenientBanner) finder.castView((View) finder.findRequiredView(source, R.id.vp_recommend, "field 'vp_recommend'"), R.id.vp_recommend, "field 'vp_recommend'");
        target.ll_current_goods = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.ll_current_goods, "field 'll_current_goods'"), R.id.ll_current_goods, "field 'll_current_goods'");
        target.ll_recommend = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.ll_recommend, "field 'll_recommend'"), R.id.ll_recommend, "field 'll_recommend'");
        target.tv_goods_title = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_goods_title, "field 'tv_goods_title'"), R.id.tv_goods_title, "field 'tv_goods_title'");
        target.tv_goods_summary = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_goods_summary, "field 'tv_goods_summary'"), R.id.tv_goods_summary, "field 'tv_goods_summary'");
        target.tv_new_price = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_new_price, "field 'tv_new_price'"), R.id.tv_new_price, "field 'tv_new_price'");
        target.tv_old_price = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_old_price, "field 'tv_old_price'"), R.id.tv_old_price, "field 'tv_old_price'");
        target.tv_current_goods = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_current_goods, "field 'tv_current_goods'"), R.id.tv_current_goods, "field 'tv_current_goods'");
        target.tv_comment_count = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_comment_count, "field 'tv_comment_count'"), R.id.tv_comment_count, "field 'tv_comment_count'");
        target.tv_good_comment = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_good_comment, "field 'tv_good_comment'"), R.id.tv_good_comment, "field 'tv_good_comment'");
        View view = (View) finder.findRequiredView(source, R.id.ll_comment, "field 'commentPanel' and method 'OnClick'");
        target.commentPanel = (RelativeLayout) finder.castView(view, R.id.ll_comment, "field 'commentPanel'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, R.id.goods_eva_lv, "field 'recyclerView'"), R.id.goods_eva_lv, "field 'recyclerView'");
        target.emptyCommentPanel = (LinearLayout) finder.castView((View) finder.findRequiredView(source, R.id.ll_empty_comment, "field 'emptyCommentPanel'"), R.id.ll_empty_comment, "field 'emptyCommentPanel'");
    }

    public void unbind(T target) {
        target.vp_item_goods_img = null;
        target.vp_recommend = null;
        target.ll_current_goods = null;
        target.ll_recommend = null;
        target.tv_goods_title = null;
        target.tv_goods_summary = null;
        target.tv_new_price = null;
        target.tv_old_price = null;
        target.tv_current_goods = null;
        target.tv_comment_count = null;
        target.tv_good_comment = null;
        target.commentPanel = null;
        target.recyclerView = null;
        target.emptyCommentPanel = null;
    }
}

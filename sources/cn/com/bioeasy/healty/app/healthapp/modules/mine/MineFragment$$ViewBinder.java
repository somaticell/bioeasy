package cn.com.bioeasy.healty.app.healthapp.modules.mine;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.MineFragment;

public class MineFragment$$ViewBinder<T extends MineFragment> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mTvNickname = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_nickname, "field 'mTvNickname'"), R.id.tv_nickname, "field 'mTvNickname'");
        target.mTvPersonId = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_personid, "field 'mTvPersonId'"), R.id.tv_personid, "field 'mTvPersonId'");
        target.mTvLoginTip = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_login_tip, "field 'mTvLoginTip'"), R.id.tv_login_tip, "field 'mTvLoginTip'");
        target.mTVScore = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_score, "field 'mTVScore'"), R.id.tv_score, "field 'mTVScore'");
        ((View) finder.findRequiredView(source, R.id.rl_mine_login, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_score, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_pay, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_send, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_obtain, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_refund, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_pingjia, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_all_order, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        ((View) finder.findRequiredView(source, R.id.rl_about, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mTvNickname = null;
        target.mTvPersonId = null;
        target.mTvLoginTip = null;
        target.mTVScore = null;
    }
}

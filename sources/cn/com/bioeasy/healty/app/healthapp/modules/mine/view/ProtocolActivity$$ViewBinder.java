package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.ProtocolActivity;

public class ProtocolActivity$$ViewBinder<T extends ProtocolActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.mtvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'mtvTitle'"), R.id.tv_title, "field 'mtvTitle'");
        target.mProtocolContent = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_protocol_content, "field 'mProtocolContent'"), R.id.tv_protocol_content, "field 'mProtocolContent'");
        ((View) finder.findRequiredView(source, R.id.iv_back, "method 'OnClick'")).setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    public void unbind(T target) {
        target.mtvTitle = null;
        target.mProtocolContent = null;
    }
}

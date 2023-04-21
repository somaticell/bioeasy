package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.test.view.SampleDataActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.TweetPicturesPreviewer;
import net.oschina.common.widget.RichEditText;

public class SampleDataActivity$$ViewBinder<T extends SampleDataActivity> implements ButterKnife.ViewBinder<T> {
    public void bind(ButterKnife.Finder finder, final T target, Object source) {
        target.tvTitle = (TextView) finder.castView((View) finder.findRequiredView(source, R.id.tv_title, "field 'tvTitle'"), R.id.tv_title, "field 'tvTitle'");
        target.btnCity = (Button) finder.castView((View) finder.findRequiredView(source, R.id.id_city, "field 'btnCity'"), R.id.id_city, "field 'btnCity'");
        target.btnDistinct = (Button) finder.castView((View) finder.findRequiredView(source, R.id.id_distinct, "field 'btnDistinct'"), R.id.id_distinct, "field 'btnDistinct'");
        target.marketText = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.id_market, "field 'marketText'"), R.id.id_market, "field 'marketText'");
        target.sampleNameText = (EditText) finder.castView((View) finder.findRequiredView(source, R.id.id_sample_name, "field 'sampleNameText'"), R.id.id_sample_name, "field 'sampleNameText'");
        target.mLayImages = (TweetPicturesPreviewer) finder.castView((View) finder.findRequiredView(source, R.id.recycler_images, "field 'mLayImages'"), R.id.recycler_images, "field 'mLayImages'");
        target.remarkText = (RichEditText) finder.castView((View) finder.findRequiredView(source, R.id.edit_content, "field 'remarkText'"), R.id.edit_content, "field 'remarkText'");
        View view = (View) finder.findRequiredView(source, R.id.iv_picture, "field 'add' and method 'onClick'");
        target.add = (ImageView) finder.castView(view, R.id.iv_picture, "field 'add'");
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onClick(p0);
            }
        });
        View view2 = (View) finder.findRequiredView(source, R.id.upload, "field 'uploadBtn' and method 'onClick'");
        target.uploadBtn = (Button) finder.castView(view2, R.id.upload, "field 'uploadBtn'");
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
        target.btnCity = null;
        target.btnDistinct = null;
        target.marketText = null;
        target.sampleNameText = null;
        target.mLayImages = null;
        target.remarkText = null;
        target.add = null;
        target.uploadBtn = null;
    }
}

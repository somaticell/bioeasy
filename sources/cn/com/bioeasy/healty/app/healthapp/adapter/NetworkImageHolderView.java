package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.healty.app.healthapp.R;
import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;

public class NetworkImageHolderView implements Holder<String> {
    private SimpleDraweeView imageView;
    private View rootview;

    public View createView(Context context) {
        this.rootview = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.goods_item_head_img, (ViewGroup) null);
        this.imageView = (SimpleDraweeView) this.rootview.findViewById(R.id.sdv_item_head_img);
        return this.rootview;
    }

    public void UpdateUI(Context context, int position, String data) {
        this.imageView.setImageURI(Uri.parse(data));
    }
}

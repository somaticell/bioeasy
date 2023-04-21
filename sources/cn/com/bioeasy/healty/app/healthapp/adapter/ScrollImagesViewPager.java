package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.com.bioeasy.healty.app.healthapp.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

public class ScrollImagesViewPager extends StaticPagerAdapter {
    private int[] imgs = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3};

    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(this.imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return view;
    }

    public int getCount() {
        return this.imgs.length;
    }
}

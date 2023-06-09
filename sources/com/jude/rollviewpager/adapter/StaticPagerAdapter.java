package com.jude.rollviewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class StaticPagerAdapter extends PagerAdapter {
    public abstract View getView(ViewGroup viewGroup, int i);

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = container.getChildAt(position);
        if (itemView == null) {
            itemView = getView(container, position);
            container.addView(itemView);
        }
        onBind(itemView, position);
        return itemView;
    }

    public void onBind(View view, int position) {
    }
}

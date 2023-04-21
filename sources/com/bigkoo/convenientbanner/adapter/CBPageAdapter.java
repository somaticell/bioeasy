package com.bigkoo.convenientbanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bigkoo.convenientbanner.R;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;
import java.util.List;

public class CBPageAdapter<T> extends PagerAdapter {
    private final int MULTIPLE_COUNT = 300;
    private boolean canLoop = true;
    protected CBViewHolderCreator holderCreator;
    protected List<T> mDatas;
    private CBLoopViewPager viewPager;

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        return position % realCount;
    }

    public int getCount() {
        return this.canLoop ? getRealCount() * 300 : getRealCount();
    }

    public int getRealCount() {
        if (this.mDatas == null) {
            return 0;
        }
        return this.mDatas.size();
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(toRealPosition(position), (View) null, container);
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void finishUpdate(ViewGroup container) {
        int position = this.viewPager.getCurrentItem();
        if (position == 0) {
            position = this.viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = this.viewPager.getLastItem();
        }
        try {
            this.viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
        }
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop2) {
        this.canLoop = canLoop2;
    }

    public void setViewPager(CBLoopViewPager viewPager2) {
        this.viewPager = viewPager2;
    }

    public CBPageAdapter(CBViewHolderCreator holderCreator2, List<T> datas) {
        this.holderCreator = holderCreator2;
        this.mDatas = datas;
    }

    public View getView(int position, View view, ViewGroup container) {
        Holder holder;
        if (view == null) {
            holder = (Holder) this.holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, holder);
        } else {
            holder = (Holder) view.getTag(R.id.cb_item_tag);
        }
        if (this.mDatas != null && !this.mDatas.isEmpty()) {
            holder.UpdateUI(container.getContext(), position, this.mDatas.get(position));
        }
        return view;
    }
}

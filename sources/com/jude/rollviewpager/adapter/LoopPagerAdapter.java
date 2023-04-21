package com.jude.rollviewpager.adapter;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.ActivityChooserView;
import android.view.View;
import android.view.ViewGroup;
import com.jude.rollviewpager.HintView;
import com.jude.rollviewpager.RollPagerView;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class LoopPagerAdapter extends PagerAdapter {
    private ArrayList<View> mViewList = new ArrayList<>();
    private RollPagerView mViewPager;

    /* access modifiers changed from: protected */
    public abstract int getRealCount();

    public abstract View getView(ViewGroup viewGroup, int i);

    private class LoopHintViewDelegate implements RollPagerView.HintViewDelegate {
        private LoopHintViewDelegate() {
        }

        public void setCurrentPosition(int position, HintView hintView) {
            if (hintView != null) {
                hintView.setCurrent(position % LoopPagerAdapter.this.getRealCount());
            }
        }

        public void initView(int length, int gravity, HintView hintView) {
            if (hintView != null) {
                hintView.initView(LoopPagerAdapter.this.getRealCount(), gravity);
            }
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mViewList.clear();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        if (getCount() != 0) {
            this.mViewPager.getViewPager().setCurrentItem(1073741823 - (1073741823 % getRealCount()), false);
        }
    }

    public LoopPagerAdapter(RollPagerView viewPager) {
        this.mViewPager = viewPager;
        viewPager.setHintViewDelegate(new LoopHintViewDelegate());
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = findViewByPosition(container, position % getRealCount());
        container.addView(itemView);
        return itemView;
    }

    private View findViewByPosition(ViewGroup container, int position) {
        Iterator<View> it = this.mViewList.iterator();
        while (it.hasNext()) {
            View view = it.next();
            if (((Integer) view.getTag()).intValue() == position && view.getParent() == null) {
                return view;
            }
        }
        View view2 = getView(container, position);
        view2.setTag(Integer.valueOf(position));
        this.mViewList.add(view2);
        return view2;
    }

    @Deprecated
    public final int getCount() {
        return getRealCount() <= 1 ? getRealCount() : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }
}

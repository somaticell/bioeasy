package com.bigkoo.convenientbanner.listener;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import java.util.ArrayList;

public class CBPageChangeListener implements ViewPager.OnPageChangeListener {
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private int[] page_indicatorId;
    private ArrayList<ImageView> pointViews;

    public CBPageChangeListener(ArrayList<ImageView> pointViews2, int[] page_indicatorId2) {
        this.pointViews = pointViews2;
        this.page_indicatorId = page_indicatorId2;
    }

    public void onPageScrollStateChanged(int state) {
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int index) {
        for (int i = 0; i < this.pointViews.size(); i++) {
            this.pointViews.get(index).setImageResource(this.page_indicatorId[1]);
            if (index != i) {
                this.pointViews.get(i).setImageResource(this.page_indicatorId[0]);
            }
        }
        if (this.onPageChangeListener != null) {
            this.onPageChangeListener.onPageSelected(index);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener2) {
        this.onPageChangeListener = onPageChangeListener2;
    }
}

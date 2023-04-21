package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import cn.com.bioeasy.app.scope.PerActivity;
import java.util.List;

@PerActivity
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] mTitles2) {
        super(fm);
        setFragments(fm, fragments, mTitles2);
    }

    public void setFragments(FragmentManager fm, List<Fragment> fragments, String[] mTitles2) {
        this.mTitles = mTitles2;
        if (this.mFragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mFragments) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    public Fragment getItem(int paramInt) {
        return this.mFragments.get(paramInt);
    }

    public int getCount() {
        return this.mFragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return this.mTitles[position];
    }
}

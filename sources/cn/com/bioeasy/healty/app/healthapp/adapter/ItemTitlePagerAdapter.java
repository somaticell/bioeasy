package cn.com.bioeasy.healty.app.healthapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

public class ItemTitlePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] titleArray;

    public ItemTitlePagerAdapter(FragmentManager fm, List<Fragment> fragmentList2, String[] titleArray2) {
        super(fm);
        this.fragmentList = fragmentList2;
        this.titleArray = titleArray2;
    }

    public void setFramentData(List<Fragment> fragmentList2) {
        this.fragmentList = fragmentList2;
        notifyDataSetChanged();
    }

    public void setTitleData(String[] titleArray2) {
        this.titleArray = titleArray2;
        notifyDataSetChanged();
    }

    public CharSequence getPageTitle(int position) {
        return this.titleArray[position];
    }

    public int getCount() {
        return this.titleArray.length;
    }

    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }
}

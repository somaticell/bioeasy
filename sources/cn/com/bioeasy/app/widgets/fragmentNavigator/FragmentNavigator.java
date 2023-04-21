package cn.com.bioeasy.app.widgets.fragmentNavigator;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentNavigator {
    private static final String EXTRA_CURRENT_POSITION = "extra_current_position";
    private FragmentNavigatorAdapter mAdapter;
    @IdRes
    private int mContainerViewId;
    private int mCurrentPosition = -1;
    private int mDefaultPosition;
    private FragmentManager mFragmentManager;

    public FragmentNavigator(FragmentManager fragmentManager, FragmentNavigatorAdapter adapter, @IdRes int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mAdapter = adapter;
        this.mContainerViewId = containerViewId;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mCurrentPosition = savedInstanceState.getInt(EXTRA_CURRENT_POSITION, this.mDefaultPosition);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_CURRENT_POSITION, this.mCurrentPosition);
    }

    public void showFragment(int position) {
        showFragment(position, false);
    }

    public void showFragment(int position, boolean reset) {
        showFragment(position, reset, false);
    }

    public void showFragment(int position, boolean reset, boolean allowingStateLoss) {
        this.mCurrentPosition = position;
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        int count = this.mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            if (position != i) {
                hide(i, transaction);
            } else if (reset) {
                remove(position, transaction);
                add(position, transaction);
            } else {
                show(i, transaction);
            }
        }
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public void resetFragments() {
        resetFragments(this.mCurrentPosition);
    }

    public void resetFragments(int position) {
        resetFragments(position, false);
    }

    public void resetFragments(int position, boolean allowingStateLoss) {
        this.mCurrentPosition = position;
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        removeAll(transaction);
        add(position, transaction);
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public void removeAllFragment() {
        removeAllFragment(false);
    }

    public void removeAllFragment(boolean allowingStateLoss) {
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        removeAll(transaction);
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }

    public Fragment getCurrentFragment() {
        return getFragment(this.mCurrentPosition);
    }

    public Fragment getFragment(int position) {
        return this.mFragmentManager.findFragmentByTag(this.mAdapter.getTag(position));
    }

    private void show(int position, FragmentTransaction transaction) {
        Fragment fragment = this.mFragmentManager.findFragmentByTag(this.mAdapter.getTag(position));
        if (fragment == null) {
            add(position, transaction);
        } else {
            transaction.show(fragment);
        }
    }

    private void hide(int position, FragmentTransaction transaction) {
        Fragment fragment = this.mFragmentManager.findFragmentByTag(this.mAdapter.getTag(position));
        if (fragment != null) {
            transaction.hide(fragment);
        }
    }

    private void add(int position, FragmentTransaction transaction) {
        transaction.add(this.mContainerViewId, this.mAdapter.onCreateFragment(position), this.mAdapter.getTag(position));
    }

    private void removeAll(FragmentTransaction transaction) {
        int count = this.mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            remove(i, transaction);
        }
    }

    private void remove(int position, FragmentTransaction transaction) {
        Fragment fragment = this.mFragmentManager.findFragmentByTag(this.mAdapter.getTag(position));
        if (fragment != null) {
            transaction.remove(fragment);
        }
    }

    public void setDefaultPosition(int defaultPosition) {
        this.mDefaultPosition = defaultPosition;
        if (this.mCurrentPosition == -1) {
            this.mCurrentPosition = defaultPosition;
        }
    }
}

package cn.com.bioeasy.app.widgets.fragmentNavigator;

import android.support.v4.app.Fragment;

public interface FragmentNavigatorAdapter {
    int getCount();

    String getTag(int i);

    Fragment onCreateFragment(int i);
}

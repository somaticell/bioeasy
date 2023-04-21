package com.flyco.tablayout.listener;

import android.support.annotation.DrawableRes;

public interface CustomTabEntity {
    @DrawableRes
    int getTabSelectedIcon();

    String getTabTitle();

    @DrawableRes
    int getTabUnselectedIcon();
}

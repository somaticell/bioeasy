package cn.sharesdk.onekeyshare;

import cn.sharesdk.onekeyshare.themes.classic.ClassicTheme;

public enum OnekeyShareTheme {
    CLASSIC(0, new ClassicTheme());
    
    private final OnekeyShareThemeImpl impl;
    private final int value;

    private OnekeyShareTheme(int value2, OnekeyShareThemeImpl impl2) {
        this.value = value2;
        this.impl = impl2;
    }

    public int getValue() {
        return this.value;
    }

    public OnekeyShareThemeImpl getImpl() {
        return this.impl;
    }

    public static OnekeyShareTheme fromValue(int value2) {
        for (OnekeyShareTheme theme : values()) {
            if (theme.value == value2) {
                return theme;
            }
        }
        return CLASSIC;
    }
}

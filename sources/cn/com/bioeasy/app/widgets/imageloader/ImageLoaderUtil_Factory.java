package cn.com.bioeasy.app.widgets.imageloader;

import dagger.internal.Factory;

public enum ImageLoaderUtil_Factory implements Factory<ImageLoaderUtil> {
    INSTANCE;

    public ImageLoaderUtil get() {
        return new ImageLoaderUtil();
    }

    public static Factory<ImageLoaderUtil> create() {
        return INSTANCE;
    }
}

package cn.com.bioeasy.app.widgets.imageloader;

import android.content.Context;

public class ImageLoaderUtil {
    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;
    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;
    private BaseImageLoaderStrategy mStrategy = new GlideImageLoaderStrategy();

    public void loadImage(Context context, ImageLoader img) {
        this.mStrategy.loadImage(context, img);
    }

    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }
}

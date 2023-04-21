package cn.com.bioeasy.app.widgets.imageloader;

import android.content.Context;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.common.Config;
import cn.com.bioeasy.app.storage.SpUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import java.io.IOException;
import java.io.InputStream;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    public void loadImage(Context ctx, ImageLoader img) {
        if (!SpUtil.getBoolean(ctx, Config.ONLY_WIFI_LOAD_IMG, false)) {
            loadNormal(ctx, img);
        } else if (img.getWifiStrategy() != 1) {
            loadNormal(ctx, img);
        } else if (AppUtils.getNetWorkType(ctx) == 4) {
            loadNormal(ctx, img);
        } else {
            loadCache(ctx, img);
        }
    }

    private void loadNormal(Context ctx, ImageLoader img) {
        Glide.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).into(img.getImgView());
    }

    private void loadCache(Context ctx, ImageLoader img) {
        Glide.with(ctx).using(new StreamModelLoader<String>() {
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    public void cleanup() {
                    }

                    public String getId() {
                        return model;
                    }

                    public void cancel() {
                    }
                };
            }
        }).load(img.getUrl()).placeholder(img.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img.getImgView());
    }
}

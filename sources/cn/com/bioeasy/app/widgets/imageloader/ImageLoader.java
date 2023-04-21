package cn.com.bioeasy.app.widgets.imageloader;

import android.widget.ImageView;
import cn.com.bioeasy.app.commonlib.R;

public class ImageLoader {
    private ImageView imgView;
    private int placeHolder;
    private int type;
    private String url;
    private int wifiStrategy;

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
    }

    public int getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public int getPlaceHolder() {
        return this.placeHolder;
    }

    public ImageView getImgView() {
        return this.imgView;
    }

    public int getWifiStrategy() {
        return this.wifiStrategy;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public ImageView imgView = null;
        /* access modifiers changed from: private */
        public int placeHolder = R.mipmap.prj_default_pic_big;
        /* access modifiers changed from: private */
        public int type = 2;
        /* access modifiers changed from: private */
        public String url = "";
        /* access modifiers changed from: private */
        public int wifiStrategy = 0;

        public Builder type(int type2) {
            this.type = type2;
            return this;
        }

        public Builder url(String url2) {
            this.url = url2;
            return this;
        }

        public Builder placeHolder(int placeHolder2) {
            this.placeHolder = placeHolder2;
            return this;
        }

        public Builder imgView(ImageView imgView2) {
            this.imgView = imgView2;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }
}

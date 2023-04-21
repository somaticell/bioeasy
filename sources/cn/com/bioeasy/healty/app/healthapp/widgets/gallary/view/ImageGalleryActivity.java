package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.common.AppOperator;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.ImagePreviewView;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.PreviewerViewPager;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.io.File;
import java.util.concurrent.Future;
import net.oschina.common.utils.BitmapUtil;
import net.oschina.common.widget.Loading;

public class ImageGalleryActivity extends BIBaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    public static final String KEY_COOKIE = "cookie_need";
    public static final String KEY_IMAGE = "images";
    public static final String KEY_NEED_SAVE = "save";
    public static final String KEY_POSITION = "position";
    private int mCurPosition;
    private Point mDisplayDimens;
    /* access modifiers changed from: private */
    public PreviewerViewPager mImagePager;
    /* access modifiers changed from: private */
    public String[] mImageSources;
    private TextView mIndexText;
    private boolean mNeedCookie;
    private boolean mNeedSaveLocal;

    interface DoOverrideSizeCallback {
        void onDone(int i, int i2, boolean z);
    }

    public static void show(Context context, String images) {
        show(context, images, true);
    }

    public static void show(Context context, String images, boolean needSaveLocal) {
        if (images != null) {
            show(context, new String[]{images}, 0, needSaveLocal);
        }
    }

    public static void show(Context context, String images, boolean needSaveLocal, boolean needCookie) {
        if (images != null) {
            show(context, new String[]{images}, 0, needSaveLocal, needCookie);
        }
    }

    public static void show(Context context, String[] images, int position) {
        show(context, images, position, true);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal) {
        show(context, images, position, needSaveLocal, false);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal, boolean needCookie) {
        if (images != null && images.length != 0) {
            Intent intent = new Intent(context, ImageGalleryActivity.class);
            intent.putExtra(KEY_IMAGE, images);
            intent.putExtra(KEY_POSITION, position);
            intent.putExtra(KEY_NEED_SAVE, needSaveLocal);
            intent.putExtra(KEY_COOKIE, needCookie);
            context.startActivity(intent);
        }
    }

    public void onClick(View v) {
        v.getId();
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        this.mCurPosition = position;
        this.mIndexText.setText(String.format("%s/%s", new Object[]{Integer.valueOf(position + 1), Integer.valueOf(this.mImageSources.length)}));
    }

    public void onPageScrollStateChanged(int state) {
    }

    /* access modifiers changed from: private */
    @TargetApi(13)
    public synchronized Point getDisplayDimens() {
        Point displayDimens;
        Point point;
        if (this.mDisplayDimens != null) {
            point = this.mDisplayDimens;
        } else {
            Display display = ((WindowManager) getSystemService("window")).getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= 13) {
                displayDimens = new Point();
                display.getSize(displayDimens);
            } else {
                displayDimens = new Point(display.getWidth(), display.getHeight());
            }
            this.mDisplayDimens = displayDimens;
            point = this.mDisplayDimens;
        }
        return point;
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_image_gallery;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        Bundle bundle = getIntent().getExtras();
        this.mImageSources = bundle.getStringArray(KEY_IMAGE);
        this.mCurPosition = bundle.getInt(KEY_POSITION, 0);
        this.mNeedSaveLocal = bundle.getBoolean(KEY_NEED_SAVE, true);
        this.mNeedCookie = bundle.getBoolean(KEY_COOKIE, false);
        getWindow().setLayout(-1, -1);
        setTitle("");
        this.mImagePager = (PreviewerViewPager) findViewById(R.id.vp_image);
        this.mIndexText = (TextView) findViewById(R.id.tv_index);
        this.mImagePager.addOnPageChangeListener(this);
        if (this.mNeedSaveLocal) {
            findViewById(R.id.iv_save).setOnClickListener(this);
        } else {
            findViewById(R.id.iv_save).setVisibility(8);
        }
        int len = this.mImageSources.length;
        if (this.mCurPosition < 0 || this.mCurPosition >= len) {
            this.mCurPosition = 0;
        }
        if (len == 1) {
            this.mIndexText.setVisibility(8);
        }
        this.mImagePager.setAdapter(new ViewPagerAdapter());
        this.mImagePager.setCurrentItem(this.mCurPosition);
        onPageSelected(this.mCurPosition);
    }

    private class ViewPagerAdapter extends PagerAdapter implements ImagePreviewView.OnReachBorderListener {
        private View.OnClickListener mFinishClickListener;

        private ViewPagerAdapter() {
        }

        public int getCount() {
            return ImageGalleryActivity.this.mImageSources.length;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.lay_gallery_page_item_contener, container, false);
            ImagePreviewView previewView = (ImagePreviewView) view.findViewById(R.id.iv_preview);
            previewView.setOnReachBorderListener(this);
            ImageView defaultView = (ImageView) view.findViewById(R.id.iv_default);
            loadImage(ImageGalleryActivity.this.mImageSources[position], previewView, defaultView, (Loading) view.findViewById(R.id.loading));
            previewView.setOnClickListener(getListener());
            container.addView(view);
            return view;
        }

        private View.OnClickListener getListener() {
            if (this.mFinishClickListener == null) {
                this.mFinishClickListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        ImageGalleryActivity.this.finish();
                    }
                };
            }
            return this.mFinishClickListener;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public void onReachBorder(boolean isReached) {
            ImageGalleryActivity.this.mImagePager.isInterceptable(isReached);
        }

        private <T> void loadImage(T urlOrPath, ImageView previewView, ImageView defaultView, Loading loading) {
            final T t = urlOrPath;
            final Loading loading2 = loading;
            final ImageView imageView = defaultView;
            final ImageView imageView2 = previewView;
            loadImageDoDownAndGetOverrideSize(urlOrPath, new DoOverrideSizeCallback() {
                public void onDone(int overrideW, int overrideH, boolean isTrue) {
                    DrawableRequestBuilder builder = ImageGalleryActivity.this.getImageLoader().load(t).listener(new RequestListener<T, GlideDrawable>() {
                        public boolean onException(Exception e, T t, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (e != null) {
                                e.printStackTrace();
                            }
                            loading2.stop();
                            loading2.setVisibility(8);
                            imageView.setVisibility(0);
                            return false;
                        }

                        public boolean onResourceReady(GlideDrawable resource, T t, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loading2.stop();
                            loading2.setVisibility(8);
                            return false;
                        }
                    }).diskCacheStrategy(DiskCacheStrategy.SOURCE);
                    if (isTrue && overrideW > 0 && overrideH > 0) {
                        builder = builder.override(overrideW, overrideH);
                    }
                    builder.into(imageView2);
                }
            });
        }

        private <T> void loadImageDoDownAndGetOverrideSize(T urlOrPath, final DoOverrideSizeCallback callback) {
            final Future<File> future = ImageGalleryActivity.this.getImageLoader().load(urlOrPath).downloadOnly(Integer.MIN_VALUE, Integer.MIN_VALUE);
            AppOperator.runOnThread(new Runnable() {
                public void run() {
                    final int overrideW;
                    final int overrideH;
                    try {
                        BitmapFactory.Options options = BitmapUtil.createOptions();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(((File) future.get()).getAbsolutePath(), options);
                        int width = options.outWidth;
                        int height = options.outHeight;
                        BitmapUtil.resetOptions(options);
                        if (width <= 0 || height <= 0) {
                            ImageGalleryActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    callback.onDone(0, 0, false);
                                }
                            });
                            return;
                        }
                        Point point = ImageGalleryActivity.this.getDisplayDimens();
                        int maxLen = Math.min(Math.min(point.y, point.x) * 5, 4098);
                        if (((float) width) / ((float) height) > ((float) point.x) / ((float) point.y)) {
                            overrideH = Math.min(height, point.y);
                            overrideW = Math.min(width, maxLen);
                        } else {
                            overrideW = Math.min(width, point.x);
                            overrideH = Math.min(height, maxLen);
                        }
                        ImageGalleryActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                callback.onDone(overrideW, overrideH, true);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        ImageGalleryActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                callback.onDone(0, 0, false);
                            }
                        });
                    }
                }
            });
        }
    }
}

package cn.com.bioeasy.app.widgets.recyclerview.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.widgets.imageloader.ImageLoader;
import cn.com.bioeasy.app.widgets.imageloader.ImageLoaderUtil;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageLoaderUtil imageLoaderUtil = new ImageLoaderUtil();
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews = new SparseArray<>();

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
    }

    public static ViewHolder createViewHolder(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        return new ViewHolder(context, LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    public <T extends View> T getView(int viewId) {
        View view = this.mViews.get(viewId);
        if (view != null) {
            return view;
        }
        View view2 = this.mConvertView.findViewById(viewId);
        this.mViews.put(viewId, view2);
        return view2;
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public ViewHolder setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public ViewHolder setText(int viewId, Spanned text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        this.imageLoaderUtil.loadImage(this.mContext, new ImageLoader.Builder().url(url).imgView((ImageView) getView(viewId)).strategy(1).build());
        return this;
    }

    public ViewHolder setImageUrlInGZ(int viewId, String url) {
        String newUrl = url;
        ImageView view = (ImageView) getView(viewId);
        ImageLoader.Builder builder = new ImageLoader.Builder();
        if (url != null && url.startsWith("W")) {
            newUrl = AppUtils.convertPicStartWithW(url);
        }
        if (url == null) {
            newUrl = "";
        }
        this.imageLoaderUtil.loadImage(this.mContext, builder.url(newUrl).imgView(view).strategy(1).build());
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        ((TextView) getView(viewId)).setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        ((TextView) getView(viewId)).setTextColor(this.mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint({"NewApi"})
    public ViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= 11) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        getView(viewId).setVisibility(visible ? 0 : 8);
        return this;
    }

    public ViewHolder linkify(int viewId) {
        Linkify.addLinks((TextView) getView(viewId), 15);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = (TextView) getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | 128);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ((ProgressBar) getView(viewId)).setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = (ProgressBar) getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ((ProgressBar) getView(viewId)).setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        ((RatingBar) getView(viewId)).setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = (RatingBar) getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        getView(viewId).setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        ((Checkable) getView(viewId)).setChecked(checked);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        getView(viewId).setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }
}

package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.com.bioeasy.app.gallery.Image;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageLoaderListener;
import com.bumptech.glide.Glide;

public class ImageAdapter extends BaseRecyclerAdapter<Image> {
    private ImageLoaderListener loader;

    public ImageAdapter(Context context, ImageLoaderListener loader2) {
        super(context, 0);
        this.loader = loader2;
    }

    public int getItemViewType(int position) {
        if (((Image) getItem(position)).getId() == 0) {
            return 0;
        }
        return 1;
    }

    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ImageViewHolder) {
            Glide.clear((View) ((ImageViewHolder) holder).mImageView);
        }
    }

    /* access modifiers changed from: protected */
    public RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        if (type == 0) {
            return new CamViewHolder(this.mInflater.inflate(R.layout.item_list_cam, parent, false));
        }
        return new ImageViewHolder(this.mInflater.inflate(R.layout.item_list_image, parent, false));
    }

    /* access modifiers changed from: protected */
    public void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Image item, int position) {
        int i;
        int i2 = 0;
        if (item.getId() != 0) {
            ImageViewHolder h = (ImageViewHolder) holder;
            h.mCheckView.setSelected(item.isSelect());
            View view = h.mMaskView;
            if (item.isSelect()) {
                i = 0;
            } else {
                i = 8;
            }
            view.setVisibility(i);
            ImageView imageView = h.mGifMask;
            if (!item.getPath().toLowerCase().endsWith("gif")) {
                i2 = 8;
            }
            imageView.setVisibility(i2);
            this.loader.displayImage(h.mImageView, item.getPath());
        }
    }

    private static class CamViewHolder extends RecyclerView.ViewHolder {
        CamViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mCheckView;
        ImageView mGifMask;
        ImageView mImageView;
        View mMaskView;

        ImageViewHolder(View itemView) {
            super(itemView);
            this.mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
            this.mCheckView = (ImageView) itemView.findViewById(R.id.cb_selected);
            this.mMaskView = itemView.findViewById(R.id.lay_mask);
            this.mGifMask = (ImageView) itemView.findViewById(R.id.iv_is_gif);
        }
    }
}

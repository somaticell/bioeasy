package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.bioeasy.app.gallery.ImageFolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageLoaderListener;

public class ImageFolderAdapter extends BaseRecyclerAdapter<ImageFolder> {
    private ImageLoaderListener loader;

    public ImageFolderAdapter(Context context) {
        super(context, 0);
    }

    /* access modifiers changed from: protected */
    public RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new FolderViewHolder(this.mInflater.inflate(R.layout.item_list_folder, parent, false));
    }

    /* access modifiers changed from: protected */
    public void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, ImageFolder item, int position) {
        FolderViewHolder h = (FolderViewHolder) holder;
        h.tv_name.setText(item.getName());
        h.tv_size.setText(String.format("(%s)", new Object[]{Integer.valueOf(item.getImages().size())}));
        if (this.loader != null) {
            this.loader.displayImage(h.iv_image, item.getAlbumPath());
        }
    }

    public void setLoader(ImageLoaderListener loader2) {
        this.loader = loader2;
    }

    private static class FolderViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_size;

        public FolderViewHolder(View itemView) {
            super(itemView);
            this.iv_image = (ImageView) itemView.findViewById(R.id.iv_folder);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_folder_name);
            this.tv_size = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }
}

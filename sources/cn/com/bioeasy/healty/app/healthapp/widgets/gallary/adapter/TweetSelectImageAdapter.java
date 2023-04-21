package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.com.bioeasy.app.utils.CollectionUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageGalleryActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.TweetPicturesPreviewerItemTouchCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import java.util.ArrayList;
import java.util.List;

public class TweetSelectImageAdapter extends RecyclerView.Adapter<TweetSelectImageHolder> implements TweetPicturesPreviewerItemTouchCallback.ItemTouchHelperAdapter {
    /* access modifiers changed from: private */
    public static Context context;
    private final int MAX_SIZE = 9;
    private final int TYPE_ADD = 1;
    private final int TYPE_NONE = 0;
    /* access modifiers changed from: private */
    public Callback mCallback;
    /* access modifiers changed from: private */
    public final List<Model> mModels = new ArrayList();

    public interface Callback {
        Context getContext();

        RequestManager getImgLoader();

        void onItemRemoved(int i);

        void onLoadMoreClick();

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public TweetSelectImageAdapter(Callback callback, Context context2) {
        this.mCallback = callback;
        context = context2;
    }

    public int getItemViewType(int position) {
        int size = this.mModels.size();
        if (size < 9 && position == size) {
            return 1;
        }
        return 0;
    }

    public TweetSelectImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tweet_publish_selecter, parent, false);
        return viewType == 0 ? new TweetSelectImageHolder(view, (TweetSelectImageHolder.HolderListener) new TweetSelectImageHolder.HolderListener() {
            public void onDelete(Model model) {
                int pos;
                if (TweetSelectImageAdapter.this.mCallback != null && (pos = TweetSelectImageAdapter.this.mModels.indexOf(model)) != -1) {
                    TweetSelectImageAdapter.this.mModels.remove(pos);
                    if (TweetSelectImageAdapter.this.mModels.size() > 0) {
                        TweetSelectImageAdapter.this.notifyItemRemoved(pos);
                    } else {
                        TweetSelectImageAdapter.this.notifyDataSetChanged();
                    }
                    TweetSelectImageAdapter.this.mCallback.onItemRemoved(TweetSelectImageAdapter.this.getItemCount());
                }
            }

            public void onDrag(TweetSelectImageHolder holder) {
                if (TweetSelectImageAdapter.this.mCallback != null) {
                    TweetSelectImageAdapter.this.mCallback.onStartDrag(holder);
                }
            }

            public void onClick(Model model) {
                ImageGalleryActivity.show(TweetSelectImageAdapter.this.mCallback.getContext(), model.path, false);
            }
        }) : new TweetSelectImageHolder(view, (View.OnClickListener) new View.OnClickListener() {
            public void onClick(View v) {
                Callback callback = TweetSelectImageAdapter.this.mCallback;
                if (callback != null) {
                    callback.onLoadMoreClick();
                }
            }
        });
    }

    public void onBindViewHolder(TweetSelectImageHolder holder, int position) {
        int size = this.mModels.size();
        if (size >= 9 || size != position) {
            holder.bind(position, this.mModels.get(position), this.mCallback.getImgLoader());
        }
    }

    public void onViewRecycled(TweetSelectImageHolder holder) {
        Glide.clear((View) holder.mImage);
    }

    public int getItemCount() {
        int size = this.mModels.size();
        if (size == 9) {
            return size;
        }
        if (size == 0) {
            return 0;
        }
        return size + 1;
    }

    public void clear() {
        this.mModels.clear();
    }

    public void add(Model model) {
        if (this.mModels.size() < 9) {
            this.mModels.add(model);
        }
    }

    public void add(String path) {
        add(new Model(path));
    }

    public String[] getPaths() {
        int size = this.mModels.size();
        if (size == 0) {
            return null;
        }
        String[] paths = new String[size];
        int i = 0;
        for (Model model : this.mModels) {
            paths[i] = model.path;
            i++;
        }
        return paths;
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return false;
        }
        CollectionUtil.move(this.mModels, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void onItemDelete() {
        Intent intent = new Intent();
        intent.setAction(ActionConstant.ACTION_UPLOAD);
        intent.putExtra("selected", getItemCount());
        context.sendBroadcast(intent);
    }

    public void onItemDismiss(int position) {
        this.mModels.remove(position);
        notifyItemRemoved(position);
    }

    public static class Model {
        public boolean isUpload;
        public String path;

        public Model(String path2) {
            this.path = path2;
        }
    }

    static class TweetSelectImageHolder extends RecyclerView.ViewHolder implements TweetPicturesPreviewerItemTouchCallback.ItemTouchHelperViewHolder {
        /* access modifiers changed from: private */
        public ImageView mDelete;
        /* access modifiers changed from: private */
        public ImageView mImage;
        /* access modifiers changed from: private */
        public HolderListener mListener;

        interface HolderListener {
            void onClick(Model model);

            void onDelete(Model model);

            void onDrag(TweetSelectImageHolder tweetSelectImageHolder);
        }

        private TweetSelectImageHolder(View itemView, HolderListener listener) {
            super(itemView);
            this.mListener = listener;
            this.mImage = (ImageView) itemView.findViewById(R.id.iv_content);
            this.mDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            this.mDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Object obj = v.getTag();
                    HolderListener holderListener = TweetSelectImageHolder.this.mListener;
                    if (holderListener != null && obj != null && (obj instanceof Model)) {
                        holderListener.onDelete((Model) obj);
                    }
                }
            });
            this.mImage.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    HolderListener holderListener = TweetSelectImageHolder.this.mListener;
                    if (holderListener == null) {
                        return true;
                    }
                    holderListener.onDrag(TweetSelectImageHolder.this);
                    return true;
                }
            });
            this.mImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Object obj = TweetSelectImageHolder.this.mDelete.getTag();
                    HolderListener holderListener = TweetSelectImageHolder.this.mListener;
                    if (holderListener != null && obj != null && (obj instanceof Model)) {
                        holderListener.onClick((Model) obj);
                    }
                }
            });
            this.mImage.setBackgroundColor(-2434342);
        }

        private TweetSelectImageHolder(View itemView, View.OnClickListener clickListener) {
            super(itemView);
            Intent intent = new Intent();
            intent.setAction(ActionConstant.ACTION_UPLOAD);
            intent.putExtra("selected", -1);
            TweetSelectImageAdapter.context.sendBroadcast(intent);
            this.mImage = (ImageView) itemView.findViewById(R.id.iv_content);
            this.mDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            this.mDelete.setVisibility(8);
            this.mImage.setImageResource(R.drawable.ic_tweet_add);
            this.mImage.setOnClickListener(clickListener);
            this.mImage.setBackgroundDrawable((Drawable) null);
        }

        public void bind(int position, Model model, RequestManager loader) {
            this.mDelete.setTag(model);
            Glide.clear((View) this.mImage);
            loader.load(model.path).centerCrop().error((int) R.drawable.ic_split_graph).into(this.mImage);
        }

        public void onItemSelected() {
            try {
                ((Vibrator) this.itemView.getContext().getSystemService("vibrator")).vibrate(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onItemClear() {
        }
    }
}

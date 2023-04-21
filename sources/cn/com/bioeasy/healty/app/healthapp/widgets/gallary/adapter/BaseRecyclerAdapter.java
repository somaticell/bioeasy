package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    public static final int BOTH_HEADER_FOOTER = 3;
    public static final int NEITHER = 0;
    public static final int ONLY_FOOTER = 2;
    public static final int ONLY_HEADER = 1;
    public static final int STATE_HIDE = 5;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_LOADING = 8;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_NO_MORE = 1;
    public static final int STATE_REFRESHING = 6;
    public static final int VIEW_TYPE_FOOTER = -2;
    public static final int VIEW_TYPE_HEADER = -1;
    public static final int VIEW_TYPE_NORMAL = 0;
    public final int BEHAVIOR_MODE;
    protected Context mContext;
    protected View mHeaderView;
    protected LayoutInflater mInflater;
    protected List<T> mItems = new ArrayList();
    protected int mState;
    protected String mSystemTime;
    private OnClickListener onClickListener;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;
    /* access modifiers changed from: private */
    public OnItemLongClickListener onItemLongClickListener;
    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;
    private OnLongClickListener onLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(int i, long j);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int i, long j);
    }

    public interface OnLoadingHeaderCallBack {
        void onBindHeaderHolder(RecyclerView.ViewHolder viewHolder, int i);

        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup viewGroup);
    }

    /* access modifiers changed from: protected */
    public abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder viewHolder, T t, int i);

    /* access modifiers changed from: protected */
    public abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup viewGroup, int i);

    public BaseRecyclerAdapter(Context context, int mode) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.BEHAVIOR_MODE = mode;
        this.mState = 5;
        initListener();
    }

    private void initListener() {
        this.onClickListener = new OnClickListener() {
            public void onClick(int position, long itemId) {
                if (BaseRecyclerAdapter.this.onItemClickListener != null) {
                    BaseRecyclerAdapter.this.onItemClickListener.onItemClick(position, itemId);
                }
            }
        };
        this.onLongClickListener = new OnLongClickListener() {
            public boolean onLongClick(int position, long itemId) {
                if (BaseRecyclerAdapter.this.onItemLongClickListener == null) {
                    return false;
                }
                BaseRecyclerAdapter.this.onItemLongClickListener.onLongClick(position, itemId);
                return true;
            }
        };
    }

    public void setSystemTime(String systemTime) {
        this.mSystemTime = systemTime;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case -2:
                return new FooterViewHolder(this.mInflater.inflate(R.layout.recycler_footer_view, parent, false));
            case -1:
                if (this.onLoadingHeaderCallBack != null) {
                    return this.onLoadingHeaderCallBack.onCreateHeaderHolder(parent);
                }
                throw new IllegalArgumentException("you have to impl the interface when using this viewType");
            default:
                RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
                if (holder == null) {
                    return holder;
                }
                holder.itemView.setTag(holder);
                holder.itemView.setOnLongClickListener(this.onLongClickListener);
                holder.itemView.setOnClickListener(this.onClickListener);
                return holder;
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case -2:
                FooterViewHolder fvh = (FooterViewHolder) holder;
                fvh.itemView.setVisibility(0);
                switch (this.mState) {
                    case 1:
                        fvh.tv_footer.setText(this.mContext.getResources().getString(R.string.state_not_more));
                        fvh.pb_footer.setVisibility(8);
                        return;
                    case 2:
                    case 8:
                        fvh.tv_footer.setText(this.mContext.getResources().getString(R.string.state_loading));
                        fvh.pb_footer.setVisibility(0);
                        return;
                    case 3:
                        fvh.tv_footer.setText(this.mContext.getResources().getString(R.string.state_network_error));
                        fvh.pb_footer.setVisibility(8);
                        return;
                    case 5:
                        fvh.itemView.setVisibility(8);
                        return;
                    case 6:
                        fvh.tv_footer.setText(this.mContext.getResources().getString(R.string.state_refreshing));
                        fvh.pb_footer.setVisibility(8);
                        return;
                    case 7:
                        fvh.tv_footer.setText(this.mContext.getResources().getString(R.string.state_load_error));
                        fvh.pb_footer.setVisibility(8);
                        return;
                    default:
                        return;
                }
            case -1:
                if (this.onLoadingHeaderCallBack != null) {
                    this.onLoadingHeaderCallBack.onBindHeaderHolder(holder, position);
                    return;
                }
                return;
            default:
                onBindDefaultViewHolder(holder, getItems().get(getIndex(position)), position);
                return;
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    if (BaseRecyclerAdapter.this.getItemViewType(position) == -1 || BaseRecyclerAdapter.this.getItemViewType(position) == -2) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        boolean z = true;
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && (lp instanceof StaggeredGridLayoutManager.LayoutParams)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (this.BEHAVIOR_MODE == 1) {
                if (holder.getLayoutPosition() != 0) {
                    z = false;
                }
                p.setFullSpan(z);
            } else if (this.BEHAVIOR_MODE == 2) {
                if (holder.getLayoutPosition() != this.mItems.size() + 1) {
                    z = false;
                }
                p.setFullSpan(z);
            } else if (this.BEHAVIOR_MODE != 3) {
            } else {
                if (holder.getLayoutPosition() == 0 || holder.getLayoutPosition() == this.mItems.size() + 1) {
                    p.setFullSpan(true);
                }
            }
        }
    }

    public int getItemViewType(int position) {
        if (position == 0 && (this.BEHAVIOR_MODE == 1 || this.BEHAVIOR_MODE == 3)) {
            return -1;
        }
        if (position + 1 == getItemCount() && (this.BEHAVIOR_MODE == 2 || this.BEHAVIOR_MODE == 3)) {
            return -2;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getIndex(int position) {
        return (this.BEHAVIOR_MODE == 1 || this.BEHAVIOR_MODE == 3) ? position - 1 : position;
    }

    public int getItemCount() {
        if (this.BEHAVIOR_MODE == 2 || this.BEHAVIOR_MODE == 1) {
            return this.mItems.size() + 1;
        }
        if (this.BEHAVIOR_MODE == 3) {
            return this.mItems.size() + 2;
        }
        return this.mItems.size();
    }

    public int getCount() {
        return this.mItems.size();
    }

    public final View getHeaderView() {
        return this.mHeaderView;
    }

    public final void setHeaderView(View view) {
        this.mHeaderView = view;
    }

    public final List<T> getItems() {
        return this.mItems;
    }

    public void addAll(List<T> items) {
        if (items != null) {
            this.mItems.addAll(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }
    }

    public final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(this.mItems.size());
        }
    }

    public void addItem(int position, T item) {
        if (item != null) {
            this.mItems.add(getIndex(position), item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.mItems.set(getIndex(position), item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }

    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = this.mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (getItemCount() > position) {
            this.mItems.remove(getIndex(position));
            notifyItemRemoved(position);
        }
    }

    public final T getItem(int position) {
        int p = getIndex(position);
        if (p < 0 || p >= this.mItems.size()) {
            return null;
        }
        return this.mItems.get(getIndex(position));
    }

    public final void resetItem(List<T> items) {
        if (items != null) {
            clear();
            addAll(items);
        }
    }

    public final void clear() {
        this.mItems.clear();
        setState(5, false);
        notifyDataSetChanged();
    }

    public void setState(int mState2, boolean isUpdate) {
        this.mState = mState2;
        if (isUpdate) {
            updateItem(getItemCount() - 1);
        }
    }

    public int getState() {
        return this.mState;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener2) {
        this.onItemLongClickListener = onItemLongClickListener2;
    }

    public final void setOnLoadingHeaderCallBack(OnLoadingHeaderCallBack listener) {
        this.onLoadingHeaderCallBack = listener;
    }

    public static abstract class OnClickListener implements View.OnClickListener {
        public abstract void onClick(int i, long j);

        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }
    }

    public static abstract class OnLongClickListener implements View.OnLongClickListener {
        public abstract boolean onLongClick(int i, long j);

        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            return onLongClick(holder.getAdapterPosition(), holder.getItemId());
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pb_footer;
        public TextView tv_footer;

        public FooterViewHolder(View view) {
            super(view);
            this.pb_footer = (ProgressBar) view.findViewById(R.id.pb_footer);
            this.tv_footer = (TextView) view.findViewById(R.id.tv_footer);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

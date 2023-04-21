package cn.com.bioeasy.app.widgets.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.app.widgets.recyclerview.base.ItemViewDelegate;
import cn.com.bioeasy.app.widgets.recyclerview.base.ItemViewDelegateManager;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener<T> mOnItemClickListener;
    public int offset;

    public interface OnItemClickListener<T> {
        void onItemClick(View view, RecyclerView.ViewHolder viewHolder, T t, int i);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, T t, int i);
    }

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        this.offset = 0;
        this.mContext = context;
        this.mDatas = datas;
        this.mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public MultiItemTypeAdapter(Context context) {
        this(context, new ArrayList());
    }

    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) {
            return super.getItemViewType(position);
        }
        return this.mItemViewDelegateManager.getItemViewType(this.mDatas.get(position), position);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(this.mContext, parent, this.mItemViewDelegateManager.getItemViewLayoutId(viewType));
    }

    public void convert(ViewHolder holder, T t) {
        this.mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    /* access modifiers changed from: protected */
    public boolean isEnabled(int viewType) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void setListener(final ViewHolder viewHolder) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MultiItemTypeAdapter.this.mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    MultiItemTypeAdapter.this.mOnItemClickListener.onItemClick(v, viewHolder, MultiItemTypeAdapter.this.mDatas.get(position - MultiItemTypeAdapter.this.offset), position);
                }
            }
        });
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (MultiItemTypeAdapter.this.mOnItemClickListener == null) {
                    return false;
                }
                int position = viewHolder.getAdapterPosition();
                return MultiItemTypeAdapter.this.mOnItemClickListener.onItemLongClick(v, viewHolder, MultiItemTypeAdapter.this.mDatas.get(position - MultiItemTypeAdapter.this.offset), position);
            }
        });
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, this.mDatas.get(position));
        setListener(holder);
    }

    public int getItemCount() {
        return this.mDatas.size();
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        this.mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        this.mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean useItemViewDelegateManager() {
        return this.mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void addItemList(List<T> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public void refresItems(List<T> items) {
        this.mDatas = items;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (!this.mDatas.contains(item)) {
            this.mDatas.add(item);
            notifyDataSetChanged();
        }
    }

    public void updateItem(T item) {
        int index = this.mDatas.indexOf(item);
        if (index > -1) {
            this.mDatas.set(index, item);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(T item) {
        if (this.mDatas.contains(item)) {
            this.mDatas.remove(item);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(int position) {
        if (this.mDatas.size() > position) {
            this.mDatas.remove(position);
            notifyDataSetChanged();
        }
    }
}

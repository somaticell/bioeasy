package cn.com.bioeasy.app.widgets.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.com.bioeasy.app.widgets.abslistview.base.ItemViewDelegate;
import cn.com.bioeasy.app.widgets.abslistview.base.ItemViewDelegateManager;
import java.util.List;

public class MultiItemTypeAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    private ItemViewDelegateManager mItemViewDelegateManager = new ItemViewDelegateManager();

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        this.mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return this.mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public int getViewTypeCount() {
        if (useItemViewDelegateManager()) {
            return this.mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            return this.mItemViewDelegateManager.getItemViewType(this.mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int layoutId = this.mItemViewDelegateManager.getItemViewDelegate(this.mDatas.get(position), position).getItemViewLayoutId();
        if (convertView == null) {
            viewHolder = new ViewHolder(this.mContext, LayoutInflater.from(this.mContext).inflate(layoutId, parent, false), parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /* access modifiers changed from: protected */
    public void convert(ViewHolder viewHolder, T item, int position) {
        this.mItemViewDelegateManager.convert(viewHolder, item, position);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

    public int getCount() {
        return this.mDatas.size();
    }

    public T getItem(int position) {
        return this.mDatas.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
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
        }
        notifyDataSetChanged();
    }

    public void updateItem(T item) {
        int index = this.mDatas.indexOf(item);
        if (index > -1) {
            this.mDatas.set(index, item);
        }
        notifyDataSetChanged();
    }

    public void deleteItem(T item) {
        if (this.mDatas.contains(item)) {
            this.mDatas.remove(item);
        }
        notifyDataSetChanged();
    }
}

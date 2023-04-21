package cn.com.bioeasy.app.widgets.recyclerview.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.app.widgets.recyclerview.utils.WrapperUtils;

public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    /* access modifiers changed from: private */
    public SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    /* access modifiers changed from: private */
    public SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter mInnerAdapter;
    private RecyclerView.Adapter mNotifyAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.mHeaderViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), this.mHeaderViews.get(viewType));
        }
        if (this.mFootViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), this.mFootViews.get(viewType));
        }
        return this.mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return this.mHeaderViews.keyAt(position);
        }
        if (isFooterViewPos(position)) {
            return this.mFootViews.keyAt((position - getHeadersCount()) - getRealItemCount());
        }
        return this.mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return this.mInnerAdapter.getItemCount();
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isHeaderViewPos(position) && !isFooterViewPos(position)) {
            this.mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
        }
    }

    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mNotifyAdapter = recyclerView.getAdapter();
        WrapperUtils.onAttachedToRecyclerView(this.mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = HeaderAndFooterWrapper.this.getItemViewType(position);
                if (HeaderAndFooterWrapper.this.mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (HeaderAndFooterWrapper.this.mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        this.mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view) {
        if (findHeaderKeyByView(view) == -1) {
            this.mHeaderViews.put(this.mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
            if (this.mNotifyAdapter != null) {
                this.mNotifyAdapter.notifyDataSetChanged();
            }
            if (this.mInnerAdapter instanceof MultiItemTypeAdapter) {
                ((MultiItemTypeAdapter) this.mInnerAdapter).offset++;
            }
        }
    }

    public void addFootView(View view) {
        this.mFootViews.put(this.mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return this.mHeaderViews.size();
    }

    public int getFootersCount() {
        return this.mFootViews.size();
    }

    public void deleteHeaderView(View view) {
        int key = findHeaderKeyByView(view);
        if (key != -1) {
            this.mHeaderViews.remove(key);
            if (this.mInnerAdapter instanceof MultiItemTypeAdapter) {
                MultiItemTypeAdapter multiItemTypeAdapter = (MultiItemTypeAdapter) this.mInnerAdapter;
                multiItemTypeAdapter.offset--;
            }
            if (this.mNotifyAdapter != null) {
                this.mNotifyAdapter.notifyDataSetChanged();
            }
        }
    }

    public int findHeaderKeyByView(View view) {
        for (int i = 0; i < this.mHeaderViews.size(); i++) {
            int key = this.mHeaderViews.keyAt(i);
            if (this.mHeaderViews.get(key) == view) {
                return key;
            }
        }
        return -1;
    }
}

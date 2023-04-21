package cn.com.bioeasy.app.widgets.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.app.widgets.recyclerview.utils.WrapperUtils;

public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_EMPTY = 2147483646;
    private int mEmptyLayoutId;
    private View mEmptyView;
    private RecyclerView.Adapter mInnerAdapter;

    public EmptyWrapper(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    /* access modifiers changed from: private */
    public boolean isEmpty() {
        return !(this.mEmptyView == null && this.mEmptyLayoutId == 0) && this.mInnerAdapter.getItemCount() == 0;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!isEmpty()) {
            return this.mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        if (this.mEmptyView != null) {
            return ViewHolder.createViewHolder(parent.getContext(), this.mEmptyView);
        }
        return ViewHolder.createViewHolder(parent.getContext(), parent, this.mEmptyLayoutId);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(this.mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (EmptyWrapper.this.isEmpty()) {
                    return gridLayoutManager.getSpanCount();
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
        if (isEmpty()) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return this.mInnerAdapter.getItemViewType(position);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isEmpty()) {
            this.mInnerAdapter.onBindViewHolder(holder, position);
        }
    }

    public int getItemCount() {
        if (isEmpty()) {
            return 1;
        }
        return this.mInnerAdapter.getItemCount();
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        this.mEmptyLayoutId = layoutId;
    }
}

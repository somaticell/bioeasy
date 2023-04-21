package cn.com.bioeasy.app.widgets.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.app.widgets.recyclerview.utils.WrapperUtils;

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = 2147483645;
    private RecyclerView.Adapter mInnerAdapter;
    private int mLoadMoreLayoutId;
    private View mLoadMoreView;
    private OnLoadMoreListener mOnLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return (this.mLoadMoreView == null && this.mLoadMoreLayoutId == 0) ? false : true;
    }

    /* access modifiers changed from: private */
    public boolean isShowLoadMore(int position) {
        return hasLoadMore() && position >= this.mInnerAdapter.getItemCount();
    }

    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return this.mInnerAdapter.getItemViewType(position);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 2147483645) {
            return this.mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        if (this.mLoadMoreView != null) {
            return ViewHolder.createViewHolder(parent.getContext(), this.mLoadMoreView);
        }
        return ViewHolder.createViewHolder(parent.getContext(), parent, this.mLoadMoreLayoutId);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isShowLoadMore(position)) {
            this.mInnerAdapter.onBindViewHolder(holder, position);
        } else if (this.mOnLoadMoreListener != null) {
            this.mOnLoadMoreListener.onLoadMoreRequested();
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(this.mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (LoadMoreWrapper.this.isShowLoadMore(position)) {
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
        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && (lp instanceof StaggeredGridLayoutManager.LayoutParams)) {
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
        }
    }

    public int getItemCount() {
        return (hasLoadMore() ? 1 : 0) + this.mInnerAdapter.getItemCount();
    }

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            this.mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        this.mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        this.mLoadMoreLayoutId = layoutId;
        return this;
    }
}

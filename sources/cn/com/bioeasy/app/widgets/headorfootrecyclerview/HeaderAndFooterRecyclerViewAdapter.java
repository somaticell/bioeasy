package cn.com.bioeasy.app.widgets.headorfootrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class HeaderAndFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER_VIEW = -2147483647;
    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        public void onChanged() {
            super.onChanged();
            HeaderAndFooterRecyclerViewAdapter.this.notifyDataSetChanged();
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            HeaderAndFooterRecyclerViewAdapter.this.notifyItemRangeChanged(HeaderAndFooterRecyclerViewAdapter.this.getHeaderViewsCount() + positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            HeaderAndFooterRecyclerViewAdapter.this.notifyItemRangeInserted(HeaderAndFooterRecyclerViewAdapter.this.getHeaderViewsCount() + positionStart, itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            HeaderAndFooterRecyclerViewAdapter.this.notifyItemRangeRemoved(HeaderAndFooterRecyclerViewAdapter.this.getHeaderViewsCount() + positionStart, itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = HeaderAndFooterRecyclerViewAdapter.this.getHeaderViewsCount();
            HeaderAndFooterRecyclerViewAdapter.this.notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    public HeaderAndFooterRecyclerViewAdapter() {
    }

    public HeaderAndFooterRecyclerViewAdapter(RecyclerView.Adapter innerAdapter) {
        setAdapter(innerAdapter);
    }

    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        if (adapter == null || (adapter instanceof RecyclerView.Adapter)) {
            if (this.mInnerAdapter != null) {
                notifyItemRangeRemoved(getHeaderViewsCount(), this.mInnerAdapter.getItemCount());
                this.mInnerAdapter.unregisterAdapterDataObserver(this.mDataObserver);
            }
            this.mInnerAdapter = adapter;
            this.mInnerAdapter.registerAdapterDataObserver(this.mDataObserver);
            notifyItemRangeInserted(getHeaderViewsCount(), this.mInnerAdapter.getItemCount());
            return;
        }
        throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return this.mInnerAdapter;
    }

    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        this.mHeaderViews.add(header);
        notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        this.mFooterViews.add(footer);
        notifyDataSetChanged();
    }

    public View getFooterView() {
        if (getFooterViewsCount() > 0) {
            return this.mFooterViews.get(0);
        }
        return null;
    }

    public View getHeaderView() {
        if (getHeaderViewsCount() > 0) {
            return this.mHeaderViews.get(0);
        }
        return null;
    }

    public void removeHeaderView(View view) {
        this.mHeaderViews.remove(view);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        this.mFooterViews.remove(view);
        notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return this.mFooterViews.size();
    }

    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position == 0;
    }

    public boolean isFooter(int position) {
        return getFooterViewsCount() > 0 && position == getItemCount() + -1;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < Integer.MIN_VALUE + getHeaderViewsCount()) {
            return new ViewHolder(this.mHeaderViews.get(viewType - Integer.MIN_VALUE));
        }
        if (viewType < TYPE_FOOTER_VIEW || viewType >= 1073741823) {
            return this.mInnerAdapter.onCreateViewHolder(parent, viewType - 1073741823);
        }
        return new ViewHolder(this.mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();
        if (position < headerViewsCountCount || position >= this.mInnerAdapter.getItemCount() + headerViewsCountCount) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                return;
            }
            return;
        }
        this.mInnerAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
    }

    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + this.mInnerAdapter.getItemCount();
    }

    public int getItemViewType(int position) {
        int innerCount = this.mInnerAdapter.getItemCount();
        int headerViewsCountCount = getHeaderViewsCount();
        if (position < headerViewsCountCount) {
            return Integer.MIN_VALUE + position;
        }
        if (headerViewsCountCount > position || position >= headerViewsCountCount + innerCount) {
            return ((TYPE_FOOTER_VIEW + position) - headerViewsCountCount) - innerCount;
        }
        int innerItemViewType = this.mInnerAdapter.getItemViewType(position - headerViewsCountCount);
        if (innerItemViewType < 1073741823) {
            return innerItemViewType + 1073741823;
        }
        throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

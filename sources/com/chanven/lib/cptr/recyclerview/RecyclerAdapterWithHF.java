package com.chanven.lib.cptr.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterWithHF extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_FOOTER = 7899;
    public static final int TYPE_HEADER = 7898;
    public static final int TYPE_MANAGER_GRID = 2;
    public static final int TYPE_MANAGER_LINEAR = 1;
    public static final int TYPE_MANAGER_OTHER = 0;
    public static final int TYPE_MANAGER_STAGGERED_GRID = 3;
    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        public void onChanged() {
            RecyclerAdapterWithHF.this.notifyDataSetChanged();
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            RecyclerAdapterWithHF.this.notifyItemRangeChanged(RecyclerAdapterWithHF.this.getHeadSize() + positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            RecyclerAdapterWithHF.this.notifyItemRangeInserted(RecyclerAdapterWithHF.this.getHeadSize() + positionStart, itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            RecyclerAdapterWithHF.this.notifyItemRangeRemoved(RecyclerAdapterWithHF.this.getHeadSize() + positionStart, itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            RecyclerAdapterWithHF.this.notifyItemMoved(RecyclerAdapterWithHF.this.getHeadSize() + fromPosition, RecyclerAdapterWithHF.this.getHeadSize() + toPosition);
        }
    };
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private List<View> mFooters = new ArrayList();
    private List<View> mHeaders = new ArrayList();
    private int mManagerType;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;
    /* access modifiers changed from: private */
    public OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(RecyclerAdapterWithHF recyclerAdapterWithHF, RecyclerView.ViewHolder viewHolder, int i);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(RecyclerAdapterWithHF recyclerAdapterWithHF, RecyclerView.ViewHolder viewHolder, int i);
    }

    public int getHeadSize() {
        return this.mHeaders.size();
    }

    public int getFootSize() {
        return this.mFooters.size();
    }

    public int getManagerType() {
        return this.mManagerType;
    }

    public void notifyDataSetChangedHF() {
        notifyDataSetChanged();
    }

    public void notifyItemChangedHF(int position) {
        notifyItemChanged(getRealPosition(position));
    }

    public void notifyItemMovedHF(int fromPosition, int toPosition) {
        notifyItemMovedHF(getRealPosition(fromPosition), getRealPosition(toPosition));
    }

    public void notifyItemRangeChangedHF(int positionStart, int itemCount) {
        notifyItemRangeChanged(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRangeRemovedHF(int positionStart, int itemCount) {
        notifyItemRangeRemoved(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRemovedHF(int position) {
        notifyItemRemoved(getRealPosition(position));
    }

    public void notifyItemInsertedHF(int position) {
        notifyItemInserted(getRealPosition(position));
    }

    public void notifyItemRangeInsertedHF(int positionStart, int itemCount) {
        notifyItemRangeInserted(getRealPosition(positionStart), itemCount);
    }

    public final long getItemId(int position) {
        return getItemIdHF(getRealPosition(position));
    }

    public long getItemIdHF(int position) {
        return this.mAdapter.getItemId(position);
    }

    public RecyclerView.ViewHolder onCreateViewHolderHF(ViewGroup viewGroup, int type) {
        return this.mAdapter.onCreateViewHolder(viewGroup, type);
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        if (type != 7898 && type != 7899) {
            return onCreateViewHolderHF(viewGroup, type);
        }
        FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        return new HeaderFooterViewHolder(frameLayout);
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (isHeader(position)) {
            prepareHeaderFooter((HeaderFooterViewHolder) vh, this.mHeaders.get(position));
        } else if (isFooter(position)) {
            prepareHeaderFooter((HeaderFooterViewHolder) vh, this.mFooters.get((position - getItemCountHF()) - this.mHeaders.size()));
        } else {
            vh.itemView.setOnClickListener(new MyOnClickListener(vh));
            vh.itemView.setOnLongClickListener(new MyOnLongClickListener(vh));
            onBindViewHolderHF(vh, getRealPosition(position));
        }
    }

    public int getRealPosition(int position) {
        return position - this.mHeaders.size();
    }

    public void onBindViewHolderHF(RecyclerView.ViewHolder vh, int position) {
        this.mAdapter.onBindViewHolder(vh, position);
    }

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {
        if (this.mManagerType == 3) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(-1, -1);
            layoutParams.setFullSpan(true);
            vh.itemView.setLayoutParams(layoutParams);
        }
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        vh.base.removeAllViews();
        vh.base.addView(view);
    }

    private boolean isHeader(int position) {
        return position < this.mHeaders.size();
    }

    private boolean isFooter(int position) {
        return position >= this.mHeaders.size() + getItemCountHF();
    }

    public final int getItemCount() {
        return this.mHeaders.size() + getItemCountHF() + this.mFooters.size();
    }

    public int getItemCountHF() {
        return this.mAdapter.getItemCount();
    }

    public final int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        }
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int type = getItemViewTypeHF(getRealPosition(position));
        if (type != 7898 && type != 7899) {
            return type;
        }
        throw new IllegalArgumentException("Item type cannot equal 7898 or 7899");
    }

    public int getItemViewTypeHF(int position) {
        return this.mAdapter.getItemViewType(position);
    }

    public void addHeader(View header) {
        if (!this.mHeaders.contains(header)) {
            this.mHeaders.add(header);
            notifyItemInserted(this.mHeaders.size() - 1);
        }
    }

    public void removeHeader(View header) {
        if (this.mHeaders.contains(header)) {
            notifyItemRemoved(this.mHeaders.indexOf(header));
            this.mHeaders.remove(header);
        }
    }

    public void addFooter(View footer) {
        if (!this.mFooters.contains(footer)) {
            this.mFooters.add(footer);
            notifyItemInserted(((this.mHeaders.size() + getItemCountHF()) + this.mFooters.size()) - 1);
        }
    }

    public void removeFooter(View footer) {
        if (this.mFooters.contains(footer)) {
            notifyItemRemoved(this.mHeaders.size() + getItemCountHF() + this.mFooters.indexOf(footer));
            this.mFooters.remove(footer);
        }
    }

    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            this.base = (FrameLayout) itemView;
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
        Log.d("eeee", "setOnItemClickListener " + this.onItemClickListener);
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return this.onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener2) {
        this.onItemLongClickListener = onItemLongClickListener2;
    }

    private class MyOnClickListener implements View.OnClickListener {
        private RecyclerView.ViewHolder vh;

        public MyOnClickListener(RecyclerView.ViewHolder vh2) {
            this.vh = vh2;
        }

        public void onClick(View v) {
            int position = RecyclerAdapterWithHF.this.getRealPosition(this.vh.getLayoutPosition());
            if (RecyclerAdapterWithHF.this.onItemClickListener != null) {
                RecyclerAdapterWithHF.this.onItemClickListener.onItemClick(RecyclerAdapterWithHF.this, this.vh, position);
            }
            RecyclerAdapterWithHF.this.onItemClick(this.vh, position);
        }
    }

    private class MyOnLongClickListener implements View.OnLongClickListener {
        private RecyclerView.ViewHolder vh;

        public MyOnLongClickListener(RecyclerView.ViewHolder vh2) {
            this.vh = vh2;
        }

        public boolean onLongClick(View v) {
            int position = RecyclerAdapterWithHF.this.getRealPosition(this.vh.getLayoutPosition());
            if (RecyclerAdapterWithHF.this.onItemLongClickListener != null) {
                RecyclerAdapterWithHF.this.onItemLongClickListener.onItemLongClick(RecyclerAdapterWithHF.this, this.vh, position);
            }
            RecyclerAdapterWithHF.this.onItemLongClick(this.vh, position);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
    }

    /* access modifiers changed from: protected */
    public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
    }

    public RecyclerAdapterWithHF(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
        adapter.registerAdapterDataObserver(this.adapterDataObserver);
    }
}

package com.chanven.lib.cptr.loadmore;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

public class RecyclerViewHandler implements LoadMoreHandler {
    /* access modifiers changed from: private */
    public View mFooter;
    /* access modifiers changed from: private */
    public RecyclerAdapterWithHF mRecyclerAdapter;

    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, View.OnClickListener onClickLoadMoreListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        this.mRecyclerAdapter = (RecyclerAdapterWithHF) recyclerView.getAdapter();
        if (loadMoreView == null) {
            return false;
        }
        final Context context = recyclerView.getContext().getApplicationContext();
        loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {
            public View addFootView(int layoutId) {
                View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                View unused = RecyclerViewHandler.this.mFooter = view;
                return addFootView(view);
            }

            public View addFootView(View view) {
                RecyclerViewHandler.this.mRecyclerAdapter.addFooter(view);
                return view;
            }
        }, onClickLoadMoreListener);
        return true;
    }

    public void addFooter() {
        if (this.mRecyclerAdapter.getFootSize() <= 0 && this.mFooter != null) {
            this.mRecyclerAdapter.addFooter(this.mFooter);
        }
    }

    public void removeFooter() {
        if (this.mRecyclerAdapter.getFootSize() > 0 && this.mFooter != null) {
            this.mRecyclerAdapter.removeFooter(this.mFooter);
        }
    }

    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        ((RecyclerView) contentView).addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
    }

    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener2) {
            this.onScrollBottomListener = onScrollBottomListener2;
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 0 && isScollBottom(recyclerView) && this.onScrollBottomListener != null) {
                this.onScrollBottomListener.onScorllBootom();
            }
        }

        private boolean isScollBottom(RecyclerView recyclerView) {
            return !isCanScollVertically(recyclerView);
        }

        private boolean isCanScollVertically(RecyclerView recyclerView) {
            if (Build.VERSION.SDK_INT >= 14) {
                return ViewCompat.canScrollVertically(recyclerView, 1);
            }
            if (ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight()) {
                return true;
            }
            return false;
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    }
}

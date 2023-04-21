package com.chanven.lib.cptr.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;

public class GridViewHandler implements LoadMoreHandler {
    /* access modifiers changed from: private */
    public View mFooter;
    /* access modifiers changed from: private */
    public GridViewWithHeaderAndFooter mGridView;

    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, View.OnClickListener onClickLoadMoreListener) {
        this.mGridView = (GridViewWithHeaderAndFooter) contentView;
        ListAdapter adapter = this.mGridView.getAdapter();
        boolean hasInit = false;
        if (loadMoreView != null) {
            final Context context = this.mGridView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, GridViewHandler.this.mGridView, false);
                    View unused = GridViewHandler.this.mFooter = view;
                    return addFootView(view);
                }

                public View addFootView(View view) {
                    GridViewHandler.this.mGridView.addFooterView(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
            if (adapter != null) {
                this.mGridView.setAdapter(adapter);
            }
        }
        return hasInit;
    }

    public void addFooter() {
        if (this.mGridView.getFooterViewCount() <= 0 && this.mFooter != null) {
            this.mGridView.addFooterView(this.mFooter);
        }
    }

    public void removeFooter() {
        if (this.mGridView.getFooterViewCount() > 0 && this.mFooter != null) {
            this.mGridView.removeFooterView(this.mFooter);
        }
    }

    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) contentView;
        gridView.setOnScrollListener(new GridViewOnScrollListener(onScrollBottomListener));
        gridView.setOnItemSelectedListener(new GridViewOnItemSelectedListener(onScrollBottomListener));
    }

    private class GridViewOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private OnScrollBottomListener onScrollBottomListener;

        public GridViewOnItemSelectedListener(OnScrollBottomListener onScrollBottomListener2) {
            this.onScrollBottomListener = onScrollBottomListener2;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (adapterView.getLastVisiblePosition() + 1 == adapterView.getCount() && this.onScrollBottomListener != null) {
                this.onScrollBottomListener.onScorllBootom();
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private static class GridViewOnScrollListener implements AbsListView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public GridViewOnScrollListener(OnScrollBottomListener onScrollBottomListener2) {
            this.onScrollBottomListener = onScrollBottomListener2;
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() + 1 == view.getCount() && this.onScrollBottomListener != null) {
                this.onScrollBottomListener.onScorllBootom();
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }
}

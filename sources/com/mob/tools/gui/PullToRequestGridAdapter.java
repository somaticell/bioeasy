package com.mob.tools.gui;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

public abstract class PullToRequestGridAdapter extends PullToRequestBaseListAdapter {
    /* access modifiers changed from: private */
    public PullToRequestBaseAdapter adapter;
    /* access modifiers changed from: private */
    public boolean fling;
    /* access modifiers changed from: private */
    public ScrollableGridView gridView = onNewGridView(getContext());
    /* access modifiers changed from: private */
    public OnListStopScrollListener osListener;
    /* access modifiers changed from: private */
    public boolean pullUpReady;

    public PullToRequestGridAdapter(PullToRequestView view) {
        super(view);
        this.gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int firstVisibleItem;
            private int visibleItemCount;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean unused = PullToRequestGridAdapter.this.fling = scrollState == 2;
                if (scrollState != 0) {
                    return;
                }
                if (PullToRequestGridAdapter.this.osListener != null) {
                    PullToRequestGridAdapter.this.osListener.onListStopScrolling(this.firstVisibleItem, this.visibleItemCount);
                } else if (PullToRequestGridAdapter.this.adapter != null) {
                    PullToRequestGridAdapter.this.adapter.notifyDataSetChanged();
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem2, int visibleItemCount2, int totalItemCount) {
                this.firstVisibleItem = firstVisibleItem2;
                this.visibleItemCount = visibleItemCount2;
                View v = view.getChildAt(visibleItemCount2 - 1);
                boolean unused = PullToRequestGridAdapter.this.pullUpReady = firstVisibleItem2 + visibleItemCount2 == totalItemCount && v != null && v.getBottom() <= view.getBottom();
                PullToRequestGridAdapter.this.onScroll(PullToRequestGridAdapter.this.gridView, firstVisibleItem2, visibleItemCount2, totalItemCount);
            }
        });
        this.adapter = new PullToRequestBaseAdapter(this);
        this.gridView.setAdapter(this.adapter);
    }

    /* access modifiers changed from: protected */
    public ScrollableGridView onNewGridView(Context context) {
        return new ScrollableGridView(context);
    }

    public Scrollable getBodyView() {
        return this.gridView;
    }

    public boolean isPullDownReady() {
        return this.gridView.isReadyToPull();
    }

    public boolean isPullUpReady() {
        return this.pullUpReady;
    }

    public GridView getGridView() {
        return this.gridView;
    }

    public boolean isFling() {
        return this.fling;
    }

    public void onScroll(Scrollable scrollable, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.adapter.notifyDataSetChanged();
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.gridView.setHorizontalSpacing(horizontalSpacing);
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.gridView.setVerticalSpacing(verticalSpacing);
    }

    public void setNumColumns(int numColumns) {
        this.gridView.setNumColumns(numColumns);
    }

    public void setColumnWidth(int columnWidth) {
        this.gridView.setColumnWidth(columnWidth);
    }

    public void setStretchMode(int stretchMode) {
        this.gridView.setStretchMode(stretchMode);
    }
}

package com.mob.tools.gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public abstract class PullToRequestListAdapter extends PullToRequestBaseListAdapter {
    /* access modifiers changed from: private */
    public PullToRequestBaseAdapter adapter;
    /* access modifiers changed from: private */
    public boolean fling;
    /* access modifiers changed from: private */
    public ScrollableListView listView = onNewListView(getContext());
    /* access modifiers changed from: private */
    public OnListStopScrollListener osListener;
    /* access modifiers changed from: private */
    public boolean pullUpReady;

    public PullToRequestListAdapter(PullToRequestView view) {
        super(view);
        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int firstVisibleItem;
            private int visibleItemCount;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean unused = PullToRequestListAdapter.this.fling = scrollState == 2;
                if (scrollState != 0) {
                    return;
                }
                if (PullToRequestListAdapter.this.osListener != null) {
                    PullToRequestListAdapter.this.osListener.onListStopScrolling(this.firstVisibleItem, this.visibleItemCount);
                } else if (PullToRequestListAdapter.this.adapter != null) {
                    PullToRequestListAdapter.this.adapter.notifyDataSetChanged();
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem2, int visibleItemCount2, int totalItemCount) {
                this.firstVisibleItem = firstVisibleItem2;
                this.visibleItemCount = visibleItemCount2;
                View v = view.getChildAt(visibleItemCount2 - 1);
                boolean unused = PullToRequestListAdapter.this.pullUpReady = firstVisibleItem2 + visibleItemCount2 == totalItemCount && v != null && v.getBottom() <= view.getBottom();
                PullToRequestListAdapter.this.onScroll(PullToRequestListAdapter.this.listView, firstVisibleItem2, visibleItemCount2, totalItemCount);
            }
        });
        this.adapter = new PullToRequestBaseAdapter(this);
        this.listView.setAdapter(this.adapter);
    }

    /* access modifiers changed from: protected */
    public ScrollableListView onNewListView(Context context) {
        return new ScrollableListView(context);
    }

    public Scrollable getBodyView() {
        return this.listView;
    }

    public ListView getListView() {
        return this.listView;
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

    public void setDivider(Drawable divider) {
        this.listView.setDivider(divider);
    }

    public void setDividerHeight(int height) {
        this.listView.setDividerHeight(height);
    }

    public boolean isPullDownReady() {
        return this.listView.isReadyToPull();
    }

    public boolean isPullUpReady() {
        return this.pullUpReady;
    }
}

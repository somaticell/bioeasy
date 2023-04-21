package com.chanven.lib.cptr.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;

public class ListViewHandler implements LoadMoreHandler {
    /* access modifiers changed from: private */
    public View mFooter;
    private ListView mListView;

    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, View.OnClickListener onClickLoadMoreListener) {
        final ListView listView = (ListView) contentView;
        this.mListView = listView;
        if (loadMoreView == null) {
            return false;
        }
        final Context context = listView.getContext().getApplicationContext();
        loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {
            public View addFootView(int layoutId) {
                View view = LayoutInflater.from(context).inflate(layoutId, listView, false);
                View unused = ListViewHandler.this.mFooter = view;
                return addFootView(view);
            }

            public View addFootView(View view) {
                listView.addFooterView(view);
                return view;
            }
        }, onClickLoadMoreListener);
        return true;
    }

    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        ListView listView = (ListView) contentView;
        listView.setOnScrollListener(new ListViewOnScrollListener(onScrollBottomListener));
        listView.setOnItemSelectedListener(new ListViewOnItemSelectedListener(onScrollBottomListener));
    }

    public void removeFooter() {
        if (this.mListView.getFooterViewsCount() > 0 && this.mFooter != null) {
            this.mListView.removeFooterView(this.mFooter);
        }
    }

    public void addFooter() {
        if (this.mListView.getFooterViewsCount() <= 0 && this.mFooter != null) {
            this.mListView.addFooterView(this.mFooter);
        }
    }

    private class ListViewOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private OnScrollBottomListener onScrollBottomListener;

        public ListViewOnItemSelectedListener(OnScrollBottomListener onScrollBottomListener2) {
            this.onScrollBottomListener = onScrollBottomListener2;
        }

        public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
            if (listView.getLastVisiblePosition() + 1 == listView.getCount() && this.onScrollBottomListener != null) {
                this.onScrollBottomListener.onScorllBootom();
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private static class ListViewOnScrollListener implements AbsListView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public ListViewOnScrollListener(OnScrollBottomListener onScrollBottomListener2) {
            this.onScrollBottomListener = onScrollBottomListener2;
        }

        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            if (scrollState == 0 && listView.getLastVisiblePosition() + 1 == listView.getCount() && this.onScrollBottomListener != null) {
                this.onScrollBottomListener.onScorllBootom();
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }
}

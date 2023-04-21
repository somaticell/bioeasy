package com.chanven.lib.cptr.loadmore;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;
import java.lang.reflect.Field;

public class SwipeRefreshHelper {
    private boolean hasInitLoadMoreView = false;
    /* access modifiers changed from: private */
    public boolean isAutoLoadMoreEnable = true;
    /* access modifiers changed from: private */
    public boolean isLoadMoreEnable = false;
    private boolean isLoadingMore = false;
    private ILoadMoreViewFactory loadMoreViewFactory = new DefaultLoadMoreViewFooter();
    private View mContentView;
    private LoadMoreHandler mLoadMoreHandler;
    private ILoadMoreViewFactory.ILoadMoreView mLoadMoreView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        public void onRefresh() {
            if (SwipeRefreshHelper.this.mOnSwipeRefreshListener != null) {
                SwipeRefreshHelper.this.mOnSwipeRefreshListener.onfresh();
            }
        }
    };
    /* access modifiers changed from: private */
    public OnSwipeRefreshListener mOnSwipeRefreshListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View.OnClickListener onClickLoadMoreListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (SwipeRefreshHelper.this.isLoadMoreEnable && !SwipeRefreshHelper.this.isLoadingMore()) {
                SwipeRefreshHelper.this.loadMore();
            }
        }
    };
    private OnScrollBottomListener onScrollBottomListener = new OnScrollBottomListener() {
        public void onScorllBootom() {
            if (SwipeRefreshHelper.this.isAutoLoadMoreEnable && SwipeRefreshHelper.this.isLoadMoreEnable && !SwipeRefreshHelper.this.isLoadingMore()) {
                SwipeRefreshHelper.this.loadMore();
            }
        }
    };

    public interface OnSwipeRefreshListener {
        void onfresh();
    }

    public void autoRefresh() {
        if (this.mOnSwipeRefreshListener != null) {
            this.mSwipeRefreshLayout.setRefreshing(true);
            this.mOnSwipeRefreshListener.onfresh();
        }
    }

    public SwipeRefreshHelper(SwipeRefreshLayout refreshLayout) {
        this.mSwipeRefreshLayout = refreshLayout;
        init();
    }

    private void init() {
        if (this.mSwipeRefreshLayout.getChildCount() <= 0) {
            throw new RuntimeException("SwipRefreshLayout has no child view");
        }
        try {
            Field field = this.mSwipeRefreshLayout.getClass().getDeclaredField("mTarget");
            field.setAccessible(true);
            this.mContentView = (View) field.get(this.mSwipeRefreshLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnSwipeRefreshListener(OnSwipeRefreshListener onSwipeRefreshListener) {
        this.mOnSwipeRefreshListener = onSwipeRefreshListener;
        this.mSwipeRefreshLayout.setOnRefreshListener(this.mOnRefreshListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public void refreshComplete() {
        this.mSwipeRefreshLayout.setRefreshing(false);
    }

    public void setFooterView(ILoadMoreViewFactory factory) {
        if (factory == null) {
            return;
        }
        if (this.loadMoreViewFactory == null || this.loadMoreViewFactory != factory) {
            this.loadMoreViewFactory = factory;
            if (this.hasInitLoadMoreView) {
                this.mLoadMoreHandler.removeFooter();
                this.mLoadMoreView = this.loadMoreViewFactory.madeLoadMoreView();
                this.hasInitLoadMoreView = this.mLoadMoreHandler.handleSetAdapter(this.mContentView, this.mLoadMoreView, this.onClickLoadMoreListener);
                if (!this.isLoadMoreEnable) {
                    this.mLoadMoreHandler.removeFooter();
                }
            }
        }
    }

    public void setLoadMoreEnable(boolean enable) {
        if (this.isLoadMoreEnable != enable) {
            this.isLoadMoreEnable = enable;
            if (!this.hasInitLoadMoreView && this.isLoadMoreEnable) {
                this.mLoadMoreView = this.loadMoreViewFactory.madeLoadMoreView();
                if (this.mLoadMoreHandler == null) {
                    if (this.mContentView instanceof GridView) {
                        this.mLoadMoreHandler = new GridViewHandler();
                    } else if (this.mContentView instanceof AbsListView) {
                        this.mLoadMoreHandler = new ListViewHandler();
                    } else if (this.mContentView instanceof RecyclerView) {
                        this.mLoadMoreHandler = new RecyclerViewHandler();
                    }
                }
                if (this.mLoadMoreHandler == null) {
                    throw new IllegalStateException("unSupported contentView !");
                }
                this.hasInitLoadMoreView = this.mLoadMoreHandler.handleSetAdapter(this.mContentView, this.mLoadMoreView, this.onClickLoadMoreListener);
                this.mLoadMoreHandler.setOnScrollBottomListener(this.mContentView, this.onScrollBottomListener);
            } else if (!this.hasInitLoadMoreView) {
            } else {
                if (this.isLoadMoreEnable) {
                    this.mLoadMoreHandler.addFooter();
                } else {
                    this.mLoadMoreHandler.removeFooter();
                }
            }
        }
    }

    public boolean isLoadMoreEnable() {
        return this.isLoadMoreEnable;
    }

    public void setAutoLoadMoreEnable(boolean isAutoLoadMoreEnable2) {
        this.isAutoLoadMoreEnable = isAutoLoadMoreEnable2;
    }

    /* access modifiers changed from: private */
    public void loadMore() {
        this.isLoadingMore = true;
        this.mLoadMoreView.showLoading();
        if (this.mOnLoadMoreListener != null) {
            this.mOnLoadMoreListener.loadMore();
        }
    }

    public void loadMoreComplete(boolean hasMore) {
        this.isLoadingMore = false;
        if (hasMore) {
            this.mLoadMoreView.showNormal();
        } else {
            setNoMoreData();
        }
    }

    public void setNoMoreData() {
        this.isLoadingMore = false;
        this.mLoadMoreView.showNomore();
    }

    public boolean isLoadingMore() {
        return this.isLoadingMore;
    }
}

package cn.com.bioeasy.app.widgets.headorfootrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewUtils {
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
            if (headerAndFooterAdapter.getHeaderViewsCount() == 0) {
                headerAndFooterAdapter.addHeaderView(view);
            }
        }
    }

    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
            if (headerAndFooterAdapter.getFooterViewsCount() == 0) {
                headerAndFooterAdapter.addFooterView(view);
            }
        }
    }

    public static void removeFooterView(RecyclerView recyclerView) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) && ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
            ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).removeFooterView(((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView());
        }
    }

    public static void removeHeaderView(RecyclerView recyclerView) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) && ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount() > 0) {
            ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).removeFooterView(((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderView());
        }
    }

    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        int headerViewCounter;
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) || (headerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount()) <= 0) {
            return holder.getLayoutPosition();
        }
        return holder.getLayoutPosition() - headerViewCounter;
    }

    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        int headerViewCounter;
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) || (headerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount()) <= 0) {
            return holder.getAdapterPosition();
        }
        return holder.getAdapterPosition() - headerViewCounter;
    }
}

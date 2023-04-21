package cn.com.bioeasy.app.widgets.headorfootrecyclerview;

import android.support.v7.widget.GridLayoutManager;

public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(HeaderAndFooterRecyclerViewAdapter adapter2, int spanSize) {
        this.adapter = adapter2;
        this.mSpanSize = spanSize;
    }

    public int getSpanSize(int position) {
        if (this.adapter.isHeader(position) || this.adapter.isFooter(position)) {
            return this.mSpanSize;
        }
        return 1;
    }
}

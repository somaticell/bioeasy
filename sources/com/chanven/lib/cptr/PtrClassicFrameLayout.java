package com.chanven.lib.cptr;

import android.content.Context;
import android.util.AttributeSet;
import com.chanven.lib.cptr.loadmore.DefaultLoadMoreViewFooter;

public class PtrClassicFrameLayout extends PtrFrameLayout {
    private PtrClassicDefaultHeader mPtrClassicHeader;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        this.mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(this.mPtrClassicHeader);
        addPtrUIHandler(this.mPtrClassicHeader);
        setFooterView(new DefaultLoadMoreViewFooter());
    }

    public PtrClassicDefaultHeader getHeader() {
        return this.mPtrClassicHeader;
    }

    public void setLastUpdateTimeKey(String key) {
        if (this.mPtrClassicHeader != null) {
            this.mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    public void setLastUpdateTimeRelateObject(Object object) {
        if (this.mPtrClassicHeader != null) {
            this.mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}

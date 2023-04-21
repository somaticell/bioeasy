package cn.com.bioeasy.app.widgets.recyclerview.section;

import android.content.Context;
import android.view.View;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.app.widgets.recyclerview.section.SectionRVAdapter;

public abstract class Section {
    private Integer failedResourceId;
    Integer footerResourceId;
    boolean hasFooter;
    boolean hasHeader;
    Integer headerResourceId;
    int itemResourceId;
    private Integer loadingResourceId;
    private State state;
    boolean visible;

    public enum State {
        LOADING,
        LOADED,
        FAILED
    }

    public abstract int getContentItemsTotal();

    public abstract ViewHolder getItemViewHolder(View view, int i);

    public abstract void onBindItemViewHolder(ViewHolder viewHolder, int i);

    Section() {
        this.state = State.LOADED;
        this.visible = true;
        this.hasHeader = false;
        this.hasFooter = false;
    }

    public Section(int itemResourceId2, int loadingResourceId2, int failedResourceId2) {
        this.state = State.LOADED;
        this.visible = true;
        this.hasHeader = false;
        this.hasFooter = false;
        this.itemResourceId = itemResourceId2;
        this.loadingResourceId = Integer.valueOf(loadingResourceId2);
        this.failedResourceId = Integer.valueOf(failedResourceId2);
    }

    public Section(int headerResourceId2, int itemResourceId2, int loadingResourceId2, int failedResourceId2) {
        this(itemResourceId2, loadingResourceId2, failedResourceId2);
        this.headerResourceId = Integer.valueOf(headerResourceId2);
        this.hasHeader = true;
    }

    public Section(int headerResourceId2, int footerResourceId2, int itemResourceId2, int loadingResourceId2, int failedResourceId2) {
        this(headerResourceId2, itemResourceId2, loadingResourceId2, failedResourceId2);
        this.footerResourceId = Integer.valueOf(footerResourceId2);
        this.hasFooter = true;
    }

    public final void setState(State state2) {
        this.state = state2;
    }

    public final State getState() {
        return this.state;
    }

    public final boolean isVisible() {
        return this.visible;
    }

    public final void setVisible(boolean visible2) {
        this.visible = visible2;
    }

    public final boolean hasHeader() {
        return this.hasHeader;
    }

    public final void setHasHeader(boolean hasHeader2) {
        this.hasHeader = hasHeader2;
    }

    public final boolean hasFooter() {
        return this.hasFooter;
    }

    public final void setHasFooter(boolean hasFooter2) {
        this.hasFooter = hasFooter2;
    }

    public final Integer getHeaderResourceId() {
        return this.headerResourceId;
    }

    public final Integer getFooterResourceId() {
        return this.footerResourceId;
    }

    public final int getItemResourceId() {
        return this.itemResourceId;
    }

    public final Integer getLoadingResourceId() {
        return this.loadingResourceId;
    }

    public final Integer getFailedResourceId() {
        return this.failedResourceId;
    }

    public final void onBindContentViewHolder(ViewHolder holder, int position) {
        switch (this.state) {
            case LOADING:
                onBindLoadingViewHolder(holder);
                return;
            case LOADED:
                onBindItemViewHolder(holder, position);
                return;
            case FAILED:
                onBindFailedViewHolder(holder);
                return;
            default:
                throw new IllegalStateException("Invalid state");
        }
    }

    public final int getSectionItemsTotal() {
        int contentItemsTotal;
        int i;
        int i2 = 1;
        switch (this.state) {
            case LOADING:
                contentItemsTotal = 1;
                break;
            case LOADED:
                contentItemsTotal = getContentItemsTotal();
                break;
            case FAILED:
                contentItemsTotal = 1;
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }
        if (this.hasHeader) {
            i = 1;
        } else {
            i = 0;
        }
        int i3 = i + contentItemsTotal;
        if (!this.hasFooter) {
            i2 = 0;
        }
        return i3 + i2;
    }

    public ViewHolder getHeaderViewHolder(Context context, View view) {
        return new SectionRVAdapter.EmptyViewHolder(context, view);
    }

    public void onBindHeaderViewHolder(ViewHolder holder) {
    }

    public ViewHolder getFooterViewHolder(Context context, View view) {
        return new SectionRVAdapter.EmptyViewHolder(context, view);
    }

    public void onBindFooterViewHolder(ViewHolder holder) {
    }

    public ViewHolder getLoadingViewHolder(Context context, View view) {
        return new SectionRVAdapter.EmptyViewHolder(context, view);
    }

    public void onBindLoadingViewHolder(ViewHolder holder) {
    }

    public ViewHolder getFailedViewHolder(Context context, View view) {
        return new SectionRVAdapter.EmptyViewHolder(context, view);
    }

    public void onBindFailedViewHolder(ViewHolder holder) {
    }
}

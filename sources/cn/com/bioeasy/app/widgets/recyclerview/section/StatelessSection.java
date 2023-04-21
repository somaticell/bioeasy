package cn.com.bioeasy.app.widgets.recyclerview.section;

import android.content.Context;
import android.view.View;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;

public abstract class StatelessSection extends Section {
    public StatelessSection(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    public StatelessSection(int headerResourceId, int itemResourceId) {
        this(itemResourceId);
        this.headerResourceId = Integer.valueOf(headerResourceId);
        this.hasHeader = true;
    }

    public StatelessSection(int headerResourceId, int footerResourceId, int itemResourceId) {
        this(headerResourceId, itemResourceId);
        this.footerResourceId = Integer.valueOf(footerResourceId);
        this.hasFooter = true;
    }

    public final void onBindLoadingViewHolder(ViewHolder holder) {
        super.onBindLoadingViewHolder(holder);
    }

    public final ViewHolder getLoadingViewHolder(Context context, View view) {
        return super.getLoadingViewHolder(context, view);
    }

    public final void onBindFailedViewHolder(ViewHolder holder) {
        super.onBindFailedViewHolder(holder);
    }

    public final ViewHolder getFailedViewHolder(Context context, View view) {
        return super.getFailedViewHolder(context, view);
    }
}

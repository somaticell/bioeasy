package cn.com.bioeasy.app.widgets.abslistview.base;

import cn.com.bioeasy.app.widgets.abslistview.ViewHolder;

public interface ItemViewDelegate<T> {
    void convert(ViewHolder viewHolder, T t, int i);

    int getItemViewLayoutId();

    boolean isForViewType(T t, int i);
}

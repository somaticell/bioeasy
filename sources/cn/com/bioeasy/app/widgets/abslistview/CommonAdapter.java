package cn.com.bioeasy.app.widgets.abslistview;

import android.content.Context;
import cn.com.bioeasy.app.widgets.abslistview.base.ItemViewDelegate;
import java.util.List;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    /* access modifiers changed from: protected */
    public abstract void convert(ViewHolder viewHolder, T t, int i);

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            public int getItemViewLayoutId() {
                return layoutId;
            }

            public boolean isForViewType(T t, int position) {
                return true;
            }

            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }
}

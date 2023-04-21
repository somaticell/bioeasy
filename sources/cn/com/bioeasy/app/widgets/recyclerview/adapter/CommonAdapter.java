package cn.com.bioeasy.app.widgets.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import cn.com.bioeasy.app.widgets.recyclerview.base.ItemViewDelegate;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mLayoutId;

    /* access modifiers changed from: protected */
    public abstract void convert(ViewHolder viewHolder, T t, int i);

    public CommonAdapter(Context context, int layoutId) {
        this(context, new ArrayList(), layoutId);
    }

    public CommonAdapter(Context context, List<T> datas, final int layoutId) {
        super(context, datas);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.mDatas = datas;
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

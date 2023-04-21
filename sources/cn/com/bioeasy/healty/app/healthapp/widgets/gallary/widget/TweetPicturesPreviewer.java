package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter.TweetSelectImageAdapter;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal.SelectOptions;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.SelectImageActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class TweetPicturesPreviewer extends RecyclerView implements TweetSelectImageAdapter.Callback {
    private RequestManager mCurImageLoader;
    private TweetSelectImageAdapter mImageAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public TweetPicturesPreviewer(Context context) {
        super(context);
        init();
    }

    public TweetPicturesPreviewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TweetPicturesPreviewer(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mImageAdapter = new TweetSelectImageAdapter(this, getContext());
        setLayoutManager(new GridLayoutManager(getContext(), 3));
        setAdapter(this.mImageAdapter);
        setOverScrollMode(2);
        this.mItemTouchHelper = new ItemTouchHelper(new TweetPicturesPreviewerItemTouchCallback(this.mImageAdapter));
        this.mItemTouchHelper.attachToRecyclerView(this);
    }

    public void set(String[] paths) {
        this.mImageAdapter.clear();
        for (String path : paths) {
            this.mImageAdapter.add(path);
        }
        this.mImageAdapter.notifyDataSetChanged();
    }

    public void onLoadMoreClick() {
        SelectImageActivity.show(getContext(), new SelectOptions.Builder().setHasCam(true).setSelectCount(9).setSelectedImages(this.mImageAdapter.getPaths()).setCallback(new SelectOptions.Callback() {
            public void doSelected(String[] images) {
                TweetPicturesPreviewer.this.set(images);
            }
        }).build());
    }

    public RequestManager getImgLoader() {
        if (this.mCurImageLoader == null) {
            this.mCurImageLoader = Glide.with(getContext());
        }
        return this.mCurImageLoader;
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        this.mItemTouchHelper.startDrag(viewHolder);
    }

    public void onItemRemoved(int itemSize) {
        this.mImageAdapter.onItemDelete();
    }

    public String[] getPaths() {
        return this.mImageAdapter.getPaths();
    }
}

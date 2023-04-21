package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import cn.com.bioeasy.app.gallery.ImageFolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter.BaseRecyclerAdapter;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter.ImageFolderAdapter;

public class ImageFolderPopupWindow extends PopupWindow implements View.OnAttachStateChangeListener, BaseRecyclerAdapter.OnItemClickListener {
    private ImageFolderAdapter mAdapter;
    private Callback mCallback;
    private RecyclerView mFolderView;

    public interface Callback {
        void onDismiss();

        void onSelect(ImageFolderPopupWindow imageFolderPopupWindow, ImageFolder imageFolder);

        void onShow();
    }

    public ImageFolderPopupWindow(Context context, Callback callback) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_folder, (ViewGroup) null), -1, -2);
        this.mCallback = callback;
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);
        setFocusable(true);
        View content = getContentView();
        content.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageFolderPopupWindow.this.dismiss();
            }
        });
        content.addOnAttachStateChangeListener(this);
        this.mFolderView = (RecyclerView) content.findViewById(R.id.rv_popup_folder);
        this.mFolderView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setAdapter(ImageFolderAdapter adapter) {
        this.mAdapter = adapter;
        this.mFolderView.setAdapter(adapter);
        this.mAdapter.setOnItemClickListener(this);
    }

    public void onViewAttachedToWindow(View v) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onShow();
        }
    }

    public void onViewDetachedFromWindow(View v) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onDismiss();
        }
    }

    public void onItemClick(int position, long itemId) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onSelect(this, (ImageFolder) this.mAdapter.getItem(position));
        }
    }
}

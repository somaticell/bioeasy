package cn.com.bioeasy.app.widgets.recyclerview.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import java.util.List;

public abstract class ParallaxRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> mData;
    /* access modifiers changed from: private */
    public CustomRelativeWrapper mHeader;
    /* access modifiers changed from: private */
    public OnClickEvent mOnClickEvent;
    private OnParallaxScroll mParallaxScroll;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private float mScrollMultiplier = 0.5f;
    private boolean mShouldClipView = true;

    public interface OnClickEvent {
        void onClick(View view, int i);
    }

    public interface OnParallaxScroll {
        void onParallaxScroll(float f, float f2, View view);
    }

    public static class VIEW_TYPES {
        public static final int FIRST_VIEW = 3;
        public static final int HEADER = 2;
        public static final int NORMAL = 1;
    }

    public abstract int getItemCountImpl(ParallaxRecyclerAdapter<T> parallaxRecyclerAdapter);

    public abstract void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<T> parallaxRecyclerAdapter, int i);

    public abstract RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<T> parallaxRecyclerAdapter, int i);

    public void translateHeader(float of) {
        float left;
        float ofCalculated = of * this.mScrollMultiplier;
        if (Build.VERSION.SDK_INT >= 11 && of < ((float) this.mHeader.getHeight())) {
            this.mHeader.setTranslationY(ofCalculated);
        } else if (of < ((float) this.mHeader.getHeight())) {
            TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, ofCalculated, ofCalculated);
            anim.setFillAfter(true);
            anim.setDuration(0);
            this.mHeader.startAnimation(anim);
        }
        this.mHeader.setClipY(Math.round(ofCalculated));
        if (this.mParallaxScroll != null) {
            if (this.mRecyclerView.findViewHolderForAdapterPosition(0) != null) {
                left = Math.min(1.0f, ofCalculated / (((float) this.mHeader.getHeight()) * this.mScrollMultiplier));
            } else {
                left = 1.0f;
            }
            this.mParallaxScroll.onParallaxScroll(left, of, this.mHeader);
        }
    }

    public void setParallaxHeader(View header, RecyclerView view) {
        this.mRecyclerView = view;
        this.mHeader = new CustomRelativeWrapper(header.getContext(), this.mShouldClipView);
        this.mHeader.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.mHeader.addView(header, new RelativeLayout.LayoutParams(-1, -1));
        view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (ParallaxRecyclerAdapter.this.mHeader != null) {
                    ParallaxRecyclerAdapter.this.translateHeader(ParallaxRecyclerAdapter.this.mRecyclerView.getLayoutManager().getChildAt(0) == ParallaxRecyclerAdapter.this.mHeader ? (float) ParallaxRecyclerAdapter.this.mRecyclerView.computeVerticalScrollOffset() : (float) ParallaxRecyclerAdapter.this.mHeader.getHeight());
                }
            }
        });
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (this.mHeader == null) {
            onBindViewHolderImpl(viewHolder, this, i);
        } else if (i != 0) {
            onBindViewHolderImpl(viewHolder, this, i - 1);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        if (i == 2 && this.mHeader != null) {
            return new ViewHolder(this.mHeader);
        }
        if (!(i != 3 || this.mHeader == null || this.mRecyclerView == null || (holder = this.mRecyclerView.findViewHolderForAdapterPosition(0)) == null)) {
            translateHeader((float) (-holder.itemView.getTop()));
        }
        final RecyclerView.ViewHolder holder2 = onCreateViewHolderImpl(viewGroup, this, i);
        if (this.mOnClickEvent == null) {
            return holder2;
        }
        holder2.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParallaxRecyclerAdapter.this.mOnClickEvent.onClick(v, holder2.getAdapterPosition() - (ParallaxRecyclerAdapter.this.mHeader == null ? 0 : 1));
            }
        });
        return holder2;
    }

    public boolean hasHeader() {
        return this.mHeader != null;
    }

    public void setOnClickEvent(OnClickEvent onClickEvent) {
        this.mOnClickEvent = onClickEvent;
    }

    public boolean isShouldClipView() {
        return this.mShouldClipView;
    }

    public void setShouldClipView(boolean shouldClickView) {
        this.mShouldClipView = shouldClickView;
    }

    public void setOnParallaxScroll(OnParallaxScroll parallaxScroll) {
        this.mParallaxScroll = parallaxScroll;
        this.mParallaxScroll.onParallaxScroll(0.0f, 0.0f, this.mHeader);
    }

    public ParallaxRecyclerAdapter(List<T> data) {
        this.mData = data;
    }

    public List<T> getData() {
        return this.mData;
    }

    public void setData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addItem(T item, int position) {
        this.mData.add(position, item);
        notifyItemInserted((this.mHeader == null ? 0 : 1) + position);
    }

    public void removeItem(T item) {
        int position = this.mData.indexOf(item);
        if (position >= 0) {
            this.mData.remove(item);
            notifyItemRemoved((this.mHeader == null ? 0 : 1) + position);
        }
    }

    public int getItemCount() {
        return (this.mHeader == null ? 0 : 1) + getItemCountImpl(this);
    }

    public int getItemViewType(int position) {
        if (position == 1) {
            return 3;
        }
        if (position != 0 || this.mHeader == null) {
            return 1;
        }
        return 2;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class CustomRelativeWrapper extends RelativeLayout {
        private int mOffset;
        private boolean mShouldClip;

        public CustomRelativeWrapper(Context context, boolean shouldClick) {
            super(context);
            this.mShouldClip = shouldClick;
        }

        /* access modifiers changed from: protected */
        public void dispatchDraw(Canvas canvas) {
            if (this.mShouldClip) {
                canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + this.mOffset));
            }
            super.dispatchDraw(canvas);
        }

        public void setClipY(int offset) {
            this.mOffset = offset;
            invalidate();
        }
    }

    public void setScrollMultiplier(float mul) {
        this.mScrollMultiplier = mul;
    }

    public float getScrollMultiplier() {
        return this.mScrollMultiplier;
    }
}

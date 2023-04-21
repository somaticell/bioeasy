package com.bigkoo.convenientbanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.CBPageChangeListener;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConvenientBanner<T> extends LinearLayout {
    /* access modifiers changed from: private */
    public AdSwitchTask adSwitchTask;
    /* access modifiers changed from: private */
    public long autoTurningTime;
    private boolean canLoop = true;
    private boolean canTurn = false;
    private ViewGroup loPageTurningPoint;
    private List<T> mDatas;
    private ArrayList<ImageView> mPointViews = new ArrayList<>();
    private boolean manualPageable = true;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private CBPageAdapter pageAdapter;
    private CBPageChangeListener pageChangeListener;
    private int[] page_indicatorId;
    private ViewPagerScroller scroller;
    /* access modifiers changed from: private */
    public boolean turning;
    /* access modifiers changed from: private */
    public CBLoopViewPager viewPager;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT,
        ALIGN_PARENT_RIGHT,
        CENTER_HORIZONTAL
    }

    public ConvenientBanner(Context context) {
        super(context);
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        this.canLoop = a.getBoolean(0, true);
        a.recycle();
        init(context);
    }

    @TargetApi(11)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        this.canLoop = a.getBoolean(0, true);
        a.recycle();
        init(context);
    }

    @TargetApi(21)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        this.canLoop = a.getBoolean(0, true);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(R.layout.include_viewpager, this, true);
        this.viewPager = (CBLoopViewPager) hView.findViewById(R.id.cbLoopViewPager);
        this.loPageTurningPoint = (ViewGroup) hView.findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();
        this.adSwitchTask = new AdSwitchTask(this);
    }

    static class AdSwitchTask implements Runnable {
        private final WeakReference<ConvenientBanner> reference;

        AdSwitchTask(ConvenientBanner convenientBanner) {
            this.reference = new WeakReference<>(convenientBanner);
        }

        public void run() {
            ConvenientBanner convenientBanner = (ConvenientBanner) this.reference.get();
            if (convenientBanner != null && convenientBanner.viewPager != null && convenientBanner.turning) {
                convenientBanner.viewPager.setCurrentItem(convenientBanner.viewPager.getCurrentItem() + 1);
                convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
            }
        }
    }

    public ConvenientBanner setPages(CBViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        this.pageAdapter = new CBPageAdapter(holderCreator, this.mDatas);
        this.viewPager.setAdapter(this.pageAdapter, this.canLoop);
        if (this.page_indicatorId != null) {
            setPageIndicator(this.page_indicatorId);
        }
        return this;
    }

    public void notifyDataSetChanged() {
        this.viewPager.getAdapter().notifyDataSetChanged();
        if (this.page_indicatorId != null) {
            setPageIndicator(this.page_indicatorId);
        }
    }

    public ConvenientBanner setPointViewVisible(boolean visible) {
        this.loPageTurningPoint.setVisibility(visible ? 0 : 8);
        return this;
    }

    public ConvenientBanner setPageIndicator(int[] page_indicatorId2) {
        this.loPageTurningPoint.removeAllViews();
        this.mPointViews.clear();
        this.page_indicatorId = page_indicatorId2;
        if (this.mDatas != null) {
            for (int count = 0; count < this.mDatas.size(); count++) {
                ImageView pointView = new ImageView(getContext());
                pointView.setPadding(5, 0, 5, 0);
                if (this.mPointViews.isEmpty()) {
                    pointView.setImageResource(page_indicatorId2[1]);
                } else {
                    pointView.setImageResource(page_indicatorId2[0]);
                }
                this.mPointViews.add(pointView);
                this.loPageTurningPoint.addView(pointView);
            }
            this.pageChangeListener = new CBPageChangeListener(this.mPointViews, page_indicatorId2);
            this.viewPager.setOnPageChangeListener(this.pageChangeListener);
            this.pageChangeListener.onPageSelected(this.viewPager.getRealItem());
            if (this.onPageChangeListener != null) {
                this.pageChangeListener.setOnPageChangeListener(this.onPageChangeListener);
            }
        }
        return this;
    }

    public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        int i;
        int i2;
        int i3 = -1;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.loPageTurningPoint.getLayoutParams();
        if (align == PageIndicatorAlign.ALIGN_PARENT_LEFT) {
            i = -1;
        } else {
            i = 0;
        }
        layoutParams.addRule(9, i);
        if (align == PageIndicatorAlign.ALIGN_PARENT_RIGHT) {
            i2 = -1;
        } else {
            i2 = 0;
        }
        layoutParams.addRule(11, i2);
        if (align != PageIndicatorAlign.CENTER_HORIZONTAL) {
            i3 = 0;
        }
        layoutParams.addRule(14, i3);
        this.loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    public boolean isTurning() {
        return this.turning;
    }

    public ConvenientBanner startTurning(long autoTurningTime2) {
        if (this.turning) {
            stopTurning();
        }
        this.canTurn = true;
        this.autoTurningTime = autoTurningTime2;
        this.turning = true;
        postDelayed(this.adSwitchTask, autoTurningTime2);
        return this;
    }

    public void stopTurning() {
        this.turning = false;
        removeCallbacks(this.adSwitchTask);
    }

    public ConvenientBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        this.viewPager.setPageTransformer(true, transformer);
        return this;
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            this.scroller = new ViewPagerScroller(this.viewPager.getContext());
            mScroller.set(this.viewPager, this.scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return this.viewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable2) {
        this.viewPager.setCanScroll(manualPageable2);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 1 || action == 3 || action == 4) {
            if (this.canTurn) {
                startTurning(this.autoTurningTime);
            }
        } else if (action == 0 && this.canTurn) {
            stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    public int getCurrentItem() {
        if (this.viewPager != null) {
            return this.viewPager.getRealItem();
        }
        return -1;
    }

    public void setcurrentitem(int index) {
        if (this.viewPager != null) {
            this.viewPager.setCurrentItem(index);
        }
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return this.onPageChangeListener;
    }

    public ConvenientBanner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener2) {
        this.onPageChangeListener = onPageChangeListener2;
        if (this.pageChangeListener != null) {
            this.pageChangeListener.setOnPageChangeListener(onPageChangeListener2);
        } else {
            this.viewPager.setOnPageChangeListener(onPageChangeListener2);
        }
        return this;
    }

    public boolean isCanLoop() {
        return this.viewPager.isCanLoop();
    }

    public ConvenientBanner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            this.viewPager.setOnItemClickListener((OnItemClickListener) null);
        } else {
            this.viewPager.setOnItemClickListener(onItemClickListener);
        }
        return this;
    }

    public void setScrollDuration(int scrollDuration) {
        this.scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return this.scroller.getScrollDuration();
    }

    public CBLoopViewPager getViewPager() {
        return this.viewPager;
    }

    public void setCanLoop(boolean canLoop2) {
        this.canLoop = canLoop2;
        this.viewPager.setCanLoop(canLoop2);
    }
}

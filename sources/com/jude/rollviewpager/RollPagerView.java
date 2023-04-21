package com.jude.rollviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class RollPagerView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private int alpha;
    private int color;
    /* access modifiers changed from: private */
    public int delay;
    private int gravity;
    /* access modifiers changed from: private */
    public PagerAdapter mAdapter;
    /* access modifiers changed from: private */
    public TimeTaskHandler mHandler;
    /* access modifiers changed from: private */
    public View mHintView;
    /* access modifiers changed from: private */
    public HintViewDelegate mHintViewDelegate;
    /* access modifiers changed from: private */
    public long mRecentTouchTime;
    private ViewPager mViewPager;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private Timer timer;

    public interface HintViewDelegate {
        void initView(int i, int i2, HintView hintView);

        void setCurrentPosition(int i, HintView hintView);
    }

    public RollPagerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RollPagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHintViewDelegate = new HintViewDelegate() {
            public void setCurrentPosition(int position, HintView hintView) {
                if (hintView != null) {
                    hintView.setCurrent(position);
                }
            }

            public void initView(int length, int gravity, HintView hintView) {
                if (hintView != null) {
                    hintView.initView(length, gravity);
                }
            }
        };
        this.mHandler = new TimeTaskHandler(this);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (this.mViewPager != null) {
            removeView(this.mViewPager);
        }
        TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.RollViewPager);
        this.gravity = type.getInteger(R.styleable.RollViewPager_rollviewpager_hint_gravity, 1);
        this.delay = type.getInt(R.styleable.RollViewPager_rollviewpager_play_delay, 0);
        this.color = type.getColor(R.styleable.RollViewPager_rollviewpager_hint_color, -16777216);
        this.alpha = type.getInt(R.styleable.RollViewPager_rollviewpager_hint_alpha, 0);
        this.paddingLeft = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingLeft, 0.0f);
        this.paddingRight = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingRight, 0.0f);
        this.paddingTop = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingTop, 0.0f);
        this.paddingBottom = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingBottom, (float) Util.dip2px(getContext(), 4.0f));
        this.mViewPager = new ViewPager(getContext());
        this.mViewPager.setId(R.id.viewpager_inner);
        this.mViewPager.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        addView(this.mViewPager);
        type.recycle();
        initHint(new ColorPointHintView(getContext(), Color.parseColor("#E3AC42"), Color.parseColor("#88ffffff")));
    }

    private static final class TimeTaskHandler extends Handler {
        private WeakReference<RollPagerView> mRollPagerViewWeakReference;

        public TimeTaskHandler(RollPagerView rollPagerView) {
            this.mRollPagerViewWeakReference = new WeakReference<>(rollPagerView);
        }

        public void handleMessage(Message msg) {
            RollPagerView rollPagerView = (RollPagerView) this.mRollPagerViewWeakReference.get();
            int cur = rollPagerView.getViewPager().getCurrentItem() + 1;
            if (cur >= rollPagerView.mAdapter.getCount()) {
                cur = 0;
            }
            rollPagerView.getViewPager().setCurrentItem(cur);
            rollPagerView.mHintViewDelegate.setCurrentPosition(cur, (HintView) rollPagerView.mHintView);
        }
    }

    private static class WeakTimerTask extends TimerTask {
        private WeakReference<RollPagerView> mRollPagerViewWeakReference;

        public WeakTimerTask(RollPagerView mRollPagerView) {
            this.mRollPagerViewWeakReference = new WeakReference<>(mRollPagerView);
        }

        public void run() {
            RollPagerView rollPagerView = (RollPagerView) this.mRollPagerViewWeakReference.get();
            if (rollPagerView == null) {
                cancel();
            } else if (rollPagerView.isShown() && System.currentTimeMillis() - rollPagerView.mRecentTouchTime > ((long) rollPagerView.delay)) {
                rollPagerView.mHandler.sendEmptyMessage(0);
            }
        }
    }

    private void startPlay() {
        if (this.delay > 0 && this.mAdapter != null && this.mAdapter.getCount() > 1) {
            if (this.timer != null) {
                this.timer.cancel();
            }
            this.timer = new Timer();
            this.timer.schedule(new WeakTimerTask(this), (long) this.delay, (long) this.delay);
        }
    }

    private void stopPlay() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public void setHintViewDelegate(HintViewDelegate delegate) {
        this.mHintViewDelegate = delegate;
    }

    private void initHint(HintView hintview) {
        if (this.mHintView != null) {
            removeView(this.mHintView);
        }
        if (hintview != null && (hintview instanceof HintView)) {
            this.mHintView = (View) hintview;
            loadHintView();
        }
    }

    private void loadHintView() {
        addView(this.mHintView);
        this.mHintView.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -2);
        lp.addRule(12);
        this.mHintView.setLayoutParams(lp);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(this.color);
        gd.setAlpha(this.alpha);
        this.mHintView.setBackgroundDrawable(gd);
        this.mHintViewDelegate.initView(this.mAdapter == null ? 0 : this.mAdapter.getCount(), this.gravity, (HintView) this.mHintView);
    }

    public void setAnimationDurtion(final int during) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mField.set(this.mViewPager, new Scroller(getContext(), new Interpolator() {
                public float getInterpolation(float t) {
                    float t2 = t - 1.0f;
                    return (t2 * t2 * t2 * t2 * t2) + 1.0f;
                }
            }) {
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    int duration2;
                    if (System.currentTimeMillis() - RollPagerView.this.mRecentTouchTime > ((long) RollPagerView.this.delay)) {
                        duration2 = during;
                    } else {
                        duration2 = duration / 2;
                    }
                    super.startScroll(startX, startY, dx, dy, duration2);
                }

                public void startScroll(int startX, int startY, int dx, int dy) {
                    super.startScroll(startX, startY, dx, dy, during);
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    public void setPlayDelay(int delay2) {
        this.delay = delay2;
        startPlay();
    }

    public void pause() {
        stopPlay();
    }

    public void resume() {
        startPlay();
    }

    public boolean isPlaying() {
        return this.timer != null;
    }

    public void setHintPadding(int left, int top, int right, int bottom) {
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        this.mHintView.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
    }

    public void setHintAlpha(int alpha2) {
        this.alpha = alpha2;
        initHint((HintView) this.mHintView);
    }

    public void setHintView(HintView hintview) {
        if (this.mHintView != null) {
            removeView(this.mHintView);
        }
        this.mHintView = (View) hintview;
        if (hintview != null && (hintview instanceof View)) {
            initHint(hintview);
        }
    }

    public ViewPager getViewPager() {
        return this.mViewPager;
    }

    public void setAdapter(PagerAdapter adapter) {
        this.mViewPager.setAdapter(adapter);
        this.mViewPager.addOnPageChangeListener(this);
        this.mAdapter = adapter;
        dataSetChanged();
        adapter.registerDataSetObserver(new JPagerObserver());
    }

    private class JPagerObserver extends DataSetObserver {
        private JPagerObserver() {
        }

        public void onChanged() {
            RollPagerView.this.dataSetChanged();
        }

        public void onInvalidated() {
            RollPagerView.this.dataSetChanged();
        }
    }

    /* access modifiers changed from: private */
    public void dataSetChanged() {
        startPlay();
        if (this.mHintView != null) {
            this.mHintViewDelegate.initView(this.mAdapter.getCount(), this.gravity, (HintView) this.mHintView);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.mRecentTouchTime = System.currentTimeMillis();
        return super.dispatchTouchEvent(ev);
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageSelected(int arg0) {
        this.mHintViewDelegate.setCurrentPosition(arg0, (HintView) this.mHintView);
    }
}

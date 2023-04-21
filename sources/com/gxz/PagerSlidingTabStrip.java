package com.gxz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PagerSlidingTabStrip extends HorizontalScrollView {
    private static final int[] ATTRS = {16842901, 16842904};
    private Context context;
    /* access modifiers changed from: private */
    public int currentPosition;
    /* access modifiers changed from: private */
    public float currentPositionOffset;
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    public ViewPager.OnPageChangeListener delegatePageListener;
    private int dividerColor;
    private int dividerPaddingTopBottom;
    private Paint dividerPaint;
    private int dividerWidth;
    private LinearLayout.LayoutParams expandedTabLayoutParams;
    private int indicatorColor;
    private int indicatorHeight;
    private int lastScrollX;
    private Locale locale;
    /* access modifiers changed from: private */
    public boolean mFadeEnabled;
    /* access modifiers changed from: private */
    public State mState;
    private LinearLayout.LayoutParams matchParentTabLayoutParams;
    /* access modifiers changed from: private */
    public int oldPage;
    private PageListener pageListener;
    /* access modifiers changed from: private */
    public ViewPager pager;
    private Paint rectPaint;
    private int scrollOffset;
    /* access modifiers changed from: private */
    public int selectedPosition;
    private int selectedTabTextColor;
    private boolean shouldExpand;
    /* access modifiers changed from: private */
    public boolean smoothScrollWhenClickTab;
    private int tabBackgroundResId;
    private int tabCount;
    private int tabPadding;
    private int tabTextColor;
    private int tabTextSize;
    private Typeface tabTypeface;
    private int tabTypefaceStyle;
    /* access modifiers changed from: private */
    public List<Map<String, View>> tabViews;
    /* access modifiers changed from: private */
    public LinearLayout tabsContainer;
    private boolean textAllCaps;
    private int underlineColor;
    private int underlineHeight;
    /* access modifiers changed from: private */
    public float zoomMax;

    public interface IconTabProvider {
        int getPageIconResId(int i);
    }

    private enum State {
        IDLE,
        GOING_LEFT,
        GOING_RIGHT
    }

    public PagerSlidingTabStrip(Context context2) {
        this(context2, (AttributeSet) null);
    }

    public PagerSlidingTabStrip(Context context2, AttributeSet attrs) {
        this(context2, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        this.currentPosition = 0;
        this.selectedPosition = 0;
        this.currentPositionOffset = 0.0f;
        this.indicatorColor = -10066330;
        this.underlineColor = 436207616;
        this.dividerColor = 436207616;
        this.shouldExpand = false;
        this.textAllCaps = true;
        this.scrollOffset = 52;
        this.indicatorHeight = 8;
        this.underlineHeight = 2;
        this.dividerPaddingTopBottom = 12;
        this.tabPadding = 24;
        this.dividerWidth = 1;
        this.tabTextSize = 12;
        this.tabTextColor = -10066330;
        this.selectedTabTextColor = -12206054;
        this.tabTypeface = null;
        this.tabTypefaceStyle = 0;
        this.lastScrollX = 0;
        this.smoothScrollWhenClickTab = true;
        this.tabViews = new ArrayList();
        this.mFadeEnabled = true;
        this.zoomMax = 0.3f;
        this.context = context2;
        setFillViewport(true);
        setWillNotDraw(false);
        this.tabsContainer = new LinearLayout(context2);
        this.tabsContainer.setOrientation(0);
        this.tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.tabsContainer);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.scrollOffset = (int) TypedValue.applyDimension(1, (float) this.scrollOffset, dm);
        this.indicatorHeight = (int) TypedValue.applyDimension(1, (float) this.indicatorHeight, dm);
        this.underlineHeight = (int) TypedValue.applyDimension(1, (float) this.underlineHeight, dm);
        this.dividerPaddingTopBottom = (int) TypedValue.applyDimension(1, (float) this.dividerPaddingTopBottom, dm);
        this.tabPadding = (int) TypedValue.applyDimension(1, (float) this.tabPadding, dm);
        this.dividerWidth = (int) TypedValue.applyDimension(1, (float) this.dividerWidth, dm);
        this.tabTextSize = (int) TypedValue.applyDimension(2, (float) this.tabTextSize, dm);
        TypedArray a = context2.obtainStyledAttributes(attrs, ATTRS);
        this.tabTextSize = a.getDimensionPixelSize(0, this.tabTextSize);
        this.tabTextColor = a.getColor(1, this.tabTextColor);
        a.recycle();
        TypedArray a2 = context2.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
        this.indicatorColor = a2.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, this.indicatorColor);
        this.underlineColor = a2.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, this.underlineColor);
        this.dividerColor = a2.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, this.dividerColor);
        this.indicatorHeight = a2.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, this.indicatorHeight);
        this.underlineHeight = a2.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, this.underlineHeight);
        this.dividerPaddingTopBottom = a2.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPaddingTopBottom, this.dividerPaddingTopBottom);
        this.tabPadding = a2.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, this.tabPadding);
        this.tabBackgroundResId = a2.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, this.tabBackgroundResId);
        this.shouldExpand = a2.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, this.shouldExpand);
        this.scrollOffset = a2.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, this.scrollOffset);
        this.textAllCaps = a2.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, this.textAllCaps);
        this.selectedTabTextColor = a2.getColor(R.styleable.PagerSlidingTabStrip_pstsTextSelectedColor, this.selectedTabTextColor);
        this.zoomMax = a2.getFloat(R.styleable.PagerSlidingTabStrip_pstsScaleZoomMax, this.zoomMax);
        this.smoothScrollWhenClickTab = a2.getBoolean(R.styleable.PagerSlidingTabStrip_pstsSmoothScrollWhenClickTab, this.smoothScrollWhenClickTab);
        a2.recycle();
        this.rectPaint = new Paint();
        this.rectPaint.setAntiAlias(true);
        this.rectPaint.setStyle(Paint.Style.FILL);
        this.dividerPaint = new Paint();
        this.dividerPaint.setAntiAlias(true);
        this.dividerPaint.setStrokeWidth((float) this.dividerWidth);
        this.defaultTabLayoutParams = new LinearLayout.LayoutParams(-2, -1);
        this.matchParentTabLayoutParams = new LinearLayout.LayoutParams(-1, -1);
        this.expandedTabLayoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
        if (this.locale == null) {
            this.locale = getResources().getConfiguration().locale;
        }
        this.pageListener = new PageListener();
    }

    public void setViewPager(ViewPager pager2) {
        this.pager = pager2;
        if (this.pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.pager.addOnPageChangeListener(this.pageListener);
        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    private void notifyDataSetChanged() {
        this.tabsContainer.removeAllViews();
        this.tabCount = this.pager.getAdapter().getCount();
        for (int i = 0; i < this.tabCount; i++) {
            if (this.pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) this.pager.getAdapter()).getPageIconResId(i));
            } else {
                addTextTab(i, this.pager.getAdapter().getPageTitle(i).toString());
            }
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                PagerSlidingTabStrip.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int unused = PagerSlidingTabStrip.this.currentPosition = PagerSlidingTabStrip.this.pager.getCurrentItem();
                PagerSlidingTabStrip.this.scrollToChild(PagerSlidingTabStrip.this.currentPosition, 0);
                PagerSlidingTabStrip.this.updateTabStyles();
            }
        });
    }

    private void addTextTab(int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(17);
        tab.setSingleLine();
        tab.setIncludeFontPadding(false);
        TextView tab2 = new TextView(getContext());
        tab2.setText(title);
        tab2.setGravity(17);
        tab2.setSingleLine();
        tab2.setIncludeFontPadding(false);
        addTab(position, tab, tab2);
    }

    private void addIconTab(int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);
        addTab(position, tab, (View) null);
    }

    private void addTab(final int position, View tab, View tab2) {
        tab.setPadding(this.tabPadding, 0, this.tabPadding, 0);
        tab2.setPadding(this.tabPadding, 0, this.tabPadding, 0);
        FrameLayout framelayout = new FrameLayout(this.context);
        framelayout.addView(tab, 0, this.matchParentTabLayoutParams);
        framelayout.addView(tab2, 1, this.matchParentTabLayoutParams);
        this.tabsContainer.addView(framelayout, position, this.shouldExpand ? this.expandedTabLayoutParams : this.defaultTabLayoutParams);
        framelayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean unused = PagerSlidingTabStrip.this.mFadeEnabled = false;
                PagerSlidingTabStrip.this.pager.setCurrentItem(position, PagerSlidingTabStrip.this.smoothScrollWhenClickTab);
                int unused2 = PagerSlidingTabStrip.this.currentPosition = position;
                PagerSlidingTabStrip.this.scrollToChild(position, 0);
            }
        });
        Map<String, View> map = new HashMap<>();
        ViewHelper.setAlpha(tab, 1.0f);
        map.put("normal", tab);
        ViewHelper.setAlpha(tab2, 0.0f);
        map.put("selected", tab2);
        this.tabViews.add(position, map);
    }

    /* access modifiers changed from: private */
    public void updateTabStyles() {
        for (int i = 0; i < this.tabCount; i++) {
            FrameLayout frameLayout = (FrameLayout) this.tabsContainer.getChildAt(i);
            frameLayout.setBackgroundResource(this.tabBackgroundResId);
            for (int j = 0; j < frameLayout.getChildCount(); j++) {
                View v = frameLayout.getChildAt(j);
                if (v instanceof TextView) {
                    TextView tab = (TextView) v;
                    tab.setTextSize(0, (float) this.tabTextSize);
                    tab.setTypeface(this.tabTypeface, this.tabTypefaceStyle);
                    tab.setPadding(this.tabPadding, 0, this.tabPadding, 0);
                    if (j == 0) {
                        tab.setTextColor(this.tabTextColor);
                    } else {
                        tab.setTextColor(this.selectedTabTextColor);
                    }
                    ViewHelper.setAlpha((View) this.tabViews.get(i).get("normal"), 1.0f);
                    ViewHelper.setAlpha((View) this.tabViews.get(i).get("selected"), 0.0f);
                    ViewHelper.setPivotX(frameLayout, ((float) frameLayout.getMeasuredWidth()) * 0.5f);
                    ViewHelper.setPivotY(frameLayout, ((float) frameLayout.getMeasuredHeight()) * 0.5f);
                    ViewHelper.setScaleX(frameLayout, 1.0f);
                    ViewHelper.setScaleY(frameLayout, 1.0f);
                    if (this.textAllCaps) {
                        if (Build.VERSION.SDK_INT >= 14) {
                            tab.setAllCaps(true);
                        } else {
                            tab.setText(tab.getText().toString().toUpperCase(this.locale));
                        }
                    }
                    if (i == this.selectedPosition) {
                        ViewHelper.setAlpha((View) this.tabViews.get(i).get("normal"), 0.0f);
                        ViewHelper.setAlpha((View) this.tabViews.get(i).get("selected"), 1.0f);
                        ViewHelper.setPivotX(frameLayout, ((float) frameLayout.getMeasuredWidth()) * 0.5f);
                        ViewHelper.setPivotY(frameLayout, ((float) frameLayout.getMeasuredHeight()) * 0.5f);
                        ViewHelper.setScaleX(frameLayout, this.zoomMax + 1.0f);
                        ViewHelper.setScaleY(frameLayout, this.zoomMax + 1.0f);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void scrollToChild(int position, int offset) {
        if (this.tabCount != 0) {
            int newScrollX = this.tabsContainer.getChildAt(position).getLeft() + offset;
            if (position > 0 || offset > 0) {
                newScrollX -= this.scrollOffset;
            }
            if (newScrollX != this.lastScrollX) {
                this.lastScrollX = newScrollX;
                int k = this.tabsContainer.getChildAt(position).getMeasuredWidth();
                smoothScrollTo(((k / 2) + (this.tabsContainer.getChildAt(position).getLeft() + offset)) - (getMeasuredWidth() / 2), 0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode() && this.tabCount != 0) {
            int height = getHeight();
            this.rectPaint.setColor(this.underlineColor);
            canvas.drawRect(0.0f, (float) (height - this.underlineHeight), (float) this.tabsContainer.getWidth(), (float) height, this.rectPaint);
            this.rectPaint.setColor(this.indicatorColor);
            View currentTab = this.tabsContainer.getChildAt(this.currentPosition);
            float lineLeft = (float) currentTab.getLeft();
            float lineRight = (float) currentTab.getRight();
            if (this.currentPositionOffset > 0.0f && this.currentPosition < this.tabCount - 1) {
                View nextTab = this.tabsContainer.getChildAt(this.currentPosition + 1);
                float nextTabLeft = (float) nextTab.getLeft();
                float nextTabRight = (float) nextTab.getRight();
                lineLeft = (this.currentPositionOffset * nextTabLeft) + ((1.0f - this.currentPositionOffset) * lineLeft);
                lineRight = (this.currentPositionOffset * nextTabRight) + ((1.0f - this.currentPositionOffset) * lineRight);
            }
            canvas.drawRect(lineLeft, (float) (height - this.indicatorHeight), lineRight, (float) height, this.rectPaint);
            this.dividerPaint.setColor(this.dividerColor);
            for (int i = 0; i < this.tabCount - 1; i++) {
                View tab = this.tabsContainer.getChildAt(i);
                canvas.drawLine((float) tab.getRight(), (float) this.dividerPaddingTopBottom, (float) tab.getRight(), (float) (height - this.dividerPaddingTopBottom), this.dividerPaint);
            }
        }
    }

    private class PageListener implements ViewPager.OnPageChangeListener {
        private int oldPosition;

        private PageListener() {
            this.oldPosition = 0;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float effectOffset;
            int unused = PagerSlidingTabStrip.this.currentPosition = position;
            float unused2 = PagerSlidingTabStrip.this.currentPositionOffset = positionOffset;
            if (!(PagerSlidingTabStrip.this.tabsContainer == null || PagerSlidingTabStrip.this.tabsContainer.getChildAt(position) == null)) {
                PagerSlidingTabStrip.this.scrollToChild(position, (int) (((float) PagerSlidingTabStrip.this.tabsContainer.getChildAt(position).getWidth()) * positionOffset));
            }
            PagerSlidingTabStrip.this.invalidate();
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            if (PagerSlidingTabStrip.this.mState == State.IDLE && positionOffset > 0.0f) {
                int unused3 = PagerSlidingTabStrip.this.oldPage = PagerSlidingTabStrip.this.pager.getCurrentItem();
                State unused4 = PagerSlidingTabStrip.this.mState = position == PagerSlidingTabStrip.this.oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
            }
            boolean goingRight = position == PagerSlidingTabStrip.this.oldPage;
            if (PagerSlidingTabStrip.this.mState == State.GOING_RIGHT && !goingRight) {
                State unused5 = PagerSlidingTabStrip.this.mState = State.GOING_LEFT;
            } else if (PagerSlidingTabStrip.this.mState == State.GOING_LEFT && goingRight) {
                State unused6 = PagerSlidingTabStrip.this.mState = State.GOING_RIGHT;
            }
            if (PagerSlidingTabStrip.this.isSmall(positionOffset)) {
                effectOffset = 0.0f;
            } else {
                effectOffset = positionOffset;
            }
            View mLeft = PagerSlidingTabStrip.this.tabsContainer.getChildAt(position);
            View mRight = PagerSlidingTabStrip.this.tabsContainer.getChildAt(position + 1);
            if (effectOffset == 0.0f) {
                State unused7 = PagerSlidingTabStrip.this.mState = State.IDLE;
            }
            if (PagerSlidingTabStrip.this.mFadeEnabled) {
                PagerSlidingTabStrip.this.animateFadeScale(mLeft, mRight, effectOffset, position);
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                PagerSlidingTabStrip.this.scrollToChild(PagerSlidingTabStrip.this.pager.getCurrentItem(), 0);
                boolean unused = PagerSlidingTabStrip.this.mFadeEnabled = true;
            }
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        public void onPageSelected(int position) {
            int unused = PagerSlidingTabStrip.this.selectedPosition = position;
            ViewHelper.setAlpha((View) ((Map) PagerSlidingTabStrip.this.tabViews.get(this.oldPosition)).get("normal"), 1.0f);
            ViewHelper.setAlpha((View) ((Map) PagerSlidingTabStrip.this.tabViews.get(this.oldPosition)).get("selected"), 0.0f);
            View v_old = PagerSlidingTabStrip.this.tabsContainer.getChildAt(this.oldPosition);
            ViewHelper.setPivotX(v_old, ((float) v_old.getMeasuredWidth()) * 0.5f);
            ViewHelper.setPivotY(v_old, ((float) v_old.getMeasuredHeight()) * 0.5f);
            ViewHelper.setScaleX(v_old, 1.0f);
            ViewHelper.setScaleY(v_old, 1.0f);
            ViewHelper.setAlpha((View) ((Map) PagerSlidingTabStrip.this.tabViews.get(position)).get("normal"), 0.0f);
            ViewHelper.setAlpha((View) ((Map) PagerSlidingTabStrip.this.tabViews.get(position)).get("selected"), 1.0f);
            View v_new = PagerSlidingTabStrip.this.tabsContainer.getChildAt(position);
            ViewHelper.setPivotX(v_new, ((float) v_new.getMeasuredWidth()) * 0.5f);
            ViewHelper.setPivotY(v_new, ((float) v_new.getMeasuredHeight()) * 0.5f);
            ViewHelper.setScaleX(v_new, PagerSlidingTabStrip.this.zoomMax + 1.0f);
            ViewHelper.setScaleY(v_new, PagerSlidingTabStrip.this.zoomMax + 1.0f);
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageSelected(position);
            }
            this.oldPosition = PagerSlidingTabStrip.this.selectedPosition;
        }
    }

    public void setIndicatorColor(int indicatorColor2) {
        this.indicatorColor = indicatorColor2;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightDp) {
        this.indicatorHeight = (int) TypedValue.applyDimension(1, (float) indicatorLineHeightDp, getResources().getDisplayMetrics());
        invalidate();
    }

    public int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor2) {
        this.underlineColor = underlineColor2;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public void setDividerColor(int dividerColor2) {
        this.dividerColor = dividerColor2;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return this.dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightDp) {
        this.underlineHeight = (int) TypedValue.applyDimension(1, (float) underlineHeightDp, getResources().getDisplayMetrics());
        invalidate();
    }

    public int getUnderlineHeight() {
        return this.underlineHeight;
    }

    public void setDividerPaddingTopBottom(int dividerPaddingDp) {
        this.dividerPaddingTopBottom = (int) TypedValue.applyDimension(1, (float) dividerPaddingDp, getResources().getDisplayMetrics());
        invalidate();
    }

    public int getDividerPaddingTopBottom() {
        return this.dividerPaddingTopBottom;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand2) {
        this.shouldExpand = shouldExpand2;
        notifyDataSetChanged();
    }

    public boolean getShouldExpand() {
        return this.shouldExpand;
    }

    public boolean isTextAllCaps() {
        return this.textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps2) {
        this.textAllCaps = textAllCaps2;
    }

    public void setTextSize(int textSizeSp) {
        this.tabTextSize = (int) TypedValue.applyDimension(2, (float) textSizeSp, getResources().getDisplayMetrics());
        updateTabStyles();
    }

    public int getTextSize() {
        return this.tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return this.tabTextColor;
    }

    public void setSelectedTextColor(int textColor) {
        this.selectedTabTextColor = textColor;
        updateTabStyles();
    }

    public void setSelectedTextColorResource(int resId) {
        this.selectedTabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getSelectedTextColor() {
        return this.selectedTabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
        updateTabStyles();
    }

    public int getTabBackground() {
        return this.tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingDp) {
        this.tabPadding = (int) TypedValue.applyDimension(1, (float) paddingDp, getResources().getDisplayMetrics());
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return this.tabPadding;
    }

    public boolean isSmoothScrollWhenClickTab() {
        return this.smoothScrollWhenClickTab;
    }

    public void setSmoothScrollWhenClickTab(boolean smoothScrollWhenClickTab2) {
        this.smoothScrollWhenClickTab = smoothScrollWhenClickTab2;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.currentPosition = savedState.currentPosition;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPosition = this.currentPosition;
        return savedState;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPosition = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.currentPosition);
        }
    }

    public void setFadeEnabled(boolean enabled) {
        this.mFadeEnabled = enabled;
    }

    public boolean getFadeEnabled() {
        return this.mFadeEnabled;
    }

    public float getZoomMax() {
        return this.zoomMax;
    }

    public void setZoomMax(float zoomMax2) {
        this.zoomMax = zoomMax2;
    }

    /* access modifiers changed from: private */
    public boolean isSmall(float positionOffset) {
        return ((double) Math.abs(positionOffset)) < 1.0E-4d;
    }

    /* access modifiers changed from: protected */
    public void animateFadeScale(View left, View right, float positionOffset, int position) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                ViewHelper.setAlpha((View) this.tabViews.get(position).get("normal"), positionOffset);
                ViewHelper.setAlpha((View) this.tabViews.get(position).get("selected"), 1.0f - positionOffset);
                float mScale = (this.zoomMax + 1.0f) - (this.zoomMax * positionOffset);
                ViewHelper.setPivotX(left, ((float) left.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(left, ((float) left.getMeasuredHeight()) * 0.5f);
                ViewHelper.setScaleX(left, mScale);
                ViewHelper.setScaleY(left, mScale);
            }
            if (right != null) {
                ViewHelper.setAlpha((View) this.tabViews.get(position + 1).get("normal"), 1.0f - positionOffset);
                ViewHelper.setAlpha((View) this.tabViews.get(position + 1).get("selected"), positionOffset);
                float mScale2 = 1.0f + (this.zoomMax * positionOffset);
                ViewHelper.setPivotX(right, ((float) right.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(right, ((float) right.getMeasuredHeight()) * 0.5f);
                ViewHelper.setScaleX(right, mScale2);
                ViewHelper.setScaleY(right, mScale2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.pageListener != null && this.pager != null) {
            this.pager.removeOnPageChangeListener(this.pageListener);
        }
    }
}

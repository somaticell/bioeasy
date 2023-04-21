package com.flyco.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import java.util.ArrayList;

public class SlidingTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private static final int STYLE_BLOCK = 2;
    private static final int STYLE_NORMAL = 0;
    private static final int STYLE_TRIANGLE = 1;
    private Context mContext;
    private float mCurrentPositionOffset;
    private int mCurrentTab;
    private int mDividerColor;
    private float mDividerPadding;
    private Paint mDividerPaint;
    private float mDividerWidth;
    private int mHeight;
    private int mIndicatorColor;
    private float mIndicatorCornerRadius;
    private GradientDrawable mIndicatorDrawable;
    private int mIndicatorGravity;
    private float mIndicatorHeight;
    private float mIndicatorMarginBottom;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginTop;
    private Rect mIndicatorRect;
    private int mIndicatorStyle;
    private float mIndicatorWidth;
    private boolean mIndicatorWidthEqualTitle;
    private SparseArray<Boolean> mInitSetMap;
    private int mLastScrollX;
    /* access modifiers changed from: private */
    public OnTabSelectListener mListener;
    private Paint mRectPaint;
    private int mTabCount;
    private float mTabPadding;
    private Rect mTabRect;
    private boolean mTabSpaceEqual;
    private float mTabWidth;
    private LinearLayout mTabsContainer;
    private boolean mTextAllCaps;
    private boolean mTextBold;
    private Paint mTextPaint;
    private int mTextSelectColor;
    private int mTextUnselectColor;
    private float mTextsize;
    private String[] mTitles;
    private Paint mTrianglePaint;
    private Path mTrianglePath;
    private int mUnderlineColor;
    private int mUnderlineGravity;
    private float mUnderlineHeight;
    /* access modifiers changed from: private */
    public ViewPager mViewPager;
    private float margin;

    public interface CustomTabProvider {
        View getCustomTabView(ViewGroup viewGroup, int i);

        void tabSelect(View view);

        void tabUnselect(View view);
    }

    public SlidingTabLayout(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIndicatorRect = new Rect();
        this.mTabRect = new Rect();
        this.mIndicatorDrawable = new GradientDrawable();
        this.mRectPaint = new Paint(1);
        this.mDividerPaint = new Paint(1);
        this.mTrianglePaint = new Paint(1);
        this.mTrianglePath = new Path();
        this.mIndicatorStyle = 0;
        this.mTextPaint = new Paint(1);
        this.mInitSetMap = new SparseArray<>();
        setFillViewport(true);
        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);
        this.mContext = context;
        this.mTabsContainer = new LinearLayout(context);
        addView(this.mTabsContainer);
        obtainAttributes(context, attrs);
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        if (!height.equals("-1") && !height.equals("-2")) {
            TypedArray a = context.obtainStyledAttributes(attrs, new int[]{16842997});
            this.mHeight = a.getDimensionPixelSize(0, -2);
            a.recycle();
        }
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5 = 7.0f;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout);
        this.mIndicatorStyle = ta.getInt(R.styleable.SlidingTabLayout_tl_indicator_style, 0);
        this.mIndicatorColor = ta.getColor(R.styleable.SlidingTabLayout_tl_indicator_color, Color.parseColor(this.mIndicatorStyle == 2 ? "#4B6A87" : "#ffffff"));
        int i = R.styleable.SlidingTabLayout_tl_indicator_height;
        if (this.mIndicatorStyle == 1) {
            f = 4.0f;
        } else {
            f = (float) (this.mIndicatorStyle == 2 ? -1 : 2);
        }
        this.mIndicatorHeight = ta.getDimension(i, (float) dp2px(f));
        int i2 = R.styleable.SlidingTabLayout_tl_indicator_width;
        if (this.mIndicatorStyle == 1) {
            f2 = 10.0f;
        } else {
            f2 = -1.0f;
        }
        this.mIndicatorWidth = ta.getDimension(i2, (float) dp2px(f2));
        int i3 = R.styleable.SlidingTabLayout_tl_indicator_corner_radius;
        if (this.mIndicatorStyle == 2) {
            f3 = -1.0f;
        } else {
            f3 = 0.0f;
        }
        this.mIndicatorCornerRadius = ta.getDimension(i3, (float) dp2px(f3));
        this.mIndicatorMarginLeft = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_left, (float) dp2px(0.0f));
        int i4 = R.styleable.SlidingTabLayout_tl_indicator_margin_top;
        if (this.mIndicatorStyle == 2) {
            f4 = 7.0f;
        } else {
            f4 = 0.0f;
        }
        this.mIndicatorMarginTop = ta.getDimension(i4, (float) dp2px(f4));
        this.mIndicatorMarginRight = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_right, (float) dp2px(0.0f));
        int i5 = R.styleable.SlidingTabLayout_tl_indicator_margin_bottom;
        if (this.mIndicatorStyle != 2) {
            f5 = 0.0f;
        }
        this.mIndicatorMarginBottom = ta.getDimension(i5, (float) dp2px(f5));
        this.mIndicatorGravity = ta.getInt(R.styleable.SlidingTabLayout_tl_indicator_gravity, 80);
        this.mIndicatorWidthEqualTitle = ta.getBoolean(R.styleable.SlidingTabLayout_tl_indicator_width_equal_title, false);
        this.mUnderlineColor = ta.getColor(R.styleable.SlidingTabLayout_tl_underline_color, Color.parseColor("#ffffff"));
        this.mUnderlineHeight = ta.getDimension(R.styleable.SlidingTabLayout_tl_underline_height, (float) dp2px(0.0f));
        this.mUnderlineGravity = ta.getInt(R.styleable.SlidingTabLayout_tl_underline_gravity, 80);
        this.mDividerColor = ta.getColor(R.styleable.SlidingTabLayout_tl_divider_color, Color.parseColor("#ffffff"));
        this.mDividerWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_divider_width, (float) dp2px(0.0f));
        this.mDividerPadding = ta.getDimension(R.styleable.SlidingTabLayout_tl_divider_padding, (float) dp2px(12.0f));
        this.mTextsize = ta.getDimension(R.styleable.SlidingTabLayout_tl_textsize, (float) sp2px(14.0f));
        this.mTextSelectColor = ta.getColor(R.styleable.SlidingTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        this.mTextUnselectColor = ta.getColor(R.styleable.SlidingTabLayout_tl_textUnselectColor, Color.parseColor("#AAffffff"));
        this.mTextBold = ta.getBoolean(R.styleable.SlidingTabLayout_tl_textBold, false);
        this.mTextAllCaps = ta.getBoolean(R.styleable.SlidingTabLayout_tl_textAllCaps, false);
        this.mTabSpaceEqual = ta.getBoolean(R.styleable.SlidingTabLayout_tl_tab_space_equal, false);
        this.mTabWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_width, (float) dp2px(-1.0f));
        this.mTabPadding = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_padding, (this.mTabSpaceEqual || this.mTabWidth > 0.0f) ? (float) dp2px(0.0f) : (float) dp2px(20.0f));
        ta.recycle();
    }

    public void setViewPager(ViewPager vp) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        }
        this.mViewPager = vp;
        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void setViewPager(ViewPager vp, String[] titles) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        } else if (titles == null || titles.length == 0) {
            throw new IllegalStateException("Titles can not be EMPTY !");
        } else if (titles.length != vp.getAdapter().getCount()) {
            throw new IllegalStateException("Titles length must be the same as the page count !");
        } else {
            this.mViewPager = vp;
            this.mTitles = titles;
            this.mViewPager.removeOnPageChangeListener(this);
            this.mViewPager.addOnPageChangeListener(this);
            notifyDataSetChanged();
        }
    }

    public void setViewPager(ViewPager vp, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments) {
        if (vp == null) {
            throw new IllegalStateException("ViewPager can not be NULL !");
        } else if (titles == null || titles.length == 0) {
            throw new IllegalStateException("Titles can not be EMPTY !");
        } else {
            this.mViewPager = vp;
            this.mViewPager.setAdapter(new InnerPagerAdapter(fa.getSupportFragmentManager(), fragments, titles));
            this.mViewPager.removeOnPageChangeListener(this);
            this.mViewPager.addOnPageChangeListener(this);
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        View tabView;
        this.mTabsContainer.removeAllViews();
        this.mTabCount = this.mTitles == null ? this.mViewPager.getAdapter().getCount() : this.mTitles.length;
        for (int i = 0; i < this.mTabCount; i++) {
            if (this.mViewPager.getAdapter() instanceof CustomTabProvider) {
                tabView = ((CustomTabProvider) this.mViewPager.getAdapter()).getCustomTabView(this, i);
            } else {
                tabView = View.inflate(this.mContext, R.layout.layout_tab, (ViewGroup) null);
            }
            addTab(i, (this.mTitles == null ? this.mViewPager.getAdapter().getPageTitle(i) : this.mTitles[i]).toString(), tabView);
        }
        updateTabStyles();
    }

    private void addTab(final int position, String title, View tabView) {
        TextView tv_tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
        if (!(tv_tab_title == null || title == null)) {
            tv_tab_title.setText(title);
        }
        tabView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SlidingTabLayout.this.mViewPager.getCurrentItem() != position) {
                    SlidingTabLayout.this.mViewPager.setCurrentItem(position);
                    if (SlidingTabLayout.this.mListener != null) {
                        SlidingTabLayout.this.mListener.onTabSelect(position);
                    }
                } else if (SlidingTabLayout.this.mListener != null) {
                    SlidingTabLayout.this.mListener.onTabReselect(position);
                }
            }
        });
        LinearLayout.LayoutParams lp_tab = this.mTabSpaceEqual ? new LinearLayout.LayoutParams(0, -1, 1.0f) : new LinearLayout.LayoutParams(-2, -1);
        if (this.mTabWidth > 0.0f) {
            lp_tab = new LinearLayout.LayoutParams((int) this.mTabWidth, -1);
        }
        this.mTabsContainer.addView(tabView, position, lp_tab);
    }

    private void updateTabStyles() {
        int i = 0;
        while (i < this.mTabCount) {
            TextView tv_tab_title = (TextView) this.mTabsContainer.getChildAt(i).findViewById(R.id.tv_tab_title);
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(i == this.mCurrentTab ? this.mTextSelectColor : this.mTextUnselectColor);
                tv_tab_title.setTextSize(0, this.mTextsize);
                tv_tab_title.setPadding((int) this.mTabPadding, 0, (int) this.mTabPadding, 0);
                if (this.mTextAllCaps) {
                    tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
                }
                if (this.mTextBold) {
                    tv_tab_title.getPaint().setFakeBoldText(this.mTextBold);
                }
            }
            i++;
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.mCurrentTab = position;
        this.mCurrentPositionOffset = positionOffset;
        scrollToCurrentTab();
        invalidate();
    }

    public void onPageSelected(int position) {
        updateTabSelection(position);
    }

    public void onPageScrollStateChanged(int state) {
    }

    private void scrollToCurrentTab() {
        if (this.mTabCount > 0) {
            int offset = (int) (this.mCurrentPositionOffset * ((float) this.mTabsContainer.getChildAt(this.mCurrentTab).getWidth()));
            int newScrollX = this.mTabsContainer.getChildAt(this.mCurrentTab).getLeft() + offset;
            if (this.mCurrentTab > 0 || offset > 0) {
                calcIndicatorRect();
                newScrollX = (newScrollX - ((getWidth() / 2) - getPaddingLeft())) + ((this.mTabRect.right - this.mTabRect.left) / 2);
            }
            if (newScrollX != this.mLastScrollX) {
                this.mLastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }
        }
    }

    private void updateTabSelection(int position) {
        int i = 0;
        while (i < this.mTabCount) {
            View tabView = this.mTabsContainer.getChildAt(i);
            boolean isSelect = i == position;
            TextView tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
            if (tab_title != null) {
                tab_title.setTextColor(isSelect ? this.mTextSelectColor : this.mTextUnselectColor);
            }
            if (this.mViewPager.getAdapter() instanceof CustomTabProvider) {
                if (isSelect) {
                    ((CustomTabProvider) this.mViewPager.getAdapter()).tabSelect(tabView);
                } else {
                    ((CustomTabProvider) this.mViewPager.getAdapter()).tabUnselect(tabView);
                }
            }
            i++;
        }
    }

    private void calcIndicatorRect() {
        View currentTabView = this.mTabsContainer.getChildAt(this.mCurrentTab);
        float left = (float) currentTabView.getLeft();
        float right = (float) currentTabView.getRight();
        if (this.mIndicatorStyle == 0 && this.mIndicatorWidthEqualTitle) {
            this.mTextPaint.setTextSize(this.mTextsize);
            this.margin = ((right - left) - this.mTextPaint.measureText(((TextView) currentTabView.findViewById(R.id.tv_tab_title)).getText().toString())) / 2.0f;
        }
        if (this.mCurrentTab < this.mTabCount - 1) {
            View nextTabView = this.mTabsContainer.getChildAt(this.mCurrentTab + 1);
            float nextTabLeft = (float) nextTabView.getLeft();
            float nextTabRight = (float) nextTabView.getRight();
            left += this.mCurrentPositionOffset * (nextTabLeft - left);
            right += this.mCurrentPositionOffset * (nextTabRight - right);
            if (this.mIndicatorStyle == 0 && this.mIndicatorWidthEqualTitle) {
                this.mTextPaint.setTextSize(this.mTextsize);
                this.margin += this.mCurrentPositionOffset * ((((nextTabRight - nextTabLeft) - this.mTextPaint.measureText(((TextView) nextTabView.findViewById(R.id.tv_tab_title)).getText().toString())) / 2.0f) - this.margin);
            }
        }
        this.mIndicatorRect.left = (int) left;
        this.mIndicatorRect.right = (int) right;
        if (this.mIndicatorStyle == 0 && this.mIndicatorWidthEqualTitle) {
            this.mIndicatorRect.left = (int) ((this.margin + left) - 1.0f);
            this.mIndicatorRect.right = (int) ((right - this.margin) - 1.0f);
        }
        this.mTabRect.left = (int) left;
        this.mTabRect.right = (int) right;
        if (this.mIndicatorWidth >= 0.0f) {
            float indicatorLeft = ((float) currentTabView.getLeft()) + ((((float) currentTabView.getWidth()) - this.mIndicatorWidth) / 2.0f);
            if (this.mCurrentTab < this.mTabCount - 1) {
                indicatorLeft += this.mCurrentPositionOffset * ((float) ((currentTabView.getWidth() / 2) + (this.mTabsContainer.getChildAt(this.mCurrentTab + 1).getWidth() / 2)));
            }
            this.mIndicatorRect.left = (int) indicatorLeft;
            this.mIndicatorRect.right = (int) (((float) this.mIndicatorRect.left) + this.mIndicatorWidth);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode() && this.mTabCount > 0) {
            int height = getHeight();
            int paddingLeft = getPaddingLeft();
            if (this.mDividerWidth > 0.0f) {
                this.mDividerPaint.setStrokeWidth(this.mDividerWidth);
                this.mDividerPaint.setColor(this.mDividerColor);
                for (int i = 0; i < this.mTabCount - 1; i++) {
                    View tab = this.mTabsContainer.getChildAt(i);
                    canvas.drawLine((float) (tab.getRight() + paddingLeft), this.mDividerPadding, (float) (tab.getRight() + paddingLeft), ((float) height) - this.mDividerPadding, this.mDividerPaint);
                }
            }
            if (this.mUnderlineHeight > 0.0f) {
                this.mRectPaint.setColor(this.mUnderlineColor);
                if (this.mUnderlineGravity == 80) {
                    canvas.drawRect((float) paddingLeft, ((float) height) - this.mUnderlineHeight, (float) (this.mTabsContainer.getWidth() + paddingLeft), (float) height, this.mRectPaint);
                } else {
                    canvas.drawRect((float) paddingLeft, 0.0f, (float) (this.mTabsContainer.getWidth() + paddingLeft), this.mUnderlineHeight, this.mRectPaint);
                }
            }
            calcIndicatorRect();
            if (this.mIndicatorStyle == 1) {
                if (this.mIndicatorHeight > 0.0f) {
                    this.mTrianglePaint.setColor(this.mIndicatorColor);
                    this.mTrianglePath.reset();
                    this.mTrianglePath.moveTo((float) (this.mIndicatorRect.left + paddingLeft), (float) height);
                    this.mTrianglePath.lineTo((float) ((this.mIndicatorRect.left / 2) + paddingLeft + (this.mIndicatorRect.right / 2)), ((float) height) - this.mIndicatorHeight);
                    this.mTrianglePath.lineTo((float) (this.mIndicatorRect.right + paddingLeft), (float) height);
                    this.mTrianglePath.close();
                    canvas.drawPath(this.mTrianglePath, this.mTrianglePaint);
                }
            } else if (this.mIndicatorStyle == 2) {
                if (this.mIndicatorHeight < 0.0f) {
                    this.mIndicatorHeight = (((float) height) - this.mIndicatorMarginTop) - this.mIndicatorMarginBottom;
                }
                if (this.mIndicatorHeight > 0.0f) {
                    if (this.mIndicatorCornerRadius < 0.0f || this.mIndicatorCornerRadius > this.mIndicatorHeight / 2.0f) {
                        this.mIndicatorCornerRadius = this.mIndicatorHeight / 2.0f;
                    }
                    this.mIndicatorDrawable.setColor(this.mIndicatorColor);
                    this.mIndicatorDrawable.setBounds(((int) this.mIndicatorMarginLeft) + paddingLeft + this.mIndicatorRect.left, (int) this.mIndicatorMarginTop, (int) (((float) (this.mIndicatorRect.right + paddingLeft)) - this.mIndicatorMarginRight), (int) (this.mIndicatorMarginTop + this.mIndicatorHeight));
                    this.mIndicatorDrawable.setCornerRadius(this.mIndicatorCornerRadius);
                    this.mIndicatorDrawable.draw(canvas);
                }
            } else if (this.mIndicatorHeight > 0.0f) {
                this.mIndicatorDrawable.setColor(this.mIndicatorColor);
                if (this.mIndicatorGravity == 80) {
                    this.mIndicatorDrawable.setBounds(((int) this.mIndicatorMarginLeft) + paddingLeft + this.mIndicatorRect.left, (height - ((int) this.mIndicatorHeight)) - ((int) this.mIndicatorMarginBottom), (this.mIndicatorRect.right + paddingLeft) - ((int) this.mIndicatorMarginRight), height - ((int) this.mIndicatorMarginBottom));
                } else {
                    this.mIndicatorDrawable.setBounds(((int) this.mIndicatorMarginLeft) + paddingLeft + this.mIndicatorRect.left, (int) this.mIndicatorMarginTop, (this.mIndicatorRect.right + paddingLeft) - ((int) this.mIndicatorMarginRight), ((int) this.mIndicatorHeight) + ((int) this.mIndicatorMarginTop));
                }
                this.mIndicatorDrawable.setCornerRadius(this.mIndicatorCornerRadius);
                this.mIndicatorDrawable.draw(canvas);
            }
        }
    }

    public void setCurrentTab(int currentTab) {
        this.mCurrentTab = currentTab;
        this.mViewPager.setCurrentItem(currentTab);
    }

    public void setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
        invalidate();
    }

    public void setTabPadding(float tabPadding) {
        this.mTabPadding = (float) dp2px(tabPadding);
        updateTabStyles();
    }

    public void setTabSpaceEqual(boolean tabSpaceEqual) {
        this.mTabSpaceEqual = tabSpaceEqual;
        updateTabStyles();
    }

    public void setTabWidth(float tabWidth) {
        this.mTabWidth = (float) dp2px(tabWidth);
        updateTabStyles();
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = (float) dp2px(indicatorHeight);
        invalidate();
    }

    public void setIndicatorWidth(float indicatorWidth) {
        this.mIndicatorWidth = (float) dp2px(indicatorWidth);
        invalidate();
    }

    public void setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = (float) dp2px(indicatorCornerRadius);
        invalidate();
    }

    public void setIndicatorGravity(int indicatorGravity) {
        this.mIndicatorGravity = indicatorGravity;
        invalidate();
    }

    public void setIndicatorMargin(float indicatorMarginLeft, float indicatorMarginTop, float indicatorMarginRight, float indicatorMarginBottom) {
        this.mIndicatorMarginLeft = (float) dp2px(indicatorMarginLeft);
        this.mIndicatorMarginTop = (float) dp2px(indicatorMarginTop);
        this.mIndicatorMarginRight = (float) dp2px(indicatorMarginRight);
        this.mIndicatorMarginBottom = (float) dp2px(indicatorMarginBottom);
        invalidate();
    }

    public void setIndicatorWidthEqualTitle(boolean indicatorWidthEqualTitle) {
        this.mIndicatorWidthEqualTitle = indicatorWidthEqualTitle;
        invalidate();
    }

    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = (float) dp2px(underlineHeight);
        invalidate();
    }

    public void setUnderlineGravity(int underlineGravity) {
        this.mUnderlineGravity = underlineGravity;
        invalidate();
    }

    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    public void setDividerWidth(float dividerWidth) {
        this.mDividerWidth = (float) dp2px(dividerWidth);
        invalidate();
    }

    public void setDividerPadding(float dividerPadding) {
        this.mDividerPadding = (float) dp2px(dividerPadding);
        invalidate();
    }

    public void setTextsize(float textsize) {
        this.mTextsize = (float) sp2px(textsize);
        updateTabStyles();
    }

    public void setTextSelectColor(int textSelectColor) {
        this.mTextSelectColor = textSelectColor;
        updateTabStyles();
    }

    public void setTextUnselectColor(int textUnselectColor) {
        this.mTextUnselectColor = textUnselectColor;
        updateTabStyles();
    }

    public void setTextBold(boolean textBold) {
        this.mTextBold = textBold;
        updateTabStyles();
    }

    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        updateTabStyles();
    }

    public int getTabCount() {
        return this.mTabCount;
    }

    public int getCurrentTab() {
        return this.mCurrentTab;
    }

    public int getIndicatorStyle() {
        return this.mIndicatorStyle;
    }

    public float getTabPadding() {
        return this.mTabPadding;
    }

    public boolean isTabSpaceEqual() {
        return this.mTabSpaceEqual;
    }

    public float getTabWidth() {
        return this.mTabWidth;
    }

    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public float getIndicatorHeight() {
        return this.mIndicatorHeight;
    }

    public float getIndicatorWidth() {
        return this.mIndicatorWidth;
    }

    public float getIndicatorCornerRadius() {
        return this.mIndicatorCornerRadius;
    }

    public float getIndicatorMarginLeft() {
        return this.mIndicatorMarginLeft;
    }

    public float getIndicatorMarginTop() {
        return this.mIndicatorMarginTop;
    }

    public float getIndicatorMarginRight() {
        return this.mIndicatorMarginRight;
    }

    public float getIndicatorMarginBottom() {
        return this.mIndicatorMarginBottom;
    }

    public int getUnderlineColor() {
        return this.mUnderlineColor;
    }

    public float getUnderlineHeight() {
        return this.mUnderlineHeight;
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }

    public float getDividerWidth() {
        return this.mDividerWidth;
    }

    public float getDividerPadding() {
        return this.mDividerPadding;
    }

    public float getTextsize() {
        return this.mTextsize;
    }

    public int getTextSelectColor() {
        return this.mTextSelectColor;
    }

    public int getTextUnselectColor() {
        return this.mTextUnselectColor;
    }

    public boolean isTextBold() {
        return this.mTextBold;
    }

    public boolean isTextAllCaps() {
        return this.mTextAllCaps;
    }

    public TextView getTitleView(int tab) {
        return (TextView) this.mTabsContainer.getChildAt(tab).findViewById(R.id.tv_tab_title);
    }

    public void showMsg(int position, int num) {
        if (position >= this.mTabCount) {
            position = this.mTabCount - 1;
        }
        MsgView tipView = (MsgView) this.mTabsContainer.getChildAt(position).findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            UnreadMsgUtils.show(tipView, num);
            if (this.mInitSetMap.get(position) == null || !this.mInitSetMap.get(position).booleanValue()) {
                setMsgMargin(position, 4.0f, 2.0f);
                this.mInitSetMap.put(position, true);
            }
        }
    }

    public void showDot(int position) {
        if (position >= this.mTabCount) {
            position = this.mTabCount - 1;
        }
        showMsg(position, 0);
    }

    public void hideMsg(int position) {
        if (position >= this.mTabCount) {
            position = this.mTabCount - 1;
        }
        MsgView tipView = (MsgView) this.mTabsContainer.getChildAt(position).findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            tipView.setVisibility(8);
        }
    }

    public void setMsgMargin(int position, float leftPadding, float bottomPadding) {
        if (position >= this.mTabCount) {
            position = this.mTabCount - 1;
        }
        View tabView = this.mTabsContainer.getChildAt(position);
        MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            this.mTextPaint.setTextSize(this.mTextsize);
            float textWidth = this.mTextPaint.measureText(((TextView) tabView.findViewById(R.id.tv_tab_title)).getText().toString());
            float textHeight = this.mTextPaint.descent() - this.mTextPaint.ascent();
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) tipView.getLayoutParams();
            lp.leftMargin = this.mTabWidth >= 0.0f ? (int) ((this.mTabWidth / 2.0f) + (textWidth / 2.0f) + ((float) dp2px(leftPadding))) : (int) (this.mTabPadding + textWidth + ((float) dp2px(leftPadding)));
            lp.topMargin = this.mHeight > 0 ? (((int) (((float) this.mHeight) - textHeight)) / 2) - dp2px(bottomPadding) : 0;
            tipView.setLayoutParams(lp);
        }
    }

    public MsgView getMsgView(int position) {
        if (position >= this.mTabCount) {
            position = this.mTabCount - 1;
        }
        return (MsgView) this.mTabsContainer.getChildAt(position).findViewById(R.id.rtv_msg_tip);
    }

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }

    class InnerPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<>();
        private String[] titles;

        public InnerPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments2, String[] titles2) {
            super(fm);
            this.fragments = fragments2;
            this.titles = titles2;
        }

        public int getCount() {
            return this.fragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return this.titles[position];
        }

        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        public int getItemPosition(Object object) {
            return -2;
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", this.mCurrentTab);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (this.mCurrentTab != 0 && this.mTabsContainer.getChildCount() > 0) {
                updateTabSelection(this.mCurrentTab);
                scrollToCurrentTab();
            }
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public int dp2px(float dp) {
        return (int) ((dp * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* access modifiers changed from: protected */
    public int sp2px(float sp) {
        return (int) ((sp * this.mContext.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}

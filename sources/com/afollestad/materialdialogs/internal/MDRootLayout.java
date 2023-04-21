package com.afollestad.materialdialogs.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ScrollView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R;
import com.afollestad.materialdialogs.util.DialogUtils;

public class MDRootLayout extends ViewGroup {
    private static final int INDEX_NEGATIVE = 1;
    private static final int INDEX_NEUTRAL = 0;
    private static final int INDEX_POSITIVE = 2;
    private ViewTreeObserver.OnScrollChangedListener mBottomOnScrollChangedListener;
    private int mButtonBarHeight;
    private GravityEnum mButtonGravity = GravityEnum.START;
    private int mButtonHorizontalEdgeMargin;
    private int mButtonPaddingFull;
    /* access modifiers changed from: private */
    public final MDButton[] mButtons = new MDButton[3];
    private View mContent;
    private Paint mDividerPaint;
    private int mDividerWidth;
    /* access modifiers changed from: private */
    public boolean mDrawBottomDivider = false;
    /* access modifiers changed from: private */
    public boolean mDrawTopDivider = false;
    private boolean mForceStack = false;
    private boolean mIsStacked = false;
    private boolean mNoTitleNoPadding;
    private int mNoTitlePaddingFull;
    private boolean mReducePaddingNoTitleNoButtons;
    private View mTitleBar;
    private ViewTreeObserver.OnScrollChangedListener mTopOnScrollChangedListener;
    private boolean mUseFullPadding = true;

    public MDRootLayout(Context context) {
        super(context);
        init(context, (AttributeSet) null, 0);
    }

    public MDRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    @TargetApi(11)
    public MDRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MDRootLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        Resources r = context.getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MDRootLayout, defStyleAttr, 0);
        this.mReducePaddingNoTitleNoButtons = a.getBoolean(R.styleable.MDRootLayout_md_reduce_padding_no_title_no_buttons, true);
        a.recycle();
        this.mNoTitlePaddingFull = r.getDimensionPixelSize(R.dimen.md_notitle_vertical_padding);
        this.mButtonPaddingFull = r.getDimensionPixelSize(R.dimen.md_button_frame_vertical_padding);
        this.mButtonHorizontalEdgeMargin = r.getDimensionPixelSize(R.dimen.md_button_padding_frame_side);
        this.mButtonBarHeight = r.getDimensionPixelSize(R.dimen.md_button_height);
        this.mDividerPaint = new Paint();
        this.mDividerWidth = r.getDimensionPixelSize(R.dimen.md_divider_height);
        this.mDividerPaint.setColor(DialogUtils.resolveColor(context, R.attr.md_divider_color));
        setWillNotDraw(false);
    }

    public void noTitleNoPadding() {
        this.mNoTitleNoPadding = true;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v.getId() == R.id.titleFrame) {
                this.mTitleBar = v;
            } else if (v.getId() == R.id.buttonDefaultNeutral) {
                this.mButtons[0] = (MDButton) v;
            } else if (v.getId() == R.id.buttonDefaultNegative) {
                this.mButtons[1] = (MDButton) v;
            } else if (v.getId() == R.id.buttonDefaultPositive) {
                this.mButtons[2] = (MDButton) v;
            } else {
                this.mContent = v;
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean stacked;
        int fullPadding;
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        this.mUseFullPadding = true;
        boolean hasButtons = false;
        if (!this.mForceStack) {
            int buttonsWidth = 0;
            for (MDButton button : this.mButtons) {
                if (button != null && isVisible(button)) {
                    button.setStacked(false, false);
                    measureChild(button, widthMeasureSpec, heightMeasureSpec);
                    buttonsWidth += button.getMeasuredWidth();
                    hasButtons = true;
                }
            }
            stacked = buttonsWidth > width - (getContext().getResources().getDimensionPixelSize(R.dimen.md_neutral_button_margin) * 2);
        } else {
            stacked = true;
        }
        int stackedHeight = 0;
        this.mIsStacked = stacked;
        if (stacked) {
            for (MDButton button2 : this.mButtons) {
                if (button2 != null && isVisible(button2)) {
                    button2.setStacked(true, false);
                    measureChild(button2, widthMeasureSpec, heightMeasureSpec);
                    stackedHeight += button2.getMeasuredHeight();
                    hasButtons = true;
                }
            }
        }
        int availableHeight = height;
        int minPadding = 0;
        if (!hasButtons) {
            fullPadding = 0 + (this.mButtonPaddingFull * 2);
        } else if (this.mIsStacked) {
            availableHeight -= stackedHeight;
            fullPadding = 0 + (this.mButtonPaddingFull * 2);
            minPadding = 0 + (this.mButtonPaddingFull * 2);
        } else {
            availableHeight -= this.mButtonBarHeight;
            fullPadding = 0 + (this.mButtonPaddingFull * 2);
        }
        if (isVisible(this.mTitleBar)) {
            this.mTitleBar.measure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), 0);
            availableHeight -= this.mTitleBar.getMeasuredHeight();
        } else if (!this.mNoTitleNoPadding) {
            fullPadding += this.mNoTitlePaddingFull;
        }
        if (isVisible(this.mContent)) {
            this.mContent.measure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), View.MeasureSpec.makeMeasureSpec(availableHeight - minPadding, Integer.MIN_VALUE));
            if (this.mContent.getMeasuredHeight() > availableHeight - fullPadding) {
                this.mUseFullPadding = false;
                availableHeight = 0;
            } else if (!this.mReducePaddingNoTitleNoButtons || isVisible(this.mTitleBar) || hasButtons) {
                this.mUseFullPadding = true;
                availableHeight -= this.mContent.getMeasuredHeight() + fullPadding;
            } else {
                this.mUseFullPadding = false;
                availableHeight -= this.mContent.getMeasuredHeight() + minPadding;
            }
        }
        setMeasuredDimension(width, height - availableHeight);
    }

    private static boolean isVisible(View v) {
        boolean visible;
        if (v == null || v.getVisibility() == 8) {
            visible = false;
        } else {
            visible = true;
        }
        if (!visible || !(v instanceof MDButton)) {
            return visible;
        }
        if (((MDButton) v).getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mContent != null) {
            if (this.mDrawTopDivider) {
                int y = this.mContent.getTop();
                canvas.drawRect(0.0f, (float) (y - this.mDividerWidth), (float) getMeasuredWidth(), (float) y, this.mDividerPaint);
            }
            if (this.mDrawBottomDivider) {
                int y2 = this.mContent.getBottom();
                canvas.drawRect(0.0f, (float) y2, (float) getMeasuredWidth(), (float) (this.mDividerWidth + y2), this.mDividerPaint);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int bl;
        int br;
        int bl2;
        int br2;
        int br3;
        int bl3;
        if (isVisible(this.mTitleBar)) {
            int height = this.mTitleBar.getMeasuredHeight();
            this.mTitleBar.layout(l, t, r, t + height);
            t += height;
        } else if (!this.mNoTitleNoPadding && this.mUseFullPadding) {
            t += this.mNoTitlePaddingFull;
        }
        if (isVisible(this.mContent)) {
            this.mContent.layout(l, t, r, this.mContent.getMeasuredHeight() + t);
        }
        if (this.mIsStacked) {
            int b2 = b - this.mButtonPaddingFull;
            for (MDButton mButton : this.mButtons) {
                if (isVisible(mButton)) {
                    mButton.layout(l, b2 - mButton.getMeasuredHeight(), r, b2);
                    b2 -= mButton.getMeasuredHeight();
                }
            }
        } else {
            int barBottom = b;
            if (this.mUseFullPadding) {
                barBottom -= this.mButtonPaddingFull;
            }
            int barTop = barBottom - this.mButtonBarHeight;
            int offset = this.mButtonHorizontalEdgeMargin;
            int neutralLeft = -1;
            int neutralRight = -1;
            if (isVisible(this.mButtons[2])) {
                if (this.mButtonGravity == GravityEnum.END) {
                    bl3 = l + offset;
                    br3 = bl3 + this.mButtons[2].getMeasuredWidth();
                } else {
                    br3 = r - offset;
                    bl3 = br3 - this.mButtons[2].getMeasuredWidth();
                    neutralRight = bl3;
                }
                this.mButtons[2].layout(bl3, barTop, br3, barBottom);
                offset += this.mButtons[2].getMeasuredWidth();
            }
            if (isVisible(this.mButtons[1])) {
                if (this.mButtonGravity == GravityEnum.END) {
                    bl2 = l + offset;
                    br2 = bl2 + this.mButtons[1].getMeasuredWidth();
                } else if (this.mButtonGravity == GravityEnum.START) {
                    br2 = r - offset;
                    bl2 = br2 - this.mButtons[1].getMeasuredWidth();
                } else {
                    bl2 = l + this.mButtonHorizontalEdgeMargin;
                    br2 = bl2 + this.mButtons[1].getMeasuredWidth();
                    neutralLeft = br2;
                }
                this.mButtons[1].layout(bl2, barTop, br2, barBottom);
            }
            if (isVisible(this.mButtons[0])) {
                if (this.mButtonGravity == GravityEnum.END) {
                    br = r - this.mButtonHorizontalEdgeMargin;
                    bl = br - this.mButtons[0].getMeasuredWidth();
                } else if (this.mButtonGravity == GravityEnum.START) {
                    bl = l + this.mButtonHorizontalEdgeMargin;
                    br = bl + this.mButtons[0].getMeasuredWidth();
                } else {
                    if (neutralLeft == -1 && neutralRight != -1) {
                        neutralLeft = neutralRight - this.mButtons[0].getMeasuredWidth();
                    } else if (neutralRight == -1 && neutralLeft != -1) {
                        neutralRight = neutralLeft + this.mButtons[0].getMeasuredWidth();
                    } else if (neutralRight == -1) {
                        neutralLeft = ((r - l) / 2) - (this.mButtons[0].getMeasuredWidth() / 2);
                        neutralRight = neutralLeft + this.mButtons[0].getMeasuredWidth();
                    }
                    bl = neutralLeft;
                    br = neutralRight;
                }
                this.mButtons[0].layout(bl, barTop, br, barBottom);
            }
        }
        setUpDividersVisibility(this.mContent, true, true);
    }

    public void setForceStack(boolean forceStack) {
        this.mForceStack = forceStack;
        invalidate();
    }

    public void setDividerColor(int color) {
        this.mDividerPaint.setColor(color);
        invalidate();
    }

    public void setButtonGravity(GravityEnum gravity) {
        this.mButtonGravity = gravity;
        invertGravityIfNecessary();
    }

    private void invertGravityIfNecessary() {
        if (Build.VERSION.SDK_INT >= 17 && getResources().getConfiguration().getLayoutDirection() == 1) {
            switch (this.mButtonGravity) {
                case START:
                    this.mButtonGravity = GravityEnum.END;
                    return;
                case END:
                    this.mButtonGravity = GravityEnum.START;
                    return;
                default:
                    return;
            }
        }
    }

    public void setButtonStackedGravity(GravityEnum gravity) {
        for (MDButton mButton : this.mButtons) {
            if (mButton != null) {
                mButton.setStackedGravity(gravity);
            }
        }
    }

    private void setUpDividersVisibility(final View view, final boolean setForTop, final boolean setForBottom) {
        if (view != null) {
            if (view instanceof ScrollView) {
                ScrollView sv = (ScrollView) view;
                if (canScrollViewScroll(sv)) {
                    addScrollListener(sv, setForTop, setForBottom);
                    return;
                }
                if (setForTop) {
                    this.mDrawTopDivider = false;
                }
                if (setForBottom) {
                    this.mDrawBottomDivider = false;
                }
            } else if (view instanceof AdapterView) {
                AdapterView sv2 = (AdapterView) view;
                if (canAdapterViewScroll(sv2)) {
                    addScrollListener(sv2, setForTop, setForBottom);
                    return;
                }
                if (setForTop) {
                    this.mDrawTopDivider = false;
                }
                if (setForBottom) {
                    this.mDrawBottomDivider = false;
                }
            } else if (view instanceof WebView) {
                view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        if (view.getMeasuredHeight() == 0) {
                            return true;
                        }
                        if (!MDRootLayout.canWebViewScroll((WebView) view)) {
                            if (setForTop) {
                                boolean unused = MDRootLayout.this.mDrawTopDivider = false;
                            }
                            if (setForBottom) {
                                boolean unused2 = MDRootLayout.this.mDrawBottomDivider = false;
                            }
                        } else {
                            MDRootLayout.this.addScrollListener((ViewGroup) view, setForTop, setForBottom);
                        }
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });
            } else if (view instanceof RecyclerView) {
                boolean canScroll = canRecyclerViewScroll((RecyclerView) view);
                if (setForTop) {
                    this.mDrawTopDivider = canScroll;
                }
                if (setForBottom) {
                    this.mDrawBottomDivider = canScroll;
                }
            } else if (view instanceof ViewGroup) {
                View topView = getTopView((ViewGroup) view);
                setUpDividersVisibility(topView, setForTop, setForBottom);
                View bottomView = getBottomView((ViewGroup) view);
                if (bottomView != topView) {
                    setUpDividersVisibility(bottomView, false, true);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void addScrollListener(final ViewGroup vg, final boolean setForTop, final boolean setForBottom) {
        if ((!setForBottom && this.mTopOnScrollChangedListener == null) || (setForBottom && this.mBottomOnScrollChangedListener == null)) {
            ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
                public void onScrollChanged() {
                    boolean hasButtons = false;
                    MDButton[] access$400 = MDRootLayout.this.mButtons;
                    int length = access$400.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            MDButton button = access$400[i];
                            if (button != null && button.getVisibility() != 8) {
                                hasButtons = true;
                                break;
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                    if (vg instanceof WebView) {
                        MDRootLayout.this.invalidateDividersForWebView((WebView) vg, setForTop, setForBottom, hasButtons);
                    } else {
                        MDRootLayout.this.invalidateDividersForScrollingView(vg, setForTop, setForBottom, hasButtons);
                    }
                    MDRootLayout.this.invalidate();
                }
            };
            if (!setForBottom) {
                this.mTopOnScrollChangedListener = onScrollChangedListener;
                vg.getViewTreeObserver().addOnScrollChangedListener(this.mTopOnScrollChangedListener);
            } else {
                this.mBottomOnScrollChangedListener = onScrollChangedListener;
                vg.getViewTreeObserver().addOnScrollChangedListener(this.mBottomOnScrollChangedListener);
            }
            onScrollChangedListener.onScrollChanged();
        }
    }

    /* access modifiers changed from: private */
    public void invalidateDividersForScrollingView(ViewGroup view, boolean setForTop, boolean setForBottom, boolean hasButtons) {
        boolean z = true;
        if (setForTop && view.getChildCount() > 0) {
            this.mDrawTopDivider = (this.mTitleBar == null || this.mTitleBar.getVisibility() == 8 || view.getScrollY() + view.getPaddingTop() <= view.getChildAt(0).getTop()) ? false : true;
        }
        if (setForBottom && view.getChildCount() > 0) {
            if (!hasButtons || (view.getScrollY() + view.getHeight()) - view.getPaddingBottom() >= view.getChildAt(view.getChildCount() - 1).getBottom()) {
                z = false;
            }
            this.mDrawBottomDivider = z;
        }
    }

    /* access modifiers changed from: private */
    public void invalidateDividersForWebView(WebView view, boolean setForTop, boolean setForBottom, boolean hasButtons) {
        boolean z = true;
        if (setForTop) {
            this.mDrawTopDivider = (this.mTitleBar == null || this.mTitleBar.getVisibility() == 8 || view.getScrollY() + view.getPaddingTop() <= 0) ? false : true;
        }
        if (setForBottom) {
            if (!hasButtons || ((float) ((view.getScrollY() + view.getMeasuredHeight()) - view.getPaddingBottom())) >= ((float) view.getContentHeight()) * view.getScale()) {
                z = false;
            }
            this.mDrawBottomDivider = z;
        }
    }

    public static boolean canRecyclerViewScroll(RecyclerView view) {
        boolean lastItemVisible;
        if (view == null || view.getAdapter() == null || view.getLayoutManager() == null) {
            return false;
        }
        RecyclerView.LayoutManager lm = view.getLayoutManager();
        int count = view.getAdapter().getItemCount();
        if (lm instanceof LinearLayoutManager) {
            int lastVisible = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
            if (lastVisible == -1) {
                return false;
            }
            if (lastVisible == count - 1) {
                lastItemVisible = true;
            } else {
                lastItemVisible = false;
            }
            if (!lastItemVisible || (view.getChildCount() > 0 && view.getChildAt(view.getChildCount() - 1).getBottom() > view.getHeight() - view.getPaddingBottom())) {
                return true;
            }
            return false;
        }
        throw new MaterialDialog.NotImplementedException("Material Dialogs currently only supports LinearLayoutManager. Please report any new layout managers.");
    }

    private static boolean canScrollViewScroll(ScrollView sv) {
        if (sv.getChildCount() != 0 && (sv.getMeasuredHeight() - sv.getPaddingTop()) - sv.getPaddingBottom() < sv.getChildAt(0).getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean canWebViewScroll(WebView view) {
        return ((float) view.getMeasuredHeight()) < ((float) view.getContentHeight()) * view.getScale();
    }

    private static boolean canAdapterViewScroll(AdapterView lv) {
        boolean firstItemVisible;
        boolean lastItemVisible;
        if (lv.getLastVisiblePosition() == -1) {
            return false;
        }
        if (lv.getFirstVisiblePosition() == 0) {
            firstItemVisible = true;
        } else {
            firstItemVisible = false;
        }
        if (lv.getLastVisiblePosition() == lv.getCount() - 1) {
            lastItemVisible = true;
        } else {
            lastItemVisible = false;
        }
        if (!firstItemVisible || !lastItemVisible || lv.getChildCount() <= 0 || lv.getChildAt(0).getTop() < lv.getPaddingTop() || lv.getChildAt(lv.getChildCount() - 1).getBottom() > lv.getHeight() - lv.getPaddingBottom()) {
            return true;
        }
        return false;
    }

    @Nullable
    private static View getBottomView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
            View child = viewGroup.getChildAt(i);
            if (child.getVisibility() == 0 && child.getBottom() == viewGroup.getMeasuredHeight()) {
                return child;
            }
        }
        return null;
    }

    @Nullable
    private static View getTopView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
            View child = viewGroup.getChildAt(i);
            if (child.getVisibility() == 0 && child.getTop() == 0) {
                return child;
            }
        }
        return null;
    }
}

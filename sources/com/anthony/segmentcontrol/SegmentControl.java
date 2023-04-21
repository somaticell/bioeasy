package com.anthony.segmentcontrol;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.edu.anthony.segmentcontrol.R;

public class SegmentControl extends View {
    private int DEFAULT_NORMAL_COLOR;
    private int DEFAULT_SELECTED_COLOR;
    private boolean inTapRegion;
    private ColorStateList mBackgroundColors;
    private RadiusDrawable mBackgroundDrawable;
    private int mBoundWidth;
    private Rect[] mCacheBounds;
    private Paint.FontMetrics mCachedFM;
    private int mCornerRadius;
    private int mCurrentIndex;
    private float mCurrentX;
    private float mCurrentY;
    private Direction mDirection;
    private int mHorizonGap;
    private OnSegmentControlClickListener mOnSegmentControlClickListener;
    private Paint mPaint;
    private RadiusDrawable mSelectedDrawable;
    private int mSeparatorWidth;
    private int mSingleChildHeight;
    private int mSingleChildWidth;
    private float mStartX;
    private float mStartY;
    private Rect[] mTextBounds;
    private ColorStateList mTextColors;
    private int mTextSize;
    private String[] mTexts;
    private int mTouchSlop;
    private int mVerticalGap;

    public interface OnSegmentControlClickListener {
        void onSegmentControlClick(int i);
    }

    public enum Direction {
        HORIZONTAL(0),
        VERTICAL(1);
        
        int value;

        private Direction(int v) {
            this.value = v;
        }
    }

    public SegmentControl(Context context) {
        this(context, (AttributeSet) null);
    }

    public SegmentControl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        int touchSlop;
        this.mBoundWidth = 4;
        this.mSeparatorWidth = this.mBoundWidth / 2;
        this.DEFAULT_SELECTED_COLOR = -13455873;
        this.DEFAULT_NORMAL_COLOR = -1;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentControl);
        String textArray = ta.getString(R.styleable.SegmentControl_texts);
        if (textArray != null) {
            this.mTexts = textArray.split("\\|");
        }
        this.mTextSize = ta.getDimensionPixelSize(R.styleable.SegmentControl_android_textSize, (int) TypedValue.applyDimension(2, 14.0f, context.getResources().getDisplayMetrics()));
        this.mCornerRadius = ta.getDimensionPixelSize(R.styleable.SegmentControl_cornerRadius, (int) TypedValue.applyDimension(1, 5.0f, context.getResources().getDisplayMetrics()));
        this.mDirection = Direction.values()[ta.getInt(R.styleable.SegmentControl_block_direction, 0)];
        this.mHorizonGap = ta.getDimensionPixelSize(R.styleable.SegmentControl_horizonGap, 0);
        this.mVerticalGap = ta.getDimensionPixelSize(R.styleable.SegmentControl_verticalGap, 0);
        int gap = ta.getDimensionPixelSize(R.styleable.SegmentControl_gaps, (int) TypedValue.applyDimension(1, 2.0f, context.getResources().getDisplayMetrics()));
        if (this.mHorizonGap == 0) {
            this.mHorizonGap = gap;
        }
        if (this.mVerticalGap == 0) {
            this.mVerticalGap = gap;
        }
        this.mBackgroundDrawable = new RadiusDrawable(this.mCornerRadius, true);
        this.mBackgroundDrawable.setStrokeWidth(2);
        this.DEFAULT_NORMAL_COLOR = ta.getColor(R.styleable.SegmentControl_normalColor, this.DEFAULT_NORMAL_COLOR);
        this.DEFAULT_SELECTED_COLOR = ta.getColor(R.styleable.SegmentControl_sc_selectedColor, this.DEFAULT_SELECTED_COLOR);
        this.mBackgroundColors = ta.getColorStateList(R.styleable.SegmentControl_backgroundColors);
        this.mTextColors = ta.getColorStateList(R.styleable.SegmentControl_textColors);
        if (this.mBackgroundColors == null) {
            this.mBackgroundColors = new ColorStateList(new int[][]{new int[]{16842913}, new int[]{-16842913}}, new int[]{this.DEFAULT_SELECTED_COLOR, this.DEFAULT_NORMAL_COLOR});
        }
        if (this.mTextColors == null) {
            this.mTextColors = new ColorStateList(new int[][]{new int[]{16842913}, new int[]{-16842913}}, new int[]{this.DEFAULT_NORMAL_COLOR, this.DEFAULT_SELECTED_COLOR});
        }
        this.mBoundWidth = ta.getDimensionPixelSize(R.styleable.SegmentControl_boundWidth, this.mBoundWidth);
        this.mSeparatorWidth = ta.getDimensionPixelSize(R.styleable.SegmentControl_separatorWidth, this.mSeparatorWidth);
        ta.recycle();
        this.mBackgroundDrawable = new RadiusDrawable(this.mCornerRadius, true);
        this.mBackgroundDrawable.setStrokeWidth(this.mBoundWidth);
        this.mBackgroundDrawable.setStrokeColor(getSelectedBGColor());
        this.mBackgroundDrawable.setFillColor(getNormalBGColor());
        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(this.mBackgroundDrawable);
        } else {
            setBackground(this.mBackgroundDrawable);
        }
        this.mSelectedDrawable = new RadiusDrawable(false);
        this.mSelectedDrawable.setFillColor(getSelectedBGColor());
        this.mPaint = new Paint(1);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mCachedFM = this.mPaint.getFontMetrics();
        if (context == null) {
            touchSlop = ViewConfiguration.getTouchSlop();
        } else {
            touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }
        this.mTouchSlop = touchSlop * touchSlop;
        this.inTapRegion = false;
    }

    public void setText(String... texts) {
        this.mTexts = texts;
        if (this.mTexts != null) {
            requestLayout();
        }
    }

    public void setSelectedTextColors(ColorStateList color) {
        this.mTextColors = color;
        invalidate();
    }

    public void setColors(ColorStateList colors) {
        this.mBackgroundColors = colors;
        if (this.mBackgroundDrawable != null) {
            this.mBackgroundDrawable.setStrokeColor(getSelectedBGColor());
            this.mBackgroundDrawable.setFillColor(getNormalBGColor());
        }
        if (this.mSelectedDrawable != null) {
            this.mSelectedDrawable.setFillColor(getSelectedBGColor());
        }
        invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
        if (this.mBackgroundDrawable != null) {
            this.mBackgroundDrawable.setRadius(cornerRadius);
        }
        invalidate();
    }

    public void setDirection(Direction direction) {
        Direction tDirection = this.mDirection;
        this.mDirection = direction;
        if (tDirection != direction) {
            requestLayout();
            invalidate();
        }
    }

    public void setTextSize(int textSize_sp) {
        setTextSize(2, textSize_sp);
    }

    public void setTextSize(int unit, int textSize) {
        this.mPaint.setTextSize((float) ((int) TypedValue.applyDimension(unit, (float) textSize, getContext().getResources().getDisplayMetrics())));
        if (textSize != this.mTextSize) {
            this.mTextSize = textSize;
            this.mCachedFM = this.mPaint.getFontMetrics();
            requestLayout();
        }
    }

    public void setSelectedIndex(int index) {
        this.mCurrentIndex = index;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (this.mTexts == null || this.mTexts.length <= 0) {
            width = widthMode == 0 ? 0 : widthSize;
            if (heightMode == 0) {
                height = 0;
            } else {
                height = heightSize;
            }
        } else {
            this.mSingleChildHeight = 0;
            this.mSingleChildWidth = 0;
            if (this.mCacheBounds == null || this.mCacheBounds.length != this.mTexts.length) {
                this.mCacheBounds = new Rect[this.mTexts.length];
            }
            if (this.mTextBounds == null || this.mTextBounds.length != this.mTexts.length) {
                this.mTextBounds = new Rect[this.mTexts.length];
            }
            for (int i = 0; i < this.mTexts.length; i++) {
                String text = this.mTexts[i];
                if (text != null) {
                    if (this.mTextBounds[i] == null) {
                        this.mTextBounds[i] = new Rect();
                    }
                    this.mPaint.getTextBounds(text, 0, text.length(), this.mTextBounds[i]);
                    if (this.mSingleChildWidth < this.mTextBounds[i].width() + (this.mHorizonGap * 2)) {
                        this.mSingleChildWidth = this.mTextBounds[i].width() + (this.mHorizonGap * 2);
                    }
                    if (this.mSingleChildHeight < this.mTextBounds[i].height() + (this.mVerticalGap * 2)) {
                        this.mSingleChildHeight = this.mTextBounds[i].height() + (this.mVerticalGap * 2);
                    }
                }
            }
            switch (widthMode) {
                case Integer.MIN_VALUE:
                    if (this.mDirection != Direction.HORIZONTAL) {
                        if (widthSize > this.mSingleChildWidth) {
                            width = this.mSingleChildWidth;
                            break;
                        } else {
                            width = widthSize;
                            break;
                        }
                    } else if (widthSize > this.mSingleChildWidth * this.mTexts.length) {
                        width = this.mSingleChildWidth * this.mTexts.length;
                        break;
                    } else {
                        this.mSingleChildWidth = widthSize / this.mTexts.length;
                        width = widthSize;
                        break;
                    }
                case 1073741824:
                    width = widthSize;
                    break;
                default:
                    if (this.mDirection != Direction.HORIZONTAL) {
                        width = this.mSingleChildWidth;
                        break;
                    } else {
                        width = this.mSingleChildWidth * this.mTexts.length;
                        break;
                    }
            }
            switch (heightMode) {
                case Integer.MIN_VALUE:
                    if (this.mDirection != Direction.VERTICAL) {
                        if (heightSize > this.mSingleChildHeight) {
                            height = this.mSingleChildHeight;
                            break;
                        } else {
                            height = heightSize;
                            break;
                        }
                    } else if (heightSize > this.mSingleChildHeight * this.mTexts.length) {
                        height = this.mSingleChildHeight * this.mTexts.length;
                        break;
                    } else {
                        this.mSingleChildHeight = heightSize / this.mTexts.length;
                        height = heightSize;
                        break;
                    }
                case 1073741824:
                    height = heightSize;
                    break;
                default:
                    if (this.mDirection != Direction.VERTICAL) {
                        height = this.mSingleChildHeight;
                        break;
                    } else {
                        height = this.mSingleChildHeight * this.mTexts.length;
                        break;
                    }
            }
            switch (this.mDirection) {
                case HORIZONTAL:
                    if (this.mSingleChildWidth != width / this.mTexts.length) {
                        this.mSingleChildWidth = width / this.mTexts.length;
                    }
                    this.mSingleChildHeight = height;
                    break;
                case VERTICAL:
                    if (this.mSingleChildHeight != height / this.mTexts.length) {
                        this.mSingleChildHeight = height / this.mTexts.length;
                    }
                    this.mSingleChildWidth = width;
                    break;
            }
            for (int i2 = 0; i2 < this.mTexts.length; i2++) {
                if (this.mCacheBounds[i2] == null) {
                    this.mCacheBounds[i2] = new Rect();
                }
                if (this.mDirection == Direction.HORIZONTAL) {
                    this.mCacheBounds[i2].left = this.mSingleChildWidth * i2;
                    this.mCacheBounds[i2].top = 0;
                } else {
                    this.mCacheBounds[i2].left = 0;
                    this.mCacheBounds[i2].top = this.mSingleChildHeight * i2;
                }
                this.mCacheBounds[i2].right = this.mCacheBounds[i2].left + this.mSingleChildWidth;
                this.mCacheBounds[i2].bottom = this.mCacheBounds[i2].top + this.mSingleChildHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int index;
        switch (event.getAction() & 255) {
            case 0:
                this.inTapRegion = true;
                this.mStartX = event.getX();
                this.mStartY = event.getY();
                break;
            case 1:
                if (this.inTapRegion) {
                    if (this.mDirection == Direction.HORIZONTAL) {
                        index = (int) (this.mStartX / ((float) this.mSingleChildWidth));
                    } else {
                        index = (int) (this.mStartY / ((float) this.mSingleChildHeight));
                    }
                    if (this.mOnSegmentControlClickListener != null) {
                        this.mOnSegmentControlClickListener.onSegmentControlClick(index);
                    }
                    this.mCurrentIndex = index;
                    invalidate();
                    break;
                }
                break;
            case 2:
                this.mCurrentX = event.getX();
                this.mCurrentY = event.getY();
                int dx = (int) (this.mCurrentX - this.mStartX);
                int dy = (int) (this.mCurrentY - this.mStartY);
                if ((dx * dx) + (dy * dy) > this.mTouchSlop) {
                    this.inTapRegion = false;
                    break;
                }
                break;
        }
        return true;
    }

    private int getSelectedTextColor() {
        return this.mTextColors.getColorForState(new int[]{16842913}, this.DEFAULT_NORMAL_COLOR);
    }

    private int getNormalTextColor() {
        return this.mTextColors.getColorForState(new int[]{-16842913}, this.DEFAULT_SELECTED_COLOR);
    }

    private int getSelectedBGColor() {
        return this.mBackgroundColors.getColorForState(new int[]{16842913}, this.DEFAULT_SELECTED_COLOR);
    }

    private int getNormalBGColor() {
        return this.mBackgroundColors.getColorForState(new int[]{-16842913}, this.DEFAULT_NORMAL_COLOR);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mTexts != null && this.mTexts.length > 0) {
            for (int i = 0; i < this.mTexts.length; i++) {
                if (i < this.mTexts.length - 1) {
                    this.mPaint.setColor(getSelectedBGColor());
                    this.mPaint.setStrokeWidth((float) this.mSeparatorWidth);
                    if (this.mDirection == Direction.HORIZONTAL) {
                        canvas.drawLine((float) this.mCacheBounds[i].right, 0.0f, (float) this.mCacheBounds[i].right, (float) getHeight(), this.mPaint);
                    } else {
                        canvas.drawLine((float) this.mCacheBounds[i].left, (float) (this.mSingleChildHeight * (i + 1)), (float) this.mCacheBounds[i].right, (float) (this.mSingleChildHeight * (i + 1)), this.mPaint);
                    }
                }
                if (i != this.mCurrentIndex || this.mSelectedDrawable == null) {
                    this.mPaint.setColor(getNormalTextColor());
                } else {
                    int topLeftRadius = 0;
                    int topRightRadius = 0;
                    int bottomLeftRadius = 0;
                    int bottomRightRadius = 0;
                    if (this.mDirection == Direction.HORIZONTAL) {
                        if (i == 0) {
                            topLeftRadius = this.mCornerRadius;
                            bottomLeftRadius = this.mCornerRadius;
                        } else if (i == this.mTexts.length - 1) {
                            topRightRadius = this.mCornerRadius;
                            bottomRightRadius = this.mCornerRadius;
                        }
                    } else if (i == 0) {
                        topLeftRadius = this.mCornerRadius;
                        topRightRadius = this.mCornerRadius;
                    } else if (i == this.mTexts.length - 1) {
                        bottomLeftRadius = this.mCornerRadius;
                        bottomRightRadius = this.mCornerRadius;
                    }
                    this.mSelectedDrawable.setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
                    this.mSelectedDrawable.setBounds(this.mCacheBounds[i]);
                    this.mSelectedDrawable.draw(canvas);
                    this.mPaint.setColor(getSelectedTextColor());
                }
                canvas.drawText(this.mTexts[i], (float) (this.mCacheBounds[i].left + ((this.mSingleChildWidth - this.mTextBounds[i].width()) / 2)), (((float) this.mCacheBounds[i].top) + (((((float) this.mSingleChildHeight) - this.mCachedFM.ascent) + this.mCachedFM.descent) / 2.0f)) - this.mCachedFM.descent, this.mPaint);
            }
        }
    }

    public void setOnSegmentControlClickListener(OnSegmentControlClickListener listener) {
        this.mOnSegmentControlClickListener = listener;
    }

    public OnSegmentControlClickListener getOnSegmentControlClicklistener() {
        return this.mOnSegmentControlClickListener;
    }
}

package cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import cn.com.bioeasy.app.utils.ScreenUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class TagListView extends FlowLayout implements View.OnClickListener {
    private String TAG = "TagListView";
    private Context mContext;
    private boolean mIsDeleteMode;
    /* access modifiers changed from: private */
    public OnTagCheckedChangedListener mOnTagCheckedChangedListener;
    private OnTagClickListener mOnTagClickListener;
    private Tag mSelectTag;
    private float mTagListViewHeight;
    private int mTagViewBackgroundResId;
    private int mTagViewTextColorResId;
    private final List<Tag> mTags = new ArrayList();
    private int mTtagCountOfRow;
    private int mTtagViewWidth;

    public interface OnTagCheckedChangedListener {
        void onTagCheckedChanged(TagView tagView, Tag tag);
    }

    public interface OnTagClickListener {
        void onTagClick(TagView tagView, Tag tag);
    }

    public TagListView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TagListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    public TagListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.mContext = context;
        init();
    }

    public void onClick(View v) {
        if (v instanceof TagView) {
            TagView checkTagView = (TagView) v;
            Tag localTag = (Tag) checkTagView.getTag();
            this.mSelectTag = localTag;
            checkTagView.setSelected(true);
            if (this.mOnTagClickListener != null) {
                this.mOnTagClickListener.onTagClick(checkTagView, localTag);
            }
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childrenView = getChildAt(i);
            if (childrenView instanceof TagView) {
                TagView childrenTagView = (TagView) childrenView;
                if (((Tag) childrenTagView.getTag()).getId() != this.mSelectTag.getId()) {
                    childrenTagView.setSelected(false);
                }
            }
        }
    }

    private void init() {
        this.mTtagCountOfRow = 3;
    }

    private void inflateTagView(Tag t, boolean b) {
        TagView localTagView = (TagView) View.inflate(getContext(), R.layout.tag, (ViewGroup) null);
        localTagView.setText(t.getTitle());
        localTagView.setTag(t);
        if (this.mTtagViewWidth <= 0) {
            this.mTtagViewWidth = 320;
        }
        int selfWidth = ScreenUtils.dip2px(this.mContext, 320.0f);
        int horizontalSpacing = ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_horizontal_spacing));
        int paddingBound = ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_common_margin));
        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams((selfWidth - ((horizontalSpacing * 2) + (paddingBound * 2))) / this.mTtagCountOfRow, ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_height)));
        new Button(this.mContext).setText(t.getTitle());
        localTagView.setLayoutParams(layoutParams);
        if (this.mTagViewTextColorResId <= 0) {
            localTagView.setTextColor(getResources().getColor(R.color.color_font_gray));
        }
        if (this.mTagViewBackgroundResId <= 0) {
            this.mTagViewBackgroundResId = R.drawable.tag_button_bg_unchecked;
            localTagView.setBackgroundResource(this.mTagViewBackgroundResId);
        }
        localTagView.setSelected(t.isChecked());
        localTagView.setCheckEnable(b);
        if (this.mIsDeleteMode) {
            localTagView.setPadding(localTagView.getPaddingLeft(), localTagView.getPaddingTop(), (int) TypedValue.applyDimension(1, 5.0f, getContext().getResources().getDisplayMetrics()), localTagView.getPaddingBottom());
        }
        if (t.getBackgroundResId() > 0) {
            localTagView.setBackgroundResource(t.getBackgroundResId());
        }
        if (t.getLeftDrawableResId() > 0 || t.getRightDrawableResId() > 0) {
            localTagView.setCompoundDrawablesWithIntrinsicBounds(t.getLeftDrawableResId(), 0, t.getRightDrawableResId(), 0);
        }
        localTagView.setOnClickListener(this);
        final Tag tag = t;
        localTagView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean) {
                tag.setChecked(paramAnonymousBoolean);
                if (TagListView.this.mOnTagCheckedChangedListener != null) {
                    TagListView.this.mOnTagCheckedChangedListener.onTagCheckedChanged((TagView) paramAnonymousCompoundButton, tag);
                }
            }
        });
        addView(localTagView);
    }

    public void addTag(int i, String s) {
        addTag(i, s, false);
    }

    public void addTag(int i, String s, boolean b) {
        addTag(new Tag(i, s), b);
    }

    public void addTag(Tag tag) {
        addTag(tag, false);
    }

    public void addTag(Tag tag, boolean b) {
        this.mTags.add(tag);
        inflateTagView(tag, b);
    }

    public void addTags(List<Tag> lists) {
        addTags(lists, false);
    }

    public void addTags(List<Tag> lists, boolean b) {
        for (int i = 0; i < lists.size(); i++) {
            addTag(lists.get(i), b);
        }
    }

    public List<Tag> getTags() {
        return this.mTags;
    }

    public float getTagListViewHeight() {
        return this.mTagListViewHeight;
    }

    public View getViewByTag(Tag tag) {
        return findViewWithTag(tag);
    }

    public void removeTag(Tag tag) {
        this.mTags.remove(tag);
        removeView(getViewByTag(tag));
    }

    public void setDeleteMode(boolean b) {
        this.mIsDeleteMode = b;
    }

    public void setOnTagCheckedChangedListener(OnTagCheckedChangedListener onTagCheckedChangedListener) {
        this.mOnTagCheckedChangedListener = onTagCheckedChangedListener;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setTagViewBackgroundRes(int res) {
        this.mTagViewBackgroundResId = res;
    }

    public void setTagViewTextColorRes(int res) {
        this.mTagViewTextColorResId = res;
    }

    public void setTags(List<? extends Tag> lists) {
        setTags(lists, false);
    }

    public void setTags(List<? extends Tag> lists, boolean b) {
        removeAllViews();
        this.mTags.clear();
        int count = lists.size();
        for (int i = 0; i < count; i++) {
            addTag((Tag) lists.get(i), b);
        }
        int itemHeight = ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_height));
        int verticalSpacing = ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_vertical_spacing));
        int listviewPaddingTop = ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_listview_padding_top));
        int listviewBottomTop = ScreenUtils.dip2px(this.mContext, this.mContext.getResources().getDimension(R.dimen.tag_common_margin));
        int rows = Double.valueOf(Math.ceil(((double) count) / 3.0d)).intValue();
        this.mTagListViewHeight = (float) ((itemHeight * rows) + ((rows - 1) * verticalSpacing) + listviewPaddingTop + listviewBottomTop);
    }

    public void setTtagViewWidth(int tagWidth) {
        this.mTtagViewWidth = tagWidth;
    }
}

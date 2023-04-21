package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.afollestad.materialdialogs.internal.MDTintHelper;

class DefaultAdapter extends BaseAdapter {
    private final MaterialDialog dialog;
    private final GravityEnum itemGravity;
    @LayoutRes
    private final int layout;

    public DefaultAdapter(MaterialDialog dialog2, @LayoutRes int layout2) {
        this.dialog = dialog2;
        this.layout = layout2;
        this.itemGravity = dialog2.mBuilder.itemsGravity;
    }

    public boolean hasStableIds() {
        return true;
    }

    public int getCount() {
        if (this.dialog.mBuilder.items != null) {
            return this.dialog.mBuilder.items.length;
        }
        return 0;
    }

    public Object getItem(int position) {
        return this.dialog.mBuilder.items[position];
    }

    public long getItemId(int position) {
        return (long) position;
    }

    @SuppressLint({"WrongViewCast"})
    public View getView(int index, View view, ViewGroup parent) {
        boolean selected;
        if (view == null) {
            view = LayoutInflater.from(this.dialog.getContext()).inflate(this.layout, parent, false);
        }
        TextView tv = (TextView) view.findViewById(R.id.title);
        switch (this.dialog.listType) {
            case SINGLE:
                RadioButton radio = (RadioButton) view.findViewById(R.id.control);
                if (this.dialog.mBuilder.selectedIndex == index) {
                    selected = true;
                } else {
                    selected = false;
                }
                MDTintHelper.setTint(radio, this.dialog.mBuilder.widgetColor);
                radio.setChecked(selected);
                break;
            case MULTI:
                CheckBox checkbox = (CheckBox) view.findViewById(R.id.control);
                boolean selected2 = this.dialog.selectedIndicesList.contains(Integer.valueOf(index));
                MDTintHelper.setTint(checkbox, this.dialog.mBuilder.widgetColor);
                checkbox.setChecked(selected2);
                break;
        }
        tv.setText(this.dialog.mBuilder.items[index]);
        tv.setTextColor(this.dialog.mBuilder.itemColor);
        this.dialog.setTypeface(tv, this.dialog.mBuilder.regularFont);
        view.setTag(index + ":" + this.dialog.mBuilder.items[index]);
        setupGravity((ViewGroup) view);
        if (this.dialog.mBuilder.itemIds != null) {
            if (index < this.dialog.mBuilder.itemIds.length) {
                view.setId(this.dialog.mBuilder.itemIds[index]);
            } else {
                view.setId(-1);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            ViewGroup group = (ViewGroup) view;
            if (group.getChildCount() == 2) {
                if (group.getChildAt(0) instanceof CompoundButton) {
                    group.getChildAt(0).setBackground((Drawable) null);
                } else if (group.getChildAt(1) instanceof CompoundButton) {
                    group.getChildAt(1).setBackground((Drawable) null);
                }
            }
        }
        return view;
    }

    @TargetApi(17)
    private void setupGravity(ViewGroup view) {
        ((LinearLayout) view).setGravity(this.itemGravity.getGravityInt() | 16);
        if (view.getChildCount() != 2) {
            return;
        }
        if (this.itemGravity == GravityEnum.END && !isRTL() && (view.getChildAt(0) instanceof CompoundButton)) {
            CompoundButton first = (CompoundButton) view.getChildAt(0);
            view.removeView(first);
            TextView second = (TextView) view.getChildAt(0);
            view.removeView(second);
            second.setPadding(second.getPaddingRight(), second.getPaddingTop(), second.getPaddingLeft(), second.getPaddingBottom());
            view.addView(second);
            view.addView(first);
        } else if (this.itemGravity == GravityEnum.START && isRTL() && (view.getChildAt(1) instanceof CompoundButton)) {
            CompoundButton first2 = (CompoundButton) view.getChildAt(1);
            view.removeView(first2);
            TextView second2 = (TextView) view.getChildAt(0);
            view.removeView(second2);
            second2.setPadding(second2.getPaddingRight(), second2.getPaddingTop(), second2.getPaddingRight(), second2.getPaddingBottom());
            view.addView(first2);
            view.addView(second2);
        }
    }

    @TargetApi(17)
    private boolean isRTL() {
        boolean z = true;
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        if (this.dialog.getBuilder().getContext().getResources().getConfiguration().getLayoutDirection() != 1) {
            z = false;
        }
        return z;
    }
}

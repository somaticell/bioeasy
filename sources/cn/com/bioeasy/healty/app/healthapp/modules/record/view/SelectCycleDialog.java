package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import cn.com.bioeasy.app.utils.ScreenUtils;
import cn.com.bioeasy.app.widgets.util.AnimationUtil;
import cn.com.bioeasy.app.widgets.util.ViewUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.FirstClassAdapter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.FirstClassItem;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SecondClassItem;
import java.util.ArrayList;
import java.util.List;

public class SelectCycleDialog extends PopupWindow {
    /* access modifiers changed from: private */
    public Context cxt;
    /* access modifiers changed from: private */
    public List<FirstClassItem> firstList;
    /* access modifiers changed from: private */
    public ListView leftLV;
    /* access modifiers changed from: private */
    public PopupWindowListener popupWindowListener;
    private View view;

    public interface PopupWindowListener {
        void onClick(int i);
    }

    public void addPopupWindowListener(PopupWindowListener listener) {
        this.popupWindowListener = listener;
    }

    public SelectCycleDialog(Context context) {
        super(context);
        this.cxt = context;
        initData();
        initPopup();
    }

    private void initData() {
        this.firstList = new ArrayList();
        this.firstList.add(new FirstClassItem(7, "近一周", (List<SecondClassItem>) null));
        this.firstList.add(new FirstClassItem(30, "近一个月", (List<SecondClassItem>) null));
        this.firstList.add(new FirstClassItem(90, "近三个月", (List<SecondClassItem>) null));
    }

    private void initPopup() {
        this.view = LayoutInflater.from(this.cxt).inflate(R.layout.popup_layout, (ViewGroup) null);
        this.leftLV = (ListView) this.view.findViewById(R.id.pop_listview_left);
        setContentView(this.view);
        setBackgroundDrawable(new PaintDrawable());
        setFocusable(true);
        setHeight((ScreenUtils.getScreenH((Activity) this.cxt) * 2) / 3);
        setWidth(ScreenUtils.getScreenW((Activity) this.cxt));
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                SelectCycleDialog.this.leftLV.setSelection(0);
                SelectCycleDialog.this.getContentView().startAnimation(AnimationUtil.createOutAnimation(SelectCycleDialog.this.cxt.getApplicationContext(), 1100));
            }
        });
        setBackgroundDrawable(new ColorDrawable(88000000));
        this.leftLV.setAdapter(new FirstClassAdapter(this.cxt, this.firstList));
        this.leftLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (SelectCycleDialog.this.popupWindowListener != null) {
                    SelectCycleDialog.this.popupWindowListener.onClick(((FirstClassItem) SelectCycleDialog.this.firstList.get(position)).getId());
                    SelectCycleDialog.this.dismiss();
                }
            }
        });
    }

    public void showPopupWindow(View lrTab) {
        if (isShowing()) {
            dismiss();
            return;
        }
        ViewUtils.getViewMeasuredHeight(this.view);
        getContentView().startAnimation(AnimationUtil.createInAnimation(this.cxt, -1100));
        showAsDropDown(lrTab, 0, 0);
    }
}

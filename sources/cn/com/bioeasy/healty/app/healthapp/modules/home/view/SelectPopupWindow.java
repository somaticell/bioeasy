package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import cn.com.bioeasy.app.utils.ScreenUtils;
import cn.com.bioeasy.app.widgets.util.AnimationUtil;
import cn.com.bioeasy.app.widgets.util.ViewUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.FirstClassAdapter;
import cn.com.bioeasy.healty.app.healthapp.adapter.SecondClassAdapter;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.FirstClassItem;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SecondClassItem;
import com.facebook.imageutils.JfifUtil;
import com.flyco.tablayout.BuildConfig;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

public class SelectPopupWindow extends PopupWindow {
    /* access modifiers changed from: private */
    public static Context cxt;
    /* access modifiers changed from: private */
    public View darkView;
    /* access modifiers changed from: private */
    public List<FirstClassItem> firstList;
    /* access modifiers changed from: private */
    public boolean isUp;
    private ImageView ivArrow;
    /* access modifiers changed from: private */
    public ListView leftLV;
    /* access modifiers changed from: private */
    public ListView rightLV;
    private List<SecondClassItem> secondList;
    private View view;

    public SelectPopupWindow(Context context, ImageView imageView, View darkView2) {
        super(context);
        cxt = context;
        initPopupView(imageView, darkView2);
    }

    public void initPopupView(ImageView imageView, View darkView2) {
        initData();
        initPopup(imageView, darkView2);
    }

    private void initData() {
        this.firstList = new ArrayList();
        ArrayList<SecondClassItem> secondList0 = new ArrayList<>();
        secondList0.add(new SecondClassItem(1, "智能搜索"));
        secondList0.add(new SecondClassItem(2, "200米"));
        secondList0.add(new SecondClassItem(3, "400米"));
        secondList0.add(new SecondClassItem(4, "1000米"));
        this.firstList.add(new FirstClassItem(1, "附近", secondList0));
        ArrayList<SecondClassItem> secondList1 = new ArrayList<>();
        secondList1.add(new SecondClassItem(101, "新安市场"));
        secondList1.add(new SecondClassItem(102, "西乡市场"));
        secondList1.add(new SecondClassItem(103, "固戍市场"));
        this.firstList.add(new FirstClassItem(1, "宝安区", secondList1));
        ArrayList<SecondClassItem> secondList2 = new ArrayList<>();
        secondList2.add(new SecondClassItem(201, "西丽街道"));
        secondList2.add(new SecondClassItem(BuildConfig.VERSION_CODE, "南头下边村"));
        secondList2.add(new SecondClassItem(203, "西丽街道"));
        secondList2.add(new SecondClassItem(204, "西丽街道"));
        secondList2.add(new SecondClassItem(205, "西丽街道"));
        secondList2.add(new SecondClassItem(206, "西丽街道"));
        secondList2.add(new SecondClassItem(207, "西丽街道"));
        secondList2.add(new SecondClassItem(JfifUtil.MARKER_RST0, "西丽街道"));
        secondList2.add(new SecondClassItem(209, "西丽街道"));
        secondList2.add(new SecondClassItem(210, "西丽街道"));
        secondList2.add(new SecondClassItem(211, "西丽街道"));
        secondList2.add(new SecondClassItem(212, "西丽街道"));
        this.firstList.add(new FirstClassItem(2, "南山区", secondList2));
        ArrayList<SecondClassItem> secondList3 = new ArrayList<>();
        secondList3.add(new SecondClassItem(301, "永新菜市场"));
        secondList3.add(new SecondClassItem(302, "永新菜市场"));
        secondList3.add(new SecondClassItem(303, "永新菜市场"));
        secondList3.add(new SecondClassItem(304, "永新菜市场"));
        secondList3.add(new SecondClassItem(305, "永新菜市场v"));
        this.firstList.add(new FirstClassItem(3, "福田区", secondList3));
        this.firstList.add(new FirstClassItem(4, "罗湖区", new ArrayList()));
        this.firstList.add(new FirstClassItem(5, "龙岗区", (List<SecondClassItem>) null));
    }

    private void initPopup(final ImageView imageView, View darkView2) {
        this.ivArrow = imageView;
        this.darkView = darkView2;
        this.view = LayoutInflater.from(cxt).inflate(R.layout.popup_layout, (ViewGroup) null);
        this.leftLV = (ListView) this.view.findViewById(R.id.pop_listview_left);
        this.rightLV = (ListView) this.view.findViewById(R.id.pop_listview_right);
        setContentView(this.view);
        setBackgroundDrawable(new PaintDrawable());
        setFocusable(true);
        setHeight((ScreenUtils.getScreenH((Activity) cxt) * 2) / 3);
        setWidth(ScreenUtils.getScreenW((Activity) cxt));
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                SelectPopupWindow.this.darkView.setVisibility(8);
                SelectPopupWindow.this.leftLV.setSelection(0);
                SelectPopupWindow.this.rightLV.setSelection(0);
                boolean unused = SelectPopupWindow.this.isUp = true;
                imageView.setBackgroundResource(R.drawable.arrow_down);
                SelectPopupWindow.this.getContentView().startAnimation(AnimationUtil.createOutAnimation(SelectPopupWindow.cxt.getApplicationContext(), 1100));
            }
        });
        setBackgroundDrawable(new ColorDrawable(88000000));
        final FirstClassAdapter firstAdapter = new FirstClassAdapter(cxt, this.firstList);
        this.leftLV.setAdapter(firstAdapter);
        this.secondList = new ArrayList();
        this.secondList.addAll(this.firstList.get(0).getSecondList());
        final SecondClassAdapter secondAdapter = new SecondClassAdapter(cxt, this.secondList);
        this.rightLV.setAdapter(secondAdapter);
        this.leftLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<SecondClassItem> list2 = ((FirstClassItem) SelectPopupWindow.this.firstList.get(position)).getSecondList();
                if (list2 == null || list2.size() == 0) {
                    SelectPopupWindow.this.dismiss();
                    SelectPopupWindow.this.handleResult(((FirstClassItem) SelectPopupWindow.this.firstList.get(position)).getId(), -1, ((FirstClassItem) SelectPopupWindow.this.firstList.get(position)).getName());
                    return;
                }
                FirstClassAdapter adapter = (FirstClassAdapter) parent.getAdapter();
                if (adapter.getSelectedPosition() != position) {
                    adapter.setSelectedPosition(position);
                    adapter.notifyDataSetChanged();
                    SelectPopupWindow.this.updateSecondListView(list2, secondAdapter);
                }
            }
        });
        this.rightLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SelectPopupWindow.this.dismiss();
                int firstPosition = firstAdapter.getSelectedPosition();
                SelectPopupWindow.this.handleResult(((FirstClassItem) SelectPopupWindow.this.firstList.get(firstPosition)).getId(), ((FirstClassItem) SelectPopupWindow.this.firstList.get(firstPosition)).getSecondList().get(position).getId(), ((FirstClassItem) SelectPopupWindow.this.firstList.get(firstPosition)).getSecondList().get(position).getName());
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateSecondListView(List<SecondClassItem> list2, SecondClassAdapter secondAdapter) {
        this.secondList.clear();
        this.secondList.addAll(list2);
        secondAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void handleResult(int firstId, int secondId, String selectedName) {
        Toast.makeText(cxt, "first id:" + firstId + ",second id:" + secondId, 0).show();
    }

    public void tab1OnClick(LinearLayout lrTab) {
        if (isShowing()) {
            dismiss();
            this.isUp = true;
            this.ivArrow.setBackgroundResource(R.drawable.arrow_down);
            return;
        }
        this.isUp = false;
        this.ivArrow.setBackgroundResource(R.drawable.arrow_up);
        Logger.e("viewMeasuredHeight :::" + ViewUtils.getViewMeasuredHeight(this.view), new Object[0]);
        getContentView().startAnimation(AnimationUtil.createInAnimation(cxt, -1100));
        showAsDropDown(lrTab, 0, 0);
        this.darkView.setVisibility(0);
    }
}

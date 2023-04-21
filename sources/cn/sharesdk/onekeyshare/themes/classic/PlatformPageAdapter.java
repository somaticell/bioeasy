package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.CustomerLogo;
import com.mob.tools.gui.ViewPagerAdapter;
import com.mob.tools.utils.ResHelper;
import java.util.ArrayList;

public abstract class PlatformPageAdapter extends ViewPagerAdapter implements View.OnClickListener {
    public static final int DESIGN_BOTTOM_HEIGHT = 52;
    protected static final int MIN_CLICK_INTERVAL = 1000;
    protected int bottomHeight;
    protected int cellHeight;
    protected Object[][] cells;
    private long lastClickTime;
    protected int lineSize;
    protected int logoHeight;
    protected int paddingTop;
    private PlatformPage page;
    protected int panelHeight;
    protected int sepLineWidth;
    private IndicatorView vInd;

    /* access modifiers changed from: protected */
    public abstract void calculateSize(Context context, ArrayList<Object> arrayList);

    /* access modifiers changed from: protected */
    public abstract void collectCells(ArrayList<Object> arrayList);

    public PlatformPageAdapter(PlatformPage page2, ArrayList<Object> cells2) {
        this.page = page2;
        if (cells2 != null && !cells2.isEmpty()) {
            calculateSize(page2.getContext(), cells2);
            collectCells(cells2);
        }
    }

    public int getBottomHeight() {
        return this.bottomHeight;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    public int getCount() {
        if (this.cells == null) {
            return 0;
        }
        return this.cells.length;
    }

    public void setIndicator(IndicatorView view) {
        this.vInd = view;
    }

    public void onScreenChange(int currentScreen, int lastScreen) {
        if (this.vInd != null) {
            this.vInd.setScreenCount(getCount());
            this.vInd.onScreenChange(currentScreen, lastScreen);
        }
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createPanel(parent.getContext());
        }
        refreshPanel((LinearLayout[]) ResHelper.forceCast(((LinearLayout) ResHelper.forceCast(convertView)).getTag()), this.cells[index]);
        return convertView;
    }

    private View createPanel(Context context) {
        LinearLayout llPanel = new LinearLayout(context);
        llPanel.setOrientation(1);
        llPanel.setBackgroundColor(-855310);
        int lineCount = this.panelHeight / this.cellHeight;
        LinearLayout[] llCells = new LinearLayout[(this.lineSize * lineCount)];
        llPanel.setTag(llCells);
        int cellBack = ResHelper.getBitmapRes(context, "ssdk_oks_classic_platform_cell_back");
        for (int i = 0; i < lineCount; i++) {
            LinearLayout llLine = new LinearLayout(context);
            llPanel.addView(llLine, new LinearLayout.LayoutParams(-1, this.cellHeight));
            for (int j = 0; j < this.lineSize; j++) {
                llCells[(this.lineSize * i) + j] = new LinearLayout(context);
                llCells[(this.lineSize * i) + j].setBackgroundResource(cellBack);
                llCells[(this.lineSize * i) + j].setOrientation(1);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, this.cellHeight);
                lp.weight = 1.0f;
                llLine.addView(llCells[(this.lineSize * i) + j], lp);
                if (j < this.lineSize - 1) {
                    llLine.addView(new View(context), new LinearLayout.LayoutParams(this.sepLineWidth, -1));
                }
            }
            llPanel.addView(new View(context), new LinearLayout.LayoutParams(-1, this.sepLineWidth));
        }
        for (LinearLayout llCell : llCells) {
            ImageView ivLogo = new ImageView(context);
            ivLogo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-1, this.logoHeight);
            lp2.topMargin = this.paddingTop;
            llCell.addView(ivLogo, lp2);
            TextView tvName = new TextView(context);
            tvName.setTextColor(-10197916);
            tvName.setTextSize(2, 14.0f);
            tvName.setGravity(17);
            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-1, -2);
            lp3.weight = 1.0f;
            llCell.addView(tvName, lp3);
        }
        return llPanel;
    }

    private void refreshPanel(LinearLayout[] llCells, Object[] logos) {
        int cellBack = ResHelper.getBitmapRes(this.page.getContext(), "ssdk_oks_classic_platform_cell_back");
        int disableBack = ResHelper.getBitmapRes(this.page.getContext(), "ssdk_oks_classic_platfrom_cell_back_nor");
        for (int i = 0; i < logos.length; i++) {
            ImageView ivLogo = (ImageView) ResHelper.forceCast(llCells[i].getChildAt(0));
            TextView tvName = (TextView) ResHelper.forceCast(llCells[i].getChildAt(1));
            if (logos[i] == null) {
                ivLogo.setVisibility(4);
                tvName.setVisibility(4);
                llCells[i].setBackgroundResource(disableBack);
                llCells[i].setOnClickListener((View.OnClickListener) null);
            } else {
                ivLogo.setVisibility(0);
                tvName.setVisibility(0);
                ivLogo.requestLayout();
                tvName.requestLayout();
                llCells[i].setBackgroundResource(cellBack);
                llCells[i].setOnClickListener(this);
                llCells[i].setTag(logos[i]);
                if (logos[i] instanceof CustomerLogo) {
                    CustomerLogo logo = (CustomerLogo) ResHelper.forceCast(logos[i]);
                    if (logo.logo != null) {
                        ivLogo.setImageBitmap(logo.logo);
                    } else {
                        ivLogo.setImageBitmap((Bitmap) null);
                    }
                    if (logo.label != null) {
                        tvName.setText(logo.label);
                    } else {
                        tvName.setText("");
                    }
                } else {
                    String name = ((Platform) ResHelper.forceCast(logos[i])).getName().toLowerCase();
                    int resId = ResHelper.getBitmapRes(ivLogo.getContext(), "ssdk_oks_classic_" + name);
                    if (resId > 0) {
                        ivLogo.setImageResource(resId);
                    } else {
                        ivLogo.setImageBitmap((Bitmap) null);
                    }
                    int resId2 = ResHelper.getStringRes(tvName.getContext(), "ssdk_" + name);
                    if (resId2 > 0) {
                        tvName.setText(resId2);
                    } else {
                        tvName.setText("");
                    }
                }
            }
        }
    }

    public void onClick(View v) {
        long time = System.currentTimeMillis();
        if (time - this.lastClickTime >= 1000) {
            this.lastClickTime = time;
            if (v.getTag() instanceof CustomerLogo) {
                this.page.performCustomLogoClick(v, (CustomerLogo) ResHelper.forceCast(v.getTag()));
                return;
            }
            this.page.showEditPage((Platform) ResHelper.forceCast(v.getTag()));
        }
    }
}

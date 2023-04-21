package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alibaba.fastjson.asm.Opcodes;
import com.mob.tools.utils.ResHelper;

public class PRTHeader extends LinearLayout {
    private static final int DESIGN_AVATAR_PADDING = 24;
    private static final int DESIGN_AVATAR_WIDTH = 64;
    private static final int DESIGN_SCREEN_WIDTH = 720;
    private RotateImageView ivArrow;
    private ProgressBar pbRefreshing;
    private TextView tvHeader;

    public PRTHeader(Context context) {
        super(context);
        int[] size = ResHelper.getScreenSize(context);
        float ratio = (size[0] < size[1] ? (float) size[0] : (float) size[1]) / 720.0f;
        setOrientation(1);
        LinearLayout llInner = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        lp.gravity = 1;
        addView(llInner, lp);
        this.ivArrow = new RotateImageView(context);
        int resId = ResHelper.getBitmapRes(context, "ssdk_oks_ptr_ptr");
        if (resId > 0) {
            this.ivArrow.setImageResource(resId);
        }
        int avatarWidth = (int) (64.0f * ratio);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(avatarWidth, avatarWidth);
        lp2.gravity = 16;
        int avataPadding = (int) (24.0f * ratio);
        lp2.bottomMargin = avataPadding;
        lp2.topMargin = avataPadding;
        llInner.addView(this.ivArrow, lp2);
        this.pbRefreshing = new ProgressBar(context);
        this.pbRefreshing.setIndeterminateDrawable(context.getResources().getDrawable(ResHelper.getBitmapRes(context, "ssdk_oks_classic_progressbar")));
        llInner.addView(this.pbRefreshing, lp2);
        this.pbRefreshing.setVisibility(8);
        this.tvHeader = new TextView(getContext());
        this.tvHeader.setTextSize(2, 18.0f);
        this.tvHeader.setPadding(avataPadding, 0, avataPadding, 0);
        this.tvHeader.setTextColor(-16139513);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
        lp3.gravity = 16;
        llInner.addView(this.tvHeader, lp3);
    }

    public void onPullDown(int percent) {
        if (percent > 100) {
            int degree = ((percent - 100) * Opcodes.GETFIELD) / 20;
            if (degree > 180) {
                degree = Opcodes.GETFIELD;
            }
            if (degree < 0) {
                degree = 0;
            }
            this.ivArrow.setRotation((float) degree);
        } else {
            this.ivArrow.setRotation(0.0f);
        }
        if (percent < 100) {
            int resId = ResHelper.getStringRes(getContext(), "ssdk_oks_pull_to_refresh");
            if (resId > 0) {
                this.tvHeader.setText(resId);
                return;
            }
            return;
        }
        int resId2 = ResHelper.getStringRes(getContext(), "ssdk_oks_release_to_refresh");
        if (resId2 > 0) {
            this.tvHeader.setText(resId2);
        }
    }

    public void onRequest() {
        this.ivArrow.setVisibility(8);
        this.pbRefreshing.setVisibility(0);
        int resId = ResHelper.getStringRes(getContext(), "ssdk_oks_refreshing");
        if (resId > 0) {
            this.tvHeader.setText(resId);
        }
    }

    public void reverse() {
        this.pbRefreshing.setVisibility(8);
        this.ivArrow.setRotation(180.0f);
        this.ivArrow.setVisibility(0);
    }
}

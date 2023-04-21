package cn.sharesdk.onekeyshare.themes.classic;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeySharePage;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.FriendAdapter;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class FriendListPage extends OnekeySharePage implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int DESIGN_LEFT_PADDING = 40;
    private FriendAdapter adapter;
    private int checkNum = 0;
    private int lastPosition = -1;
    private LinearLayout llPage;
    private Platform platform;
    private RelativeLayout rlTitle;
    private TextView tvCancel;
    private TextView tvConfirm;

    /* access modifiers changed from: protected */
    public abstract int getDesignTitleHeight();

    /* access modifiers changed from: protected */
    public abstract float getRatio();

    public FriendListPage(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    public void setPlatform(Platform platform2) {
        this.platform = platform2;
    }

    public void onCreate() {
        float f;
        this.activity.getWindow().setBackgroundDrawable(new ColorDrawable(-789517));
        this.llPage = new LinearLayout(this.activity);
        this.llPage.setOrientation(1);
        this.activity.setContentView(this.llPage);
        this.rlTitle = new RelativeLayout(this.activity);
        float ratio = getRatio();
        this.llPage.addView(this.rlTitle, new LinearLayout.LayoutParams(-1, (int) (((float) getDesignTitleHeight()) * ratio)));
        initTitle(this.rlTitle, ratio);
        View line = new View(this.activity);
        if (ratio < 1.0f) {
            f = 1.0f;
        } else {
            f = ratio;
        }
        LinearLayout.LayoutParams lpline = new LinearLayout.LayoutParams(-1, (int) f);
        line.setBackgroundColor(-2434599);
        this.llPage.addView(line, lpline);
        FrameLayout flPage = new FrameLayout(getContext());
        LinearLayout.LayoutParams lpFl = new LinearLayout.LayoutParams(-1, -2);
        lpFl.weight = 1.0f;
        flPage.setLayoutParams(lpFl);
        this.llPage.addView(flPage);
        PullToRequestView followList = new PullToRequestView(getContext());
        followList.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        flPage.addView(followList);
        this.adapter = new FriendAdapter(this, followList);
        this.adapter.setPlatform(this.platform);
        this.adapter.setRatio(ratio);
        this.adapter.setOnItemClickListener(this);
        followList.setAdapter(this.adapter);
        followList.performPullingDown(true);
    }

    private void initTitle(RelativeLayout rlTitle2, float ratio) {
        this.tvCancel = new TextView(this.activity);
        this.tvCancel.setTextColor(-12895429);
        this.tvCancel.setTextSize(2, 18.0f);
        this.tvCancel.setGravity(17);
        int resId = ResHelper.getStringRes(this.activity, "ssdk_oks_cancel");
        if (resId > 0) {
            this.tvCancel.setText(resId);
        }
        int padding = (int) (40.0f * ratio);
        this.tvCancel.setPadding(padding, 0, padding, 0);
        rlTitle2.addView(this.tvCancel, new RelativeLayout.LayoutParams(-2, -1));
        this.tvCancel.setOnClickListener(this);
        TextView tvTitle = new TextView(this.activity);
        tvTitle.setTextColor(-12895429);
        tvTitle.setTextSize(2, 22.0f);
        tvTitle.setGravity(17);
        int resId2 = ResHelper.getStringRes(this.activity, "ssdk_oks_contacts");
        if (resId2 > 0) {
            tvTitle.setText(resId2);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2, -1);
        lp.addRule(13);
        rlTitle2.addView(tvTitle, lp);
        this.tvConfirm = new TextView(this.activity);
        this.tvConfirm.setTextColor(-37615);
        this.tvConfirm.setTextSize(2, 18.0f);
        this.tvConfirm.setGravity(17);
        int resId3 = ResHelper.getStringRes(this.activity, "ssdk_oks_confirm");
        if (resId3 > 0) {
            this.tvConfirm.setText(resId3);
        }
        this.tvConfirm.setPadding(padding, 0, padding, 0);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(-2, -1);
        lp2.addRule(11);
        rlTitle2.addView(this.tvConfirm, lp2);
        this.tvConfirm.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.equals(this.tvCancel)) {
            finish();
            return;
        }
        ArrayList<String> selected = new ArrayList<>();
        int size = this.adapter.getCount();
        for (int i = 0; i < size; i++) {
            if (this.adapter.getItem(i).checked) {
                selected.add(this.adapter.getItem(i).atName);
            }
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("selected", selected);
        res.put("platform", this.platform);
        setResult(res);
        finish();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        boolean z = false;
        if ("FacebookMessenger".equals(this.platform.getName())) {
            if (this.lastPosition >= 0) {
                this.adapter.getItem(this.lastPosition).checked = false;
            }
            this.lastPosition = position;
        }
        FriendAdapter.Following following = this.adapter.getItem(position);
        if (!following.checked) {
            z = true;
        }
        following.checked = z;
        if (following.checked) {
            this.checkNum++;
        } else {
            this.checkNum--;
        }
        updateConfirmView();
        this.adapter.notifyDataSetChanged();
    }

    private void updateConfirmView() {
        int resId = ResHelper.getStringRes(this.activity, "ssdk_oks_confirm");
        String confirm = "Confirm";
        if (resId > 0) {
            confirm = getContext().getResources().getString(resId);
        }
        if (this.checkNum == 0) {
            this.tvConfirm.setText(confirm);
        } else if (this.checkNum > 0) {
            this.tvConfirm.setText(confirm + "(" + this.checkNum + ")");
        }
    }
}

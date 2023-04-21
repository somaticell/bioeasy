package cn.sharesdk.onekeyshare.themes.classic;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.CustomerLogo;
import cn.sharesdk.onekeyshare.OnekeySharePage;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import com.mob.tools.gui.MobViewPager;
import com.mob.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlatformPage extends OnekeySharePage {
    private Animation animHide;
    private Animation animShow;
    /* access modifiers changed from: private */
    public Runnable beforeFinish;
    /* access modifiers changed from: private */
    public boolean finished;
    /* access modifiers changed from: private */
    public ClassicTheme impl;
    private LinearLayout llPanel;

    /* access modifiers changed from: protected */
    public abstract PlatformPageAdapter newAdapter(ArrayList<Object> arrayList);

    public PlatformPage(OnekeyShareThemeImpl impl2) {
        super(impl2);
        this.impl = (ClassicTheme) ResHelper.forceCast(impl2);
    }

    public void onCreate() {
        this.activity.getWindow().setBackgroundDrawable(new ColorDrawable(1275068416));
        initAnims();
        LinearLayout llPage = new LinearLayout(this.activity);
        llPage.setOrientation(1);
        this.activity.setContentView(llPage);
        TextView vTop = new TextView(this.activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.weight = 1.0f;
        vTop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlatformPage.this.finish();
            }
        });
        llPage.addView(vTop, lp);
        this.llPanel = new LinearLayout(this.activity);
        this.llPanel.setOrientation(1);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-1, -2);
        this.llPanel.setAnimation(this.animShow);
        llPage.addView(this.llPanel, lp2);
        MobViewPager mvp = new MobViewPager(this.activity);
        PlatformPageAdapter adapter = newAdapter(collectCells());
        this.llPanel.addView(mvp, new LinearLayout.LayoutParams(-1, adapter.getPanelHeight()));
        IndicatorView vInd = new IndicatorView(this.activity);
        this.llPanel.addView(vInd, new LinearLayout.LayoutParams(-1, adapter.getBottomHeight()));
        vInd.setScreenCount(adapter.getCount());
        vInd.onScreenChange(0, 0);
        adapter.setIndicator(vInd);
        mvp.setAdapter(adapter);
    }

    /* access modifiers changed from: protected */
    public ArrayList<Object> collectCells() {
        ArrayList<Object> cells = new ArrayList<>();
        Platform[] platforms = ShareSDK.getPlatformList();
        if (platforms == null) {
            platforms = new Platform[0];
        }
        HashMap<String, String> hides = getHiddenPlatforms();
        if (hides == null) {
            hides = new HashMap<>();
        }
        for (Platform p : platforms) {
            if (!hides.containsKey(p.getName())) {
                cells.add(p);
            }
        }
        ArrayList<CustomerLogo> customers = getCustomerLogos();
        if (customers != null && customers.size() > 0) {
            cells.addAll(customers);
        }
        return cells;
    }

    public final void showEditPage(final Platform platform) {
        this.beforeFinish = new Runnable() {
            public void run() {
                boolean isSilent = PlatformPage.this.isSilent();
                boolean isCustomPlatform = platform instanceof CustomPlatform;
                boolean isUseClientToShare = PlatformPage.this.isUseClientToShare(platform);
                if (isSilent || isCustomPlatform || isUseClientToShare) {
                    PlatformPage.this.shareSilently(platform);
                    return;
                }
                Platform.ShareParams sp = PlatformPage.this.formateShareData(platform);
                if (sp != null) {
                    ShareSDK.logDemoEvent(3, (Platform) null);
                    if (PlatformPage.this.getCustomizeCallback() != null) {
                        PlatformPage.this.getCustomizeCallback().onShare(platform, sp);
                    }
                    PlatformPage.this.impl.showEditPage(PlatformPage.this.activity, platform, sp);
                }
            }
        };
        finish();
    }

    public final void performCustomLogoClick(final View v, final CustomerLogo logo) {
        this.beforeFinish = new Runnable() {
            public void run() {
                logo.listener.onClick(v);
            }
        };
        finish();
    }

    private void initAnims() {
        this.animShow = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        this.animShow.setDuration(300);
        this.animHide = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        this.animHide.setDuration(300);
    }

    public boolean onFinish() {
        if (this.finished) {
            this.finished = false;
            return false;
        }
        this.animHide.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (PlatformPage.this.beforeFinish == null) {
                    ShareSDK.logDemoEvent(2, (Platform) null);
                } else {
                    PlatformPage.this.beforeFinish.run();
                    Runnable unused = PlatformPage.this.beforeFinish = null;
                }
                boolean unused2 = PlatformPage.this.finished = true;
                PlatformPage.this.finish();
            }
        });
        this.llPanel.clearAnimation();
        this.llPanel.setAnimation(this.animHide);
        this.llPanel.setVisibility(8);
        return true;
    }
}

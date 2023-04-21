package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.themes.classic.FriendAdapter;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.gui.BitmapProcessor;
import com.mob.tools.utils.ResHelper;

public class FriendListItem extends LinearLayout {
    private static final int DESIGN_AVATAR_PADDING = 24;
    private static final int DESIGN_AVATAR_WIDTH = 64;
    private static final int DESIGN_ITEM_HEIGHT = 96;
    private static final int DESIGN_ITEM_PADDING = 20;
    private AsyncImageView aivIcon;
    private Bitmap bmChd;
    private Bitmap bmUnch;
    private ImageView ivCheck;
    private TextView tvName;

    public FriendListItem(Context context, float ratio) {
        super(context);
        int itemPadding = (int) (20.0f * ratio);
        setPadding(itemPadding, 0, itemPadding, 0);
        setMinimumHeight((int) (96.0f * ratio));
        setBackgroundColor(-1);
        this.ivCheck = new ImageView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        lp.gravity = 16;
        addView(this.ivCheck, lp);
        this.aivIcon = new AsyncImageView(context);
        int avatarWidth = (int) (64.0f * ratio);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(avatarWidth, avatarWidth);
        lp2.gravity = 16;
        int avatarMargin = (int) (24.0f * ratio);
        lp2.setMargins(avatarMargin, 0, avatarMargin, 0);
        addView(this.aivIcon, lp2);
        this.tvName = new TextView(context);
        this.tvName.setTextColor(-16777216);
        this.tvName.setTextSize(2, 18.0f);
        this.tvName.setSingleLine();
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
        lp3.gravity = 16;
        lp3.weight = 1.0f;
        addView(this.tvName, lp3);
        int resId = ResHelper.getBitmapRes(context, "ssdk_oks_classic_check_checked");
        if (resId > 0) {
            this.bmChd = BitmapFactory.decodeResource(context.getResources(), resId);
        }
        int resId2 = ResHelper.getBitmapRes(getContext(), "ssdk_oks_classic_check_default");
        if (resId2 > 0) {
            this.bmUnch = BitmapFactory.decodeResource(context.getResources(), resId2);
        }
    }

    public void update(FriendAdapter.Following following, boolean fling) {
        this.tvName.setText(following.screenName);
        this.ivCheck.setImageBitmap(following.checked ? this.bmChd : this.bmUnch);
        if (this.aivIcon == null) {
            return;
        }
        if (fling) {
            Bitmap bm = BitmapProcessor.getBitmapFromCache(following.icon);
            if (bm == null || bm.isRecycled()) {
                this.aivIcon.execute((String) null, 0);
            } else {
                this.aivIcon.setImageBitmap(bm);
            }
        } else {
            this.aivIcon.execute(following.icon, 0);
        }
    }
}

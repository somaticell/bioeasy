package cn.sharesdk.onekeyshare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.achartengine.chart.LineChart;

public abstract class OnekeyShareThemeImpl implements PlatformActionListener, Handler.Callback {
    protected PlatformActionListener callback = this;
    protected Context context;
    protected ArrayList<CustomerLogo> customerLogos;
    protected ShareContentCustomizeCallback customizeCallback;
    protected boolean dialogMode;
    protected boolean disableSSO;
    protected HashMap<String, String> hiddenPlatforms;
    protected HashMap<String, Object> shareParamsMap;
    protected boolean silent;

    /* access modifiers changed from: protected */
    public abstract void showEditPage(Context context2, Platform platform, Platform.ShareParams shareParams);

    /* access modifiers changed from: protected */
    public abstract void showPlatformPage(Context context2);

    public final void setDialogMode(boolean dialogMode2) {
        this.dialogMode = dialogMode2;
    }

    public final void setShareParamsMap(HashMap<String, Object> shareParamsMap2) {
        this.shareParamsMap = shareParamsMap2;
    }

    public final void setSilent(boolean silent2) {
        this.silent = silent2;
    }

    public final void setCustomerLogos(ArrayList<CustomerLogo> customerLogos2) {
        this.customerLogos = customerLogos2;
    }

    public final void setHiddenPlatforms(HashMap<String, String> hiddenPlatforms2) {
        this.hiddenPlatforms = hiddenPlatforms2;
    }

    public final void setPlatformActionListener(PlatformActionListener callback2) {
        if (callback2 == null) {
            callback2 = this;
        }
        this.callback = callback2;
    }

    public final void setShareContentCustomizeCallback(ShareContentCustomizeCallback customizeCallback2) {
        this.customizeCallback = customizeCallback2;
    }

    public final void disableSSO() {
        this.disableSSO = true;
    }

    public final void show(Context context2) {
        this.context = context2;
        if (this.shareParamsMap.containsKey("platform")) {
            Platform platform = ShareSDK.getPlatform(String.valueOf(this.shareParamsMap.get("platform")));
            boolean isCustomPlatform = platform instanceof CustomPlatform;
            boolean isUseClientToShare = isUseClientToShare(platform);
            if (this.silent || isCustomPlatform || isUseClientToShare) {
                shareSilently(platform);
            } else {
                prepareForEditPage(platform);
            }
        } else {
            showPlatformPage(context2);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isUseClientToShare(Platform platform) {
        String name = platform.getName();
        if ("Wechat".equals(name) || "WechatMoments".equals(name) || "WechatFavorite".equals(name) || "ShortMessage".equals(name) || "Email".equals(name) || "Qzone".equals(name) || "QQ".equals(name) || "Pinterest".equals(name) || "Instagram".equals(name) || "Yixin".equals(name) || "YixinMoments".equals(name) || "QZone".equals(name) || "Mingdao".equals(name) || LineChart.TYPE.equals(name) || "KakaoStory".equals(name) || "KakaoTalk".equals(name) || "Bluetooth".equals(name) || "WhatsApp".equals(name) || "BaiduTieba".equals(name) || "Laiwang".equals(name) || "LaiwangMoments".equals(name) || "Alipay".equals(name) || "AlipayMoments".equals(name) || "FacebookMessenger".equals(name) || "GooglePlus".equals(name) || "Dingding".equals(name) || "Youtube".equals(name) || "Meipai".equals(name)) {
            return true;
        }
        if ("Evernote".equals(name)) {
            if ("true".equals(platform.getDevinfo("ShareByAppClient"))) {
                return true;
            }
        } else if ("SinaWeibo".equals(name) && "true".equals(platform.getDevinfo("ShareByAppClient"))) {
            Intent test = new Intent("android.intent.action.SEND");
            test.setPackage("com.sina.weibo");
            test.setType("image/*");
            ResolveInfo ri = platform.getContext().getPackageManager().resolveActivity(test, 0);
            if (ri == null) {
                Intent test2 = new Intent("android.intent.action.SEND");
                test2.setPackage("com.sina.weibog3");
                test2.setType("image/*");
                ri = platform.getContext().getPackageManager().resolveActivity(test2, 0);
            }
            if (ri == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public final void shareSilently(Platform platform) {
        Platform.ShareParams sp;
        if (formateShareData(platform) && (sp = shareDataToShareParams(platform)) != null) {
            toast("ssdk_oks_sharing");
            if (this.customizeCallback != null) {
                this.customizeCallback.onShare(platform, sp);
            }
            if (this.disableSSO) {
                platform.SSOSetting(this.disableSSO);
            }
            platform.setPlatformActionListener(this.callback);
            platform.share(sp);
        }
    }

    private void prepareForEditPage(Platform platform) {
        Platform.ShareParams sp;
        if (formateShareData(platform) && (sp = shareDataToShareParams(platform)) != null) {
            ShareSDK.logDemoEvent(3, (Platform) null);
            if (this.customizeCallback != null) {
                this.customizeCallback.onShare(platform, sp);
            }
            showEditPage(this.context, platform, sp);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean formateShareData(Platform plat) {
        String name = plat.getName();
        if (("Alipay".equals(name) || "AlipayMoments".equals(name)) && !plat.isClientValid()) {
            toast("ssdk_alipay_client_inavailable");
            return false;
        } else if ("KakaoTalk".equals(name) && !plat.isClientValid()) {
            toast("ssdk_kakaotalk_client_inavailable");
            return false;
        } else if ("KakaoStory".equals(name) && !plat.isClientValid()) {
            toast("ssdk_kakaostory_client_inavailable");
            return false;
        } else if (LineChart.TYPE.equals(name) && !plat.isClientValid()) {
            toast("ssdk_line_client_inavailable");
            return false;
        } else if ("WhatsApp".equals(name) && !plat.isClientValid()) {
            toast("ssdk_whatsapp_client_inavailable");
            return false;
        } else if ("Pinterest".equals(name) && !plat.isClientValid()) {
            toast("ssdk_pinterest_client_inavailable");
            return false;
        } else if ("Instagram".equals(name) && !plat.isClientValid()) {
            toast("ssdk_instagram_client_inavailable");
            return false;
        } else if (!"QZone".equals(name) || plat.isClientValid()) {
            boolean isLaiwang = "Laiwang".equals(name);
            boolean isLaiwangMoments = "LaiwangMoments".equals(name);
            if ((isLaiwang || isLaiwangMoments) && !plat.isClientValid()) {
                toast("ssdk_laiwang_client_inavailable");
                return false;
            }
            if (!("YixinMoments".equals(name) || "Yixin".equals(name)) || plat.isClientValid()) {
                boolean isWechat = "WechatFavorite".equals(name) || "Wechat".equals(name) || "WechatMoments".equals(name);
                if (isWechat && !plat.isClientValid()) {
                    toast("ssdk_wechat_client_inavailable");
                    return false;
                } else if (!"FacebookMessenger".equals(name) || plat.isClientValid()) {
                    if (!this.shareParamsMap.containsKey("shareType")) {
                        int shareType = 1;
                        String imagePath = String.valueOf(this.shareParamsMap.get("imagePath"));
                        if (imagePath == null || !new File(imagePath).exists()) {
                            Bitmap viewToShare = (Bitmap) ResHelper.forceCast(this.shareParamsMap.get("viewToShare"));
                            if (viewToShare == null || viewToShare.isRecycled()) {
                                Object imageUrl = this.shareParamsMap.get("imageUrl");
                                if (imageUrl != null && !TextUtils.isEmpty(String.valueOf(imageUrl))) {
                                    shareType = 2;
                                    if (String.valueOf(imageUrl).endsWith(".gif") && isWechat) {
                                        shareType = 9;
                                    } else if (this.shareParamsMap.containsKey("url") && !TextUtils.isEmpty(this.shareParamsMap.get("url").toString())) {
                                        shareType = 4;
                                        if (this.shareParamsMap.containsKey("musicUrl") && !TextUtils.isEmpty(this.shareParamsMap.get("musicUrl").toString()) && isWechat) {
                                            shareType = 5;
                                        }
                                    }
                                }
                            } else {
                                shareType = 2;
                                if (this.shareParamsMap.containsKey("url") && !TextUtils.isEmpty(this.shareParamsMap.get("url").toString())) {
                                    shareType = 4;
                                    if (this.shareParamsMap.containsKey("musicUrl") && !TextUtils.isEmpty(this.shareParamsMap.get("musicUrl").toString()) && isWechat) {
                                        shareType = 5;
                                    }
                                }
                            }
                        } else {
                            shareType = 2;
                            if (imagePath.endsWith(".gif") && isWechat) {
                                shareType = 9;
                            } else if (this.shareParamsMap.containsKey("url") && !TextUtils.isEmpty(this.shareParamsMap.get("url").toString())) {
                                shareType = 4;
                                if (this.shareParamsMap.containsKey("musicUrl") && !TextUtils.isEmpty(this.shareParamsMap.get("musicUrl").toString()) && isWechat) {
                                    shareType = 5;
                                }
                            }
                        }
                        this.shareParamsMap.put("shareType", Integer.valueOf(shareType));
                    }
                    return true;
                } else {
                    toast("ssdk_facebookmessenger_client_inavailable");
                    return false;
                }
            } else {
                toast("ssdk_yixin_client_inavailable");
                return false;
            }
        } else {
            toast("ssdk_qq_client_inavailable");
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public final Platform.ShareParams shareDataToShareParams(Platform plat) {
        if (plat == null || this.shareParamsMap == null) {
            toast("ssdk_oks_share_failed");
            return null;
        }
        try {
            Bitmap viewToShare = (Bitmap) ResHelper.forceCast(this.shareParamsMap.get("viewToShare"));
            if (TextUtils.isEmpty((String) ResHelper.forceCast(this.shareParamsMap.get("imagePath"))) && viewToShare != null && !viewToShare.isRecycled()) {
                File ss = new File(ResHelper.getCachePath(plat.getContext(), "screenshot"), String.valueOf(System.currentTimeMillis()) + ".jpg");
                FileOutputStream fos = new FileOutputStream(ss);
                viewToShare.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                this.shareParamsMap.put("imagePath", ss.getAbsolutePath());
            }
            return new Platform.ShareParams(this.shareParamsMap);
        } catch (Throwable t) {
            t.printStackTrace();
            toast("ssdk_oks_share_failed");
            return null;
        }
    }

    private void toast(final String resOrName) {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                int resId = ResHelper.getStringRes(OnekeyShareThemeImpl.this.context, resOrName);
                if (resId > 0) {
                    Toast.makeText(OnekeyShareThemeImpl.this.context, resId, 0).show();
                } else {
                    Toast.makeText(OnekeyShareThemeImpl.this.context, resOrName, 0).show();
                }
                return false;
            }
        });
    }

    public final void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    public final void onError(Platform platform, int action, Throwable t) {
        t.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
        ShareSDK.logDemoEvent(4, platform);
    }

    public final void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
        ShareSDK.logDemoEvent(5, platform);
    }

    public final boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1:
                int resId = ResHelper.getStringRes(this.context, "ssdk_oks_share_completed");
                if (resId <= 0) {
                    return false;
                }
                toast(this.context.getString(resId));
                return false;
            case 2:
                String expName = msg.obj.getClass().getSimpleName();
                if ("WechatClientNotExistException".equals(expName) || "WechatTimelineNotSupportedException".equals(expName) || "WechatFavoriteNotSupportedException".equals(expName)) {
                    toast("ssdk_wechat_client_inavailable");
                    return false;
                } else if ("GooglePlusClientNotExistException".equals(expName)) {
                    toast("ssdk_google_plus_client_inavailable");
                    return false;
                } else if ("QQClientNotExistException".equals(expName)) {
                    toast("ssdk_qq_client_inavailable");
                    return false;
                } else if ("YixinClientNotExistException".equals(expName) || "YixinTimelineNotSupportedException".equals(expName)) {
                    toast("ssdk_yixin_client_inavailable");
                    return false;
                } else if ("KakaoTalkClientNotExistException".equals(expName)) {
                    toast("ssdk_kakaotalk_client_inavailable");
                    return false;
                } else if ("KakaoStoryClientNotExistException".equals(expName)) {
                    toast("ssdk_kakaostory_client_inavailable");
                    return false;
                } else if ("WhatsAppClientNotExistException".equals(expName)) {
                    toast("ssdk_whatsapp_client_inavailable");
                    return false;
                } else if ("FacebookMessengerClientNotExistException".equals(expName)) {
                    toast("ssdk_facebookmessenger_client_inavailable");
                    return false;
                } else {
                    toast("ssdk_oks_share_failed");
                    return false;
                }
            case 3:
                toast("ssdk_oks_share_canceled");
                return false;
            default:
                return false;
        }
    }
}

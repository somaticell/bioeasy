package cn.sharesdk.wechat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageGalleryActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.DeviceHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WechatHelper {
    private static WechatHelper a;
    private h b = new h();
    private i c;
    private String d;
    private String e;

    public static class ShareParams extends Platform.ShareParams {
        @Deprecated
        public String extInfo;
        @Deprecated
        public String filePath;
        @Deprecated
        public Bitmap imageData;
        @Deprecated
        public String imageUrl;
        @Deprecated
        public String musicUrl;
        @Deprecated
        protected int scene;
        @Deprecated
        public int shareType;
        @Deprecated
        public String title;
        @Deprecated
        public String url;
    }

    public static WechatHelper a() {
        if (a == null) {
            a = new WechatHelper();
        }
        return a;
    }

    public void a(String str) {
        this.e = str;
    }

    public void b(String str) {
        this.d = str;
    }

    private WechatHelper() {
    }

    public void a(i iVar) throws Throwable {
        this.c = iVar;
        a aVar = new a();
        aVar.a = "snsapi_userinfo";
        aVar.b = "sharesdk_wechat_auth";
        this.b.a((j) aVar);
    }

    public boolean b() {
        return this.b.a();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00d4, code lost:
        if (com.mob.tools.utils.ResHelper.copyFile(r0, r4) != false) goto L_0x00d6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(cn.sharesdk.wechat.utils.i r12, cn.sharesdk.framework.Platform.ShareParams r13, cn.sharesdk.framework.PlatformActionListener r14) throws java.lang.Throwable {
        /*
            r11 = this;
            r10 = 0
            cn.sharesdk.framework.Platform r5 = r12.b()
            java.lang.String r0 = r13.getImagePath()
            java.lang.String r1 = r13.getImageUrl()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x001e
            java.io.File r2 = new java.io.File
            r2.<init>(r0)
            boolean r2 = r2.exists()
            if (r2 != 0) goto L_0x0066
        L_0x001e:
            android.graphics.Bitmap r2 = r13.getImageData()
            if (r2 == 0) goto L_0x0154
            boolean r3 = r2.isRecycled()
            if (r3 != 0) goto L_0x0154
            android.content.Context r0 = r5.getContext()
            java.lang.String r1 = "images"
            java.lang.String r0 = com.mob.tools.utils.ResHelper.getCachePath(r0, r1)
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            long r6 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r4 = ".png"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r0, r3)
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r1)
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG
            r4 = 100
            r2.compress(r3, r4, r0)
            r0.flush()
            r0.close()
            java.lang.String r0 = r1.getAbsolutePath()
        L_0x0066:
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.SEND"
            r3.<init>(r1)
            java.lang.String r1 = r13.getText()
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0088
            java.lang.String r1 = r5.getShortLintk(r1, r10)
            r13.setText(r1)
            java.lang.String r2 = "android.intent.extra.TEXT"
            r3.putExtra(r2, r1)
            java.lang.String r2 = "Kdescription"
            r3.putExtra(r2, r1)
        L_0x0088:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x016e
            java.io.File r2 = new java.io.File
            r2.<init>(r0)
            boolean r1 = r2.exists()
            if (r1 == 0) goto L_0x0102
            java.lang.String r1 = "/data/"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x0184
            android.content.Context r1 = r5.getContext()
            java.lang.String r4 = "images"
            java.lang.String r4 = com.mob.tools.utils.ResHelper.getCachePath(r1, r4)
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            long r8 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r7 = r2.getName()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r1.<init>(r4, r6)
            java.lang.String r4 = r1.getAbsolutePath()
            r1.createNewFile()
            boolean r4 = com.mob.tools.utils.ResHelper.copyFile((java.lang.String) r0, (java.lang.String) r4)
            if (r4 == 0) goto L_0x0184
        L_0x00d6:
            int r2 = android.os.Build.VERSION.SDK_INT
            r4 = 24
            if (r2 < r4) goto L_0x0164
            java.lang.String r2 = "android.intent.extra.STREAM"
            android.content.Context r4 = r5.getContext()
            java.lang.String r1 = r1.getAbsolutePath()
            android.net.Uri r1 = com.mob.tools.utils.ResHelper.pathToContentUri(r4, r1)
            r3.putExtra(r2, r1)
        L_0x00ed:
            java.net.FileNameMap r1 = java.net.URLConnection.getFileNameMap()
            java.lang.String r0 = r1.getContentTypeFor(r0)
            if (r0 == 0) goto L_0x00fd
            int r1 = r0.length()
            if (r1 > 0) goto L_0x00ff
        L_0x00fd:
            java.lang.String r0 = "image/*"
        L_0x00ff:
            r3.setType(r0)
        L_0x0102:
            java.lang.String r0 = "scene"
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            java.lang.Object r0 = r13.get(r0, r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r1 = 1
            if (r0 != r1) goto L_0x0174
            java.lang.String r0 = "com.tencent.mm.ui.tools.ShareToTimeLineUI"
        L_0x0115:
            java.lang.String r1 = "com.tencent.mm"
            r3.setClassName(r1, r0)
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            r3.addFlags(r0)
            android.content.Context r0 = r5.getContext()
            r0.startActivity(r3)
            android.content.Context r0 = r5.getContext()
            com.mob.tools.utils.DeviceHelper r2 = com.mob.tools.utils.DeviceHelper.getInstance(r0)
            android.content.Context r0 = r5.getContext()
            java.lang.String r3 = r0.getPackageName()
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            java.lang.String r0 = "ShareParams"
            r6.put(r0, r13)
            java.lang.String r0 = r2.getTopTaskPackageName()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0177
            if (r14 == 0) goto L_0x0153
            if (r14 == 0) goto L_0x0153
            r0 = 9
            r14.onComplete(r5, r0, r6)
        L_0x0153:
            return
        L_0x0154:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0066
            android.content.Context r0 = r5.getContext()
            java.lang.String r0 = com.mob.tools.utils.BitmapHelper.downloadBitmap(r0, r1)
            goto L_0x0066
        L_0x0164:
            java.lang.String r2 = "android.intent.extra.STREAM"
            android.net.Uri r1 = android.net.Uri.fromFile(r1)
            r3.putExtra(r2, r1)
            goto L_0x00ed
        L_0x016e:
            java.lang.String r0 = "text/plain"
            r3.setType(r0)
            goto L_0x0102
        L_0x0174:
            java.lang.String r0 = "com.tencent.mm.ui.tools.ShareImgUI"
            goto L_0x0115
        L_0x0177:
            r8 = 2000(0x7d0, double:9.88E-321)
            cn.sharesdk.wechat.utils.WechatHelper$1 r0 = new cn.sharesdk.wechat.utils.WechatHelper$1
            r1 = r11
            r4 = r14
            r0.<init>(r2, r3, r4, r5, r6)
            com.mob.tools.utils.UIHandler.sendEmptyMessageDelayed(r10, r8, r0)
            goto L_0x0153
        L_0x0184:
            r1 = r2
            goto L_0x00d6
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.wechat.utils.WechatHelper.a(cn.sharesdk.wechat.utils.i, cn.sharesdk.framework.Platform$ShareParams, cn.sharesdk.framework.PlatformActionListener):void");
    }

    public void b(i iVar) throws Throwable {
        Platform b2 = iVar.b();
        Platform.ShareParams a2 = iVar.a();
        PlatformActionListener c2 = iVar.c();
        int shareType = a2.getShareType();
        String title = a2.getTitle();
        String text = a2.getText();
        int scence = a2.getScence();
        String imagePath = a2.getImagePath();
        String imageUrl = a2.getImageUrl();
        Bitmap imageData = a2.getImageData();
        String musicUrl = a2.getMusicUrl();
        String url = a2.getUrl();
        String filePath = a2.getFilePath();
        String extInfo = a2.getExtInfo();
        Context context = b2.getContext();
        switch (shareType) {
            case 1:
                a(title, text, scence, iVar);
                return;
            case 2:
                if (imagePath != null && imagePath.length() > 0) {
                    a(context, title, text, imagePath, scence, iVar);
                    return;
                } else if (imageData != null && !imageData.isRecycled()) {
                    a(context, title, text, imageData, scence, iVar);
                    return;
                } else if (imageUrl == null || imageUrl.length() <= 0) {
                    a(context, title, text, "", scence, iVar);
                    return;
                } else {
                    a(context, title, text, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                    return;
                }
            case 4:
                String shortLintk = b2.getShortLintk(url, false);
                iVar.a().setUrl(shortLintk);
                if (imagePath != null && imagePath.length() > 0) {
                    b(context, title, text, shortLintk, imagePath, scence, iVar);
                    return;
                } else if (imageData != null && !imageData.isRecycled()) {
                    b(context, title, text, shortLintk, imageData, scence, iVar);
                    return;
                } else if (imageUrl == null || imageUrl.length() <= 0) {
                    b(context, title, text, shortLintk, "", scence, iVar);
                    return;
                } else {
                    b(context, title, text, shortLintk, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                    return;
                }
            case 5:
                String shortLintk2 = b2.getShortLintk(musicUrl + " " + url, false);
                String str = shortLintk2.split(" ")[0];
                String str2 = shortLintk2.split(" ")[1];
                if (imagePath != null && imagePath.length() > 0) {
                    a(context, title, text, str, str2, imagePath, scence, iVar);
                    return;
                } else if (imageData != null && !imageData.isRecycled()) {
                    a(context, title, text, str, str2, imageData, scence, iVar);
                    return;
                } else if (imageUrl == null || imageUrl.length() <= 0) {
                    a(context, title, text, str, str2, "", scence, iVar);
                    return;
                } else {
                    a(context, title, text, str, str2, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                    return;
                }
            case 6:
                String shortLintk3 = b2.getShortLintk(url, false);
                iVar.a().setUrl(shortLintk3);
                if (imagePath != null && imagePath.length() > 0) {
                    a(context, title, text, shortLintk3, imagePath, scence, iVar);
                    return;
                } else if (imageData != null && !imageData.isRecycled()) {
                    a(context, title, text, shortLintk3, imageData, scence, iVar);
                    return;
                } else if (imageUrl == null || imageUrl.length() <= 0) {
                    a(context, title, text, shortLintk3, "", scence, iVar);
                    return;
                } else {
                    a(context, title, text, shortLintk3, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                    return;
                }
            case 7:
                if (scence == 1) {
                    throw new Throwable("WechatMoments does not support SAHRE_APP");
                } else if (scence == 2) {
                    throw new Throwable("WechatFavorite does not support SAHRE_APP");
                } else if (imagePath != null && imagePath.length() > 0) {
                    b(context, title, text, filePath, extInfo, imagePath, scence, iVar);
                    return;
                } else if (imageData != null && !imageData.isRecycled()) {
                    b(context, title, text, filePath, extInfo, imageData, scence, iVar);
                    return;
                } else if (imageUrl == null || imageUrl.length() <= 0) {
                    b(context, title, text, filePath, extInfo, "", scence, iVar);
                    return;
                } else {
                    b(context, title, text, filePath, extInfo, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                    return;
                }
            case 8:
                if (scence == 1) {
                    throw new Throwable("WechatMoments does not support SHARE_FILE");
                } else if (imagePath != null && imagePath.length() > 0) {
                    c(context, title, text, filePath, imagePath, scence, iVar);
                    return;
                } else if (imageData != null && !imageData.isRecycled()) {
                    c(context, title, text, filePath, imageData, scence, iVar);
                    return;
                } else if (imageUrl == null || imageUrl.length() <= 0) {
                    c(context, title, text, filePath, "", scence, iVar);
                    return;
                } else {
                    c(context, title, text, filePath, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                    return;
                }
            case 9:
                if (scence == 1) {
                    throw new Throwable("WechatMoments does not support SHARE_EMOJI");
                } else if (scence == 2) {
                    throw new Throwable("WechatFavorite does not support SHARE_EMOJI");
                } else if (imagePath != null && imagePath.length() > 0) {
                    b(context, title, text, imagePath, scence, iVar);
                    return;
                } else if (imageUrl != null && imageUrl.length() > 0) {
                    b(context, title, text, new NetworkHelper().downloadCache(b2.getContext(), imageUrl, ImageGalleryActivity.KEY_IMAGE, true, (NetworkHelper.NetworkTimeOut) null), scence, iVar);
                    return;
                } else if (imageData == null || imageData.isRecycled()) {
                    b(context, title, text, "", scence, iVar);
                    return;
                } else {
                    b(context, title, text, imageData, scence, iVar);
                    return;
                }
            case 11:
                if (scence == 1) {
                    throw new Throwable("WechatMoments does not support SAHRE_APP");
                } else if (scence == 2) {
                    throw new Throwable("WechatFavorite does not support SAHRE_APP");
                } else if (TextUtils.isEmpty(this.d) || TextUtils.isEmpty(this.e)) {
                    throw new Throwable("userName or path does not null");
                } else {
                    String shortLintk4 = b2.getShortLintk(url, false);
                    iVar.a().setUrl(shortLintk4);
                    if (imagePath != null && imagePath.length() > 0) {
                        a(context, shortLintk4, this.d, this.e, title, text, imagePath, scence, iVar);
                        return;
                    } else if (imageData != null && !imageData.isRecycled()) {
                        a(context, shortLintk4, this.d, this.e, title, text, imageData, scence, iVar);
                        return;
                    } else if (imageUrl == null || imageUrl.length() <= 0) {
                        a(context, shortLintk4, this.d, this.e, title, text, "", scence, iVar);
                        return;
                    } else {
                        Context context2 = context;
                        a(context2, shortLintk4, this.d, this.e, title, text, BitmapHelper.downloadBitmap(b2.getContext(), imageUrl), scence, iVar);
                        return;
                    }
                }
            default:
                if (c2 != null) {
                    c2.onError(b2, 9, new IllegalArgumentException("shareType = " + shareType));
                    return;
                }
                return;
        }
    }

    private void a(String str, String str2, int i, i iVar) throws Throwable {
        WXTextObject wXTextObject = new WXTextObject();
        wXTextObject.text = str2;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.mediaObject = wXTextObject;
        wXMediaMessage.description = str2;
        a(wXMediaMessage, "text", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, int i, i iVar) throws Throwable {
        WXImageObject wXImageObject = new WXImageObject();
        if (str3.startsWith("/data/")) {
            wXImageObject.imageData = c(str3);
        } else {
            wXImageObject.imagePath = str3;
        }
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.mediaObject = wXImageObject;
        if (i != 0) {
            wXMediaMessage.title = str;
            wXMediaMessage.description = str2;
        }
        wXMediaMessage.thumbData = b(context, str3);
        a(wXMediaMessage, "img", i, iVar);
    }

    private void a(Context context, String str, String str2, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXImageObject wXImageObject = new WXImageObject();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        wXImageObject.imageData = byteArrayOutputStream.toByteArray();
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.mediaObject = wXImageObject;
        if (i != 0) {
            wXMediaMessage.title = str;
            wXMediaMessage.description = str2;
        }
        wXMediaMessage.thumbData = a(context, bitmap);
        a(wXMediaMessage, "img", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, String str4, String str5, int i, i iVar) throws Throwable {
        WXMusicObject wXMusicObject = new WXMusicObject();
        wXMusicObject.musicUrl = str4;
        wXMusicObject.musicDataUrl = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXMusicObject;
        wXMediaMessage.thumbData = b(context, str5);
        a(wXMediaMessage, "music", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, String str4, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXMusicObject wXMusicObject = new WXMusicObject();
        wXMusicObject.musicUrl = str4;
        wXMusicObject.musicDataUrl = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXMusicObject;
        wXMediaMessage.thumbData = a(context, bitmap);
        a(wXMediaMessage, "music", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, String str4, int i, i iVar) throws Throwable {
        WXVideoObject wXVideoObject = new WXVideoObject();
        wXVideoObject.videoUrl = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXVideoObject;
        wXMediaMessage.thumbData = b(context, str4);
        a(wXMediaMessage, "video", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXVideoObject wXVideoObject = new WXVideoObject();
        wXVideoObject.videoUrl = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXVideoObject;
        wXMediaMessage.thumbData = a(context, bitmap);
        a(wXMediaMessage, "video", i, iVar);
    }

    private void b(Context context, String str, String str2, String str3, String str4, int i, i iVar) throws Throwable {
        WXWebpageObject wXWebpageObject = new WXWebpageObject();
        wXWebpageObject.webpageUrl = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXWebpageObject;
        if (str4 != null && new File(str4).exists()) {
            wXMediaMessage.thumbData = b(context, str4);
            if (wXMediaMessage.thumbData == null) {
                throw new RuntimeException("checkArgs fail, thumbData is null");
            } else if (wXMediaMessage.thumbData.length > 32768) {
                throw new RuntimeException("checkArgs fail, thumbData is too large: " + wXMediaMessage.thumbData.length + " > " + 32768);
            }
        }
        a(wXMediaMessage, "webpage", i, iVar);
    }

    private void b(Context context, String str, String str2, String str3, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXWebpageObject wXWebpageObject = new WXWebpageObject();
        wXWebpageObject.webpageUrl = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXWebpageObject;
        if (bitmap != null && !bitmap.isRecycled()) {
            wXMediaMessage.thumbData = a(context, bitmap);
            if (wXMediaMessage.thumbData == null) {
                throw new RuntimeException("checkArgs fail, thumbData is null");
            } else if (wXMediaMessage.thumbData.length > 32768) {
                throw new RuntimeException("checkArgs fail, thumbData is too large: " + wXMediaMessage.thumbData.length + " > " + 32768);
            }
        }
        a(wXMediaMessage, "webpage", i, iVar);
    }

    private void b(Context context, String str, String str2, String str3, String str4, String str5, int i, i iVar) throws Throwable {
        WXAppExtendObject wXAppExtendObject = new WXAppExtendObject();
        wXAppExtendObject.filePath = str3;
        wXAppExtendObject.extInfo = str4;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXAppExtendObject;
        wXMediaMessage.thumbData = b(context, str5);
        a(wXMediaMessage, "appdata", i, iVar);
    }

    private void b(Context context, String str, String str2, String str3, String str4, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXAppExtendObject wXAppExtendObject = new WXAppExtendObject();
        wXAppExtendObject.filePath = str3;
        wXAppExtendObject.extInfo = str4;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXAppExtendObject;
        wXMediaMessage.thumbData = a(context, bitmap);
        a(wXMediaMessage, "appdata", i, iVar);
    }

    private void c(Context context, String str, String str2, String str3, String str4, int i, i iVar) throws Throwable {
        WXFileObject wXFileObject = new WXFileObject();
        wXFileObject.filePath = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXFileObject;
        wXMediaMessage.thumbData = b(context, str4);
        a(wXMediaMessage, "filedata", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, String str4, String str5, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXMiniProgramObject wXMiniProgramObject = new WXMiniProgramObject();
        wXMiniProgramObject.webpageUrl = str;
        wXMiniProgramObject.userName = str2;
        wXMiniProgramObject.path = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str4;
        wXMediaMessage.mediaObject = wXMiniProgramObject;
        wXMediaMessage.description = str5;
        wXMediaMessage.thumbData = a(context, bitmap);
        a(wXMediaMessage, "webpage", i, iVar);
    }

    private void a(Context context, String str, String str2, String str3, String str4, String str5, String str6, int i, i iVar) throws Throwable {
        WXMiniProgramObject wXMiniProgramObject = new WXMiniProgramObject();
        wXMiniProgramObject.webpageUrl = str;
        wXMiniProgramObject.userName = str2;
        wXMiniProgramObject.path = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str4;
        wXMediaMessage.mediaObject = wXMiniProgramObject;
        wXMediaMessage.description = str5;
        wXMediaMessage.thumbData = b(context, str6);
        a(wXMediaMessage, "webpage", i, iVar);
    }

    private void c(Context context, String str, String str2, String str3, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXFileObject wXFileObject = new WXFileObject();
        wXFileObject.filePath = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.description = str2;
        wXMediaMessage.mediaObject = wXFileObject;
        wXMediaMessage.thumbData = a(context, bitmap);
        a(wXMediaMessage, "filedata", i, iVar);
    }

    private void b(Context context, String str, String str2, String str3, int i, i iVar) throws Throwable {
        WXEmojiObject wXEmojiObject = new WXEmojiObject();
        wXEmojiObject.emojiPath = str3;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.mediaObject = wXEmojiObject;
        wXMediaMessage.description = str2;
        wXMediaMessage.thumbData = b(context, str3);
        a(wXMediaMessage, "emoji", i, iVar);
    }

    private void b(Context context, String str, String str2, Bitmap bitmap, int i, i iVar) throws Throwable {
        WXEmojiObject wXEmojiObject = new WXEmojiObject();
        byte[] a2 = a(context, bitmap);
        wXEmojiObject.emojiData = a2;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = str;
        wXMediaMessage.mediaObject = wXEmojiObject;
        wXMediaMessage.description = str2;
        wXMediaMessage.thumbData = a2;
        a(wXMediaMessage, "emoji", i, iVar);
    }

    private byte[] c(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            for (int read = fileInputStream.read(bArr); read > 0; read = fileInputStream.read(bArr)) {
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.flush();
            fileInputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    private byte[] b(Context context, String str) throws Throwable {
        if (!new File(str).exists()) {
            throw new FileNotFoundException();
        }
        return a(context, BitmapHelper.getBitmap(str), BitmapHelper.getBmpFormat(str));
    }

    private byte[] a(Context context, Bitmap bitmap) throws Throwable {
        if (bitmap == null) {
            throw new RuntimeException("checkArgs fail, thumbData is null");
        } else if (!bitmap.isRecycled()) {
            return a(context, bitmap, Bitmap.CompressFormat.PNG);
        } else {
            throw new RuntimeException("checkArgs fail, thumbData is recycled");
        }
    }

    private byte[] a(Context context, Bitmap bitmap, Bitmap.CompressFormat compressFormat) throws Throwable {
        if (bitmap == null) {
            throw new RuntimeException("checkArgs fail, thumbData is null");
        } else if (bitmap.isRecycled()) {
            throw new RuntimeException("checkArgs fail, thumbData is recycled");
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(compressFormat, 100, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            int length = byteArray.length;
            while (length > 32768) {
                bitmap = a(bitmap, ((double) length) / 32768.0d);
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                bitmap.compress(compressFormat, 100, byteArrayOutputStream2);
                byteArrayOutputStream2.flush();
                byteArrayOutputStream2.close();
                byteArray = byteArrayOutputStream2.toByteArray();
                length = byteArray.length;
            }
            return byteArray;
        }
    }

    private Bitmap a(Bitmap bitmap, double d2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        double sqrt = Math.sqrt(d2);
        return Bitmap.createScaledBitmap(bitmap, (int) (((double) width) / sqrt), (int) (((double) height) / sqrt), true);
    }

    public boolean a(Context context, String str) {
        return this.b.a(context, str);
    }

    public boolean a(WechatHandlerActivity wechatHandlerActivity) {
        return this.b.a(wechatHandlerActivity, this.c);
    }

    public boolean c() {
        return this.b.b();
    }

    public boolean d() {
        return this.b.c();
    }

    private void a(WXMediaMessage wXMediaMessage, String str, int i, i iVar) throws Throwable {
        Class<?> cls;
        String str2 = DeviceHelper.getInstance(iVar.b().getContext()).getPackageName() + ".wxapi.WXEntryActivity";
        try {
            cls = Class.forName(str2);
        } catch (Throwable th) {
            d.a().d(th);
            cls = null;
        }
        if (cls != null && !WechatHandlerActivity.class.isAssignableFrom(cls)) {
            new Throwable(str2 + " does not extend from " + WechatHandlerActivity.class.getName()).printStackTrace();
        }
        d dVar = new d();
        dVar.c = str + System.currentTimeMillis();
        dVar.a = wXMediaMessage;
        dVar.b = i;
        this.c = iVar;
        this.b.a((j) dVar);
    }
}

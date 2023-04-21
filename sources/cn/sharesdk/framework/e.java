package cn.sharesdk.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import cn.sharesdk.framework.b.b.a;
import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.utils.d;
import com.mob.commons.eventrecoder.EventRecorder;
import com.mob.tools.utils.ResHelper;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: ShareSDKCore */
public class e {
    private Context a;
    private String b;

    public e(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public ArrayList<Platform> a() {
        ArrayList<Platform> d = d();
        a(d);
        return d;
    }

    private ArrayList<Platform> d() {
        ArrayList<Platform> arrayList = new ArrayList<>();
        for (String cls : new String[]{"cn.sharesdk.douban.Douban", "cn.sharesdk.evernote.Evernote", "cn.sharesdk.facebook.Facebook", "cn.sharesdk.renren.Renren", "cn.sharesdk.sina.weibo.SinaWeibo", "cn.sharesdk.kaixin.KaiXin", "cn.sharesdk.linkedin.LinkedIn", "cn.sharesdk.system.email.Email", "cn.sharesdk.system.text.ShortMessage", "cn.sharesdk.tencent.qq.QQ", "cn.sharesdk.tencent.qzone.QZone", "cn.sharesdk.tencent.weibo.TencentWeibo", "cn.sharesdk.twitter.Twitter", "cn.sharesdk.wechat.friends.Wechat", "cn.sharesdk.wechat.moments.WechatMoments", "cn.sharesdk.wechat.favorite.WechatFavorite", "cn.sharesdk.youdao.YouDao", "cn.sharesdk.google.GooglePlus", "cn.sharesdk.foursquare.FourSquare", "cn.sharesdk.pinterest.Pinterest", "cn.sharesdk.flickr.Flickr", "cn.sharesdk.tumblr.Tumblr", "cn.sharesdk.dropbox.Dropbox", "cn.sharesdk.vkontakte.VKontakte", "cn.sharesdk.instagram.Instagram", "cn.sharesdk.yixin.friends.Yixin", "cn.sharesdk.yixin.moments.YixinMoments", "cn.sharesdk.mingdao.Mingdao", "cn.sharesdk.line.Line", "cn.sharesdk.kakao.story.KakaoStory", "cn.sharesdk.kakao.talk.KakaoTalk", "cn.sharesdk.whatsapp.WhatsApp", "cn.sharesdk.pocket.Pocket", "cn.sharesdk.instapaper.Instapaper", "cn.sharesdk.facebookmessenger.FacebookMessenger", "cn.sharesdk.alipay.friends.Alipay", "cn.sharesdk.alipay.moments.AlipayMoments", "cn.sharesdk.dingding.friends.Dingding", "cn.sharesdk.youtube.Youtube", "cn.sharesdk.meipai.Meipai"}) {
            try {
                Constructor<?> constructor = Class.forName(cls).getConstructor(new Class[]{Context.class});
                constructor.setAccessible(true);
                arrayList.add((Platform) constructor.newInstance(new Object[]{this.a}));
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        return arrayList;
    }

    public void a(ArrayList<Platform> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList, new Comparator<Platform>() {
                /* renamed from: a */
                public int compare(Platform platform, Platform platform2) {
                    if (platform.getSortId() != platform2.getSortId()) {
                        return platform.getSortId() - platform2.getSortId();
                    }
                    return platform.getPlatformId() - platform2.getPlatformId();
                }
            });
        }
    }

    public void a(Handler handler, boolean z, int i) {
        cn.sharesdk.framework.b.d a2 = cn.sharesdk.framework.b.d.a(this.a, this.b);
        if (a2 != null) {
            a2.a(handler);
            a2.a(z);
            a2.a(i);
            a2.startThread();
        }
    }

    public void b() {
        cn.sharesdk.framework.b.d a2 = cn.sharesdk.framework.b.d.a(this.a, this.b);
        if (a2 != null) {
            a2.stopThread();
        }
    }

    public void a(int i, Platform platform) {
        cn.sharesdk.framework.b.b.d dVar = new cn.sharesdk.framework.b.b.d();
        switch (i) {
            case 1:
                dVar.a = "SHARESDK_ENTER_SHAREMENU";
                break;
            case 2:
                dVar.a = "SHARESDK_CANCEL_SHAREMENU";
                break;
            case 3:
                dVar.a = "SHARESDK_EDIT_SHARE";
                break;
            case 4:
                dVar.a = "SHARESDK_FAILED_SHARE";
                break;
            case 5:
                dVar.a = "SHARESDK_CANCEL_SHARE";
                break;
        }
        if (platform != null) {
            dVar.b = platform.getPlatformId();
        }
        cn.sharesdk.framework.b.d a2 = cn.sharesdk.framework.b.d.a(this.a, this.b);
        if (a2 != null) {
            a2.a((c) dVar);
        }
    }

    public void a(String str, int i) {
        cn.sharesdk.framework.b.d a2 = cn.sharesdk.framework.b.d.a(this.a, this.b);
        if (a2 != null) {
            a aVar = new a();
            aVar.b = str;
            aVar.a = i;
            a2.a((c) aVar);
        }
    }

    public void a(f fVar) {
    }

    public String a(int i, String str, HashMap<Integer, HashMap<String, Object>> hashMap) {
        HashMap hashMap2 = hashMap.get(Integer.valueOf(i));
        if (hashMap2 == null) {
            return null;
        }
        Object obj = hashMap2.get(str);
        return obj == null ? null : String.valueOf(obj);
    }

    public boolean a(HashMap<String, Object> hashMap, HashMap<Integer, HashMap<String, Object>> hashMap2) {
        int i;
        if (hashMap == null || hashMap.size() <= 0) {
            return false;
        }
        ArrayList arrayList = (ArrayList) hashMap.get("fakelist");
        if (arrayList == null) {
            return false;
        }
        EventRecorder.addBegin("ShareSDK", "parseDevInfo");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap3 = (HashMap) it.next();
            if (hashMap3 != null) {
                try {
                    i = ResHelper.parseInt(String.valueOf(hashMap3.get("snsplat")));
                } catch (Throwable th) {
                    d.a().w(th);
                    i = -1;
                }
                if (i != -1) {
                    hashMap2.put(Integer.valueOf(i), hashMap3);
                }
            }
        }
        EventRecorder.addEnd("ShareSDK", "parseDevInfo");
        return true;
    }

    public String a(String str, boolean z, int i, String str2) {
        return cn.sharesdk.framework.b.a.a(this.a, this.b).a(str, i, z, str2);
    }

    public String a(String str) {
        return cn.sharesdk.framework.b.a.a(this.a, this.b).a(str);
    }

    public String a(Bitmap bitmap) {
        return cn.sharesdk.framework.b.a.a(this.a, this.b).a(bitmap);
    }

    public String c() {
        return "2.8.3";
    }

    public void a(int i, int i2, HashMap<Integer, HashMap<String, Object>> hashMap) {
        hashMap.put(Integer.valueOf(i2), hashMap.get(Integer.valueOf(i)));
    }
}

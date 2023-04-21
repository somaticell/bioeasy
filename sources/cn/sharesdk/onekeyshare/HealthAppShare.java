package cn.sharesdk.onekeyshare;

import android.content.Context;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;

public class HealthAppShare {
    public static void showShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("标题");
        oks.setTitleUrl("http://sharesdk.cn");
        oks.setText("我是分享文本");
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setUrl("http://sharesdk.cn");
        oks.setComment("我是测试评论文本");
        oks.setSite("ShareSDK");
        oks.setSiteUrl("http://sharesdk.cn");
        oks.show(context);
    }

    public static void shareWxContent(Context context, Content content, String url) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(content.getTitle());
        oks.setTitleUrl(url);
        oks.setText(content.getSummary());
        oks.setImageUrl(content.getIcon());
        oks.setUrl(url);
        oks.setComment(content.getSummary());
        oks.setSite(url);
        oks.setSiteUrl(url);
        oks.show(context);
    }

    public static void shareTestReesult(Context context, SampleData sampleData, String url) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(sampleData.getSampleName() + "检测结果");
        oks.setTitleUrl(url);
        oks.setText("");
        oks.setUrl(url);
        oks.setSite(url);
        oks.setSiteUrl(url);
        oks.show(context);
    }
}

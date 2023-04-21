package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal.SelectOptions;

public class CropActivity extends BIBaseActivity implements View.OnClickListener {
    private static SelectOptions mOption;
    private CropLayout mCropLayout;

    public static void show(Fragment fragment, SelectOptions options) {
        Intent intent = new Intent(fragment.getActivity(), CropActivity.class);
        mOption = options;
        fragment.startActivityForResult(intent, 4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006e  */
    @butterknife.OnClick({2131230922, 2131230923})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r11) {
        /*
            r10 = this;
            r9 = 1
            r8 = 0
            int r6 = r11.getId()
            switch(r6) {
                case 2131230922: goto L_0x000a;
                case 2131230923: goto L_0x0079;
                default: goto L_0x0009;
            }
        L_0x0009:
            return
        L_0x000a:
            r0 = 0
            r3 = 0
            cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.CropLayout r6 = r10.mCropLayout     // Catch:{ Exception -> 0x005a }
            android.graphics.Bitmap r0 = r6.cropBitmap()     // Catch:{ Exception -> 0x005a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005a }
            r6.<init>()     // Catch:{ Exception -> 0x005a }
            java.io.File r7 = r10.getFilesDir()     // Catch:{ Exception -> 0x005a }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x005a }
            java.lang.String r7 = "/crop.jpg"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x005a }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x005a }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x005a }
            r4.<init>(r5)     // Catch:{ Exception -> 0x005a }
            android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            r7 = 100
            r0.compress(r6, r7, r4)     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            r4.flush()     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            r4.close()     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            android.content.Intent r2 = new android.content.Intent     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            r2.<init>()     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            java.lang.String r6 = "crop_path"
            r2.putExtra(r6, r5)     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            r6 = -1
            r10.setResult(r6, r2)     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            r10.finish()     // Catch:{ Exception -> 0x0080, all -> 0x007d }
            if (r0 == 0) goto L_0x0051
            r0.recycle()
        L_0x0051:
            java.io.Closeable[] r6 = new java.io.Closeable[r9]
            r6[r8] = r4
            net.oschina.common.utils.StreamUtil.close(r6)
            r3 = r4
            goto L_0x0009
        L_0x005a:
            r1 = move-exception
        L_0x005b:
            r1.printStackTrace()     // Catch:{ all -> 0x006b }
            if (r0 == 0) goto L_0x0063
            r0.recycle()
        L_0x0063:
            java.io.Closeable[] r6 = new java.io.Closeable[r9]
            r6[r8] = r3
            net.oschina.common.utils.StreamUtil.close(r6)
            goto L_0x0009
        L_0x006b:
            r6 = move-exception
        L_0x006c:
            if (r0 == 0) goto L_0x0071
            r0.recycle()
        L_0x0071:
            java.io.Closeable[] r7 = new java.io.Closeable[r9]
            r7[r8] = r3
            net.oschina.common.utils.StreamUtil.close(r7)
            throw r6
        L_0x0079:
            r10.finish()
            goto L_0x0009
        L_0x007d:
            r6 = move-exception
            r3 = r4
            goto L_0x006c
        L_0x0080:
            r1 = move-exception
            r3 = r4
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.CropActivity.onClick(android.view.View):void");
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_crop;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        setTitle("");
        getWindow().setLayout(-1, -1);
        this.mCropLayout = (CropLayout) findViewById(R.id.cropLayout);
        getImageLoader().load(mOption.getSelectedImages().get(0)).into(this.mCropLayout.getImageView());
        this.mCropLayout.setCropWidth(mOption.getCropWidth());
        this.mCropLayout.setCropHeight(mOption.getCropHeight());
        this.mCropLayout.start();
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}

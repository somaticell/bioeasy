package cn.com.bioeasy.healty.app.healthapp.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.file.FileUtil;
import cn.com.bioeasy.app.net.HttpHelper;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.MainActivity;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheKeys;
import cn.com.bioeasy.healty.app.healthapp.data.VersionInfo;
import cn.com.bioeasy.healty.app.healthapp.injection.module.NetworkModule;
import cn.com.bioeasy.healty.app.healthapp.splash.SplashLoadDataTask;
import java.io.File;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends Activity implements SplashLoadDataTask.LoadDataCallback {
    /* access modifiers changed from: private */
    public boolean isLoadingData = false;
    /* access modifiers changed from: private */
    public boolean isTimeOut = false;
    private PowerManager.WakeLock mWakeLock = null;
    /* access modifiers changed from: private */
    public VersionInfo oldVersionInfo;
    /* access modifiers changed from: private */
    public ProgressDialog progressDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (SplashActivity.this.isLoadingData) {
                    SplashActivity.this.switchToMainActivity();
                }
                boolean unused = SplashActivity.this.isTimeOut = true;
            }
        }, 2000);
        loadVersionInfo();
        PowerManager powerManager = (PowerManager) getSystemService("power");
        if (powerManager != null) {
            this.mWakeLock = powerManager.newWakeLock(26, "SplashActivity:WakeLock");
        }
    }

    private void loadVersionInfo() {
        ((AppVersionApi) new HttpHelper(getApplicationContext()).getService(AppVersionApi.class)).loadVersionInfo("http://www.bioeasy.com/dist/te_health_en/version.json?r=" + Math.random()).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<VersionInfo>() {
            public void onNext(VersionInfo versionInfo) {
                SplashActivity.this.processVersionInfo(versionInfo);
            }

            public void onError(Throwable e) {
                boolean unused = SplashActivity.this.isLoadingData = true;
                if (SplashActivity.this.isTimeOut) {
                    SplashActivity.this.switchToMainActivity();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void processVersionInfo(VersionInfo newVersionInfo) {
        int oldVersionCode;
        int oldApkVersion;
        if (newVersionInfo == null) {
            this.isLoadingData = true;
            if (this.isTimeOut) {
                switchToMainActivity();
                return;
            }
            return;
        }
        HealthApp healthApp = HealthApp.x;
        int newAppVersion = HealthApp.getVersionCode(this);
        this.oldVersionInfo = HealthApp.x.getDataManager().getVersionInfo();
        if (this.oldVersionInfo != null) {
            oldVersionCode = this.oldVersionInfo.getVersionCode();
        } else {
            oldVersionCode = 0;
        }
        if (this.oldVersionInfo != null) {
            oldApkVersion = this.oldVersionInfo.getApkVersion();
        } else {
            oldApkVersion = 0;
        }
        int newVersionCode = newVersionInfo.getVersionCode();
        int newApkVersion = newVersionInfo.getApkVersion();
        Log.e("=======", "=======appVersion:" + newAppVersion + ",versionCode: " + oldVersionCode + ",newVersionCode:" + newVersionCode);
        if (oldVersionCode == 0) {
            if (newAppVersion < newVersionCode) {
                showUpdateDialog(newVersionInfo);
            } else {
                switchToLoginPage(newVersionInfo);
            }
        } else if (oldVersionCode < newVersionCode) {
            Log.e("=======", "show update dialog");
            if (newApkVersion > oldApkVersion) {
                showUpdateDialog(newVersionInfo);
                return;
            }
            if (newVersionInfo.isRefresh()) {
                clearCache();
            }
            switchToLoginPage(newVersionInfo);
        } else {
            switchToLoginPage(newVersionInfo);
        }
    }

    private void switchToLoginPage(VersionInfo newVersionInfo) {
        HealthApp.x.getDataManager().setVersionInfo(newVersionInfo);
        NetworkModule.TEST_HTTP_URL = newVersionInfo.getTestDataServer();
        NetworkModule.APP_SERVER_URL = newVersionInfo.getAppServer();
        this.isLoadingData = true;
        if (this.isTimeOut) {
            switchToMainActivity();
        }
    }

    private void clearCache() {
        HealthApp.x.getDataManager().clear(new String[]{CacheKeys.TEST_STRIP_CATEGORY_LIST, CacheKeys.TEST_STRIP_CATEGORY_MAP, CacheKeys.TEST_STRIP_MAP, CacheKeys.GOODS_CATEGORY_LIST});
    }

    /* access modifiers changed from: private */
    public void switchToMainActivity() {
        NetworkModule.initialize(getApplicationContext());
        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }

    private void showUpdateDialog(final VersionInfo newVersion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.latest_version) + newVersion.getVersionName());
        builder.setIcon(R.drawable.bioeasy_logo);
        builder.setMessage(newVersion.getDescription());
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (SplashActivity.this.oldVersionInfo == null) {
                    VersionInfo unused = SplashActivity.this.oldVersionInfo = new VersionInfo();
                    SplashActivity.this.oldVersionInfo.setVersionCode(0);
                    VersionInfo access$400 = SplashActivity.this.oldVersionInfo;
                    HealthApp healthApp = HealthApp.x;
                    access$400.setVersionName(HealthApp.getVersionName(SplashActivity.this));
                    NetworkModule.TEST_HTTP_URL = newVersion.getTestDataServer();
                    NetworkModule.APP_SERVER_URL = newVersion.getAppServer();
                    HealthApp.x.getDataManager().setVersionInfo(SplashActivity.this.oldVersionInfo);
                }
                dialog.dismiss();
                SplashActivity.this.switchToMainActivity();
            }
        });
        builder.setPositiveButton(getString(R.string.upgrade), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SplashActivity.this.downloadAPK(newVersion);
            }
        });
        builder.setNegativeButton(getString(R.string.talk_later), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                boolean unused = SplashActivity.this.isLoadingData = true;
            }
        });
        builder.show();
    }

    /* access modifiers changed from: protected */
    public void downloadAPK(VersionInfo versionInfo) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            showProgressDialog();
            final String storePath = FileUtil.getDiskCacheDir(getApplicationContext()).getPath() + "/";
            final String packageName = AppUtils.getUrlFileName(versionInfo.getAppPackage());
            ((AppVersionApi) new HttpHelper(getApplicationContext()).getService(AppVersionApi.class)).downloadFile(versionInfo.getAppPackage() + "?r=" + Math.random()).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Action1<ResponseBody>() {
                public void call(ResponseBody responseBody) {
                    if (SplashActivity.this.writeResponseBodyToDisk(responseBody, storePath, packageName)) {
                        SplashActivity.this.progressDialog.dismiss();
                        SplashActivity.this.installAPK(storePath + packageName);
                        return;
                    }
                    SplashActivity.this.progressDialog.dismiss();
                    SplashActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SplashActivity.this.getApplicationContext(), SplashActivity.this.getString(R.string.update_package_download_failure), 0).show();
                        }
                    });
                }
            }, (Action1<Throwable>) new Action1<Throwable>() {
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                    SplashActivity.this.switchToMainActivity();
                    SplashActivity.this.progressDialog.dismiss();
                }
            });
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.available_SD_cards), 0).show();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0076 A[SYNTHETIC, Splitter:B:32:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007b A[Catch:{ IOException -> 0x006d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeResponseBodyToDisk(okhttp3.ResponseBody r17, java.lang.String r18, java.lang.String r19) {
        /*
            r16 = this;
            java.io.File r8 = new java.io.File     // Catch:{ IOException -> 0x006d }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x006d }
            r13.<init>()     // Catch:{ IOException -> 0x006d }
            r0 = r18
            java.lang.StringBuilder r13 = r13.append(r0)     // Catch:{ IOException -> 0x006d }
            r0 = r19
            java.lang.StringBuilder r13 = r13.append(r0)     // Catch:{ IOException -> 0x006d }
            java.lang.String r13 = r13.toString()     // Catch:{ IOException -> 0x006d }
            r8.<init>(r13)     // Catch:{ IOException -> 0x006d }
            r9 = 0
            r10 = 0
            r13 = 4096(0x1000, float:5.74E-42)
            byte[] r3 = new byte[r13]     // Catch:{ IOException -> 0x0082 }
            long r4 = r17.contentLength()     // Catch:{ IOException -> 0x0082 }
            r6 = 0
            java.io.InputStream r9 = r17.byteStream()     // Catch:{ IOException -> 0x0082 }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0082 }
            r11.<init>(r8)     // Catch:{ IOException -> 0x0082 }
        L_0x002f:
            int r12 = r9.read(r3)     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            r13 = -1
            if (r12 != r13) goto L_0x0045
            r11.flush()     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            r13 = 1
            if (r9 == 0) goto L_0x003f
            r9.close()     // Catch:{ IOException -> 0x006d }
        L_0x003f:
            if (r11 == 0) goto L_0x0044
            r11.close()     // Catch:{ IOException -> 0x006d }
        L_0x0044:
            return r13
        L_0x0045:
            r13 = 0
            r11.write(r3, r13, r12)     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            long r14 = (long) r12     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            long r6 = r6 + r14
            r0 = r16
            android.app.ProgressDialog r13 = r0.progressDialog     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            int r14 = (int) r4     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            r13.setMax(r14)     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            r0 = r16
            android.app.ProgressDialog r13 = r0.progressDialog     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            int r14 = (int) r6     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            r13.setProgress(r14)     // Catch:{ IOException -> 0x005c, all -> 0x007f }
            goto L_0x002f
        L_0x005c:
            r2 = move-exception
            r10 = r11
        L_0x005e:
            r2.printStackTrace()     // Catch:{ all -> 0x0073 }
            r13 = 0
            if (r9 == 0) goto L_0x0067
            r9.close()     // Catch:{ IOException -> 0x006d }
        L_0x0067:
            if (r10 == 0) goto L_0x0044
            r10.close()     // Catch:{ IOException -> 0x006d }
            goto L_0x0044
        L_0x006d:
            r2 = move-exception
            r2.printStackTrace()
            r13 = 0
            goto L_0x0044
        L_0x0073:
            r13 = move-exception
        L_0x0074:
            if (r9 == 0) goto L_0x0079
            r9.close()     // Catch:{ IOException -> 0x006d }
        L_0x0079:
            if (r10 == 0) goto L_0x007e
            r10.close()     // Catch:{ IOException -> 0x006d }
        L_0x007e:
            throw r13     // Catch:{ IOException -> 0x006d }
        L_0x007f:
            r13 = move-exception
            r10 = r11
            goto L_0x0074
        L_0x0082:
            r2 = move-exception
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.healty.app.healthapp.splash.SplashActivity.writeResponseBodyToDisk(okhttp3.ResponseBody, java.lang.String, java.lang.String):boolean");
    }

    private void showProgressDialog() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.show();
    }

    /* access modifiers changed from: protected */
    public void installAPK(String saveFile) {
        HealthApp.x.getDataManager().setVersionInfo((VersionInfo) null);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(saveFile)), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switchToMainActivity();
    }

    public void loaded() {
    }

    public void loadError() {
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mWakeLock != null) {
            this.mWakeLock.acquire();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
        }
    }
}

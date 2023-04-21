package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.common.AppUtils;
import cn.com.bioeasy.app.file.FileUtil;
import cn.com.bioeasy.app.net.HttpHelper;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.base.BatteryState;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import cn.com.bioeasy.healty.app.healthapp.ble.IBLEResponse;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheKeys;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import cn.com.bioeasy.healty.app.healthapp.data.VersionInfo;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.DeviceSettingPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.ListChoseDialog;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.ProgressChoseDialog;
import cn.com.bioeasy.healty.app.healthapp.splash.AppVersionApi;
import com.alipay.sdk.cons.a;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DeviceSettingActivity extends BIBaseActivity implements IBLEResponse {
    private static final String[] POWER_VALUES = {a.e, "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] TEST_COUNT = {a.e, "2", "3", "4"};
    /* access modifiers changed from: private */
    public String FIRMWARE_NAME = null;
    @Inject
    BLERepository bleRepository;
    @Inject
    CacheManager cacheManager;
    @Bind({2131231042})
    RelativeLayout checkNum;
    private int clickCount = 0;
    @Inject
    DeviceSettingPresenter deviceSettingPresenter;
    @Bind({2131231176})
    BatteryState powerStatus;
    /* access modifiers changed from: private */
    public ProgressDialog progressDialog;
    @Bind({2131231046})
    RelativeLayout rlLoadFile;
    @Bind({2131231047})
    Button sendBtn;
    @Bind({2131231038})
    TextView tvBlueName;
    @Bind({2131231048})
    TextView tvPmFile;
    @Bind({2131231008})
    TextView tvTitle;
    private PowerManager.WakeLock wakeLock;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.deviceSettingPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_parameter_setting;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(R.string.device_setting_title);
        this.tvBlueName.setText(String.format(getString(R.string.ble_connected), new Object[]{this.bleRepository.getDeviceName()}));
        this.checkNum.setVisibility(8);
        this.rlLoadFile.setVisibility(8);
        this.powerStatus.setVisibility(0);
        this.bleRepository.addBLEResponse(Byte.valueOf(BLEServiceApi.POWER_STATUS_CMD), this);
        this.bleRepository.sendPowerStatusCmd();
    }

    @OnClick({2131231014, 2131231039, 2131231017, 2131231042, 2131231050, 2131231045, 2131231038, 2131231047})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_led_change:
                ProgressChoseDialog ledDialog = new ProgressChoseDialog(this);
                ledDialog.setProgress(this.cacheManager.getInt(CacheKeys.LED_PERCENT));
                ProgressChoseDialog.setData(new ProgressChoseDialog.SendValue() {
                    public void SendData(int value) {
                        DeviceSettingActivity.this.cacheManager.saveInt(CacheKeys.LED_PERCENT, value);
                        DeviceSettingActivity.this.deviceSettingPresenter.setLEDBrightless(value);
                    }
                });
                ledDialog.setTitles(R.string.tv_mine_screen_change);
                ledDialog.ShowDialog();
                return;
            case R.id.rl_auto_power_failure:
                ListChoseDialog powerDialog = new ListChoseDialog(POWER_VALUES, this);
                powerDialog.setTitles(R.string.tv_mine_auto_power);
                powerDialog.setListValue(this.cacheManager.getInt(CacheKeys.POWER_TIME));
                ListChoseDialog.setData(new ListChoseDialog.SendValue() {
                    public void SendData(int value) {
                        DeviceSettingActivity.this.cacheManager.saveInt(CacheKeys.POWER_TIME, value);
                        DeviceSettingActivity.this.deviceSettingPresenter.setPowerAutoOff(value);
                    }
                });
                powerDialog.ShowDialog();
                return;
            case R.id.tv_current_bluename:
                this.clickCount++;
                if (this.clickCount >= 7) {
                    showMessage((int) R.string.debug_mode);
                    HealthApp.x.setDebug(true);
                } else if (this.clickCount < 7) {
                    showMessage(getString(R.string.click_times) + this.clickCount);
                }
                if (this.clickCount >= 14) {
                    showMessage((int) R.string.free_debug_mode);
                    HealthApp.x.setFreeDebug(true);
                    return;
                }
                return;
            case R.id.bt_change:
            case R.id.iv_back:
                finish();
                return;
            case R.id.rl_check_num:
                ListChoseDialog dialog = new ListChoseDialog(TEST_COUNT, this);
                dialog.setTitles(R.string.tv_mine_check_count);
                ListChoseDialog.setData(new ListChoseDialog.SendValue() {
                    public void SendData(int value) {
                    }
                });
                dialog.ShowDialog();
                return;
            case R.id.rl_check_pwm:
                VersionInfo versionInfo = HealthApp.x.getDataManager().getVersionInfo();
                if (versionInfo == null || StringUtil.isNullOrEmpty(versionInfo.getFirmware())) {
                    showMessage((int) R.string.now_latest_version);
                    return;
                } else {
                    downloadFirmware(versionInfo);
                    return;
                }
            case R.id.btn_send_pwm:
                uploadFirmwareData();
                return;
            default:
                return;
        }
    }

    public void onResponse(Byte cmd, final String[] hexData) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (BLEServiceApi.RESULT_OK.equals(hexData[0])) {
                    int powerValue = Integer.parseInt(hexData[1] + hexData[2], 16);
                    DeviceSettingActivity.this.powerStatus.refreshPower(powerValue <= 1800 ? 0.0f : ((float) (((double) (powerValue - 1800)) * 1.0d)) / 1800.0f);
                }
            }
        });
    }

    private void uploadFirmwareData() {
        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input device upgrade password").setIcon(R.drawable.bioeasy_logo).setView(inputServer).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (HealthApp.x.getDataManager().getVersionInfo().getFirmwarePwd().equals(inputServer.getText().toString())) {
                    new Thread(new Runnable() {
                        public void run() {
                            DeviceSettingActivity.this.deviceSettingPresenter.uploadFirmwareData(DeviceSettingActivity.this.FIRMWARE_NAME);
                        }
                    }).start();
                } else {
                    DeviceSettingActivity.this.showMessage("incorrect password!");
                }
            }
        });
        builder.show();
    }

    public void checkPwmFile() {
        runOnUiThread(new Runnable() {
            public void run() {
                DeviceSettingActivity.this.showProgress(DeviceSettingActivity.this.getString(R.string.firmware_update), DeviceSettingActivity.this.getString(R.string.firmware_update_tip));
            }
        });
        new Thread(new Runnable() {
            public void run() {
                DeviceSettingActivity.this.deviceSettingPresenter.updateFirmWare(DeviceSettingActivity.this.FIRMWARE_NAME);
            }
        }).start();
    }

    public void updateSendProgress(String progress) {
        double val = Double.parseDouble(progress) * 100.0d;
        if (val >= 99.0d) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    DeviceSettingActivity.this.hideProgress();
                    DeviceSettingActivity.this.showMessage((int) R.string.firmware_upgrade);
                }
            }, 20000);
            this.sendBtn.setVisibility(8);
        }
        final String progressStr = new DecimalFormat("##0.00").format(val);
        runOnUiThread(new Runnable() {
            public void run() {
                DeviceSettingActivity.this.tvPmFile.setText(DeviceSettingActivity.this.getString(R.string.firmware_update_one) + "(" + progressStr + "%)");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void downloadFirmware(VersionInfo versionInfo) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            AppVersionApi service = (AppVersionApi) new HttpHelper(getApplicationContext()).getService(AppVersionApi.class);
            final String storePath = FileUtil.getDiskCacheDir(getApplicationContext()).getPath();
            final String firmwareName = AppUtils.getUrlFileName(versionInfo.getFirmware());
            final String filePath = storePath + "/" + firmwareName;
            if (new File(filePath).exists()) {
                showFileAndSend(filePath);
                return;
            }
            showProgressDialog();
            service.downloadFile(versionInfo.getFirmware()).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Action1<ResponseBody>() {
                public void call(ResponseBody responseBody) {
                    if (DeviceSettingActivity.this.writeResponseBodyToDisk(responseBody, storePath, firmwareName)) {
                        DeviceSettingActivity.this.progressDialog.dismiss();
                        DeviceSettingActivity.this.showFileAndSend(filePath);
                        return;
                    }
                    DeviceSettingActivity.this.progressDialog.dismiss();
                    DeviceSettingActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            DeviceSettingActivity.this.showMessage((int) R.string.update_package_download_failure);
                        }
                    });
                }
            }, (Action1<Throwable>) new Action1<Throwable>() {
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                    DeviceSettingActivity.this.progressDialog.dismiss();
                }
            });
            return;
        }
        showMessage((int) R.string.available_SD_cards);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007c A[SYNTHETIC, Splitter:B:32:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0081 A[Catch:{ IOException -> 0x0073 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeResponseBodyToDisk(okhttp3.ResponseBody r17, java.lang.String r18, java.lang.String r19) {
        /*
            r16 = this;
            java.io.File r8 = new java.io.File     // Catch:{ IOException -> 0x0073 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0073 }
            r13.<init>()     // Catch:{ IOException -> 0x0073 }
            r0 = r18
            java.lang.StringBuilder r13 = r13.append(r0)     // Catch:{ IOException -> 0x0073 }
            java.lang.String r14 = "/"
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ IOException -> 0x0073 }
            r0 = r19
            java.lang.StringBuilder r13 = r13.append(r0)     // Catch:{ IOException -> 0x0073 }
            java.lang.String r13 = r13.toString()     // Catch:{ IOException -> 0x0073 }
            r8.<init>(r13)     // Catch:{ IOException -> 0x0073 }
            r9 = 0
            r10 = 0
            r13 = 4096(0x1000, float:5.74E-42)
            byte[] r3 = new byte[r13]     // Catch:{ IOException -> 0x0088 }
            long r4 = r17.contentLength()     // Catch:{ IOException -> 0x0088 }
            r6 = 0
            java.io.InputStream r9 = r17.byteStream()     // Catch:{ IOException -> 0x0088 }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0088 }
            r11.<init>(r8)     // Catch:{ IOException -> 0x0088 }
        L_0x0035:
            int r12 = r9.read(r3)     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            r13 = -1
            if (r12 != r13) goto L_0x004b
            r11.flush()     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            r13 = 1
            if (r9 == 0) goto L_0x0045
            r9.close()     // Catch:{ IOException -> 0x0073 }
        L_0x0045:
            if (r11 == 0) goto L_0x004a
            r11.close()     // Catch:{ IOException -> 0x0073 }
        L_0x004a:
            return r13
        L_0x004b:
            r13 = 0
            r11.write(r3, r13, r12)     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            long r14 = (long) r12     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            long r6 = r6 + r14
            r0 = r16
            android.app.ProgressDialog r13 = r0.progressDialog     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            int r14 = (int) r4     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            r13.setMax(r14)     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            r0 = r16
            android.app.ProgressDialog r13 = r0.progressDialog     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            int r14 = (int) r6     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            r13.setProgress(r14)     // Catch:{ IOException -> 0x0062, all -> 0x0085 }
            goto L_0x0035
        L_0x0062:
            r2 = move-exception
            r10 = r11
        L_0x0064:
            r2.printStackTrace()     // Catch:{ all -> 0x0079 }
            r13 = 0
            if (r9 == 0) goto L_0x006d
            r9.close()     // Catch:{ IOException -> 0x0073 }
        L_0x006d:
            if (r10 == 0) goto L_0x004a
            r10.close()     // Catch:{ IOException -> 0x0073 }
            goto L_0x004a
        L_0x0073:
            r2 = move-exception
            r2.printStackTrace()
            r13 = 0
            goto L_0x004a
        L_0x0079:
            r13 = move-exception
        L_0x007a:
            if (r9 == 0) goto L_0x007f
            r9.close()     // Catch:{ IOException -> 0x0073 }
        L_0x007f:
            if (r10 == 0) goto L_0x0084
            r10.close()     // Catch:{ IOException -> 0x0073 }
        L_0x0084:
            throw r13     // Catch:{ IOException -> 0x0073 }
        L_0x0085:
            r13 = move-exception
            r10 = r11
            goto L_0x007a
        L_0x0088:
            r2 = move-exception
            goto L_0x0064
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity.writeResponseBodyToDisk(okhttp3.ResponseBody, java.lang.String, java.lang.String):boolean");
    }

    private void showProgressDialog() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.show();
    }

    /* access modifiers changed from: protected */
    public void showFileAndSend(String saveFile) {
        this.FIRMWARE_NAME = saveFile;
        runOnUiThread(new Runnable() {
            public void run() {
                DeviceSettingActivity.this.rlLoadFile.setVisibility(0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.wakeLock = ((PowerManager) getSystemService("power")).newWakeLock(536870922, DeviceSettingActivity.class.getName());
        this.wakeLock.acquire();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.wakeLock != null) {
            this.wakeLock.release();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.bleRepository.removeBLEResponse(Byte.valueOf(BLEServiceApi.POWER_STATUS_CMD), this);
    }
}

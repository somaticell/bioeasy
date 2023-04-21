package cn.com.bioeasy.healty.app.healthapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.Bind;
import cn.com.bioeasy.app.base.AppManager;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.base.TabsFragment;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ble.ble.BleService;
import javax.inject.Inject;

public class MainActivity extends BIBaseActivity implements BaseView {
    private static final int EXIT_PRESS_TIME_INTERVAL = 2000;
    @Inject
    BLERepository bleRepository;
    private long lastExistAppPressTime;
    private BluetoothAdapter mBluetoothAdapter;
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
            MainActivity.this.bleRepository.stop();
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            MainActivity.this.bleRepository.start(service);
        }
    };
    @Bind({2131231050})
    ImageView mIvBack;
    private LocationClient mLocClient;
    @Bind({2131231173})
    Toolbar mToolbar;
    @Inject
    MainPresenter mainPresenter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (ActionConstant.ACTION_SWITCH_BOTTOM_MENU.equals(intent.getAction())) {
                int index = intent.getIntExtra("index", -1);
                if (index > 0) {
                    int unused = MainActivity.this.tabIndex = index;
                }
            } else if (intent.getAction().equals(IntentActions.USER_SIGN)) {
                MainActivity.this.mainPresenter.dailySign();
            }
        }
    };
    /* access modifiers changed from: private */
    public int tabIndex = -1;
    TabsFragment tabsFragment;

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_health_main;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mIvBack.setVisibility(8);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConstant.ACTION_SWITCH_BOTTOM_MENU);
        intentFilter.addAction(IntentActions.USER_SIGN);
        registerReceiver(this.receiver, intentFilter);
        this.tabsFragment = (TabsFragment) getSupportFragmentManager().findFragmentById(R.id.main_bottom);
        checkBLEFeature();
        bindService(new Intent(this, BleService.class), this.mConnection, 1);
        initBaiduLocation();
    }

    public void checkGPSSetting() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE"}, 99);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 99:
                if (grantResults[0] != 0) {
                    showMessage((int) R.string.open_manually);
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.mainPresenter;
    }

    public void onBackPressed() {
        if (this.lastExistAppPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            AppManager.exitApplication();
            return;
        }
        showMessage(2000, getString(R.string.exit_program), 2);
        this.lastExistAppPressTime = System.currentTimeMillis();
    }

    private void checkBLEFeature() {
        HealthApp healthApp = HealthApp.x;
        if (HealthApp.hasSystemFeature(this, "android.hardware.bluetooth_le")) {
            this.mBluetoothAdapter = ((BluetoothManager) getApplication().getSystemService("bluetooth")).getAdapter();
            if (this.mBluetoothAdapter == null) {
            }
        }
    }

    private void initBaiduLocation() {
        checkGPSSetting();
        this.mLocClient = new LocationClient(this);
        this.mLocClient.registerLocationListener((BDLocationListener) new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(10000);
        option.setIsNeedAddress(true);
        this.mLocClient.setLocOption(option);
        this.mLocClient.start();
    }

    public class MyLocationListener implements BDLocationListener {
        public MyLocationListener() {
        }

        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                HealthApp.x.dataManager.setAddress(location.getAddress());
                HealthApp.x.dataManager.setLongitude(location.getLongitude());
                HealthApp.x.dataManager.setLatitude(location.getLatitude());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.tabIndex >= 0) {
            this.tabsFragment.onTabSelect(this.tabIndex);
            this.tabIndex = -1;
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.mLocClient != null) {
            this.mLocClient.stop();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        if (this.mLocClient != null) {
            this.mLocClient.start();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.receiver);
        this.bleRepository.stop();
        unbindService(this.mConnection);
        if (this.mLocClient != null) {
            this.mLocClient.stop();
        }
    }
}

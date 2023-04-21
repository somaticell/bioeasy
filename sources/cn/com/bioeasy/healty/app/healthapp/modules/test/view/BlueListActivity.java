package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.abslistview.CommonAdapter;
import cn.com.bioeasy.app.widgets.abslistview.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import cn.com.bioeasy.healty.app.healthapp.ble.IBLEResponse;
import cn.com.bioeasy.healty.app.healthapp.ble.LeDevice;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity;
import com.kyleduo.switchbutton.SwitchButton;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class BlueListActivity extends BIBaseActivity implements AdapterView.OnItemClickListener, IBLEResponse {
    private static final int REQUEST_ENABLE_BT = 2;
    @Inject
    BLERepository bleRepository;
    @Bind({2131231177})
    Button btnSetting;
    /* access modifiers changed from: private */
    public boolean isConnecting = false;
    @Bind({2131231085})
    ImageView ivChecked;
    private BluetoothAdapter mBluetoothAdapter;
    @Bind({2131231088})
    Button mBtNext;
    /* access modifiers changed from: private */
    public CommonAdapter mLeDeviceListAdapter;
    BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            BlueListActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (BlueListActivity.this.isTargetDevice(device.getName())) {
                        BlueListActivity.this.mLeDeviceListAdapter.addItem(new LeDevice(device.getName(), device.getAddress(), rssi, scanRecord));
                    }
                }
            });
        }
    };
    @Bind({2131231087})
    ListView mLv;
    private boolean mScanning;
    @Bind({2131231084})
    SwitchButton mSwitchButton;
    @Bind({2131231086})
    TextView tvBlueName;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private void addBackHome() {
        finish();
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_search_list;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        boolean z = true;
        this.tvTitle.setText(R.string.blue_list);
        this.mLeDeviceListAdapter = new CommonAdapter<LeDevice>(this.context, R.layout.listitem_device, new ArrayList()) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder viewHolder, LeDevice item, int position) {
                viewHolder.setText(R.id.txt_blue_name, item.getName());
            }
        };
        this.mLv.setAdapter(this.mLeDeviceListAdapter);
        this.mLv.setOnItemClickListener(this);
        this.mBluetoothAdapter = ((BluetoothManager) getSystemService("bluetooth")).getAdapter();
        scanLeDevice(true);
        Button button = this.mBtNext;
        if (this.mLeDeviceListAdapter.getCount() != 0) {
            z = false;
        }
        button.setClickable(z);
        this.bleRepository.addBLEResponse((byte) -1, this);
    }

    @OnClick({2131231050, 2131231084, 2131231088, 2131231089, 2131231177})
    public void onClicks(View v) {
        if (v.getId() == R.id.bt_next) {
            if (this.bleRepository.isConnected()) {
                switchPage(TestItemCategoryActivity.class);
                return;
            }
            switchPage(TestItemCategoryActivity.class);
            showMessage((int) R.string.bluetooth_unconnected);
        } else if (v.getId() == R.id.btn_partanter_setting) {
            if (!this.bleRepository.isConnected()) {
                showMessage((int) R.string.no_connection_bluetooth);
            } else {
                switchPage(DeviceSettingActivity.class);
            }
        } else if (v.getId() == R.id.iv_back) {
            addBackHome();
        } else if (v.getId() == R.id.sbt_open_blue) {
            if (isBluetoothEnabled()) {
                if (turnOffBluetooth()) {
                    this.ivChecked.setVisibility(4);
                    this.tvBlueName.setText(R.string.tv_blue_no_device_connect);
                    this.mBtNext.setClickable(false);
                    this.mBtNext.setEnabled(false);
                    showMessage((int) R.string.ble_close);
                }
            } else if (turnOnBluetooth() && this.ivChecked.getVisibility() == 0) {
                showMessage((int) R.string.ble_open);
                this.mBtNext.setClickable(true);
                this.mBtNext.setEnabled(true);
            }
        } else if (v.getId() == R.id.buy_device_tip) {
            Intent intent = new Intent();
            intent.putExtra("index", 2);
            intent.setAction(ActionConstant.ACTION_SWITCH_BOTTOM_MENU);
            sendBroadcast(intent);
            finish();
        }
    }

    /* access modifiers changed from: private */
    public boolean isTargetDevice(String deviceName) {
        if (StringUtil.isNullOrEmpty(deviceName) || deviceName.indexOf("YR") <= -1) {
            return false;
        }
        return true;
    }

    private void scanLeDevice(boolean scan) {
        if (!scan) {
            if (this.mBluetoothAdapter != null) {
                this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
            }
            this.mScanning = false;
        } else if (!isBluetoothEnabled()) {
            showMessage((int) R.string.scan_bt_disabled);
        } else if (!this.mScanning) {
            this.mScanning = true;
            this.mSwitchButton.setChecked(true);
            this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
        }
    }

    private boolean isBluetoothEnabled() {
        return this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled();
    }

    private boolean turnOffBluetooth() {
        if (this.mBluetoothAdapter != null) {
            return this.mBluetoothAdapter.disable();
        }
        return false;
    }

    private boolean turnOnBluetooth() {
        if (this.mBluetoothAdapter != null) {
            return this.mBluetoothAdapter.enable();
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        addBackHome();
        return true;
    }

    public void onResume() {
        super.onResume();
        refreshDeviceList();
        scanLeDevice(true);
    }

    /* access modifiers changed from: private */
    public void refreshDeviceList() {
        if (this.mBluetoothAdapter != null && !this.mBluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
        }
        this.mLeDeviceListAdapter.clearData();
        List<LeDevice> deviceList = this.bleRepository.getConnectedDevices();
        if (deviceList.size() > 0) {
            onCheckedBlueDevice(deviceList.get(0).getName());
        } else {
            onCheckedBlueDevice((String) null);
        }
        this.mLeDeviceListAdapter.addItemList(deviceList);
    }

    public void onPause() {
        super.onPause();
        scanLeDevice(false);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == 0) {
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onDestroy() {
        this.bleRepository.removeBLEResponse((byte) -1, this);
        super.onDestroy();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        LeDevice device = (LeDevice) this.mLeDeviceListAdapter.getItem(position);
        if (this.bleRepository.canConnected(device)) {
            this.isConnecting = true;
            showProgress("");
            this.bleRepository.connect(device);
            onCheckedBlueDevice(device.getName());
        }
    }

    private void onCheckedBlueDevice(String blueName) {
        if (blueName == null || !this.mSwitchButton.isChecked()) {
            this.ivChecked.setVisibility(4);
            this.tvBlueName.setText(R.string.tv_blue_no_device_connect);
            this.mBtNext.setClickable(false);
            this.mBtNext.setEnabled(false);
            this.btnSetting.setVisibility(8);
            this.btnSetting.setClickable(false);
            this.btnSetting.setTextColor(getResources().getColor(R.color.alpha_gray));
            return;
        }
        this.ivChecked.setVisibility(0);
        this.tvBlueName.setText(blueName);
        this.mBtNext.setClickable(true);
        this.mBtNext.setEnabled(true);
        this.btnSetting.setVisibility(0);
        this.btnSetting.setClickable(true);
        this.btnSetting.setTextColor(-16777216);
    }

    public void onResponse(Byte cmd, final String[] hexDatas) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (BlueListActivity.this.isConnecting) {
                    BlueListActivity.this.hideProgress();
                    boolean unused = BlueListActivity.this.isConnecting = false;
                }
                if (hexDatas[0].equals(BLEServiceApi.RESULT_NG)) {
                    BlueListActivity.this.showMessage((int) R.string.bluetooth_connection_disconnection);
                    BlueListActivity.this.refreshDeviceList();
                }
            }
        });
    }
}

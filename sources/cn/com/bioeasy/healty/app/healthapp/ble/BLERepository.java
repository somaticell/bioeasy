package cn.com.bioeasy.healty.app.healthapp.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.IBinder;
import android.util.Log;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.util.ToastUtils;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.ble.socket.PackageWriter;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheKeys;
import cn.com.bioeasy.healty.app.healthapp.cache.CacheManager;
import com.ble.api.DataUtil;
import com.ble.ble.BleCallBack;
import com.ble.ble.BleService;
import com.ble.ble.constants.BleUUIDS;
import com.ble.ble.util.GattUtil;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BLERepository implements BLEServiceApi {
    /* access modifiers changed from: private */
    public static final String TAG = BLERepository.class.getSimpleName();
    private String bufferPackage = "";
    @Inject
    CacheManager cacheManager;
    /* access modifiers changed from: private */
    public String cancelDeviceMac;
    /* access modifiers changed from: private */
    public String deviceMac;
    /* access modifiers changed from: private */
    public boolean isConnected;
    private final BleCallBack mBleCallBack = new BleCallBack() {
        public void onConnected(String mac) {
            Log.w(BLERepository.TAG, "onConnected() -=========== " + mac);
        }

        public void onConnectTimeout(String mac) {
            boolean unused = BLERepository.this.isConnected = false;
            HealthApp.x.setConnected(BLERepository.this.isConnected);
            Log.w(BLERepository.TAG, "onConnectTimeout() -=========== " + mac);
            if (!mac.equals(BLERepository.this.cancelDeviceMac)) {
                BLERepository.this.sendResponse((byte) -1, BLEServiceApi.RESULT_NG);
                BLERepository.this.showMessage(BLERepository.this.getString(R.string.scan_connect_timeout) + ":" + mac + " ");
            }
        }

        public void onConnectionError(String mac, int status, int newState) {
            boolean unused = BLERepository.this.isConnected = false;
            HealthApp.x.setConnected(BLERepository.this.isConnected);
            Log.w(BLERepository.TAG, "onConnectionError() -=========== " + mac + ", status = " + status + ", newState = " + newState);
            BLERepository.this.sendResponse((byte) -1, BLEServiceApi.RESULT_NG);
            BLERepository.this.showMessage(BLERepository.this.getString(R.string.scan_connection_error) + ":" + mac + "\nstatus:" + status + "\nnew state:" + newState + "\n");
        }

        public void onDisconnected(String mac) {
            boolean unused = BLERepository.this.isConnected = false;
            HealthApp.x.setConnected(BLERepository.this.isConnected);
            if (mac.equals(BLERepository.this.deviceMac)) {
                BLERepository.this.sendResponse((byte) -1, BLEServiceApi.RESULT_NG);
            }
            Log.w(BLERepository.TAG, "onDisconnected() -=========== " + mac);
        }

        public void onServicesUndiscovered(String mac, int status) {
            boolean unused = BLERepository.this.isConnected = false;
            HealthApp.x.setConnected(BLERepository.this.isConnected);
            BLERepository.this.sendResponse((byte) -1, BLEServiceApi.RESULT_NG);
            Log.e(BLERepository.TAG, "onServicesUndiscovered() -=========== " + mac + ", status = " + status);
        }

        public void onServicesDiscovered(String mac) {
            boolean unused = BLERepository.this.isConnected = true;
            HealthApp.x.setConnected(BLERepository.this.isConnected);
            Log.e(BLERepository.TAG, "onServicesDiscovered() -=========== " + mac);
            BLERepository.this.sendResponse((byte) -1, BLEServiceApi.RESULT_OK);
            new Timer().schedule(new ServicesDiscoveredTask(mac), 300);
        }

        public void onReadRemoteRssi(String s, int i, int i1) {
        }

        public void onCharacteristicWrite(String s, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            Logger.w("onCharacteristicWrite===========" + i, new Object[0]);
        }

        public void onCharacteristicChanged(String mac, BluetoothGattCharacteristic c) {
            BLERepository.this.processInputData(c.getValue());
        }
    };
    /* access modifiers changed from: private */
    public BleService mLeService;
    private int receiveDataCount = 0;
    private String receiveDataTotal = "";
    private Map<Byte, List<IBLEResponse>> responseMap = new HashMap();

    public void addBLEResponse(Byte cmd, IBLEResponse response) {
        if (!this.responseMap.containsKey(cmd)) {
            this.responseMap.put(cmd, new ArrayList());
        }
        List<IBLEResponse> list = this.responseMap.get(cmd);
        if (!list.contains(response)) {
            list.add(response);
        }
    }

    public void removeBLEResponse(Byte cmd, IBLEResponse response) {
        if (this.responseMap.containsKey(cmd)) {
            List<IBLEResponse> list = this.responseMap.get(cmd);
            if (list.contains(response)) {
                list.remove(response);
            }
        }
    }

    public void start(IBinder service) {
        this.mLeService = ((BleService.LocalBinder) service).getService(this.mBleCallBack);
        this.mLeService.setDecode(false);
        this.mLeService.setConnectTimeout(5000);
        this.mLeService.initialize();
    }

    private class ServicesDiscoveredTask extends TimerTask {
        String address;

        ServicesDiscoveredTask(String address2) {
            this.address = address2;
        }

        public void run() {
            BLERepository.this.enableNotification(this.address, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[1]);
        }
    }

    public boolean enableNotification(String address, UUID serUuid, UUID charUuid) {
        BluetoothGatt gatt = this.mLeService.getBluetoothGatt(address);
        BluetoothGattCharacteristic c = GattUtil.getGattCharacteristic(gatt, serUuid, charUuid);
        Log.e(TAG, "gatt=" + gatt + ", characteristic=" + c);
        return setCharacteristicNotification(gatt, c, true);
    }

    public boolean setCharacteristicNotification(BluetoothGatt gatt, BluetoothGattCharacteristic c, boolean enable) {
        if (this.mLeService != null) {
            return this.mLeService.setCharacteristicNotification(gatt, c, enable);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void processInputData(byte[] receiveBytes) {
        try {
            this.receiveDataCount += receiveBytes.length;
            this.bufferPackage += DataUtil.byteArrayToHex(receiveBytes) + " ";
            if (this.bufferPackage.indexOf("5C 72 5C 6E") > -1) {
                String currentPackage = this.bufferPackage.substring(0, this.bufferPackage.indexOf("5C 72 5C 6E")).trim();
                this.bufferPackage = this.bufferPackage.substring(this.bufferPackage.indexOf("5C 72 5C 6E") + "5C 72 5C 6E".length()).trim();
                final String sendPackage = currentPackage;
                new Thread() {
                    public void run() {
                        BLERepository.this.sendResponse(sendPackage);
                    }
                }.start();
            }
        } catch (Exception e) {
            Logger.e(e.getMessage(), new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public String getString(int key) {
        return HealthApp.x.getString(key);
    }

    /* access modifiers changed from: private */
    public void showMessage(String message) {
        try {
            ToastUtils.showToast(message);
        } catch (Exception e) {
            Logger.e(e.getMessage(), new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void sendResponse(String receiveData) {
        String[] allHexString = receiveData.split(" ");
        if (allHexString == null || allHexString.length <= 0) {
            Logger.e("=======:Not Found Cmd:" + receiveData, new Object[0]);
            return;
        }
        byte cmd = (byte) Integer.parseInt(allHexString[0], 16);
        sendResponse(cmd, (String[]) Arrays.copyOfRange(allHexString, 1, allHexString.length));
        if (!this.responseMap.containsKey(Byte.valueOf(cmd))) {
            Logger.e("=======:Not Found Cmd:" + receiveData, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void sendResponse(byte cmd, String data) {
        if (this.responseMap.containsKey(Byte.valueOf(cmd))) {
            sendResponse(cmd, new String[]{data});
            return;
        }
        Logger.e("=======:Not Found Cmd:" + cmd, new Object[0]);
    }

    /* access modifiers changed from: private */
    public void sendResponse(byte cmd, String[] data) {
        try {
            if (this.responseMap.containsKey(Byte.valueOf(cmd))) {
                for (IBLEResponse response : this.responseMap.get(Byte.valueOf(cmd))) {
                    response.onResponse(Byte.valueOf(cmd), data);
                }
                return;
            }
            Logger.e("=======:Not Found Cmd:" + cmd, new Object[0]);
        } catch (Exception e) {
            Logger.e(e.getMessage(), new Object[0]);
        }
    }

    public void stop() {
        if (this.mLeService != null) {
            this.mLeService.removeBleCallBack(this.mBleCallBack);
            this.mLeService = null;
        }
        this.isConnected = false;
        this.cacheManager.saveString(CacheKeys.BLUE_MAC, "");
        this.cacheManager.saveString(CacheKeys.BLUE_NAME, (String) null);
    }

    private void sendBigData(final Byte cmd, final byte[] totalBytes) {
        new Thread() {
            public void run() {
                byte[] sendBytes;
                try {
                    int count = (int) Math.ceil((((double) totalBytes.length) * 1.0d) / 17.0d);
                    for (int i = 0; i < count; i++) {
                        if (i == count - 1) {
                            sendBytes = Arrays.copyOfRange(totalBytes, i * 17, totalBytes.length);
                        } else {
                            sendBytes = Arrays.copyOfRange(totalBytes, i * 17, (i + 1) * 17);
                        }
                        BLERepository.this.mLeService.send(BLERepository.this.deviceMac, sendBytes, false);
                        if (i < count - 1) {
                            Thread.sleep(40);
                        }
                        BLERepository.this.sendResponse(cmd.byteValue(), new String[]{String.valueOf(((double) (i + 1)) / (((double) count) * 1.0d))});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendRequest(Byte cmd, Integer value) {
        if (isConnected()) {
            this.mLeService.send(this.deviceMac, PackageWriter.write(cmd, value), false);
            return;
        }
        sendResponse(cmd.byteValue(), BLEServiceApi.NOT_CONNECTED);
    }

    public void sendRequest(Byte cmd, Byte value) {
        if (isConnected()) {
            byte[] sendBytes = PackageWriter.write(cmd, value);
            Log.w(TAG, "=========send request: " + cmd + ", data:" + DataUtil.byteArrayToHex(sendBytes));
            this.mLeService.send(this.deviceMac, sendBytes, false);
            return;
        }
        sendResponse(cmd.byteValue(), BLEServiceApi.NOT_CONNECTED);
    }

    public void sendRequest(Byte cmd, byte[] sendBytes) {
        if (isConnected()) {
            sendBigData(cmd, sendBytes);
        } else {
            sendResponse(cmd.byteValue(), BLEServiceApi.NOT_CONNECTED);
        }
    }

    public void sendRequest(Byte cmd) {
        if (isConnected()) {
            this.mLeService.send(this.deviceMac, PackageWriter.write(cmd, (String) null), false);
            return;
        }
        sendResponse(cmd.byteValue(), BLEServiceApi.NOT_CONNECTED);
    }

    public void connect(LeDevice device) {
        Log.w(TAG, "connect==");
        if (this.mLeService != null) {
            this.cancelDeviceMac = this.deviceMac;
            if (!StringUtil.isNullOrEmpty(this.deviceMac)) {
                this.mLeService.setAutoConnect(this.deviceMac, false);
            }
            this.mLeService.connect(device.getMac(), true);
            this.deviceMac = device.getMac();
            this.deviceMac = this.deviceMac != null ? this.deviceMac : "";
            this.cacheManager.saveString(CacheKeys.BLUE_MAC, this.deviceMac);
            this.cacheManager.saveString(CacheKeys.BLUE_NAME, device.getName());
        }
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public boolean canConnected(LeDevice device) {
        if (this.mLeService == null || device.getMac() == null) {
            return false;
        }
        if (!isConnected() || !device.getMac().equals(this.deviceMac)) {
            return true;
        }
        return false;
    }

    public String getDeviceName() {
        String deviceName = this.cacheManager.getString(CacheKeys.BLUE_NAME);
        if (deviceName != null) {
            return deviceName;
        }
        return "无";
    }

    public String getDeviceMac() {
        String mac = this.cacheManager.getString(CacheKeys.BLUE_MAC);
        if (mac != null) {
            return mac;
        }
        return "无";
    }

    public List<LeDevice> getConnectedDevices() {
        List<LeDevice> list = new ArrayList<>();
        if (this.mLeService != null) {
            List<BluetoothDevice> connectedDevices = this.mLeService.getConnectedDevices();
            for (int i = 0; i < connectedDevices.size(); i++) {
                list.add(new LeDevice(connectedDevices.get(i).getName(), connectedDevices.get(i).getAddress()));
            }
        }
        return list;
    }

    public void updateFirmwareCmd(int length) {
        sendRequest(Byte.valueOf(BLEServiceApi.FIRMWARE_CMD), Integer.valueOf(length));
    }

    public void uploadFirmwareData(byte[] datas) {
        sendRequest(Byte.valueOf(BLEServiceApi.FIRMWARE_UPLOAD), datas);
    }

    public void sendTestCmd(Byte testCount) {
        sendRequest((Byte) (byte) 1, testCount);
    }

    public void sendDebugTestDataCmd(Byte testCount) {
        sendRequest((Byte) (byte) 2, testCount);
    }

    public void sendPowerTimeCmd(byte value) {
        sendRequest(Byte.valueOf(BLEServiceApi.POWER_CMD), Byte.valueOf(value));
    }

    public void sendLEDBrightnessCmd(byte value) {
        sendRequest(Byte.valueOf(BLEServiceApi.LED_CMD), Byte.valueOf(value));
    }

    public void sendPowerStatusCmd() {
        sendRequest(Byte.valueOf(BLEServiceApi.POWER_STATUS_CMD), (Byte) null);
    }
}

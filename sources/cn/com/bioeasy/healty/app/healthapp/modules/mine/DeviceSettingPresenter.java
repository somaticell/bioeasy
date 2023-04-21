package cn.com.bioeasy.healty.app.healthapp.modules.mine;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.file.FileUtil;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import cn.com.bioeasy.healty.app.healthapp.ble.IBLEResponse;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.BindView;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.DeviceSettingActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.user.modal.UserRepository;
import javax.inject.Inject;

public class DeviceSettingPresenter extends BasePresenter<BindView, UserRepository> implements IBLEResponse {
    private BLERepository bleRepository;

    @Inject
    public DeviceSettingPresenter(BLERepository bleRepository2, UserRepository mRepository) {
        super(mRepository);
        this.bleRepository = bleRepository2;
        bleRepository2.addBLEResponse(Byte.valueOf(BLEServiceApi.FIRMWARE_CMD), this);
        bleRepository2.addBLEResponse(Byte.valueOf(BLEServiceApi.LED_CMD), this);
        bleRepository2.addBLEResponse(Byte.valueOf(BLEServiceApi.POWER_CMD), this);
        bleRepository2.addBLEResponse(Byte.valueOf(BLEServiceApi.FIRMWARE_UPLOAD), this);
    }

    public void uploadFirmwareData(String fileName) {
        byte[] bytes = readPwmFile(fileName);
        if (bytes != null) {
            this.bleRepository.updateFirmwareCmd(bytes.length);
        }
    }

    public void onResponse(Byte cmd, String[] data) {
        if (34 == cmd.byteValue()) {
            if (data != null && data[0].equals(BLEServiceApi.RESULT_OK)) {
                ((DeviceSettingActivity) this.mUiView).checkPwmFile();
            }
        } else if (-99 == cmd.byteValue()) {
            ((DeviceSettingActivity) this.mUiView).updateSendProgress(data[0]);
        } else {
            if (33 == cmd.byteValue() || 35 == cmd.byteValue()) {
            }
        }
    }

    public void setLEDBrightless(int value) {
        this.bleRepository.sendLEDBrightnessCmd((byte) (value >= 0 ? value + 1 : 0));
    }

    public void setPowerAutoOff(int value) {
        this.bleRepository.sendPowerTimeCmd((byte) (value >= 0 ? value + 1 : 0));
    }

    public boolean updateFirmWare(String fileName) {
        this.bleRepository.uploadFirmwareData(readPwmFile(fileName));
        return true;
    }

    private byte[] readPwmFile(String filePath) {
        try {
            return FileUtil.readFileBytes(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onDestroy() {
        this.bleRepository.removeBLEResponse(Byte.valueOf(BLEServiceApi.FIRMWARE_CMD), this);
        this.bleRepository.removeBLEResponse(Byte.valueOf(BLEServiceApi.LED_CMD), this);
        this.bleRepository.removeBLEResponse(Byte.valueOf(BLEServiceApi.POWER_CMD), this);
        this.bleRepository.removeBLEResponse(Byte.valueOf(BLEServiceApi.FIRMWARE_UPLOAD), this);
    }
}

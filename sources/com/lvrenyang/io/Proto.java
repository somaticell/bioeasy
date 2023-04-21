package com.lvrenyang.io;

import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.android.phone.mrpc.core.RpcException;
import com.lvrenyang.io.base.IO;
import java.util.List;

public class Proto {
    private static final String TAG = "Proto";
    private IO IO = new IO();

    public interface OnProgressCallBack {
        void OnProgress(double d, double d2);
    }

    public void Set(IO io) {
        if (io != null) {
            this.IO = io;
        }
    }

    public IO GetIO() {
        return this.IO;
    }

    public boolean SendPackage(int cmd, int para, byte[] buffer, int offset, int count) {
        int nBytesReaded;
        byte[] pcmdbuf = new byte[(count + 12)];
        if (count > 0) {
            System.arraycopy(buffer, offset, pcmdbuf, 12, count);
        }
        pcmdbuf[0] = 3;
        pcmdbuf[1] = -1;
        pcmdbuf[2] = (byte) cmd;
        pcmdbuf[3] = (byte) (cmd >> 8);
        pcmdbuf[4] = (byte) para;
        pcmdbuf[5] = (byte) (para >> 8);
        pcmdbuf[6] = (byte) (para >> 16);
        pcmdbuf[7] = (byte) (para >> 24);
        pcmdbuf[8] = (byte) count;
        pcmdbuf[9] = (byte) (count >> 8);
        pcmdbuf[10] = 0;
        pcmdbuf[11] = 0;
        for (int j = 0; j < 10; j++) {
            pcmdbuf[10] = (byte) (pcmdbuf[10] ^ pcmdbuf[j]);
        }
        for (int j2 = 0; j2 < count; j2++) {
            pcmdbuf[11] = (byte) (pcmdbuf[11] ^ pcmdbuf[j2 + 12]);
        }
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        boolean result = false;
        try {
            byte[] rechead = new byte[12];
            this.IO.SkipAvailable();
            if (this.IO.Write(pcmdbuf, 0, pcmdbuf.length) == pcmdbuf.length) {
                long beginTime = System.currentTimeMillis();
                while (true) {
                    if (this.IO.IsOpened()) {
                        if (System.currentTimeMillis() - beginTime <= ((long) RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED) && (nBytesReaded = this.IO.Read(rechead, 0, 1, RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED)) > 0) {
                            if (nBytesReaded == 1 && rechead[0] == 3 && this.IO.Read(rechead, 1, 1, RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED) == 1 && (rechead[1] & BLEServiceApi.CONNECTED_STATUS) == 254 && this.IO.Read(rechead, 2, 10, RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED) == 10) {
                                int reccmd = (rechead[2] & 255) | ((rechead[3] & BLEServiceApi.CONNECTED_STATUS) << 8);
                                int recpara = (rechead[4] & 255) | ((rechead[5] & BLEServiceApi.CONNECTED_STATUS) << 8) | ((rechead[6] & BLEServiceApi.CONNECTED_STATUS) << 16) | ((rechead[7] & BLEServiceApi.CONNECTED_STATUS) << 24);
                                if (cmd == reccmd && para == recpara) {
                                    result = true;
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            this.IO.mMainLocker.unlock();
            return result;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            this.IO.mMainLocker.unlock();
            return false;
        } catch (Throwable th) {
            this.IO.mMainLocker.unlock();
            throw th;
        }
    }

    public boolean SendPackage(int cmd, int para, byte[] buffer, int offset, int count, int retry) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        boolean result = false;
        int i = 0;
        while (i < retry) {
            try {
                result = SendPackage(cmd, para, buffer, offset, count);
                if (result) {
                    break;
                }
                Thread.sleep(100);
                i++;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
                return result;
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        }
        this.IO.mMainLocker.unlock();
        return result;
    }

    public boolean Download(byte[] data, int cmd, int para, OnProgressCallBack onProgress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            if (!SendPackage(32, 0, "DEVICE??".getBytes(), 0, 8, 5)) {
                throw new Exception("Test Failed");
            }
            int nBytesWritten = 0;
            onProgress.OnProgress((double) 0, (double) data.length);
            while (nBytesWritten < data.length) {
                int nPackageSize = Math.min(256, data.length - nBytesWritten);
                if (SendPackage(cmd, para + nBytesWritten, data, nBytesWritten, nPackageSize, 5)) {
                    nBytesWritten += nPackageSize;
                    onProgress.OnProgress((double) nBytesWritten, (double) data.length);
                } else {
                    throw new Exception("SendPackage Failed");
                }
            }
            if (nBytesWritten == data.length) {
                result = true;
            } else {
                result = false;
            }
            return result;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean UpdateProgram(byte[] data, OnProgressCallBack onProgress) {
        boolean result = false;
        if (this.IO.IsOpened()) {
            result = false;
            this.IO.mMainLocker.lock();
            try {
                if (!SendPackage(32, 0, "DEVICE??".getBytes(), 0, 8, 5)) {
                    throw new Exception("Test Failed");
                } else if (!SendPackage(47, 0, (byte[]) null, 0, 0, 5)) {
                    throw new Exception("Update Cmd 0x2F Failed");
                } else {
                    result = Download(data, 46, 0, onProgress);
                    SendPackage(47, -1, (byte[]) null, 0, 0, 1);
                }
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        return result;
    }

    public boolean Downloads(List<byte[]> datas, int[] cmds, int[] paras, OnProgressCallBack onProgress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        int i = 0;
        while (i < datas.size() && (result = Download(datas.get(i), cmds[i], paras[i], onProgress))) {
            try {
                i++;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                return result;
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        return result;
    }
}

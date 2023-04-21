package com.lvrenyang.io;

import android.graphics.Bitmap;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.lvrenyang.io.base.IO;
import compress_lib_api.CompressLib;

public class Pos {
    private static final String TAG = "Pos";
    private ESCCmd Cmd = new ESCCmd();
    private IO IO = new IO();

    public void Set(IO io) {
        if (io != null) {
            this.IO = io;
        }
    }

    public IO GetIO() {
        return this.IO;
    }

    public boolean POS_PrintPicture(Bitmap mBitmap, int nWidth, int nBinaryAlgorithm, int nCompressMethod) {
        byte[] data;
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            int dstw = ((nWidth + 7) / 8) * 8;
            int dsth = (mBitmap.getHeight() * dstw) / mBitmap.getWidth();
            int[] dst = new int[(dstw * dsth)];
            ImageProcessing.resizeImage(mBitmap, dstw, dsth).getPixels(dst, 0, dstw, 0, 0, dstw, dsth);
            byte[] gray = ImageProcessing.GrayImage(dst);
            boolean[] dithered = new boolean[(dstw * dsth)];
            if (nBinaryAlgorithm == 0) {
                ImageProcessing.format_K_dither16x16(dstw, dsth, gray, dithered);
            } else {
                ImageProcessing.format_K_threshold(dstw, dsth, gray, dithered);
            }
            switch (nCompressMethod) {
                case 1:
                    data = ImageProcessing.eachLinePixToCompressCmd(dithered, dstw);
                    break;
                case 2:
                    data = CompressLib.RasterImageToCompressCmd(dstw, dsth, ImageProcessing.Image1ToRasterData(dstw, dsth, dithered));
                    break;
                default:
                    data = ImageProcessing.eachLinePixToCmd(dithered, dstw, 0);
                    break;
            }
            return this.IO.Write(data, 0, data.length) == data.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean POS_S_TextOut(String pszString, int nOrgx, int nWidthTimes, int nHeightTimes, int nFontType, int nFontStyle) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (nOrgx <= 65535 && nOrgx >= 0 && nWidthTimes <= 7 && nWidthTimes >= 0 && nHeightTimes <= 7 && nHeightTimes >= 0 && nFontType >= 0 && nFontType <= 4) {
            try {
                if (pszString.length() != 0) {
                    this.Cmd.ESC_dollors_nL_nH[2] = (byte) (nOrgx % 256);
                    this.Cmd.ESC_dollors_nL_nH[3] = (byte) (nOrgx / 256);
                    this.Cmd.GS_exclamationmark_n[2] = (byte) (new byte[]{0, 16, 32, 48, 64, 80, 96, 112}[nWidthTimes] + new byte[]{0, 1, 2, 3, 4, 5, 6, 7}[nHeightTimes]);
                    byte[] tmp_ESC_M_n = this.Cmd.ESC_M_n;
                    if (nFontType == 0 || nFontType == 1) {
                        tmp_ESC_M_n[2] = (byte) nFontType;
                    } else {
                        tmp_ESC_M_n = new byte[0];
                    }
                    this.Cmd.GS_E_n[2] = (byte) ((nFontStyle >> 3) & 1);
                    this.Cmd.ESC_line_n[2] = (byte) ((nFontStyle >> 7) & 3);
                    this.Cmd.FS_line_n[2] = (byte) ((nFontStyle >> 7) & 3);
                    this.Cmd.ESC_lbracket_n[2] = (byte) ((nFontStyle >> 9) & 1);
                    this.Cmd.GS_B_n[2] = (byte) ((nFontStyle >> 10) & 1);
                    this.Cmd.ESC_V_n[2] = (byte) ((nFontStyle >> 12) & 1);
                    this.Cmd.ESC_9_n[2] = 1;
                    byte[] data = byteArraysToBytes(new byte[][]{this.Cmd.ESC_dollors_nL_nH, this.Cmd.GS_exclamationmark_n, tmp_ESC_M_n, this.Cmd.GS_E_n, this.Cmd.ESC_line_n, this.Cmd.FS_line_n, this.Cmd.ESC_lbracket_n, this.Cmd.GS_B_n, this.Cmd.ESC_V_n, this.Cmd.FS_AND, this.Cmd.ESC_9_n, pszString.getBytes()});
                    return this.IO.Write(data, 0, data.length) == data.length;
                }
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                return false;
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        throw new Exception("invalid args");
    }

    public boolean POS_TextOut(String pszString, int nLan, int nOrgx, int nWidthTimes, int nHeightTimes, int nFontType, int nFontStyle) {
        byte[] pbString;
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (nOrgx <= 65535 && nOrgx >= 0 && nWidthTimes <= 7 && nWidthTimes >= 0 && nHeightTimes <= 7 && nHeightTimes >= 0 && nFontType >= 0 && nFontType <= 4) {
            try {
                if (pszString.length() != 0) {
                    this.Cmd.ESC_dollors_nL_nH[2] = (byte) (nOrgx % 256);
                    this.Cmd.ESC_dollors_nL_nH[3] = (byte) (nOrgx / 256);
                    this.Cmd.GS_exclamationmark_n[2] = (byte) (new byte[]{0, 16, 32, 48, 64, 80, 96, 112}[nWidthTimes] + new byte[]{0, 1, 2, 3, 4, 5, 6, 7}[nHeightTimes]);
                    byte[] tmp_ESC_M_n = this.Cmd.ESC_M_n;
                    if (nFontType == 0 || nFontType == 1) {
                        tmp_ESC_M_n[2] = (byte) nFontType;
                    } else {
                        tmp_ESC_M_n = new byte[0];
                    }
                    this.Cmd.GS_E_n[2] = (byte) ((nFontStyle >> 3) & 1);
                    this.Cmd.ESC_line_n[2] = (byte) ((nFontStyle >> 7) & 3);
                    this.Cmd.FS_line_n[2] = (byte) ((nFontStyle >> 7) & 3);
                    this.Cmd.ESC_lbracket_n[2] = (byte) ((nFontStyle >> 9) & 1);
                    this.Cmd.GS_B_n[2] = (byte) ((nFontStyle >> 10) & 1);
                    this.Cmd.ESC_V_n[2] = (byte) ((nFontStyle >> 12) & 1);
                    if (nLan == 0) {
                        this.Cmd.ESC_9_n[2] = 0;
                        pbString = pszString.getBytes("GBK");
                    } else if (nLan == 3) {
                        this.Cmd.ESC_9_n[2] = 3;
                        pbString = pszString.getBytes("Big5");
                    } else if (nLan == 4) {
                        this.Cmd.ESC_9_n[2] = 4;
                        pbString = pszString.getBytes("Shift_JIS");
                    } else if (nLan == 5) {
                        this.Cmd.ESC_9_n[2] = 5;
                        pbString = pszString.getBytes("EUC-KR");
                    } else {
                        this.Cmd.ESC_9_n[2] = 1;
                        pbString = pszString.getBytes();
                    }
                    byte[] data = byteArraysToBytes(new byte[][]{this.Cmd.ESC_dollors_nL_nH, this.Cmd.GS_exclamationmark_n, tmp_ESC_M_n, this.Cmd.GS_E_n, this.Cmd.ESC_line_n, this.Cmd.FS_line_n, this.Cmd.ESC_lbracket_n, this.Cmd.GS_B_n, this.Cmd.ESC_V_n, this.Cmd.FS_AND, this.Cmd.ESC_9_n, pbString});
                    return this.IO.Write(data, 0, data.length) == data.length;
                }
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                return false;
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        throw new Exception("invalid args");
    }

    public boolean POS_FeedLine() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = byteArraysToBytes(new byte[][]{this.Cmd.CR, this.Cmd.LF});
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_S_Align(int align) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (align < 0 || align > 2) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            byte[] data = this.Cmd.ESC_a_n;
            data[2] = (byte) align;
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_SetLineHeight(int nHeight) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (nHeight < 0 || nHeight > 255) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            byte[] data = this.Cmd.ESC_3_n;
            data[2] = (byte) nHeight;
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_S_SetBarcode(String strCodedata, int nOrgx, int nType, int nWidthX, int nHeight, int nHriFontType, int nHriFontPosition) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (nOrgx < 0 || nOrgx > 65535 || nType < 65 || nType > 73 || nHeight < 1 || nHeight > 255) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
                return false;
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            byte[] bCodeData = strCodedata.getBytes();
            this.Cmd.ESC_dollors_nL_nH[2] = (byte) (nOrgx % 256);
            this.Cmd.ESC_dollors_nL_nH[3] = (byte) (nOrgx / 256);
            this.Cmd.GS_w_n[2] = (byte) nWidthX;
            this.Cmd.GS_h_n[2] = (byte) nHeight;
            this.Cmd.GS_f_n[2] = (byte) (nHriFontType & 1);
            this.Cmd.GS_H_n[2] = (byte) (nHriFontPosition & 3);
            this.Cmd.GS_k_m_n_[2] = (byte) nType;
            this.Cmd.GS_k_m_n_[3] = (byte) bCodeData.length;
            byte[] data = byteArraysToBytes(new byte[][]{this.Cmd.ESC_dollors_nL_nH, this.Cmd.GS_w_n, this.Cmd.GS_h_n, this.Cmd.GS_f_n, this.Cmd.GS_H_n, this.Cmd.GS_k_m_n_, bCodeData});
            boolean ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_S_SetQRcode(String strCodedata, int nWidthX, int nVersion, int nErrorCorrectionLevel) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (nWidthX < 1 || nWidthX > 16 || nErrorCorrectionLevel < 1 || nErrorCorrectionLevel > 4 || nVersion < 0 || nVersion > 16) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            byte[] bCodeData = strCodedata.getBytes();
            this.Cmd.GS_w_n[2] = (byte) nWidthX;
            this.Cmd.GS_k_m_v_r_nL_nH[3] = (byte) nVersion;
            this.Cmd.GS_k_m_v_r_nL_nH[4] = (byte) nErrorCorrectionLevel;
            this.Cmd.GS_k_m_v_r_nL_nH[5] = (byte) (bCodeData.length & 255);
            this.Cmd.GS_k_m_v_r_nL_nH[6] = (byte) ((bCodeData.length & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            byte[] data = byteArraysToBytes(new byte[][]{this.Cmd.GS_w_n, this.Cmd.GS_k_m_v_r_nL_nH, bCodeData});
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_DoubleQRCode(String QR1Data, int QR1Position, int QR1Ecc, int QR1Version, String QR2Data, int QR2Position, int QR2Ecc, int QR2Version, int ModuleSize) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[] qr1code = QR1Data.getBytes();
            int QR1Datalen = qr1code.length;
            byte[] qr2code = QR2Data.getBytes();
            int QR2Datalen = qr2code.length;
            byte[] data = byteArraysToBytes(new byte[][]{new byte[]{31, 81, 2, (byte) ModuleSize}, new byte[]{(byte) ((65280 & QR1Position) >> 8), (byte) (QR1Position & 255), (byte) ((65280 & QR1Datalen) >> 8), (byte) (QR1Datalen & 255), (byte) QR1Ecc, (byte) QR1Version}, qr1code, new byte[]{(byte) ((65280 & QR2Position) >> 8), (byte) (QR2Position & 255), (byte) ((65280 & QR2Datalen) >> 8), (byte) (QR2Datalen & 255), (byte) QR2Ecc, (byte) QR2Version}, qr2code});
            return this.IO.Write(data, 0, data.length) == data.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean POS_EPSON_SetQRCode(String strCodedata, int nWidthX, int nErrorCorrectionLevel) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (nWidthX < 1 || nWidthX > 16 || nErrorCorrectionLevel < 1 || nErrorCorrectionLevel > 4) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            byte[] bCodeData = strCodedata.getBytes();
            this.Cmd.GS_leftbracket_k_pL_pH_cn_67_n[7] = (byte) nWidthX;
            this.Cmd.GS_leftbracket_k_pL_pH_cn_69_n[7] = (byte) (nErrorCorrectionLevel + 47);
            this.Cmd.GS_leftbracket_k_pL_pH_cn_80_m__d1dk[3] = (byte) ((bCodeData.length + 3) & 255);
            this.Cmd.GS_leftbracket_k_pL_pH_cn_80_m__d1dk[4] = (byte) (((bCodeData.length + 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            byte[] data = byteArraysToBytes(new byte[][]{this.Cmd.GS_leftbracket_k_pL_pH_cn_67_n, this.Cmd.GS_leftbracket_k_pL_pH_cn_69_n, this.Cmd.GS_leftbracket_k_pL_pH_cn_80_m__d1dk, bCodeData, this.Cmd.GS_leftbracket_k_pL_pH_cn_fn_m});
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_Reset() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = this.Cmd.ESC_ALT;
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_SetMotionUnit(int nHorizontalMU, int nVerticalMU) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (nHorizontalMU < 0 || nHorizontalMU > 255 || nVerticalMU < 0 || nVerticalMU > 255) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            byte[] data = this.Cmd.GS_P_x_y;
            data[2] = (byte) nHorizontalMU;
            data[3] = (byte) nVerticalMU;
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:22:0x0042=Splitter:B:22:0x0042, B:13:0x0027=Splitter:B:13:0x0027} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean POS_SetCharSetAndCodePage(int r9, int r10) {
        /*
            r8 = this;
            r3 = 1
            r4 = 0
            com.lvrenyang.io.base.IO r5 = r8.IO
            boolean r5 = r5.IsOpened()
            if (r5 != 0) goto L_0x000b
        L_0x000a:
            return r4
        L_0x000b:
            r2 = 0
            com.lvrenyang.io.base.IO r5 = r8.IO
            java.util.concurrent.locks.ReentrantLock r5 = r5.mMainLocker
            r5.lock()
            if (r9 < 0) goto L_0x0027
            r5 = 15
            if (r9 > r5) goto L_0x0027
            if (r10 < 0) goto L_0x0027
            r5 = 19
            if (r10 > r5) goto L_0x0027
            r5 = 10
            if (r10 <= r5) goto L_0x0042
            r5 = 16
            if (r10 >= r5) goto L_0x0042
        L_0x0027:
            java.lang.Exception r3 = new java.lang.Exception     // Catch:{ Exception -> 0x002f }
            java.lang.String r4 = "invalid args"
            r3.<init>(r4)     // Catch:{ Exception -> 0x002f }
            throw r3     // Catch:{ Exception -> 0x002f }
        L_0x002f:
            r1 = move-exception
            java.lang.String r3 = "Pos"
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x007d }
            android.util.Log.i(r3, r4)     // Catch:{ all -> 0x007d }
            com.lvrenyang.io.base.IO r3 = r8.IO
            java.util.concurrent.locks.ReentrantLock r3 = r3.mMainLocker
            r3.unlock()
        L_0x0040:
            r4 = r2
            goto L_0x000a
        L_0x0042:
            com.lvrenyang.io.ESCCmd r5 = r8.Cmd     // Catch:{ Exception -> 0x002f }
            byte[] r5 = r5.ESC_R_n     // Catch:{ Exception -> 0x002f }
            r6 = 2
            byte r7 = (byte) r9     // Catch:{ Exception -> 0x002f }
            r5[r6] = r7     // Catch:{ Exception -> 0x002f }
            com.lvrenyang.io.ESCCmd r5 = r8.Cmd     // Catch:{ Exception -> 0x002f }
            byte[] r5 = r5.ESC_t_n     // Catch:{ Exception -> 0x002f }
            r6 = 2
            byte r7 = (byte) r10     // Catch:{ Exception -> 0x002f }
            r5[r6] = r7     // Catch:{ Exception -> 0x002f }
            r5 = 2
            byte[][] r5 = new byte[r5][]     // Catch:{ Exception -> 0x002f }
            r6 = 0
            com.lvrenyang.io.ESCCmd r7 = r8.Cmd     // Catch:{ Exception -> 0x002f }
            byte[] r7 = r7.ESC_R_n     // Catch:{ Exception -> 0x002f }
            r5[r6] = r7     // Catch:{ Exception -> 0x002f }
            r6 = 1
            com.lvrenyang.io.ESCCmd r7 = r8.Cmd     // Catch:{ Exception -> 0x002f }
            byte[] r7 = r7.ESC_t_n     // Catch:{ Exception -> 0x002f }
            r5[r6] = r7     // Catch:{ Exception -> 0x002f }
            byte[] r0 = r8.byteArraysToBytes(r5)     // Catch:{ Exception -> 0x002f }
            com.lvrenyang.io.base.IO r5 = r8.IO     // Catch:{ Exception -> 0x002f }
            r6 = 0
            int r7 = r0.length     // Catch:{ Exception -> 0x002f }
            int r5 = r5.Write(r0, r6, r7)     // Catch:{ Exception -> 0x002f }
            int r6 = r0.length     // Catch:{ Exception -> 0x002f }
            if (r5 != r6) goto L_0x007b
            r2 = r3
        L_0x0073:
            com.lvrenyang.io.base.IO r3 = r8.IO
            java.util.concurrent.locks.ReentrantLock r3 = r3.mMainLocker
            r3.unlock()
            goto L_0x0040
        L_0x007b:
            r2 = r4
            goto L_0x0073
        L_0x007d:
            r3 = move-exception
            com.lvrenyang.io.base.IO r4 = r8.IO
            java.util.concurrent.locks.ReentrantLock r4 = r4.mMainLocker
            r4.unlock()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lvrenyang.io.Pos.POS_SetCharSetAndCodePage(int, int):boolean");
    }

    public boolean POS_SetRightSpacing(int nDistance) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (nDistance < 0 || nDistance > 255) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            this.Cmd.ESC_SP_n[2] = (byte) nDistance;
            byte[] data = this.Cmd.ESC_SP_n;
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_S_SetAreaWidth(int nWidth) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        if (nWidth < 0 || nWidth > 65535) {
            try {
                throw new Exception("invalid args");
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        } else {
            this.Cmd.GS_W_nL_nH[2] = (byte) (nWidth % 256);
            this.Cmd.GS_W_nL_nH[3] = (byte) (nWidth / 256);
            byte[] data = this.Cmd.GS_W_nL_nH;
            ret = this.IO.Write(data, 0, data.length) == data.length;
            this.IO.mMainLocker.unlock();
            return ret;
        }
    }

    public boolean POS_SetDoubleByteMode() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {28, BLEServiceApi.POWER_STATUS_CMD};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_SetSingleByteMode() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {28, 46};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_SetDoubleByteEncoding(int nEncoding) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {27, 57, (byte) nEncoding};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_CutPaper() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {29, 86, 66, 0};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_Beep(int nBeepCount, int nBeepMillis) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {27, 66, (byte) nBeepCount, (byte) nBeepMillis};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_KickDrawer(int nDrawerIndex, int nPulseTime) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {27, 112, (byte) nDrawerIndex, (byte) nPulseTime, (byte) nPulseTime};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_SetPrintSpeed(int nSpeed) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean ret = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] data = {31, 40, 115, 2, 0, (byte) ((int) (((long) nSpeed) & 255)), (byte) ((int) ((((long) nSpeed) & 65280) >> 8))};
            if (this.IO.Write(data, 0, data.length) == data.length) {
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return ret;
    }

    public boolean POS_QueryStatus(byte[] status, int timeout, int MaxRetry) {
        boolean result = false;
        if (this.IO.IsOpened()) {
            this.IO.mMainLocker.lock();
            result = false;
            try {
                byte[] cmd = {29, 114, 1};
                while (true) {
                    int MaxRetry2 = MaxRetry;
                    MaxRetry = MaxRetry2 - 1;
                    if (MaxRetry2 < 0) {
                        break;
                    }
                    this.IO.SkipAvailable();
                    if (this.IO.Write(cmd, 0, cmd.length) == cmd.length && this.IO.Read(status, 0, 1, timeout) == 1) {
                        result = true;
                        break;
                    }
                }
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        return result;
    }

    public boolean POS_RTQueryStatus(byte[] status, int type, int timeout, int MaxRetry) {
        boolean result = false;
        if (this.IO.IsOpened()) {
            this.IO.mMainLocker.lock();
            result = false;
            try {
                byte[] cmd = {16, 4, (byte) type};
                while (true) {
                    int MaxRetry2 = MaxRetry;
                    MaxRetry = MaxRetry2 - 1;
                    if (MaxRetry2 < 0) {
                        break;
                    }
                    this.IO.SkipAvailable();
                    if (this.IO.Write(cmd, 0, cmd.length) == cmd.length && this.IO.Read(status, 0, 1, timeout) == 1) {
                        result = true;
                        break;
                    }
                }
            } catch (Exception e) {
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        return result;
    }

    public int POS_TicketSucceed(int dwSendIndex, int timeout) {
        if (!this.IO.IsOpened()) {
            return -1;
        }
        boolean result = false;
        boolean b_conn_closed = false;
        boolean b_write_failed = false;
        boolean b_read_failed = false;
        boolean b_nopaper = false;
        boolean b_offline = false;
        boolean b_unknow_error = false;
        this.IO.mMainLocker.lock();
        try {
            Log.i(TAG, String.format("Get Ticket %d Result", new Object[]{Integer.valueOf(dwSendIndex)}));
            byte[] recbuf = new byte[7];
            byte[] rtqueryCmd = {16, 4, 1};
            byte[] ticketCmd = {29, 40, 72, 6, 0, 48, 48, (byte) dwSendIndex, (byte) (dwSendIndex >> 8), (byte) (dwSendIndex >> 16), (byte) (dwSendIndex >> 24)};
            this.IO.SkipAvailable();
            if (this.IO.Write(ticketCmd, 0, ticketCmd.length) == ticketCmd.length) {
                long beginTime = System.currentTimeMillis();
                while (true) {
                    if (!this.IO.IsOpened()) {
                        b_conn_closed = true;
                        break;
                    } else if (System.currentTimeMillis() - beginTime > ((long) timeout)) {
                        break;
                    } else {
                        int nBytesReaded = this.IO.Read(recbuf, 0, 1, 1000);
                        if (nBytesReaded < 0) {
                            b_read_failed = true;
                            break;
                        } else if (nBytesReaded == 0) {
                            this.IO.Write(rtqueryCmd, 0, rtqueryCmd.length);
                        } else if (nBytesReaded != 1) {
                            continue;
                        } else if (recbuf[0] == 55) {
                            if (this.IO.Read(recbuf, 1, 1, timeout) == 1 && ((recbuf[1] == 34 || recbuf[1] == 51) && this.IO.Read(recbuf, 2, 5, timeout) == 5 && dwSendIndex == ((recbuf[2] & 255) | ((recbuf[3] & BLEServiceApi.CONNECTED_STATUS) << 8) | ((recbuf[4] & BLEServiceApi.CONNECTED_STATUS) << 16) | ((recbuf[5] & BLEServiceApi.CONNECTED_STATUS) << 24)))) {
                                switch (recbuf[1]) {
                                    case 34:
                                        result = true;
                                        break;
                                    case 51:
                                        b_nopaper = true;
                                        break;
                                    default:
                                        b_unknow_error = true;
                                        break;
                                }
                                Log.i(TAG, String.format("Ticket Result: %02X %02X %02X %02X %02X %02X %02X", new Object[]{Byte.valueOf(recbuf[0]), Byte.valueOf(recbuf[1]), Byte.valueOf(recbuf[2]), Byte.valueOf(recbuf[3]), Byte.valueOf(recbuf[4]), Byte.valueOf(recbuf[5]), Byte.valueOf(recbuf[6])}));
                            }
                        } else if ((recbuf[0] & 18) == 18) {
                            Log.i(TAG, String.format("Printer RT Status: %02X ", new Object[]{Byte.valueOf(recbuf[0])}));
                            if ((recbuf[0] & 8) != 0) {
                                b_offline = true;
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                b_write_failed = true;
            }
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(dwSendIndex);
            objArr[1] = result ? "Succeed" : "Failed";
            Log.i(TAG, String.format("Ticket %d %s", objArr));
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        if (b_conn_closed) {
            return -1;
        }
        if (b_write_failed) {
            return -2;
        }
        if (b_read_failed) {
            return -3;
        }
        if (b_offline) {
            return -4;
        }
        if (b_nopaper) {
            return -5;
        }
        return (!b_unknow_error && result) ? 0 : -6;
    }

    private byte[] byteArraysToBytes(byte[][] data) {
        int length = 0;
        for (byte[] length2 : data) {
            length += length2.length;
        }
        byte[] send = new byte[length];
        int k = 0;
        for (int i = 0; i < data.length; i++) {
            int j = 0;
            while (j < data[i].length) {
                send[k] = data[i][j];
                j++;
                k++;
            }
        }
        return send;
    }
}

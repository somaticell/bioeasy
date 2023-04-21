package com.lvrenyang.qrcode;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QRCode {
    private static final int PAD0 = 236;
    private static final int PAD1 = 17;
    private int errorCorrectLevel = 2;
    private int moduleCount;
    private Boolean[][] modules;
    private List<QRData> qrDataList = new ArrayList(1);
    private int typeNumber = 1;

    public int getTypeNumber() {
        return this.typeNumber;
    }

    public void setTypeNumber(int typeNumber2) {
        this.typeNumber = typeNumber2;
    }

    public int getErrorCorrectLevel() {
        return this.errorCorrectLevel;
    }

    public void setErrorCorrectLevel(int errorCorrectLevel2) {
        this.errorCorrectLevel = errorCorrectLevel2;
    }

    public void addData(String data) {
        addData(data, QRUtil.getMode(data));
    }

    public void addData(String data, int mode) {
        switch (mode) {
            case 1:
                addData((QRData) new QRNumber(data));
                return;
            case 2:
                addData((QRData) new QRAlphaNum(data));
                return;
            case 4:
                addData((QRData) new QR8BitByte(data));
                return;
            case 8:
                addData((QRData) new QRKanji(data));
                return;
            default:
                throw new IllegalArgumentException("mode:" + mode);
        }
    }

    public void clearData() {
        this.qrDataList.clear();
    }

    /* access modifiers changed from: protected */
    public void addData(QRData qrData) {
        this.qrDataList.add(qrData);
    }

    /* access modifiers changed from: protected */
    public int getDataCount() {
        return this.qrDataList.size();
    }

    /* access modifiers changed from: protected */
    public QRData getData(int index) {
        return this.qrDataList.get(index);
    }

    public boolean isDark(int row, int col) {
        if (this.modules[row][col] != null) {
            return this.modules[row][col].booleanValue();
        }
        return false;
    }

    public int getModuleCount() {
        return this.moduleCount;
    }

    public Boolean[][] getModules() {
        return this.modules;
    }

    public void make() {
        make(false, getBestMaskPattern());
    }

    private int getBestMaskPattern() {
        int minLostPoint = 0;
        int pattern = 0;
        for (int i = 0; i < 8; i++) {
            make(true, i);
            int lostPoint = QRUtil.getLostPoint(this);
            if (i == 0 || minLostPoint > lostPoint) {
                minLostPoint = lostPoint;
                pattern = i;
            }
        }
        return pattern;
    }

    private void make(boolean test, int maskPattern) {
        this.moduleCount = (this.typeNumber * 4) + 17;
        this.modules = (Boolean[][]) Array.newInstance(Boolean.class, new int[]{this.moduleCount, this.moduleCount});
        setupPositionProbePattern(0, 0);
        setupPositionProbePattern(this.moduleCount - 7, 0);
        setupPositionProbePattern(0, this.moduleCount - 7);
        setupPositionAdjustPattern();
        setupTimingPattern();
        setupTypeInfo(test, maskPattern);
        if (this.typeNumber >= 7) {
            setupTypeNumber(test);
        }
        mapData(createData(this.typeNumber, this.errorCorrectLevel, (QRData[]) this.qrDataList.toArray(new QRData[this.qrDataList.size()])), maskPattern);
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mapData(byte[] r14, int r15) {
        /*
            r13 = this;
            r9 = 0
            r8 = 1
            r5 = -1
            int r10 = r13.moduleCount
            int r7 = r10 + -1
            r0 = 7
            r1 = 0
            int r10 = r13.moduleCount
            int r3 = r10 + -1
        L_0x000d:
            if (r3 <= 0) goto L_0x0060
            r10 = 6
            if (r3 != r10) goto L_0x0014
            int r3 = r3 + -1
        L_0x0014:
            r2 = 0
        L_0x0015:
            r10 = 2
            if (r2 >= r10) goto L_0x0054
            java.lang.Boolean[][] r10 = r13.modules
            r10 = r10[r7]
            int r11 = r3 - r2
            r10 = r10[r11]
            if (r10 != 0) goto L_0x004d
            r4 = 0
            int r10 = r14.length
            if (r1 >= r10) goto L_0x002e
            byte r10 = r14[r1]
            int r10 = r10 >>> r0
            r10 = r10 & 1
            if (r10 != r8) goto L_0x0050
            r4 = r8
        L_0x002e:
            int r10 = r3 - r2
            boolean r6 = com.lvrenyang.qrcode.QRUtil.getMask(r15, r7, r10)
            if (r6 == 0) goto L_0x0039
            if (r4 != 0) goto L_0x0052
            r4 = r8
        L_0x0039:
            java.lang.Boolean[][] r10 = r13.modules
            r10 = r10[r7]
            int r11 = r3 - r2
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r4)
            r10[r11] = r12
            int r0 = r0 + -1
            r10 = -1
            if (r0 != r10) goto L_0x004d
            int r1 = r1 + 1
            r0 = 7
        L_0x004d:
            int r2 = r2 + 1
            goto L_0x0015
        L_0x0050:
            r4 = r9
            goto L_0x002e
        L_0x0052:
            r4 = r9
            goto L_0x0039
        L_0x0054:
            int r7 = r7 + r5
            if (r7 < 0) goto L_0x005b
            int r10 = r13.moduleCount
            if (r10 > r7) goto L_0x0014
        L_0x005b:
            int r7 = r7 - r5
            int r5 = -r5
            int r3 = r3 + -2
            goto L_0x000d
        L_0x0060:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lvrenyang.qrcode.QRCode.mapData(byte[], int):void");
    }

    private void setupPositionAdjustPattern() {
        int[] pos = QRUtil.getPatternPosition(this.typeNumber);
        for (int i = 0; i < pos.length; i++) {
            for (int col : pos) {
                int row = pos[i];
                if (this.modules[row][col] == null) {
                    for (int r = -2; r <= 2; r++) {
                        for (int c = -2; c <= 2; c++) {
                            if (r == -2 || r == 2 || c == -2 || c == 2 || (r == 0 && c == 0)) {
                                this.modules[row + r][col + c] = true;
                            } else {
                                this.modules[row + r][col + c] = false;
                            }
                        }
                    }
                }
            }
        }
    }

    private void setupPositionProbePattern(int row, int col) {
        for (int r = -1; r <= 7; r++) {
            for (int c = -1; c <= 7; c++) {
                if (row + r > -1 && this.moduleCount > row + r && col + c > -1 && this.moduleCount > col + c) {
                    if ((r < 0 || r > 6 || !(c == 0 || c == 6)) && ((c < 0 || c > 6 || !(r == 0 || r == 6)) && (2 > r || r > 4 || 2 > c || c > 4))) {
                        this.modules[row + r][col + c] = false;
                    } else {
                        this.modules[row + r][col + c] = true;
                    }
                }
            }
        }
    }

    private void setupTimingPattern() {
        for (int r = 8; r < this.moduleCount - 8; r++) {
            if (this.modules[r][6] == null) {
                this.modules[r][6] = Boolean.valueOf(r % 2 == 0);
            }
        }
        for (int c = 8; c < this.moduleCount - 8; c++) {
            if (this.modules[6][c] == null) {
                this.modules[6][c] = Boolean.valueOf(c % 2 == 0);
            }
        }
    }

    private void setupTypeNumber(boolean test) {
        boolean z;
        boolean z2;
        int bits = QRUtil.getBCHTypeNumber(this.typeNumber);
        for (int i = 0; i < 18; i++) {
            if (test || ((bits >> i) & 1) != 1) {
                z2 = false;
            } else {
                z2 = true;
            }
            this.modules[i / 3][(((i % 3) + this.moduleCount) - 8) - 3] = Boolean.valueOf(z2);
        }
        for (int i2 = 0; i2 < 18; i2++) {
            if (test || ((bits >> i2) & 1) != 1) {
                z = false;
            } else {
                z = true;
            }
            this.modules[(((i2 % 3) + this.moduleCount) - 8) - 3][i2 / 3] = Boolean.valueOf(z);
        }
    }

    private void setupTypeInfo(boolean test, int maskPattern) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        int bits = QRUtil.getBCHTypeInfo((this.errorCorrectLevel << 3) | maskPattern);
        for (int i = 0; i < 15; i++) {
            if (test || ((bits >> i) & 1) != 1) {
                z2 = false;
            } else {
                z2 = true;
            }
            Boolean mod = Boolean.valueOf(z2);
            if (i < 6) {
                this.modules[i][8] = mod;
            } else if (i < 8) {
                this.modules[i + 1][8] = mod;
            } else {
                this.modules[(this.moduleCount - 15) + i][8] = mod;
            }
        }
        for (int i2 = 0; i2 < 15; i2++) {
            if (test || ((bits >> i2) & 1) != 1) {
                z = false;
            } else {
                z = true;
            }
            Boolean mod2 = Boolean.valueOf(z);
            if (i2 < 8) {
                this.modules[8][(this.moduleCount - i2) - 1] = mod2;
            } else if (i2 < 9) {
                this.modules[8][((15 - i2) - 1) + 1] = mod2;
            } else {
                this.modules[8][(15 - i2) - 1] = mod2;
            }
        }
        Boolean[] boolArr = this.modules[this.moduleCount - 8];
        if (test) {
            z3 = false;
        }
        boolArr[8] = Boolean.valueOf(z3);
    }

    public static byte[] createData(int typeNumber2, int errorCorrectLevel2, QRData[] dataArray) {
        RSBlock[] rsBlocks = RSBlock.getRSBlocks(typeNumber2, errorCorrectLevel2);
        BitBuffer buffer = new BitBuffer();
        for (QRData data : dataArray) {
            buffer.put(data.getMode(), 4);
            buffer.put(data.getLength(), data.getLengthInBits(typeNumber2));
            data.write(buffer);
        }
        int totalDataCount = 0;
        for (RSBlock dataCount : rsBlocks) {
            totalDataCount += dataCount.getDataCount();
        }
        if (buffer.getLengthInBits() > totalDataCount * 8) {
            throw new IllegalArgumentException("code length overflow. (" + buffer.getLengthInBits() + ">" + (totalDataCount * 8) + ")");
        }
        if (buffer.getLengthInBits() + 4 <= totalDataCount * 8) {
            buffer.put(0, 4);
        }
        while (buffer.getLengthInBits() % 8 != 0) {
            buffer.put(false);
        }
        while (buffer.getLengthInBits() < totalDataCount * 8) {
            buffer.put(PAD0, 8);
            if (buffer.getLengthInBits() >= totalDataCount * 8) {
                break;
            }
            buffer.put(17, 8);
        }
        return createBytes(buffer, rsBlocks);
    }

    private static byte[] createBytes(BitBuffer buffer, RSBlock[] rsBlocks) {
        int offset = 0;
        int maxDcCount = 0;
        int maxEcCount = 0;
        int[][] dcdata = new int[rsBlocks.length][];
        int[][] ecdata = new int[rsBlocks.length][];
        for (int r = 0; r < rsBlocks.length; r++) {
            int dcCount = rsBlocks[r].getDataCount();
            int ecCount = rsBlocks[r].getTotalCount() - dcCount;
            maxDcCount = Math.max(maxDcCount, dcCount);
            maxEcCount = Math.max(maxEcCount, ecCount);
            dcdata[r] = new int[dcCount];
            for (int i = 0; i < dcdata[r].length; i++) {
                dcdata[r][i] = buffer.getBuffer()[i + offset] & BLEServiceApi.CONNECTED_STATUS;
            }
            offset += dcCount;
            Polynomial rsPoly = QRUtil.getErrorCorrectPolynomial(ecCount);
            Polynomial modPoly = new Polynomial(dcdata[r], rsPoly.getLength() - 1).mod(rsPoly);
            ecdata[r] = new int[(rsPoly.getLength() - 1)];
            for (int i2 = 0; i2 < ecdata[r].length; i2++) {
                int modIndex = (modPoly.getLength() + i2) - ecdata[r].length;
                ecdata[r][i2] = modIndex >= 0 ? modPoly.get(modIndex) : 0;
            }
        }
        int totalCodeCount = 0;
        for (RSBlock totalCount : rsBlocks) {
            totalCodeCount += totalCount.getTotalCount();
        }
        byte[] data = new byte[totalCodeCount];
        int index = 0;
        for (int i3 = 0; i3 < maxDcCount; i3++) {
            for (int r2 = 0; r2 < rsBlocks.length; r2++) {
                if (i3 < dcdata[r2].length) {
                    data[index] = (byte) dcdata[r2][i3];
                    index++;
                }
            }
        }
        for (int i4 = 0; i4 < maxEcCount; i4++) {
            for (int r3 = 0; r3 < rsBlocks.length; r3++) {
                if (i4 < ecdata[r3].length) {
                    data[index] = (byte) ecdata[r3][i4];
                    index++;
                }
            }
        }
        return data;
    }

    public static int getMinimumQRCodeTypeNumber(String data, int errorCorrectLevel2) {
        int mode = QRUtil.getMode(data);
        QRCode qr = new QRCode();
        qr.setErrorCorrectLevel(errorCorrectLevel2);
        qr.addData(data, mode);
        int length = qr.getData(0).getLength();
        int typeNumber2 = 1;
        while (typeNumber2 <= 10 && length > QRUtil.getMaxLength(typeNumber2, mode, errorCorrectLevel2)) {
            typeNumber2++;
        }
        return typeNumber2;
    }

    public static QRCode getFixedSizeQRCode(String data, int errorCorrectLevel2, int typeNumber2) {
        int mode = QRUtil.getMode(data);
        QRCode qr = new QRCode();
        qr.setErrorCorrectLevel(errorCorrectLevel2);
        qr.addData(data, mode);
        if (qr.getData(0).getLength() > QRUtil.getMaxLength(typeNumber2, mode, errorCorrectLevel2)) {
            return null;
        }
        qr.setTypeNumber(typeNumber2);
        qr.make();
        return qr;
    }
}

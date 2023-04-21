package com.ble.ble;

import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseArray;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.ble.api.DataUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LeScanRecord {
    private final int a;
    private final List<ParcelUuid> b;
    private final SparseArray<byte[]> c;
    private final Map<ParcelUuid, byte[]> d;
    private final int e;
    private final String f;
    private final float[] g;
    private final byte[] h;

    private LeScanRecord(List<ParcelUuid> list, SparseArray<byte[]> sparseArray, Map<ParcelUuid, byte[]> map, int i, int i2, float[] fArr, String str, byte[] bArr) {
        this.b = list;
        this.c = sparseArray;
        this.d = map;
        this.f = str;
        this.a = i;
        this.e = i2;
        this.g = fArr;
        this.h = bArr;
    }

    private static int a(byte[] bArr, int i, int i2, int i3, List<ParcelUuid> list) {
        while (i2 > 0) {
            list.add(a.a(a(bArr, i, i3)));
            i2 -= i3;
            i += i3;
        }
        return i;
    }

    private static String a(SparseArray<byte[]> sparseArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            sb.append('[');
            sb.append(String.format(Locale.US, "%02X %02X ", new Object[]{Integer.valueOf(keyAt & 255), Integer.valueOf((keyAt >> 8) & 255)}));
            sb.append(DataUtil.byteArrayToHex(sparseArray.get(keyAt)));
            sb.append(']');
            if (i < sparseArray.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String a(List<ParcelUuid> list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return sb.toString().trim();
            }
            sb.append(list.get(i2).toString());
            sb.append("\n");
            i = i2 + 1;
        }
    }

    private static String a(Map<ParcelUuid, byte[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : map.entrySet()) {
            sb.append("0x");
            sb.append(((ParcelUuid) next.getKey()).toString().substring(4, 8));
            sb.append('[');
            sb.append(DataUtil.byteArrayToHex((byte[]) next.getValue()));
            sb.append("]\n");
        }
        return sb.toString();
    }

    private static void a(byte[] bArr, int i, int i2, float[] fArr) {
        byte[] a2 = a(bArr, i, i2);
        int i3 = ((a2[1] & BLEServiceApi.CONNECTED_STATUS) << 8) + (a2[0] & BLEServiceApi.CONNECTED_STATUS);
        byte b2 = a2[2] & BLEServiceApi.CONNECTED_STATUS;
        fArr[0] = ((float) i3) * 1.25f;
        fArr[1] = ((float) (b2 + ((a2[3] & BLEServiceApi.CONNECTED_STATUS) << 8))) * 1.25f;
    }

    private static byte[] a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static LeScanRecord parseFromBytes(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int i = 0;
        byte b2 = -1;
        ArrayList arrayList = new ArrayList();
        String str = null;
        byte b3 = -2147483648;
        float[] fArr = new float[2];
        SparseArray sparseArray = new SparseArray();
        HashMap hashMap = new HashMap();
        while (i < bArr.length) {
            try {
                int i2 = i + 1;
                byte b4 = bArr[i] & BLEServiceApi.CONNECTED_STATUS;
                if (b4 == 0) {
                    return new LeScanRecord(arrayList, sparseArray, hashMap, b2, b3, fArr, str, bArr);
                }
                int i3 = b4 - 1;
                int i4 = i2 + 1;
                switch (bArr[i2] & BLEServiceApi.CONNECTED_STATUS) {
                    case 1:
                        b2 = bArr[i4] & BLEServiceApi.CONNECTED_STATUS;
                        break;
                    case 2:
                    case 3:
                        a(bArr, i4, i3, 2, arrayList);
                        break;
                    case 4:
                    case 5:
                        a(bArr, i4, i3, 4, arrayList);
                        break;
                    case 6:
                    case 7:
                        a(bArr, i4, i3, 16, arrayList);
                        break;
                    case 8:
                    case 9:
                        str = new String(a(bArr, i4, i3));
                        break;
                    case 10:
                        b3 = bArr[i4];
                        break;
                    case 18:
                        a(bArr, i4, i3, fArr);
                        break;
                    case 22:
                        hashMap.put(a.a(a(bArr, i4, 2)), a(bArr, i4 + 2, i3 - 2));
                        break;
                    case 255:
                        sparseArray.put(((bArr[i4 + 1] & BLEServiceApi.CONNECTED_STATUS) << 8) + (bArr[i4] & BLEServiceApi.CONNECTED_STATUS), a(bArr, i4 + 2, i3 - 2));
                        break;
                }
                i = i3 + i4;
            } catch (Exception e2) {
                Log.e("LeScanRecord", "unable to parse scan record: " + Arrays.toString(bArr));
                return new LeScanRecord((List<ParcelUuid>) null, (SparseArray<byte[]>) null, (Map<ParcelUuid, byte[]>) null, -1, Integer.MIN_VALUE, (float[]) null, (String) null, bArr);
            }
        }
        return new LeScanRecord(arrayList, sparseArray, hashMap, b2, b3, fArr, str, bArr);
    }

    public int getAdvertiseFlags() {
        return this.a;
    }

    public byte[] getBytes() {
        return this.h;
    }

    public float[] getConnectionIntervalRange() {
        return this.g;
    }

    public String getLocalName() {
        return this.f;
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.c;
    }

    public byte[] getManufacturerSpecificData(int i) {
        return this.c.get(i);
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.d;
    }

    public byte[] getServiceData(ParcelUuid parcelUuid) {
        if (parcelUuid == null) {
            return null;
        }
        return this.d.get(parcelUuid);
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.b;
    }

    public int getTxPowerLevel() {
        return this.e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Advertise Flags: " + this.a);
        sb.append("\n");
        sb.append("Service UUID: " + a(this.b));
        sb.append("\n");
        sb.append("Local Name: " + this.f);
        sb.append("\n");
        sb.append("Tx Power Level: " + this.e);
        sb.append("\n");
        sb.append("Connection Interval Range: " + this.g[0] + "ms~" + this.g[1] + "ms");
        sb.append("\n");
        sb.append("Service Data: " + a(this.d));
        sb.append("\n");
        sb.append("Manufacturer Specific Data: " + a(this.c));
        return sb.toString();
    }
}

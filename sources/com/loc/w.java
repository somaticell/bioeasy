package com.loc;

import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.sdk.sys.a;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: Utils */
public class w {
    public static boolean a(JSONObject jSONObject, String str) {
        return jSONObject != null && jSONObject.has(str);
    }

    public static byte[] a() {
        try {
            String[] split = new StringBuffer("16,16,18,77,15,911,121,77,121,911,38,77,911,99,86,67,611,96,48,77,84,911,38,67,021,301,86,67,611,98,48,77,511,77,48,97,511,58,48,97,511,84,501,87,511,96,48,77,221,911,38,77,121,37,86,67,25,301,86,67,021,96,86,67,021,701,86,67,35,56,86,67,611,37,221,87").reverse().toString().split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            byte[] bArr = new byte[split.length];
            for (int i = 0; i < split.length; i++) {
                bArr[i] = Byte.parseByte(split[i]);
            }
            String[] split2 = new StringBuffer(new String(r.a(new String(bArr)))).reverse().toString().split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            byte[] bArr2 = new byte[split2.length];
            for (int i2 = 0; i2 < split2.length; i2++) {
                bArr2[i2] = Byte.parseByte(split2[i2]);
            }
            return bArr2;
        } catch (Throwable th) {
            y.a(th, "Utils", "getIV");
            return new byte[16];
        }
    }

    static String a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(a.b);
            }
            sb.append((String) next.getKey());
            sb.append("=");
            sb.append((String) next.getValue());
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0033 A[SYNTHETIC, Splitter:B:22:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0038 A[SYNTHETIC, Splitter:B:25:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0047 A[SYNTHETIC, Splitter:B:32:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x004c A[SYNTHETIC, Splitter:B:35:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.Throwable r4) {
        /*
            r0 = 0
            java.io.StringWriter r3 = new java.io.StringWriter     // Catch:{ Throwable -> 0x002b, all -> 0x0041 }
            r3.<init>()     // Catch:{ Throwable -> 0x002b, all -> 0x0041 }
            java.io.PrintWriter r2 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            r4.printStackTrace(r2)     // Catch:{ Throwable -> 0x006f }
            java.lang.Throwable r1 = r4.getCause()     // Catch:{ Throwable -> 0x006f }
        L_0x0012:
            if (r1 == 0) goto L_0x001c
            r1.printStackTrace(r2)     // Catch:{ Throwable -> 0x006f }
            java.lang.Throwable r1 = r1.getCause()     // Catch:{ Throwable -> 0x006f }
            goto L_0x0012
        L_0x001c:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x006f }
            if (r3 == 0) goto L_0x0025
            r3.close()     // Catch:{ Throwable -> 0x005f }
        L_0x0025:
            if (r2 == 0) goto L_0x002a
            r2.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x002a:
            return r0
        L_0x002b:
            r1 = move-exception
            r2 = r0
            r3 = r0
        L_0x002e:
            r1.printStackTrace()     // Catch:{ all -> 0x006a }
            if (r3 == 0) goto L_0x0036
            r3.close()     // Catch:{ Throwable -> 0x005a }
        L_0x0036:
            if (r2 == 0) goto L_0x002a
            r2.close()     // Catch:{ Throwable -> 0x003c }
            goto L_0x002a
        L_0x003c:
            r1 = move-exception
        L_0x003d:
            r1.printStackTrace()
            goto L_0x002a
        L_0x0041:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r0 = r1
        L_0x0045:
            if (r3 == 0) goto L_0x004a
            r3.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x004a:
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ Throwable -> 0x0055 }
        L_0x004f:
            throw r0
        L_0x0050:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x004a
        L_0x0055:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x004f
        L_0x005a:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0036
        L_0x005f:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0025
        L_0x0064:
            r1 = move-exception
            goto L_0x003d
        L_0x0066:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L_0x0045
        L_0x006a:
            r0 = move-exception
            goto L_0x0045
        L_0x006c:
            r1 = move-exception
            r2 = r0
            goto L_0x002e
        L_0x006f:
            r1 = move-exception
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.w.a(java.lang.Throwable):java.lang.String");
    }

    public static String b(Map<String, String> map) {
        return a(a(map));
    }

    public static String a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            String[] split = str.split(a.b);
            Arrays.sort(split);
            StringBuffer stringBuffer = new StringBuffer();
            for (String append : split) {
                stringBuffer.append(append);
                stringBuffer.append(a.b);
            }
            String stringBuffer2 = stringBuffer.toString();
            if (stringBuffer2.length() > 1) {
                return (String) stringBuffer2.subSequence(0, stringBuffer2.length() - 1);
            }
            return str;
        } catch (Throwable th) {
            y.a(th, "Utils", "sortParams");
        }
    }

    public static byte[] a(byte[] bArr) {
        try {
            return d(bArr);
        } catch (IOException e) {
            y.a(e, "Utils", "gZip");
        } catch (Throwable th) {
            y.a(th, "Utils", "gZip");
        }
        return new byte[0];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0038, code lost:
        if (r2 != null) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0040, code lost:
        if (r2 == null) goto L_0x0026;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        if (r2 != null) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        r2.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.security.PublicKey a(android.content.Context r5) throws java.security.cert.CertificateException, java.security.spec.InvalidKeySpecException, java.security.NoSuchAlgorithmException, java.lang.NullPointerException, java.io.IOException {
        /*
            r0 = 0
            r1 = 674(0x2a2, float:9.44E-43)
            byte[] r1 = new byte[r1]
            r1 = {48, -126, 2, -98, 48, -126, 2, 7, -96, 3, 2, 1, 2, 2, 9, 0, -99, 15, 119, 58, 44, -19, -105, -40, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 5, 5, 0, 48, 104, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 19, 48, 17, 6, 3, 85, 4, 8, 12, 10, 83, 111, 109, 101, 45, 83, 116, 97, 116, 101, 49, 16, 48, 14, 6, 3, 85, 4, 7, 12, 7, 66, 101, 105, 106, 105, 110, 103, 49, 17, 48, 15, 6, 3, 85, 4, 10, 12, 8, 65, 117, 116, 111, 110, 97, 118, 105, 49, 31, 48, 29, 6, 3, 85, 4, 3, 12, 22, 99, 111, 109, 46, 97, 117, 116, 111, 110, 97, 118, 105, 46, 97, 112, 105, 115, 101, 114, 118, 101, 114, 48, 30, 23, 13, 49, 51, 48, 56, 49, 53, 48, 55, 53, 54, 53, 53, 90, 23, 13, 50, 51, 48, 56, 49, 51, 48, 55, 53, 54, 53, 53, 90, 48, 104, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 19, 48, 17, 6, 3, 85, 4, 8, 12, 10, 83, 111, 109, 101, 45, 83, 116, 97, 116, 101, 49, 16, 48, 14, 6, 3, 85, 4, 7, 12, 7, 66, 101, 105, 106, 105, 110, 103, 49, 17, 48, 15, 6, 3, 85, 4, 10, 12, 8, 65, 117, 116, 111, 110, 97, 118, 105, 49, 31, 48, 29, 6, 3, 85, 4, 3, 12, 22, 99, 111, 109, 46, 97, 117, 116, 111, 110, 97, 118, 105, 46, 97, 112, 105, 115, 101, 114, 118, 101, 114, 48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -15, -27, -128, -56, 118, -59, 62, -127, 79, 125, -36, 121, 0, 63, -125, -30, 118, 5, -85, -121, 91, 39, 90, 123, 72, -126, -83, -41, -45, -77, -42, -120, -81, 23, -2, -121, -29, 123, -7, 22, -114, -20, -25, 74, 67, -43, 65, 124, -7, 11, -72, 38, -123, 16, -58, 80, 32, 58, -33, 14, 11, 36, 60, 13, -121, 100, 105, -32, 123, -31, 114, -101, -41, 12, 100, 33, -120, 63, 126, -123, 48, 55, 80, -116, 28, -10, 125, 59, -41, -95, -126, 118, -70, 43, -127, 9, 93, -100, 81, -19, -114, -41, 85, -103, -37, -116, 118, 72, 86, 125, -43, -92, -11, 63, 69, -38, -10, -65, 126, -53, -115, 60, 62, -86, -80, 1, 39, 19, 2, 3, 1, 0, 1, -93, 80, 48, 78, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, -29, 63, 48, -79, -113, -13, 26, 85, 22, -27, 93, -5, 122, -103, -109, 14, -18, 6, -13, -109, 48, 31, 6, 3, 85, 29, 35, 4, 24, 48, 22, -128, 20, -29, 63, 48, -79, -113, -13, 26, 85, 22, -27, 93, -5, 122, -103, -109, 14, -18, 6, -13, -109, 48, 12, 6, 3, 85, 29, 19, 4, 5, 48, 3, 1, 1, -1, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 5, 5, 0, 3, -127, -127, 0, -32, -74, 55, -125, -58, -128, 15, -62, 100, -60, 3, -86, 81, 112, -61, -56, -69, -126, 8, 99, -100, -38, -108, -56, -122, 125, 19, -64, -61, 90, 85, -47, -8, -123, -103, 105, 77, -32, -65, -62, -28, 67, -28, -78, 116, -49, 120, -2, 33, 13, 47, 46, -5, -112, 3, -101, -125, -115, 92, -124, 58, 80, 107, -67, 82, 6, -63, 39, -90, -1, 85, -58, 82, -115, 119, 13, -4, -32, 0, -98, 100, -41, 94, -75, 75, -103, 126, -80, 85, 40, -27, 60, 105, 28, -27, -21, -15, -98, 103, -88, -109, 35, -119, -27, -26, -122, 113, 63, 35, -33, 70, 23, 33, -23, 66, 108, 56, 112, 46, -85, -123, -123, 33, 118, 27, 96, -7, -103} // fill-array
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x003b, all -> 0x0043 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x003b, all -> 0x0043 }
            java.lang.String r1 = "X.509"
            java.security.cert.CertificateFactory r1 = java.security.cert.CertificateFactory.getInstance(r1)     // Catch:{ Throwable -> 0x004e }
            java.lang.String r3 = "RSA"
            java.security.KeyFactory r3 = java.security.KeyFactory.getInstance(r3)     // Catch:{ Throwable -> 0x004e }
            java.security.cert.Certificate r1 = r1.generateCertificate(r2)     // Catch:{ Throwable -> 0x004e }
            if (r1 == 0) goto L_0x0021
            if (r3 != 0) goto L_0x0027
        L_0x0021:
            if (r2 == 0) goto L_0x0026
        L_0x0023:
            r2.close()
        L_0x0026:
            return r0
        L_0x0027:
            java.security.spec.X509EncodedKeySpec r4 = new java.security.spec.X509EncodedKeySpec     // Catch:{ Throwable -> 0x004e }
            java.security.PublicKey r1 = r1.getPublicKey()     // Catch:{ Throwable -> 0x004e }
            byte[] r1 = r1.getEncoded()     // Catch:{ Throwable -> 0x004e }
            r4.<init>(r1)     // Catch:{ Throwable -> 0x004e }
            java.security.PublicKey r0 = r3.generatePublic(r4)     // Catch:{ Throwable -> 0x004e }
            if (r2 == 0) goto L_0x0026
            goto L_0x0023
        L_0x003b:
            r1 = move-exception
            r2 = r0
        L_0x003d:
            r1.printStackTrace()     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x0026
            goto L_0x0023
        L_0x0043:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0046:
            if (r2 == 0) goto L_0x004b
            r2.close()
        L_0x004b:
            throw r0
        L_0x004c:
            r0 = move-exception
            goto L_0x0046
        L_0x004e:
            r1 = move-exception
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.w.a(android.content.Context):java.security.PublicKey");
    }

    static String b(byte[] bArr) {
        try {
            return c(bArr);
        } catch (Throwable th) {
            y.a(th, "Utils", "HexString");
            return null;
        }
    }

    public static String c(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & BLEServiceApi.CONNECTED_STATUS);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x002e A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0033 A[SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:19:0x002a=Splitter:B:19:0x002a, B:31:0x003c=Splitter:B:31:0x003c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] d(byte[] r4) throws java.io.IOException, java.lang.Throwable {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0025, Throwable -> 0x0037, all -> 0x0043 }
            r2.<init>()     // Catch:{ IOException -> 0x0025, Throwable -> 0x0037, all -> 0x0043 }
            java.util.zip.GZIPOutputStream r1 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0055, Throwable -> 0x004e, all -> 0x0049 }
            r1.<init>(r2)     // Catch:{ IOException -> 0x0055, Throwable -> 0x004e, all -> 0x0049 }
            r1.write(r4)     // Catch:{ IOException -> 0x005a, Throwable -> 0x0053 }
            r1.finish()     // Catch:{ IOException -> 0x005a, Throwable -> 0x0053 }
            byte[] r0 = r2.toByteArray()     // Catch:{ IOException -> 0x005a, Throwable -> 0x0053 }
            if (r1 == 0) goto L_0x001d
            r1.close()     // Catch:{ Throwable -> 0x0041 }
        L_0x001d:
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ Throwable -> 0x0023 }
            goto L_0x0003
        L_0x0023:
            r0 = move-exception
            throw r0
        L_0x0025:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r0 = r1
            r1 = r3
        L_0x002a:
            throw r0     // Catch:{ all -> 0x002b }
        L_0x002b:
            r0 = move-exception
        L_0x002c:
            if (r1 == 0) goto L_0x0031
            r1.close()     // Catch:{ Throwable -> 0x003d }
        L_0x0031:
            if (r2 == 0) goto L_0x0036
            r2.close()     // Catch:{ Throwable -> 0x003f }
        L_0x0036:
            throw r0
        L_0x0037:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r0 = r1
            r1 = r3
        L_0x003c:
            throw r0     // Catch:{ all -> 0x002b }
        L_0x003d:
            r0 = move-exception
            throw r0
        L_0x003f:
            r0 = move-exception
            throw r0
        L_0x0041:
            r0 = move-exception
            throw r0
        L_0x0043:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r0 = r1
            r1 = r3
            goto L_0x002c
        L_0x0049:
            r1 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
            goto L_0x002c
        L_0x004e:
            r1 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
            goto L_0x003c
        L_0x0053:
            r0 = move-exception
            goto L_0x003c
        L_0x0055:
            r1 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
            goto L_0x002a
        L_0x005a:
            r0 = move-exception
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.w.d(byte[]):byte[]");
    }
}

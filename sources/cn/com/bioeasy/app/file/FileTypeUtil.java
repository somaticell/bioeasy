package cn.com.bioeasy.app.file;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.util.HashMap;

public class FileTypeUtil {
    public static final HashMap<String, String> mFileTypes = new HashMap<>();

    static {
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("89504E", "png");
        mFileTypes.put("47494638", "gif");
    }

    public static String getFileType(String filePath) {
        return mFileTypes.get(getFileHeader(filePath));
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0029 A[SYNTHETIC, Splitter:B:20:0x0029] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getFileHeader(java.lang.String r6) {
        /*
            r1 = 0
            r3 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x001d, all -> 0x0026 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x001d, all -> 0x0026 }
            r4 = 3
            byte[] r0 = new byte[r4]     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            r4 = 0
            int r5 = r0.length     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            r2.read(r0, r4, r5)     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            java.lang.String r3 = bytesToHexString(r0)     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            if (r2 == 0) goto L_0x0035
            r2.close()     // Catch:{ IOException -> 0x001a }
            r1 = r2
        L_0x0019:
            return r3
        L_0x001a:
            r4 = move-exception
            r1 = r2
            goto L_0x0019
        L_0x001d:
            r4 = move-exception
        L_0x001e:
            if (r1 == 0) goto L_0x0019
            r1.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x0019
        L_0x0024:
            r4 = move-exception
            goto L_0x0019
        L_0x0026:
            r4 = move-exception
        L_0x0027:
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ IOException -> 0x002d }
        L_0x002c:
            throw r4
        L_0x002d:
            r5 = move-exception
            goto L_0x002c
        L_0x002f:
            r4 = move-exception
            r1 = r2
            goto L_0x0027
        L_0x0032:
            r4 = move-exception
            r1 = r2
            goto L_0x001e
        L_0x0035:
            r1 = r2
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.file.FileTypeUtil.getFileHeader(java.lang.String):java.lang.String");
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & BLEServiceApi.CONNECTED_STATUS).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}

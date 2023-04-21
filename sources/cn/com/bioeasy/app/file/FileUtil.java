package cn.com.bioeasy.app.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.text.TextUtils;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class FileUtil {
    public static final String ASSETS_PREFIX = "file://android_assets/";
    public static final String ASSETS_PREFIX2 = "file://android_asset/";
    public static final String ASSETS_PREFIX3 = "assets://";
    public static final String ASSETS_PREFIX4 = "asset://";
    public static final String DRAWABLE_PREFIX = "drawable://";
    public static final String FILE_PREFIX = "file://";
    public static String FILE_READING_ENCODING = "UTF-8";
    public static String FILE_WRITING_ENCODING = "UTF-8";
    public static final String RAW_PREFIX = "file://android_raw/";
    public static final String RAW_PREFIX2 = "raw://";
    public static final HashMap<String, String> mFileTypes = new HashMap<>();

    public static InputStream getStream(Context context, String url) throws IOException {
        String lowerUrl = url.toLowerCase();
        if (lowerUrl.startsWith("file://android_assets/")) {
            return getAssetsStream(context, url.substring("file://android_assets/".length()));
        }
        if (lowerUrl.startsWith("file://android_asset/")) {
            return getAssetsStream(context, url.substring("file://android_asset/".length()));
        }
        if (lowerUrl.startsWith("assets://")) {
            return getAssetsStream(context, url.substring("assets://".length()));
        }
        if (lowerUrl.startsWith("asset://")) {
            return getAssetsStream(context, url.substring("asset://".length()));
        }
        if (lowerUrl.startsWith("file://android_raw/")) {
            return getRawStream(context, url.substring("file://android_raw/".length()));
        }
        if (lowerUrl.startsWith("raw://")) {
            return getRawStream(context, url.substring("raw://".length()));
        }
        if (lowerUrl.startsWith("file://")) {
            return getFileStream(url.substring("file://".length()));
        }
        if (lowerUrl.startsWith("drawable://")) {
            return getDrawableStream(context, url.substring("drawable://".length()));
        }
        throw new IllegalArgumentException(String.format("Unsupported url: %s \nSupported: \n%sxxx\n%sxxx\n%sxxx", new Object[]{url, "file://android_assets/", "file://android_raw/", "file://"}));
    }

    private static InputStream getAssetsStream(Context context, String path) throws IOException {
        return context.getAssets().open(path);
    }

    private static InputStream getFileStream(String path) throws IOException {
        return new FileInputStream(path);
    }

    private static InputStream getRawStream(Context context, String rawName) throws IOException {
        int id = context.getResources().getIdentifier(rawName, "raw", context.getPackageName());
        if (id != 0) {
            try {
                return context.getResources().openRawResource(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IOException(String.format("raw of id: %s from %s not found", new Object[]{Integer.valueOf(id), rawName}));
    }

    private static InputStream getDrawableStream(Context context, String rawName) throws IOException {
        int id = context.getResources().getIdentifier(rawName, "drawable", context.getPackageName());
        if (id != 0) {
            Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        throw new IOException(String.format("bitmap of id: %s from %s not found", new Object[]{Integer.valueOf(id), rawName}));
    }

    public static String getString(Context context, String url) throws IOException {
        return getString(context, url, "UTF-8");
    }

    public static String getString(Context context, String url, String encoding) throws IOException {
        String result = readStreamString(getStream(context, url), encoding);
        if (result.startsWith("﻿")) {
            return result.substring(1);
        }
        return result;
    }

    public static String readStreamString(InputStream is, String encoding) throws IOException {
        return new String(readStream(is), encoding);
    }

    public static byte[] readStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        while (true) {
            int readlen = is.read(buf);
            if (readlen >= 0) {
                baos.write(buf, 0, readlen);
            } else {
                baos.close();
                return baos.toByteArray();
            }
        }
    }

    public static byte[] readFileBytes(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] result = new byte[((int) file.length())];
        InputStream input = null;
        int totalBytesRead = 0;
        try {
            InputStream input2 = new BufferedInputStream(new FileInputStream(file));
            while (totalBytesRead < result.length) {
                try {
                    int bytesRead = input2.read(result, totalBytesRead, result.length - totalBytesRead);
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                    }
                } catch (Throwable th) {
                    th = th;
                    input = input2;
                    input.close();
                    throw th;
                }
            }
            input2.close();
            return result;
        } catch (Throwable th2) {
            th = th2;
            input.close();
            throw th;
        }
    }

    public static Bitmap getDrawableBitmap(Context context, String rawName) {
        BitmapDrawable drawable;
        int id = context.getResources().getIdentifier(rawName, "drawable", context.getPackageName());
        if (id == 0 || (drawable = (BitmapDrawable) context.getResources().getDrawable(id)) == null) {
            return null;
        }
        return drawable.getBitmap();
    }

    public static HashMap<String, String> simpleProperty2HashMap(Context context, String path) {
        try {
            return simpleProperty2HashMap(getStream(context, path));
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private static HashMap<String, String> simpleProperty2HashMap(InputStream in) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        for (String key : properties.keySet()) {
            hashMap.put(key, (String) properties.get(key));
        }
        return hashMap;
    }

    public static void copyRawFile(Context ctx, String rawFileName, String to) {
        String[] names = rawFileName.split("\\.");
        String toFile = to + "/" + names[0] + "." + names[1];
        if (!new File(toFile).exists()) {
            try {
                InputStream is = getStream(ctx, "raw://" + names[0]);
                OutputStream os = new FileOutputStream(toFile);
                byte[] bytes = new byte[1024];
                while (true) {
                    int byteCount = is.read(bytes);
                    if (byteCount != -1) {
                        os.write(bytes, 0, byteCount);
                    } else {
                        os.close();
                        is.close();
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static {
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("89504E", "png");
        mFileTypes.put("47494638", "gif");
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x006b A[SYNTHETIC, Splitter:B:43:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0070 A[Catch:{ Exception -> 0x007e }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:49:0x0075=Splitter:B:49:0x0075, B:38:0x005f=Splitter:B:38:0x005f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFile(java.lang.String r11, java.lang.String r12) throws java.lang.Exception {
        /*
            r0 = 0
            r5 = 0
            r2 = 0
            if (r12 == 0) goto L_0x000d
            java.lang.String r9 = ""
            boolean r9 = r9.equals(r12)
            if (r9 == 0) goto L_0x000f
        L_0x000d:
            java.lang.String r12 = FILE_READING_ENCODING
        L_0x000f:
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x005e, IOException -> 0x0074 }
            r6.<init>(r11)     // Catch:{ FileNotFoundException -> 0x005e, IOException -> 0x0074 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x009b, IOException -> 0x008f, all -> 0x0083 }
            java.io.InputStreamReader r9 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x009b, IOException -> 0x008f, all -> 0x0083 }
            r9.<init>(r6, r12)     // Catch:{ FileNotFoundException -> 0x009b, IOException -> 0x008f, all -> 0x0083 }
            r3.<init>(r9)     // Catch:{ FileNotFoundException -> 0x009b, IOException -> 0x008f, all -> 0x0083 }
            java.lang.String r9 = "UTF-8"
            boolean r8 = r9.equalsIgnoreCase(r12)     // Catch:{ FileNotFoundException -> 0x009e, IOException -> 0x0092, all -> 0x0086 }
            r1 = r0
        L_0x0025:
            java.lang.String r7 = r3.readLine()     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x008a }
            if (r7 == 0) goto L_0x0045
            if (r1 != 0) goto L_0x003e
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x008a }
            r0.<init>()     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x008a }
        L_0x0032:
            if (r8 == 0) goto L_0x0039
            java.lang.String r7 = removeBomHeaderIfExists(r7)     // Catch:{ FileNotFoundException -> 0x009e, IOException -> 0x0092, all -> 0x0086 }
            r8 = 0
        L_0x0039:
            r0.append(r7)     // Catch:{ FileNotFoundException -> 0x009e, IOException -> 0x0092, all -> 0x0086 }
            r1 = r0
            goto L_0x0025
        L_0x003e:
            java.lang.String r9 = "\n"
            r1.append(r9)     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x008a }
            r0 = r1
            goto L_0x0032
        L_0x0045:
            if (r1 != 0) goto L_0x0054
            java.lang.String r9 = ""
        L_0x0049:
            if (r3 == 0) goto L_0x004e
            r3.close()     // Catch:{ Exception -> 0x0059 }
        L_0x004e:
            if (r6 == 0) goto L_0x0053
            r6.close()     // Catch:{ Exception -> 0x0059 }
        L_0x0053:
            return r9
        L_0x0054:
            java.lang.String r9 = r1.toString()     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x008a }
            goto L_0x0049
        L_0x0059:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x0053
        L_0x005e:
            r4 = move-exception
        L_0x005f:
            java.lang.Exception r9 = new java.lang.Exception     // Catch:{ all -> 0x0068 }
            java.lang.String r10 = "要读取的文件没有找到!"
            r9.<init>(r10, r4)     // Catch:{ all -> 0x0068 }
            throw r9     // Catch:{ all -> 0x0068 }
        L_0x0068:
            r9 = move-exception
        L_0x0069:
            if (r2 == 0) goto L_0x006e
            r2.close()     // Catch:{ Exception -> 0x007e }
        L_0x006e:
            if (r5 == 0) goto L_0x0073
            r5.close()     // Catch:{ Exception -> 0x007e }
        L_0x0073:
            throw r9
        L_0x0074:
            r4 = move-exception
        L_0x0075:
            java.lang.Exception r9 = new java.lang.Exception     // Catch:{ all -> 0x0068 }
            java.lang.String r10 = "读取文件时错误!"
            r9.<init>(r10, r4)     // Catch:{ all -> 0x0068 }
            throw r9     // Catch:{ all -> 0x0068 }
        L_0x007e:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x0073
        L_0x0083:
            r9 = move-exception
            r5 = r6
            goto L_0x0069
        L_0x0086:
            r9 = move-exception
            r2 = r3
            r5 = r6
            goto L_0x0069
        L_0x008a:
            r9 = move-exception
            r2 = r3
            r5 = r6
            r0 = r1
            goto L_0x0069
        L_0x008f:
            r4 = move-exception
            r5 = r6
            goto L_0x0075
        L_0x0092:
            r4 = move-exception
            r2 = r3
            r5 = r6
            goto L_0x0075
        L_0x0096:
            r4 = move-exception
            r2 = r3
            r5 = r6
            r0 = r1
            goto L_0x0075
        L_0x009b:
            r4 = move-exception
            r5 = r6
            goto L_0x005f
        L_0x009e:
            r4 = move-exception
            r2 = r3
            r5 = r6
            goto L_0x005f
        L_0x00a2:
            r4 = move-exception
            r2 = r3
            r5 = r6
            r0 = r1
            goto L_0x005f
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.file.FileUtil.readFile(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0081 A[SYNTHETIC, Splitter:B:27:0x0081] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0086 A[Catch:{ Exception -> 0x00b9 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File writeFile(java.io.InputStream r14, java.lang.String r15, boolean r16) throws java.lang.Exception {
        /*
            java.lang.String r8 = extractFilePath(r15)
            boolean r10 = pathExists(r8)
            if (r10 != 0) goto L_0x000e
            r10 = 1
            makeDir(r8, r10)
        L_0x000e:
            if (r16 != 0) goto L_0x0052
            boolean r10 = fileExists(r15)
            if (r10 == 0) goto L_0x0052
            java.lang.String r10 = "."
            boolean r10 = r15.contains(r10)
            if (r10 == 0) goto L_0x008a
            java.lang.String r10 = "."
            int r10 = r15.lastIndexOf(r10)
            java.lang.String r9 = r15.substring(r10)
            r10 = 0
            java.lang.String r11 = "."
            int r11 = r15.lastIndexOf(r11)
            java.lang.String r7 = r15.substring(r10, r11)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r10 = r10.append(r7)
            java.lang.String r11 = "_"
            java.lang.StringBuilder r10 = r10.append(r11)
            long r12 = cn.com.bioeasy.app.utils.DateUtil.getNowTime()
            java.lang.StringBuilder r10 = r10.append(r12)
            java.lang.StringBuilder r10 = r10.append(r9)
            java.lang.String r15 = r10.toString()
        L_0x0052:
            r5 = 0
            r3 = 0
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00c5 }
            r4.<init>(r15)     // Catch:{ Exception -> 0x00c5 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00c7, all -> 0x00be }
            r6.<init>(r4)     // Catch:{ Exception -> 0x00c7, all -> 0x00be }
            r0 = 0
            r10 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r10]     // Catch:{ Exception -> 0x006f, all -> 0x00c1 }
        L_0x0063:
            int r0 = r14.read(r1)     // Catch:{ Exception -> 0x006f, all -> 0x00c1 }
            r10 = -1
            if (r0 == r10) goto L_0x00a6
            r10 = 0
            r6.write(r1, r10, r0)     // Catch:{ Exception -> 0x006f, all -> 0x00c1 }
            goto L_0x0063
        L_0x006f:
            r2 = move-exception
            r3 = r4
            r5 = r6
        L_0x0072:
            r2.printStackTrace()     // Catch:{ all -> 0x007e }
            java.lang.Exception r10 = new java.lang.Exception     // Catch:{ all -> 0x007e }
            java.lang.String r11 = "写文件错误"
            r10.<init>(r11, r2)     // Catch:{ all -> 0x007e }
            throw r10     // Catch:{ all -> 0x007e }
        L_0x007e:
            r10 = move-exception
        L_0x007f:
            if (r5 == 0) goto L_0x0084
            r5.close()     // Catch:{ Exception -> 0x00b9 }
        L_0x0084:
            if (r14 == 0) goto L_0x0089
            r14.close()     // Catch:{ Exception -> 0x00b9 }
        L_0x0089:
            throw r10
        L_0x008a:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r10 = r10.append(r15)
            java.lang.String r11 = "_"
            java.lang.StringBuilder r10 = r10.append(r11)
            long r12 = cn.com.bioeasy.app.utils.DateUtil.getNowTime()
            java.lang.StringBuilder r10 = r10.append(r12)
            java.lang.String r15 = r10.toString()
            goto L_0x0052
        L_0x00a6:
            r6.flush()     // Catch:{ Exception -> 0x006f, all -> 0x00c1 }
            if (r6 == 0) goto L_0x00ae
            r6.close()     // Catch:{ Exception -> 0x00b4 }
        L_0x00ae:
            if (r14 == 0) goto L_0x00b3
            r14.close()     // Catch:{ Exception -> 0x00b4 }
        L_0x00b3:
            return r4
        L_0x00b4:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00b3
        L_0x00b9:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0089
        L_0x00be:
            r10 = move-exception
            r3 = r4
            goto L_0x007f
        L_0x00c1:
            r10 = move-exception
            r3 = r4
            r5 = r6
            goto L_0x007f
        L_0x00c5:
            r2 = move-exception
            goto L_0x0072
        L_0x00c7:
            r2 = move-exception
            r3 = r4
            goto L_0x0072
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.file.FileUtil.writeFile(java.io.InputStream, java.lang.String, boolean):java.io.File");
    }

    public static File writeFile(String path, String content, String encoding, boolean isOverride) throws Exception {
        if (TextUtils.isEmpty(encoding)) {
            encoding = FILE_WRITING_ENCODING;
        }
        return writeFile(new ByteArrayInputStream(content.getBytes(encoding)), path, isOverride);
    }

    public static String extractFilePath(String _sFilePathName) {
        int nPos = _sFilePathName.lastIndexOf(47);
        if (nPos < 0) {
            nPos = _sFilePathName.lastIndexOf(92);
        }
        return nPos >= 0 ? _sFilePathName.substring(0, nPos + 1) : "";
    }

    public static String extractFileName(String _sFilePathName) {
        return extractFileName(_sFilePathName, File.separator);
    }

    public static String extractFileName(String _sFilePathName, String _sFileSeparator) {
        int nPos;
        int i = 47;
        if (_sFileSeparator == null) {
            nPos = _sFilePathName.lastIndexOf(File.separatorChar);
            if (nPos < 0) {
                if (File.separatorChar == '/') {
                    i = 92;
                }
                nPos = _sFilePathName.lastIndexOf(i);
            }
        } else {
            nPos = _sFilePathName.lastIndexOf(_sFileSeparator);
        }
        return nPos < 0 ? _sFilePathName : _sFilePathName.substring(nPos + 1);
    }

    public static boolean pathExists(String _sPathFileName) {
        return fileExists(extractFilePath(_sPathFileName));
    }

    public static boolean fileExists(String _sPathFileName) {
        return new File(_sPathFileName).exists();
    }

    public static boolean makeDir(String _sDir, boolean _bCreateParentDir) {
        boolean zResult;
        File file = new File(_sDir);
        if (_bCreateParentDir) {
            zResult = file.mkdirs();
        } else {
            zResult = file.mkdir();
        }
        if (!zResult) {
            return file.exists();
        }
        return zResult;
    }

    private static String removeBomHeaderIfExists(String _sLine) {
        if (_sLine == null) {
            return null;
        }
        String line = _sLine;
        if (line.length() <= 0) {
            return line;
        }
        char ch = line.charAt(0);
        while (true) {
            if (ch != 65279 && ch != 65534) {
                return line;
            }
            line = line.substring(1);
            if (line.length() == 0) {
                return line;
            }
            ch = line.charAt(0);
        }
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
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.file.FileUtil.getFileHeader(java.lang.String):java.lang.String");
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

    public static File getDiskCacheDir(Context context) {
        String cachePath;
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator);
    }

    public static File getDataFileDir(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + File.separator);
    }
}

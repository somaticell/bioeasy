package net.oschina.common.net;

import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import net.oschina.common.net.io.IOParam;
import net.oschina.common.net.io.StrParam;
import net.oschina.common.utils.Logger;

public final class Util {
    private static final String LOG_TAG = "QOK";

    public static <T> T[] listToParams(List<T> params, Class<T> tClass) {
        if (params == null || params.size() == 0) {
            return (Object[]) Array.newInstance(tClass, 0);
        }
        try {
            return params.toArray((Object[]) Array.newInstance(tClass, params.size()));
        } catch (Exception e) {
            e.printStackTrace();
            return (Object[]) Array.newInstance(tClass, 0);
        }
    }

    public static StrParam[] mapToStringParams(Map<String, String> params) {
        if (params == null) {
            return new StrParam[0];
        }
        int size = params.size();
        if (size == 0) {
            return new StrParam[0];
        }
        StrParam[] res = new StrParam[size];
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            res[i] = new StrParam(entry.getKey(), entry.getValue());
            i++;
        }
        return res;
    }

    public static IOParam[] mapToFileParams(Map<String, File> params) {
        if (params == null) {
            return new IOParam[0];
        }
        int size = params.size();
        if (size == 0) {
            return new IOParam[0];
        }
        IOParam[] res = new IOParam[size];
        int i = 0;
        for (Map.Entry<String, File> entry : params.entrySet()) {
            res[i] = new IOParam(entry.getKey(), entry.getValue());
            i++;
        }
        return res;
    }

    public static String getFileMimeType(String path) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(path);
        if (contentTypeFor == null) {
            return "application/octet-stream";
        }
        return contentTypeFor;
    }

    public static File getFile(String fileDir, String fileName, String url) {
        if (TextUtils.isEmpty(fileDir)) {
            throw new NullPointerException("File Dir is not null.");
        }
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (TextUtils.isEmpty(fileName)) {
            int separatorIndex = url.lastIndexOf("/");
            fileName = separatorIndex < 0 ? url : url.substring(separatorIndex + 1, url.length());
            if (TextUtils.isEmpty(fileName) || !fileName.contains(".")) {
                fileName = String.valueOf(System.currentTimeMillis()) + ".cache";
            }
        }
        return new File(dir, fileName);
    }

    public static File makeFile(File file) {
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void log(String msg) {
        if (Logger.DEBUG && !TextUtils.isEmpty(msg)) {
            Log.d(LOG_TAG, msg);
        }
    }

    public static void log(String fromat, Object... strs) {
        if (Logger.DEBUG && !TextUtils.isEmpty(fromat) && strs != null) {
            Log.d(LOG_TAG, String.format(fromat, strs));
        }
    }

    public static void log(String msg, Throwable tr) {
        if (Logger.DEBUG && !TextUtils.isEmpty(msg)) {
            Log.d(LOG_TAG, msg, tr);
        }
    }

    public static void exception(Exception e) {
        if (Logger.DEBUG && e != null) {
            e.printStackTrace();
        }
    }
}

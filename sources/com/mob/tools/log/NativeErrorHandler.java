package com.mob.tools.log;

import android.content.Context;
import cn.com.bioeasy.app.utils.ListUtils;
import com.mob.tools.MobLog;
import com.mob.tools.utils.ResHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class NativeErrorHandler {
    private static final boolean ENABLE;
    private static final int MAX_LOG_SIZE = 100;

    private static native void nativePrepare(String str);

    static {
        boolean loaded = false;
        try {
            System.loadLibrary("neh");
            loaded = true;
        } catch (Throwable th) {
        }
        ENABLE = loaded;
    }

    public static boolean prepare(Context context) {
        if (ENABLE) {
            String path = getCachePath(context);
            uploadCreashLog(path);
            nativePrepare(path);
        }
        return ENABLE;
    }

    private static String getCachePath(Context context) {
        File file = new File(ResHelper.getCacheRoot(context), "NativeCrashLogs");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private static void uploadCreashLog(String path) {
        try {
            Iterator<NativeCrashInfo> it = parseIndex(path).iterator();
            while (it.hasNext()) {
                MobLog.getInstance().nativeCrashLog(parseLog(path, it.next()));
            }
            ResHelper.deleteFileAndFolder(new File(path));
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
        }
    }

    private static ArrayList<NativeCrashInfo> parseIndex(String path) throws Throwable {
        File indexFile = new File(path, ".ncl");
        if (!indexFile.exists()) {
            return new ArrayList<>();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(indexFile)));
        ArrayList<NativeCrashInfo> infos = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            String[] i = line.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            if (i.length >= 2) {
                NativeCrashInfo info = new NativeCrashInfo();
                info.time = ResHelper.parseLong(i[0]);
                info.signal = ResHelper.parseInt(i[1]);
                infos.add(info);
                line = br.readLine();
            }
        }
        br.close();
        return infos;
    }

    private static String parseLog(String path, NativeCrashInfo info) throws Throwable {
        File logFile = new File(path, "." + info.time);
        if (!logFile.exists()) {
            return "";
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
        LinkedList<String> logs = new LinkedList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            logs.add(line);
            if (logs.size() > 100) {
                logs.remove(0);
            }
        }
        br.close();
        StringBuffer sb = new StringBuffer();
        Iterator it = logs.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next()).append(10);
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }

    private static class NativeCrashInfo {
        public int signal;
        public long time;

        private NativeCrashInfo() {
        }
    }
}

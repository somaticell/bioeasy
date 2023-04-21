package com.mob.commons;

import com.mob.tools.MobLog;
import com.mob.tools.utils.FileLocker;
import java.io.File;

/* compiled from: Locks */
public class d {
    public static final void a(File file, LockAction lockAction) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileLocker fileLocker = new FileLocker();
            fileLocker.setLockFile(file.getAbsolutePath());
            if (fileLocker.lock(true) && !lockAction.run(fileLocker)) {
                fileLocker.release();
            }
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }
}

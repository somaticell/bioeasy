package com.facebook.cache.disk;

import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.common.file.FileTree;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

public class DefaultDiskStorageSupplier implements DiskStorageSupplier {
    private static final Class<?> TAG = DefaultDiskStorageSupplier.class;
    private final String mBaseDirectoryName;
    private final Supplier<File> mBaseDirectoryPathSupplier;
    private final CacheErrorLogger mCacheErrorLogger;
    @VisibleForTesting
    volatile State mCurrentState = new State((File) null, (DiskStorage) null);
    private final int mVersion;

    @VisibleForTesting
    static class State {
        @Nullable
        public final File rootDirectory;
        @Nullable
        public final DiskStorage storage;

        @VisibleForTesting
        State(@Nullable File rootDirectory2, @Nullable DiskStorage storage2) {
            this.storage = storage2;
            this.rootDirectory = rootDirectory2;
        }
    }

    public DefaultDiskStorageSupplier(int version, Supplier<File> baseDirectoryPathSupplier, String baseDirectoryName, CacheErrorLogger cacheErrorLogger) {
        this.mVersion = version;
        this.mCacheErrorLogger = cacheErrorLogger;
        this.mBaseDirectoryPathSupplier = baseDirectoryPathSupplier;
        this.mBaseDirectoryName = baseDirectoryName;
    }

    public synchronized DiskStorage get() throws IOException {
        if (shouldCreateNewStorage()) {
            deleteOldStorageIfNecessary();
            createStorage();
        }
        return (DiskStorage) Preconditions.checkNotNull(this.mCurrentState.storage);
    }

    private boolean shouldCreateNewStorage() {
        State currentState = this.mCurrentState;
        return currentState.storage == null || currentState.rootDirectory == null || !currentState.rootDirectory.exists();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void deleteOldStorageIfNecessary() {
        if (this.mCurrentState.storage != null && this.mCurrentState.rootDirectory != null) {
            FileTree.deleteRecursively(this.mCurrentState.rootDirectory);
        }
    }

    private void createStorage() throws IOException {
        File rootDirectory = new File(this.mBaseDirectoryPathSupplier.get(), this.mBaseDirectoryName);
        createRootDirectoryIfNecessary(rootDirectory);
        this.mCurrentState = new State(rootDirectory, new DefaultDiskStorage(rootDirectory, this.mVersion, this.mCacheErrorLogger));
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void createRootDirectoryIfNecessary(File rootDirectory) throws IOException {
        try {
            FileUtils.mkdirs(rootDirectory);
            FLog.d(TAG, "Created cache directory %s", (Object) rootDirectory.getAbsolutePath());
        } catch (FileUtils.CreateDirectoryException cde) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, "createRootDirectoryIfNecessary", cde);
            throw cde;
        }
    }
}

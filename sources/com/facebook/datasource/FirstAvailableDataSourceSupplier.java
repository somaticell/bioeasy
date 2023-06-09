package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class FirstAvailableDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    /* access modifiers changed from: private */
    public final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    private FirstAvailableDataSourceSupplier(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.mDataSourceSuppliers = dataSourceSuppliers;
    }

    public static <T> FirstAvailableDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        return new FirstAvailableDataSourceSupplier<>(dataSourceSuppliers);
    }

    public DataSource<T> get() {
        return new FirstAvailableDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FirstAvailableDataSourceSupplier)) {
            return false;
        }
        return Objects.equal(this.mDataSourceSuppliers, ((FirstAvailableDataSourceSupplier) other).mDataSourceSuppliers);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("list", (Object) this.mDataSourceSuppliers).toString();
    }

    @ThreadSafe
    private class FirstAvailableDataSource extends AbstractDataSource<T> {
        private DataSource<T> mCurrentDataSource = null;
        private DataSource<T> mDataSourceWithResult = null;
        private int mIndex = 0;

        public FirstAvailableDataSource() {
            if (!startNextDataSource()) {
                setFailure(new RuntimeException("No data source supplier or supplier returned null."));
            }
        }

        @Nullable
        public synchronized T getResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null ? dataSourceWithResult.getResult() : null;
        }

        public synchronized boolean hasResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null && dataSourceWithResult.hasResult();
        }

        public boolean close() {
            synchronized (this) {
                if (!super.close()) {
                    return false;
                }
                DataSource<T> currentDataSource = this.mCurrentDataSource;
                this.mCurrentDataSource = null;
                DataSource<T> dataSourceWithResult = this.mDataSourceWithResult;
                this.mDataSourceWithResult = null;
                closeSafely(dataSourceWithResult);
                closeSafely(currentDataSource);
                return true;
            }
        }

        private boolean startNextDataSource() {
            DataSource<T> dataSource;
            Supplier<DataSource<T>> dataSourceSupplier = getNextSupplier();
            if (dataSourceSupplier != null) {
                dataSource = dataSourceSupplier.get();
            } else {
                dataSource = null;
            }
            if (!setCurrentDataSource(dataSource) || dataSource == null) {
                closeSafely(dataSource);
                return false;
            }
            dataSource.subscribe(new InternalDataSubscriber(), CallerThreadExecutor.getInstance());
            return true;
        }

        @Nullable
        private synchronized Supplier<DataSource<T>> getNextSupplier() {
            Supplier<DataSource<T>> supplier;
            if (isClosed() || this.mIndex >= FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers.size()) {
                supplier = null;
            } else {
                List access$100 = FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers;
                int i = this.mIndex;
                this.mIndex = i + 1;
                supplier = (Supplier) access$100.get(i);
            }
            return supplier;
        }

        private synchronized boolean setCurrentDataSource(DataSource<T> dataSource) {
            boolean z;
            if (isClosed()) {
                z = false;
            } else {
                this.mCurrentDataSource = dataSource;
                z = true;
            }
            return z;
        }

        private synchronized boolean clearCurrentDataSource(DataSource<T> dataSource) {
            boolean z;
            if (isClosed() || dataSource != this.mCurrentDataSource) {
                z = false;
            } else {
                this.mCurrentDataSource = null;
                z = true;
            }
            return z;
        }

        @Nullable
        private synchronized DataSource<T> getDataSourceWithResult() {
            return this.mDataSourceWithResult;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
            closeSafely(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void maybeSetDataSourceWithResult(com.facebook.datasource.DataSource<T> r3, boolean r4) {
            /*
                r2 = this;
                r0 = 0
                monitor-enter(r2)
                com.facebook.datasource.DataSource<T> r1 = r2.mCurrentDataSource     // Catch:{ all -> 0x001b }
                if (r3 != r1) goto L_0x000a
                com.facebook.datasource.DataSource<T> r1 = r2.mDataSourceWithResult     // Catch:{ all -> 0x001b }
                if (r3 != r1) goto L_0x000c
            L_0x000a:
                monitor-exit(r2)     // Catch:{ all -> 0x001b }
            L_0x000b:
                return
            L_0x000c:
                com.facebook.datasource.DataSource<T> r1 = r2.mDataSourceWithResult     // Catch:{ all -> 0x001b }
                if (r1 == 0) goto L_0x0012
                if (r4 == 0) goto L_0x0016
            L_0x0012:
                com.facebook.datasource.DataSource<T> r0 = r2.mDataSourceWithResult     // Catch:{ all -> 0x001b }
                r2.mDataSourceWithResult = r3     // Catch:{ all -> 0x001b }
            L_0x0016:
                monitor-exit(r2)     // Catch:{ all -> 0x001b }
                r2.closeSafely(r0)
                goto L_0x000b
            L_0x001b:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x001b }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.FirstAvailableDataSourceSupplier.FirstAvailableDataSource.maybeSetDataSourceWithResult(com.facebook.datasource.DataSource, boolean):void");
        }

        /* access modifiers changed from: private */
        public void onDataSourceFailed(DataSource<T> dataSource) {
            if (clearCurrentDataSource(dataSource)) {
                if (dataSource != getDataSourceWithResult()) {
                    closeSafely(dataSource);
                }
                if (!startNextDataSource()) {
                    setFailure(dataSource.getFailureCause());
                }
            }
        }

        /* access modifiers changed from: private */
        public void onDataSourceNewResult(DataSource<T> dataSource) {
            maybeSetDataSourceWithResult(dataSource, dataSource.isFinished());
            if (dataSource == getDataSourceWithResult()) {
                setResult(null, dataSource.isFinished());
            }
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private InternalDataSubscriber() {
            }

            public void onFailure(DataSource<T> dataSource) {
                FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
            }

            public void onCancellation(DataSource<T> dataSource) {
            }

            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    FirstAvailableDataSource.this.onDataSourceNewResult(dataSource);
                } else if (dataSource.isFinished()) {
                    FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
                }
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
                FirstAvailableDataSource.this.setProgress(Math.max(FirstAvailableDataSource.this.getProgress(), dataSource.getProgress()));
            }
        }
    }
}

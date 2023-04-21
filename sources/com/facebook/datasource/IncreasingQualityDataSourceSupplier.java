package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class IncreasingQualityDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    /* access modifiers changed from: private */
    public final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    private IncreasingQualityDataSourceSupplier(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.mDataSourceSuppliers = dataSourceSuppliers;
    }

    public static <T> IncreasingQualityDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        return new IncreasingQualityDataSourceSupplier<>(dataSourceSuppliers);
    }

    public DataSource<T> get() {
        return new IncreasingQualityDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IncreasingQualityDataSourceSupplier)) {
            return false;
        }
        return Objects.equal(this.mDataSourceSuppliers, ((IncreasingQualityDataSourceSupplier) other).mDataSourceSuppliers);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("list", (Object) this.mDataSourceSuppliers).toString();
    }

    @ThreadSafe
    private class IncreasingQualityDataSource extends AbstractDataSource<T> {
        @GuardedBy("IncreasingQualityDataSource.this")
        @Nullable
        private ArrayList<DataSource<T>> mDataSources;
        @GuardedBy("IncreasingQualityDataSource.this")
        private int mIndexOfDataSourceWithResult;

        public IncreasingQualityDataSource() {
            int n = IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.size();
            this.mIndexOfDataSourceWithResult = n;
            this.mDataSources = new ArrayList<>(n);
            int i = 0;
            while (i < n) {
                DataSource<T> dataSource = (DataSource) ((Supplier) IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.get(i)).get();
                this.mDataSources.add(dataSource);
                dataSource.subscribe(new InternalDataSubscriber(i), CallerThreadExecutor.getInstance());
                if (!dataSource.hasResult()) {
                    i++;
                } else {
                    return;
                }
            }
        }

        @Nullable
        private synchronized DataSource<T> getDataSource(int i) {
            return (this.mDataSources == null || i >= this.mDataSources.size()) ? null : this.mDataSources.get(i);
        }

        @Nullable
        private synchronized DataSource<T> getAndClearDataSource(int i) {
            DataSource<T> dataSource = null;
            synchronized (this) {
                if (this.mDataSources != null && i < this.mDataSources.size()) {
                    dataSource = this.mDataSources.set(i, (Object) null);
                }
            }
            return dataSource;
        }

        @Nullable
        private synchronized DataSource<T> getDataSourceWithResult() {
            return getDataSource(this.mIndexOfDataSourceWithResult);
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

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
            if (r1 >= r0.size()) goto L_0x0028;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
            closeSafely(r0.get(r1));
            r1 = r1 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0028, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0010, code lost:
            if (r0 == null) goto L_0x0028;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            r1 = 0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean close() {
            /*
                r3 = this;
                monitor-enter(r3)
                boolean r2 = super.close()     // Catch:{ all -> 0x0025 }
                if (r2 != 0) goto L_0x000a
                r2 = 0
                monitor-exit(r3)     // Catch:{ all -> 0x0025 }
            L_0x0009:
                return r2
            L_0x000a:
                java.util.ArrayList<com.facebook.datasource.DataSource<T>> r0 = r3.mDataSources     // Catch:{ all -> 0x0025 }
                r2 = 0
                r3.mDataSources = r2     // Catch:{ all -> 0x0025 }
                monitor-exit(r3)     // Catch:{ all -> 0x0025 }
                if (r0 == 0) goto L_0x0028
                r1 = 0
            L_0x0013:
                int r2 = r0.size()
                if (r1 >= r2) goto L_0x0028
                java.lang.Object r2 = r0.get(r1)
                com.facebook.datasource.DataSource r2 = (com.facebook.datasource.DataSource) r2
                r3.closeSafely(r2)
                int r1 = r1 + 1
                goto L_0x0013
            L_0x0025:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0025 }
                throw r2
            L_0x0028:
                r2 = 1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource.close():boolean");
        }

        /* access modifiers changed from: private */
        public void onDataSourceNewResult(int index, DataSource<T> dataSource) {
            maybeSetIndexOfDataSourceWithResult(index, dataSource, dataSource.isFinished());
            if (dataSource == getDataSourceWithResult()) {
                setResult(null, index == 0 && dataSource.isFinished());
            }
        }

        /* access modifiers changed from: private */
        public void onDataSourceFailed(int index, DataSource<T> dataSource) {
            closeSafely(tryGetAndClearDataSource(index, dataSource));
            if (index == 0) {
                setFailure(dataSource.getFailureCause());
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
            r0 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
            if (r0 <= r1) goto L_0x0010;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0024, code lost:
            closeSafely(getAndClearDataSource(r0));
            r0 = r0 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void maybeSetIndexOfDataSourceWithResult(int r5, com.facebook.datasource.DataSource<T> r6, boolean r7) {
            /*
                r4 = this;
                monitor-enter(r4)
                int r2 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x002e }
                int r1 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x002e }
                com.facebook.datasource.DataSource r3 = r4.getDataSource(r5)     // Catch:{ all -> 0x002e }
                if (r6 != r3) goto L_0x000f
                int r3 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x002e }
                if (r5 != r3) goto L_0x0011
            L_0x000f:
                monitor-exit(r4)     // Catch:{ all -> 0x002e }
            L_0x0010:
                return
            L_0x0011:
                com.facebook.datasource.DataSource r3 = r4.getDataSourceWithResult()     // Catch:{ all -> 0x002e }
                if (r3 == 0) goto L_0x001d
                if (r7 == 0) goto L_0x0020
                int r3 = r4.mIndexOfDataSourceWithResult     // Catch:{ all -> 0x002e }
                if (r5 >= r3) goto L_0x0020
            L_0x001d:
                r1 = r5
                r4.mIndexOfDataSourceWithResult = r5     // Catch:{ all -> 0x002e }
            L_0x0020:
                monitor-exit(r4)     // Catch:{ all -> 0x002e }
                r0 = r2
            L_0x0022:
                if (r0 <= r1) goto L_0x0010
                com.facebook.datasource.DataSource r3 = r4.getAndClearDataSource(r0)
                r4.closeSafely(r3)
                int r0 = r0 + -1
                goto L_0x0022
            L_0x002e:
                r3 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x002e }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.IncreasingQualityDataSourceSupplier.IncreasingQualityDataSource.maybeSetIndexOfDataSourceWithResult(int, com.facebook.datasource.DataSource, boolean):void");
        }

        @Nullable
        private synchronized DataSource<T> tryGetAndClearDataSource(int i, DataSource<T> dataSource) {
            if (dataSource == getDataSourceWithResult()) {
                dataSource = null;
            } else if (dataSource == getDataSource(i)) {
                dataSource = getAndClearDataSource(i);
            }
            return dataSource;
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private int mIndex;

            public InternalDataSubscriber(int index) {
                this.mIndex = index;
            }

            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    IncreasingQualityDataSource.this.onDataSourceNewResult(this.mIndex, dataSource);
                } else if (dataSource.isFinished()) {
                    IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
                }
            }

            public void onFailure(DataSource<T> dataSource) {
                IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
            }

            public void onCancellation(DataSource<T> dataSource) {
            }

            public void onProgressUpdate(DataSource<T> dataSource) {
                if (this.mIndex == 0) {
                    IncreasingQualityDataSource.this.setProgress(dataSource.getProgress());
                }
            }
        }
    }
}

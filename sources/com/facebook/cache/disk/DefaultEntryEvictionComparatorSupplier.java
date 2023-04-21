package com.facebook.cache.disk;

import com.facebook.cache.disk.DiskStorage;

public class DefaultEntryEvictionComparatorSupplier implements EntryEvictionComparatorSupplier {
    public EntryEvictionComparator get() {
        return new EntryEvictionComparator() {
            public int compare(DiskStorage.Entry e1, DiskStorage.Entry e2) {
                long time1 = e1.getTimestamp();
                long time2 = e2.getTimestamp();
                if (time1 < time2) {
                    return -1;
                }
                return time2 == time1 ? 0 : 1;
            }
        };
    }
}

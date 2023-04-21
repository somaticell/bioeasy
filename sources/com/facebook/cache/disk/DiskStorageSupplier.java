package com.facebook.cache.disk;

import java.io.IOException;

public interface DiskStorageSupplier {
    DiskStorage get() throws IOException;
}

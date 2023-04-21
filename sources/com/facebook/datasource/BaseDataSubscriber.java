package com.facebook.datasource;

public abstract class BaseDataSubscriber<T> implements DataSubscriber<T> {
    /* access modifiers changed from: protected */
    public abstract void onFailureImpl(DataSource<T> dataSource);

    /* access modifiers changed from: protected */
    public abstract void onNewResultImpl(DataSource<T> dataSource);

    public void onNewResult(DataSource<T> dataSource) {
        boolean shouldClose = dataSource.isFinished();
        try {
            onNewResultImpl(dataSource);
        } finally {
            if (shouldClose) {
                dataSource.close();
            }
        }
    }

    public void onFailure(DataSource<T> dataSource) {
        try {
            onFailureImpl(dataSource);
        } finally {
            dataSource.close();
        }
    }

    public void onCancellation(DataSource<T> dataSource) {
    }

    public void onProgressUpdate(DataSource<T> dataSource) {
    }
}

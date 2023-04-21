package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;

public class DefaultCacheKeyFactory implements CacheKeyFactory {
    private static DefaultCacheKeyFactory sInstance = null;

    protected DefaultCacheKeyFactory() {
    }

    public static synchronized DefaultCacheKeyFactory getInstance() {
        DefaultCacheKeyFactory defaultCacheKeyFactory;
        synchronized (DefaultCacheKeyFactory.class) {
            if (sInstance == null) {
                sInstance = new DefaultCacheKeyFactory();
            }
            defaultCacheKeyFactory = sInstance;
        }
        return defaultCacheKeyFactory;
    }

    public CacheKey getBitmapCacheKey(ImageRequest request) {
        return new BitmapMemoryCacheKey(getCacheKeySourceUri(request.getSourceUri()).toString(), request.getResizeOptions(), request.getAutoRotateEnabled(), request.getImageDecodeOptions(), (CacheKey) null, (String) null);
    }

    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest request) {
        CacheKey postprocessorCacheKey;
        String postprocessorName;
        Postprocessor postprocessor = request.getPostprocessor();
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.getPostprocessorCacheKey();
            postprocessorName = postprocessor.getClass().getName();
        } else {
            postprocessorCacheKey = null;
            postprocessorName = null;
        }
        return new BitmapMemoryCacheKey(getCacheKeySourceUri(request.getSourceUri()).toString(), request.getResizeOptions(), request.getAutoRotateEnabled(), request.getImageDecodeOptions(), postprocessorCacheKey, postprocessorName);
    }

    public CacheKey getEncodedCacheKey(ImageRequest request) {
        return new SimpleCacheKey(getCacheKeySourceUri(request.getSourceUri()).toString());
    }

    public Uri getCacheKeySourceUri(Uri sourceUri) {
        return sourceUri;
    }
}

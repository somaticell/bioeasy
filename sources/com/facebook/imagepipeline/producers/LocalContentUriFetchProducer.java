package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapCounterProvider;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriFetchProducer extends LocalFetchProducer {
    private static final float ACCEPTABLE_REQUESTED_TO_ACTUAL_SIZE_RATIO = 1.3333334f;
    private static final String DISPLAY_PHOTO_PATH = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").getPath();
    private static final Rect MICRO_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 96, 96);
    private static final Rect MINI_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 512, BitmapCounterProvider.MAX_BITMAP_COUNT);
    private static final int NO_THUMBNAIL = 0;
    @VisibleForTesting
    static final String PRODUCER_NAME = "LocalContentUriFetchProducer";
    private static final String[] PROJECTION = {"_id", "_data"};
    private static final Class<?> TAG = LocalContentUriFetchProducer.class;
    private static final String[] THUMBNAIL_PROJECTION = {"_data"};
    private final ContentResolver mContentResolver;

    public LocalContentUriFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mContentResolver = contentResolver;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0049, code lost:
        r0 = getCameraImage(r2, r7.getResizeOptions());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.facebook.imagepipeline.image.EncodedImage getEncodedImage(com.facebook.imagepipeline.request.ImageRequest r7) throws java.io.IOException {
        /*
            r6 = this;
            r5 = -1
            android.net.Uri r2 = r7.getSourceUri()
            boolean r3 = isContactUri(r2)
            if (r3 == 0) goto L_0x0043
            java.lang.String r3 = r2.toString()
            java.lang.String r4 = "/photo"
            boolean r3 = r3.endsWith(r4)
            if (r3 == 0) goto L_0x0022
            android.content.ContentResolver r3 = r6.mContentResolver
            java.io.InputStream r1 = r3.openInputStream(r2)
        L_0x001d:
            com.facebook.imagepipeline.image.EncodedImage r0 = r6.getEncodedImage(r1, r5)
        L_0x0021:
            return r0
        L_0x0022:
            android.content.ContentResolver r3 = r6.mContentResolver
            java.io.InputStream r1 = android.provider.ContactsContract.Contacts.openContactPhotoInputStream(r3, r2)
            if (r1 != 0) goto L_0x001d
            java.io.IOException r3 = new java.io.IOException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Contact photo does not exist: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0043:
            boolean r3 = isCameraUri(r2)
            if (r3 == 0) goto L_0x0053
            com.facebook.imagepipeline.common.ResizeOptions r3 = r7.getResizeOptions()
            com.facebook.imagepipeline.image.EncodedImage r0 = r6.getCameraImage(r2, r3)
            if (r0 != 0) goto L_0x0021
        L_0x0053:
            android.content.ContentResolver r3 = r6.mContentResolver
            java.io.InputStream r3 = r3.openInputStream(r2)
            com.facebook.imagepipeline.image.EncodedImage r0 = r6.getEncodedImage(r3, r5)
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.LocalContentUriFetchProducer.getEncodedImage(com.facebook.imagepipeline.request.ImageRequest):com.facebook.imagepipeline.image.EncodedImage");
    }

    private static boolean isContactUri(Uri uri) {
        return "com.android.contacts".equals(uri.getAuthority()) && !uri.getPath().startsWith(DISPLAY_PHOTO_PATH);
    }

    private static boolean isCameraUri(Uri uri) {
        String uriString = uri.toString();
        return uriString.startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()) || uriString.startsWith(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString());
    }

    @Nullable
    private EncodedImage getCameraImage(Uri uri, ResizeOptions resizeOptions) throws IOException {
        EncodedImage thumbnail;
        Cursor cursor = this.mContentResolver.query(uri, PROJECTION, (String) null, (String[]) null, (String) null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            String pathname = cursor.getString(cursor.getColumnIndex("_data"));
            if (resizeOptions != null && (thumbnail = getThumbnail(resizeOptions, cursor.getInt(cursor.getColumnIndex("_id")))) != null) {
                thumbnail.setRotationAngle(getRotationAngle(pathname));
                cursor.close();
                return thumbnail;
            } else if (pathname != null) {
                EncodedImage encodedImage = getEncodedImage(new FileInputStream(pathname), getLength(pathname));
                cursor.close();
                return encodedImage;
            } else {
                cursor.close();
                return null;
            }
        } finally {
            cursor.close();
        }
    }

    private EncodedImage getThumbnail(ResizeOptions resizeOptions, int imageId) throws IOException {
        EncodedImage encodedImage = null;
        int thumbnailKind = getThumbnailKind(resizeOptions);
        if (thumbnailKind != 0) {
            Cursor thumbnailCursor = null;
            try {
                Cursor thumbnailCursor2 = MediaStore.Images.Thumbnails.queryMiniThumbnail(this.mContentResolver, (long) imageId, thumbnailKind, THUMBNAIL_PROJECTION);
                if (thumbnailCursor2 != null) {
                    thumbnailCursor2.moveToFirst();
                    if (thumbnailCursor2.getCount() > 0) {
                        String thumbnailUri = thumbnailCursor2.getString(thumbnailCursor2.getColumnIndex("_data"));
                        if (new File(thumbnailUri).exists()) {
                            encodedImage = getEncodedImage(new FileInputStream(thumbnailUri), getLength(thumbnailUri));
                            if (thumbnailCursor2 != null) {
                                thumbnailCursor2.close();
                            }
                        }
                    }
                    if (thumbnailCursor2 != null) {
                        thumbnailCursor2.close();
                    }
                } else if (thumbnailCursor2 != null) {
                    thumbnailCursor2.close();
                }
            } catch (Throwable th) {
                if (thumbnailCursor != null) {
                    thumbnailCursor.close();
                }
                throw th;
            }
        }
        return encodedImage;
    }

    private static int getThumbnailKind(ResizeOptions resizeOptions) {
        if (isThumbnailBigEnough(resizeOptions, MICRO_THUMBNAIL_DIMENSIONS)) {
            return 3;
        }
        if (isThumbnailBigEnough(resizeOptions, MINI_THUMBNAIL_DIMENSIONS)) {
            return 1;
        }
        return 0;
    }

    @VisibleForTesting
    static boolean isThumbnailBigEnough(ResizeOptions resizeOptions, Rect thumbnailDimensions) {
        return ((float) resizeOptions.width) <= ((float) thumbnailDimensions.width()) * ACCEPTABLE_REQUESTED_TO_ACTUAL_SIZE_RATIO && ((float) resizeOptions.height) <= ((float) thumbnailDimensions.height()) * ACCEPTABLE_REQUESTED_TO_ACTUAL_SIZE_RATIO;
    }

    private static int getLength(String pathname) {
        if (pathname == null) {
            return -1;
        }
        return (int) new File(pathname).length();
    }

    /* access modifiers changed from: protected */
    public String getProducerName() {
        return PRODUCER_NAME;
    }

    private static int getRotationAngle(String pathname) {
        if (pathname == null) {
            return 0;
        }
        try {
            return JfifUtil.getAutoRotateAngleFromOrientation(new ExifInterface(pathname).getAttributeInt("Orientation", 1));
        } catch (IOException ioe) {
            FLog.e(TAG, (Throwable) ioe, "Unable to retrieve thumbnail rotation for %s", pathname);
            return 0;
        }
    }
}

package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUrlConnectionNetworkFetcher extends BaseNetworkFetcher<FetchState> {
    private static final int NUM_NETWORK_THREADS = 3;
    private final ExecutorService mExecutorService = Executors.newFixedThreadPool(3);

    public FetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext context) {
        return new FetchState(consumer, context);
    }

    public void fetch(final FetchState fetchState, final NetworkFetcher.Callback callback) {
        final Future<?> future = this.mExecutorService.submit(new Runnable() {
            /* JADX WARNING: type inference failed for: r10v5, types: [java.net.URLConnection] */
            /* JADX WARNING: Multi-variable type inference failed */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r12 = this;
                    r1 = 0
                    com.facebook.imagepipeline.producers.FetchState r10 = r4
                    android.net.Uri r7 = r10.getUri()
                    java.lang.String r6 = r7.getScheme()
                    com.facebook.imagepipeline.producers.FetchState r10 = r4
                    android.net.Uri r10 = r10.getUri()
                    java.lang.String r8 = r10.toString()
                L_0x0015:
                    java.net.URL r9 = new java.net.URL     // Catch:{ Exception -> 0x0054 }
                    r9.<init>(r8)     // Catch:{ Exception -> 0x0054 }
                    java.net.URLConnection r10 = r9.openConnection()     // Catch:{ Exception -> 0x0054 }
                    r0 = r10
                    java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x0054 }
                    r1 = r0
                    java.lang.String r10 = "Location"
                    java.lang.String r5 = r1.getHeaderField(r10)     // Catch:{ Exception -> 0x0054 }
                    if (r5 != 0) goto L_0x0043
                    r4 = 0
                L_0x002b:
                    if (r5 == 0) goto L_0x0033
                    boolean r10 = r4.equals(r6)     // Catch:{ Exception -> 0x0054 }
                    if (r10 == 0) goto L_0x004c
                L_0x0033:
                    java.io.InputStream r3 = r1.getInputStream()     // Catch:{ Exception -> 0x0054 }
                    com.facebook.imagepipeline.producers.NetworkFetcher$Callback r10 = r5     // Catch:{ Exception -> 0x0054 }
                    r11 = -1
                    r10.onResponse(r3, r11)     // Catch:{ Exception -> 0x0054 }
                    if (r1 == 0) goto L_0x0042
                    r1.disconnect()
                L_0x0042:
                    return
                L_0x0043:
                    android.net.Uri r10 = android.net.Uri.parse(r5)     // Catch:{ Exception -> 0x0054 }
                    java.lang.String r4 = r10.getScheme()     // Catch:{ Exception -> 0x0054 }
                    goto L_0x002b
                L_0x004c:
                    r8 = r5
                    r6 = r4
                    if (r1 == 0) goto L_0x0015
                    r1.disconnect()
                    goto L_0x0015
                L_0x0054:
                    r2 = move-exception
                    com.facebook.imagepipeline.producers.NetworkFetcher$Callback r10 = r5     // Catch:{ all -> 0x0060 }
                    r10.onFailure(r2)     // Catch:{ all -> 0x0060 }
                    if (r1 == 0) goto L_0x0042
                    r1.disconnect()
                    goto L_0x0042
                L_0x0060:
                    r10 = move-exception
                    if (r1 == 0) goto L_0x0066
                    r1.disconnect()
                L_0x0066:
                    throw r10
                */
                throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.AnonymousClass1.run():void");
            }
        });
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                if (future.cancel(false)) {
                    callback.onCancellation();
                }
            }
        });
    }
}

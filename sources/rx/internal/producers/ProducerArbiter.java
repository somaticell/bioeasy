package rx.internal.producers;

import rx.Producer;

public final class ProducerArbiter implements Producer {
    static final Producer NULL_PRODUCER = new Producer() {
        public void request(long n) {
        }
    };
    Producer currentProducer;
    boolean emitting;
    long missedProduced;
    Producer missedProducer;
    long missedRequested;
    long requested;

    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r4 = r11.requested + r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002d, code lost:
        if (r4 >= 0) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x002f, code lost:
        r4 = com.facebook.common.time.Clock.MAX_TIME;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0034, code lost:
        r11.requested = r4;
        r0 = r11.currentProducer;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0038, code lost:
        if (r0 == null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003a, code lost:
        r0.request(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x003d, code lost:
        emitLoop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0041, code lost:
        if (1 != 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0043, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r11.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0047, code lost:
        monitor-exit(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x004c, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x004d, code lost:
        if (0 == 0) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x004f, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r11.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0054, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void request(long r12) {
        /*
            r11 = this;
            r8 = 0
            int r6 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r6 >= 0) goto L_0x000e
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "n >= 0 required"
            r6.<init>(r7)
            throw r6
        L_0x000e:
            int r6 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r6 != 0) goto L_0x0013
        L_0x0012:
            return
        L_0x0013:
            monitor-enter(r11)
            boolean r6 = r11.emitting     // Catch:{ all -> 0x001f }
            if (r6 == 0) goto L_0x0022
            long r6 = r11.missedRequested     // Catch:{ all -> 0x001f }
            long r6 = r6 + r12
            r11.missedRequested = r6     // Catch:{ all -> 0x001f }
            monitor-exit(r11)     // Catch:{ all -> 0x001f }
            goto L_0x0012
        L_0x001f:
            r6 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x001f }
            throw r6
        L_0x0022:
            r6 = 1
            r11.emitting = r6     // Catch:{ all -> 0x001f }
            monitor-exit(r11)     // Catch:{ all -> 0x001f }
            r1 = 0
            long r2 = r11.requested     // Catch:{ all -> 0x004c }
            long r4 = r2 + r12
            int r6 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r6 >= 0) goto L_0x0034
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
        L_0x0034:
            r11.requested = r4     // Catch:{ all -> 0x004c }
            rx.Producer r0 = r11.currentProducer     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x003d
            r0.request(r12)     // Catch:{ all -> 0x004c }
        L_0x003d:
            r11.emitLoop()     // Catch:{ all -> 0x004c }
            r1 = 1
            if (r1 != 0) goto L_0x0012
            monitor-enter(r11)
            r6 = 0
            r11.emitting = r6     // Catch:{ all -> 0x0049 }
            monitor-exit(r11)     // Catch:{ all -> 0x0049 }
            goto L_0x0012
        L_0x0049:
            r6 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0049 }
            throw r6
        L_0x004c:
            r6 = move-exception
            if (r1 != 0) goto L_0x0054
            monitor-enter(r11)
            r7 = 0
            r11.emitting = r7     // Catch:{ all -> 0x0055 }
            monitor-exit(r11)     // Catch:{ all -> 0x0055 }
        L_0x0054:
            throw r6
        L_0x0055:
            r6 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0055 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerArbiter.request(long):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0 = r11.requested;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
        if (r0 == com.facebook.common.time.Clock.MAX_TIME) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        r4 = r0 - r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002e, code lost:
        if (r4 >= 0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        throw new java.lang.IllegalStateException("more items arrived than were requested");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0039, code lost:
        if (0 == 0) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003b, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r11.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0040, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r11.requested = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0046, code lost:
        emitLoop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x004a, code lost:
        if (1 != 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x004c, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r11.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0050, code lost:
        monitor-exit(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void produced(long r12) {
        /*
            r11 = this;
            r8 = 0
            int r3 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r3 > 0) goto L_0x000e
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "n > 0 required"
            r3.<init>(r6)
            throw r3
        L_0x000e:
            monitor-enter(r11)
            boolean r3 = r11.emitting     // Catch:{ all -> 0x0041 }
            if (r3 == 0) goto L_0x001a
            long r6 = r11.missedProduced     // Catch:{ all -> 0x0041 }
            long r6 = r6 + r12
            r11.missedProduced = r6     // Catch:{ all -> 0x0041 }
            monitor-exit(r11)     // Catch:{ all -> 0x0041 }
        L_0x0019:
            return
        L_0x001a:
            r3 = 1
            r11.emitting = r3     // Catch:{ all -> 0x0041 }
            monitor-exit(r11)     // Catch:{ all -> 0x0041 }
            r2 = 0
            long r0 = r11.requested     // Catch:{ all -> 0x0038 }
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r3 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r3 == 0) goto L_0x0046
            long r4 = r0 - r12
            int r3 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r3 >= 0) goto L_0x0044
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0038 }
            java.lang.String r6 = "more items arrived than were requested"
            r3.<init>(r6)     // Catch:{ all -> 0x0038 }
            throw r3     // Catch:{ all -> 0x0038 }
        L_0x0038:
            r3 = move-exception
            if (r2 != 0) goto L_0x0040
            monitor-enter(r11)
            r6 = 0
            r11.emitting = r6     // Catch:{ all -> 0x0055 }
            monitor-exit(r11)     // Catch:{ all -> 0x0055 }
        L_0x0040:
            throw r3
        L_0x0041:
            r3 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0041 }
            throw r3
        L_0x0044:
            r11.requested = r4     // Catch:{ all -> 0x0038 }
        L_0x0046:
            r11.emitLoop()     // Catch:{ all -> 0x0038 }
            r2 = 1
            if (r2 != 0) goto L_0x0019
            monitor-enter(r11)
            r3 = 0
            r11.emitting = r3     // Catch:{ all -> 0x0052 }
            monitor-exit(r11)     // Catch:{ all -> 0x0052 }
            goto L_0x0019
        L_0x0052:
            r3 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0052 }
            throw r3
        L_0x0055:
            r3 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0055 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerArbiter.produced(long):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r4.currentProducer = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0014, code lost:
        if (r5 == null) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0016, code lost:
        r5.request(r4.requested);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001b, code lost:
        emitLoop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001f, code lost:
        if (1 != 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0021, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r4.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0025, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x002d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x002e, code lost:
        if (0 == 0) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0030, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r4.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0035, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProducer(rx.Producer r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r1 = r4.emitting     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x000d
            if (r5 != 0) goto L_0x0009
            rx.Producer r5 = NULL_PRODUCER     // Catch:{ all -> 0x002a }
        L_0x0009:
            r4.missedProducer = r5     // Catch:{ all -> 0x002a }
            monitor-exit(r4)     // Catch:{ all -> 0x002a }
        L_0x000c:
            return
        L_0x000d:
            r1 = 1
            r4.emitting = r1     // Catch:{ all -> 0x002a }
            monitor-exit(r4)     // Catch:{ all -> 0x002a }
            r0 = 0
            r4.currentProducer = r5     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x001b
            long r2 = r4.requested     // Catch:{ all -> 0x002d }
            r5.request(r2)     // Catch:{ all -> 0x002d }
        L_0x001b:
            r4.emitLoop()     // Catch:{ all -> 0x002d }
            r0 = 1
            if (r0 != 0) goto L_0x000c
            monitor-enter(r4)
            r1 = 0
            r4.emitting = r1     // Catch:{ all -> 0x0027 }
            monitor-exit(r4)     // Catch:{ all -> 0x0027 }
            goto L_0x000c
        L_0x0027:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0027 }
            throw r1
        L_0x002a:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x002a }
            throw r1
        L_0x002d:
            r1 = move-exception
            if (r0 != 0) goto L_0x0035
            monitor-enter(r4)
            r2 = 0
            r4.emitting = r2     // Catch:{ all -> 0x0036 }
            monitor-exit(r4)     // Catch:{ all -> 0x0036 }
        L_0x0035:
            throw r1
        L_0x0036:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0036 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerArbiter.setProducer(rx.Producer):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
        r6 = r14.requested;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        if (r6 == com.facebook.common.time.Clock.MAX_TIME) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        r8 = r6 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
        if (r8 < 0) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        if (r8 != com.facebook.common.time.Clock.MAX_TIME) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r6 = com.facebook.common.time.Clock.MAX_TIME;
        r14.requested = com.facebook.common.time.Clock.MAX_TIME;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0049, code lost:
        if (r2 == null) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004d, code lost:
        if (r2 != NULL_PRODUCER) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004f, code lost:
        r14.currentProducer = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        r10 = r8 - r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005c, code lost:
        if (r10 >= 0) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0065, code lost:
        throw new java.lang.IllegalStateException("more produced than requested");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0066, code lost:
        r6 = r10;
        r14.requested = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006a, code lost:
        r14.currentProducer = r2;
        r2.request(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0070, code lost:
        r3 = r14.currentProducer;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0072, code lost:
        if (r3 == null) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0078, code lost:
        if (r4 == 0) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007a, code lost:
        r3.request(r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void emitLoop() {
        /*
            r14 = this;
        L_0x0000:
            monitor-enter(r14)
            long r4 = r14.missedRequested     // Catch:{ all -> 0x0053 }
            long r0 = r14.missedProduced     // Catch:{ all -> 0x0053 }
            rx.Producer r2 = r14.missedProducer     // Catch:{ all -> 0x0053 }
            r12 = 0
            int r12 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r12 != 0) goto L_0x001a
            r12 = 0
            int r12 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r12 != 0) goto L_0x001a
            if (r2 != 0) goto L_0x001a
            r12 = 0
            r14.emitting = r12     // Catch:{ all -> 0x0053 }
            monitor-exit(r14)     // Catch:{ all -> 0x0053 }
            return
        L_0x001a:
            r12 = 0
            r14.missedRequested = r12     // Catch:{ all -> 0x0053 }
            r12 = 0
            r14.missedProduced = r12     // Catch:{ all -> 0x0053 }
            r12 = 0
            r14.missedProducer = r12     // Catch:{ all -> 0x0053 }
            monitor-exit(r14)     // Catch:{ all -> 0x0053 }
            long r6 = r14.requested
            r12 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r12 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r12 == 0) goto L_0x0049
            long r8 = r6 + r4
            r12 = 0
            int r12 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r12 < 0) goto L_0x0042
            r12 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r12 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r12 != 0) goto L_0x0056
        L_0x0042:
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r14.requested = r6
        L_0x0049:
            if (r2 == 0) goto L_0x0070
            rx.Producer r12 = NULL_PRODUCER
            if (r2 != r12) goto L_0x006a
            r12 = 0
            r14.currentProducer = r12
            goto L_0x0000
        L_0x0053:
            r12 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x0053 }
            throw r12
        L_0x0056:
            long r10 = r8 - r0
            r12 = 0
            int r12 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r12 >= 0) goto L_0x0066
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "more produced than requested"
            r12.<init>(r13)
            throw r12
        L_0x0066:
            r6 = r10
            r14.requested = r10
            goto L_0x0049
        L_0x006a:
            r14.currentProducer = r2
            r2.request(r6)
            goto L_0x0000
        L_0x0070:
            rx.Producer r3 = r14.currentProducer
            if (r3 == 0) goto L_0x0000
            r12 = 0
            int r12 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r12 == 0) goto L_0x0000
            r3.request(r4)
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerArbiter.emitLoop():void");
    }
}

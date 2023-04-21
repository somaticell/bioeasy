package rx.internal.util.unsafe;

import com.facebook.common.time.Clock;

public class MpmcArrayQueue<E> extends MpmcArrayQueueConsumerField<E> {
    long p30;
    long p31;
    long p32;
    long p33;
    long p34;
    long p35;
    long p36;
    long p37;
    long p40;
    long p41;
    long p42;
    long p43;
    long p44;
    long p45;
    long p46;

    public MpmcArrayQueue(int capacity) {
        super(Math.max(2, capacity));
    }

    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        long capacity = this.mask + 1;
        long[] lSequenceBuffer = this.sequenceBuffer;
        long cIndex = Clock.MAX_TIME;
        while (true) {
            long currentProducerIndex = lvProducerIndex();
            long seqOffset = calcSequenceOffset(currentProducerIndex);
            long delta = lvSequence(lSequenceBuffer, seqOffset) - currentProducerIndex;
            if (delta == 0) {
                if (casProducerIndex(currentProducerIndex, 1 + currentProducerIndex)) {
                    spElement(calcElementOffset(currentProducerIndex), e);
                    soSequence(lSequenceBuffer, seqOffset, 1 + currentProducerIndex);
                    return true;
                }
            } else if (delta < 0 && currentProducerIndex - capacity <= cIndex) {
                cIndex = lvConsumerIndex();
                if (currentProducerIndex - capacity <= cIndex) {
                    return false;
                }
            }
        }
    }

    public E poll() {
        long[] lSequenceBuffer = this.sequenceBuffer;
        long pIndex = -1;
        while (true) {
            long currentConsumerIndex = lvConsumerIndex();
            long seqOffset = calcSequenceOffset(currentConsumerIndex);
            long delta = lvSequence(lSequenceBuffer, seqOffset) - (1 + currentConsumerIndex);
            if (delta == 0) {
                if (casConsumerIndex(currentConsumerIndex, 1 + currentConsumerIndex)) {
                    long offset = calcElementOffset(currentConsumerIndex);
                    E e = lpElement(offset);
                    spElement(offset, null);
                    soSequence(lSequenceBuffer, seqOffset, this.mask + currentConsumerIndex + 1);
                    return e;
                }
            } else if (delta < 0 && currentConsumerIndex >= pIndex) {
                pIndex = lvProducerIndex();
                if (currentConsumerIndex == pIndex) {
                    return null;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public E peek() {
        /*
            r6 = this;
        L_0x0000:
            long r0 = r6.lvConsumerIndex()
            long r4 = r6.calcElementOffset(r0)
            java.lang.Object r2 = r6.lpElement(r4)
            if (r2 != 0) goto L_0x0016
            long r4 = r6.lvProducerIndex()
            int r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x0000
        L_0x0016:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.util.unsafe.MpmcArrayQueue.peek():java.lang.Object");
    }

    public int size() {
        long before;
        long currentProducerIndex;
        long after = lvConsumerIndex();
        do {
            before = after;
            currentProducerIndex = lvProducerIndex();
            after = lvConsumerIndex();
        } while (before != after);
        return (int) (currentProducerIndex - after);
    }

    public boolean isEmpty() {
        return lvConsumerIndex() == lvProducerIndex();
    }
}

package com.hbm.util;

import com.hbm.interfaces.BitMask;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentBitSet implements BitMask, Cloneable {
    private static final VarHandle WORDS = MethodHandles.arrayElementVarHandle(long[].class);

    private final long[] words;
    private final int wordCount;
    private final int logicalSize;
    private final LongAdder bitCount = new LongAdder();

    public ConcurrentBitSet(int logicalSize) {
        if (logicalSize < 0) throw new NegativeArraySizeException("logicalSize < 0: " + logicalSize);
        this.logicalSize = logicalSize;
        this.wordCount = (logicalSize + 63) >>> 6;
        this.words = new long[wordCount];
    }

    public ConcurrentBitSet(ConcurrentBitSet other) {
        this.logicalSize = other.logicalSize;
        this.wordCount = other.wordCount;
        this.words = new long[wordCount];
        for (int i = 0; i < wordCount; i++) {
            this.words[i] = (long) WORDS.getVolatile(other.words, i);
        }
        this.bitCount.add(other.bitCount.sum());
    }

    public static ConcurrentBitSet fromLongArray(long[] data, int logicalSize) {
        ConcurrentBitSet bitSet = new ConcurrentBitSet(logicalSize);
        if (logicalSize == 0) return bitSet;

        int wordsToCopy = Math.min(data.length, bitSet.wordCount);
        int lastIdx = (logicalSize - 1) >>> 6;
        int rem = logicalSize & 63;
        long tailMask = rem == 0 ? -1L : ((1L << rem) - 1L);

        long totalBits = 0L;
        for (int i = 0; i < wordsToCopy; i++) {
            long w = data[i];
            if (i > lastIdx) w = 0L;
            else if (i == lastIdx && rem != 0) w &= tailMask;
            bitSet.words[i] = w;
            totalBits += Long.bitCount(w);
        }
        bitSet.bitCount.add(totalBits);
        return bitSet;
    }

    public static ConcurrentBitSet valueOf(long[] data) {
        int len = 0;
        for (int i = data.length - 1; i >= 0; i--) {
            long w = data[i];
            if (w != 0) {
                len = (i << 6) + (64 - Long.numberOfLeadingZeros(w));
                break;
            }
        }
        return fromLongArray(data, len);
    }

    public static ConcurrentBitSet fromWords(ByteBuffer buf, int logicalSize) {
        ConcurrentBitSet bitSet = new ConcurrentBitSet(logicalSize);
        int wc = bitSet.wordCount;
        int need = wc << 3;
        if (buf.remaining() < need) {
            throw new IllegalArgumentException("Buffer underflow: need " + need + " bytes for words, have " + buf.remaining());
        }
        if (wc == 0) return bitSet;
        long totalBits = 0L;
        int last = wc - 1;
        long tailMask = bitSet.lastWordMask();
        for (int i = 0; i < last; i++) {
            long w = buf.getLong();
            bitSet.words[i] = w;
            totalBits += Long.bitCount(w);
        }
        long wLast = buf.getLong() & tailMask;
        bitSet.words[last] = wLast;
        totalBits += Long.bitCount(wLast);
        bitSet.bitCount.add(totalBits);
        return bitSet;
    }

    public void toWords(ByteBuffer buf) {
        int need = wordCount << 3;
        if (buf.remaining() < need) {
            throw new IllegalArgumentException("Buffer too small: need " + need + " bytes remaining, have " + buf.remaining());
        }
        if (wordCount == 0) return;

        int last = wordCount - 1;
        long tailMask = lastWordMask();

        for (int i = 0; i < last; i++) {
            buf.putLong((long) WORDS.getVolatile(words, i));
        }
        buf.putLong(((long) WORDS.getVolatile(words, last)) & tailMask);
    }

    @Override
    public boolean get(int bit) {
        if (bit < 0) throw new IndexOutOfBoundsException("bit < 0: " + bit);
        if (bit >= logicalSize) return false;
        int wordIndex = bit >>> 6;
        long mask = 1L << (bit & 63);
        long word = (long) WORDS.getVolatile(words, wordIndex);
        return (word & mask) != 0;
    }

    @Override
    public void set(int bit) {
        if (bit < 0 || bit >= logicalSize) return;
        int wordIndex = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long oldWord = (long) WORDS.getVolatile(words, wordIndex);
            long newWord = oldWord | mask;
            if (oldWord == newWord) return;
            if (WORDS.compareAndSet(words, wordIndex, oldWord, newWord)) {
                bitCount.increment();
                return;
            }
        }
    }

    @Override
    public boolean getAndSet(int bit) {
        if (bit < 0 || bit >= logicalSize) throw new IndexOutOfBoundsException("bit index out of bounds: " + bit);
        int wordIndex = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long oldWord = (long) WORDS.getVolatile(words, wordIndex);
            if ((oldWord & mask) != 0) return true;
            long newWord = oldWord | mask;
            if (WORDS.compareAndSet(words, wordIndex, oldWord, newWord)) {
                bitCount.increment();
                return false;
            }
        }
    }

    public void set(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > logicalSize || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        if (fromIndex == toIndex) return;
        int startWord = fromIndex >>> 6;
        int endWord = (toIndex - 1) >>> 6;
        long startMask = -1L << (fromIndex & 63);
        long endMask = (toIndex & 63) == 0 ? -1L : ((1L << (toIndex & 63)) - 1L);
        if (startWord == endWord) {
            setBits(startWord, startMask & endMask);
        } else {
            setBits(startWord, startMask);
            for (int i = startWord + 1; i < endWord; i++) setBits(i, -1L);
            if (endMask != 0) setBits(endWord, endMask);
        }
    }

    private void setBits(int wi, long mask) {
        if (mask == 0) return;
        while (true) {
            long oldWord = (long) WORDS.getVolatile(words, wi);
            long newWord = oldWord | mask;
            if (oldWord == newWord) return;
            if (WORDS.compareAndSet(words, wi, oldWord, newWord)) {
                bitCount.add(Long.bitCount(newWord ^ oldWord));
                return;
            }
        }
    }

    public void clear(int bit) {
        if (bit < 0 || bit >= logicalSize) return;
        int wordIndex = bit >>> 6;
        long mask = ~(1L << (bit & 63));
        while (true) {
            long oldWord = (long) WORDS.getVolatile(words, wordIndex);
            long newWord = oldWord & mask;
            if (oldWord == newWord) return;
            if (WORDS.compareAndSet(words, wordIndex, oldWord, newWord)) {
                bitCount.decrement();
                return;
            }
        }
    }

    public boolean getAndClear(int bit) {
        if (bit < 0 || bit >= logicalSize) throw new IndexOutOfBoundsException("bit index out of bounds: " + bit);
        int wordIndex = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long oldWord = (long) WORDS.getVolatile(words, wordIndex);
            if ((oldWord & mask) == 0) return false;
            long newWord = oldWord & ~mask;
            if (WORDS.compareAndSet(words, wordIndex, oldWord, newWord)) {
                bitCount.decrement();
                return true;
            }
        }
    }

    public void clear(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > logicalSize || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        if (fromIndex == toIndex) return;
        int startWord = fromIndex >>> 6;
        int endWord = (toIndex - 1) >>> 6;
        long startMask = -1L << (fromIndex & 63);
        long endMask = (toIndex & 63) == 0 ? -1L : ((1L << (toIndex & 63)) - 1L);
        if (startWord == endWord) {
            clearBits(startWord, startMask & endMask);
        } else {
            clearBits(startWord, startMask);
            for (int i = startWord + 1; i < endWord; i++) clearBits(i, -1L);
            if (endMask != 0) clearBits(endWord, endMask);
        }
    }

    private void clearBits(int wi, long mask) {
        if (mask == 0) return;
        while (true) {
            long oldWord = (long) WORDS.getVolatile(words, wi);
            long newWord = oldWord & ~mask;
            if (oldWord == newWord) return;
            if (WORDS.compareAndSet(words, wi, oldWord, newWord)) {
                bitCount.add(-Long.bitCount(oldWord ^ newWord));
                return;
            }
        }
    }

    public void clear() {
        clear(0, logicalSize);
    }

    @Override
    public int nextSetBit(int from) {
        if (from < 0) from = 0;
        int wordIndex = from >>> 6;
        if (wordIndex >= wordCount) return -1;
        long word = ((long) WORDS.getVolatile(words, wordIndex)) & (~0L << (from & 63));
        while (true) {
            if (word != 0) {
                int idx = (wordIndex << 6) + Long.numberOfTrailingZeros(word);
                return (idx < logicalSize) ? idx : -1;
            }
            wordIndex++;
            if (wordIndex >= wordCount) return -1;
            word = (long) WORDS.getVolatile(words, wordIndex);
        }
    }

    @Override
    public int nextClearBit(int from) {
        if (from < 0) throw new IndexOutOfBoundsException("from < 0: " + from);
        if (from >= logicalSize) return from;
        int wordIndex = from >>> 6;
        if (wordIndex >= wordCount) return from;
        long word = ~((long) WORDS.getVolatile(words, wordIndex)) & (-1L << (from & 63));
        while (true) {
            if (word != 0) {
                int idx = (wordIndex << 6) + Long.numberOfTrailingZeros(word);
                return Math.min(idx, logicalSize);
            }
            wordIndex++;
            if (wordIndex >= wordCount) return logicalSize;
            word = ~((long) WORDS.getVolatile(words, wordIndex));
        }
    }

    @Override
    public int previousSetBit(int from) {
        if (from < 0) return -1;
        if (from >= logicalSize) from = logicalSize - 1;
        if (from < 0) return -1;
        int wordIndex = from >>> 6;
        long mask = ~0L >>> (63 - (from & 63));
        long word = ((long) WORDS.getVolatile(words, wordIndex)) & mask;
        while (true) {
            if (word != 0) return (wordIndex << 6) + (63 - Long.numberOfLeadingZeros(word));
            wordIndex--;
            if (wordIndex < 0) return -1;
            word = (long) WORDS.getVolatile(words, wordIndex);
        }
    }

    @Override
    public int previousClearBit(int from) {
        if (from < 0) return -1;
        if (from >= logicalSize) from = logicalSize - 1;
        if (from < 0) return -1;
        int wordIndex = from >>> 6;
        long mask = ~0L >>> (63 - (from & 63));
        long word = ~((long) WORDS.getVolatile(words, wordIndex)) & mask;
        while (true) {
            if (word != 0) return (wordIndex << 6) + (63 - Long.numberOfLeadingZeros(word));
            wordIndex--;
            if (wordIndex < 0) return -1;
            word = ~((long) WORDS.getVolatile(words, wordIndex));
        }
    }

    @Override
    public boolean isEmpty() {
        return bitCount.sum() == 0;
    }

    @Override
    public long cardinality() {
        return bitCount.sum();
    }

    @Override
    public int length() {
        if (logicalSize == 0) return 0;
        int maxWord = (logicalSize - 1) >>> 6;
        long mask = lastWordMask();
        for (int i = maxWord; i >= 0; i--) {
            long w = (long) WORDS.getVolatile(words, i);
            if (i == maxWord) w &= mask;
            if (w != 0L) {
                return (i << 6) + (64 - Long.numberOfLeadingZeros(w));
            }
        }
        return 0;
    }

    @Override
    public int size() {
        return wordCount << 6;
    }

    @Override
    public int logicalSize() {
        return logicalSize;
    }

    @Override
    public long[] toLongArray() {
        int len = length();
        if (len == 0) return new long[0];

        int used = (len + 63) >>> 6;
        long[] out = new long[used];
        int last = used - 1;
        int rem = len & 63;
        long tailMask = rem == 0 ? -1L : ((1L << rem) - 1L);

        for (int i = 0; i < used; i++) {
            long v = (long) WORDS.getVolatile(words, i);
            if (i == last && rem != 0) v &= tailMask;
            out[i] = v;
        }
        return out;
    }

    private long lastWordMask() {
        int r = logicalSize & 63;
        return r == 0 ? -1L : ((1L << r) - 1L);
    }

    @Override
    public ConcurrentBitSet clone() {
        return new ConcurrentBitSet(this);
    }
}

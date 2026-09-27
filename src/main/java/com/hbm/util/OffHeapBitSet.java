package com.hbm.util;

import com.hbm.interfaces.BitMask;
import java.util.Arrays;

public final class OffHeapBitSet implements BitMask, Cloneable {
    private final int logicalSizeLocal;
    private final int wordCountLocal;
    private final long[] words;
    private long bitCount;

    public OffHeapBitSet(int logicalSize) {
        if (logicalSize < 0) throw new NegativeArraySizeException("logicalSize < 0: " + logicalSize);
        this.logicalSizeLocal = logicalSize;
        this.wordCountLocal = (logicalSize + 63) >>> 6;
        this.words = new long[wordCountLocal];
        this.bitCount = 0L;
    }

    @Override
    public void free() {
        Arrays.fill(words, 0L);
        bitCount = 0L;
    }

    @Override
    public boolean get(int bit) {
        if (bit < 0 || bit >= logicalSizeLocal) return false;
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        return (words[wi] & mask) != 0L;
    }

    @Override
    public void set(int bit) {
        if (bit < 0 || bit >= logicalSizeLocal) return;
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        long old = words[wi];
        if ((old & mask) != 0L) return;
        words[wi] = old | mask;
        bitCount++;
    }

    @Override
    public boolean getAndSet(int bit) {
        if (bit < 0 || bit >= logicalSizeLocal) throw new IndexOutOfBoundsException("bit index out of bounds: " + bit);
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        long old = words[wi];
        if ((old & mask) != 0L) return true;
        words[wi] = old | mask;
        bitCount++;
        return false;
    }

    public void clear(int bit) {
        if (bit < 0 || bit >= logicalSizeLocal) return;
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        long old = words[wi];
        if ((old & mask) == 0L) return;
        words[wi] = old & ~mask;
        bitCount--;
    }

    public boolean getAndClear(int bit) {
        if (bit < 0 || bit >= logicalSizeLocal) throw new IndexOutOfBoundsException("bit index out of bounds: " + bit);
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        long old = words[wi];
        if ((old & mask) == 0L) return false;
        words[wi] = old & ~mask;
        bitCount--;
        return true;
    }

    @Override
    public int nextSetBit(int from) {
        if (from < 0) from = 0;
        int wi = from >>> 6;
        if (wi >= wordCountLocal) return -1;
        long word = words[wi] & (~0L << (from & 63));
        while (true) {
            if (word != 0L) {
                int idx = (wi << 6) + Long.numberOfTrailingZeros(word);
                return (idx < logicalSizeLocal) ? idx : -1;
            }
            wi++;
            if (wi >= wordCountLocal) return -1;
            word = words[wi];
        }
    }

    @Override
    public int nextClearBit(int from) {
        if (from < 0) throw new IndexOutOfBoundsException("from < 0: " + from);
        if (from >= logicalSizeLocal) return from;
        int wi = from >>> 6;
        if (wi >= wordCountLocal) return from;
        long word = ~words[wi] & (-1L << (from & 63));
        while (true) {
            if (word != 0L) {
                int idx = (wi << 6) + Long.numberOfTrailingZeros(word);
                return Math.min(idx, logicalSizeLocal);
            }
            wi++;
            if (wi >= wordCountLocal) return logicalSizeLocal;
            word = ~words[wi];
        }
    }

    @Override
    public int previousSetBit(int from) {
        if (from < 0) return -1;
        if (from >= logicalSizeLocal) from = logicalSizeLocal - 1;
        if (from < 0) return -1;
        int wi = from >>> 6;
        long mask = ~0L >>> (63 - (from & 63));
        long word = words[wi] & mask;
        while (true) {
            if (word != 0L) return (wi << 6) + (63 - Long.numberOfLeadingZeros(word));
            wi--;
            if (wi < 0) return -1;
            word = words[wi];
        }
    }

    @Override
    public int previousClearBit(int from) {
        if (from < 0) return -1;
        if (from >= logicalSizeLocal) from = logicalSizeLocal - 1;
        if (from < 0) return -1;
        int wi = from >>> 6;
        long mask = ~0L >>> (63 - (from & 63));
        long word = ~words[wi] & mask;
        while (true) {
            if (word != 0L) return (wi << 6) + (63 - Long.numberOfLeadingZeros(word));
            wi--;
            if (wi < 0) return -1;
            word = ~words[wi];
        }
    }

    @Override
    public boolean isEmpty() {
        return bitCount == 0L;
    }

    @Override
    public long cardinality() {
        return bitCount;
    }

    @Override
    public int length() {
        if (logicalSizeLocal == 0) return 0;
        int maxWord = (logicalSizeLocal - 1) >>> 6;
        long mask = lastWordMask();
        for (int i = maxWord; i >= 0; i--) {
            long w = words[i];
            if (i == maxWord) w &= mask;
            if (w != 0L) return (i << 6) + (64 - Long.numberOfLeadingZeros(w));
        }
        return 0;
    }

    @Override
    public int size() {
        return wordCountLocal << 6;
    }

    @Override
    public int logicalSize() {
        return logicalSizeLocal;
    }

    private long lastWordMask() {
        int r = logicalSizeLocal & 63;
        return r == 0 ? -1L : ((1L << r) - 1L);
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
            long v = words[i];
            if (i == last && rem != 0) v &= tailMask;
            out[i] = v;
        }
        return out;
    }

    @Override
    public OffHeapBitSet clone() {
        OffHeapBitSet b = new OffHeapBitSet(this.logicalSizeLocal);
        System.arraycopy(this.words, 0, b.words, 0, this.wordCountLocal);
        b.bitCount = this.bitCount;
        return b;
    }
}

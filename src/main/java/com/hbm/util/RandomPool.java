package com.hbm.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomPool {
    private static final int CAPACITY = 256;
    private static final Random[] POOL = new Random[CAPACITY];
    private static int size;

    private RandomPool() {
    }

    public static synchronized Random borrow(long seed) {
        if (size == 0) {
            return new Random(seed);
        } else {
            int i = --size;
            Random rand = POOL[i];
            POOL[i] = null;
            rand.setSeed(seed);
            return rand;
        }
    }

    public static synchronized Random borrow() {
        if (size == 0) {
            return new Random(ThreadLocalRandom.current().nextLong() ^ System.nanoTime());
        } else {
            int i = --size;
            Random rand = POOL[i];
            POOL[i] = null;
            rand.setSeed(ThreadLocalRandom.current().nextLong() ^ System.nanoTime());
            return rand;
        }
    }

    public static synchronized void recycle(Random rand) {
        if (rand != null && size < CAPACITY) {
            POOL[size++] = rand;
        }
    }
}

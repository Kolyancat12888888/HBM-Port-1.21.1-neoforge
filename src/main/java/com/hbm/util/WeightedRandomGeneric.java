package com.hbm.util;

public class WeightedRandomGeneric<T> {
    public final int itemWeight;
    public final T item;

    public WeightedRandomGeneric(T o, int weight) {
        this.itemWeight = weight;
        this.item = o;
    }

    public T get() {
        return item;
    }
}

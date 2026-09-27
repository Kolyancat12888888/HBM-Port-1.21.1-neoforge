package com.hbm.util;

import com.hbm.interfaces.Spaghetti;
import java.util.Objects;

@Spaghetti("alreay??")
public class Tuple {

    public static class ObjectLongPair<T> {
        public T key;
        public long value;

        public ObjectLongPair(T x, long y) {
            this.key = x;
            this.value = y;
        }

        public T getKey() {
            return this.key;
        }

        public long getValue() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ObjectLongPair<?> other = (ObjectLongPair<?>) obj;
            return value == other.value && Objects.equals(key, other.key);
        }
    }

    public static class Pair<X, Y> {
        public X key;
        public Y value;

        public Pair(X x, Y y) {
            this.key = x;
            this.value = y;
        }

        public X getKey() {
            return this.key;
        }

        public Y getValue() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return Objects.equals(key, other.key) && Objects.equals(value, other.value);
        }
    }

    public static class Triplet<X, Y, Z> {
        public X x;
        public Y y;
        public Z z;

        public Triplet(X x, Y y, Z z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public X getX() {
            return this.x;
        }

        public Y getY() {
            return this.y;
        }

        public Z getZ() {
            return this.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
            return Objects.equals(x, other.x) && Objects.equals(y, other.y) && Objects.equals(z, other.z);
        }
    }

    public static class Quartet<W, X, Y, Z> {
        public W w;
        public X x;
        public Y y;
        public Z z;

        public Quartet(W w, X x, Y y, Z z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public W getW() {
            return this.w;
        }

        public X getX() {
            return this.x;
        }

        public Y getY() {
            return this.y;
        }

        public Z getZ() {
            return this.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(w, x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quartet<?, ?, ?, ?> other = (Quartet<?, ?, ?, ?>) obj;
            return Objects.equals(w, other.w) && Objects.equals(x, other.x) && Objects.equals(y, other.y) && Objects.equals(z, other.z);
        }
    }

    public static class Quintet<V, W, X, Y, Z> {
        public V v;
        public W w;
        public X x;
        public Y y;
        public Z z;

        public Quintet(V v, W w, X x, Y y, Z z) {
            this.v = v;
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public V getV() {
            return this.v;
        }

        public W getW() {
            return this.w;
        }

        public X getX() {
            return this.x;
        }

        public Y getY() {
            return this.y;
        }

        public Z getZ() {
            return this.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v, w, x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quintet<?, ?, ?, ?, ?> other = (Quintet<?, ?, ?, ?, ?>) obj;
            return Objects.equals(v, other.v) && Objects.equals(w, other.w) && Objects.equals(x, other.x) && Objects.equals(y, other.y) && Objects.equals(z, other.z);
        }
    }
}

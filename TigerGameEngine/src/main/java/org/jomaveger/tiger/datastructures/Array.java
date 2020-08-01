package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import org.jomaveger.tge.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

@Invariant("checkInvariant()")
public class Array<T> implements Serializable, Iterable<T> {

    private T[] array;

    @Requires("size >= 0")
    @Ensures("isEmpty() && (length() == size)")
    public Array(final Integer size) {
        array = (T[]) new Object[size];
    }

    @Ensures("result >= 0")
    public Integer length() {
        Integer result = array.length;
        return result;
    }

    public Boolean isEmpty() {
        Boolean isEmpty = true;
        if (this.length() == 0)
            isEmpty = true;
        else {
            Boolean empty = true;
            int i = 0;
            while (i < length() && empty) {
                if (array[i] != null) {
                    isEmpty = false;
                }
                i++;
           }
        }
        return isEmpty;
    }

    @Requires("index >= 0 && index <= (length() - 1)")
    @Ensures("length() == old(length())")
    public T get(final Integer index) {
        T elem = array[index];
        return elem;
    }

    @Requires("index >= 0 && index <= (length() - 1)")
    @Ensures("length() == old(length())")
    public void set(final T elem, final Integer index) {
        array[index] = elem;
    }

    @Ensures("result.equals(this) || result.isEmpty()")
    public Array<T> deepCopy() {
        Array<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new Array<>(this.length());
        }
        return deepCopy;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.array);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Array<T> that = (Array<T>) otherObject;

        return Arrays.deepEquals(this.array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.array);
    }

    public T[] toNativeArray() {
        return this.array;
    }

    private boolean checkInvariant() {
        return this.array != null && this.array.length >= 0;
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {

        private Integer index = 0;

        @Override
        public boolean hasNext() {
            return index < Array.this.array.length;
        }

        @Override
        public T next() {
            return Array.this.array[index++];
        }
    }
}
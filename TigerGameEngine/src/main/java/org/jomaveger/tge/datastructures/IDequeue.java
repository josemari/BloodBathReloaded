package org.jomaveger.tge.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IDequeue<T> {
    
    @Requires("elem != null")
    @Ensures("getFirst() == elem && size() == (old(size()) + 1)")
    void addFirst(final T elem);

    @Requires("elem != null")
    @Ensures("getLast() == elem && size() == (old(size()) + 1)")
    void addLast(final T elem);

    @Requires("!isEmpty()")
    @Ensures("size() == old(size())")
    T getFirst();

    @Requires("!isEmpty()")
    @Ensures("size() == old(size())")
    T getLast();

    @Requires("!isEmpty()")
    @Ensures("size() == old(size()) - 1")
    void removeFirst();

    @Requires("!isEmpty()")
    @Ensures("size() == old(size()) - 1")
    void removeLast();

    @Requires("elem != null")
    @Ensures("size() == old(size())")
    Boolean contains(final T elem);

    @Ensures("result == (size() == 0)")
    Boolean isEmpty();

    @Ensures("result >= 0")
    Integer size();

    @Ensures("result.equals(this) || result.isEmpty()")
    IDequeue<T> deepCopy();
}

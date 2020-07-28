package org.jomaveger.tiger.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IQueue<T> {

	@Requires("elem != null")
    @Ensures("(!(size()==1) || (front() == elem)) && (size() == (old(size()) + 1))")  
    void enqueue(final T elem);

    @Requires("size() > 0")
    @Ensures("size() == (old(size()) - 1)")   
    void dequeue();

    @Requires("size() > 0")
    @Ensures("size() == old(size())")
    T front();

    @Ensures("result == (size() == 0)")
    Boolean isEmpty();

    @Ensures("result >= 0")
    Integer size();

    @Ensures("result.equals(this) || result.isEmpty()")
    IQueue<T> deepCopy();
}

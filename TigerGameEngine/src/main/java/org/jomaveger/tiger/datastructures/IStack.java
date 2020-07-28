package org.jomaveger.tiger.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IStack<T> {

	
	@Requires("elem != null")
	@Ensures("(peek() == elem) && !isEmpty() && (size() == old(size()) + 1)")
    void push(final T elem);

	@Requires("!isEmpty()")
	@Ensures("size() == old(size()) - 1")
    void pop();

	@Requires("!isEmpty()")
	@Ensures("size() == old(size())")
    T peek();

	@Ensures("result == (size() == 0)")
    Boolean isEmpty();

	@Ensures("result >= 0")
    Integer size();

	@Ensures("result.equals(this) || result.isEmpty()")
    IStack<T> deepCopy();
}
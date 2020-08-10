package org.jomaveger.tge.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IList<T> extends Iterable<T> {

	@Requires("elem != null")
	@Ensures("(getFirst() == elem) && (size() == (old(size()) + 1))")
    void addFirst(final T elem);

	@Requires("elem != null")
	@Ensures("(getLast() == elem) && (size() == (old(size()) + 1))")
    void addLast(final T elem);

	@Requires("!isEmpty()")
	@Ensures("(result == get(0)) && (size() == old(size()))")
    T getFirst();

	@Requires("!isEmpty()")
	@Ensures("(result == get(size() - 1)) && (size() == old(size()))")
    T getLast();

	@Requires("!isEmpty()")
	@Ensures("size() == (old(size()) - 1)")
    void removeFirst();

	@Requires("!isEmpty()")
	@Ensures("size() == (old(size()) - 1)")
    void removeLast();

	@Requires("elem != null")
	@Ensures("size() == old(size())")
    Boolean contains(final T elem);

	@Requires("elem != null")
	@Ensures("!contains(elem) || size() == (old(size()) - 1)")
    void remove(final T elem);

	@Ensures("isEmpty()")
    void clear();

	@Requires("!isEmpty() && (index >= 0) && (index <= (size() - 1))")
	@Ensures("(result != null) && (size() == old(size()))")
    T get(Integer index);

	@Requires("!isEmpty() && (elem != null) && (index >= 0) && (index <= (size() - 1))")
	@Ensures("size() == old(size())")
    void set(Integer index, final T elem);

	@Requires("!isEmpty() && (elem != null) && (index >= 0) && (index <= size())")
	@Ensures("(size() == (old(size()) + 1)) && (contains(elem) == true)")
    void add(Integer index, final T elem);

	@Requires("!isEmpty() && (index >= 0) && (index <= (size() - 1))")
	@Ensures("size() == (old(size()) - 1)")
    void remove(Integer index);

	@Requires("elem != null")
	@Ensures("(size() == old(size())) && ((result == -1) || (contains(elem)))")
    Integer indexOf(final T elem);

	@Requires("elem != null")
	@Ensures("(size() == old(size())) && ((result == -1) || (contains(elem))) && \n" + 
			"		((result == -1) || (result >= indexOf(elem)))")
    Integer lastIndexOf(final T elem);

	@Ensures("result == (size() == 0)")
    Boolean isEmpty();

	@Ensures("result >= 0")
    Integer size();

	@Ensures("result.equals(this) || result.isEmpty()")
    IList<T> deepCopy();
}

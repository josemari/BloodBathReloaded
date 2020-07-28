package org.jomaveger.tiger.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface ISet<T> {

	@Requires("elem != null")
	@Ensures("!isEmpty() && (cardinal() == 1) && (contains(elem) == true)")
    void makeUnitSet(final T elem);

	@Ensures("isEmpty() && (cardinal() == 0)")
    void clear();

	@Requires("elem != null")
	@Ensures("!isEmpty() && (contains(elem) == true) && (old(contains(elem)) || cardinal() == old(cardinal()) + 1)")
    void add(final T elem);

	@Requires("elem != null")
	@Ensures("cardinal() == old(cardinal())")
    Boolean contains(final T elem);

	@Requires("elem != null")
	@Ensures("!contains(elem) || cardinal() == (old(cardinal()) - 1)")
    void remove(final T elem);

	@Requires("other != null")
	@Ensures("cardinal() == old(cardinal())")
    Boolean isSubset(final ISet<T> other);

	@Requires("other != null")
	@Ensures("cardinal() <= old(cardinal()) + old(other.cardinal())")
    void union(final ISet<T> other);

	@Requires("other != null")
	@Ensures("cardinal() <= old(cardinal()) + old(other.cardinal())")
    void intersection(final ISet<T> other);

	@Requires("other != null")
	@Ensures("cardinal() <= old(cardinal())")
    void difference(final ISet<T> other);

    @Ensures("result == (cardinal() == 0)")
    Boolean isEmpty();

    @Ensures("result >= 0")
    Integer cardinal();

    @Ensures("result.equals(this) || result.isEmpty()")
    ISet<T> deepCopy();
}

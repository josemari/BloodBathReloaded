package org.jomaveger.tge.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IBag<T> {

    @Requires("elem != null")
    @Ensures("!isEmpty() && cardinal() == old(cardinal()) + 1 "
            + "&& multiplicity(elem) == old(multiplicity(elem)) + 1 "
            + "&& (!(multiplicity(elem) == 1) "
            + "|| cardinalDistinct() == old(cardinalDistinct()) + 1)")
    void add(final T elem);

    @Requires("elem != null")
    @Ensures("result >= 0 && result <= cardinal() && result <= cardinalDistinct()")
    Integer multiplicity(final T elem);

    @Requires("elem != null")
    @Ensures("cardinal() == old(cardinal()) - 1 "
            + "&& multiplicity(elem) == old(multiplicity(elem)) - 1 "
            + "&& (!(multiplicity(elem) == 0) "
            + "|| cardinalDistinct() == old(cardinalDistinct()) - 1)")    
    void delete(final T elem);

    @Requires("elem != null")
    @Ensures("cardinal() == old(cardinal()) - old(multiplicity(elem)) "
            + "&& multiplicity(elem) == 0 "
            + "&& cardinalDistinct() == old(cardinalDistinct()) - 1")
    void remove(final T elem);

    @Ensures("isEmpty()")
    void clear();

    @Requires("other != null")
    @Ensures("cardinal() == old(cardinal()) + old(other.cardinal()) "
            + "&& cardinalDistinct() <= "
            + "old(cardinalDistinct()) + old(other.cardinalDistinct())")
    void union(final IBag<T> other);

    @Requires("other != null")
    @Ensures("cardinal() < old(cardinal()) + old(other.cardinal()) "
            + "&& cardinalDistinct() < "
            + "old(cardinalDistinct()) + old(other.cardinalDistinct())")
    void intersection(final IBag<T> other);

    @Requires("other != null")
    @Ensures("cardinal() <= old(cardinal()) + old(other.cardinal()) "
            + "&& cardinalDistinct() <= "
            + "old(cardinalDistinct()) + old(other.cardinalDistinct())")
    void difference(final IBag<T> other);

    @Ensures("result == (cardinal() == 0)")
    Boolean isEmpty();

    @Ensures("result >= 0")
    Integer cardinal();

    @Ensures("result <= cardinal()")
    Integer cardinalDistinct();

    @Ensures("result.equals(this) || result.isEmpty()")
    IBag<T> deepCopy();
}
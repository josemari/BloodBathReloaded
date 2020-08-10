package org.jomaveger.tge.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tge.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("checkInvariant()")
public class Bag<T> implements IBag<T>, Serializable {

    private ITable<T, Integer> bag;

    @Ensures("isEmpty() && (cardinal() == 0) && cardinalDistinct() == 0")
    public Bag() {
        this.bag = new LinkedTable<>();
    }

    @Override
    public void add(final T elem) {
        if (this.bag.contains(elem)) {
            Integer actualMultiplicity = this.bag.get(elem);
            this.bag.set(elem, ++actualMultiplicity);
        } else {
            this.bag.set(elem, 1);
        }
    }

    @Override
    public Integer multiplicity(T elem) {
        Integer multiplicity;
        if (this.bag.contains(elem)) {
            multiplicity = this.bag.get(elem);
        } else {
            multiplicity = 0;
        }
        return multiplicity;
    }

    @Override
    public void delete(T elem) {
        if (this.bag.contains(elem)) {
            Integer actualMultiplicity = this.bag.get(elem);
            if (actualMultiplicity > 1) {
                this.bag.set(elem, --actualMultiplicity);
            } else {
                this.bag.remove(elem);
            }
        }
    }

    @Override
    public void remove(T elem) {
        this.bag.remove(elem);
    }

    @Override
    public void clear() {
        this.bag.clear();
    }

    @Override
    public void union(IBag<T> other) {
        Bag<T> otherBag = (Bag<T>) other;
        IList<T> list = otherBag.bag.keyList();
        for (T elem : list) {
            if (this.bag.contains(elem)) {
                Integer actualMultiplicity = this.bag.get(elem);
                Integer otherMultiplicity = otherBag.bag.get(elem);
                this.bag.set(elem, actualMultiplicity + otherMultiplicity);
            } else {
                this.bag.set(elem, otherBag.bag.get(elem));
            }
        }
    }

    @Override
    public void intersection(IBag<T> other) {
        Bag<T> temp = new Bag<>();
        Bag<T> otherBag = (Bag<T>) other;
        IList<T> list = otherBag.bag.keyList();
        for (T elem : list) {
            if (this.bag.contains(elem)) {
                Integer thisMultiplicity = this.bag.get(elem);
                Integer otherMultiplicity = otherBag.bag.get(elem);
                Integer minMultiplicity = Math.min(thisMultiplicity, otherMultiplicity);
                temp.bag.set(elem, minMultiplicity);
            }
        }

        this.clear();
        IList<T> tempList = temp.bag.keyList();
        for (T elem : tempList) {
            this.bag.set(elem, temp.bag.get(elem));
        }
    }

    @Override
    public void difference(IBag<T> other) {
        Bag<T> temp = new Bag<>();
        Bag<T> otherBag = (Bag<T>) other;
        IList<T> list = this.bag.keyList();
        for (T elem : list) {
            if (otherBag.bag.contains(elem)) {
                Integer thisMultiplicity = this.bag.get(elem);
                Integer otherMultiplicity = otherBag.bag.get(elem);
                if (thisMultiplicity > otherMultiplicity) {
                    temp.bag.set(elem, thisMultiplicity - otherMultiplicity);
                }
            } else {
                temp.bag.set(elem, this.bag.get(elem));
            }
        }

        this.clear();
        IList<T> tempList = temp.bag.keyList();
        for (T elem : tempList) {
            this.bag.set(elem, temp.bag.get(elem));
        }
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = this.bag.isEmpty();
        return condition;
    }

    @Override
    public Integer cardinal() {
        Integer cardinal = 0;
        IList<T> list = this.bag.keyList();
        for (T elem : list) {
            cardinal += this.bag.get(elem);
        }
        return cardinal;
    }

    @Override
    public Integer cardinalDistinct() {
        Integer cardinal = 0;
        IList<T> list = this.bag.keyList();
        cardinal = list.size();
        return cardinal;
    }

    @Override
    public IBag<T> deepCopy() {
        IBag<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new Bag<>();
        }
        return deepCopy;
    }

    private boolean checkInvariant() {
        return this.bag != null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Bag<T> that = (Bag<T>) otherObject;
        return Objects.equals(this.bag, that.bag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.bag);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.bag.toString());
        string.append("]");
        return string.toString();
    }
}
package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tiger.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("checkInvariant()")
public class Set<T> implements ISet<T>, Serializable {

    private IList<T> set;

    @Ensures("isEmpty() && (cardinal() == 0)")
    public Set() {
        this.set = new LinkedList<>();
    }

    @Override
    public void makeUnitSet(final T elem) {
        this.clear();
        this.add(elem);
    }

    @Override
    public void clear() {
        this.set.clear();
    }

    @Override
    public void add(final T elem) {
        if (! this.set.contains(elem)) {
            this.set.addLast(elem);
        }
    }

    @Override
    public Boolean contains(final T elem) {
    	Boolean contains = this.set.contains(elem);
        return contains;
    }

    @Override
    public void remove(final T elem) {
        this.set.remove(elem);
    }

    @Override
    public Boolean isSubset(final ISet<T> other) {
    	Boolean isSubset = Boolean.TRUE;
        for (Integer i = 0; i < this.set.size(); i++) {
            if (! other.contains(this.set.get(i))) {
            	isSubset = Boolean.FALSE;
            }
        }
        return isSubset;
    }

    @Override
    public void union(final ISet<T> other) {
        Set<T> otherSet = (Set<T>) other;
        for (Integer i = 0; i < otherSet.set.size(); i++) {
            if (! this.set.contains(otherSet.set.get(i))) {
                this.set.addLast(otherSet.set.get(i));
            }
        }
    }

    @Override
    public void intersection(final ISet<T> other) {
        ISet<T> temp = new Set<T>();
        for (T elem : this.set) {
            if (other.contains(elem)) {
                temp.add(elem);
            }
        }
        this.clear();
        Set<T> tempSet = (Set<T>) temp;
        for (Integer i = 0; i < tempSet.set.size(); i++) {
            this.add(tempSet.set.get(i));
        }
    }

    @Override
    public void difference(final ISet<T> other) {
        Set<T> otherSet = (Set<T>) other;
        for (Integer i = 0; i < otherSet.set.size(); i++) {
            this.remove(otherSet.set.get(i));
        }
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = this.set.isEmpty();
        return condition;
    }

    @Override
    public Integer cardinal() {
        Integer size = this.set.size();
        return size;
    }

    @Override
    public ISet<T> deepCopy() {
    	ISet<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new Set<>();
        }
        return deepCopy;
    }
    
    private boolean checkInvariant() {
    	return this.set != null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Set<T> that = (Set<T>) otherObject;
        return Objects.equals(this.set, that.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.set);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.set.toString());
        string.append("]");
        return string.toString();
    }
}

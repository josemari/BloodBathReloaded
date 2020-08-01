package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tge.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;


@Invariant("checkInvariant()")
public class LinkedTable<K, V> implements ITable<K, V>, Serializable {

    private IList<TableEntry<K, V>> table;

    @Ensures("isEmpty()")
    public LinkedTable() {
        this.table = new LinkedList<>();
    }

    @Override
    public void set(final K key, final V value) {
        boolean insert = true;
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key)) {
                this.table.get(i).setValue(value);
                insert = false;
            }
        }
        if (insert) {
            this.table.addLast(new TableEntry<>(key, value));
        }
    }

    @Override
    public V get(final K key) {
        V result = null;
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key))
                result = this.table.get(i).getValue();
        }
        return result;
    }

    @Override
    public void remove(final K key) {
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key))
                this.table.remove(i);
        }
    }

    @Override
    public void clear() {
        this.table.clear();
    }

    @Override
    public Boolean contains(final K key) {
        Boolean contains = Boolean.FALSE;
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key))
                contains = Boolean.TRUE;
        }
        return contains;
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = this.table.isEmpty();
        return condition;
    }

    @Override
    public Integer size() {
        Integer size = this.table.size();
        return size;
    }

    @Override
    public ITable<K, V> deepCopy() {
        ITable<K, V> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new LinkedTable<>();
        }
        return deepCopy;
    }

    @Override
    public IList<K> keyList() {
        IList<K> list = new LinkedList<K>();
        for (int i = 0; i < this.table.size(); i++) {
            list.addLast(this.table.get(i).getKey());
        }
        return list;
    }

    private boolean checkInvariant() {
        return this.table != null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        LinkedTable<K, V> that = (LinkedTable<K, V>) otherObject;
        return Objects.equals(this.table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.table);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.table.toString());
        string.append("]");
        return string.toString();
    }
}
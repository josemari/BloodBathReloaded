package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tge.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;


@Invariant("checkInvariant()")
public class OpenHashTable<K, V> implements ITable<K, V>, Serializable {

    private Integer m;
    private Integer n;
    private Double maxL;
    private Array<IList<TableEntry<K, V>>> table;

    
    public OpenHashTable() {
        this(16, 2.5);
    }

    @Ensures("isEmpty()")
    public OpenHashTable(Integer m0, Double maxL) {
        this.maxL = maxL;
        this.m = m0;
        this.table = new Array<>(this.m);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(new LinkedList<>(), i);
        }
        this.n = 0;
    }

    @Override
    public void set(K key, V value) {
        if ((1.0 * this.n) / this.m > this.maxL) {
            this.restructure();
        }

        int i = this.index(key);

        Boolean encontrado = this.contains(key);
        if (encontrado) {
            IList<TableEntry<K, V>> tableEntries = this.table.get(i);
            for (Integer j = 0; j < tableEntries.size(); j++) {
                if (tableEntries.get(j).getKey().equals(key))
                    tableEntries.get(j).setValue(value);
            }
        } else {
            IList<TableEntry<K, V>> tableEntries = this.table.get(i);
            tableEntries.addFirst(new TableEntry<>(key, value));
            this.table.set(tableEntries, i);
            this.n++;
        }
    }

    private int index(K key) {
        return Math.abs(key.hashCode()) % this.m;
    }

    private void restructure() {
        Array<IList<TableEntry<K, V>>> tmp = this.table;
        this.n = 0;
        this.m = this.m * 2;
        this.table = new Array<>(this.m);
        for (int i = 0; i < tmp.length(); i++) {
            IList<TableEntry<K, V>> tableEntries = tmp.get(i);
            for (int k = 0; k < tableEntries.size(); k++) {
                this.set(tableEntries.get(k).getKey(), tableEntries.get(k).getValue());
            }
        }
    }

    @Override
    public V get(K key) {
        int i = this.index(key);

        IList<TableEntry<K, V>> tableEntries = this.table.get(i);
        V result = null;
        for (Integer j = 0; j < tableEntries.size(); j++) {
            if (tableEntries.get(j).getKey().equals(key))
                result = tableEntries.get(j).getValue();
        }
        return result;
    }

    @Override
    public void remove(K key) {
        int i = this.index(key);

        IList<TableEntry<K, V>> tableEntries = this.table.get(i);

        for (Integer j = 0; j < tableEntries.size(); j++) {
            if (tableEntries.get(j).getKey().equals(key)) {
                tableEntries.remove(j);
                this.n--;
            }
        }
    }

    @Override
    public void clear() {
        this.table = new Array<>(this.m);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(new LinkedList<>(), i);
        }
        this.n = 0;
    }

    @Override
    public Boolean contains(K key) {
        int i = this.index(key);

        IList<TableEntry<K, V>> tableEntries = this.table.get(i);
        Boolean contains = Boolean.FALSE;
        for (Integer j = 0; j < tableEntries.size(); j++) {
            if (tableEntries.get(j).getKey().equals(key))
                contains = Boolean.TRUE;
        }
        return contains;
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = this.n == 0;
        return condition;
    }

    @Override
    public Integer size() {
        Integer size = this.n;
        return size;
    }

    @Override
    public ITable<K, V> deepCopy() {
        ITable<K, V> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new OpenHashTable<>();
        }
        return deepCopy;
    }

    @Override
    public IList<K> keyList() {
        IList<K> list = new LinkedList<K>();
        for (int i = 0; i < this.table.length(); i++) {
            IList<TableEntry<K, V>> tableEntries = this.table.get(i);
            for (int k = 0; k < tableEntries.size(); k++) {
                list.addLast(tableEntries.get(k).getKey());
            }
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

        OpenHashTable<K, V> that = (OpenHashTable<K, V>) otherObject;
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
package org.jomaveger.tge.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IBinarySearchTree<K extends Comparable<K>, V> {

    @Requires("!isEmpty()")
    K min();

    @Requires("!isEmpty()")
    K max();

    @Requires("key != null")
    @Ensures("size() == old(size())")
    Boolean contains(final K key);

    @Requires("key != null")
    @Ensures("(!contains(key) || result != null) && size() == old(size())")        
    V get(final K key);

    @Requires("key != null && value != null")
    @Ensures("(!old(contains(key)) || size() == old(size())) "
            + "|| (old(contains(key)) || size() == old(size()) + 1)")
    void put(final K key, final V value);

    @Requires("key != null")
    @Ensures("(old(contains(key)) || size() == old(size())) "
            + "&& (!old(contains(key)) || size() == old(size()) - 1)")
    void remove(final K key);

    @Ensures("result == (size() == 0)")
    Boolean isEmpty();

    @Ensures("isEmpty()")
    void makeEmpty();

    Boolean isBalanced();

    @Ensures("result >= 0")
    Integer height();

    @Ensures("result >= 0")
    Integer size();

    @Ensures("result >= 0")
    Integer leaves();

    @Ensures("result.size() >= 0")
    IList<TableEntry<K,V>> preorder();

    @Ensures("result.size() >= 0")
    IList<TableEntry<K,V>> inorder();

    @Ensures("result.size() >= 0")
    IList<TableEntry<K,V>> postorder();

    @Ensures("result.size() >= 0")
    IList<TableEntry<K,V>> levelorder();

    @Ensures("result.size() >= 0")
    IList<TableEntry<K,V>> getOrderedList();

    @Requires("list != null")
    @Ensures("result.size() >= 0 && (list.isEmpty() || !result.equals(list))")
    <T extends Comparable<T>> IList<T> sort(IList<T> list);

    @Ensures("result.equals(this) || result.isEmpty()")
    IBinarySearchTree<K, V> deepCopy();
}
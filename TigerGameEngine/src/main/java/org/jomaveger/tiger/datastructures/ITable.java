package org.jomaveger.tiger.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import java.util.function.Function;

public interface ITable<K, V> {
    
    @Requires("key != null && value != null")
    @Ensures("!isEmpty() && (old(contains(key)) || size() == old(size()) + 1) "
    + "&& (!old(contains(key)) || old(keyList().size()) == keyList().size())")
    void set(final K key, final V value);

    @Requires("key != null")
    @Ensures("size() == old(size()) && (old(keyList().size()) == keyList().size())"
            + " && (!old(contains(key)) && result == null) "
            + "|| (old(contains(key)) && result != null)")
    V get(final K key);

    @Requires("key != null")
    @Ensures("(old(contains(key)) || old(keyList().size()) == keyList().size()) "
        + "&& (old(contains(key)) || size() == old(size())) "
        + "&& (!old(contains(key)) || old(keyList().size()) - 1 == keyList().size())"
        + " && (!old(contains(key)) || size() == old(size()) - 1)")
    void remove(final K key);

    @Ensures("isEmpty()")
    void clear();

    @Requires("key != null")
    @Ensures("size() == old(size()) && keyList().equals(old(keyList()))")
    Boolean contains(final K key);

    @Ensures("result == (size() == 0)")
    Boolean isEmpty();

    @Ensures("result >= 0")
    Integer size();

    @Ensures("result.equals(this) || result.isEmpty()")
    ITable<K, V> deepCopy();

    @Ensures("result != null && result.size() >= 0")
    IList<K> keyList();
}

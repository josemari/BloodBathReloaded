package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import org.jomaveger.tiger.util.lang.DeepCloneable;
import org.jomaveger.tiger.util.lang.ordering.NaturalOrder;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

@Invariant("checkInvariant()")
public class BinarySearchTree<K extends Comparable<K>, V>
            implements IBinarySearchTree<K, V>, Serializable {

    private Comparator<K> ordering;
    private BinaryNode<TableEntry<K, V>> root;

    private IList<TableEntry<K, V>> preorder;
    private IList<TableEntry<K, V>> inorder;
    private IList<TableEntry<K, V>> postorder;
    private IList<TableEntry<K, V>> levelorder;

    @Ensures("isEmpty()")
    public BinarySearchTree() {
        this.root = null;
        this.ordering = new NaturalOrder<K>();
    }

    @Requires("ordering != null")
    @Ensures("isEmpty()")
    public BinarySearchTree(Comparator<K> ordering) {
        this.root = null;
        this.ordering = ordering;
    }

    @Override
    public void put(K key, V value) {
        this.root = this.recPut(this.root, key, value);
    }

    private BinaryNode<TableEntry<K, V>> recPut(BinaryNode<TableEntry<K, V>> node, K key, V value) {
       if (node == null) {
            TableEntry<K, V> pair = new TableEntry<>(key, value);
            node = new BinaryNode<>();
            node.setElem(pair);
        }
        int cmp = ordering.compare(key, node.getElem().getKey());
        if (cmp < 0) {
            node.setLeft(this.recPut(node.getLeft(), key, value));
        } else if (cmp > 0) {
            node.setRight(this.recPut(node.getRight(), key, value));
        } else {
            node.getElem().setValue(value);
        }
        return node;
    }

    @Override
    public V get(K key) {
        V value = this.recGet(this.root, key);
        return value;
    }

    private V recGet(BinaryNode<TableEntry<K, V>> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = ordering.compare(key, node.getElem().getKey());
        if (cmp < 0) {
            return recGet(node.getLeft(), key);
        } else if (cmp > 0) {
            return recGet(node.getRight(), key);
        } else {
            return node.getElem().getValue();
        }
    }

    @Override
    public Boolean contains(K key) {
        Boolean contains = this.recContains(this.root, key);
        return contains;
    }

    private Boolean recContains(BinaryNode<TableEntry<K, V>> node, K key) {
        if (node == null) {
            return Boolean.FALSE;
        }
        int cmp = ordering.compare(key, node.getElem().getKey());
        if (cmp < 0) {
            return recContains(node.getLeft(), key);
        } else if (cmp > 0) {
            return recContains(node.getRight(), key);
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public void remove(K key) {
        this.root = this.recRemove(this.root, key);
    }

    private BinaryNode<TableEntry<K, V>> recRemove(BinaryNode<TableEntry<K, V>> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = ordering.compare(key, node.getElem().getKey());
        if (cmp < 0) {
            node.setLeft(this.recRemove(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(this.recRemove(node.getRight(), key));
        } else {
            if (node.getRight() == null) return node.getLeft();
            if (node.getLeft() == null) return node.getRight();
            BinaryNode<TableEntry<K, V>> t = node;
            node = min(t.getRight());
            node.setRight(deleteMin(t.getRight()));
            node.setLeft(t.getLeft());
        }
        return node;
    }

    private BinaryNode<TableEntry<K, V>> deleteMin(BinaryNode<TableEntry<K, V>> node) {
        if (node.getLeft() == null) return node.getRight();
        node.setLeft(deleteMin(node.getLeft()));
        return node;
    }

    private BinaryNode<TableEntry<K, V>> min(BinaryNode<TableEntry<K, V>> node) {
        if (node.getLeft() == null) return node;
        else return min(node.getLeft());
    }

    @Override
    public K min() {
        BinaryNode<TableEntry<K, V>> node = this.root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        K min = node.getElem().getKey();
        return min;
    }

    @Override
    public K max() {
        BinaryNode<TableEntry<K, V>> node = this.root;
        while (node.getRight() != null) {
            node = node.getRight();
        }
        K max = node.getElem().getKey();
        return max;
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = (this.root == null);
        return condition;
    }

    @Override
    public void makeEmpty() {
        this.root = null;
    }

    @Override
    public Boolean isBalanced() {
        Boolean isBalanced = this.recIsBalanced(this.root);
        return isBalanced;
    }

    private Boolean recIsBalanced(BinaryNode<TableEntry<K, V>> node) {
        Integer lh;
        Integer rh;

        if (node == null)
            return Boolean.TRUE;

        lh = recHeight(node.getLeft());
        rh = recHeight(node.getRight());

        if (Math.abs(lh - rh) <= 1
                && recIsBalanced(node.getLeft())
                && recIsBalanced(node.getRight()))
            return Boolean.TRUE;

        return Boolean.FALSE;
    }

    @Override
    public Integer height() {
        Integer result = this.recHeight(this.root);
        return result;
    }

    private Integer recHeight(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + Math.max(this.recHeight(node.getLeft()), this.recHeight(node.getRight()));
        }
    }

    @Override
    public Integer size() {
        Integer result = this.recSize(this.root);
        return result;
    }

    private Integer recSize(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + this.recSize(node.getLeft()) + this.recSize(node.getRight());
        }
    }

    @Override
    public Integer leaves() {
        Integer result = this.recLeaves(this.root);
        return result;
    }

    private Integer recLeaves(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        } else {
            return this.recLeaves(node.getLeft()) + this.recLeaves(node.getRight());
        }
    }

    @Override
    public IList<TableEntry<K, V>> preorder() {
        this.preorder = new LinkedList<>();
        if (this.isBalanced()) {
            this.preorder = this.recPreorder(this.root);
        } else {
           this.preorder = this.itPreorder(this.root);
        }
        return this.preorder;
    }

    private IList<TableEntry<K, V>> recPreorder(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return this.preorder;
        } else {
            this.preorder.addLast(node.getElem());
            this.recPreorder(node.getLeft());
            this.recPreorder(node.getRight());
        }

        return this.preorder;
    }

    private IList<TableEntry<K, V>> itPreorder(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return this.preorder;
        } else {
            IStack<BinaryNode<TableEntry<K, V>>> stack = new LinkedStack<>();
            stack.push(node);

            while (!stack.isEmpty()) {

                BinaryNode<TableEntry<K, V>> curr = stack.peek();
                stack.pop();

                this.preorder.addLast(curr.getElem());

                if (curr.getRight() != null) {
                    stack.push(curr.getRight());
                }

                if (curr.getLeft() != null) {
                    stack.push(curr.getLeft());
                }
            }

            return this.preorder;
        }
    }

    @Override
    public IList<TableEntry<K, V>> inorder() {
        this.inorder = new LinkedList<>();
        if (this.isBalanced()) {
            this.inorder = this.recInorder(this.root);
        } else {
            this.inorder = this.itInorder(this.root);
        }
        return this.inorder;
    }

    private IList<TableEntry<K, V>> itInorder(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return this.inorder;
        } else {
            IStack<BinaryNode<TableEntry<K, V>>> stack = new LinkedStack<>();
            BinaryNode<TableEntry<K, V>> curr = node;

            while (!stack.isEmpty() || curr != null) {

                if (curr != null) {
                    stack.push(curr);
                    curr = curr.getLeft();
                }
                else {
                    curr = stack.peek();
                    stack.pop();

                    this.inorder.addLast(curr.getElem());

                    curr = curr.getRight();
                }
           }

            return this.inorder;
       }
    }

    private IList<TableEntry<K, V>> recInorder(BinaryNode<TableEntry<K, V>> node) {
       if (node == null) {
            return this.inorder;
        } else {
            this.recInorder(node.getLeft());
            this.inorder.addLast(node.getElem());
            this.recInorder(node.getRight());
        }

        return this.inorder;
    }

    @Override
    public IList<TableEntry<K, V>> postorder() {
        this.postorder = new LinkedList<>();
        if (this.isBalanced()) {
            this.postorder = this.recPostorder(this.root);
        } else {
            this.postorder = this.itPostorder(this.root);
        }
        return this.postorder;
    }

    private IList<TableEntry<K, V>> itPostorder(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return this.postorder;
        } else {
            IStack<BinaryNode<TableEntry<K, V>>> stack = new LinkedStack<>();
            stack.push(node);

            while (!stack.isEmpty()) {

                BinaryNode<TableEntry<K, V>> curr = stack.peek();
                stack.pop();

                this.postorder.addFirst(curr.getElem());

                if (curr.getLeft() != null) {
                    stack.push(curr.getLeft());
                }

                if (curr.getRight() != null) {
                    stack.push(curr.getRight());
                }
            }

            return this.postorder;
        }
    }

    private IList<TableEntry<K, V>> recPostorder(BinaryNode<TableEntry<K, V>> node) {
        if (node == null) {
            return this.postorder;
        } else {
            this.recPostorder(node.getLeft());
            this.recPostorder(node.getRight());
            this.postorder.addLast(node.getElem());
        }

        return postorder;
    }

    @Override
    public IList<TableEntry<K, V>> levelorder() {
        this.levelorder = new LinkedList<>();
        BinaryNode<TableEntry<K, V>> next, child;

        if (!this.isEmpty()) {
            IQueue<BinaryNode<TableEntry<K, V>>> q = new LinkedQueue<>();
            q.enqueue(this.root);
            while (!q.isEmpty()) {
                next = q.front();
                q.dequeue();
                this.levelorder.addLast(next.getElem());
                child = next.getLeft();
                if (child != null) {
                    q.enqueue(child);
                }
                child = next.getRight();
                if (child != null) {
                    q.enqueue(child);
                }
            }
        }
        return this.levelorder;
    }

    @Override
    public IList<TableEntry<K, V>> getOrderedList() {
        IList<TableEntry<K, V>> orderedList = this.inorder();
        return orderedList;
    }

    @Override
    public IBinarySearchTree<K, V> deepCopy() {
        IBinarySearchTree<K, V> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new BinarySearchTree<>();
        }
        return deepCopy;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        for (TableEntry<K, V> elem: this.getOrderedList()) {
            string.append(elem.toString()).append(", ");
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        BinarySearchTree<K, V> that = (BinarySearchTree<K, V>) otherObject;

        if (!Objects.equals(this.size(), that.size()))
            return false;

        return Objects.equals(this.getOrderedList(), that.getOrderedList());
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size() + 1];
        array[0] = this.size();

        Integer i = 1;
        for (TableEntry<K, V> elem: this.getOrderedList()) {
            array[i] = elem;
            i++;
        }

        return Objects.hash(array);
    }

    private boolean checkInvariant() {
        Boolean isEmpty = this.root == null;
        return isBST(this.root, isEmpty ? null : this.minInv(),
                                isEmpty ? null : this.maxInv());
    }

    private boolean isBST(BinaryNode<TableEntry<K, V>> node, K min, K max) {
        if (node == null) {
            return true;
        }
        if (min != null && this.ordering.compare(node.getElem().getKey(), min) < 0) {
            return false;
        }
        if (max != null && this.ordering.compare(node.getElem().getKey(), max) > 0) {
            return false;
        }
        return isBST(node.getLeft(), min, node.getElem().getKey())
                && isBST(node.getRight(), node.getElem().getKey(), max);
    }

    private K minInv() {
        BinaryNode<TableEntry<K, V>> node = this.root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        K min = node.getElem().getKey();

        return min;
    }

    private K maxInv() {
        BinaryNode<TableEntry<K, V>> node = this.root;
        while (node.getRight() != null) {
            node = node.getRight();
        }
        K max = node.getElem().getKey();

        return max;
    }

    @Override
    public <T extends Comparable<T>> IList<T> sort(IList<T> list) {
        IBinarySearchTree<T, T> tree = new BinarySearchTree<>();

        while (!list.isEmpty()) {
            T elem = list.getFirst();
            list.removeFirst();
            tree.put(elem, elem);
        }

        IList<TableEntry<T, T>> orderedList = tree.getOrderedList();
        IList<T> resul = new LinkedList<>();

        for (TableEntry<T, T> entry : orderedList) {
            resul.addLast(entry.getKey());
        }
        return resul;
    }
}
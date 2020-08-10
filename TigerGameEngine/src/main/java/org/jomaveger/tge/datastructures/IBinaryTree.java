package org.jomaveger.tge.datastructures;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface IBinaryTree<T> {

    @Ensures("!isEmpty() || result == null")
    BinaryNode<T> getRoot();

    @Requires("!isEmpty()")
    BinaryNode<T> getLeftChild();

    @Requires("!isEmpty()")
    BinaryNode<T> getRightChild();

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
    IList<T> preorder();

    @Ensures("result.size() >= 0")
    IList<T> inorder();

    @Ensures("result.size() >= 0")
    IList<T> postorder();

    @Ensures("result.size() >= 0")
    IList<T> levelorder();

    @Ensures("result.equals(this) || result.isEmpty()")
    IBinaryTree<T> deepCopy();
}
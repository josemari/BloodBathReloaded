package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tge.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("checkInvariant()")
public class LinkedStack<T> implements IStack<T>, Serializable {

    private LinkedNode<T> top;
    private Integer size;

    @Ensures("isEmpty()")
    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(final T elem) {    	
        LinkedNode<T> newNode = new LinkedNode<T>(elem);
        newNode.setNext(this.top);
        this.top = newNode;
        this.size++;
    }

    @Override
    public void pop() {
        this.top = this.top.getNext();
        this.size--;
    }

    @Override
    public T peek() {
        T elem = this.top.getElem();
        return elem;
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = this.size == 0;
        return condition;
    }

    @Override
    public Integer size() {
        Integer size = this.size;        
        return size;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        LinkedNode<T> temp = this.top;
        while (temp != null) {
            string.append(temp.getElem()).append(", ");
            temp = temp.getNext();
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        LinkedStack<T> that = (LinkedStack<T>) otherObject;

        if (!Objects.equals(this.size, that.size)) return false;

        LinkedNode<T> tempThis = this.top;
        LinkedNode<T> tempThat = that.top;
        while (tempThis != null) {
            if (!Objects.equals(tempThis.getElem(), tempThat.getElem()))
                return false;
            tempThis = tempThis.getNext();
            tempThat = tempThat.getNext();
        }
        return true;
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size + 1];
        array[0] = this.size;

        LinkedNode<T> temp = this.top;
        Integer i = 1;
        while (temp != null && i < array.length) {
            array[i] = temp.getElem();
            i++;
            temp = temp.getNext();
        }

        return Objects.hash(array);
    }

    @Override
    public IStack<T> deepCopy() {
    	IStack<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new LinkedStack<>();
        }
        return deepCopy;
    }
    
    private boolean checkInvariant() {
        if (this.size < 0) {
            return false;
        }
        if (this.size == 0) {
            if (this.top != null) return false;
        }
        else if (this.size == 1) {
            if (this.top == null)      		return false;
            if (this.top.getNext() != null) return false;
        }
        else {
            if (this.top == null)      		return false;
            if (this.top.getNext() == null) return false;
        }

        int numberOfNodes = 0;
        for (LinkedNode<T> x = this.top; x != null && numberOfNodes <= this.size; x = x.getNext()) {
            numberOfNodes++;
        }
        if (numberOfNodes != this.size) return false;

        return true;
    }
}

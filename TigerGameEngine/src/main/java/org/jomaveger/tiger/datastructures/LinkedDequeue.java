package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tge.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("checkInvariant()")
public class LinkedDequeue<T> implements IDequeue<T>, Serializable {

    private Integer size;
    private DoublyLinkedNode<T> head;
    private DoublyLinkedNode<T> tail;
    
    @Ensures("isEmpty()")
    public LinkedDequeue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    @Override
    public void addFirst(final T elem) {
        this.head = new DoublyLinkedNode<>(elem, this.head, null);
        if (this.tail == null)
            this.tail = this.head;
        this.size++;
    }

    @Override
    public void addLast(final T elem) {
        this.tail = new DoublyLinkedNode<>(elem, null, this.tail);
        if (this.head == null)
            this.head = this.tail;
        this.size++;
    }

    @Override
    public T getFirst() {
        return this.head.getElem();
    }

    @Override
    public T getLast() {
        return this.tail.getElem();
    }

    @Override
    public void removeFirst() {
        DoublyLinkedNode<T> temp = this.head;
        this.head = this.head.getNext();
        if (this.head != null) {
            this.head.setPrevious(null);
        } else {
            this.tail = null;
        }
        temp.setNext(null);
        this.size--;
    }

    @Override
    public void removeLast() {
        DoublyLinkedNode<T> temp = this.tail;
        this.tail = this.tail.getPrevious();
        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.setNext(null);
        }
        this.size--;
    }

    @Override
    public Boolean contains(T elem) {
        DoublyLinkedNode<T> finger = this.head;
        while ((finger != null) && (!finger.getElem().equals(elem))) {
            finger = finger.getNext();
        }
        boolean condition = finger != null;
        return condition;
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = this.size == 0;
        return condition;
    }

    @Override
    public Integer size() {
        return this.size;
    }

    @Override
    public IDequeue<T> deepCopy() {
        IDequeue<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new LinkedDequeue<>();
        }
        return deepCopy;
    }
    
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        DoublyLinkedNode<T> temp = this.head;
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

        LinkedDequeue<T> that = (LinkedDequeue<T>) otherObject;

        if (!Objects.equals(this.size, that.size)) return false;

        DoublyLinkedNode<T> tempThis = this.head;
        DoublyLinkedNode<T> tempThat = that.head;
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

        DoublyLinkedNode<T> temp = this.head;
        Integer i = 1;
        while (temp != null && i < array.length) {
            array[i] = temp.getElem();
            i++;
            temp = temp.getNext();
        }

        return Objects.hash(array);
    }

    private boolean checkInvariant() {
        if (this.size < 0) {
            return false;
        }
        if (this.size == 0) {
            if (this.head != null) return false;
            if (this.tail != null) return false;
        }
        else if (this.size == 1) {
            if (this.head == null)      return false;
            if (this.head != this.tail) return false;
        }
        else {
            if (this.head == null)               return false;
            if (this.tail == null)               return false;
            if (this.head.getPrevious() != null) return false;
            if (this.tail.getNext() != null)     return false;
        }

        int numberOfNodes = 0;
        for (DoublyLinkedNode<T> x = this.head; x != null && numberOfNodes <= this.size; x = x.getNext()) {
            numberOfNodes++;
        }
        if (numberOfNodes != this.size) return false;

        return true;
    }
}

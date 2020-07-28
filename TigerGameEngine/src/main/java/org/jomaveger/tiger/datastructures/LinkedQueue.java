package org.jomaveger.tiger.datastructures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.tiger.util.lang.DeepCloneable;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("checkInvariant()")
public class LinkedQueue<T> implements IQueue<T>, Serializable {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private Integer size;

    @Ensures("isEmpty()")
    public LinkedQueue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public void enqueue(final T elem) {
        LinkedNode<T> oldLast = this.last;
        this.last = new LinkedNode<>(elem);
        if (this.size == 0)
            this.first = this.last;
        else
            oldLast.setNext(this.last);
        this.size++;
    }

    @Override
    public void dequeue() {    	
        this.first = this.first.getNext();
        this.size--;
        if (this.size == 0)
            this.last = null;
    }

    @Override
    public T front() {    	
    	T elem = this.first.getElem();
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
    public IQueue<T> deepCopy() {
    	IQueue<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new LinkedQueue<>();
        }
        return deepCopy;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        LinkedNode<T> temp = this.first;
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

        LinkedQueue<T> that = (LinkedQueue<T>) otherObject;

        if (!Objects.equals(this.size, that.size)) return false;

        LinkedNode<T> tempThis = this.first;
        LinkedNode<T> tempThat = that.first;
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

        LinkedNode<T> temp = this.first;
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
        else if (this.size == 0) {
            if (this.first != null) return false;
            if (this.last  != null) return false;
        }
        else if (this.size == 1) {
            if (this.first == null || this.last == null) return false;
            if (this.first != this.last)                 return false;
            if (this.first.getNext() != null)            return false;
        }
        else {
            if (this.first == null || this.last == null) return false;
            if (this.first == this.last)      			 return false;
            if (this.first.getNext() == null) 			 return false;
            if (this.last.getNext() != null) 			 return false;

            int numberOfNodes = 0;
            for (LinkedNode<T> x = this.first; x != null && numberOfNodes <= this.size; x = x.getNext()) {
                numberOfNodes++;
            }
            if (numberOfNodes != this.size) return false;

            LinkedNode<T> lastNode = this.first;
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }
            if (this.last != lastNode) return false;
        }

        return true;
    }
}

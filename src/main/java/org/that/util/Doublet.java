package org.that.util;

public class Doublet<T, U> {
    T head;
    U support;

    public Doublet(T _head, U _support) {
        this.head = _head;
        this.support = _support;
    }

    public T getHead() {
        return head;
    }

    public void setHead(T head) {
        this.head = head;
    }

    public U getSupport() {
        return support;
    }

    public void setSupport(U support) {
        this.support = support;
    }

}

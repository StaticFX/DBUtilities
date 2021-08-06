package de.staticred.dbv2.util;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class DoubleOptional<A, B> {

    private A a;
    private B b;

    public DoubleOptional(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public boolean isAPresent() {
        return a != null;
    }

    public boolean isBPresent() {
        return b != null;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }






}

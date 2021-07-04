package de.staticred.dbv2.util.tuple;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class Triple<T, S, G> {

    private final T left;

    private final S middle;

    private final G right;

    public Triple(T left, S middle, G right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public S getMiddle() {
        return middle;
    }

    public G getRight() {
        return right;
    }
}

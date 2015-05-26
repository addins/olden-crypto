package org.addin.crypto.classic.core;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SimpleKey<T> {
    private T key;

    public SimpleKey(T key) {
        this.key = key;
    }

    public SimpleKey() {
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }
}

package org.addin.crypto.classic.core;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public interface Encipherment<T> {
    public T encrypt(T plainText);
    public T decrypt(T cipherText);
    public void setKey(SimpleKey key);
}

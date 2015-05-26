package org.addin.crypto.classic.image;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public interface ImageEncipherment<K> {
    public static final int ENCRYPT_MODE = 0;
    public static final int DECRYPT_MODE = 1;
    
    public void setKey(K key);
    public void process(int mode);
}

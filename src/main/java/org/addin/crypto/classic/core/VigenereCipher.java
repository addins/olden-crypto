package org.addin.crypto.classic.core;

import org.addin.crypto.classic.core.exception.InappropriateKeyException;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class VigenereCipher implements Encipherment<int[]> {

    private SimpleKey<int[]> key;
    private final int elementDomain;

    public VigenereCipher(int elementDomain) {
        this.elementDomain = elementDomain;
    }

    @Override
    public int[] encrypt(int[] plainText) {
        if (plainText == null
                || plainText.length == 0) {
            return null;
        }

        isKeySet();

        int[] cipher = new int[plainText.length];

        int diff = 0;
        for (int i = 0; i < plainText.length; i++) {
            diff = 0;
            diff = plainText[i] + key.getKey()[i % key.getKey().length];
            if (diff > elementDomain) {
                diff = diff - elementDomain;
            }
            cipher[i] = (diff) % elementDomain;
        }

        return cipher;
    }

    @Override
    public int[] decrypt(int[] cipherText) {
        if (cipherText == null
                || cipherText.length == 0) {
            return null;
        }

        isKeySet();

        int[] plain = new int[cipherText.length];

        int diff = 0;
        for (int i = 0; i < cipherText.length; i++) {
            diff = 0;
            diff = cipherText[i] - key.getKey()[i % key.getKey().length];
            if (diff < 0) {
                diff = elementDomain + diff;
            }
            plain[i] = (diff) % elementDomain;
        }
        return plain;
    }

    @Override
    public void setKey(SimpleKey key) {
        this.key = key;
        isKeySet();
    }

    private void isKeySet() {
        if (key == null
                || key.getKey() == null
                || key.getKey().length == 0) {
            throw new InappropriateKeyException("Set the key first.");
        }
    }

}

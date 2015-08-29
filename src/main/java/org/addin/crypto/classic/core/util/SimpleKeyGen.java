package org.addin.crypto.classic.core.util;

import java.security.SecureRandom;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SimpleKeyGen extends SimpleKeyShuffling {

    /**
     * implementation of Fisher-Yates Shuffle
     * @param domain
     * @return 
     */
    private int[] shufflingElements() {
        SecureRandom sr = new SecureRandom();
        for (int i = domain.length - 1; i > 0; i--) {
            int idx = sr.nextInt(domain.length);
            int a = domain[idx];
            domain[idx] = domain[i];
            domain[i] = a;
        }
        return domain;
    }
    
    public SimpleKeyGen(int size) {
        super(size, IntDomainCreator.getIntFromZeroTo(size*size));
    }

    @Override
    public int[] shuffling() {
        shufflingElements();
        return domain;
    }
}

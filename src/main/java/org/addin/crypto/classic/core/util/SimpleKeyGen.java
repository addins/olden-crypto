package org.addin.crypto.classic.core.util;

import java.security.SecureRandom;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SimpleKeyGen implements SimpleKeyShuffling {

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
    
    private int size;
    private int[] domain;
    private int[] shuffledDomain;

    public SimpleKeyGen(int size) {
        this.size = size;
    }

    @Override
    public int[] shuffling() {
        this.shuffledDomain = shufflingElements();
        return this.shuffledDomain;
    }

    @Override
    public int[][] generateMatrix() {
        domain = IntDomainCreator.getIntFromZeroTo(size*size);
        shuffling();
        
        int[][] mtx = new int[size][size];
        for (int i = 0, idx = 0; i < mtx.length; i++) {
            for (int j = 0; j < mtx[i].length; j++, idx++) {
                mtx[i][j] = domain[idx];
            }            
        }
        return mtx;
    }
}

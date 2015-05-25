package org.addin.crypto.classic.core.util;

import java.security.SecureRandom;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SimpleKeyGen {
    public static int[][] generateSquareMatrix(int size){
        int[] domain = getIntFromZeroTo(size*size);
        shufflingElements(domain);
        
        int[][] mtx = new int[size][size];
        for (int i = 0, idx = 0; i < mtx.length; i++) {
            for (int j = 0; j < mtx[i].length; j++, idx++) {
                mtx[i][j] = domain[idx];
            }            
        }
        return mtx;
    }

    public static int[] getIntFromZeroTo(int to) {
        int[] output = new int[to];
        for (int i = 0; i < to; i++) {
            output[i] = i;
        }
        return output;
    }

    /**
     * implementation of Fisher-Yates Shuffle
     * @param input
     * @return 
     */
    public static int[] shufflingElements(int[] input) {
        SecureRandom sr = new SecureRandom();
        for (int i = input.length - 1; i > 0; i--) {
            int idx = sr.nextInt(input.length);
            int a = input[idx];
            input[idx] = input[i];
            input[i] = a;
        }
        return input;
    }
}

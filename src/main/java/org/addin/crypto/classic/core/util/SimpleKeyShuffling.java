package org.addin.crypto.classic.core.util;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public abstract class SimpleKeyShuffling {
    protected int size;
    protected int[] domain;

    public SimpleKeyShuffling(int size) {
        this.size = size;
    }

    public SimpleKeyShuffling(int size, int[] domain) {
        this.size = size;
        this.domain = domain;
    }
    
    protected abstract int[] shuffling();
    
    public int[][] generateMatrix(){
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

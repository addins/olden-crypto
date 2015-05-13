/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.addin.crypto.classic.image;

import org.addin.crypto.classic.core.Encipherment;
import org.addin.crypto.classic.core.SimpleKey;
import org.addin.crypto.classic.core.VigenereCipher;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class TwoDimensionsVigenereCipher implements Encipherment<int[][]>{

    private VigenereCipher cipher;

    public TwoDimensionsVigenereCipher() {
        cipher = new VigenereCipher(256);
    }
    
    @Override
    public int[][] encrypt(int[][] plainText) {
        int[][] res = new int[plainText.length][plainText[0].length];
        int i=0;
        for(int[] e : plainText){
            res[i] = cipher.encrypt(e);
            i++;
        }
        return res;
    }

    @Override
    public int[][] decrypt(int[][] cipherText) {
        int[][] res = new int[cipherText.length][cipherText[0].length];
        int i=0;
        for(int[] e : cipherText){
            res[i] = cipher.decrypt(e);
            i++;
        }
        return res;
    }

    @Override
    public void setKey(SimpleKey key) {
        cipher.setKey(key);
    }
    
}

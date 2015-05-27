/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.addin.crypto.classic.image;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineInt;
import org.addin.crypto.classic.core.SimpleKey;
import org.addin.crypto.classic.core.util.SimpleKeyGen;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class ImageLineIntSuperEncryptionTest {
    
    public ImageLineIntSuperEncryptionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of encrypt method, of class ImageLineIntSuperEncryption.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        ImageLineInt plainText = null;
        ImageLineIntSuperEncryption instance = null;
        ImageLineInt expResult = null;
        ImageLineInt result = instance.encrypt(plainText);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrypt method, of class ImageLineIntSuperEncryption.
     */
    @Test
    public void testDecrypt() {
        System.out.println("decrypt");
        ImageLineInt cipherText = null;
        ImageLineIntSuperEncryption instance = null;
        ImageLineInt expResult = null;
        ImageLineInt result = instance.decrypt(cipherText);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setKey method with zero at first element of the matrix, 
     * it should be no problem with vigenere cipher. (if you know how the key
     * for the vigenere cipher created... :-))
     * of class ImageLineIntSuperEncryption.
     */
    @Test
    public void testSetKey() {
        System.out.println("setKey");
        int[][] kM = SimpleKeyGen.generateSquareMatrix(16);
        swapZeroToFirst(kM);
        SimpleKey key = new SimpleKey(kM);
        ImageLineIntSuperEncryption instance = new ImageLineIntSuperEncryption(
                new ImageInfo(1, 1, 8, false));
        instance.setKey(key);
    }
    
    private static int[][] swapZeroToFirst(int[][] inp){
        int first = inp[0][0];
        for (int i = 0; i < inp.length; i++) {
            for (int j = 0; j < inp[i].length; j++) {
                if(inp[i][j]==0){
                    inp[0][0] = 0;
                    inp[i][j] = first;
                    i = inp.length;
                    break;
                }                    
            }            
        }
        return inp;
    }
    
}

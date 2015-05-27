/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.addin.crypto.classic.image;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
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

    ImageInfo imageInfo;
    SimpleKey<int[][]> key;

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
        int cols = 5, rows = 1, bitDepth = 8;
        boolean isAlpha = false;
        imageInfo = new ImageInfo(cols, rows, bitDepth, isAlpha);

        key = new SimpleKey<>(new int[][]{
            {189, 97, 179, 133, 30, 119, 200, 198, 230, 202, 199, 155, 24, 148, 4, 92},
            {188, 175, 232, 66, 11, 211, 32, 218, 7, 243, 167, 126, 20, 121, 60, 23},
            {62, 140, 123, 245, 184, 25, 31, 116, 0, 124, 109, 161, 10, 234, 145, 108},
            {88, 61, 222, 196, 237, 117, 37, 35, 252, 181, 26, 93, 152, 22, 185, 89},
            {170, 228, 154, 221, 6, 169, 225, 177, 130, 159, 238, 250, 136, 172, 15, 149},
            {3, 64, 186, 164, 49, 192, 41, 100, 29, 219, 51, 85, 180, 183, 162, 201},
            {156, 105, 107, 86, 247, 115, 194, 249, 241, 137, 73, 5, 47, 14, 103, 74},
            {1, 208, 16, 253, 8, 120, 106, 44, 110, 246, 254, 53, 72, 206, 52, 91},
            {57, 143, 157, 226, 12, 216, 101, 150, 229, 2, 171, 231, 43, 84, 134, 95},
            {242, 227, 158, 125, 165, 135, 118, 55, 59, 39, 87, 197, 99, 178, 195, 214},
            {79, 132, 217, 147, 236, 36, 122, 34, 151, 235, 81, 160, 46, 174, 210, 142},
            {205, 166, 96, 251, 209, 224, 129, 18, 240, 48, 182, 21, 112, 40, 65, 76},
            {75, 82, 50, 207, 128, 17, 69, 239, 102, 168, 187, 63, 111, 144, 153, 9},
            {42, 131, 113, 204, 94, 98, 28, 90, 104, 78, 176, 193, 38, 83, 191, 212},
            {139, 215, 33, 138, 190, 54, 127, 70, 163, 203, 67, 68, 141, 71, 223, 220},
            {233, 114, 213, 173, 58, 19, 13, 56, 27, 248, 45, 80, 244, 146, 77, 255}
        });
    }

    @After
    public void tearDown() {
        imageInfo = null;
        key = null;
    }

    /**
     * Test of encrypt method, of class ImageLineIntSuperEncryption.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        
        ImageLineInt plainText = createPlainImageLine();
        
        ImageLineIntSuperEncryption instance = new ImageLineIntSuperEncryption(imageInfo);
        instance.setKey(key);
        
        ImageLineInt expResult = createCipherImageLine();
        
        ImageLineInt result = instance.encrypt(plainText);
        assertEquals(expResult, result);
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
     * Test of setKey method with zero at first element of the matrix, it should
     * be no problem with vigenere cipher. (if you know how the key for the
     * vigenere cipher created... :-)) of class ImageLineIntSuperEncryption.
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

    private static int[][] swapZeroToFirst(int[][] inp) {
        int first = inp[0][0];
        for (int i = 0; i < inp.length; i++) {
            for (int j = 0; j < inp[i].length; j++) {
                if (inp[i][j] == 0) {
                    inp[0][0] = 0;
                    inp[i][j] = first;
                    i = inp.length;
                    break;
                }
            }
        }
        return inp;
    }

    /**
     * create plain image to be used with the determined key
     * 5 cols image
     * @return 
     */
    private ImageLineInt createPlainImageLine() {
        ImageLineInt ili = new ImageLineInt(imageInfo);
        
        ImageLineHelper.setPixelRGB8(ili, 0, 255, 127, 0);
        ImageLineHelper.setPixelRGB8(ili, 1, 255, 127, 51);
        ImageLineHelper.setPixelRGB8(ili, 2, 255, 127, 102);
        ImageLineHelper.setPixelRGB8(ili, 3, 255, 127, 103);
        ImageLineHelper.setPixelRGB8(ili, 4, 255, 127, 204);
        
        return ili;
    }

    /**
     * create cipher image that manually calculated with the determined key
     * 5 cols image
     * @return 
     */
    private ImageLineInt createCipherImageLine() {
        ImageLineInt ili = new ImageLineInt(imageInfo);
        
        ImageLineHelper.setPixelRGB8(ili, 0, 97, 117, 97);
        ImageLineHelper.setPixelRGB8(ili, 1, 179, 166, 4);
        ImageLineHelper.setPixelRGB8(ili, 2, 133, 85, 237);
        ImageLineHelper.setPixelRGB8(ili, 3, 30, 73, 81);
        ImageLineHelper.setPixelRGB8(ili, 4, 230, 229, 145);
        
        return ili;
    }
}

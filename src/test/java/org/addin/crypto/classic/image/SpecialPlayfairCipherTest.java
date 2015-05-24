package org.addin.crypto.classic.image;

import org.addin.crypto.classic.core.SimpleKey;
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
public class SpecialPlayfairCipherTest {
    
    SimpleKey<int[][]> key;
    
    public SpecialPlayfairCipherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        key = new SimpleKey<>();
        key.setKey(new int[][]{
            {'F','E','D','C'},
            {'B','A','9','8'},
            {'7','6','5','4'},
            {'3','2','1','0'}
        });
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertBogusBetweenTwinAndAtOddEnd method, of class SpecialPlayfairCipher.
     */
    @Test
    public void testInsertBogusBetweenTwinAndAtOddEnd() {
        System.out.println("insertBogusBetweenTwinAndAtOddEnd");
        int[] input = new int[]{85,56,45};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(256);
        int[] expResult = new int[]{85,56,45,0};
        int[] result = instance.insertBogusBetweenTwinAndAtOddEnd(input);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of removeBogus method, of class SpecialPlayfairCipher.
     */
    @Test
    public void testRemoveBogus() {
        System.out.println("removeBogus");
        int[] input = new int[]{85,56,45,0};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(256);
        int[] expResult = new int[]{85,56,45};
        int[] result = instance.removeBogus(input);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of onEqualValueEncrypt method, of class SpecialPlayfairCipher.
     */
    @Test
    public void testOnEqualValueEncrypt() {
        System.out.println("onEqualValueEncrypt");
        int[] digraph = new int[]{3,3};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(256);
        int[] expResult = new int[]{3,3};
        int[] result = instance.onEqualValueEncrypt(digraph);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of onEqualValueDecrypt method, of class SpecialPlayfairCipher.
     */
    @Test
    public void testOnEqualValueDecrypt() {
        System.out.println("onEqualValueDecrypt");
        int[] digraph = new int[]{3,3};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(256);
        int[] expResult = new int[]{3,3};
        int[] result = instance.onEqualValueDecrypt(digraph);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of encrypt method to encrypt plaintext that has repeated char
     * and odd length, of class PlayfairCipher.
     * 
     */
    @Test
    public void testEncrypt(){
        System.out.println("encrypt plaintext that has repeated char and odd length");
        int[] plainText = new int[]{'A','7','7','2','D','D','D'};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(16);
        instance.setBogusDomain('A');
        instance.setKey(key);
        int[] expResult = new int[]{'B','6','6','3','D','D','E','9'};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of decrypt method to decrypt ciphertext that the plaintext 
     * has repeated char and odd length, of class PlayfairCipher.
     * 
     */
    @Test
    public void testDecrypt(){
        System.out.println("decrypt ciphertext that has repeated char and odd length");
        int[] cipherText = new int[]{'B','6','6','3','D','D','E','9'};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(16);
        instance.setBogusDomain('A');
        instance.setKey(key);
        int[] expResult = new int[]{'A','7','7','2','D','D','D'};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }
    
}

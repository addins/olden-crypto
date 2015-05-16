package org.addin.crypto.classic.core;

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
public class VigenereCipherTest {
    
    SimpleKey<int[]> simpleKey;
    
    public VigenereCipherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        simpleKey = new SimpleKey();
        simpleKey.setKey(new int[]{3,2});
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of setKey method, of class VigenereCipher.
     */
    @Test
    public void testSetKey() {
        System.out.println("setKey");
        SimpleKey key = new SimpleKey();
        VigenereCipher instance = new VigenereCipher(26);
        instance.setKey(key);
        fail("design need to refined.");
    }

    /**
     * Test of encrypt method, of class VigenereCipher.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        int[] plainText = new int[]{1,2,3,4,5,0};
        VigenereCipher instance = new VigenereCipher(26);        
        instance.setKey(simpleKey);
        int[] expResult = new int[]{4,4,6,6,8,2};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of decrypt method, of class VigenereCipher.
     */
    @Test
    public void testDecrypt() {
        System.out.println("decrypt");
        int[] cipherText = new int[]{4,4,6,6,8,2};
        VigenereCipher instance = new VigenereCipher(26);
        instance.setKey(simpleKey);
        int[] expResult = new int[]{1,2,3,4,5,0};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }
    
}

package org.addin.crypto.classic.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.addin.crypto.classic.core.exception.InappropriateKeyException;

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
     * Test of setKey method with invalid key, SimpleKey without int array set, 
     * of class VigenereCipher.
     */
    @Test(expected = InappropriateKeyException.class)
    public void testSetKeyWithInvalidKey() {
        System.out.println("setKey with invalid key");
        SimpleKey key = new SimpleKey();
        VigenereCipher instance = new VigenereCipher(26);
        instance.setKey(key);
    }
    
    /**
     * Test of setKey method with valid key, of class VigenereCipher.
     */
    @Test
    public void testSetKeyWithValidKey() {
        System.out.println("setKey with valid key");
        SimpleKey key = new SimpleKey();
        key.setKey(new int[]{1,3,4,6});
        VigenereCipher instance = new VigenereCipher(26);
        instance.setKey(key);
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
     * Test of encrypt method without key, of class VigenereCipher.
     */
    @Test(expected = InappropriateKeyException.class)
    public void testEncryptWithoutKey() {
        System.out.println("encrypt without key set");
        int[] plainText = new int[]{1,2,3,4,5,0};
        VigenereCipher instance = new VigenereCipher(26);
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
    
    /**
     * Test of decrypt method without key, of class VigenereCipher.
     */
    @Test(expected = InappropriateKeyException.class)
    public void testDecryptWithoutKey() {
        System.out.println("decrypt without key set");
        int[] cipherText = new int[]{4,4,6,6,8,2};
        VigenereCipher instance = new VigenereCipher(26);
        int[] expResult = new int[]{1,2,3,4,5,0};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }
}

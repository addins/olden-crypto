package org.addin.crypto.classic.core;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class KeyStreamVigenereCipherTest {
    
    SimpleKey<int[]> simpleKey;
    
    public KeyStreamVigenereCipherTest() {
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
     * Test of encrypt method, of class KeyStreamVigenereCipher.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        int[] plainText = new int[]{1,2,3,4,5,0,23};
        KeyStreamVigenereCipher instance = new KeyStreamVigenereCipher(26);        
        instance.setKey(simpleKey);
        int[] expResult = new int[]{4,4,8,11,17,19,2};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of decrypt method, of class KeyStreamVigenereCipher.
     */
    @Test
    public void testDecrypt() {
        System.out.println("decrypt");
        int[] cipherText = new int[]{4,4,8,11,17,19,2};
        KeyStreamVigenereCipher instance = new KeyStreamVigenereCipher(26);
        instance.setKey(simpleKey);
        int[] expResult = new int[]{1,2,3,4,5,0,23};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }
    
}

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
public class LetterVigenereCipherTest {
    
    String key = "";
    
    public LetterVigenereCipherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        key = "LEMONLEMONLE";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of encrypt method, of class LetterVigenereCipher.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        String plainText = "ATTACKATDAWN";
        LetterVigenereCipher instance = new LetterVigenereCipher();
        instance.setKey(key);
        String expResult = "LXFOPVEFRNHR";
        String result = instance.encrypt(plainText);
        assertEquals(expResult, result);
    }

    /**
     * Test of decrypt method, of class LetterVigenereCipher.
     */
    @Test
    public void testDecrypt() {
        System.out.println("decrypt");
        String cipherText = "LXFOPVEFRNHR";
        LetterVigenereCipher instance = new LetterVigenereCipher();
        instance.setKey(key);
        String expResult = "ATTACKATDAWN";
        String result = instance.decrypt(cipherText);
        assertEquals(expResult, result);
    }

    /**
     * Test of setKey method with invalid key, SimpleKey without int array set, 
     * of class LetterVigenereCipher.
     */
    @Test(expected = InappropriateKeyException.class)
    public void testSetKeyWithEmptyString() {
        System.out.println("setKey with empty string");
        String key = "";
        LetterVigenereCipher instance = new LetterVigenereCipher();
        instance.setKey(key);
    }
    
    /**
     * Test of setKey method, of class LetterVigenereCipher.
     */
    @Test
    public void testSetKeyWithAnyString() {
        System.out.println("setKey with any string");
        String key = "abcd";
        LetterVigenereCipher instance = new LetterVigenereCipher();
        instance.setKey(key);
    }    
}

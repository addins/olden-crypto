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
public class LetterPlayfairCipherTest {
    
    String key = "";
    
    public LetterPlayfairCipherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        key = "playfirexmbcdghknoqstuvwz";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of encrypt method, of class LetterPlayfairCipher.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        String plainText = "Hidethegoldinthetreestump";
        LetterPlayfairCipher instance = new LetterPlayfairCipher();
        instance.setKey(key);
        String expResult = "BMODZBXDNABEKUDMUIXMMOUVIF";
        String result = instance.encrypt(plainText);
        assertEquals(expResult, result);
    }

    /**
     * Test of decrypt method, of class LetterPlayfairCipher.
     */
    @Test
    public void testDecrypt() {
        System.out.println("decrypt");
        String cipherText = "BMODZBXDNABEKUDMUIXMMOUVIF";
        LetterPlayfairCipher instance = new LetterPlayfairCipher();
        instance.setKey(key);
        String expResult = "HIDETHEGOLDINTHETREESTUMP";
        String result = instance.decrypt(cipherText);
        assertEquals(expResult, result);
    }

    /**
     * Test of setKey method, of class LetterPlayfairCipher.
     */
    @Test
    public void testSetKey() {
        System.out.println("setKey");
        
        LetterPlayfairCipher instance = new LetterPlayfairCipher();
        instance.setKey(key);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

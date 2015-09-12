package org.addin.crypto.classic.core;

import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
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
@TestClassReport(description = "Test class untuk VigenereCipher")
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
    @TestSingleReport(description = "set key dengan INVALID key (key kosong)", 
            expectations = "melemparkan eksepsi InappropriateKeyException")
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
    @TestSingleReport(description = "set key dengan VALID key {1,3,4,6}", 
            expectations = "tidak ada nilai balik dan eksepsi")
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
    @TestSingleReport(description = "melakukan enkripsi plain text {1,2,3,4,5,0}, "
            + "dengan key {3,2}", 
            expectations = "menghasilkan cipher text {4,4,6,6,8,2}")
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
    @TestSingleReport(description = "melakukan enkripsi plain text {1,2,3,4,5,0}, "
            + "TANPA key", 
            expectations = "melemparkan eksepsi InappropriateKeyException")
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
    @TestSingleReport(description = "melakukan dekripsi cipher text {4,4,6,6,8,2}, "
            + "dengan key {3,2}", 
            expectations = "menghasilkan plain text {1,2,3,4,5,0}")
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
    @TestSingleReport(description = "melakukan dekripsi cipher text {1,2,3,4,5,0}, "
            + "TANPA key", 
            expectations = "melemparkan eksepsi InappropriateKeyException")
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

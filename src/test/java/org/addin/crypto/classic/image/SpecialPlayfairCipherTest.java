package org.addin.crypto.classic.image;

import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
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
@TestClassReport(description = "Test class untuk SpecialPlayfairCipher")
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
            {15,14,13,12},
            {11,10,9,8},
            {7,6,5,4},
            {3,2,1,0}
        });
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertBogusBetweenTwinAndAtOddEnd method, of class SpecialPlayfairCipher.
     */
    @TestSingleReport(description = "menggenapkan data ganjil {85,56,45}",
            expectations = "data hasil penggenapan adalah {85,56,45,45}")
    @Test
    public void testInsertBogusBetweenTwinAndAtOddEnd() {
        System.out.println("insertBogusBetweenTwinAndAtOddEnd");
        int[] input = new int[]{85,56,45};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(256);
        int[] expResult = new int[]{85,56,45,45};
        int[] result = instance.insertBogusBetweenTwinAndAtOddEnd(input);
        assertArrayEquals(expResult, result);
    }


    /**
     * Test of onEqualValueEncrypt method, of class SpecialPlayfairCipher.
     */
    @TestSingleReport(description = "mengenkripsi plaintext kembar {3,3}",
            expectations = "data kembar tidak berubah")
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
    @TestSingleReport(description = "mengdekripsi ciphertext kembar {3,3}",
            expectations = "data kembar tidak berubah")
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
    @TestSingleReport(description = "mengenkripsi plaintext {10,7,7,2,13,13,13}",
            expectations = "ciphertext sama dengan {11,6,6,3,13,13,13}")
    @Test
    public void testEncrypt(){
        System.out.println("encrypt plaintext that has repeated char and odd length");
        int[] plainText = new int[]{10,7,7,2,13,13,13};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(16);
        instance.setBogusDomain(10);
        instance.setKey(key);
        int[] expResult = new int[]{11,6,6,3,13,13,13};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }
    
    @TestSingleReport(description = "mengenkripsi plaintext dengan digraph kembar {10,7,7,7,13,13,13}",
            expectations = "ciphertext sama dengan {11,6,7,7,13,13,13}")
    @Test
    public void testEncryptDuplicate(){
        System.out.println("encrypt plaintext that has repeated char and odd length");
        int[] plainText = new int[]{10,7,7,7,13,13,13};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(16);
        instance.setBogusDomain(10);
        instance.setKey(key);
        int[] expResult = new int[]{11,6,7,7,13,13,13};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of decrypt method to decrypt ciphertext that the plaintext 
     * has repeated char and odd length, of class PlayfairCipher.
     * 
     */
    @TestSingleReport(description = "mengenkripsi ciphertext {11,6,6,3,13,13,14,9} "
            + "yang plaintext-nya memiliki digraph kembar",
            expectations = "plaintext sama dengan {10,7,7,2,13,13,13,10}")
    @Test
    public void testDecrypt(){
        System.out.println("decrypt ciphertext that has repeated char and odd length");
        int[] cipherText = new int[]{11,6,6,3,13,13,14,9};
        SpecialPlayfairCipher instance = new SpecialPlayfairCipher(16);
        instance.setBogusDomain(10);
        instance.setKey(key);
        int[] expResult = new int[]{10,7,7,2,13,13,13,10};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }
    
}

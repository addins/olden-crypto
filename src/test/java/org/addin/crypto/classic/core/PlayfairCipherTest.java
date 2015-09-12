package org.addin.crypto.classic.core;

import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
import org.addin.crypto.classic.core.exception.InappropriateKeyException;
import org.addin.crypto.classic.core.exception.InvalidBogusDomainException;
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
@TestClassReport(description = "Test class untuk PlayfairCipher")
public class PlayfairCipherTest {
    
    SimpleKey<int[][]> key;
    
    public PlayfairCipherTest() {
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
     * Test of encrypt method to encrypt even length plaintext, 
     * of class PlayfairCipher.
     * domainElement = 
     * {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}
     */
    @TestSingleReport(description = "melakukan enkripsi plain text {'A','7','2','D'}"
            + " (jumlah karakter genap) dengan key "
            + "{'F','E','D','C'},\n" 
            +"{'B','A','9','8'},\n" 
            +"{'7','6','5','4'},\n" 
            +"{'3','2','1','0'}"
            , expectations = "menghasilkan cipher text {'B','6','1','E'}")
    @Test
    public void testEncryptEvenPlaintext() {
        System.out.println("encrypt even length plaintext");
        int[] plainText = new int[]{'A','7','2','D'};
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setBogusDomain('A');
        instance.setKey(key);
        int[] expResult = new int[]{'B','6','1','E'};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of encrypt method to encrypt plaintext that has repeated char, 
     * of class PlayfairCipher.
     * 
     */
    @TestSingleReport(description = "melakukan enkripsi plain text {'A','7','7','2','D','C'}"
            + " dengan key "
            + "{'F','E','D','C'},\n" 
            +"{'B','A','9','8'},\n" 
            +"{'7','6','5','4'},\n" 
            +"{'3','2','1','0'}"
            , expectations = "menghasilkan cipher text {'B','6','1','E'}")
    @Test
    public void testEncryptRepeatedCharPlaintext() {
        System.out.println("encrypt plaintext that has repeated char");
        int[] plainText = new int[]{'A','7','7','2','D','C'};
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setBogusDomain('A');
        instance.setKey(key);
        int[] expResult = new int[]{'B','6','B','6','1','E','E','8'};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of encrypt method to encrypt plaintext without setting bogus domain, 
     * of class PlayfairCipher.
     * 
     */
    @TestSingleReport(description = "melakukan enkripsi plain text {'A','7','7','2','D','C'}"
            + "tanpa men-set karakter sisipan, dengan key "
            + "{'F','E','D','C'},\n" 
            +"{'B','A','9','8'},\n" 
            +"{'7','6','5','4'},\n" 
            +"{'3','2','1','0'}"
            , expectations = "melemparkan eksepsi InvalidBogusDomainException")
    @Test(expected = InvalidBogusDomainException.class)
    public void testEncryptWithoutSettingBogusDomain() {
        System.out.println("encrypt plaintext without setting bogus domain");
        int[] plainText = new int[]{'A','7','7','2','D','C'};
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setKey(key);
        int[] expResult = new int[]{'B','6','B','6','1','E','E','8'};
        int[] result = instance.encrypt(plainText);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of decrypt method to decrypt even length ciphertext, 
     * of class PlayfairCipher.
     */
    @TestSingleReport(description = "melakukan dekripsi cipher text {'B','6','1','E'}"
            + " dengan key "
            + "{'F','E','D','C'},\n" 
            +"{'B','A','9','8'},\n" 
            +"{'7','6','5','4'},\n" 
            +"{'3','2','1','0'}"
            , expectations = "menghasilkan plain text {'A','7','2','D'}")
    @Test
    public void testDecryptEvenCiphertext() {
        System.out.println("decrypt even length ciphertext");
        int[] cipherText = new int[]{'B','6','1','E'};
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setBogusDomain('A');
        instance.setKey(key);
        int[] expResult = new int[]{'A','7','2','D'};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of decrypt method to decrypt that yield repeated char plaintext, 
     * of class PlayfairCipher.
     */
    @TestSingleReport(description = "melakukan dekripsi cipher text {'B','6','B','6','1','E','E','8'}"
            + " dengan key "
            + "{'F','E','D','C'},\n" 
            +"{'B','A','9','8'},\n" 
            +"{'7','6','5','4'},\n" 
            +"{'3','2','1','0'}"
            , expectations = "menghasilkan plain text {'A','7','7','2','D','C'}")
    @Test
    public void testDecryptRepeatedCharPlaintext() {
        System.out.println("decrypt that yield repeated char plaintext");
        int[] cipherText = new int[]{'B','6','B','6','1','E','E','8'};
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setBogusDomain('A');
        instance.setKey(key);
        int[] expResult = new int[]{'A','7','7','2','D','C'};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of decrypt method to decrypt without setting bogus domain, 
     * of class PlayfairCipher.
     */
    @TestSingleReport(description = "melakukan dekripsi cipher text {'B','6','B','6','1','E','E','8'}"
            + "tanpa men-set karakter sisipan, dengan key "
            + "{'F','E','D','C'},\n" 
            +"{'B','A','9','8'},\n" 
            +"{'7','6','5','4'},\n" 
            +"{'3','2','1','0'}"
            , expectations = "melemparkan eksepsi InvalidBogusDomainException")
    @Test(expected = InvalidBogusDomainException.class)
    public void testDecryptWithoutSettingBogusDomain() {
        System.out.println("decrypt without setting bogus domain");
        int[] cipherText = new int[]{'B','6','B','6','1','E','E','8'};
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setKey(key);
        int[] expResult = new int[]{'A','7','7','2','D','C'};
        int[] result = instance.decrypt(cipherText);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setKey method with null object, of class PlayfairCipher.
     */
    @TestSingleReport(description = "set key dengan null"
            , expectations = "melemparkan eksepsi NullPointerException")
    @Test(expected = NullPointerException.class)
    public void testSetKeyWithNull() {
        System.out.println("setKey with null object");
        SimpleKey key = new SimpleKey();
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setKey(key);
    }

    /**
     * Test of setKey method with duplicate element, of class PlayfairCipher.
     */
    @TestSingleReport(description = "set key dengan elemen duplikat (elemen 'D') "
            + "{'F','E','D','C'},\n" +
"            {'B','A','9','8'},\n" +
"            {'7','6','5','4'},\n" +
"            {'3','2','1','D'}"
            , expectations = "melemparkan eksepsi InappropriateKeyException")
    @Test(expected = InappropriateKeyException.class)
    public void testSetKeyWithDuplicateElement() {
        System.out.println("setKey with duplicate element");
        SimpleKey key = new SimpleKey();
        key.setKey(new int[][]{
            {'F','E','D','C'},
            {'B','A','9','8'},
            {'7','6','5','4'},
            {'3','2','1','D'}
        });
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setKey(key);
    }

    /**
     * Test of setKey method with non-square matrix, of class PlayfairCipher.
     */
    @TestSingleReport(description = "set key dengan bentuk bukan matriks persegi "
            + "{'F','E','D','C'},\n" +
"            {'B','A','9','8','O'},\n" +
"            {'7','6','5'},\n" +
"            {'3','2','1','D'}"
            , expectations = "melemparkan eksepsi InappropriateKeyException")
    @Test(expected = InappropriateKeyException.class)
    public void testSetKeyWithNonSquareMatrix() {
        System.out.println("setKey with non-square matrix");
        SimpleKey key = new SimpleKey();
        key.setKey(new int[][]{
            {'F','E','D','C'},
            {'B','A','9','8','O'},
            {'7','6','5'},
            {'3','2','1','D'}
        });
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setKey(key);
    }

    /**
     * Test of setBogusDomain method, of class PlayfairCipher.
     */
    @TestSingleReport(description = "set elemen sisipan dengan 0 "
            , expectations = "tidak ada nilai balik dan tidak ada eksepsi")
    @Test
    public void testSetBogusDomain() {
        System.out.println("setBogusDomain");
        int bogusDomain = 0;
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setBogusDomain(bogusDomain);
    }

    /**
     * Test of getBogusDomain method, of class PlayfairCipher.
     */
    @TestSingleReport(description = "get elemen sisipan yang telah di-set dengan 6 "
            , expectations = "mengembalikan nilai 6")
    @Test
    public void testGetBogusDomain() {
        System.out.println("getBogusDomain");
        PlayfairCipher instance = new PlayfairCipher(16);
        instance.setBogusDomain('6');
        int expResult = '6';
        int result = instance.getBogusDomain();
        assertEquals(expResult, result);
    }
    
}

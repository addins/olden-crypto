/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
     * Test of setKey method, of class LetterVigenereCipher.
     */
    @Test
    public void testSetKey() {
        System.out.println("setKey");
        String key = "";
        LetterVigenereCipher instance = new LetterVigenereCipher();
        instance.setKey(key);
    }
    
}

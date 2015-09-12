/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.addin.crypto.classic.core.util;

import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
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
@TestClassReport(description = "Test class untuk SpiralSquareTextKey, "
        + "class yang berfungsi untuk membuat matriks persegi dengan input teks, dan disusun secara spiral")
public class SpiralSquareTextKeyGenTest {
    
    public SpiralSquareTextKeyGenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateMatrix method, of class SpiralSquareTextKeyGen.
     */
    @TestSingleReport(description = "test membuat matrix 3x3 dengan input teks 'a'",
            expectations = "matriks yang dihasilkan sama dengan {{8,7,6},{3,0,1},{2,5,4}}")    
    @Test
    public void testGenerateMatrix() {
        System.out.println("generateMatrix");
        SpiralSquareTextKeyGen instance = new SpiralSquareTextKeyGen(3,"a");
        int[][] expResult = new int[][]{{8,7,6},{3,0,1},{2,5,4}};
        int[][] result = instance.generateMatrix();
        print2dMatrix(result);
        assertArrayEquals(expResult, result);
    }
    
    public static void print2dMatrix(int[][] input){
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(input[i][j]+", ");
            }
            System.out.println("");
        }
    }
}

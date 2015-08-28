package org.addin.crypto.classic.core.util;

import java.util.Arrays;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SimpleKeyGenTest {
    
    public SimpleKeyGenTest() {
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
     * Test of generateSquareMatrix method, of class SimpleKeyGen.
     */
    @Test
    public void testGenerateSquareMatrix() {
        System.out.println("generateSquareMatrix");
        int size = 3;
        int[][] notExpResult = new int[][]{{0,1,2},{3,4,5},{6,7,8}};
        int[][] result = new SimpleKeyGen(size).generateMatrix();
        assertThat(result, IsNot.not(IsEqual.equalTo(notExpResult)));
    }

    /**
     * Test of getIntFromZeroTo method, of class SimpleKeyGen.
     */
    @Test 
    @Ignore
    public void testGetIntFromZeroTo() {
        System.out.println("getIntFromZeroTo");
        int to = 10;
        int[] expResult = new int[]{0,1,2,3,4,5,6,7,8,9};
        int[] result = null;//SimpleKeyGen.getIntFromZeroTo(to); // <- this method have been moved.
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of shufflingElements method, of class SimpleKeyGen.
     */
    @Test
    @Ignore
    public void testShufflingElements() {
        System.out.println("shufflingElements");
        int[] input = new int[]{0,1,2,3,4,5,6,7,8,9};
        int[] result = null; //SimpleKeyGen.shufflingElements(Arrays.copyOf(input, input.length)); // method have been moved.
        assertThat(result, IsNot.not(IsEqual.equalTo(input)));
    }
    
}

package org.addin.crypto.classic.core.util;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class IntDomainCreator {
    public static int[] getIntFromZeroTo(int to) {
        int[] output = new int[to];
        for (int i = 0; i < to; i++) {
            output[i] = i;
        }
        return output;
    }
}

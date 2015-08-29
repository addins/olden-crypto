package org.addin.crypto.classic.core.util;

/**
 * based on TextKeyGen and rearrangement as explained in
 * http://www.geeksforgeeks.org/rearrange-given-array-place/
 *
 * @author addin <addins3009@gmail.com>
 */
public class GrsTextKeyGen extends TextKeyGen {

    public GrsTextKeyGen(int size) {
        super(size);
    }

    public GrsTextKeyGen(int size, String text) {
        super(size, text);
    }

    @Override
    public int[] shuffling() {
        super.shuffling();

        // First step: Increase all values by (arr[arr[i]]%n)*n
        for (int i = 0; i < domain.length; i++) {
            domain[i] += (domain[domain[i]] % domain.length) * domain.length;
        }

        // Second Step: Divide all values by n
        for (int i = 0; i < domain.length; i++) {
            domain[i] /= domain.length;
        }

        return domain;
    }
}

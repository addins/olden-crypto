package org.addin.crypto.classic.image;

import java.util.Arrays;
import org.addin.crypto.classic.core.PlayfairCipher;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SpecialPlayfairCipher extends PlayfairCipher {

    public SpecialPlayfairCipher(int elementDomain) {
        super(elementDomain);
        setBogusDomain(0);
    }

    /**
     * Not inserting bogus between twin digraph, only adding bogus at the end 
     * if the input's length is odd.
     * @param input
     * @return 
     */
    @Override
    protected int[] insertBogusBetweenTwinAndAtOddEnd(int[] input) {
        int[] output = null;
        if (input.length % 2 != 0) {
            output = Arrays.copyOf(input, input.length + 1);
            output[input.length] = getBogusDomain();
            return output;
        }
        return input;
    }

    /**
     * Since there is no bogus insertion between twin digraph while encrypting,
     * only remove bogus at the end if exist.
     * this method is called after decrypting.
     * @param input
     * @return 
     */
    @Override
    protected int[] removeBogus(int[] input) {
        int[] output = null;
        if (input.length % 2 == 0 && input[input.length-1]==getBogusDomain()) {
            output = Arrays.copyOf(input, input.length - 1);
            return output;
        }
        return input;
    }

    /**
     * nothing to do with twin digraph.
     * @param digraph
     * @return 
     */
    @Override
    protected int[] onEqualValueEncrypt(int[] digraph) {
        return digraph;
    }

    /**
     * nothing to do with twin digraph.
     * @param digraph
     * @return 
     */
    @Override
    protected int[] onEqualValueDecrypt(int[] digraph) {
        return digraph;
    }
}

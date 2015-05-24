package org.addin.crypto.classic.image;

import ar.com.hjg.pngj.ImageLineInt;
import org.addin.crypto.classic.core.Encipherment;
import org.addin.crypto.classic.core.KeyStreamVigenereCipher;
import org.addin.crypto.classic.core.SimpleKey;
import org.addin.crypto.classic.core.VigenereCipher;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class ImageLineIntSuperEncryption implements Encipherment<ImageLineInt> {

    private SpecialPlayfairCipher playfairCipher;
    private VigenereCipher vigenereCipher;

    public ImageLineIntSuperEncryption() {
        playfairCipher = new SpecialPlayfairCipher(256);
        vigenereCipher = new KeyStreamVigenereCipher(256);
    }
    
    @Override
    public ImageLineInt encrypt(ImageLineInt plainText) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImageLineInt decrypt(ImageLineInt cipherText) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setKey(SimpleKey key) {
        playfairCipher.setKey(key);
        int[][] kM = (int[][])key.getKey();
        int l = kM[0][0];
        int[] vcKey = new int[l];
        int count = 0;
        for (int i = 0; i < kM.length && count < l; i++) {
            for (int j = 0; j < kM[i].length && count < l; j++) {
                vcKey[count] = kM[i][j];
                count++;
            }
        }
        SimpleKey<int[]> vgK = new SimpleKey<>();
        vgK.setKey(vcKey);
        vigenereCipher.setKey(vgK);
    }
    
}

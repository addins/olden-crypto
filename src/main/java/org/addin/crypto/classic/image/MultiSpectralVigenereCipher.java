package org.addin.crypto.classic.image;

import boofcv.struct.image.ImageUInt8;
import boofcv.struct.image.MultiSpectral;
import org.addin.crypto.classic.core.Encipherment;
import org.addin.crypto.classic.core.SimpleKey;
import org.addin.crypto.classic.core.VigenereCipher;
import org.addin.crypto.classic.core.util.ConverterUtil;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class MultiSpectralVigenereCipher implements Encipherment<MultiSpectral<ImageUInt8>> {

    private final VigenereCipher cipher;

    public MultiSpectralVigenereCipher() {
        // color 0-255 for every channel
        cipher = new VigenereCipher(255);
    }

    @Override
    public MultiSpectral<ImageUInt8> encrypt(MultiSpectral<ImageUInt8> plainText) {
        if (plainText == null) {
            return null;
        }
        int numBands = plainText.getNumBands();
        ImageUInt8[] cpBands = new ImageUInt8[numBands];

        for (int n = 0; n < numBands; n++) {
            
            int[] c = cipher.encrypt(ConverterUtil.convertByteArraytoIntArray(
                            plainText.getBand(n).getData()
            )
            );
            byte[] cf = ConverterUtil.convertIntArraytoByteArray(c);
            if (cpBands[n] == null) {
                cpBands[n] = new ImageUInt8();
            }
            cpBands[n].setData(cf);

        }

        MultiSpectral<ImageUInt8> ms = new MultiSpectral(ImageUInt8.class,plainText.getWidth(),plainText.getHeight(), numBands);

        ms.setBands(cpBands);

        return ms;
    }

    @Override
    public MultiSpectral<ImageUInt8> decrypt(MultiSpectral<ImageUInt8> cipherText) {
        if (cipherText == null) {
            return null;
        }
        int numBands = cipherText.getNumBands();
        ImageUInt8[] plBands = new ImageUInt8[numBands];

        for (int n = 0; n < numBands; n++) {
            if (plBands[n] == null) {
                plBands[n] = new ImageUInt8();
            }
            plBands[n].setData(
                    ConverterUtil.convertIntArraytoByteArray(
                            cipher.decrypt(
                                    ConverterUtil.convertByteArraytoIntArray(cipherText.getBand(n).getData()))
                    )
            );

        }
        
        MultiSpectral<ImageUInt8> ms = new MultiSpectral(ImageUInt8.class,cipherText.getWidth(),cipherText.getHeight(), numBands);

        ms.setBands(plBands);

        return ms;
    }

    @Override
    public void setKey(SimpleKey key) {
        cipher.setKey(key);
    }

}

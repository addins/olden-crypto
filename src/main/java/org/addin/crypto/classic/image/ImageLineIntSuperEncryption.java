package org.addin.crypto.classic.image;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineInt;
import org.addin.crypto.classic.core.Encipherment;
import org.addin.crypto.classic.core.KeyStreamVigenereCipher;
import org.addin.crypto.classic.core.SimpleKey;

/**
 * This class is only for encrypting/decrypting image data that has RGB/RGBA 
 * channels and only 8 bit depth each. ImageLineInt contains RGB/A data only 
 * for a row of an image.
 * 
 * @author addin <addins3009@gmail.com>
 */
public class ImageLineIntSuperEncryption implements Encipherment<ImageLineInt> {

    private final SpecialPlayfairCipher playfairCipher;
    private final KeyStreamVigenereCipher vigenereCipher;
    
    //image related data
    private final ImageInfo imageInfo;

    public ImageLineIntSuperEncryption(ImageInfo imageInfo) {
        playfairCipher = new SpecialPlayfairCipher(256);
        vigenereCipher = new KeyStreamVigenereCipher(256);
        this.imageInfo = imageInfo;
    }
    
    @Override
    public ImageLineInt encrypt(ImageLineInt plainText) {
        int[] scanline = plainText.getScanline();
        int channels = imageInfo.channels;
        int[] redPlain = new int[imageInfo.cols];
        int[] greenPlain = new int[imageInfo.cols];
        int[] bluePlain = new int[imageInfo.cols];
        
        int[] red1stCiph;
        int[] green1stCiph;
        int[] blue1stCiph;
        
        int[] red2ndCiph;
        int[] green2ndCiph;
        int[] blue2ndCiph;
        
        for (int j = 0; j < imageInfo.cols ; j++) {
            redPlain[j] = scanline[j*channels];
            greenPlain[j] = scanline[j*channels+1];
            bluePlain[j] = scanline[j*channels+2];
        }
        
        red1stCiph = vigenereCipher.encrypt(redPlain);
        green1stCiph = vigenereCipher.encrypt(greenPlain);
        blue1stCiph = vigenereCipher.encrypt(bluePlain);
        
        red2ndCiph = playfairCipher.encrypt(red1stCiph);
        green2ndCiph = playfairCipher.encrypt(green1stCiph);
        blue2ndCiph = playfairCipher.encrypt(blue1stCiph);
        
        for (int j = 0; j < imageInfo.cols ; j++) {
            scanline[j*channels] = red2ndCiph[j];
            scanline[j*channels+1] = green2ndCiph[j];
            scanline[j*channels+2] = blue2ndCiph[j];
        }
        
        return plainText;
    }

    @Override
    public ImageLineInt decrypt(ImageLineInt cipherText) {
        int[] scanline = cipherText.getScanline();
        int channels = imageInfo.channels;
        int[] redCiph = new int[imageInfo.cols];
        int[] greenCiph = new int[imageInfo.cols];
        int[] blueCiph = new int[imageInfo.cols];
        
        int[] red1stPlain;
        int[] green1stPlain;
        int[] blue1stPlain;
        
        int[] red2ndPlain;
        int[] green2ndPlain;
        int[] blue2ndPlain;
        
        for (int j = 0; j < imageInfo.cols ; j++) {
            redCiph[j] = scanline[j*channels];
            greenCiph[j] = scanline[j*channels+1];
            blueCiph[j] = scanline[j*channels+2];
        }
        
        red1stPlain = playfairCipher.decrypt(redCiph);
        green1stPlain = playfairCipher.decrypt(greenCiph);
        blue1stPlain = playfairCipher.decrypt(blueCiph);
        
        red2ndPlain = vigenereCipher.decrypt(red1stPlain);
        green2ndPlain = vigenereCipher.decrypt(green1stPlain);
        blue2ndPlain = vigenereCipher.decrypt(blue1stPlain);
        
        
        for (int j = 0; j < imageInfo.cols ; j++) {
            scanline[j*channels] = red2ndPlain[j];
            scanline[j*channels+1] = green2ndPlain[j];
            scanline[j*channels+2] = blue2ndPlain[j];
        }
        
        return cipherText;
    }

    @Override
    public void setKey(SimpleKey key) {
        playfairCipher.setKey(key);
        int[][] kM = (int[][])key.getKey();
        int l = kM[0][0];
        
        // avoid l to be zero, in case the first element is zero. :D
        if(l==0)
            l = kM[0][1];
        
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

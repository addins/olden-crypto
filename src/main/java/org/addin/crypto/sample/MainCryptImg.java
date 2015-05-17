package org.addin.crypto.sample;

import boofcv.io.image.UtilImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.addin.crypto.classic.core.LetterPlayfairCipher;
import org.addin.crypto.classic.core.LetterVigenereCipher;
import org.addin.crypto.classic.core.SimpleKey;
import org.addin.crypto.classic.core.util.CharIntMapper;
import org.addin.crypto.classic.image.TwoDimensionsVigenereCipher;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class MainCryptImg {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello");
        /*
//        BufferedImage input = UtilImageIO.loadImage("test2.jpg");
        BufferedImage input = UtilImageIO.loadImage("test2_en.ppm");
//        UtilImageIO.loadPPM_U8(null, null, null)

        MultiSpectral<ImageUInt8> img;
        img = ConvertBufferedImage.convertFromMulti(input, null, true, ImageUInt8.class);

        MultiSpectralVigenereCipher msvc = new MultiSpectralVigenereCipher();
        SimpleKey<int[]> key1 = new SimpleKey();
//        key1.setKey(new int[]{219,119,23,145,89,34,123,65,45,78,254,132});
        key1.setKey(new int[]{0});
        msvc.setKey(key1);
        img = msvc.encrypt(img);
        img = msvc.decrypt(img);

        
        UtilImageIO.savePPM(img, "test2_de.ppm", null);
//        UtilImageIO.savePPM(img, "test2_en.ppm",null);*/
//        testImageVigenereEncrypt();
//        testImageVigenereDecrypt();
        
        LetterVigenereCipher cipher = new LetterVigenereCipher();
        cipher.setKey("drain");
        String plain = "MyNameisRobertEinsteinImnolongerMrZX";
        System.out.println("plain = " + plain.length());
        String ciph = cipher.encrypt(plain);
        System.out.println("ciph = " + ciph);
        System.out.println("-- "+cipher.decrypt(ciph));
        
        LetterPlayfairCipher playfairCipher = new LetterPlayfairCipher();
        String plain1 = plain;//"hello";
        playfairCipher.setKey("lgdbaqmhecurnifxvsokzywtp");
        String ciph1 = playfairCipher.encrypt(plain1);
        System.out.println("ciph1 = " + ciph1);
        System.out.println("-- = " + playfairCipher.decrypt(ciph1));
    }

    public static int[][] getDiagonalMatrix(int size) {
        int count = 1;
        int[][] mtx = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                mtx[i][j] = count++;
            }
        }
        return mtx;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(CharIntMapper.getCharRepresentative(matrix[i][j],true) + "\t");
            }
            System.out.println();
        }
    }

    public static void testImageVigenereEncrypt() {
        BufferedImage image = UtilImageIO.loadImage("test2.jpg");
        int[][] marchThroughImage = marchThroughImage(image);
        TwoDimensionsVigenereCipher cipher = new TwoDimensionsVigenereCipher();
        SimpleKey<int[]> key;
        key = new SimpleKey();
        key.setKey(new int[]{219,119,23,145,89,34,123,65,45,78,254,132});
//        key.setKey(new int[]{0});
        cipher.setKey(key);
        int[][] encrypt = cipher.encrypt(marchThroughImage);
        saveImage("test2_en.jpg", encrypt, image.getWidth(), image.getHeight());
    }
    
    public static void testImageVigenereDecrypt() {
        BufferedImage image = UtilImageIO.loadImage("test2_en.jpg");
        int[][] marchThroughImage = marchThroughImage(image);
        TwoDimensionsVigenereCipher cipher = new TwoDimensionsVigenereCipher();
        SimpleKey<int[]> key;
        key = new SimpleKey();
        key.setKey(new int[]{219,119,23,145,89,34,123,65,45,78,254,132});
//        key.setKey(new int[]{0});
        cipher.setKey(key);
        int[][] decrypt = cipher.decrypt(marchThroughImage);
        saveImage("test2_de.jpg", decrypt, image.getWidth(), image.getHeight());
    }
    
    public static void saveImage(String filename, int[][] data, int width, int height){
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = data[0][count];
                rgb = (rgb << 8) + data[1][count];
                rgb = (rgb << 8) + data[2][count];
                bi.setRGB(j, i, rgb);
                count++;
            }
        }
        
        UtilImageIO.saveImage(bi, filename);
    }

    public static void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

    public static int extractBlue(int pixel) {
        int blue = (pixel) & 0xff;
        return blue;
    }

    public static int extractAlpha(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        return alpha;
    }

    public static int extractRed(int pixel) {
        int red = (pixel >> 16) & 0xff;
        return red;
    }

    public static int extractGreen(int pixel) {
        int green = (pixel >> 8) & 0xff;
        return green;
    }

    private static int[][] marchThroughImage(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();

        int[][] extract = new int[3][w * h];

        int count = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getRGB(j, i);
                extract[0][count] = extractRed(pixel);
                extract[1][count] = extractGreen(pixel);
                extract[2][count] = extractBlue(pixel);
                count++;
            }
        }
        return extract;
    }
}

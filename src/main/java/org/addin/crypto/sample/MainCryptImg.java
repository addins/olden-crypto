package org.addin.crypto.sample;

import boofcv.io.image.UtilImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.addin.crypto.classic.core.LetterPlayfairCipher;
import org.addin.crypto.classic.core.LetterVigenereCipher;
import org.addin.crypto.classic.core.SimpleKey;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class MainCryptImg {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello");
//        testImageVigenereEncrypt();
//        testImageVigenereDecrypt();
    }
    
    public static void testLetterVigenereCipher(){
        LetterVigenereCipher cipher = new LetterVigenereCipher();
        cipher.setKey("drain");
        String plain = "MyNameisRobertEinsteinImnolongerMrZX";
        System.out.println("plain = " + plain.length());
        String ciph = cipher.encrypt(plain);
        System.out.println("ciph = " + ciph);
        System.out.println("-- "+cipher.decrypt(ciph));
    }
    
    public static void testLetterPlayfairCipher(){
        LetterPlayfairCipher playfairCipher = new LetterPlayfairCipher();
        String plain1 = "hello";
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
                System.out.print(/*CharIntMapper.getCharRepresentative(*/matrix[i][j]/*,true)*/ + "\t");
            }
            System.out.println();
        }
    }

//    public static void testImageVigenereEncrypt() {
//        String fn = "benera";
//        BufferedImage image = UtilImageIO.loadImage(fn+".png");
//        int[][] marchThroughImage = marchThroughImage(image);
//        TwoDimensionsVigenereCipher cipher = new TwoDimensionsVigenereCipher();
//        SimpleKey<int[]> key;
//        key = new SimpleKey();
//        key.setKey(new int[]{219,119,23,145,89,34,123,65,45,78,254,132});
////        key.setKey(new int[]{0});
//        cipher.setKey(key);
//        int[][] encrypt = cipher.encrypt(marchThroughImage);
//        saveImage(fn+"_en.png", encrypt, image.getWidth(), image.getHeight());
//    }
//    
//    public static void testImageVigenereDecrypt() {
//        String fn = "benera";
//        BufferedImage image = UtilImageIO.loadImage(fn+"_en.png");
//        int[][] marchThroughImage = marchThroughImage(image);
//        TwoDimensionsVigenereCipher cipher = new TwoDimensionsVigenereCipher();
//        SimpleKey<int[]> key;
//        key = new SimpleKey();
//        key.setKey(new int[]{219,119,23,145,89,34,123,65,45,78,254,132});
////        key.setKey(new int[]{0});
//        cipher.setKey(key);
//        int[][] decrypt = cipher.decrypt(marchThroughImage);
//        saveImage(fn+"_de.png", decrypt, image.getWidth(), image.getHeight());
//    }
    
    public static void saveImage(String filename, int[][] data, int width, int height){
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = data[3][count];
                rgb = (rgb << 8) + data[0][count];
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

        int[][] extract = new int[4][w * h];

        int count = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getRGB(j, i);
                extract[0][count] = extractRed(pixel);
                extract[1][count] = extractGreen(pixel);
                extract[2][count] = extractBlue(pixel);
                extract[3][count] = extractAlpha(pixel);
                count++;
            }
        }
        return extract;
    }
    
    private static int[][][] marchThroughImageByRow(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();

        int[][][] extract = new int[4][h][w];

        int count = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getRGB(j, i);
                extract[0][i][j] = extractRed(pixel);
                extract[1][i][j] = extractGreen(pixel);
                extract[2][i][j] = extractBlue(pixel);
                extract[3][i][j] = extractAlpha(pixel);
                count++;
            }
        }
        return extract;
    }
    
    private static int[][] convertItBack(int[][][] data){
        printMatrix(data[0]);
        System.out.println("-----------------------");
        printMatrix(data[1]);
        System.out.println("-----------------------");
        printMatrix(data[2]);
        System.out.println("-----------------------");
        printMatrix(data[3]);
        System.out.println("-----------------------");
        int he = data[0].length;
        int wi = data[0][0].length;
        int[][] fin = new int[data.length][wi*he];
//        System.out.println(":"+he+" "+wi);
        for (int ch = 0; ch < data.length; ch++) {
            for (int h = 0, idx = 0; h < he; h++) {
                for (int w = 0; w < wi-2; w++,idx++) {
//                    System.out.println("::"+ch+" "+h+" "+w+" "+idx);
                    fin[ch][idx] = data[ch][h][w];
                }
            }
        }
        return fin;
    }
}

package org.addin.crypto.sample;

import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.ChunkCopyBehaviour;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import java.io.File;
import java.security.SecureRandom;
import org.addin.crypto.classic.core.SimpleKey;
import org.addin.crypto.classic.core.util.SimpleKeyGen;
import org.addin.crypto.classic.image.ImageEncipherment;
import org.addin.crypto.classic.image.ImageLineIntSuperEncryption;
import org.addin.crypto.classic.image.PngImageSuperEncryption;
import org.addin.crypto.classic.image.SpecialPlayfairCipher;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SamplePngj {
    
    private static SimpleKey<int[][]> STATIC_KEY;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testEncryptImage();
        testDecryptImage();
//        testPlayfairImageEnc();
//        testPlayfairImageDec();
//        testGenerateMatrix();
    }

    public static void sample() {
        String fileName = "benera";
        PngReader pngr = new PngReader(new File(fileName + ".png"));
        System.out.println(pngr.toString());
        int channels = pngr.imgInfo.channels;
        if (channels < 3 || pngr.imgInfo.bitDepth != 8) {
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        }
        PngWriter pngw = new PngWriter(new File(fileName + "_edt.png"), pngr.imgInfo, true);
        pngw.copyChunksFrom(pngr.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        pngw.getMetadata().setText(PngChunkTextVar.KEY_Description, "Decreased red and increased green");
        for (int row = 0; row < pngr.imgInfo.rows; row++) {
            IImageLine l1 = pngr.readRow();
            int[] scanline = ((ImageLineInt) l1).getScanline();
            for (int j = 0; j < pngr.imgInfo.cols; j++) {
                scanline[j * channels] /= 2;
                scanline[j * channels + 1] = ImageLineHelper.clampTo_0_255(scanline[j * channels + 1] + 20);
            }
            pngw.writeRow(l1);
        }
        pngr.end();
        pngw.end();
    }

    public static void testEncryptImage() {
        String fileName = "bug1.png";
        String encrFileName = "bug1_en.png";
        
        STATIC_KEY = new SimpleKey<>(buggyKey());
        printMatrix(STATIC_KEY.getKey());
        
        PngImageSuperEncryption pise = new PngImageSuperEncryption(fileName, encrFileName);
        pise.setKey(STATIC_KEY);
        pise.process(ImageEncipherment.ENCRYPT_MODE);
        
    }

    public static void testDecryptImage() {
        String fileName = "bug1_en.png";
        String encrFileName = "bug1_de.png";

        PngImageSuperEncryption pise = new PngImageSuperEncryption(fileName, encrFileName);
        pise.setKey(STATIC_KEY);
        pise.process(ImageEncipherment.DECRYPT_MODE);
    }
    
    
    public static void testPlayfairImageEnc(){
        String fileName = "tes2.png";
        String encrFileName = "tes2_en.png";

        PngReader pngr = new PngReader(new File(fileName));
        System.out.println(pngr);

        int channels = pngr.imgInfo.channels;
        if (channels < 3 || pngr.imgInfo.bitDepth != 8) {
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        }

        SpecialPlayfairCipher cipher = new SpecialPlayfairCipher(256);
        SimpleKey<int[][]> key = new SimpleKey<>();
        int[][] mtx = getDiagonalMatrix(16);
        key.setKey(mtx);
        cipher.setKey(key);

        PngWriter pngw = new PngWriter(new File(encrFileName), pngr.imgInfo, true);
        pngw.copyChunksFrom(pngr.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        pngw.getMetadata().setText(PngChunkTextVar.KEY_Description, "encrypted image");
        for (int row = 0; row < pngr.imgInfo.rows; row++) {
            IImageLine l1 = pngr.readRow();
            int[] scanline = ((ImageLineInt) l1).getScanline();
            int[] redPlain = new int[pngr.imgInfo.cols];
            int[] greenPlain = new int[pngr.imgInfo.cols];
            int[] bluePlain = new int[pngr.imgInfo.cols];

            int[] red1stCiph = null;
            int[] green1stCiph = null;
            int[] blue1stCiph = null;

            for (int j = 0; j < pngr.imgInfo.cols; j++) {
                redPlain[j] = scanline[j * channels];
                greenPlain[j] = scanline[j * channels + 1];
                bluePlain[j] = scanline[j * channels + 2];
            }

            red1stCiph = cipher.encrypt(redPlain);
            green1stCiph = cipher.encrypt(greenPlain);
            blue1stCiph = cipher.encrypt(bluePlain);
            
            for (int j = 0; j < pngr.imgInfo.cols; j++) {
                scanline[j * channels] = red1stCiph[j];
                scanline[j * channels + 1] = green1stCiph[j];
                scanline[j * channels + 2] = blue1stCiph[j];
            }
            pngw.writeRow(l1);
        }
        pngr.end();
        pngw.end();
    }
    
    public static void testPlayfairImageDec(){
        String fileName = "tes2_en.png";
        String encrFileName = "tes2_de.png";

        PngReader pngr = new PngReader(new File(fileName));
        System.out.println(pngr);

        int channels = pngr.imgInfo.channels;
        if (channels < 3 || pngr.imgInfo.bitDepth != 8) {
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        }

        SpecialPlayfairCipher cipher = new SpecialPlayfairCipher(256);
        SimpleKey<int[][]> key = new SimpleKey<>();
        int[][] mtx = getDiagonalMatrix(16);
        key.setKey(mtx);
        cipher.setKey(key);

        PngWriter pngw = new PngWriter(new File(encrFileName), pngr.imgInfo, true);
        pngw.copyChunksFrom(pngr.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        pngw.getMetadata().setText(PngChunkTextVar.KEY_Description, "encrypted image");
        for (int row = 0; row < pngr.imgInfo.rows; row++) {
            IImageLine l1 = pngr.readRow();
            int[] scanline = ((ImageLineInt) l1).getScanline();
            int[] redPlain = new int[pngr.imgInfo.cols];
            int[] greenPlain = new int[pngr.imgInfo.cols];
            int[] bluePlain = new int[pngr.imgInfo.cols];

            int[] red1stCiph = null;
            int[] green1stCiph = null;
            int[] blue1stCiph = null;

            for (int j = 0; j < pngr.imgInfo.cols; j++) {
                redPlain[j] = scanline[j * channels];
                greenPlain[j] = scanline[j * channels + 1];
                bluePlain[j] = scanline[j * channels + 2];
            }

            red1stCiph = cipher.decrypt(redPlain);
            green1stCiph = cipher.decrypt(greenPlain);
            blue1stCiph = cipher.decrypt(bluePlain);
            
            for (int j = 0; j < pngr.imgInfo.cols; j++) {
                scanline[j * channels] = red1stCiph[j];
                scanline[j * channels + 1] = green1stCiph[j];
                scanline[j * channels + 2] = blue1stCiph[j];
            }
            pngw.writeRow(l1);
        }
        pngr.end();
        pngw.end();
    }
    
    public static void testGenerateMatrix(){
        int[][] mtx = generateSquareMatrix(16);
        printMatrix(mtx);
        System.out.println("---------------------------");
        mtx = generateSquareMatrix(16);
        printMatrix(mtx);
    }

    public static int[][] getDiagonalMatrix(int size) {
        int count = 0;
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
    
    public static int[][] generateSquareMatrix(int size){
        int[] domain = getIntFromZeroTo(size*size);
        shufflingElements(domain);
        
        int[][] mtx = new int[size][size];
        for (int i = 0, idx = 0; i < mtx.length; i++) {
            for (int j = 0; j < mtx[i].length; j++, idx++) {
                mtx[i][j] = domain[idx];
            }            
        }
        return mtx;
    }

    public static int[] getIntFromZeroTo(int to) {
        int[] output = new int[to];
        for (int i = 0; i < to; i++) {
            output[i] = i;
        }
        return output;
    }

    /**
     * implementation of Fisher-Yates Shuffle
     * @param input
     * @return 
     */
    public static int[] shufflingElements(int[] input) {
        SecureRandom sr = new SecureRandom();
        for (int i = input.length - 1; i > 0; i--) {
            int idx = sr.nextInt(input.length);
            int a = input[idx];
            input[idx] = input[i];
            input[i] = a;
        }
        return input;
    }
    
    public static int[][] buggyKey(){
        return new int[][]{
            {0, 121, 230, 46, 203, 55, 128, 154, 30, 65, 190, 92, 150, 83, 43, 172},
            {59, 191, 19, 151, 36, 254, 53, 100, 62, 84, 144, 237, 246, 251, 178, 39},
            {201, 74, 101, 98, 33, 216, 175, 247, 205, 25, 63, 214, 168, 28, 235, 177},
            {224, 130, 171, 226, 45, 86, 182, 159, 35, 61, 123, 227, 169, 153, 50, 16},
            {12, 52, 129, 200, 238, 181, 158, 122, 70, 29, 193, 207, 31, 7, 3, 248},
            {116, 21, 15, 217, 211, 64, 125, 184, 240, 236, 162, 68, 138, 222, 67, 69},
            {176, 206, 170, 167, 179, 79, 145, 219, 85, 250, 13, 4, 94, 208, 234, 135},
            {249, 6, 88, 54, 38, 218, 143, 117, 255, 87, 90, 225, 212, 163, 96, 120},
            {57, 115, 157, 20, 155, 41, 114, 108, 75, 82, 40, 192, 58, 223, 127, 111},
            {188, 231, 136, 131, 147, 166, 104, 213, 199, 47, 139, 221, 110, 118, 99, 196},
            {22, 10, 229, 5, 42, 220, 152, 76, 49, 173, 133, 78, 186, 242, 132, 187},
            {2, 32, 48, 149, 9, 103, 164, 148, 124, 134, 185, 77, 107, 202, 8, 140},
            {142, 26, 23, 244, 160, 156, 72, 194, 81, 174, 239, 252, 95, 232, 119, 97},
            {146, 24, 14, 228, 109, 1, 37, 105, 165, 34, 102, 253, 113, 80, 161, 71},
            {91, 112, 51, 180, 66, 245, 210, 209, 73, 215, 17, 189, 241, 126, 233, 89},
            {60, 18, 198, 27, 243, 44, 197, 106, 183, 195, 204, 137, 141, 93, 11, 56}
        };
    }

}

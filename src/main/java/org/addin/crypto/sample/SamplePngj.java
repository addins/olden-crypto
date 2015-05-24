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
import org.addin.crypto.classic.image.ImageLineIntSuperEncryption;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SamplePngj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        testEncryptImage();
//        testDecryptImage();
        testGenerateMatrix();
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
        String fileName = "drawing.png";
        String encrFileName = "drawing_en.png";

        PngReader pngr = new PngReader(new File(fileName));
        System.out.println(pngr);

        int channels = pngr.imgInfo.channels;
        if (channels < 3 || pngr.imgInfo.bitDepth != 8) {
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        }

        ImageLineIntSuperEncryption cipher = new ImageLineIntSuperEncryption(pngr.imgInfo);
        SimpleKey<int[][]> key = new SimpleKey<>();
        int[][] mtx = getDiagonalMatrix(16);
        key.setKey(mtx);
        cipher.setKey(key);

        PngWriter pngw = new PngWriter(new File(encrFileName), pngr.imgInfo, true);
        pngw.copyChunksFrom(pngr.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        pngw.getMetadata().setText(PngChunkTextVar.KEY_Description, "encrypted image");
        for (int row = 0; row < pngr.imgInfo.rows; row++) {
            IImageLine l1 = pngr.readRow();
            cipher.encrypt((ImageLineInt) l1);
            pngw.writeRow(l1);
        }
        pngr.end();
        pngw.end();
    }

    public static void testDecryptImage() {
        String fileName = "drawing_en.png";
        String encrFileName = "drawing_de.png";

        PngReader pngr = new PngReader(new File(fileName));
        System.out.println(pngr);

        int channels = pngr.imgInfo.channels;
        if (channels < 3 || pngr.imgInfo.bitDepth != 8) {
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        }

        ImageLineIntSuperEncryption cipher = new ImageLineIntSuperEncryption(pngr.imgInfo);
        SimpleKey<int[][]> key = new SimpleKey<>();
        key.setKey(getDiagonalMatrix(16));
        cipher.setKey(key);

        PngWriter pngw = new PngWriter(new File(encrFileName), pngr.imgInfo, true);
        pngw.copyChunksFrom(pngr.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        pngw.getMetadata().setText(PngChunkTextVar.KEY_Description, "encrypted image");
        for (int row = 0; row < pngr.imgInfo.rows; row++) {
            IImageLine l1 = pngr.readRow();
            cipher.decrypt((ImageLineInt) l1);
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

}

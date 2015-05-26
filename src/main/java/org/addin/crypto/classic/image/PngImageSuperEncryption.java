package org.addin.crypto.classic.image;

import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.ChunkCopyBehaviour;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import java.io.File;
import org.addin.crypto.classic.core.Encipherment;
import org.addin.crypto.classic.core.SimpleKey;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class PngImageSuperEncryption implements ImageEncipherment<SimpleKey<int[][]>> {

    private String inputFileName;
    private String outputFileName;
    
    private PngReader reader;
    private PngWriter writer;

    private Encipherment<ImageLineInt> cipher;

    public PngImageSuperEncryption(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public PngImageSuperEncryption(Encipherment<ImageLineInt> cipher) {
        this.cipher = cipher;
    }

    @Override
    public void setKey(SimpleKey<int[][]> key) {
        initialProcess();
        cipher.setKey(key);
    }

    @Override
    public void process(int mode) {
        initialProcess();
        switch (mode) {
            case ImageEncipherment.ENCRYPT_MODE:
                encrypt();
                break;
            case ImageEncipherment.DECRYPT_MODE:
                decrypt();
                break;
            default:
                throw new RuntimeException("Invalid mode.");
        }
    }

    private void encrypt() {
        validatingImageSupport();
        
        writer.copyChunksFrom(reader.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        writer.getMetadata().setText(PngChunkTextVar.KEY_Description, "encrypted image");
        
        for (int row = 0; row < reader.imgInfo.rows; row++) {
            IImageLine l1 = reader.readRow();
            cipher.encrypt((ImageLineInt) l1);
            writer.writeRow(l1);
        }
        
        reader.end();
        writer.end();
    }

    private void decrypt() {
        validatingImageSupport();
        
        writer.copyChunksFrom(reader.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        writer.getMetadata().setText(PngChunkTextVar.KEY_Description, "decrypted image");
        
        for (int row = 0; row < reader.imgInfo.rows; row++) {
            IImageLine l1 = reader.readRow();
            cipher.decrypt((ImageLineInt) l1);
            writer.writeRow(l1);
        }
        
        reader.end();
        writer.end();
    }

    private void initialProcess() {
        if (null == reader) {
            reader = new PngReader(new File(inputFileName));
        }
        if (null == writer) {
            writer = new PngWriter(new File(outputFileName), reader.imgInfo);
        }
        if (cipher == null) {
            cipher = new ImageLineIntSuperEncryption(reader.imgInfo);
        }
    }

    private void validatingImageSupport() {
        int channels = reader.imgInfo.channels;
        if (channels < 3 || reader.imgInfo.bitDepth != 8) {
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        }
    }
}

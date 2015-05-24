package org.addin.crypto.sample;

import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.ChunkCopyBehaviour;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import java.io.File;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SamplePngj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName = "benera";
        PngReader pngr = new PngReader(new File(fileName+".png"));
        System.out.println(pngr.toString());
        int channels = pngr.imgInfo.channels;
        if(channels < 3 || pngr.imgInfo.bitDepth != 8)
            throw new RuntimeException("this method is for RGB8/RGBA8 images");
        PngWriter pngw = new PngWriter(new File(fileName+"_edt.png"), pngr.imgInfo, true);
        pngw.copyChunksFrom(pngr.getChunksList(), ChunkCopyBehaviour.COPY_ALL_SAFE);
        pngw.getMetadata().setText(PngChunkTextVar.KEY_Description, "Decreased red and increased green");
        for (int row = 0; row < pngr.imgInfo.rows; row++) {
            IImageLine l1 = pngr.readRow();
            int[] scanline = ((ImageLineInt)l1).getScanline();
            for (int j = 0; j < pngr.imgInfo.cols; j++) {
                scanline[j*channels] /= 2;
                scanline[j*channels+1] = ImageLineHelper.clampTo_0_255(scanline[j*channels+1]+20);
            }
            pngw.writeRow(l1);
        }
        pngr.end();
        pngw.end();
    }
    
}

package org.addin.crypto.classic.core.util;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class ConverterUtil {

    public static int[] convertFloatArraytoIntArray(float[] input) {
        if(input==null) return null;
        int[] res = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = Math.round(input[i]);
        }
        return res;
    }

    public static float[] convertIntArraytoFloatArray(int[] input) {
        if(input==null) return null;
        float[] res = new float[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = input[i];
        }
        return res;
    }
    
    public static int[] convertByteArraytoIntArray(byte[] input){
        if(input==null) return null;
        int[] res = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            res[i] = input[i] & 0xff;
        }
        return res;
    }
    
    public static byte[] convertIntArraytoByteArray(int[] input){
        if(input==null) return null;
        byte[] res;
        ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 4);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(input);
        res = byteBuffer.array();
        return res;
    }
}


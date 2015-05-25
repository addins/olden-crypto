package org.addin.crypto.classic.core;

import java.util.ArrayList;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class KeyStreamVigenereCipher extends VigenereCipher {
    
    private final ArrayList<Integer> keystream;
    private int streamPos = -1;

    public KeyStreamVigenereCipher(int elementDomain) {
        super(elementDomain);
        keystream = new ArrayList<>();
    }

    @Override
    protected int getKeyElementAt(int i) {
        int m = key.getKey().length;
        
        if (i == 0) {
            streamPos++;
        }
        if (streamPos > -1) {
            i = i + streamPos * m;
        }
        if(i>=m){
            int ki1,ki2;
            if(i-1 >= m)
                ki1 = keystream.get(i-1-m);
            else 
                ki1 = key.getKey()[i-1];
            
            if(i-m >= m)
                ki2 = keystream.get(i-m-m);
            else 
                ki2 = key.getKey()[i-m];
            
            int keyi = (ki1+ki2) % elementDomain;
            keystream.add(keyi);
            return keystream.get(i-m);
        }
        return super.getKeyElementAt(i);
    }
}

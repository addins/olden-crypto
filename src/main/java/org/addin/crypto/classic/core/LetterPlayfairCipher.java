package org.addin.crypto.classic.core;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class LetterPlayfairCipher extends PlayfairCipher{

    public LetterPlayfairCipher() {
        super(26);
        super.setBogusDomain(CharIntMapper.getIntRepresentative('x'));
    }
    
    public String encrypt(String plainText) {
        int[] plain = new int[plainText.length()];
        for(int i = 0; i<plainText.length();i++){
            char c = plainText.charAt(i);
            if (c == 'j' || c == 'J')
                c = 'i';
            plain[i] = CharIntMapper.getIntRepresentative(c);
        }
        int[] cipher = super.encrypt(plain);
        String cipherText = "";
        for(int i = 0; i<cipher.length;i++){
            cipherText += CharIntMapper.getCharRepresentative(cipher[i], true);
        }
        return cipherText;
    }
    
    public String decrypt(String cipherText) {
        int[] cipher = new int[cipherText.length()];
        for(int i = 0; i<cipherText.length();i++){
            cipher[i] = CharIntMapper.getIntRepresentative(cipherText.charAt(i));
        }
        int[] plain = super.decrypt(cipher);
        String plainText = "";
        for(int i = 0; i<plain.length;i++){
            plainText += CharIntMapper.getCharRepresentative(plain[i], true);
        }
        return plainText;
    }

    public void setKey(String key) {
        if(key.length()<elementDomain-1){
            throw new RuntimeException("key too small.");
        }
        int m = (int) Math.sqrt(this.elementDomain);
        int[][] keys = new int[m][m];
        for(int i = 0,idx = 0; i<m;i++){
            for (int j = 0; j < m; j++, idx++) {
                keys[i][j] = CharIntMapper.getIntRepresentative(key.charAt(idx));
            }
        }
        SimpleKey<int[][]> ck = new SimpleKey();
        ck.setKey(keys);
        super.setKey(ck);
    } 
}

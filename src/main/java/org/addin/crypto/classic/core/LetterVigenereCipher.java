package org.addin.crypto.classic.core;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class LetterVigenereCipher extends VigenereCipher{

    public LetterVigenereCipher() {
        // alphabet is consist of 26 letter
        super(26);
    }

    public String encrypt(String plainText) {
        int[] plain = new int[plainText.length()];
        for(int i = 0; i<plainText.length();i++){
            plain[i] = CharIntMapper.getIntRepresentative(plainText.charAt(i));
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
        int[] keys = new int[key.length()];
        for(int i = 0; i<key.length();i++){
            keys[i] = CharIntMapper.getIntRepresentative(key.charAt(i));
        }
        SimpleKey<int[]> ck = new SimpleKey();
        ck.setKey(keys);
        super.setKey(ck);
    }    
}

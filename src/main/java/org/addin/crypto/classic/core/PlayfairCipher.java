package org.addin.crypto.classic.core;

import java.util.ArrayList;
import java.util.HashSet;
import org.addin.crypto.classic.core.exception.InappropriateKeyException;
import org.addin.crypto.classic.core.exception.InvalidBogusDomainException;
import org.apache.commons.collections4.SortedBidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class PlayfairCipher implements Encipherment<int[]> {

    protected int[][] key;
    private int bogusDomain = -1;

    protected final int elementDomain;
    
    private SortedBidiMap<Integer, KeyPosition> valToPosKey;

    public PlayfairCipher(int elementDomain) {
        this.elementDomain = elementDomain;
    }

    @Override
    public int[] encrypt(int[] plainText) {
        isKeySet();
        isBogusDomainSet();

        int[] plainEven = insertBogusBetweenTwinAndAtOddEnd(plainText);

        int[] cipherText = new int[plainEven.length];

        for (int idx = 1; idx < plainEven.length; idx += 2) {
            int el1 = plainEven[idx - 1];
            int el2 = plainEven[idx];

            // position as x,y in key
            int[] pos1 = findPositionInKey(el1);
            int[] pos2 = findPositionInKey(el2);

            if (el1 == el2) {
                int[] ciphDigraphs = onEqualValueEncrypt(new int[]{el1, el2});
                cipherText[idx - 1] = ciphDigraphs[0];
                cipherText[idx] = ciphDigraphs[1];
            } else if (pos1[0] == pos2[0]) {
                int[] ciphDigraphs = onSameRowEncrypt(pos1[0], pos1[1], pos2[0], pos2[1]);
                cipherText[idx - 1] = ciphDigraphs[0];
                cipherText[idx] = ciphDigraphs[1];
            } else if (pos1[1] == pos2[1]) {
                int[] ciphDigraphs = onSameColumnEncrypt(pos1[0], pos1[1], pos2[0], pos2[1]);
                cipherText[idx - 1] = ciphDigraphs[0];
                cipherText[idx] = ciphDigraphs[1];
            } else {
                int[] ciphDigraphs = onDifferentRowAndColumnEncrypt(pos1[0], pos1[1], pos2[0], pos2[1]);
                cipherText[idx - 1] = ciphDigraphs[0];
                cipherText[idx] = ciphDigraphs[1];
            }
        }

        return cipherText;
    }

    @Override
    public int[] decrypt(int[] cipherText) {
        isKeySet();
        isBogusDomainSet();
        int[] plainText = new int[cipherText.length];

        for (int idx = 1; idx < cipherText.length; idx += 2) {
            int el1 = cipherText[idx - 1];
            int el2 = cipherText[idx];

            // position as x,y in key
            int[] pos1 = findPositionInKey(el1);
            int[] pos2 = findPositionInKey(el2);

            int diff = 0;

            if (el1 == el2) {
                int[] plainDigraphs = onEqualValueDecrypt(new int[]{el1, el2});
                plainText[idx - 1] = plainDigraphs[0];
                plainText[idx] = plainDigraphs[1];
            } else if (pos1[0] == pos2[0]) {
                int[] plainDigraphs = onSameRowDecrypt(pos1[0], pos1[1], pos2[0], pos2[1]);
                plainText[idx - 1] = plainDigraphs[0];
                plainText[idx] = plainDigraphs[1];
            } else if (pos1[1] == pos2[1]) {
                int[] plainDigraphs = onSameColumnDecrypt(pos1[0], pos1[1], pos2[0], pos2[1]);
                plainText[idx - 1] = plainDigraphs[0];
                plainText[idx] = plainDigraphs[1];
            } else {
                int[] plainDigraphs = onDifferentRowAndColumnDecrypt(pos1[0], pos1[1], pos2[0], pos2[1]);
                plainText[idx - 1] = plainDigraphs[0];
                plainText[idx] = plainDigraphs[1];
            }
        }
        // removing bogus
        plainText = removeBogus(plainText);

        return plainText;
    }

    @Override
    public void setKey(SimpleKey key) throws InappropriateKeyException, ClassCastException {
        int[][] keyA = null;
        try {
            keyA = (int[][]) key.getKey();
        } catch (ClassCastException e) {
            throw new ClassCastException("the key need to be 2 dimensions array.");
        }
        if (hasNoDuplicateElement(keyA) && isSquareMatrix(keyA, this.elementDomain)) {
            this.key = keyA;
            if(valToPosKey == null){
                valToPosKey = new DualTreeBidiMap<>();
            }
            
            for (int i = 0; i < this.key.length;i++) {
                for (int j=0; j< this.key[i].length; j++) {
                    valToPosKey.put(this.key[i][j], new KeyPosition(i, j));
                }
            }
            
        } else {
            throw new InappropriateKeyException("Key cannot contains duplicate value and must be "
                    + (int) Math.sqrt(elementDomain) + " square matrix.");
        }
    }

    public void setBogusDomain(int bogusDomain) {
        this.bogusDomain = bogusDomain;
    }

    public int getBogusDomain() {
        isBogusDomainSet();
        return bogusDomain;
    }

    protected int[] insertBogusBetweenTwinAndAtOddEnd(int[] input) {
        // need something dynamic.
        ArrayList<Integer> outp = new ArrayList<>();

        outp.add(0, input[0]);
        // start to inserting bogus element.
        for (int idx = 1; idx < input.length; idx++) {
            if (input[idx] == input[idx - 1]) {
                outp.add(bogusDomain);
                outp.add(input[idx]);
            } else {
                outp.add(input[idx]);
            }
        }

        // if size is odd, we need it to be even.
        if (outp.size() % 2 != 0) {
            outp.add(bogusDomain);
        }

        // convert it to primitive int array.
        int[] output = new int[outp.size()];
        int i = 0;
        for (Integer integer : outp) {
            output[i++] = integer;
        }
        return output;
    }

    protected int[] removeBogus(int[] input) {
        // need something dynamic.
        ArrayList<Integer> outp = new ArrayList<>();

        outp.add(0, input[0]);
        // start to removing bogus element.
        for (int idx = 1; idx < input.length; idx++) {
            if (input[idx] == bogusDomain) {
                if (idx + 1 < input.length && input[idx - 1] == input[idx + 1]) {
                    outp.add(input[idx + 1]);
                    idx++;
                    continue;
                }
            }
            outp.add(input[idx]);
        }

        // if the last element is bogusDomain, remove it.
        if (outp.get(outp.size() - 1) == bogusDomain) {
            outp.remove(outp.size() - 1);
        }

        // convert it to primitive int array.
        int[] output = new int[outp.size()];
        int i = 0;
        for (Integer integer : outp) {
            output[i++] = integer;
        }
        return output;
    }

    private void isKeySet() {
        if (key == null
                || key.length == 0) {
            throw new InappropriateKeyException("Set the key first.");
        }
    }

    private void isBogusDomainSet() {
        if (bogusDomain == -1) {
            throw new InvalidBogusDomainException("Bogus domain value need to be set");
        }
    }

    protected final int[] findPositionInKey(int element) {
        isKeySet();
        /*for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[i].length; j++) {
                if (key[i][j] == element) {
                    return new int[]{i, j};
                }
            }
        }*/
        
        return valToPosKey.get(element).getPosition();
    }

    protected final int getElementInKey(int x, int y) {
        isKeySet();
        return key[x][y];
    }

    private static boolean hasNoDuplicateElement(int[][] keyA) {
        HashSet<Integer> set = new HashSet<>();
        for (int[] r : keyA) {
            for (int c : r) {
                if (!set.add(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSquareMatrix(int[][] keyA, int elementDomain) {
        int ordo = (int) Math.sqrt(elementDomain);

        int c = 0;
        for (int[] r : keyA) {
            if (r.length != ordo) {
                return false;
            }
            c++;
        }

        return c == ordo;
    }

    protected int[] onEqualValueEncrypt(int[] digraph) {
        throw new RuntimeException("No twin digraph allowed in playfair cipher.");
    }

    protected int[] onSameRowEncrypt(int x1, int y1, int x2, int y2) {
        int[] output = new int[2];
        output[0] = getElementInKey(x1, (y1 + 1) % key[x1].length);
        output[1] = getElementInKey(x2, (y2 + 1) % key[x2].length);
        return output;
    }

    protected int[] onSameColumnEncrypt(int x1, int y1, int x2, int y2) {
        int[] output = new int[2];
        output[0] = getElementInKey((x1 + 1) % key.length, y1);
        output[1] = getElementInKey((x2 + 1) % key.length, y2);
        return output;
    }

    protected int[] onDifferentRowAndColumnEncrypt(int x1, int y1, int x2, int y2) {
        int[] output = new int[2];
        output[0] = getElementInKey(x1, y2);
        output[1] = getElementInKey(x2, y1);
        return output;
    }

    protected int[] onEqualValueDecrypt(int[] digraph) {
        throw new RuntimeException("No twin digraph allowed in playfair cipher.");
    }

    protected int[] onSameRowDecrypt(int x1, int y1, int x2, int y2) {
        int[] output = new int[2];
        int diff = y1 - 1;
        if (diff < 0) {
            diff = diff + key[x1].length;
        }
        output[0] = getElementInKey(x1, (diff) % key[x1].length);

        diff = y2 - 1;
        if (diff < 0) {
            diff = diff + key[x2].length;
        }
        output[1] = getElementInKey(x2, (diff) % key[x2].length);
        return output;
    }

    protected int[] onSameColumnDecrypt(int x1, int y1, int x2, int y2) {
        int[] output = new int[2];
        int diff = x1 - 1;
        if (diff < 0) {
            diff = diff + key.length;
        }
        output[0] = getElementInKey((diff) % key.length, y1);

        diff = x2 - 1;
        if (diff < 0) {
            diff = diff + key.length;
        }
        output[1] = getElementInKey((diff) % key.length, y2);
        return output;
    }

    protected int[] onDifferentRowAndColumnDecrypt(int x1, int y1, int x2, int y2) {
        int[] output = new int[2];
        output[0] = getElementInKey(x1, y2);
        output[1] = getElementInKey(x2, y1);
        return output;
    }
    
    private static class KeyPosition implements Comparable<KeyPosition>{
        int[] val;

        public KeyPosition(int i, int j) {
            this.val = new int[]{i,j};
        }
        
        public int[] getPosition(){
            return this.val;
        }

        @Override
        public int compareTo(KeyPosition o) {
            if(o.val[0] >= val[0] || (o.val[0] == val[0] && o.val[1] >= val[1])) return -1;
            if(o.val[0] <= val[0] || (o.val[0] == val[0] && o.val[1] <= val[1])) return 1;
            return 0;
        }
    }
}

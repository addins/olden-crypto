package org.addin.crypto.classic.core;

import java.util.ArrayList;
import java.util.HashSet;
import org.addin.crypto.classic.core.exception.InapropriateKeyException;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class PlayfairCipher implements Encipherment<int[]> {

    private int[][] key;
    private int bogusDomain;

    protected final int elementDomain;

    public PlayfairCipher(int elementDomain) {
        this.elementDomain = elementDomain;
    }

    @Override
    public int[] encrypt(int[] plainText) {
        isKeySet();

        int[] plainEven = insertBogusBetweenTwin(plainText);

        System.out.println("");
        for (int i = 0; i < plainEven.length; i++) {
            int j = plainEven[i];
            System.out.print(CharIntMapper.getCharRepresentative(j, true));

        }
        System.out.println("");

        int[] cipherText = new int[plainEven.length];

        for (int idx = 1; idx < plainEven.length; idx += 2) {
            int el1 = plainEven[idx - 1];
            int el2 = plainEven[idx];

            // position as x,y in key
            int[] pos1 = findPositionInKey(el1);
            int[] pos2 = findPositionInKey(el2);

            if (pos1[0] == pos2[0]) {
                cipherText[idx - 1] = getElementInKey(pos1[0], (pos1[1] + 1) % key[pos1[0]].length);
                cipherText[idx] = getElementInKey(pos2[0], (pos2[1] + 1) % key[pos2[0]].length);
            } else if (pos1[1] == pos2[1]) {
                cipherText[idx - 1] = getElementInKey((pos1[0] + 1) % key.length, pos1[1]);
                cipherText[idx] = getElementInKey((pos2[0] + 1) % key.length, pos2[1]);
            } else {
                cipherText[idx - 1] = getElementInKey(pos1[0], pos2[1]);
                cipherText[idx] = getElementInKey(pos2[0], pos1[1]);
            }
        }

        return cipherText;
    }

    @Override
    public int[] decrypt(int[] cipherText) {
        isKeySet();
        int[] plainText = new int[cipherText.length];

        for (int idx = 1; idx < cipherText.length; idx += 2) {
            int el1 = cipherText[idx - 1];
            int el2 = cipherText[idx];

            // position as x,y in key
            int[] pos1 = findPositionInKey(el1);
            int[] pos2 = findPositionInKey(el2);

            int diff = 0;

            if (pos1[0] == pos2[0]) {
                diff = pos1[1] - 1;
                if (diff < 0) {
                    diff = diff + key[pos1[0]].length;
                }
                plainText[idx - 1] = getElementInKey(pos1[0], (diff) % key[pos1[0]].length);

                diff = pos2[1] - 1;
                if (diff < 0) {
                    diff = diff + key[pos2[0]].length;
                }
                plainText[idx] = getElementInKey(pos2[0], (diff) % key[pos2[0]].length);
            } else if (pos1[1] == pos2[1]) {
                diff = pos1[0] - 1;
                if (diff < 0) {
                    diff = diff + key.length;
                }
                plainText[idx - 1] = getElementInKey((diff) % key.length, pos1[1]);

                diff = pos2[0] - 1;
                if (diff < 0) {
                    diff = diff + key.length;
                }
                plainText[idx] = getElementInKey((diff) % key.length, pos2[1]);
            } else {
                plainText[idx - 1] = getElementInKey(pos1[0], pos2[1]);
                plainText[idx] = getElementInKey(pos2[0], pos1[1]);
            }
        }
        // removing bogus
        plainText = removeBogus(plainText);

        return plainText;
    }

    @Override
    public void setKey(SimpleKey key) throws RuntimeException {
        int[][] keyA = (int[][]) key.getKey();
        if (hasNoDuplicateElement(keyA) && isSquareMatrix(keyA, this.elementDomain)) {
            this.key = keyA;
        }else{
            throw new InapropriateKeyException("Key cannot contain duplicate value and must be "
                    +Math.sqrt(elementDomain)+" square matrix.");
        }
    }

    public void setBogusDomain(int bogusDomain) {
        this.bogusDomain = bogusDomain;
    }

    public int getBogusDomain() {
        return bogusDomain;
    }

    private int[] insertBogusBetweenTwin(int[] input) {
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

    private int[] removeBogus(int[] input) {
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
            throw new InapropriateKeyException("Set the key first.");
        }
    }

    private int[] findPositionInKey(int element) {
        isKeySet();
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[i].length; j++) {
                if (key[i][j] == element) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int getElementInKey(int x, int y) {
        isKeySet();
        return key[x][y];
    }

    private static boolean hasNoDuplicateElement(int[][] keyA) {
        HashSet<Integer> set = new HashSet<>();
        for(int[] r : keyA){
            for(int c : r){
                if(!set.add(c))
                    return false;
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
}

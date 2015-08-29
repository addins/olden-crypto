package org.addin.crypto.classic.core.util;

import java.util.ArrayList;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class TextKeyGen extends SimpleKeyShuffling {

    private String text;

    public TextKeyGen(int size) {
        super(size, IntDomainCreator.getIntFromZeroTo(size*size));
    }

    public TextKeyGen(int size, String text) {
        this(size);
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int[] shuffling() {
        if(text==null || text.isEmpty())
            throw new NullPointerException("Text must not be null.");
        shufflingDomain();
        return domain;
    }
    
    private void shufflingDomain() {
        int[] res = null;
        int[] intText = null;
        intText = getIntsFromText(text);
        res = rearrange(intText);
        this.domain = res;
    }

    private int[] getIntsFromText(String text) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            if (!arrayList.contains(CharIntMapper.getIntRepresentative(text.charAt(i)))) {
                arrayList.add(CharIntMapper.getIntRepresentative(text.charAt(i)));
            }
        }

        int[] res = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Integer get = arrayList.get(i);
            res[i] = get;
        }
        return res;
    }

    private int[] rearrange(int[] theFirst) {
        int[] res = new int[size * size];
        for (int i = 0; i < theFirst.length; i++) {
            res[i] = theFirst[i];
        }
        
        for(int i = 0, idx=theFirst.length; i < domain.length; i++){
            if(!arrayHasValue(res, domain[i])){
                res[idx] = domain[i];
                idx++;
            }
        }
        return res;
    }
    
    private boolean arrayHasValue(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            int j = array[i];
            if(j == value)
                return true;
        }
        return false;
    }
}

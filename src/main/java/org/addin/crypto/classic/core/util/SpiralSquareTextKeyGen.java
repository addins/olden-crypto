package org.addin.crypto.classic.core.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class SpiralSquareTextKeyGen extends TextKeyGen{

    public SpiralSquareTextKeyGen(int size, String text) {
        super(size, text);
    }

    @Override
    public int[] shuffling() {
        int[] initial = super.shuffling();
        int[] reversed = new int[initial.length];
        
        for (int i = 0; i < reversed.length; i++) {
            reversed[i] = initial[initial.length-1-i];            
        }
        domain = reversed;
        return reversed;
    }

    @Override
    public int[][] generateMatrix() {
        int[][] initialMtx = super.generateMatrix();
        return makeSpiralSquareArray(initialMtx, size);
    }
    
    private int[][] makeSpiralSquareArray(int[][] input, int mXn){
        int top = 0, bottom = mXn-1, left = 0, right = mXn-1;
        int direction = 0; // 0 = to the right, 1 = to the bottom, 2 = to the left, 3 = upward.
        int[][] res = new int[mXn][mXn];
        int x = 0,y =0;
        
        while (top <= bottom && left <= right) {            
            if(direction == 0){
                for (int i = left; i <= right; i++) {
                    res[x][y] = input[top][i];
                    y++;
                    if(y==mXn){
                        y=0;
                        x++;
                    }
                    if(x==mXn)
                        break;
                }
                top++;
            }else if(direction == 1){
                for (int i = top; i <= bottom; i++) {
                    res[x][y] = input[i][right];
                    y++;
                    if(y==mXn){
                        y=0;
                        x++;
                    }
                    if(x==mXn)
                        break;
                }
                right--;
            }else if(direction == 2){
                for (int i = right; i >= left; i--) {
                    res[x][y] = input[bottom][i];
                    y++;
                    if(y==mXn){
                        y=0;
                        x++;
                    }
                    if(x==mXn)
                        break;
                }
                bottom--;
            }else if(direction == 3){
                for (int i = bottom; i >= top; i--) {
                    res[x][y] = input[i][left];
                    y++;
                    if(y==mXn){
                        y=0;
                        x++;
                    }
                    if(x==mXn)
                        break;
                }
                left++;
            }
            direction = (direction+1)%4;
        }
        return res;
    }
}

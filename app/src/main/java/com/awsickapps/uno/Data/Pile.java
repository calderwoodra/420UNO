package com.awsickapps.uno.data;

import java.util.Arrays;

/**
 * Created by allen on 3/15/15.
 */
public class Pile {

    int[] reds, blues, greens, yellows, wilds;
    int size;
    boolean isDraw;

    public Pile(boolean isDraw){

        this.isDraw = isDraw;
        reds = new int[13];
        blues = new int[13];
        greens = new int[13];
        yellows = new int[13];
        wilds = new int[2]; /* 0 : wild, 1 : wild draw 4 */

        if(isDraw){

            for(int i = 0; i < 9; i++){
                reds[i] = 2;
                blues[i] = 2;
                greens[i] = 2;
                yellows[i] = 2;
            }

            wilds[0] = wilds[1] = 4;

        //isDiscard
        }else{
            Arrays.fill(reds, 0);
            Arrays.fill(blues, 0);
            Arrays.fill(greens, 0);
            Arrays.fill(yellows, 0);
            Arrays.fill(wilds, 0);
        }
    }

    public Card drawCard(){
        if(isDraw){
            return null;
        }else{
            throw new RuntimeException("Cannot draw from discard pile");
        }
    }

}

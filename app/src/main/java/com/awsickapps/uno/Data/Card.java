package com.awsickapps.uno.data;

import android.support.annotation.NonNull;

/**
 * Created by allen on 3/15/15.
 */
public class Card {

    enum Color {red, blue, green, yellow, wild}
    Color color;
    int number;

    /*
        1-9 : 1-9
        10  : Skip
        11  : reverse
        12  : draw two
        13  : wild
        14  : wild draw four
     */

    public Card(int number,@NonNull Color color){

        if(number > 14 || number < 1)
            throw new RuntimeException("Invalid card number(" + number + ") must be between 1-14 inclusive.");

        this.number = number;
        this.color = color;
    }

    public int getImageResource(){

        switch (number){
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return 0;
            case 3:
                return 0;
            case 4:
                return 0;
            case 5:
                return 0;
            case 6:
                return 0;
            case 7:
                return 0;
            case 8:
                return 0;
            case 9:
                return 0;
            case 10:
                return 0;
            case 11:
                return 0;
            case 12:
                return 0;
            case 13:
                return 0;
            case 14:
                return 0;
            default:
                return 0;
        }
    }
}

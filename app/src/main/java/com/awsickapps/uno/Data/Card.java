package com.awsickapps.uno.data;

import android.support.annotation.NonNull;

import com.awsickapps.uno.Data;
import com.awsickapps.uno.R;

/**
 * Created by allen on 3/15/15.
 */
public class Card {

    public enum Color {red, blue, green, yellow, wild}
    public Color color;
    public int number;

    /*
        0-9 : 0-9
        10  : Skip
        11  : reverse
        12  : draw two
        13  : wild
        14  : wild draw four
     */

    public Card(int number,@NonNull Color color){

        if(number > 14 || number < 0)
            throw new RuntimeException("Invalid card number(" + number + ") must be between 0-14 inclusive.");

        this.number = number;
        this.color = color;
    }

    public int getImageResource(){

        switch (number){
            case 0:
                switch (color){
                    case red:
                        return R.drawable.red_0;
                    case blue:
                        return R.drawable.blue_0;
                    case green:
                        return R.drawable.green_0;
                    case yellow:
                        return R.drawable.yellow_0;
                }
            case 1:
                switch (color){
                    case red:
                        return R.drawable.red_1;
                    case blue:
                        return R.drawable.blue_1;
                    case green:
                        return R.drawable.green_1;
                    case yellow:
                        return R.drawable.yellow_1;
                }
            case 2:
                switch (color){
                    case red:
                        return R.drawable.red_2;
                    case blue:
                        return R.drawable.blue_2;
                    case green:
                        return R.drawable.green_2;
                    case yellow:
                        return R.drawable.yellow_2;
                }
            case 3:
                switch (color){
                    case red:
                        return R.drawable.red_3;
                    case blue:
                        return R.drawable.blue_3;
                    case green:
                        return R.drawable.green_3;
                    case yellow:
                        return R.drawable.yellow_3;
                }
            case 4:
                switch (color){
                    case red:
                        return R.drawable.red_4;
                    case blue:
                        return R.drawable.blue_4;
                    case green:
                        return R.drawable.green_4;
                    case yellow:
                        return R.drawable.yellow_4;
                }
            case 5:
                switch (color){
                    case red:
                        return R.drawable.red_5;
                    case blue:
                        return R.drawable.blue_5;
                    case green:
                        return R.drawable.green_5;
                    case yellow:
                        return R.drawable.yellow_5;
                }
            case 6:
                switch (color){
                    case red:
                        return R.drawable.red_6;
                    case blue:
                        return R.drawable.blue_6;
                    case green:
                        return R.drawable.green_6;
                    case yellow:
                        return R.drawable.yellow_6;
                }
            case 7:
                switch (color){
                    case red:
                        return R.drawable.red_7;
                    case blue:
                        return R.drawable.blue_7;
                    case green:
                        return R.drawable.green_7;
                    case yellow:
                        return R.drawable.yellow_7;
                }
            case 8:
                switch (color){
                    case red:
                        return R.drawable.red_8;
                    case blue:
                        return R.drawable.blue_8;
                    case green:
                        return R.drawable.green_8;
                    case yellow:
                        return R.drawable.yellow_8;
                }
            case 9:
                switch (color){
                    case red:
                        return R.drawable.red_9;
                    case blue:
                        return R.drawable.blue_9;
                    case green:
                        return R.drawable.green_9;
                    case yellow:
                        return R.drawable.yellow_9;
                }
            case Data.SKIP:
                switch (color){
                    case red:
                        return R.drawable.red_skip;
                    case blue:
                        return R.drawable.blue_skip;
                    case green:
                        return R.drawable.green_skip;
                    case yellow:
                        return R.drawable.yellow_skip;
                }
            case Data.REVERSE:
                switch (color){
                    case red:
                        return R.drawable.red_reverse;
                    case blue:
                        return R.drawable.blue_reverse;
                    case green:
                        return R.drawable.green_reverse;
                    case yellow:
                        return R.drawable.yellow_reverse;
                }
            case Data.DRAW_TWO:
                switch (color){
                    case red:
                        return R.drawable.red_add2;
                    case blue:
                        return R.drawable.blue_add2;
                    case green:
                        return R.drawable.green_add2;
                    case yellow:
                        return R.drawable.yellow_add2;
                }
            case Data.WILD:
                return R.drawable.wild_card;
            case Data.WILD_DRAW_FOUR:
                return R.drawable.wild_take4;
            default:
                return 0;
        }
    }

    public boolean canPlayOn(Card discard){
        return (discard.color == this.color) || (discard.number == this.number) ||
                (this.number >= 13);
    }

    public void setColor(Color color){
        if(number == Data.WILD || number == Data.WILD_DRAW_FOUR)
            this.color = color;
        else
            throw new RuntimeException("Cannot change the color of that card!");
    }
}

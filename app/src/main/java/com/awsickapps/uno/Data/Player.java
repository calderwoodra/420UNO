package com.awsickapps.uno.data;

import java.util.ArrayList;

/**
 * Created by allen on 3/15/15.
 */
public class Player {

    public String name;
    public ArrayList<Card> hand;

    public Player(String name){

        this.name = name;
        hand = new ArrayList<>();
    }
}

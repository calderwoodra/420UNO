package com.awsickapps.uno.data;

import java.util.ArrayList;

/**
 * Created by allen on 3/15/15.
 */
public class Player {

    public String name;
    public ArrayList<Card> hand;
    public boolean isAI;

    /* TODO: Possible refactor
    public TextView tvPlayer;
    public RecyclerView rvHand;
    public PlayerHandsAdapter adapter;
    */

    public Player(String name, boolean isAI){

        this.name = name;
        hand = new ArrayList<>();
        this.isAI = isAI;
    }
}

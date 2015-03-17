package com.awsickapps.uno.data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by allen on 3/15/15.
 */
public class Game {

    Pile draw, discard;
    ArrayList<Player> players;
    boolean isCCW = false;
    int currentPlayer;

    public Game(String name){

        draw = new Pile(true);
        discard = new Pile(false);
        currentPlayer = 0;
        players = new ArrayList<>();
        players.add(new Player(name));
        players.add(new Player("AI 1"));
        players.add(new Player("AI 2"));
        players.add(new Player("AI 3"));
    }

    public Card drawCard(){
        return draw.drawCard();
    }

    public void discardCard(Card card){
        discard.cards.add(card);
    }

    public void refillDrawPile(){
        draw = discard;
        discard = new Pile(false);
        draw.shuffle();
    }

    public boolean isCCW(){
        return isCCW;
    }

}



package com.awsickapps.uno.data;

import com.awsickapps.uno.PlayerHandsAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by allen on 3/15/15.
 */
public class Game {

    public Pile draw, discard;
    public ArrayList<Player> players;
    public Player currentPlayer;

    boolean isCCW = false;
    int numberOfPlayers = 4;

    public Game(String name){

        draw = new Pile(true);
        discard = new Pile(false);
        players = new ArrayList<>();
        players.add(new Player(name));
        players.add(new Player("AI 1"));
        players.add(new Player("AI 2"));
        players.add(new Player("AI 3"));
        currentPlayer = players.get(0);
        topToDiscard();
    }

    public Card drawCard(){
        return draw.drawCard();
    }

    public void discardCard(Card card){
        discard.add(card);
    }

    public void refillDrawPile(){
        draw = discard;
        discard = new Pile(false);
        discard.add(draw.cards.remove(0));
        draw.shuffle();
    }

    public boolean isCCW(){
        return isCCW;
    }

    public void topToDiscard(){
        discard.add(draw.drawCard());
    }

}



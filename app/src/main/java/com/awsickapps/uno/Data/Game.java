package com.awsickapps.uno.data;

import android.widget.Toast;

import com.awsickapps.uno.activities.PlayActivity;

import java.util.ArrayList;

/**
 * Created by allen on 3/15/15.
 */
public class Game {

    public Pile draw, discard;
    public ArrayList<Player> players;
    public Player currentPlayer;


    boolean isCCW = false;
    int numberOfPlayers, playerIndex;
    private PlayActivity activity;

    public Game(String name, PlayActivity activity){

        this.activity = activity;
        draw = new Pile(true);
        discard = new Pile(false);
        players = new ArrayList<>();
        players.add(new Player(name));
        players.add(new Player("AI 1"));
        players.add(new Player("AI 2"));
        players.add(new Player("AI 3"));
        numberOfPlayers = players.size();
        playerIndex = 0;
        currentPlayer = players.get(playerIndex);
        topToDiscard();
    }

    public Card drawCard(){
        return draw.drawCard();
    }

    public void discardCard(Card card){
        discard.add(card);
        if(isCCW)
            playerIndex--;
        else
            playerIndex++;

        currentPlayer = players.get(playerIndex%numberOfPlayers);
        activity.endTurn();
    }

    public void refillDrawPile(){
        Toast.makeText(activity, "**************reshuffle applied!********", Toast.LENGTH_LONG).show();
        draw = discard;
        draw.isDraw = true;
        discard = new Pile(false);
        discard.add(draw.cards.remove(0));  //Done so that the card on top of the discard pile remains
        draw.size--;                        //because of the remove operation happening above.
        draw.shuffle();
    }

    public boolean isCCW(){
        return isCCW;
    }

    public void topToDiscard(){
        discard.add(draw.drawCard());
    }

}



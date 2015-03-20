package com.awsickapps.uno.data;

import android.widget.Toast;

import com.awsickapps.uno.Data;
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

    public void discardCard(Card card){
        discard.add(card);
        boolean pickingColor = false;

        switch (card.number){
            case Data.REVERSE:
                isCCW = !isCCW;
                break;
            case Data.DRAW_TWO:
                draw(2);
                break;
            case Data.WILD_DRAW_FOUR:
                activity.pickColor();
                pickingColor = true;
                break;
            case Data.WILD:
                activity.pickColor();
                draw(4);
                pickingColor = true;
                break;
            case Data.SKIP:
                if(isCCW)
                    playerIndex--;
                else
                    playerIndex++;
                break;
        }

        if(isCCW)
            playerIndex--;
        else
            playerIndex++;

        if(playerIndex < 0)
            playerIndex += 4;

        if(!pickingColor) {
            if (!currentPlayer.hand.isEmpty()) {
                currentPlayer = players.get(playerIndex % numberOfPlayers);
                activity.endTurn();
            }else
                activity.endGame(currentPlayer);
        }else{
            currentPlayer = players.get(playerIndex % numberOfPlayers);
        }
    }

    public void refillDrawPile(){
        Toast.makeText(activity, "**************reshuffle applied!********", Toast.LENGTH_LONG).show();
        draw = discard;
        draw.isDraw = true;
        discard = new Pile(false);
        discard.add(draw.drawCard());
        draw.shuffle();
    }

    public void topToDiscard(){
        do {

            discard.add(draw.drawCard());

        }while(discard.getTop().color == Card.Color.wild);
    }

    private void draw(int cards){

    }


}



package com.awsickapps.uno.data;

import android.util.Log;
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
        players.add(new Player(name, false));
        players.add(new Player("AI 1", true));
        players.add(new Player("AI 2", true));
        players.add(new Player("AI 3", true));
        numberOfPlayers = players.size();
        playerIndex = 0;
        currentPlayer = players.get(playerIndex);
        topToDiscard();
    }

    public void discardCard(Card card, int position){

        discard.add(card);
        Player thisPlayer = currentPlayer;
        //activity.rvMap.get(currentPlayer).getLayoutManager().removeViewAt(position);

        switch (card.number){
            case Data.REVERSE:
                isCCW = !isCCW;
                break;
            /*case Data.WILD_DRAW_FOUR:
            case Data.WILD:
                activity.pickColor();
                break;*/
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
            playerIndex += numberOfPlayers;

        //TODO: possibly improve this logic
        if(card.number < 13) { //if not wild

            if (!currentPlayer.hand.isEmpty()) {
                currentPlayer = players.get(playerIndex % numberOfPlayers);
                if(card.number == Data.DRAW_TWO) activity.drawExtra(currentPlayer, 2);
                activity.endTurn();
            }else
                activity.endGame(currentPlayer);
        }else{
            currentPlayer = players.get(playerIndex % numberOfPlayers);
            if (card.number == Data.WILD_DRAW_FOUR) activity.drawExtra(currentPlayer, 4);
            activity.pickColor(thisPlayer);
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



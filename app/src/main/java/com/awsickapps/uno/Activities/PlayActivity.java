package com.awsickapps.uno.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.awsickapps.uno.PlayerHandsAdapter;
import com.awsickapps.uno.R;
import com.awsickapps.uno.data.Card;
import com.awsickapps.uno.data.Game;
import com.awsickapps.uno.data.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by allen on 3/15/15.
 */
public class PlayActivity extends Activity{

    ArrayList<Card> leftHand, topHand, rightHand, bottomHand;
    ImageView ivDraw, ivDiscard;
    RecyclerView rvLeft, rvRight, rvTop, rvBottom;
    PlayerHandsAdapter leftAdapter, rightAdapter, topAdapter, bottomAdapter;
    HashMap<Player, PlayerHandsAdapter> adapterMap;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        adapterMap = new HashMap<>();

        rvLeft   = (RecyclerView) findViewById(R.id.left);
        rvRight  = (RecyclerView) findViewById(R.id.right);
        rvBottom = (RecyclerView) findViewById(R.id.bottom);
        rvTop    = (RecyclerView) findViewById(R.id.top);
        ivDraw   = (ImageView) findViewById(R.id.draw);
        ivDiscard= (ImageView) findViewById(R.id.discard);

        setupGame();
        engageTurn(game.currentPlayer, game.discard.getTop());
    }

    //TODO: redo to accomidate 4+ players
    private void setupGame(){
        game = new Game("Player 1", this);
        ivDiscard.setImageResource(game.discard.getTop().getImageResource());
        ivDiscard.setVisibility(View.VISIBLE);

        bottomHand = game.players.get(0).hand;
        leftHand   = game.players.get(1).hand;
        topHand    = game.players.get(2).hand;
        rightHand  = game.players.get(3).hand;

        bottomAdapter = new PlayerHandsAdapter(this, game.players.get(0), game);
        leftAdapter   = new PlayerHandsAdapter(this, game.players.get(1), game);
        topAdapter    = new PlayerHandsAdapter(this, game.players.get(2), game);
        rightAdapter  = new PlayerHandsAdapter(this, game.players.get(3), game);

        adapterMap.put(game.players.get(0), bottomAdapter);
        adapterMap.put(game.players.get(1), leftAdapter);
        adapterMap.put(game.players.get(2), topAdapter);
        adapterMap.put(game.players.get(3), rightAdapter);

        rvLeft.setAdapter(leftAdapter);
        rvLeft.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvRight.setAdapter(rightAdapter);
        rvRight.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvTop.setAdapter(topAdapter);
        rvTop.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvBottom.setAdapter(bottomAdapter);
        rvBottom.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));

        for(int i =0; i < 7; i++){
            drawCard(bottomHand, bottomAdapter);
            drawCard(leftHand, leftAdapter);
            drawCard(rightHand, rightAdapter);
            drawCard(topHand, topAdapter);
        }
    }

    private void engageTurn(Player player, Card discard){

        if(!hasValidCard(discard, player.hand)){
            while(!hasValidCard(discard, player.hand)){
                drawCard(player.hand, adapterMap.get(player));
            }
        }
    }

    private void drawCard(ArrayList<Card> hand, PlayerHandsAdapter adapter){
        if(!game.draw.isEmpty()){
            hand.add(game.draw.drawCard());
            adapter.setCards(hand);
            adapter.notifyDataSetChanged();
            if(game.draw.isEmpty())
                ivDraw.setVisibility(View.INVISIBLE);
        }else{
            game.refillDrawPile();
            ivDiscard.setVisibility(View.VISIBLE);
            drawCard(hand, adapter);
        }
    }

    private boolean hasValidCard(Card discard, List<Card> hand){

        for(Card card : hand){
            if(card.canPlayOn(discard))
                return true;
        }

        return false;
    }

    public void endTurn(){
        Card discardTop = game.discard.getTop();
        ivDiscard.setImageResource(discardTop.getImageResource());
        engageTurn(game.currentPlayer, discardTop);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //TODO: display confirmation screen to leave the game.
    }
}

package com.awsickapps.uno.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awsickapps.uno.DiscardHistoryAdapter;
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

/*
    TODO:
        hide AI cards
        build AI decisions
        flesh out menu/options screens
        create parallax cards in hands
        create indicator for whose turn it is
        onclick discard, create history overlay
        add delays and transitions between card placements
        create animation for shuffling the discard to the deck
        create uno button
 */
public class PlayActivity extends Activity implements View.OnClickListener{

    ArrayList<Card> leftHand, topHand, rightHand, bottomHand;
    ImageView ivDraw, ivDiscard;
    Button bGreen, bRed, bYellow, bBlue;
    RecyclerView rvLeft, rvRight, rvTop, rvBottom, rvDiscard;
    PlayerHandsAdapter leftAdapter, rightAdapter, topAdapter, bottomAdapter;
    RelativeLayout rlDiscard;
    DiscardHistoryAdapter discardAdapter;
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
        rvDiscard= (RecyclerView) findViewById(R.id.discardHistoryList);
        ivDraw   = (ImageView) findViewById(R.id.draw);
        ivDiscard= (ImageView) findViewById(R.id.discard);
        bGreen   = (Button) findViewById(R.id.bGreen);
        bBlue    = (Button) findViewById(R.id.bBlue);
        bYellow  = (Button) findViewById(R.id.bYellow);
        bRed     = (Button) findViewById(R.id.bRed);
        rlDiscard= (RelativeLayout) findViewById(R.id.rlDiscard);

        rlDiscard.setOnClickListener(this);
        ivDiscard.setOnClickListener(this);
        bGreen.setOnClickListener(this);
        bBlue.setOnClickListener(this);
        bRed.setOnClickListener(this);
        bYellow.setOnClickListener(this);

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
        discardAdapter= new DiscardHistoryAdapter(this, game.discard.cards);

        adapterMap.put(game.players.get(0), bottomAdapter);
        adapterMap.put(game.players.get(1), leftAdapter);
        adapterMap.put(game.players.get(2), topAdapter);
        adapterMap.put(game.players.get(3), rightAdapter);

        rvLeft.setAdapter(leftAdapter);
        rvRight.setAdapter(rightAdapter);
        rvTop.setAdapter(topAdapter);
        rvBottom.setAdapter(bottomAdapter);
        rvDiscard.setAdapter(discardAdapter);
        rvLeft.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvRight.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvTop.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvBottom.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvDiscard.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));

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
            discardAdapter.cards = game.discard.cards;
            ivDraw.setVisibility(View.VISIBLE);
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

    public void pickColor(){
        bRed.setVisibility(View.VISIBLE);
        bBlue.setVisibility(View.VISIBLE);
        bGreen.setVisibility(View.VISIBLE);
        bYellow.setVisibility(View.VISIBLE);
    }

    public void endGame(Player player){
        //TODO: Make this more glorious...
        Toast.makeText(this, player.name + " has won!!!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //TODO: display confirmation screen to leave the game.
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBlue:
                game.discard.getTop().setColor(Card.Color.blue);
                colorChosen();
                break;
            case R.id.bYellow:
                game.discard.getTop().setColor(Card.Color.yellow);
                colorChosen();
                break;
            case R.id.bGreen:
                game.discard.getTop().setColor(Card.Color.green);
                colorChosen();
                break;
            case R.id.bRed :
                game.discard.getTop().setColor(Card.Color.red);
                colorChosen();
                break;
            case R.id.discard:
                discardAdapter.reverseCards();
                discardAdapter.notifyDataSetChanged();
                rlDiscard.setVisibility(View.VISIBLE);
                break;
            case R.id.rlDiscard:
                rlDiscard.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void colorChosen(){
        bRed.setVisibility(View.INVISIBLE);
        bBlue.setVisibility(View.INVISIBLE);
        bGreen.setVisibility(View.INVISIBLE);
        bYellow.setVisibility(View.INVISIBLE);
        endTurn();
    }

    public void drawExtra(Player player, int draw){
        for (int i = 0; i < draw; i++)
            drawCard(player.hand, adapterMap.get(player));

    }
}

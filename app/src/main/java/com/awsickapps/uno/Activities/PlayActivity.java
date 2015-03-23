package com.awsickapps.uno.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awsickapps.uno.Data;
import com.awsickapps.uno.DiscardHistoryAdapter;
import com.awsickapps.uno.PlayerHandsAdapter;
import com.awsickapps.uno.R;
import com.awsickapps.uno.VerticalTextView;
import com.awsickapps.uno.data.Card;
import com.awsickapps.uno.data.Game;
import com.awsickapps.uno.data.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by allen on 3/15/15.
 * ${PACKAGE_NAME}
 */
/*
    TODO:
        hide AI cards
        build AI decisions
        flesh out menu/options screens
        create parallax cards in hands
        create indicator for what the current color is
        add delays and transitions between card placements
        create animation for shuffling the discard to the deck
        create uno button(will use the deck, give a 5 second delay to press it or they have to draw!)
 */
public class PlayActivity extends Activity implements View.OnClickListener{

    Game game;
    RelativeLayout rlDiscard;
    TextView tvTop, tvBottom;
    RelativeLayout rlGameBoard;
    LinearLayout llChooseColor;
    ImageView ivDraw, ivDiscard;
    VerticalTextView tvLeft, tvRight;
    Button bGreen, bRed, bYellow, bBlue;
    DiscardHistoryAdapter discardAdapter;
    HashMap<Player, TextView> textViewMap;
    HashMap<Player, PlayerHandsAdapter> adapterMap;
    ArrayList<Card> leftHand, topHand, rightHand, bottomHand;
    RecyclerView rvLeft, rvRight, rvTop, rvBottom, rvDiscard;
    PlayerHandsAdapter leftAdapter, rightAdapter, topAdapter, bottomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        adapterMap = new HashMap<>();
        textViewMap= new HashMap<>();

        rvLeft        = (RecyclerView) findViewById(R.id.left);
        rvRight       = (RecyclerView) findViewById(R.id.right);
        rvBottom      = (RecyclerView) findViewById(R.id.bottom);
        rvTop         = (RecyclerView) findViewById(R.id.top);
        rvDiscard     = (RecyclerView) findViewById(R.id.discardHistoryList);
        ivDraw        = (ImageView) findViewById(R.id.draw);
        ivDiscard     = (ImageView) findViewById(R.id.discard);
        bGreen        = (Button) findViewById(R.id.bGreen);
        bBlue         = (Button) findViewById(R.id.bBlue);
        bYellow       = (Button) findViewById(R.id.bYellow);
        bRed          = (Button) findViewById(R.id.bRed);
        rlDiscard     = (RelativeLayout) findViewById(R.id.rlDiscard);
        tvLeft        = (VerticalTextView) findViewById(R.id.tvLeft);
        tvRight       = (VerticalTextView) findViewById(R.id.tvRight);
        tvBottom      = (TextView) findViewById(R.id.tvBottom);
        tvTop         = (TextView) findViewById(R.id.tvTop);
        rlGameBoard   = (RelativeLayout) findViewById(R.id.rlGameBoard);
        llChooseColor = (LinearLayout) findViewById(R.id.llChoseColor);

        rlDiscard.setOnClickListener(this);
        ivDiscard.setOnClickListener(this);
        bGreen.setOnClickListener(this);
        bBlue.setOnClickListener(this);
        bRed.setOnClickListener(this);
        bYellow.setOnClickListener(this);

        setupGame();
        engageTurn(game.currentPlayer, game.discard.getTop());
    }
    public void endTurn(){
        Card discardTop = game.discard.getTop();
        ivDiscard.setImageResource(discardTop.getImageResource());
        setCurrentColor();
        engageTurn(game.currentPlayer, discardTop);
    }
    public void pickColor(){
        llChooseColor.setVisibility(View.VISIBLE);
    }
    public void endGame(Player player){

        Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra(Data.WINNING_SCREEN_KEY, true);
        intent.putExtra(Data.WINNER_NAME_KEY, player.name);
        startActivityForResult(intent, Data.WIN_REQUEST_CODE);
    }
    public void drawExtra(Player player, int draw){
        for (int i = 0; i < draw; i++)
            drawCard(player.hand, adapterMap.get(player));

    }

    private void colorChosen(){
        llChooseColor.setVisibility(View.INVISIBLE);
        endTurn();
    }
    private boolean hasValidCard(Card discard, List<Card> hand){

        for(Card card : hand){
            if(card.canPlayOn(discard))
                return true;
        }

        return false;
    }
    //TODO: redo to accommadate 4+ players
    private void setupGame(){
        game = new Game("Player 1", this);
        ivDiscard.setImageResource(game.discard.getTop().getImageResource());
        setCurrentColor();
        ivDiscard.setVisibility(View.VISIBLE);

        tvBottom.setText(game.players.get(0).name);
        tvLeft.setText(game.players.get(1).name);
        tvTop.setText(game.players.get(2).name);
        tvRight.setText(game.players.get(3).name);

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

        textViewMap.put(game.players.get(0), tvBottom);
        textViewMap.put(game.players.get(1), tvLeft);
        textViewMap.put(game.players.get(2), tvTop);
        textViewMap.put(game.players.get(3), tvRight);

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

        tvBottom.setBackgroundColor(Color.TRANSPARENT);
        tvLeft.setBackgroundColor(Color.TRANSPARENT);
        tvRight.setBackgroundColor(Color.TRANSPARENT);
        tvTop.setBackgroundColor(Color.TRANSPARENT);

        textViewMap.get(game.currentPlayer).setBackgroundColor(Color.BLACK);

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
            ivDraw.setVisibility(View.VISIBLE);
            drawCard(hand, adapter);
        }
    }
    private void setCurrentColor(){
        switch (game.discard.getTop().color) {
            case yellow:
                rlGameBoard.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            case blue:
                rlGameBoard.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case green:
                rlGameBoard.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case wild:
                rlGameBoard.setBackgroundColor(getResources().getColor(R.color.wild));
                break;
            case red:
                rlGameBoard.setBackgroundColor(getResources().getColor(R.color.red));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra(Data.QUIT_KEY, true);
        startActivityForResult(intent, Data.QUIT_REQUEST_CODE);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Data.QUIT_REQUEST_CODE){
            if(data.getBooleanExtra(Data.QUIT_KEY, false))
                finish();
        }else if(requestCode == Data.WIN_REQUEST_CODE){
            finish();
        }
    }
}

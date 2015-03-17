package com.awsickapps.uno.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.awsickapps.uno.PlayerHandsAdapter;
import com.awsickapps.uno.R;
import com.awsickapps.uno.data.Card;
import com.awsickapps.uno.data.Game;

import java.util.ArrayList;

/**
 * Created by allen on 3/15/15.
 */
public class PlayActivity extends Activity{

    ArrayList<Card> leftHand, topHand, rightHand, bottomHand;
    RecyclerView rvLeft, rvRight, rvTop, rvBottom;
    PlayerHandsAdapter leftAdapter, rightAdapter, topAdapter, bottomAdapter;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        leftHand   = new ArrayList<>();
        topHand    = new ArrayList<>();
        rightHand  = new ArrayList<>();
        bottomHand = new ArrayList<>();

        rvLeft   = (RecyclerView) findViewById(R.id.left);
        rvRight  = (RecyclerView) findViewById(R.id.right);
        rvBottom = (RecyclerView) findViewById(R.id.top);
        rvTop    = (RecyclerView) findViewById(R.id.bottom);

        setupGame();
    }

    private void setupGame(){
        game = new Game("Allen");
        bottomAdapter = new PlayerHandsAdapter(this, bottomHand);
        leftAdapter = new PlayerHandsAdapter(this, leftHand);
        rightAdapter = new PlayerHandsAdapter(this, rightHand);
        topAdapter = new PlayerHandsAdapter(this, topHand);
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

    private void drawCard(ArrayList<Card> hand, PlayerHandsAdapter adapter){
        hand.add(game.drawCard());
        adapter.setCards(hand);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //TODO: display confirmation screen to leave the game.
    }
}

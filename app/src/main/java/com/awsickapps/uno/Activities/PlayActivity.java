package com.awsickapps.uno.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.awsickapps.uno.R;
import com.awsickapps.uno.data.Card;

import java.util.ArrayList;

/**
 * Created by allen on 3/15/15.
 */
public class PlayActivity extends Activity{

    FrameLayout bottom, left, top, right;
    LayoutInflater inflater;
    ArrayList<Card> leftHand, topHand, rightHand, bottomHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        bottom = (FrameLayout) findViewById(R.id.flBottom);
        right  = (FrameLayout) findViewById(R.id.flRight);
        left   = (FrameLayout) findViewById(R.id.flLeft);
        top    = (FrameLayout) findViewById(R.id.flTop);

        leftHand   = new ArrayList<>();
        topHand    = new ArrayList<>();
        rightHand  = new ArrayList<>();
        bottomHand = new ArrayList<>();

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        setupGame();
    }

    private void setupGame(){
        drawCard(bottom);
        drawCard(top);
        drawCard(left);
        drawCard(right);
    }

    private void drawCard(FrameLayout parent){

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //TODO: display confirmation screen to leave the game.
    }
}

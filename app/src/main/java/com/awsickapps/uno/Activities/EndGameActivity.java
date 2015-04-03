package com.awsickapps.uno.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.awsickapps.uno.Data;
import com.awsickapps.uno.R;

/**
 * Created by allen on 3/21/15.
 */
public class EndGameActivity extends Activity {


    TextView tvDeatils;
    Button button1, button2;
    Intent intent;

    /*
        this activity will be used for 2 screens:
            quit game confirmation
            game winner presentation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game_dialog);
        intent = getIntent();

        tvDeatils = (TextView) findViewById(R.id.tvDetails);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        if(intent.getBooleanExtra(Data.WINNING_SCREEN_KEY, false))
            winGame();
        else
            confirmExit();

    }

    private void winGame(){
        tvDeatils.setText(intent.getStringExtra(Data.WINNER_NAME_KEY) + " wins!!!");
        button1.setText("Play again");
        button2.setText("Exit Game");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Data.RESTART_RESULT_CODE);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Data.QUIT_RESULT_CODE);
                finish();
            }
        });
    }

    private void confirmExit(){
        tvDeatils.setText("Would you like to restart the game?");
        button1.setText("No");
        button2.setText("Yes");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Data.RESTART_RESULT_CODE);
                finish();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

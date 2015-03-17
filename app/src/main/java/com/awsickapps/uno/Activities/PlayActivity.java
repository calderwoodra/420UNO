package com.awsickapps.uno.activities;

import android.app.Activity;
import android.os.Bundle;

import com.awsickapps.uno.R;

/**
 * Created by allen on 3/15/15.
 */
public class PlayActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //TODO: display confirmation screen to leave the game.
    }
}

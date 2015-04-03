package com.awsickapps.uno.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.awsickapps.uno.R;

public class MenuActivity extends ActionBarActivity implements View.OnClickListener{

    Button bPlay, bOptions, bHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bOptions = (Button) findViewById(R.id.bOptions);
        bPlay = (Button) findViewById(R.id.bPlay);
        bHighScores = (Button) findViewById(R.id.bHighScores);
        bOptions.setOnClickListener(this);
        bPlay.setOnClickListener(this);
        bHighScores.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bOptions:
                this.startActivity(this, OptionsActivity.class);
                break;
            case R.id.bPlay:
                this.startActivity(this, PlayActivity.class);
                break;
            case R.id.bHighScores:
                this.startActivity(this, HighScoresActivity.class);
                break;
        }
    }

    private void startActivity(Context context, Class c){
        Intent i = new Intent(context, c);
        startActivity(i);
    }
}

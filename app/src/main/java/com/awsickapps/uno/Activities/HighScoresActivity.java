package com.awsickapps.uno.activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.awsickapps.uno.Data;
import com.awsickapps.uno.R;

import java.util.Arrays;

/**
 * Created by allen on 4/3/15.
 * com.awsickapps.uno.activities
 */
public class HighScoresActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores_layout);
        ListAdapter adapter = new ListAdapter(this);
        getListView().setAdapter(adapter);
    }

    private class ListAdapter extends BaseAdapter{

        LayoutInflater inflater;
        int[] highScores;
        Context context;

        public ListAdapter(Context context){
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.context = context;
            String[] scores = Data.getHighScores(context).split(",");
            highScores = new int[scores.length];
            for(int i = 0; i < scores.length; i++)
                highScores[i] = Integer.parseInt(scores[i]);

            Arrays.sort(highScores);

        }

        @Override
        public int getCount() {
            return highScores.length;
        }

        @Override
        public Object getItem(int position) {
            return highScores[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView row = (TextView) inflater.inflate(R.layout.highscore_row, parent, false);
            row.setText((position + 1) + ".\t" + highScores[getCount() - position - 1]);
            return row;
        }
    }
}

package com.awsickapps.uno.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awsickapps.uno.Data;
import com.awsickapps.uno.DiscardHistoryAdapter;
import com.awsickapps.uno.PlayerHandsAdapter;
import com.awsickapps.uno.R;
import com.awsickapps.uno.data.Card;
import com.awsickapps.uno.data.Game;
import com.awsickapps.uno.data.GameScores;
import com.awsickapps.uno.data.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by allen on 3/15/15.
 * ${PACKAGE_NAME}
 */
/*
    TODO:
        flesh out menu/options screens
        create parallax cards in hands
        create animation for shuffling the discard to the deck
 */
public class PlayActivity extends Activity implements View.OnClickListener{

    int numOfDraws = 0;
    private static final int animationTime = 300;
    private static final int aiTurnDuration = 1250;

    private Context context;

    private boolean unoCalled = false;

    Game game;
    RelativeLayout rlDiscard, rlScores;
    TextView tvTop, tvBottom, tvLeft, tvRight;
    TextView tvTopScore, tvLeftScore, tvRightScore, tvBottomScore, tvScores;
    RelativeLayout rlGameBoard;
    Button bGreen, bRed, bYellow, bBlue, bUno, bScores;
    DiscardHistoryAdapter discardAdapter;
    HashMap<Player, TextView> textViewMap;
    LinearLayout llChooseColor, llDrawDiscard;
    public HashMap<Player, RecyclerView> rvMap;
    public ImageView ivDraw, ivDiscard;
    HashMap<Player, PlayerHandsAdapter> adapterMap;
    ArrayList<Card> leftHand, topHand, rightHand, bottomHand;
    public RecyclerView rvLeft, rvRight, rvTop, rvBottom, rvDiscard;
    PlayerHandsAdapter leftAdapter, rightAdapter, topAdapter, bottomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        adapterMap = new HashMap<>();
        textViewMap= new HashMap<>();
        rvMap      = new HashMap<>();

        context = this;

        rvLeft          = (RecyclerView) findViewById(R.id.left);
        rvRight         = (RecyclerView) findViewById(R.id.right);
        rvBottom        = (RecyclerView) findViewById(R.id.bottom);
        rvTop           = (RecyclerView) findViewById(R.id.top);
        rvDiscard       = (RecyclerView) findViewById(R.id.discardHistoryList);
        ivDraw          = (ImageView) findViewById(R.id.draw);
        ivDiscard       = (ImageView) findViewById(R.id.discard);
        bGreen          = (Button) findViewById(R.id.bGreen);
        bBlue           = (Button) findViewById(R.id.bBlue);
        bYellow         = (Button) findViewById(R.id.bYellow);
        bRed            = (Button) findViewById(R.id.bRed);
        bUno            = (Button) findViewById(R.id.bUno);
        bScores         = (Button) findViewById(R.id.bScores);
        tvLeft          = (TextView) findViewById(R.id.tvLeft);
        tvRight         = (TextView) findViewById(R.id.tvRight);
        tvBottom        = (TextView) findViewById(R.id.tvBottom);
        tvTop           = (TextView) findViewById(R.id.tvTop);
        tvBottomScore   = (TextView) findViewById(R.id.tvBottomScore);
        tvRightScore    = (TextView) findViewById(R.id.tvRightScore);
        tvLeftScore     = (TextView) findViewById(R.id.tvLeftScore);
        tvTopScore      = (TextView) findViewById(R.id.tvTopScore);
        tvScores        = (TextView) findViewById(R.id.tvScores);
        rlDiscard       = (RelativeLayout) findViewById(R.id.rlDiscard);
        rlGameBoard     = (RelativeLayout) findViewById(R.id.rlGameBoard);
        rlScores        = (RelativeLayout) findViewById(R.id.rlScore);
        llChooseColor   = (LinearLayout) findViewById(R.id.llChoseColor);
        llDrawDiscard   = (LinearLayout) findViewById(R.id.llDrawDiscard);

        rlDiscard.setOnClickListener(this);
        ivDiscard.setOnClickListener(this);
        rlScores.setOnClickListener(this);
        ivDraw.setOnClickListener(this);
        bGreen.setOnClickListener(this);
        bBlue.setOnClickListener(this);
        bRed.setOnClickListener(this);
        bYellow.setOnClickListener(this);
        bUno.setOnClickListener(this);
        bScores.setOnClickListener(this);

        setupGame();
        engageTurn(game.currentPlayer, game.discard.getTop());
    }
    public void endTurn(){
        final Card discardTop = game.discard.getTop();
        ivDiscard.setImageResource(discardTop.getImageResource());
        setCurrentColor();
        adjustHandSize();
        if(game.currentPlayer.isAI) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    engageTurn(game.currentPlayer, discardTop);
                }
            }, aiTurnDuration);
        }else{
            engageTurn(game.currentPlayer, discardTop);
        }

        //tvBottom.setVisibility(View.INVISIBLE);//.setBackgroundColor(Color.TRANSPARENT);
        //tvLeft.setVisibility(View.INVISIBLE);//.setBackgroundColor(Color.TRANSPARENT);
        //tvRight.setVisibility(View.INVISIBLE);//.setBackgroundColor(Color.TRANSPARENT);
        //tvTop.setVisibility(View.INVISIBLE);//.setBackgroundColor(Color.TRANSPARENT);

        //TextView tv = textViewMap.get(game.currentPlayer);
        //tv.setVisibility(View.VISIBLE);//.setBackgroundColor(Color.BLACK);
        //tv.setText(game.currentPlayer.name + ", " + game.currentPlayer.hand.size() + " cards remaining");

        rvLeft.setBackgroundColor(Color.TRANSPARENT);
        rvBottom.setBackgroundColor(Color.TRANSPARENT);
        rvTop.setBackgroundColor(Color.TRANSPARENT);
        rvRight.setBackgroundColor(Color.TRANSPARENT);

        RecyclerView rv = rvMap.get(game.currentPlayer);
        rv.setBackgroundResource(R.drawable.border);
        updateHandSize();
    }
    public void updateHandSize(){
        textViewMap.get(game.currentPlayer).setText("" + game.currentPlayer.hand.size());
    }
    public void initiateUno(final Player player){
        if (player.hand.size() == 1 && !player.isAI) {
            unoCalled = false;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!unoCalled) {
                        drawExtra(player, 2);
                        Toast.makeText(context, "Forgot to call Uno!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, aiTurnDuration*3);
        }
    }
    public void pickColor(Player lastPlayer){

        llChooseColor.setVisibility(View.VISIBLE);
        if(lastPlayer.isAI){
            chooseColor(getMaxColor(lastPlayer.hand));
        }
    }
    public void endGame(Player player){

        int score = 0;
        for(Player p : game.players){
            for(Card c : p.hand){
                score += c.getValue();
            }
        }

        int result = Data.setScore(Integer.parseInt("" + player.name.charAt(player.name.length() - 1)), score );
        if(result != -1){
            rlScores.setVisibility(View.VISIBLE);
            setupScoreView(tvScores);
            Toast.makeText(this, "Player " + result + " is the GRAND WINNER!!!", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Player " + result + " is the GRAND WINNER!!!", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Player " + result + " is the GRAND WINNER!!!", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Data.gameScores = Collections.emptyList();
                    finish();
                }
            }, 10000);
        }else {
            Intent intent = new Intent(this, EndGameActivity.class);
            intent.putExtra(Data.WINNING_SCREEN_KEY, true);
            intent.putExtra(Data.WINNER_NAME_KEY, player.name);
            intent.putExtra(Data.HIGH_SCORES_KEY, score);
            startActivityForResult(intent, Data.WIN_REQUEST_CODE);
        }
    }
    public void drawExtra(Player player, int draw){
        for (int i = 0; i < draw; i++)
            drawCard(player.hand, adapterMap.get(player), rvMap.get(player));
    }

    private void colorChosen(){
        llChooseColor.setVisibility(View.INVISIBLE);
        endTurn();
    }
    private int hasValidCard(Card discard, List<Card> hand){

        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).canPlayOn(discard))
                return i;
        }

        return -1;
    }

    //TODO: redo to accommadate 4+ players
    private void setupGame(){
        game = new Game("Player 4", this);
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

        rvMap.put(game.players.get(0), rvBottom);
        rvMap.put(game.players.get(1), rvLeft);
        rvMap.put(game.players.get(2), rvTop);
        rvMap.put(game.players.get(3), rvRight);

        textViewMap.put(game.players.get(0), tvBottomScore);
        textViewMap.put(game.players.get(1), tvLeftScore);
        textViewMap.put(game.players.get(2), tvTopScore);
        textViewMap.put(game.players.get(3), tvRightScore);


        rvLeft.setAdapter(leftAdapter);
        rvRight.setAdapter(rightAdapter);
        rvTop.setAdapter(topAdapter);
        rvBottom.setAdapter(bottomAdapter);
        rvDiscard.setAdapter(discardAdapter);
        rvLeft.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRight.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDiscard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        for(int i = 0; i < 7; i++){
            drawCard(bottomHand, bottomAdapter, rvBottom);
            drawCard(leftHand, leftAdapter, rvLeft);
            drawCard(rightHand, rightAdapter, rvRight);
            drawCard(topHand, topAdapter, rvTop);
        }
    }
    private void engageTurn(Player player, Card discard){

        int position;
        while((position = hasValidCard(discard, player.hand)) < 0)
            drawCard(player.hand, adapterMap.get(player), rvMap.get(player));

        adjustHandSize();

        if (player.isAI)
            adapterMap.get(game.currentPlayer).playCard(position);
    }
    public void adjustHandSize(){
        if(game.currentPlayer.name.equals("Player 4")){
            int size = game.currentPlayer.hand.size();
            if(size < 8){
                rvBottom.getLayoutParams().width = 16 + size * (int)getResources().getDimension(R.dimen.card_width);
            }else{
                rvBottom.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        }
    }
    private Card.Color getMaxColor(ArrayList<Card> hand){

        int blue = 0, red = 0, green = 0, yellow = 0;
        for(Card card : hand){
            if(card.number < 13) {
                if (card.color == Card.Color.blue)
                    blue++;
                if (card.color == Card.Color.green)
                    green++;
                if (card.color == Card.Color.red)
                    red++;
                if (card.color == Card.Color.yellow)
                    yellow++;
            }
        }

        if((blue >= red) && (blue >= green) && (blue >= yellow))
            return Card.Color.blue;
        else if((red >= blue) && (red >= green) && (red >= yellow))
            return Card.Color.red;
        else if((yellow >= red) && (yellow >= green) && (yellow >= blue))
            return Card.Color.yellow;
        else
            return Card.Color.green;
    }
    private void chooseColor(Card.Color color){
        switch (color){
            case blue:
                bBlue.performClick();
                break;
            case green:
                bGreen.performClick();
                break;
            case yellow:
                bYellow.performClick();
                break;
            case red:
                bRed.performClick();
                break;
        }
    }
    private void drawCard(ArrayList<Card> hand, PlayerHandsAdapter adapter, RecyclerView rv){
        if(!game.draw.isEmpty()){
            //drawCardAnimation();
            hand.add(game.draw.drawCard());
            adapter.setCards(hand);
            //adapter.notifyItemInserted(hand.size());
            adapter.notifyDataSetChanged();
            if(game.draw.isEmpty())
                ivDraw.setVisibility(View.INVISIBLE);
        }else{
            game.refillDrawPile();
            ivDraw.setVisibility(View.VISIBLE);
            drawCard(hand, adapter, rv);
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

    private void setupScoreView(TextView tvScores){
        int[] totals = Data.getTotals();
        String text = "History: \n";
        for(GameScores gs : Data.gameScores)
            text += "Player " + gs.winningPlayer + "'s Score: " + gs.scoreEarner + "\n";

        text += "\n\ntotals: \n";
        text += "Player 1 total: " + totals[0] + "\n";
        text += "Player 2 total: " + totals[1] + "\n";
        text += "Player 3 total: " + totals[2] + "\n";
        text += "Player 4 total: " + totals[3];

        tvScores.setText(text);
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
                //discardAdapter.reverseCards();
                discardAdapter.notifyDataSetChanged();
                rlDiscard.setVisibility(View.VISIBLE);
                break;
            case R.id.rlDiscard:
                rlDiscard.setVisibility(View.INVISIBLE);
                //discardAdapter.reverseCards();
                break;
            case R.id.bUno:
                unoCalled = true;
                Toast.makeText(this, "UNO CALLED!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bScores:
                rlScores.setVisibility(View.VISIBLE);
                setupScoreView(tvScores);
                break;
            case R.id.rlScore:
                rlScores.setVisibility(View.GONE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Data.RESTART_RESULT_CODE){
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
            finish();
        }else if(resultCode == Data.QUIT_RESULT_CODE){
            finish();
        }
    }
}

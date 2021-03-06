package com.awsickapps.uno;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.awsickapps.uno.activities.PlayActivity;
import com.awsickapps.uno.data.Card;
import com.awsickapps.uno.data.Game;
import com.awsickapps.uno.data.Player;

import java.util.Collections;
import java.util.List;

/**
 * Created by allen on 3/17/15.
 */
public class PlayerHandsAdapter extends RecyclerView.Adapter<PlayerHandsAdapter.CardViewHolder>{


    LayoutInflater inflater;
    List<Card> cards = Collections.emptyList();
    Game game;
    Context context;
    Player player;
    PlayActivity playActivity;

    public PlayerHandsAdapter(Context context, Player player, Game game){
        inflater = LayoutInflater.from(context);
        cards = player.hand;
        this.game = game;
        this.context = context;
        playActivity = ((PlayActivity) context);
        this.player = player;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.card_view, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int i) {
        if(player.isAI)
            cardViewHolder.ivCard.setImageResource(R.drawable.uno_back);
        else
            cardViewHolder.ivCard.setImageResource(cards.get(i).getImageResource());

        cardViewHolder.ta = new TranslateAnimation(
                cardViewHolder.ivCard.getX(),
                playActivity.ivDiscard.getX(),
                cardViewHolder.ivCard.getY(),
                playActivity.ivDiscard.getY());
        cardViewHolder.ta.setDuration(1000);

        cardViewHolder.ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player == game.currentPlayer)
                    if (cards.get(i).canPlayOn(game.discard.getTop())) {
                        cardViewHolder.ivCard.setImageResource(cards.get(i).getImageResource());
                        cardViewHolder.ta.start();
                        playCard(i);
                    } else
                        Toast.makeText(context, "Card is not legal!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Not your turn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void playCard(int i){
        Log.d("playCard", "Color: " + cards.get(i).color + "\t\tnumber: " + cards.get(i).number + "\tplayer: " + game.currentPlayer.name);
        game.discardCard(cards.remove(i), i);
        notifyDataSetChanged();

    }

    public void setCards(List<Card> cards){
        this.cards = cards;
        Collections.sort(cards);
    }

    class CardViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivCard;
        private TranslateAnimation ta;

        public CardViewHolder(View itemView) {
            super(itemView);
            ivCard = (ImageView) itemView.findViewById(R.id.ivCard);

        }
    }


}

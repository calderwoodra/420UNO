package com.awsickapps.uno;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

    public PlayerHandsAdapter(Context context, Player player, Game game){
        inflater = LayoutInflater.from(context);
        cards = player.hand;
        this.game = game;
        this.context = context;
        this.player = player;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.card_view, viewGroup, false);
        CardViewHolder cvh = new CardViewHolder(view);
        return cvh;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, final int i) {
        cardViewHolder.ivCard.setImageResource(cards.get(i).getImageResource());
        cardViewHolder.ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player == game.currentPlayer)
                    if(cards.get(i).canPlayOn(game.discard.getTop()))
                        playCard(i);
                    else
                        Toast.makeText(context, "Card is not legal!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Not your turn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playCard(int i){
        Toast.makeText(context, "Cards matched", Toast.LENGTH_SHORT).show();
        game.discardCard(cards.remove(i));
        this.notifyDataSetChanged();
    }

    public void setCards(List<Card> cards){
        this.cards = cards;
    }

    class CardViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivCard;

        public CardViewHolder(View itemView) {
            super(itemView);
            ivCard = (ImageView) itemView.findViewById(R.id.ivCard);

        }
    }
}

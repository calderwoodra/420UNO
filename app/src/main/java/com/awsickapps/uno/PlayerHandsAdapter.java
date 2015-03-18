package com.awsickapps.uno;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awsickapps.uno.data.Card;

import java.util.Collections;
import java.util.List;

/**
 * Created by allen on 3/17/15.
 */
public class PlayerHandsAdapter extends RecyclerView.Adapter<PlayerHandsAdapter.CardViewHolder>{


    LayoutInflater inflater;
    List<Card> cards = Collections.emptyList();

    public PlayerHandsAdapter(Context context, List<Card> cards){
        inflater = LayoutInflater.from(context);
        this.cards = cards;
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
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.ivCard.setImageResource(cards.get(i).getImageResource());
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

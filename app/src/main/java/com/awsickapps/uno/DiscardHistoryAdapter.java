package com.awsickapps.uno;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awsickapps.uno.data.Card;
import com.awsickapps.uno.data.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by allen on 3/20/15.
 */
public class DiscardHistoryAdapter extends RecyclerView.Adapter<DiscardHistoryAdapter.CardViewHolder> {

    LayoutInflater inflater;
    public List<Card> cards = Collections.emptyList();
    Context context;

    public DiscardHistoryAdapter(Context context, ArrayList<Card> cards){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cards = cards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view, parent, false);
        CardViewHolder cvh = new CardViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.ivCard.setImageResource(cards.get(position).getImageResource());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void reverseCards(){
        ArrayList<Card> reverse = new ArrayList<>();
        for(int i = cards.size() - 1; i >= 0; i--){
            reverse.add(cards.get(i));
        }
        cards = reverse;
    }

    class CardViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivCard;

        public CardViewHolder(View itemView) {
            super(itemView);
            ivCard = (ImageView) itemView.findViewById(R.id.ivCard);

        }
    }
}

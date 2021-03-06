package com.awsickapps.uno.data;

import com.awsickapps.uno.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by allen on 3/15/15.
 */
public class Pile {

    public ArrayList<Card> cards;
    int size;
    boolean isDraw;

    public Pile(boolean isDraw){

        this.isDraw = isDraw;
        cards = new ArrayList<>();
        if(isDraw)
            createDeck();
    }

    public Card drawCard(){
        if(isDraw){
            size--;
            return cards.remove(size);
        }else{
            throw new RuntimeException("Cannot draw from discard pile");
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card getTop(){
        return cards.get(size -1);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public void add(Card card){
        cards.add(card);
        size++;
    }

    private void createDeck(){

        cards.add(new Card(0, Card.Color.blue));
        cards.add(new Card(0, Card.Color.red));
        cards.add(new Card(0, Card.Color.green));
        cards.add(new Card(0, Card.Color.yellow));

        cards.add(new Card(1, Card.Color.yellow));
        cards.add(new Card(1, Card.Color.yellow));
        cards.add(new Card(1, Card.Color.green));
        cards.add(new Card(1, Card.Color.green));
        cards.add(new Card(1, Card.Color.blue));
        cards.add(new Card(1, Card.Color.blue));
        cards.add(new Card(1, Card.Color.red));
        cards.add(new Card(1, Card.Color.red));

        cards.add(new Card(2, Card.Color.yellow));
        cards.add(new Card(2, Card.Color.yellow));
        cards.add(new Card(2, Card.Color.green));
        cards.add(new Card(2, Card.Color.green));
        cards.add(new Card(2, Card.Color.blue));
        cards.add(new Card(2, Card.Color.blue));
        cards.add(new Card(2, Card.Color.red));
        cards.add(new Card(2, Card.Color.red));

        cards.add(new Card(3, Card.Color.yellow));
        cards.add(new Card(3, Card.Color.yellow));
        cards.add(new Card(3, Card.Color.green));
        cards.add(new Card(3, Card.Color.green));
        cards.add(new Card(3, Card.Color.blue));
        cards.add(new Card(3, Card.Color.blue));
        cards.add(new Card(3, Card.Color.red));
        cards.add(new Card(3, Card.Color.red));

        cards.add(new Card(4, Card.Color.yellow));
        cards.add(new Card(4, Card.Color.yellow));
        cards.add(new Card(4, Card.Color.green));
        cards.add(new Card(4, Card.Color.green));
        cards.add(new Card(4, Card.Color.blue));
        cards.add(new Card(4, Card.Color.blue));
        cards.add(new Card(4, Card.Color.red));
        cards.add(new Card(4, Card.Color.red));

        cards.add(new Card(5, Card.Color.yellow));
        cards.add(new Card(5, Card.Color.yellow));
        cards.add(new Card(5, Card.Color.green));
        cards.add(new Card(5, Card.Color.green));
        cards.add(new Card(5, Card.Color.blue));
        cards.add(new Card(5, Card.Color.blue));
        cards.add(new Card(5, Card.Color.red));
        cards.add(new Card(5, Card.Color.red));

        cards.add(new Card(6, Card.Color.yellow));
        cards.add(new Card(6, Card.Color.yellow));
        cards.add(new Card(6, Card.Color.green));
        cards.add(new Card(6, Card.Color.green));
        cards.add(new Card(6, Card.Color.blue));
        cards.add(new Card(6, Card.Color.blue));
        cards.add(new Card(6, Card.Color.red));
        cards.add(new Card(6, Card.Color.red));

        cards.add(new Card(7, Card.Color.yellow));
        cards.add(new Card(7, Card.Color.yellow));
        cards.add(new Card(7, Card.Color.green));
        cards.add(new Card(7, Card.Color.green));
        cards.add(new Card(7, Card.Color.blue));
        cards.add(new Card(7, Card.Color.blue));
        cards.add(new Card(7, Card.Color.red));
        cards.add(new Card(7, Card.Color.red));

        cards.add(new Card(8, Card.Color.yellow));
        cards.add(new Card(8, Card.Color.yellow));
        cards.add(new Card(8, Card.Color.green));
        cards.add(new Card(8, Card.Color.green));
        cards.add(new Card(8, Card.Color.blue));
        cards.add(new Card(8, Card.Color.blue));
        cards.add(new Card(8, Card.Color.red));
        cards.add(new Card(8, Card.Color.red));

        cards.add(new Card(9, Card.Color.yellow));
        cards.add(new Card(9, Card.Color.yellow));
        cards.add(new Card(9, Card.Color.green));
        cards.add(new Card(9, Card.Color.green));
        cards.add(new Card(9, Card.Color.blue));
        cards.add(new Card(9, Card.Color.blue));
        cards.add(new Card(9, Card.Color.red));
        cards.add(new Card(9, Card.Color.red));

        cards.add(new Card(Data.SKIP, Card.Color.yellow));
        cards.add(new Card(Data.SKIP, Card.Color.yellow));
        cards.add(new Card(Data.SKIP, Card.Color.green));
        cards.add(new Card(Data.SKIP, Card.Color.green));
        cards.add(new Card(Data.SKIP, Card.Color.blue));
        cards.add(new Card(Data.SKIP, Card.Color.blue));
        cards.add(new Card(Data.SKIP, Card.Color.red));
        cards.add(new Card(Data.SKIP, Card.Color.red));

        cards.add(new Card(Data.REVERSE, Card.Color.yellow));
        cards.add(new Card(Data.REVERSE, Card.Color.yellow));
        cards.add(new Card(Data.REVERSE, Card.Color.green));
        cards.add(new Card(Data.REVERSE, Card.Color.green));
        cards.add(new Card(Data.REVERSE, Card.Color.blue));
        cards.add(new Card(Data.REVERSE, Card.Color.blue));
        cards.add(new Card(Data.REVERSE, Card.Color.red));
        cards.add(new Card(Data.REVERSE, Card.Color.red));

        cards.add(new Card(Data.DRAW_TWO, Card.Color.yellow));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.yellow));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.green));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.green));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.blue));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.blue));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.red));
        cards.add(new Card(Data.DRAW_TWO, Card.Color.red));

        cards.add(new Card(Data.WILD, Card.Color.wild));
        cards.add(new Card(Data.WILD, Card.Color.wild));
        cards.add(new Card(Data.WILD, Card.Color.wild));
        cards.add(new Card(Data.WILD, Card.Color.wild));;

        cards.add(new Card(Data.WILD_DRAW_FOUR, Card.Color.wild));
        cards.add(new Card(Data.WILD_DRAW_FOUR, Card.Color.wild));
        cards.add(new Card(Data.WILD_DRAW_FOUR, Card.Color.wild));
        cards.add(new Card(Data.WILD_DRAW_FOUR, Card.Color.wild));

        size = cards.size();
        Collections.shuffle(cards);
    }

}

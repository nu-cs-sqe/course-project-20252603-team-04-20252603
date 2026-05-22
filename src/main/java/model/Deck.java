package model;


import java.util.ArrayDeque;
import java.util.ArrayList;

public class Deck {

    private ArrayDeque<Card> unusedCards;
    private ArrayList<Card> usedCards;
    public Deck(){
        this.unusedCards = new ArrayDeque<>();
        this.usedCards = new ArrayList<>();
    }

    public void shuffle(){

    }

    public ArrayDeque<Card> getUnusedCards(){
        return unusedCards;
    }

    public ArrayList<Card> getUsedCards(){
        return usedCards;
    }

    public void reshuffleIfEmpty(){
        if(unusedCards.isEmpty()){
            unusedCards.addAll(usedCards);
            usedCards.clear();
            shuffle();
        }
    }

    public Card draw(){
        reshuffleIfEmpty();
        if(unusedCards.isEmpty() && usedCards.isEmpty()){
            throw new IllegalStateException("Both unused and used piles are empty");
        }
        return unusedCards.removeFirst();
    }

    public void discard(Card card){
        if(card == null){
            throw new IllegalArgumentException("Card cannot be null");
        }
        if(usedCards.contains(card)){
            throw new IllegalArgumentException("Card already discarded");
        }
        usedCards.add(card);
    }
}

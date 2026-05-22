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

    public Card draw(){
        return unusedCards.removeFirst();
    }
}

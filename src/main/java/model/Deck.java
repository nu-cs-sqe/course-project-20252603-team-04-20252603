package model;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Deck {

    private final Random rand;
    private ArrayDeque<Card> unusedCards;
    private ArrayList<Card> usedCards;
    private Optional<Card> lastDrawn = Optional.empty();

    public Deck() {
        this(new Random());
    }

    Deck(Random rand) {
        this.rand = rand;
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
        lastDrawn = Optional.of(unusedCards.removeFirst());
        return lastDrawn.get();
    }

    public void discard(Card card){
        if(card == null){
            throw new IllegalArgumentException("Card cannot be null");
        }
        if(lastDrawn.isEmpty() || lastDrawn.get() != card){
            throw new IllegalArgumentException("Can only discard the card that was just drawn");
        }
        if(usedCards.contains(card)){
            throw new IllegalArgumentException("Card already discarded");
        }
        usedCards.add(card);
        lastDrawn = Optional.empty();
    }
}

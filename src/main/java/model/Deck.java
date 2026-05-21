package model;

import java.util.Deque;
import java.util.List;

public class Deck {

    private Deque<Card> unusedCards;
    private List<Card> usedCards;
    public Deck(){

    }

    public void shuffle(){

    }

    public Deque<Card> getUnusedCards(){
        return unusedCards;
    }

    public List<Card> getUsedCards(){
        return usedCards;
    }
}

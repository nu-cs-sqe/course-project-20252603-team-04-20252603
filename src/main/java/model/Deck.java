package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Deck {

    private final Random rand;
    private final ArrayDeque<Card> unusedCards;
    private final ArrayList<Card> usedCards;
    private Optional<Card> lastDrawn = Optional.empty();

    public Deck() {
        this(new Random());
    }

    Deck(Random rand) {
        this(rand, List.of(), List.of());
    }

    Deck(Random rand, Collection<Card> unused) {
        this(rand, unused, List.of());
    }

    Deck(Random rand, Collection<Card> unused, Collection<Card> used) {
        this.rand = rand;
        this.unusedCards = new ArrayDeque<>(unused);
        this.usedCards = new ArrayList<>(used);
    }

    public void shuffle() {
        ArrayList<Card> cards = new ArrayList<>(unusedCards);
        Collections.shuffle(cards, rand);
        unusedCards.clear();
        for (Card card : cards) {
            unusedCards.addLast(card);
        }
    }

    public List<Card> getUnusedCards() {
        return new ArrayList<>(unusedCards);
    }

    public List<Card> getUsedCards() {
        return new ArrayList<>(usedCards);
    }

    public void reshuffleIfEmpty() {
        if (unusedCards.isEmpty()) {
            unusedCards.addAll(usedCards);
            usedCards.clear();
            shuffle();
        }
    }

    public Card draw() {
        reshuffleIfEmpty();
        if (unusedCards.isEmpty()) {
            throw new IllegalStateException("Both unused and used piles are empty");
        }
        lastDrawn = Optional.of(unusedCards.removeFirst());
        return lastDrawn.get();
    }

    public void discard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }
        if (lastDrawn.isEmpty() || lastDrawn.get() != card) {
            throw new IllegalArgumentException("Can only discard the card that was just drawn");
        }
        if (usedCards.contains(card)) {
            throw new IllegalArgumentException("Card already discarded");
        }
        usedCards.add(card);
        lastDrawn = Optional.empty();
    }
}

package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTests {

    @Test
    public void TC1_Shuffle_EmptyUnusedPile() {
        Deck deck = new Deck();

        assertDoesNotThrow(deck::shuffle,
                "Shuffling an empty deck should not throw");

        assertTrue(deck.getUnusedCards().isEmpty(),
                "unusedCards should remain empty after shuffle");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after shuffle");
    }

    @Test
    public void TC2_Shuffle_SingleCardInUnusedPile() {
        Card c1 = new Card();
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);

        assertDoesNotThrow(deck::shuffle,
                "Shuffling a single-card deck should not throw");

        assertEquals(1, deck.getUnusedCards().size(),
                "unusedCards should still contain exactly one card");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should still contain C1");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after shuffle");
    }

}

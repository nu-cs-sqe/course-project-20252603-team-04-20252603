package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

}

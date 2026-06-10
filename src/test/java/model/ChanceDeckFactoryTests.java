package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChanceDeckFactoryTests {

    @Test
    public void TC1_standardDeck_ContainsTheSixChanceCards() {
        Deck deck = ChanceDeckFactory.standardDeck();

        assertNotNull(deck);
        List<Card> cards = deck.getUnusedCards();
        assertEquals(6, cards.size());
        for (Card card : cards) {
            assertFalse(card.getTitle().isEmpty());
        }
    }
}

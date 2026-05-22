package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
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
        Card c1 = EasyMock.createMock(Card.class);
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

    @Test
    public void TC3_Shuffle_MultipleCardsInUnusedPile() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUnusedCards().add(c3);

        assertDoesNotThrow(deck::shuffle,
                "Shuffling a multi-card deck should not throw");

        assertEquals(3, deck.getUnusedCards().size(),
                "unusedCards should still contain three cards");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should still contain C1");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should still contain C2");
        assertTrue(deck.getUnusedCards().contains(c3),
                "unusedCards should still contain C3");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after shuffle");
    }

    @Test
    public void TC4_Shuffle_DoesNotModifyUsedPile() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUsedCards().add(c3);

        assertDoesNotThrow(deck::shuffle,
                "Shuffling should not throw when used pile has cards");

        assertEquals(2, deck.getUnusedCards().size(),
                "unusedCards should still contain two cards");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should still contain C1");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should still contain C2");
        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should still contain exactly one card");
        assertTrue(deck.getUsedCards().contains(c3),
                "usedCards should still contain C3");
    }

    @Test
    public void TC5_Draw_WhenUnusedPileHasMultipleCards() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUnusedCards().add(c3);

        Card drawn = deck.draw();

        assertSame(c1, drawn, "draw should return front card C1");
        assertEquals(2, deck.getUnusedCards().size(),
                "unusedCards should have two cards after draw");
        assertFalse(deck.getUnusedCards().contains(c1),
                "drawn card C1 should be removed from unusedCards");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should still contain C2");
        assertTrue(deck.getUnusedCards().contains(c3),
                "unusedCards should still contain C3");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after draw");
    }

    @Test
    public void TC6_Draw_LastCardFromUnusedPile() {
        Card c1 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);

        Card drawn = deck.draw();

        assertSame(c1, drawn, "draw should return C1");
        assertTrue(deck.getUnusedCards().isEmpty(),
                "unusedCards should be empty after drawing last card");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after draw");
    }

}

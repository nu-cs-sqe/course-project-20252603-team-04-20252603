package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void TC7_Draw_WhenUnusedEmptyTriggersReshuffleFromUsed() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUsedCards().add(c1);
        deck.getUsedCards().add(c2);
        deck.getUsedCards().add(c3);

        Card drawn = deck.draw();

        assertNotNull(drawn, "draw should return a card after reshuffling from used");
        assertTrue(drawn == c1 || drawn == c2 || drawn == c3,
                "drawn card should be one of the reshuffled cards");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should be empty after reshuffle and draw");
        assertEquals(2, deck.getUnusedCards().size(),
                "unusedCards should hold the two remaining cards");
        assertFalse(deck.getUnusedCards().contains(drawn),
                "drawn card should no longer be in unusedCards");
        assertEquals(3, 1 + deck.getUnusedCards().size() + deck.getUsedCards().size(),
                "total card count should remain 3");
    }

    @Test
    public void TC8_Draw_WhenBothPilesEmpty_Throws() {
        Deck deck = new Deck();

        assertThrows(IllegalStateException.class, deck::draw,
                "draw should throw when both unused and used piles are empty");

        assertTrue(deck.getUnusedCards().isEmpty(),
                "unusedCards should remain empty");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty");
    }

    @Test
    public void TC9_ConsecutiveDrawsExhaustUnusedThenReshuffle() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);

        Card first = deck.draw();
        deck.getUsedCards().add(first);

        Card second = deck.draw();
        deck.getUsedCards().add(second);

        Card third = deck.draw();

        assertSame(c1, first, "first draw should return C1");
        assertSame(c2, second, "second draw should return C2");
        assertNotNull(third, "third draw should succeed after reshuffle");
        assertTrue(third == c1 || third == c2,
                "third draw should return a reshuffled card");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should be empty after reshuffle and third draw");
        assertEquals(1, deck.getUnusedCards().size(),
                "one card should remain in unusedCards");
        assertEquals(2, 1 + deck.getUnusedCards().size() + deck.getUsedCards().size(),
                "both cards should still be accounted for in the deck");
    }

    @Test
    public void TC10_Discard_NullCard_Throws() {
        Deck deck = new Deck();

        assertThrows(IllegalArgumentException.class, () -> deck.discard(null),
                "discard should reject a null card");

        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain unchanged");
    }

    @Test
    public void TC11_Discard_ValidCardAfterDraw() {
        Card c1 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);

        Card drawn = deck.draw();
        deck.discard(drawn);

        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should contain the discarded card");
        assertTrue(deck.getUsedCards().contains(c1),
                "usedCards should contain C1");
        assertFalse(deck.getUnusedCards().contains(c1),
                "C1 should not be in unusedCards after discard");
        assertTrue(deck.getUnusedCards().isEmpty(),
                "unusedCards should be empty after drawing the only card");
    }

    @Test
    public void TC12_Discard_DoesNotChangeUnusedPile() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUnusedCards().add(c3);

        Card drawn = deck.draw();
        deck.discard(drawn);

        assertEquals(2, deck.getUnusedCards().size(),
                "unusedCards size should be unchanged by discard");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should still contain C2");
        assertTrue(deck.getUnusedCards().contains(c3),
                "unusedCards should still contain C3");
        assertFalse(deck.getUnusedCards().contains(c1),
                "drawn card C1 should not be in unusedCards");
        assertTrue(deck.getUsedCards().contains(c1),
                "usedCards should contain discarded C1");
    }

    @Test
    public void TC13_Discard_SameCardTwice_Rejected() {
        Card c1 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);

        deck.discard(deck.draw());

        assertThrows(IllegalArgumentException.class, () -> deck.discard(c1),
                "second discard of the same card should be rejected");
        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should contain at most one copy of C1");
        assertTrue(deck.getUsedCards().contains(c1),
                "usedCards should still contain C1");
    }

}

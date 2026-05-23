package model;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Random;

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
    public void TC3_Shuffle_TwoCardsReversesDequeOrder() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(2)).andReturn(0);
        EasyMock.replay(rand);

        Deck deck = new Deck(rand);
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);

        deck.shuffle();

        assertSame(c2, deck.getUnusedCards().removeFirst(),
                "front of deque should be C2 after shuffle");
        assertSame(c1, deck.getUnusedCards().removeFirst(),
                "second card in deque should be C1 after shuffle");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after shuffle");
        EasyMock.verify(rand);
    }

    @Test
    public void TC4_Shuffle_TwoCardsWithIdentitySwap() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(2)).andReturn(1);
        EasyMock.replay(rand);

        Deck deck = new Deck(rand);
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);

        deck.shuffle();

        assertSame(c1, deck.getUnusedCards().removeFirst(),
                "front of deque should remain C1 after shuffle");
        assertSame(c2, deck.getUnusedCards().removeFirst(),
                "second card in deque should remain C2 after shuffle");
        EasyMock.verify(rand);
    }

    @Test
    public void TC5_Shuffle_ThreeCardsToDeterministicOrder() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(3)).andReturn(0);
        EasyMock.expect(rand.nextInt(2)).andReturn(0);
        EasyMock.replay(rand);

        Deck deck = new Deck(rand);
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUnusedCards().add(c3);

        deck.shuffle();

        assertSame(c2, deck.getUnusedCards().removeFirst(),
                "front of deque should be C2 after shuffle");
        assertSame(c3, deck.getUnusedCards().removeFirst(),
                "second card in deque should be C3 after shuffle");
        assertSame(c1, deck.getUnusedCards().removeFirst(),
                "third card in deque should be C1 after shuffle");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty after shuffle");
        EasyMock.verify(rand);
    }

    @Test
    public void TC6_Shuffle_DoesNotModifyUsedPile() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(2)).andReturn(0);
        EasyMock.replay(rand);

        Deck deck = new Deck(rand);
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUsedCards().add(c3);

        deck.shuffle();

        assertSame(c2, deck.getUnusedCards().removeFirst(),
                "front of deque should be C2 after shuffle");
        assertSame(c1, deck.getUnusedCards().removeFirst(),
                "second card in deque should be C1 after shuffle");
        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should still contain exactly one card");
        assertSame(c3, deck.getUsedCards().get(0),
                "usedCards should still contain C3");
        EasyMock.verify(rand);
    }

    @Test
    public void TC9_Draw_WhenUnusedPileHasMultipleCards() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Card c4 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUnusedCards().add(c3);
        deck.getUsedCards().add(c4);

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
        assertTrue(deck.getUsedCards().contains(c4),
                "usedCards should be unchanged when drawing from unused pile");
    }

    @Test
    public void TC10_Draw_LastCardFromUnusedPile() {
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
    public void TC11_Draw_WhenUnusedEmptyTriggersReshuffleFromUsed() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        CountingDeck deck = new CountingDeck();
        deck.getUsedCards().add(c1);
        deck.getUsedCards().add(c2);
        deck.getUsedCards().add(c3);

        Card drawn = deck.draw();

        assertEquals(1, deck.getShuffleCount(), "draw should shuffle when unused pile is empty");
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
    public void TC12_Draw_WhenBothPilesEmpty_Throws() {
        Deck deck = new Deck();

        assertThrows(IllegalStateException.class, deck::draw,
                "draw should throw when both unused and used piles are empty");

        assertTrue(deck.getUnusedCards().isEmpty(),
                "unusedCards should remain empty");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty");
    }

    @Test
    public void TC13_ConsecutiveDrawsExhaustUnusedThenReshuffle() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);

        Card first = deck.draw();
        deck.discard(first);

        Card second = deck.draw();
        deck.discard(second);

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
    public void TC14_Discard_NullCard_Throws() {
        Deck deck = new Deck();

        assertThrows(IllegalArgumentException.class, () -> deck.discard(null),
                "discard should reject a null card");

        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain unchanged");
    }

    @Test
    public void TC15_Discard_ValidCardAfterDraw() {
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
    public void TC16_Discard_DoesNotChangeUnusedPile() {
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
    public void TC17_Discard_SameCardTwice_Rejected() throws Exception {
        Card c1 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);

        deck.discard(deck.draw());

        assertThrows(IllegalArgumentException.class, () -> deck.discard(c1),
                "second discard of the same card should be rejected");

        Field lastDrawnField = Deck.class.getDeclaredField("lastDrawn");
        lastDrawnField.setAccessible(true);
        lastDrawnField.set(deck, Optional.of(c1));

        IllegalArgumentException alreadyDiscarded = assertThrows(IllegalArgumentException.class,
                () -> deck.discard(c1));
        assertTrue(alreadyDiscarded.getMessage().contains("already discarded"));

        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should contain at most one copy of C1");
        assertTrue(deck.getUsedCards().contains(c1),
                "usedCards should still contain C1");
    }

    @Test
    public void TC18_Discard_CardNotInEitherPile_Rejected() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c2);
        deck.getUsedCards().add(c1);

        deck.draw();

        assertThrows(IllegalArgumentException.class, () -> deck.discard(c3),
                "discard should reject a card that was not just drawn");

        assertTrue(deck.getUnusedCards().isEmpty(),
                "drawn card should be removed from unusedCards");
        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should be unchanged");
        assertTrue(deck.getUsedCards().contains(c1),
                "usedCards should still contain C1");
        assertFalse(deck.getUsedCards().contains(c3),
                "unknown card C3 should not be added to usedCards");
    }

    @Test
    public void TC19_ReshuffleIfEmpty_UnusedNotEmpty_NoOp() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.getUsedCards().add(c3);

        deck.reshuffleIfEmpty();

        assertEquals(2, deck.getUnusedCards().size(),
                "unusedCards should be unchanged");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should still contain C1");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should still contain C2");
        assertEquals(1, deck.getUsedCards().size(),
                "usedCards should be unchanged");
        assertTrue(deck.getUsedCards().contains(c3),
                "usedCards should still contain C3");
    }

    @Test
    public void TC20_ReshuffleIfEmpty_UnusedEmptyMovesOneCard() {
        Card c1 = EasyMock.createMock(Card.class);
        CountingDeck deck = new CountingDeck();
        deck.getUsedCards().add(c1);

        deck.reshuffleIfEmpty();

        assertEquals(1, deck.getShuffleCount(), "reshuffleIfEmpty should call shuffle");
        assertEquals(1, deck.getUnusedCards().size(),
                "unusedCards should contain the reshuffled card");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should contain C1");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should be empty after reshuffle");
    }

    @Test
    public void TC21_ReshuffleIfEmpty_UnusedEmptyMovesMultipleCards() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUsedCards().add(c1);
        deck.getUsedCards().add(c2);
        deck.getUsedCards().add(c3);

        deck.reshuffleIfEmpty();

        assertEquals(3, deck.getUnusedCards().size(),
                "all used cards should move to unusedCards");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should contain C1");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should contain C2");
        assertTrue(deck.getUnusedCards().contains(c3),
                "unusedCards should contain C3");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should be empty after reshuffle");
        assertEquals(3, deck.getUnusedCards().size() + deck.getUsedCards().size(),
                "total card count should remain 3");
    }

    @Test
    public void TC22_ReshuffleIfEmpty_BothPilesEmpty_NoOp() {
        Deck deck = new Deck();

        assertDoesNotThrow(deck::reshuffleIfEmpty,
                "reshuffleIfEmpty should not throw when both piles are empty");

        assertTrue(deck.getUnusedCards().isEmpty(),
                "unusedCards should remain empty");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should remain empty");
    }

    @Test
    public void TC23_ReshuffleIfEmpty_PreservesTotalCardCount() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);
        Card c3 = EasyMock.createMock(Card.class);
        Card c4 = EasyMock.createMock(Card.class);
        Deck deck = new Deck();
        deck.getUsedCards().add(c1);
        deck.getUsedCards().add(c2);
        deck.getUsedCards().add(c3);
        deck.getUsedCards().add(c4);

        deck.reshuffleIfEmpty();

        assertEquals(4, deck.getUnusedCards().size() + deck.getUsedCards().size(),
                "total card count should remain 4 after reshuffle");
        assertTrue(deck.getUsedCards().isEmpty(),
                "usedCards should be empty after reshuffle");
        assertEquals(4, deck.getUnusedCards().size(),
                "all four cards should be in unusedCards");
        assertTrue(deck.getUnusedCards().contains(c1),
                "unusedCards should contain C1");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should contain C2");
        assertTrue(deck.getUnusedCards().contains(c3),
                "unusedCards should contain C3");
        assertTrue(deck.getUnusedCards().contains(c4),
                "unusedCards should contain C4");
    }

    @Test
    public void TC24_FullChanceTileCycle_DrawThenDiscard() {
        Card c1 = EasyMock.createMock(Card.class);
        Card c2 = EasyMock.createMock(Card.class);

        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(2)).andReturn(1);
        EasyMock.replay(rand);

        Deck deck = new Deck(rand);
        deck.getUnusedCards().add(c1);
        deck.getUnusedCards().add(c2);
        deck.shuffle();

        Card drawn = deck.draw();
        deck.discard(drawn);

        assertSame(c1, drawn, "draw should return the top card");
        assertEquals(1, deck.getUsedCards().size(),
                "drawn card should be in usedCards after discard");
        assertTrue(deck.getUsedCards().contains(drawn),
                "usedCards should contain the discarded card");
        assertFalse(deck.getUnusedCards().contains(drawn),
                "drawn card should not be in unusedCards after discard");
        assertEquals(1, deck.getUnusedCards().size(),
                "remaining unused card should still be in the deck");
        assertTrue(deck.getUnusedCards().contains(c2),
                "unusedCards should still contain the undrawn card");
        EasyMock.verify(rand);
    }

    private static class CountingDeck extends Deck {
        private int shuffleCount;

        @Override
        public void shuffle() {
            shuffleCount++;
        }

        int getShuffleCount() {
            return shuffleCount;
        }
    }

}

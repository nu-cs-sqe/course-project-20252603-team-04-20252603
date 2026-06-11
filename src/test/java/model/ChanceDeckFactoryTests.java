package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void TC2_standardDeck_WithDeterministicRandom_ShufflesCardsBeforeReturning() {
        Random random = EasyMock.createMock(Random.class);
        EasyMock.expect(random.nextInt(6)).andReturn(0);
        EasyMock.expect(random.nextInt(5)).andReturn(0);
        EasyMock.expect(random.nextInt(4)).andReturn(0);
        EasyMock.expect(random.nextInt(3)).andReturn(0);
        EasyMock.expect(random.nextInt(2)).andReturn(0);
        EasyMock.replay(random);

        Deck deck = ChanceDeckFactory.standardDeck(random);

        List<Card> cards = deck.getUnusedCards();
        assertEquals(6, cards.size());
        assertSame(GoToJailCardEffect.class, cards.get(0).getCardEffect().getClass());
        assertSame(GoBackThreeSpacesCardEffect.class, cards.get(1).getCardEffect().getClass());
        assertSame(AIBubblePopCardEffect.class, cards.get(2).getCardEffect().getClass());
        assertSame(SubscriptionServiceCardEffect.class, cards.get(3).getCardEffect().getClass());
        assertSame(StockMarketCrashCardEffect.class, cards.get(4).getCardEffect().getClass());
        assertSame(AdvanceToGoCardEffect.class, cards.get(5).getCardEffect().getClass());
        EasyMock.verify(random);
    }

    @Test
    public void TC3_privateConstructor_CannotBeUsedOutsideReflection() throws Exception {
        Constructor<ChanceDeckFactory> constructor =
                ChanceDeckFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()),
                "ChanceDeckFactory should not be instantiable");

        constructor.setAccessible(true);
        ChanceDeckFactory instance = constructor.newInstance();
        assertNotNull(instance);
    }
}

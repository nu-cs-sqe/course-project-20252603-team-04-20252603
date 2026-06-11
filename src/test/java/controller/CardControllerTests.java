package controller;

import java.util.Map;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.Card;
import model.CardEffect;
import model.Deck;
import model.GameEngine;
import model.Player;

public class CardControllerTests {

    @Test
    public void constructor_OnNullDeck_ThrowsNullPointerException() {
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(game);

        assertThrows(NullPointerException.class,
                () -> new CardController(null, game));

        EasyMock.verify(game);
    }

    @Test
    public void constructor_OnNullGame_ThrowsNullPointerException() {
        Deck deck = EasyMock.createMock(Deck.class);
        EasyMock.replay(deck);

        assertThrows(NullPointerException.class,
                () -> new CardController(deck, null));

        EasyMock.verify(deck);
    }

    @Test
    public void constructor_OnBothNull_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new CardController(null, null));
    }

    @Test
    public void constructor_OnValidDeckAndGame_ConstructsSuccessfully() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(deck, game);

        assertDoesNotThrow(() -> new CardController(deck, game));

        EasyMock.verify(deck, game);
    }

    @Test
    public void drawChanceCard_OnNullPlayer_ThrowsNullPointerException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        assertThrows(NullPointerException.class,
                () -> controller.drawChanceCard(null));

        EasyMock.verify(deck, game);
    }

    @Test
    public void drawChanceCard_OnInactivePlayer_ThrowsIllegalArgumentException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(deck, game, player);

        CardController controller = new CardController(deck, game);

        assertThrows(IllegalArgumentException.class,
                () -> controller.drawChanceCard(player));

        EasyMock.verify(deck, game, player);
    }

    @Test
    public void drawChanceCard_OnActivePlayer_ReturnsTopCardAndDrawsOnce() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        Card c1 = new Card("Advance to GO", "Advance to GO. Collect $200.", (p, g) -> {});

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(deck.draw()).andReturn(c1);
        EasyMock.replay(deck, game, player);

        CardController controller = new CardController(deck, game);

        assertSame(c1, controller.drawChanceCard(player));

        EasyMock.verify(deck, game, player);
    }

    @Test
    public void drawChanceCard_OnExhaustedDeck_PropagatesIllegalStateException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);

        EasyMock.expect(player.getActive()).andReturn(true);
        EasyMock.expect(deck.draw()).andThrow(new IllegalStateException("empty"));
        EasyMock.replay(deck, game, player);

        CardController controller = new CardController(deck, game);

        assertThrows(IllegalStateException.class,
                () -> controller.drawChanceCard(player));

        EasyMock.verify(deck, game, player);
    }

    @Test
    public void applyCard_OnNullCard_ThrowsNullPointerException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        EasyMock.replay(deck, game, player);

        CardController controller = new CardController(deck, game);

        assertThrows(NullPointerException.class,
                () -> controller.applyCard(null, player));

        EasyMock.verify(deck, game, player);
    }

    @Test
    public void applyCard_OnNullPlayer_ThrowsNullPointerException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Card card = new Card("Advance to GO", "Advance to GO. Collect $200.", (p, g) -> {});
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        assertThrows(NullPointerException.class,
                () -> controller.applyCard(card, null));

        EasyMock.verify(deck, game);
    }

    @Test
    public void applyCard_OnBothNull_ThrowsNullPointerException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        assertThrows(NullPointerException.class,
                () -> controller.applyCard(null, null));

        EasyMock.verify(deck, game);
    }

    @Test
    public void applyCard_OnInactivePlayer_ThrowsIllegalArgumentException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        Card card = new Card("Advance to GO", "Advance to GO. Collect $200.", (p, g) -> {});

        EasyMock.expect(player.getActive()).andReturn(false);
        EasyMock.replay(deck, game, player);

        CardController controller = new CardController(deck, game);

        assertThrows(IllegalArgumentException.class,
                () -> controller.applyCard(card, player));

        EasyMock.verify(deck, game, player);
    }

    @Test
    public void applyCard_OnValidCardAndActivePlayer_InvokesEffectOnce() {
        Deck deck = EasyMock.createNiceMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        CardEffect effect = EasyMock.createMock(CardEffect.class);
        Card card = new Card("Advance to GO", "Advance to GO. Collect $200.", effect);

        EasyMock.expect(player.getActive()).andReturn(true);
        effect.apply(player, game);
        EasyMock.expectLastCall().once();
        EasyMock.replay(deck, game, player, effect);

        CardController controller = new CardController(deck, game);

        controller.applyCard(card, player);

        EasyMock.verify(deck, game, player, effect);
    }

    @Test
    public void applyCard_OnValidCard_DiscardsSameCardOnce() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        CardEffect effect = EasyMock.createMock(CardEffect.class);
        Card card = new Card("Advance to GO", "Advance to GO. Collect $200.", effect);

        EasyMock.expect(player.getActive()).andReturn(true);
        effect.apply(player, game);
        EasyMock.expectLastCall().once();
        deck.discard(card);
        EasyMock.expectLastCall().once();
        EasyMock.replay(deck, game, player, effect);

        CardController controller = new CardController(deck, game);

        controller.applyCard(card, player);

        EasyMock.verify(deck, game, player, effect);
    }

    @Test
    public void applyCard_OnEffectThrows_PropagatesAndDoesNotDiscard() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Player player = EasyMock.createMock(Player.class);
        CardEffect effect = EasyMock.createMock(CardEffect.class);
        Card card = new Card("Advance to GO", "Advance to GO. Collect $200.", effect);

        EasyMock.expect(player.getActive()).andReturn(true);
        effect.apply(player, game);
        EasyMock.expectLastCall().andThrow(new RuntimeException("boom"));
        EasyMock.replay(deck, game, player, effect);

        CardController controller = new CardController(deck, game);

        assertThrows(RuntimeException.class,
                () -> controller.applyCard(card, player));

        EasyMock.verify(deck, game, player, effect);
    }

    @Test
    public void showCard_OnNullCard_ThrowsNullPointerException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        assertThrows(NullPointerException.class,
                () -> controller.showCard(null));

        EasyMock.verify(deck, game);
    }

    @Test
    public void showCard_OnValidCard_ReturnsTitleAndDescriptionMap() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Card card = new Card("Advance to GO", "Advance to GO. Collect $200.", (p, g) -> {});
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        Map<String, String> result = controller.showCard(card);

        assertEquals(2, result.size());
        assertEquals("Advance to GO", result.get("title"));
        assertEquals("Advance to GO. Collect $200.", result.get("description"));

        EasyMock.verify(deck, game);
    }

    @Test
    public void showCard_OnDifferingTitleAndDescription_MapsToCorrectKeys() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Card card = new Card("Stock Market Crash", "Every player pays $200.", (p, g) -> {});
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        Map<String, String> result = controller.showCard(card);

        assertEquals("Stock Market Crash", result.get("title"));
        assertEquals("Every player pays $200.", result.get("description"));

        EasyMock.verify(deck, game);
    }

    @Test
    public void showCard_OnSpecialCharacters_PreservesValuesExactly() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        Card card = new Card("Pay $100!", "Pay $100 for a subscription service!", (p, g) -> {});
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        Map<String, String> result = controller.showCard(card);

        assertEquals("Pay $100!", result.get("title"));
        assertEquals("Pay $100 for a subscription service!", result.get("description"));

        EasyMock.verify(deck, game);
    }
}

package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardControllerTests {

    // ==================================================================================================
    // drawChanceCard(Player player)
    // ==================================================================================================

    // TC1: Null player -> IllegalArgumentException; deck.draw() not called
    @Test
    public void drawChanceCard_OnNullPlayer_ThrowsIllegalArgumentException() {
        Deck deck = EasyMock.createMock(Deck.class);
        GameEngine game = EasyMock.createMock(GameEngine.class);
        EasyMock.replay(deck, game);

        CardController controller = new CardController(deck, game);

        assertThrows(IllegalArgumentException.class,
                () -> controller.drawChanceCard(null));

        EasyMock.verify(deck, game);
    }

    // TC2: Inactive (eliminated) player -> IllegalArgumentException; deck.draw() not called
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

    // TC3: Active player draws the top card -> returns C1; deck.draw() called once
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

    // TC4: Deck exhausted -> deck.draw() throws IllegalStateException; propagates to caller
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
}

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
}
